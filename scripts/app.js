/* ============ Точка входа ============ */
import { initKeyboard, bindPress, Store, Nav } from './core.js';
import { mainScreen } from './main-screen.js';
import { seedDemo } from './seed.js';

initKeyboard();

(async () => {
  const notes = await Store.notes();
  if (!notes.length) {
    await seedDemo();   // демо-заметки как на скриншоте (только при первом запуске)
  }
  const ms = mainScreen();
  document.getElementById('screens-root').appendChild(ms.el);
  bindPress();
  ms.load();

  /* Перерисовка главной при возвращении из редактора */
  document.addEventListener('notes-changed', () => ms.load());
})();

/* Перехват «назад» на Android — корректная работа как в нативном приложении:
   сначала закрываем меню/sheet/оверлеи/режим выбора, потом экран, в самом конце ничего */
document.addEventListener('app-back', () => {
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
  /* 5. режим выбора на главном экране */
  const selecting = document.querySelector('.main-screen.selecting');
  if (selecting) { selecting.dispatchEvent(new CustomEvent('app-exit-selection')); return; }
  /* 6. обычный экран */
  if (!Nav.pop()) { /* уже первый экран — ничего не делаем, приложение остаётся открытым */ }
});
