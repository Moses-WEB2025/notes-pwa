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

/* Состояние анимации по каждому экрану. Каждой новой анимации выдаём номер
   поколения: незавершённые колбэки (rAF/setTimeout) предыдущей запоминают свой
   номер и, если экран успел уехать в другую анимацию, молча выходят. Без этого
   при быстром «открыл — закрыл — открыл» отложенные шаги старой анимации
   доигрывались поверх новой (экран дёргался и «возвращалась старая анимация»). */
const phase = new WeakMap();     /* 'open' | 'close' — пока анимация не доехала */
const token = new WeakMap();     /* номер текущего поколения */
const timers = new WeakMap();    /* Set<timeout id> — чтобы отменять по поколению */

/* Начать новое поколение: гасим все отложенные шаги предыдущей анимации. */
function begin(el) {
  const set = timers.get(el);
  if (set) { set.forEach(clearTimeout); set.clear(); }
  const t = (token.get(el) || 0) + 1;
  token.set(el, t);
  return t;
}

/* setTimeout, живущий только пока актуально его поколение. */
function later(el, t, ms, fn) {
  const id = setTimeout(() => {
    const s = timers.get(el); if (s) s.delete(id);
    if (token.get(el) !== t) return;
    fn();
  }, ms);
  let s = timers.get(el); if (!s) { s = new Set(); timers.set(el, s); }
  s.add(id);
}

/* requestAnimationFrame с той же проверкой поколения. */
function frame(el, t, fn) {
  requestAnimationFrame(() => { if (token.get(el) === t) fn(); });
}

/* Системная настройка «уменьшить движение» — тогда морфинг не играем. */
export function morphDisabled() {
  return window.matchMedia('(prefers-reduced-motion: reduce)').matches;
}

/* Идёт ли морфинг этого экрана ('open'/'close'), иначе null.
   Нужно редактору: при быстром закрытии нельзя закрывать экран слайдом
   поверх незаконченного морфинга — вместо этого морфинг разворачиваем. */
export function morphPhase(el) {
  return phase.get(el) || null;
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
  phase.delete(el);
  el.classList.remove('morphing', 'morph-hide', 'morph-fade');
  el.style.transformOrigin = '';
  el.style.transform = '';
  el.style.borderRadius = '';
  el.style.willChange = '';
}

/* Вырастить экран из карточки. el уже добавлен в DOM.
   Повторный вызов на том же экране (в т.ч. если он ещё сжимался) отменяет
   предыдущую анимацию и стартует заново с геометрии карточки. */
export function morphOpen(el, card) {
  const t = begin(el);
  phase.set(el, 'open');
  /* Раскрытием содержимого управляет только морфинг: снимаем собственную
     анимацию входа у шапки редактора, иначе она раскрывает верхнюю панель
     отдельно от тела окна (см. .morph-flat в theme.css). */
  el.classList.add('morph-flat');
  const f = frameOf(card);
  el.classList.remove('morphing');      /* без перехода — задаём старт вручную */
  el.style.transformOrigin = '0 0';
  el.style.willChange = 'transform';
  el.style.transform = `translate3d(${f.x}px, ${f.y}px, 0) scale(${f.sx}, ${f.sy})`;
  el.style.borderRadius = cornerRadius(f);
  /* Содержимое скрыто с ПЕРВОГО кадра и меняет прозрачность с переходом
     (morph-fade). Сам класс скрытия снимем позже — тогда содержимое проявится
     плавно, а не рывком. */
  el.classList.add('morph-hide', 'morph-fade');
  void el.offsetWidth;                  /* фиксируем стартовое состояние */
  frame(el, t, () => {
    el.classList.add('morphing');       /* теперь включаем переход */
    void el.offsetWidth;
    el.style.transform = 'translate3d(0px, 0px, 0) scale(1, 1)';
    el.style.borderRadius = '0px';
    later(el, t, REVEAL, () => el.classList.remove('morph-hide'));
  });
  later(el, t, SETTLE, () => reset(el));
}

/* Сжать экран обратно в карточку. onEnd — когда анимация закончилась
   (там экран и убирается). Если открытие ещё не доехало, оно отменяется,
   и экран плавно возвращается в карточку с текущего места. */
export function morphClose(el, card, onEnd) {
  const t = begin(el);
  phase.set(el, 'close');
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
  later(el, t, SETTLE, () => { reset(el); onEnd && onEnd(); });
}

export { DUR };
