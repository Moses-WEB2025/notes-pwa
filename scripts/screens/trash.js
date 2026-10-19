/* ============ Корзина ============
   Корзина у каждого раздела своя: на главной — обычные заметки, на «Скрытых» —
   личные (скрытые или из скрытой папки). Так удалённые личные заметки не
   всплывают на главной и наоборот. */
import { Store, escapeHtml, bindPress, Nav, purgeTrash } from '../core.js';
import { toast, confirmSheet } from '../components/ui.js';

export function openTrashScreen(opts = {}) {
  const isPrivate = opts.mode === 'hidden';
  const tEl = document.createElement('div');
  tEl.className = 'screen trash-screen';

  tEl.innerHTML = `
    <div class="appbar back-header">
      <button class="icon-btn" data-a="back" data-press><span class="material-symbols-rounded">arrow_back</span></button>
      <div class="appbar-title">Корзина</div>
      <span style="width:44px"></span>
    </div>
    <div class="scroll" style="padding-top:8px"></div>`;

  const sc = tEl.querySelector('.scroll');
  const fill = async () => {
    /* Чистим просроченное перед показом: список и баннер должны совпадать
       с тем, что реально лежит в корзине. */
    const purged = await purgeTrash();
    const [all, folders] = await Promise.all([Store.notes(), Store.folders()]);
    const hiddenFolderIds = new Set(folders.filter(f => f.hidden).map(f => f.id));
    const isPrivateNote = n => !!n.hidden || !!(n.folderId && hiddenFolderIds.has(n.folderId));
    const del = all
      .filter(n => n.deleted && (isPrivate ? isPrivateNote(n) : !isPrivateNote(n)))
      /* Порядок — по времени попадания в корзину (у старых записей его нет,
         тогда по времени правки). */
      .sort((a, b) => (b.deletedAt || b.updatedAt) - (a.deletedAt || a.updatedAt));
    sc.innerHTML = `
      ${purged ? `<div class="trash-banner">Заметки, находящиеся в корзине более 30 дней, были безвозвратно удалены</div>` : ''}
      ${del.length ? '' : `
        <div class="trash-empty">
          <span class="material-symbols-rounded">article</span>
          <div class="t">Нет заметок</div>
        </div>`}`;
    del.forEach(n => {
      const r = document.createElement('div');
      r.className = 'folder-row';
      r.innerHTML = `<span class="f-name">${escapeHtml(n.title || 'Без названия')}</span>
        <button class="icon-btn" data-a="restore" data-press><span class="material-symbols-rounded">restore</span></button>`;
      r.querySelector('[data-a=restore]').addEventListener('click', async () => {
        n.deleted = false;
        n.deletedAt = null;
        await Store.saveNoteRaw(n);
        /* Главный экран обновляется только по этому событию. Без него заметка
           возвращается в базу, но в списке не появляется — выглядит как потерянная. */
        document.dispatchEvent(new CustomEvent('notes-changed'));
        toast('Восстановлено');
        fill();
      });
      sc.appendChild(r);
    });
    bindPress(sc);
  };
  tEl.querySelector('[data-a=back]').addEventListener('click', () => Nav.pop());
  /* Экран добавляется вызывающим кодом через Nav.pushEl — иначе он появлялся
     бы мгновенно, без слайда, как у нативного приложения. */
  bindPress(tEl);
  fill();
  return tEl;
}
