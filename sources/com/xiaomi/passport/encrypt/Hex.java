package com.xiaomi.passport.encrypt;

/* JADX INFO: loaded from: classes4.dex */
public class Hex {
    private static final char[] HEX_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String get(byte[] bArr) {
        int length = bArr.length;
        StringBuilder sb = new StringBuilder(length << 1);
        for (int i = 0; i < length; i++) {
            char[] cArr = HEX_CHARS;
            sb.append(cArr[(bArr[i] & 240) >>> 4]);
            sb.append(cArr[bArr[i] & 15]);
        }
        return sb.toString();
    }
}
