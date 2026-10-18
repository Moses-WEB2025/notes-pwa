/* Проверка scroll-fx.js: ветка Android (протяжка по инерции работает)
   и ветка iOS (слушатель scroll не ставится, прокрутку не ломает). */
import assert from 'node:assert/strict';
import { pathToFileURL } from 'node:url';

const PLATFORM = process.env.PLATFORM || 'android';

let T = 0;
globalThis.performance = { now: () => T };
let rafId = 1;
const rafQueue = new Map();
globalThis.requestAnimationFrame = (cb) => { const id = rafId++; rafQueue.set(id, cb); return id; };
globalThis.cancelAnimationFrame = (id) => { rafQueue.delete(id); };
function flushFrame(dtMs) {
  T += dtMs;
  const items = [...rafQueue.entries()];
  rafQueue.clear();
  for (const [, cb] of items) cb(T);
}

function makeStyle() {
  const m = new Map();
  return {
    setProperty(k, v) { m.set(k, String(v)); },
    removeProperty(k) { m.delete(k); },
    getPropertyValue(k) { return m.has(k) ? m.get(k) : ''; },
    set transition(v) { m.set('transition', v); },
    get transition() { return m.get('transition') || ''; },
    set willChange(v) { m.set('will-change', v); },
    get willChange() { return m.get('will-change') || ''; },
  };
}
function makeEl(scrollHeight, clientHeight) {
  const el = {
    scrollTop: 0, scrollHeight, clientHeight,
    style: makeStyle(), classList: {},
    matches(sel) { return sel === '.scroll, .editor-body'; },
    closest(sel) { return sel === '.scroll, .editor-body' ? el : null; },
  };
  return el;
}

const listeners = {};
globalThis.document = { addEventListener(type, fn) { (listeners[type] ||= []).push(fn); } };
globalThis.window = { matchMedia: () => ({ matches: false }) };
/* В Node 24 globalThis.navigator — свойство только для чтения, обычное
   присваивание падает с TypeError. Подменяем через defineProperty. */
Object.defineProperty(globalThis, 'navigator', {
  configurable: true,
  value: PLATFORM === 'ios'
    ? { platform: 'iPhone', userAgent: 'Mozilla/5.0 (iPhone; CPU iPhone OS 17_0 like Mac OS X)', maxTouchPoints: 5 }
    : { platform: 'Linux armv8l', userAgent: 'Mozilla/5.0 (Linux; Android 13) Chrome/120', maxTouchPoints: 5 },
});

const mod = await import(pathToFileURL('/workspace/scripts/scroll-fx.js').href);
mod.initScrollFx();

function fling(el, from, to, step, ms, handler) {
  el.scrollTop = from;
  T += 16; handler({ target: el });
  for (let top = from + step; ; top += step) {
    el.scrollTop = step > 0 ? Math.min(to, top) : Math.max(to, top);
    T += ms; handler({ target: el });
    if (el.scrollTop === to) break;
  }
}
function trackPull(el, frames) {
  let min = 0, max = 0;
  for (let i = 0; i < frames; i++) {
    flushFrame(16);
    const raw = el.style.getPropertyValue('--pull-y');
    if (raw !== '') { const v = parseFloat(raw); min = Math.min(min, v); max = Math.max(max, v); }
  }
  return { min, max, rest: el.style.getPropertyValue('--pull-y'), spring: !!el.__pullSpring };
}

const onScroll = (listeners.scroll || [])[0];
console.log(`PLATFORM=${PLATFORM} scroll-слушатель:`, onScroll ? 'есть' : 'нет');

if (PLATFORM === 'ios') {
  /* Регресс был именно в этом: на iOS слушатель ставиться не должен, иначе
     пружина дёргает transform скроллера во время инерции и прокрутка встаёт. */
  assert.equal(onScroll, undefined, 'на iOS зарегистрирован слушатель scroll');
  const el = makeEl(3000, 600);
  const s = trackPull(el, 40);
  assert.equal(s.rest, '', 'трансформ --pull-y должен остаться пустым');
  console.log('ios:', s, '— прокрутку ничего не трогает');
} else {
  assert.ok(onScroll, 'на Android слушатель scroll не зарегистрирован');
  {
    const el = makeEl(3000, 600);
    fling(el, 1000, 2400, 40, 16, onScroll);
    const s = trackPull(el, 240);
    console.log('низ:', s);
    assert.ok(s.min < -5, `растяжка у нижнего края не включилась (min=${s.min})`);
    assert.equal(s.spring, false);
    assert.equal(s.rest, '');
  }
  {
    const el = makeEl(3000, 600);
    fling(el, 1400, 0, -40, 16, onScroll);
    const s = trackPull(el, 240);
    console.log('верх:', s);
    assert.ok(s.max > 5, `растяжка у верхнего края не включилась (max=${s.max})`);
  }
  {
    const el = makeEl(3000, 600);
    fling(el, 2380, 2400, 4, 16, onScroll);
    const s = trackPull(el, 120);
    console.log('медленно:', s);
    assert.equal(s.min, 0, 'растяжка включилась на медленном доведении');
  }
  {
    const el = makeEl(3000, 600);
    fling(el, 1000, 1200, 40, 16, onScroll);
    const s = trackPull(el, 120);
    console.log('середина:', s);
    assert.equal(s.min, 0, 'растяжка включилась в середине списка');
  }
}
console.log('OK');
