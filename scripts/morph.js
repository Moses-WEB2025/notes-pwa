/* ============ Морфинг: заметка «растёт» из карточки в экран и обратно ============
   Ручной FLIP-переход. Экран редактора — один композитный слой: анимируем
   только его transform (translate + scale) от прямоугольника нажатой карточки
   до полного экрана. Содержимое на время роста прозрачно, поэтому растянутого
   текста не видно — наружу «растёт» только цветная поверхность заметки.

   Почему transform, а не width/height или clip-path: transform доводится
   композитором и не перерисовывает дерево каждый кадр, поэтому на iOS переход
   плавный. Скругление углов компенсируем обратным масштабом и уводим в ноль. */

const DUR = 300;      /* длительность перехода — как у слайда (--dur-3) */
const SETTLE = 340;   /* когда снимаем служебные стили / убираем экран */

/* Системная настройка «уменьшить движение» — тогда морфинг не играем. */
export function morphDisabled() {
  return window.matchMedia('(prefers-reduced-motion: reduce)').matches;
}

/* Видна ли карточка: морфинг к уехавшей за экран карточке выглядел бы
   странно, а её координаты не имеют смысла. */
export function cardOnScreen(card) {
  const r = card.getBoundingClientRect();
  return r.width > 0 && r.height > 0 && r.bottom > 0 && r.top < window.innerHeight;
}

function radiusOf(el) {
  const v = parseFloat(getComputedStyle(el).borderTopLeftRadius);
  return Number.isFinite(v) ? v : 0;
}

/* Насколько сжать полный экран, чтобы он лёг точно на карточку. */
function frameFor(rect, radius) {
  const vw = window.innerWidth, vh = window.innerHeight;
  return {
    x: rect.left,
    y: rect.top,
    sx: Math.max(0.01, rect.width / vw),
    sy: Math.max(0.01, rect.height / vh),
    radius,
  };
}

function applyFrame(el, f) {
  el.style.transformOrigin = '0 0';
  el.style.transform = `translate(${f.x}px, ${f.y}px) scale(${f.sx}, ${f.sy})`;
  /* Под сжатием радиус сплющился бы по осям — берём по меньшей и делим на неё. */
  el.style.borderRadius = (f.radius / Math.min(f.sx, f.sy)) + 'px';
}

function clearFrame(el) {
  el.style.transformOrigin = '';
  el.style.transform = '';
  el.style.borderRadius = '';
}

/* Содержимое экрана на время перехода скрываем: иначе при сжатии был бы
   виден растянутый текст. */
function hideChildren(el) {
  const kids = [...el.children];
  kids.forEach(k => { k.style.transition = 'opacity 180ms ease'; k.style.opacity = '0'; });
  return kids;
}
function clearChildren(kids) {
  kids.forEach(k => { k.style.transition = ''; k.style.opacity = ''; });
}

/* Вырастить экран из карточки. el уже добавлен в DOM. */
export function morphOpen(el, card) {
  const kids = hideChildren(el);
  const f = frameFor(card.getBoundingClientRect(), radiusOf(card));
  applyFrame(el, f);                        /* стартовая геометрия — ещё без перехода */
  el.classList.add('morphing');             /* теперь включаем переход */
  void el.offsetWidth;                      /* фиксируем стартовое состояние */
  requestAnimationFrame(() => {
    el.style.transform = 'translate(0px, 0px) scale(1, 1)';
    el.style.borderRadius = '0px';
    /* Содержимое проявляем, когда поверхность уже почти развернулась. */
    setTimeout(() => { kids.forEach(k => { k.style.opacity = ''; }); }, 110);
  });
  setTimeout(() => {
    el.classList.remove('morphing');
    clearFrame(el);
    clearChildren(kids);
  }, SETTLE);
}

/* Сжать экран обратно в карточку. onEnd — когда анимация закончилась
   (там экран и убирается). */
export function morphClose(el, card, onEnd) {
  const kids = [...el.children];
  const f = frameFor(card.getBoundingClientRect(), radiusOf(card));
  el.style.transformOrigin = '0 0';
  el.style.transform = 'translate(0px, 0px) scale(1, 1)';
  el.style.borderRadius = '0px';
  kids.forEach(k => { k.style.transition = 'opacity 150ms ease'; });
  el.classList.add('morphing');             /* переход — уже от полного экрана */
  void el.offsetWidth;
  requestAnimationFrame(() => {
    kids.forEach(k => { k.style.opacity = '0'; });   /* содержимое гаснет, экран сжимается */
    applyFrame(el, f);
  });
  setTimeout(() => { onEnd && onEnd(); }, SETTLE);
}

export { DUR };
