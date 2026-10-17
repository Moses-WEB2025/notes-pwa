/* ============ Главный экран «Заметки» ============ */
import { Store, escapeHtml, htmlToPlain, debounce, uid, bindPress, Nav, vibrate } from './core.js';
import { toast, confirmSheet, attachMenu, sheetSections } from './components/ui.js';
import { openEditor } from './screens/editor.js';
import { openFolders } from './screens/folders.js';
import { renderNotesGrid, updateSelection } from './screens/grid.js';
import { openTrashScreen } from './screens/trash.js';

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
    <button class="icon-btn sel-only" data-a="sel-close" data-press aria-label="Готово"><span class="material-symbols-rounded">close</span></button>
    <button class="icon-btn" data-a="folders" data-press aria-label="Папки"><span class="material-symbols-rounded">folder_open</span></button>
    <span class="spacer"></span>
    <button class="icon-btn" data-a="trash" data-press aria-label="Корзина"><span class="material-symbols-rounded">delete</span></button>
    <button class="icon-btn sel-only" data-a="sel-all" data-press aria-label="Выбрать все"><span class="material-symbols-rounded">select_all</span></button>`;

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

  /* чипы фильтров режима выбора */
  const chipsRow = document.createElement('div');
  chipsRow.className = 'chips-row hidden';
  chipsRow.innerHTML = `
    <button class="chip active" data-c="all">Все</button>
    <button class="chip" data-c="notes">Заметки</button>
    <button class="chip" data-c="nocat">Без категории</button>
    <button class="chip" data-c="hidden">Скрытые</button>`;

  const fab = document.createElement('button');
  fab.className = 'fab';
  fab.innerHTML = `<span class="material-symbols-rounded">add</span>`;

  const tabbar = document.createElement('div');
  tabbar.className = 'tabbar';
  tabbar.innerHTML = `
    <button class="tab active"><span class="material-symbols-rounded">edit_note</span><span class="lbl">Заметки</span></button>`;

  /* нижняя панель режима выбора: Скрыть | Закрепить | Переместить | Удалить */
  const selBar = document.createElement('div');
  selBar.className = 'select-bar hidden';
  selBar.innerHTML = `
    <button data-sel="hide"><span class="material-symbols-rounded">lock</span><span class="lbl">Скрыть</span></button>
    <button data-sel="pin"><span class="material-symbols-rounded">push_pin</span><span class="lbl">Закрепить</span></button>
    <button data-sel="move"><span class="material-symbols-rounded">drive_file_move</span><span class="lbl">Переместить</span></button>
    <button data-sel="delete"><span class="material-symbols-rounded">delete</span><span class="lbl">Удалить</span></button>`;

  el.append(appbar, h1row, searchWrap, chipsRow, gridHolder, fab, tabbar, selBar);

  /* ---------- режим выбора (долгий тап) ---------- */
  let selFilter = 'all';
  /* id карточки, которую нужно «отскоком» подсветить после текущего тапа */
  let bounceId = null;
  const sel = {
    active: false,
    set: new Set(),
    onEnter(id) { this.active = true; this.set.add(id); bounceId = id; enterSelection(); refresh(); },
    onToggle(id) { if (this.set.has(id)) this.set.delete(id); else this.set.add(id); bounceId = id; refresh(); },
  };

  function enterSelection() {
    el.classList.add('selecting');
    chipsRow.classList.remove('hidden');
    vibrate(12);
  }
  function exitSelection() {
    sel.active = false; sel.set.clear(); selFilter = 'all';
    chipsRow.querySelectorAll('.chip').forEach(c => c.classList.toggle('active', c.dataset.c === 'all'));
    el.classList.remove('selecting');
    chipsRow.classList.add('hidden');
    refresh();
  }

  chipsRow.addEventListener('click', e => {
    const c = e.target.closest('.chip'); if (!c) return;
    selFilter = c.dataset.c;
    chipsRow.querySelectorAll('.chip').forEach(x => x.classList.toggle('active', x === c));
    sel.set.clear();
    refresh();
  });

  function refresh() {
    const q = searchInput.value.trim().toLowerCase();
    let list = notes;
    if (sel.active) {
      list = selFilter === 'hidden' ? notes.filter(n => n.hidden) : notes.filter(n => !n.hidden);
      if (selFilter === 'notes') list = list.filter(n => (n.title || '').trim() || htmlToPlain(n.content));
      if (selFilter === 'nocat') list = list.filter(n => !n.folderId);
    } else {
      list = list.filter(n => !n.hidden);
      if (filterColor) list = list.filter(n => n.color === filterColor);
      if (filterFolder) list = list.filter(n => n.folderId === filterFolder);
    }
    if (q) list = list.filter(n => (n.title || '').toLowerCase().includes(q) || htmlToPlain(n.content).toLowerCase().includes(q));
    renderNotesGrid(gridHolder, list, q, { sel: { active: sel.active, selected: sel.set, onEnter: sel.onEnter.bind(sel), onToggle: sel.onToggle.bind(sel) }, bounceId });
    bounceId = null;
    searchField.classList.toggle('has-text', !!searchInput.value.trim());
    renderChip();
    renderSelBar();
  }

  /* заметки, попадающие под текущий фильтр режима выбора */
  function selectionList() {
    const q = searchInput.value.trim().toLowerCase();
    let list = selFilter === 'hidden' ? notes.filter(n => n.hidden) : notes.filter(n => !n.hidden);
    if (selFilter === 'notes') list = list.filter(n => (n.title || '').trim() || htmlToPlain(n.content));
    if (selFilter === 'nocat') list = list.filter(n => !n.folderId);
    if (q) list = list.filter(n => (n.title || '').toLowerCase().includes(q) || htmlToPlain(n.content).toLowerCase().includes(q));
    return list;
  }

  function renderSelBar() {
    selBar.classList.toggle('hidden', !sel.active);
    const n = sel.set.size;
    h1row.querySelector('h1').textContent = !sel.active ? 'Заметки' : (n ? `Выбрано ${n} объект${n % 10 === 1 && n % 100 !== 11 ? '' : (n % 10 >= 2 && n % 10 <= 4 && (n % 100 < 12 || n % 100 > 14) ? 'а' : 'ов')}` : 'Выбрано');
    const any = n > 0;
    selBar.querySelectorAll('button').forEach(b => b.disabled = !any);
    if (!sel.active) return;

    const picked = [...sel.set].map(id => notes.find(x => x.id === id)).filter(Boolean);
    /* Кнопки показывают, что произойдёт по нажатию: закреплённое снимаем,
       а скрытое — показываем (иначе действие необратимо). */
    const allPinned = picked.length > 0 && picked.every(x => x.pinned);
    const pinBtn = selBar.querySelector('[data-sel=pin]');
    pinBtn.querySelector('.lbl').textContent = allPinned ? 'Открепить' : 'Закрепить';
    pinBtn.querySelector('.material-symbols-rounded').classList.toggle('filled', allPinned);

    const allHidden = picked.length > 0 && picked.every(x => x.hidden);
    const hideBtn = selBar.querySelector('[data-sel=hide]');
    hideBtn.querySelector('.lbl').textContent = allHidden ? 'Показать' : 'Скрыть';
    hideBtn.querySelector('.material-symbols-rounded').textContent = allHidden ? 'visibility' : 'lock';

    /* «Выбрать все» превращается в «Снять все», когда выбрано уже всё */
    const list = selectionList();
    const allSelected = list.length > 0 && list.every(x => sel.set.has(x.id));
    appbar.querySelector('[data-a=sel-all] .material-symbols-rounded').textContent = allSelected ? 'deselect' : 'select_all';
    appbar.querySelector('[data-a=sel-all]').setAttribute('aria-label', allSelected ? 'Снять выбор' : 'Выбрать все');
  }

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
    refresh();
  }

  function renderChip() {
    if (chipEl) { chipEl.remove(); chipEl = null; }
    if (filterColor || filterFolder) {
      const c = COLORS.find(c => c.key === filterColor);
      chipEl = document.createElement('button');
      chipEl.className = 'cat-chip';
      chipEl.innerHTML = `<span class="material-symbols-rounded">${c ? 'palette' : 'folder_open'}</span>${c ? c.label : 'Папка'}<span class="material-symbols-rounded" style="font-size:16px">close</span>`;
      chipEl.addEventListener('click', () => { filterColor = null; filterFolder = null; refresh(); });
      searchWrap.after(chipEl);
    }
  }

  searchInput.addEventListener('focus', () => searchCancel.classList.remove('hidden'));
  searchInput.addEventListener('blur', () => setTimeout(() => { if (!searchInput.value.trim()) searchCancel.classList.add('hidden'); }, 160));
  searchCancel.addEventListener('click', () => { searchInput.value = ''; searchInput.blur(); refresh(); searchCancel.classList.add('hidden'); });
  searchInput.addEventListener('input', debounce(refresh, 100));
  searchClear.addEventListener('click', () => { searchInput.value = ''; refresh(); });

  appbar.querySelector('[data-a=folders]').addEventListener('click', () => {
    const foldersEl = openFolders();
    Nav.pushEl(foldersEl);
  });
  appbar.querySelector('[data-a=trash]').addEventListener('click', () => Nav.pushEl(openTrashScreen()));
  appbar.querySelector('[data-a=sel-close]').addEventListener('click', exitSelection);
  appbar.querySelector('[data-a=sel-all]').addEventListener('click', () => {
    const list = selectionList();
    const allSelected = list.length > 0 && list.every(n => sel.set.has(n.id));
    if (allSelected) sel.set.clear();
    else list.forEach(n => sel.set.add(n.id));
    refresh();
  });
  fab.addEventListener('click', () => openEditor(null, { folderId: filterFolder }));

  /* кнопки режима выбора */
  selBar.addEventListener('click', async e => {
    const b = e.target.closest('[data-sel]'); if (!b || !sel.set.size) return;
    const ids = [...sel.set];
    const act = b.dataset.sel;
    if (act === 'pin') {
      const picked = ids.map(id => notes.find(x => x.id === id)).filter(Boolean);
      /* если всё выбранное уже закреплено — снимаем закрепление со всех */
      const target = !(picked.length > 0 && picked.every(x => x.pinned));
      for (const nn of picked) { nn.pinned = target; await Store.saveNote(nn); }
      toast(target ? 'Закреплено' : 'Откреплено'); exitSelection(); load();
    } else if (act === 'move') {
      const folders = await Store.folders();
      sheetSections({ title: 'В папку', sections: [api => {
        const box = document.createElement('div'); box.className = 'sheet-actions';
        box.innerHTML = [`<button class="sheet-action" data-f=""><span class="material-symbols-rounded">block</span>Без папки</button>`,
          ...folders.map(f => `<button class="sheet-action" data-f="${f.id}"><span class="material-symbols-rounded">folder</span>${escapeHtml(f.name)}</button>`)].join('');
        box.addEventListener('click', async ev => {
          const btn = ev.target.closest('[data-f]'); if (!btn) return;
          for (const id of ids) { const nn = notes.find(x => x.id === id); if (nn) { nn.folderId = btn.dataset.f || null; await Store.saveNote(nn); } }
          toast('Перемещено'); api.close(); exitSelection(); load();
        });
        return box;
      }]});
    } else if (act === 'delete') {
      if (await confirmSheet({ title: 'Удалите заметки', text: ids.length === 1 ? 'Удалить эту заметку?' : `Удалить ${ids.length} заметок?` })) {
        for (const id of ids) { const nn = notes.find(x => x.id === id); if (nn) { nn.deleted = true; await Store.saveNoteRaw(nn); } }
        toast('Заметки удалены'); exitSelection(); load();
      }
    } else if (act === 'hide') {
      const picked = ids.map(id => notes.find(x => x.id === id)).filter(Boolean);
      /* скрытые заметки можно вернуть: если всё выбранное уже скрыто — показываем */
      const target = !(picked.length > 0 && picked.every(x => x.hidden));
      for (const nn of picked) { nn.hidden = target; await Store.saveNoteRaw(nn); }
      toast(target ? 'Скрыто' : 'Показано'); exitSelection(); load();
    }
  });

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

  /* выход из режима выбора по системной «назад» */
  el.addEventListener('app-exit-selection', exitSelection);

  document.addEventListener('notes-changed', () => { if (document.body.contains(el)) load(); });
  load();

  return { el, load };
}
