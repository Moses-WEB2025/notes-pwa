import http from 'node:http';
import fs from 'node:fs';
import path from 'node:path';
import { chromium } from 'playwright';

const ROOT = '/workspace';
const MIME = { '.html': 'text/html; charset=utf-8', '.js': 'text/javascript; charset=utf-8', '.css': 'text/css; charset=utf-8', '.json': 'application/json', '.png': 'image/png', '.woff2': 'font/woff2' };
const server = http.createServer((req, res) => {
  let p = decodeURIComponent(req.url.split('?')[0]); if (p === '/') p = '/index.html';
  const f = path.join(ROOT, p);
  if (!fs.existsSync(f) || fs.statSync(f).isDirectory()) { res.writeHead(404); res.end('nf'); return; }
  res.writeHead(200, { 'Content-Type': MIME[path.extname(f)] || 'application/octet-stream' });
  fs.createReadStream(f).pipe(res);
});
await new Promise(r => server.listen(8124, r));

const browser = await chromium.launch();
const context = await browser.newContext({ viewport: { width: 390, height: 844 }, deviceScaleFactor: 3, isMobile: true, hasTouch: true });
const page = await context.newPage();
page.on('pageerror', e => console.log('[pageerror]', e.message));
await page.route('**/sw.js', r => r.abort());
await page.addInitScript(() => { try { Object.defineProperty(window, 'PublicKeyCredential', { value: undefined, configurable: true }); } catch (e) {} });
await page.goto('http://127.0.0.1:8124/', { waitUntil: 'load' });
await page.waitForSelector('.main-screen');

await page.evaluate(async () => {
  const { Store } = await import('/scripts/core.js');
  const now = Date.now();
  for (let i = 0; i < 40; i++) await Store.saveNote({ id: 'h' + i, title: 'Секретная заметка ' + i, content: '<p>Скрытый ' + i + '</p>', hidden: true, createdAt: now, updatedAt: now - i * 1000 });
});

async function openHidden() {
  await page.evaluate(async () => {
    const { Nav } = await import('/scripts/core.js');
    const { mainScreen } = await import('/scripts/main-screen.js');
    Nav.pushEl(mainScreen({ mode: 'hidden' }).el);
  });
  await page.waitForTimeout(600);
}
await openHidden();

// Скроллим — включается hd-shrink
await page.evaluate(() => { document.querySelector('#screens-root > .screen:last-child .grid-holder').scrollTop = 400; });
await page.waitForTimeout(500);

const probe = () => page.evaluate(() => {
  const el = document.querySelector('#screens-root > .screen:last-child');
  const h1 = el.querySelector('.h1-row h1');
  const row = el.querySelector('.h1-row');
  const cs = getComputedStyle(h1);
  const r = h1.getBoundingClientRect();
  return {
    h1x: getComputedStyle(el).getPropertyValue('--h1-x').trim(),
    rowClientW: row.clientWidth,
    rowW: Math.round(row.getBoundingClientRect().width),
    padL: cs.paddingLeft,   // это h1, не row — оставим для справки
    rowPad: getComputedStyle(row).paddingLeft + '/' + getComputedStyle(row).paddingRight,
    textW: Math.round(r.width),
    center: Math.round(r.left + r.width / 2),
    viewCenter: window.innerWidth / 2,
  };
});

console.log('после скролла (как есть):', await probe());
await page.evaluate(() => window.dispatchEvent(new Event('resize')));
await page.waitForTimeout(400);
console.log('после resize (пересчёт):  ', await probe());

await browser.close();
server.close();
