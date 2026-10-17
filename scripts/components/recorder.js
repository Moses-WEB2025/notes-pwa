/* ============ Запись аудио ============ */
import { Store, uid, vibrate } from '../core.js';

export function openRecorder(note, onSaved) {
  const ov = document.createElement('div');
  ov.className = 'rec-overlay';
  ov.innerHTML = `
    <div class="rec-timer">0:00</div>
    <div class="rec-wave">${'<i></i>'.repeat(48)}</div>
    <div class="rec-btns">
      <button class="rec-circle-btn" data-a="cancel"><span class="material-symbols-rounded">close</span></button>
      <button class="rec-circle-btn stop-rec" data-a="stop"><span class="material-symbols-rounded">stop</span></button>
    </div>`;
  document.body.appendChild(ov);
  requestAnimationFrame(()=>ov.classList.add('open'));

  let stream=null, rec=null, chunks=[], timer=null, secs=0, t0=0, audioCtx=null, analyser=null, rafId=null;

  async function start() {
    try {
      stream = await navigator.mediaDevices.getUserMedia({ audio: true });
    } catch(e) { ov.remove(); return; }
    const chunks2 = chunks;
    const mr = new MediaRecorder(stream);
    rec = mr;
    mr.ondataavailable = e => chunks2.push(e.data);
    mr.onstop = () => finish(chunks2);
    mr.start();
    t0 = Date.now();
    timer = setInterval(()=>{ secs = Math.floor((Date.now() - t0) / 1000); ov.querySelector('.rec-timer').textContent = fmt(secs); }, 200);
    /* визуализация волны */
    try {
      audioCtx = new AudioContext();
      const src = audioCtx.createMediaStreamSource(stream);
      analyser = audioCtx.createAnalyser(); analyser.fftSize = 128;
      src.connect(analyser);
      const bars = ov.querySelectorAll('.rec-wave i');
      const data = new Uint8Array(analyser.frequencyBinCount);
      const draw = () => {
        analyser.getByteFrequencyData(data);
        bars.forEach((b,i)=>{ const v=data[i%data.length]/255; b.style.height=(5+v*120)+'px'; });
        rafId = requestAnimationFrame(draw);
      };
      draw();
    } catch(e){}
  }
  function fmt(s){const m=Math.floor(s/60);return `${m}:${(s%60)<10?'0':''}${Math.floor(s%60)}`;}

  function finish(chs) {
    cancelAnimationFrame(rafId);
    clearInterval(timer);
    stream && stream.getTracks().forEach(t=>t.stop());
    audioCtx && audioCtx.close();
    const blob = new Blob(chs, { type: rec && rec.mimeType || 'audio/webm' });
    const dur = Math.round((Date.now() - t0) / 1000);
    ov.classList.remove('open');
    setTimeout(()=>ov.remove(), 200);
    (async () => {
      const id = 'aud_'+uid();
      await Store.putBlob(id, blob);
      onSaved && onSaved({ id, duration: dur, mime: blob.type });
    })();
  }

  const stop = () => { if (rec && rec.state !== 'inactive') rec.stop(); };
  ov.querySelector('[data-a=stop]').addEventListener('click', stop);
  ov.querySelector('[data-a=cancel]').addEventListener('click', () => {
    cancelAnimationFrame(rafId); clearInterval(timer);
    stream && stream.getTracks().forEach(t=>t.stop());
    audioCtx && audioCtx.close();
    rec && rec.state!=='inactive' && rec.stop();
    rec = null; chunks.length = 0;
    ov.classList.remove('open');
    setTimeout(()=>ov.remove(), 200);
  });

  vibrate(10);
  start();
}
