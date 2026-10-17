/* Общие UI-компоненты: тосты, диалоги, меню, bottom sheet */
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

export function confirmDialog({ title, text = '', okLabel = 'Удалить', danger = true }) {
  return new Promise(resolve => {
    const bd = document.createElement('div');
    bd.className = 'dlg-backdrop';
    bd.innerHTML = `
      <div class="dialog">
        <h3>${title}</h3>
        ${text ? `<p>${text}</p>` : ''}
        <div class="dlg-btns">
          <button class="dlg-btn" data-a="cancel">Отмена</button>
          <button class="dlg-btn ${danger ? 'danger' : ''}" data-a="ok">${okLabel}</button>
        </div>
      </div>`;
    document.body.appendChild(bd);
    requestAnimationFrame(() => bd.classList.add('open'));
    bd.addEventListener('click', e => {
      const a = e.target.closest('[data-a]') && e.target.closest('[data-a]').dataset.a;
      if (!a) return;
      bd.classList.remove('open');
      setTimeout(() => bd.remove(), 220);
      resolve(a === 'ok');
    });
  });
}

export function promptDialog({ title, placeholder = '', value = '', okLabel = 'Сохранить' }) {
  return new Promise(resolve => {
    const bd = document.createElement('div');
    bd.className = 'dlg-backdrop';
    bd.innerHTML = `
      <div class="dialog">
        <h3>${title}</h3>
        <input class="dlg-input" placeholder="${placeholder}" value="${value.replace(/"/g, '&quot;')}" />
        <div class="dlg-btns">
          <button class="dlg-btn" data-a="cancel">Отмена</button>
          <button class="dlg-btn" data-a="ok">${okLabel}</button>
        </div>
      </div>`;
    document.body.appendChild(bd);
    requestAnimationFrame(() => bd.classList.add('open'));
    const input = bd.querySelector('input');
    input.addEventListener('input', () => {
      // автоуменьшение колёса масштаба не даю; ввод — обычный
    });
    input.focus();
    setTimeout(() => input.focus(), 60);
    const close = (ok) => {
      bd.classList.remove('open');
      setTimeout(() => bd.remove(), 220);
      resolve(ok ? (input.value.trim() || null) : undefined);
    };
    bd.querySelector('[data-a=cancel]').addEventListener('click', () => close(false));
    bd.querySelector('[data-a=ok]').addEventListener('click', () => close(true));
    input.addEventListener('keydown', e => { if (e.key === 'Enter') close(true); });
  });
}

/* Меню: позиционируется рядом с якорем */
export function attachMenu(anchor, items) {
  const menu = document.createElement('div');
  menu.className = 'menu-surface';
  menu.innerHTML = items.map((it, i) => {
    if (it.divider) return `<div style="height:1px;background:var(--divider);margin:4px 6px;"></div>`;
    const right = it.color ? `<span class="mi-sw" style="background:${it.css||it.color};border-color:${it.css||it.color}"></span>` : (it.checked ? `<span class="material-symbols-rounded mi-check">check</span>` : '');
    return `<div class="mi" data-i="${i}"><span class="material-symbols-rounded">${it.icon}</span><span>${escapeHtml(it.label)}</span>${right}</div>`;
  }).join('');
  document.body.appendChild(menu);

  /* позиция: под якорем (right-aligned как на скриншотах) */
  let ax = null, ay = null;
  if (anchor && anchor.getBoundingClientRect) {
    try { const r = anchor.getBoundingClientRect(); ax = r.right; ay = r.bottom + 6; } catch (e) {}
  }
  if (ax !== null) {
    const mw = menu.offsetWidth || 210;
    menu.style.left = Math.max(12, Math.min(window.innerWidth - mw - 12, ax - mw)) + 'px';
    menu.style.top = ay + 'px';
    setTimeout(() => {
      const mh = menu.offsetHeight;
      if (ay + mh > window.innerHeight - 16) menu.style.top = Math.max(64, ay - mh - 52) + 'px';
    }, 0);
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

/* Bottom sheet с контентом (element builder) */
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
