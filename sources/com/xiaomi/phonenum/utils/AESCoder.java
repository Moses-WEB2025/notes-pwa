package com.xiaomi.phonenum.utils;

import android.util.Base64;
import com.xiaomi.accountsdk.utils.AccountLogger;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes4.dex */
public class AESCoder {
    public static final String AES_ALGORITHM = "AES/CBC/PKCS5Padding";
    public static final String TAG = "AESCoder";
    private static final String UTF8 = "UTF-8";
    private SecretKeySpec keySpec;

    public AESCoder(byte[] bArr) {
        if (bArr == null) {
            throw new SecurityException("aes key is null");
        }
        if (bArr.length != 16) {
            AccountLogger.log("AESCoder", "aesKey is invalid");
        }
        this.keySpec = new SecretKeySpec(bArr, "AES");
    }

    public String decrypt(String str) throws CipherException {
        if (str == null) {
            AccountLogger.log("AESCoder", "decrypt failed for empty data");
            return null;
        }
        try {
            return new String(decrypt(Base64.decode(str, 2)), "UTF-8");
        } catch (Exception e) {
            throw new CipherException("fail to decrypt by aescoder", e);
        }
    }

    private byte[] decrypt(byte[] bArr) throws CipherException {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(2, this.keySpec, new IvParameterSpec(getInitalVector()));
            if (bArr == null) {
                throw new IllegalBlockSizeException("no block data");
            }
            return cipher.doFinal(bArr);
        } catch (Exception e) {
            throw new CipherException("fail to decrypt by aescoder", e);
        }
    }

    private byte[] getInitalVector() {
        return "0102030405060708".getBytes();
    }

    public String encrypt(String str) throws CipherException {
        try {
            return Base64.encodeToString(encrypt(str.getBytes("UTF-8")), 10);
        } catch (Exception e) {
            throw new CipherException("fail to encrypt by aescoder", e);
        }
    }

    private byte[] encrypt(byte[] bArr) throws CipherException {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(1, this.keySpec, new IvParameterSpec(getInitalVector()));
            return cipher.doFinal(bArr);
        } catch (Exception e) {
            throw new CipherException("fail to encrypt by aescoder", e);
        }
    }

    public class CipherException extends Exception {
        private static final long serialVersionUID = -1479750857131098427L;

        public CipherException(String str) {
            super(str);
        }

        public CipherException(String str, Throwable th) {
            super(str, th);
        }

        public CipherException(Throwable th) {
            super(th);
        }
    }
}
