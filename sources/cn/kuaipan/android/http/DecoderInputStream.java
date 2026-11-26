package cn.kuaipan.android.http;

import cn.kuaipan.android.utils.RandomInputStream;
import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.util.zip.DataFormatException;

/* JADX INFO: loaded from: classes.dex */
public class DecoderInputStream extends FilterInputStream {
    private static final int BUF_SIZE = 512;
    private final byte[] buf;
    private boolean closed;
    private final IKscDecoder decoder;
    private boolean eof;
    private long markPos;
    private final long startPos;

    public DecoderInputStream(InputStream inputStream, IKscDecoder iKscDecoder) {
        this(inputStream, iKscDecoder, 512);
    }

    public DecoderInputStream(InputStream inputStream, IKscDecoder iKscDecoder, int i) {
        super(inputStream);
        this.markPos = -1L;
        if (inputStream == null || iKscDecoder == null) {
            throw null;
        }
        if (i <= 0) {
            throw new IllegalArgumentException();
        }
        this.decoder = iKscDecoder;
        this.buf = new byte[i];
        if (this.in instanceof RandomInputStream) {
            this.startPos = ((RandomInputStream) this.in).getCurrentPos();
        } else {
            this.startPos = 0L;
        }
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        byte[] bArr = new byte[1];
        if (read(bArr, 0, 1) == -1) {
            return -1;
        }
        return bArr[0] & 255;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (this.closed) {
            throw new IOException("Stream is closed");
        }
        if (bArr == null) {
            throw null;
        }
        if (i < 0 || i2 < 0 || i + i2 > bArr.length) {
            throw new IndexOutOfBoundsException();
        }
        if (i2 == 0) {
            return 0;
        }
        if (i > bArr.length || i2 < 0 || i < 0 || bArr.length - i < i2) {
            throw new ArrayIndexOutOfBoundsException();
        }
        try {
            int andDecode = readAndDecode(bArr, i, i2);
            if (andDecode > 0) {
                return andDecode;
            }
            if (this.eof && this.decoder.canEnd()) {
                return -1;
            }
            if (this.eof) {
                throw new EOFException();
            }
            throw ((IOException) new IOException().initCause(new DataFormatException("Failed read data from decoder.")));
        } catch (IOException e) {
            if (this.eof) {
                throw new EOFException();
            }
            throw e;
        }
    }

    private int readAndDecode(byte[] bArr, int i, int i2) throws IOException {
        if (this.closed) {
            throw new IOException("Stream is closed");
        }
        int i3 = 0;
        if (this.eof) {
            return 0;
        }
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedIOException();
        }
        int i4 = i;
        int i5 = i2;
        while (i5 > 0) {
            i3 = this.in.read(bArr, i4, i5);
            if (i3 < 0) {
                break;
            }
            i4 += i3;
            i5 -= i3;
        }
        if (i5 < i2) {
            this.decoder.decodeData(bArr, i, i2 - i5);
        }
        if (i3 < 0) {
            this.eof = true;
        }
        return i2 - i5;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public long skip(long j) throws IOException {
        long j2 = 0;
        if (j < 0) {
            throw new IllegalArgumentException();
        }
        while (j2 < j) {
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedIOException();
            }
            byte[] bArr = this.buf;
            long j3 = j - j2;
            int i = read(bArr, 0, j3 > ((long) bArr.length) ? bArr.length : (int) j3);
            if (i == -1) {
                break;
            }
            j2 += (long) i;
        }
        return j2;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int available() throws IOException {
        if (this.closed) {
            throw new IOException("Stream is closed");
        }
        return this.eof ? 0 : 1;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.closed) {
            return;
        }
        this.closed = true;
        this.eof = true;
        super.close();
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public void mark(int i) {
        if (this.in instanceof RandomInputStream) {
            this.markPos = ((RandomInputStream) this.in).getCurrentPos();
        }
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public void reset() throws IOException {
        if (!markSupported()) {
            throw new IOException();
        }
        if (this.markPos >= 0) {
            if (((RandomInputStream) this.in).getCurrentPos() > this.markPos) {
                this.eof = false;
            }
            ((RandomInputStream) this.in).moveToPos(this.markPos);
            this.decoder.init();
            this.decoder.skip(this.markPos - this.startPos);
        }
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public boolean markSupported() {
        return (this.in instanceof RandomInputStream) && this.decoder.supportRepeat();
    }
}
