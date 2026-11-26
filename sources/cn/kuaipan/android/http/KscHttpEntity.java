package cn.kuaipan.android.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.HttpEntity;
import org.apache.http.entity.HttpEntityWrapper;

/* JADX INFO: loaded from: classes.dex */
public class KscHttpEntity extends HttpEntityWrapper {
    private static final int MAX_BYTE_CONTENT_LENGTH = 8192;
    private final IKscDecoder mDecoder;

    public KscHttpEntity(HttpEntity httpEntity, IKscDecoder iKscDecoder) {
        super(httpEntity);
        this.mDecoder = iKscDecoder;
    }

    @Override // org.apache.http.entity.HttpEntityWrapper, org.apache.http.HttpEntity
    public InputStream getContent() throws IOException {
        if (this.mDecoder == null) {
            return super.getContent();
        }
        return new DecoderInputStream(super.getContent(), this.mDecoder, 8192);
    }

    @Override // org.apache.http.entity.HttpEntityWrapper, org.apache.http.HttpEntity
    public void writeTo(OutputStream outputStream) throws IOException {
        if (outputStream == null) {
            throw new IllegalArgumentException("Output stream may not be null");
        }
        InputStream content = getContent();
        byte[] bArr = new byte[8192];
        while (true) {
            int i = content.read(bArr);
            if (i == -1) {
                return;
            } else {
                outputStream.write(bArr, 0, i);
            }
        }
    }

    public static KscHttpEntity getRepeatableEntity(HttpEntity httpEntity, IKscDecoder iKscDecoder, NetCacheManager netCacheManager) {
        if (httpEntity == null) {
            return null;
        }
        return new KscHttpEntity(getRepeatable(httpEntity, netCacheManager), iKscDecoder);
    }

    private static HttpEntity getRepeatable(HttpEntity httpEntity, NetCacheManager netCacheManager) {
        return (httpEntity == null || httpEntity.isRepeatable()) ? httpEntity : new KscBufferedHttpEntity(httpEntity, netCacheManager);
    }
}
