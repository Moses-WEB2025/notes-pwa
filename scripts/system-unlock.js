/* ============ Системная разблокировка (WebAuthn) ============
   Показывает системный диалог подтверждения — ПИН, графический ключ, отпечаток
   или Face ID. Свой экран ввода мы НЕ рисуем: всё делает ОС.

   Первый вызов регистрирует platform-ключ (система тут же просит подтвердить
   личность), дальше этот же ключ просто проверяется. Это «мягкий» локальный
   замок: сервера нет, подпись не разбираем — важен сам факт, что пользователь
   прошёл системную проверку. Нужен secure context (HTTPS или localhost). */
import { Store } from './core.js';

const CRED_KEY = 'hiddenCredentialId';

const toB64 = buf => btoa(String.fromCharCode(...new Uint8Array(buf)));
const fromB64 = s => Uint8Array.from(atob(s), c => c.charCodeAt(0));
const rand = n => crypto.getRandomValues(new Uint8Array(n));

export function systemUnlockAvailable() {
  return !!(window.PublicKeyCredential && navigator.credentials &&
    navigator.credentials.create && navigator.credentials.get && window.isSecureContext);
}

/* id зарегистрированного ключа читаем заранее и держим в памяти. Диалогу
   WebAuthn нужно «короткое действие пользователя» — его даёт touchend, и лишний
   await (поход в IndexedDB) перед вызовом create/get мог это окно съесть.
   К моменту протяжки значение уже прочитано, и вызов идёт сразу за касанием. */
let cachedId = null, idLoaded = false;
const idReady = Store.getSet(CRED_KEY)
  .then(v => { cachedId = v ?? null; idLoaded = true; return cachedId; })
  .catch(() => { cachedId = null; idLoaded = true; return null; });

/* Причина последней неудачи — чтобы экран мог показать её пользователю:
   без этого системный диалог просто «не открывался», и понять почему было
   нельзя (нет блокировки экрана, небезопасный контекст, IP вместо домена). */
let lastError = null;
export function systemUnlockError() { return lastError; }

async function rememberId(id) {
  cachedId = id; idLoaded = true;
  try { await Store.setSet(CRED_KEY, id); } catch (_) {}
}

export async function systemUnlock() {
  lastError = null;
  if (!systemUnlockAvailable()) {
    lastError = window.isSecureContext ? 'нет поддержки' : 'нужен HTTPS';
    return false;
  }
  /* rp.id не может быть IP-адресом — WebAuthn в этом случае отказывает,
     поэтому для таких адресов поле просто не передаём. */
  const rpId = /^[\d.]+$|:/.test(location.hostname) ? null : location.hostname;
  const id = idLoaded ? cachedId : await idReady;
  try {
    if (!id) {
      /* Первый раз: регистрируем ключ — ОС сразу показывает свой диалог. */
      const cred = await navigator.credentials.create({
        publicKey: {
          rp: rpId ? { name: 'Заметки', id: rpId } : { name: 'Заметки' },
          user: { id: rand(16), name: 'notes-user', displayName: 'Заметки' },
          challenge: rand(32),
          pubKeyCredParams: [
            { type: 'public-key', alg: -7 },    /* ES256 */
            { type: 'public-key', alg: -257 },  /* RS256 */
          ],
          authenticatorSelection: {
            authenticatorAttachment: 'platform',
            userVerification: 'required',
            residentKey: 'preferred',
          },
          timeout: 60000,
          attestation: 'none',
        },
      });
      if (!cred) { lastError = 'нет ответа'; return false; }
      await rememberId(toB64(cred.rawId));
      return true;
    }
    /* Дальше: просто проверяем личность тем же ключом. */
    const assertion = await navigator.credentials.get({
      publicKey: {
        challenge: rand(32),
        ...(rpId ? { rpId } : {}),
        allowCredentials: [{ id: fromB64(id), type: 'public-key' }],
        userVerification: 'required',
        timeout: 60000,
      },
    });
    if (!assertion) lastError = 'нет ответа';
    return !!assertion;
  } catch (e) {
    /* Ключ зарегистрирован, но больше не подходит (например, сменился
       владелец устройства) — забываем его, чтобы зарегистрировать заново.
       Просто отменённый диалог (NotAllowedError) ключ не портит. */
    lastError = (e && e.name) || 'ошибка';
    if (id && e && (e.name === 'InvalidStateError' || e.name === 'SecurityError')) {
      await rememberId(null);
    }
    return false;
  }
}
