package com.xiaomi.passport.utils;

import android.text.TextUtils;
import android.util.Base64;
import com.xiaomi.accountsdk.account.exception.CryptoException;
import com.xiaomi.accountsdk.request.CipherException;
import com.xiaomi.accountsdk.utils.AESCoder;
import com.xiaomi.accountsdk.utils.RSACoder;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/* JADX INFO: loaded from: classes4.dex */
public class PassportEnvEncryptUtils {
    private static final String SYMMETRIC_ALGORITHM = "AES";

    public static class EncryptResult {
        public String content;
        public String encryptedKey;
    }

    public static class EncryptResultWithIv extends EncryptResult {
        public byte[] iv;
    }

    public static EncryptResult encrypt(String[] strArr) throws EncryptException {
        return encrypt(TextUtils.join(":", strArr));
    }

    public static EncryptResultWithIv encryptWithChangingIv(String str) throws EncryptException {
        byte[] bArr = new byte[16];
        new SecureRandom().nextBytes(bArr);
        return encryptWithIv(str, bArr);
    }

    private static EncryptResultWithIv encryptWithIv(String str, byte[] bArr) throws EncryptException {
        EncryptResultWithIv encryptResultWithIv = new EncryptResultWithIv();
        encryptResultWithIv.iv = (byte[]) bArr.clone();
        SecretKey secretKeyGenerateSymmetricKey = generateSymmetricKey();
        try {
            String strEncodeToString = Base64.encodeToString(RSACoder.encrypt(Base64.encode(secretKeyGenerateSymmetricKey.getEncoded(), 10), RSACoder.getCertificatePublicKey(RSACoder.SPECIFIED_RSA_PUBLIC_KEY)), 10);
            encryptResultWithIv.content = aesEncrypt(str, secretKeyGenerateSymmetricKey, encryptResultWithIv.iv);
            encryptResultWithIv.encryptedKey = strEncodeToString;
            return encryptResultWithIv;
        } catch (CryptoException e) {
            throw new EncryptException(e);
        }
    }

    @Deprecated
    public static EncryptResult encrypt(String str) throws EncryptException {
        return encryptWithIv(str, AESCoder.DEFAULT_IV);
    }

    private static SecretKey generateSymmetricKey() throws EncryptException {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(SYMMETRIC_ALGORITHM);
            keyGenerator.init(new SecureRandom());
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptException(e);
        }
    }

    private static String aesEncrypt(String str, SecretKey secretKey, byte[] bArr) throws EncryptException {
        try {
            return new AESCoder(secretKey.getEncoded()).encryptWithIv(str, bArr);
        } catch (CipherException e) {
            throw new EncryptException(e);
        }
    }

    public static class EncryptException extends Exception {
        public EncryptException(Throwable th) {
            super(th);
        }
    }
}
