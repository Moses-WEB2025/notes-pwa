package cn.kuaipan.android.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.HttpEntity;
import org.apache.http.entity.HttpEntityWrapper;

/* JADX INFO: loaded from: classes.dex */
public class KscBufferedHttpEntity extends HttpEntityWrapper {
    private final RandomInputBuffer buffer;
    private final IOException err;

    @Override // org.apache.http.entity.HttpEntityWrapper, org.apache.http.HttpEntity
    public boolean isRepeatable() {
        return true;
    }

    public KscBufferedHttpEntity(HttpEntity httpEntity, NetCacheManager netCacheManager) {
        super(httpEntity);
        RandomInputBuffer randomInputBuffer = null;
        IOException iOException = null;
        if (!httpEntity.isRepeatable() || httpEntity.getContentLength() < 0) {
            try {
                RandomInputBuffer randomInputBuffer2 = new RandomInputBuffer(httpEntity.getContent(), netCacheManager);
                e = null;
                randomInputBuffer = randomInputBuffer2;
            } catch (IOException e) {
                e = e;
            }
            this.buffer = randomInputBuffer;
            iOException = e;
        } else {
            this.buffer = null;
        }
        this.err = iOException;
    }

    @Override // org.apache.http.entity.HttpEntityWrapper, org.apache.http.HttpEntity
    public long getContentLength() {
        return this.wrappedEntity.getContentLength();
    }

    @Override // org.apache.http.entity.HttpEntityWrapper, org.apache.http.HttpEntity
    public InputStream getContent() throws IOException {
        if (this.buffer != null) {
            return new BufferInputStream(this.buffer);
        }
        IOException iOException = this.err;
        if (iOException == null) {
            return this.wrappedEntity.getContent();
        }
        throw iOException;
    }

    @Override // org.apache.http.entity.HttpEntityWrapper, org.apache.http.HttpEntity
    public boolean isChunked() {
        return this.buffer == null && this.wrappedEntity.isChunked();
    }

    @Override // org.apache.http.entity.HttpEntityWrapper, org.apache.http.HttpEntity
    public void writeTo(OutputStream outputStream) throws Throwable {
        InputStream content;
        if (outputStream == null) {
            throw new IllegalArgumentException("Output stream may not be null");
        }
        if (this.buffer != null) {
            try {
                content = getContent();
                try {
                    byte[] bArr = new byte[4096];
                    while (true) {
                        int i = content.read(bArr);
                        if (i == -1) {
                            break;
                        } else {
                            outputStream.write(bArr, 0, i);
                        }
                    }
                    if (content != null) {
                        content.close();
                    }
                } catch (Throwable th) {
                    th = th;
                    if (content != null) {
                        content.close();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                content = null;
            }
        } else {
            this.wrappedEntity.writeTo(outputStream);
        }
    }

    @Override // org.apache.http.entity.HttpEntityWrapper, org.apache.http.HttpEntity
    public boolean isStreaming() {
        return this.buffer == null && this.wrappedEntity.isStreaming();
    }

    @Override // org.apache.http.entity.HttpEntityWrapper, org.apache.http.HttpEntity
    public void consumeContent() throws IOException {
        RandomInputBuffer randomInputBuffer = this.buffer;
        if (randomInputBuffer != null) {
            randomInputBuffer.close();
        }
        super.consumeContent();
    }
}
