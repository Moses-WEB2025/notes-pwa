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
  const el = makeEl([makeEl()]);
  const card = makeEl();
  card.__radius = '20px';
  card.__rect = { left: 20, top: 100, right: 190, bottom: 300, width: 170, height: 200 };

  morphOpen(el, card);
  assert.ok(el.classList.contains('morph-hide'), 'содержимое не скрыто на старте');
  assert.ok(el.classList.contains('morph-fade'), 'переход прозрачности не подготовлен');
  assert.equal(el.style.willChange, 'transform', 'слой не подготовлен под transform');
  assert.match(el.style.transform, /^translate3d\(20px, 100px, 0\) scale\(/, 'стартовая геометрия не от карточки');

  /* Радиус компенсируется ПО КАЖДОЙ ОСИ: иначе угол на экране вытянулся бы. */
  const sx = 170 / 390, sy = 200 / 844;
  assert.equal(el.style.borderRadius, `${(20 / sx).toFixed(1)}px / ${(20 / sy).toFixed(1)}px`,
    `стартовый радиус не компенсирован по осям (${el.style.borderRadius})`);

  flush();  /* кадр: включаем переход и уходим в полный экран */
  assert.ok(el.classList.contains('morphing'), 'класс перехода не поставлен');
  assert.equal(el.style.transform, 'translate3d(0px, 0px, 0) scale(1, 1)');
  assert.equal(el.style.borderRadius, '0px');

  await sleep(180);
  assert.equal(el.classList.contains('morph-hide'), false, 'содержимое должно проявиться к концу роста');
  assert.equal(el.classList.contains('morph-fade'), true, 'класс перехода нужен до конца');

  await sleep(240);
  assert.equal(el.classList.contains('morphing'), false, 'класс перехода не снят');
  assert.equal(el.classList.contains('morph-fade'), false, 'служебный класс прозрачности не снят');
  assert.equal(el.style.transform, '', 'инлайновый transform не очищен');
  assert.equal(el.style.borderRadius, '');
  console.log('рост: ok');
}

/* --- сжатие обратно в карточку --- */
{
  const el = makeEl([makeEl(), makeEl()]);   /* например, верхняя панель и тело */
  const card = makeEl();
  card.__radius = '20px';
  card.__rect = { left: 10, top: 50, right: 180, bottom: 250, width: 170, height: 200 };

  let ended = false;
  morphClose(el, card, () => { ended = true; });
  /* Главное регрессионное требование: содержимое не должно быть видно на
     уменьшающейся заметке, поэтому скрываем его сразу и без фейда. */
  assert.ok(el.classList.contains('morph-hide'), 'содержимое не скрыто при сжатии — будут видны иконки');
  assert.equal(el.classList.contains('morph-fade'), false, 'при сжатии фейд содержимого недопустим');
  assert.ok(el.classList.contains('morphing'), 'переход не включён');
  assert.equal(ended, false, 'экран убирается только по окончании');
  /* Старт сжатия — синхронно, в том же кадре, что и скрытие содержимого. */
  assert.match(el.style.transform, /^translate3d\(10px, 50px, 0\) scale\(/, 'не сжался к прямоугольнику карточки');
  const fsx = 170 / 390, fsy = 200 / 844;
  assert.equal(el.style.borderRadius, `${(20 / fsx).toFixed(1)}px / ${(20 / fsy).toFixed(1)}px`);

  await sleep(380);
  assert.equal(ended, true, 'колбэк окончания не вызван');
  assert.equal(el.classList.contains('morphing'), false);
  assert.equal(el.style.transform, '');
  assert.equal(el.style.willChange, '');
  console.log('сжатие: ok');
}

console.log('OK');
