package com.xiaomi.verificationsdk.internal;

import android.text.TextUtils;
import android.util.Base64;
import com.xiaomi.accountsdk.request.CipherException;
import com.xiaomi.accountsdk.utils.AESCoder;
import com.xiaomi.accountsdk.utils.CloudCoder;
import com.xiaomi.passport.utils.PassportEnvEncryptUtils;
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
import java.util.UUID;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/* JADX INFO: loaded from: classes4.dex */
public class EnvEncryptUtils {
    private static final int ENV_FACTOR_LENGTH = 6;
    private static final String RSA_CERTIFICATE_TYPE = "X.509";
    private static final String RSA_ENCRYPT_TRANSFORMATION = "RSA/ECB/PKCS1Padding";
    private static final String SYMMETRIC_ALGORITHM = "AES";
    private static final String UTF8 = "UTF-8";
    private static volatile SecretKey mAesKey = null;
    private static volatile String mEncryptedKey = null;
    private static volatile PublicKey mRsaPublicKey = null;
    private static String rsaPublicKeyStr = "-----BEGIN CERTIFICATE-----\nMIIDXTCCAkWgAwIBAgIJAOMjETkYAg3lMA0GCSqGSIb3DQEBCwUAMEUxCzAJBgNV\nBAYTAkFVMRMwEQYDVQQIDApTb21lLVN0YXRlMSEwHwYDVQQKDBhJbnRlcm5ldCBX\naWRnaXRzIFB0eSBMdGQwHhcNMTgwMTExMDk0ODQ3WhcNMTgwMjEwMDk0ODQ3WjBF\nMQswCQYDVQQGEwJBVTETMBEGA1UECAwKU29tZS1TdGF0ZTEhMB8GA1UECgwYSW50\nZXJuZXQgV2lkZ2l0cyBQdHkgTHRkMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIB\nCgKCAQEArxfNLkuAQ/BYHzkzVwtug+0abmYRBVCEScSzGxJIOsfxVzcuqaKO87H2\no2wBcacD3bRHhMjTkhSEqxPjQ/FEXuJ1cdbmr3+b3EQR6wf/cYcMx2468/QyVoQ7\nBADLSPecQhtgGOllkC+cLYN6Md34Uii6U+VJf0p0q/saxUTZvhR2ka9fqJ4+6C6c\nOghIecjMYQNHIaNW+eSKunfFsXVU+QfMD0q2EM9wo20aLnos24yDzRjh9HJc6xfr\n37jRlv1/boG/EABMG9FnTm35xWrVR0nw3cpYF7GZg13QicS/ZwEsSd4HyboAruMx\nJBPvK3Jdr4ZS23bpN0cavWOJsBqZVwIDAQABo1AwTjAdBgNVHQ4EFgQU0AWcCdJQ\nruwyc3Hjt2dEmYXmUCUwHwYDVR0jBBgwFoAU0AWcCdJQruwyc3Hjt2dEmYXmUCUw\nDAYDVR0TBAUwAwEB/zANBgkqhkiG9w0BAQsFAAOCAQEASssjoOG4RbRjy6ivCDkZ\nteOLur7jQ6/AZru8n8Fow8WGd9zM1uFZs9+c6okvlmUeCPuOiYDxps6xfL23hkQc\nKghkMofjBn2eLoqTiQ+woAoZHAqLuiFUHae85KH7185EV8NTm2LpJJoqh2x5KU4Q\nlCWm+5uadtqPEbO9wmI1er5Kob/njPI0QtQn2/5H9u7t71MCjHIGuQ5OSrZmmyzV\nTFUVbdkf0mwoL44JO7ErbdOQ/nq+dElYiGnq7tI9gdt78k3Pgi7ykU5W7pnDbxLN\no66M6umwCy2ies0zzsxS9pKzurddXd+Zt4NBh74MV0RCf2PbDCtUODTBowLeDluq\nXw==\n-----END CERTIFICATE-----\n";

    public static class EncryptResult {
        public String content;
        public String encryptedKey;
    }

    public static EncryptResult encrypt(String[] strArr) throws EncryptException {
        return encrypt(TextUtils.join(":", strArr));
    }

    public static EncryptResult encrypt(String str) throws EncryptException {
        EncryptResult encryptResult = new EncryptResult();
        synchronized (PassportEnvEncryptUtils.class) {
            if (mAesKey == null || mEncryptedKey == null) {
                mAesKey = generateSymmetricKey();
                mEncryptedKey = rsaEncrypt(Base64.encodeToString(mAesKey.getEncoded(), 10), getRsaPublicKey());
            }
        }
        encryptResult.content = aesEncrypt(str, mAesKey);
        encryptResult.encryptedKey = mEncryptedKey;
        return encryptResult;
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
            keyGenerator.init(128, new SecureRandom());
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptException(e);
        }
    }

    private static String aesEncrypt(String str, SecretKey secretKey) throws EncryptException {
        try {
            return new AESCoder(secretKey.getEncoded()).encrypt(str);
        } catch (CipherException e) {
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

    static void setRsaPublicKeyForTest(String str) {
        rsaPublicKeyStr = str;
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().trim().replaceAll("-", "");
    }

    public static String hash(String str) {
        return hash(str, 6);
    }

    private static String hash(String str, int i) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        String strHash4SHA1 = CloudCoder.hash4SHA1(str);
        return (i <= 0 || i > strHash4SHA1.length()) ? strHash4SHA1 : strHash4SHA1.substring(0, i);
    }
}
