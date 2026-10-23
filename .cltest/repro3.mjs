import http from 'node:http';
import fs from 'node:fs';
import path from 'node:path';
import { fileURLToPath } from 'node:url';
import { createRequire } from 'node:module';

const require = createRequire(import.meta.url);
const PW = 'C:\\Users\\HELIOS NEO\\AppData\\Roaming\\npm\\node_modules\\n8n\\node_modules\\playwright';
const { chromium } = require(PW);

const ROOT = path.resolve(path.dirname(fileURLToPath(import.meta.url)), '..');
const MIME = { '.html': 'text/html; charset=utf-8', '.js': 'text/javascript; charset=utf-8', '.css': 'text/css; charset=utf-8', '.json': 'application/json', '.png': 'image/png', '.woff2': 'font/woff2', '.svg': 'image/svg+xml' };

const server = http.createServer((req, res) => {
  let p = decodeURIComponent(req.url.split('?')[0]);
  if (p === '/') p = '/index.html';
  const f = path.join(ROOT, p);
  if (!fs.existsSync(f) || fs.statSync(f).isDirectory()) { res.writeHead(404); res.end('nf'); return; }
  res.writeHead(200, { 'Content-Type': MIME[path.extname(f)] || 'application/octet-stream' });
  fs.createReadStream(f).pipe(res);
});
await new Promise(r => server.listen(8131, r));

const browser = await chromium.launch({ channel: 'chrome' });
const context = await browser.newContext({
  viewport: { width: 390, height: 844 }, deviceScaleFactor: 3, isMobile: true, hasTouch: true,
  userAgent: 'Mozilla/5.0 (Linux; Android 14; Pixel 8) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Mobile Safari/537.36',
});
const page = await context.newPage();
page.on('pageerror', e => console.log('  [pageerror]', e.message));
await page.route('**/sw.js', r => r.abort());
await page.addInitScript(() => { try { Object.defineProperty(window, 'PublicKeyCredential', { value: undefined, configurable: true }); } catch (e) {} });
await page.goto('http://127.0.0.1:8131/', { waitUntil: 'load' });
await page.waitForSelector('.main-screen');

await page.evaluate(async () => {
  const { Store } = await import('/scripts/core.js');
  const now = Date.now();
  await Store.saveNote({ id: 'ck', title: 'Чек', content: '<div class="check-item"><span class="box" contenteditable="false" role="checkbox" aria-checked="false"></span><span class="lbl">пункт один</span></div><div>обычный абзац для вставки чек-листа</div><div><br></div>', color: null, createdAt: now, updatedAt: now, pinned: false, hidden: false });
  await Store.saveNote({ id: 'bq', title: 'Цитата', content: '<blockquote><div>первая строка цитаты</div><div>вторая строка цитаты</div></blockquote><div>обычный абзац вне цитаты</div>', color: null, createdAt: now, updatedAt: now, pinned: false, hidden: false });
});

async function openEditor(id) {
  await page.evaluate(async (id) => {
    const { Nav } = await import('/scripts/core.js');
    const { openEditor } = await import('/scripts/screens/editor.js');
    Nav.pushEl(openEditor(id));
  }, id);
  await page.waitForTimeout(400);
  await page.evaluate(() => {
    document.body.classList.add('kb-open');
    document.body.style.setProperty('--kb-h', '260px');
    document.dispatchEvent(new CustomEvent('kb-change', { detail: { open: true } }));
    const c = document.querySelector('.note-content-input');
    c.focus({ preventScroll: true });
  });
  await page.waitForTimeout(200);
}
async function closeTop() {
  await page.evaluate(() => {
    const s = document.querySelectorAll('#screens-root > .screen');
    for (let i = s.length - 1; i >= 1; i--) s[i].remove();
  });
  await page.waitForTimeout(150);
}
async function setCaret(js) {
  await page.evaluate(js);
  await page.waitForTimeout(80);
}
function pick(sel) {
  return page.evaluate((s) => {
    const el = document.querySelector(s);
    if (!el) return null;
    const b = el.getBoundingClientRect();
    return { x: b.x + b.width / 2, y: b.y + b.height / 2 };
  }, sel);
}
async function caretState() {
  return page.evaluate(() => {
    const sel = window.getSelection();
    const n = sel.anchorNode;
    const inLblElem = n.nodeType === 1 && n.classList?.contains('lbl');
    const inLblText = n.nodeType === 3 && n.parentElement?.classList.contains('lbl');
    const lbl = inLblElem ? n : (inLblText ? n.parentElement : null);
    let block = n.nodeType === 3 ? n.parentElement : n;
    while (block && block !== document.querySelector('.note-content-input')) {
      if (/^(DIV|P|H1|H2|H3|BLOCKQUOTE)$/.test(block.tagName)) break;
      block = block.parentElement;
    }
    const bq = (block ? block.closest('blockquote') : null);
    return {
      inLbl: !!lbl,
      labelClass: lbl ? lbl.className : null,
      off: sel.anchorOffset,
      inText: n.nodeType === 3 ? n.parentElement.tagName : null,
      blockTag: block ? block.tagName : null,
      inQuote: !!bq,
      html: document.querySelector('.note-content-input').innerHTML,
      quotes: document.querySelectorAll('.note-content-input blockquote').length,
    };
  });
}

let pass = 0, fail = 0;
function check(name, ok, extra = '') {
  console.log(`${ok ? 'OK  ' : 'BAD '} ${name}${extra ? '  | ' + extra : ''}`);
  ok ? pass++ : fail++;
}

// ===== T1: вставка чек-листа в середину абзаца → каретка в тексте, справа от бокса =====
await openEditor('ck');
await setCaret(() => {
  const c = document.querySelector('.note-content-input');
  const div = c.querySelectorAll('div')[1];
  const r = document.createRange();
  r.setStart(div.firstChild, 8); r.collapse(true);
  const sel = window.getSelection(); sel.removeAllRanges(); sel.addRange(r);
});
await page.click('[data-tool="check"]');
await page.waitForTimeout(300);
check('T1 вставка: каретка в .lbl того же символа (8)', (await caretState()).inLbl && (await caretState()).off === 8);

// ===== T2: тап по боксу → галочка вкл, каретка в начале .lbl =====
await setCaret(() => {
  const c = document.querySelector('.note-content-input');
  const lbl = c.querySelector('.check-item .lbl');
  const r = document.createRange();
  r.setStart(lbl.firstChild, 4); r.collapse(true);
  const sel = window.getSelection(); sel.removeAllRanges(); sel.addRange(r);
});
await page.evaluate(() => {
  const box = document.querySelector('.check-item .box');
  box.dispatchEvent(new PointerEvent('pointerdown', { bubbles: true, cancelable: true, composed: true, pointerId: 1, button: 0 }));
});
await page.waitForTimeout(150);
{
  const st = await caretState();
  const on = await page.evaluate(() => ({
    checked: document.querySelector('.check-item').classList.contains('checked'),
    aria: document.querySelector('.check-item .box').getAttribute('aria-checked'),
  }));
  check('T2 тап по боксу: галочка вкл (checked+aria)', on.checked && on.aria === 'true');
  check('T2 тап по боксу: каретка в начале .lbl (offset 0)', st.off === 0 && st.inLbl, `off=${st.off} inLbl=${st.inLbl} class=${st.labelClass} html=${st.html.slice(0, 140)}`);
}

// ===== T3: вставка чек-листа в пустой абзац → каретка сразу после бокса =====
await setCaret(() => {
  const c = document.querySelector('.note-content-input');
  const div = c.querySelectorAll('div')[2]; // пустой <div><br></div>
  const r = document.createRange();
  r.selectNodeContents(div); r.collapse(true);
  const sel = window.getSelection(); sel.removeAllRanges(); sel.addRange(r);
});
await page.click('[data-tool="check"]');
await page.waitForTimeout(300);
{
  const st = await caretState();
  check('T3 вставка в пустой абзац: каретка в начале .lbl (offset 0)', st.inLbl && st.off === 0, `off=${st.off} inLbl=${st.inLbl} class=${st.labelClass} html=${st.html.slice(0, 140)}`);
}

// ===== T4: H2 внутри цитаты → blockquote цел, тег внутри меняется =====
await closeTop();
await openEditor('bq');
await setCaret(() => {
  const c = document.querySelector('.note-content-input');
  const div = c.querySelector('blockquote div');
  const r = document.createRange();
  r.setStart(div.firstChild, 5); r.collapse(true);
  const sel = window.getSelection(); sel.removeAllRanges(); sel.addRange(r);
});
await page.click('[data-tool="format"]');
await page.waitForTimeout(200);
await page.click('[data-fmt="h2"]');
await page.waitForTimeout(300);
{
  const st = await caretState();
  const h2inside = /<blockquote><h2>/.test(st.html) && st.quotes === 1;
  check('T4 H2 внутри цитаты: блок h2 внутри ЕДИНОЙ цитаты', h2inside && st.blockTag === 'H2' && st.inQuote, `blockTag=${st.blockTag} inQuote=${st.inQuote} quotes=${st.quotes} html=${st.html.slice(0, 180)}`);
}

// ===== T5: повторный H2 → снятие к div, цитата цела =====
await page.click('[data-fmt="h2"]');
await page.waitForTimeout(300);
{
  const st = await caretState();
  const ok = /<blockquote><div>/.test(st.html) && st.quotes === 1 && st.blockTag === 'DIV' && st.inQuote;
  check('T5 повторное H2: возврат к div, цитата цела', ok, `blockTag=${st.blockTag} inQuote=${st.inQuote} quotes=${st.quotes} html=${st.html.slice(0, 180)}`);
}

// ===== T6: H2 вне цитаты (обычный абзац) — не сломан =====
await setCaret(() => {
  const c = document.querySelector('.note-content-input');
  const div = c.querySelectorAll('div')[c.querySelectorAll('div').length - 1];
  const r = document.createRange();
  r.setStart(div.firstChild, 2); r.collapse(true);
  const sel = window.getSelection(); sel.removeAllRanges(); sel.addRange(r);
});
await page.click('[data-fmt="h2"]');
await page.waitForTimeout(300);
{
  const st = await caretState();
  check('T6 H2 вне цитаты: <h2> на месте, без blockquote', /<h2>/.test(st.html) && st.quotes === 1 && st.blockTag === 'H2' && !st.inQuote, `blockTag=${st.blockTag} inQuote=${st.inQuote} quotes=${st.quotes} html=${st.html.slice(0, 180)}`);
}

// ===== T7: «Обычный текст» (p) внутри цитаты — цитата цела =====
await setCaret(() => {
  const c = document.querySelector('.note-content-input');
  const h = c.querySelector('blockquote h2') || c.querySelector('blockquote div');
  const r = document.createRange();
  r.selectNodeContents(h); r.collapse(true);
  const sel = window.getSelection(); sel.removeAllRanges(); sel.addRange(r);
});
await page.click('[data-fmt="p"]');
await page.waitForTimeout(300);
{
  const st = await caretState();
  check('T7 «Обычный текст» в цитате: div внутри ЕДИНОЙ цитаты', /<blockquote><div>/.test(st.html) && st.quotes === 1 && st.blockTag === 'DIV' && st.inQuote, `blockTag=${st.blockTag} inQuote=${st.inQuote} quotes=${st.quotes} html=${st.html.slice(0, 180)}`);
}

console.log(`\nИтог: OK=${pass} BAD=${fail}`);
await browser.close();
server.close();
process.exit(fail ? 1 : 0);