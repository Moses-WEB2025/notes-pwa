/* ============ Рисование ============
   Полноценный редактор рисунка в духе ibis Paint, но целиком в стиле
   приложения: та же шапка, те же Lucide-иконки, что и в тулбаре редактора,
   те же радиусы, тени и тайминги анимаций.

   Возможности:
   · рисование четырьмя инструментами — ручка, карандаш, кисть и маркер
     (у каждого своя форма кончика, толщина, прозрачность, мягкость края
     и зернистость), а также ластик; гладкие линии (квадратичное сглаживание);
   · заливка (ведро) и пипетка;
   · фигуры: линия, прямоугольник, круг (контур/заливка);
   · палитра HSV (поле «насыщенность/яркость» + ползунок тона) и пресеты;
   · история команд (отмена/возврат) — по стеку операций, без снимков,
     поэтому память тратится мало даже при долгом рисовании;
   · жесты: один палец — рисовать; два — масштаб, поворот и сдвиг холста
     (поворот свободный, у сетки 0/90/180/270 — лёгкая микрофиксация);
     двойной тап двумя пальцами — отмена.

   Производительность: во время рисования пиксели трогает только верхний
   слой (canvas) и маленькая «прямоугольная» область вокруг штриха;
   готовые штрихи один раз накладываются на базовый слой; масштаб и
   сдвиг — только transform (композитор, 60–120 fps). */
import { toast } from './ui.js';
import { vibrate } from '../core.js';
import { icon } from '../icons.js';

const PAPER = '#ffffff';          /* цвет «бумаги» и ластика */
const MAXDIM = 2560;              /* макс. сторона холста (логические px) */
const MIN_K = 0.2, MAX_K = 10;    /* пределы масштаба: сильное отдаление и приближение */
const SIZE_MIN = 1, SIZE_MAX = 160;
const TOLERANCE = 32;             /* допуск заливки (строгое совпадение) */
const TOLERANCE_SOFT = 110;       /* мягкий допуск: дотягивает заливку до края линии */
const SNAP_STEP = Math.PI / 2;    /* «сетка» поворота: 0 / 90 / 180 / 270 */
const SNAP_TOL = 4 * Math.PI / 180; /* магнитная микрофиксация у сетки (градусы) */
const HIST_MAX = 40;              /* предел числа операций в истории */
const HIST_BYTES = 48 * 1024 * 1024; /* и суммарной памяти под снимки */

/* Пресеты под палитру приложения (тёплые/холодные, нейтральные). */
const PRESETS = [
  '#1c1d21', '#4b4f57', '#8a8f98', '#c9ccd2', '#ffffff', '#3e2723', '#8d6e63', '#d7ccc8',
  '#d93025', '#e05555', '#f0616f', '#e05a9b', '#c2418f', '#9b51e0', '#6b4df5', '#4d6ef5',
  '#1a4a76', '#2f9cd6', '#31c9c0', '#2bb673', '#1d9152', '#7cb342', '#f7e04b', '#f2a824',
];

/* Инструменты пера. Отличаются формой кончика, толщиной, прозрачностью,
   «мягкостью» края (размытие через тень) и зернистостью (графит).
     width — множитель толщины относительно ползунка размера;
     alpha — множитель прозрачности (маркер полупрозрачный, как текстовыделитель);
     soft  — доля размера, уходящая в размытый край (0 — резкий край);
     grain — сила «прогрызания» штриха для эффекта карандаша. */
const INKS = {
  pen:    { cap: 'round',  join: 'round', width: 1,    alpha: 1,    soft: 0,   grain: 0 },
  pencil: { cap: 'round',  join: 'round', width: 0.75, alpha: 0.85, soft: 0,   grain: 0.55 },
  brush:  { cap: 'round',  join: 'round', width: 1,    alpha: 1,    soft: 0.9, grain: 0 },
  marker: { cap: 'square', join: 'round', width: 1.35, alpha: 0.6,  soft: 0,   grain: 0 },
};

/* ---- Цветовые утилиты ---- */
const clamp = (v, a, b) => Math.max(a, Math.min(b, v));
const hexToRgb = (h) => {
  h = (h || '#000').replace('#', '');
  if (h.length === 3) h = h.split('').map((c) => c + c).join('');
  const n = parseInt(h, 16);
  return [(n >> 16) & 255, (n >> 8) & 255, n & 255];
};
const rgbToHex = (r, g, b) => '#' + [r, g, b].map((v) => clamp(v | 0, 0, 255).toString(16).padStart(2, '0')).join('');
function hsvToRgb(h, s, v) {
  h = ((h % 360) + 360) % 360;
  const c = v * s, x = c * (1 - Math.abs(((h / 60) % 2) - 1)), m = v - c;
  let r, g, b;
  if (h < 60) { r = c; g = x; b = 0; }
  else if (h < 120) { r = x; g = c; b = 0; }
  else if (h < 180) { r = 0; g = c; b = x; }
  else if (h < 240) { r = 0; g = x; b = c; }
  else if (h < 300) { r = x; g = 0; b = c; }
  else { r = c; g = 0; b = x; }
  return [Math.round((r + m) * 255), Math.round((g + m) * 255), Math.round((b + m) * 255)];
}
function rgbToHsv(r, g, b) {
  r /= 255; g /= 255; b /= 255;
  const max = Math.max(r, g, b), min = Math.min(r, g, b), d = max - min;
  let h = 0;
  if (d) {
    if (max === r) h = ((g - b) / d) % 6;
    else if (max === g) h = (b - r) / d + 2;
    else h = (r - g) / d + 4;
    h *= 60; if (h < 0) h += 360;
  }
  return [h, max ? d / max : 0, max];
}

/* Общая кривая размера: ползунок 0..1 → размер 1..160 (логарифмически,
   чтобы мелкие размеры выбирались точнее). */
const sizeFromT = (t) => Math.round(SIZE_MIN * Math.pow(SIZE_MAX / SIZE_MIN, t));
const tFromSize = (s) => Math.log(s / SIZE_MIN) / Math.log(SIZE_MAX / SIZE_MIN);

export function openDrawing(onDone) {
  const ov = document.createElement('div');
  ov.className = 'draw-overlay';
  ov.innerHTML = `
    <div class="appbar back-header dr-bar">
      <button class="icon-btn" data-a="close" aria-label="Закрыть">${icon('arrow-left')}</button>
      <div class="appbar-title left-pos">Рисунок</div>
      <span class="spacer"></span>
      <button class="icon-btn" data-a="undo" aria-label="Отменить" disabled>${icon('undo-2')}</button>
      <button class="icon-btn" data-a="redo" aria-label="Вернуть" disabled>${icon('redo-2')}</button>
      <button class="dr-done" data-a="done" aria-label="Готово">${icon('check')}</button>
    </div>

    <div class="dr-stage">
      <div class="dr-board">
        <canvas class="dr-base"></canvas>
        <canvas class="dr-live"></canvas>
      </div>
      <div class="dr-hint">Один палец — рисовать · Два — масштаб, поворот и сдвиг</div>
    </div>

    <div class="dr-options">
      <div class="dr-opt" data-row="size">
        <span class="dr-opt-ic"><i class="dr-size-dot"></i></span>
        <div class="dr-slider" data-slider="size">
          <div class="dr-track"><div class="dr-fill"></div><div class="dr-knob"></div></div>
        </div>
        <span class="dr-val" data-val="size">14</span>
      </div>
      <div class="dr-opt" data-row="opacity">
        <span class="dr-opt-ic">${icon('droplet')}</span>
        <div class="dr-slider" data-slider="opacity">
          <div class="dr-track"><div class="dr-fill"></div><div class="dr-knob"></div></div>
        </div>
        <span class="dr-val" data-val="opacity">100%</span>
      </div>
      <div class="dr-opt dr-chips" data-row="ink">
        <button class="dr-chip active" data-ink="pen">${icon('pen-tool')}<span>Ручка</span></button>
        <button class="dr-chip" data-ink="pencil">${icon('pencil')}<span>Карандаш</span></button>
        <button class="dr-chip" data-ink="brush">${icon('paintbrush')}<span>Кисть</span></button>
        <button class="dr-chip" data-ink="marker">${icon('highlighter')}<span>Маркер</span></button>
      </div>
      <div class="dr-opt dr-chips" data-row="shape">
        <button class="dr-chip" data-shape="line">${icon('minus')}<span>Линия</span></button>
        <button class="dr-chip" data-shape="rect">${icon('square')}<span>Прямоугольник</span></button>
        <button class="dr-chip" data-shape="ellipse">${icon('circle')}<span>Круг</span></button>
        <button class="dr-chip" data-shape="fillmode">${icon('paint-bucket')}<span>Заливка</span></button>
      </div>
    </div>

    <div class="dr-dock">
      <button class="dr-tool active" data-tool="brush" aria-label="Рисование">${icon('paintbrush')}</button>
      <button class="dr-tool" data-tool="eraser" aria-label="Ластик">${icon('eraser')}</button>
      <button class="dr-tool" data-tool="fill" aria-label="Заливка">${icon('paint-bucket')}</button>
      <button class="dr-tool" data-tool="shape" aria-label="Фигуры">${icon('shapes')}</button>
      <button class="dr-tool" data-tool="picker" aria-label="Пипетка">${icon('pipette')}</button>
      <span class="spacer"></span>
      <button class="dr-color" data-a="color" aria-label="Цвет">${icon('palette')}<i class="dr-dot"></i></button>
      <button class="dr-tool" data-a="clear" aria-label="Очистить">${icon('trash-2')}</button>
      <button class="dr-tool" data-a="fit" aria-label="Вписать">${icon('maximize-2')}</button>
    </div>

    <div class="dr-sheet-backdrop" data-a="sheet-bg"></div>
    <div class="dr-sheet" data-sheet="color">
      <div class="dr-sheet-head">
        <span class="dr-sheet-title">Цвет</span>
        <button class="icon-btn dr-sheet-x" data-a="sheet-close" aria-label="Скрыть">${icon('x')}</button>
      </div>
      <canvas class="dr-sv"></canvas>
      <div class="dr-hue"><i class="dr-hue-knob"></i></div>
      <div class="dr-color-meta">
        <i class="dr-color-prev"></i><span class="dr-color-hex">#1C1D21</span>
      </div>
      <div class="dr-presets"></div>
    </div>

    <div class="dr-confirm" data-confirm>
      <div class="dr-confirm-card">
        <div class="dr-confirm-title">Выйти без сохранения?</div>
        <div class="dr-confirm-text">Рисунок не будет добавлен в заметку.</div>
        <div class="confirm-btns">
          <button class="confirm-btn" data-c="no">Отмена</button>
          <button class="confirm-btn danger" data-c="yes">Выйти</button>
        </div>
      </div>
    </div>`;
  document.body.appendChild(ov);
  requestAnimationFrame(() => ov.classList.add('open'));

  /* ---------- Ссылки на узлы ---------- */
  const $ = (s, r = ov) => r.querySelector(s);
  const $$ = (s, r = ov) => Array.prototype.slice.call(r.querySelectorAll(s));
  const stage = $('.dr-stage');
  const board = $('.dr-board');
  const base = $('.dr-base');
  const live = $('.dr-live');
  const bctx = base.getContext('2d');
  const lctx = live.getContext('2d');
  const buf = document.createElement('canvas');   /* буфер текущего штриха (непрозрачный) */
  const fctx = buf.getContext('2d');
  const hint = $('.dr-hint');

  /* ---------- Размер документа ---------- */
  const availW = Math.max(140, stage.clientWidth - 24);
  const availH = Math.max(140, stage.clientHeight - 24);
  /* Супердискретизация ×2: у холста вчетверо больше пикселей, чем на
     экране в масштабе 1:1, поэтому рисунок остаётся резким и при
     приближении, а не расплывается в «квадратики». */
  const dpr = Math.min(window.devicePixelRatio || 1, 2) * 2;
  let docW = Math.round(availW * dpr);
  let docH = Math.round(availH * dpr);
  const longest = Math.max(docW, docH);
  if (longest > MAXDIM) {
    const s = MAXDIM / longest;
    docW = Math.max(1, Math.round(docW * s));
    docH = Math.max(1, Math.round(docH * s));
  }
  board.style.width = availW + 'px';
  board.style.height = availH + 'px';
  base.width = live.width = buf.width = docW;
  base.height = live.height = buf.height = docH;
  bctx.fillStyle = PAPER;
  bctx.fillRect(0, 0, docW, docH);

  /* ---------- Состояние ---------- */
  let color = '#1c1d21';
  let size = 14;
  let alpha = 1;
  let tool = 'brush';
  let ink = 'pen';
  let shape = 'line';
  let shapeFill = false;
  let hue = 0, sat = 0, val = 0.12;
  let k = 1, tx = 0, ty = 0, rot = 0;
  let sheetOpen = false;
  let confirmOpen = false;
  let contentBox = null;      /* охват всего нарисованного — для обрезки при сохранении */

  /* ---------- Геометрия ---------- */
  const applyT = () => {
    board.style.transform = 'translate3d(' + tx + 'px,' + ty + 'px,0) rotate(' + rot + 'rad) scale(' + k + ')';
  };
  /* Экран → документ. Порядок обратный к CSS-трансформации
     (translate → rotate → scale): сдвигаем на tx/ty, отменяем поворот
     вокруг начала доски и делим на масштаб. */
  const toDoc = (cx, cy, o) => {
    const org = o || originXY();
    const dx = cx - org.x - tx, dy = cy - org.y - ty;
    const ct = Math.cos(rot), st = Math.sin(rot);
    const rx = (dx * ct + dy * st) / k;
    const ry = (-dx * st + dy * ct) / k;
    return { x: rx * docW / board.clientWidth, y: ry * docH / board.clientHeight };
  };
  const originXY = () => {
    const r = stage.getBoundingClientRect();
    return { x: r.left + board.offsetLeft, y: r.top + board.offsetTop };
  };

  const normBox = (b) => {
    const x = clamp(Math.floor(b.x), 0, docW);
    const y = clamp(Math.floor(b.y), 0, docH);
    const x2 = clamp(Math.ceil(b.x + b.w), 0, docW);
    const y2 = clamp(Math.ceil(b.y + b.h), 0, docH);
    return { x, y, w: Math.max(0, x2 - x), h: Math.max(0, y2 - y) };
  };
  const boxOfPoints = (pts, pad) => {
    let x0 = Infinity, y0 = Infinity, x1 = -Infinity, y1 = -Infinity;
    for (const p of pts) {
      if (p.x < x0) x0 = p.x;
      if (p.x > x1) x1 = p.x;
      if (p.y < y0) y0 = p.y;
      if (p.y > y1) y1 = p.y;
    }
    return { x: x0 - pad, y: y0 - pad, w: (x1 - x0) + pad * 2, h: (y1 - y0) + pad * 2 };
  };
  const unionBox = (a, b) => {
    if (!a) return b;
    if (!b) return a;
    const x = Math.min(a.x, b.x), y = Math.min(a.y, b.y);
    return { x, y, w: Math.max(a.x + a.w, b.x + b.w) - x, h: Math.max(a.y + a.h, b.y + b.h) - y };
  };

  /* ---------- История ---------- */
  const hist = [];
  let hp = 0, histBytes = 0;

  const syncHist = () => {
    $('[data-a=undo]').disabled = hp <= 0;
    $('[data-a=redo]').disabled = hp >= hist.length;
  };
  const trimHist = () => {
    while (hist.length > HIST_MAX || (histBytes > HIST_BYTES && hist.length > 1)) {
      const c = hist.shift();
      histBytes -= c.before.data.byteLength + c.after.data.byteLength;
      if (hp > 0) hp--;
    }
  };
  /* Снимок области «до» и «после»: отмена и возврат — это просто
     восстановление пикселей, поэтому работают одинаково точно для
     любого инструмента (включая заливку и очистку). */
  const commit = (rawBox, draw) => {
    const bbox = normBox(rawBox);
    if (!bbox.w || !bbox.h) return;
    const before = bctx.getImageData(bbox.x, bbox.y, bbox.w, bbox.h);
    draw();
    const after = bctx.getImageData(bbox.x, bbox.y, bbox.w, bbox.h);
    hist.length = hp;
    hist.push({ bbox, before, after });
    hp++;
    histBytes += before.data.byteLength + after.data.byteLength;
    trimHist();
    syncHist();
    contentBox = unionBox(contentBox, bbox);
    hint.classList.add('gone');
  };
  const undo = () => {
    if (hp <= 0) return;
    hp--;
    const c = hist[hp];
    bctx.putImageData(c.before, c.bbox.x, c.bbox.y);
    syncHist();
    vibrate(8);
  };
  const redo = () => {
    if (hp >= hist.length) return;
    const c = hist[hp];
    bctx.putImageData(c.after, c.bbox.x, c.bbox.y);
    hp++;
    syncHist();
    vibrate(8);
  };

  /* ---------- Инструменты рисования ---------- */
  const inkSpec = () => (tool === 'eraser' ? INKS.pen : (INKS[ink] || INKS.pen));
  const inkColor = () => (tool === 'eraser' ? PAPER : color);
  const inkAlpha = () => (tool === 'eraser' ? 1 : clamp(alpha * inkSpec().alpha, 0, 1));
  /* Насколько штрих выходит за «осевую» линию: полуширина с учётом
     множителя инструмента плюс размытие мягкого края. По этому запасу
     считаются прямоугольники перерисовки, чтобы края не срезались. */
  const strokeExtent = (w) => {
    const s = inkSpec();
    return w * s.width / 2 + (s.soft ? w * s.soft * 2 : 0) + 2;
  };

  const configCtx = (ctx, w, c) => {
    ctx.lineCap = 'round';
    ctx.lineJoin = 'round';
    ctx.lineWidth = w;
    ctx.strokeStyle = c;
    ctx.fillStyle = c;
  };
  /* Контур под выбранный инструмент: форма кончика, толщина и, для
     «мягких» инструментов (кисть), размытый край — он рисуется тенью
     того же цвета, поэтому линия тает к краям, как настоящая кисть. */
  const applyInk = (ctx, w, c) => {
    const s = inkSpec();
    const lw = w * s.width;
    ctx.lineCap = s.cap;
    ctx.lineJoin = s.join;
    ctx.lineWidth = lw;
    ctx.strokeStyle = c;
    ctx.fillStyle = c;
    ctx.shadowColor = s.soft ? c : 'rgba(0,0,0,0)';
    ctx.shadowBlur = s.soft ? w * s.soft : 0;
  };
  /* Сглаживание: каждый новый отрезок — квадратичная кривая, проходящая
     через середины соседних точек. Так линия остаётся мягкой на любом
     числе точек и не «ломается» при быстром движении пальца. */
  const strokeBegin = (ctx, p, w, c) => {
    const s = inkSpec();
    applyInk(ctx, w, c);
    const lw = w * s.width;
    ctx.beginPath();
    if (s.cap === 'square') ctx.rect(p.x - lw / 2, p.y - lw / 2, lw, lw);
    else ctx.arc(p.x, p.y, Math.max(0.5, lw / 2), 0, Math.PI * 2);
    ctx.fill();
    return { prev: p, mid: p };
  };
  const strokeStep = (ctx, st, p) => {
    const mid = { x: (st.prev.x + p.x) / 2, y: (st.prev.y + p.y) / 2 };
    ctx.beginPath();
    ctx.moveTo(st.mid.x, st.mid.y);
    ctx.quadraticCurveTo(st.prev.x, st.prev.y, mid.x, mid.y);
    ctx.stroke();
    st.prev = p;
    st.mid = mid;
  };
  const strokeEnd = (ctx, st) => {
    if (!st) return;
    ctx.beginPath();
    ctx.moveTo(st.mid.x, st.mid.y);
    ctx.lineTo(st.prev.x, st.prev.y);
    ctx.stroke();
  };
  /* Карандаш: «прогрызаем» в уже нарисованном штрихе мелкие светлые
     пятна, чтобы линия получилась зернистой, как графит по бумаге.
     Работаем в буфере штриха (destination-out), на холст это не влияет. */
  const grainPass = (ctx, pts, w) => {
    if (pts.length < 2) return;
    const r = Math.max(0.6, w * 0.2);
    const n = clamp(Math.round(pts.length * 1.2), 24, 700);
    ctx.save();
    ctx.globalCompositeOperation = 'destination-out';
    for (let i = 0; i < n; i++) {
      const p = pts[(Math.random() * pts.length) | 0];
      ctx.globalAlpha = 0.08 + Math.random() * 0.45;
      ctx.beginPath();
      ctx.arc(
        p.x + (Math.random() - 0.5) * w,
        p.y + (Math.random() - 0.5) * w,
        r * (0.35 + Math.random() * 0.9),
        0, Math.PI * 2
      );
      ctx.fill();
    }
    ctx.restore();
  };

  const shapePath = (ctx, a, b, type, filled, w, c) => {
    ctx.save();
    configCtx(ctx, w, c);
    if (type === 'line') {
      ctx.beginPath();
      ctx.moveTo(a.x, a.y);
      ctx.lineTo(b.x, b.y);
      ctx.stroke();
    } else if (type === 'rect') {
      const x = Math.min(a.x, b.x), y = Math.min(a.y, b.y);
      const wd = Math.abs(b.x - a.x), hd = Math.abs(b.y - a.y);
      if (filled) ctx.fillRect(x, y, wd, hd);
      else ctx.strokeRect(x, y, wd, hd);
    } else {
      const cx = (a.x + b.x) / 2, cy = (a.y + b.y) / 2;
      const rxx = Math.abs(b.x - a.x) / 2, ryy = Math.abs(b.y - a.y) / 2;
      ctx.beginPath();
      ctx.ellipse(cx, cy, rxx, ryy, 0, 0, Math.PI * 2);
      if (filled) ctx.fill();
      else ctx.stroke();
    }
    ctx.restore();
  };

  /* --- Активная операция (штрих или фигура) --- */
  let sess = null;

  const clearLive = (region) => {
    const r = normBox(region);
    if (r.w && r.h) lctx.clearRect(r.x, r.y, r.w, r.h);
  };

  /* Штрих рисуется в буфер непрозрачным, а на «живой» слой попадает уже
     с нужной прозрачностью — поэтому пересечения сегментов не темнеют. */
  const paintStrokeRegion = (region) => {
    lctx.save();
    lctx.clearRect(region.x, region.y, region.w, region.h);
    lctx.globalAlpha = sess.alpha;
    lctx.drawImage(buf, region.x, region.y, region.w, region.h, region.x, region.y, region.w, region.h);
    lctx.restore();
  };

  const beginStroke = (p) => {
    const w = size, c = inkColor(), a = inkAlpha();
    const ext = strokeExtent(w);
    const dot = { x: p.x - ext, y: p.y - ext, w: ext * 2, h: ext * 2 };
    fctx.clearRect(0, 0, docW, docH);
    sess = { kind: 'stroke', pts: [p], w, c, alpha: a, spec: inkSpec(), st: null, painted: dot };
    sess.st = strokeBegin(fctx, p, w, c);
    paintStrokeRegion(normBox(dot));
  };
  const moveStroke = (p) => {
    const segBox = normBox(boxOfPoints([sess.st.prev, p], strokeExtent(sess.w)));
    strokeStep(fctx, sess.st, p);
    sess.pts.push(p);
    /* Перерисовываем только область нового отрезка: она накрывает и
       перекрытие с предыдущим, поэтому прозрачность не «наслаивается»,
       а стоимость кадра не растёт с длиной штриха. */
    paintStrokeRegion(segBox);
    sess.painted = unionBox(sess.painted, segBox);
  };
  const endStroke = () => {
    const s = sess;
    sess = null;
    if (!s) return;
    strokeEnd(fctx, s.st);
    if (s.spec.grain) grainPass(fctx, s.pts, s.w);
    const box = boxOfPoints(s.pts, strokeExtent(s.w));
    clearLive(s.painted);
    commit(box, () => {
      bctx.save();
      bctx.globalAlpha = s.alpha;
      bctx.drawImage(buf, 0, 0);
      bctx.restore();
    });
    fctx.clearRect(0, 0, docW, docH);
  };

  const drawShapeLive = () => {
    const s = sess;
    const nb = normBox(boxOfPoints([s.a, s.b], size / 2 + 3));
    const region = normBox(unionBox(s.painted, nb));
    lctx.clearRect(region.x, region.y, region.w, region.h);
    lctx.save();
    lctx.globalAlpha = alpha;
    shapePath(lctx, s.a, s.b, shape, shapeFill, size, color);
    lctx.restore();
    s.painted = nb;
  };
  const beginShape = (p) => {
    sess = { kind: 'shape', a: p, b: p, painted: null };
    drawShapeLive();
  };
  const moveShape = (p) => {
    sess.b = p;
    drawShapeLive();
  };
  const endShape = () => {
    const s = sess;
    sess = null;
    if (!s) return;
    const box = boxOfPoints([s.a, s.b], size / 2 + 3);
    clearLive(s.painted);
    commit(box, () => {
      bctx.save();
      bctx.globalAlpha = alpha;
      shapePath(bctx, s.a, s.b, shape, shapeFill, size, color);
      bctx.restore();
    });
  };

  const cancelPaint = () => {
    if (!sess) return;
    if (sess.kind === 'stroke') {
      clearLive(sess.painted);
      fctx.clearRect(0, 0, docW, docH);
    } else {
      clearLive(sess.painted);
    }
    sess = null;
  };

  /* --- Пипетка --- */
  const pick = (p) => {
    const x = clamp(Math.floor(p.x), 0, docW - 1);
    const y = clamp(Math.floor(p.y), 0, docH - 1);
    const d = bctx.getImageData(x, y, 1, 1).data;
    setColor(rgbToHex(d[0], d[1], d[2]));
    setTool('brush');
    vibrate(10);
  };

  /* --- Заливка (сканлайн) --- */
  const fillAt = (p) => {
    const sx = clamp(Math.floor(p.x), 0, docW - 1);
    const sy = clamp(Math.floor(p.y), 0, docH - 1);
    const img = bctx.getImageData(0, 0, docW, docH);
    const d = img.data;
    const W = docW, H = docH;
    const si = (sy * W + sx) * 4;
    const tr = d[si], tg = d[si + 1], tb = d[si + 2];
    const fr = hexToRgb(color);
    const a = alpha;
    if (a >= 0.999 && tr === fr[0] && tg === fr[1] && tb === fr[2]) return;

    const seen = new Uint8Array(W * H);
    /* Наибольшее расхождение пикселя с цветом-образцом (0..255). */
    const diff = (i) => {
      const o = i * 4;
      return Math.max(Math.abs(d[o] - tr), Math.abs(d[o + 1] - tg), Math.abs(d[o + 2] - tb));
    };
    const match = (i) => diff(i) <= TOLERANCE;
    const stack = [sx, sy];
    let minX = sx, maxX = sx, minY = sy, maxY = sy;

    while (stack.length) {
      const y = stack.pop();
      const x = stack.pop();
      if (seen[y * W + x]) continue;
      let x1 = x;
      while (x1 >= 0 && !seen[y * W + x1] && match(y * W + x1)) x1--;
      x1++;
      let x2 = x;
      while (x2 < W && !seen[y * W + x2] && match(y * W + x2)) x2++;
      x2--;
      if (x2 < x1) { seen[y * W + x] = 1; continue; }
      for (let i = x1; i <= x2; i++) {
        const o = (y * W + i) * 4;
        d[o] = Math.round(d[o] * (1 - a) + fr[0] * a);
        d[o + 1] = Math.round(d[o + 1] * (1 - a) + fr[1] * a);
        d[o + 2] = Math.round(d[o + 2] * (1 - a) + fr[2] * a);
        d[o + 3] = 255;
        seen[y * W + i] = 1;
      }
      if (x1 < minX) minX = x1;
      if (x2 > maxX) maxX = x2;
      if (y < minY) minY = y;
      if (y > maxY) maxY = y;
      const rows = [y - 1, y + 1];
      for (const ny of rows) {
        if (ny < 0 || ny >= H) continue;
        let run = false;
        for (let i = x1; i <= x2; i++) {
          const j = ny * W + i;
          const ok = !seen[j] && match(j);
          if (ok && !run) { stack.push(i, ny); run = true; }
          else if (!ok) run = false;
        }
      }
    }

    /* Край линии сглажен (антиалиасинг), поэтому пиксели у самой
       границы — смесь линии и фона. Дотягиваем заливку до них:
       подмешиваем цвет тем сильнее, чем ближе пиксель к образцу.
       Иначе по краю остаётся тонкая светлая рамка. */
    const y0 = Math.max(0, minY - 1), y1 = Math.min(H - 1, maxY + 1);
    const x0 = Math.max(0, minX - 1), x1 = Math.min(W - 1, maxX + 1);
    for (let y = y0; y <= y1; y++) {
      for (let x = x0; x <= x1; x++) {
        const i = y * W + x;
        if (seen[i]) continue;
        const near =
          (x > 0 && seen[i - 1]) || (x < W - 1 && seen[i + 1]) ||
          (y > 0 && seen[i - W]) || (y < H - 1 && seen[i + W]);
        if (!near) continue;
        const dd = diff(i);
        if (dd > TOLERANCE_SOFT) continue;
        const w = a * clamp((TOLERANCE_SOFT - dd) / (TOLERANCE_SOFT - TOLERANCE), 0, 1);
        if (w <= 0) continue;
        const o = i * 4;
        d[o] = Math.round(d[o] * (1 - w) + fr[0] * w);
        d[o + 1] = Math.round(d[o + 1] * (1 - w) + fr[1] * w);
        d[o + 2] = Math.round(d[o + 2] * (1 - w) + fr[2] * w);
        d[o + 3] = 255;
      }
    }

    const box = normBox({ x: minX - 1, y: minY - 1, w: (maxX - minX) + 3, h: (maxY - minY) + 3 });
    commit(box, () => {
      bctx.putImageData(img, 0, 0, box.x, box.y, box.w, box.h);
    });
  };

  const beginPaint = (p) => {
    if (tool === 'picker') { pick(p); return; }
    if (tool === 'fill') { fillAt(p); vibrate(8); return; }
    if (tool === 'shape') { beginShape(p); return; }
    beginStroke(p);
  };
  const extendPaint = (p) => {
    if (!sess) return;
    if (sess.kind === 'stroke') moveStroke(p);
    else moveShape(p);
  };
  const finishPaint = () => {
    if (!sess) return;
    if (sess.kind === 'stroke') endStroke();
    else endShape();
  };

  /* ---------- Жесты и указатели ---------- */
  const pointers = new Map();
  let multi = false;         /* после двух пальцев рисование ждём до полного отпускания */
  let gesture = null;

  const gestureMove = () => {
    const arr = Array.from(pointers.values());
    const p1 = arr[0], p2 = arr[1];
    if (!p1 || !p2 || !gesture) return;
    const g = gesture;
    const c = { x: (p1.x + p2.x) / 2, y: (p1.y + p2.y) / 2 };
    const dist = Math.max(10, Math.hypot(p2.x - p1.x, p2.y - p1.y));
    const ang = Math.atan2(p2.y - p1.y, p2.x - p1.x);
    const nk = clamp(g.k0 * (dist / g.d0), MIN_K, MAX_K);
    /* Поворот полностью свободный, но рядом с «сеткой» (0/90/180/270)
       холст мягко примагничивается — отойти можно в любой момент. */
    let nr = g.rot0 + (ang - g.a0);
    const grid = Math.round(nr / SNAP_STEP) * SNAP_STEP;
    const onGrid = Math.abs(nr - grid) <= SNAP_TOL;
    if (onGrid) nr = grid;
    if (onGrid && !g.onGrid) vibrate(6);
    g.onGrid = onGrid;
    /* Точка холста под стартовым центроидом остаётся под текущим:
       экран = origin + translate + rotate · (scale · layout). */
    const nk0 = g.ux * nk, nk1 = g.uy * nk;
    const cr = Math.cos(nr), sr = Math.sin(nr);
    tx = c.x - g.ox - (nk0 * cr - nk1 * sr);
    ty = c.y - g.oy - (nk0 * sr + nk1 * cr);
    k = nk;
    rot = nr;
    applyT();
    const moved = Math.hypot(c.x - g.c0.x, c.y - g.c0.y) + Math.abs(dist - g.d0) + Math.abs(ang - g.a0) * 120;
    if (moved > g.moved) g.moved = moved;
  };
  const beginGesture = () => {
    const arr = Array.from(pointers.values());
    const p1 = arr[0], p2 = arr[1];
    if (!p1 || !p2) return;
    const o = originXY();
    const c0 = { x: (p1.x + p2.x) / 2, y: (p1.y + p2.y) / 2 };
    /* Layout-координата точки под центроидом: отменяем сдвиг и поворот. */
    const dx = c0.x - o.x - tx, dy = c0.y - o.y - ty;
    const ct = Math.cos(rot), st = Math.sin(rot);
    gesture = {
      t0: performance.now(),
      c0,
      d0: Math.max(10, Math.hypot(p2.x - p1.x, p2.y - p1.y)),
      a0: Math.atan2(p2.y - p1.y, p2.x - p1.x),
      k0: k, rot0: rot, ox: o.x, oy: o.y,
      ux: (dx * ct + dy * st) / k,
      uy: (-dx * st + dy * ct) / k,
      moved: 0, onGrid: false,
    };
  };
  const endGesture = () => {
    const g = gesture;
    gesture = null;
    if (!g) return;
    /* Двойной тап двумя пальцами — отмена (как в ibis Paint). */
    if (pointers.size === 0 && performance.now() - g.t0 < 280 && g.moved < 14) undo();
  };

  const onDown = (e) => {
    if (sheetOpen || confirmOpen) return;
    if (e.pointerType === 'mouse' && e.button !== 0) return;
    try { board.setPointerCapture(e.pointerId); } catch (_) {}
    pointers.set(e.pointerId, { x: e.clientX, y: e.clientY });
    if (pointers.size === 1) {
      beginPaint(toDoc(e.clientX, e.clientY));
    } else {
      multi = true;
      cancelPaint();
      /* Жест начинаем ровно на втором пальце: третий и далее лишь
         продолжают уже начатый поворот/зум, не сбрасывая его. */
      if (pointers.size === 2) beginGesture();
    }
    e.preventDefault();
  };
  const onMove = (e) => {
    const p = pointers.get(e.pointerId);
    if (!p) return;
    p.x = e.clientX;
    p.y = e.clientY;
    if (pointers.size >= 2) { gestureMove(); return; }
    if (multi) return;
    /* Склеенные события: на 120 Гц экранах браузер отдаёт пачку точек
       одним кадром — линия получается заметно ровнее. */
    const coalesced = e.getCoalescedEvents ? e.getCoalescedEvents() : null;
    if (coalesced && coalesced.length) {
      const o = originXY();
      for (const ce of coalesced) extendPaint(toDoc(ce.clientX, ce.clientY, o));
    } else {
      extendPaint(toDoc(e.clientX, e.clientY));
    }
  };
  const onUp = (e) => {
    if (!pointers.has(e.pointerId)) return;
    pointers.delete(e.pointerId);
    try { board.releasePointerCapture(e.pointerId); } catch (_) {}
    if (pointers.size === 0) {
      if (!multi) finishPaint();
      endGesture();
      multi = false;
    }
  };

  board.addEventListener('pointerdown', onDown);
  board.addEventListener('pointermove', onMove);
  board.addEventListener('pointerup', onUp);
  board.addEventListener('pointercancel', onUp);
  board.addEventListener('contextmenu', (e) => e.preventDefault());

  /* ---------- Слайдеры ---------- */
  const bindSlider = (el, setT) => {
    let active = false;
    const handle = (e) => {
      const r = el.getBoundingClientRect();
      setT(clamp((e.clientX - r.left) / r.width, 0, 1));
    };
    el.addEventListener('pointerdown', (e) => {
      active = true;
      try { el.setPointerCapture(e.pointerId); } catch (_) {}
      handle(e);
      vibrate(5);
      e.preventDefault();
    });
    el.addEventListener('pointermove', (e) => {
      if (!active) return;
      handle(e);
      e.preventDefault();
    });
    const up = (e) => {
      if (!active) return;
      active = false;
      try { el.releasePointerCapture(e.pointerId); } catch (_) {}
    };
    el.addEventListener('pointerup', up);
    el.addEventListener('pointercancel', up);
  };

  const sliderSize = $('[data-slider=size]');
  const sliderOpacity = $('[data-slider=opacity]');
  const sizeDot = $('.dr-size-dot');

  const syncSizeUI = () => {
    const t = tFromSize(size);
    sliderSize.querySelector('.dr-fill').style.width = (t * 100) + '%';
    sliderSize.querySelector('.dr-knob').style.left = (t * 100) + '%';
    $('[data-val=size]').textContent = String(size);
    const dot = Math.round(5 + t * 25);
    sizeDot.style.width = dot + 'px';
    sizeDot.style.height = dot + 'px';
  };
  const syncOpacityUI = () => {
    const t = (alpha - 0.1) / 0.9;
    sliderOpacity.querySelector('.dr-fill').style.width = (t * 100) + '%';
    sliderOpacity.querySelector('.dr-knob').style.left = (t * 100) + '%';
    $('[data-val=opacity]').textContent = Math.round(alpha * 100) + '%';
  };

  bindSlider(sliderSize, (t) => { size = sizeFromT(t); syncSizeUI(); });
  bindSlider(sliderOpacity, (t) => { alpha = Math.round((0.1 + t * 0.9) * 100) / 100; syncOpacityUI(); });

  /* ---------- Инструменты / тип пера / фигуры ---------- */
  const toolBtns = $$('.dr-tool[data-tool]');
  const inkChips = $$('.dr-chip[data-ink]');
  const shapeChips = $$('.dr-chip[data-shape]');
  const setTool = (t) => {
    tool = t;
    toolBtns.forEach((b) => b.classList.toggle('active', b.dataset.tool === t));
    const opts = $('.dr-options');
    opts.classList.toggle('shapes', t === 'shape');
    opts.classList.toggle('ink', t === 'brush');
  };
  const setInk = (v) => {
    ink = INKS[v] ? v : 'pen';
    inkChips.forEach((b) => b.classList.toggle('active', b.dataset.ink === ink));
  };
  toolBtns.forEach((b) => b.addEventListener('click', () => { setTool(b.dataset.tool); vibrate(6); }));
  inkChips.forEach((b) => b.addEventListener('click', () => { setInk(b.dataset.ink); vibrate(6); }));

  shapeChips.forEach((b) => b.addEventListener('click', () => {
    if (b.dataset.shape === 'fillmode') {
      shapeFill = !shapeFill;
      b.classList.toggle('active', shapeFill);
    } else {
      shape = b.dataset.shape;
      shapeChips.forEach((x) => {
        if (x.dataset.shape !== 'fillmode') x.classList.toggle('active', x === b);
      });
      setTool('shape');
    }
    vibrate(6);
  }));

  /* ---------- Цвет ---------- */
  const svC = $('.dr-sv');
  const svx = svC.getContext('2d');
  const hueEl = $('.dr-hue');
  const hueKnob = $('.dr-hue-knob');
  const presetsEl = $('.dr-presets');
  presetsEl.innerHTML = PRESETS.map((c) => '<button class="dr-preset" data-c="' + c + '" style="background:' + c + '"></button>').join('');

  const syncColorUI = () => {
    $('.dr-dot').style.background = color;
    $('.dr-color-prev').style.background = color;
    $('.dr-color-hex').textContent = color.toUpperCase();
    /* Точка у ползунка размера красится текущим цветом кисти. */
    sizeDot.style.background = color;
    $$('.dr-preset').forEach((el) => el.classList.toggle('active', el.dataset.c.toLowerCase() === color.toLowerCase()));
  };
  const updateHueKnob = () => { hueKnob.style.left = (hue / 360 * 100) + '%'; };

  const drawSV = () => {
    const w = svC.clientWidth, h = svC.clientHeight;
    if (!w || !h) return;
    const dw = Math.round(w * dpr), dh = Math.round(h * dpr);
    if (svC.width !== dw || svC.height !== dh) { svC.width = dw; svC.height = dh; }
    const pure = hsvToRgb(hue, 1, 1);
    svx.fillStyle = rgbToHex(pure[0], pure[1], pure[2]);
    svx.fillRect(0, 0, dw, dh);
    const gx = svx.createLinearGradient(0, 0, dw, 0);
    gx.addColorStop(0, 'rgba(255,255,255,1)');
    gx.addColorStop(1, 'rgba(255,255,255,0)');
    svx.fillStyle = gx;
    svx.fillRect(0, 0, dw, dh);
    const gy = svx.createLinearGradient(0, 0, 0, dh);
    gy.addColorStop(0, 'rgba(0,0,0,0)');
    gy.addColorStop(1, 'rgba(0,0,0,1)');
    svx.fillStyle = gy;
    svx.fillRect(0, 0, dw, dh);
    const cx = sat * dw, cy = (1 - val) * dh, r = 9 * dpr;
    svx.beginPath();
    svx.arc(clamp(cx, r, dw - r), clamp(cy, r, dh - r), r, 0, Math.PI * 2);
    svx.lineWidth = 2.5 * dpr;
    svx.strokeStyle = 'rgba(0,0,0,.35)';
    svx.stroke();
    svx.lineWidth = 1.5 * dpr;
    svx.strokeStyle = '#fff';
    svx.stroke();
  };

  const applyHSV = () => {
    const c = hsvToRgb(hue, sat, val);
    color = rgbToHex(c[0], c[1], c[2]);
    syncColorUI();
    updateHueKnob();
    drawSV();
  };
  const setColor = (hex) => {
    color = hex;
    const c = hexToRgb(hex);
    const hsv = rgbToHsv(c[0], c[1], c[2]);
    hue = hsv[0];
    sat = hsv[1];
    val = hsv[2];
    syncColorUI();
    updateHueKnob();
    drawSV();
  };

  const bindArea = (el, fn) => {
    let active = false;
    const handle = (e) => {
      const r = el.getBoundingClientRect();
      fn(clamp((e.clientX - r.left) / r.width, 0, 1), clamp((e.clientY - r.top) / r.height, 0, 1));
    };
    el.addEventListener('pointerdown', (e) => {
      active = true;
      try { el.setPointerCapture(e.pointerId); } catch (_) {}
      handle(e);
      e.preventDefault();
    });
    el.addEventListener('pointermove', (e) => {
      if (!active) return;
      handle(e);
      e.preventDefault();
    });
    const up = (e) => {
      if (!active) return;
      active = false;
      try { el.releasePointerCapture(e.pointerId); } catch (_) {}
    };
    el.addEventListener('pointerup', up);
    el.addEventListener('pointercancel', up);
  };

  bindArea(svC, (x, y) => { sat = x; val = 1 - y; applyHSV(); });
  bindArea(hueEl, (x) => { hue = x * 360; applyHSV(); });
  presetsEl.addEventListener('click', (e) => {
    const b = e.target.closest('.dr-preset');
    if (!b) return;
    setColor(b.dataset.c);
    vibrate(6);
  });

  /* ---------- Панель цвета ---------- */
  const sheet = $('.dr-sheet');
  const sheetBg = $('[data-a=sheet-bg]');
  const openSheet = () => {
    sheetOpen = true;
    sheetBg.classList.add('open');
    sheet.classList.add('open');
    requestAnimationFrame(drawSV);
  };
  const closeSheet = () => {
    if (!sheetOpen) return;
    sheetOpen = false;
    sheetBg.classList.remove('open');
    sheet.classList.remove('open');
  };
  $('[data-a=color]').addEventListener('click', () => {
    setColor(color);
    if (sheetOpen) closeSheet();
    else openSheet();
  });
  $('[data-a=sheet-close]').addEventListener('click', closeSheet);
  sheetBg.addEventListener('click', closeSheet);

  /* ---------- Верхние кнопки ---------- */
  $('[data-a=undo]').addEventListener('click', undo);
  $('[data-a=redo]').addEventListener('click', redo);

  $('[data-a=fit]').addEventListener('click', () => {
    board.classList.add('glide');
    k = 1; tx = 0; ty = 0; rot = 0;
    applyT();
    setTimeout(() => board.classList.remove('glide'), 340);
    vibrate(6);
  });

  /* ---------- Диалог подтверждения ---------- */
  const drConfirm = $('[data-confirm]');
  let confirmRes = null;
  const dismissConfirm = () => {
    if (!confirmRes) return;
    const r = confirmRes;
    confirmRes = null;
    confirmOpen = false;
    drConfirm.classList.remove('open');
    r(false);
  };
  const ask = (title, text, okLabel) => {
    dismissConfirm();
    $('.dr-confirm-title').textContent = title;
    $('.dr-confirm-text').textContent = text;
    $('[data-c=yes]').textContent = okLabel;
    confirmOpen = true;
    drConfirm.classList.add('open');
    return new Promise((resolve) => { confirmRes = resolve; });
  };
  drConfirm.addEventListener('click', (e) => {
    const b = e.target.closest('[data-c]');
    if (!b || !confirmRes) return;
    const v = b.dataset.c === 'yes';
    const r = confirmRes;
    confirmRes = null;
    confirmOpen = false;
    drConfirm.classList.remove('open');
    r(v);
  });

  $('[data-a=clear]').addEventListener('click', async () => {
    if (!hist.length) { toast('Холст уже пуст'); return; }
    const yes = await ask('Очистить холст?', 'Всё нарисованное можно вернуть кнопкой «Отменить».', 'Очистить');
    if (!yes) return;
    commit({ x: 0, y: 0, w: docW, h: docH }, () => {
      bctx.fillStyle = PAPER;
      bctx.fillRect(0, 0, docW, docH);
    });
    vibrate(12);
  });

  /* ---------- Закрытие и сохранение ---------- */
  const close = () => {
    window.removeEventListener('keydown', onKey);
    ov.classList.remove('open');
    setTimeout(() => ov.remove(), 360);
  };

  const finish = () => {
    if (!hist.length) { close(); return; }
    const pad = Math.round(16 * dpr);
    const box = contentBox
      ? normBox({ x: contentBox.x - pad, y: contentBox.y - pad, w: contentBox.w + pad * 2, h: contentBox.h + pad * 2 })
      : { x: 0, y: 0, w: docW, h: docH };
    if (!box.w || !box.h) { close(); return; }
    let url = '';
    try {
      const out = document.createElement('canvas');
      out.width = Math.max(1, box.w);
      out.height = Math.max(1, box.h);
      const octx = out.getContext('2d');
      octx.fillStyle = PAPER;
      octx.fillRect(0, 0, out.width, out.height);
      octx.drawImage(base, box.x, box.y, box.w, box.h, 0, 0, box.w, box.h);
      url = out.toDataURL('image/png');
    } catch (err) {
      toast('Не удалось сохранить рисунок');
      return;
    }
    if (url && onDone) onDone(url);
    close();
  };

  $('[data-a=done]').addEventListener('click', finish);
  $('[data-a=close]').addEventListener('click', async () => {
    if (confirmOpen) { dismissConfirm(); return; }
    if (!hist.length) { close(); return; }
    if (await ask('Выйти без сохранения?', 'Рисунок не будет добавлен в заметку.', 'Выйти')) close();
  });

  /* ---------- Клавиатура (для настольных браузеров) ---------- */
  function onKey(e) {
    if (e.key === 'Escape') {
      if (confirmOpen) dismissConfirm();
      else if (sheetOpen) closeSheet();
      else $('[data-a=close]').click();
      return;
    }
    if ((e.metaKey || e.ctrlKey) && (e.key === 'z' || e.key === 'Z' || e.key === 'я')) {
      e.preventDefault();
      if (e.shiftKey) redo();
      else undo();
    }
  }
  window.addEventListener('keydown', onKey);

  /* ---------- Стартовое состояние ---------- */
  syncSizeUI();
  syncOpacityUI();
  setTool('brush');
  setInk('pen');
  shapeChips.forEach((x) => { if (x.dataset.shape === 'line') x.classList.add('active'); });
  setColor('#1c1d21');
  syncHist();
  applyT();
  setTimeout(() => $('.dr-hint').classList.add('gone'), 3400);
}
