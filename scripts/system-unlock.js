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

export async function systemUnlock() {
  if (!systemUnlockAvailable()) return false;
  const rpId = location.hostname;
  let id = await Store.getSet(CRED_KEY);
  try {
    if (!id) {
      /* Первый раз: регистрируем ключ — ОС сразу показывает свой диалог. */
      const cred = await navigator.credentials.create({
        publicKey: {
          rp: { name: 'Заметки', id: rpId },
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
      if (!cred) return false;
      await Store.setSet(CRED_KEY, toB64(cred.rawId));
      return true;
    }
    /* Дальше: просто проверяем личность тем же ключом. */
    const assertion = await navigator.credentials.get({
      publicKey: {
        challenge: rand(32),
        rpId,
        allowCredentials: [{ id: fromB64(id), type: 'public-key' }],
        userVerification: 'required',
        timeout: 60000,
      },
    });
    return !!assertion;
  } catch (e) {
    /* Пользователь отменил системный диалог или ключ больше не подходит —
       забываем его, чтобы в следующий раз зарегистрировать заново. */
    if (id && e && (e.name === 'NotAllowedError' || e.name === 'InvalidStateError')) {
      try { await Store.setSet(CRED_KEY, null); } catch (_) {}
    }
    return false;
  }
}
