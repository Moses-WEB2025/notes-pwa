/* ============ Общий рендер сетки заметок (обычный + режим выбора) ============ */
import { fmtCardTime, escapeHtml, highlight, htmlToPlain, bindPress, vibrate } from '../core.js';
import { morphDisabled } from '../morph.js';
import { openEditor } from './editor.js';

export function renderNotesGrid(container, notes, q = '', opts = {}) {
  /* Выезд карточек снизу играем ТОЛЬКО при первой отрисовке сетки за сессию.
     Раньше анимация запускалась на каждой перерисовке (возврат из редактора,
     поиск, смена фильтра) — заметки на главной «подпрыгивали» при каждом
     обновлении списка. */
  const firstPaint = !container.__gridPainted;
  container.__gridPainted = true;

  /* FLIP: запоминаем, где карточки стояли до перерисовки, чтобы смена порядка
     (например, отредактированная заметка уехала наверх) шла плавно, а не
     рывком. Прокрутку сохраняем тоже — по ней же считаем смещения. */
  const keepTop = container.scrollTop;
  const before = new Map();
  if (!firstPaint) {
    container.querySelectorAll('.note-card').forEach(c => before.set(c.dataset.id, c.getBoundingClientRect()));
  }

  container.innerHTML = '';
  container.classList.toggle('selectable', !!(opts && opts.sel && opts.sel.active));

  const col1 = document.createElement('div'); col1.className = 'grid-col';
  const col2 = document.createElement('div'); col2.className = 'grid-col';
  container.append(col1, col2);

  /* Балансируем колонки по оценке высоты карточки: каждая следующая идёт в
     более короткую. Раньше карточки раскладывались по «весу» с порогом, и все
     текстовые заметки уезжали в правый столбец — левый оставался пустым. */
  let h1 = 0, h2 = 0;
  /* В режиме выбора сетка перерисовывается на каждый тап: общая анимация
     появления превращала бы это в «дёрганье» всего экрана. Поэтому в режиме
     выбора карточки появляются без анимации, а «отскок» получает только та,
     которую сейчас выбрали или сняли (opts.bounceId). */
  const inSel = !!(opts && opts.sel && opts.sel.active);
  notes.forEach((n, i) => {
    const m = cardMetrics(n);
    const card = buildCard(n, q, opts, m);
    if (firstPaint && !inSel && i < 8) { card.classList.add('entering'); card.style.animationDelay = Math.min(i * 28, 200) + 'ms'; }
    if (opts && opts.bounceId != null && card.dataset.id === opts.bounceId) card.classList.add('bounce');
    if (h1 <= h2) { col1.appendChild(card); h1 += m.height; }
    else { col2.appendChild(card); h2 += m.height; }
  });
  /* Прокрутку возвращаем ДО измерения новых позиций: иначе они посчитались бы
     от «нулевого» скролла, и карточки поехали бы на величину прокрутки. */
  container.scrollTop = opts.keepTop != null ? opts.keepTop : keepTop;
  if (before.size) playReorder(container, before);
  bindPress(container);
}

/* Плавная перестановка (FLIP): карточке, сменившей место, даём стартовать от
   прежней позиции и уехать на новую. Новым карточкам анимировать нечего. */
function playReorder(container, before) {
  if (morphDisabled()) return;
  const moved = [];
  container.querySelectorAll('.note-card').forEach(card => {
    const o = before.get(card.dataset.id);
    if (!o) return;
    const r = card.getBoundingClientRect();
    const dx = o.left - r.left, dy = o.top - r.top;
    if (Math.abs(dx) < 1 && Math.abs(dy) < 1) return;
    card.style.transition = 'none';           /* ставим старт без анимации… */
    card.style.transform = `translate(${dx}px, ${dy}px)`;
    moved.push(card);
  });
  if (!moved.length) return;
  void container.offsetWidth;
  requestAnimationFrame(() => {               /* …и отпускаем — едет transition из CSS */
    moved.forEach(card => { card.style.transition = ''; card.style.transform = ''; });
  });
}

/* ---- Правила размеров карточки в общем списке ----
   Высота карточек должна различаться (не все одного размера), но не разъезжаться
   в крайности. Поэтому число строк текста разбито на несколько фиксированных
   ступеней по объёму текста, а высота карточки считается по фактическим размерам
   строк (шрифт/межстрочный интервал из CSS).
   Верхняя ступень намеренно небольшая — длинные заметки не должны занимать
   половину экрана. */
const TITLE_LINE = 22.4;   /* 17px × 1.32 */
const BODY_LINE  = 20.3;   /* 14px × 1.45 */
function bodyLinesFor(chars) {
  if (chars <= 0)   return 1;   /* «Нет текста» — одна строка */
  if (chars <= 70)  return 2;   /* короткая */
  if (chars <= 170) return 3;   /* средняя */
  if (chars <= 320) return 4;   /* длинная */
  return 5;                     /* очень длинная — максимум */
}
function cardMetrics(n) {
  const chars = htmlToPlain(n.content).length;
  const titleChars = (n.title || '').trim().length;
  const titleLines = titleChars ? Math.min(2, Math.ceil(titleChars / 19)) : 1;
  const bodyLines = bodyLinesFor(chars);
  /* padding 15+13, два flex-gap по 8, строка meta ≈ 17 */
  const height = 28 + titleLines * TITLE_LINE + 16 + bodyLines * BODY_LINE + 17;
  return { chars, titleLines, bodyLines, height };
}

/* Обновление выделения без перерисовки сетки — сохраняет позицию скролла */
export function updateSelection(container, selected) {
  container.querySelectorAll('.note-card').forEach(card => {
    card.classList.toggle('sur-select', selected.has(card.dataset.id));
  });
}

function buildCard(n, q, opts, m) {
  m = m || cardMetrics(n);
  const card = document.createElement('div');
  card.className = 'note-card';
  card.dataset.id = n.id;
  card.setAttribute('data-press', '');

  if (n.color) card.classList.add('c-' + n.color);
  if (n.pinned) card.classList.add('pinned');
  if (n.hidden) card.classList.add('is-hidden');
  if (opts && opts.sel && opts.sel.active && opts.sel.selected.has(n.id)) card.classList.add('sur-select');

  const plain = htmlToPlain(n.content);
  const titleHtml = q ? highlight(n.title || '', q) : escapeHtml(n.title || '');
  /* Превью показывает начало текста. Если совпадение с запросом находится
     дальше — сдвигаем фрагмент к совпадению, иначе подсветки не видно. */
  let bodyText = plain.slice(0, 320);
  if (q) {
    const idx = plain.toLowerCase().indexOf(q);
    if (idx > 0) {
      const start = Math.max(0, idx - 24);
      bodyText = (start > 0 ? '… ' : '') + plain.slice(start, start + 320);
    }
  }
  const bodyHtml = q ? highlight(bodyText, q) : escapeHtml(bodyText);

  card.innerHTML = `
    <div class="nc-pin ${n.pinned ? 'show' : ''}"><span class="material-symbols-rounded">push_pin</span></div>
    <div class="nc-title">${titleHtml || '<span class="dim">Без названия</span>'}</div>
    ${plain ? `<div class="nc-body" style="--nc-lines:${m.bodyLines}">${bodyHtml}</div>` : `<div class="nc-body dim" style="--nc-lines:1">Нет текста</div>`}
    <div class="nc-meta">
      <span>${escapeHtml(fmtCardTime(n.updatedAt))}</span>
      ${n.audio ? `<span class="audio-chip"><span class="eq"><i></i><i></i><i></i><i></i><i></i></span></span>` : ''}
    </div>
    <div class="nc-radio"><span class="material-symbols-rounded">check</span></div>`;

  /* Взаимодействие: зажим открывает режим выбора, обычный тап открывает
     заметку либо (в режиме выбора) отмечает/снимает её.
     ВАЖНО: отметку делаем прямо на touchend. Раньше она ждала click, а браузер
     не всегда его генерирует после лёгкого смещения пальца — из-за этого часть
     тапов «терялась» и по заметке приходилось кликать повторно. */
  let lpTimer = null, lpFired = false, startX = 0, startY = 0, moved = false, tapHandled = false;

  card.addEventListener('touchstart', e => {
    const t = e.touches[0];
    startX = t.clientX; startY = t.clientY;
    moved = false; lpFired = false;
    lpTimer = setTimeout(() => {
      lpFired = true;
      vibrate(18);
      card.classList.remove('pressed');
      if (opts && opts.sel && !opts.sel.active) opts.sel.onEnter(n.id);   /* зажим → режим выбора */
      else if (opts && opts.sel) opts.sel.onToggle(n.id);
    }, 430);
  }, { passive: true });

  card.addEventListener('touchmove', e => {
    const t = e.touches[0];
    /* Заметное смещение — это прокрутка списка, а не тап: зажим отменяем. */
    if (Math.abs(t.clientX - startX) > 8 || Math.abs(t.clientY - startY) > 8) {
      moved = true;
      clearTimeout(lpTimer);
    }
  }, { passive: true });

  card.addEventListener('touchend', e => {
    clearTimeout(lpTimer);
    if (lpFired || moved) return;
    if (opts && opts.sel && opts.sel.active) {
      /* Отмечаем сразу и гасим синтетический click, который иначе снял бы выбор */
      e.preventDefault();
      tapHandled = true;
      opts.sel.onToggle(n.id);
    }
  });
  card.addEventListener('touchcancel', () => clearTimeout(lpTimer), { passive: true });

  card.addEventListener('click', () => {
    if (tapHandled) { tapHandled = false; return; }
    if (lpFired) { lpFired = false; return; }
    if (opts && opts.sel && opts.sel.active) opts.sel.onToggle(n.id);
    /* Цвет и саму карточку передаём сразу: редактор покрасит холст и системную
       полосу синхронно, не дожидаясь загрузки заметки, и вырастет из карточки. */
    else openEditor(n.id, { color: n.color, origin: card });
  });

  return card;
}
