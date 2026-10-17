/* ============ Ядро: утилиты + IndexedDB + навигация + пресс + клавиатура + жесты ============ */

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
    if (prev) prev.classList.add('anim', 'under');
    requestAnimationFrame(() => {
      el.classList.add('anim'); el.classList.remove('enter-right');
      setTimeout(() => { el.classList.remove('anim'); prev && prev.classList.remove('anim'); }, 320);
    });
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
    top.classList.add('anim');
    top.style.transform = 'translateX(100%)';
    prev && prev.classList.remove('under');
    const t = top;
    setTimeout(() => t.remove(), 320);
    vibrate(4);
    return true;
  },
  top(){ const s=this.screens; return s[s.length-1]; }
};

/* ---------- Свайп «назад» от левого края (нативный iOS-стиль) ---------- */
let gesture = null, gestureDone = false;
document.addEventListener('touchstart', e => {
  /* жест работает на каждом экране свыше первого, независимо от стека */
  const screens = document.querySelectorAll('#screens-root > .screen');
  gestureDone = false;
  if (screens.length <= 1) return;
  const t = e.touches[0];
  if (t.clientX < 28) {
    gesture = { sx: t.clientX, sy: t.clientY, top: screens[screens.length - 1], started: false, dx: 0 };
  }
}, { passive: true });
document.addEventListener('touchmove',e=>{
  if(!gesture)return;
  const t=e.touches[0];
  const dx=t.clientX-gesture.sx, dy=Math.abs(t.clientY-gesture.sy);
  if(!gesture.started&&dx>18&&dy<26){gesture.started=true;gesture.top.classList.add('swipe-back');}
  if(gesture.started){
    gesture.dx=dx;
    const w=window.innerWidth;
    gesture.top.style.transition='none';
    gesture.top.style.transform=`translateX(${Math.max(0,dx)}px)`;
  }
},{passive:true});
document.addEventListener('touchend', e => {
  if (!gesture) return;
  const g = gesture; gesture = null;
  if (!g.started) return;
  g.top.style.transition = ''; g.top.classList.remove('swipe-back');
  if (g.dx > 110) {
    /* завершаем через общий pop — так стек и Android back остаются синхронными */
    gestureDone = true;
    g.top.classList.add('anim');
    g.top.style.transform = 'translateX(100%)';
    setTimeout(() => g.top.remove(), 320);
    vibrate(4);
  } else {
    g.top.style.transform = '';
  }
}, { passive: true });
document.addEventListener('touchcancel',()=>{ if(gesture&&gesture.started){gesture.top.style.transform='';gesture=null;} },{passive:true});

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
  let lastOpen=-1;
  const upd=(ev)=>{
    let open=false,kbH=0;
    if(vv){
      kbH=Math.max(0,window.innerHeight-vv.height-vv.offsetTop);
      open=kbH>80;
    } else {
      /* фолбэк: высота клавиатуры = «сжатие» окна после фокуса на вводе */
      if(ev&&ev.type==='focusin') setTimeout(upd,350);
      kbH=Math.max(0,refH-window.innerHeight);
      open=kbH>80;
    }
    refH=Math.max(refH,window.innerHeight);
    if(open!==lastOpen){
      lastOpen=open;
      document.documentElement.style.setProperty('--kb-h',open?kbH+'px':'0px');
      document.body.classList.toggle('kb-open',open);
      /* дернем ещё раз через кадр — клавиатура доезжает не мгновенно */
      requestAnimationFrame(()=>{
        let h=0,o=false;
        if(vv){ h=Math.max(0,window.innerHeight-vv.height-vv.offsetTop); o=h>80; }
        else { h=Math.max(0,refH-window.innerHeight); o=h>80; }
        document.documentElement.style.setProperty('--kb-h',o?h+'px':'0px');
        document.body.classList.toggle('kb-open',o);
      });
    }
    document.dispatchEvent(new CustomEvent('kb-change',{detail:{open,height:kbH}}));
  };
  if(vv){ vv.addEventListener('resize',upd); vv.addEventListener('scroll',upd); }
  window.addEventListener('resize',upd);
  window.addEventListener('focusin',upd);
  window.addEventListener('focusout',()=>setTimeout(upd,120));
  upd();
}
