/* ============ Скины инструментов ============
   Оригинальные слои панели рисования Xiaomi Notes 3.2.7.9.
   Каждый инструмент собран ровно из тех же drawable, что и в приложении:

     doodle_ic_brush_<brush>_body / _head / _shadow_s / _shadow_u
     doodle_ic_tool_<tool>_body / _shadow_s / _shadow_u

   body и тени — готовые рендеры со светотенью; head — alpha-маска
   акцентного слоя (наконечник, точка и полоса на корпусе). В системном
   приложении она заливается активным цветом, поэтому мы делаем то же:
   цвет заменяет RGB маски, а её альфа сохраняет оригинальную светотень.
   Тень _s — у поднятого (выбранного) инструмента, _u — у остальных.

   Полотно drawable рассчитано на панель 156 dp, но нижний торец каждого
   инструмента в системе уходит под кромку экрана. Поэтому корпус
   продолжается вниз: строка чуть выше нижнего скругления растягивается
   до низа полотна (полотно длиннее панели на OVERHANG). Так инструмент
   стоит «в» панели, а не «на» ней — и торец не виден даже после подъёма. */

const NATIVE_W = 306;                       // ширина исходного полотна drawable
const NATIVE_H = 547;                       // высота исходного полотна drawable
const CONTENT_H = 156;                      // натуральная высота drawable в CSS px
const DESIGN_H = 178;                       // высота панели, под которую свёрстано
const OVERHANG = 34;                        // запас корпуса под нижней кромкой экрана
const SKIN_H = DESIGN_H + OVERHANG;         // полотно скина целиком
const SKIN_W = NATIVE_W / NATIVE_H * CONTENT_H;

const SKINS = {
  pen:          { res: 'pencil',       accent: true,  ink: '#1c1c1c' },
  markerBlue:   { res: 'watercolor',   accent: true,  ink: '#5aaef8' },
  markerYellow: { res: 'markpen',      accent: true,  ink: '#ffab3f' },
  fountain:     { res: 'pen',          accent: true,  ink: '#69cf36' },
  eraser:       { res: 'tool_eraser' },
  image:        { res: 'tool_picture' },
  text:         { res: 'tool_text' },
  note:         { res: 'tool_background' },
};

const ITEMS = new Map();

function asset(name) {
  return new URL(`assets/original/${name}`, document.baseURI).href;
}

export function toolSkin(id) {
  const s = SKINS[id];
  if (!s) return '';
  return `<span class="dw-skin-box" style="width:${SKIN_W.toFixed(2)}px;height:${SKIN_H}px"><canvas class="dw-skin" data-skin="${id}" aria-hidden="true"></canvas></span>`;
}

/* Где заканчивается корпус и начинается нижнее скругление.
   Возвращает строку исходного полотна, с которой корпус можно тянуть вниз. */
let scratch = null, scratchCtx = null;
function bodyCutRow(img) {
  const w = NATIVE_W, h = NATIVE_H;
  if (!scratch) {
    scratch = document.createElement('canvas');
    scratch.width = w; scratch.height = h;
    try { scratchCtx = scratch.getContext('2d', { willReadFrequently: true }); }
    catch (e) { scratchCtx = scratch.getContext('2d'); }
  }
  if (!scratchCtx) return Math.round(h * 0.9);
  scratchCtx.clearRect(0, 0, w, h);
  scratchCtx.drawImage(img, 0, 0, w, h);
  let data;
  try { data = scratchCtx.getImageData(0, 0, w, h).data; }
  catch (e) { return Math.round(h * 0.9); }

  const widths = new Uint16Array(h);
  let top = h, bottom = -1;
  for (let y = 0; y < h; y++) {
    let x0 = -1, x1 = -1;
    const row = y * w * 4;
    for (let x = 0; x < w; x++) {
      if (data[row + x * 4 + 3] > 12) { if (x0 < 0) x0 = x; x1 = x; }
    }
    if (x1 >= 0) {
      widths[y] = x1 - x0 + 1;
      if (y < top) top = y;
      bottom = y;
    }
  }
  if (bottom < 0) return Math.round(h * 0.9);
  const span = bottom - top + 1;
  // Ориентир — ширина корпуса чуть выше торца (у маркера внизу бывает
  // узкий хвостовик, его тоже надо спрятать за кромкой экрана).
  const refFrom = Math.max(top, bottom - Math.round(span * 0.1));
  let ref = 0;
  for (let y = refFrom; y <= bottom; y++) if (widths[y] > ref) ref = widths[y];
  const thr = ref * 0.86;
  const limit = Math.max(top, bottom - Math.max(18, Math.round(span * 0.22)));
  let y = bottom;
  while (y > limit && widths[y] < thr) y--;
  // берём строку чуть выше среза: у самого торца края уже сглажены
  return Math.max(top + 2, y - 5);
}

/* Дорисовывает низ корпуса: срез растягивается вниз до конца полотна.
   Строка берётся ровно там, где кончается ровный корпус, поэтому шва нет. */
function extendDown(ctx, img, row, cw, ch, contentPx) {
  const hNative = img.height || NATIVE_H;
  const y = Math.max(0, Math.min(hNative - 2, row));
  const dy = y * contentPx / NATIVE_H;
  if (dy >= ch) return;
  ctx.drawImage(img, 0, y, img.width, 2, 0, dy, cw, ch - dy);
}

/* Тень не обрезается ножом на конце drawable, а плавно сходит на нет. */
function extendShadow(rec, img) {
  const { ctx, cw, ch, contentPx } = rec;
  const tailH = ch - contentPx;
  if (tailH <= 0) return;
  if (!rec.tail) rec.tail = document.createElement('canvas');
  const t = rec.tail;
  if (t.width !== cw || t.height !== tailH) { t.width = cw; t.height = tailH; }
  const g = t.getContext('2d');
  g.setTransform(1, 0, 0, 1, 0, 0);
  g.globalCompositeOperation = 'source-over';
  g.clearRect(0, 0, cw, tailH);
  g.drawImage(img, 0, Math.max(0, img.height - 3), img.width, 3, 0, 0, cw, tailH);
  g.globalCompositeOperation = 'destination-in';
  const grad = g.createLinearGradient(0, 0, 0, tailH);
  grad.addColorStop(0, 'rgba(0,0,0,.9)');
  grad.addColorStop(.55, 'rgba(0,0,0,.45)');
  grad.addColorStop(1, 'rgba(0,0,0,0)');
  g.fillStyle = grad;
  g.fillRect(0, 0, cw, tailH);
  g.globalCompositeOperation = 'source-over';
  ctx.drawImage(t, 0, contentPx);
}

function paintHead(rec) {
  const { ctx, cw, ch, imgs, tctx, contentPx } = rec;
  if (!imgs.head || !rec.ink) return;
  tctx.globalCompositeOperation = 'source-over';
  tctx.clearRect(0, 0, cw, ch);
  tctx.fillStyle = rec.ink;
  tctx.fillRect(0, 0, cw, contentPx);
  tctx.globalCompositeOperation = 'destination-in';
  tctx.drawImage(imgs.head, 0, 0, cw, contentPx);
  tctx.globalCompositeOperation = 'source-over';
  ctx.drawImage(rec.tint, 0, 0);
}

function render(rec) {
  const { ctx, cw, ch, imgs, contentPx } = rec;
  if (!ctx) return;
  ctx.clearRect(0, 0, cw, ch);
  const shadow = rec.active ? imgs.shadowS : imgs.shadowU;
  if (shadow) {
    ctx.drawImage(shadow, 0, 0, cw, contentPx);
    extendShadow(rec, shadow);
  }
  if (imgs.body) {
    ctx.drawImage(imgs.body, 0, 0, cw, contentPx);
    extendDown(ctx, imgs.body, rec.cutRow, cw, ch, contentPx);
  }
  if (rec.s.accent) paintHead(rec);
}

export function initToolSkins(toolInk, root = document) {
  const dpr = Math.min(3, Math.max(2, window.devicePixelRatio || 2));
  for (const [id, s] of Object.entries(SKINS)) {
    const canvas = root.querySelector(`canvas[data-skin="${id}"]`);
    if (!canvas) continue;
    const cw = Math.round(SKIN_W * dpr);
    const ch = Math.round(SKIN_H * dpr);
    canvas.width = cw;
    canvas.height = ch;
    const ctx = canvas.getContext('2d');
    if (!ctx) continue;
    ctx.imageSmoothingEnabled = true;
    ctx.imageSmoothingQuality = 'high';

    const tint = document.createElement('canvas');
    tint.width = cw;
    tint.height = ch;

    const rec = {
      id, s, ctx, cw, ch,
      contentPx: Math.round(CONTENT_H * dpr),
      ink: toolInk[id] || s.ink || null,
      active: false,
      imgs: { body: null, shadowS: null, shadowU: null, head: null },
      tint, tctx: tint.getContext('2d'),
      cutRow: Math.round(NATIVE_H * 0.9),
      tail: null,
      ready: 0,
      need: s.accent ? 4 : 3,
    };
    ITEMS.set(id, rec);

    const load = (suffix, key) => {
      const img = new Image();
      img.decoding = 'async';
      img.onload = () => {
        if (key === 'body') rec.cutRow = bodyCutRow(img);
        rec.imgs[key] = img;
        rec.ready++;
        if (rec.ready >= rec.need) render(rec);
      };
      img.onerror = () => {
        rec.ready++;
        if (rec.ready >= rec.need) render(rec);
      };
      img.src = asset(`${s.res}-${suffix}.png`);
    };
    load('shadow-u', 'shadowU');
    load('shadow-s', 'shadowS');
    load('body', 'body');
    if (s.accent) load('head', 'head');
  }
}

export function setToolSkinColor(id, ink) {
  const rec = ITEMS.get(id);
  if (!rec || !rec.s.accent || !ink || rec.ink === ink) return;
  rec.ink = ink;
  if (rec.ready >= rec.need) render(rec);
}

export function setToolSkinActive(id, active) {
  const rec = ITEMS.get(id);
  if (!rec || rec.active === active) return;
  rec.active = active;
  if (rec.ready >= rec.need) render(rec);
}
