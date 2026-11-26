package com.xiaomi.accountsdk.utils;

import android.util.Base64;
import com.xiaomi.accountsdk.request.CipherException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes4.dex */
public class AESCoderForGcm implements CryptCoder {
    public static final String AES_GCM_ALGORITHM = "AES/GCM/NoPadding";
    public static final int AES_LENGTH = 16;
    public static final String AES_PROVIDER_BC = "BC";
    public static final String TAG = "AESCoder";
    private static final String UTF8 = "UTF-8";
    public static final String version = "2";
    private String algorithm;
    private SecretKeySpec keySpec;
    private String provider;

    public AESCoderForGcm(byte[] bArr) {
        this.provider = AES_PROVIDER_BC;
        if (bArr == null) {
            throw new SecurityException("aes key is null");
        }
        if (bArr.length != 16) {
            AccountLogger.log("AESCoder", "aesKey is invalid");
        }
        this.keySpec = new SecretKeySpec(bArr, "AES");
    }

    public AESCoderForGcm(String str, String str2, byte[] bArr) {
        this(bArr);
        this.algorithm = str;
        this.provider = str2;
    }

    public AESCoderForGcm(String str) {
        this(str == null ? null : Base64.decode(str, 2));
    }

    public AESCoderForGcm(String str, String str2, String str3) {
        this(str3);
        this.algorithm = str;
        this.provider = str2;
    }

    public String decryptWithIV(String str) throws CipherException {
        if (str == null) {
            AccountLogger.log("AESCoder", "decrypt failed for empty data");
            return null;
        }
        try {
            String[] strArrSplit = str.split(":");
            if (strArrSplit.length != 3) {
                throw new CipherException("invalid encrypted data format, expected 3 parts separated by ':'");
            }
            String str2 = strArrSplit[0];
            String str3 = strArrSplit[1];
            String str4 = strArrSplit[2];
            if (!"2".equals(str2)) {
                throw new CipherException("unsupported version: " + str2);
            }
            return new String(decryptWithIV(Base64.decode(str4, 2), Base64.decode(str3, 8)), "UTF-8");
        } catch (Exception e) {
            throw new CipherException("fail to decrypt by aescoder", e);
        }
    }

    @Override // com.xiaomi.accountsdk.utils.CryptCoder
    public String decrypt(String str) throws CipherException {
        return decryptWithIV(str);
    }

    public byte[] decryptWithIV(byte[] bArr, byte[] bArr2) throws CipherException {
        try {
            Cipher cipher = Cipher.getInstance(this.algorithm);
            cipher.init(2, this.keySpec, new GCMParameterSpec(128, bArr2));
            if (bArr == null) {
                throw new IllegalBlockSizeException("no block data");
            }
            return cipher.doFinal(bArr);
        } catch (Exception e) {
            throw new CipherException("fail to decrypt by aescoder", e);
        }
    }

    @Override // com.xiaomi.accountsdk.utils.CryptCoder
    public String encrypt(String str) throws CipherException {
        return encryptWithIv(str, generateRandomIV());
    }

    private byte[] generateRandomIV() {
        byte[] bArr = new byte[12];
        new SecureRandom().nextBytes(bArr);
        return bArr;
    }

    public String encryptWithIv(String str, byte[] bArr) throws CipherException {
        try {
            byte[] bArrEncryptWithIv = encryptWithIv(str.getBytes("UTF-8"), bArr);
            if (bArr.length != 12) {
                throw new CipherException("GCM requires 12-byte IV, got: " + bArr.length);
            }
            return "2:" + Base64.encodeToString(bArr, 8) + ":" + Base64.encodeToString(bArrEncryptWithIv, 2);
        } catch (Exception e) {
            throw new CipherException("fail to encrypt by aescoder", e);
        }
    }

    public byte[] encryptWithIv(byte[] bArr, byte[] bArr2) throws CipherException {
        try {
            Cipher cipher = Cipher.getInstance(this.algorithm);
            cipher.init(1, this.keySpec, new GCMParameterSpec(128, bArr2));
            return cipher.doFinal(bArr);
        } catch (Exception e) {
            throw new CipherException("fail to encrypt by aescoder", e);
        }
    }
}
