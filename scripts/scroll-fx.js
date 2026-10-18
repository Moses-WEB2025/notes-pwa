/* ============ Прокрутка «как на iPhone»: упругая протяжка на границах ============
   Нативные списки в мобильном web-view едут с инерцией, но у края «упираются»
   в стену. На iPhone прокрутка в этот момент продолжается ещё немного и
   пружиной возвращается назад. Здесь повторяем это поведение: пока палец тянет
   страницу за верхний или нижний край, содержимое следует за ним с растущим
   сопротивлением (эффект «резинки»), а после отпускания мягко доезжает назад.

   Протяжка включается в тот момент, когда жест доходит до края — даже если это
   случилось уже посреди обычной прокрутки. Отдельного «дотягивающего» касания
   не требуется.

   Отдельно обрабатываем инерцию: если палец отпущен, а список доезжает до края
   по инерции (бросок), touchmove уже не приходит — и включить протяжку некому,
   поэтому у края список резко встаёт. В этом случае следим за scroll и запускаем
   пружину со скоростью броска в момент удара о край (см. onScroll).

   Работает за счёт CSS-переменной --pull-y на скроллере (см. styles/theme.css
   и styles/screens.css): сама нативная инерция и плавность не подменяются —
   трогаем только протяжку за границей, поэтому обычная прокрутка остаётся
   такой же быстрой и плавной, как в браузере. */

const PULL_SELECTOR = '.scroll, .editor-body';
/* Порог, после которого определяем направление жеста (вертикаль/горизонталь) */
const AXIS_LOCK = 8;
/* Предел протяжки относительно высоты контейнера (насколько далеко уедет край) */
const MAX_PULL_RATIO = 0.6;
/* Бросок по инерции: ниже этого порога считаем, что список просто довели
   пальцем, а не «швырнули» — пружину не запускаем. Верхний предел ограничивает
   «вылет» края, чтобы он не уезжал слишком далеко. */
const FLING_MIN = 350;   /* px/s */
const FLING_MAX = 2600;  /* px/s */

let drag = null;

/* Палец на поле ввода или выделяемом тексте — это работа с текстом, а не
   прокрутка: такую протяжку не перехватываем, чтобы не мешать выделению. */
function isTextTarget(t) {
  return !!(t && t.closest && t.closest('input, textarea, [contenteditable], .note-content-input'));
}

function reducedMotion() {
  return !!(window.matchMedia && window.matchMedia('(prefers-reduced-motion: reduce)').matches);
}

/* Затухающая протяжка: в начале следует за пальцем почти один в один, а чем
   дальше — тем сильнее сопротивление. Так край «не улетает» бесконечно. */
function resist(x, dim) {
  const max = Math.max(80, dim * MAX_PULL_RATIO);
  return max * (1 - Math.exp(-x / max));
}

function setPull(el, y) {
  el.style.setProperty('--pull-y', y.toFixed(2) + 'px');
}

function cancelSpring(el) {
  if (el.__pullSpring) { cancelAnimationFrame(el.__pullSpring); el.__pullSpring = 0; }
}

/* Мгновенно вернуть край на место (например, при новом касании). */
function resetPull(el) {
  cancelSpring(el);
  el.style.removeProperty('--pull-y');
  el.style.transition = '';
  el.style.willChange = '';
}

/* Возврат пружиной к нулю: лёгкий перелёт и мягкое затухание, как у iOS.
   v0 — начальная скорость (px/s): при протяжке пальцем её нет, а при ударе о
   край по инерции пружина стартует со скоростью броска и край «выстреливает». */
function springBack(el, from, v0 = 0) {
  cancelSpring(el);
  let x = from, v = v0, last = performance.now();
  const K = 190;  /* жёсткость */
  const C = 25;   /* демпфирование: чуть меньше критического — есть перелёт */
  const step = (now) => {
    const dt = Math.min(0.032, (now - last) / 1000);
    last = now;
    if (dt <= 0) { el.__pullSpring = requestAnimationFrame(step); return; }
    v += (-K * x - C * v) * dt;
    x += v * dt;
    if (Math.abs(x) < 0.25 && Math.abs(v) < 4) {
      el.style.removeProperty('--pull-y');
      el.style.transition = '';
      el.style.willChange = '';
      el.__pullSpring = 0;
      return;
    }
    setPull(el, x);
    el.__pullSpring = requestAnimationFrame(step);
  };
  el.__pullSpring = requestAnimationFrame(step);
}

function beginPull(el) {
  cancelSpring(el);
  /* На время протяжки отключаем CSS-переходы: иначе край «догонял» бы палец. */
  el.style.transition = 'none';
  el.style.willChange = 'transform';
}

function endDrag() {
  if (!drag) return;
  const d = drag; drag = null;
  if (!d.engaged) return;
  const cur = parseFloat(d.el.style.getPropertyValue('--pull-y')) || 0;
  springBack(d.el, cur);
}

/* Следим за положением края и скоростью прокрутки.
   1) Обновляем флаги «у верхнего/нижнего края» — по ним touchmove решает,
      включать ли протяжку: на iOS scrollTop в момент жеста может отставать.
   2) Если палец уже отпущен (нет drag) и список по инерции доезжает до края,
      запускаем пружину со скоростью броска — растяжка появляется ровно в
      момент достижения конца, без отдельного повторного скролла. */
function onScroll(e) {
  const el = e.target;
  if (!el || !el.classList || !el.matches || !el.matches(PULL_SELECTOR)) return;
  const top = el.scrollTop;
  el.__sfAtTop = top <= 0;
  el.__sfAtBottom = top + el.clientHeight >= el.scrollHeight - 1.5;

  const now = performance.now();
  const prevTop = el.__sfTop, prevT = el.__sfTime;
  el.__sfTop = top; el.__sfTime = now;

  if (drag || reducedMotion() || el.__pullSpring) return;
  if (prevTop == null) return;
  const dt = (now - prevT) / 1000;
  if (dt <= 0 || dt > 0.1) return;         /* пауза перед этим кадром — не скорость */
  const v = (top - prevTop) / dt;          /* > 0 — движемся к нижнему краю */
  const speed = Math.abs(v);
  if (speed < FLING_MIN) return;
  /* Направление «вылета»: снизу контент уходит вверх (−), сверху — вниз (+). */
  let dir = 0;
  if (el.__sfAtBottom && v > 0) dir = -1;
  else if (el.__sfAtTop && v < 0) dir = 1;
  if (!dir) return;
  beginPull(el);
  springBack(el, 0, dir * Math.min(speed, FLING_MAX));
}

function onTouchStart(e) {
  if (drag || e.touches.length !== 1 || reducedMotion()) return;
  const el = e.target.closest ? e.target.closest(PULL_SELECTOR) : null;
  if (!el || isTextTarget(e.target)) return;
  /* Новое касание — прерываем возможный незавершённый возврат. */
  if (el.__pullSpring) resetPull(el);
  const t = e.touches[0];
  drag = {
    el, sx: t.clientX, sy: t.clientY,
    startTop: el.scrollTop,   /* от какой позиции начался жест */
    decided: false, engaged: false, horizontal: false,
  };
}

function onTouchMove(e) {
  if (!drag || e.touches.length !== 1) return;
  const t = e.touches[0];
  const dx = t.clientX - drag.sx;
  const dy = t.clientY - drag.sy;

  if (!drag.decided) {
    if (Math.abs(dx) < AXIS_LOCK && Math.abs(dy) < AXIS_LOCK) return;
    drag.decided = true;
    /* Горизонтальный жест (свайп «назад», прокрутка чипов) — не наш. */
    if (Math.abs(dy) <= Math.abs(dx)) { drag.horizontal = true; return; }
  }
  if (drag.horizontal) return;

  const el = drag.el;

  /* Край проверяем на каждом кадре, а не один раз в начале жеста: список может
     доехать до края уже в процессе обычной прокрутки, и «резинка» должна
     включиться сразу, без второго касания. Пока до края не дошли — не мешаем
     браузеру и просто отдаём ему прокрутку. */
  if (!drag.engaged) {
    /* Флаги с scroll-события подстраховывают: на iOS scrollTop в момент жеста
       может отставать на кадр, и прямое сравнение не заметило бы край. */
    const atTop = el.scrollTop <= 0 || el.__sfAtTop;
    const atBottom = el.scrollTop + el.clientHeight >= el.scrollHeight - 1 || el.__sfAtBottom;
    let edge = null;
    if (atTop && dy > 0) edge = 'top';
    else if (atBottom && dy < 0) edge = 'bottom';
    if (!edge) return;
    drag.engaged = true;
    drag.edge = edge;
    /* Точка отсчёта протяжки. Если список не двигался с начала жеста — считаем
       от старта (резинка отвечает с первого пикселя). Если до края доехали в
       процессе прокрутки — отсчёт берём от текущего положения, иначе край
       прыгнул бы сразу на всю пройденную дистанцию. */
    drag.baseY = Math.abs(el.scrollTop - drag.startTop) < 2 ? drag.sy : t.clientY;
    beginPull(el);
  }

  /* Гасим нативную протяжку и ведём край за пальцем с сопротивлением.
     Обратный ход за точку отсчёта не пускаем: у верхнего края край ходит
     только вниз, у нижнего — только вверх. */
  e.preventDefault();
  const dim = el.clientHeight || 400;
  const pull = drag.edge === 'top' ? t.clientY - drag.baseY : drag.baseY - t.clientY;
  const mag = pull > 0 ? resist(pull, dim) : 0;
  setPull(el, drag.edge === 'top' ? mag : -mag);
}

/* Подключаем обработчики на документ: так жесты работают и на экранах,
   которые создаются позже (папки, корзина, редактор). */
export function initScrollFx() {
  document.addEventListener('touchstart', onTouchStart, { passive: true });
  document.addEventListener('touchmove', onTouchMove, { passive: false });
  document.addEventListener('touchend', endDrag, { passive: true });
  document.addEventListener('touchcancel', endDrag, { passive: true });
  /* scroll не всплывает — ловим на фазе перехвата. Нужен для протяжки по
     инерции (см. onScroll) и для флагов «у края». */
  document.addEventListener('scroll', onScroll, { capture: true, passive: true });
}
