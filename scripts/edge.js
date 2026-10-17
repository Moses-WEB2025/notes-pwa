/* ============ Системные зоны экрана (edge-to-edge) ============
   Задача: статус-бар сверху и область жестов/навигации снизу остаются
   видимыми (системные элементы не скрываем), а их фон совпадает с текущим
   экраном и меняется при переходе.

   Что реально возможно в PWA-браузерах (проверено по документации и
   баг-трекерам движков, 2026):

   • Android (Chrome, в браузере и в установленной PWA) — цвет верхней
     системной полосы задаёт <meta name="theme-color">. Нижнюю
     навигационную панель из веба задать нельзя: в Android 15+ её цвет
     принудительно прозрачный. Значит фон экрана должен просто рисоваться
     под неё (viewport-fit=cover), а контент — отступать через
     env(safe-area-inset-*).

   • iOS 26+ (Safari и Home Screen web app) — theme-color больше не читается.
     Safari сам подбирает цвет по background-color ближайшего position:fixed
     элемента у верхнего/нижнего края вьюпорта (у нас это body), поэтому
     красим и html, и body. Контент под системной полосой Safari не рисует —
     это его собственная хрома, стилями не убирается; поэтому лифтуемые
     элементы не заводим под safe-area (см. --search-top в main.css).

   Итог: одна точка обновления — setEdgeColor() — и хуки на каждый переход
   экрана / смену его фона. */

/* Верхний экран — тот же критерий, что и у Nav.screens (без импорта core.js,
   чтобы не плодить циклические зависимости). */
function topScreen() {
  return document.querySelector('#screens-root > .screen:last-child');
}

function themeMeta() {
  let m = document.querySelector('meta[name="theme-color"]');
  if (!m) {
    m = document.createElement('meta');
    m.name = 'theme-color';
    document.head.appendChild(m);
  }
  return m;
}

function isOpaque(color) {
  return !!color && color !== 'transparent' && color !== 'rgba(0, 0, 0, 0)';
}

/* Базовый фон приложения (--bg). Именно его берём, когда у экрана нет своего
   фона: body мы сами перекрашиваем, поэтому брать цвет из body нельзя. */
function appBgColor() {
  const v = getComputedStyle(document.documentElement).getPropertyValue('--bg').trim();
  return v || '#f7f7f7';
}

/* Цвет конкретного экрана; прозрачный — базовый фон приложения. */
function colorOf(el) {
  if (el) {
    const bg = getComputedStyle(el).backgroundColor;
    if (isOpaque(bg)) return bg;
  }
  return appBgColor();
}

/* Покрасить системные зоны в конкретный цвет (CSS-значение). */
export function setEdgeColor(color) {
  if (!isOpaque(color)) return;
  /* 1) Android (Chrome — и браузер, и установленная PWA): верхнюю системную
        полосу красит <meta name="theme-color">. */
  themeMeta().setAttribute('content', color);
  /* 2) iOS 26+ / Safari: theme-color там больше не читается. Safari сам
        подбирает цвет по background-color ближайшего position:fixed элемента
        у верхнего и нижнего края экрана. body у нас position:fixed, поэтому
        красим html + body — это же фон, видимый в зазоре при слайде экранов. */
  document.documentElement.style.backgroundColor = color;
  document.body.style.backgroundColor = color;
}

/* Перекрасить системные зоны по конкретному экрану. */
export function refreshEdgeColorFor(el) {
  setEdgeColor(colorOf(el));
}

/* Пересчитать цвет по текущему верхнему экрану. */
export function refreshEdgeColor() {
  refreshEdgeColorFor(topScreen());
}
