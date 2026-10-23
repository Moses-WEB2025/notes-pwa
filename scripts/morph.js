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

import { cssMs } from './core.js';

/* Длительность берём из CSS (--dur-3) — одна точка правды со стилями. Если бы
   здесь стояло своё число, при смене тайминга в CSS служебные классы снимались
   бы раньше конца анимации, и в финале был бы виден рывок. Запас settleMs
   покрывает старт (двойной rAF) и возможное опоздание кадра на слабом
   устройстве — снятие стилей всегда попадает в уже законченный переход. */
const dur3 = () => cssMs('--dur-3', 340);
const settleMs = () => dur3() + 80;
/* Момент проявления содержимого — чуть раньше середины роста. */
const revealMs = () => Math.round(dur3() * 0.42);

/* Состояние анимации по каждому экрану. Каждой новой анимации выдаём номер
   поколения: незавершённые колбэки (rAF/setTimeout) предыдущей запоминают свой
   номер и, если экран успел уехать в другую анимацию, молча выходят. Без этого
   при быстром «открыл — закрыл — открыл» отложенные шаги старой анимации
   доигрывались поверх новой (экран дёргался и «возвращалась старая анимация»). */
const phase = new WeakMap();     /* 'open' | 'close' — пока анимация не доехала */
const token = new WeakMap();     /* номер текущего поколения */
const timers = new WeakMap();    /* Set<timeout id> — чтобы отменять по поколению */
const endOff = new WeakMap();    /* снять transitionend предыдущего перехода */

/* Начать новое поколение: гасим все отложенные шаги и слушатели предыдущей
   анимации. */
function begin(el) {
  const off = endOff.get(el); if (off) { off(); endOff.delete(el); }
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

/* Дождаться конца перехода точно: transitionend срабатывает в момент реального
   завершения (учитывает опоздавшие кадры), а таймер страхует от случая, когда
   событие не придёт вовсе (например, элемент потерял слой). */
function onSettled(el, t, fn) {
  let done = false;
  const run = () => {
    if (done || token.get(el) !== t) return;
    done = true;
    const off = endOff.get(el); if (off) { off(); endOff.delete(el); }
    fn();
  };
  const onEnd = e => { if (e.target === el && e.propertyName === 'transform') run(); };
  el.addEventListener('transitionend', onEnd);
  endOff.set(el, () => el.removeEventListener('transitionend', onEnd));
  later(el, t, settleMs(), run);
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

/* Геометрия, которой экран должен совпасть с карточкой.
   В момент тапа карточка «нажата» (bindPress сжимает её до scale(.96)), и
   getBoundingClientRect() вернул бы СЖАТЫЙ прямоугольник — заметка тогда
   вырастала бы не из карточки, а чуть изнутри неё, и в начале анимации был бы
   заметен рывок. Поэтому пересчитываем бокс обратно к «спокойному»:
   размеры берём из offsetWidth/offsetHeight (transform их не меняет), а
   положение — обратным преобразованием относительно transform-origin
   (у .note-card это center bottom). */
function frameOf(card) {
  const r = card.getBoundingClientRect();
  const ow = card.offsetWidth || r.width;
  const s = ow > 0 ? r.width / ow : 1;          /* текущий масштаб (нажатие) */
  const w = s > 0 ? r.width / s : r.width;      /* несжатые размеры */
  const h = s > 0 ? r.height / s : r.height;
  const x = r.left - (1 - s) * w / 2;           /* origin по X — центр… */
  const y = r.top - (1 - s) * h;                /* …по Y — низ карточки */
  return {
    x,
    y,
    sx: Math.max(0.01, w / window.innerWidth),
    sy: Math.max(0.01, h / window.innerHeight),
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
  el.style.backfaceVisibility = '';
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
  el.style.backfaceVisibility = 'hidden';
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
    later(el, t, revealMs(), () => el.classList.remove('morph-hide'));
    onSettled(el, t, () => reset(el));
  });
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
  el.style.backfaceVisibility = 'hidden';
  el.classList.add('morphing');         /* переход включён, геометрия ещё полного экрана */
  void el.offsetWidth;
  /* Скрытие содержимого и старт сжатия — в одном кадре. Если спрятать
     содержимое раньше, мелькнёт пустой экран; если позже (или с фейдом) —
     на уменьшающейся заметке будут видны иконки верхней панели. */
  el.classList.add('morph-hide');
  el.style.transform = `translate3d(${f.x}px, ${f.y}px, 0) scale(${f.sx}, ${f.sy})`;
  el.style.borderRadius = cornerRadius(f);
  onSettled(el, t, () => { reset(el); onEnd && onEnd(); });
}
