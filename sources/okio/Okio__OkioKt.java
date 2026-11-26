package okio;

import androidx.exifinterface.media.ExifInterface;
import java.io.Closeable;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Okio.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000(\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0000\u001a\u00020\u0003*\u00020\u0004\u001a\r\u0010\u0005\u001a\u00020\u0004H\u0007¢\u0006\u0002\b\u0006\u001aN\u0010\u0007\u001a\u0002H\b\"\u0010\b\u0000\u0010\t*\n\u0018\u00010\nj\u0004\u0018\u0001`\u000b\"\u0004\b\u0001\u0010\b*\u0002H\t2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\b0\rH\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0002\u0010\u000e\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u000f"}, d2 = {"buffer", "Lokio/BufferedSource;", "Lokio/Source;", "Lokio/BufferedSink;", "Lokio/Sink;", "blackholeSink", "blackhole", "use", "R", ExifInterface.GPS_DIRECTION_TRUE, "Ljava/io/Closeable;", "Lokio/Closeable;", "block", "Lkotlin/Function1;", "(Ljava/io/Closeable;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "okio"}, k = 5, mv = {2, 1, 0}, xi = 48, xs = "okio/Okio")
final /* synthetic */ class Okio__OkioKt {
    public static final BufferedSource buffer(Source source) {
        Intrinsics.checkNotNullParameter(source, "<this>");
        return new RealBufferedSource(source);
    }

    public static final BufferedSink buffer(Sink sink) {
        Intrinsics.checkNotNullParameter(sink, "<this>");
        return new RealBufferedSink(sink);
    }

    public static final Sink blackhole() {
        return new BlackholeSink();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r3v2, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r3v4 */
    public static final <T extends Closeable, R> R use(T t, Function1<? super T, ? extends R> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        R th = null;
        try {
            R rInvoke = block.invoke(t);
            if (t != null) {
                try {
                    t.close();
                } catch (Throwable th2) {
                    th = th2;
                }
            }
            R r = th;
            th = rInvoke;
            th = r;
        } catch (Throwable th3) {
            th = th3;
            if (t != null) {
                try {
                    t.close();
                } catch (Throwable th4) {
                    ExceptionsKt.addSuppressed(th, th4);
                }
            }
        }
        if (th == 0) {
            return th;
        }
        throw th;
    }
}
