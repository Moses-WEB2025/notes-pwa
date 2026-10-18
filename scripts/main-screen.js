/* ============ Главный экран «Заметки» ============ */
import { Store, escapeHtml, htmlToPlain, debounce, uid, bindPress, Nav, vibrate } from './core.js';
import { setEdgeColor, refreshEdgeColor } from './edge.js';
import { toast, confirmSheet, attachMenu, sheetSections } from './components/ui.js';
import { openEditor } from './screens/editor.js';
import { openFolders } from './screens/folders.js';
import { renderNotesGrid } from './screens/grid.js';
import { openTrashScreen } from './screens/trash.js';
import { systemUnlock, systemUnlockAvailable, systemUnlockError } from './system-unlock.js';

const COLORS = [
  { key: 'none', label: 'Без цвета', css: '#ffffff' },
  { key: 'yellow', label: 'Жёлтая', css: '#f3b13c' },
  { key: 'blue', label: 'Синяя', css: '#1a4a76' },
  { key: 'paper', label: 'Бумага', css: '#fbf3de' },
  { key: 'pink', label: 'Розовая', css: '#ec6d94' },
  { key: 'red', label: 'Красная', css: '#e05555' },
];

export function mainScreen() { return notesScreen({}); }

/* Страница «Скрытые» — тот же экран, что и главная, но без ряда категорий:
   она открывается только после системной разблокировки (протяжка вниз у самого
   верха главной). Все отличия собраны в опции hidden ниже. */
export function hiddenScreen() { return notesScreen({ hidden: true }); }

function notesScreen(opts = {}) {
  const hiddenMode = !!opts.hidden;
  const baseTitle = hiddenMode ? 'Скрытые' : 'Заметки';
  const el = document.createElement('div');
  el.className = 'screen main-screen' + (hiddenMode ? ' hidden-screen' : '');

  /* шапка: папки + корзина (на «Скрытых» — стрелка «назад» + «выбрать все») */
  const appbar = document.createElement('div');
  appbar.className = 'appbar';
  appbar.innerHTML = `
    <button class="icon-btn sel-only" data-a="sel-close" data-press aria-label="Готово"><span class="material-symbols-rounded">close</span></button>
    ${hiddenMode
      ? `<button class="icon-btn" data-a="back" data-press aria-label="Назад"><span class="material-symbols-rounded">arrow_back</span></button>`
      : `<button class="icon-btn" data-a="folders" data-press aria-label="Папки"><span class="material-symbols-rounded">folder_open</span></button>`}
    <span class="spacer"></span>
    ${hiddenMode ? '' : `<button class="icon-btn" data-a="trash" data-press aria-label="Корзина"><span class="material-symbols-rounded">delete</span></button>`}
    <button class="icon-btn sel-only" data-a="sel-all" data-press aria-label="Выбрать все"><span class="material-symbols-rounded">select_all</span></button>`;

  const h1row = document.createElement('div');
  h1row.className = 'h1-row';
  h1row.innerHTML = `<h1>${baseTitle}</h1>`;

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

  /* категории папок — ровно под строкой поиска. На «Скрытых» ряда нет: там
     показываются только скрытые заметки, фильтровать по папкам нечего. */
  const folderChips = hiddenMode ? null : document.createElement('div');
  if (folderChips) folderChips.className = 'folder-tabs';

  const gridHolder = document.createElement('div');
  gridHolder.className = 'scroll grid-holder';

  /* светлый холст, перекрывающий интерфейс во время поиска */
  const searchCanvas = document.createElement('div');
  searchCanvas.className = 'search-canvas';

  /* чипы фильтров режима выбора (только на главной: на «Скрытых» фильтровать
     нечего — там и так одни скрытые заметки) */
  const chipsRow = hiddenMode ? null : document.createElement('div');
  if (chipsRow) {
    chipsRow.className = 'chips-row hidden';
    chipsRow.innerHTML = `
      <button class="chip active" data-c="all">Все</button>
      <button class="chip" data-c="notes">Заметки</button>
      <button class="chip" data-c="nocat">Без категории</button>
      <button class="chip" data-c="hidden">Скрытые</button>`;
  }

  const fab = document.createElement('button');
  fab.className = 'fab' + (hiddenMode ? ' fab-blue' : '');
  fab.innerHTML = `<span class="material-symbols-rounded">add</span>`;

  const tabbar = document.createElement('div');
  tabbar.className = 'tabbar';
  tabbar.innerHTML = `
    <button class="tab active"><span class="material-symbols-rounded">edit_note</span><span class="lbl">Заметки</span></button>`;

  /* нижняя панель режима выбора: Скрыть | Закрепить | Переместить | Удалить.
     На «Скрытых» кнопки «Переместить» нет — там три действия, а заметки и так
     скрыты, поэтому первая кнопка возвращает их в обычный список. */
  const selBar = document.createElement('div');
  selBar.className = 'select-bar hidden';
  selBar.innerHTML = hiddenMode ? `
    <button data-sel="hide"><span class="material-symbols-rounded">visibility</span><span class="lbl">Показать</span></button>
    <button data-sel="pin"><span class="material-symbols-rounded">push_pin</span><span class="lbl">Закрепить</span></button>
    <button data-sel="delete"><span class="material-symbols-rounded">delete</span><span class="lbl">Удалить</span></button>` : `
    <button data-sel="hide"><span class="material-symbols-rounded">lock</span><span class="lbl">Скрыть</span></button>
    <button data-sel="pin"><span class="material-symbols-rounded">push_pin</span><span class="lbl">Закрепить</span></button>
    <button data-sel="move"><span class="material-symbols-rounded">drive_file_move</span><span class="lbl">Переместить</span></button>
    <button data-sel="delete"><span class="material-symbols-rounded">delete</span><span class="lbl">Удалить</span></button>`;

  const parts = [appbar, h1row, searchWrap];
  if (folderChips) parts.push(folderChips);
  parts.push(searchCanvas);
  if (chipsRow) parts.push(chipsRow);
  parts.push(gridHolder, fab, tabbar, selBar);
  el.append(...parts);

  /* ---------- режим выбора (долгий тап) ---------- */
  let selFilter = 'all';
  /* id карточки, которую нужно «отскоком» подсветить после текущего тапа */
  let bounceId = null;
  const sel = {
    active: false,
    set: new Set(),
    onEnter(id) { this.active = true; this.set.add(id); bounceId = id; enterSelection(); refresh(); },
    onToggle(id) { if (this.set.has(id)) this.set.delete(id); else this.set.add(id); paintCard(id); },
  };

  /* Отмечаем одну карточку без перерисовки всей сетки. Перерисовка во время
     быстрых тапов уничтожала элементы прямо под пальцем, и нажатия «терялись». */
  function paintCard(id) {
    const card = gridHolder.querySelector(`.note-card[data-id="${CSS.escape(String(id))}"]`);
    if (card) {
      card.classList.toggle('sur-select', sel.set.has(id));
      card.classList.remove('bounce');
      void card.offsetWidth;       /* перезапуск анимации «отскока» */
      card.classList.add('bounce');
    }
    renderSelBar();
  }

  function enterSelection() {
    el.classList.add('selecting');
    chipsRow?.classList.remove('hidden');
    vibrate(12);
  }
  function exitSelection() {
    sel.active = false; sel.set.clear(); selFilter = 'all';
    chipsRow?.querySelectorAll('.chip').forEach(c => c.classList.toggle('active', c.dataset.c === 'all'));
    el.classList.remove('selecting');
    chipsRow?.classList.add('hidden');
    refresh();
  }

  chipsRow?.addEventListener('click', e => {
    const c = e.target.closest('.chip'); if (!c) return;
    selFilter = c.dataset.c;
    chipsRow.querySelectorAll('.chip').forEach(x => x.classList.toggle('active', x === c));
    sel.set.clear();
    refresh();
  });

  function refresh() {
    const q = searchInput.value.trim().toLowerCase();
    let list;
    if (hiddenMode) {
      /* На «Скрытых» показываем только скрытые заметки — и в покое, и в режиме
         выбора: фильтры по папкам и цвету тут лишние. */
      list = notes.filter(n => n.hidden);
    } else if (sel.active) {
      list = selFilter === 'hidden' ? notes.filter(n => n.hidden) : notes.filter(n => !n.hidden);
      if (selFilter === 'notes') list = list.filter(n => (n.title || '').trim() || htmlToPlain(n.content));
      if (selFilter === 'nocat') list = list.filter(n => !n.folderId);
    } else {
      list = list.filter(n => !n.hidden);
      /* Заметки скрытых папок прячем из общего списка: они появляются только
         после системной разблокировки (протяжка вниз у верха экрана). */
      if (!hiddenUnlocked) list = list.filter(n => !noteInHiddenFolder(n));
      if (filterColor) list = list.filter(n => n.color === filterColor);
      if (filterFolder) list = list.filter(n => n.folderId === filterFolder);
    }
    if (q) list = list.filter(n => (n.title || '').toLowerCase().includes(q) || htmlToPlain(n.content).toLowerCase().includes(q));
    /* Перерисовка сетки не должна «прыгать»: сохраняем позицию прокрутки.
       Исключение — поиск: там результаты логично показать сверху. Прокрутку
       восстанавливает сам рендер (до расчёта FLIP-перестановки). */
    const keepTop = el.classList.contains('searching') ? 0 : gridHolder.scrollTop;
    renderNotesGrid(gridHolder, list, q, { sel: { active: sel.active, selected: sel.set, onEnter: sel.onEnter.bind(sel), onToggle: sel.onToggle.bind(sel) }, bounceId, keepTop });
    bounceId = null;
    searchField.classList.toggle('has-text', !!searchInput.value.trim());
    renderFolderChips();
    renderChip();
    renderSelBar();
    /* Прокрутка восстановлена — приводим шапку в соответствие */
    syncHeaderShrink();
  }

  /* заметки, попадающие под текущий фильтр режима выбора */
  function selectionList() {
    const q = searchInput.value.trim().toLowerCase();
    let list;
    if (hiddenMode) {
      list = notes.filter(n => n.hidden);
    } else {
      list = selFilter === 'hidden' ? notes.filter(n => n.hidden) : notes.filter(n => !n.hidden);
      if (selFilter === 'notes') list = list.filter(n => (n.title || '').trim() || htmlToPlain(n.content));
      if (selFilter === 'nocat') list = list.filter(n => !n.folderId);
    }
    if (q) list = list.filter(n => (n.title || '').toLowerCase().includes(q) || htmlToPlain(n.content).toLowerCase().includes(q));
    return list;
  }

  function renderSelBar() {
    selBar.classList.toggle('hidden', !sel.active);
    const n = sel.set.size;
    h1row.querySelector('h1').textContent = !sel.active ? baseTitle : (n ? `Выбрано ${n} объект${n % 10 === 1 && n % 100 !== 11 ? '' : (n % 10 >= 2 && n % 10 <= 4 && (n % 100 < 12 || n % 100 > 14) ? 'а' : 'ов')}` : 'Выбрано');
    const any = n > 0;
    selBar.querySelectorAll('button').forEach(b => b.disabled = !any);

    /* «Выбрать все» превращается в «Снять все», когда выбрано уже всё. Считаем
       это ДО выхода из режима выбора: на «Скрытых» кнопка видна и без него, и
       сброшенный заголовок «Снять всё» остался бы висеть после закрытия выбора. */
    const list = selectionList();
    const allSelected = sel.active && list.length > 0 && list.every(x => sel.set.has(x.id));
    const selAllBtn = appbar.querySelector('[data-a=sel-all]');
    selAllBtn.querySelector('.material-symbols-rounded').textContent = allSelected ? 'deselect' : 'select_all';
    selAllBtn.setAttribute('aria-label', allSelected ? 'Снять выбор' : 'Выбрать все');

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
  }

  /* ---------- состояние ---------- */
  let notes = [];
  let folders = [];
  /* id скрытых папок: их заметки не показываем, пока не разблокировано */
  const hiddenFolderIds = new Set();
  /* Пока false — скрытые папки и их заметки в общем списке не видны. Ставится
     в true после системной разблокировки (протяжка вниз у самого верха). */
  let hiddenUnlocked = false;
  let filterColor = null, filterFolder = null;
  let chipEl = null;

  const noteInHiddenFolder = n => !!(n.folderId && hiddenFolderIds.has(n.folderId));

  /* Папки для списков выбора («В папку»): скрытые не показываем, пока не открыто,
     иначе их названия проступали бы в обычных меню. */
  const selectableFolders = async () =>
    (await Store.folders()).filter(f => !f.deleted && (hiddenUnlocked || !f.hidden));

  const searchInput = searchWrap.querySelector('input');
  const searchField = searchWrap.querySelector('.searchfield');
  const searchClear = searchWrap.querySelector('.search-clear');
  const searchCancel = searchWrap.querySelector('.search-cancel');

  async function load() {
    [notes, folders] = await Promise.all([Store.notes(), Store.folders()]);
    notes = notes.filter(n => !n.deleted)
      .sort((a, b) => (b.pinned ? 1 : 0) - (a.pinned ? 1 : 0) || b.updatedAt - a.updatedAt);
    folders = folders.filter(f => !f.deleted);
    hiddenFolderIds.clear();
    for (const f of folders) if (f.hidden) hiddenFolderIds.add(f.id);
    /* Скрытая папка могла исчезнуть (удалена или снова скрыта) — снимаем фильтр,
       иначе список остался бы пустым без видимой причины. */
    if (filterFolder && !folders.some(f => f.id === filterFolder && (hiddenUnlocked || !f.hidden)))
      filterFolder = null;
    refresh();
  }

  /* После правки списка на «Скрытых» главная под нами тоже должна перечитать
     заметки — она слушает то же событие; на главной достаточно своего load(). */
  const reload = () => {
    if (hiddenMode) document.dispatchEvent(new CustomEvent('notes-changed'));
    else load();
  };

  /* ---------- категории папок под строкой поиска ----------
     Показываем только реально существующие папки. Скрытые в список не попадают,
     пока не пройдена системная разблокировка. Ряд нужен лишь тогда, когда есть
     куда переключаться: одна кнопка «Все» ничего не фильтрует. */
  function renderFolderChips() {
    if (!folderChips) return;
    const visible = folders.filter(f => hiddenUnlocked || !f.hidden);
    folderChips.innerHTML = '';
    folderChips.classList.toggle('hidden', visible.length === 0);
    if (!visible.length) return;

    const mk = (label, id, hidden) => {
      const b = document.createElement('button');
      b.className = 'folder-tab' + ((filterFolder || null) === id ? ' active' : '');
      b.dataset.f = id || '';
      if (hidden) b.classList.add('is-hidden');
      b.innerHTML = `${hidden ? '<span class="material-symbols-rounded">lock</span>' : ''}${escapeHtml(label)}`;
      return b;
    };
    folderChips.appendChild(mk('Все', null, false));
    visible.forEach(f => folderChips.appendChild(mk(f.name, f.id, f.hidden)));
  }

  folderChips?.addEventListener('click', e => {
    const b = e.target.closest('.folder-tab'); if (!b) return;
    filterFolder = b.dataset.f || null;
    refresh();
  });

  function renderChip() {
    if (chipEl) { chipEl.remove(); chipEl = null; }
    /* Папку теперь показывает активный чип в ряду категорий, поэтому плавающий
       чип оставляем только под фильтр цвета. */
    if (filterColor) {
      const c = COLORS.find(c => c.key === filterColor);
      chipEl = document.createElement('button');
      chipEl.className = 'cat-chip';
      chipEl.innerHTML = `<span class="material-symbols-rounded">palette</span>${c ? c.label : 'Цвет'}<span class="material-symbols-rounded" style="font-size:16px">close</span>`;
      chipEl.addEventListener('click', () => { filterColor = null; refresh(); });
      searchWrap.after(chipEl);
    }
  }

  /* ---------- сжимающаяся шапка при прокрутке списка ----------
     Список прокручен вниз → строка с крупным заголовком схлопывается, а сам
     заголовок уменьшается и поднимается на линию иконок аппбара. */
  let hdShrunk = false, hdRaf = 0;

  /* Сдвиг заголовка к линии иконок. Ширину текста в сжатом кегле иначе не
     вычислить — измеряем её, временно применив кегль (кадр не рисуется). */
  function measureShrinkShift() {
    const h1 = h1row.querySelector('h1');
    const prev = h1.style.fontSize;
    h1.style.fontSize = '26px';
    const textW = h1.offsetWidth;               /* offsetWidth игнорирует transform */
    h1.style.fontSize = prev;
    el.style.setProperty('--h1-x', Math.max(0, (h1row.clientWidth - textW) / 2) + 'px');

    /* Вертикаль: центр заголовка в схлопнутой строке совпадает с центром иконок
       аппбара. Высоту строки после схлопывания берём из кегля и line-height,
       чтобы результат не зависел от незавершённого перехода. */
    const iconSel = el.classList.contains('selecting')
      ? '[data-a=sel-close]'
      : (hiddenMode ? '[data-a=back]' : '[data-a=folders]');
    const icon = appbar.querySelector(iconSel);
    const iconMid = icon.getBoundingClientRect().top + icon.offsetHeight / 2;
    const rowTop = h1row.getBoundingClientRect().top;   /* верх строки = низ аппбара */
    el.style.setProperty('--h1-y', (iconMid - (rowTop + 26 * 1.12 / 2)) + 'px');
  }

  function syncHeaderShrink() {
    const on = !el.classList.contains('searching') && gridHolder.scrollTop > 12;
    if (on === hdShrunk) return;
    hdShrunk = on;
    if (on) measureShrinkShift();
    el.classList.toggle('hd-shrink', on);
  }

  gridHolder.addEventListener('scroll', () => {
    if (hdRaf) return;
    hdRaf = requestAnimationFrame(() => { hdRaf = 0; syncHeaderShrink(); });
  }, { passive: true });
  window.addEventListener('resize', () => { if (hdShrunk) measureShrinkShift(); });

  /* ---------- поиск: при фокусе строка поднимается наверх экрана ---------- */
  function syncSearchLift() {
    const on = document.activeElement === searchInput || !!searchInput.value.trim();
    /* В поиске шапка уезжает вверх целиком — сжатое состояние сбрасываем, чтобы
       --hd-up (высота шапки) считалась по полной строке заголовка. */
    if (on && hdShrunk) { hdShrunk = false; el.classList.remove('hd-shrink'); }
    /* Высота шапки, которую строка поиска «переезжает»: аппбар + строка
       заголовка в развёрнутом виде. Высоту строки берём из CSS-переменной, а не
       из offsetHeight: сразу после снятия .hd-shrink строка ещё «доезжает»
       переходом, и layout вернул бы схлопнутую высоту — строка поиска встала бы
       не на место, а заметки получили бы неверный подъём. */
    if (on) {
      const rowH = parseFloat(getComputedStyle(el).getPropertyValue('--h1-row-h')) || 67;
      el.style.setProperty('--hd-up', (appbar.offsetHeight + rowH) + 'px');
    }
    el.classList.toggle('searching', on);
    /* Пока запрос пуст — заметки скрыты под серым холстом; с вводом текста
       результаты поднимаются над холстом. */
    el.classList.toggle('search-filled', !!searchInput.value.trim());
    /* Во время поиска верх экрана занимает серый холст — держим системные
       зоны в его цвете (цвет берём из CSS, а не дублируем константой). */
    if (on) setEdgeColor(getComputedStyle(searchCanvas).backgroundColor);
    else refreshEdgeColor();
  }

  /* Закрытие поиска тем же свайпом «назад» — возврат на главную страницу */
  function closeSearch() {
    searchInput.value = '';
    searchInput.blur();
    searchCancel.classList.add('hidden');
    el.style.setProperty('--search-dx', '0px');
    el.classList.remove('search-drag');
    syncSearchLift();
    refresh();
  }

  searchInput.addEventListener('focus', () => { searchCancel.classList.remove('hidden'); syncSearchLift(); });
  searchInput.addEventListener('blur', () => setTimeout(() => {
    if (!searchInput.value.trim()) searchCancel.classList.add('hidden');
    syncSearchLift();
  }, 160));
  searchCancel.addEventListener('click', closeSearch);
  searchInput.addEventListener('input', syncSearchLift);
  searchInput.addEventListener('input', debounce(refresh, 100));
  searchClear.addEventListener('click', () => { searchInput.value = ''; refresh(); });

  if (hiddenMode) {
    appbar.querySelector('[data-a=back]').addEventListener('click', () => Nav.pop());
  } else {
    appbar.querySelector('[data-a=folders]').addEventListener('click', () => {
      const foldersEl = openFolders();
      Nav.pushEl(foldersEl);
    });
    appbar.querySelector('[data-a=trash]').addEventListener('click', () => Nav.pushEl(openTrashScreen()));
  }
  appbar.querySelector('[data-a=sel-close]').addEventListener('click', exitSelection);
  appbar.querySelector('[data-a=sel-all]').addEventListener('click', () => {
    const list = selectionList();
    const allSelected = list.length > 0 && list.every(n => sel.set.has(n.id));
    if (allSelected) sel.set.clear();
    else list.forEach(n => sel.set.add(n.id));
    /* На «Скрытых» кнопка видна всегда: если выбор ещё не начат, она его включает */
    if (hiddenMode && !sel.active && !allSelected && list.length) { sel.active = true; enterSelection(); }
    refresh();
  });
  fab.addEventListener('click', () => hiddenMode
    ? openEditor(null, { hidden: true })
    : openEditor(null, { folderId: filterFolder }));

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
      toast(target ? 'Закреплено' : 'Откреплено'); exitSelection(); reload();
    } else if (act === 'move') {
      const folders = await selectableFolders();
      sheetSections({ title: 'В папку', sections: [api => {
        const box = document.createElement('div'); box.className = 'sheet-actions';
        box.innerHTML = [`<button class="sheet-action" data-f=""><span class="material-symbols-rounded">block</span>Без папки</button>`,
          ...folders.map(f => `<button class="sheet-action" data-f="${f.id}"><span class="material-symbols-rounded">folder</span>${escapeHtml(f.name)}</button>`)].join('');
        box.addEventListener('click', async ev => {
          const btn = ev.target.closest('[data-f]'); if (!btn) return;
          for (const id of ids) { const nn = notes.find(x => x.id === id); if (nn) { nn.folderId = btn.dataset.f || null; await Store.saveNote(nn); } }
          toast('Перемещено'); api.close(); exitSelection(); reload();
        });
        return box;
      }]});
    } else if (act === 'delete') {
      if (await confirmSheet({ title: 'Удалите заметки', text: ids.length === 1 ? 'Удалить эту заметку?' : `Удалить ${ids.length} заметок?` })) {
        for (const id of ids) { const nn = notes.find(x => x.id === id); if (nn) { nn.deleted = true; await Store.saveNoteRaw(nn); } }
        toast('Заметки удалены'); exitSelection(); reload();
      }
    } else if (act === 'hide') {
      const picked = ids.map(id => notes.find(x => x.id === id)).filter(Boolean);
      /* скрытые заметки можно вернуть: если всё выбранное уже скрыто — показываем */
      const target = !(picked.length > 0 && picked.every(x => x.hidden));
      for (const nn of picked) { nn.hidden = target; await Store.saveNoteRaw(nn); }
      toast(target ? 'Скрыто' : 'Показано'); exitSelection(); reload();
    }
  });

  function longPressMenu(n, card) {
    attachMenu(card, [
      /* На «Скрытых» в том же меню первым пунктом — открытый замок: заметка
         возвращается в обычный список. */
      ...(hiddenMode ? [{ icon: 'lock_open', label: 'Отменить скрытие', onClick: async () => { n.hidden = false; await Store.saveNoteRaw(n); toast('Скрытие отменено'); reload(); } }] : []),
      { icon: 'palette', label: 'Цвет', onClick: () => colorSheetFor(n) },
      { icon: 'folder',  label: 'В папку', onClick: () => folderSheetFor(n) },
      { icon: 'push_pin', label: n.pinned ? 'Открепить' : 'Закрепить', checked: !!n.pinned, onClick: async () => { n.pinned = !n.pinned; await Store.saveNote(n); toast(n.pinned ? 'Закреплено' : 'Откреплено'); reload(); } },
      { icon: 'content_copy', label: 'Копировать', onClick: async () => { const c = { ...n, id: uid(), createdAt: Date.now(), updatedAt: Date.now() }; await Store.saveNote(c); toast('Скопировано'); reload(); } },
      { divider: true },
      { icon: 'delete', label: 'Удалить', onClick: async () => {
        if (await confirmSheet({ title: 'Удалите заметки', text: 'Удалить эту заметку?' })) { await Store.delNote(n.id); toast('Заметка удалена'); reload(); }
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
        await Store.saveNote(n); api.close(); reload();
      }));
      return row;
    }]});
  }

  async function folderSheetFor(n) {
    const folders = await selectableFolders();
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
        await Store.saveNote(n); toast('Перемещено'); api.close(); reload();
      });
      return box;
    }]});
  }

  /* ---------- скрытые папки: протяжка экрана вниз у самого верха ----------
     Как в MIUI: длинная протяжка списка за верхний край вызывает системную
     проверку личности, и только после неё появляются скрытые папки. Своего
     экрана не рисуем — диалог (ПИН/графический ключ/биометрия) показывает сама
     система через WebAuthn.

     Проверку запускаем на отпускании пальца, а не во время движения: системный
     диалог WebAuthn требует «короткого действия пользователя», и это действие
     даёт именно touchend (touchmove таким действием не считается). */
  const HIDDEN_PULL_PX = 72;   /* путь пальца, после которого проверка готова */
  const HIDDEN_HINT_PX = 68;   /* на сколько выезжает подсказка-замок */
  let hiddenPull = null;

  /* Подсказка-замок. Без неё протяжка выглядела как «ничего не происходит»:
     единственным откликом был системный диалог, который появляется уже на
     отпускании. Теперь замок выезжает из-под шапки вслед за пальцем и меняется
     на «Отпустите», когда порог пройден. */
  const pullHint = document.createElement('div');
  pullHint.className = 'hidden-pull';
  pullHint.innerHTML = `
    <div class="hidden-pull-pill">
      <span class="material-symbols-rounded">lock</span>
      <span class="lbl">Потяните вниз</span>
    </div>`;
  /* На самой странице «Скрытые» протяжка не нужна — подсказку туда не ставим */
  if (!hiddenMode) el.append(pullHint);

  const pullIcon = pullHint.querySelector('.material-symbols-rounded');
  const pullLabel = pullHint.querySelector('.lbl');
  let hintReady = false;

  /* Порог пройден — меняем замок на открытый и зовём отпустить палец.
     Переписываем подписи только в момент переключения, а не каждый кадр. */
  function setHintReady(ready) {
    if (ready === hintReady) return;
    hintReady = ready;
    pullHint.classList.toggle('ready', ready);
    pullIcon.textContent = ready ? 'lock_open' : 'lock';
    pullLabel.textContent = ready ? 'Отпустите' : 'Потяните вниз';
  }

  function placePullHint() {
    /* Верх подсказки совпадает с верхом списка: замок «выезжает» из-под шапки
       ровно там, где открывается полоса фона. Берём offsetTop, а не
       getBoundingClientRect: последний вернул бы позицию, сдвинутую текущей
       протяжкой («резинка» ещё возвращается), и замок встал бы не на место. */
    pullHint.style.top = gridHolder.offsetTop + 'px';
  }

  function paintPullHint(dist) {
    const t = Math.max(0, Math.min(1, dist / HIDDEN_PULL_PX));
    pullHint.style.setProperty('--hidden-pull', (t * HIDDEN_HINT_PX).toFixed(1) + 'px');
    pullHint.style.opacity = Math.min(1, t * 2.2).toFixed(2);
    setHintReady(t >= 1);
  }

  function hidePullHint() {
    setHintReady(false);
    pullHint.classList.remove('pulling');
    pullHint.style.opacity = '0';
    pullHint.style.setProperty('--hidden-pull', '0px');
  }

  /* Жест доступен всегда, пока скрытое не открыто: проверка вызывается самим
     протягиванием, независимо от того, есть ли уже скрытые папки. Иначе при
     первой попытке (скрытых папок ещё нет) жест молча ничего не делал. */
  const canUnlockHidden = () => !hiddenMode && !hiddenUnlocked && !sel.active
    && !el.classList.contains('searching');

  async function unlockHidden() {
    if (hiddenUnlocked) return;
    if (!systemUnlockAvailable()) {
      toast('Системная проверка недоступна: нужен HTTPS или localhost');
      return;
    }
    /* Любая неожиданная ошибка обязана дойти до пользователя: иначе протяжка
       выглядела бы как «ничего не произошло» (так было с прежней версией). */
    let ok = false;
    try {
      ok = await systemUnlock();
    } catch (e) {
      toast('Ошибка системной проверки: ' + ((e && e.name) || e));
      return;
    }
    if (ok) {
      hiddenUnlocked = true;
      refresh();
      /* Разблокировано — переходим на отдельную страницу «Скрытые», а не
         показываем скрытое в общем списке. */
      Nav.pushEl(hiddenScreen().el);
    } else {
      toast(`Не удалось подтвердить${systemUnlockError() ? ': ' + systemUnlockError() : ''}`);
    }
  }

  /* Слушаем весь экран, а не только сетку: протянуть можно и начав с любой
     точки над заметками. Условие одно — список находится в самом верху. */
  function beginHiddenPull(y) {
    hiddenPull = { y, ready: false };
    placePullHint();
    pullHint.classList.add('pulling');
    paintPullHint(0);
  }

  el.addEventListener('touchstart', e => {
    if (canUnlockHidden() && e.touches.length === 1 && gridHolder.scrollTop <= 0) {
      beginHiddenPull(e.touches[0].clientY);
    } else {
      hiddenPull = null;
    }
  }, { passive: true });

  el.addEventListener('touchmove', e => {
    if (e.touches.length !== 1) return;
    const y = e.touches[0].clientY;
    /* Список мог доехать до верха уже в процессе прокрутки — тогда отсчёт
       начинаем с этого места, как и «резинка» (см. scroll-fx.js). */
    if (!hiddenPull) {
      if (canUnlockHidden() && gridHolder.scrollTop <= 0) beginHiddenPull(y);
      return;
    }
    /* Список уехал от верха — это обычная прокрутка, а не вытягивание. */
    if (gridHolder.scrollTop > 0) { hiddenPull = null; hidePullHint(); return; }
    const dist = y - hiddenPull.y;
    if (dist < 0) return;
    hiddenPull.ready = dist >= HIDDEN_PULL_PX;
    paintPullHint(dist);
  }, { passive: true });

  /* Отпускание: если порог пройден — вызываем системную проверку. Именно
     touchend, а не touchmove: диалогу WebAuthn нужно «короткое действие
     пользователя», каковым движение пальца не считается. */
  el.addEventListener('touchend', () => {
    const ready = hiddenPull && hiddenPull.ready;
    hiddenPull = null;
    hidePullHint();
    if (ready) unlockHidden();
  }, { passive: true });
  el.addEventListener('touchcancel', () => { hiddenPull = null; hidePullHint(); }, { passive: true });

  /* выход из режима выбора по системной «назад» */
  el.addEventListener('app-exit-selection', exitSelection);
  /* закрытие поиска тем же свайпом «назад» */
  el.addEventListener('app-exit-search', closeSearch);

  document.addEventListener('notes-changed', () => { if (document.body.contains(el)) load(); });
  /* Папку скрыли/показали/переименовали на экране «Папки» — перечитываем их.
     Без этого кэш папок оставался прежним, и протяжка вниз (вход в скрытое)
     молча ничего не делала: главный экран ещё «не знал» о скрытой папке. */
  document.addEventListener('folders-changed', () => { if (document.body.contains(el)) load(); });
  load();

  return { el, load };
}
