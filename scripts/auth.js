/* ============ Системная проверка личности (WebAuthn / passkey) ============
   Личная папка защищена СИСТЕМНЫМ подтверждением — Face ID / Touch ID /
   отпечатком / PIN. Своих окон мы не рисуем: браузер сам показывает системное
   окно через WebAuthn (navigator.credentials).

   Схема: при первом входе регистрируем платформенный ключ доступа (создание
   тоже подтверждается биометрией), его rawId кладём в настройки базы. При
   последующих входах просим подтвердить этот же ключ — система снова
   спрашивает биометрию/PIN.

   Если у устройства нет ни биометрии, ни ключа доступа (нет платформенного
   аутентификатора) — папку открываем без проверки, чтобы она не оказалась
   навсегда недоступной. Отмена/таймаут пользователем — это отказ, не пускаем. */

import { Store } from './core.js';

const CRED_KEY = 'private-cred-id';
const RP_NAME = 'Заметки';
const TIMEOUT = 60000;

function b64url(buf) {
  const bytes = new Uint8Array(buf);
  let bin = '';
  for (const b of bytes) bin += String.fromCharCode(b);
  return btoa(bin).replace(/\+/g, '-').replace(/\//g, '_').replace(/=+$/, '');
}

function fromB64url(str) {
  const s = String(str).replace(/-/g, '+').replace(/_/g, '/');
  const pad = s + '='.repeat((4 - (s.length % 4)) % 4);
  const bin = atob(pad);
  const arr = new Uint8Array(bin.length);
  for (let i = 0; i < bin.length; i++) arr[i] = bin.charCodeAt(i);
  return arr;
}

function randomChallenge() {
  const a = new Uint8Array(32);
  crypto.getRandomValues(a);
  return a;
}

/* Есть ли на устройстве платформенный аутентификатор (биометрия/PIN). */
async function hasPlatformAuthenticator() {
  try {
    return !!(window.PublicKeyCredential
      && await PublicKeyCredential.isUserVerifyingPlatformAuthenticatorAvailable());
  } catch (e) {
    return false;
  }
}

/* Подтвердить личность пользователя.
   true  — можно открывать личную папку;
   false — пользователь отказался/отменил проверку. */
export async function verifyIdentity() {
  if (!await hasPlatformAuthenticator()) return true;

  try {
    let credId = await Store.getSet(CRED_KEY);

    if (!credId) {
      /* Первый вход: регистрируем платформенный ключ (система попросит
         подтвердить личность при создании). */
      const cred = await navigator.credentials.create({
        publicKey: {
          challenge: randomChallenge(),
          rp: { name: RP_NAME },
          user: { id: randomChallenge(), name: 'private-folder', displayName: 'Личная папка' },
          pubKeyCredParams: [
            { type: 'public-key', alg: -7 },
            { type: 'public-key', alg: -257 },
          ],
          authenticatorSelection: {
            authenticatorAttachment: 'platform',
            userVerification: 'required',
            residentKey: 'preferred',
          },
          attestation: 'none',
          timeout: TIMEOUT,
        },
      });
      if (!cred) return true;
      credId = b64url(cred.rawId);
      await Store.setSet(CRED_KEY, credId);
      return true;
    }

    /* Повторный вход: просим подтвердить уже созданный ключ. */
    const res = await navigator.credentials.get({
      publicKey: {
        challenge: randomChallenge(),
        allowCredentials: [{ type: 'public-key', id: fromB64url(credId), transports: ['internal'] }],
        userVerification: 'required',
        timeout: TIMEOUT,
      },
    });
    return !!res;
  } catch (e) {
    /* Отмена/таймаут пользователем — считаем отказом. Любая другая ошибка
       (недоступный аутентификатор, небезопасный контекст и т.п.) — пропускаем,
       чтобы папка не осталась запертой из-за настроек окружения. */
    if (e && e.name === 'NotAllowedError') return false;
    return true;
  }
}
