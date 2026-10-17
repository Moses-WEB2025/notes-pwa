/* ============ Общий рендер сетки заметок ============ */
import { fmtCardTime, escapeHtml, highlight, htmlToPlain, bindPress, vibrate } from '../core.js';
import { openEditor } from './editor.js';

export function renderNotesGrid(container, notes, q = '', onChanged) {
  container.innerHTML = '';
  const col1 = document.createElement('div'); col1.className = 'grid-col';
  const col2 = document.createElement('div'); col2.className = 'grid-col';
  container.append(col1, col2);

  notes.forEach((n, i) => {
    const card = buildCard(n, q, onChanged);
    if (i < 8) { card.classList.add('entering'); card.style.animationDelay = Math.min(i * 28, 200) + 'ms'; }
    const weight = (n.color === 'blue' ? 2.4 : 0) + htmlToPlain(n.content).length / 90 + (n.title || '').length / 44;
    (weight > 1.2 ? col2 : col1).appendChild(card);
  });
  bindPress(container);
}

function buildCard(n, q, onChanged) {
  const card = document.createElement('div');
  card.className = 'note-card';
  card.setAttribute('data-press', '');

  if (n.color) card.classList.add('c-' + n.color);
  if (n.pinned) card.classList.add('pinned');

  const plain = htmlToPlain(n.content);
  const titleHtml = q ? highlight(n.title || '', q) : escapeHtml(n.title || '');
  const bodyHtml = q ? highlight(plain.slice(0, 160), q) : escapeHtml(plain.slice(0, 160));

  card.innerHTML = `
    <div class="nc-pin ${n.pinned ? 'show' : ''}"><span class="material-symbols-rounded">push_pin</span></div>
    <div class="nc-title">${titleHtml || '<span class="dim">Без названия</span>'}</div>
    ${plain ? `<div class="nc-body">${bodyHtml}</div>` : `<div class="nc-body dim">Нет текста</div>`}
    <div class="nc-meta">
      <span>${escapeHtml(fmtCardTime(n.updatedAt))}</span>
      ${n.audio ? `<span class="audio-chip"><span class="eq"><i></i><i></i><i></i><i></i><i></i></span></span>` : ''}
    </div>`;

  let lpTimer = null, lpFired = false;
  card.addEventListener('touchstart', () => {
    lpFired = false;
    lpTimer = setTimeout(() => { lpFired = true; vibrate(15); card.classList.remove('pressed'); longPress(n); }, 480);
  }, { passive: true });
  ['touchmove', 'touchend', 'touchcancel'].forEach(ev => card.addEventListener(ev, () => clearTimeout(lpTimer), { passive: true }));
  card.addEventListener('click', () => { if (lpFired) { lpFired = false; return; } openEditor(n.id); });

  function longPress(n2) {
    import('../components/ui.js').then(async ({ attachMenu, confirmSheet }) => {
      attachMenu(card, [
        { icon: 'delete', label: 'Удалить', onClick: async () => {
          if (await confirmSheet({ title: 'Удалите заметки', text: 'Удалить эту заметку?' })) {
            const { Store } = await import('../core.js');
            await Store.delNote(n.id);
            onChanged && onChanged();
            toast && 0;
          }
        } },
      ]);
    });
  }

  return card;
}
