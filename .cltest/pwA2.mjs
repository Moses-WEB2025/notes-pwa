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
await new Promise(r => server.listen(8124, r));

const browser = await chromium.launch();
const context = await browser.newContext({
  viewport: { width: 390, height: 844 }, deviceScaleFactor: 3, isMobile: true, hasTouch: true,
  userAgent: 'Mozilla/5.0 (Linux; Android 14; Pixel 8) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Mobile Safari/537.36',
});
const page = await context.newPage();
page.on('pageerror', e => console.log('  [pageerror]', e.message));
await page.route('**/sw.js', r => r.abort());
await page.addInitScript(() => { try { Object.defineProperty(window, 'PublicKeyCredential', { value: undefined, configurable: true }); } catch (e) {} });
await page.goto('http://127.0.0.1:8124/', { waitUntil: 'load' });
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
    Nav.pushEl(mainScreen({ mode: 'hidden' }).el);
  });
  await page.waitForTimeout(600);
}

const cdp = await context.newCDPSession(page);
async function edgeSwipe(fromX = 6, toX = 300) {
  const y = 500;
  await cdp.send('Input.dispatchTouchEvent', { type: 'touchStart', touchPoints: [{ x: fromX, y }] });
  for (let i = 1; i <= 8; i++) {
    await cdp.send('Input.dispatchTouchEvent', { type: 'touchMove', touchPoints: [{ x: fromX + (toX - fromX) * i / 8, y }] });
    await page.waitForTimeout(12);
  }
  await cdp.send('Input.dispatchTouchEvent', { type: 'touchEnd', touchPoints: [] });
}
const state = () => page.evaluate(() => ({
  screens: document.querySelectorAll('#screens-root > .screen').length,
  top: (document.querySelector('#screens-root > .screen:last-child')?.className || '').replace('screen ', ''),
  histLen: history.length,
}));

async function run(name, action) {
  await openHidden();
  const before = await state();
  await action();
  await page.waitForTimeout(500);
  const afterAction = await state();
  await edgeSwipe();
  await page.waitForTimeout(800);
  const after = await state();
  const ok = after.screens === before.screens - 1;
  console.log(`${ok ? 'OK ' : 'BAD'} | ${name}\n     до=${JSON.stringify(before)} после_действия=${JSON.stringify(afterAction)} после_жеста=${JSON.stringify(after)}`);
  // подчистить, если остались лишние экраны
  if (!ok) {
    await page.evaluate(() => {
      const s = document.querySelectorAll('#screens-root > .screen');
      for (let i = s.length - 1; i >= 1; i--) s[i].remove();
    });
  }
}

const last = '#screens-root > .screen:last-child';

console.log('=== Баг A: расширенный набор действий на «Скрытых» ===\n');

await run('E1 скролл списка', async () => {
  await page.evaluate(() => { document.querySelector('#screens-root > .screen:last-child .grid-holder').scrollTop = 500; });
});

await run('E2 тап по «папки»', async () => {
  await page.click(`${last} [data-a=folders]`);
});

await run('E3 тап по «корзина» + назад', async () => {
  await page.click(`${last} [data-a=trash]`);
  await page.waitForTimeout(600);
  await page.click('#screens-root > .screen:last-child [data-a=back]');
  await page.waitForTimeout(600);
});

await run('E4 фокус поиска + ввод', async () => {
  await page.click(`${last} .searchfield input`);
  await page.type(`${last} .searchfield input`, 'секрет');
});

await run('E5 FAB → редактор → назад', async () => {
  await page.click(`${last} .fab`);
  await page.waitForTimeout(700);
  await page.click('#screens-root > .screen:last-child [data-a=back]');
  await page.waitForTimeout(900);
});

await run('E6 тап по карточке → редактор → назад', async () => {
  await page.click(`${last} .note-card`);
  await page.waitForTimeout(700);
  await page.click('#screens-root > .screen:last-child [data-a=back]');
  await page.waitForTimeout(900);
});

await run('E7 долгий тап → меню → клик мимо', async () => {
  const card = await page.$(`${last} .note-card`);
  const box = await card.boundingBox();
  await cdp.send('Input.dispatchTouchEvent', { type: 'touchStart', touchPoints: [{ x: box.x + 30, y: box.y + 20 }] });
  await page.waitForTimeout(700);
  await cdp.send('Input.dispatchTouchEvent', { type: 'touchEnd', touchPoints: [] });
  await page.waitForTimeout(500);
  await page.keyboard.press('Escape').catch(() => {});
  await page.mouse.click(195, 700);
});

await run('E8 долгий тап → режим выбора (жест должен снять выбор, экран остаться)', async () => {
  const card = await page.$(`${last} .note-card`);
  const box = await card.boundingBox();
  await cdp.send('Input.dispatchTouchEvent', { type: 'touchStart', touchPoints: [{ x: box.x + 30, y: box.y + 20 }] });
  await page.waitForTimeout(700);
  await cdp.send('Input.dispatchTouchEvent', { type: 'touchEnd', touchPoints: [] });
  await page.waitForTimeout(500);
});

await browser.close();
server.close();
