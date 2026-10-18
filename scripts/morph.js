/* ============ Морфинг: заметка «растёт» из карточки в экран и обратно ============
   Ручной FLIP-переход. Экран редактора — один композитный слой: ведём только
   его transform (translate + scale) от прямоугольника нажатой карточки до
   полного экрана. Содержимое на время перехода гасим прозрачностью, поэтому
   растянутого текста не видно — наружу растёт только цветная поверхность.

   Почему transform, а не width/height: transform доводится композитором и не
   перекладывает дерево каждый кадр, поэтому на iOS переход плавный.
   Скругление углов компенсируем обратным масштабом ПО КАЖДОЙ ОСИ отдельно:
   при неоднородном сжатии один общий радиус превращался в широкий эллипс
   (60×20 вместо 20×20) — из-за этого переход выглядел «кривым». */

const DUR = 300;      /* длительность перехода — как у слайда (--dur-3) */
const SETTLE = 340;   /* когда снимаем служебные стили / убираем экран */
const REVEAL = 140;   /* когда проявляем содержимое растущего экрана */

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

/* Геометрия, которой экран должен совпасть с карточкой. */
function frameOf(card) {
  const r = card.getBoundingClientRect();
  return {
    x: r.left,
    y: r.top,
    sx: Math.max(0.01, r.width / window.innerWidth),
    sy: Math.max(0.01, r.height / window.innerHeight),
    radius: radiusOf(card),
  };
}

/* Радиус по осям: под сжатием сплющился бы — делим каждую ось на её масштаб,
   тогда на экране угол остаётся круглым, как у карточки. */
function cornerRadius(f) {
  return `${(f.radius / f.sx).toFixed(1)}px / ${(f.radius / f.sy).toFixed(1)}px`;
}

function reset(el) {
  el.classList.remove('morphing', 'morph-hide', 'morph-fade');
  el.style.transformOrigin = '';
  el.style.transform = '';
  el.style.borderRadius = '';
  el.style.willChange = '';
}

/* Вырастить экран из карточки. el уже добавлен в DOM. */
export function morphOpen(el, card) {
  const f = frameOf(card);
  el.style.transformOrigin = '0 0';
  el.style.willChange = 'transform';
  el.style.transform = `translate3d(${f.x}px, ${f.y}px, 0) scale(${f.sx}, ${f.sy})`;
  el.style.borderRadius = cornerRadius(f);
  /* Содержимое скрыто с ПЕРВОГО кадра и меняет прозрачность с переходом
     (morph-fade). Сам класс скрытия снимем позже — тогда содержимое проявится
     плавно, а не рывком. */
  el.classList.add('morph-hide', 'morph-fade');
  void el.offsetWidth;                      /* фиксируем стартовое состояние */
  requestAnimationFrame(() => {
    el.classList.add('morphing');           /* теперь включаем переход */
    void el.offsetWidth;
    el.style.transform = 'translate3d(0px, 0px, 0) scale(1, 1)';
    el.style.borderRadius = '0px';
    setTimeout(() => el.classList.remove('morph-hide'), REVEAL);
  });
  setTimeout(() => reset(el), SETTLE);
}

/* Сжать экран обратно в карточку. onEnd — когда анимация закончилась
   (там экран и убирается). */
export function morphClose(el, card, onEnd) {
  const f = frameOf(card);
  el.style.transformOrigin = '0 0';
  el.style.willChange = 'transform';    /* слой готовим до старта перехода */
  el.classList.add('morphing');         /* переход включён, геометрия ещё полного экрана */
  void el.offsetWidth;
  /* Скрытие содержимого и старт сжатия — в одном кадре. Если спрятать
     содержимое раньше, мелькнёт пустой экран; если позже (или с фейдом) —
     на уменьшающейся заметке будут видны иконки верхней панели. */
  el.classList.add('morph-hide');
  el.style.transform = `translate3d(${f.x}px, ${f.y}px, 0) scale(${f.sx}, ${f.sy})`;
  el.style.borderRadius = cornerRadius(f);
  setTimeout(() => { reset(el); onEnd && onEnd(); }, SETTLE);
}

export { DUR };
