package okio;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import defpackage.R2;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.Deflater;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.internal.Intrinsics;
import miuix.navigation.SplitLayout;
import okio.internal._ZlibJvmKt;

/* JADX INFO: compiled from: GzipSink.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0004\b\u0003\u0010\u0004J\u0018\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u0014H\u0016J\b\u0010\u001a\u001a\u00020\u001bH\u0016J\b\u0010\u001c\u001a\u00020\u0014H\u0016J\b\u0010\u001d\u001a\u00020\u0014H\u0002J\u0018\u0010\u001e\u001a\u00020\u00142\u0006\u0010\u001f\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u0013\u0010\u0006\u001a\u00060\u0007j\u0002`\bH\u0007¢\u0006\u0004\b \u0010\tR\u000e\u0010\u0002\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0019\u0010\u0006\u001a\u00060\u0007j\u0002`\b8G¢\u0006\n\n\u0002\u0010\n\u001a\u0004\b\u0006\u0010\tR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\u00060\u0010j\u0002`\u0011X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0012¨\u0006!"}, d2 = {"Lokio/GzipSink;", "Lokio/Sink;", "sink", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lokio/Sink;)V", "Lokio/RealBufferedSink;", "deflater", "Ljava/util/zip/Deflater;", "Lokio/Deflater;", "()Ljava/util/zip/Deflater;", "Ljava/util/zip/Deflater;", "deflaterSink", "Lokio/DeflaterSink;", "closed", "", "crc", "Ljava/util/zip/CRC32;", "Lokio/internal/CRC32;", "Ljava/util/zip/CRC32;", "write", "", "source", "Lokio/Buffer;", "byteCount", "", "flush", "timeout", "Lokio/Timeout;", SplitLayout.TAG_CLOSE, "writeFooter", "updateCrc", "buffer", "-deprecated_deflater", "okio"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class GzipSink implements Sink {
    private boolean closed;
    private final CRC32 crc;
    private final Deflater deflater;
    private final DeflaterSink deflaterSink;
    private final RealBufferedSink sink;

    public GzipSink(Sink sink) {
        Intrinsics.checkNotNullParameter(sink, "sink");
        RealBufferedSink realBufferedSink = new RealBufferedSink(sink);
        this.sink = realBufferedSink;
        Deflater deflater = new Deflater(_ZlibJvmKt.getDEFAULT_COMPRESSION(), true);
        this.deflater = deflater;
        this.deflaterSink = new DeflaterSink((BufferedSink) realBufferedSink, deflater);
        this.crc = new CRC32();
        Buffer buffer = realBufferedSink.bufferField;
        buffer.writeShort(R2.dimen.miuix_appcompat_action_sheet_content_separate_item_margin_top);
        buffer.writeByte(8);
        buffer.writeByte(0);
        buffer.writeInt(0);
        buffer.writeByte(0);
        buffer.writeByte(0);
    }

    public final Deflater deflater() {
        return this.deflater;
    }

    @Override // okio.Sink
    public void write(Buffer source, long byteCount) throws IOException {
        Intrinsics.checkNotNullParameter(source, "source");
        if (byteCount < 0) {
            throw new IllegalArgumentException(("byteCount < 0: " + byteCount).toString());
        }
        if (byteCount == 0) {
            return;
        }
        updateCrc(source, byteCount);
        this.deflaterSink.write(source, byteCount);
    }

    @Override // okio.Sink, java.io.Flushable
    public void flush() throws IOException {
        this.deflaterSink.flush();
    }

    @Override // okio.Sink
    /* JADX INFO: renamed from: timeout */
    public Timeout getThis$0() {
        return this.sink.getThis$0();
    }

    @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws Throwable {
        if (this.closed) {
            return;
        }
        this.deflaterSink.finishDeflate$okio();
        writeFooter();
        th = null;
        try {
            this.deflater.end();
        } catch (Throwable th) {
            if (th == null) {
                th = th;
            }
        }
        try {
            this.sink.close();
        } catch (Throwable th2) {
            if (th == null) {
                th = th2;
            }
        }
        this.closed = true;
        if (th != null) {
            throw th;
        }
    }

    private final void writeFooter() {
        this.sink.writeIntLe((int) this.crc.getValue());
        this.sink.writeIntLe((int) this.deflater.getBytesRead());
    }

    private final void updateCrc(Buffer buffer, long byteCount) {
        Segment segment = buffer.head;
        Intrinsics.checkNotNull(segment);
        while (byteCount > 0) {
            int iMin = (int) Math.min(byteCount, segment.limit - segment.pos);
            this.crc.update(segment.data, segment.pos, iMin);
            byteCount -= (long) iMin;
            segment = segment.next;
            Intrinsics.checkNotNull(segment);
        }
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "deflater", imports = {}))
    /* JADX INFO: renamed from: -deprecated_deflater, reason: not valid java name and from getter */
    public final Deflater getDeflater() {
        return this.deflater;
    }
}
