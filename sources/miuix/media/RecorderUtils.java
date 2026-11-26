package miuix.media;

import java.nio.MappedByteBuffer;

/* JADX INFO: loaded from: classes5.dex */
class RecorderUtils {
    public static int convertErrorCode(int i, boolean z) {
        if (z) {
            if (i == 100) {
                return 1010;
            }
            if (i != 800) {
                return i != 801 ? 999 : 1007;
            }
            return 1008;
        }
        if (i == 1007) {
            return 1007;
        }
        switch (i) {
            case 1:
                return 1001;
            case 2:
                return 1002;
            case 3:
                return 1003;
            case 4:
                return 1004;
            case 5:
                return 1005;
            case 6:
                return 1006;
            case 7:
                return 1007;
            case 8:
                return 1009;
            default:
                return 999;
        }
    }

    RecorderUtils() {
    }

    public static byte[] getWaveHeader(int i, int i2) {
        int i3 = i == 16 ? 1 : 2;
        long j = ((i2 * i3) * 16) / 8;
        return new byte[]{82, 73, 70, 70, 0, 0, 0, 0, 87, 65, 86, 69, 102, 109, 116, 32, 16, 0, 0, 0, 1, 0, (byte) i3, 0, (byte) (i2 & 255), (byte) ((i2 >> 8) & 255), (byte) ((i2 >> 16) & 255), (byte) ((i2 >> 24) & 255), (byte) (j & 255), (byte) ((j >> 8) & 255), (byte) ((j >> 16) & 255), (byte) ((j >> 24) & 255), (byte) ((i3 * 16) / 8), 0, 16, 0, 100, 97, 116, 97, 0, 0, 0, 0};
    }

    public static void updateBytes(MappedByteBuffer mappedByteBuffer, int i, byte[] bArr) {
        for (int i2 = 0; i2 < 4; i2++) {
            mappedByteBuffer.put(i + i2, bArr[i2]);
        }
    }

    public static byte[] getBytes(long j) {
        return new byte[]{(byte) (j & 255), (byte) ((j >> 8) & 255), (byte) ((j >> 16) & 255), (byte) ((j >> 24) & 255)};
    }

    public static byte[] short2byte(short[] sArr, int i) {
        byte[] bArr = new byte[i * 2];
        for (int i2 = 0; i2 < i; i2++) {
            int i3 = i2 * 2;
            short s = sArr[i2];
            bArr[i3] = (byte) (s & 255);
            bArr[i3 + 1] = (byte) (s >> 8);
            sArr[i2] = 0;
        }
        return bArr;
    }
}
