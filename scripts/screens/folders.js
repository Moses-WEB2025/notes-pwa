/* ============ Экран «Папки» ============ */
import { Store, escapeHtml, Nav, uid, vibrate, bindPress } from '../core.js';
import { toast, confirmSheet, promptSheet, attachMenu } from '../components/ui.js';
import { openTrashScreen } from './trash.js';

export function openFolders(opts = {}) {
  /* Экран общий для двух разделов: открыт с главной — показывает обычные папки
     и обычные заметки, открыт со «Скрытых» — только скрытые папки и личные
     заметки. Разделы не пересекаются. */
  const isPrivate = opts.mode === 'hidden';
  const el = document.createElement('div');
  el.className = 'screen folders-screen';

  /* шапка: слева — назад, затем мусорка, справа пусто */
  const appbar = document.createElement('div');
  appbar.className = 'appbar back-header';
  appbar.innerHTML = `
    <button class="icon-btn" data-a="back" data-press><span class="material-symbols-rounded">arrow_back</span></button>
    <button class="icon-btn" data-a="trash" data-press aria-label="Корзина"><span class="material-symbols-rounded">delete</span></button>
    <div class="appbar-title">Папки</div>
    <span style="width:44px"></span>`;

  const scroll = document.createElement('div');
  scroll.className = 'scroll';
  scroll.style.paddingTop = '8px';

  el.append(appbar, scroll);

  /* Сообщаем главному экрану и «Скрытым», что папки изменились: они перечитают
     себя и учтут скрытые папки сразу, без перехода между экранами. */
  const notify = () => document.dispatchEvent(new CustomEvent('folders-changed'));

  async function load() {
    const [folders, allNotes] = await Promise.all([Store.folders(), Store.notes()]);
    /* Личная (скрытая) заметка — либо с флагом hidden, либо лежит в скрытой
       папке. Разделы строго не пересекаются: на главной — только обычные,
       на «Скрытых» — только личные. */
    const hiddenFolderIds = new Set(folders.filter(f => f.hidden).map(f => f.id));
    const priv = n => !!n.hidden || !!(n.folderId && hiddenFolderIds.has(n.folderId));
    const active = allNotes.filter(n => !n.deleted && (isPrivate ? priv(n) : !priv(n)));
    scroll.innerHTML = '';

    /* «Все» — с галочкой (выбрано) */
    const all = document.createElement('div');
    all.className = 'folder-row selected';
    all.setAttribute('data-press', '');
    all.innerHTML = `<span class="material-symbols-rounded f-check">check</span><span class="f-name">Все</span><span class="f-count">${active.length}</span>`;
    all.addEventListener('click', () => Nav.pop());
    scroll.appendChild(all);

    /* Папки только своего раздела: на главной скрытых нет, на «Скрытых» —
       только скрытые. Замок отмечает личную папку, заметки которой видны
       лишь на экране «Скрытые». */
    folders.filter(f => !f.deleted && (isPrivate ? f.hidden : !f.hidden)).forEach(f => {
      const row = document.createElement('div');
      row.className = 'folder-row' + (f.hidden ? ' f-hidden' : '');
      row.setAttribute('data-press', '');
      const cnt = allNotes.filter(n => !n.deleted && n.folderId === f.id && (isPrivate ? priv(n) : !priv(n))).length;
      row.innerHTML = `<span class="material-symbols-rounded f-fold" style="color:${f.hidden ? 'var(--private-blue)' : 'var(--accent)'}">${f.hidden ? 'lock' : 'folder'}</span><span class="f-name">${escapeHtml(f.name)}</span><span class="f-count">${cnt}</span>`;
      let lp = null, fired = false;
      row.addEventListener('touchstart', () => { fired = false; lp = setTimeout(() => { fired = true; vibrate(20); folderMenu(f); }, 480); }, { passive: true });
      ['touchmove', 'touchend', 'touchcancel'].forEach(ev => row.addEventListener(ev, () => clearTimeout(lp), { passive: true }));
      row.addEventListener('click', () => { if (fired) { fired = false; return; }
        import('./grid.js').then(({ renderNotesGrid }) => openFolderView(f));
      });
      scroll.appendChild(row);
    });

    /* «Новая папка» — во всю ширину, плюс в кружке над подписью */
    const add = document.createElement('button');
    add.className = 'folder-new';
    add.innerHTML = `<span class="material-symbols-rounded">add_circle</span><span class="t">Новая папка</span>`;
    add.addEventListener('click', async () => {
      const name = await promptSheet({ title: 'Новая папка', placeholder: 'Название папки' });
      if (!name) return;
      /* Папка, созданная на «Скрытых», сразу скрытая — иначе она ушла бы на
         главную и разделы пересеклись бы. */
      await Store.saveFolder({ id: uid(), name, createdAt: Date.now(), hidden: isPrivate });
      toast('Папка создана');
      notify();
      load();
    });
    scroll.appendChild(add);
    bindPress(scroll);
  }

  async function openFolderView(f) {
    const vEl = document.createElement('div');
    vEl.className = 'screen folder-view';
    vEl.innerHTML = `
      <div class="appbar back-header">
        <button class="icon-btn" data-a="back" data-press><span class="material-symbols-rounded">arrow_back</span></button>
        <div class="appbar-title">${escapeHtml(f.name)}</div>
        <span style="width:44px"></span>
      </div>
      <div class="scroll grid-holder"></div>`;
    const grid = vEl.querySelector('.grid-holder');
    const fill = async () => {
      const notes = (await Store.notes()).filter(n => !n.deleted && n.folderId === f.id && (isPrivate || !n.hidden)).sort((a, b) => b.updatedAt - a.updatedAt);
      grid.innerHTML = '';
      if (!notes.length) {
        grid.innerHTML = `<div style="padding:60px 20px;text-align:center;color:var(--text-tertiary);width:100%">В папке пока нет заметок</div>`;
        return;
      }
      const { renderNotesGrid } = await import('./grid.js');
      renderNotesGrid(grid, notes, '');
    };
    vEl.querySelector('[data-a=back]').addEventListener('click', () => Nav.pop());
    Nav.pushEl(vEl);
    fill();
  }

  function folderMenu(f) {
    attachMenu(f, [
      { icon: 'edit', label: 'Переименовать', onClick: async () => {
        const name = await promptSheet({ title: 'Переименовать папку', value: f.name });
        if (name) { f.name = name; await Store.saveFolder(f); notify(); load(); }
      } },
      /* Скрытая папка не появляется на главной — её заметки видны только на
         экране «Скрытые» (там же и сама папка, отдельным чипом). */
      { icon: f.hidden ? 'visibility' : 'visibility_off', label: f.hidden ? 'Показать папку' : 'Скрыть папку', onClick: async () => {
        f.hidden = !f.hidden;
        await Store.saveFolder(f);
        toast(f.hidden ? 'Папка скрыта' : 'Папка показана');
        notify();
        load();
      } },
      { icon: 'delete', label: 'Удалить папку', onClick: async () => {
        if (await confirmSheet({ title: 'Удалите папку', text: `Удалить папку «${f.name}»? Заметки останутся, но потеряют папку.` })) {
          const notes = await Store.notes();
          for (const n of notes.filter(n => n.folderId === f.id)) { n.folderId = null; await Store.saveNoteRaw(n); }
          await Store.delFolder(f.id);
          toast('Папка удалена');
          notify();
          load();
        }
      } },
    ]);
  }

  appbar.querySelector('[data-a=back]').addEventListener('click', () => Nav.pop());
  appbar.querySelector('[data-a=trash]').addEventListener('click', () => Nav.pushEl(openTrashScreen({ mode: isPrivate ? 'hidden' : 'main' })));

  load();
  return el;
}
