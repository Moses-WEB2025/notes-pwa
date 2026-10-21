/* ============ Рисование: бесконечный холст в стиле «Заметок» Xiaomi ============
   - бесконечный холст с точечной сеткой, пан/зум двумя пальцами
   - карандаш, маркер, ластик, перо, выделение, фото, текст, заметка
   - палитра с оттенками и полосой тона (как в системном приложении)
   - «поднимающийся» выбранный инструмент и пружинные анимации
   - сохранение векторного документа в IndexedDB и предпросмотр в заметку
*/
import { Store, uid, vibrate } from '../core.js';
import { toast } from './ui.js';
import { setEdgeColor, refreshEdgeColor } from '../edge.js';
import { toolSkin, initToolSkins, setToolSkinColor, setToolSkinActive } from './drawing-skins.js';

const TAU = Math.PI * 2;
const GRID = 21.33;                 // шаг точек в world-координатах (как на скриншоте)
const SHADE_L = [22, 35, 52, 56, 64, 74, 87];
const MAX_HISTORY = 40;
const FONT = `-apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif`;

/* Тональная полоса: 13 цветов + серый */
const HUES = [
  { h: 2,   s: 90,  c: '#fc382c' },
  { h: 22,  s: 100, c: '#ff6a03' },
  { h: 33,  s: 100, c: '#fe9f0f' },
  { h: 43,  s: 100, c: '#ffbb1a' },
  { h: 52,  s: 100, c: '#fed800' },
  { h: 66,  s: 95,  c: '#d4de0c' },
  { h: 88,  s: 97,  c: '#97e104' },
  { h: 135, s: 86,  c: '#1dce38' },
  { h: 178, s: 92,  c: '#11c9c7' },
  { h: 207, s: 100, c: '#3383ff' },
  { h: 236, s: 85,  c: '#7867f8' },
  { h: 268, s: 88,  c: '#b84af7' },
  { h: 325, s: 88,  c: '#e73f94' },
  { h: 0,   s: 0,   c: '#808080', gray: true },
];

const TOOLS = [
  { id: 'pen',          label: 'Карандаш',         w: 40, ink: '#1c1c1c' },
  { id: 'markerBlue',   label: 'Голубой маркер',   w: 56, ink: '#5aaef8', marker: true },
  { id: 'markerYellow', label: 'Жёлтый маркер',    w: 52, ink: '#ffab3f', marker: true },
  { id: 'fountain',     label: 'Перо',             w: 52, ink: '#69cf36' },
  { id: 'eraser',       label: 'Ластик',           w: 50 },
  { id: 'image',        label: 'Изображение',      w: 66, ml: 15 },
  { id: 'text',         label: 'Текст',            w: 65, ml: 12, ink: '#1c1c1c' },
  { id: 'note',         label: 'Заметка',          w: 73, ml: 12, ink: '#1c1c1c' },
];
const TOOL_META = Object.fromEntries(TOOLS.map(t => [t.id, t]));
const DEFAULT_INK = Object.fromEntries(TOOLS.filter(t => t.ink).map(t => [t.id, t.ink]));

/* Настройки кистей: у первых четырёх инструментов — толщина и прозрачность,
   у ластика — толщина и режим стирания. Толщина хранится индексом ступени,
   поэтому одна и та же настройка одинаково читается на всех инструментах. */
const BRUSH_IDS = ['pen', 'markerBlue', 'markerYellow', 'fountain', 'eraser'];
const BASE_WIDTH = { pen: 2.6, markerBlue: 20, markerYellow: 22, fountain: 3.1, eraser: 14 };
const SIZE_STEPS = [.42, .64, 1, 1.55, 2.3];
const SIZE_DOTS = [20, 17, 14, 11, 8];   // диаметр точки в карточке: от крупной к мелкой
const SIZE_LABELS = ['Самая толстая', 'Толстая', 'Средняя', 'Тонкая', 'Самая тонкая'];
const ERASER_MODES = ['whole', 'spot'];
const MIN_OPACITY = .1;
function defaultToolSet() {
  return {
    pen:          { size: 2, opacity: 1 },
    markerBlue:   { size: 2, opacity: 1 },
    markerYellow: { size: 2, opacity: 1 },
    fountain:     { size: 2, opacity: 1 },
    eraser:       { size: 2, mode: 'whole' },
  };
}

const SVG_UNDO = `<svg viewBox="0 0 28 28" aria-hidden="true"><path d="M4.6 8.4h11.2a6.7 6.7 0 0 1 6.7 6.7v7.4" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"/><path d="M9.7 3.5 4.5 8.4l5.2 4.9" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/></svg>`;
const SVG_REDO = `<svg viewBox="0 0 28 28" aria-hidden="true"><g transform="matrix(-1 0 0 1 28 0)"><path d="M4.6 8.4h11.2a6.7 6.7 0 0 1 6.7 6.7v7.4" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"/><path d="M9.7 3.5 4.5 8.4l5.2 4.9" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/></g></svg>`;
const SVG_EXPAND = `<svg viewBox="0 0 28 28" aria-hidden="true"><path d="M3.6 10.6V3.6h7" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/><path d="M17.4 24.4h7v-7" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/><circle cx="17.2" cy="10.7" r="1.75" fill="currentColor"/></svg>`;
const SVG_CHECK = `<svg viewBox="0 0 28 28" aria-hidden="true"><path d="M3.6 15.4l6.9 6.6L24.6 5.6" fill="none" stroke="currentColor" stroke-width="2.15" stroke-linecap="round" stroke-linejoin="round"/></svg>`;
const SVG_TRASH = `<svg viewBox="0 0 24 24" aria-hidden="true"><path d="M4 7h16M9.5 7V4.8h5V7M6.7 7l.8 12.2h9l.8-12.2M10.2 10.2v5.6M13.8 10.2v5.6" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"/></svg>`;
const SVG_ERASE_STROKE = `<svg class="dw-mode-ico" viewBox="0 0 24 24" aria-hidden="true"><g transform="rotate(-38 12 11.4)"><rect x="6" y="7.4" width="12" height="8" rx="2.4" fill="none" stroke="currentColor" stroke-width="1.7"/><path d="M11 7.6v7.8" stroke="currentColor" stroke-width="1.7"/></g><path d="M4.4 20.6h15.2" fill="none" stroke="currentColor" stroke-width="1.7" stroke-linecap="round"/></svg>`;
const SVG_OK_SM = `<svg viewBox="0 0 24 24" aria-hidden="true"><path d="M4.5 12.6l5 5L19.8 6.8" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/></svg>`;

const clamp = (v, a, b) => Math.min(b, Math.max(a, v));
function hslHex(h, s, l) {
  s = clamp(s, 0, 100) / 100; l = clamp(l, 0, 100) / 100;
  const k = n => (n + h / 30) % 12;
  const a = s * Math.min(l, 1 - l);
  const f = n => l - a * Math.max(-1, Math.min(k(n) - 3, 9 - k(n), 1));
  const to = x => Math.round(255 * x).toString(16).padStart(2, '0');
  return `#${to(f(0))}${to(f(8))}${to(f(4))}`;
}
function rgbOf(hex) {
  const m = /^#?([0-9a-f]{6})$/i.exec(hex || '');
  const n = m ? parseInt(m[1], 16) : 0;
  return [(n >> 16) & 255, (n >> 8) & 255, n & 255];
}
function mixHex(a, b, t) {
  const A = rgbOf(a), B = rgbOf(b);
  return '#' + A.map((v, i) => Math.round(v + (B[i] - v) * t).toString(16).padStart(2, '0')).join('');
}
const tint = (c, t) => mixHex(c, '#ffffff', t);
const shade = (c, t) => mixHex(c, '#000000', t);

export function openDrawing(onDone, opts = {}) {
  const ov = document.createElement('div');
  ov.className = 'draw-overlay';
  ov.dataset.tool = 'pen';

  /* Панель инструментов масштабируется от высоты экрана: на телефоне
     остаётся нативной (178dp), на планшете чуть крупнее. Скины рисуются
     в натуральную величину, поэтому --dw-z просто масштабирует полотно. */
  function fitDock() {
    const vh = Math.max(420, Math.round(ov.getBoundingClientRect().height || window.innerHeight || 800));
    const h = Math.round(clamp(vh * 0.21, 132, 208));
    ov.style.setProperty('--dw-dock-h', h + 'px');
    // скины нарисованы под панель 178: масштаб = высота панели / 178
    ov.style.setProperty('--dw-z', (h / 178).toFixed(4));
  }
  document.body.appendChild(ov);
  fitDock();

  const hueBg = HUES.map((h, i) => `${h.c} ${(i / HUES.length) * 100}% ${((i + 1) / HUES.length) * 100}%`).join(',');
  const swatches = Array.from({ length: 7 }, (_, i) =>
    `<button type="button" class="dw-swatch" data-shade="${i}" style="--i:${i}" aria-label="Оттенок ${i + 1}"></button>`).join('');

  ov.innerHTML = `
    <canvas class="dw-canvas"></canvas>
    <div class="dw-ui-layer"></div>
    <div class="dw-top">
      <button type="button" class="dw-ib" data-a="undo" aria-label="Отменить" disabled>${SVG_UNDO}</button>
      <button type="button" class="dw-ib" data-a="redo" aria-label="Повторить" disabled>${SVG_REDO}</button>
      <span class="dw-top-spacer"></span>
      <button type="button" class="dw-ib" data-a="focus" aria-label="Скрыть панели">${SVG_EXPAND}</button>
      <button type="button" class="dw-ib dw-ib-check" data-a="done" aria-label="Готово">${SVG_CHECK}</button>
    </div>
    <div class="dw-dock">
      <button type="button" class="dw-color" data-a="color" aria-label="Выбрать цвет"><span class="dw-color-dot"></span></button>
      <div class="dw-strip">
        ${TOOLS.map((t, i) => `<button type="button" class="dw-tool" data-tool="${t.id}" style="width:calc(${t.w}px * var(--dw-z));margin-left:calc(${t.ml || 0}px * var(--dw-z));--i:${i}" aria-label="${t.label}">${toolSkin(t.id)}</button>`).join('')}
      </div>
    </div>
    <div class="dw-opts is-off" aria-hidden="true">
      <div class="dw-opt-card dw-opt-alpha">
        <div class="dw-alpha-track" data-alpha role="slider" aria-label="Прозрачность" aria-valuemin="10" aria-valuemax="100" aria-valuenow="100">
          <span class="dw-alpha-knob" data-alpha-knob></span>
        </div>
      </div>
      <div class="dw-opt-card dw-opt-mode" role="group" aria-label="Режим ластика">
        <button type="button" class="dw-mode-btn" data-mode="spot" aria-label="Стирать участками" title="Стирать участками"><span class="dw-mode-ico is-checker" aria-hidden="true"></span></button>
        <button type="button" class="dw-mode-btn" data-mode="whole" aria-label="Стирать штрих целиком" title="Стирать штрих целиком">${SVG_ERASE_STROKE}</button>
      </div>
      <div class="dw-opt-card dw-opt-size" role="group" aria-label="Толщина">
        ${SIZE_DOTS.map((d, i) => `<button type="button" class="dw-size" data-size="${SIZE_DOTS.length - 1 - i}" aria-label="${SIZE_LABELS[i]}"><i class="dw-size-dot" style="--d:${d}px"></i></button>`).join('')}
      </div>
    </div>
    <div class="dw-palette" aria-hidden="true">
      <div class="dw-swatch-card"><div class="dw-swatches">${swatches}</div></div>
      <div class="dw-hue" data-hue style="background:linear-gradient(90deg,${hueBg})">
        <div class="dw-knob" data-knob></div>
      </div>
    </div>
    <button type="button" class="dw-focus-exit" data-a="focus" aria-label="Показать панели">${SVG_EXPAND}</button>
    <button type="button" class="hidden" data-a="close" aria-hidden="true"></button>`;

  document.body.classList.add('dw-open');
  try { setEdgeColor('#ffffff'); } catch (e) {}
  ov.addEventListener('touchmove', e => { if (e.touches.length > 1) e.preventDefault(); }, { passive: false });

  const canvas = ov.querySelector('.dw-canvas');
  const ctx = canvas.getContext('2d');
  const scene = document.createElement('canvas');
  const sctx = scene.getContext('2d');
  /* Слой «чернил»: всё нарисованное лежит отдельно от бумаги. Так ластик
     стирает штрихи, а не точки-сетку под ними, и мультиплай маркера
     смешивается только с чернилами, а не с точками бумаги. */
  const ink = document.createElement('canvas');
  const ictx = ink.getContext('2d');
  const ui = ov.querySelector('.dw-ui-layer');
  const strip = ov.querySelector('.dw-strip');
  const hueBar = ov.querySelector('[data-hue]');
  const knob = ov.querySelector('[data-knob]');
  const colorDot = ov.querySelector('.dw-color-dot');
  const colorBtn = ov.querySelector('[data-a=color]');
  const btnUndo = ov.querySelector('[data-a=undo]');
  const btnRedo = ov.querySelector('[data-a=redo]');
  const toolEls = [...ov.querySelectorAll('.dw-tool')];
  const shadeEls = [...ov.querySelectorAll('.dw-swatch')];
  const optsRow = ov.querySelector('.dw-opts');
  const alphaTrack = ov.querySelector('[data-alpha]');
  const alphaKnob = ov.querySelector('[data-alpha-knob]');
  const sizeEls = [...ov.querySelectorAll('.dw-size')];
  const modeEls = [...ov.querySelectorAll('.dw-mode-btn')];
  const measure = document.createElement('canvas').getContext('2d');

  /* ---------- Состояние ---------- */
  const drawId = opts.drawId || ('draw_' + uid());
  let items = [];
  let view = { x: 0, y: 0, s: 1 };
  let viewReady = false;
  let doc = null;
  let tool = 'pen';
  let toolInk = { ...DEFAULT_INK };
  let toolSet = defaultToolSet();
  const sizeStep = (id = tool) => SIZE_STEPS[(toolSet[id] || {}).size] || 1;
  function eraserRadius() { return BASE_WIDTH.eraser * sizeStep('eraser'); }
  let hueIndex = 3;                 // палитра: амбра по умолчанию, пока не синхронизирована
  let shadeIndex = 3;
  const activeInk = () => toolInk[tool] || null;

  function applyToolAccent(el, id) {
    const c = toolInk[id];
    if (!c) return;
    el.style.setProperty('--ink', c);
    el.style.setProperty('--ink-soft', tint(c, .55));
    el.style.setProperty('--ink-deep', shade(c, .18));
    setToolSkinColor(id, c);
  }
  function applyAllToolAccents() {
    toolEls.forEach(el => applyToolAccent(el, el.dataset.tool));
  }
  function updateColorUI() {
    const c = activeInk();
    colorBtn.classList.toggle('no-color', !c);
    if (c) colorDot.style.background = c;
    syncOpts();
  }

  /* ---------- Настройки кисти (толщина / прозрачность / режим ластика) ---------- */
  function hexA(hex, a) {
    const [r, g, b] = rgbOf(hex || '#1c1c1c');
    return `rgba(${r},${g},${b},${a})`;
  }
  function syncOpts() {
    if (!optsRow) return;
    const on = BRUSH_IDS.includes(tool);
    optsRow.classList.toggle('is-off', !on);
    optsRow.setAttribute('aria-hidden', on ? 'false' : 'true');
    if (!on) return;
    const cfg = toolSet[tool] || {};
    const ink = activeInk() || '#1c1c1c';
    ov.style.setProperty('--opt-ink', ink);
    /* полоса прозрачности: шахматка «бумаги» слева → плотный цвет инструмента справа */
    alphaTrack.style.backgroundImage =
      `linear-gradient(90deg,${hexA(ink, 0)} 0%,${ink} 100%),` +
      'repeating-conic-gradient(#ededed 0% 25%,#fff 0% 50%)';
    const op = cfg.opacity == null ? 1 : cfg.opacity;
    const p = clamp((op - MIN_OPACITY) / (1 - MIN_OPACITY), 0, 1);
    alphaKnob.style.left = (p * 100).toFixed(2) + '%';
    alphaTrack.setAttribute('aria-valuenow', String(Math.round(op * 100)));
    sizeEls.forEach(el => el.classList.toggle('is-sel', +el.dataset.size === cfg.size));
    const mode = cfg.mode || 'whole';
    modeEls.forEach(el => el.classList.toggle('is-sel', el.dataset.mode === mode));
  }
  let alphaDrag = false;
  function setOpacityAt(clientX) {
    if (!BRUSH_IDS.includes(tool) || tool === 'eraser') return;
    const r = alphaTrack.getBoundingClientRect();
    const p = clamp((clientX - r.left) / Math.max(1, r.width), 0, 1);
    const op = MIN_OPACITY + (1 - MIN_OPACITY) * p;
    toolSet[tool].opacity = Math.round(op * 100) / 100;
    syncOpts();
  }
  alphaTrack.addEventListener('pointerdown', e => {
    e.preventDefault();
    e.stopPropagation();
    alphaDrag = true;
    try { alphaTrack.setPointerCapture?.(e.pointerId); } catch (err) {}
    setOpacityAt(e.clientX);
    vibrate(3);
  });
  alphaTrack.addEventListener('pointermove', e => { if (alphaDrag) setOpacityAt(e.clientX); });
  const endAlpha = () => { alphaDrag = false; };
  alphaTrack.addEventListener('pointerup', endAlpha);
  alphaTrack.addEventListener('pointercancel', endAlpha);
  sizeEls.forEach(el => el.addEventListener('click', () => {
    if (!BRUSH_IDS.includes(tool)) return;
    const v = +el.dataset.size;
    if (toolSet[tool].size === v) return;
    toolSet[tool].size = v;
    syncOpts();
    vibrate(6);
  }));
  modeEls.forEach(el => el.addEventListener('click', () => {
    if (tool !== 'eraser') return;
    const m = el.dataset.mode;
    if (!ERASER_MODES.includes(m) || toolSet.eraser.mode === m) return;
    toolSet.eraser.mode = m;
    syncOpts();
    buildScene(); draw();
    toast(m === 'whole' ? 'Касание стирает штрих целиком' : 'Стирайте участками, как ручкой');
    vibrate(6);
  }));
  let paletteOpen = false;
  let focus = false;
  let editing = null;
  let selectedObj = null;
  let selIds = new Set();
  let selBounds = null;
  let undoStack = [];
  let redoStack = [];
  let closed = false;
  let loaded = false;
  let W = 1, H = 1, DPR = 1;

  let stroke = null;
  let eraser = null;
  let lasso = null;
  let selDrag = null;
  let objDrag = null;
  let imgResize = null;
  let panDrag = null;
  let gesture = null;
  const pointers = new Map();
  const images = new Map();

  /* ---------- Разметка ---------- */
  const topBar = ov.querySelector('.dw-top');
  const dock = ov.querySelector('.dw-dock');
  const palette = ov.querySelector('.dw-palette');
  const focusExit = ov.querySelector('.dw-focus-exit');
  const btnDone = ov.querySelector('[data-a=done]');
  const swatchCard = ov.querySelector('.dw-swatch-card');

  const chrome = document.createElement('div');
  chrome.className = 'dw-obj-chrome hidden';
  chrome.innerHTML = `<button type="button" class="dw-chip dw-chip-del" data-o="del" aria-label="Удалить">${SVG_TRASH}</button><button type="button" class="dw-handle" data-o="size" aria-label="Размер"></button>`;
  ui.appendChild(chrome);

  const lassoChrome = document.createElement('div');
  lassoChrome.className = 'dw-lasso-chrome hidden';
  lassoChrome.innerHTML = `<button type="button" class="dw-chip dw-chip-del" data-l="del" aria-label="Удалить выделение">${SVG_TRASH}</button>`;
  ui.appendChild(lassoChrome);

  /* ---------- Геометрия ---------- */
  function resize() {
    const r = ov.getBoundingClientRect();
    W = Math.max(1, r.width); H = Math.max(1, r.height);
    DPR = Math.min(window.devicePixelRatio || 1, 3);
    canvas.width = Math.round(W * DPR);
    canvas.height = Math.round(H * DPR);
    scene.width = canvas.width;
    scene.height = canvas.height;
    ink.width = canvas.width;
    ink.height = canvas.height;
    ctx.setTransform(DPR, 0, 0, DPR, 0, 0);
    sctx.setTransform(DPR, 0, 0, DPR, 0, 0);
    if (!viewReady && loaded) {
      view = (doc && doc.view) ? { ...doc.view } : { x: W / 2, y: H * 0.46, s: 1 };
      viewReady = true;
      if (!doc?.view && items.length) fitView();
    }
    if (!viewReady) { view = { x: W / 2, y: H * 0.46, s: 1 }; }
    // пока открыта клавиатура, высота вида — это видимая часть, панель не трогаем
    if (!editing) fitDock();
    buildScene(); draw(); syncChrome();
  }

  function toWorld(e) {
    const r = canvas.getBoundingClientRect();
    return { x: (e.clientX - r.left - view.x) / view.s, y: (e.clientY - r.top - view.y) / view.s };
  }
  function toWorldXY(cx, cy) {
    const r = canvas.getBoundingClientRect();
    return { x: (cx - r.left - view.x) / view.s, y: (cy - r.top - view.y) / view.s };
  }
  function toScreen(x, y) { return { x: x * view.s + view.x, y: y * view.s + view.y }; }

  /* ---------- Отрисовка ---------- */
  let raf = 0;
  function requestDraw() {
    if (raf) return;
    raf = requestAnimationFrame(() => { raf = 0; draw(); });
  }
  let buildRaf = 0;
  function requestBuild() {
    if (buildRaf) return;
    buildRaf = requestAnimationFrame(() => { buildRaf = 0; buildScene(); draw(); syncChrome(); });
  }

  /* Плавный переход вида — там, где резкий скачок читается как рывок:
     вход в правку текста, подгонка объекта под экран. */
  let viewAnim = 0;
  function animateView(to, ms = 220) {
    cancelAnimationFrame(viewAnim);
    const from = { ...view };
    const t0 = performance.now();
    const dur = Math.max(80, ms);
    const ease = t => 1 - Math.pow(1 - t, 3);
    const step = now => {
      if (closed) return;
      const k = clamp((now - t0) / dur, 0, 1);
      const e = ease(k);
      view.s = from.s + (to.s - from.s) * e;
      view.x = from.x + (to.x - from.x) * e;
      view.y = from.y + (to.y - from.y) * e;
      buildScene(); draw();
      if (editing) positionEditor();
      if (k < 1) viewAnim = requestAnimationFrame(step);
    };
    viewAnim = requestAnimationFrame(step);
  }

  function drawDots(c) {
    const step = GRID * view.s;
    if (step < 3.5) return;
    const rad = clamp(1.0 * Math.min(view.s, 1.35), 0.6, 1.4);
    c.fillStyle = '#f2f2f2';
    const x0 = Math.ceil((-view.x) / step) * step + view.x;
    const y0 = Math.ceil((-view.y) / step) * step + view.y;
    for (let x = x0; x < W; x += step) {
      for (let y = y0; y < H; y += step) {
        c.beginPath();
        c.arc(x, y, rad, 0, TAU);
        c.fill();
      }
    }
  }

  /* Объекты рисуются в слой чернил в порядке слоёв; штрихи ластика
     вырезают из него пиксели (destination-out), а не удаляют объекты. */
  function paintErase(c, e) {
    const pts = e.points;
    if (!pts || !pts.length) return;
    const r = Math.max(1, e.r || 12);
    c.save();
    c.globalCompositeOperation = 'destination-out';
    c.strokeStyle = '#000';
    c.fillStyle = '#000';
    c.lineCap = 'round';
    c.lineJoin = 'round';
    /* мягкая кромка: полупрозрачный широкий проход + плотная середина */
    if (pts.length === 1) {
      c.globalAlpha = .55;
      c.beginPath(); c.arc(pts[0].x, pts[0].y, r * 1.12, 0, TAU); c.fill();
      c.globalAlpha = 1;
      c.beginPath(); c.arc(pts[0].x, pts[0].y, r * .86, 0, TAU); c.fill();
    } else {
      for (const [k, a] of [[1.18, .45], [1, 1]]) {
        c.globalAlpha = a;
        c.lineWidth = r * 2 * k;
        c.beginPath();
        c.moveTo(pts[0].x, pts[0].y);
        for (let i = 1; i < pts.length; i++) c.lineTo(pts[i].x, pts[i].y);
        c.stroke();
      }
    }
    c.restore();
  }

  function paintInk() {
    ictx.setTransform(DPR, 0, 0, DPR, 0, 0);
    ictx.globalCompositeOperation = 'source-over';
    ictx.globalAlpha = 1;
    ictx.clearRect(0, 0, W, H);
    ictx.save();
    ictx.translate(view.x, view.y);
    ictx.scale(view.s, view.s);
    for (const it of items) {
      if (it === editing) {
        if (it.kind === 'note') drawNote(ictx, it, true);
        continue;
      }
      if (it.kind === 'stroke') drawStroke(ictx, it);
      else if (it.kind === 'erase') paintErase(ictx, it);
      else if (it.kind === 'text') drawTextObj(ictx, it);
      else if (it.kind === 'note') drawNote(ictx, it, false);
      else if (it.kind === 'image') drawImageObj(ictx, it);
    }
    if (eraser && !eraser.whole) paintErase(ictx, eraser);
    ictx.restore();
    ictx.globalCompositeOperation = 'source-over';
    ictx.globalAlpha = 1;
  }

  function buildScene() {
    sctx.setTransform(DPR, 0, 0, DPR, 0, 0);
    sctx.clearRect(0, 0, W, H);
    sctx.fillStyle = '#fff';
    sctx.fillRect(0, 0, W, H);
    drawDots(sctx);
    paintInk();
    sctx.drawImage(ink, 0, 0, W, H);
  }

  function drawLiveStroke(c) {
    c.save();
    c.translate(view.x, view.y);
    c.scale(view.s, view.s);
    drawStroke(c, stroke);
    c.restore();
  }

  function draw() {
    ctx.setTransform(DPR, 0, 0, DPR, 0, 0);
    ctx.clearRect(0, 0, W, H);
    ctx.drawImage(scene, 0, 0, W, H);
    if (stroke) drawLiveStroke(ctx);

    if (lasso) {
      const pts = lasso.points;
      if (pts.length > 1) {
        ctx.save();
        ctx.setLineDash([6, 5]);
        ctx.lineWidth = 1.6;
        ctx.strokeStyle = 'rgba(30,30,30,.55)';
        ctx.beginPath();
        const p0 = toScreen(pts[0].x, pts[0].y);
        ctx.moveTo(p0.x, p0.y);
        for (let i = 1; i < pts.length; i++) {
          const p = toScreen(pts[i].x, pts[i].y);
          ctx.lineTo(p.x, p.y);
        }
        const last = toScreen(pts[pts.length - 1].x, pts[pts.length - 1].y);
        if (lasso.closing) ctx.lineTo(p0.x, p0.y);
        ctx.stroke();
        ctx.restore();
      }
    }

    if (eraser) {
      const p = toScreen(eraser.x, eraser.y);
      ctx.save();
      ctx.beginPath();
      ctx.arc(p.x, p.y, (eraser.r || eraserRadius()) * view.s, 0, TAU);
      ctx.fillStyle = 'rgba(255,255,255,.72)';
      ctx.fill();
      ctx.lineWidth = 1;
      ctx.strokeStyle = 'rgba(0,0,0,.22)';
      ctx.stroke();
      ctx.restore();
    }
  }

  /* ---------- Кисти ---------- */
  function hashStr(str) {
    let h = 2166136261;
    for (let i = 0; i < str.length; i++) h = Math.imul(h ^ str.charCodeAt(i), 16777619);
    return h >>> 0;
  }
  function mulberry32(a) {
    return function () {
      a |= 0; a = (a + 0x6D2B79F5) | 0;
      let t = Math.imul(a ^ (a >>> 15), 1 | a);
      t = (t + Math.imul(t ^ (t >>> 7), 61 | t)) ^ t;
      return ((t ^ (t >>> 14)) >>> 0) / 4294967296;
    };
  }
  function traceSmooth(c, pts) {
    c.beginPath();
    c.moveTo(pts[0].x, pts[0].y);
    if (pts.length === 2) {
      c.lineTo(pts[1].x, pts[1].y);
      return;
    }
    for (let i = 1; i < pts.length - 1; i++) {
      const mx = (pts[i].x + pts[i + 1].x) / 2;
      const my = (pts[i].y + pts[i + 1].y) / 2;
      c.quadraticCurveTo(pts[i].x, pts[i].y, mx, my);
    }
    c.lineTo(pts[pts.length - 1].x, pts[pts.length - 1].y);
  }

  /* ---------- Геометрия штриха ---------- */
  /* Осевая линия сглаженным сплайном — общая для всех инструментов. */
  function centerPath(pts) {
    const p = new Path2D();
    p.moveTo(pts[0].x, pts[0].y);
    if (pts.length === 2) { p.lineTo(pts[1].x, pts[1].y); return p; }
    for (let i = 1; i < pts.length - 1; i++) {
      const mx = (pts[i].x + pts[i + 1].x) / 2;
      const my = (pts[i].y + pts[i + 1].y) / 2;
      p.quadraticCurveTo(pts[i].x, pts[i].y, mx, my);
    }
    p.lineTo(pts[pts.length - 1].x, pts[pts.length - 1].y);
    return p;
  }

  /* «Лента» переменной ширины: по перпендикуляру к оси откладываем половину
     толщины. Так выглядит след реального инструмента — с нажимом и сужением
     на сходе, без «бусин» на стыках отдельных сегментов. */
  function ribbonPath(pts, widthAt) {
    const n = pts.length;
    const L = new Float32Array(n * 2), R = new Float32Array(n * 2);
    for (let i = 0; i < n; i++) {
      const p = pts[i];
      const a = pts[i - 1] || p, b = pts[i + 1] || p;
      let dx = b.x - a.x, dy = b.y - a.y;
      const len = Math.hypot(dx, dy) || 1;
      dx /= len; dy /= len;
      const hw = Math.max(.08, widthAt(i, p) / 2);
      L[i * 2] = p.x - dy * hw; L[i * 2 + 1] = p.y + dx * hw;
      R[i * 2] = p.x + dy * hw; R[i * 2 + 1] = p.y - dx * hw;
    }
    const path = new Path2D();
    path.moveTo(L[0], L[1]);
    for (let i = 1; i < n; i++) path.lineTo(L[i * 2], L[i * 2 + 1]);
    for (let i = n - 1; i >= 0; i--) path.lineTo(R[i * 2], R[i * 2 + 1]);
    path.closePath();
    /* круглые торцы */
    const h0 = Math.max(.08, widthAt(0, pts[0]) / 2);
    const h1 = Math.max(.08, widthAt(n - 1, pts[n - 1]) / 2);
    path.moveTo(pts[0].x + h0, pts[0].y);
    path.arc(pts[0].x, pts[0].y, h0, 0, TAU);
    path.moveTo(pts[n - 1].x + h1, pts[n - 1].y);
    path.arc(pts[n - 1].x, pts[n - 1].y, h1, 0, TAU);
    return path;
  }

  /* Сужение к концам: реальный инструмент касается бумаги постепенно. */
  function taper(t, edge = .12) {
    const k = Math.min(t, 1 - t) / edge + .3;
    return k >= 1 ? 1 : Math.max(0, k);
  }

  /* Графит: лента с нажимом и сужением на сходе плюс рваное «зерно»
     вдоль оси — линия читается как карандаш по бумаге, а не как заливка. */
  function drawGraphite(c, s) {
    const pts = s.points;
    if (!pts || !pts.length) return;
    const rnd = mulberry32(hashStr(s.id || 'pencil'));
    const base = Math.max(.8, s.width || 2.4);
    const a = s.alpha == null ? 1 : s.alpha;
    if (pts.length === 1) {
      c.save();
      c.globalCompositeOperation = 'multiply';
      c.fillStyle = s.color;
      c.globalAlpha = .75 * a;
      c.beginPath();
      c.arc(pts[0].x, pts[0].y, Math.max(.5, base / 2), 0, TAU);
      c.fill();
      c.restore();
      return;
    }
    const line = smoothDense(pts, 3);
    const n = line.length;
    const wAt = (i, p) => Math.max(.42, base * taper(i / (n - 1), .1) * ((p.w || base) / base));
    const body = ribbonPath(line, wAt);
    const axis = centerPath(line);

    c.save();
    c.globalCompositeOperation = 'multiply';
    c.fillStyle = s.color;
    c.strokeStyle = s.color;
    c.lineCap = 'round';
    c.lineJoin = 'round';

    /* корпус графита: чуть светлее, чтобы зерно читалось */
    c.globalAlpha = .64 * a;
    c.fill(body);
    /* плотная сердцевина */
    c.globalAlpha = .46 * a;
    c.lineWidth = Math.max(.38, base * .32);
    c.stroke(axis);
    /* волокна: короткие разрывы вдоль оси — бумага «проступает» в штрихе */
    for (let k = 0; k < 5; k++) {
      c.globalAlpha = (.1 + rnd() * .18) * a;
      c.lineWidth = Math.max(.25, base * (.12 + rnd() * .34));
      c.setLineDash([.5 + rnd() * 2.2, .4 + rnd() * 1.8]);
      c.lineDashOffset = rnd() * 10;
      c.stroke(axis);
    }
    c.setLineDash([]);
    /* крупинки у края ленты */
    for (let i = 1; i < line.length; i += 2) {
      const p = line[i];
      const hw = wAt(i, p) / 2;
      for (let j = 0; j < 2; j++) {
        const a2 = rnd() * TAU, d = hw * (.72 + rnd() * .5);
        c.globalAlpha = (.09 + rnd() * .16) * a;
        c.beginPath();
        c.arc(p.x + Math.cos(a2) * d, p.y + Math.sin(a2) * d, .12 + rnd() * .3, 0, TAU);
        c.fill();
      }
    }
    c.restore();
  }

  /* Маркер: ровный тон одним проходом (без «бусин» на стыках) и multiply —
     пересечения штрихов темнеют, как у настоящего маркера. */
  function drawMarker(c, s) {
    const pts = s.points;
    if (!pts || !pts.length) return;
    const w = Math.max(2, s.width || 20);
    const a = s.alpha == null ? .3 : s.alpha;
    c.save();
    c.globalCompositeOperation = 'multiply';
    c.strokeStyle = s.color;
    c.fillStyle = s.color;
    c.lineCap = 'round';
    c.lineJoin = 'round';
    if (pts.length === 1) {
      c.globalAlpha = a;
      c.beginPath(); c.arc(pts[0].x, pts[0].y, w / 2, 0, TAU); c.fill();
      c.restore();
      return;
    }
    const axis = centerPath(smoothDense(pts, 3));
    /* впитывание по кромке */
    c.globalAlpha = a * .45;
    c.lineWidth = w * 1.06;
    c.stroke(axis);
    /* ровный корпус */
    c.globalAlpha = a;
    c.lineWidth = w;
    c.stroke(axis);
    c.restore();
  }

  /* Перо: тонкая лента с сильным нажимом, острый сход и лёгкое впитывание
     по кромке — линия как от настоящего пера. */
  function drawInk(c, s) {
    const pts = s.points;
    if (!pts || !pts.length) return;
    const base = Math.max(.6, s.width || 3);
    const a = s.alpha == null ? 1 : s.alpha;
    c.save();
    c.fillStyle = s.color;
    c.strokeStyle = s.color;
    c.lineCap = 'round';
    c.lineJoin = 'round';
    if (pts.length === 1) {
      c.globalAlpha = a;
      c.beginPath(); c.arc(pts[0].x, pts[0].y, base / 2, 0, TAU); c.fill();
      c.restore();
      return;
    }
    const line = smoothDense(pts, 5);
    const n = line.length;
    const wAt = (i, p) => Math.max(.32, (p.w || base) * taper(i / (n - 1), .09));
    const body = ribbonPath(line, wAt);
    c.globalAlpha = a;
    c.fill(body);
    /* кромка темнее — впитывание в бумагу */
    c.globalAlpha = a * .22;
    c.lineWidth = .7;
    c.stroke(body);
    c.restore();
  }

  function smoothDense(pts, sub = 4) {
    if (!pts || pts.length < 3) return pts;
    const out = [pts[0]];
    for (let i = 1; i < pts.length - 1; i++) {
      const a = pts[i - 1], b = pts[i], c = pts[i + 1];
      const ax = (a.x + b.x) / 2, ay = (a.y + b.y) / 2;
      const bx = (b.x + c.x) / 2, by = (b.y + c.y) / 2;
      const aw = (a.w || b.w || 2), bw = (b.w || c.w || 2);
      for (let s = 1; s <= sub; s++) {
        const t = s / sub, u = 1 - t;
        out.push({
          x: u * u * ax + 2 * u * t * b.x + t * t * bx,
          y: u * u * ay + 2 * u * t * b.y + t * t * by,
          w: aw + (bw - aw) * t,
        });
      }
    }
    out.push(pts[pts.length - 1]);
    return out;
  }

  function drawStroke(c, s) {
    const pts = s.points;
    if (!pts || !pts.length) return;
    if (s.tool === 'marker') return drawMarker(c, s);
    if (s.tool === 'fountain') return drawInk(c, s);
    return drawGraphite(c, s);
  }

  /* ---------- Текст ---------- */
  /* Перенос по словам — так же, как это делает textarea в редакторе:
     иначе после подтверждения текст перескакивает на другие строки. */
  function wrapLines(text, maxW, size) {
    measure.font = `500 ${size}px ${FONT}`;
    const out = [];
    const paras = String(text == null ? '' : text).split('\n');
    for (const para of paras) {
      if (!para) { out.push(''); continue; }
      let line = '';
      for (const word of para.split(/(\s+)/)) {
        if (!word) continue;
        if (!line && /^\s+$/.test(word)) continue;
        const test = line + word;
        if (line && measure.measureText(test).width > maxW) {
          out.push(line.replace(/\s+$/, ''));
          line = word.replace(/^\s+/, '');
          while (line.length > 1 && measure.measureText(line).width > maxW) {
            let cut = line.length - 1;
            while (cut > 1 && measure.measureText(line.slice(0, cut)).width > maxW) cut--;
            out.push(line.slice(0, cut));
            line = line.slice(cut);
          }
        } else {
          line = test;
        }
      }
      out.push(line);
    }
    return out;
  }
  function textLines(o) { return wrapLines(o.text, Math.max(20, o.w), o.size); }
  function textH(o) { return Math.max(1, textLines(o).length) * o.size * 1.35; }

  function drawTextObj(c, o) {
    const lines = textLines(o);
    c.save();
    c.font = `500 ${o.size}px ${FONT}`;
    c.fillStyle = o.color;
    c.textBaseline = 'top';
    const lh = o.size * 1.35;
    lines.forEach((ln, i) => c.fillText(ln, o.x, o.y + i * lh));
    c.restore();
  }

  function notePad(o) { return o.size * 0.95; }

  function drawNote(c, o, skipText) {
    const pad = notePad(o);
    c.save();
    c.shadowColor = 'rgba(0,0,0,.10)';
    c.shadowBlur = 12;
    c.shadowOffsetY = 4;
    roundRect(c, o.x, o.y, o.w, o.h, 7);
    c.fillStyle = '#fffdf1';
    c.fill();
    c.restore();

    c.save();
    roundRect(c, o.x, o.y, o.w, o.h, 7);
    c.clip();
    c.fillStyle = '#f0e7c6';
    const step = 8;
    for (let x = o.x + 12; x < o.x + o.w - 6; x += step) {
      for (let y = o.y + 12; y < o.y + o.h - 6; y += step) {
        c.beginPath(); c.arc(x, y, .7, 0, TAU); c.fill();
      }
    }
    const f = Math.min(20, o.w * .18);
    c.fillStyle = 'rgba(0,0,0,.045)';
    c.beginPath();
    c.moveTo(o.x + o.w - f, o.y);
    c.lineTo(o.x + o.w, o.y + f);
    c.lineTo(o.x + o.w - f, o.y + f);
    c.closePath();
    c.fill();
    c.restore();

    if (!skipText) {
      const lines = wrapLines(o.text, o.w - pad * 2, o.size);
      c.save();
      c.font = `500 ${o.size}px ${FONT}`;
      c.fillStyle = o.color;
      c.textBaseline = 'top';
      const lh = o.size * 1.35;
      lines.forEach((ln, i) => c.fillText(ln, o.x + pad, o.y + pad + i * lh));
      c.restore();
    }
  }

  function drawImageObj(c, o) {
    const entry = images.get(imgKey(o));
    c.save();
    roundRect(c, o.x, o.y, o.w, o.h, 8);
    c.clip();
    if (entry && entry.img && entry.img.complete !== false) {
      c.drawImage(entry.img, o.x, o.y, o.w, o.h);
    } else {
      c.fillStyle = '#f3f3f3';
      c.fillRect(o.x, o.y, o.w, o.h);
      c.fillStyle = '#cfcfcf';
      const s = Math.min(o.w, o.h) * .22;
      c.beginPath();
      c.arc(o.x + o.w / 2, o.y + o.h / 2, s, 0, TAU);
      c.fill();
      ensureImage(o);
    }
    c.restore();
    c.save();
    roundRect(c, o.x, o.y, o.w, o.h, 8);
    c.strokeStyle = 'rgba(0,0,0,.06)';
    c.lineWidth = 1 / view.s;
    c.stroke();
    c.restore();
  }

  function roundRect(c, x, y, w, h, r) {
    r = Math.min(r, w / 2, h / 2);
    c.beginPath();
    c.moveTo(x + r, y);
    c.arcTo(x + w, y, x + w, y + h, r);
    c.arcTo(x + w, y + h, x, y + h, r);
    c.arcTo(x, y + h, x, y, r);
    c.arcTo(x, y, x + w, y, r);
    c.closePath();
  }

  /* ---------- Изображения ---------- */
  const imgKey = o => o.blobId || o.src || '';
  function ensureImage(o) {
    const key = imgKey(o);
    if (!key || images.has(key)) return images.get(key) || null;
    const rec = { img: null, url: '' };
    images.set(key, rec);
    (async () => {
      try {
        let url = o.src || '';
        if (!url && o.blobId) {
          const blob = await Store.blob(o.blobId);
          if (!blob) return;
          url = URL.createObjectURL(blob);
        }
        if (!url) return;
        rec.url = url;
        const img = new Image();
        img.onload = () => { rec.img = img; if (ov.isConnected) requestBuild(); };
        img.src = url;
      } catch (e) { /* пусто */ }
    })();
    return rec;
  }

  async function preloadImages() {
    const list = items.filter(i => i.kind === 'image' && !images.get(imgKey(i))?.img);
    await Promise.all(list.map(o => new Promise(res => {
      let url = o.src || '';
      const done = () => res();
      (async () => {
        try {
          if (!url && o.blobId) {
            const blob = await Store.blob(o.blobId);
            if (blob) url = URL.createObjectURL(blob);
          }
          if (!url) return done();
          const img = new Image();
          img.onload = () => { images.set(imgKey(o), { img, url }); done(); };
          img.onerror = done;
          img.src = url;
        } catch (e) { done(); }
      })();
    })));
  }

  /* ---------- Документ ---------- */
  function contentBounds() {
    let minX = Infinity, minY = Infinity, maxX = -Infinity, maxY = -Infinity;
    const add = (x0, y0, x1, y1) => {
      if (x0 < minX) minX = x0; if (y0 < minY) minY = y0;
      if (x1 > maxX) maxX = x1; if (y1 > maxY) maxY = y1;
    };
    for (const it of items) {
      if (it.kind === 'stroke') {
        const w = (it.width || 2) / 2;
        for (const p of it.points) add(p.x - w, p.y - w, p.x + w, p.y + w);
      } else if (it.kind === 'text') {
        add(it.x, it.y, it.x + it.w, it.y + textH(it));
      } else if (it.kind === 'note' || it.kind === 'image') {
        add(it.x, it.y, it.x + it.w, it.y + it.h);
      }
    }
    if (!isFinite(minX)) return null;
    return { minX, minY, maxX, maxY, w: maxX - minX, h: maxY - minY };
  }

  function fitView() {
    const b = contentBounds();
    if (!b) return;
    const pad = 44;
    const s = clamp(Math.min((W - pad * 2) / Math.max(1, b.w), (H - pad * 2) / Math.max(1, b.h)), 0.2, 1.4);
    view.s = s;
    view.x = W / 2 - (b.minX + b.maxX) / 2 * s;
    view.y = H / 2 - (b.minY + b.maxY) / 2 * s;
  }

  function snapshot() {
    try { return { items: structuredClone(items), view: { ...view } }; }
    catch (e) { return { items: JSON.parse(JSON.stringify(items)), view: { ...view } }; }
  }
  function pushUndo() {
    undoStack.push(snapshot());
    if (undoStack.length > MAX_HISTORY) undoStack.shift();
    redoStack.length = 0;
    updateUndoBtns();
  }
  function updateUndoBtns() {
    btnUndo.disabled = !undoStack.length;
    btnRedo.disabled = !redoStack.length;
  }
  function restoreState(st) {
    items = st.items;
    view = { ...st.view };
    doc = { ...(doc || {}), view };
    editing = null;
    selectedObj = null;
    selIds.clear(); selBounds = null;
    [...ui.querySelectorAll('.dw-editor')].forEach(el => el.remove());
    buildScene(); draw(); syncChrome(); updateUndoBtns();
  }
  function doUndo() {
    if (!undoStack.length) return;
    redoStack.push(snapshot());
    restoreState(undoStack.pop());
    vibrate(6);
  }
  function doRedo() {
    if (!redoStack.length) return;
    undoStack.push(snapshot());
    restoreState(redoStack.pop());
    vibrate(6);
  }
  btnUndo.addEventListener('click', doUndo);
  btnRedo.addEventListener('click', doRedo);

  /* ---------- Палитра ---------- */
  const GRAY_SHADE_L = [5, 18, 36, 50, 66, 82, 94];
  function shades() {
    const hue = HUES[hueIndex];
    if (hue.gray) return ['#0b0b0b', '#2e2e2e', '#5c5c5c', '#808080', '#a9a9a9', '#d0d0d0', '#efefef'];
    return SHADE_L.map(L => hslHex(hue.h, hue.s, L));
  }
  function paintPalette() {
    const cols = shades();
    shadeEls.forEach((el, i) => {
      el.style.background = cols[i];
      el.classList.toggle('is-sel', i === shadeIndex);
    });
    knob.style.left = (((hueIndex + .5) / HUES.length) * 100) + '%';
    knob.style.background = cols[shadeIndex];
    knob.style.borderColor = '#fff7df';
  }
  function setHue(i, silent) {
    i = clamp(i, 0, HUES.length - 1);
    if (i === hueIndex) return;
    hueIndex = i;
    paintPalette();
    // тон применяется сразу: инструмент перекрашивается под ползунком
    if (!silent) { setInkForTool(shades()[shadeIndex]); vibrate(4); }
  }
  function syncPaletteFromInk(color) {
    const m = /^#?([0-9a-f]{6})$/i.exec(color || '');
    if (!m) return;
    const n = parseInt(m[1], 16);
    const r = ((n >> 16) & 255) / 255, g = ((n >> 8) & 255) / 255, b = (n & 255) / 255;
    const mx = Math.max(r, g, b), mn = Math.min(r, g, b);
    const l = (mx + mn) / 2;
    const d = mx - mn;
    const sat = d === 0 ? 0 : d / (1 - Math.abs(2 * l - 1));
    if (sat < .14) {
      /* серый/чёрный — отдельная шкала */
      hueIndex = HUES.length - 1;
      let si = 0, sd = 1e9;
      GRAY_SHADE_L.forEach((L, i) => {
        const dd = Math.abs(L - l * 100);
        if (dd < sd) { sd = dd; si = i; }
      });
      shadeIndex = si;
      return;
    }
    let h = 0;
    if (mx === r) h = ((g - b) / d) % 6;
    else if (mx === g) h = (b - r) / d + 2;
    else h = (r - g) / d + 4;
    h = (h * 60 + 360) % 360;
    let bi = hueIndex, bd = 1e9;
    HUES.forEach((q, i) => {
      if (q.gray) return;
      const dd = Math.min(Math.abs(q.h - h), 360 - Math.abs(q.h - h));
      if (dd < bd) { bd = dd; bi = i; }
    });
    hueIndex = bi;
    let si = shadeIndex, sd = 1e9;
    SHADE_L.forEach((L, i) => {
      const dd = Math.abs(L - l * 100);
      if (dd < sd) { sd = dd; si = i; }
    });
    shadeIndex = si;
  }
  function openPalette() {
    if (paletteOpen || !activeInk()) return;
    syncPaletteFromInk(activeInk());
    paletteOpen = true;
    ov.classList.add('palette-open');
    palette.setAttribute('aria-hidden', 'false');
    paintPalette();
    vibrate(8);
  }
  function closePalette() {
    if (!paletteOpen) return;
    paletteOpen = false;
    ov.classList.remove('palette-open');
    palette.setAttribute('aria-hidden', 'true');
  }
  /* Цвет принадлежит инструменту: у каждого свой акцент по умолчанию,
     а выбранный в палитре цвет меняет и рисунок, и сам корпус. */
  function setInkForTool(color) {
    if (!color || !TOOL_META[tool]?.ink) return;
    toolInk[tool] = color;
    const el = toolEls.find(x => x.dataset.tool === tool);
    if (el) applyToolAccent(el, tool);
    updateColorUI();
  }
  function pickShade(i) {
    if (!activeInk()) return;
    shadeIndex = i;
    setInkForTool(shades()[i]);
    paintPalette();
    vibrate(10);
    setTimeout(() => { if (!closed) closePalette(); }, 150);
  }
  shadeEls.forEach(el => el.addEventListener('click', () => pickShade(+el.dataset.shade)));
  colorBtn.addEventListener('click', () => {
    if (!activeInk()) return;
    paletteOpen ? closePalette() : openPalette();
  });

  let hueDrag = false;
  function hueAt(clientX) {
    const r = hueBar.getBoundingClientRect();
    return clamp((clientX - r.left) / Math.max(1, r.width), 0, 0.9999);
  }
  function setHueAt(clientX) {
    const frac = hueAt(clientX);
    setHue(Math.floor(frac * HUES.length));
  }
  hueBar.addEventListener('pointerdown', e => {
    e.preventDefault();
    e.stopPropagation();
    hueDrag = true;
    try { hueBar.setPointerCapture?.(e.pointerId); } catch (err) {}
    setHueAt(e.clientX);
  });
  hueBar.addEventListener('pointermove', e => {
    if (!hueDrag) return;
    setHueAt(e.clientX);
  });
  const endHue = () => { hueDrag = false; };
  hueBar.addEventListener('pointerup', endHue);
  hueBar.addEventListener('pointercancel', endHue);

  /* ---------- Инструменты ---------- */
  /* Своя прокрутка полосы: браузерный scrollTo с behavior: 'smooth' в этом
     webview может не запустить анимацию вовсе (scrollLeft оставался на
     месте), из-за чего полоса не ехала влево. Считаем сами — одинаково
     в обе стороны и без зависимости от платформы. */
  let stripAnim = 0;
  function scrollStrip(to) {
    cancelAnimationFrame(stripAnim);
    const max = Math.max(0, strip.scrollWidth - strip.clientWidth);
    const dst = clamp(to, 0, max);
    const from = strip.scrollLeft;
    if (Math.abs(dst - from) < 1) return;
    const reduce = window.matchMedia && window.matchMedia('(prefers-reduced-motion: reduce)').matches;
    if (reduce) { strip.scrollLeft = dst; return; }
    const t0 = performance.now();
    const dur = 260;
    const step = (t) => {
      const p = Math.min(1, (t - t0) / dur);
      const e = 1 - Math.pow(1 - p, 3);
      strip.scrollLeft = from + (dst - from) * e;
      if (p < 1) stripAnim = requestAnimationFrame(step);
    };
    stripAnim = requestAnimationFrame(step);
  }
  function scrollToolIntoView(id) {
    const el = toolEls.find(x => x.dataset.tool === id);
    if (!el) return;
    /* Координаты с экрана приводим к вёрстке: панель может быть отмасштаблена
       через --dw-z, а scrollLeft всегда в пикселях вёрстки. */
    const sr = strip.getBoundingClientRect();
    const er = el.getBoundingClientRect();
    const scale = strip.offsetWidth > 0 ? sr.width / strip.offsetWidth : 1;
    const k = Math.abs(scale) > 0.01 ? scale : 1;
    const l = (er.left - sr.left) / k + strip.scrollLeft;
    const r = l + er.width / k;
    const view = strip.clientWidth;
    if (l < strip.scrollLeft + 4) {
      scrollStrip(Math.max(0, l - 12));
    } else if (r > strip.scrollLeft + view - 4) {
      scrollStrip(r - view + 12);
    }
  }
  function setTool(id) {
    if (!TOOLS.some(t => t.id === id)) return;
    /* Подкручиваем полосу даже для уже выбранного инструмента: иначе после
       прокрутки вправо тап по активному левому инструменту ничего не делал. */
    if (id === tool) { scrollToolIntoView(id); return; }
    if (editing) commitEditing();
    if (selIds.size) clearSelection();
    if (selectedObj) deselectObj();
    tool = id;
    ov.dataset.tool = id;
    toolEls.forEach(el => {
      const on = el.dataset.tool === id;
      el.classList.toggle('is-active', on);
      setToolSkinActive(el.dataset.tool, on);
    });
    closePalette();
    updateColorUI();
    vibrate(7);
    scrollToolIntoView(id);
  }
  toolEls.forEach(el => {
    el.addEventListener('click', () => setTool(el.dataset.tool));
  });
  // стартовое положение
  initToolSkins(toolInk, ov);
  toolEls[0].classList.add('is-active');
  setToolSkinActive('pen', true);
  applyAllToolAccents();
  updateColorUI();

  /* ---------- Точки/штрихи ---------- */
  function pressureOf(e) {
    if (e.pointerType === 'pen' && e.pressure > 0) return e.pressure;
    return 0.5;
  }
  function startStroke(w, e) {
    const meta = TOOL_META[tool] || TOOL_META.pen;
    const isMarker = !!meta.marker;
    const cfg = toolSet[tool] || { size: 2, opacity: 1 };
    const k = SIZE_STEPS[cfg.size] || 1;
    const op = cfg.opacity == null ? 1 : cfg.opacity;
    /* базовые значения — след инструмента при среднем размере и 100% прозрачности;
       настройки из панели просто масштабируют толщину и альфу */
    const base = isMarker
      ? { width: tool === 'markerYellow' ? 22 : 20, alpha: .30 }
      : tool === 'fountain'
        ? { width: 3.1, alpha: 1 }
        : { width: (e.pointerType === 'pen' && e.pressure > 0) ? 1.9 + e.pressure * 1.6 : 2.6, alpha: 1 };
    stroke = {
      kind: 'stroke', id: uid(),
      tool: isMarker ? 'marker' : tool === 'fountain' ? 'fountain' : 'pen',
      color: activeInk() || '#1c1c1c', width: base.width * k, alpha: base.alpha * op,
      scale: k,
      pressured: tool === 'fountain', stylus: e.pointerType === 'pen',
      points: [],
    };
    addPoint(w, pressureOf(e));
    draw();
  }
  function addPoint(w, press) {
    if (!stroke) return;
    const pts = stroke.points;
    const last = pts[pts.length - 1];
    if (last) {
      const dx = w.x - last.x, dy = w.y - last.y;
      if (dx * dx + dy * dy < 0.35) return;
    }
    let width = stroke.width;
    if (stroke.pressured) {
      if (stroke.tool === 'fountain') {
        const last2 = pts[pts.length - 1];
        const dist = last2 ? Math.hypot(w.x - last2.x, w.y - last2.y) : 0;
        width = (stroke.stylus
          ? 1.6 + press * 3.6
          : 3.2 - clamp(dist / 40, 0, 1) * 0.8) * (stroke.scale || 1);
      } else {
        width = (1.6 + press * 3.2) * (stroke.scale || 1);
      }
    }
    pts.push({ x: w.x, y: w.y, w: width });
  }
  function finishStroke() {
    if (!stroke) return;
    const s = stroke; stroke = null;
    if (!s.points.length) { draw(); return; }
    pushUndo();
    items.push(s);
    sctx.setTransform(DPR, 0, 0, DPR, 0, 0);
    sctx.save();
    sctx.translate(view.x, view.y);
    sctx.scale(view.s, view.s);
    drawStroke(sctx, s);
    sctx.restore();
    draw();
  }

  /* ---------- Ластик ----------
     Стираем пиксели слоя чернил: настоящая резинка снимает часть штриха,
     а не удаляет объект целиком. */
  function addErasePoint(w) {
    if (!eraser) return false;
    const pts = eraser.points;
    const last = pts[pts.length - 1];
    if (last && Math.hypot(w.x - last.x, w.y - last.y) < eraser.r * 0.22) return false;
    pts.push({ x: w.x, y: w.y });
    eraser.x = w.x; eraser.y = w.y;
    return true;
  }
  function finishErase() {
    if (!eraser) return;
    const e = eraser; eraser = null;
    /* В режиме «Целиком» штрихи уже удалены (отмена снята перед первым
       удалением) — записывать штрих ластика в документ не нужно. */
    if (!e.whole && e.points.length) { pushUndo(); items.push(e); }
    buildScene(); draw(); syncChrome();
  }

  /* Режим «Целиком»: касание снимает весь штрих, который попал под ластик.
     Так же работает резинка, которая стирает написанное, а не вырезает дырки. */
  function strokeHitAt(it, x, y, r) {
    const pts = it.points;
    if (!pts || !pts.length) return false;
    const half = r / 2 + Math.max(1, (it.width || 2) / 2);
    if (pts.length === 1) return Math.hypot(pts[0].x - x, pts[0].y - y) <= half;
    for (let i = 1; i < pts.length; i++) {
      const a = pts[i - 1], b = pts[i];
      const vx = b.x - a.x, vy = b.y - a.y;
      const len2 = vx * vx + vy * vy;
      const t = len2 ? clamp(((x - a.x) * vx + (y - a.y) * vy) / len2, 0, 1) : 0;
      const dx = x - (a.x + vx * t), dy = y - (a.y + vy * t);
      if (Math.hypot(dx, dy) <= half) return true;
    }
    return false;
  }
  function eraseWholeAt(w, gest) {
    const hits = [];
    for (const it of items) {
      if (it.kind !== 'stroke') continue;
      if (gest.dead && gest.dead.has(it.id)) continue;
      if (strokeHitAt(it, w.x, w.y, gest.r)) hits.push(it);
    }
    if (!hits.length) return false;
    if (!gest.undone) { pushUndo(); gest.undone = true; }
    const ids = new Set(hits.map(h => h.id));
    items = items.filter(it => !ids.has(it.id));
    hits.forEach(h => { if (gest.dead) gest.dead.add(h.id); selIds.delete(h.id); });
    if (selIds.size) calcSelBounds(); else selBounds = null;
    vibrate(6);
    return true;
  }

  /* ---------- Объекты ---------- */
  function hitObject(w) {
    for (let i = items.length - 1; i >= 0; i--) {
      const o = items[i];
      if (o === editing) continue;
      if (o.kind === 'image' || o.kind === 'note') {
        if (w.x >= o.x && w.x <= o.x + o.w && w.y >= o.y && w.y <= o.y + o.h) return o;
      } else if (o.kind === 'text') {
        if (w.x >= o.x - 4 && w.x <= o.x + o.w + 4 && w.y >= o.y - 4 && w.y <= o.y + textH(o) + 4) return o;
      }
    }
    return null;
  }

  function selectObject(o) {
    selectedObj = o;
    if (selIds.size) clearSelection();
    buildScene(); draw(); syncChrome();
  }
  function deselectObj() {
    if (!selectedObj) return;
    selectedObj = null;
    syncChrome();
  }

  /* Новый объект не должен вырастать из-под панели или уезжать за край. */
  function clampObjOnScreen(o) {
    const s = view.s;
    const dockH = dock.getBoundingClientRect().height || 178;
    const topLimit = (topBar.getBoundingClientRect().bottom || 48) + 10;
    const p = toScreen(o.x, o.y);
    const h = (o.kind === 'note' ? o.h : o.size * 1.35) * s;
    const w = o.w * s;
    let dx = 0, dy = 0;
    if (p.x + w > W - 12) dx = (W - 12) - (p.x + w);
    if (p.x + dx < 12) dx = 12 - p.x;
    if (p.y + h > H - dockH - 12) dy = (H - dockH - 12) - (p.y + h);
    if (p.y + dy < topLimit) dy = topLimit - p.y;
    o.x += dx / s;
    o.y += dy / s;
  }
  function insertText(w) {
    const o = { kind: 'text', id: uid(), x: w.x, y: w.y, w: 230, text: '', size: 19, color: activeInk() || '#1c1c1c', __new: true };
    clampObjOnScreen(o);
    pushUndo();
    items.push(o);
    startEditing(o);
  }
  function insertNote(w) {
    const o = { kind: 'note', id: uid(), x: w.x - 95, y: w.y - 60, w: 190, h: 140, text: '', size: 15, color: activeInk() || '#1c1c1c', __new: true };
    clampObjOnScreen(o);
    pushUndo();
    items.push(o);
    startEditing(o);
  }
  function insertImageAt(file, w) {
    const blobId = 'dwi_' + uid();
    Store.putBlob(blobId, file).catch(() => {});
    const url = URL.createObjectURL(file);
    const img = new Image();
    img.onload = () => {
      const maxSide = 300;
      const k = Math.min(1, maxSide / Math.max(img.naturalWidth, img.naturalHeight));
      const iw = Math.max(40, img.naturalWidth * k), ih = Math.max(40, img.naturalHeight * k);
      images.set(blobId, { img, url });
      const o = { kind: 'image', id: uid(), x: w.x - iw / 2, y: w.y - ih / 2, w: iw, h: ih, blobId };
      pushUndo();
      items.push(o);
      selectObject(o);
      buildScene(); draw();
    };
    img.src = url;
  }
  function pickImage(w) {
    const inp = document.createElement('input');
    inp.type = 'file';
    inp.accept = 'image/*';
    inp.className = 'dw-file';
    inp.style.cssText = 'position:fixed;left:-9999px;top:0;width:1px;height:1px;opacity:0;';
    document.body.appendChild(inp);
    inp.onchange = () => {
      const f = inp.files && inp.files[0];
      inp.remove();
      if (!f) return;
      insertImageAt(f, w);
    };
    setTimeout(() => { if (inp.isConnected && !inp.files?.length) inp.remove(); }, 60000);
    inp.click();
  }

  /* ---------- Редактор текста/заметки ---------- */
  let editorBox = null, editorTa = null, editorBar = null;

  function startEditing(o) {
    if (editing === o) return;
    if (editing) commitEditing();
    editing = o;
    // пока идёт правка, рамка выделения и её кнопки не нужны —
    // иначе на экране две корзины и две галочки одновременно
    selectedObj = null;
    syncChrome();

    /* Мелкий масштаб делает каретку нечитаемой, но резкий зум выглядит
       рывком: подводим масштаб плавно, одним коротким твином. */
    if (view.s < 0.8) {
      const p = toScreen(o.x, o.y);
      animateView({ s: 1, x: p.x - o.x, y: p.y - o.y }, 220);
    }

    editorBox = document.createElement('div');
    editorBox.className = 'dw-editor ' + (o.kind === 'note' ? 'is-note' : 'is-text');
    editorTa = document.createElement('textarea');
    editorTa.className = 'dw-editor-ta';
    editorTa.value = o.text || '';
    editorTa.placeholder = o.kind === 'note' ? 'Заметка' : 'Текст';
    editorTa.style.color = o.color;
    editorTa.spellcheck = false;
    editorTa.rows = 1;
    editorBox.appendChild(editorTa);
    ui.appendChild(editorBox);

    editorBar = document.createElement('div');
    editorBar.className = 'dw-editor-bar';
    editorBar.innerHTML = `<button type="button" class="dw-act del" data-e="del" aria-label="Удалить">${SVG_TRASH}</button><button type="button" class="dw-act ok" data-e="ok" aria-label="Готово">${SVG_OK_SM}</button>`;
    ui.appendChild(editorBar);

    positionEditor();
    if (o.kind === 'text') { autoGrow(); positionEditor(); }
    requestAnimationFrame(() => {
      if (!editorBar) return;
      editorBar.classList.add('show');
      positionEditor();          // размеры плашки известны только после вставки
    });

    const end = editorTa.value.length;
    editorTa.focus({ preventScroll: true });
    try { editorTa.setSelectionRange(end, end); } catch (e) {}
    editorTa.addEventListener('input', () => {
      /* рост считаем уже после того, как ширина рамки подогнана под строку,
         иначе перенос считается по старой (узкой) ширине */
      if (o.kind === 'text') { positionEditor(); autoGrow(); positionEditor(); }
      else { growNote(); positionEditor(); }
      ensureEditorVisible();
    });
    editorTa.addEventListener('keydown', e => {
      if (e.key === 'Escape') { e.preventDefault(); commitEditing(); }
    });
    /* Срабатываем на pointerdown: на мобильных тап по кнопке не должен
       уводить фокус из поля и обязан срабатывать с первого касания. */
    editorBar.addEventListener('pointerdown', e => {
      e.preventDefault();
      e.stopPropagation();
      const b = e.target.closest('[data-e]');
      if (!b) return;
      vibrate(6);
      if (b.dataset.e === 'del') removeEditing();
      else commitEditing();
    });

    buildScene(); draw();
    ensureEditorVisible();
    // клавиатура появляется с задержкой: поправляемся после её анимации
    setTimeout(() => { if (editing === o) ensureEditorVisible(); }, 260);
  }

  /* Держим редактор над клавиатурой: поднимаем/опускаем вид, если он вылез. */
  function ensureEditorVisible() {
    if (!editing || !editorBox || !editorBox.isConnected) return;
    const vv = window.visualViewport;
    const visibleH = Math.min(H, (vv ? vv.offsetTop + vv.height : H));
    const topLimit = (topBar.getBoundingClientRect().bottom || 48) + 12;
    const r = editorBox.getBoundingClientRect();
    const ovr = ov.getBoundingClientRect();
    const top = r.top - ovr.top;
    const bottom = r.bottom - ovr.top;
    const left = r.left - ovr.left;
    const right = r.right - ovr.left;
    let dy = 0, dx = 0;
    if (bottom > visibleH - 14) dy = (visibleH - 14) - bottom;
    if (top + dy < topLimit) dy = topLimit - top;
    if (right > W - 12) dx = (W - 12) - right;
    if (left + dx < 12) dx = 12 - left;
    if (!dy && !dx) return;
    view.x += dx;
    view.y += dy;
    buildScene(); draw();
    positionEditor();
  }

  function autoGrow() {
    if (!editorTa) return;
    editorTa.style.height = 'auto';
    const h = Math.max(editorTa.scrollHeight, 30);
    editorTa.style.height = h + 'px';
  }

  /* Заметка растёт вместе с текстом, как бумажная карточка в системе,
     а не прячет последние строки в скролле. */
  function growNote() {
    if (!editing || editing.kind !== 'note' || !editorTa) return;
    const s = Math.max(0.2, view.s);
    const pad = notePad(editing);
    const prev = editorTa.style.height;
    editorTa.style.height = 'auto';
    const sh = editorTa.scrollHeight;
    editorTa.style.height = prev;
    const h = Math.max(140, Math.ceil(sh / s + pad * 2));
    if (Math.abs(h - editing.h) < 1) return;
    editing.h = h;
    buildScene(); draw();
  }

  /* Нижняя граница — реально видимая часть экрана: с открытой клавиатурой
     visualViewport ниже layout-viewport, и плашка не должна под неё уезжать. */
  function visibleBottom() {
    const vv = window.visualViewport;
    const ovTop = ov.getBoundingClientRect().top || 0;
    const vh = vv ? (vv.offsetTop + vv.height) : H;
    return clamp(vh - ovTop, 160, H);
  }
  function positionEditorBar(left, top, boxW, boxH) {
    if (!editorBar || !editorBar.isConnected) return;
    const bw = editorBar.offsetWidth || 92;
    const bh = editorBar.offsetHeight || 48;
    const topLimit = (topBar.getBoundingClientRect().bottom || 48) + 6;
    const bottomLim = visibleBottom();
    let bTop = top - bh - 12;
    if (bTop < topLimit) bTop = top + boxH + 14;
    bTop = clamp(bTop, 6, Math.max(6, bottomLim - bh - 8));
    const bLeft = clamp(left + (boxW - bw) / 2, 8, Math.max(8, W - bw - 8));
    editorBar.style.left = Math.round(bLeft) + 'px';
    editorBar.style.top = Math.round(bTop) + 'px';
  }

  function positionEditor() {
    if (!editing || !editorBox) return;
    const s = view.s;
    let left, top, w, h;
    if (editing.kind === 'note') {
      const pad = notePad(editing) * s;
      const p = toScreen(editing.x, editing.y);
      left = p.x + pad;
      top = p.y + pad;
      w = Math.max(20, editing.w * s - pad * 2);
      h = Math.max(20, editing.h * s - pad * 2);
      editorBox.style.width = w + 'px';
      editorBox.style.height = h + 'px';
    } else {
      const p = toScreen(editing.x, editing.y);
      left = p.x;
      top = p.y;
      /* Рамка идёт по фактической ширине строки (не шире самого блока):
         пустой текст не выглядит полем во весь экран, а длинная строка
         всё равно упирается в ширину блока и переносится как на холсте. */
      let bw = Math.max(40, editing.w * s);
      if (editorTa) {
        measure.font = `500 ${Math.max(8, editing.size * s)}px ${FONT}`;
        let mw = 0;
        for (const ln of (editorTa.value || '').split('\n')) {
          mw = Math.max(mw, measure.measureText(ln || ' ').width);
        }
        bw = clamp(mw + 10, 44, bw);
      }
      w = bw;
      editorBox.style.width = w + 'px';
      h = editorBox.offsetHeight || Math.max(28, editing.size * s * 1.35);
    }
    editorBox.style.left = left + 'px';
    editorBox.style.top = top + 'px';
    editorTa.style.fontSize = (editing.size * s) + 'px';
    editorBox.style.setProperty('--dw-ed-line', (editing.size * s * 1.35) + 'px');
    positionEditorBar(left, top, editorBox.offsetWidth || w, h);
  }

  function closeEditorDom() {
    if (editorBox) { editorBox.remove(); editorBox = null; }
    if (editorBar) { editorBar.remove(); editorBar = null; }
    editorTa = null;
  }

  function removeEditing() {
    if (!editing) return;
    const o = editing;
    const isNew = !!o.__new;
    editing = null;
    closeEditorDom();
    delete o.__new;
    if (!isNew) pushUndo();
    items = items.filter(x => x !== o);
    selectedObj = null;
    buildScene(); draw(); syncChrome();
    vibrate(8);
  }

  function commitEditing() {
    if (!editing) return;
    const o = editing;
    const text = editorTa ? editorTa.value.replace(/\s+$/, '') : (o.text || '');
    const isNew = !!o.__new;
    editing = null;
    closeEditorDom();
    delete o.__new;
    if (!text.trim()) {
      // пустую плашку не сохраняем; для нового объекта хватит отмены вставки
      if (!isNew) pushUndo();
      items = items.filter(x => x !== o);
    } else if (text !== o.text) {
      if (!isNew) pushUndo();
      o.text = text;
    }
    selectedObj = null;
    buildScene(); draw(); syncChrome();
  }

  /* ---------- Выделение (лассо) ---------- */
  function pointInPoly(x, y, poly) {
    let inside = false;
    for (let i = 0, j = poly.length - 1; i < poly.length; j = i++) {
      const xi = poly[i].x, yi = poly[i].y, xj = poly[j].x, yj = poly[j].y;
      if (((yi > y) !== (yj > y)) && (x < (xj - xi) * (y - yi) / (yj - yi) + xi)) inside = !inside;
    }
    return inside;
  }
  function calcSelBounds() {
    let minX = Infinity, minY = Infinity, maxX = -Infinity, maxY = -Infinity;
    for (const it of items) {
      if (it.kind !== 'stroke' || !selIds.has(it.id)) continue;
      const hw = (it.width || 2) / 2;
      for (const p of it.points) {
        minX = Math.min(minX, p.x - hw); minY = Math.min(minY, p.y - hw);
        maxX = Math.max(maxX, p.x + hw); maxY = Math.max(maxY, p.y + hw);
      }
    }
    if (!isFinite(minX)) { selBounds = null; selIds.clear(); }
    else selBounds = { minX, minY, maxX, maxY };
  }
  function clearSelection() {
    if (!selIds.size && !selBounds) return;
    selIds.clear(); selBounds = null;
    syncChrome();
  }
  function finishLasso() {
    if (!lasso) return;
    const pts = lasso.points.slice();
    lasso = null;
    draw();
    if (pts.length < 4) { clearSelection(); return; }
    const ids = new Set();
    for (const it of items) {
      if (it.kind !== 'stroke') continue;
      let inside = 0, total = 0;
      for (const p of it.points) { total++; if (pointInPoly(p.x, p.y, pts)) inside++; }
      if (total && inside / total >= 0.55) ids.add(it.id);
    }
    selIds = ids;
    calcSelBounds();
    if (!selIds.size) { selBounds = null; syncChrome(); return; }
    vibrate(9);
    syncChrome();
  }

  /* ---------- Chrome ---------- */
  function syncChrome() {
    if (selectedObj && items.includes(selectedObj)) {
      const o = selectedObj;
      const p = toScreen(o.x, o.y);
      const w = o.w * view.s;
      const h = (o.kind === 'text' ? textH(o) : o.h) * view.s;
      chrome.classList.remove('hidden');
      chrome.style.left = p.x + 'px';
      chrome.style.top = p.y + 'px';
      chrome.style.width = Math.max(10, w) + 'px';
      chrome.style.height = Math.max(10, h) + 'px';
    } else {
      chrome.classList.add('hidden');
      if (selectedObj) selectedObj = null;
    }

    if (selBounds && selIds.size) {
      const p = toScreen(selBounds.minX, selBounds.minY);
      lassoChrome.classList.remove('hidden');
      lassoChrome.style.left = p.x + 'px';
      lassoChrome.style.top = p.y + 'px';
      lassoChrome.style.width = Math.max(10, (selBounds.maxX - selBounds.minX) * view.s) + 'px';
      lassoChrome.style.height = Math.max(10, (selBounds.maxY - selBounds.minY) * view.s) + 'px';
    } else {
      lassoChrome.classList.add('hidden');
    }
    if (editing) positionEditor();
  }

  chrome.addEventListener('pointerdown', e => {
    if (e.target.closest('[data-o=size]')) e.stopPropagation();
  });
  chrome.querySelector('[data-o=del]').addEventListener('click', () => {
    if (!selectedObj) return;
    const o = selectedObj;
    pushUndo();
    items = items.filter(x => x !== o);
    selectedObj = null;
    buildScene(); draw(); syncChrome(); vibrate(8);
  });
  const sizeHandle = chrome.querySelector('[data-o=size]');
  sizeHandle.addEventListener('pointerdown', e => {
    if (!selectedObj || selectedObj.kind !== 'image') return;
    e.preventDefault(); e.stopPropagation();
    const o = selectedObj;
    imgResize = {
      o, start: toWorldXY(e.clientX, e.clientY),
      w0: o.w, h0: o.h, ratio: o.w / o.h, pushed: false,
      pointerId: e.pointerId, el: e.target,
    };
    try { e.target.setPointerCapture(e.pointerId); } catch (err) {}
  });
  sizeHandle.addEventListener('pointermove', e => {
    if (!imgResize) return;
    const w = toWorldXY(e.clientX, e.clientY);
    const dx = w.x - imgResize.start.x;
    const nw = Math.max(40, imgResize.w0 + dx);
    if (!imgResize.pushed) { pushUndo(); imgResize.pushed = true; }
    imgResize.o.w = nw;
    imgResize.o.h = nw / imgResize.ratio;
    requestBuild();
  });
  const endResize = () => { imgResize = null; };
  sizeHandle.addEventListener('pointerup', endResize);
  sizeHandle.addEventListener('pointercancel', endResize);

  lassoChrome.querySelector('[data-l=del]').addEventListener('click', () => {
    if (!selIds.size) return;
    pushUndo();
    items = items.filter(it => !(it.kind === 'stroke' && selIds.has(it.id)));
    clearSelection();
    buildScene(); draw(); vibrate(8);
  });

  /* ---------- События указателя ---------- */
  function startPinch() {
    const ps = [...pointers.values()];
    const mid = { x: (ps[0].x + ps[1].x) / 2, y: (ps[0].y + ps[1].y) / 2 };
    gesture = {
      d0: Math.max(1, Math.hypot(ps[0].x - ps[1].x, ps[0].y - ps[1].y)),
      anchor: toWorldXY(mid.x, mid.y),
      s0: view.s,
      mid,
    };
  }
  function updatePinch() {
    const ps = [...pointers.values()];
    if (ps.length < 2 || !gesture) return;
    const d = Math.max(1, Math.hypot(ps[0].x - ps[1].x, ps[0].y - ps[1].y));
    const mid = { x: (ps[0].x + ps[1].x) / 2, y: (ps[0].y + ps[1].y) / 2 };
    const s = clamp(gesture.s0 * d / gesture.d0, 0.25, 4);
    view.s = s;
    const r = canvas.getBoundingClientRect();
    view.x = (mid.x - r.left) - gesture.anchor.x * s;
    view.y = (mid.y - r.top) - gesture.anchor.y * s;
    if (gesture.d0 && Math.abs(d - gesture.d0) > 1) { /* noop */ }
    requestBuild();
  }

  function onDown(e) {
    if (closed) return;
    if (e.button === 2 || e.button === 1) {
      panDrag = { x: e.clientX, y: e.clientY, vx: view.x, vy: view.y };
      e.preventDefault();
      return;
    }
    if (editing) { commitEditing(); return; }
    if (paletteOpen) { closePalette(); return; }
    if (focus) { /* рисование остаётся доступным */ }

    try { canvas.setPointerCapture(e.pointerId); } catch (err) {}
    pointers.set(e.pointerId, { x: e.clientX, y: e.clientY });
    if (pointers.size === 2) {
      if (stroke) finishStroke();
      if (eraser) finishErase();
      if (lasso) { lasso = null; draw(); }
      lassoChrome.classList.add('hidden');
      startPinch();
      return;
    }
    if (pointers.size > 2) return;
    const w = toWorld(e);

    if (tool === 'text' || tool === 'note') {
      const hit = hitObject(w);
      if (hit && (hit.kind === 'text' || hit.kind === 'note')) {
        objDrag = { o: hit, start: w, x0: hit.x, y0: hit.y, moved: false, kind: hit.kind };
        if (hit !== selectedObj) selectObject(hit);
      } else {
        if (tool === 'text') insertText(w);
        else insertNote(w);
      }
      return;
    }
    if (tool === 'image') {
      const hit = hitObject(w);
      if (hit && hit.kind === 'image') {
        objDrag = { o: hit, start: w, x0: hit.x, y0: hit.y, moved: false, kind: 'image' };
        selectObject(hit);
      } else {
        if (selectedObj) deselectObj();
        pickImage(w);
      }
      return;
    }
    if (tool === 'eraser') {
      const whole = (toolSet.eraser.mode || 'whole') === 'whole';
      eraser = { kind: 'erase', id: uid(), r: eraserRadius(), x: w.x, y: w.y, whole, points: [{ x: w.x, y: w.y }] };
      if (whole) { eraser.dead = new Set(); eraseWholeAt(w, eraser); }
      requestBuild();
      return;
    }
    startStroke(w, e);
  }

  function onMove(e) {
    if (closed) return;
    if (panDrag) {
      view.x = panDrag.vx + (e.clientX - panDrag.x);
      view.y = panDrag.vy + (e.clientY - panDrag.y);
      requestBuild();
      return;
    }
    const p = pointers.get(e.pointerId);
    if (p) { p.x = e.clientX; p.y = e.clientY; }

    if (pointers.size >= 2 && gesture) { updatePinch(); return; }
    if (imgResize) return; // обрабатывается на ручке

    if (selDrag) {
      const w = toWorld(e);
      let dx = w.x - selDrag.start.x, dy = w.y - selDrag.start.y;
      if (!selDrag.moved) {
        if (Math.hypot(dx, dy) < 1.2) return;
        pushUndo();
        selDrag.moved = true;
      }
      for (const it of items) {
        if (it.kind !== 'stroke' || !selIds.has(it.id)) continue;
        const orig = selDrag.orig.get(it.id);
        if (!orig) continue;
        it.points = orig.map(pt => ({ x: pt.x + dx, y: pt.y + dy, w: pt.w }));
      }
      calcSelBounds();
      requestBuild();
      return;
    }
    if (objDrag) {
      const w = toWorld(e);
      let dx = w.x - objDrag.start.x, dy = w.y - objDrag.start.y;
      if (!objDrag.moved) {
        if (Math.hypot(dx, dy) < 1.2) return;
        pushUndo();
        objDrag.moved = true;
      }
      objDrag.o.x = objDrag.x0 + dx;
      objDrag.o.y = objDrag.y0 + dy;
      requestBuild();
      return;
    }
    if (eraser) {
      if (eraser.whole) {
        const w = toWorld(e);
        eraser.x = w.x; eraser.y = w.y;
        if (eraseWholeAt(w, eraser)) { buildScene(); syncChrome(); }
        requestDraw();
        return;
      }
      if (addErasePoint(toWorld(e))) requestBuild(); else requestDraw();
      return;
    }
    if (lasso) {
      const w = toWorld(e);
      const last = lasso.points[lasso.points.length - 1];
      if (Math.hypot(w.x - last.x, w.y - last.y) > 1.5 / view.s) {
        lasso.points.push(w);
        requestDraw();
      }
      return;
    }
    if (stroke) {
      let list = [e];
      if (e.getCoalescedEvents) {
        const ce = e.getCoalescedEvents();
        if (ce && ce.length) list = ce;
      }
      for (const ev of list) {
        const w = toWorld(ev);
        addPoint(w, pressureOf(ev));
      }
      requestDraw();
    }
  }

  function onUp(e) {
    if (panDrag) { panDrag = null; return; }
    pointers.delete(e.pointerId);
    if (pointers.size < 2 && gesture) { gesture = null; buildScene(); draw(); syncChrome(); }
    if (imgResize) { imgResize = null; }

    if (selDrag) {
      if (!selDrag.moved) { /* простой тап по выделению */ }
      selDrag = null;
      buildScene(); draw(); syncChrome();
      return;
    }
    if (objDrag) {
      const od = objDrag; objDrag = null;
      if (!od.moved) {
        if (od.kind === 'text' || od.kind === 'note') { startEditing(od.o); }
      } else {
        buildScene(); draw(); syncChrome();
      }
      return;
    }
    if (eraser) { finishErase(); return; }
    if (lasso) { finishLasso(); return; }
    if (stroke) { finishStroke(); return; }
  }

  function onWheel(e) {
    if (closed) return;
    if (paletteOpen) closePalette();
    e.preventDefault();
    const r = canvas.getBoundingClientRect();
    const mx = e.clientX - r.left, my = e.clientY - r.top;
    const world = { x: (mx - view.x) / view.s, y: (my - view.y) / view.s };
    const k = Math.exp(-e.deltaY * 0.0016);
    view.s = clamp(view.s * k, 0.25, 4);
    view.x = mx - world.x * view.s;
    view.y = my - world.y * view.s;
    requestBuild();
  }

  canvas.addEventListener('pointerdown', onDown);
  canvas.addEventListener('pointermove', onMove);
  canvas.addEventListener('pointerup', onUp);
  canvas.addEventListener('pointercancel', onUp);
  canvas.addEventListener('wheel', onWheel, { passive: false });
  canvas.addEventListener('contextmenu', e => e.preventDefault());

  /* ---------- Верхняя панель / режим ---------- */
  function toggleFocus() {
    focus = !focus;
    ov.classList.toggle('focus', focus);
    if (focus) { closePalette(); if (editing) commitEditing(); }
    syncChrome();
    vibrate(6);
  }
  topBar.querySelector('[data-a=focus]').addEventListener('click', toggleFocus);
  focusExit.addEventListener('click', toggleFocus);

  /* ---------- Сохранение ---------- */
  async function renderPreview() {
    const b = contentBounds();
    if (!b) return null;
    await preloadImages();
    const pad = 28;
    const rawW = b.w + pad * 2, rawH = b.h + pad * 2;
    const scale = Math.min(2, 1600 / Math.max(rawW, rawH));
    const cw = Math.max(1, Math.round(rawW * scale));
    const ch = Math.max(1, Math.round(rawH * scale));
    const c = document.createElement('canvas');
    c.width = cw; c.height = ch;
    const g = c.getContext('2d');
    g.fillStyle = '#fff';
    g.fillRect(0, 0, cw, ch);
    /* Штрихи и стирание собираем отдельным слоем: ластик вырезает пиксели
       чернил (destination-out), поэтому в предпросмотре он должен снимать
       часть штриха, а не пропадать совсем. */
    const layer = document.createElement('canvas');
    layer.width = cw; layer.height = ch;
    const lg = layer.getContext('2d');
    lg.setTransform(scale, 0, 0, scale, (pad - b.minX) * scale, (pad - b.minY) * scale);
    for (const it of items) {
      if (it.kind === 'stroke') drawStroke(lg, it);
      else if (it.kind === 'erase') paintErase(lg, it);
    }
    g.drawImage(layer, 0, 0);
    g.setTransform(scale, 0, 0, scale, (pad - b.minX) * scale, (pad - b.minY) * scale);
    for (const it of items) {
      if (it.kind === 'text') drawTextObj(g, it);
      else if (it.kind === 'note') drawNote(g, it, false);
      else if (it.kind === 'image') drawImageObj(g, it);
    }
    return c.toDataURL('image/png');
  }

  let saving = false;
  async function saveAndClose() {
    if (saving || closed) return;
    if (editing) commitEditing();
    if (selIds.size) clearSelection();
    deselectObj();
    saving = true;
    btnDone.classList.add('busy');
    try {
      let dataUrl = null;
      if (items.length) dataUrl = await renderPreview();
      if (items.length) {
        const payload = { v: 1, items, view: { ...view }, tool, ink: { ...toolInk }, set: toolSet };
        await Store.putBlob(drawId, new Blob([JSON.stringify(payload)], { type: 'application/json' }));
      }
      const result = { dataUrl, id: drawId };
      closeOverlay(() => onDone && onDone(result));
    } catch (err) {
      console.error('drawing save failed', err);
      saving = false;
      btnDone.classList.remove('busy');
      toast('Не удалось сохранить рисунок');
    }
  }

  function closeOverlay(after) {
    if (closed) return;
    closed = true;
    if (editing) commitEditing();
    document.removeEventListener('keydown', onKey);
    window.removeEventListener('resize', resize);
    try {
      window.visualViewport?.removeEventListener('resize', onViewport);
      window.visualViewport?.removeEventListener('scroll', onViewport);
    } catch (e) {}
    document.body.classList.remove('dw-open');
    try { refreshEdgeColor(); } catch (e) {}
    ov.classList.remove('open');
    ov.classList.add('closing');
    ov.style.transition = 'opacity .18s ease';
    ov.style.opacity = '0';
    setTimeout(() => {
      try { canvas.width = 0; canvas.height = 0; scene.width = 0; scene.height = 0; } catch (e) {}
      try { ro.disconnect(); } catch (e) {}
      try {
        for (const rec of images.values()) {
          if (rec && rec.url && rec.url.startsWith('blob:')) URL.revokeObjectURL(rec.url);
        }
      } catch (e) {}
      try { document.querySelectorAll('.dw-file').forEach(el => el.remove()); } catch (e) {}
      try { ov.remove(); } catch (e) {}
      after && after();
    }, 240);
  }

  function cancel() {
    // отмена: ничего не сохраняем
    closeOverlay(() => onDone && onDone(null));
  }

  btnDone.addEventListener('click', saveAndClose);
  ov.querySelector('[data-a=close]').addEventListener('click', cancel);

  function onKey(e) {
    const tgt = e.target;
    const typing = !!(editing && (tgt === editorTa || tgt?.closest?.('.dw-editor')));
    if (typing) {
      if (e.key === 'Escape') { e.preventDefault(); commitEditing(); }
      return;
    }
    if (e.key === 'Escape') {
      if (editing) { e.preventDefault(); commitEditing(); return; }
      if (paletteOpen) { e.preventDefault(); closePalette(); return; }
      if (focus) { e.preventDefault(); toggleFocus(); return; }
      if (selIds.size) { e.preventDefault(); clearSelection(); return; }
      return;
    }
    const meta = e.ctrlKey || e.metaKey;
    if (meta && e.key && e.key.toLowerCase() === 'z') {
      e.preventDefault();
      if (e.shiftKey) doRedo(); else doUndo();
    } else if (meta && e.key && e.key.toLowerCase() === 'y') {
      e.preventDefault(); doRedo();
    } else if (meta && e.key === 'Enter' && editing) {
      e.preventDefault(); commitEditing();
    } else if (e.key === '1' && !meta) setTool('pen');
    else if (e.key === '2' && !meta) setTool('markerBlue');
    else if (e.key === '3' && !meta) setTool('markerYellow');
    else if (e.key === '4' && !meta) setTool('fountain');
    else if (e.key === '5' && !meta) setTool('eraser');
    else if (e.key === '6' && !meta) setTool('image');
    else if (e.key === '7' && !meta) setTool('text');
    else if (e.key === '8' && !meta) setTool('note');
  }
  document.addEventListener('keydown', onKey);

  /* ---------- Загрузка документа ---------- */
  (async () => {
    if (opts.drawId) {
      try {
        const blob = await Store.blob(opts.drawId);
        if (blob) {
          doc = JSON.parse(await blob.text());
          if (Array.isArray(doc.items)) items = doc.items;
        }
      } catch (e) { /* пустой документ */ }
    }
    // Старый рисунок из прежнего редактора (без документа) — открываем как фон,
    // чтобы его можно было продолжить, а не потерять.
    if (!doc && opts.imageSrc) {
      const im = new Image();
      im.onload = () => {
        const k = Math.min(1, 420 / Math.max(im.naturalWidth || 1, im.naturalHeight || 1));
        const iw = Math.max(40, (im.naturalWidth || 300) * k);
        const ih = Math.max(40, (im.naturalHeight || 200) * k);
        images.set(opts.imageSrc, { img: im, url: opts.imageSrc });
        items = [{ kind: 'image', id: uid(), x: -iw / 2, y: -ih / 2, w: iw, h: ih, src: opts.imageSrc }];
        if (viewReady) { fitView(); buildScene(); draw(); syncChrome(); updateUndoBtns(); }
      };
      im.src = opts.imageSrc;
    }
    loaded = true;
    viewReady = false;
    toolInk = { ...DEFAULT_INK };
    if (doc) {
      let t = doc.tool;
      if (t === 'marker') t = 'markerBlue';
      if (t === 'select') t = 'eraser';
      if (t && TOOL_META[t]) tool = t;
      if (doc.ink && typeof doc.ink === 'object') {
        for (const id of Object.keys(DEFAULT_INK)) {
          const c = doc.ink[id];
          if (typeof c === 'string' && /^#[0-9a-f]{6}$/i.test(c)) toolInk[id] = c;
        }
      }
      if (doc.set && typeof doc.set === 'object') {
        for (const id of BRUSH_IDS) {
          const s = doc.set[id];
          if (!s || typeof s !== 'object') continue;
          const sz = Number(s.size);
          if (Number.isFinite(sz)) toolSet[id].size = clamp(Math.round(sz), 0, SIZE_STEPS.length - 1);
          if (typeof s.opacity === 'number') toolSet[id].opacity = clamp(s.opacity, MIN_OPACITY, 1);
          if (id === 'eraser' && ERASER_MODES.includes(s.mode)) toolSet[id].mode = s.mode;
        }
      }
    }
    toolEls.forEach(el => {
      const on = el.dataset.tool === tool;
      el.classList.toggle('is-active', on);
      setToolSkinActive(el.dataset.tool, on);
    });
    ov.dataset.tool = tool;
    applyAllToolAccents();
    updateColorUI();
    paintPalette();
    syncOpts();
    resize();
    scrollToolIntoView(tool);
    if (ov.isConnected) {
      requestAnimationFrame(() => ov.classList.add('open'));
      setTimeout(() => { if (!closed) { ov.style.transition = 'none'; ov.style.opacity = '1'; } }, 320);
    }
    await preloadImages();
    buildScene(); draw(); updateUndoBtns();
  })();

  const ro = new ResizeObserver(() => { if (!closed) resize(); });
  ro.observe(ov);
  window.addEventListener('resize', resize);
  const onViewport = () => {
    if (closed || !editing) return;
    positionEditor();          // плашка действий держится у объекта
    ensureEditorVisible();
  };
  try {
    window.visualViewport?.addEventListener('resize', onViewport);
    window.visualViewport?.addEventListener('scroll', onViewport);
  } catch (e) {}

  const api = {
    close: cancel,
    element: ov,
    getState: () => ({ items, view: { ...view }, tool, ink: activeInk(), toolInk: { ...toolInk }, toolSet: JSON.parse(JSON.stringify(toolSet)), hueIndex, shadeIndex, paletteOpen, focus, undo: undoStack.length, redo: redoStack.length }),
  };
  try { window.__dwLast = api; } catch (e) {}
  return api;
}
