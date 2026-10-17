/* ============ Главный экран «Заметки» ============ */
import { Store, escapeHtml, htmlToPlain, debounce, uid, bindPress } from './core.js';
import { toast, confirmSheet, attachMenu, sheetSections } from './components/ui.js';
import { openEditor } from './screens/editor.js';
import { openFolders } from './screens/folders.js';
import { renderNotesGrid } from './screens/grid.js';

const COLORS = [
  { key: 'none', label: 'Без цвета', css: '#ffffff' },
  { key: 'yellow', label: 'Жёлтая', css: '#f3b13c' },
  { key: 'blue', label: 'Синяя', css: '#1a4a76' },
  { key: 'paper', label: 'Бумага', css: '#fbf3de' },
  { key: 'pink', label: 'Розовая', css: '#ec6d94' },
  { key: 'red', label: 'Красная', css: '#e05555' },
];

export function mainScreen() {
  const el = document.createElement('div');
  el.className = 'screen main-screen';

  /* шапка: папки + корзина */
  const appbar = document.createElement('div');
  appbar.className = 'appbar';
  appbar.innerHTML = `
    <button class="icon-btn" data-a="folders" data-press aria-label="Папки"><span class="material-symbols-rounded">folder_open</span></button>
    <span class="spacer"></span>
    <button class="icon-btn" data-a="trash" data-press aria-label="Корзина"><span class="material-symbols-rounded">delete</span></button>`;

  const h1row = document.createElement('div');
  h1row.className = 'h1-row';
  h1row.innerHTML = `<h1>Заметки</h1>`;

  /* поиск + «Отмена» */
  const searchWrap = document.createElement('div');
  searchWrap.className = 'searchbar';
  searchWrap.innerHTML = `
    <div class="searchbar-row">
      <div class="searchfield">
        <span class="material-symbols-rounded">search</span>
        <input placeholder="Поиск заметок" autocomplete="off" />
        <button class="search-clear" tabindex="-1"><span class="material-symbols-rounded" style="font-size:15px">close</span></button>
      </div>
      <button class="search-cancel hidden" data-press>Отмена</button>
    </div>`;

  const gridHolder = document.createElement('div');
  gridHolder.className = 'scroll grid-holder';

  const fab = document.createElement('button');
  fab.className = 'fab';
  fab.innerHTML = `<span class="material-symbols-rounded">add</span>`;

  const tabbar = document.createElement('div');
  tabbar.className = 'tabbar';
  tabbar.innerHTML = `
    <button class="tab active"><span class="material-symbols-rounded">edit_note</span><span class="lbl">Заметки</span></button>`;

  el.append(appbar, h1row, searchWrap, gridHolder, fab, tabbar);

  /* ---------- состояние ---------- */
  let notes = [];
  let filterColor = null, filterFolder = null;
  let chipEl = null;

  const searchInput = searchWrap.querySelector('input');
  const searchField = searchWrap.querySelector('.searchfield');
  const searchClear = searchWrap.querySelector('.search-clear');
  const searchCancel = searchWrap.querySelector('.search-cancel');

  async function load() {
    notes = (await Store.notes()).filter(n => !n.deleted)
      .sort((a, b) => (b.pinned ? 1 : 0) - (a.pinned ? 1 : 0) || b.updatedAt - a.updatedAt);
    apply();
  }

  function apply() {
    const q = searchInput.value.trim().toLowerCase();
    let list = notes;
    if (filterColor) list = list.filter(n => n.color === filterColor);
    if (filterFolder) list = list.filter(n => n.folderId === filterFolder);
    if (q) list = list.filter(n => (n.title || '').toLowerCase().includes(q) || htmlToPlain(n.content).toLowerCase().includes(q));
    renderNotesGrid(gridHolder, list, q, longPressMenu);
    searchField.classList.toggle('has-text', !!searchInput.value.trim());
    renderChip();
  }

  function renderChip() {
    if (chipEl) { chipEl.remove(); chipEl = null; }
    if (filterColor || filterFolder) {
      const c = COLORS.find(c => c.key === filterColor);
      chipEl = document.createElement('button');
      chipEl.className = 'cat-chip';
      chipEl.innerHTML = `<span class="material-symbols-rounded">${c ? 'palette' : 'folder_open'}</span>${c ? c.label : 'Папка'}<span class="material-symbols-rounded" style="font-size:16px">close</span>`;
      chipEl.addEventListener('click', () => { filterColor = null; filterFolder = null; apply(); });
      searchWrap.after(chipEl);
    }
  }

  searchInput.addEventListener('focus', () => searchCancel.classList.remove('hidden'));
  searchInput.addEventListener('blur', () => setTimeout(() => { if (!searchInput.value.trim()) searchCancel.classList.add('hidden'); }, 160));
  searchCancel.addEventListener('click', () => { searchInput.value = ''; searchInput.blur(); apply(); searchCancel.classList.add('hidden'); });
  searchInput.addEventListener('input', debounce(apply, 100));
  searchClear.addEventListener('click', () => { searchInput.value = ''; apply(); });

  appbar.querySelector('[data-a=folders]').addEventListener('click', () => {
    const foldersEl = openFolders();
    document.getElementById('screens-root').appendChild(foldersEl);
  });
  appbar.querySelector('[data-a=trash]').addEventListener('click', () => openTrashScreen());
  fab.addEventListener('click', () => openEditor(null, { folderId: filterFolder }));

  function longPressMenu(n, card) {
    attachMenu(card, [
      { icon: 'palette', label: 'Цвет', onClick: () => colorSheetFor(n) },
      { icon: 'folder',  label: 'В папку', onClick: () => folderSheetFor(n) },
      { icon: 'push_pin', label: n.pinned ? 'Открепить' : 'Закрепить', checked: !!n.pinned, onClick: async () => { n.pinned = !n.pinned; await Store.saveNote(n); toast(n.pinned ? 'Закреплено' : 'Откреплено'); load(); } },
      { icon: 'content_copy', label: 'Копировать', onClick: async () => { const c = { ...n, id: uid(), createdAt: Date.now(), updatedAt: Date.now() }; await Store.saveNote(c); toast('Скопировано'); load(); } },
      { divider: true },
      { icon: 'delete', label: 'Удалить', onClick: async () => {
        if (await confirmSheet({ title: 'Удалите заметки', text: 'Удалить эту заметку?' })) { await Store.delNote(n.id); toast('Заметка удалена'); load(); }
      } },
    ]);
  }

  function colorSheetFor(n) {
    sheetSections({ title: 'Цвет', sections: [api => {
      const row = document.createElement('div');
      row.className = 'color-row';
      row.innerHTML = `<div class="row-options">${COLORS.map(c => {
        const sel = (n.color || 'none') === c.key;
        return `<button class="swatch ${sel ? 'selected' : ''}" data-k="${c.key}" style="background:${c.css}">${sel ? '<span class="material-symbols-rounded">check</span>' : ''}</button>`;
      }).join('')}</div>`;
      row.querySelectorAll('.swatch').forEach(sw => sw.addEventListener('click', async () => {
        n.color = sw.dataset.k === 'none' ? null : sw.dataset.k;
        await Store.saveNote(n); api.close(); load();
      }));
      return row;
    }]});
  }

  async function folderSheetFor(n) {
    const folders = await Store.folders();
    sheetSections({ title: 'В папку', sections: [api => {
      const box = document.createElement('div');
      box.className = 'sheet-actions';
      box.innerHTML = [
        `<button class="sheet-action" data-f=""><span class="material-symbols-rounded">block</span>Без папки</button>`,
        ...folders.map(f => `<button class="sheet-action" data-f="${f.id}"><span class="material-symbols-rounded">folder</span>${escapeHtml(f.name)}</button>`)
      ].join('');
      box.addEventListener('click', async e => {
        const b = e.target.closest('[data-f]'); if (!b) return;
        n.folderId = b.dataset.f || null;
        await Store.saveNote(n); toast('Перемещено'); api.close(); load();
      });
      return box;
    }]});
  }

  /* корзина */
  function openTrashScreen() {
    const tEl = document.createElement('div');
    tEl.className = 'screen trash-screen';
    tEl.innerHTML = `
      <div class="appbar back-header">
        <button class="icon-btn" data-a="back" data-press><span class="material-symbols-rounded">arrow_back</span></button>
        <div class="appbar-title">Корзина</div><span style="width:44px"></span>
      </div>
      <div class="scroll" style="padding-top:8px"></div>`;
    const sc = tEl.querySelector('.scroll');
    const fill = async () => {
      const all = await Store.notes();
      const del = all.filter(n => n.deleted);
      sc.innerHTML = `
        <div class="trash-banner">Заметки, находящиеся в корзине более 30 дней, были безвозвратно удалены</div>
        ${del.length ? '' : `
          <div class="trash-empty">
            <span class="material-symbols-rounded">article</span>
            <div class="t">Нет заметок</div>
          </div>`}`;
      del.sort((a,b) => b.updatedAt - a.updatedAt).forEach(n => {
        const r = document.createElement('div');
        r.className = 'folder-row';
        r.innerHTML = `<span class="f-name">${escapeHtml(n.title || 'Без названия')}</span>
          <button class="icon-btn" data-a="restore" data-press><span class="material-symbols-rounded">restore</span></button>`;
        r.querySelector('[data-a=restore]').addEventListener('click', async () => { n.deleted = false; await Store.saveNoteRaw(n); toast('Восстановлено'); fill(); });
        sc.appendChild(r);
      });
      bindPress(sc);
    };
    tEl.querySelector('[data-a=back]').addEventListener('click', () => tEl.remove());
    document.getElementById('screens-root').appendChild(tEl);
    fill();
  }

  document.addEventListener('notes-changed', () => { if (document.body.contains(el)) load(); });
  load();

  return { el, load };
}
