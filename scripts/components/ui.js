/* Общие UI-компоненты: тосты, bottom-sheet диалоги, контекстное меню */
import { Store, uid } from '../core.js';
const escapeHtml = s => (s || '').replace(/[&<>"']/g, c => ({ '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#39;' }[c]));

export function toast(text, ms = 2200) {
  const holder = document.getElementById('toast-holder');
  const t = document.createElement('div');
  t.className = 'toast';
  t.textContent = text;
  holder.appendChild(t);
  requestAnimationFrame(() => t.classList.add('show'));
  setTimeout(() => {
    t.classList.remove('show');
    setTimeout(() => t.remove(), 300);
  }, ms);
}

/* ===== Bottom-sheet (единый стиль окон, как на скриншотах) ===== */
export function openSheet(builder) {
  const bd = document.createElement('div');
  bd.className = 'sheet-backdrop';
  const sheet = document.createElement('div');
  sheet.className = 'sheet';
  bd.appendChild(sheet);
  document.body.appendChild(bd);
  const api = {
    close: () => {
      bd.classList.remove('open');
      sheet.classList.remove('open');
      setTimeout(() => bd.remove(), 320);
    },
    el: sheet
  };
  builder(sheet, api);
  requestAnimationFrame(() => {
    bd.classList.add('open');
    sheet.classList.add('open');
  });
  bd.addEventListener('click', e => { if (e.target === bd) api.close(); });
  return api;
}

export function sheetSections({ title, sections }) {
  return openSheet((sheet, api) => {
    sheet.innerHTML = `<div class="pull-strip"></div><div class="sheet-title">${title}</div>`;
    sections.forEach(sec => sheet.appendChild(sec(api)));
  });
}

/* Диалог подтверждения — bottom sheet в стиле скриншота:
   заголовок, подпись, две большие кнопки-чипа «Отмена» (белая) и危险 красным */
export function confirmSheet({ title = 'Удалите заметки', text = '', okLabel = 'Удалить' }) {
  return new Promise(resolve => {
    sheetSections({
      title: escapeHtml(title),
      sections: [api => {
        const box = document.createElement('div');
        box.className = 'confirm-box';
        box.innerHTML = `
          ${text ? `<div class="confirm-text">${escapeHtml(text)}</div>` : ''}
          <div class="confirm-btns">
            <button class="confirm-btn" data-a="cancel">Отмена</button>
            <button class="confirm-btn danger" data-a="ok">${escapeHtml(okLabel)}</button>
          </div>`;
        box.addEventListener('click', e => {
          const b = e.target.closest('[data-a]'); if (!b) return;
          api.close();
          resolve(b.dataset.a === 'ok');
        });
        return box;
      }]
    });
  });
}

/* совместимые алиасы */
export const confirmDialog = confirmSheet;

/* Меню выбора папки (перенос заметки, редактор): «Новая папка» — в самом верху,
   затем «Без папки» и папки текущего раздела. Скрытые и обычные папки не
   пересекаются: на главной показываются обычные, на «Скрытых» — скрытые.
   Созданная папка сразу попадает в нужный раздел и назначается выбранной.
   onPick(folderId | null) вызывается после выбора. */
export function folderPickSheet({ title = 'В папку', isPrivate = false, onPick }) {
  return openSheet((sheet, api) => {
    sheet.innerHTML = `<div class="pull-strip"></div><div class="sheet-title">${escapeHtml(title)}</div>`;
    const box = document.createElement('div');
    box.className = 'sheet-actions';
    sheet.appendChild(box);

    const render = async () => {
      const all = await Store.folders();
      const list = all.filter(f => !f.deleted && (isPrivate ? f.hidden : !f.hidden));
      box.innerHTML = [
        `<button class="sheet-action" data-new><span class="material-symbols-rounded">create_new_folder</span>Новая папка</button>`,
        `<button class="sheet-action" data-f=""><span class="material-symbols-rounded">block</span>Без папки</button>`,
        ...list.map(f => `<button class="sheet-action" data-f="${f.id}"><span class="material-symbols-rounded">folder</span>${escapeHtml(f.name)}</button>`),
      ].join('');
    };

    box.addEventListener('click', async e => {
      const b = e.target.closest('button'); if (!b) return;
      if (b.hasAttribute('data-new')) {
        const name = await promptSheet({ title: 'Новая папка', placeholder: 'Название папки' });
        if (!name) return;
        const f = { id: uid(), name, createdAt: Date.now(), hidden: isPrivate };
        await Store.saveFolder(f);
        /* Оповещаем экраны — ряды чипов и «Папки» перечитают себя */
        document.dispatchEvent(new CustomEvent('folders-changed'));
        api.close();
        onPick(f.id);
        return;
      }
      const fid = b.dataset.f || null;
      api.close();
      onPick(fid);
    });

    render();
  });
}

/* Диалог ввода — тоже bottom sheet */
export function promptSheet({ title, placeholder = '', value = '', okLabel = 'Сохранить' }) {
  return new Promise(resolve => {
    sheetSections({
      title: escapeHtml(title),
      sections: [api => {
        const box = document.createElement('div');
        box.className = 'confirm-box';
        box.innerHTML = `
          <input class="dlg-input" placeholder="${escapeHtml(placeholder)}" value="${escapeHtml(value)}" />
          <div class="confirm-btns">
            <button class="confirm-btn" data-a="cancel">Отмена</button>
            <button class="confirm-btn" data-a="ok">${escapeHtml(okLabel)}</button>
          </div>`;
        const input = box.querySelector('input');
        input.focus();
        const close = ok => {
          api.close();
          resolve(ok ? (input.value.trim() || null) : undefined);
        };
        box.querySelector('[data-a=cancel]').addEventListener('click', () => close(false));
        box.querySelector('[data-a=ok]').addEventListener('click', () => close(true));
        input.addEventListener('keydown', e => { if (e.key === 'Enter') close(true); });
        return box;
      }]
    });
  });
}

/* ===== Контекстное меню (долгий тап / 3 точки) ===== */
export function attachMenu(anchor, items) {
  const menu = document.createElement('div');
  menu.className = 'menu-surface';
  menu.innerHTML = items.map((it, i) => {
    if (it.divider) return `<div class="menu-divider"></div>`;
    const right = it.checked ? `<span class="material-symbols-rounded mi-check">check</span>` : '';
    return `<div class="mi" data-i="${i}"><span class="material-symbols-rounded">${it.icon}</span><span>${escapeHtml(it.label)}</span>${right}</div>`;
  }).join('');
  document.body.appendChild(menu);

  /* позиция: под якорем, правый край по якорю; для карточек — по точке касания (справа, ниже центра) */
  let ax = null, ay = null;
  if (anchor && anchor.getBoundingClientRect) {
    try {
      const r = anchor.getBoundingClientRect();
      /* если якорь большой (карточка) — меню ближе к правому верху якоря */
      if (r.width > window.innerWidth * 0.5) { ax = r.right - 14; ay = r.top + 26; }
      else { ax = r.right; ay = r.bottom + 6; }
    } catch (e) {}
  }
  if (ax !== null) {
    const mw = menu.offsetWidth || 210;
    menu.style.left = Math.max(12, Math.min(window.innerWidth - mw - 12, ax - mw)) + 'px';
    menu.style.top = ay + 'px';
    requestAnimationFrame(() => {
      const mh = menu.offsetHeight;
      if (ay + mh > window.innerHeight - 16) menu.style.top = Math.max(64, window.innerHeight - mh - 16) + 'px';
    });
  } else {
    menu.style.right = '18px';
    menu.style.top = '34%';
  }

  const show = () => {
    menu.classList.add('open');
    setTimeout(() => document.addEventListener('pointerdown', onDoc, true), 10);
  };
  const close = () => {
    menu.classList.remove('open');
    document.removeEventListener('pointerdown', onDoc, true);
    setTimeout(() => menu.remove(), 180);
  };
  const onDoc = (e) => { if (!menu.contains(e.target)) close(); };
  menu.addEventListener('click', e => {
    const mi = e.target.closest('.mi');
    if (!mi) return;
    const item = items[+mi.dataset.i];
    close();
    item.onClick && setTimeout(item.onClick, 120);
  });
  show();
  return { close };
}
