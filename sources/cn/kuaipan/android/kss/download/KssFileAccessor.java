package cn.kuaipan.android.kss.download;

import cn.kuaipan.android.utils.Encode;
import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.concurrent.locks.ReentrantLock;
import miui.cloud.common.XLogger;

/* JADX INFO: loaded from: classes.dex */
public class KssFileAccessor implements KssAccessor {
    private static final int MAX_RETRY_COUNT = 2;
    private static final long RETRY_DELAY = 200;
    private RandomAccessFile mFileAccessor;
    private FileLock mFileLocker;
    private boolean mClosed = false;
    private final ReentrantLock mLocker = new ReentrantLock();

    public KssFileAccessor(File file) throws InterruptedException, IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rws");
        this.mFileAccessor = randomAccessFile;
        FileLock fileLockTryLockByRetry = tryLockByRetry(randomAccessFile.getChannel());
        this.mFileLocker = fileLockTryLockByRetry;
        if (fileLockTryLockByRetry == null) {
            throw new IOException("Failed Lock the target file: " + file);
        }
    }

    private FileLock tryLockByRetry(FileChannel fileChannel) throws InterruptedException, IOException {
        for (int i = 0; i < 2; i++) {
            try {
                return fileChannel.tryLock();
            } catch (OverlappingFileLockException unused) {
                if (i != 1) {
                    XLogger.logi("tryLock fail, retry after 200ms");
                    Thread.sleep(RETRY_DELAY);
                }
            }
        }
        return null;
    }

    @Override // cn.kuaipan.android.kss.download.KssAccessor
    public void lock() {
        this.mLocker.lock();
    }

    @Override // cn.kuaipan.android.kss.download.KssAccessor
    public void unlock() {
        this.mLocker.unlock();
    }

    @Override // cn.kuaipan.android.kss.download.KssAccessor
    public String sha1(long j, long j2) throws IOException {
        if (this.mClosed) {
            throw new IOException();
        }
        lock();
        try {
            return Encode.SHA1Encode(this.mFileAccessor, j, j2);
        } finally {
            unlock();
        }
    }

    @Override // cn.kuaipan.android.kss.download.KssAccessor
    public int write(byte[] bArr, int i, int i2, LoadRecorder loadRecorder) throws IOException {
        if (this.mClosed) {
            throw new IOException();
        }
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedIOException();
        }
        lock();
        if (loadRecorder != null) {
            try {
                this.mFileAccessor.seek(loadRecorder.getStart());
                i2 = (int) Math.min(i2, loadRecorder.size());
            } finally {
                unlock();
            }
        }
        this.mFileAccessor.write(bArr, i, i2);
        if (loadRecorder != null) {
            loadRecorder.add(i2);
        }
        return i2;
    }

    @Override // cn.kuaipan.android.kss.download.KssAccessor
    public void inflate(long j) throws IOException {
        if (this.mClosed) {
            throw new IOException();
        }
        lock();
        try {
            this.mFileAccessor.seek(j - 1);
            this.mFileAccessor.write(0);
        } finally {
            unlock();
        }
    }

    @Override // cn.kuaipan.android.kss.download.KssAccessor
    public void close() throws IOException {
        if (this.mClosed) {
            return;
        }
        this.mClosed = true;
        try {
            FileLock fileLock = this.mFileLocker;
            if (fileLock != null) {
                fileLock.release();
                this.mFileLocker = null;
            }
            try {
                if (this.mFileAccessor != null) {
                }
            } catch (IOException unused) {
            }
        } finally {
            try {
                RandomAccessFile randomAccessFile = this.mFileAccessor;
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                    this.mFileAccessor = null;
                }
            } catch (IOException unused2) {
            }
        }
    }

    protected void finalize() throws Throwable {
        try {
            close();
        } finally {
            super.finalize();
        }
    }
}
