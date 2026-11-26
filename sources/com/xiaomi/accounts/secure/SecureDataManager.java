package com.xiaomi.accounts.secure;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.SystemClock;
import android.security.keystore.KeyGenParameterSpec;
import android.text.TextUtils;
import android.util.Base64;
import com.xiaomi.accountsdk.utils.AccountLogger;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Locale;
import java.util.WeakHashMap;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/* JADX INFO: loaded from: classes4.dex */
public class SecureDataManager {
    private static final int AES_CBC_BLOCK_SIZE = 16;
    private static final int AES_KEY_SIZE = 128;
    private static final String AES_TRANSFORMATION = "AES/CBC/NoPadding";
    private static final String ALIAS_XIAOMI_PASSPORT = "XiaomiPassport";
    private static final String ANDROID_KEY_STORE = "AndroidKeyStore";
    private static final String ENCRYPTED_STRING_PREFIX = "ENCRYPTED@";
    private static final String KEY_BOOL_GENERATE_SECRET_KEY_SUCCESS = "generate_secret_key_success";
    private static final String SP_NAME_GENERATE_SECRET_KEY_RET = "generate_secret_key_ret";
    private static final String TAG = "SecureDataManager";
    private static volatile KeyStore sKeyStore;
    private static volatile Key sSecureKey;
    private static final byte[] AES_IV_BYTES = "0102030405060708".getBytes();
    private static final WeakHashMap<String, String> sEncryptedDada = new WeakHashMap<>();
    private static final WeakHashMap<String, String> sDecryptedDada = new WeakHashMap<>();

    private static void generateKeyAboveM(Context context) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES", ANDROID_KEY_STORE);
            Locale locale = Locale.getDefault();
            setLocale(context, Locale.ENGLISH);
            try {
                try {
                    keyGenerator.init(new KeyGenParameterSpec.Builder(ALIAS_XIAOMI_PASSPORT, 3).setBlockModes("CBC").setEncryptionPaddings("NoPadding").setKeySize(128).setRandomizedEncryptionRequired(false).setUserAuthenticationRequired(false).build());
                    setLocale(context, locale);
                    SecretKey secretKeyGenerateKey = keyGenerator.generateKey();
                    KeyStore initedKeyStore = getInitedKeyStore();
                    if (secretKeyGenerateKey == null || initedKeyStore == null) {
                        return;
                    }
                    sSecureKey = secretKeyGenerateKey;
                } catch (InvalidAlgorithmParameterException e) {
                    AccountLogger.log(TAG, "init keypair generator failed", e);
                    setLocale(context, locale);
                }
            } catch (Throwable th) {
                setLocale(context, locale);
                throw th;
            }
        } catch (NoSuchAlgorithmException e2) {
            AccountLogger.log(TAG, "get key generator failed", e2);
        } catch (NoSuchProviderException e3) {
            AccountLogger.log(TAG, "get key generator failed", e3);
        }
    }

    private static SharedPreferences getSp(Context context) {
        return context.getSharedPreferences(SP_NAME_GENERATE_SECRET_KEY_RET, 0);
    }

    private static void setGenerateSecretKeySuccess(Context context, boolean z) {
        getSp(context).edit().putBoolean(KEY_BOOL_GENERATE_SECRET_KEY_SUCCESS, z).commit();
    }

    public static boolean hasGeneratedSecretKey(Context context) {
        return getSp(context).contains(KEY_BOOL_GENERATE_SECRET_KEY_SUCCESS);
    }

    public static boolean isGenerateSecretKeySuccess(Context context) {
        return getSp(context).getBoolean(KEY_BOOL_GENERATE_SECRET_KEY_SUCCESS, false);
    }

    /* JADX WARN: Code duplicated, block: B:31:0x0059  */
    public static synchronized void safeGenerateOrInitKey(Context context) {
        if (sSecureKey != null) {
            return;
        }
        if (hasGeneratedSecretKey(context)) {
            initExistingKey(context);
            AccountLogger.log(TAG, "already generate key");
            return;
        }
        AccountLogger.log(TAG, "start safe generate key");
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        boolean z = true;
        try {
            try {
                KeyStore initedKeyStore = getInitedKeyStore();
                if (initedKeyStore != null && initedKeyStore.containsAlias(ALIAS_XIAOMI_PASSPORT)) {
                    AccountLogger.log(TAG, "contains key already, init existing key");
                    initExistingKey(context);
                    if (sSecureKey == null) {
                        z = false;
                    }
                    setGenerateSecretKeySuccess(context, z);
                    return;
                }
                generateKeyAboveM(context);
                initExistingKey(context);
                if (sSecureKey == null) {
                    z = false;
                }
                setGenerateSecretKeySuccess(context, z);
                AccountLogger.log(TAG, "generate key cost " + (SystemClock.elapsedRealtime() - jElapsedRealtime) + " ms");
            } catch (Exception e) {
                AccountLogger.log(TAG, "generate key failed", e);
                if (sSecureKey == null) {
                    z = false;
                }
            }
        } catch (Throwable th) {
            if (sSecureKey == null) {
                z = false;
            }
            setGenerateSecretKeySuccess(context, z);
            throw th;
        }
    }

    private static void setLocale(Context context, Locale locale) {
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    private static synchronized KeyStore getInitedKeyStore() {
        if (sKeyStore != null) {
            return sKeyStore;
        }
        try {
            KeyStore keyStore = KeyStore.getInstance(ANDROID_KEY_STORE);
            try {
                try {
                    try {
                        keyStore.load(null);
                        sKeyStore = keyStore;
                        return keyStore;
                    } catch (NoSuchAlgorithmException e) {
                        AccountLogger.log(TAG, "init android keystore failed", e);
                        return null;
                    }
                } catch (IOException e2) {
                    AccountLogger.log(TAG, "init android keystore failed", e2);
                    return null;
                }
            } catch (CertificateException e3) {
                AccountLogger.log(TAG, "init android keystore failed", e3);
                return null;
            }
        } catch (KeyStoreException e4) {
            AccountLogger.log(TAG, "get android keystore failed", e4);
            return null;
        }
    }

    public static synchronized void deleteKey() {
        KeyStore initedKeyStore = getInitedKeyStore();
        try {
            if (initedKeyStore != null) {
                initedKeyStore.deleteEntry(ALIAS_XIAOMI_PASSPORT);
            } else {
                AccountLogger.log(TAG, "get null android key store");
            }
        } catch (KeyStoreException e) {
            throw new IllegalStateException(e);
        }
    }

    public static synchronized void initExistingKey(Context context) {
        if (hasGeneratedSecretKey(context) && !isGenerateSecretKeySuccess(context)) {
            AccountLogger.log(TAG, "not gen success, do not get");
            return;
        }
        if (sSecureKey != null) {
            AccountLogger.log(TAG, "key is not null, skip");
            return;
        }
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        KeyStore initedKeyStore = getInitedKeyStore();
        if (initedKeyStore == null) {
            AccountLogger.log(TAG, "initExistingKey#get null android key store");
            return;
        }
        try {
            if (!initedKeyStore.containsAlias(ALIAS_XIAOMI_PASSPORT)) {
                AccountLogger.log(TAG, "initExistingKey#has not generate key yet");
                return;
            }
            try {
                sSecureKey = initedKeyStore.getKey(ALIAS_XIAOMI_PASSPORT, null);
                AccountLogger.log(TAG, "get key null? " + (sSecureKey == null));
            } catch (KeyStoreException e) {
                throw new IllegalStateException(e);
            } catch (NoSuchAlgorithmException e2) {
                AccountLogger.log(TAG, "get key failed", e2);
            } catch (UnrecoverableKeyException e3) {
                AccountLogger.log(TAG, "get key failed", e3);
            }
            AccountLogger.log(TAG, "initExistingKey#init existing key cost " + (SystemClock.elapsedRealtime() - jElapsedRealtime) + " ms");
            return;
        } catch (KeyStoreException e4) {
            throw new IllegalStateException(e4);
        }
        throw th;
    }

    private static String encodeBase64(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return null;
        }
        return Base64.encodeToString(bArr, 10);
    }

    private static byte[] decodeBase64(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return Base64.decode(str, 10);
    }

    public static String encrypt(Context context, String str) {
        String str2 = sEncryptedDada.get(str);
        if (str2 != null) {
            return str2;
        }
        if (sSecureKey == null) {
            initExistingKey(context);
        }
        if (sSecureKey == null) {
            return str;
        }
        try {
            Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
            try {
                cipher.init(1, sSecureKey, new IvParameterSpec(AES_IV_BYTES));
                try {
                    byte[] bytes = str.getBytes();
                    if (bytes.length % 16 != 0) {
                        byte[] bArr = new byte[((bytes.length / 16) + 1) * 16];
                        for (int i = 0; i < bytes.length; i++) {
                            bArr[i] = bytes[i];
                        }
                        bytes = bArr;
                    }
                    String str3 = ENCRYPTED_STRING_PREFIX + encodeBase64(cipher.doFinal(bytes));
                    sEncryptedDada.put(str, str3);
                    return str3;
                } catch (BadPaddingException e) {
                    AccountLogger.log(TAG, "encrypt failed", e);
                    return str;
                } catch (IllegalBlockSizeException e2) {
                    AccountLogger.log(TAG, "encrypt failed", e2);
                    return str;
                }
            } catch (InvalidAlgorithmParameterException e3) {
                AccountLogger.log(TAG, "init cipher failed", e3);
                return str;
            } catch (InvalidKeyException e4) {
                AccountLogger.log(TAG, "init cipher failed", e4);
                return str;
            }
        } catch (NoSuchAlgorithmException e5) {
            AccountLogger.log(TAG, "get cipher failed", e5);
            return str;
        } catch (NoSuchPaddingException e6) {
            AccountLogger.log(TAG, "get cipher failed", e6);
            return str;
        }
    }

    public static String decrypt(Context context, String str) {
        if (!str.startsWith(ENCRYPTED_STRING_PREFIX)) {
            return str;
        }
        String str2 = sDecryptedDada.get(str);
        if (str2 != null) {
            return str2;
        }
        if (sSecureKey == null) {
            initExistingKey(context);
        }
        if (sSecureKey == null) {
            return null;
        }
        String strSubstring = str.substring(ENCRYPTED_STRING_PREFIX.length());
        try {
            Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
            try {
                cipher.init(2, sSecureKey, new IvParameterSpec(AES_IV_BYTES));
                byte[] bArrDoFinal = new byte[0];
                try {
                    bArrDoFinal = cipher.doFinal(decodeBase64(strSubstring));
                } catch (BadPaddingException e) {
                    AccountLogger.log(TAG, "decrypt failed", e);
                    return str;
                } catch (IllegalBlockSizeException e2) {
                    AccountLogger.log(TAG, "decrypt failed", e2);
                }
                int length = bArrDoFinal.length;
                int i = 0;
                for (int i2 = 0; i2 < length && bArrDoFinal[i2] != 0; i2++) {
                    i++;
                }
                String str3 = new String(bArrDoFinal, 0, i);
                sDecryptedDada.put(str, str3);
                return str3;
            } catch (InvalidAlgorithmParameterException e3) {
                AccountLogger.log(TAG, "init cipher failed", e3);
                return str;
            } catch (InvalidKeyException e4) {
                AccountLogger.log(TAG, "init cipher failed", e4);
                return str;
            }
        } catch (NoSuchAlgorithmException e5) {
            AccountLogger.log(TAG, "get cipher failed", e5);
            return str;
        } catch (NoSuchPaddingException e6) {
            AccountLogger.log(TAG, "get cipher failed", e6);
            return str;
        }
    }

    public static boolean isEncrypted(String str) {
        return str.startsWith(ENCRYPTED_STRING_PREFIX);
    }
}
