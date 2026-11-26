package cn.kuaipan.android.kss.upload;

import androidx.collection.SieveCacheKt;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.entity.InputStreamEntity;

/* JADX INFO: loaded from: classes.dex */
public class KssInputStreamEntity extends InputStreamEntity {
    private static final int OUTPUT_BUFFER_SIZE = 8192;
    private boolean consumed;
    private final InputStream content;
    private final long length;

    public KssInputStreamEntity(InputStream inputStream, long j) {
        super(inputStream, j);
        this.consumed = false;
        inputStream.mark((int) Math.min(SieveCacheKt.NodeLinkMask, j));
        this.content = inputStream;
        this.length = j;
    }

    @Override // org.apache.http.entity.InputStreamEntity, org.apache.http.HttpEntity
    public boolean isRepeatable() {
        return super.isRepeatable() || this.content.markSupported();
    }

    @Override // org.apache.http.entity.InputStreamEntity, org.apache.http.HttpEntity
    public InputStream getContent() throws IOException {
        this.content.reset();
        return super.getContent();
    }

    @Override // org.apache.http.entity.InputStreamEntity, org.apache.http.HttpEntity
    public void writeTo(OutputStream outputStream) throws IOException {
        int i;
        this.content.reset();
        if (outputStream == null) {
            throw new IllegalArgumentException("Output stream may not be null");
        }
        InputStream inputStream = this.content;
        byte[] bArr = new byte[8192];
        long j = this.length;
        if (j < 0) {
            while (true) {
                int i2 = inputStream.read(bArr);
                if (i2 == -1) {
                    break;
                } else {
                    outputStream.write(bArr, 0, i2);
                }
            }
        } else {
            while (j > 0 && (i = inputStream.read(bArr, 0, (int) Math.min(8192L, j))) != -1) {
                outputStream.write(bArr, 0, i);
                j -= (long) i;
            }
        }
        this.consumed = true;
    }

    @Override // org.apache.http.entity.InputStreamEntity, org.apache.http.HttpEntity
    public boolean isStreaming() {
        return !this.consumed;
    }

    @Override // org.apache.http.entity.InputStreamEntity, org.apache.http.entity.AbstractHttpEntity, org.apache.http.HttpEntity
    public void consumeContent() throws IOException {
        this.consumed = true;
        this.content.close();
    }
}
