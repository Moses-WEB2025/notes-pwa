package cn.kuaipan.android.http;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.RandomAccessFile;

/* JADX INFO: loaded from: classes.dex */
public class RandomInputBuffer {
    private static final int UNIT = 8192;
    private byte[] buf;
    private File bufFile;
    private final NetCacheManager cache;
    private boolean eof;
    private RandomAccessFile file;
    private boolean fileAssigned;
    private InputStream in;
    private int pos;

    public RandomInputBuffer(InputStream inputStream, NetCacheManager netCacheManager) {
        if (netCacheManager == null || inputStream == null) {
            throw new IllegalArgumentException("InputStream & NetCacheManager can not be null.");
        }
        this.in = inputStream;
        this.cache = netCacheManager;
        this.buf = new byte[8192];
        this.pos = 0;
        this.eof = false;
    }

    public synchronized int available(int i) throws IOException {
        int iAvailable;
        InputStream inputStream = this.in;
        if (inputStream == null) {
            throw new IOException("RandomInputBuffer has been closed.");
        }
        if (!this.eof) {
            iAvailable = inputStream.available() + this.pos;
        } else {
            iAvailable = this.pos;
        }
        return iAvailable - i;
    }

    public synchronized void close() throws IOException {
        InputStream inputStream = this.in;
        if (inputStream != null) {
            inputStream.close();
            this.in = null;
        }
        RandomAccessFile randomAccessFile = this.file;
        if (randomAccessFile != null) {
            randomAccessFile.close();
            this.file = null;
        }
        File file = this.bufFile;
        if (file != null) {
            this.cache.releaseCache(file);
            this.bufFile = null;
        }
        this.buf = null;
        this.pos = 0;
        this.eof = false;
    }

    public int read(int i) throws IOException {
        byte[] bArr = new byte[1];
        if (read(i, bArr) > 0) {
            return bArr[0];
        }
        return -1;
    }

    public int read(int i, byte[] bArr) throws IOException {
        return read(i, bArr, 0, bArr.length);
    }

    public synchronized int read(int i, byte[] bArr, int i2, int i3) throws IOException {
        if (this.in == null) {
            throw new IOException("RandomInputBuffer has been closed.");
        }
        if (bArr == null) {
            throw new NullPointerException("buffer == null");
        }
        if ((i2 | i3) < 0 || i2 > bArr.length - i3) {
            throw new IndexOutOfBoundsException();
        }
        if (i3 == 0) {
            return 0;
        }
        int i4 = i + i3;
        if (i4 > this.pos) {
            fillBuf(i4);
        }
        return readFromBuf(i, bArr, i2, i3);
    }

    private int readFromBuf(int i, byte[] bArr, int i2, int i3) throws IOException {
        if (i3 == 0) {
            return 0;
        }
        if (this.eof && i >= this.pos) {
            return -1;
        }
        int i4 = this.pos;
        if (i > i4) {
            throw new IndexOutOfBoundsException("from > pos");
        }
        if (i == i4) {
            return 0;
        }
        int iMin = Math.min(i4 - i, i3);
        RandomAccessFile randomAccessFile = this.file;
        if (randomAccessFile == null) {
            System.arraycopy(this.buf, i, bArr, i2, iMin);
            return iMin;
        }
        randomAccessFile.seek(i);
        return this.file.read(bArr, i2, iMin);
    }

    /* JADX WARN: Code duplicated, block: B:30:0x0053  */
    /* JADX WARN: Code duplicated, block: B:32:0x0058  */
    /* JADX WARN: Code duplicated, block: B:39:0x007b  */
    /* JADX WARN: Code duplicated, block: B:40:0x0081  */
    /* JADX WARN: Code duplicated, block: B:41:0x0084  */
    /* JADX WARN: Code duplicated, block: B:43:0x0096  */
    /* JADX WARN: Code duplicated, block: B:44:0x00ab  */
    private void fillBuf(int i) throws IOException {
        int i2;
        byte[] bArr;
        int i3;
        int length;
        if (this.eof || i <= this.pos) {
            return;
        }
        if (i % 8192 != 0) {
            i = ((i / 8192) * 8192) + 8192;
        }
        if (!this.fileAssigned && i > this.buf.length) {
            this.fileAssigned = true;
            try {
                try {
                    File fileAssignCache = this.cache.assignCache();
                    this.bufFile = fileAssignCache;
                    if (fileAssignCache != null) {
                        RandomAccessFile randomAccessFile = new RandomAccessFile(this.bufFile, "rw");
                        this.file = randomAccessFile;
                        randomAccessFile.write(this.buf, 0, this.pos);
                    }
                } catch (InterruptedIOException e) {
                    throw e;
                } catch (Exception unused) {
                    this.file = null;
                }
            } catch (InterruptedIOException e2) {
                throw e2;
            } catch (Exception unused2) {
                RandomAccessFile randomAccessFile2 = this.file;
                if (randomAccessFile2 != null) {
                    randomAccessFile2.close();
                }
                this.file = null;
                if (this.file == null) {
                    bArr = this.buf;
                    if (i > bArr.length) {
                        length = bArr.length;
                        do {
                            length *= 2;
                        } while (length < i);
                        byte[] bArr2 = new byte[length];
                        byte[] bArr3 = this.buf;
                        System.arraycopy(bArr3, 0, bArr2, 0, bArr3.length);
                        this.buf = bArr2;
                    }
                    InputStream inputStream = this.in;
                    byte[] bArr4 = this.buf;
                    int i4 = this.pos;
                    i3 = inputStream.read(bArr4, i4, Math.min(bArr4.length - i4, i - i4));
                    if (i3 >= 0) {
                        this.pos += i3;
                        return;
                    } else {
                        this.eof = true;
                        return;
                    }
                }
                InputStream inputStream2 = this.in;
                byte[] bArr5 = this.buf;
                i2 = inputStream2.read(bArr5, 0, Math.min(bArr5.length, i - this.pos));
                if (i2 >= 0) {
                    this.file.seek(this.pos);
                    this.file.write(this.buf, 0, i2);
                    this.pos += i2;
                    return;
                }
                this.eof = true;
            }
        }
        if (this.file == null) {
            bArr = this.buf;
            if (i > bArr.length) {
                length = bArr.length;
                do {
                    length *= 2;
                } while (length < i);
                byte[] bArr6 = new byte[length];
                byte[] bArr7 = this.buf;
                System.arraycopy(bArr7, 0, bArr6, 0, bArr7.length);
                this.buf = bArr6;
            }
            InputStream inputStream3 = this.in;
            byte[] bArr8 = this.buf;
            int i5 = this.pos;
            i3 = inputStream3.read(bArr8, i5, Math.min(bArr8.length - i5, i - i5));
            if (i3 >= 0) {
                this.pos += i3;
                return;
            } else {
                this.eof = true;
                return;
            }
        }
        InputStream inputStream4 = this.in;
        byte[] bArr9 = this.buf;
        i2 = inputStream4.read(bArr9, 0, Math.min(bArr9.length, i - this.pos));
        if (i2 >= 0) {
            this.file.seek(this.pos);
            this.file.write(this.buf, 0, i2);
            this.pos += i2;
            return;
        }
        this.eof = true;
    }
}
