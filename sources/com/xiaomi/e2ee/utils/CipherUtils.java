package com.xiaomi.e2ee.utils;

import com.xiaomi.e2ee.E2EEException;
import com.xiaomi.e2ee.appkey.KeyEntry;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes4.dex */
public class CipherUtils {
    private static final String ALGORITHM = "AES";
    private static final String FILE_ENCRYPT_MODE = "AES/CTR/NoPadding";
    private static final int GCM_TAG_SIZE = 128;
    private static final String STR_ENCRYPT_MODE = "AES/GCM/NoPadding";

    public static Cipher getDecodeFileCipher(KeyEntry keyEntry, int i) throws InterruptedException, E2EEException {
        return getDecodeFileCipher(RecordKeyHelper.getDecryptRecordKey(keyEntry.encryptedRecordKey, keyEntry.recordIV, keyEntry.appKeyVersion), keyEntry.recordIV, i);
    }

    public static Cipher getDecodeFileCipher(String str, String str2, int i) throws E2EEException {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(E2EECommonUtils.decode(str), ALGORITHM);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(E2EECommonUtils.decode(str2));
            Cipher cipher = Cipher.getInstance(FILE_ENCRYPT_MODE);
            cipher.init(i, secretKeySpec, ivParameterSpec);
            return cipher;
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new E2EEException(E2EEException.GET_CIPHER_ERROR, e);
        }
    }

    public static Cipher getDecodeStrCipher(KeyEntry keyEntry, int i) throws InterruptedException, E2EEException {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(E2EECommonUtils.decode(RecordKeyHelper.getDecryptRecordKey(keyEntry.encryptedRecordKey, keyEntry.recordIV, keyEntry.appKeyVersion)), ALGORITHM);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            if (i == 1) {
                cipher.init(i, secretKeySpec, new IvParameterSpec(E2EECommonUtils.decode(keyEntry.recordIV)));
                return cipher;
            }
            cipher.init(i, secretKeySpec, new GCMParameterSpec(128, E2EECommonUtils.decode(keyEntry.recordIV)));
            return cipher;
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new E2EEException(E2EEException.GET_CIPHER_ERROR, e);
        }
    }

    public static Cipher getDecodeRecordKeyCipher(String str, String str2, int i) throws E2EEException {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(E2EECommonUtils.decode(str), ALGORITHM);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            if (i == 1) {
                cipher.init(i, secretKeySpec, new IvParameterSpec(E2EECommonUtils.decode(str2)));
                return cipher;
            }
            cipher.init(i, secretKeySpec, new GCMParameterSpec(128, E2EECommonUtils.decode(str2)));
            return cipher;
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new E2EEException(E2EEException.GET_CIPHER_ERROR, e);
        }
    }
}
