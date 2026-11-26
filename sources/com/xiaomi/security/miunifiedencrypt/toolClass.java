package com.xiaomi.security.miunifiedencrypt;

import android.util.Log;

/* JADX INFO: loaded from: classes4.dex */
public class toolClass {
    private static final String TAG = "unifiedEncryptSDK/toolClass";

    public static String toHexString(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            sb.append(String.format("%02x", Byte.valueOf(b)));
        }
        return sb.toString();
    }

    public static byte[] toBytes(String str) {
        if (str == null) {
            Log.d(TAG, "hexString null");
            return null;
        }
        String lowerCase = str.toLowerCase();
        byte[] bArr = new byte[lowerCase.length() >> 1];
        int i = 0;
        for (int i2 = 0; i2 < lowerCase.length() && i <= lowerCase.length() - 1; i2++) {
            bArr[i2] = (byte) ((((byte) (Character.digit(lowerCase.charAt(i), 16) & 255)) << 4) | ((byte) (Character.digit(lowerCase.charAt(i + 1), 16) & 255)));
            i += 2;
        }
        return bArr;
    }
}
