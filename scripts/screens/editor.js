/* ============ Редактор заметки ============ */
import { Store, Nav, fmtEditorDate, escapeHtml, uid, debounce, vibrate } from '../core.js';
import { toast, attachMenu, sheetSections, openSheet, confirmSheet } from '../components/ui.js';
import { openRecorder } from '../components/recorder.js';
import { openDrawing } from '../components/drawing.js';

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
function textColorFor(bg) { return bg && lum(bg) < 160 ? '#ffffff' : 'var(--ink, #1c1d21)'; }
function mutedFor(bg)     { return bg && lum(bg) < 160 ? 'rgba(255,255,255,.62)' : 'var(--text-tertiary)'; }
function caretFor(bg)     { return bg && lum(bg) < 160 ? 'rgba(255,255,255,.9)' : 'var(--accent)'; }
function folderChipFor(bg){ return bg && lum(bg) < 160 ? 'rgba(255,255,255,.16)' : 'var(--surface2)'; }

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
  let undoStack = [], redoStack = [], suppress = false;
  let players = [];
  let currentAudio = null;

  (async () => {
    foldersList = await Store.folders();
    if (id) {
      note = await Store.note(id);
      if (!note) { toast('Заметка не найдена'); Nav.pop(); return; }
    } else {
      note = {
        id: uid(), title: '', content: '', color: null, folderId: opts.folderId || null,
        pinned: false, audio: null, createdAt: Date.now(), updatedAt: Date.now(),
      };
    }
    titleInput.value = note.title || '';
    const c = note.content || '';
    content.innerHTML = /[<>]/.test(c) ? c : c.split('\n').join('<br>');
    content.querySelectorAll('.check-item input[type="checkbox"]').forEach(cb => {
      cb.closest('.check-item')?.classList.toggle('checked', cb.checked);
    });
    updFolderChip(); updMeta(); renderPlayers(); applyTheme();
  })();

  /* ---------- Тема холста (цвет + автоконтраст текста) ---------- */
  /* окрашиваем весь экран: фон .screen + элементы в CSS от переменных */
  function applyTheme() {
    const c = COLORS.find(c => c.key === (note.color || 'none'));
    const bg = c && c.css;
    el.style.background = bg || 'var(--bg)';
    el.style.setProperty('--ed-ink', textColorFor(bg));
    el.style.setProperty('--ed-muted', mutedFor(bg));
    el.style.setProperty('--ed-caret', caretFor(bg));
    el.style.setProperty('--ed-chip', folderChipFor(bg));
  }

  function updFolderChip() {
    const f = foldersList.find(f => f.id === note.folderId);
    folderChip.querySelector('.fc-label').textContent = f ? f.name : 'Без категории';
  }
  function updMeta() {
    const chars = (content.textContent || '').replace(/\s/g, '').length;
    meta.innerHTML = `<span>${escapeHtml(fmtEditorDate(note.updatedAt))}</span><span class="dot"></span><span>${chars} символов</span>`;
  }

  /* ---------- Undo/Redo ---------- */
  function pushUndo() {
    if (suppress) return;
    undoStack.push({ title: titleInput.value, html: content.innerHTML });
    if (undoStack.length > 100) undoStack.shift();
    redoStack.length = 0; updUndoBtns();
  }
  function updUndoBtns() {
    topbarKb.querySelector('[data-a=undo]').disabled = !undoStack.length;
    topbarKb.querySelector('[data-a=redo]').disabled = !redoStack.length;
  }
  function applyState(s) {
    suppress = true; titleInput.value = s.title; content.innerHTML = s.html; suppress = false;
    updMeta(); scheduleSave();
  }
  function doUndo() {
    if (!undoStack.length) return;
    redoStack.push({ title: titleInput.value, html: content.innerHTML });
    applyState(undoStack.pop()); updUndoBtns();
  }
  function doRedo() {
    if (!redoStack.length) return;
    undoStack.push({ title: titleInput.value, html: content.innerHTML });
    applyState(redoStack.pop()); updUndoBtns();
  }
  topbarKb.querySelector('[data-a=undo]').addEventListener('click', doUndo);
  topbarKb.querySelector('[data-a=redo]').addEventListener('click', doRedo);

  /* ввод */
  const typed = debounce(() => { pushUndo(); updMeta(); scheduleSave(); }, 350);
  titleInput.addEventListener('input', typed);
  content.addEventListener('input', typed);
  content.addEventListener('pointerdown', e => {
    const cb = e.target.closest('.check-item input[type="checkbox"]');
    if (!cb) return;
    e.preventDefault();
    cb.checked = !cb.checked;
    cb.closest('.check-item')?.classList.toggle('checked', cb.checked);
    scheduleSave();
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
    pushUndo();
    const next = document.createElement('div');
    next.className = 'check-item';
    next.dataset.check = '1';
    next.innerHTML = '<input type="checkbox"><span class="lbl"></span>';
    item.after(next);
    const nl = next.querySelector('.lbl');
    placeCaretIn(nl);
    saveSelection();
    scheduleSave();
  });

  /* переключение панелей по клавиатуре; тулбар — в body, чтобы fixed был над клавиатурой */
  function dockToolbar(open) {
    if (open && toolbar.parentElement !== document.body) document.body.appendChild(toolbar);
    else if (!open && toolbar.parentElement === document.body) el.appendChild(toolbar);
  }
  function teardownKb() {
    document.removeEventListener('kb-change', onKb);
    dockToolbar(false);
  }
  document.addEventListener('kb-change', onKb);
  function onKb(e) {
    if (!el.isConnected) { teardownKb(); return; }
    const open = e.detail.open;
    topbar.classList.toggle('hidden', open);
    topbarKb.classList.toggle('hidden', !open);
    dockToolbar(open);
  }

  /* ---------- Сохранение ---------- */
  const saveContent = () => {
    note.title = titleInput.value.trim();
    note.content = content.innerHTML.replace(/<br\s*\/?>/gi, '\n');
  };
  const scheduleSave = debounce(async () => {
    saveContent();
    await Store.saveNoteRaw(note);
  }, 450);
  async function finalSave() {
    saveContent();
    const empty = !note.title && !(content.textContent || '').trim() && !note.audio && !(note.content || '').includes('<img') && !(note.content || '').includes('data-drawing');
    if (empty && isNew) { await Store.delNote(note.id); }
    else { await Store.saveNote(note); }
    document.dispatchEvent(new CustomEvent('notes-changed'));
  }

  async function done() { content.blur(); titleInput.blur(); teardownKb(); await finalSave(); Nav.pop(); }

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
    pushUndo();
    ensureBlock();
    focusEditor();
    mutate();
    saveSelection();
    scheduleSave();
    updMeta();
  }

  content.addEventListener('keyup', saveSelection);
  content.addEventListener('mouseup', saveSelection);
  content.addEventListener('focus', saveSelection);
  document.addEventListener('selectionchange', () => {
    const sel = window.getSelection();
    if (sel?.anchorNode && content.contains(sel.anchorNode)) saveSelection();
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

  /* ---------- Плеер аудио ---------- */
  function fmtDur2(s) { const m = Math.floor(s / 60); return `${m}:${s % 60 < 10 ? '0' : ''}${Math.floor(s % 60)}`; }
  function renderPlayers() {
    players.forEach(p => p.remove()); players = [];
    if (!note.audio) return;
    const p = document.createElement('div');
    p.className = 'player-chip';
    p.innerHTML = `<span class="material-symbols-rounded play" role="button">play_arrow</span><span class="time">${fmtDur2(note.audio.duration || 0)}</span>`;
    p.querySelector('.play').addEventListener('click', async () => {
      const ic = p.querySelector('.play');
      if (currentAudio) { currentAudio.pause(); currentAudio = null; ic.textContent = 'play_arrow'; return; }
      const blob = await Store.blob(note.audio.id);
      if (!blob) { toast('Аудио недоступно'); return; }
      currentAudio = new Audio(URL.createObjectURL(blob));
      currentAudio.onended = () => { ic.textContent = 'play_arrow'; currentAudio = null; };
      ic.textContent = 'pause';
      currentAudio.play();
    });
    meta.after(p); players.push(p);
  }

  Nav.pushEl(el);
  vibrate(5);
  return el;
}
