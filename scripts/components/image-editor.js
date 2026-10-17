/* ============ Редактор изображения: обрезка + поворот ============ */
export function openImageEditor(src, onDone) {
  const ov = document.createElement('div');
  ov.className = 'img-edit-overlay';
  ov.innerHTML = `
    <div class="appbar back-header">
      <button class="icon-btn" data-a="back"><span class="material-symbols-rounded">arrow_back</span></button>
      <div class="appbar-title">Редактировать</div>
      <button class="icon-btn" data-a="done"><span class="material-symbols-rounded">check</span></button>
    </div>
    <div class="ie-stage">
      <div class="ie-frame">
        <canvas class="ie-canvas"></canvas>
        <div class="ie-crop">
          <span class="ie-handle tl" data-h="tl"></span>
          <span class="ie-handle tr" data-h="tr"></span>
          <span class="ie-handle bl" data-h="bl"></span>
          <span class="ie-handle br" data-h="br"></span>
        </div>
      </div>
    </div>
    <div class="ie-tools">
      <button class="ie-tool" data-a="rotate"><span class="material-symbols-rounded">rotate_right</span>Повернуть</button>
      <button class="ie-tool" data-a="reset"><span class="material-symbols-rounded">restart_alt</span>Сбросить</button>
    </div>`;
  document.body.appendChild(ov);
  requestAnimationFrame(() => ov.classList.add('open'));

  const stage = ov.querySelector('.ie-stage');
  const frame = ov.querySelector('.ie-frame');
  const canvas = ov.querySelector('.ie-canvas');
  const cropEl = ov.querySelector('.ie-crop');

  const img = new Image();
  let rot = 0;            // 0 / 90 / 180 / 270
  let work = null;        // {canvas, w, h} — изображение с учётом поворота
  let dispW = 1, dispH = 1, scale = 1;
  let crop = { x: 0, y: 0, w: 1, h: 1 };
  let ready = false;

  img.onload = () => { fit(); };
  img.src = src;

  function buildWork() {
    const nw = img.naturalWidth, nh = img.naturalHeight;
    const swap = rot % 180 !== 0;
    const w = swap ? nh : nw, h = swap ? nw : nh;
    const c = document.createElement('canvas');
    c.width = w; c.height = h;
    const cx = c.getContext('2d');
    cx.translate(w / 2, h / 2);
    cx.rotate(rot * Math.PI / 180);
    cx.drawImage(img, -nw / 2, -nh / 2);
    work = { canvas: c, w, h };
  }

  function fit() {
    buildWork();
    const r = stage.getBoundingClientRect();
    const pad = 24;
    const availW = Math.max(40, r.width - pad * 2);
    const availH = Math.max(40, r.height - pad * 2);
    scale = Math.min(availW / work.w, availH / work.h);
    dispW = Math.round(work.w * scale);
    dispH = Math.round(work.h * scale);
    frame.style.width = dispW + 'px';
    frame.style.height = dispH + 'px';
    frame.style.left = Math.round((r.width - dispW) / 2) + 'px';
    frame.style.top = Math.round((r.height - dispH) / 2) + 'px';
    const dpr = Math.min(3, window.devicePixelRatio || 1);
    canvas.width = Math.round(dispW * dpr);
    canvas.height = Math.round(dispH * dpr);
    const cx = canvas.getContext('2d');
    cx.setTransform(dpr, 0, 0, dpr, 0, 0);
    cx.clearRect(0, 0, dispW, dispH);
    cx.drawImage(work.canvas, 0, 0, dispW, dispH);
    crop = { x: 0, y: 0, w: dispW, h: dispH };
    renderCrop();
    ready = true;
  }

  function renderCrop() {
    cropEl.style.left = crop.x + 'px';
    cropEl.style.top = crop.y + 'px';
    cropEl.style.width = crop.w + 'px';
    cropEl.style.height = crop.h + 'px';
  }

  /* ---- Перетаскивание и изменение рамки обрезки ---- */
  function startDrag(e, handle) {
    if (!ready) return;
    e.preventDefault(); e.stopPropagation();
    const sx = e.clientX, sy = e.clientY;
    const c0 = { ...crop };
    const rect = frame.getBoundingClientRect();
    const target = e.currentTarget;
    try { target.setPointerCapture(e.pointerId); } catch (err) {}

    const move = ev => {
      const dx = ev.clientX - sx, dy = ev.clientY - sy;
      if (!handle) {
        crop.x = Math.max(0, Math.min(dispW - c0.w, c0.x + dx));
        crop.y = Math.max(0, Math.min(dispH - c0.h, c0.y + dy));
      } else {
        const minS = 40;
        let left = c0.x, top = c0.y, right = c0.x + c0.w, bottom = c0.y + c0.h;
        if (handle.includes('l')) left = Math.max(0, Math.min(right - minS, c0.x + dx));
        if (handle.includes('r')) right = Math.min(dispW, Math.max(left + minS, c0.x + c0.w + dx));
        if (handle.includes('t')) top = Math.max(0, Math.min(bottom - minS, c0.y + dy));
        if (handle.includes('b')) bottom = Math.min(dispH, Math.max(top + minS, c0.y + c0.h + dy));
        crop = { x: left, y: top, w: right - left, h: bottom - top };
      }
      renderCrop();
    };
    const up = () => {
      target.removeEventListener('pointermove', move);
      target.removeEventListener('pointerup', up);
      target.removeEventListener('pointercancel', up);
    };
    target.addEventListener('pointermove', move);
    target.addEventListener('pointerup', up);
    target.addEventListener('pointercancel', up);
    void rect;
  }

  cropEl.addEventListener('pointerdown', e => {
    const h = e.target.closest('.ie-handle');
    startDrag(e, h ? h.dataset.h : null);
  });

  ov.querySelector('[data-a=rotate]').addEventListener('click', () => { rot = (rot + 90) % 360; fit(); });
  ov.querySelector('[data-a=reset]').addEventListener('click', () => { rot = 0; fit(); });

  function close(result) {
    ov.classList.remove('open');
    setTimeout(() => ov.remove(), 200);
    if (result && onDone) onDone(result);
  }

  ov.querySelector('[data-a=done]').addEventListener('click', () => {
    if (!ready) return close(null);
    const sx = crop.x / scale, sy = crop.y / scale, sw = crop.w / scale, sh = crop.h / scale;
    const out = document.createElement('canvas');
    out.width = Math.max(1, Math.round(sw));
    out.height = Math.max(1, Math.round(sh));
    const cx = out.getContext('2d');
    cx.drawImage(work.canvas, sx, sy, sw, sh, 0, 0, out.width, out.height);
    close(out.toDataURL('image/png'));
  });
  ov.querySelector('[data-a=back]').addEventListener('click', () => close(null));

  window.addEventListener('resize', () => { if (ready) fit(); });
}
