package com.xiaomi.e2ee.io;

import java.io.IOException;
import java.io.InputStream;
import javax.crypto.Cipher;

/* JADX INFO: loaded from: classes4.dex */
public class E2EEInputStream extends InputStream {
    private final Cipher cipher;
    private final InputStream content;
    private boolean eof;

    public E2EEInputStream(InputStream inputStream, Cipher cipher) {
        this.content = inputStream;
        this.cipher = cipher;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        byte[] bArr = new byte[1];
        if (read(bArr, 0, 1) == -1) {
            return -1;
        }
        return bArr[0] & 255;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (this.eof) {
            return -1;
        }
        return readAndDecode(bArr, i, i2);
    }

    private int readAndDecode(byte[] bArr, int i, int i2) throws IOException {
        int i3 = i2;
        int i4 = 0;
        while (i3 > 0) {
            i4 = this.content.read(bArr, i, i3);
            if (i4 < 0) {
                break;
            }
            i += i4;
            i3 -= i4;
        }
        if (i3 < i2) {
            byte[] bArrUpdate = this.cipher.update(bArr);
            System.arraycopy(bArrUpdate, 0, bArr, 0, bArrUpdate.length);
        }
        if (i4 < 0) {
            this.eof = true;
        }
        return i2 - i3;
    }
}
