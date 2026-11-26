package org.jsoup.internal;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Supplier;
import org.jsoup.helper.Validate;

/* JADX INFO: loaded from: classes6.dex */
class SimpleBufferedInput extends FilterInputStream {
    static final SoftPool<byte[]> BufferPool = new SoftPool<>(new Supplier() { // from class: org.jsoup.internal.SimpleBufferedInput$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final Object get() {
            return SimpleBufferedInput.lambda$static$0();
        }
    });
    static final int BufferSize = 8192;
    private int bufLength;
    private int bufMark;
    private int bufPos;
    private byte[] byteBuf;
    private boolean inReadFully;

    static /* synthetic */ byte[] lambda$static$0() {
        return new byte[8192];
    }

    SimpleBufferedInput(InputStream inputStream) {
        super(inputStream);
        this.bufMark = -1;
        this.inReadFully = false;
        if (inputStream == null) {
            this.inReadFully = true;
        }
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        if (this.bufPos >= this.bufLength) {
            fill();
            if (this.bufPos >= this.bufLength) {
                return -1;
            }
        }
        byte[] buf = getBuf();
        int i = this.bufPos;
        this.bufPos = i + 1;
        return buf[i] & 255;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        Validate.notNull(bArr);
        if (i < 0 || i2 < 0 || i2 > bArr.length - i) {
            throw new IndexOutOfBoundsException();
        }
        if (i2 == 0) {
            return 0;
        }
        int i3 = this.bufLength - this.bufPos;
        if (i3 <= 0) {
            if (!this.inReadFully && this.bufMark < 0) {
                int i4 = this.in.read(bArr, i, i2);
                closeIfDone(i4);
                return i4;
            }
            fill();
            i3 = this.bufLength - this.bufPos;
        }
        int iMin = Math.min(i3, i2);
        if (iMin <= 0) {
            return -1;
        }
        System.arraycopy(getBuf(), this.bufPos, bArr, i, iMin);
        this.bufPos += iMin;
        return iMin;
    }

    private void fill() throws IOException {
        if (this.inReadFully) {
            return;
        }
        if (this.byteBuf == null) {
            this.byteBuf = BufferPool.borrow();
        }
        int i = this.bufMark;
        if (i < 0) {
            this.bufPos = 0;
        } else {
            int i2 = this.bufPos;
            if (i2 >= 8192) {
                if (i > 0) {
                    int i3 = i2 - i;
                    byte[] bArr = this.byteBuf;
                    System.arraycopy(bArr, i, bArr, 0, i3);
                    this.bufPos = i3;
                    this.bufMark = 0;
                } else {
                    this.bufMark = -1;
                    this.bufPos = 0;
                }
            }
        }
        this.bufLength = this.bufPos;
        InputStream inputStream = this.in;
        byte[] bArr2 = this.byteBuf;
        int i4 = this.bufPos;
        int i5 = inputStream.read(bArr2, i4, bArr2.length - i4);
        if (i5 > 0) {
            this.bufLength = this.bufPos + i5;
            while (this.byteBuf.length - this.bufLength > 0 && this.in.available() >= 1) {
                InputStream inputStream2 = this.in;
                byte[] bArr3 = this.byteBuf;
                int i6 = this.bufLength;
                i5 = inputStream2.read(bArr3, i6, bArr3.length - i6);
                if (i5 <= 0) {
                    break;
                } else {
                    this.bufLength += i5;
                }
            }
        }
        closeIfDone(i5);
    }

    private void closeIfDone(int i) throws IOException {
        if (i == -1) {
            this.inReadFully = true;
            super.close();
        }
    }

    byte[] getBuf() {
        Validate.notNull(this.byteBuf);
        return this.byteBuf;
    }

    boolean baseReadFully() {
        return this.inReadFully;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int available() throws IOException {
        if (this.byteBuf != null) {
            int i = this.bufLength;
            int i2 = this.bufPos;
            if (i - i2 > 0) {
                return i - i2;
            }
        }
        if (this.inReadFully) {
            return 0;
        }
        return this.in.available();
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public void mark(int i) {
        if (i > 8192) {
            throw new IllegalArgumentException("Read-ahead limit is greater than buffer size");
        }
        this.bufMark = this.bufPos;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public void reset() throws IOException {
        int i = this.bufMark;
        if (i < 0) {
            throw new IOException("Resetting to invalid mark");
        }
        this.bufPos = i;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.in != null) {
            super.close();
        }
        byte[] bArr = this.byteBuf;
        if (bArr == null) {
            return;
        }
        BufferPool.release(bArr);
        this.byteBuf = null;
    }
}
