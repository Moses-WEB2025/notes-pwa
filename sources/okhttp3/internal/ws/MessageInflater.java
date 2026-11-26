package okhttp3.internal.ws;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.io.Closeable;
import java.io.IOException;
import java.util.zip.Inflater;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.navigation.SplitLayout;
import okio.Buffer;
import okio.InflaterSource;
import okio.Source;

/* JADX INFO: compiled from: MessageInflater.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0007J\b\u0010\u000f\u001a\u00020\rH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lokhttp3/internal/ws/MessageInflater;", "Ljava/io/Closeable;", "noContextTakeover", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Z)V", "deflatedBytes", "Lokio/Buffer;", "inflater", "Ljava/util/zip/Inflater;", "inflaterSource", "Lokio/InflaterSource;", "inflate", "", "buffer", SplitLayout.TAG_CLOSE, "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class MessageInflater implements Closeable {
    private final Buffer deflatedBytes;
    private final Inflater inflater;
    private final InflaterSource inflaterSource;
    private final boolean noContextTakeover;

    public MessageInflater(boolean z) {
        this.noContextTakeover = z;
        Buffer buffer = new Buffer();
        this.deflatedBytes = buffer;
        Inflater inflater = new Inflater(true);
        this.inflater = inflater;
        this.inflaterSource = new InflaterSource((Source) buffer, inflater);
    }

    public final void inflate(Buffer buffer) throws IOException {
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        if (this.deflatedBytes.size() != 0) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (this.noContextTakeover) {
            this.inflater.reset();
        }
        this.deflatedBytes.writeAll(buffer);
        this.deflatedBytes.writeInt(65535);
        long bytesRead = this.inflater.getBytesRead() + this.deflatedBytes.size();
        do {
            this.inflaterSource.readOrInflate(buffer, Long.MAX_VALUE);
            if (this.inflater.getBytesRead() >= bytesRead) {
                return;
            }
        } while (!this.inflater.finished());
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.inflaterSource.close();
    }
}
