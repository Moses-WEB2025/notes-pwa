/* Проверка morph.js: рост экрана из карточки и сжатие обратно.
   DOM заменён минимальными заглушками, таймеры настоящие (переход 340 мс). */
import assert from 'node:assert/strict';
import { pathToFileURL } from 'node:url';

let reduced = false;
globalThis.window = {
  innerWidth: 390,
  innerHeight: 844,
  matchMedia: () => ({ matches: reduced }),
};

const rafQ = [];
globalThis.requestAnimationFrame = (cb) => { rafQ.push(cb); return rafQ.length; };
function flush() { const q = rafQ.splice(0); q.forEach((cb) => cb(0)); }
const sleep = (ms) => new Promise((r) => setTimeout(r, ms));

function makeEl(children = []) {
  return {
    style: {},
    children,
    classList: {
      _s: new Set(),
      add(...c) { c.forEach((x) => this._s.add(x)); },
      remove(...c) { c.forEach((x) => this._s.delete(x)); },
      contains(c) { return this._s.has(c); },
    },
    get offsetWidth() { return 390; },
    __rect: { left: 0, top: 0, right: 390, bottom: 844, width: 390, height: 844 },
    getBoundingClientRect() { return this.__rect; },
  };
}
globalThis.getComputedStyle = (el) => ({ borderTopLeftRadius: el.__radius || '0px' });

const mod = await import(pathToFileURL('/workspace/scripts/morph.js').href);
const { morphOpen, morphClose, morphDisabled, cardOnScreen } = mod;

/* --- «уменьшить движение» отключает морфинг --- */
assert.equal(morphDisabled(), false);
reduced = true;
assert.equal(morphDisabled(), true);
reduced = false;

/* --- карточка вне экрана морфинг не запускает --- */
{
  const far = makeEl();
  far.__rect = { left: 20, top: -300, bottom: -50, width: 170, height: 200 };
  assert.equal(cardOnScreen(far), false, 'карточка над экраном должна отсекаться');
  const near = makeEl();
  near.__rect = { left: 20, top: 100, bottom: 300, width: 170, height: 200 };
  assert.equal(cardOnScreen(near), true);
}

/* --- рост из карточки --- */
{
  const kid = makeEl();
  const el = makeEl([kid]);
  const card = makeEl();
  card.__radius = '20px';
  card.__rect = { left: 20, top: 100, right: 190, bottom: 300, width: 170, height: 200 };

  morphOpen(el, card);
  assert.ok(el.classList.contains('morphing'), 'класс перехода не поставлен');
  assert.match(el.style.transform, /^translate\(20px, 100px\) scale\(/, 'стартовая геометрия не от карточки');
  assert.equal(kid.style.opacity, '0', 'содержимое должно быть скрыто на время роста');

  const sx = 170 / 390, sy = 200 / 844;
  const expectR = 20 / Math.min(sx, sy);
  assert.ok(Math.abs(parseFloat(el.style.borderRadius) - expectR) < 1e-6,
    `стартовый радиус не компенсирован (${el.style.borderRadius})`);

  flush();  /* кадр: уходим в полный экран */
  assert.equal(el.style.transform, 'translate(0px, 0px) scale(1, 1)');
  assert.equal(el.style.borderRadius, '0px');

  await sleep(140);
  assert.equal(kid.style.opacity, '', 'содержимое должно проявиться к концу роста');

  await sleep(240);
  assert.equal(el.classList.contains('morphing'), false, 'класс перехода не снят');
  assert.equal(el.style.transform, '', 'инлайновый transform не очищен');
  assert.equal(el.style.borderRadius, '');
  assert.equal(kid.style.transition, '', 'служебный transition у содержимого не снят');
  assert.equal(kid.style.opacity, '');
  console.log('рост: ok');
}

/* --- сжатие обратно в карточку --- */
{
  const kid = makeEl();
  const el = makeEl([kid]);
  const card = makeEl();
  card.__radius = '20px';
  card.__rect = { left: 10, top: 50, right: 180, bottom: 250, width: 170, height: 200 };

  let ended = false;
  morphClose(el, card, () => { ended = true; });
  assert.ok(el.classList.contains('morphing'));
  assert.equal(el.style.transform, 'translate(0px, 0px) scale(1, 1)', 'сжатие должно стартовать с полного экрана');
  assert.equal(ended, false, 'экран убирается только по окончании');

  flush();
  assert.match(el.style.transform, /^translate\(10px, 50px\) scale\(/, 'не сжался к прямоугольнику карточки');
  assert.equal(kid.style.opacity, '0', 'содержимое должно гаснуть при сжатии');

  await sleep(360);
  assert.equal(ended, true, 'колбэк окончания не вызван');
  console.log('сжатие: ok');
}

console.log('OK');
