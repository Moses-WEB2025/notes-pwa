package com.xiaomi.e2ee.utils;

import java.security.SecureRandom;

/* JADX INFO: loaded from: classes4.dex */
public class E2EECommonUtils {
    private static final int BASE64_FLAG = 11;
    private static final int IV_LENGTH = 16;

    public static String encodeToString(byte[] bArr) {
        return Base64.encodeToString(bArr, 11);
    }

    public static byte[] decode(String str) {
        return Base64.decode(str, 11);
    }

    public static String getRandomRecordIV() {
        byte[] bArr = new byte[16];
        new SecureRandom().nextBytes(bArr);
        return encodeToString(bArr);
    }

    public static String calculateDecodeFileIV(long j, String str) {
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
