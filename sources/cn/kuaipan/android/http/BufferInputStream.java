package cn.kuaipan.android.http;

import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
public class BufferInputStream extends InputStream {
    private static final byte[] tempBuf = new byte[8192];
    private RandomInputBuffer buf;
    private int markpos = -1;
    private int pos;

    @Override // java.io.InputStream
    public boolean markSupported() {
        return true;
    }

    public BufferInputStream(RandomInputBuffer randomInputBuffer) {
        this.buf = randomInputBuffer;
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        RandomInputBuffer randomInputBuffer = this.buf;
        if (randomInputBuffer == null) {
            throw streamClosed();
        }
        return randomInputBuffer.available(this.pos);
    }

    private IOException streamClosed() throws IOException {
        throw new IOException("BufferedInputStream is closed");
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.buf = null;
    }

    @Override // java.io.InputStream
    public synchronized void mark(int i) {
        this.markpos = this.pos;
    }

    @Override // java.io.InputStream
    public synchronized int read() throws IOException {
        int i;
        RandomInputBuffer randomInputBuffer = this.buf;
        if (randomInputBuffer == null) {
            throw streamClosed();
        }
        i = randomInputBuffer.read(this.pos);
        if (i >= 0) {
            this.pos++;
        }
        return i;
    }

    @Override // java.io.InputStream
    public synchronized int read(byte[] bArr, int i, int i2) throws IOException {
        int i3;
        RandomInputBuffer randomInputBuffer = this.buf;
        if (randomInputBuffer == null) {
            throw streamClosed();
        }
        i3 = randomInputBuffer.read(this.pos, bArr, i, i2);
        if (i3 > 0) {
            this.pos += i3;
        }
        return i3;
    }

    @Override // java.io.InputStream
    public synchronized void reset() throws IOException {
        if (this.buf == null) {
            throw new IOException("Stream is closed");
        }
        int i = this.markpos;
        if (-1 == i) {
            throw new IOException("Mark has been invalidated.");
        }
        this.pos = i;
    }

    @Override // java.io.InputStream
    public synchronized long skip(long j) throws IOException {
        if (j < 1) {
            return 0L;
        }
        RandomInputBuffer randomInputBuffer = this.buf;
        if (randomInputBuffer == null) {
            throw streamClosed();
        }
        long j2 = ((long) this.pos) + j;
        int i = 0;
        while (true) {
            int i2 = this.pos;
            if (j2 <= i2) {
                break;
            }
            byte[] bArr = tempBuf;
            int i3 = randomInputBuffer.read(i2, bArr, 0, (int) Math.min(bArr.length, j2 - ((long) i2)));
            if (i3 < 0) {
                break;
            }
            this.pos += i3;
            i += i3;
        }
        return i;
    }
}
