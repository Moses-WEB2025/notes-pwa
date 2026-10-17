/* ============ Экран «Папки» (скрин 2) ============ */
import { Store, escapeHtml, Nav, uid, vibrate, bindPress } from '../core.js';
import { toast, confirmDialog, promptDialog, attachMenu } from '../components/ui.js';

export function openFolders() {
  const el = document.createElement('div');
  el.className = 'screen folders-screen';
  el.style.zIndex = '5';

  const appbar = document.createElement('div');
  appbar.className = 'appbar back-header';
  appbar.innerHTML = `
    <button class="icon-btn" data-a="back" data-press><span class="material-symbols-rounded">arrow_back</span></button>
    <div class="appbar-title">Папки</div>
    <button class="icon-btn" data-a="trash" data-press aria-label="Корзина"><span class="material-symbols-rounded">delete</span></button>`;

  const scroll = document.createElement('div');
  scroll.className = 'scroll';
  scroll.style.paddingTop = '8px';

  el.append(appbar, scroll);

  async function load() {
    const [folders, allNotes] = await Promise.all([Store.folders(), Store.notes()]);
    const active = allNotes.filter(n => !n.deleted);
    scroll.innerHTML = '';

    /* «Все» — с галочкой (выбрано) */
    const all = document.createElement('div');
    all.className = 'folder-row selected';
    all.setAttribute('data-press', '');
    all.innerHTML = `<span class="material-symbols-rounded f-check">check</span><span class="f-name">Все</span><span class="f-count">${active.length}</span>`;
    all.addEventListener('click', () => Nav.pop());
    scroll.appendChild(all);

    /* папки */
    folders.filter(f => !f.deleted).forEach(f => {
      const row = document.createElement('div');
      row.className = 'folder-row';
      row.setAttribute('data-press', '');
      const cnt = active.filter(n => n.folderId === f.id).length;
      row.innerHTML = `<span class="material-symbols-rounded f-fold" style="color:var(--accent)">folder</span><span class="f-name">${escapeHtml(f.name)}</span><span class="f-count">${cnt}</span>`;
      let lp = null, fired = false;
      row.addEventListener('touchstart', () => { fired = false; lp = setTimeout(() => { fired = true; vibrate(20); folderMenu(f); }, 480); }, { passive: true });
      ['touchmove', 'touchend', 'touchcancel'].forEach(ev => row.addEventListener(ev, () => clearTimeout(lp), { passive: true }));
      row.addEventListener('click', () => { if (fired) { fired = false; return; } openFolderView(f); });
      scroll.appendChild(row);
    });

    /* «Новая папка» */
    const add = document.createElement('button');
    add.className = 'folder-new';
    add.innerHTML = `<span class="material-symbols-rounded">add_circle</span><span class="t">Новая папка</span>`;
    add.addEventListener('click', async () => {
      const name = await promptDialog({ title: 'Новая папка', placeholder: 'Название папки' });
      if (!name) return;
      await Store.saveFolder({ id: uid(), name, createdAt: Date.now() });
      toast('Папка создана');
      load();
    });
    scroll.appendChild(add);
    bindPress(scroll);
  }

  async function openFolderView(f) {
    const vEl = document.createElement('div');
    vEl.className = 'screen folder-view';
    vEl.style.zIndex = '6';
    vEl.innerHTML = `
      <div class="appbar back-header">
        <button class="icon-btn" data-a="back" data-press><span class="material-symbols-rounded">arrow_back</span></button>
        <div class="appbar-title">${escapeHtml(f.name)}</div>
        <span style="width:44px"></span>
      </div>
      <div class="scroll grid-holder"></div>`;
    const grid = vEl.querySelector('.grid-holder');
    const fill = async () => {
      const notes = (await Store.notes()).filter(n => !n.deleted && n.folderId === f.id).sort((a, b) => b.updatedAt - a.updatedAt);
      grid.innerHTML = '';
      if (!notes.length) {
        grid.innerHTML = `<div style="padding:60px 20px;text-align:center;color:var(--text-tertiary);width:100%">В папке пока нет заметок</div>`;
        return;
      }
      const { renderNotesGrid } = await import('./grid.js');
      renderNotesGrid(grid, notes, '', () => fill());
    };
    vEl.querySelector('[data-a=back]').addEventListener('click', () => vEl.remove());
    el.appendChild(vEl);
    fill();
  }

  function folderMenu(f) {
    attachMenu(f.name, [
      { icon: 'edit', label: 'Переименовать', onClick: async () => {
        const name = await promptDialog({ title: 'Переименовать папку', value: f.name });
        if (name) { f.name = name; await Store.saveFolder(f); load(); }
      } },
      { icon: 'delete', label: 'Удалить папку', onClick: async () => {
        if (await confirmDialog({ title: `Удалить папку «${f.name}»?`, text: 'Заметки останутся, но потеряют папку.' })) {
          const notes = await Store.notes();
          for (const n of notes.filter(n => n.folderId === f.id)) { n.folderId = null; await Store.saveNoteRaw(n); }
          await Store.delFolder(f.id);
          toast('Папка удалена');
          load();
        }
      } },
    ]);
  }

  appbar.querySelector('[data-a=back]').addEventListener('click', () => Nav.pop());
  appbar.querySelector('[data-a=trash]').addEventListener('click', () => {
    toast('Корзина пуста');
  });

  load();
  return el;
}
