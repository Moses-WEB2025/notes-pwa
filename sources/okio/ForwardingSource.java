package okio;

import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.io.IOException;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.internal.Intrinsics;
import miuix.navigation.SplitLayout;

/* JADX INFO: compiled from: ForwardingSource.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0004\b\u0003\u0010\u0004J\u0018\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0007H\u0016J\b\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\r\u0010\u0002\u001a\u00020\u0001H\u0007¢\u0006\u0002\b\u0011R\u0013\u0010\u0002\u001a\u00020\u00018G¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0005¨\u0006\u0012"}, d2 = {"Lokio/ForwardingSource;", "Lokio/Source;", "delegate", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lokio/Source;)V", "()Lokio/Source;", "read", "", "sink", "Lokio/Buffer;", "byteCount", "timeout", "Lokio/Timeout;", SplitLayout.TAG_CLOSE, "", "toString", "", "-deprecated_delegate", "okio"}, k = 1, mv = {2, 1, 0}, xi = 48)
public abstract class ForwardingSource implements Source {
    private final Source delegate;

    public ForwardingSource(Source delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        this.delegate = delegate;
    }

    public final Source delegate() {
        return this.delegate;
    }

    @Override // okio.Source
    public long read(Buffer sink, long byteCount) throws IOException {
        Intrinsics.checkNotNullParameter(sink, "sink");
        return this.delegate.read(sink, byteCount);
    }

    @Override // okio.Source
    /* JADX INFO: renamed from: timeout */
    public Timeout getTimeout() {
        return this.delegate.getTimeout();
    }

    @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.delegate.close();
    }

    public String toString() {
        return getClass().getSimpleName() + CoreConstants.LEFT_PARENTHESIS_CHAR + this.delegate + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "delegate", imports = {}))
    /* JADX INFO: renamed from: -deprecated_delegate, reason: not valid java name and from getter */
    public final Source getDelegate() {
        return this.delegate;
    }
}
