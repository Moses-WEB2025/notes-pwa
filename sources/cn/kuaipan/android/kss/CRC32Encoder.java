package cn.kuaipan.android.kss;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.CRC32;

/* JADX INFO: loaded from: classes.dex */
public class CRC32Encoder {
    private final CRC32 crc32 = new CRC32();
    private final byte[] CRC_BUF = new byte[8192];

    public int getCRC(InputStream inputStream, long j) throws IOException {
        this.crc32.reset();
        while (j > 0) {
            byte[] bArr = this.CRC_BUF;
            int i = inputStream.read(bArr, 0, (int) Math.min(bArr.length, j));
            if (i < 0) {
                break;
            }
            j -= (long) i;
            this.crc32.update(this.CRC_BUF, 0, i);
        }
        return (int) this.crc32.getValue();
    }
}
