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

   • iOS 26+ (Safari и Home Screen web app) — apple-mobile-web-app-status-bar-style
     = black-translucent устарел, и Home Screen web app в принципе не рисует
     контент под статус-баром. Safari сам подкрашивает статус-бар цветом
     background-color у <body>, а с iOS 26 перестал обновлять theme-color
     на лету. Поэтому задаём оба значения сразу.

   Итог: одна точка обновления — applyEdgeColor() — и хуки на каждый переход
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

/* Цвет фона верхнего экрана. Прозрачные экраны берут цвет body. */
function currentScreenColor() {
  const fallback = getComputedStyle(document.body).backgroundColor;
  const el = topScreen();
  if (!el) return fallback;
  const bg = getComputedStyle(el).backgroundColor;
  return isOpaque(bg) ? bg : fallback;
}

/* Покрасить системные зоны в конкретный цвет (CSS-значение). */
export function setEdgeColor(color) {
  if (!isOpaque(color)) return;
  themeMeta().setAttribute('content', color);
  /* body — источник подкраски статус-бара в Safari (iOS 26+), а также фон,
     который виден в зазоре при слайде между экранами. */
  document.body.style.backgroundColor = color;
}

/* Пересчитать цвет по текущему верхнему экрану. */
export function refreshEdgeColor() {
  setEdgeColor(currentScreenColor());
}
