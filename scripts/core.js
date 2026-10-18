/* ============ Ядро: утилиты + IndexedDB + навигация + пресс + клавиатура + жесты ============ */
import { refreshEdgeColor, refreshEdgeColorFor } from './edge.js';

/* ---------- Даты ---------- */
export function pad2(n){return n<10?'0'+n:''+n;}
const MONTHS_CAP=['Январь','Февраль','Март','Апрель','Май','Июнь','Июль','Август','Сентябрь','Октябрь','Ноябрь','Декабрь'];
const MONTHS_GEN=['января','февраля','марта','апреля','мая','июня','июля','августа','сентября','октября','ноября','декабря'];
const DOW=['Вс','Пн','Вт','Ср','Чт','Пт','Сб'];

export function fmtCardTime(ts){
  const d=new Date(ts), now=new Date();
  if(d.toDateString()===now.toDateString()) return `${DOW[d.getDay()]} ${pad2(d.getHours())}:${pad2(d.getMinutes())}`;
  const y=new Date(now); y.setDate(now.getDate()-1);
  if(d.toDateString()===y.toDateString()) return `Вчера ${pad2(d.getHours())}:${pad2(d.getMinutes())}`;
  if(d.getFullYear()!==now.getFullYear()) return `${d.getDate()} ${MONTHS_CAP[d.getMonth()]} ${d.getFullYear()}`;
  return `${d.getDate()} ${MONTHS_CAP[d.getMonth()]}`;
}
export function fmtEditorDate(ts){
  const d=new Date(ts);
  return `${d.getDate()} ${MONTHS_GEN[d.getMonth()]} ${pad2(d.getHours())}:${pad2(d.getMinutes())}`;
}
export function fmtDur(s){const m=Math.floor(s/60);return `${m}:${pad2(Math.floor(s%60))}`;}

export function escapeHtml(s){return (s||'').replace(/[&<>"']/g,c=>({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c]));}
export function uid(){return Date.now().toString(36)+Math.random().toString(36).slice(2,8);}
export function debounce(fn,ms){let t;return function(...a){clearTimeout(t);t=setTimeout(()=>fn.apply(this,a),ms);};}
export function vibrate(ms=10){try{navigator.vibrate&&navigator.vibrate(ms);}catch(e){}}

export function highlight(text,q){
  const t=escapeHtml(text||'');
  if(!q)return t;
  const re=new RegExp('('+q.replace(/[.*+?^${}()|[\]\\]/g,'\\$&')+')','gi');
  return t.replace(re,'<mark class="hm">$1</mark>');
}
export function htmlToPlain(html){
  const div=document.createElement('div');div.innerHTML=html||'';
  return (div.textContent||'').replace(/\s+/g,' ').trim();
}

/* ---------- IndexedDB ---------- */
const DB='notes-app';let dbp=null;
function open(){if(dbp)return dbp;dbp=new Promise((res,rej)=>{const r=indexedDB.open(DB,1);r.onupgradeneeded=()=>{const db=r.result;
  if(!db.objectStoreNames.contains('notes')){const s=db.createObjectStore('notes',{keyPath:'id'});s.createIndex('folderId','folderId');}
  if(!db.objectStoreNames.contains('folders'))db.createObjectStore('folders',{keyPath:'id'});
  if(!db.objectStoreNames.contains('blobs'))db.createObjectStore('blobs',{keyPath:'id'});
  if(!db.objectStoreNames.contains('settings'))db.createObjectStore('settings',{keyPath:'key'});
};r.onsuccess=()=>res(r.result);r.onerror=()=>rej(r.error);});return dbp;}
function req(r){return new Promise((res,rej)=>{r.onsuccess=()=>res(r.result);r.onerror=()=>rej(r.error);});}

export const Store={
  async notes(){const db=await open();return req(db.transaction('notes').objectStore('notes').getAll());},
  async note(id){const db=await open();return req(db.transaction('notes').objectStore('notes').get(id));},
  async saveNote(n){if(!n.createdAt)n.createdAt=Date.now();n.updatedAt=Date.now();const db=await open();return req(db.transaction('notes','readwrite').objectStore('notes').put(n));},
  async saveNoteRaw(n){const db=await open();return req(db.transaction('notes','readwrite').objectStore('notes').put(n));},
  async delNote(id){const db=await open();return req(db.transaction('notes','readwrite').objectStore('notes').delete(id));},
  async folders(){const db=await open();return req(db.transaction('folders').objectStore('folders').getAll());},
  async saveFolder(f){const db=await open();return req(db.transaction('folders','readwrite').objectStore('folders').put(f));},
  async delFolder(id){const db=await open();return req(db.transaction('folders','readwrite').objectStore('folders').delete(id));},
  async getSet(k){const db=await open();const r=await req(db.transaction('settings').objectStore('settings').get(k));return r?r.value:undefined;},
  async setSet(k,v){const db=await open();return req(db.transaction('settings','readwrite').objectStore('settings').put({key:k,value:v}));},
  async putBlob(id,blob){const db=await open();return req(db.transaction('blobs','readwrite').objectStore('blobs').put({id,blob}));},
  async blob(id){const db=await open();const r=await req(db.transaction('blobs').objectStore('blobs').get(id));return r?r.blob:null;},
  async delBlob(id){const db=await open();return req(db.transaction('blobs','readwrite').objectStore('blobs').delete(id));},
  async seed(notes,folders){const db=await open();return new Promise((res,rej)=>{const t=db.transaction(['notes','folders'],'readwrite');
    notes.forEach(n=>t.objectStore('notes').put(n));folders.forEach(f=>t.objectStore('folders').put(f));
    t.oncomplete=res;t.onerror=()=>rej(t.error);});}
};

/* ---------- Навигация (стек по DOM — никогда не рассинхронизируется) ---------- */
export const Nav={
  get screens(){ return document.querySelectorAll('#screens-root > .screen'); },
  pushEl(el) {
    const screens = this.screens;
    const prev = screens[screens.length - 1];
    el.classList.add('screen', 'enter-right');
    document.getElementById('screens-root').appendChild(el);
    /* Новый экран сразу становится текущим — перекрашиваем системные зоны
       под него (в т.ч. зазор, который виден во время слайда). */
    refreshEdgeColor();
    if (prev) prev.classList.add('anim', 'under');
    /* Двойной rAF: даём браузеру отрисовать стартовое положение (за экраном),
       иначе переход иногда «схлопывается» и экран появляется мгновенно. */
    requestAnimationFrame(() => requestAnimationFrame(() => {
      el.classList.add('anim');
      el.classList.remove('enter-right');
      setTimeout(() => { el.classList.remove('anim'); prev && prev.classList.remove('anim'); }, 340);
    }));
    vibrate(5);
  },
  /* фабрика optional для совместимости */
  push(factory) {
    const el = typeof factory === 'function' ? factory() : factory;
    this.pushEl(el);
  },
  /* pop по фактическому DOM: закрывает текущий верхний экран */
  pop() {
    const screens = this.screens;
    if (screens.length <= 1) return false;
    document.body.classList.remove('kb-open');
    const top = screens[screens.length - 1];
    const prev = screens[screens.length - 2];
    /* Верхним снова становится предыдущий экран — красим системные зоны
       СРАЗУ, ещё до старта слайда. Иначе цвет закрываемого экрана «залипает»
       на всё время анимации (и ещё на кадр после удаления). */
    refreshEdgeColorFor(prev);
    /* Снимаем inline-transition ('none' после свайпа) — дальше работает
       класс .anim, и анимация продолжится с текущей позиции пальца. */
    top.style.transition = '';
    top.classList.add('anim');
    top.style.transform = 'translateX(100%)';
    if (prev) {
      /* Предыдущий экран тоже плавно возвращается из-под сдвига,
         а не «прыгает» на место за один кадр */
      prev.style.transition = '';
      prev.classList.add('anim');
      prev.classList.remove('under');
      prev.style.transform = '';
      setTimeout(() => prev.classList.remove('anim'), 340);
    }
    setTimeout(() => { top.remove(); }, 340);
    vibrate(4);
    return true;
  },
  top(){ const s=this.screens; return s[s.length-1]; }
};

/* ---------- Свайп «назад» от края экрана (iOS / Android edge-back) ----------
   Работает с ОБОИХ краёв, как системный жест Android: и от левого края вправо,
   и от правого края влево. Направление сведено к одной величине dx — насколько
   жест продвинулся к закрытию экрана, — поэтому сдвиг, доводка и пружинный
   возврат общие для обоих краёв. */
const EDGE_BACK_PX = 28;
const BACK_COMMIT_PX = 110;
const BACK_COMMIT_RATIO = 0.33;

function backGestureBlocked() {
  return !!(
    document.querySelector('.sheet-backdrop.open') ||
    document.querySelector('.menu-surface') ||
    document.querySelector('.rec-overlay.open') ||
    document.querySelector('.draw-overlay.open') ||
    /* Выбранное фото: у его рамки есть угловые маркеры, в том числе у правого
       края экрана — свайп там тянет размер, а не закрывает экран. */
    document.querySelector('.img-frame')
  );
}

/* Плавный возврат экрана на место, если свайп «назад» не довели до конца.
   Раньше здесь позиция сбрасывалась мгновенно (без transition) — экран
   резко «прыгал» назад. Теперь возврат идёт той же кривой, что и слайд. */
function settleBackGesture(g) {
  if (!g) return;
  g.top.style.transition = '';
  g.top.classList.add('anim');
  g.top.style.transform = '';
  if (g.prev) {
    g.prev.style.transition = '';
    g.prev.classList.add('anim');
    g.prev.style.transform = '';
  }
  setTimeout(() => {
    g.top.classList.remove('anim');
    if (g.prev) g.prev.classList.remove('anim');
  }, 340);
}

function applyBackGestureDrag(g, dx) {
  const w = window.innerWidth;
  const x = Math.max(0, dx);
  const ratio = Math.min(1, x / w);
  g.top.style.transition = 'none';
  g.top.style.transform = `translateX(${x}px)`;
  if (g.prev) {
    g.prev.style.transition = 'none';
    g.prev.style.transform = `translateX(${-0.26 * w * (1 - ratio)}px)`;
  }
}

let gesture = null;

document.addEventListener('touchstart', e => {
  if (e.touches.length !== 1 || backGestureBlocked()) return;
  const screens = document.querySelectorAll('#screens-root > .screen');
  /* Тот же свайп «назад» закрывает и поиск на главном экране, когда других
     экранов нет: строка поиска уезжает вниз, возвращаясь на главную. */
  const searching = document.querySelector('.main-screen.searching');
  const searchMode = !!searching && screens.length <= 1;
  if (screens.length <= 1 && !searching) return;
  const t = e.touches[0];
  const w = window.innerWidth;
  /* Жест должен начаться у кромки: у левой ведём вправо (dir = +1), у правой —
     влево (dir = −1). Закрытие поиска — только от левого края: строка поиска
     уезжает вправо, у правого края такой жест смысла не имеет. */
  let dir = 0;
  if (t.clientX <= EDGE_BACK_PX) dir = 1;
  else if (t.clientX >= w - EDGE_BACK_PX) dir = -1;
  if (!dir) return;
  if (searchMode && dir !== 1) return;
  const top = screens[screens.length - 1];
  const prev = screens[screens.length - 2] || null;
  gesture = {
    sx: t.clientX, sy: t.clientY, dir,
    top, prev, search: searchMode,
    started: false, dx: 0,
    lastP: 0, lastT: performance.now(), vx: 0,
  };
}, { passive: true });

document.addEventListener('touchmove', e => {
  if (!gesture || e.touches.length !== 1) return;
  const t = e.touches[0];
  /* Прогресс жеста к закрытию — общий для обоих краёв: у левого это смещение
     вправо, у правого — влево. */
  const dx = (t.clientX - gesture.sx) * gesture.dir;
  const dy = Math.abs(t.clientY - gesture.sy);
  const now = performance.now();
  const dt = Math.max(1, now - gesture.lastT);
  gesture.vx = (dx - gesture.lastP) / dt;
  gesture.lastP = dx;
  gesture.lastT = now;

  if (!gesture.started && dx > 14 && dy < 32 && dx > dy * 0.6) {
    gesture.started = true;
    gesture.top.classList.add(gesture.search ? 'search-drag' : 'swipe-back');
  }
  if (!gesture.started) return;

  gesture.dx = dx;
  if (gesture.search) {
    /* Поиск не сдвигает экран — строка просто тянется за пальцем. */
    gesture.top.style.setProperty('--search-dx', Math.max(0, dx) + 'px');
  } else {
    applyBackGestureDrag(gesture, dx);
  }
  e.preventDefault();
}, { passive: false });

document.addEventListener('touchend', () => {
  if (!gesture) return;
  const g = gesture;
  gesture = null;
  if (!g.started) return;

  const w = window.innerWidth;
  const commit = g.dx > BACK_COMMIT_PX || g.dx > w * BACK_COMMIT_RATIO || (g.vx > 0.45 && g.dx > 48);
  g.top.classList.remove('swipe-back', 'search-drag');
  if (g.search) g.top.style.setProperty('--search-dx', '0px');

  if (commit) {
    /* Не сбрасываем позицию вручную: эстафету принимает app-back → Nav.pop()
       (или закрытие поиска), и всё доезжает до места с той точки, где отпустили. */
    document.dispatchEvent(new CustomEvent('app-back'));
    return;
  }
  /* Отмена жеста — мягкий возврат, а не мгновенный скачок */
  if (!g.search) settleBackGesture(g);
}, { passive: true });

document.addEventListener('touchcancel', () => {
  if (!gesture) return;
  const g = gesture;
  gesture = null;
  if (!g.started) return;
  g.top.classList.remove('swipe-back', 'search-drag');
  if (g.search) { g.top.style.setProperty('--search-dx', '0px'); return; }
  settleBackGesture(g);
});

/* ---------- Пресс-состояния ---------- */
export function bindPress(root=document){
  root.querySelectorAll('[data-press]').forEach(el=>{
    if(el.__press)return;el.__press=true;
    el.addEventListener('touchstart',()=>el.classList.add('pressed'),{passive:true});
    el.addEventListener('touchend',()=>setTimeout(()=>el.classList.remove('pressed'),90),{passive:true});
    el.addEventListener('touchcancel',()=>el.classList.remove('pressed'),{passive:true});
  });
}

/* ---------- Клавиатура (visualViewport + фолбэк) ---------- */
export function initKeyboard(){
  const vv=window.visualViewport;
  let refH=window.innerHeight;
  let lastOpen=false;

  const measure=()=>{
    const shrink=Math.max(0,refH-window.innerHeight);
    /* Сжатие визуального вьюпорта (iOS) — надёжный признак открытой клавиатуры:
       он не зависит от offsetTop, в отличие от «высоты клавиатуры». */
    const vvShrink=vv?Math.max(0,refH-vv.height):0;
    let vvTop=0,vvKb=0;
    if(vv){
      vvTop=vv.offsetTop;
      vvKb=Math.max(0,window.innerHeight-vv.height-vv.offsetTop);
    }
    /* Клавиатура открыта, если уменьшилось окно (Android) или визуальный
       вьюпорт (iOS). Раньше проверяли vvKb>80 — но при тапе по самым нижним
       строкам длинного текста iOS сдвигает visualViewport вверх (offsetTop
       растёт), vvKb «проседал» и kb-open снимался: тулбар терял позицию над
       клавиатурой. Теперь позиция считается, пока клавиатура реально открыта. */
    const open=shrink>80||vvShrink>80;
    /* Android уменьшает само окно — там клавиатуру компенсирует layout (kbH=0).
       iOS — берём отступ от низа visualViewport (учёт offsetTop сохраняем). */
    const kbH=!open?0:(shrink>80?0:vvKb);
    return { open, kbH, vvTop:open?vvTop:0 };
  };

  const apply=({open,kbH,vvTop})=>{
    document.documentElement.style.setProperty('--kb-h',open?kbH+'px':'0px');
    document.documentElement.style.setProperty('--vv-top',open?vvTop+'px':'0px');
    document.body.classList.toggle('kb-open',open);
    if(open) window.scrollTo(0,0);
  };

  const upd=(ev)=>{
    if(ev&&ev.type==='focusin'&&!vv) setTimeout(upd,350);
    const m=measure();
    const opening=m.open&&!lastOpen;
    lastOpen=m.open;
    apply(m);
    if(!m.open) refH=window.innerHeight;
    else refH=Math.max(refH,window.innerHeight);
    if(opening){
      requestAnimationFrame(()=>apply(measure()));
      setTimeout(()=>apply(measure()),150);
    }
    document.dispatchEvent(new CustomEvent('kb-change',{detail:{open:m.open,height:m.kbH,offsetTop:m.vvTop}}));
  };

  if(vv){ vv.addEventListener('resize',upd); vv.addEventListener('scroll',upd); }
  window.addEventListener('resize',upd);
  window.addEventListener('focusin',upd);
  window.addEventListener('focusout',()=>setTimeout(upd,120));
  upd();
}
