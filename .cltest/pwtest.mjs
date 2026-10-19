import http from 'node:http';
import fs from 'node:fs';
import path from 'node:path';
import { chromium } from 'playwright';

const ROOT = '/workspace';
const MIME = { '.html': 'text/html; charset=utf-8', '.js': 'text/javascript; charset=utf-8', '.css': 'text/css; charset=utf-8', '.json': 'application/json', '.png': 'image/png', '.woff2': 'font/woff2' };

const server = http.createServer((req, res) => {
  let p = decodeURIComponent(req.url.split('?')[0]);
  if (p === '/') p = '/index.html';
  const f = path.join(ROOT, p);
  if (!fs.existsSync(f) || fs.statSync(f).isDirectory()) { res.writeHead(404); res.end('nf'); return; }
  res.writeHead(200, { 'Content-Type': MIME[path.extname(f)] || 'application/octet-stream' });
  fs.createReadStream(f).pipe(res);
});
await new Promise(r => server.listen(8123, r));

const browser = await chromium.launch();
const context = await browser.newContext({
  viewport: { width: 390, height: 844 },
  deviceScaleFactor: 3,
  isMobile: true,
  hasTouch: true,
  userAgent: 'Mozilla/5.0 (Linux; Android 14; Pixel 8) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Mobile Safari/537.36',
});
const page = await context.newPage();
page.on('console', m => { const t = m.text(); if (/error|Error|warn/i.test(t)) console.log('  [console]', t); });
page.on('pageerror', e => console.log('  [pageerror]', e.message));
await page.route('**/sw.js', r => r.abort());

await page.addInitScript(() => {
  try { Object.defineProperty(window, 'PublicKeyCredential', { value: undefined, configurable: true }); } catch (e) {}
});

await page.goto('http://127.0.0.1:8123/', { waitUntil: 'load' });
await page.waitForSelector('.main-screen');

await page.evaluate(async () => {
  const { Store } = await import('/scripts/core.js');
  const now = Date.now();
  for (let i = 0; i < 40; i++) {
    await Store.saveNote({ id: 'h' + i, title: 'Секретная заметка ' + i, content: '<p>Скрытый текст ' + i + '</p>', hidden: true, createdAt: now, updatedAt: now - i * 1000, pinned: false });
  }
});

async function openHidden() {
  await page.evaluate(async () => {
    const { Nav } = await import('/scripts/core.js');
    const { mainScreen } = await import('/scripts/main-screen.js');
    const s = mainScreen({ mode: 'hidden' });
    Nav.pushEl(s.el);
  });
  await page.waitForTimeout(600);
}

const cdp = await context.newCDPSession(page);
async function edgeSwipe(fromX = 6, toX = 300) {
  const y = 500;
  await cdp.send('Input.dispatchTouchEvent', { type: 'touchStart', touchPoints: [{ x: fromX, y }] });
  const steps = 8;
  for (let i = 1; i <= steps; i++) {
    const x = fromX + (toX - fromX) * i / steps;
    await cdp.send('Input.dispatchTouchEvent', { type: 'touchMove', touchPoints: [{ x, y }] });
    await page.waitForTimeout(12);
  }
  await cdp.send('Input.dispatchTouchEvent', { type: 'touchEnd', touchPoints: [] });
}

const state = () => page.evaluate(() => ({
  screens: document.querySelectorAll('#screens-root > .screen').length,
  top: (document.querySelector('#screens-root > .screen:last-child')?.className || '').replace('screen ', ''),
  url: location.href,
  histLen: history.length,
}));

console.log('== старт ==', await state());

await openHidden();
console.log('== открыли Скрытые ==', await state());

console.log('\n--- A1: жест сразу после входа ---');
await edgeSwipe();
await page.waitForTimeout(700);
console.log('после жеста:', await state());

await openHidden();
console.log('\n--- A2: действие (+ → редактор → назад) и жест ---');
await page.click('#screens-root > .screen:last-child .fab');
await page.waitForTimeout(700);
console.log('после открытия редактора:', await state());
await page.click('#screens-root > .screen:last-child [data-a=back]');
await page.waitForTimeout(900);
console.log('после закрытия редактора:', await state());
await edgeSwipe();
await page.waitForTimeout(800);
console.log('после жеста:', await state());

await openHidden();
console.log('\n--- A3: системный back (goBack) сразу ---');
let navigated;
try { navigated = await Promise.race([page.goBack({ timeout: 2500 }), page.waitForTimeout(2500).then(() => 'timeout')]); } catch (e) { navigated = 'err:' + e.message; }
await page.waitForTimeout(400);
console.log('goBack ->', navigated === 'timeout' ? 'timeout' : navigated, await state());

await openHidden();
await page.click('#screens-root > .screen:last-child .fab');
await page.waitForTimeout(700);
await page.click('#screens-root > .screen:last-child [data-a=back]');
await page.waitForTimeout(900);
console.log('\n--- A4: действие, затем системный back ---');
console.log('после действия:', await state());
try { navigated = await Promise.race([page.goBack({ timeout: 2500 }), page.waitForTimeout(2500).then(() => 'timeout')]); } catch (e) { navigated = 'err:' + e.message; }
await page.waitForTimeout(400);
console.log('goBack ->', navigated === 'timeout' ? 'timeout' : navigated, await state());

console.log('\n--- B: центровка заголовка при сжатии ---');
await openHidden();
await page.evaluate(() => { document.querySelector('#screens-root > .screen:last-child .grid-holder').scrollTop = 400; });
await page.waitForTimeout(500);
const center = await page.evaluate(() => {
  const el = document.querySelector('#screens-root > .screen:last-child');
  const h1 = el.querySelector('.h1-row h1');
  const r = h1.getBoundingClientRect();
  const row = el.querySelector('.h1-row').getBoundingClientRect();
  const cs = getComputedStyle(el);
  return {
    shrink: el.classList.contains('hd-shrink'),
    h1x: cs.getPropertyValue('--h1-x'), h1y: cs.getPropertyValue('--h1-y'),
    text: h1.textContent, textW: Math.round(r.width),
    textCenter: Math.round(r.left + r.width / 2), viewCenter: window.innerWidth / 2,
    rowLeft: row.left, rowW: row.width,
    h1Top: Math.round(r.top), h1H: Math.round(r.height), rowTop: Math.round(row.top),
    appbarBottom: Math.round(el.querySelector('.appbar').getBoundingClientRect().bottom),
  };
});
console.log('Скрытые:', center);

await page.evaluate(() => { document.querySelector('.main-screen .grid-holder').scrollTop = 400; });
await page.waitForTimeout(500);
const center2 = await page.evaluate(() => {
  const el = document.querySelector('#screens-root > .screen:first-child');
  const h1 = el.querySelector('.h1-row h1');
  const r = h1.getBoundingClientRect();
  return { shrink: el.classList.contains('hd-shrink'), h1x: getComputedStyle(el).getPropertyValue('--h1-x'), textW: Math.round(r.width), textCenter: Math.round(r.left + r.width / 2), viewCenter: window.innerWidth / 2 };
});
console.log('Заметки:', center2);

await browser.close();
server.close();
