/* ============ Главный экран «Заметки» ============ */
import { Store, escapeHtml, htmlToPlain, debounce, uid, bindPress, Nav, vibrate } from './core.js';
import { setEdgeColor, refreshEdgeColor } from './edge.js';
import { toast, confirmSheet, attachMenu, sheetSections } from './components/ui.js';
import { openEditor } from './screens/editor.js';
import { openFolders } from './screens/folders.js';
import { renderNotesGrid } from './screens/grid.js';
import { openTrashScreen } from './screens/trash.js';
import { verifyIdentity } from './auth.js';

const COLORS = [
  { key: 'none', label: 'Без цвета', css: '#ffffff' },
  { key: 'yellow', label: 'Жёлтая', css: '#f3b13c' },
  { key: 'blue', label: 'Синяя', css: '#1a4a76' },
  { key: 'paper', label: 'Бумага', css: '#fbf3de' },
  { key: 'pink', label: 'Розовая', css: '#ec6d94' },
  { key: 'red', label: 'Красная', css: '#e05555' },
];

export function mainScreen(opts = {}) {
  /* Один и тот же экран в двух режимах: обычные «Заметки» и личная папка
     «Скрытые» (только заметки с флагом hidden). Разметка, поиск и режим
     выбора общие — меняются подпись и набор заметок. */
  const isPrivate = opts.mode === 'hidden';
  const TITLE = isPrivate ? 'Скрытые' : 'Заметки';

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
  h1row.innerHTML = `<h1>${TITLE}</h1>`;

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

  /* светлый холст, перекрывающий интерфейс во время поиска */
  const searchCanvas = document.createElement('div');
  searchCanvas.className = 'search-canvas';

  /* чипы фильтров режима выбора */
  const chipsRow = document.createElement('div');
  chipsRow.className = 'chips-row hidden';
  chipsRow.innerHTML = `
    <button class="chip active" data-c="all">Все</button>
    <button class="chip" data-c="notes">Заметки</button>
    <button class="chip" data-c="nocat">Без категории</button>`;

  const fab = document.createElement('button');
  fab.className = 'fab';
  fab.innerHTML = `<span class="material-symbols-rounded">add</span>`;

  const tabbar = document.createElement('div');
  tabbar.className = 'tabbar';
  tabbar.innerHTML = `
    <button class="tab active"><span class="material-symbols-rounded">edit_note</span><span class="lbl">${TITLE}</span></button>`;

  /* нижняя панель режима выбора: Скрыть | Закрепить | Переместить | Удалить */
  const selBar = document.createElement('div');
  selBar.className = 'select-bar hidden';
  selBar.innerHTML = `
    <button data-sel="hide"><span class="material-symbols-rounded">lock</span><span class="lbl">Скрыть</span></button>
    <button data-sel="pin"><span class="material-symbols-rounded">push_pin</span><span class="lbl">Закрепить</span></button>
    <button data-sel="move"><span class="material-symbols-rounded">drive_file_move</span><span class="lbl">Переместить</span></button>
    <button data-sel="delete"><span class="material-symbols-rounded">delete</span><span class="lbl">Удалить</span></button>`;

  el.append(appbar, h1row, searchWrap, searchCanvas, chipsRow, gridHolder, fab, tabbar, selBar);

  /* Слой протяжки вниз (только на главной): пока список оттянут за верхний
     край, поверх проявляется замок с подсказкой. Живёт в разметке всегда —
     показывает/скрывает его JS через --pp. */
  let pullEl = null;
  if (!isPrivate) {
    pullEl = document.createElement('div');
    pullEl.className = 'pull-private';
    pullEl.innerHTML = `
      <div class="pp-icon"><span class="material-symbols-rounded">lock</span></div>
      <div class="pp-text">Отпустите, чтобы открыть личную папку</div>`;
    el.appendChild(pullEl);
  }

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
      if (selFilter === 'notes') list = list.filter(n => (n.title || '').trim() || htmlToPlain(n.content));
      if (selFilter === 'nocat') list = list.filter(n => !n.folderId);
    } else {
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
    renderChip();
    renderSelBar();
    /* Прокрутка восстановлена — приводим шапку в соответствие */
    syncHeaderShrink();
  }

  /* заметки, попадающие под текущий фильтр режима выбора */
  function selectionList() {
    const q = searchInput.value.trim().toLowerCase();
    let list = notes;
    if (selFilter === 'notes') list = list.filter(n => (n.title || '').trim() || htmlToPlain(n.content));
    if (selFilter === 'nocat') list = list.filter(n => !n.folderId);
    if (q) list = list.filter(n => (n.title || '').toLowerCase().includes(q) || htmlToPlain(n.content).toLowerCase().includes(q));
    return list;
  }

  function renderSelBar() {
    selBar.classList.toggle('hidden', !sel.active);
    const n = sel.set.size;
    h1row.querySelector('h1').textContent = !sel.active ? TITLE : (n ? `Выбрано ${n} объект${n % 10 === 1 && n % 100 !== 11 ? '' : (n % 10 >= 2 && n % 10 <= 4 && (n % 100 < 12 || n % 100 > 14) ? 'а' : 'ов')}` : 'Выбрано');
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
    /* Набор заметок задаёт режим: главная показывает всё, кроме скрытых,
       личная папка — только скрытые. Поэтому скрытые никогда не попадают
       на главный экран. */
    notes = (await Store.notes())
      .filter(n => !n.deleted && (isPrivate ? n.hidden : !n.hidden))
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
    const icon = appbar.querySelector(el.classList.contains('selecting') ? '[data-a=sel-close]' : '[data-a=folders]');
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
  /* В личной папке новая заметка сразу скрытая — иначе она бы ушла на главную
     и не появилась бы здесь. */
  fab.addEventListener('click', () => openEditor(null, { folderId: filterFolder, hidden: isPrivate }));

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

  /* ---------- протяжка вниз → личная папка (только на главной) ----------
     Список тянем за верхний край той же «резинкой», что и везде (--pull-y
     ведёт scroll-fx). Пока натяжение растёт, поверх плавно проявляется замок
     с подсказкой. Отпустили дальше порога — системная проверка личности, и при
     успехе экран «Скрытые» въезжает обычной анимацией (как любой другой экран). */
  if (pullEl) {
    const PULL_THRESHOLD = 120;   /* насколько оттянут край, чтобы сработало */
    let startY = 0, tracking = false, active = false;

    const pullValue = () => parseFloat(gridHolder.style.getPropertyValue('--pull-y')) || 0;

    /* Спрятать замок: включаем переход (класс .animate) и гасим прогресс —
       подсказка мягко растворяется, а «резинка» списка возвращается сама. */
    function hidePull() {
      pullEl.classList.add('animate');
      pullEl.classList.remove('on', 'ready');
      pullEl.style.setProperty('--pp', '0');
    }

    gridHolder.addEventListener('touchstart', e => {
      if (e.touches.length !== 1) return;
      /* В поиске и в режиме выбора протяжка не открывает личную папку. */
      if (el.classList.contains('searching') || el.classList.contains('selecting')) return;
      tracking = gridHolder.scrollTop <= 0;
      startY = e.touches[0].clientY;
      active = false;
    }, { passive: true });

    gridHolder.addEventListener('touchmove', e => {
      if (!tracking || e.touches.length !== 1) return;
      const dy = e.touches[0].clientY - startY;
      if (dy <= 0 || gridHolder.scrollTop > 0) return;
      active = true;
      const y = pullValue();
      const p = Math.min(1, y / PULL_THRESHOLD);
      pullEl.classList.remove('animate');   /* внутри жеста — строго за пальцем */
      pullEl.style.setProperty('--pp', p.toFixed(3));
      pullEl.classList.add('on');
      pullEl.classList.toggle('ready', y >= PULL_THRESHOLD);
    }, { passive: true });

    gridHolder.addEventListener('touchend', async () => {
      if (!active) return;
      active = false;
      const reached = pullValue() >= PULL_THRESHOLD;
      hidePull();
      if (!reached) return;
      if (await verifyIdentity()) openPrivate();
    });

    gridHolder.addEventListener('touchcancel', () => {
      if (!active) return;
      active = false;
      hidePull();
    }, { passive: true });
  }

  /* Открыть личную папку «Скрытые» обычной навигацией (слайд справа). */
  function openPrivate() {
    const s = mainScreen({ mode: 'hidden' });
    Nav.pushEl(s.el);
  }

  /* выход из режима выбора по системной «назад» */
  el.addEventListener('app-exit-selection', exitSelection);
  /* закрытие поиска тем же свайпом «назад» */
  el.addEventListener('app-exit-search', closeSearch);

  document.addEventListener('notes-changed', () => { if (document.body.contains(el)) load(); });
  load();

  return { el, load };
}
