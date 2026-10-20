/* ============ Скины инструментов ============
   Оригинальные слои панели рисования Xiaomi Notes 3.2.7.9.
   Каждый инструмент собран ровно из тех же drawable, что и в приложении:

     doodle_ic_brush_<brush>_body / _head / _shadow_s / _shadow_u
     doodle_ic_tool_<tool>_body / _shadow_s / _shadow_u

   body и тени — готовые рендеры со светотенью; head — alpha-маска
   акцентного слоя (наконечник, точка и полоса на корпусе). В системном
   приложении она заливается активным цветом, поэтому мы делаем то же:
   цвет заменяет RGB маски, а её альфа сохраняет оригинальную светотень.
   Тень _s — у поднятого (выбранного) инструмента, _u — у остальных. */

const NATIVE_W = 306;                       // ширина исходного полотна drawable
const NATIVE_H = 547;                       // высота исходного полотна drawable
// 547 px drawable при 560 dpi — это ровно 156 dp, то есть высота панели
// инструментов. Рисуем полотно в натуральную величину: инструмент стоит
// на нижней кромке панели, запечённые тени не обрезаются.
const SKIN_H = 156;                         // высота полотна в CSS px
const SKIN_W = NATIVE_W / NATIVE_H * SKIN_H;
const SKIN_TOP = 0;                         // полотно совпадает с кнопкой

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
  return `<span class="dw-skin-box" style="width:${SKIN_W.toFixed(2)}px;height:${SKIN_H}px;top:${SKIN_TOP}px"><canvas class="dw-skin" data-skin="${id}" aria-hidden="true"></canvas></span>`;
}

function paintHead(rec) {
  const { ctx, cw, ch, imgs, tctx } = rec;
  if (!imgs.head || !rec.ink) return;
  tctx.globalCompositeOperation = 'source-over';
  tctx.clearRect(0, 0, cw, ch);
  tctx.fillStyle = rec.ink;
  tctx.fillRect(0, 0, cw, ch);
  tctx.globalCompositeOperation = 'destination-in';
  tctx.drawImage(imgs.head, 0, 0, cw, ch);
  tctx.globalCompositeOperation = 'source-over';
  ctx.drawImage(rec.tint, 0, 0);
}

function render(rec) {
  const { ctx, cw, ch, imgs } = rec;
  if (!ctx) return;
  ctx.clearRect(0, 0, cw, ch);
  const shadow = rec.active ? imgs.shadowS : imgs.shadowU;
  if (shadow) ctx.drawImage(shadow, 0, 0, cw, ch);
  if (imgs.body) ctx.drawImage(imgs.body, 0, 0, cw, ch);
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
      ink: toolInk[id] || s.ink || null,
      active: false,
      imgs: { body: null, shadowS: null, shadowU: null, head: null },
      tint, tctx: tint.getContext('2d'),
      ready: 0,
      need: s.accent ? 4 : 3,
    };
    ITEMS.set(id, rec);

    const load = (suffix, key) => {
      const img = new Image();
      img.decoding = 'async';
      img.onload = () => {
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
