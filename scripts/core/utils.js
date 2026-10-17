/* Утилиты: даты, форматирование, текст */

export function pad2(n) { return n < 10 ? '0' + n : '' + n; }

const MONTHS_GEN = ['января','февраля','марта','апреля','мая','июня','июля','августа','сентября','октября','ноября','декабря'];
const MONTHS_CAP = ['Январь','Февраль','Март','Апрель','Май','Июнь','Июль','Август','Сентябрь','Октябрь','Ноябрь','Декабрь'];
const DOW_SHORT = ['Вс','Пн','Вт','Ср','Чт','Пт','Сб'];

export function fmtCardTime(ts) {
  const d = new Date(ts);
  const now = new Date();
  const sameDay = d.toDateString() === now.toDateString();
  const yest = new Date(now); yest.setDate(now.getDate() - 1);
  if (sameDay) return `${DOW_SHORT[d.getDay()]} ${pad2(d.getHours())}:${pad2(d.getMinutes())}`;
  if (d.toDateString() === yest.toDateString()) return `Вчера ${pad2(d.getHours())}:${pad2(d.getMinutes())}`;
  const day = d.getDate();
  const m = MONTHS_CAP[d.getMonth()];
  const label = day <= 2 ? m + ' ' + day : m + ' ' + day; // e.g. "Ноябрь 30"
  if (d.getFullYear() !== now.getFullYear()) return label + ' ' + d.getFullYear();
  return label;
}

export function fmtEditorMeta(ts) {
  const d = new Date(ts);
  return `${d.getDate()} ${MONTHS_GEN[d.getMonth()]} ${pad2(d.getHours())}:${pad2(d.getMinutes())}`;
}

export function fmtDuration(sec) {
  const m = Math.floor(sec / 60), s = Math.floor(sec % 60);
  return `${m}:${pad2(s)}`;
}

export function initials(text) {
  const t = (text || '').trim();
  return t ? t.slice(0, 1).toUpperCase() : '•';
}

export function escapeHtml(s) {
  return (s || '').replace(/[&<>"']/g, c => ({ '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#39;' }[c]));
}

export function uid() {
  return Date.now().toString(36) + Math.random().toString(36).slice(2, 8);
}

/* Подсветка поиска: возвращает HTML со <mark> вокруг совпадений (без чувствительности к регистру) */
export function highlight(text, q) {
  const t = escapeHtml(text || '');
  if (!q) return t;
  try {
    const re = new RegExp('(' + q.replace(/[.*+?^${}()|[\]\\]/g, '\\$&') + ')', 'gi');
    return t.replace(re, '<mark class="hm">$1</mark>');
  } catch (e) { return t; }
}

export function debounce(fn, ms) {
  let t;
  return function (...args) { clearTimeout(t); t = setTimeout(() => fn.apply(this, args), ms); };
}

export function vibrate(ms) {
  try { navigator.vibrate && navigator.vibrate(ms || 10); } catch (e) {}
}

/* plain text from html for card previews */
export function htmlToPlain(html) {
  const div = document.createElement('div');
  div.innerHTML = html;
  (div.querySelectorAll('mark[data-dummy]') || []).forEach(m => m.remove());
  return (div.textContent || '').replace(/\s+/g, ' ').trim();
}
