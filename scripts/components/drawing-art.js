/* ============ Художественные иконки инструментов рисования ============
   Скевоморфные SVG-иллюстрации в стиле системного приложения «Заметки» Xiaomi.
   Все элементы стоят на нижней границе и «уходят» за край экрана, как в оригинале. */

const DEFS = `
  <defs>
    <linearGradient id="dwBody" x1="0" y1="0" x2="1" y2="0">
      <stop offset="0" stop-color="#ffffff"/>
      <stop offset=".32" stop-color="#fefefe"/>
      <stop offset=".58" stop-color="#f7f7f7"/>
      <stop offset=".82" stop-color="#e2e2e2"/>
      <stop offset="1" stop-color="#c9c9c9"/>
    </linearGradient>
    <linearGradient id="dwBodyWide" x1="0" y1="0" x2="1" y2="0">
      <stop offset="0" stop-color="#fdfdfd"/>
      <stop offset=".26" stop-color="#ffffff"/>
      <stop offset=".6" stop-color="#f8f8f8"/>
      <stop offset=".86" stop-color="#eaeaea"/>
      <stop offset="1" stop-color="#d9d9d9"/>
    </linearGradient>
    <linearGradient id="dwWood" x1="0" y1="0" x2="1" y2="0">
      <stop offset="0" stop-color="#fdfaf4"/>
      <stop offset=".5" stop-color="#f5eee2"/>
      <stop offset="1" stop-color="#ded3c1"/>
    </linearGradient>
    <linearGradient id="dwLead" x1="0" y1="0" x2="1" y2="0">
      <stop offset="0" stop-color="#4a4a4a"/>
      <stop offset=".42" stop-color="#1c1c1c"/>
      <stop offset="1" stop-color="#030303"/>
    </linearGradient>
    <linearGradient id="dwBlue" x1="0" y1="0" x2="1" y2="0">
      <stop offset="0" stop-color="#dff3ff"/>
      <stop offset=".38" stop-color="#a9dcff"/>
      <stop offset=".72" stop-color="#6dbcf7"/>
      <stop offset="1" stop-color="#4a9ee0"/>
    </linearGradient>
    <linearGradient id="dwOrange" x1="0" y1="0" x2="1" y2="0">
      <stop offset="0" stop-color="#ffdfae"/>
      <stop offset=".48" stop-color="#fcc066"/>
      <stop offset="1" stop-color="#eb9a2f"/>
    </linearGradient>
    <linearGradient id="dwGreen" x1="0" y1="0" x2="1" y2="0">
      <stop offset="0" stop-color="#d3f5a5"/>
      <stop offset=".55" stop-color="#8fdd49"/>
      <stop offset="1" stop-color="#59bc1c"/>
    </linearGradient>
    <linearGradient id="dwSilver" x1="0" y1="0" x2="1" y2="0">
      <stop offset="0" stop-color="#f7f8f9"/>
      <stop offset=".26" stop-color="#c9ced3"/>
      <stop offset=".52" stop-color="#fcfcfd"/>
      <stop offset=".76" stop-color="#b3b8be"/>
      <stop offset="1" stop-color="#e8eaec"/>
    </linearGradient>
    <filter id="dwShadow" x="-40%" y="-20%" width="180%" height="150%">
      <feDropShadow dx="0" dy="2" stdDeviation="1.8" flood-color="#000" flood-opacity=".10"/>
    </filter>
    <filter id="dwCardShadow" x="-30%" y="-15%" width="160%" height="140%">
      <feDropShadow dx="0" dy="3" stdDeviation="3" flood-color="#000" flood-opacity=".06"/>
    </filter>
  </defs>`;

/* ---------- Карандаш ---------- */
const PEN = `
<svg class="dw-art" viewBox="0 0 60 140" preserveAspectRatio="none" aria-hidden="true">
  ${DEFS}
  <g filter="url(#dwShadow)">
    <path d="M12 44h36v96H12z" fill="url(#dwBody)"/>
    <path d="M12 44c0-1.4 8-4 18-4s18 2.6 18 4z" fill="#efe9dd"/>
    <path d="M12 44l18-26 18 26z" fill="url(#dwWood)"/>
    <path d="M30 18v26" stroke="#d8cdbb" stroke-width=".9"/>
    <path d="M18 34L12 44" stroke="#e8dfce" stroke-width=".9"/>
    <path d="M30 5l4.6 17h-9.2z" fill="url(#dwLead)"/>
    <path d="M13.6 47v91" stroke="#fff" stroke-width="1.5" opacity=".95"/>
    <path d="M45.8 48v90" stroke="#bfbfbf" stroke-width="1" opacity=".5"/>
    <path d="M12.3 44h35.4" stroke="#dcdcdc" stroke-width=".7" opacity=".65"/>
    <path d="M29.9 6.4l1.1 2.2" stroke="#6b6b6b" stroke-width=".7" opacity=".7"/>
  </g>
</svg>`;

/* ---------- Маркер (голубой) ---------- */
const MARKER = `
<svg class="dw-art" viewBox="0 0 60 140" preserveAspectRatio="none" aria-hidden="true">
  ${DEFS}
  <g filter="url(#dwShadow)">
    <rect x="12" y="52" width="36" height="90" rx="9" fill="url(#dwBody)"/>
    <rect x="12.6" y="52.6" width="34.8" height="7" rx="3.5" fill="#fcfcfc"/>
    <path d="M21.5 52c1.7-13 4.7-24 7-36h3c2.3 12 5.3 23 7 36z" fill="url(#dwBlue)"/>
    <path d="M28.2 17h3.6l-.7 4h-2.2z" fill="#dff3ff" opacity=".95"/>
    <path d="M26.6 36c.9-6.2 2.1-11.6 3-16" stroke="#f6fdff" stroke-width="1.9" stroke-linecap="round" opacity=".9"/>
    <path d="M21 52h18" stroke="#c9ced4" stroke-width="2.4" stroke-linecap="round"/>
    <path d="M15 56v80" stroke="#fff" stroke-width="1.4" opacity=".95"/>
    <path d="M44.6 58v78" stroke="#bdbdbd" stroke-width="1" opacity=".5"/>
    <circle cx="30" cy="88" r="2.3" fill="#63ade7"/>
    <circle cx="29.2" cy="87.2" r=".8" fill="#e2f4ff"/>
  </g>
</svg>`;

/* ---------- Ластик (оранжевый) ---------- */
const ERASER = `
<svg class="dw-art" viewBox="0 0 60 140" preserveAspectRatio="none" aria-hidden="true">
  ${DEFS}
  <g filter="url(#dwShadow)">
    <rect x="7" y="40" width="46" height="102" rx="10" fill="url(#dwBodyWide)"/>
    <rect x="7.7" y="40.6" width="44.6" height="8" rx="4" fill="#fcfcfc"/>
    <path d="M18 40V29l15-16h11v27z" fill="url(#dwOrange)"/>
    <path d="M18 29l15-16 4 1.4L22 30.6z" fill="#ffe8c4"/>
    <path d="M33 13h11" stroke="#efaf52" stroke-width=".8" opacity=".7"/>
    <path d="M16 40h29" stroke="#b9b9b9" stroke-width="3.2" stroke-linecap="round"/>
    <path d="M10.2 44v94" stroke="#fff" stroke-width="1.6" opacity=".9"/>
    <path d="M49.8 46v92" stroke="#c4c4c4" stroke-width="1" opacity=".5"/>
    <circle cx="30" cy="80" r="2.3" fill="#eea23a"/>
  </g>
</svg>`;

/* ---------- Перо (стальное, зелёные чернила) ---------- */
const FOUNTAIN = `
<svg class="dw-art" viewBox="0 0 60 140" preserveAspectRatio="none" aria-hidden="true">
  ${DEFS}
  <g filter="url(#dwShadow)">
    <rect x="20" y="62" width="20" height="80" rx="8" fill="url(#dwBody)"/>
    <rect x="26.8" y="70" width="6.4" height="70" rx="3.2" fill="url(#dwGreen)"/>
    <path d="M30 14C28.8 30 24.4 45 20 58L30 68l10-10C35.6 45 31.2 30 30 14z" fill="url(#dwSilver)"/>
    <path d="M30 9c1.6 19 6.6 38 11.5 54L30 74z" fill="#fff" opacity=".16"/>
    <path d="M30 16v48" stroke="#767c82" stroke-width=".8"/>
    <path d="M30 34v30" stroke="#7cc93a" stroke-width="1.2"/>
    <path d="M25 26 21.5 52" stroke="#fff" stroke-width="1.2" opacity=".5"/>
    <circle cx="30" cy="55" r="1.8" fill="#6a6f74"/>
    <rect x="20.5" y="58" width="19" height="8" rx="3.5" fill="#dcdfe2"/>
    <path d="M23 64h14" stroke="#babfc4" stroke-width="1"/>
    <path d="M22.4 68v70" stroke="#fff" stroke-width="1.3" opacity=".8"/>
  </g>
</svg>`;

/* ---------- Карточка «выделение» ---------- */
const SELECT = `
<svg class="dw-art dw-card" viewBox="0 0 60 140" preserveAspectRatio="none" aria-hidden="true">
  ${DEFS}
  <g filter="url(#dwCardShadow)">
    <rect x="1" y="12" width="58" height="132" rx="12" fill="#fff" stroke="#f3f3f3"/>
  </g>
  <g fill="#c6c6c6">
    <circle cx="21" cy="35" r="1.15"/><circle cx="30" cy="35" r="1.15"/><circle cx="39" cy="35" r="1.15"/>
    <circle cx="21" cy="45" r="1.15"/><circle cx="39" cy="45" r="1.15"/>
    <circle cx="21" cy="55" r="1.15"/><circle cx="30" cy="55" r="1.15"/><circle cx="39" cy="55" r="1.15"/>
  </g>
  <path d="M24 65h12" stroke="#dedede" stroke-width="1.4" stroke-linecap="round"/>
  <path d="M26 78c2.6-7 6 5.4 8.6-2.2" fill="none" stroke="#bdbdbd" stroke-width="2" stroke-linecap="round"/>
</svg>`;

/* ---------- Карточка «изображение» ---------- */
const IMAGE = `
<svg class="dw-art dw-card" viewBox="0 0 60 140" preserveAspectRatio="none" aria-hidden="true">
  ${DEFS}
  <g filter="url(#dwCardShadow)">
    <rect x="1" y="12" width="58" height="132" rx="12" fill="#fff" stroke="#f3f3f3"/>
  </g>
  <g>
    <rect x="9" y="20" width="42" height="50" rx="2" fill="#fefefe" stroke="#f0f0f0"/>
    <path d="M11 68V46l13-13 12 15 7-7 6 6v21z" fill="#d5d5d5"/>
    <circle cx="43" cy="31" r="2.9" fill="#ffc59a"/>
    <circle cx="42.2" cy="30.2" r="1" fill="#ffe9d8"/>
    <rect x="9" y="76" width="42" height="50" rx="2" fill="#fefefe" stroke="#f0f0f0"/>
    <path d="M11 124V102l13-13 12 15 7-7 6 6v21z" fill="#d5d5d5"/>
    <circle cx="43" cy="87" r="2.9" fill="#ffc59a"/>
    <circle cx="42.2" cy="86.2" r="1" fill="#ffe9d8"/>
  </g>
</svg>`;

/* ---------- Карточка «текст» ---------- */
const TEXT = `
<svg class="dw-art dw-card" viewBox="0 0 60 140" preserveAspectRatio="none" aria-hidden="true">
  ${DEFS}
  <g filter="url(#dwCardShadow)">
    <rect x="1" y="12" width="58" height="132" rx="12" fill="#fff" stroke="#f3f3f3"/>
  </g>
  <rect x="9" y="28" width="42" height="48" rx="3" fill="#fff" stroke="#dcdcdc" stroke-dasharray="3.2 3.2"/>
  <text x="30" y="64" text-anchor="middle" font-family="system-ui, -apple-system, 'Segoe UI', sans-serif" font-size="32" font-weight="700" fill="#5b5b5b">T</text>
  <g stroke="#e7e7e7" stroke-width="2" stroke-linecap="round">
    <path d="M11 88h38"/><path d="M11 99h38"/><path d="M11 110h26"/>
  </g>
</svg>`;

/* ---------- Карточка-заметка ---------- */
const NOTE = `
<svg class="dw-art dw-card" viewBox="0 0 76 140" preserveAspectRatio="none" aria-hidden="true">
  ${DEFS}
  <defs>
    <pattern id="dwDots" width="4.4" height="4.4" patternUnits="userSpaceOnUse">
      <circle cx="1" cy="1" r=".65" fill="#e6e6e6"/>
    </pattern>
  </defs>
  <g filter="url(#dwCardShadow)" transform="rotate(-1.2 38 76)">
    <path d="M5 18h43l20 13v109H5z" fill="#fff" stroke="#f0f0f0"/>
    <path d="M48 18v13h20z" fill="#f2f2f2" stroke="#e9e9e9"/>
    <path d="M48 18l20 13" stroke="#fafafa" stroke-width="1"/>
    <rect x="10" y="24" width="48" height="112" fill="url(#dwDots)"/>
    <path d="M6 18.5v122" stroke="#fbfbfb" stroke-width="1.4"/>
  </g>
</svg>`;

const ART = {
  pen: PEN,
  marker: MARKER,
  eraser: ERASER,
  fountain: FOUNTAIN,
  select: SELECT,
  image: IMAGE,
  text: TEXT,
  note: NOTE,
};

export function toolArt(id) {
  return ART[id] || '';
}
