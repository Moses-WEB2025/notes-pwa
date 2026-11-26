package com.miui.notes.tool.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import com.miui.common.tool.Logger;
import com.miui.notes.tool.PreferenceUtils;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import miuix.core.util.IOUtils;

/* JADX INFO: loaded from: classes4.dex */
public class EncryptUtil {
    private static final byte[] AES_IV = {-4, -105, 16, -96, 72, -51, 122, 46, 119, 83, -106, -18, 66, -128, -6, -50};
    private static final String SECRET_KEY_FILE = "miui_notes_secret_2";

    private static class KeyManager {
        private static SecretKey sSecretKey;

        private KeyManager() {
        }

        public static synchronized SecretKey getKey(Context context) throws BadPaddingException, NoSuchPaddingException, InvalidKeySpecException, IllegalBlockSizeException, NoSuchAlgorithmException, IOException, InvalidKeyException, CertificateException, KeyStoreException, NoSuchProviderException, UnrecoverableEntryException, InvalidAlgorithmParameterException {
            SecretKey secretKeySpec;
            SecretKey secretKey = sSecretKey;
            if (secretKey != null) {
                return secretKey;
            }
            byte[] keyFromFile = getKeyFromFile(context);
            if (keyFromFile != null && keyFromFile.length > 0) {
                secretKeySpec = new SecretKeySpec(keyFromFile, "AES");
            } else {
                if (PreferenceUtils.getSecretKeyGenerated(context)) {
                    throw new RuntimeException("can not get generated key from file");
                }
                SecretKey secretKeyGenerateSecretKey = generateSecretKey();
                PreferenceUtils.setSecretKeyGenerated(context, true);
                saveKeyToFile(context, secretKeyGenerateSecretKey.getEncoded());
                secretKeySpec = secretKeyGenerateSecretKey;
            }
            sSecretKey = secretKeySpec;
            return secretKeySpec;
        }

        private static byte[] getKeyFromFile(Context context) throws Throwable {
            Throwable th;
            FileInputStream fileInputStreamOpenFileInput;
            try {
                fileInputStreamOpenFileInput = context.openFileInput(EncryptUtil.SECRET_KEY_FILE);
                try {
                    byte[] byteArray = IOUtils.toByteArray(fileInputStreamOpenFileInput);
                    IOUtils.closeQuietly((InputStream) fileInputStreamOpenFileInput);
                    return byteArray;
                } catch (IOException unused) {
                    IOUtils.closeQuietly((InputStream) fileInputStreamOpenFileInput);
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    IOUtils.closeQuietly((InputStream) fileInputStreamOpenFileInput);
                    throw th;
                }
            } catch (IOException unused2) {
                fileInputStreamOpenFileInput = null;
            } catch (Throwable th3) {
                th = th3;
                fileInputStreamOpenFileInput = null;
            }
        }

        private static void saveKeyToFile(Context context, byte[] bArr) {
            FileOutputStream fileOutputStreamOpenFileOutput = null;
            try {
                fileOutputStreamOpenFileOutput = context.openFileOutput(EncryptUtil.SECRET_KEY_FILE, 0);
                IOUtils.copy(new ByteArrayInputStream(bArr), fileOutputStreamOpenFileOutput);
            } catch (IOException unused) {
            } finally {
                IOUtils.closeQuietly((OutputStream) fileOutputStreamOpenFileOutput);
            }
        }

        private static SecretKey generateSecretKey() throws NoSuchAlgorithmException {
            SecureRandom secureRandom = new SecureRandom();
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256, secureRandom);
            return keyGenerator.generateKey();
        }
    }

    private static byte[] encrypt(Context context, byte[] bArr) throws EncryptFailedException {
        try {
            SecretKey key = KeyManager.getKey(context);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(1, key, new IvParameterSpec(AES_IV));
            return cipher.doFinal(bArr);
        } catch (IOException e) {
            e.printStackTrace();
            throw new EncryptFailedException(e.getMessage(), e);
        } catch (InvalidAlgorithmParameterException e2) {
            e2.printStackTrace();
            throw new EncryptFailedException(e2.getMessage(), e2);
        } catch (InvalidKeyException e3) {
            e3.printStackTrace();
            throw new EncryptFailedException(e3.getMessage(), e3);
        } catch (KeyStoreException e4) {
            e4.printStackTrace();
            throw new EncryptFailedException(e4.getMessage(), e4);
        } catch (NoSuchAlgorithmException e5) {
            e5.printStackTrace();
            throw new EncryptFailedException(e5.getMessage(), e5);
        } catch (NoSuchProviderException e6) {
            e6.printStackTrace();
            throw new EncryptFailedException(e6.getMessage(), e6);
        } catch (UnrecoverableEntryException e7) {
            e7.printStackTrace();
            throw new EncryptFailedException(e7.getMessage(), e7);
        } catch (CertificateException e8) {
            e8.printStackTrace();
            throw new EncryptFailedException(e8.getMessage(), e8);
        } catch (InvalidKeySpecException e9) {
            e9.printStackTrace();
            throw new EncryptFailedException(e9.getMessage(), e9);
        } catch (BadPaddingException e10) {
            e10.printStackTrace();
            throw new EncryptFailedException(e10.getMessage(), e10);
        } catch (IllegalBlockSizeException e11) {
            e11.printStackTrace();
            throw new EncryptFailedException(e11.getMessage(), e11);
        } catch (NoSuchPaddingException e12) {
            e12.printStackTrace();
            throw new EncryptFailedException(e12.getMessage(), e12);
        }
    }

    private static byte[] decrypt(Context context, byte[] bArr) throws DecryptFailedException {
        try {
            SecretKey key = KeyManager.getKey(context);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(2, key, new IvParameterSpec(AES_IV));
            return cipher.doFinal(bArr);
        } catch (IOException e) {
            e.printStackTrace();
            throw new DecryptFailedException(e.getMessage(), e);
        } catch (InvalidAlgorithmParameterException e2) {
            e2.printStackTrace();
            throw new DecryptFailedException(e2.getMessage(), e2);
        } catch (InvalidKeyException e3) {
            e3.printStackTrace();
            throw new DecryptFailedException(e3.getMessage(), e3);
        } catch (KeyStoreException e4) {
            e4.printStackTrace();
            throw new DecryptFailedException(e4.getMessage(), e4);
        } catch (NoSuchAlgorithmException e5) {
            e5.printStackTrace();
            throw new DecryptFailedException(e5.getMessage(), e5);
        } catch (NoSuchProviderException e6) {
            e6.printStackTrace();
            throw new DecryptFailedException(e6.getMessage(), e6);
        } catch (UnrecoverableEntryException e7) {
            e7.printStackTrace();
            throw new DecryptFailedException(e7.getMessage(), e7);
        } catch (CertificateException e8) {
            e8.printStackTrace();
            throw new DecryptFailedException(e8.getMessage(), e8);
        } catch (InvalidKeySpecException e9) {
            e9.printStackTrace();
            throw new DecryptFailedException(e9.getMessage(), e9);
        } catch (BadPaddingException e10) {
            e10.printStackTrace();
            throw new DecryptFailedException(e10.getMessage(), e10);
        } catch (IllegalBlockSizeException e11) {
            e11.printStackTrace();
            throw new DecryptFailedException(e11.getMessage(), e11);
        } catch (NoSuchPaddingException e12) {
            e12.printStackTrace();
            throw new DecryptFailedException(e12.getMessage(), e12);
        }
    }

    public static String encrypt(Context context, String str) throws EncryptFailedException {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            if (EncryptNewUtil.isEncryptedData(str)) {
                Logger.INSTANCE.d("EncryptUtil", "encrypt: Attempting to encrypt already encrypted/corrupted data.");
                return str;
            }
            return new String(Base64.encode(encrypt(context, str.getBytes()), 1));
        } catch (Exception e) {
            Logger.INSTANCE.d("EncryptUtil", "encrypt: isEncryptedData failed,e:" + e);
            return str;
        }
    }

    public static String decrypt(Context context, String str) throws DecryptFailedException {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        return new String(decrypt(context, Base64.decode(str.getBytes(), 1)));
    }
}
