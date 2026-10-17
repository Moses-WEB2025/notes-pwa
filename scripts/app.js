/* ============ Точка входа ============ */
import { initKeyboard, bindPress, Store } from './core.js';
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

/* Перехват «назад» на Android — корректная работа как в нативном приложении */
window.addEventListener('popstate', () => {
  document.dispatchEvent(new CustomEvent('app-back'));
});

/* офлайн-индикатор */
const upd = () => document.getElementById('offline-indicator').classList.toggle('hidden', navigator.onLine);
window.addEventListener('online', upd);
window.addEventListener('offline', upd);
upd();
