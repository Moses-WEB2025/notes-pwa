package org.jsoup.internal;

import java.io.BufferedInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import org.jsoup.Progress;
import org.jsoup.helper.Validate;

/* JADX INFO: loaded from: classes6.dex */
public class ControllableInputStream extends FilterInputStream {
    private boolean allowClose;
    private final SimpleBufferedInput buff;
    private int contentLength;
    private boolean interrupted;
    private int markPos;
    private int maxSize;
    private Progress<?> progress;
    private Object progressContext;
    private int readPos;
    private int remaining;
    private long startTime;
    private long timeout;

    private ControllableInputStream(SimpleBufferedInput simpleBufferedInput, int i) {
        super(simpleBufferedInput);
        this.timeout = 0L;
        this.allowClose = true;
        this.contentLength = -1;
        this.readPos = 0;
        Validate.isTrue(i >= 0);
        this.buff = simpleBufferedInput;
        this.maxSize = i;
        this.remaining = i;
        this.markPos = -1;
        this.startTime = System.nanoTime();
    }

    public static ControllableInputStream wrap(InputStream inputStream, int i) {
        if (inputStream instanceof ControllableInputStream) {
            return (ControllableInputStream) inputStream;
        }
        return new ControllableInputStream(new SimpleBufferedInput(inputStream), i);
    }

    public static ControllableInputStream wrap(InputStream inputStream, int i, int i2) {
        return wrap(inputStream, i2);
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        int i3;
        if (this.readPos == 0) {
            emitProgress();
        }
        boolean z = this.maxSize != 0;
        if (this.interrupted || (z && this.remaining <= 0)) {
            return -1;
        }
        if (Thread.currentThread().isInterrupted()) {
            this.interrupted = true;
            return -1;
        }
        if (z && i2 > (i3 = this.remaining)) {
            i2 = i3;
        }
        while (!expired()) {
            try {
                int i4 = super.read(bArr, i, i2);
                if (i4 == -1) {
                    this.contentLength = this.readPos;
                } else {
                    this.remaining -= i4;
                    this.readPos += i4;
                }
                emitProgress();
                return i4;
            } catch (SocketTimeoutException e) {
                if (expired() || this.timeout == 0) {
                    throw e;
                }
            }
        }
        throw new SocketTimeoutException("Read timeout");
    }

    public static ByteBuffer readToByteBuffer(InputStream inputStream, int i) throws IOException {
        int iMin;
        Validate.isTrue(i >= 0, "maxSize must be 0 (unlimited) or larger");
        Validate.notNull(inputStream);
        boolean z = i > 0;
        byte[] bArrBorrow = SimpleBufferedInput.BufferPool.borrow();
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(z ? Math.min(i, 8192) : 8192);
        while (true) {
            if (z) {
                try {
                    iMin = Math.min(i, 8192);
                } catch (Throwable th) {
                    SimpleBufferedInput.BufferPool.release(bArrBorrow);
                    throw th;
                }
            } else {
                iMin = 8192;
            }
            int i2 = inputStream.read(bArrBorrow, 0, iMin);
            if (i2 != -1) {
                if (byteBufferAllocate.remaining() < i2) {
                    ByteBuffer byteBufferAllocate2 = ByteBuffer.allocate((int) Math.max(((double) byteBufferAllocate.capacity()) * 1.5d, byteBufferAllocate.capacity() + i2));
                    byteBufferAllocate.flip();
                    byteBufferAllocate2.put(byteBufferAllocate);
                    byteBufferAllocate = byteBufferAllocate2;
                }
                byteBufferAllocate.put(bArrBorrow, 0, i2);
                if (z && (i = i - i2) <= 0) {
                    break;
                }
            } else {
                break;
            }
        }
        byteBufferAllocate.flip();
        SimpleBufferedInput.BufferPool.release(bArrBorrow);
        return byteBufferAllocate;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public void reset() throws IOException {
        super.reset();
        int i = this.maxSize;
        int i2 = this.markPos;
        this.remaining = i - i2;
        this.readPos = i2;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public void mark(int i) {
        super.mark(i);
        this.markPos = this.maxSize - this.remaining;
    }

    public boolean baseReadFully() {
        return this.buff.baseReadFully();
    }

    public int max() {
        return this.maxSize;
    }

    public void max(int i) {
        this.remaining += i - this.maxSize;
        this.maxSize = i;
    }

    public void allowClose(boolean z) {
        this.allowClose = z;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.allowClose) {
            super.close();
        }
    }

    public ControllableInputStream timeout(long j, long j2) {
        this.startTime = j;
        this.timeout = j2 * 1000000;
        return this;
    }

    private void emitProgress() {
        if (this.progress == null) {
            return;
        }
        int i = this.contentLength;
        float fMin = i > 0 ? Math.min(100.0f, (this.readPos * 100.0f) / i) : 0.0f;
        this.progress.onProgress(this.readPos, this.contentLength, fMin, this.progressContext);
        if (fMin == 100.0f) {
            this.progress = null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <ProgressContext> ControllableInputStream onProgress(int i, Progress<ProgressContext> progress, ProgressContext progresscontext) {
        Validate.notNull(progress);
        Validate.notNull(progresscontext);
        this.contentLength = i;
        this.progress = progress;
        this.progressContext = progresscontext;
        return this;
    }

    private boolean expired() {
        return this.timeout != 0 && System.nanoTime() - this.startTime > this.timeout;
    }

    public BufferedInputStream inputStream() {
        return new BufferedInputStream(this.buff);
    }
}
