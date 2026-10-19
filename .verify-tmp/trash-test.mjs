/* Проверка логики корзины: срок хранения 30 дней, удаление вложений,
   безопасность для записей без отметки времени. IndexedDB подменён
   минимальной in-memory реализацией. */

function makeIDB() {
  const stores = new Map();
  const ensure = (n) => { if (!stores.has(n)) stores.set(n, new Map()); return stores.get(n); };
  const handle = (name) => ({
    createIndex() {},
    getAll() { const r = {}; queueMicrotask(() => { r.result = [...ensure(name).values()]; r.onsuccess && r.onsuccess(); }); return r; },
    get(k) { const r = {}; queueMicrotask(() => { r.result = ensure(name).get(k); r.onsuccess && r.onsuccess(); }); return r; },
    put(v) { const r = {}; queueMicrotask(() => { ensure(name).set(v.id ?? v.key, v); r.onsuccess && r.onsuccess(); }); return r; },
    delete(k) { const r = {}; queueMicrotask(() => { ensure(name).delete(k); r.onsuccess && r.onsuccess(); }); return r; },
  });
  const db = {
    objectStoreNames: { contains: (n) => stores.has(n) },
    createObjectStore: (n) => { ensure(n); return { createIndex() {} }; },
    transaction(names) {
      const t = { objectStore: (n) => handle(n), oncomplete: null, onerror: null };
      queueMicrotask(() => queueMicrotask(() => t.oncomplete && t.oncomplete()));
      return t;
    },
  };
  return { open() { const r = {}; queueMicrotask(() => { r.result = db; r.onupgradeneeded && r.onupgradeneeded(); r.onsuccess(); }); return r; } };
}
globalThis.indexedDB = makeIDB();

/* core.js на верхнем уровне вешает обработчики — даём минимальные заглушки DOM,
   чтобы модуль импортировался в node (логику корзины это не затрагивает). */
const noop = () => {};
const fakeEl = () => ({
  style: { setProperty: noop, removeProperty: noop },
  classList: { add: noop, remove: noop, toggle: noop, contains: () => false },
  dataset: {}, appendChild: noop, removeChild: noop, setAttribute: noop, getAttribute: () => null,
  addEventListener: noop, removeEventListener: noop, querySelector: () => null, querySelectorAll: () => [],
  innerHTML: '', textContent: '', scrollTop: 0, clientWidth: 0, offsetWidth: 0, getBoundingClientRect: () => ({ top: 0, left: 0, width: 0, height: 0 }),
});
globalThis.document = {
  addEventListener: noop, removeEventListener: noop, dispatchEvent: noop,
  querySelector: () => null, querySelectorAll: () => [], createElement: fakeEl,
  documentElement: fakeEl(), body: fakeEl(), visibilityState: 'visible',
};
globalThis.window = Object.assign(globalThis, { addEventListener: noop, removeEventListener: noop, innerWidth: 390, innerHeight: 800, visualViewport: null });
try { Object.defineProperty(globalThis, 'navigator', { value: { userAgent: 'node', vibrate: noop }, configurable: true }); } catch (e) {}

const { Store, purgeTrash, trashNote, TRASH_DAYS, noteBlobIds } = await import('../scripts/core.js');

const DAY = 24 * 60 * 60 * 1000;
const now = Date.now();
let fails = 0;
const ok = (cond, msg) => { console.log((cond ? '  ok   ' : '  FAIL ') + msg); if (!cond) fails++; };

const active = { id: 'active', title: 'Активная', content: '', updatedAt: now - 100 * DAY };
const fresh = { id: 'fresh', title: 'Свежая в корзине', content: '', deleted: true, deletedAt: now - 5 * DAY, updatedAt: now - 5 * DAY };
const old = { id: 'old', title: 'Просроченная', content: '', deleted: true, deletedAt: now - 30 * DAY - 60 * 60 * 1000, updatedAt: now - 31 * DAY, audio: { id: 'aud_old', duration: 3 } };
const exact = { id: 'exact', title: 'Почти 30 дней', content: '', deleted: true, deletedAt: now - 30 * DAY + 60 * 60 * 1000, updatedAt: now - 30 * DAY };
const legacy = { id: 'legacy', title: 'Легаси', content: '', deleted: true, updatedAt: now - 400 * DAY };

for (const n of [active, fresh, old, exact, legacy]) await Store.saveNoteRaw(n);
await Store.putBlob('aud_old', { data: 1 });
await Store.putBlob('img_x', { data: 2 });
old.content = '<div><img data-blob="img_x" src="x"></div>';
await Store.saveNoteRaw(old);

ok(JSON.stringify(noteBlobIds(old)) === JSON.stringify(['aud_old', 'img_x']), 'noteBlobIds находит запись и картинку');

ok(TRASH_DAYS === 30, 'срок хранения = 30 дней');
const purged = await purgeTrash();
ok(purged === 1, 'удалена ровно 1 просроченная заметка (получено ' + purged + ')');

const after = Object.fromEntries((await Store.notes()).map((n) => [n.id, n]));
ok(!!after.active, 'активная заметка на месте');
ok(!!after.fresh, 'заметка, лежащая 5 дней, осталась');
ok(!!after.exact, 'заметка, пролежавшая меньше 30 суток, осталась');
ok(!after.old, 'заметка, пролежавшая больше 30 суток, безвозвратно удалена');
ok(!!after.legacy, 'запись без отметки времени не удалена');
ok(!!after.legacy.deletedAt, 'записи без отметки проставлен deletedAt (старт честных 30 дней)');

ok((await Store.blob('aud_old')) === null, 'аудио удалённой заметки стёрто из blobs');
ok((await Store.blob('img_x')) === null, 'картинка удалённой заметки стёрта из blobs');

ok((await purgeTrash()) === 0, 'повторная очистка ничего не удаляет');

const back = after.fresh;
back.deleted = false; back.deletedAt = null;
await Store.saveNoteRaw(back);
const again = (await Store.notes()).find((n) => n.id === 'fresh');
ok(again.deleted === false && !again.deletedAt, 'восстановление снимает deleted и deletedAt');

const t = { id: 't', title: 'T', content: '' };
await Store.saveNoteRaw(t);
await trashNote(t);
const t2 = (await Store.notes()).find((n) => n.id === 't');
ok(t2.deleted === true && typeof t2.deletedAt === 'number', 'trashNote: deleted=true и проставлен deletedAt');

console.log(fails ? `\nПРОВАЛЕНО: ${fails}` : '\nВСЕ ПРОВЕРКИ ПРОЙДЕНЫ');
process.exit(fails ? 1 : 0);
