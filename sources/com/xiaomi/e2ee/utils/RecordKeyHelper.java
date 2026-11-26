package com.xiaomi.e2ee.utils;

import android.util.LruCache;
import com.xiaomi.e2ee.E2EEException;
import com.xiaomi.e2ee.appkey.AppKeyServiceManager;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.zip.CRC32;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;

/* JADX INFO: loaded from: classes4.dex */
public class RecordKeyHelper {
    private static final int KEY_SIZE = 256;
    private static final int SIGN_SIZE = 4;
    private static final LruCache<String, String> sRecordKeyCache = new LruCache<>(200);

    public static String getRecordKey() throws E2EEException {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            return E2EECommonUtils.encodeToString(keyGenerator.generateKey().getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new E2EEException("NoSuchAlgorithmException happened when get record key", e);
        }
    }

    public static String getEncryptedRecordKey(String str, String str2, String str3) throws E2EEException {
        try {
            byte[] bArrDecode = E2EECommonUtils.decode(str);
            String strEncodeToString = E2EECommonUtils.encodeToString(addEncryptedRecordKeySig(bArrDecode, CipherUtils.getDecodeRecordKeyCipher(str2, str3, 1).doFinal(bArrDecode)));
            sRecordKeyCache.put(strEncodeToString, str);
            return strEncodeToString;
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            throw new E2EEException(e);
        }
    }

    public static String getDecryptRecordKey(String str, String str2, long j) throws InterruptedException, E2EEException {
        LruCache<String, String> lruCache = sRecordKeyCache;
        String str3 = lruCache.get(str);
        if (str3 != null) {
            return str3;
        }
        try {
            String strEncodeToString = E2EECommonUtils.encodeToString(CipherUtils.getDecodeRecordKeyCipher(AppKeyServiceManager.getInstance().getAppKeyInfo(j).decryptAppKey, str2, 2).doFinal(getEncryptedRecordeKeyWithoutSign(str)));
            lruCache.put(str, strEncodeToString);
            return strEncodeToString;
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            throw new E2EEException(e);
        }
    }

    private static byte[] getEncryptedRecordeKeyWithoutSign(String str) {
        byte[] bArrDecode = E2EECommonUtils.decode(str);
        return Arrays.copyOfRange(bArrDecode, 0, bArrDecode.length - 4);
    }

    private static byte[] addEncryptedRecordKeySig(byte[] bArr, byte[] bArr2) {
        CRC32 crc32 = new CRC32();
        crc32.update(bArr);
        long value = crc32.getValue();
        byte[] bArr3 = new byte[bArr2.length + 4];
        System.arraycopy(bArr2, 0, bArr3, 0, bArr2.length);
        System.arraycopy(new byte[]{(byte) ((value >> 24) & 255), (byte) ((value >> 16) & 255), (byte) ((value >> 8) & 255), (byte) (value & 255)}, 0, bArr3, bArr2.length, 4);
        return bArr3;
    }
}
