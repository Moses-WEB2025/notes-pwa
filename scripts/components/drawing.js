/* ============ Рисование ============ */
import { toast } from './ui.js';

export function openDrawing(onDone) {
  const ov = document.createElement('div');
  ov.className = 'draw-overlay';
  const PENS = ['#1c1d21', '#e05555', '#f2a824', '#1a4a76', '#1d9152'];
  ov.innerHTML = `
    <div class="appbar back-header">
      <button class="icon-btn" data-a="back"><span class="material-symbols-rounded">arrow_back</span></button>
      <div class="appbar-title">Рисунок</div>
      <button class="icon-btn" data-a="done"><span class="material-symbols-rounded">check</span></button>
    </div>
    <div class="draw-canvas-holder"><canvas></canvas></div>
    <div class="draw-tools">
      ${PENS.map((c,i)=>`<button class="swatch ${i===0?'selected':''}" data-c="${c}" style="background:${c}"></button>`).join('')}
      <button class="size-btn" data-s="3" style="font-size:11px"><span class="material-symbols-rounded" style="font-size:16px">brush</span>S</button>
      <button class="size-btn active" data-s="5"><span class="material-symbols-rounded" style="font-size:18px">brush</span>M</button>
      <button class="size-btn" data-s="9"><span class="material-symbols-rounded" style="font-size:20px">brush</span>L</button>
      <span class="spacer"></span>
      <button class="icon-btn" data-a="erase"><span class="material-symbols-rounded">ink_eraser</span></button>
    </div>`;
  document.body.appendChild(ov);
  requestAnimationFrame(()=>ov.classList.add('open'));

  const canvas = ov.querySelector('canvas');
  const holder = ov.querySelector('.draw-canvas-holder');
  const ctx = canvas.getContext('2d');
  let color = PENS[0], size = 5, drawing = false, last = null;

  function resize() {
    const r = holder.getBoundingClientRect();
    const snap = canvas.toDataURL();
    canvas.width = r.width * devicePixelRatio;
    canvas.height = r.height * devicePixelRatio;
    ctx.scale(devicePixelRatio, devicePixelRatio);
    const img = new Image();
    img.onload = () => ctx.drawImage(img, 0, 0, r.width, r.height);
    img.src = snap;
    ctx.lineCap = ctx.lineJoin = 'round';
  }
  setTimeout(resize, 220);
  window.addEventListener('resize', resize);

  const pos = e => {
    const r = canvas.getBoundingClientRect();
    return { x: e.clientX - r.left, y: e.clientY - r.top };
  };
  canvas.addEventListener('pointerdown', e => { drawing = true; last = pos(e); canvas.setPointerCapture(e.pointerId); });
  canvas.addEventListener('pointermove', e => {
    if (!drawing) return;
    const p = pos(e);
    ctx.strokeStyle = color; ctx.lineWidth = size;
    ctx.beginPath(); ctx.moveTo(last.x, last.y); ctx.lineTo(p.x, p.y); ctx.stroke();
    last = p;
  });
  canvas.addEventListener('pointerup', () => { drawing = false; });

  ov.querySelectorAll('[data-c]').forEach(b=>b.addEventListener('click',()=>{
    color = b.dataset.c;
    ov.querySelectorAll('[data-c]').forEach(x=>x.classList.remove('selected'));
    b.classList.add('selected');
  }));
  ov.querySelectorAll('[data-s]').forEach(b=>b.addEventListener('click',()=>{
    size = +b.dataset.s;
    ov.querySelectorAll('[data-s]').forEach(x=>x.classList.remove('active'));
    b.classList.add('active');
  }));
  ov.querySelector('[data-a=erase]').addEventListener('click', ()=>{ ctx.clearRect(0,0,canvas.width,canvas.height); });

  ov.querySelector('[data-a=done]').addEventListener('click', ()=>{
    const url = canvas.toDataURL('image/png');
    ov.classList.remove('open');
    setTimeout(()=>ov.remove(),300);
    onDone && onDone(url);
  });
  ov.querySelector('[data-a=back]').addEventListener('click', ()=>{
    ov.classList.add('closing');
    ov.classList.remove('open');
    setTimeout(()=>ov.remove(),300);
  });
}
