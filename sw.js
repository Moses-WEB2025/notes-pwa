/* Заметки — Service Worker: полный офлайн (прекэш всех ресурсов, fallback навигации) */
const VERSION = 'notes-v2.4.32';
const PRECACHE = `precache-${VERSION}`;
const RUNTIME = `runtime-${VERSION}`;

const PRECACHE_URLS = [
  './',
  './index.html',
  './offline.html',
  './manifest.json',
  './styles/theme.css',
  './styles/main.css',
  './styles/components.css',
  './styles/screens.css',
  './scripts/core.js',
  './scripts/icons.js',
  './scripts/edge.js',
  './scripts/morph.js',
  './scripts/app.js',
  './scripts/auth.js',
  './scripts/scroll-fx.js',
  './scripts/main-screen.js',
  './scripts/seed.js',
  './scripts/components/ui.js',
  './scripts/components/recorder.js',
  './scripts/components/drawing.js',
  './scripts/components/image-editor.js',
  './scripts/screens/editor.js',
  './scripts/screens/folders.js',
  './scripts/screens/grid.js',
  './scripts/screens/trash.js',
  './assets/fonts/material-symbols-rounded.woff2',
  './assets/icons/icon-192.png',
  './assets/icons/icon-512.png'
];

self.addEventListener('install', (event) => {
  event.waitUntil(
    (async () => {
      const cache = await caches.open(PRECACHE);
      // Порционно, чтобы один сбой не сорвал установку
      await Promise.allSettled(PRECACHE_URLS.map((u) => cache.add(new Request(u, { cache: 'reload' }))));
      await self.skipWaiting();
    })()
  );
});

self.addEventListener('activate', (event) => {
  event.waitUntil(
    (async () => {
      const keys = await caches.keys();
      // Обновление это или первая установка: были ли кэши прежних версий
      const hadOld = keys.some((k) => k !== PRECACHE && k !== RUNTIME);
      await Promise.all(keys.filter((k) => ![PRECACHE, RUNTIME].includes(k)).map((k) => caches.delete(k)));
      await self.clients.claim();
      // Если вышла новая версия — разово перезагружаем уже открытые окна,
      // иначе они остаются на старом прекэше до ручной очистки данных сайта.
      if (hadOld) {
        const clients = await self.clients.matchAll({ type: 'window' });
        clients.forEach((c) => c.navigate(c.url));
      }
    })()
  );
});

self.addEventListener('message', (event) => {
  if (event.data === 'SKIP_WAITING') self.skipWaiting();
});

function isNavigation(request) {
  return request.mode === 'navigate' || (request.method === 'GET' && request.destination === 'document');
}

self.addEventListener('fetch', (event) => {
  const { request } = event;
  if (request.method !== 'GET') return;
  const url = new URL(request.url);
  if (url.origin !== self.location.origin) return;

  // Навигация: сеть → кэш → offline.html
  if (isNavigation(request)) {
    event.respondWith(
      (async () => {
        try {
          const fresh = await fetch(request);
          const cache = await caches.open(RUNTIME);
          cache.put(request, fresh.clone());
          return fresh;
        } catch (e) {
          const cached =
            (await caches.match(request)) ||
            (await caches.match('./index.html')) ||
            (await caches.match('./offline.html'));
          return cached || new Response('<h1>Офлайн</h1>', { headers: { 'Content-Type': 'text/html; charset=utf-8' } });
        }
      })()
    );
    return;
  }

  // Статика: cache-first + фоновое обновление (stale-while-revalidate).
  // Мгновенный запуск из кэша, свежая версия подтянется для следующей загрузки.
  event.respondWith(
    (async () => {
      const cache = await caches.open(PRECACHE);
      const cached = await caches.match(request);
      const fetchAndUpdate = fetch(request).then(async (fresh) => {
        if (fresh && fresh.ok) {
          await cache.put(request, fresh.clone());
          const all = await self.clients.matchAll();
          all.forEach(c => c.postMessage({ type: 'UPDATED' }));
        }
        return fresh;
      }).catch(() => null);
      if (cached) { fetchAndUpdate.catch(() => {}); return cached; }
      const fresh = await fetchAndUpdate;
      if (fresh) return fresh;
      return new Response('', { status: 504, statusText: 'Offline' });
    })()
  );
});

/* Когда кэш обновился в фоне — мягко просим страницу перезагрузить один раз */
self.addEventListener('message', (event) => {
  if (event.data && event.data.type === 'APPLY_UPDATE') self.skipWaiting();
});
