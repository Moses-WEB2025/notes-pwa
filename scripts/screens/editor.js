/* ============ Редактор заметки ============ */
import { Store, Nav, fmtEditorDate, escapeHtml, uid, debounce, vibrate, trashNote, screenSettle } from '../core.js';
import { refreshEdgeColor } from '../edge.js';
import { morphOpen, morphClose, morphDisabled, cardOnScreen, morphPhase } from '../morph.js';
import { toast, attachMenu, sheetSections, openSheet, confirmSheet, folderPickSheet } from '../components/ui.js';
import { openRecorder } from '../components/recorder.js';
import { openDrawing } from '../components/drawing.js';
import { openImageEditor } from '../components/image-editor.js';
import { icon } from '../icons.js';

/* ---------- Страницы панели форматирования ----------
   Три группы инструментов как в системных заметках, листаются свободным
   горизонтальным скроллом. «Крестик» вынесен в закреплённую секцию справа
   (общую для всех страниц), поэтому в страницы его не добавляем. */
const FMT_PAGES = {
  1: [['p', 'case-sensitive', 'Обычный текст'], ['h1', 'heading-1', 'Заголовок 1'], ['h2', 'heading-2', 'Заголовок 2'], ['h3', 'heading-3', 'Заголовок 3'], ['bold', 'bold', 'Жирный'], ['italic', 'italic', 'Курсив']],
  /* Списки идут парой: маркированный сразу перед нумерованным. */
  2: [['underline', 'underline', 'Подчёркнутый'], ['strike', 'strikethrough', 'Зачёркнутый'], ['ul', 'list', 'Маркированный список'], ['ol', 'list-ordered', 'Нумерованный список'], ['check', 'list-checks', 'Чек-лист']],
  /* «Увеличить отступ» (сдвиг вправо) стоит левее «уменьшить» — как в
     системных редакторах. */
  3: [['indent', 'indent-increase', 'Увеличить отступ'], ['outdent', 'indent-decrease', 'Уменьшить отступ'], ['quote', 'quote', 'Цитата'], ['align-left', 'align-left', 'По левому краю'], ['align-center', 'align-center', 'По центру'], ['align-right', 'align-right', 'По правому краю'], ['align-justify', 'align-justify', 'По ширине']],
};
function fmtButton(tool, ic, label) {
  return `<button type="button" class="fmt-tool" data-fmt="${tool}" aria-label="${label}">${icon(ic)}</button>`;
}
function fmtPage(n) {
  const items = FMT_PAGES[n].map(([t, i, l]) => fmtButton(t, i, l)).join('');
  return `<div class="fmt-page" data-page="${n}">${items}</div>`;
}

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
/* Цвет ссылки в тексте: на тёмном холсте светлый синий, на светлом — тёмный. */
function linkColorFor(bg) { return bg && lum(bg) < LIGHT_TEXT_MAX ? '#9fc2ff' : '#155dc8'; }

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

  /* ===== Главное нижнее меню (над клавиатурой) + панель форматирования =====
     «Т» (Lucide type) раскрывает .format-panel — три страницы инструментов
     с горизонтальным скроллом. Медиа-инструменты вставки остаются в меню. */
  const toolbar = document.createElement('div');
  toolbar.className = 'format-toolbar';
  toolbar.innerHTML = `
    <button type="button" class="tool" data-tool="format" aria-label="Форматирование" aria-expanded="false">${icon('type')}</button>
    <button type="button" class="tool" data-tool="audio" aria-label="Аудио">${icon('audio-lines')}</button>
    <button type="button" class="tool" data-tool="image" aria-label="Фото">${icon('image')}</button>
    <button type="button" class="tool" data-tool="draw" aria-label="Рисунок">${icon('pen-tool')}</button>
    <button type="button" class="tool" data-tool="check" aria-label="Чек-лист">${icon('square-check')}</button>`;

  const formatPanel = document.createElement('div');
  formatPanel.className = 'format-panel';
  formatPanel.innerHTML = `
    <div class="fmt-scroll" role="toolbar" aria-label="Форматирование текста">${fmtPage(1)}${fmtPage(2)}${fmtPage(3)}</div>
    <div class="fmt-close"><button type="button" class="fmt-tool" data-fmt="close" aria-label="Скрыть форматирование">${icon('x')}</button></div>`;

  el.append(topbar, topbarKb, bodyWrap, toolbar, formatPanel);

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
    /* Плейн-текст раскладываем в абзацы-<div> (строка = блок). Все инструменты
       (заголовки, цитата, чек-лист) работают с блочными элементами, и «голый»
       текст без обёрток ломал их: например, бокс чек-листа не находил абзац под
       кареткой и создавал пустой новый пункт вместо обёртки уже написанного. */
    content.innerHTML = /[<>]/.test(c)
      ? c
      : c.split('\n').map(line => `<div>${line || '<br>'}</div>`).join('');
    await hydrateImages();
    normalizeBlankBlocks();
    normalizeChecklist();
    /* Ссылки из разметки заметки (и старые плейн-текст адреса) — в кликабельные <a>. */
    linkifyContent();
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
    el.style.setProperty('--ed-link', linkColorFor(bg));
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
  /* ---------- Канон пункта чек-листа ----------
     <div class="check-item" [class="checked"]>
       <span class="box" contenteditable="false" role="checkbox" aria-checked="…"></span>
       <span class="lbl">…текст пункта…</span>
     </div>
     Бокс — не <input>, а <span>: нативный чекбокс внутри contenteditable на
     мобильных сам перехватывал фокус и «переезжал» по странице предметом
     тапа. Отметка живёт классом `checked` на пункте — он же уходит в разметку
     заметки и переживает перезапуск.
     normalizeChecklist() приводит готовую разметку к канону: переносит отметку
     со старых пунктов с <input>, заводит метку, собирает «голые» узлы пункта в
     .lbl (иначе они не зачёркиваются и ломают flex-раскладку) и — главное —
     распускает пункт, у которого стёрли бокс: он снова обычный абзац, без
     навсегда застрявшего зачёркивания. */
  function normalizeChecklist() {
    const items = content.querySelectorAll('.check-item');
    if (!items.length) return;
    /* Правка разметки не должна сбивать каретку: держим её текстовым смещением. */
    const wasCollapsed = !!window.getSelection()?.isCollapsed;
    const off = wasCollapsed ? caretTextOffset() : null;
    let changed = false;
    items.forEach(item => {
      if (!item.isConnected) return;
      /* Старый пункт с <input type="checkbox"> — переводим на наш бокс. */
      const legacy = item.querySelector('input[type="checkbox"]');
      if (legacy) {
        const box = document.createElement('span');
        box.className = 'box';
        box.setAttribute('contenteditable', 'false');
        box.setAttribute('role', 'checkbox');
        if (legacy.checked || legacy.hasAttribute('checked')) item.classList.add('checked');
        legacy.replaceWith(box);
        changed = true;
      }
      let box = item.querySelector('.box');
      if (box && box.parentElement !== item) { item.insertBefore(box, item.firstChild); changed = true; }
      /* Бокс стёрли — пункт перестал быть чек-листом: распускаем в обычный абзац. */
      if (!box) {
        itemToBlock(item);
        changed = true;
        return;
      }
      /* Канон — ровно два узла: бокс и метка. Если структура иная (метки нет,
         рядом «голый» текст, метка вложена) — пересобираем метку из всего, что
         в пункте кроме бокса, сохраняя порядок узлов. */
      const kids = Array.from(item.childNodes);
      const canonical = kids.length === 2 && kids[0] === box
        && kids[1].nodeType === 1 && kids[1].classList.contains('lbl');
      if (!canonical) {
        const lbl = document.createElement('span');
        lbl.className = 'lbl';
        kids.forEach(n => {
          if (n === box) return;
          if (n.nodeType === 1 && n.classList.contains('lbl')) {
            while (n.firstChild) lbl.appendChild(n.firstChild);
            n.remove();
            return;
          }
          lbl.appendChild(n);
        });
        box.after(lbl);
        changed = true;
      }
      const on = item.classList.contains('checked');
      const aria = on ? 'true' : 'false';
      if (box.getAttribute('aria-checked') !== aria) { box.setAttribute('aria-checked', aria); changed = true; }
    });
    if (changed && off != null) setCaretTextOffset(off);
  }
  /* Пустой абзац (пробел между строками) держится на <br>. В старых заметках
     он сохранён как <div>\n</div> (см. прежний saveContent) — пробельный узел
     схлопывается, абзац теряет высоту и строка пропадает. Возвращаем таким
     абзацам <br>, чтобы заметки, сохранённые до исправления, снова читались
     ровно так, как были написаны. */
  function normalizeBlankBlocks() {
    content.querySelectorAll('div, p').forEach(block => {
      if (block.querySelector('br, img, .box') || block.children.length) return;
      if (block.textContent === '' || !/[^\s]/.test(block.textContent)) {
        block.textContent = '';
        block.appendChild(document.createElement('br'));
      }
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
  /* Место каретки как смещение в тексте — чтобы оно пережило замену innerHTML. */
  function caretOffset() {
    const sel = window.getSelection();
    if (!sel?.rangeCount) return null;
    const r = sel.getRangeAt(0);
    if (!content.contains(r.startContainer)) return null;
    const pre = r.cloneRange();
    pre.selectNodeContents(content);
    pre.setEnd(r.startContainer, r.startOffset);
    return pre.toString().length;
  }
  function setCaretOffset(off) {
    if (off == null) return;
    const walker = document.createTreeWalker(content, NodeFilter.SHOW_TEXT);
    let acc = 0, node;
    while ((node = walker.nextNode())) {
      const len = node.nodeValue.length;
      if (acc + len >= off) {
        const r = document.createRange();
        r.setStart(node, off - acc);
        r.collapse(true);
        const sel = window.getSelection();
        sel.removeAllRanges(); sel.addRange(r);
        savedRange = r.cloneRange();
        return;
      }
      acc += len;
    }
  }
  function applyState(s) {
    /* html заменяется целиком, поэтому каретка «сгорает» вместе со старыми
       узлами, а фокус может уехать. Возвращаем и фокус, и место каретки —
       иначе отмена/возврат роняют клавиатуру и нижнее меню. */
    const wasEditing = document.activeElement === content;
    const off = wasEditing ? caretOffset() : null;
    suppress = true; titleInput.value = s.title; content.innerHTML = s.html; suppress = false;
    normalizeChecklist();
    if (selImg && !content.contains(selImg)) clearImageSelection();
    if (wasEditing) { content.focus({ preventScroll: true }); setCaretOffset(off); }
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
  /* Стрелки отмены/возврата не должны забирать фокус у текста: иначе клавиатура
     и нижнее меню схлопываются после первого же нажатия. */
  const keepKb = e => e.preventDefault();
  [undoBtn, redoBtn].forEach(b => {
    b.addEventListener('pointerdown', keepKb);
    b.addEventListener('mousedown', keepKb);
  });
  undoBtn.addEventListener('click', doUndo);
  redoBtn.addEventListener('click', doRedo);

  /* ввод */
  const typed = debounce(() => { commit(); updMeta(); scheduleSave(); }, 350);
  titleInput.addEventListener('input', typed);
  content.addEventListener('input', typed);
  /* Пробел, набранный в конце подчёркнутого/зачёркнутого текста, не должен
     оставаться оформленным — чистим пустое место сразу после ввода. */
  content.addEventListener('input', e => { if (!e.isComposing) stripSpaceDecoration(); });
  /* После каждой правки возвращаем пункты чек-листа к канону: если бокс стёрли,
     пункт снова становится обычным абзацем (зачёркивание уходит вместе с ним). */
  content.addEventListener('input', e => { if (!e.isComposing) normalizeChecklist(); });
  /* Адреса, набранные или вставленные, сразу становятся кликабельными ссылками. */
  content.addEventListener('input', e => { if (!e.isComposing) linkifyContent(); });

  /* ---------- Отметка пункта чек-листа ----------
     Тап по боксу переключает пункт. Тап НЕ должен ни двигать каретку, ни
     открывать клавиатуру, ни прокручивать холст. Ключевой момент: на телефоне
     каретку и клавиатуру вызывает default-действие самого тапа (touchstart),
     а не pointerdown, поэтому одного e.preventDefault() в pointerdown мало —
     холст «улетал» в самый низ. Гасим default на всех трёх событиях, а
     прокрутку дополнительно блокируем на 0.45 с (lastBoxTap). */
  let lastBoxTap = 0;
  const boxFromEvent = e => {
    const t = e.target;
    if (!t || t.nodeType !== 1) return null;
    const box = t.closest && t.closest('.check-item .box');
    return box && content.contains(box) ? box : null;
  };
  function toggleCheckItem(box) {
    const item = box.closest('.check-item');
    if (!item || !content.contains(item)) return;
    item.classList.toggle('checked');
    box.setAttribute('aria-checked', item.classList.contains('checked') ? 'true' : 'false');
    /* Каретку сразу уводим со «стаканчика» бокса в текст пункта: фокус должен
       стоять справа от галочки на той же строке, а не на самом боксе — иначе
       следующий тап по нему промахивается мимо переключателя. */
    const lbl = item.querySelector('.lbl');
    if (lbl) placeCaretIn(lbl);
    commit(); scheduleSave(); updMeta();
  }
  content.addEventListener('touchstart', e => {
    const box = boxFromEvent(e);
    if (!box) return;
    /* Гасим default уже здесь: именно он на iOS двигает каретку и фокусирует
       холст, из-за чего страница уезжала вниз. Метку времени ставим сразу —
       все «следящие» обработчики (focusin, selectionchange, автодосдвиг) на
       время тапа молчат. */
    e.preventDefault();
    lastBoxTap = performance.now();
  }, { passive: false });
  content.addEventListener('pointerdown', e => {
    const box = boxFromEvent(e);
    if (!box) return;
    e.preventDefault();
    lastBoxTap = performance.now();
    toggleCheckItem(box);
  });
  content.addEventListener('mousedown', e => {
    if (boxFromEvent(e)) e.preventDefault();
    /* Клик по ссылке не должен двигать каретку и создавать выделение —
       открытием занимается обработчик выше. */
    else if (linkAtEvent(e)) e.preventDefault();
  });

  /* Enter внутри пункта: «хвост» метки после каретки переезжает в новый пункт.
     На пустом пункте — выходим из списка обычным абзацем (как в системных
     заметках), иначе из списка было не выбраться. Пункт ищем по каретке, а не
     по e.target: у contenteditable-хоста событие клавиши приходит на сам хост,
     поэтому e.target.closest('.check-item') всегда пусто и разрезание не
     срабатывало. */
  content.addEventListener('keydown', e => {
    if (e.key !== 'Enter' || e.isComposing || e.shiftKey) return;
    const sel = window.getSelection();
    const item = checkItemAt(sel?.anchorNode);
    if (!item) return;
    const lbl = item.querySelector('.lbl');
    if (!lbl) return;
    if (!sel?.rangeCount || !sel.isCollapsed || !lbl.contains(sel.anchorNode)) return;
    e.preventDefault();
    const r = sel.getRangeAt(0);
    const head = document.createRange();
    head.selectNodeContents(lbl);
    head.setEnd(r.startContainer, r.startOffset);
    const tail = document.createRange();
    tail.selectNodeContents(lbl);
    tail.setStart(r.startContainer, r.startOffset);
    /* Копии содержимого снимаем ДО правки: после неё старые границы ranges
       недействительны. */
    const headFrag = head.cloneContents();
    const tailFrag = tail.cloneContents();
    const tailText = tail.toString();
    if (!(lbl.textContent || '').trim() && !lbl.querySelector('img') && !item.classList.contains('checked')) {
      const p = itemToBlock(item);
      placeCaretIn(p);
    } else if (!tailText) {
      const next = makeCheckItem();
      item.after(next);
      placeCaretIn(next.querySelector('.lbl'));
    } else {
      lbl.textContent = '';
      lbl.appendChild(headFrag);
      const next = makeCheckItem();
      next.querySelector('.lbl').appendChild(tailFrag);
      item.after(next);
      placeCaretIn(next.querySelector('.lbl'));
    }
    saveSelection();
    commit();
    scheduleSave();
    updMeta();
  });

  /* Backspace в начале пункта убирает бокс — пункт снова обычный абзац.
     Бокс — contenteditable="false", кареткой его не «взять» и сам он нативно
     не удалялся, поэтому обрабатываем явно: иначе от чекбокса не избавиться. */
  content.addEventListener('keydown', e => {
    if (e.key !== 'Backspace' || e.isComposing) return;
    const sel = window.getSelection();
    if (!sel?.rangeCount || !sel.isCollapsed) return;
    const item = checkItemAt(sel.anchorNode);
    if (!item) return;
    const lbl = item.querySelector('.lbl');
    if (!lbl) return;
    /* Каретка перед всем содержимым пункта? Бокс (contenteditable=false) стоит
       первым и текста не несёт — проверке он не мешает. */
    const pre = document.createRange();
    pre.selectNodeContents(item);
    pre.setEnd(sel.anchorNode, sel.anchorOffset);
    const before = pre.cloneContents();
    if ((before.textContent || '').length || before.querySelector('img')) return;
    e.preventDefault();
    const p = itemToBlock(item);
    placeCaretIn(p);
    saveSelection();
    commit();
    scheduleSave();
    updMeta();
  });

  /* Тулбар и панель форматирования переезжают в body, пока видны: position:
     fixed поверх холста должен отсчитываться от окна, а не от экрана заметки. */
  function dock(node, show) {
    if (show && node.parentElement !== document.body) document.body.appendChild(node);
    else if (!show && node.parentElement === document.body) el.appendChild(node);
  }
  /* Нижний тулбар (и основное меню, и панель форматирования) живёт ТОЛЬКО
     вместе с клавиатурой: нет клавиатуры — нет тулбара. Поэтому видимость
     ведём от состояния клавиатуры, а не от фокуса в тексте. */
  let kbOpen = false;
  /* Раскрыта ли панель форматирования (кнопка «Т» в нижнем меню). */
  let fmtOpen = false;
  /* Панель форматирования относится к тексту заметки, а не к заголовку: пока
     курсор в заголовке — прячем её, даже если клавиатура открыта. */
  function editingTitle() { return document.activeElement === titleInput; }
  /* Кнопка «Т» отражает, раскрыта ли панель. */
  function reflectFormatBtn() {
    const btn = toolbar.querySelector('[data-tool="format"]');
    btn?.classList.toggle('active', fmtOpen);
    btn?.setAttribute('aria-expanded', fmtOpen ? 'true' : 'false');
  }
  function syncFormatBar() {
    if (!el.isConnected) {
      fmtOpen = false; reflectFormatBtn();
      document.body.classList.remove('fmt-open');
      dock(toolbar, false); dock(formatPanel, false);
      return;
    }
    /* Клавиатура закрылась (в т.ч. системным жестом «назад» — Android сначала
       закрывает именно её) — тулбара быть не должно вовсе. Иначе панель
       оставалась висеть внизу пустой полосой и «падала» на место меню. */
    const show = kbOpen && !editingTitle();
    /* Панель живёт в том же слоте, что и меню, и появляется НА ЕГО МЕСТЕ.
       Поэтому докаем её вместе с меню (невидимой), а видимость переключаем
       классом fmt-open — так смена проигрывается переходом, а не «врывается». */
    if (!show) fmtOpen = false;
    reflectFormatBtn();
    dock(toolbar, show);
    dock(formatPanel, show);
    document.body.classList.toggle('fmt-open', show && fmtOpen);
  }
  /* Открыть/закрыть панель форматирования кнопкой «Т» или «крестиком». */
  function setFormatOpen(on) {
    fmtOpen = !!on;
    syncFormatBar();
    if (fmtOpen) { syncFormatState(); ensureCaretClear(); }
  }
  /* «Отмена» панели жестом или системной кнопкой «назад»: app-back при
     раскрытой панели присылает fmt-close (см. scripts/app.js), и панель
     убирается так же, как по «крестику» — главное меню встаёт на её место. */
  function onFmtClose() {
    if (!el.isConnected) { document.removeEventListener('fmt-close', onFmtClose); return; }
    if (fmtOpen) setFormatOpen(false);
  }
  document.addEventListener('fmt-close', onFmtClose);
  function teardownKb() {
    document.removeEventListener('kb-change', onKb);
    document.removeEventListener('fmt-close', onFmtClose);
    document.body.classList.remove('fmt-open');
    dock(toolbar, false); dock(formatPanel, false);
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
    /* Тап по боксу чек-листа не двигает каретку — автодосдвиг холста не нужен.
       Страховка на случай, если браузер всё-таки «увёл» каретку в конец. */
    if (performance.now() - lastBoxTap < 450) return;
    const r = caretRect();
    if (!r) return;
    /* Панель и меню занимают один слот, поэтому берём высоту видимой полосы. */
    const barH = fmtOpen ? (formatPanel.offsetHeight || 0) : (toolbar.offsetHeight || 0);
    const safeBottom = bodyWrap.getBoundingClientRect().bottom - barH - CARET_GAP;
    const delta = r.bottom - safeBottom;
    if (delta > 0.5) bodyWrap.scrollTop += delta;
  }
  el.addEventListener('focusin', () => {
    /* Тап по боксу чек-листа фокусирует холст, но каретку нам не двигал:
       автодосдвиг молчит, иначе страница «улетает» к каретке (в самый низ). */
    if (performance.now() - lastBoxTap < 450) return;
    ensureCaretClear();
    /* Строку система двигает уже после начала анимации клавиатуры. */
    setTimeout(ensureCaretClear, 120);
    setTimeout(ensureCaretClear, 360);
  });

  /* ---------- Сохранение ---------- */
  const saveContent = () => {
    note.title = titleInput.value.trim();
    /* Разметку сохраняем как есть. Раньше здесь <br> заменялся на «\n» — из-за
       этого пустой абзац (<div><br></div>) уходил в базу как <div>\n</div>:
       пробельный текстовый узел схлопывался, абзац терял высоту и пробел между
       строками при повторном входе исчезал. */
    note.content = content.innerHTML;
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
  /* Пауза перед перерисовкой главной — ровно столько, сколько длится переход
     экрана (--dur-3 + запас). Так сетка перестраивается уже после анимации и
     не «дёргает» её на слабых устройствах, где кадр может опоздать. */
  async function finalSave(redrawDelay = screenSettle()) {
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
      await finalSave();
      return;
    }
    /* Слайд запускаем сразу, не дожидаясь записи в базу: пауза перед анимацией
       и перерисовка главной «на ходу» делали возврат медленным и рваным. */
    Nav.pop();
    await finalSave();
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
      /* как и везде в приложении: заметка уходит в корзину и лежит там 30 дней */
      await trashNote(note);
      toast('Перемещено в корзину');
      document.dispatchEvent(new CustomEvent('notes-changed'));
      teardownKb();
      Nav.pop();
    }
  }

  /* Меню «Папка» в редакторе: сверху «Новая папка», далее папки того же
     раздела, что и заметка — у личной только скрытые, у обычной только
     обычные. Разделы не пересекаются. */
  function folderSheet() {
    const isPrivate = !!note.hidden || foldersList.some(f => f.hidden && f.id === note.folderId);
    folderPickSheet({
      title: 'Папка',
      isPrivate,
      onPick: async fid => {
        note.folderId = fid;
        foldersList = await Store.folders();
        await Store.saveNote(note);
        updFolderChip();
      },
    });
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

  /* Каретка как смещение по тексту: переживает перестановку узлов, которую
     делают очистка пробелов и снятие оформления (узлы не пересоздаются,
     меняется только их порядок и границы текстовых узлов). */
  function caretTextOffset() {
    const sel = window.getSelection();
    if (!sel?.rangeCount) return null;
    const r = sel.getRangeAt(0);
    if (r.startContainer !== content && !content.contains(r.startContainer)) return null;
    let off = 0;
    const walker = document.createTreeWalker(content, NodeFilter.SHOW_TEXT);
    let n;
    while ((n = walker.nextNode())) {
      if (n === r.startContainer) return off + r.startOffset;
      off += n.nodeValue.length;
    }
    return off;
  }
  function setCaretTextOffset(offset) {
    let left = offset;
    let target = null, tOff = 0;
    const walker = document.createTreeWalker(content, NodeFilter.SHOW_TEXT);
    let n;
    while ((n = walker.nextNode())) {
      if (left <= n.nodeValue.length) { target = n; tOff = left; break; }
      left -= n.nodeValue.length;
    }
    const sel = window.getSelection();
    const r = document.createRange();
    if (target) r.setStart(target, tOff);
    else { r.selectNodeContents(content); r.collapse(false); }
    r.collapse(true);
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

  /* Инструменты, работающие с фрагментом текста, а не с целым блоком. */
  const INLINE_TOOLS = new Set(['bold', 'italic', 'underline', 'strike']);
  /* Знаки конца фразы: точка, !, ?, ;, : и перенос строки. Запятая границей
     не считается — как в современных редакторах. */
  const SENT_STOP = /[.!?;:\n]/;
  /* Без выделения растягиваем каретку до границ текущей фразы, чтобы инструмент
     применялся ко всей фразе, а не к одному слову под кареткой. */
  function expandToSentence() {
    const sel = window.getSelection();
    if (!sel || !sel.rangeCount) return;
    const r = sel.getRangeAt(0);
    if (!r.collapsed) return;
    const node = r.startContainer;
    if (node.nodeType !== 3) return;
    const text = node.nodeValue;
    let s = r.startOffset, e = r.startOffset;
    while (s > 0 && !SENT_STOP.test(text[s - 1])) s--;
    while (e < text.length && !SENT_STOP.test(text[e])) e++;
    while (s < e && /\s/.test(text[s])) s++;
    while (e > s && /\s/.test(text[e - 1])) e--;
    if (e > s) { r.setStart(node, s); r.setEnd(node, e); sel.removeAllRanges(); sel.addRange(r); }
  }
  /* Пробелы по краям выделения не форматируем: иначе подчёркивание и
     зачёркивание «закрывают» пустое место за последним словом. */
  function trimSelection() {
    const sel = window.getSelection();
    if (!sel || !sel.rangeCount) return;
    const r = sel.getRangeAt(0);
    if (r.collapsed) return;
    const a = r.startContainer, b = r.endContainer;
    if (a.nodeType === 3) {
      let o = r.startOffset;
      while (o < a.nodeValue.length && /\s/.test(a.nodeValue[o])) o++;
      if (o !== r.startOffset) r.setStart(a, o);
    }
    if (b.nodeType === 3) {
      let o = r.endOffset;
      while (o > 0 && /\s/.test(b.nodeValue[o - 1])) o--;
      if (o !== r.endOffset) r.setEnd(b, o);
    }
    if (r.collapsed) return;
    sel.removeAllRanges();
    sel.addRange(r);
  }

  /* ---- Оформление фрагмента: надёжное снятие и запрет на пробелы ---- */

  /* Как хранится каждое оформление. Подчёркивание и зачёркивание делят одно
     свойство text-decoration, поэтому снимаем только своё значение, не трогая
     соседнее. */
  const DECOR = {
    underline: { kind: 'decoration', value: 'underline' },
    strikeThrough: { kind: 'decoration', value: 'line-through' },
    italic: { kind: 'style', prop: 'fontStyle', value: 'italic' },
  };
  const INLINE_SELECTOR = 'span, u, s, strike, i, em, font';
  function decorText(el) {
    return (el.style?.textDecorationLine || el.style?.textDecoration || '').toLowerCase();
  }
  function hasDecor(el, cmd) {
    if (cmd === 'italic') return (el.style?.fontStyle || '').toLowerCase() === 'italic' || /^(I|EM)$/.test(el.tagName);
    const dec = decorText(el);
    if (cmd === 'underline') return dec.includes('underline') || el.tagName === 'U';
    if (cmd === 'strikeThrough') return /line-through/.test(dec) || /^(S|STRIKE)$/.test(el.tagName);
    return false;
  }
  /* Элементы оформления, задетые выделением (или под кареткой). */
  function decoratedAtSelection() {
    const sel = window.getSelection();
    if (!sel || !sel.rangeCount) return [];
    const r = sel.getRangeAt(0);
    const caretEl = r.collapsed && r.startContainer.nodeType === 3 ? r.startContainer.parentElement : r.startContainer;
    const out = [];
    content.querySelectorAll(INLINE_SELECTOR).forEach(el => {
      if (r.collapsed ? (el === caretEl || el.contains(caretEl)) : r.intersectsNode(el)) out.push(el);
    });
    return out;
  }
  /* Развернуть элемент, оставив внутри только текст (без оформления). */
  function unwrapEl(el) {
    const frag = document.createDocumentFragment();
    while (el.firstChild) frag.appendChild(el.firstChild);
    el.replaceWith(frag);
  }
  /* Снять конкретное оформление с текста, задетого выделением. */
  function clearDecor(cmd) {
    const d = DECOR[cmd];
    if (!d) return;
    decoratedAtSelection().forEach(el => {
      if (!el.isConnected) return;
      if (d.kind === 'decoration') {
        const parts = decorText(el).split(/\s+/).filter(x => x && x !== d.value && x !== 'none');
        el.style.textDecorationLine = parts.length ? parts.join(' ') : '';
      } else if ((el.style[d.prop] || '').toLowerCase() === d.value) {
        el.style[d.prop] = '';
      }
      const tagHit = (cmd === 'underline' && el.tagName === 'U')
        || (cmd === 'strikeThrough' && /^(S|STRIKE)$/.test(el.tagName))
        || (cmd === 'italic' && /^(I|EM)$/.test(el.tagName));
      /* Оформление снято, обёртка больше не нужна — распускаем её. */
      if (tagHit || (el.tagName === 'SPAN' && !el.getAttribute('style') && !el.className)) unwrapEl(el);
    });
  }
  /* Переключатель оформления: если оно уже есть — надёжно снимаем (даже при
     частичном выделении или каретке на границе), иначе применяем. */
  function toggleInline(cmd) {
    document.execCommand('styleWithCSS', false, true);
    let active = false;
    try { active = document.queryCommandState(cmd); } catch (e) {}
    if (!active) active = decoratedAtSelection().some(el => hasDecor(el, cmd));
    const wasCollapsed = !!window.getSelection()?.isCollapsed;
    if (active) {
      const off = wasCollapsed ? caretTextOffset() : null;
      clearDecor(cmd);
      if (off != null) setCaretTextOffset(off);
    } else {
      document.execCommand(cmd);
    }
  }
  /* Пробелы не должны быть подчёркнуты/зачёркнуты — на пустом месте декорация
     видна как лишняя линия. Вынимаем краевые пробелы из оформленных элементов
     и снимаем оформление с тех, что состоят только из пробелов. */
  function stripSpaceDecoration() {
    const wasCollapsed = !!window.getSelection()?.isCollapsed;
    const off = wasCollapsed ? caretTextOffset() : null;
    let changed = false;
    Array.from(content.querySelectorAll(INLINE_SELECTOR)).forEach(el => {
      if (!el.isConnected) return;
      if (!/underline|line-through/.test(decorText(el)) && !/^(U|S|STRIKE)$/.test(el.tagName)) return;
      changed = true;
      const first = el.firstChild;
      if (first?.nodeType === 3) {
        const m = first.nodeValue.match(/^\s+/);
        if (m) {
          if (m[0].length === first.nodeValue.length) el.parentNode.insertBefore(first, el);
          else { first.nodeValue = first.nodeValue.slice(m[0].length); el.parentNode.insertBefore(document.createTextNode(m[0]), el); }
        }
      }
      const last = el.lastChild;
      if (last?.nodeType === 3 && last.isConnected) {
        const m = last.nodeValue.match(/\s+$/);
        if (m) {
          if (m[0].length === last.nodeValue.length) el.parentNode.insertBefore(last, el.nextSibling);
          else { last.nodeValue = last.nodeValue.slice(0, last.nodeValue.length - m[0].length); el.parentNode.insertBefore(document.createTextNode(m[0]), el.nextSibling); }
        }
      }
      /* Остались только пробелы (или пусто) — обёртка не нужна. */
      if (el.isConnected && !/[^\s]/.test(el.textContent) && !el.querySelector('img')) unwrapEl(el);
    });
    if (changed && off != null) setCaretTextOffset(off);
  }

  /* ---- Ссылки в тексте заметки ----
     Адрес (http/https/ftp или www), набранный или вставленный в заметку,
     автоматически становится кликабельной ссылкой — В ЛЮБОМ оформлении:
     жирный, курсив, заголовок, пункт чек-листа и т.д. Обрабатываем текстовые
     узлы, поэтому существующие inline-обёртки вокруг адреса не мешают, а
     уже собранные <a> и нередактируемые узлы (боксы) пропускаем. */
  const LINK_RE = /\b((?:https?|ftp):\/\/[^\s<>"'`]+|www\.[^\s<>"'`]+)/gi;
  const LINK_TRAIL = /[.,;:!?)\]}"'»]+$/;
  function linkHref(url) { return /^www\./i.test(url) ? 'https://' + url : url; }
  /* Ближайшая ссылка под событием (внутри холста). */
  function linkAtEvent(e) {
    const t = e.target;
    if (!t || t.nodeType !== 1 || !t.closest) return null;
    const a = t.closest('a');
    return a && content.contains(a) ? a : null;
  }
  function openLinkHref(a) {
    const href = a?.getAttribute('href') || '';
    if (!href) return;
    let url = href;
    if (!/^[a-z][a-z0-9+.-]*:/i.test(url)) url = 'https://' + url;
    window.open(url, '_blank', 'noopener');
  }
  /* Развернуть адреса в одном текстовом узле на <a>. Возвращает true, если
     узел менялся. Доводящие знаки («https://x.com,») в адрес не берём. */
  function linkifyTextNode(node) {
    const text = node.nodeValue;
    if (!text || !LINK_RE.test(text)) return false;
    LINK_RE.lastIndex = 0;
    const frag = document.createDocumentFragment();
    let last = 0, m, changed = false;
    while ((m = LINK_RE.exec(text))) {
      const raw = m[0];
      const tail = raw.match(LINK_TRAIL);
      const url = tail ? raw.slice(0, raw.length - tail[0].length) : raw;
      if (!url) continue;
      if (m.index > last) frag.appendChild(document.createTextNode(text.slice(last, m.index)));
      const a = document.createElement('a');
      a.href = linkHref(url);
      a.textContent = url;
      frag.appendChild(a);
      last = m.index + raw.length;
      changed = true;
    }
    if (!changed) return false;
    if (last < text.length) frag.appendChild(document.createTextNode(text.slice(last)));
    node.replaceWith(frag);
    return true;
  }
  /* Пройтись по холсту и собрать адреса в ссылки, не сбивая каретку. */
  function linkifyContent() {
    const wasCollapsed = !!window.getSelection()?.isCollapsed;
    const off = wasCollapsed ? caretTextOffset() : null;
    let changed = false;
    const walker = document.createTreeWalker(content, NodeFilter.SHOW_TEXT);
    const nodes = [];
    while (walker.nextNode()) nodes.push(walker.currentNode);
    for (const node of nodes) {
      if (!node.isConnected) continue;
      let el = node.parentElement, skip = false;
      while (el && el !== content) {
        if (el.tagName === 'A' || el.getAttribute?.('contenteditable') === 'false') { skip = true; break; }
        el = el.parentElement;
      }
      if (skip) continue;
      if (linkifyTextNode(node)) changed = true;
    }
    if (!changed) return;
    /* Пустой <a> (текст целиком стёрли) больше не нужен. */
    content.querySelectorAll('a').forEach(a => { if (a.isConnected && !a.textContent) a.remove(); });
    if (off != null) setCaretTextOffset(off);
  }

  /* ---- Прогрессивное выделение двойным тапом ---- */
  /* Слово → предложение → абзац → весь текст. Повторный двойной тап по тому
     же слову поднимает выделение на ступень выше, тап по другому слову
     начинает заново. Работает и мышью, и пальцем. */
  const WORD_CH = /[\p{L}\p{N}_'’\-]/u;
  const BLOCK_TAGS = /^(DIV|P|H1|H2|H3|LI|BLOCKQUOTE)$/;
  let tapDownPt = null, tapLastAt = 0, tapLastPt = null;
  let dtWord = null, dtLevel = 0, dtLastAt = 0;

  function firstText(node) { if (node.nodeType === 3) return node; for (const c of node.childNodes) { const t = firstText(c); if (t) return t; } return null; }
  function lastText(node) { if (node.nodeType === 3) return node; for (let i = node.childNodes.length - 1; i >= 0; i--) { const t = lastText(node.childNodes[i]); if (t) return t; } return null; }
  /* Текстовая позиция под точкой касания. */
  function textPosFromPoint(x, y) {
    let range = null;
    if (document.caretRangeFromPoint) range = document.caretRangeFromPoint(x, y);
    else if (document.caretPositionFromPoint) {
      const p = document.caretPositionFromPoint(x, y);
      if (p) { range = document.createRange(); range.setStart(p.offsetNode, p.offset); }
    }
    if (!range) return null;
    let node = range.startContainer, off = range.startOffset;
    if (node.nodeType !== 3) {
      if (node.nodeType !== 1) return null;
      const after = node.childNodes[off] && firstText(node.childNodes[off]);
      const before = off > 0 && lastText(node.childNodes[off - 1]);
      if (after) { node = after; off = 0; }
      else if (before) { node = before; off = before.nodeValue.length; }
      else return null;
    }
    return content.contains(node) ? { node, off } : null;
  }
  /* Резерв, если браузер не умеет позицию по точке: берём начало выделения. */
  function selectionPos() {
    const sel = window.getSelection();
    if (!sel?.rangeCount) return null;
    const r = sel.getRangeAt(0);
    let node = r.startContainer, off = r.startOffset;
    if (node.nodeType !== 3) {
      if (node.nodeType !== 1) return null;
      const after = node.childNodes[off] && firstText(node.childNodes[off]);
      const before = off > 0 && lastText(node.childNodes[off - 1]);
      if (after) { node = after; off = 0; }
      else if (before) { node = before; off = before.nodeValue.length; }
      else return null;
    }
    return content.contains(node) ? { node, off } : null;
  }
  function blockOf(node) {
    let el = node.nodeType === 3 ? node.parentElement : node;
    while (el && el !== content) {
      if (el.nodeType === 1 && BLOCK_TAGS.test(el.tagName)) return el;
      el = el.parentElement;
    }
    return content;
  }
  function offsetInRoot(root, node, off) {
    let acc = 0;
    const w = document.createTreeWalker(root, NodeFilter.SHOW_TEXT);
    let n;
    while ((n = w.nextNode())) { if (n === node) return acc + off; acc += n.nodeValue.length; }
    return acc;
  }
  function pointInRoot(root, index) {
    let left = index, last = null;
    const w = document.createTreeWalker(root, NodeFilter.SHOW_TEXT);
    let n;
    while ((n = w.nextNode())) { if (left <= n.nodeValue.length) return { node: n, off: left }; left -= n.nodeValue.length; last = n; }
    return last ? { node: last, off: last.nodeValue.length } : null;
  }
  /* Слово вокруг позиции (буквы, цифры, дефис, апостроф). */
  function wordRangeAt(pos) {
    const text = pos.node.nodeValue;
    let s = pos.off, e = pos.off;
    while (s > 0 && WORD_CH.test(text[s - 1])) s--;
    while (e < text.length && WORD_CH.test(text[e])) e++;
    if (s === e) return null;
    const r = document.createRange();
    r.setStart(pos.node, s); r.setEnd(pos.node, e);
    return r;
  }
  /* Предложение вокруг позиции — границы те же, что у expandToSentence. */
  function sentenceRangeAt(pos) {
    const block = blockOf(pos.node);
    const full = block.textContent;
    const idx = offsetInRoot(block, pos.node, pos.off);
    let s = idx, e = idx;
    while (s > 0 && !SENT_STOP.test(full[s - 1])) s--;
    while (e < full.length && !SENT_STOP.test(full[e])) e++;
    while (s < e && /\s/.test(full[s])) s++;
    while (e > s && /\s/.test(full[e - 1])) e--;
    if (e <= s) return null;
    const a = pointInRoot(block, s), b = pointInRoot(block, e);
    if (!a || !b) return null;
    const r = document.createRange();
    r.setStart(a.node, a.off); r.setEnd(b.node, b.off);
    return r;
  }
  function applyExpand(level, pos, word) {
    let r;
    if (level === 1) r = sentenceRangeAt(pos) || word;
    else if (level === 2) { r = document.createRange(); r.selectNodeContents(blockOf(pos.node)); }
    else if (level === 3) { r = document.createRange(); r.selectNodeContents(content); }
    else r = word;
    if (!r) return;
    const sel = window.getSelection();
    sel.removeAllRanges();
    sel.addRange(r);
    savedRange = r.cloneRange();
    if (fmtOpen) syncFormatState();
  }
  function onTapUp(e) {
    const now = performance.now();
    const pt = { x: e.clientX, y: e.clientY };
    const isDouble = now - tapLastAt < 320 && tapLastPt && Math.hypot(pt.x - tapLastPt.x, pt.y - tapLastPt.y) < 32;
    if (!isDouble) { tapLastAt = now; tapLastPt = pt; return; }
    tapLastAt = 0; tapLastPt = null;
    const pos = textPosFromPoint(pt.x, pt.y) || selectionPos();
    if (!pos) return;
    const word = wordRangeAt(pos);
    if (!word) return;
    const same = dtWord && word.startContainer === dtWord.node && word.startOffset === dtWord.start && word.endOffset === dtWord.end;
    const recent = now - dtLastAt < 1200;
    dtLevel = (same && recent) ? (dtLevel >= 3 ? 0 : dtLevel + 1) : 0;
    dtWord = { node: word.startContainer, start: word.startOffset, end: word.endOffset };
    dtLastAt = now;
    applyExpand(dtLevel, pos, word);
  }
  /* Тап по ссылке открывает её; время нажатия запоминаем, чтобы не путать
     простое касание с длинным (браузерным контекстным меню). */
  let linkTapAt = 0;
  content.addEventListener('pointerdown', e => {
    tapDownPt = { x: e.clientX, y: e.clientY };
    linkTapAt = linkAtEvent(e) ? performance.now() : 0;
  });
  content.addEventListener('pointerup', e => {
    const moved = tapDownPt ? Math.hypot(e.clientX - tapDownPt.x, e.clientY - tapDownPt.y) : 0;
    tapDownPt = null;
    if (moved > 12) { tapLastAt = 0; tapLastPt = null; return; }
    if (e.target?.closest?.('img.rich-img')) return;
    if (e.target?.closest?.('.draw-block')) return;
    /* Тап по боксу чек-листа не должен запускать выделение по двойному тапу. */
    if (e.target?.closest?.('.check-item .box')) return;
    /* Короткое касание ссылки — открываем её, не трогая каретку и выделение. */
    const a = linkAtEvent(e);
    if (a && linkTapAt && performance.now() - linkTapAt < 500) {
      e.preventDefault();
      linkTapAt = 0;
      openLinkHref(a);
      return;
    }
    onTapUp(e);
  });
  /* Не даём браузеру в contenteditable самому переходить по ссылке. */
  content.addEventListener('click', e => {
    if (linkAtEvent(e)) e.preventDefault();
  });

  function runFormat(mutate, key) {
    ensureBlock();
    focusEditor();
    if (INLINE_TOOLS.has(key)) { expandToSentence(); trimSelection(); }
    mutate();
    if (INLINE_TOOLS.has(key)) stripSpaceDecoration();
    /* Оформление могло разобрать <a> на куски — возвращаем ссылки на место. */
    linkifyContent();
    saveSelection();
    commit();
    scheduleSave();
    updMeta();
  }

  content.addEventListener('keyup', saveSelection);
  content.addEventListener('mouseup', saveSelection);
  content.addEventListener('focus', saveSelection);
  document.addEventListener('selectionchange', () => {
    /* Тап по боксу чек-листа не должен ни переписывать сохранённую каретку,
       ни тянуть за собой холст — на время тапа просто молчим. */
    if (performance.now() - lastBoxTap < 450) return;
    const sel = window.getSelection();
    if (sel?.anchorNode && content.contains(sel.anchorNode)) {
      saveSelection(); ensureCaretClear();
      if (fmtOpen) syncFormatState();
    }
  });

  /* Тап по кнопкам меню и панели не должен уводить каретку из текста. */
  const keepFocus = e => { e.preventDefault(); saveSelection(); };
  toolbar.addEventListener('pointerdown', keepFocus);
  toolbar.addEventListener('mousedown', keepFocus);
  formatPanel.addEventListener('pointerdown', keepFocus);
  formatPanel.addEventListener('mousedown', keepFocus);

  /* Вставить пункт чек-листа (нужен и в главном меню, и на панели).
     Блок(и) под кареткой/выделением превращаются в пункты с боксом, СОХРАНЯЯ
     свой текст: бокс встаёт ПЕРЕД уже написанным текстом, а не создаёт пустой
     пункт рядом. Пустой абзац даёт пустой пункт. */
  function makeCheckItem() {
    const item = document.createElement('div');
    item.className = 'check-item';
    item.dataset.check = '1';
    item.innerHTML = '<span class="box" contenteditable="false" role="checkbox" aria-checked="false"></span><span class="lbl"></span>';
    return item;
  }
  /* Ближайший пункт чек-листа над узлом (или null). */
  function checkItemAt(node) {
    let n = node?.nodeType === 3 ? node.parentElement : node;
    while (n && n !== content) {
      if (n.nodeType === 1 && n.classList?.contains('check-item')) return n;
      n = n.parentElement;
    }
    return null;
  }
  /* Распустить пункт в обычный абзац: текст переезжает в <div>, бокс и отметка
     уходят. Возвращает новый абзац (в него же ставится каретка). */
  function itemToBlock(item) {
    const p = document.createElement('div');
    const src = item.querySelector('.lbl') || item;
    while (src.firstChild) p.appendChild(src.firstChild);
    if (!p.firstChild) p.appendChild(document.createElement('br'));
    item.replaceWith(p);
    return p;
  }
  /* Смещение каретки ОТ НАЧАЛА блока (в символах текста), а не всего контента:
     после переноса узлов точки отсчёта всего контента меняются, а границы
     именно этого блока сохраняются. */
  function caretOffsetIn(block) {
    const sel = window.getSelection();
    if (!sel?.rangeCount) return null;
    const r = sel.getRangeAt(0);
    if (r.startContainer !== block && !block.contains(r.startContainer)) return null;
    const pre = r.cloneRange();
    pre.selectNodeContents(block);
    pre.setEnd(r.startContainer, r.startOffset);
    return pre.toString().length;
  }
  /* Поставить каретку в начало текста метки — сразу справа от бокса.
     Если задано смещение, сажаем каретку на тот же символ текста,
     где она стояла до перестановки. */
  function restoreCaretInLabel(lbl, off) {
    let target = null, tOff = 0;
    if (off != null) {
      let left = off;
      const walker = document.createTreeWalker(lbl, NodeFilter.SHOW_TEXT);
      let n;
      while ((n = walker.nextNode())) {
        if (left <= n.nodeValue.length) { target = n; tOff = left; break; }
        left -= n.nodeValue.length;
      }
    }
    const r = document.createRange();
    if (target) r.setStart(target, tOff);
    else { r.selectNodeContents(lbl); r.collapse(true); }
    r.collapse(true);
    const sel = window.getSelection();
    sel.removeAllRanges();
    sel.addRange(r);
    savedRange = r.cloneRange();
  }
  function insertChecklist() {
    runFormat(() => {
      const sel = window.getSelection();
      /* Каретка (или начало выделения) уже в пункте — добавляем новый пункт ПОД
         текущим. Раньше здесь блочный <div> вставлялся внутрь <span class="lbl">
         и разметка ломалась. */
      const cur = checkItemAt(sel?.anchorNode);
      if (cur) {
        const next = makeCheckItem();
        cur.after(next);
        placeCaretIn(next.querySelector('.lbl'));
        return;
      }
      if (sel?.isCollapsed) {
        /* Свежий «голый» текст под кареткой сначала собираем в абзац-<div>,
           иначе блок не найдётся и получится пустой новый пункт. */
        ensureCaretBlock();
      }
      /* Позицию каретки в блоке запоминаем ДО перестановки узлов: потом
         браузер теряет её привязку и «сажает» фокус на бокс — следующий тап
         по нему промахивается, а каретка налазит на галочку. */
      const caretBlockEl = sel?.isCollapsed ? caretBlock() : null;
      const caretOff = caretBlockEl ? caretOffsetIn(caretBlockEl) : null;
      const blocks = targetBlocks().filter(b => b.isConnected && !b.classList?.contains('check-item'));
      if (!blocks.length) {
        /* Собирать нечего — добавляем пустой пункт в конец заметки. */
        const item = makeCheckItem();
        content.appendChild(item);
        placeCaretIn(item.querySelector('.lbl'));
        return;
      }
      let firstLbl = null;
      blocks.forEach(block => {
        if (!block.isConnected || block.classList?.contains('check-item')) return;
        const item = makeCheckItem();
        const lbl = item.querySelector('.lbl');
        while (block.firstChild) lbl.appendChild(block.firstChild);
        block.replaceWith(item);
        if (!firstLbl) firstLbl = lbl;
      });
      /* Каретку возвращаем в текст пункта — сразу справа от бокса, на том же
         символе, где она стояла до вставки. */
      if (firstLbl) restoreCaretInLabel(firstLbl, caretOff);
    });
  }

  /* ---------- Рисунок: бесконечный холст с продолжением ---------- */
  function insertDrawing(result) {
    if (!result || !result.dataUrl) return;
    runFormat(() => {
      insertAtCursor(`<div class="draw-block" data-drawing="1" data-draw-id="${result.id}"><img class="rich-img" src="${result.dataUrl}" alt="" draggable="false"></div><div><br></div>`);
    });
  }

  function openDrawEditor(block) {
    if (selImg) clearImageSelection();
    saveSelection();
    content.blur();
    const drawId = block?.getAttribute('data-draw-id') || null;
    const img = block?.querySelector('img');
    openDrawing(async (res) => {
      if (res === null) return;                 // отмена — ничего не меняем
      if (!res.dataUrl) {                        // всё стёрли — убираем рисунок
        if (block) { block.remove(); commit(); scheduleSave(); updMeta(); }
        return;
      }
      if (block && img) {
        img.src = res.dataUrl;
        if (!block.hasAttribute('data-draw-id')) block.setAttribute('data-draw-id', res.id || '');
        commit(); scheduleSave(); updMeta();
      } else {
        insertDrawing(res);
      }
    }, { drawId, imageSrc: (!drawId && img) ? img.src : null });
  }

  /* Главное нижнее меню: «Т» раскрывает панель, остальные — вставка контента. */
  toolbar.querySelectorAll('.tool').forEach(btn => btn.addEventListener('click', () => {
    vibrate(8);
    const k = btn.dataset.tool;
    if (k === 'format') { setFormatOpen(!fmtOpen); return; }
    saveSelection();
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
      openDrawEditor(null);
    } else if (k === 'check') {
      insertChecklist();
    }
  }));

  /* Блочный стиль абзаца (H1/H2/H3) — переключателем: повторный тап возвращает
     обычный текст. Блок берём самый внутренний (caretBlock), а не через
     queryCommandValue: внутри цитаты он вернул бы саму цитату, и заголовок
     «сбрасывался» — инструмент менял тег цитаты вместо текста внутри неё. */
  function currentBlockTag() {
    let cur = '';
    try { cur = (document.queryCommandValue('formatBlock') || '').toLowerCase(); } catch (e) {}
    return cur;
  }
  /* Тег блока под кареткой меняем САМИ, а не через execCommand('formatBlock'):
     внутри blockquote браузеры перестраивают саму цитату — «жёлтая полоса»
     обрывается на куски, или команда вовсе не срабатывает. Меняется только
     тег текущего блока, обёртка цитаты остаётся целой. */
  function replaceBlockTag(block, tag) {
    const el = document.createElement(tag);
    while (block.firstChild) el.appendChild(block.firstChild);
    /* Сохраняем выравнивание и отступы, заданные на самом блоке. */
    if (block.style.textAlign) el.style.textAlign = block.style.textAlign;
    if (block.style.paddingLeft) el.style.paddingLeft = block.style.paddingLeft;
    if (block.style.paddingRight) el.style.paddingRight = block.style.paddingRight;
    block.replaceWith(el);
    /* Текстовые узлы переехали вместе с кареткой, но у пустого блока
       привязка могла потеряться — возвращаем каретку в новый блок. */
    const sel = window.getSelection();
    const n = sel?.anchorNode;
    if (!n || !el.contains(n)) placeCaretIn(el);
    return el;
  }
  function toggleBlock(tag) {
    const b = caretBlock();
    let inside = !!b && b.tagName.toLowerCase() === tag.toLowerCase();
    /* Блока рядом нет (курсор на пустом месте, выделен весь текст) — смотрим у редактора. */
    if (!b) inside = currentBlockTag() === tag.toLowerCase();
    if (b) { replaceBlockTag(b, inside ? 'div' : tag); return; }
    document.execCommand('formatBlock', false, inside ? 'div' : tag);
  }
  /* Снять цитату: убрать тег blockquote, сохранив абзацы внутри него. */
  function unwrapQuote(bq) {
    const kids = Array.from(bq.childNodes);
    const hasBlocks = kids.some(n => n.nodeType === 1 && /^(DIV|P|H1|H2|H3|UL|OL|BLOCKQUOTE)$/.test(n.tagName));
    if (hasBlocks) {
      const frag = document.createDocumentFragment();
      kids.forEach(n => frag.appendChild(n));
      bq.replaceWith(frag);
      return kids[0] || null;
    }
    const div = document.createElement('div');
    kids.forEach(n => div.appendChild(n));
    bq.replaceWith(div);
    return div;
  }
  /* Находится ли курсор/выделение внутри цитаты (цитата может быть выше
     текущего блока, поэтому проверяем предков, а не сам блок). */
  function selectionInQuote() {
    const blocks = targetBlocks();
    if (blocks.length) return blocks.some(b => !!b.closest('blockquote'));
    return !!caretBlock()?.closest?.('blockquote');
  }
  /* Поставить цитату: вложить блоки ВНУТРЬ blockquote, а не заменять их тег.
     Иначе formatBlock менял, например, h1 на blockquote — формат текста
     внутри цитаты «сбрасывался». */
  function wrapQuote() {
    const blocks = targetBlocks();
    if (!blocks.length) { document.execCommand('formatBlock', false, 'blockquote'); return; }
    /* Соседние блоки с одним родителем объединяем в одну цитату. */
    const groups = [];
    blocks.forEach(b => {
      const g = groups.find(g => g.parent === b.parentNode && g.last.nextSibling === b);
      if (g) g.last = b; else groups.push({ parent: b.parentNode, first: b, last: b });
    });
    groups.forEach(g => {
      const bq = document.createElement('blockquote');
      g.first.replaceWith(bq);
      let n = g.first;
      while (n) { const next = n === g.last ? null : n.nextSibling; bq.appendChild(n); n = next; }
    });
    /* Узлы не пересоздавались — каретка осталась валидной, возвращаем её. */
    restoreSelection();
  }
  /* Цитата — переключатель: повторный тап снимает её. */
  function toggleQuote() {
    if (!selectionInQuote()) { wrapQuote(); return; }
    const bqs = new Set();
    targetBlocks().forEach(b => { const q = b.closest('blockquote'); if (q) bqs.add(q); });
    if (!bqs.size) { const q = content.querySelector('blockquote'); if (q) bqs.add(q); }
    if (!bqs.size) return;
    let first = null;
    Array.from(bqs).forEach(q => { const n = unwrapQuote(q); if (!first) first = n; });
    if (first) placeCaretAtEnd(first);
  }
  function placeCaretAtEnd(node) {
    const r = document.createRange();
    r.selectNodeContents(node);
    r.collapse(false);
    const sel = window.getSelection();
    sel.removeAllRanges();
    sel.addRange(r);
    savedRange = r.cloneRange();
  }
  /* Повторный тап по активному выравниванию возвращает абзац к левому краю —
     так инструмент снимается повторным нажатием. */
  function alignCmd(cmd) {
    let active = false;
    try { active = document.queryCommandState(cmd); } catch (e) {}
    document.execCommand('styleWithCSS', false, true);
    document.execCommand(active && cmd !== 'justifyLeft' ? 'justifyLeft' : cmd);
  }
  /* ---------- Отступ блока ----------
     Отступ делаем сами, а не через execCommand('indent'): нужен шаг и сторона.
     У абзаца, выровненного вправо, отступ уходит от правого края — «отводы»
     работают наоборот. «Уменьшить отступ» серая, пока отступа нет. */
  const INDENT_STEP = 28;
  function caretBlock() {
    let node = window.getSelection()?.anchorNode;
    if (node?.nodeType === 3) node = node.parentElement;
    while (node && node !== content) {
      if (node.nodeType === 1 && /^(DIV|P|H1|H2|H3|BLOCKQUOTE|LI)$/.test(node.tagName)) return node;
      node = node.parentElement;
    }
    return null;
  }
  /* Текст, набранный в свежей заметке, бывает «голым» (без абзацев-<div>).
     Тогда caretBlock() пуст, и инструменты не находят блок под кареткой.
     Собираем такой текст в <div>, разбивая строки по <br>. Каретку сохраняем
     по текстовому смещению (перестановка узлов её не сбивает). */
  function ensureCaretBlock() {
    if (caretBlock()) return;
    const off = caretTextOffset();
    const isBlock = n => n.nodeType === 1 &&
      /^(DIV|P|H1|H2|H3|BLOCKQUOTE|UL|OL|LI|FIGURE)$/.test(n.tagName);
    let div = null;
    Array.from(content.childNodes).forEach(node => {
      if (node.nodeType === 1 && node.tagName === 'BR') { div = null; node.remove(); return; }
      if (isBlock(node)) { div = null; return; }
      if (!div) { div = document.createElement('div'); content.insertBefore(div, node); }
      div.appendChild(node);
    });
    if (off != null) setCaretTextOffset(off);
  }
  function indentSide(block) {
    if (!block) return 'left';
    const ta = block.style.textAlign || getComputedStyle(block).textAlign || '';
    return /right/i.test(ta) ? 'right' : 'left';
  }
  function indentOf(block) {
    if (!block) return 0;
    const v = indentSide(block) === 'right' ? block.style.paddingRight : block.style.paddingLeft;
    return Math.max(0, parseFloat(v) || 0);
  }
  /* Блоки, которых касается выделение. Без выделения — блок под кареткой.
     Отступ применяем ко всем выделенным абзацам, а не только к одному. */
  function targetBlocks() {
    const sel = window.getSelection();
    if (!sel || !sel.rangeCount) return [];
    const r = sel.getRangeAt(0);
    if (r.collapsed) { const b = caretBlock(); return b ? [b] : []; }
    const all = [];
    content.querySelectorAll('div,p,h1,h2,h3,blockquote,li').forEach(b => {
      if (r.intersectsNode(b)) all.push(b);
    });
    return all.filter(b => !all.some(o => o !== b && b.contains(o)));
  }
  function changeIndent(dir) {
    const blocks = targetBlocks();
    if (!blocks.length) return;
    blocks.forEach(block => {
      const side = indentSide(block) === 'right' ? 'paddingRight' : 'paddingLeft';
      const next = Math.max(0, indentOf(block) + dir * INDENT_STEP);
      block.style[side] = next ? next + 'px' : '';
    });
  }
  const FMT_ACTIONS = {
    p: () => { const b = caretBlock(); if (b) replaceBlockTag(b, 'div'); else document.execCommand('formatBlock', false, 'div'); },
    h1: () => toggleBlock('h1'),
    h2: () => toggleBlock('h2'),
    h3: () => toggleBlock('h3'),
    bold: () => { document.execCommand('styleWithCSS', false, true); document.execCommand('bold'); },
    italic: () => toggleInline('italic'),
    underline: () => toggleInline('underline'),
    strike: () => toggleInline('strikeThrough'),
    ul: () => document.execCommand('insertUnorderedList'),
    ol: () => document.execCommand('insertOrderedList'),
    indent: () => changeIndent(1),
    outdent: () => changeIndent(-1),
    quote: () => toggleQuote(),
    'align-left': () => alignCmd('justifyLeft'),
    'align-center': () => alignCmd('justifyCenter'),
    'align-right': () => alignCmd('justifyRight'),
    'align-justify': () => alignCmd('justifyFull'),
  };
  /* Подсветка инструментов по стилю под кареткой. */
  function syncFormatState() {
    if (!fmtOpen || !formatPanel.isConnected) return;
    const q = c => { try { return document.queryCommandState(c); } catch (e) { return false; } };
    let block = '';
    try { block = (document.queryCommandValue('formatBlock') || '').toLowerCase(); } catch (e) {}
    const on = {
      /* Реальное состояние текста: у заголовка он полужирный, поэтому кнопка
         «Жирный» подсвечена и снимается/возвращается повторным тапом. */
      bold: q('bold'), italic: q('italic'), underline: q('underline'), strike: q('strikeThrough'),
      ul: q('insertUnorderedList'), ol: q('insertOrderedList'),
      h1: block === 'h1', h2: block === 'h2', h3: block === 'h3',
      p: !block || block === 'div' || block === 'p', quote: block === 'blockquote',
      'align-left': q('justifyLeft'), 'align-center': q('justifyCenter'),
      'align-right': q('justifyRight'), 'align-justify': q('justifyFull'),
    };
    formatPanel.querySelectorAll('[data-fmt]').forEach(b => b.classList.toggle('active', !!on[b.dataset.fmt]));
    /* «Уменьшить отступ» неактивна, пока ни у одного выделенного блока нет отступа. */
    const out = formatPanel.querySelector('[data-fmt="outdent"]');
    if (out) out.disabled = !targetBlocks().some(b => indentOf(b) > 0);
  }
  /* Применение инструментов панели форматирования к тексту заметки. */
  formatPanel.addEventListener('click', e => {
    const btn = e.target.closest('[data-fmt]');
    if (!btn || !fmtOpen) return;
    vibrate(8);
    const k = btn.dataset.fmt;
    if (k === 'close') { setFormatOpen(false); return; }
    saveSelection();
    if (k === 'check') insertChecklist();
    else if (FMT_ACTIONS[k]) runFormat(FMT_ACTIONS[k], k);
    syncFormatState();
  });

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
    let active = false, ref = null, vy = 0, raf = 0;
    let lastY = e.clientY;
    const line = document.createElement('div');
    line.className = 'img-drop-line';
    /* Куда встанет фото: до первого блока, чья середина ниже пальца. */
    const place = () => {
      const kids = [...content.children].filter(k => k !== unit);
      const cr = content.getBoundingClientRect();
      let y = cr.bottom; ref = null;
      for (const k of kids) {
        const kr = k.getBoundingClientRect();
        if (lastY < kr.top + kr.height / 2) { y = kr.top; ref = k; break; }
      }
      line.style.left = (cr.left + 12) + 'px';
      line.style.width = Math.max(0, cr.width - 24) + 'px';
      line.style.top = y + 'px';
    };
    /* Автоскролл у кромок: палец у самой верхней/нижней кромки холста подтягивает
       его за собой. Без этого фото нельзя было дотащить туда, что выше или ниже
       видимой области, — в длинной заметке «на самый верх» не получалось. */
    const tick = () => {
      raf = 0;
      if (!vy) return;
      const before = bodyWrap.scrollTop;
      bodyWrap.scrollTop += vy;
      if (bodyWrap.scrollTop === before) return;   // дошли до края — дальше некуда
      place();
      raf = requestAnimationFrame(tick);
    };
    const move = ev => {
      if (!active && Math.hypot(ev.clientX - sx, ev.clientY - sy) < 8) return;
      if (!active) { active = true; img.classList.add('img-dragging'); document.body.appendChild(line); }
      lastY = ev.clientY;
      place();
      const r = bodyWrap.getBoundingClientRect();
      const EDGE = 72, MAXV = 16;
      const topGap = ev.clientY - r.top, botGap = r.bottom - ev.clientY;
      if (topGap < EDGE) vy = -Math.ceil(MAXV * Math.min(1, (EDGE - topGap) / EDGE));
      else if (botGap < EDGE) vy = Math.ceil(MAXV * Math.min(1, (EDGE - botGap) / EDGE));
      else vy = 0;
      if (vy && !raf) raf = requestAnimationFrame(tick);
    };
    const up = () => {
      window.removeEventListener('pointermove', move);
      window.removeEventListener('pointerup', up);
      window.removeEventListener('pointercancel', up);
      if (raf) { cancelAnimationFrame(raf); raf = 0; }
      vy = 0;
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
  const isImgTarget = t => !!(t && t.closest && t.closest('img.rich-img') && !t.closest('[data-drawing]'));

  /* тап по фото не должен ставить каретку и открывать клавиатуру холста */
  content.addEventListener('pointerdown', e => {
    const t = e.target;
    const drawBlock = t?.closest?.('.draw-block');
    if (drawBlock) {
      e.preventDefault(); e.stopPropagation();
      imgTouchAt = performance.now();
      if (document.activeElement === content || document.activeElement === titleInput) { content.blur(); titleInput.blur(); }
      openDrawEditor(drawBlock);
      return;
    }
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
