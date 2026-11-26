package com.miui.handwritecommon.api.util;

/* JADX INFO: compiled from: BitmapEx.java */
/* JADX INFO: loaded from: classes2.dex */
class ByteOrder {
    ByteOrder() {
    }

    public static void reverse(byte[] bArr) {
        int length = bArr.length;
        int i = length / 2;
        for (int i2 = 0; i2 < i; i2++) {
            byte b = bArr[i2];
            int i3 = (length - 1) - i2;
            bArr[i2] = bArr[i3];
            bArr[i3] = b;
        }
    }

    public static int reverse(int i) {
        byte[] bArr = {(byte) (i & 255), (byte) ((i >> 8) & 255), (byte) ((i >> 16) & 255), (byte) ((i >> 24) & 255)};
        return (bArr[3] & 255) + ((bArr[2] & 255) << 8) + ((bArr[1] & 255) << 16) + ((bArr[0] & 255) << 24);
    }

    public static long reverse(long j) {
        byte[] bArr = {(byte) (j & 255), (byte) ((j >> 8) & 255), (byte) ((j >> 16) & 255), (byte) ((j >> 24) & 255), (byte) ((j >> 32) & 255), (byte) ((j >> 40) & 255), (byte) ((j >> 48) & 255), (byte) ((j >> 56) & 255)};
        return ((long) (bArr[7] & 255)) + ((long) ((bArr[6] & 255) << 8)) + ((long) ((bArr[5] & 255) << 16)) + (((long) (bArr[4] & 255)) << 24) + (((long) (bArr[3] & 255)) << 32) + (((long) (bArr[2] & 255)) << 40) + (((long) (bArr[1] & 255)) << 48) + (((long) (bArr[0] & 255)) << 56);
    }

    public static short reverse(short s) {
        byte[] bArr = {(byte) (s & 255), (byte) ((s >> 8) & 255)};
        return (short) ((bArr[1] & 255) + ((bArr[0] & 255) << 8));
    }
}
