/* ============ Прокрутка «как на iPhone»: упругая протяжка на границах ============
   Нативные списки в мобильном web-view едут с инерцией, но у края «упираются»
   в стену. На iPhone прокрутка в этот момент продолжается ещё немного и
   пружиной возвращается назад. Здесь повторяем это поведение: пока палец тянет
   страницу за верхний или нижний край, содержимое следует за ним с растущим
   сопротивлением (эффект «резинки»), а после отпускания мягко доезжает назад.

   Протяжка включается в тот момент, когда жест доходит до края — даже если это
   случилось уже посреди обычной прокрутки. Отдельного «дотягивающего» касания
   не требуется.

   Работает за счёт CSS-переменной --pull-y на скроллере (см. styles/theme.css
   и styles/screens.css): сама нативная инерция и плавность не подменяются —
   трогаем только протяжку за границей, поэтому обычная прокрутка остаётся
   такой же быстрой и плавной, как в браузере. */

const PULL_SELECTOR = '.scroll, .editor-body';
/* Порог, после которого определяем направление жеста (вертикаль/горизонталь) */
const AXIS_LOCK = 8;
/* Предел протяжки относительно высоты контейнера (насколько далеко уедет край) */
const MAX_PULL_RATIO = 0.6;

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

/* Возврат пружиной к нулю: лёгкий перелёт и мягкое затухание, как у iOS. */
function springBack(el, from) {
  cancelSpring(el);
  let x = from, v = 0, last = performance.now();
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
    const atTop = el.scrollTop <= 0;
    const atBottom = el.scrollTop + el.clientHeight >= el.scrollHeight - 1;
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
}
