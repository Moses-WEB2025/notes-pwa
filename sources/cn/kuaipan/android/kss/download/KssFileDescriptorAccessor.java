package cn.kuaipan.android.kss.download;

import cn.kuaipan.android.utils.Encode;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: loaded from: classes.dex */
public class KssFileDescriptorAccessor implements KssAccessor {
    private FileInputStream mFileInputStream;
    private FileLock mFileLocker;
    private FileOutputStream mFileOutputStream;
    private boolean mClosed = false;
    private final ReentrantLock mLocker = new ReentrantLock();

    @Override // cn.kuaipan.android.kss.download.KssAccessor
    public void inflate(long j) throws IOException {
    }

    public KssFileDescriptorAccessor(DownloadDescriptorFile downloadDescriptorFile) throws IOException {
        this.mFileInputStream = downloadDescriptorFile.openFileInputStream();
        FileOutputStream fileOutputStreamOpenFileOutputStream = downloadDescriptorFile.openFileOutputStream();
        this.mFileOutputStream = fileOutputStreamOpenFileOutputStream;
        this.mFileLocker = fileOutputStreamOpenFileOutputStream.getChannel().tryLock();
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
            return Encode.SHA1Encode(this.mFileInputStream.getChannel(), j, j2);
        } finally {
            unlock();
        }
    }

    @Override // cn.kuaipan.android.kss.download.KssAccessor
    public int write(byte[] bArr, int i, int i2, LoadRecorder loadRecorder) throws IOException {
        long start;
        if (this.mClosed) {
            throw new IOException();
        }
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedIOException();
        }
        lock();
        if (loadRecorder != null) {
            try {
                start = loadRecorder.getStart();
                i2 = (int) Math.min(i2, loadRecorder.size());
            } finally {
                unlock();
            }
        } else {
            start = 0;
        }
        FileChannel channel = this.mFileOutputStream.getChannel();
        channel.position(start);
        channel.write(ByteBuffer.wrap(bArr, i, i2));
        if (loadRecorder != null) {
            loadRecorder.add(i2);
        }
        return i2;
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
                FileOutputStream fileOutputStream = this.mFileOutputStream;
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                    this.mFileOutputStream = null;
                }
                if (this.mFileInputStream != null) {
                }
            } catch (IOException unused) {
            }
        } finally {
            try {
                FileOutputStream fileOutputStream2 = this.mFileOutputStream;
                if (fileOutputStream2 != null) {
                    fileOutputStream2.close();
                    this.mFileOutputStream = null;
                }
                FileInputStream fileInputStream = this.mFileInputStream;
                if (fileInputStream != null) {
                    fileInputStream.close();
                    this.mFileInputStream = null;
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
