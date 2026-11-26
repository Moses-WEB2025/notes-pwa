package okhttp3.internal.http2;

import androidx.collection.SieveCacheKt;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import miuix.navigation.SplitLayout;
import okhttp3.internal._UtilCommonKt;
import okhttp3.internal._UtilJvmKt;
import okhttp3.internal.concurrent.Lockable;
import okio.Buffer;
import okio.BufferedSink;

/* JADX INFO: compiled from: Http2Writer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\t\u0018\u0000 <2\u00020\u00012\u00020\u0002:\u0001<B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0004\b\u0007\u0010\bJ\u0006\u0010\u0012\u001a\u00020\u0013J\u000e\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u0016J$\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\f2\u0006\u0010\u0019\u001a\u00020\f2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001bJ\u0006\u0010\u001d\u001a\u00020\u0013J\u0016\u0010\u001e\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\f2\u0006\u0010\u001f\u001a\u00020 J\u0006\u0010!\u001a\u00020\fJ(\u0010\"\u001a\u00020\u00132\u0006\u0010#\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\f2\b\u0010$\u001a\u0004\u0018\u00010\n2\u0006\u0010%\u001a\u00020\fJ(\u0010&\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\f2\u0006\u0010'\u001a\u00020\f2\b\u0010(\u001a\u0004\u0018\u00010\n2\u0006\u0010%\u001a\u00020\fJ\u000e\u0010)\u001a\u00020\u00132\u0006\u0010)\u001a\u00020\u0016J\u001e\u0010*\u001a\u00020\u00132\u0006\u0010+\u001a\u00020\u00062\u0006\u0010,\u001a\u00020\f2\u0006\u0010-\u001a\u00020\fJ\u001e\u0010.\u001a\u00020\u00132\u0006\u0010/\u001a\u00020\f2\u0006\u0010\u001f\u001a\u00020 2\u0006\u00100\u001a\u000201J\u0016\u00102\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\f2\u0006\u00103\u001a\u000204J&\u00105\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\f2\u0006\u00106\u001a\u00020\f2\u0006\u00107\u001a\u00020\f2\u0006\u0010'\u001a\u00020\fJ\b\u00108\u001a\u00020\u0013H\u0016J\u0018\u00109\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\f2\u0006\u0010%\u001a\u000204H\u0002J$\u0010:\u001a\u00020\u00132\u0006\u0010#\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\f2\f\u0010;\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006="}, d2 = {"Lokhttp3/internal/http2/Http2Writer;", "Ljava/io/Closeable;", "Lokhttp3/internal/concurrent/Lockable;", "sink", "Lokio/BufferedSink;", "client", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lokio/BufferedSink;Z)V", "hpackBuffer", "Lokio/Buffer;", "maxFrameSize", "", "closed", "hpackWriter", "Lokhttp3/internal/http2/Hpack$Writer;", "getHpackWriter", "()Lokhttp3/internal/http2/Hpack$Writer;", "connectionPreface", "", "applyAndAckSettings", "peerSettings", "Lokhttp3/internal/http2/Settings;", "pushPromise", "streamId", "promisedStreamId", "requestHeaders", "", "Lokhttp3/internal/http2/Header;", "flush", "rstStream", "errorCode", "Lokhttp3/internal/http2/ErrorCode;", "maxDataLength", "data", "outFinished", "source", "byteCount", "dataFrame", "flags", "buffer", "settings", "ping", "ack", "payload1", "payload2", "goAway", "lastGoodStreamId", "debugData", "", "windowUpdate", "windowSizeIncrement", "", "frameHeader", "length", "type", SplitLayout.TAG_CLOSE, "writeContinuationFrames", "headers", "headerBlock", "Companion", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class Http2Writer implements Closeable, Lockable {
    private static final Logger logger = Logger.getLogger(Http2.class.getName());
    private final boolean client;
    private boolean closed;
    private final Buffer hpackBuffer;
    private final Hpack.Writer hpackWriter;
    private int maxFrameSize;
    private final BufferedSink sink;

    public Http2Writer(BufferedSink sink, boolean z) {
        Intrinsics.checkNotNullParameter(sink, "sink");
        this.sink = sink;
        this.client = z;
        Buffer buffer = new Buffer();
        this.hpackBuffer = buffer;
        this.maxFrameSize = 16384;
        this.hpackWriter = new Hpack.Writer(0, false, buffer, 3, null);
    }

    public final Hpack.Writer getHpackWriter() {
        return this.hpackWriter;
    }

    public final void connectionPreface() throws IOException {
        synchronized (this) {
            if (this.closed) {
                throw new IOException("closed");
            }
            if (this.client) {
                Logger logger2 = logger;
                if (logger2.isLoggable(Level.FINE)) {
                    logger2.fine(_UtilJvmKt.format(">> CONNECTION " + Http2.CONNECTION_PREFACE.hex(), new Object[0]));
                }
                this.sink.write(Http2.CONNECTION_PREFACE);
                this.sink.flush();
                Unit unit = Unit.INSTANCE;
            }
        }
    }

    public final void applyAndAckSettings(Settings peerSettings) throws IOException {
        Intrinsics.checkNotNullParameter(peerSettings, "peerSettings");
        synchronized (this) {
            if (this.closed) {
                throw new IOException("closed");
            }
            this.maxFrameSize = peerSettings.getMaxFrameSize(this.maxFrameSize);
            if (peerSettings.getHeaderTableSize() != -1) {
                this.hpackWriter.resizeHeaderTable(peerSettings.getHeaderTableSize());
            }
            frameHeader(0, 0, 4, 1);
            this.sink.flush();
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void pushPromise(int streamId, int promisedStreamId, List<Header> requestHeaders) throws IOException {
        Intrinsics.checkNotNullParameter(requestHeaders, "requestHeaders");
        synchronized (this) {
            if (this.closed) {
                throw new IOException("closed");
            }
            this.hpackWriter.writeHeaders(requestHeaders);
            long size = this.hpackBuffer.size();
            int iMin = (int) Math.min(((long) this.maxFrameSize) - 4, size);
            long j = iMin;
            frameHeader(streamId, iMin + 4, 5, size == j ? 4 : 0);
            this.sink.writeInt(promisedStreamId & Integer.MAX_VALUE);
            this.sink.write(this.hpackBuffer, j);
            if (size > j) {
                writeContinuationFrames(streamId, size - j);
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void flush() throws IOException {
        synchronized (this) {
            if (this.closed) {
                throw new IOException("closed");
            }
            this.sink.flush();
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void rstStream(int streamId, ErrorCode errorCode) throws IOException {
        Intrinsics.checkNotNullParameter(errorCode, "errorCode");
        synchronized (this) {
            if (this.closed) {
                throw new IOException("closed");
            }
            if (errorCode.getHttpCode() == -1) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            frameHeader(streamId, 4, 3, 0);
            this.sink.writeInt(errorCode.getHttpCode());
            this.sink.flush();
            Unit unit = Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: maxDataLength, reason: from getter */
    public final int getMaxFrameSize() {
        return this.maxFrameSize;
    }

    public final void data(boolean outFinished, int streamId, Buffer source, int byteCount) throws IOException {
        synchronized (this) {
            if (this.closed) {
                throw new IOException("closed");
            }
            dataFrame(streamId, outFinished ? 1 : 0, source, byteCount);
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void dataFrame(int streamId, int flags, Buffer buffer, int byteCount) throws IOException {
        frameHeader(streamId, byteCount, 0, flags);
        if (byteCount > 0) {
            BufferedSink bufferedSink = this.sink;
            Intrinsics.checkNotNull(buffer);
            bufferedSink.write(buffer, byteCount);
        }
    }

    public final void settings(Settings settings) throws IOException {
        Intrinsics.checkNotNullParameter(settings, "settings");
        synchronized (this) {
            if (this.closed) {
                throw new IOException("closed");
            }
            frameHeader(0, settings.size() * 6, 4, 0);
            for (int i = 0; i < 10; i++) {
                if (settings.isSet(i)) {
                    this.sink.writeShort(i);
                    this.sink.writeInt(settings.get(i));
                }
            }
            this.sink.flush();
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void ping(boolean ack, int payload1, int payload2) throws IOException {
        synchronized (this) {
            if (this.closed) {
                throw new IOException("closed");
            }
            frameHeader(0, 8, 6, ack ? 1 : 0);
            this.sink.writeInt(payload1);
            this.sink.writeInt(payload2);
            this.sink.flush();
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void goAway(int lastGoodStreamId, ErrorCode errorCode, byte[] debugData) throws IOException {
        Intrinsics.checkNotNullParameter(errorCode, "errorCode");
        Intrinsics.checkNotNullParameter(debugData, "debugData");
        synchronized (this) {
            if (this.closed) {
                throw new IOException("closed");
            }
            if (errorCode.getHttpCode() == -1) {
                throw new IllegalArgumentException("errorCode.httpCode == -1".toString());
            }
            frameHeader(0, debugData.length + 8, 7, 0);
            this.sink.writeInt(lastGoodStreamId);
            this.sink.writeInt(errorCode.getHttpCode());
            if (!(debugData.length == 0)) {
                this.sink.write(debugData);
            }
            this.sink.flush();
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void windowUpdate(int streamId, long windowSizeIncrement) throws IOException {
        int i;
        long j;
        synchronized (this) {
            if (this.closed) {
                throw new IOException("closed");
            }
            if (windowSizeIncrement == 0 || windowSizeIncrement > SieveCacheKt.NodeLinkMask) {
                throw new IllegalArgumentException(("windowSizeIncrement == 0 || windowSizeIncrement > 0x7fffffffL: " + windowSizeIncrement).toString());
            }
            Logger logger2 = logger;
            if (logger2.isLoggable(Level.FINE)) {
                i = streamId;
                j = windowSizeIncrement;
                logger2.fine(Http2.INSTANCE.frameLogWindowUpdate(false, i, 4, j));
            } else {
                i = streamId;
                j = windowSizeIncrement;
            }
            frameHeader(i, 4, 8, 0);
            this.sink.writeInt((int) j);
            this.sink.flush();
            Unit unit = Unit.INSTANCE;
        }
    }

    /* JADX WARN: Code duplicated, block: B:7:0x001d  */
    public final void frameHeader(int streamId, int length, int type, int flags) throws IOException {
        int i;
        int i2;
        int i3;
        int i4;
        if (type != 8) {
            Logger logger2 = logger;
            if (logger2.isLoggable(Level.FINE)) {
                i = streamId;
                i2 = length;
                i3 = type;
                i4 = flags;
                logger2.fine(Http2.INSTANCE.frameLog(false, i, i2, i3, i4));
            } else {
                i = streamId;
                i2 = length;
                i3 = type;
                i4 = flags;
            }
        } else {
            i = streamId;
            i2 = length;
            i3 = type;
            i4 = flags;
        }
        if (i2 > this.maxFrameSize) {
            throw new IllegalArgumentException(("FRAME_SIZE_ERROR length > " + this.maxFrameSize + ": " + i2).toString());
        }
        if ((Integer.MIN_VALUE & i) != 0) {
            throw new IllegalArgumentException(("reserved bit set: " + i).toString());
        }
        _UtilCommonKt.writeMedium(this.sink, i2);
        this.sink.writeByte(i3 & 255);
        this.sink.writeByte(i4 & 255);
        this.sink.writeInt(Integer.MAX_VALUE & i);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        synchronized (this) {
            this.closed = true;
            this.sink.close();
            Unit unit = Unit.INSTANCE;
        }
    }

    private final void writeContinuationFrames(int streamId, long byteCount) throws IOException {
        while (byteCount > 0) {
            long jMin = Math.min(this.maxFrameSize, byteCount);
            byteCount -= jMin;
            frameHeader(streamId, (int) jMin, 9, byteCount == 0 ? 4 : 0);
            this.sink.write(this.hpackBuffer, jMin);
        }
    }

    public final void headers(boolean outFinished, int streamId, List<Header> headerBlock) throws IOException {
        Intrinsics.checkNotNullParameter(headerBlock, "headerBlock");
        synchronized (this) {
            if (this.closed) {
                throw new IOException("closed");
            }
            this.hpackWriter.writeHeaders(headerBlock);
            long size = this.hpackBuffer.size();
            long jMin = Math.min(this.maxFrameSize, size);
            int i = size == jMin ? 4 : 0;
            if (outFinished) {
                i |= 1;
            }
            frameHeader(streamId, (int) jMin, 1, i);
            this.sink.write(this.hpackBuffer, jMin);
            if (size > jMin) {
                writeContinuationFrames(streamId, size - jMin);
            }
            Unit unit = Unit.INSTANCE;
        }
    }
}
