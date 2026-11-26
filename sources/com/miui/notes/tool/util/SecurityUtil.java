package com.miui.notes.tool.util;

import android.content.Context;
import android.security.KeyPairGeneratorSpec;
import android.text.TextUtils;
import android.util.Base64;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Date;
import java.util.Calendar;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.x500.X500Principal;
import miuix.core.util.IOUtils;
import miuix.internal.hybrid.SignUtils;

/* JADX INFO: loaded from: classes4.dex */
@Deprecated
public class SecurityUtil {
    private static final byte[] AES_IV = {-4, -105, 16, -96, 72, -51, 122, 46, 119, 83, -106, -18, 66, -128, -6, -50};
    private static final String ALIAS = "miui_notes_secret";
    private static final String SECRET_KEY_FILE = "miui_notes_secret";
    private static final String SECRET_PROVIDER = "AndroidKeyStore";

    private static class KeyManager {
        private static SecretKey sSecretKey;

        private KeyManager() {
        }

        public static synchronized SecretKey getKey(Context context) throws InvalidKeySpecException, IllegalBlockSizeException, IOException, CertificateException, KeyStoreException, InvalidAlgorithmParameterException, DecryptFailedException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, UnrecoverableEntryException {
            SecretKey secretKeyGenerateSecretKey;
            SecretKey secretKey = sSecretKey;
            if (secretKey != null) {
                return secretKey;
            }
            byte[] keyFromFile = getKeyFromFile(context);
            if (keyFromFile != null) {
                byte[] bArrDecryptKey = decryptKey(context, keyFromFile);
                if (bArrDecryptKey == null || bArrDecryptKey.length <= 0) {
                    throw new DecryptFailedException("can not decrypt secret key!");
                }
                secretKeyGenerateSecretKey = new SecretKeySpec(bArrDecryptKey, "AES");
            } else {
                secretKeyGenerateSecretKey = generateSecretKey();
                saveKeyToFile(context, encryptKey(context, secretKeyGenerateSecretKey.getEncoded()));
            }
            sSecretKey = secretKeyGenerateSecretKey;
            return secretKeyGenerateSecretKey;
        }

        private static byte[] getKeyFromFile(Context context) throws Throwable {
            Throwable th;
            FileInputStream fileInputStreamOpenFileInput;
            try {
                fileInputStreamOpenFileInput = context.openFileInput("miui_notes_secret");
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
                fileOutputStreamOpenFileOutput = context.openFileOutput("miui_notes_secret", 0);
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

        private static void generateKeyPairInKeyStore(Context context) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
            Date date = new Date(0L);
            Calendar calendar = Calendar.getInstance();
            calendar.set(2100, 0, 1);
            KeyPairGeneratorSpec keyPairGeneratorSpecBuild = new KeyPairGeneratorSpec.Builder(context).setAlias("miui_notes_secret").setSubject(new X500Principal("CN=MiuiNotes, O=Android Authority")).setSerialNumber(BigInteger.ONE).setStartDate(date).setEndDate(calendar.getTime()).build();
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(SignUtils.KEY_RSA, SecurityUtil.SECRET_PROVIDER);
            keyPairGenerator.initialize(keyPairGeneratorSpecBuild);
            keyPairGenerator.generateKeyPair();
        }

        private static KeyStore.PrivateKeyEntry getKeyPairFromKeyStore(Context context) throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException, NoSuchProviderException, InvalidAlgorithmParameterException, UnrecoverableEntryException {
            KeyStore keyStore = KeyStore.getInstance(SecurityUtil.SECRET_PROVIDER);
            keyStore.load(null);
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry("miui_notes_secret", null);
            if (privateKeyEntry != null) {
                return privateKeyEntry;
            }
            generateKeyPairInKeyStore(context);
            return (KeyStore.PrivateKeyEntry) keyStore.getEntry("miui_notes_secret", null);
        }

        private static byte[] encryptKey(Context context, byte[] bArr) throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, IOException, InvalidKeyException, KeyStoreException, CertificateException, NoSuchProviderException, InvalidAlgorithmParameterException, UnrecoverableEntryException {
            KeyStore.PrivateKeyEntry keyPairFromKeyStore = getKeyPairFromKeyStore(context);
            Cipher cipher = Cipher.getInstance(SignUtils.CIPHER_RSA);
            cipher.init(1, keyPairFromKeyStore.getCertificate().getPublicKey());
            return cipher.doFinal(bArr);
        }

        private static byte[] decryptKey(Context context, byte[] bArr) throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, IOException, InvalidKeyException, KeyStoreException, CertificateException, NoSuchProviderException, InvalidAlgorithmParameterException, UnrecoverableEntryException {
            KeyStore.PrivateKeyEntry keyPairFromKeyStore = getKeyPairFromKeyStore(context);
            Cipher cipher = Cipher.getInstance(SignUtils.CIPHER_RSA);
            cipher.init(2, keyPairFromKeyStore.getPrivateKey());
            return cipher.doFinal(bArr);
        }
    }

    private static byte[] encrypt(Context context, byte[] bArr) throws EncryptFailedException {
        try {
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
        } catch (DecryptFailedException e13) {
            e13.printStackTrace();
            throw new EncryptFailedException(e13.getMessage(), e13);
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
        return new String(Base64.encode(encrypt(context, str.getBytes()), 1));
    }

    public static String decrypt(Context context, String str) throws DecryptFailedException {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        return new String(decrypt(context, Base64.decode(str.getBytes(), 1)));
    }
}
