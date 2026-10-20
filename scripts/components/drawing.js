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

const SVG_UNDO = `<svg viewBox="0 0 28 28" aria-hidden="true"><path d="M4.6 8.4h11.2a6.7 6.7 0 0 1 6.7 6.7v7.4" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"/><path d="M9.7 3.5 4.5 8.4l5.2 4.9" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/></svg>`;
const SVG_REDO = `<svg viewBox="0 0 28 28" aria-hidden="true"><g transform="matrix(-1 0 0 1 28 0)"><path d="M4.6 8.4h11.2a6.7 6.7 0 0 1 6.7 6.7v7.4" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"/><path d="M9.7 3.5 4.5 8.4l5.2 4.9" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/></g></svg>`;
const SVG_EXPAND = `<svg viewBox="0 0 28 28" aria-hidden="true"><path d="M3.6 10.6V3.6h7" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/><path d="M17.4 24.4h7v-7" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/><circle cx="17.2" cy="10.7" r="1.75" fill="currentColor"/></svg>`;
const SVG_CHECK = `<svg viewBox="0 0 28 28" aria-hidden="true"><path d="M3.6 15.4l6.9 6.6L24.6 5.6" fill="none" stroke="currentColor" stroke-width="2.15" stroke-linecap="round" stroke-linejoin="round"/></svg>`;
const SVG_TRASH = `<svg viewBox="0 0 24 24" aria-hidden="true"><path d="M4 7h16M9.5 7V4.8h5V7M6.7 7l.8 12.2h9l.8-12.2M10.2 10.2v5.6M13.8 10.2v5.6" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"/></svg>`;
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
        ${TOOLS.map((t, i) => `<button type="button" class="dw-tool" data-tool="${t.id}" style="width:${t.w}px;margin-left:${t.ml || 0}px;--i:${i}" aria-label="${t.label}">${toolSkin(t.id)}</button>`).join('')}
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

  document.body.appendChild(ov);
  document.body.classList.add('dw-open');
  try { setEdgeColor('#ffffff'); } catch (e) {}
  ov.addEventListener('touchmove', e => { if (e.touches.length > 1) e.preventDefault(); }, { passive: false });

  const canvas = ov.querySelector('.dw-canvas');
  const ctx = canvas.getContext('2d');
  const scene = document.createElement('canvas');
  const sctx = scene.getContext('2d');
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
  const measure = document.createElement('canvas').getContext('2d');

  /* ---------- Состояние ---------- */
  const drawId = opts.drawId || ('draw_' + uid());
  let items = [];
  let view = { x: 0, y: 0, s: 1 };
  let viewReady = false;
  let doc = null;
  let tool = 'pen';
  let toolInk = { ...DEFAULT_INK };
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
  }
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
    ctx.setTransform(DPR, 0, 0, DPR, 0, 0);
    sctx.setTransform(DPR, 0, 0, DPR, 0, 0);
    if (!viewReady && loaded) {
      view = (doc && doc.view) ? { ...doc.view } : { x: W / 2, y: H * 0.46, s: 1 };
      viewReady = true;
      if (!doc?.view && items.length) fitView();
    }
    if (!viewReady) { view = { x: W / 2, y: H * 0.46, s: 1 }; }
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

  function buildScene() {
    sctx.setTransform(DPR, 0, 0, DPR, 0, 0);
    sctx.clearRect(0, 0, W, H);
    sctx.fillStyle = '#fff';
    sctx.fillRect(0, 0, W, H);
    drawDots(sctx);
    sctx.save();
    sctx.translate(view.x, view.y);
    sctx.scale(view.s, view.s);
    for (const it of items) {
      if (it === editing) {
        if (it.kind === 'note') drawNote(sctx, it, true);
        continue;
      }
      if (it.kind === 'stroke') drawStroke(sctx, it);
      else if (it.kind === 'text') drawTextObj(sctx, it);
      else if (it.kind === 'note') drawNote(sctx, it, false);
      else if (it.kind === 'image') drawImageObj(sctx, it);
    }
    sctx.restore();
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
      ctx.arc(p.x, p.y, ERASER_R * view.s, 0, TAU);
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

  /* Графит: несколько тонких «волокон» с рваным краем и крупинками,
     а не сплошная линия — как настоящий карандаш по бумаге. */
  function drawPencil(c, s) {
    const pts = s.points;
    const rnd = mulberry32(hashStr(s.id || 'pencil'));
    const base = Math.max(1.2, s.width || 2.4);
    c.save();
    c.globalCompositeOperation = 'multiply';
    c.lineCap = 'round';
    c.lineJoin = 'round';
    c.strokeStyle = s.color;
    c.fillStyle = s.color;

    if (pts.length === 1) {
      c.globalAlpha = .5;
      for (let i = 0; i < 5; i++) {
        c.beginPath();
        c.arc(pts[0].x + (rnd() - .5) * base * 1.4, pts[0].y + (rnd() - .5) * base * 1.4, .25 + rnd() * .55, 0, TAU);
        c.fill();
      }
      c.restore();
      return;
    }

    /* мягкая подложка */
    c.globalAlpha = .22;
    c.lineWidth = base * 1.6;
    traceSmooth(c, pts);
    c.stroke();

    /* плотное графитовое ядро */
    c.globalAlpha = .58;
    c.lineWidth = Math.max(.55, base * .52);
    c.setLineDash([3.2, 1.1]);
    c.lineDashOffset = rnd() * 4;
    traceSmooth(c, pts);
    c.stroke();
    c.setLineDash([]);

    /* волокна графита */
    for (let k = 0; k < 5; k++) {
      c.globalAlpha = .24 + rnd() * .16;
      c.lineWidth = Math.max(.3, base * (.2 + rnd() * .22));
      c.setLineDash([.8 + rnd() * 1.8, .25 + rnd() * .8]);
      c.lineDashOffset = rnd() * 9;
      const ox = (rnd() - .5) * base * .5;
      const oy = (rnd() - .5) * base * .5;
      c.save();
      c.translate(ox, oy);
      traceSmooth(c, pts);
      c.stroke();
      c.restore();
    }
    c.setLineDash([]);

    /* крупинки */
    for (let i = 1; i < pts.length; i++) {
      const a = pts[i - 1], b = pts[i];
      const dx = b.x - a.x, dy = b.y - a.y;
      const len = Math.hypot(dx, dy);
      if (!len) continue;
      const n = Math.min(6, Math.max(1, Math.round(len / 2.0)));
      for (let j = 0; j < n; j++) {
        const t = rnd();
        const x = a.x + dx * t + (rnd() - .5) * base * 1.05;
        const y = a.y + dy * t + (rnd() - .5) * base * 1.05;
        c.globalAlpha = .18 + rnd() * .24;
        c.beginPath();
        c.arc(x, y, .2 + rnd() * .45, 0, TAU);
        c.fill();
      }
    }
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
    if (s.tool === 'pen') { drawPencil(c, s); return; }

    c.save();
    c.lineCap = 'round';
    c.lineJoin = 'round';
    c.strokeStyle = s.color;
    c.fillStyle = s.color;
    c.globalAlpha = s.alpha == null ? 1 : s.alpha;
    if (s.tool === 'marker') c.globalCompositeOperation = 'multiply';

    if (pts.length === 1) {
      c.beginPath();
      c.arc(pts[0].x, pts[0].y, Math.max(1, (s.width || 2) / 2), 0, TAU);
      c.fill();
      c.restore();
      return;
    }
    if (s.pressured) {
      const line = s.tool === 'fountain' ? smoothDense(pts, 5) : pts;
      for (let i = 1; i < line.length; i++) {
        const a = line[i - 1], b = line[i];
        c.lineWidth = Math.max(0.5, ((a.w || s.width) + (b.w || s.width)) / 2);
        c.beginPath();
        c.moveTo(a.x, a.y);
        c.lineTo(b.x, b.y);
        c.stroke();
      }
      c.restore();
      return;
    }
    c.lineWidth = s.width || 2;
    traceSmooth(c, pts);
    c.stroke();
    c.restore();
  }

  /* ---------- Текст ---------- */
  function wrapLines(text, maxW, size) {
    measure.font = `500 ${size}px ${FONT}`;
    const out = [];
    const paras = String(text == null ? '' : text).split('\n');
    for (const para of paras) {
      if (!para) { out.push(''); continue; }
      let line = '';
      for (const ch of para) {
        const test = line + ch;
        if (line && measure.measureText(test).width > maxW) { out.push(line); line = ch; }
        else line = test;
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
    if (!silent) vibrate(4);
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
  function pickShade(i) {
    if (!activeInk()) return;
    shadeIndex = i;
    const col = shades()[i];
    toolInk[tool] = col;
    const el = toolEls.find(x => x.dataset.tool === tool);
    if (el) applyToolAccent(el, tool);
    updateColorUI();
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
  const ERASER_R = 14;
  function scrollToolIntoView(id, smooth = true) {
    const el = toolEls.find(x => x.dataset.tool === id);
    if (!el) return;
    const l = el.offsetLeft, r = l + el.offsetWidth;
    if (l < strip.scrollLeft + 4) strip.scrollTo({ left: Math.max(0, l - 10), behavior: smooth ? 'smooth' : 'auto' });
    else if (r > strip.scrollLeft + strip.clientWidth - 4) strip.scrollTo({ left: r - strip.clientWidth + 10, behavior: smooth ? 'smooth' : 'auto' });
  }
  function setTool(id) {
    if (!TOOLS.some(t => t.id === id)) return;
    if (id === tool) return;
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
    scrollToolIntoView(id, true);
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
    const base = isMarker
      ? { width: tool === 'markerYellow' ? 22 : 20, alpha: .30 }
      : tool === 'fountain'
        ? { width: 3.1, alpha: 1 }
        : { width: (e.pointerType === 'pen' && e.pressure > 0) ? 1.9 + e.pressure * 1.6 : 2.6, alpha: 1 };
    stroke = {
      kind: 'stroke', id: uid(),
      tool: isMarker ? 'marker' : tool === 'fountain' ? 'fountain' : 'pen',
      color: activeInk() || '#1c1c1c', width: base.width, alpha: base.alpha,
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
        width = stroke.stylus
          ? 1.6 + press * 3.6
          : 3.2 - clamp(dist / 40, 0, 1) * 0.8;
      } else {
        width = 1.6 + press * 3.2;
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

  /* ---------- Ластик ---------- */
  function strokeHit(s, w, r) {
    const rr = r + (s.width || 2) / 2;
    for (const p of s.points) {
      if (Math.hypot(p.x - w.x, p.y - w.y) < rr) return true;
    }
    return false;
  }
  function eraseAt(w) {
    const before = items;
    const kept = before.filter(it => !(it.kind === 'stroke' && strokeHit(it, w, ERASER_R)));
    if (kept.length === before.length) return false;
    if (!eraseUndo) { pushUndo(); eraseUndo = true; }
    items = kept;
    return true;
  }
  let eraseUndo = false;

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

  function insertText(w) {
    const o = { kind: 'text', id: uid(), x: w.x, y: w.y, w: 230, text: '', size: 19, color: activeInk() || '#1c1c1c' };
    pushUndo();
    items.push(o);
    startEditing(o);
    selectObject(o);
  }
  function insertNote(w) {
    const o = { kind: 'note', id: uid(), x: w.x - 95, y: w.y - 60, w: 190, h: 140, text: '', size: 15, color: activeInk() || '#1c1c1c' };
    pushUndo();
    items.push(o);
    startEditing(o);
    selectObject(o);
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
  let editorBox = null, editorTa = null, editorActs = null;

  function startEditing(o) {
    if (editing === o) return;
    if (editing) commitEditing();
    editing = o;
    editorBox = document.createElement('div');
    editorBox.className = 'dw-editor ' + (o.kind === 'note' ? 'is-note' : 'is-text');
    editorTa = document.createElement('textarea');
    editorTa.className = 'dw-editor-ta';
    editorTa.value = o.text || '';
    editorTa.placeholder = o.kind === 'note' ? 'Заметка' : 'Текст';
    editorTa.style.color = o.color;
    editorTa.spellcheck = false;
    editorBox.appendChild(editorTa);
    editorActs = document.createElement('div');
    editorActs.className = 'dw-editor-acts';
    editorActs.innerHTML = `<button type="button" class="dw-act del" data-e="del" aria-label="Удалить">${SVG_TRASH}</button><button type="button" class="dw-act ok" data-e="ok" aria-label="Готово">${SVG_OK_SM}</button>`;
    editorBox.appendChild(editorActs);
    ui.appendChild(editorBox);
    positionEditor();
    if (o.kind === 'text') autoGrow();
    const end = editorTa.value.length;
    editorTa.focus({ preventScroll: true });
    try { editorTa.setSelectionRange(end, end); } catch (e) {}
    editorTa.addEventListener('input', () => { if (o.kind === 'text') { autoGrow(); positionEditor(); } });
    editorTa.addEventListener('keydown', e => {
      if (e.key === 'Escape') { e.preventDefault(); commitEditing(); }
    });
    editorActs.addEventListener('pointerdown', e => e.preventDefault());
    editorActs.addEventListener('click', e => {
      const b = e.target.closest('[data-e]');
      if (!b) return;
      if (b.dataset.e === 'del') { removeEditing(true); }
      else commitEditing();
    });
    buildScene(); draw();
  }

  function autoGrow() {
    if (!editorTa) return;
    editorTa.style.height = 'auto';
    const h = Math.max(editorTa.scrollHeight, 30);
    editorTa.style.height = h + 'px';
  }

  function positionEditor() {
    if (!editing || !editorBox) return;
    const s = view.s;
    if (editing.kind === 'note') {
      const pad = notePad(editing) * s;
      const p = toScreen(editing.x, editing.y);
      editorBox.style.left = (p.x + pad) + 'px';
      editorBox.style.top = (p.y + pad) + 'px';
      editorBox.style.width = Math.max(20, editing.w * s - pad * 2) + 'px';
      editorBox.style.height = Math.max(20, editing.h * s - pad * 2) + 'px';
      editorTa.style.fontSize = (editing.size * s) + 'px';
      editorBox.style.setProperty('--dw-ed-line', (editing.size * s * 1.35) + 'px');
    } else {
      const p = toScreen(editing.x, editing.y);
      editorBox.style.left = p.x + 'px';
      editorBox.style.top = p.y + 'px';
      editorBox.style.width = Math.max(40, editing.w * s) + 'px';
      editorTa.style.fontSize = (editing.size * s) + 'px';
      editorBox.style.setProperty('--dw-ed-line', (editing.size * s * 1.35) + 'px');
    }
    editorBox.style.setProperty('--dw-ed-scale', s);
  }

  function removeEditing(silent) {
    if (!editing) return;
    const o = editing;
    pushUndo();
    items = items.filter(x => x !== o);
    editing = null;
    if (editorBox) { editorBox.remove(); editorBox = null; editorTa = null; editorActs = null; }
    selectedObj = null;
    buildScene(); draw(); syncChrome();
    if (!silent) vibrate(6);
  }

  function commitEditing(save = true) {
    if (!editing) return;
    const o = editing;
    const text = editorTa ? editorTa.value.replace(/\s+$/, '') : (o.text || '');
    const box = editorBox;
    editing = null;
    editorBox = null; editorTa = null; editorActs = null;
    if (box) box.remove();
    if (!text.trim()) {
      pushUndo();
      items = items.filter(x => x !== o);
      selectedObj = null;
    } else if (text !== o.text) {
      pushUndo();
      o.text = text;
    }
    if (selectedObj === o) selectedObj = null;
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
      eraseUndo = false;
      eraser = { x: w.x, y: w.y };
      if (eraseAt(w)) requestBuild(); else draw();
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
      const w = toWorld(e);
      eraser.x = w.x; eraser.y = w.y;
      if (eraseAt(w)) requestBuild(); else requestDraw();
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
    if (eraser) { eraser = null; draw(); return; }
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
    g.setTransform(scale, 0, 0, scale, (pad - b.minX) * scale, (pad - b.minY) * scale);
    for (const it of items) {
      if (it.kind === 'stroke') drawStroke(g, it);
      else if (it.kind === 'text') drawTextObj(g, it);
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
        const payload = { v: 1, items, view: { ...view }, tool };
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
    resize();
    scrollToolIntoView(tool, false);
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

  const api = {
    close: cancel,
    element: ov,
    getState: () => ({ items, view: { ...view }, tool, ink: activeInk(), toolInk: { ...toolInk }, hueIndex, shadeIndex, paletteOpen, focus, undo: undoStack.length, redo: redoStack.length }),
  };
  try { window.__dwLast = api; } catch (e) {}
  return api;
}
