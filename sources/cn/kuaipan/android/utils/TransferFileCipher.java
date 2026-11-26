package cn.kuaipan.android.utils;

import com.xiaomi.e2ee.E2EEException;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
public class TransferFileCipher {
    public static Cipher getFileCipher(String str, String str2, int i) throws E2EEException {
        byte[] bArrDecode = decode(str);
        byte[] bArrDecode2 = decode(str2);
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(bArrDecode, "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(bArrDecode2);
            Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
            cipher.init(i, secretKeySpec, ivParameterSpec);
            return cipher;
        } catch (Exception e) {
            throw new E2EEException(E2EEException.GET_CIPHER_ERROR, e);
        }
    }

    private static byte[] decode(String str) {
        return Base64.decode(str, 11);
    }

    private static String encodeToString(byte[] bArr) {
        return Base64.encodeToString(bArr, 11);
    }

    public static String calculateIV(long j, String str) {
        byte[] bArrDecode = decode(str);
        int length = bArrDecode.length;
        int i = (int) (j / 16);
        do {
            length--;
            int i2 = i + (bArrDecode[length] & 255);
            bArrDecode[length] = (byte) i2;
            i = i2 >> 8;
        } while (length != 0);
        return encodeToString(bArrDecode);
    }
}
