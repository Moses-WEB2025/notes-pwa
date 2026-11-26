package okio;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.io.IOException;
import javax.crypto.Cipher;
import javax.crypto.ShortBufferException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.navigation.SplitLayout;

/* JADX INFO: compiled from: CipherSink.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0000\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0018\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u0013H\u0002J\b\u0010\u0016\u001a\u00020\u000fH\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u000fH\u0016J\n\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lokio/CipherSink;", "Lokio/Sink;", "sink", "Lokio/BufferedSink;", "cipher", "Ljavax/crypto/Cipher;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lokio/BufferedSink;Ljavax/crypto/Cipher;)V", "getCipher", "()Ljavax/crypto/Cipher;", "blockSize", "", "closed", "", "write", "", "source", "Lokio/Buffer;", "byteCount", "", "update", "remaining", "flush", "timeout", "Lokio/Timeout;", SplitLayout.TAG_CLOSE, "doFinal", "", "okio"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class CipherSink implements Sink {
    private final int blockSize;
    private final Cipher cipher;
    private boolean closed;
    private final BufferedSink sink;

    public CipherSink(BufferedSink sink, Cipher cipher) {
        Intrinsics.checkNotNullParameter(sink, "sink");
        Intrinsics.checkNotNullParameter(cipher, "cipher");
        this.sink = sink;
        this.cipher = cipher;
        int blockSize = cipher.getBlockSize();
        this.blockSize = blockSize;
        if (blockSize <= 0) {
            throw new IllegalArgumentException(("Block cipher required " + cipher).toString());
        }
    }

    public final Cipher getCipher() {
        return this.cipher;
    }

    @Override // okio.Sink
    public void write(Buffer source, long byteCount) throws IOException {
        Intrinsics.checkNotNullParameter(source, "source");
        SegmentedByteString.checkOffsetAndCount(source.size(), 0L, byteCount);
        if (this.closed) {
            throw new IllegalStateException("closed".toString());
        }
        long jUpdate = byteCount;
        while (jUpdate > 0) {
            jUpdate -= (long) update(source, jUpdate);
        }
    }

    private final int update(Buffer source, long remaining) throws IOException, ShortBufferException {
        Segment segment = source.head;
        Intrinsics.checkNotNull(segment);
        int iMin = (int) Math.min(remaining, segment.limit - segment.pos);
        Buffer buffer = this.sink.getBuffer();
        int outputSize = this.cipher.getOutputSize(iMin);
        int i = iMin;
        while (outputSize > 8192) {
            int i2 = this.blockSize;
            if (i <= i2) {
                BufferedSink bufferedSink = this.sink;
                byte[] bArrUpdate = this.cipher.update(source.readByteArray(remaining));
                Intrinsics.checkNotNullExpressionValue(bArrUpdate, "update(...)");
                bufferedSink.write(bArrUpdate);
                return (int) remaining;
            }
            i -= i2;
            outputSize = this.cipher.getOutputSize(i);
        }
        Segment segmentWritableSegment$okio = buffer.writableSegment$okio(outputSize);
        int iUpdate = this.cipher.update(segment.data, segment.pos, i, segmentWritableSegment$okio.data, segmentWritableSegment$okio.limit);
        segmentWritableSegment$okio.limit += iUpdate;
        buffer.setSize$okio(buffer.size() + ((long) iUpdate));
        if (segmentWritableSegment$okio.pos == segmentWritableSegment$okio.limit) {
            buffer.head = segmentWritableSegment$okio.pop();
            SegmentPool.recycle(segmentWritableSegment$okio);
        }
        this.sink.emitCompleteSegments();
        source.setSize$okio(source.size() - ((long) i));
        segment.pos += i;
        if (segment.pos == segment.limit) {
            source.head = segment.pop();
            SegmentPool.recycle(segment);
        }
        return i;
    }

    @Override // okio.Sink, java.io.Flushable
    public void flush() throws IOException {
        this.sink.flush();
    }

    @Override // okio.Sink
    /* JADX INFO: renamed from: timeout */
    public Timeout getTimeout() {
        return this.sink.getTimeout();
    }

    @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws Throwable {
        if (this.closed) {
            return;
        }
        this.closed = true;
        Throwable thDoFinal = doFinal();
        try {
            this.sink.close();
        } catch (Throwable th) {
            if (thDoFinal == null) {
                thDoFinal = th;
            }
        }
        if (thDoFinal != null) {
            throw thDoFinal;
        }
    }

    private final Throwable doFinal() {
        int outputSize = this.cipher.getOutputSize(0);
        Throwable th = null;
        if (outputSize == 0) {
            return null;
        }
        if (outputSize > 8192) {
            try {
                BufferedSink bufferedSink = this.sink;
                byte[] bArrDoFinal = this.cipher.doFinal();
                Intrinsics.checkNotNullExpressionValue(bArrDoFinal, "doFinal(...)");
                bufferedSink.write(bArrDoFinal);
                return null;
            } catch (Throwable th2) {
                return th2;
            }
        }
        Buffer buffer = this.sink.getBuffer();
        Segment segmentWritableSegment$okio = buffer.writableSegment$okio(outputSize);
        try {
            int iDoFinal = this.cipher.doFinal(segmentWritableSegment$okio.data, segmentWritableSegment$okio.limit);
            segmentWritableSegment$okio.limit += iDoFinal;
            buffer.setSize$okio(buffer.size() + ((long) iDoFinal));
        } catch (Throwable th3) {
            th = th3;
        }
        if (segmentWritableSegment$okio.pos == segmentWritableSegment$okio.limit) {
            buffer.head = segmentWritableSegment$okio.pop();
            SegmentPool.recycle(segmentWritableSegment$okio);
        }
        return th;
    }
}
