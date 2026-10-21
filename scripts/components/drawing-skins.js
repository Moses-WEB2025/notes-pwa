/* ============ Скины инструментов ============
   Оригинальные слои панели рисования Xiaomi Notes 3.2.7.9.

   Каждый инструмент собран из тех же drawable, что и в приложении:

     doodle_ic_brush_<brush>_body / _head / _shadow_s / _shadow_u
     doodle_ic_tool_<tool>_body / _shadow_s / _shadow_u

   body и тени — готовые рендеры со светотенью; head — alpha-маска
   акцентного слоя (наконечник, точка и полоса на корпусе). В системном
   приложении она заливается активным цветом, поэтому мы делаем то же:
   цвет заменяет RGB маски, а её альфа сохраняет оригинальную светотень.
   Тень _s — у поднятого (выбранного) инструмента, _u — у остальных.

   Полотно drawable (306×547) в системе — это один слот панели: корпус
   инструмента, его тень и поля. Нижний торец у части drawable обрезан
   выше кромки экрана, поэтому корпус и тень продолжаются вниз ровно
   тем же сечением: строка, где силуэт уже прямой (GEO.cut / shS / shU),
   растягивается до низа полотна скина. Шов не виден, потому что над ним
   лежат те же пиксели, что и в начале растяжки, а «хвосты» и скругления
   ниже среза перекрываются этой же растяжкой.

   Полярность полотна сохраняется: 547 px drawable = 156 CSS px, значит
   скин рисуется 1:1 с системным приложением и только масштабируется
   через --dw-z вместе с панелью. */

const NATIVE_H = 547;                       // высота исходного полотна drawable
const CONTENT_H = 156;                      // натуральная высота drawable в CSS px
const K = CONTENT_H / NATIVE_H;             // drawable px -> CSS px
const DESIGN_H = 178;                       // высота панели, под которую свёрстано
const OVERHANG = 44;                        // запас корпуса под нижней кромкой экрана
const SKIN_H = DESIGN_H + OVERHANG;         // полотно скина целиком

/* Геометрия снята с самих PNG (альфа-профиль по строкам):
     x0, x1  — горизонтальная обрезка, симметричная центру корпуса,
               чтобы инструмент стоял ровно по центру своей кнопки.
               По краям добавлено по 18 px drawable: силуэт тени сходит
               на нет плавно, и обрезать его по границе bbox нельзя —
               иначе у соседних инструментов видна вертикальная кромка;
     cut     — последняя строка корпуса, где силуэт ещё прямой и плотный,
               и цвет ровно тот же, что у тела выше (не затемнение торца);
     shS/shU — то же для теней: строка на «плато» силуэта, откуда тень
               продолжается вниз, не теряя плотности. */
const PAD = 18;                              // запас под мягкий край тени
const GEO = {
  pen:          { res: 'pencil',       accent: true,  ink: '#1c1c1c', fade: 50, x0: -22, x1: 194, cut: 470, shS: 514, shU: 506 },
  markerBlue:   { res: 'watercolor',   accent: true,  ink: '#5aaef8', x0: -24, x1: 192, cut: 545, shS: 515, shU: 514 },
  markerYellow: { res: 'markpen',      accent: true,  ink: '#ffab3f', x0: -37, x1: 207, cut: 479, shS: 524, shU: 515 },
  fountain:     { res: 'pen',          accent: true,  ink: '#69cf36', x0: -14, x1: 184, cut: 500, shS: 525, shU: 514 },
  eraser:       { res: 'tool_eraser',                               x0: -68, x1: 242, cut: 455, shS: 484, shU: 514 },
  image:        { res: 'tool_picture',                              x0: -90, x1: 314, cut: 525, shS: 506, shU: 513 },
  text:         { res: 'tool_text',                                 x0: -87, x1: 311, cut: 525, shS: 506, shU: 513 },
  note:         { res: 'tool_background',                           x0: -87, x1: 311, cut: 525, shS: 506, shU: 513 },
};

const ITEMS = new Map();

function asset(name) {
  return new URL(`assets/original/${name}`, document.baseURI).href;
}

/* Ширина слота в CSS px — по ней кнопка держит свой размер. */
function skinWidth(g) {
  return (g.x1 - g.x0) * K;
}

export function toolSkin(id) {
  const g = GEO[id];
  if (!g) return '';
  return `<span class="dw-skin-box" style="--sk-w:${skinWidth(g).toFixed(2)}px;--sk-h:${SKIN_H}px">` +
    `<canvas class="dw-skin" data-skin="${id}" aria-hidden="true"></canvas></span>`;
}

/* Средняя альфа по строкам: полотно сжимается в 1 px по ширине, поэтому
   каждый пиксель такой полоски — осреднённый цвет своей строки. Нужно,
   чтобы найти «плато» силуэта и не взять для продолжения строку из
   зоны затухания у нижней кромки drawable. */
function rowAlpha(img) {
  const c = document.createElement('canvas');
  c.width = 1;
  c.height = img.height;
  const x = c.getContext('2d', { willReadFrequently: true });
  if (!x) return null;
  x.drawImage(img, 0, 0, img.width, img.height, 0, 0, 1, img.height);
  try { return x.getImageData(0, 0, 1, img.height).data; } catch (e) { return null; }
}

/* Последняя строка среза, ещё лежащая на плато: у самого низа drawable
   альфа падает, и растяжка из такой строки вышла бы бледнее детали над
   ней — та самая «перемычка» в тени. */
function plateauRow(img, cut) {
  const a = rowAlpha(img);
  if (!a) return Math.max(1, cut - 4);
  let max = 0;
  for (let y = 0; y <= cut; y++) max = Math.max(max, a[y * 4 + 3]);
  if (!max) return Math.max(1, cut - 4);
  let y = cut;
  while (y > 1 && a[y * 4 + 3] < max * 0.985) y--;
  return y;
}

/* Продолжение слоя вниз: одна строка сечения «плато», разложенная по
   столбцам. Цвет и альфа в каждом столбце постоянны, поэтому ниже среза
   нет ни градиента, ни шва: корпус продолжается своим цветом, тень —
   сплошная, без разрывов. */
function paintStrip(ctx, rec, img, cut) {
  const row = plateauRow(img, cut);
  const line = document.createElement('canvas');
  line.width = img.width;
  line.height = 1;
  const lx = line.getContext('2d', { willReadFrequently: true });
  if (!lx) return;
  lx.drawImage(img, 0, row, img.width, 1, 0, 0, img.width, 1);
  let px;
  try { px = lx.getImageData(0, 0, img.width, 1).data; } catch (e) { return; }

  const { cw, ch, dpr, g } = rec;
  ctx.setTransform(1, 0, 0, 1, 0, 0);
  const top = Math.round(cut * K * dpr);
  const h = ch - top;
  if (h <= 0) { rec.setTransform(); return; }
  for (let xd = 0; xd < cw; xd++) {
    const sx = Math.round((xd + 0.5) / dpr / K + g.x0);
    if (sx < 0 || sx >= img.width) continue;
    const o = sx * 4;
    const a = px[o + 3];
    if (!a) continue;
    ctx.fillStyle = `rgba(${px[o]},${px[o + 1]},${px[o + 2]},${(a / 255).toFixed(3)})`;
    ctx.fillRect(xd, top, 1.05, h);
  }
  rec.setTransform();
}

/* Слой до среза. Под срезом исходные пиксели не рисуются вовсе: иначе
   скругление торца или узкий «хвост» drawable проступали бы поверх
   продолжения из полос. */
function drawBody(ctx, rec, img, cut) {
  const edge = cut * K + 0.4;
  ctx.save();
  ctx.beginPath();
  ctx.rect(0, 0, rec.CW, edge);
  ctx.clip();
  ctx.drawImage(img, 0, 0, img.width * K, img.height * K);
  ctx.restore();
  paintStrip(ctx, rec, img, cut);
}

/* Тень строится так же, как корпус: слой до среза плюс сплошная полоса.
   Раньше ниже среза клалась растяжка из двух строк у самой кромки, и на
   затухании тень распадалась на куски — теперь сечение одно и то же. */
function drawShadow(ctx, rec, img, cut) {
  drawBody(ctx, rec, img, cut);
}

/* Акцентный слой: цвет заливается в маску head, светотень остаётся.
   У карандаша (fade) верхушка грифеля остаётся плотной, а к выходу из
   дерева цвет светлеет: ровная чёрная «шапка» выглядела темнее корпуса,
   хотя у настоящего карандаша грифель уходит в дерево постепенно. */
function paintHead(rec) {
  const { cw, ch, imgs, tctx, tint, ink, dpr, g } = rec;
  if (!imgs.head || !ink) return;
  tctx.setTransform(1, 0, 0, 1, 0, 0);
  tctx.globalCompositeOperation = 'source-over';
  tctx.clearRect(0, 0, cw, ch);
  tctx.fillStyle = ink;
  tctx.fillRect(0, 0, cw, Math.round(CONTENT_H * dpr));
  tctx.globalCompositeOperation = 'destination-in';
  tctx.drawImage(imgs.head, -rec.g.x0 * K * dpr, 0, imgs.head.width * K * dpr, CONTENT_H * dpr);
  if (g.fade) {
    const h = Math.max(1, g.fade * K * dpr);
    const grad = tctx.createLinearGradient(0, 0, 0, h);
    grad.addColorStop(0, 'rgba(255,255,255,0)');
    grad.addColorStop(0.55, 'rgba(255,255,255,0.10)');
    grad.addColorStop(1, 'rgba(255,255,255,0.42)');
    tctx.globalCompositeOperation = 'source-atop';
    tctx.fillStyle = grad;
    tctx.fillRect(0, 0, cw, h);
  }
  tctx.globalCompositeOperation = 'source-over';
  rec.ctx.setTransform(1, 0, 0, 1, 0, 0);
  rec.ctx.drawImage(tint, 0, 0);
  rec.setTransform();
}

function render(rec) {
  const { ctx, cw, ch, imgs, g } = rec;
  if (!ctx) return;
  ctx.setTransform(1, 0, 0, 1, 0, 0);
  ctx.clearRect(0, 0, cw, ch);
  rec.setTransform();
  const shadow = rec.active ? imgs.shadowS : imgs.shadowU;
  if (shadow) drawShadow(ctx, rec, shadow, rec.active ? g.shS : g.shU);
  if (imgs.body) drawBody(ctx, rec, imgs.body, g.cut);
  if (g.accent) paintHead(rec);
}

export function initToolSkins(toolInk, root = document) {
  const dpr = Math.min(3, Math.max(2, window.devicePixelRatio || 2));
  for (const [id, g] of Object.entries(GEO)) {
    const canvas = root.querySelector(`canvas[data-skin="${id}"]`);
    if (!canvas) continue;
    const CW = skinWidth(g);
    const cw = Math.round(CW * dpr);
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
      id, g, ctx, cw, ch, dpr, CW,
      setTransform: () => ctx.setTransform(dpr, 0, 0, dpr, -g.x0 * K * dpr, 0),
      ink: toolInk[id] || g.ink || null,
      active: false,
      imgs: { body: null, shadowS: null, shadowU: null, head: null },
      tint, tctx: tint.getContext('2d'),
      ready: 0,
      need: g.accent ? 4 : 3,
    };
    ITEMS.set(id, rec);

    const load = (suffix, key) => {
      const img = new Image();
      img.decoding = 'async';
      const done = () => { rec.ready++; if (rec.ready >= rec.need) render(rec); };
      img.onload = done;
      img.onerror = done;
      img.src = asset(`${g.res}-${suffix}.png`);
      rec.imgs[key] = img;
    };
    load('shadow-u', 'shadowU');
    load('shadow-s', 'shadowS');
    load('body', 'body');
    if (g.accent) load('head', 'head');
  }
}

export function setToolSkinColor(id, ink) {
  const rec = ITEMS.get(id);
  if (!rec || !rec.g.accent || !ink || rec.ink === ink) return;
  rec.ink = ink;
  if (rec.ready >= rec.need) render(rec);
}

export function setToolSkinActive(id, active) {
  const rec = ITEMS.get(id);
  if (!rec || rec.active === active) return;
  rec.active = active;
  if (rec.ready >= rec.need) render(rec);
}
