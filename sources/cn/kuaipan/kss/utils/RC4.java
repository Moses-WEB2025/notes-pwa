package cn.kuaipan.kss.utils;

import java.security.InvalidKeyException;

/* JADX INFO: loaded from: classes.dex */
public class RC4 {
    private final int[] sBox = new int[256];
    private int x;
    private int y;

    public void makeKey(byte[] bArr) throws InvalidKeyException {
        if (bArr == null) {
            throw new InvalidKeyException("Null user key");
        }
        int length = bArr.length;
        if (length == 0) {
            throw new InvalidKeyException("Invalid user key length");
        }
        this.x = 0;
        this.y = 0;
        for (int i = 0; i < 256; i++) {
            this.sBox[i] = i;
        }
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < 256; i4++) {
            int i5 = bArr[i2] & 255;
            int[] iArr = this.sBox;
            int i6 = iArr[i4];
            i3 = (i5 + i6 + i3) & 255;
            iArr[i4] = iArr[i3];
            iArr[i3] = i6;
            i2 = (i2 + 1) % length;
        }
    }

    public void genRC4(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws InvalidKeyException {
        int i4 = 0;
        while (i4 < i2) {
            int i5 = (this.x + 1) & 255;
            this.x = i5;
            int[] iArr = this.sBox;
            int i6 = iArr[i5];
            int i7 = (this.y + i6) & 255;
            this.y = i7;
            iArr[i5] = iArr[i7];
            iArr[i7] = i6;
            bArr2[i3] = (byte) (bArr[i] ^ iArr[(iArr[i5] + i6) & 255]);
            i4++;
            i3++;
            i++;
        }
    }
}
