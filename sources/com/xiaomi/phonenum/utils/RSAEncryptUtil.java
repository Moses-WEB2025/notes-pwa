package com.xiaomi.phonenum.utils;

import android.text.TextUtils;
import android.util.Base64;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/* JADX INFO: loaded from: classes4.dex */
public class RSAEncryptUtil {
    private static final String RSA_CERTIFICATE_TYPE = "X.509";
    private static final String RSA_ENCRYPT_TRANSFORMATION = "RSA/ECB/PKCS1Padding";
    private static final String SYMMETRIC_ALGORITHM = "AES";
    private static final String UTF8 = "UTF-8";
    private static volatile PublicKey mRsaPublicKey = null;
    private static String rsaPublicKeyStr = "-----BEGIN CERTIFICATE-----\nMIICDzCCAXigAwIBAgIEWMX4OjANBgkqhkiG9w0BAQUFADBMMQswCQYDVQQGEwJD\nTjEPMA0GA1UEChMGeGlhb21pMQ8wDQYDVQQLEwZ4aWFvbWkxGzAZBgNVBAMTEmFj\nY291bnQueGlhb21pLmNvbTAeFw0xNzAzMTMwMTM5MDZaFw0xODAzMTMwMTM5MDZa\nMEwxCzAJBgNVBAYTAkNOMQ8wDQYDVQQKEwZ4aWFvbWkxDzANBgNVBAsTBnhpYW9t\naTEbMBkGA1UEAxMSYWNjb3VudC54aWFvbWkuY29tMIGfMA0GCSqGSIb3DQEBAQUA\nA4GNADCBiQKBgQCRDQSxAwWUmA57Isfphgl7H+QHgw9qObsvZM0Xx1YeDzKYVB62\nypGPcPfxnvD0+EfpdhbsMQYeO495BPPzFk+TsFJl4aR47k9sstxrIu7AFeFbdvGg\nubcEu3y/cAk7CcFE7aqKaW7+WFJzLaPVTj6tn0IUe7lFpHXnBFkpzZMVxwIDAQAB\nMA0GCSqGSIb3DQEBBQUAA4GBAICkoEOZ9OtLeZDSQpTqzq8GfU19C/aJCD6Ex7sl\nYqqXVn/p6AozxihEyvIilM56hyaKlLzNJdxPVRYUim6nv6r+kOwE8i7yDRAfcaZD\nnbBeTATPI7E3iKXLF64gjm3Syq8Pw30Yi2azEdB9U+57GBRa0cxAU6wfhn5GSXM6\nW+j0\n-----END CERTIFICATE-----\n";
    private final SecretKey mAesKey;
    private final String mEncryptedKey;

    public static class EncryptResult {
        public String content;
        public String encryptedKey;
    }

    public RSAEncryptUtil() throws EncryptException {
        SecretKey secretKeyGenerateSymmetricKey = generateSymmetricKey();
        this.mAesKey = secretKeyGenerateSymmetricKey;
        this.mEncryptedKey = rsaEncrypt(Base64.encodeToString(secretKeyGenerateSymmetricKey.getEncoded(), 10), getRsaPublicKey());
    }

    public EncryptResult encrypt(String str) throws EncryptException {
        EncryptResult encryptResult = new EncryptResult();
        encryptResult.content = aesEncrypt(str, this.mAesKey);
        encryptResult.encryptedKey = this.mEncryptedKey;
        return encryptResult;
    }

    public String aesDecrypt(String str) throws EncryptException {
        return aesDecrypt(str, this.mAesKey);
    }

    private static PublicKey getRsaPublicKey() throws EncryptException {
        if (mRsaPublicKey != null) {
            return mRsaPublicKey;
        }
        try {
            mRsaPublicKey = ((X509Certificate) CertificateFactory.getInstance(RSA_CERTIFICATE_TYPE).generateCertificate(new ByteArrayInputStream(rsaPublicKeyStr.getBytes("UTF-8")))).getPublicKey();
            return mRsaPublicKey;
        } catch (UnsupportedEncodingException e) {
            throw new EncryptException(e);
        } catch (CertificateException e2) {
            throw new EncryptException(e2);
        }
    }

    private static SecretKey generateSymmetricKey() throws EncryptException {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(SYMMETRIC_ALGORITHM);
            new SecureRandom();
            keyGenerator.init(128);
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptException(e);
        }
    }

    private static String aesEncrypt(String str, SecretKey secretKey) throws EncryptException {
        try {
            return new AESCoder(secretKey.getEncoded()).encrypt(str);
        } catch (AESCoder.CipherException e) {
            throw new EncryptException(e);
        }
    }

    private static String aesDecrypt(String str, SecretKey secretKey) throws EncryptException {
        try {
            return new AESCoder(secretKey.getEncoded()).decrypt(str);
        } catch (AESCoder.CipherException e) {
            throw new EncryptException(e);
        }
    }

    private static String rsaEncrypt(String str, PublicKey publicKey) throws EncryptException {
        return encrypt(str, publicKey, "RSA/ECB/PKCS1Padding");
    }

    private static String encrypt(String str, Key key, String str2) throws EncryptException {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            return new String(Base64.encode(encrypt(str.getBytes("UTF-8"), key, str2), 10), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new EncryptException(e);
        }
    }

    private static byte[] encrypt(byte[] bArr, Key key, String str) throws EncryptException {
        try {
            Cipher cipher = Cipher.getInstance(str);
            cipher.init(1, key);
            return cipher.doFinal(bArr);
        } catch (InvalidKeyException e) {
            throw new EncryptException(e);
        } catch (NoSuchAlgorithmException e2) {
            throw new EncryptException(e2);
        } catch (BadPaddingException e3) {
            throw new EncryptException(e3);
        } catch (IllegalBlockSizeException e4) {
            throw new EncryptException(e4);
        } catch (NoSuchPaddingException e5) {
            throw new EncryptException(e5);
        }
    }

    public static class EncryptException extends Exception {
        public EncryptException(Throwable th) {
            super(th);
        }
    }

    public static void setRsaPublicKeyForTest(String str) {
        rsaPublicKeyStr = str;
    }
}
