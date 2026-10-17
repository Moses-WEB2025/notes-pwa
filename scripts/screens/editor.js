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
    <button class="tool" data-tool="audio" aria-label="Аудио"><span class="material-symbols-rounded">graphic_eq</span></button>
    <button class="tool" data-tool="image" aria-label="Фото"><span class="material-symbols-rounded">image</span></button>
    <button class="tool" data-tool="draw" aria-label="Рисунок"><span class="material-symbols-rounded">gesture</span></button>
    <button class="tool" data-tool="check" aria-label="Чек-лист"><span class="material-symbols-rounded">check_box_outline_blank</span></button>
    <button class="tool" data-tool="heading" aria-label="Заголовок"><span class="material-symbols-rounded">title</span></button>
    <button class="tool" data-tool="bold" aria-label="Жирный"><span class="material-symbols-rounded">format_bold</span></button>
    <button class="tool" data-tool="italic" aria-label="Курсив"><span class="material-symbols-rounded">format_italic</span></button>
    <button class="tool" data-tool="list" aria-label="Список"><span class="material-symbols-rounded">format_list_bulleted</span></button>`;

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
  content.addEventListener('click', e => {
    const cb = e.target.closest('input[type="checkbox"]');
    if (cb) { setTimeout(scheduleSave, 0); }
  });

  /* переключение панелей по клавиатуре */
  document.addEventListener('kb-change', onKb);
  function onKb(e) {
    topbar.classList.toggle('hidden', e.detail.open);
    topbarKb.classList.toggle('hidden', !e.detail.open);
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

  async function done() { content.blur(); titleInput.blur(); await finalSave(); Nav.pop(); }

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

  /* ---------- Тулбар: рабочие кнопки, набор как на скриншоте (AI, аудио, фото, рисунок, чек-лист, заголовок) ---------- */
  let lastFocusRange = null;
  content.addEventListener('blur', () => {
    const s = window.getSelection();
    if (s && s.rangeCount && content.contains(s.anchorNode)) { lastFocusRange = s.getRangeAt(0).cloneRange(); }
  });
  const refocus = () => {
    content.focus();
    const s = window.getSelection();
    if (lastFocusRange && s) { s.removeAllRanges(); s.addRange(lastFocusRange); }
    return content;
  };
  toolbar.querySelectorAll('.tool').forEach(btn => btn.addEventListener('click', () => {
    vibrate(8);
    const k = btn.dataset.tool;
    if (k === 'audio') {
      content.blur();
      openRecorder(note, async (rec) => { note.audio = rec; await Store.saveNote(note); renderPlayers(); toast('Аудио добавлено'); });
    } else if (k === 'image') {
      pickImage();
    } else if (k === 'draw') {
      content.blur();
      openDrawing(async (dataUrl) => {
        pushUndo(); refocus();
        document.execCommand('insertHTML', false, `<div data-drawing="1"><img src="${dataUrl}"></div><div><br></div>`);
        scheduleSave();
      });
    } else if (k === 'check') {
      pushUndo(); refocus();
      document.execCommand('insertHTML', false, `<div class="check-item" data-check="1"><input type="checkbox"><span class="lbl">Пункт</span> </div><div><br></div>`);
      scheduleSave();
    } else if (k === 'heading') {
      /* Заголовок — toggle: h1 <-> обычный текст */
      pushUndo(); refocus();
      const sel = window.getSelection();
      let node = sel && sel.anchorNode;
      if (node && node.nodeType === 3) node = node.parentElement;
      const inH1 = !!(node && node.closest && node.closest('h1'));
      document.execCommand('formatBlock', false, inH1 ? 'div' : 'h1');
      scheduleSave();
    } else if (k === 'bold') {
      pushUndo(); refocus(); document.execCommand('bold'); scheduleSave();
    } else if (k === 'italic') {
      pushUndo(); refocus(); document.execCommand('italic'); scheduleSave();
    } else if (k === 'highlight') {
      pushUndo(); refocus();
      document.execCommand('styleWithCSS', false, true);
      document.execCommand('hiliteColor', false, '#fae5a2');
      scheduleSave();
    } else if (k === 'list') {
      pushUndo(); refocus(); document.execCommand('insertUnorderedList'); scheduleSave();
    }
  }));

  function pickImage() {
    const inp = document.createElement('input');
    inp.type = 'file'; inp.accept = 'image/*';
    inp.onchange = async () => {
      const f = inp.files[0]; if (!f) return;
      try {
        const imgId = 'img_' + uid();
        await Store.putBlob(imgId, f);
        const url = URL.createObjectURL(f);
        pushUndo(); content.focus();
        document.execCommand('insertHTML', false, `<img class="rich-img" src="${url}" data-blob="${imgId}"><div><br></div>`);
        scheduleSave(); toast('Фото добавлено');
      } catch (e) { toast('Не удалось добавить фото'); }
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
