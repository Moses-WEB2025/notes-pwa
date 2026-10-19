/* ============ Скины инструментов ============
   Точные 3D-рендеры инструментов из системного приложения, пересобранные
   в чистые PNG: аккуратная альфа-маска, без фона и без карточки у ручек.
   Для цветных инструментов есть отдельный слой акцента: при смене цвета
   перекрашивается сам акцент с сохранением светотени, а не накладывается
   плоское пятно. По умолчанию показывается оригинальный рендер. */

const SKINS = {
  pen:          { w: 46, h: 203.8, top: -1, mode: 'color',    accent: true,  tool: true },
  markerBlue:   { w: 62, h: 172.1, top: 5,  mode: 'multiply', accent: true,  tool: true },
  markerYellow: { w: 68, h: 119.2, top: 9,  mode: 'multiply', accent: true,  tool: true },
  fountain:     { w: 50, h: 158.6, top: 1,  mode: 'multiply', accent: true,  tool: true },
  eraser:       { w: 64, h: 125.4, top: -1 },
  image:        { w: 90, h: 126.0, top: 22 },
  text:         { w: 90, h: 126.0, top: 15, mode: 'color',    accent: true },
  note:         { w: 96, h: 129.1, top: 23 },
};

const DEFAULTS = {
  pen: '#1c1c1c',
  markerBlue: '#5aaef8',
  markerYellow: '#ffab3f',
  fountain: '#69cf36',
  text: '#1c1c1c',
};

const ITEMS = new Map();

function asset(name) {
  return new URL(`assets/skins/${name}`, document.baseURI).href;
}

export function toolSkin(id) {
  const s = SKINS[id];
  if (!s) return '';
  return `<span class="dw-skin-box" style="width:${s.w}px;height:${s.h}px;top:${s.top}px"><canvas class="dw-skin${s.tool ? ' dw-skin-tool' : ''}" data-skin="${id}" aria-hidden="true"></canvas></span>`;
}

function render(rec) {
  const { ctx, cw, ch } = rec;
  if (!ctx || !rec.orig) return;
  ctx.globalCompositeOperation = 'source-over';
  ctx.clearRect(0, 0, cw, ch);
  const custom = rec.accent && rec.base && rec.mask && rec.ink && rec.ink !== DEFAULTS[rec.id];
  ctx.drawImage(custom ? rec.base : rec.orig, 0, 0, cw, ch);
  if (!custom) return;
  const tint = document.createElement('canvas');
  tint.width = cw; tint.height = ch;
  const t = tint.getContext('2d');
  t.fillStyle = rec.ink;
  t.fillRect(0, 0, cw, ch);
  t.globalCompositeOperation = 'destination-in';
  t.drawImage(rec.mask, 0, 0, cw, ch);
  ctx.globalCompositeOperation = rec.mode;
  ctx.drawImage(tint, 0, 0);
  ctx.globalCompositeOperation = 'source-over';
}

export function initToolSkins(toolInk, root = document) {
  const dpr = Math.min(3, Math.max(2, window.devicePixelRatio || 2));
  for (const [id, s] of Object.entries(SKINS)) {
    const canvas = root.querySelector(`canvas[data-skin="${id}"]`);
    if (!canvas) continue;
    const cw = Math.round(s.w * dpr);
    const ch = Math.round(s.h * dpr);
    canvas.width = cw;
    canvas.height = ch;
    const ctx = canvas.getContext('2d');
    if (!ctx) continue;
    ctx.imageSmoothingEnabled = true;
    ctx.imageSmoothingQuality = 'high';
    const rec = {
      id, s, ctx, cw, ch,
      ink: toolInk[id] || DEFAULTS[id] || null,
      mode: s.mode || 'multiply',
      accent: !!s.accent,
      orig: null, base: null, mask: null,
      ready: 0, need: s.accent ? 3 : 1,
    };
    ITEMS.set(id, rec);
    const load = (name, key) => {
      const img = new Image();
      img.decoding = 'async';
      img.onload = () => {
        rec[key] = img;
        rec.ready++;
        if (rec.ready >= rec.need) render(rec);
      };
      img.onerror = () => {
        rec.ready++;
        if (rec.ready >= rec.need) render(rec);
      };
      img.src = asset(name);
    };
    load(`${id}.png`, 'orig');
    if (s.accent) {
      load(`${id}-base.png`, 'base');
      load(`${id}-accent.png`, 'mask');
    }
  }
}

export function setToolSkinColor(id, ink) {
  const rec = ITEMS.get(id);
  if (!rec) return;
  if (rec.ink === ink) return;
  rec.ink = ink;
  if (rec.ready >= rec.need) render(rec);
}


