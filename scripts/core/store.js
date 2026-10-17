/* Хранилище заметок: IndexedDB — полное офлайн */
const DB_NAME = 'notes-app';
const DB_VERSION = 1;

let dbPromise = null;

function openDB() {
  if (dbPromise) return dbPromise;
  dbPromise = new Promise((resolve, reject) => {
    const req = indexedDB.open(DB_NAME, DB_VERSION);
    req.onupgradeneeded = () => {
      const db = req.result;
      if (!db.objectStoreNames.contains('notes')) {
        const s = db.createObjectStore('notes', { keyPath: 'id' });
        s.createIndex('folderId', 'folderId');
        s.createIndex('updatedAt', 'updatedAt');
      }
      if (!db.objectStoreNames.contains('folders')) {
        db.createObjectStore('folders', { keyPath: 'id' });
      }
      if (!db.objectStoreNames.contains('settings')) {
        db.createObjectStore('settings', { keyPath: 'key' });
      }
      if (!db.objectStoreNames.contains('blobs')) {
        db.createObjectStore('blobs', { keyPath: 'id' });
      }
    };
    req.onsuccess = () => resolve(req.result);
    req.onerror = () => reject(req.error);
  });
  return dbPromise;
}

function tx(store, mode, fn) {
  return openDB().then(db => new Promise((resolve, reject) => {
    const t = db.transaction(store, mode);
    const s = t.objectStore(store);
    let out;
    try { out = fn(s); } catch (e) { reject(e); return; }
    t.oncomplete = () => resolve(out && out.result !== undefined ? out.result : out);
    t.onerror = () => reject(t.error);
    t.onabort = () => reject(t.error);
  }));
}

function reqOut(req) {
  return new Promise((resolve, reject) => {
    req.onsuccess = () => resolve(req.result);
    req.onerror = () => reject(req.error);
  });
}

export const Store = {
  async getAllNotes() {
    const db = await openDB();
    return reqOut(db.transaction('notes').objectStore('notes').getAll());
  },
  async getNote(id) {
    const db = await openDB();
    return reqOut(db.transaction('notes').objectStore('notes').get(id));
  },
  async putNote(note) {
    note.updatedAt = Date.now();
    return tx('notes', 'readwrite', s => s.put(note));
  },
  async putNoteRaw(note) { // без изменения updatedAt (при открытии)
    return tx('notes', 'readwrite', s => s.put(note));
  },
  async deleteNote(id) { return tx('notes', 'readwrite', s => s.delete(id)); },

  async getAllFolders() {
    const db = await openDB();
    return reqOut(db.transaction('folders').objectStore('folders').getAll());
  },
  async putFolder(folder) { return tx('folders', 'readwrite', s => s.put(folder)); },
  async deleteFolder(id) { return tx('folders', 'readwrite', s => s.delete(id)); },

  async getSetting(key) {
    const db = await openDB();
    const row = await reqOut(db.transaction('settings').objectStore('settings').get(key));
    return row ? row.value : undefined;
  },
  async setSetting(key, value) { return tx('settings', 'readwrite', s => s.put({ key, value })); },

  // Большие бинарники (аудио, фото) держим отдельно
  async putBlob(id, blob) { return tx('blobs', 'readwrite', s => s.put({ id, blob })); },
  async getBlob(id) {
    const db = await openDB();
    const row = await reqOut(db.transaction('blobs').objectStore('blobs').get(id));
    return row ? row.blob : null;
  },
  async deleteBlob(id) { return tx('blobs', 'readwrite', s => s.delete(id)); },

  async bulkSeed(notes, folders) {
    const db = await openDB();
    return new Promise((resolve, reject) => {
      const t = db.transaction(['notes', 'folders'], 'readwrite');
      const ns = t.objectStore('notes'); const fs = t.objectStore('folders');
      notes.forEach(n => ns.put(n));
      folders.forEach(f => fs.put(f));
      t.oncomplete = resolve; t.onerror = () => reject(t.error);
    });
  }
};
