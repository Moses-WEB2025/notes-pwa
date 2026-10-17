/* Заметки — Service Worker: полный офлайн (прекэш всех ресурсов, fallback навигации) */
const VERSION = 'notes-v1.3.0';
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
  './scripts/app.js',
  './scripts/main-screen.js',
  './scripts/seed.js',
  './scripts/components/ui.js',
  './scripts/components/recorder.js',
  './scripts/components/drawing.js',
  './scripts/screens/editor.js',
  './scripts/screens/folders.js',
  './scripts/screens/grid.js',
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
      await Promise.all(keys.filter((k) => ![PRECACHE, RUNTIME].includes(k)).map((k) => caches.delete(k)));
      await self.clients.claim();
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

  // Статика: cache-first с догрузкой в кэш
  event.respondWith(
    (async () => {
      const cached = await caches.match(request);
      if (cached) return cached;
      try {
        const fresh = await fetch(request);
        if (fresh && fresh.ok) {
          const cache = await caches.open(RUNTIME);
          cache.put(request, fresh.clone());
        }
        return fresh;
      } catch (e) {
        return new Response('', { status: 504, statusText: 'Offline' });
      }
    })()
  );
});
