/* ============ Точка входа ============ */
import { initKeyboard, bindPress, Store, Nav } from './core.js';
import { refreshEdgeColor } from './edge.js';
import { mainScreen } from './main-screen.js';
import { seedDemo } from './seed.js';
import { initScrollFx } from './scroll-fx.js';

initKeyboard();
initScrollFx();

(async () => {
  const notes = await Store.notes();
  if (!notes.length) {
    await seedDemo();   // демо-заметки как на скриншоте (только при первом запуске)
  }
  const ms = mainScreen();
  document.getElementById('screens-root').appendChild(ms.el);
  /* Системные зоны подкрашиваем под стартовый экран сразу при запуске. */
  refreshEdgeColor();
  bindPress();
  ms.load();
})();

/* Перехват «назад» на Android — корректная работа как в нативном приложении:
   сначала закрываем меню/sheet/оверлеи/режим выбора, потом экран, в самом конце ничего */
let backHandledAt = 0;
document.addEventListener('app-back', () => {
  /* Защита от двойного срабатывания на один свайп: системный жест (popstate)
     и собственный edge-жест могут прийти почти одновременно — не «проскакиваем»
     два экрана за раз. */
  const now = Date.now();
  if (now - backHandledAt < 400) return;
  backHandledAt = now;

  /* 1. bottom sheet */
  const sheet = document.querySelector('.sheet-backdrop.open');
  if (sheet) { const cancel = sheet.querySelector('[data-a=cancel]'); if (cancel) { cancel.click(); return; } sheet.remove(); return; }
  /* 2. контекстное меню */
  const menu = document.querySelector('.menu-surface');
  if (menu) { menu.remove(); return; }
  /* 3. оверлей записи — останавливаем как стоп-кнопкой */
  const rec = document.querySelector('.rec-overlay.open');
  if (rec) { const stop = rec.querySelector('.rec-circle-btn.stop-rec'); if (stop) { stop.click(); return; } rec.classList.remove('open'); return; }
  /* 4. оверлей рисования */
  const draw = document.querySelector('.draw-overlay.open');
  if (draw) { const cl = draw.querySelector('[data-a=close]'); if (cl) { cl.click(); return; } draw.classList.remove('open'); return; }
  /* 5–6. поиск и режим выбора — на ВЕРХНЕМ экране: их может показать и главная,
     и страница «Скрытые» (у обеих класс .main-screen), а querySelector вернул бы
     первую из них в DOM, то есть всегда главную. */
  const topScreen = Nav.top();
  if (topScreen && topScreen.classList.contains('main-screen')) {
    if (topScreen.classList.contains('searching')) { topScreen.dispatchEvent(new CustomEvent('app-exit-search')); return; }
    if (topScreen.classList.contains('selecting')) { topScreen.dispatchEvent(new CustomEvent('app-exit-selection')); return; }
  }
  /* 7. редактор — сохранить и закрыть как по кнопке «назад» */
  const top = topScreen;
  if (top?.classList.contains('editor-screen')) {
    const back = top.querySelector('[data-a=back]');
    if (back) { back.click(); return; }
  }
  /* 8. обычный экран */
  if (!Nav.pop()) { /* уже первый экран — ничего не делаем, приложение остаётся открытым */ }
});
