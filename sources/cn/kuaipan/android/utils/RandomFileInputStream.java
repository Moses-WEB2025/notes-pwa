package cn.kuaipan.android.utils;

import com.xiaomi.verificationsdk.internal.Constants;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.RandomAccessFile;

/* JADX INFO: loaded from: classes.dex */
public class RandomFileInputStream extends RandomInputStream {
    private final File file;
    private RandomAccessFile mRFile;
    private long mPos = 0;
    private long mMarkedPos = 0;
    private boolean mClosed = false;

    @Override // java.io.InputStream
    public boolean markSupported() {
        return true;
    }

    public RandomFileInputStream(File file) throws FileNotFoundException {
        this.file = file;
        this.mRFile = new RandomAccessFile(file, Constants.RANDOM_LONG);
    }

    @Override // java.io.InputStream
    public synchronized int read() throws IOException {
        int i;
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedIOException();
        }
        i = this.mRFile.read();
        if (i >= 0) {
            this.mPos++;
        }
        return i;
    }

    @Override // java.io.InputStream
    public synchronized int available() throws IOException {
        return (int) (this.mRFile.length() - this.mPos);
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public synchronized void close() throws IOException {
        this.mRFile.close();
        this.mClosed = true;
    }

    @Override // cn.kuaipan.android.utils.RandomInputStream
    public synchronized void moveToPos(long j) throws IOException {
        if (j < this.mRFile.length()) {
            this.mRFile.seek(j);
            this.mPos = j;
        } else {
            throw new IOException("Seek position is not available");
        }
    }

    @Override // cn.kuaipan.android.utils.RandomInputStream
    public synchronized long getCurrentPos() {
        return this.mPos;
    }

    @Override // java.io.InputStream
    public void mark(int i) {
        if (this.mClosed) {
            return;
        }
        this.mMarkedPos = this.mPos;
    }

    @Override // java.io.InputStream
    public synchronized int read(byte[] bArr) throws IOException {
        int i;
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedIOException();
        }
        i = this.mRFile.read(bArr);
        if (i > 0) {
            this.mPos += (long) i;
        }
        return i;
    }

    @Override // java.io.InputStream
    public synchronized int read(byte[] bArr, int i, int i2) throws IOException {
        int i3;
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedIOException();
        }
        i3 = this.mRFile.read(bArr, i, i2);
        if (i3 > 0) {
            this.mPos += (long) i3;
        }
        return i3;
    }

    @Override // java.io.InputStream
    public synchronized void reset() throws IOException {
        if (this.mClosed) {
            this.mClosed = false;
            this.mRFile = new RandomAccessFile(this.file, Constants.RANDOM_LONG);
        }
        this.mRFile.seek(this.mMarkedPos);
        this.mPos = this.mMarkedPos;
    }

    @Override // java.io.InputStream
    public long skip(long j) throws IOException {
        if (j == 0) {
            return 0L;
        }
        if (j < 0) {
            throw new IOException("Number of bytes to skip cannot be negative");
        }
        int i = j < 8192 ? (int) j : 8192;
        synchronized (this) {
            long j2 = i;
            long j3 = 0;
            for (long j4 = j / j2; j4 >= 0; j4--) {
                if (Thread.currentThread().isInterrupted()) {
                    throw new InterruptedIOException();
                }
                long jSkipBytes = this.mRFile.skipBytes(i);
                j3 += jSkipBytes;
                this.mPos += jSkipBytes;
                if (jSkipBytes < j2) {
                    return j3;
                }
            }
            return j3;
        }
    }
}
