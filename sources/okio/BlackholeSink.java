package okio;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.io.EOFException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.navigation.SplitLayout;

/* JADX INFO: compiled from: Okio.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\b\u0010\n\u001a\u00020\u0005H\u0016J\b\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\u0005H\u0016¨\u0006\u000e"}, d2 = {"Lokio/BlackholeSink;", "Lokio/Sink;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "write", "", "source", "Lokio/Buffer;", "byteCount", "", "flush", "timeout", "Lokio/Timeout;", SplitLayout.TAG_CLOSE, "okio"}, k = 1, mv = {2, 1, 0}, xi = 48)
final class BlackholeSink implements Sink {
    @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
    }

    @Override // okio.Sink, java.io.Flushable
    public void flush() {
    }

    @Override // okio.Sink
    public void write(Buffer source, long byteCount) throws EOFException {
        Intrinsics.checkNotNullParameter(source, "source");
        source.skip(byteCount);
    }

    @Override // okio.Sink
    /* JADX INFO: renamed from: timeout */
    public Timeout getTimeout() {
        return Timeout.NONE;
    }
}
