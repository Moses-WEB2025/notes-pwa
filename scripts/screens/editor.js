/* ============ Редактор заметки ============ */
import { Store, Nav, fmtEditorDate, escapeHtml, uid, debounce, vibrate } from '../core.js';
import { refreshEdgeColor } from '../edge.js';
import { morphOpen, morphClose, morphDisabled, cardOnScreen, morphPhase } from '../morph.js';
import { toast, attachMenu, sheetSections, openSheet, confirmSheet } from '../components/ui.js';
import { openRecorder } from '../components/recorder.js';
import { openDrawing } from '../components/drawing.js';
import { openImageEditor } from '../components/image-editor.js';

/* Цветовые темы холста */
const COLORS = [
  { key: 'none', css: null },
  { key: 'yellow', css: '#f3b13c' },
  { key: 'blue', css: '#1a4a76' },
  { key: 'paper', css: '#fbf3de' },
  { key: 'pink', css: '#ec6d94' },
  { key: 'red', css: '#e05555' },
];

/* яркость 0..255, выбор чёрного/белого текста по WCAG */
function lum(hex) {
  const h = hex.replace('#', '');
  const r = parseInt(h.slice(0, 2), 16), g = parseInt(h.slice(2, 4), 16), b = parseInt(h.slice(4, 6), 16);
  return 0.2126 * r + 0.7152 * g + 0.0722 * b;
}
/* Порог яркости, ниже которого на фоне нужен светлый текст.
   Жёлтая (#f3b13c, ~183) заметно светлее синей/розовой/красной, но на
   карточках у неё уже белый текст — при пороге 160 редактор оставлял на ней
   чёрный заголовок и серую дату, и заметка выглядела чужой. 200 выравнивает
   жёлтую с остальными цветными темами, «Бумага» (~243) остаётся тёмной. */
const LIGHT_TEXT_MAX = 200;
function textColorFor(bg) { return bg && lum(bg) < LIGHT_TEXT_MAX ? '#ffffff' : 'var(--ink, #1c1d21)'; }
function mutedFor(bg)     { return bg && lum(bg) < LIGHT_TEXT_MAX ? 'rgba(255,255,255,.62)' : 'var(--text-tertiary)'; }
function caretFor(bg)     { return bg && lum(bg) < LIGHT_TEXT_MAX ? 'rgba(255,255,255,.9)' : 'var(--accent)'; }
function folderChipFor(bg){ return bg && lum(bg) < LIGHT_TEXT_MAX ? 'rgba(255,255,255,.16)' : 'var(--surface2)'; }

export function openEditor(id, opts = {}) {
  const isNew = !id;
  const el = document.createElement('div');
  el.className = 'screen editor-screen';

  /* ===== Верхняя панель: back | share, palette, more ===== */
  const topbar = document.createElement('div');
  topbar.className = 'editor-topbar';
  topbar.innerHTML = `
    <button class="icon-btn" data-a="back"><span class="material-symbols-rounded">arrow_back</span></button>
    <span class="spacer"></span>
    <button class="icon-btn" data-a="share"><span class="material-symbols-rounded">ios_share</span></button>
    <button class="icon-btn" data-a="palette"><span class="material-symbols-rounded">palette</span></button>
    <button class="icon-btn" data-a="more"><span class="material-symbols-rounded">more_vert</span></button>`;

  /* ===== Верхняя панель при открытой клавиатуре: back | undo, redo, check ===== */
  const topbarKb = document.createElement('div');
  topbarKb.className = 'editor-topbar kb-mode hidden';
  topbarKb.innerHTML = `
    <button class="icon-btn" data-a="back2"><span class="material-symbols-rounded">arrow_back</span></button>
    <span class="spacer"></span>
    <button class="icon-btn" data-a="undo" disabled><span class="material-symbols-rounded">undo</span></button>
    <button class="icon-btn" data-a="redo" disabled><span class="material-symbols-rounded">redo</span></button>
    <button class="icon-btn" data-a="done"><span class="material-symbols-rounded">check</span></button>`;

  /* ===== тело ===== */
  const bodyWrap = document.createElement('div');
  bodyWrap.className = 'editor-body';

  const folderChip = document.createElement('button');
  folderChip.className = 'folder-chip';
  folderChip.innerHTML = `<span class="material-symbols-rounded">folder_open</span><span class="fc-label">Без категории</span>`;

  const titleInput = document.createElement('input');
  titleInput.className = 'note-title-input';
  titleInput.placeholder = 'Заголовок';
  titleInput.autocomplete = 'off';

  const meta = document.createElement('div');
  meta.className = 'meta-row';

  const content = document.createElement('div');
  content.className = 'note-content-input';
  content.contentEditable = 'true';
  content.setAttribute('data-placeholder', 'Начните ввод');

  bodyWrap.append(folderChip, titleInput, meta, content);

  /* ===== нижний тулбар: набор и порядок иконок как на скриншоте 6 ===== */
  const toolbar = document.createElement('div');
  toolbar.className = 'format-toolbar';
  toolbar.innerHTML = `
    <button type="button" class="tool" data-tool="audio" aria-label="Аудио"><span class="material-symbols-rounded">graphic_eq</span></button>
    <button type="button" class="tool" data-tool="image" aria-label="Фото"><span class="material-symbols-rounded">image</span></button>
    <button type="button" class="tool" data-tool="draw" aria-label="Рисунок"><span class="material-symbols-rounded">gesture</span></button>
    <button type="button" class="tool" data-tool="check" aria-label="Чек-лист"><span class="material-symbols-rounded">check_box_outline_blank</span></button>
    <button type="button" class="tool" data-tool="heading" aria-label="Заголовок"><span class="material-symbols-rounded">title</span></button>
    <button type="button" class="tool" data-tool="bold" aria-label="Жирный"><span class="material-symbols-rounded">format_bold</span></button>
    <button type="button" class="tool" data-tool="italic" aria-label="Курсив"><span class="material-symbols-rounded">format_italic</span></button>
    <button type="button" class="tool" data-tool="list" aria-label="Список"><span class="material-symbols-rounded">format_list_bulleted</span></button>`;

  el.append(topbar, topbarKb, bodyWrap, toolbar);

  /* ---------- Состояние ---------- */
  let note = null;
  let foldersList = [];
  /* история изменений: список снимков {title, html}; histIndex — текущая позиция */
  let history = [], histIndex = -1, suppress = false;
  let players = [];
  let stopAudio = () => {};

  (async () => {
    foldersList = await Store.folders();
    if (id) {
      note = await Store.note(id);
      if (!note) { toast('Заметка не найдена'); Nav.pop(); return; }
    } else {
      note = {
        id: uid(), title: '', content: '', color: null, folderId: opts.folderId || null,
        pinned: false, hidden: !!opts.hidden, audio: null, createdAt: Date.now(), updatedAt: Date.now(),
      };
    }
    titleInput.value = note.title || '';
    const c = note.content || '';
    content.innerHTML = /[<>]/.test(c) ? c : c.split('\n').join('<br>');
    await hydrateImages();
    syncCheckboxes();
    updFolderChip(); updMeta(); renderPlayers(); applyTheme();
    history = [snapshot()]; histIndex = 0; updUndoBtns();
    baseline = signature();
  })();

  /* ---------- Тема холста (цвет + автоконтраст текста) ---------- */
  /* Красим холст по ключу цвета. Отдельная функция нужна, чтобы успеть
     применить цвет ДО первого кадра: фон экрана, переменные контраста и
     системные зоны (верхняя полоса) выставляются синхронно, в том числе пока
     заметка ещё грузится из базы. */
  function paintCanvas(colorKey) {
    const c = COLORS.find(c => c.key === (colorKey || 'none'));
    const bg = c && c.css;
    el.style.background = bg || 'var(--bg)';
    el.style.setProperty('--ed-ink', textColorFor(bg));
    el.style.setProperty('--ed-muted', mutedFor(bg));
    el.style.setProperty('--ed-caret', caretFor(bg));
    el.style.setProperty('--ed-chip', folderChipFor(bg));
    /* Смена цвета холста — сразу перекрашиваем и системные зоны. */
    refreshEdgeColor();
  }

  function applyTheme() { paintCanvas(note.color); }

  function updFolderChip() {
    const f = foldersList.find(f => f.id === note.folderId);
    folderChip.querySelector('.fc-label').textContent = f ? f.name : 'Без категории';
  }
  function updMeta() {
    const chars = (content.textContent || '').replace(/\s/g, '').length;
    meta.innerHTML = `<span>${escapeHtml(fmtEditorDate(note.updatedAt))}</span><span class="dot"></span><span>${chars} символов</span>`;
  }

  /* ---------- Служебные ---------- */
  function snapshot() { return { title: titleInput.value, html: content.innerHTML }; }
  function syncCheckboxes() {
    content.querySelectorAll('.check-item input[type="checkbox"]').forEach(cb => {
      cb.closest('.check-item')?.classList.toggle('checked', cb.checked);
    });
  }
  /* Восстанавливаем «фото» из IndexedDB: в сохранённом HTML лежат data-blob ссылки,
     а src (blob:-URL) после перезапуска недействителен. */
  async function hydrateImages() {
    const imgs = content.querySelectorAll('img[data-blob]');
    for (const img of imgs) {
      const bid = img.getAttribute('data-blob');
      if (!bid) continue;
      const blob = await Store.blob(bid);
      if (blob) img.src = URL.createObjectURL(blob);
    }
  }

  /* ---------- Undo/Redo (история снимков) ---------- */
  const undoBtn = topbarKb.querySelector('[data-a=undo]');
  const redoBtn = topbarKb.querySelector('[data-a=redo]');
  /* Зафиксировать текущее состояние как новую вершину истории. Вызывать ПОСЛЕ изменения. */
  function commit() {
    if (suppress) return;
    const s = snapshot();
    const cur = history[histIndex];
    if (cur && cur.html === s.html && cur.title === s.title) return; /* нет изменений */
    history = history.slice(0, histIndex + 1);
    history.push(s);
    if (history.length > 100) history.shift();
    histIndex = history.length - 1;
    updUndoBtns();
  }
  function updUndoBtns() {
    undoBtn.disabled = histIndex <= 0;
    redoBtn.disabled = histIndex >= history.length - 1;
  }
  function applyState(s) {
    suppress = true; titleInput.value = s.title; content.innerHTML = s.html; suppress = false;
    syncCheckboxes();
    if (selImg && !content.contains(selImg)) clearImageSelection();
    updMeta(); scheduleSave();
  }
  function doUndo() {
    if (histIndex <= 0) return;
    histIndex--; applyState(history[histIndex]); updUndoBtns();
  }
  function doRedo() {
    if (histIndex >= history.length - 1) return;
    histIndex++; applyState(history[histIndex]); updUndoBtns();
  }
  undoBtn.addEventListener('click', doUndo);
  redoBtn.addEventListener('click', doRedo);

  /* ввод */
  const typed = debounce(() => { commit(); updMeta(); scheduleSave(); }, 350);
  titleInput.addEventListener('input', typed);
  content.addEventListener('input', typed);
  content.addEventListener('pointerdown', e => {
    const cb = e.target.closest('.check-item input[type="checkbox"]');
    if (!cb) return;
    e.preventDefault();
    cb.checked = !cb.checked;
    cb.closest('.check-item')?.classList.toggle('checked', cb.checked);
    commit(); scheduleSave();
  });
  content.addEventListener('keydown', e => {
    if (e.key !== 'Enter' || e.isComposing) return;
    const item = e.target.closest?.('.check-item');
    if (!item || !content.contains(item)) return;
    const lbl = item.querySelector('.lbl');
    if (!lbl) return;
    const sel = window.getSelection();
    if (!sel?.rangeCount || !sel.isCollapsed || !lbl.contains(sel.anchorNode)) return;
    const tail = sel.getRangeAt(0).cloneRange();
    tail.selectNodeContents(lbl);
    tail.setStart(sel.anchorNode, sel.anchorOffset);
    if (tail.toString().length) return;
    e.preventDefault();
    const next = document.createElement('div');
    next.className = 'check-item';
    next.dataset.check = '1';
    next.innerHTML = '<input type="checkbox"><span class="lbl"></span>';
    item.after(next);
    const nl = next.querySelector('.lbl');
    placeCaretIn(nl);
    saveSelection();
    commit();
    scheduleSave();
  });

  /* переключение панелей по клавиатуре; тулбар — в body, чтобы fixed был над клавиатурой */
  function dockToolbar(show) {
    if (show && toolbar.parentElement !== document.body) document.body.appendChild(toolbar);
    else if (!show && toolbar.parentElement === document.body) el.appendChild(toolbar);
  }
  /* Тулбар форматирования должен быть виден, пока идёт редактирование текста.
     Опираться только на состояние клавиатуры нельзя: после системного диалога
     выбора файла (input type=file) клавиатура на iOS/Android не открывается
     заново сама, kb-change больше не присылает open:true — и тулбар пропадал
     навсегда. Поэтому дополнительно учитываем фокус в холсте заметки. */
  let kbOpen = false;
  /* Панель форматирования относится к тексту заметки, а не к заголовку: пока
     курсор в заголовке — прячем её, даже если клавиатура открыта. */
  function editingTitle() { return document.activeElement === titleInput; }
  function editorActive() {
    const a = document.activeElement;
    return !!a && (a === content || content.contains(a) || toolbar.contains(a));
  }
  function syncFormatBar() {
    if (!el.isConnected) { document.body.classList.remove('toolbar-on'); dockToolbar(false); return; }
    const show = !editingTitle() && (kbOpen || editorActive());
    dockToolbar(show);
    document.body.classList.toggle('toolbar-on', show && !kbOpen);
  }
  function teardownKb() {
    document.removeEventListener('kb-change', onKb);
    document.body.classList.remove('toolbar-on');
    dockToolbar(false);
  }
  document.addEventListener('kb-change', onKb);
  function onKb(e) {
    if (!el.isConnected) { teardownKb(); return; }
    const opening = e.detail.open && !kbOpen;
    kbOpen = e.detail.open;
    topbar.classList.toggle('hidden', kbOpen);
    topbarKb.classList.toggle('hidden', !kbOpen);
    syncFormatBar();
    /* Клавиатура выехала — под сфокусированной строкой снова нужно место. */
    if (opening) { ensureCaretClear(); setTimeout(ensureCaretClear, 120); }
  }
  /* фокус в редакторе тоже управляет тулбаром (клавиатура может быть скрыта) */
  el.addEventListener('focusin', syncFormatBar);
  el.addEventListener('focusout', () => setTimeout(syncFormatBar, 60));

  /* ---------- Под сфокусированной строкой всегда остаётся место ----------
     Открывая клавиатуру, система показывает сфокусированную строку ровно у её
     верхнего края — а там лежит наш тулбар форматирования: он position: fixed
     и накрывает нижние 54px холста. Из-за этого каретка у нижнего края
     пряталась под тулбаром. После каждого смещения каретки досдвигаем холст
     так, чтобы под строкой остался запас (высота тулбара + CARET_GAP). */
  const CARET_GAP = 20;
  function caretRect() {
    if (document.activeElement === titleInput) return titleInput.getBoundingClientRect();
    const sel = window.getSelection();
    if (!sel || !sel.rangeCount) return null;
    const range = sel.getRangeAt(0);
    if (!content.contains(range.startContainer)) return null;
    const rects = range.getClientRects();
    const r = rects.length ? rects[rects.length - 1] : range.getBoundingClientRect();
    return r && (r.height || r.width) ? r : null;
  }
  function ensureCaretClear() {
    /* Тулбар в body = он лежит поверх холста; в заголовке его нет и запас не нужен. */
    if (!el.isConnected || toolbar.parentElement !== document.body) return;
    const r = caretRect();
    if (!r) return;
    const safeBottom = bodyWrap.getBoundingClientRect().bottom
      - (toolbar.offsetHeight || 0) - CARET_GAP;
    const delta = r.bottom - safeBottom;
    if (delta > 0.5) bodyWrap.scrollTop += delta;
  }
  el.addEventListener('focusin', () => {
    ensureCaretClear();
    /* Строку система двигает уже после начала анимации клавиатуры. */
    setTimeout(ensureCaretClear, 120);
    setTimeout(ensureCaretClear, 360);
  });

  /* ---------- Сохранение ---------- */
  const saveContent = () => {
    note.title = titleInput.value.trim();
    note.content = content.innerHTML.replace(/<br\s*\/?>/gi, '\n');
  };
  /* Отпечаток «того, что видим». baseline — состояние сразу после загрузки;
     если к закрытию отпечаток не изменился, значит правок не было. */
  let baseline = '';
  function signature() {
    return [titleInput.value, content.innerHTML, note.color || '', note.pinned ? '1' : '0', note.folderId || '', note.audio ? '1' : '0'].join('\u0001');
  }
  const scheduleSave = debounce(async () => {
    saveContent();
    await Store.saveNoteRaw(note);
  }, 450);
  async function finalSave(redrawDelay = 360) {
    saveContent();
    /* Ничего не правили — не трогаем базу и не перерисовываем главную.
       Иначе Store.saveNote() каждый раз обновлял updatedAt, и заметка
       «переставлялась» в списке при обычном открытии без изменений. */
    const sig = signature();
    if (sig === baseline) return;
    const empty = !note.title && !(content.textContent || '').trim() && !note.audio && !(note.content || '').includes('<img') && !(note.content || '').includes('data-drawing');
    if (empty && isNew) { await Store.delNote(note.id); }
    else { await Store.saveNote(note); }
    baseline = sig;
    /* Главную перерисовываем, когда анимация закрытия уже доехала: иначе
       перестройка сетки попадала в кадры анимации и «дёргала» её. */
    setTimeout(() => document.dispatchEvent(new CustomEvent('notes-changed')), redrawDelay);
  }

  async function done() {
    stopAudio(); clearImageSelection(); content.blur(); titleInput.blur(); teardownKb();
    /* Сжатие в карточку — только если заметка всё ещё видна карточкой на
       главной и экран не тянется пальцем: тогда закрытие продолжает уже
       начатое движение, а не прыгает. Если морфинг уже сжимает экран
       (повторное нажатие «назад»), второй раз не запускаем — иначе отменим
       выгрузку экрана и он «застрянет» на месте карточки. */
    if (morphPhase(el) === 'close') return;
    const card = note && document.querySelector(`.note-card[data-id="${note.id}"]`);
    /* Свайп «назад» тянет экран пальцем — там свой слайд (Nav.pop), морфинг
       только передрался бы с ним. Морфинг же в процессе разворачиваем назад. */
    const swiped = el.classList.contains('swipe-back') || (!!el.style.transform && !morphPhase(el));
    if (card && !swiped && !morphDisabled() && cardOnScreen(card)) {
      /* Системные зоны — под главную сразу, вместе со стартом сжатия */
      Nav.colorPrev();
      morphClose(el, card, () => Nav.dropTop());
      /* Сетку перерисовываем после сжатия, чтобы карточка не сменилась
         посреди анимации. */
      await finalSave(420);
      return;
    }
    /* Слайд запускаем сразу, не дожидаясь записи в базу: пауза перед анимацией
       и перерисовка главной «на ходу» делали возврат медленным и рваным. */
    Nav.pop();
    await finalSave(360);
  }

  topbar.querySelector('[data-a=back]').addEventListener('click', done);
  topbarKb.querySelector('[data-a=back2]').addEventListener('click', done);
  topbarKb.querySelector('[data-a=done]').addEventListener('click', done);

  /* ---------- share / palette / more ---------- */
  topbar.querySelector('[data-a=share]').addEventListener('click', () => {
    openSheet((sheet, api) => {
      sheet.innerHTML = `
        <div class="pull-strip"></div><div class="sheet-title">Поделиться</div>
        <div class="sheet-actions">
          <button class="sheet-action" data-k="text"><span class="material-symbols-rounded">subject</span>Как текст</button>
          <button class="sheet-action" data-k="file"><span class="material-symbols-rounded">description</span>Как файл .txt</button>
          <button class="sheet-action" data-k="html"><span class="material-symbols-rounded">code</span>Как HTML</button>
        </div>`;
      sheet.addEventListener('click', async e => {
        const b = e.target.closest('[data-k]'); if (!b) return;
        api.close();
        const t = `${note.title ? note.title + '\n\n' : ''}` + (content.textContent || '');
        if (b.dataset.k === 'text' && navigator.share) { try { await navigator.share({ title: note.title || 'Заметка', text: t }); return; } catch (err) {} }
        try {
          if (b.dataset.k !== 'text') {
            const blob = new Blob([b.dataset.k === 'html' ? content.innerHTML : t], { type: b.dataset.k === 'html' ? 'text/html' : 'text/plain' });
            const file = new File([blob], (note.title || 'заметка').slice(0, 40).replace(/[\\/:*?"<>|]/g, '') + (b.dataset.k === 'html' ? '.html' : '.txt'), { type: blob.type });
            if (navigator.canShare && navigator.canShare({ files: [file] })) { await navigator.share({ files: [file] }); return; }
          }
          await navigator.clipboard.writeText(t);
          toast('Скопировано в буфер обмена');
        } catch (err) { toast('Не удалось поделиться'); }
      });
    });
  });
  topbar.querySelector('[data-a=palette]').addEventListener('click', colorSheet);
  topbar.querySelector('[data-a=more]').addEventListener('click', () => moreMenu(topbar.querySelector('[data-a=more]')));

  function moreMenu(anchor) {
    attachMenu(anchor, [
      { icon: 'folder', label: 'В папку', onClick: folderSheet },
      { icon: 'push_pin', label: note.pinned ? 'Открепить' : 'Закрепить', checked: !!note.pinned, onClick: togglePin },
      { icon: 'content_copy', label: 'Копировать', onClick: async () => { const c = { ...note, id: uid(), createdAt: Date.now(), updatedAt: Date.now() }; await Store.saveNote(c); toast('Копия создана'); } },
      { divider: true },
      { icon: 'delete', label: 'Удалить', onClick: deleteNote },
    ]);
  }

  async function togglePin() {
    note.pinned = !note.pinned;
    await Store.saveNote(note);
    toast(note.pinned ? 'Закреплено' : 'Откреплено');
    document.dispatchEvent(new CustomEvent('notes-changed'));
  }

  async function deleteNote() {
    if (await confirmSheet({ title: 'Удалите заметки', text: 'Удалить эту заметку?' })) {
      await Store.delNote(note.id);
      toast('Заметка удалена');
      document.dispatchEvent(new CustomEvent('notes-changed'));
      teardownKb();
      Nav.pop();
    }
  }

  function folderSheet() {
    sheetSections({ title: 'Папка', sections: [api => {
      const box = document.createElement('div');
      box.className = 'sheet-actions';
      box.innerHTML = [
        `<button class="sheet-action" data-f=""><span class="material-symbols-rounded">block</span>Без категории</button>`,
        ...foldersList.map(f => `<button class="sheet-action" data-f="${f.id}"><span class="material-symbols-rounded">folder</span>${escapeHtml(f.name)}</button>`)
      ].join('');
      box.addEventListener('click', async e => {
        const b = e.target.closest('[data-f]'); if (!b) return;
        note.folderId = b.dataset.f || null;
        await Store.saveNote(note); updFolderChip(); api.close();
      });
      return box;
    }]});
  }

  function colorSheet() {
    sheetSections({ title: 'Цвет', sections: [api => {
      const row = document.createElement('div');
      row.className = 'color-row';
      row.innerHTML = `<div class="row-options">${COLORS.map(c => {
        const bg = c.css || '#ffffff';
        const sel = (note.color || 'none') === c.key;
        return `<button class="swatch ${sel ? 'selected' : ''}" data-k="${c.key}" style="background:${bg}">${sel ? '<span class="material-symbols-rounded">check</span>' : ''}</button>`;
      }).join('')}</div>`;
      row.querySelectorAll('.swatch').forEach(sw => sw.addEventListener('click', async () => {
        note.color = sw.dataset.k === 'none' ? null : sw.dataset.k;
        await Store.saveNote(note);
        applyTheme(); /* живой перелиив холста */
        api.close();
        document.dispatchEvent(new CustomEvent('notes-changed'));
      }));
      return row;
    }]});
  }

  folderChip.addEventListener('click', folderSheet);

  /* ---------- Тулбар: selection + вставка (не терять каретку при тапе по кнопкам) ---------- */
  let savedRange = null;

  function saveSelection() {
    const sel = window.getSelection();
    if (!sel?.rangeCount) return;
    const n = sel.anchorNode;
    if (!n || !content.contains(n)) return;
    savedRange = sel.getRangeAt(0).cloneRange();
  }

  function restoreSelection() {
    if (!savedRange) return false;
    if (!content.contains(savedRange.startContainer)) return false;
    const sel = window.getSelection();
    sel.removeAllRanges();
    sel.addRange(savedRange);
    return true;
  }

  function placeCaretIn(node) {
    const r = document.createRange();
    r.selectNodeContents(node);
    r.collapse(true);
    const sel = window.getSelection();
    sel.removeAllRanges();
    sel.addRange(r);
    savedRange = r.cloneRange();
  }

  function focusEditor() {
    content.focus({ preventScroll: true });
    if (!restoreSelection()) {
      const r = document.createRange();
      r.selectNodeContents(content);
      r.collapse(false);
      const sel = window.getSelection();
      sel.removeAllRanges();
      sel.addRange(r);
      savedRange = r.cloneRange();
    }
  }

  function ensureBlock() {
    if (content.querySelector('img, ul, ol, .check-item, h1, h2, div, p')) return;
    if ((content.textContent || '').trim()) return;
    content.innerHTML = '<div><br></div>';
    placeCaretIn(content.firstChild);
  }

  function insertAtCursor(html) {
    ensureBlock();
    focusEditor();
    let inserted = false;
    try {
      if (document.queryCommandSupported('insertHTML')) {
        inserted = document.execCommand('insertHTML', false, html);
      }
    } catch (err) { inserted = false; }
    if (!inserted) {
      const sel = window.getSelection();
      if (sel?.rangeCount) {
        const range = sel.getRangeAt(0);
        range.deleteContents();
        const frag = range.createContextualFragment(html);
        range.insertNode(frag);
        range.collapse(false);
        sel.removeAllRanges();
        sel.addRange(range);
        savedRange = range.cloneRange();
      } else {
        content.insertAdjacentHTML('beforeend', html);
      }
    }
    saveSelection();
  }

  function runFormat(mutate) {
    ensureBlock();
    focusEditor();
    mutate();
    saveSelection();
    commit();
    scheduleSave();
    updMeta();
  }

  content.addEventListener('keyup', saveSelection);
  content.addEventListener('mouseup', saveSelection);
  content.addEventListener('focus', saveSelection);
  document.addEventListener('selectionchange', () => {
    const sel = window.getSelection();
    if (sel?.anchorNode && content.contains(sel.anchorNode)) { saveSelection(); ensureCaretClear(); }
  });

  const keepFocus = e => { e.preventDefault(); saveSelection(); };
  toolbar.addEventListener('pointerdown', keepFocus);
  toolbar.addEventListener('mousedown', keepFocus);

  toolbar.querySelectorAll('.tool').forEach(btn => btn.addEventListener('click', () => {
    vibrate(8);
    saveSelection();
    const k = btn.dataset.tool;
    if (k === 'audio') {
      saveSelection();
      content.blur();
      openRecorder(note, async (rec) => {
        if (note.audio?.id && note.audio.id !== rec.id) { try { await Store.delBlob(note.audio.id); } catch (e) {} }
        note.audio = rec;
        await Store.saveNote(note);
        renderPlayers();
        toast('Аудио добавлено');
        focusEditor();
      });
    } else if (k === 'image') {
      pickImage();
    } else if (k === 'draw') {
      saveSelection();
      content.blur();
      openDrawing(async (dataUrl) => {
        runFormat(() => {
          insertAtCursor(`<div data-drawing="1"><img class="rich-img" src="${dataUrl}" alt=""></div><div><br></div>`);
        });
      });
    } else if (k === 'check') {
      runFormat(() => {
        insertAtCursor('<div class="check-item" data-check="1"><input type="checkbox"><span class="lbl">Пункт</span></div><div><br></div>');
        const items = content.querySelectorAll('.check-item');
        const lbl = items[items.length - 1]?.querySelector('.lbl');
        if (lbl) {
          const r = document.createRange();
          r.selectNodeContents(lbl);
          const sel = window.getSelection();
          sel.removeAllRanges();
          sel.addRange(r);
          savedRange = r.cloneRange();
        }
      });
    } else if (k === 'heading') {
      runFormat(() => {
        let node = window.getSelection()?.anchorNode;
        if (node?.nodeType === 3) node = node.parentElement;
        const inH1 = !!(node?.closest?.('h1'));
        document.execCommand('formatBlock', false, inH1 ? 'div' : 'h1');
      });
    } else if (k === 'bold') {
      runFormat(() => {
        document.execCommand('styleWithCSS', false, true);
        document.execCommand('bold');
      });
    } else if (k === 'italic') {
      runFormat(() => {
        document.execCommand('styleWithCSS', false, true);
        document.execCommand('italic');
      });
    } else if (k === 'list') {
      runFormat(() => { document.execCommand('insertUnorderedList'); });
    }
  }));

  function pickImage() {
    saveSelection();
    const inp = document.createElement('input');
    inp.type = 'file';
    inp.accept = 'image/*';
    inp.onchange = async () => {
      const f = inp.files[0];
      if (!f) return;
      try {
        const imgId = 'img_' + uid();
        await Store.putBlob(imgId, f);
        const url = URL.createObjectURL(f);
        runFormat(() => {
          insertAtCursor(`<img class="rich-img" src="${url}" data-blob="${imgId}" alt=""><div><br></div>`);
        });
        toast('Фото добавлено');
      } catch (e) {
        toast('Не удалось добавить фото');
      }
    };
    inp.click();
  }

  /* =========================================================
     Аудио: плеер со звуковой волной (по громкости) + удаление
     ========================================================= */
  function fmtDur2(s) { s = Math.max(0, Math.floor(s || 0)); const m = Math.floor(s / 60); return `${m}:${s % 60 < 10 ? '0' : ''}${s % 60}`; }
  const waveCache = new Map();

  function computePeaks(buf, bars) {
    const ch = buf.getChannelData(0);
    const peaks = new Float32Array(bars);
    const block = Math.max(1, Math.floor(ch.length / bars));
    for (let i = 0; i < bars; i++) {
      let max = 0;
      const start = i * block, end = Math.min(start + block, ch.length);
      const step = Math.max(1, Math.floor(block / 32));
      for (let j = start; j < end; j += step) { const v = Math.abs(ch[j]); if (v > max) max = v; }
      peaks[i] = max;
    }
    return peaks;
  }

  async function loadPeaks(id, blob, bars) {
    if (waveCache.has(id)) return waveCache.get(id);
    try {
      const AC = window.AudioContext || window.webkitAudioContext;
      const ac = new AC();
      const buf = await ac.decodeAudioData(await blob.arrayBuffer());
      const res = { peaks: computePeaks(buf, bars), duration: buf.duration };
      try { ac.close(); } catch (e) {}
      waveCache.set(id, res);
      return res;
    } catch (e) { return null; }
  }

  function renderPlayers() {
    stopAudio();
    stopAudio = () => {};
    players.forEach(p => p.remove()); players = [];

    const host = document.createElement('div');
    host.className = 'player-host';
    players.push(host);
    meta.after(host);

    if (!note.audio) return;

    const BARS = 46;
    const card = document.createElement('div');
    card.className = 'player-card';
    card.innerHTML = `
      <button class="pc-play" type="button" aria-label="Воспроизвести">
        <span class="material-symbols-rounded">play_arrow</span>
      </button>
      <div class="pc-mid">
        <canvas class="pc-wave"></canvas>
        <div class="pc-sub"><span class="pc-cur">${fmtDur2(0)}</span><span class="pc-sep">/</span><span class="pc-dur">${fmtDur2(note.audio.duration)}</span></div>
      </div>
      <button class="pc-del" type="button" aria-label="Удалить аудио">
        <span class="material-symbols-rounded">delete</span>
      </button>`;
    host.appendChild(card);

    const canvas = card.querySelector('.pc-wave');
    const curEl = card.querySelector('.pc-cur');
    const durEl = card.querySelector('.pc-dur');
    const playBtn = card.querySelector('.pc-play');
    const icon = playBtn.querySelector('.material-symbols-rounded');

    let peaks = null, playing = false, raf = null, realDur = 0;
    let audioEl = null, actx = null, analyser = null, dataArr = null;

    const accent = () => getComputedStyle(document.documentElement).getPropertyValue('--accent').trim() || '#f2a824';

    function draw(progress = 0, live = 0) {
      const r = canvas.getBoundingClientRect();
      if (!r.width) return;
      const dpr = Math.min(3, window.devicePixelRatio || 1);
      const W = Math.round(r.width * dpr), H = Math.round(r.height * dpr);
      if (canvas.width !== W || canvas.height !== H) { canvas.width = W; canvas.height = H; }
      const cx = canvas.getContext('2d');
      cx.clearRect(0, 0, W, H);
      const acc = accent();
      const gap = Math.max(1, 2 * dpr);
      const bw = (W - gap * (BARS - 1)) / BARS;
      const mid = H / 2;
      const head = progress * BARS;
      for (let i = 0; i < BARS; i++) {
        let p = peaks ? Math.max(0.06, peaks[i]) : 0.32;
        let bh = p * (H - 6 * dpr);
        if (playing && analyser && i === Math.floor(head) && live > 0) bh = Math.max(bh, live * (H - 4 * dpr));
        bh = Math.max(3 * dpr, bh);
        const x = i * (bw + gap);
        const played = (i + 0.5) <= head;
        cx.fillStyle = played ? acc : 'rgba(128,128,128,.35)';
        cx.beginPath();
        if (cx.roundRect) cx.roundRect(x, mid - bh / 2, bw, bh, Math.min(bw / 2, 2 * dpr));
        else cx.rect(x, mid - bh / 2, bw, bh);
        cx.fill();
      }
    }

    function loop() {
      if (!audioEl) return;
      const dur = realDur || ((audioEl.duration && isFinite(audioEl.duration)) ? audioEl.duration : (note.audio.duration || 1));
      const prog = Math.min(1, (audioEl.currentTime || 0) / dur);
      let live = 0;
      if (analyser) {
        analyser.getByteFrequencyData(dataArr);
        let s = 0; for (let i = 0; i < 16; i++) s += dataArr[i];
        live = (s / 16) / 255;
      }
      draw(prog, live);
      curEl.textContent = fmtDur2(audioEl.currentTime || 0);
      if (playing) raf = requestAnimationFrame(loop);
    }

    async function play() {
      const blob = await Store.blob(note.audio.id);
      if (!blob) { toast('Аудио недоступно'); return; }
      audioEl = new Audio(URL.createObjectURL(blob));
      audioEl.onended = () => { stopMedia(); draw(0, 0); curEl.textContent = fmtDur2(0); };
      try {
        const AC = window.AudioContext || window.webkitAudioContext;
        actx = new AC();
        try { await actx.resume(); } catch (e) {}
        const srcNode = actx.createMediaElementSource(audioEl);
        analyser = actx.createAnalyser(); analyser.fftSize = 256;
        dataArr = new Uint8Array(analyser.frequencyBinCount);
        srcNode.connect(analyser); analyser.connect(actx.destination);
      } catch (e) { analyser = null; actx = null; }
      playing = true; icon.textContent = 'pause';
      try { await audioEl.play(); } catch (e) {}
      loop();
    }

    function stopMedia() {
      if (raf) { cancelAnimationFrame(raf); raf = null; }
      if (audioEl) { try { audioEl.pause(); } catch (e) {} audioEl = null; }
      if (actx) { try { actx.close(); } catch (e) {} actx = null; }
      analyser = null; dataArr = null; playing = false;
      icon.textContent = 'play_arrow';
    }

    playBtn.addEventListener('click', () => {
      if (playing) { stopMedia(); draw(0, 0); curEl.textContent = fmtDur2(0); }
      else play();
    });

    card.querySelector('.pc-del').addEventListener('click', async () => {
      const ok = await confirmSheet({ title: 'Удалить аудио?', text: 'Аудиофайл будет удалён из этой заметки.', okLabel: 'Удалить' });
      if (!ok) return;
      const aid = note.audio && note.audio.id;
      stopMedia();
      if (aid) { try { await Store.delBlob(aid); } catch (e) {} }
      note.audio = null;
      await Store.saveNote(note);
      renderPlayers();
      updMeta();
      toast('Аудио удалено');
    });

    stopAudio = stopMedia;

    requestAnimationFrame(() => {
      draw(0, 0);
      const id = note.audio.id;
      Store.blob(id).then(blob => blob && loadPeaks(id, blob, BARS)).then(res => {
        if (!res) return;
        peaks = res.peaks;
        if (res.duration && isFinite(res.duration) && res.duration > 0) {
          realDur = res.duration;
          durEl.textContent = fmtDur2(realDur);
        }
        if (playing) loop(); else draw(0, 0);
      });
    });
  }

  /* =========================================================
     Фото: выделение тапом, изменение размера,
     перемещение, поворот, обрезка, замена, копирование, удаление
     ========================================================= */
  let selImg = null, imgFrame = null, imgBar = null;

  function movableBlock(img) {
    const p = img.parentElement;
    if (p && p !== content && p.hasAttribute('data-drawing')) return p;
    return img;
  }

  function selectImage(img) {
    if (selImg === img) { positionImageTools(); return; }
    clearImageSelection();
    selImg = img;
    img.classList.add('img-selected');
    buildFrame();
    buildBar();
    /* убираем каретку и клавиатуру — фото редактируется отдельно от текста */
    content.blur(); titleInput.blur();
    positionImageTools();
    setTimeout(positionImageTools, 120);
    setTimeout(positionImageTools, 320);
    vibrate(10);
  }

  function clearImageSelection() {
    if (selImg) selImg.classList.remove('img-selected', 'img-dragging');
    selImg = null;
    if (imgFrame) { imgFrame.remove(); imgFrame = null; }
    if (imgBar) { imgBar.remove(); imgBar = null; }
  }

  function buildFrame() {
    imgFrame = document.createElement('div');
    imgFrame.className = 'img-frame';
    imgFrame.innerHTML = ['tl', 'tr', 'bl', 'br'].map(h => `<span class="img-handle ${h}" data-h="${h}"></span>`).join('');
    document.body.appendChild(imgFrame);
    imgFrame.querySelectorAll('.img-handle').forEach(h => h.addEventListener('pointerdown', e => startResize(e, h.dataset.h)));
  }

  function buildBar() {
    imgBar = document.createElement('div');
    imgBar.className = 'img-bar';
    imgBar.innerHTML = `
      <button class="img-act" data-a="crop"><span class="material-symbols-rounded">crop</span><span>Обрезка</span></button>
      <button class="img-act" data-a="rotate"><span class="material-symbols-rounded">rotate_right</span><span>Повернуть</span></button>
      <button class="img-act" data-a="replace"><span class="material-symbols-rounded">swap_horiz</span><span>Заменить</span></button>
      <button class="img-act" data-a="copy"><span class="material-symbols-rounded">content_copy</span><span>Копировать</span></button>
      <button class="img-act danger" data-a="delete"><span class="material-symbols-rounded">delete</span><span>Удалить</span></button>`;
    document.body.appendChild(imgBar);
    imgBar.addEventListener('click', e => {
      const b = e.target.closest('[data-a]'); if (!b) return;
      vibrate(8);
      const a = b.dataset.a;
      if (a === 'crop') editCrop();
      else if (a === 'rotate') rotateImage();
      else if (a === 'replace') replaceImage();
      else if (a === 'copy') copyImage();
      else if (a === 'delete') deleteImage();
    });
  }

  function positionImageTools() {
    if (!selImg) return;
    const r = selImg.getBoundingClientRect();
    if (imgFrame) {
      imgFrame.style.left = r.left + 'px';
      imgFrame.style.top = r.top + 'px';
      imgFrame.style.width = r.width + 'px';
      imgFrame.style.height = r.height + 'px';
    }
  }

  function startResize(e, corner) {
    if (!selImg) return;
    e.preventDefault(); e.stopPropagation();
    const img = selImg;
    const startW = img.getBoundingClientRect().width;
    const maxW = Math.max(44, content.clientWidth - 40);
    const sx = e.clientX;
    const dir = corner.includes('r') ? 1 : -1;
    const target = e.currentTarget;
    try { target.setPointerCapture(e.pointerId); } catch (err) {}
    /* батчим перерисовку к одному кадру: pointermove приходит слишком часто,
       а layout-чтение в positionImageTools() дорогое */
    let pendingW = null, rafId = 0;
    const apply = () => {
      rafId = 0;
      if (pendingW == null) return;
      img.style.width = pendingW + 'px';
      positionImageTools();
    };
    const move = ev => {
      pendingW = Math.max(44, Math.min(maxW, startW + (ev.clientX - sx) * dir));
      if (!rafId) rafId = requestAnimationFrame(apply);
    };
    const up = () => {
      if (rafId) { cancelAnimationFrame(rafId); rafId = 0; }
      pendingW = null;
      target.removeEventListener('pointermove', move);
      target.removeEventListener('pointerup', up);
      target.removeEventListener('pointercancel', up);
      commit(); scheduleSave(); updMeta();
    };
    target.addEventListener('pointermove', move);
    target.addEventListener('pointerup', up);
    target.addEventListener('pointercancel', up);
  }

  function startMove(e, img) {
    e.preventDefault();
    const unit = movableBlock(img);
    const sx = e.clientX, sy = e.clientY;
    let active = false, ref = null;
    const line = document.createElement('div');
    line.className = 'img-drop-line';
    const move = ev => {
      if (!active && Math.hypot(ev.clientX - sx, ev.clientY - sy) < 8) return;
      if (!active) { active = true; img.classList.add('img-dragging'); document.body.appendChild(line); }
      const kids = [...content.children].filter(k => k !== unit);
      const cr = content.getBoundingClientRect();
      let y = cr.bottom; ref = null;
      for (const k of kids) {
        const kr = k.getBoundingClientRect();
        if (ev.clientY < kr.top + kr.height / 2) { y = kr.top; ref = k; break; }
      }
      line.style.left = (cr.left + 12) + 'px';
      line.style.width = Math.max(0, cr.width - 24) + 'px';
      line.style.top = y + 'px';
    };
    const up = () => {
      window.removeEventListener('pointermove', move);
      window.removeEventListener('pointerup', up);
      window.removeEventListener('pointercancel', up);
      if (!active) return;
      img.classList.remove('img-dragging');
      line.remove();
      if (ref && ref !== unit) content.insertBefore(unit, ref);
      else if (!ref) content.appendChild(unit);
      commit(); scheduleSave(); updMeta();
      positionImageTools();
    };
    window.addEventListener('pointermove', move);
    window.addEventListener('pointerup', up);
    window.addEventListener('pointercancel', up);
  }

  function deleteImage() {
    if (!selImg) return;
    const unit = movableBlock(selImg);
    clearImageSelection();
    const next = unit.nextElementSibling;
    unit.remove();
    if (next && next.tagName === 'DIV' && !next.classList.contains('check-item') && !next.hasAttribute('data-drawing') && !(next.textContent || '').trim() && next.children.length <= 1) next.remove();
    if (!content.children.length) content.innerHTML = '<div><br></div>';
    commit(); scheduleSave(); updMeta();
    toast('Фото удалено');
  }

  function imageToDataURL(img) {
    try {
      const c = document.createElement('canvas');
      c.width = img.naturalWidth || img.width;
      c.height = img.naturalHeight || img.height;
      c.getContext('2d').drawImage(img, 0, 0);
      return c.toDataURL('image/png');
    } catch (e) { return null; }
  }

  function rotateDataURL(url, deg) {
    return new Promise(resolve => {
      const im = new Image();
      im.onload = () => {
        const c = document.createElement('canvas');
        c.width = im.height; c.height = im.width;
        const cx = c.getContext('2d');
        cx.translate(c.width / 2, c.height / 2);
        cx.rotate(deg * Math.PI / 180);
        cx.drawImage(im, -im.width / 2, -im.height / 2);
        resolve(c.toDataURL('image/png'));
      };
      im.onerror = () => resolve(null);
      im.src = url;
    });
  }

  async function applyImageDataURL(img, dataUrl) {
    const bid = img.getAttribute('data-blob');
    try {
      const blob = await (await fetch(dataUrl)).blob();
      if (bid) { await Store.putBlob(bid, blob); img.src = URL.createObjectURL(blob); return; }
    } catch (e) {}
    img.src = dataUrl;
  }

  async function rotateImage() {
    if (!selImg) return;
    const img = selImg;
    const keepW = img.getBoundingClientRect().width + 'px';
    const dataUrl = imageToDataURL(img);
    if (!dataUrl) { toast('Не удалось изменить фото'); return; }
    const out = await rotateDataURL(dataUrl, 90);
    if (!out) return;
    await applyImageDataURL(img, out);
    img.style.width = keepW; img.style.height = 'auto';
    commit(); scheduleSave(); vibrate(10);
    positionImageTools();
  }

  function replaceImage() {
    if (!selImg) return;
    const img = selImg;
    const inp = document.createElement('input');
    inp.type = 'file'; inp.accept = 'image/*';
    inp.onchange = async () => {
      const f = inp.files[0]; if (!f) return;
      let bid = img.getAttribute('data-blob');
      if (!bid) { bid = 'img_' + uid(); img.setAttribute('data-blob', bid); }
      try {
        await Store.putBlob(bid, f);
        img.src = URL.createObjectURL(f);
        img.style.width = ''; img.style.height = '';
        commit(); scheduleSave(); positionImageTools();
        toast('Фото заменено');
      } catch (e) { toast('Не удалось заменить фото'); }
    };
    inp.click();
  }

  async function copyImage() {
    if (!selImg) return;
    const unit = movableBlock(selImg);
    const clone = unit.cloneNode(true);
    const cloneImg = clone.tagName === 'IMG' ? clone : clone.querySelector('img');
    const bid = selImg.getAttribute('data-blob');
    if (bid && cloneImg) {
      const blob = await Store.blob(bid);
      if (blob) {
        const nid = 'img_' + uid();
        await Store.putBlob(nid, blob);
        cloneImg.setAttribute('data-blob', nid);
        cloneImg.src = URL.createObjectURL(blob);
      }
    }
    unit.after(clone);
    commit(); scheduleSave();
    toast('Фото скопировано');
  }

  function editCrop() {
    if (!selImg) return;
    const img = selImg;
    const src = img.currentSrc || img.src;
    const keepW = (img.style.width || (img.getBoundingClientRect().width + 'px'));
    clearImageSelection();
    openImageEditor(src, async (out) => {
      if (!out) return;
      await applyImageDataURL(img, out);
      img.style.width = keepW; img.style.height = 'auto';
      commit(); scheduleSave(); updMeta();
      toast('Фото изменено');
    });
  }

  /* Жест: обычный тап по фото открывает редактирование; на выделенном — перетаскивание */
  let pressImg = null, pressFrom = null, pressAt = 0, imgTouchAt = 0;
  const cancelPress = () => { pressImg = null; pressFrom = null; };
  const isImgTarget = t => !!(t && t.closest && t.closest('img.rich-img'));

  /* тап по фото не должен ставить каретку и открывать клавиатуру холста */
  content.addEventListener('pointerdown', e => {
    const t = e.target;
    if (!isImgTarget(t)) {
      if (selImg) clearImageSelection();
      return;
    }
    e.preventDefault();
    imgTouchAt = performance.now();
    if (document.activeElement === content || document.activeElement === titleInput) { content.blur(); titleInput.blur(); }
    const img = t.closest('img.rich-img');
    if (img === selImg) { startMove(e, img); return; }
    /* запоминаем нажатие: выделяем только если это тап, а не скролл/свайп */
    pressImg = img; pressFrom = { x: e.clientX, y: e.clientY }; pressAt = performance.now();
  });
  content.addEventListener('contextmenu', e => { if (isImgTarget(e.target)) e.preventDefault(); });
  /* страховка: если браузер всё же сфокусировал холст по тапу на фото — сразу снимаем фокус */
  content.addEventListener('focus', () => { if (performance.now() - imgTouchAt < 450) content.blur(); }, true);

  window.addEventListener('pointerup', e => {
    if (!pressImg || !pressFrom) return;
    const moved = Math.hypot(e.clientX - pressFrom.x, e.clientY - pressFrom.y) > 18;
    const quick = performance.now() - pressAt < 600;
    const img = pressImg;
    cancelPress();
    if (!moved && quick) selectImage(img);
  });
  window.addEventListener('pointermove', e => {
    if (!pressImg || !pressFrom) return;
    if (Math.hypot(e.clientX - pressFrom.x, e.clientY - pressFrom.y) > 18) cancelPress();
  });
  window.addEventListener('pointercancel', cancelPress);

  /* Прокрутка холста НЕ закрывает клавиатуру и не снимает фокус. Клавиатура
     уходит только по системному жесту (свайп по самой клавиатуре), кнопке
     «назад» Android или клавише завершения на клавиатуре. Свайп по холсту лишь
     отменяет незавершённый тап по фото, чтобы случайное смещение пальца не
     выделяло картинку. */
  let swipeFrom = null;
  bodyWrap.addEventListener('touchstart', e => {
    swipeFrom = e.touches.length === 1 ? { x: e.touches[0].clientX, y: e.touches[0].clientY } : null;
  }, { passive: true });
  bodyWrap.addEventListener('touchmove', e => {
    if (!swipeFrom) return;
    const t = e.touches[0]; if (!t) return;
    if (Math.hypot(t.clientX - swipeFrom.x, t.clientY - swipeFrom.y) > 18) {
      swipeFrom = null;
      cancelPress();
    }
  }, { passive: true });

  /* Заголовок → основной текст. Пока набирается заголовок, увод холста вниз
     (без подтверждения заголовка) не должен закрывать клавиатуру: каретка
     переходит в самое начало текста заметки, и печатать можно дальше. */
  let lastBodyTop = bodyWrap.scrollTop;
  bodyWrap.addEventListener('scroll', () => {
    const top = bodyWrap.scrollTop;
    const scrolledDown = top > lastBodyTop + 6;
    lastBodyTop = top;
    if (!scrolledDown || document.activeElement !== titleInput) return;
    if (!titleInput.value.trim()) return;
    focusBodyStart();
  }, { passive: true });

  function focusBodyStart() {
    ensureBlock();
    content.focus({ preventScroll: true });
    const sel = window.getSelection();
    if (!sel) return;
    const r = document.createRange();
    r.setStart(content, 0);
    r.collapse(true);
    sel.removeAllRanges();
    sel.addRange(r);
    savedRange = r.cloneRange();
  }

  bodyWrap.addEventListener('scroll', positionImageTools, { passive: true });
  window.addEventListener('resize', positionImageTools);
  document.addEventListener('kb-change', () => { if (selImg) setTimeout(positionImageTools, 60); });

  /* Цвет холста применяем СИНХРОННО, до появления экрана: заметка ещё грузится
     из базы, но её цвет уже известен из списка (opts.color). Иначе системная
     полоса сначала получала базовый фон, а свой цвет — только после загрузки
     заметки, из-за чего цвет сверху менялся с заметной задержкой. */
  paintCanvas(opts.color);
  /* Если редактор открывают из карточки — экран вырастает из неё (морфинг).
     Иначе (новая заметка по кнопке «+», возврат из поиска) — обычный слайд. */
  const origin = opts.origin && opts.origin.isConnected ? opts.origin : null;
  if (origin && !morphDisabled()) {
    Nav.pushElRaw(el);
    morphOpen(el, origin);
  } else {
    Nav.pushEl(el);
  }
  vibrate(5);
  return el;
}
