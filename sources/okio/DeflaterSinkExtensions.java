package okio;

import java.util.zip.Deflater;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: renamed from: okio.-DeflaterSinkExtensions, reason: invalid class name */
/* JADX INFO: compiled from: DeflaterSink.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a \u0010\u0000\u001a\u00020\u0001*\u00020\u00022\f\b\u0002\u0010\u0003\u001a\u00060\u0004j\u0002`\u0005H\u0086\b¢\u0006\u0002\u0010\u0006¨\u0006\u0007"}, d2 = {"deflate", "Lokio/DeflaterSink;", "Lokio/Sink;", "deflater", "Ljava/util/zip/Deflater;", "Lokio/Deflater;", "(Lokio/Sink;Ljava/util/zip/Deflater;)Lokio/DeflaterSink;", "okio"}, k = 2, mv = {2, 1, 0}, xi = 48)
public final class DeflaterSinkExtensions {
    public static /* synthetic */ DeflaterSink deflate$default(Sink sink, Deflater deflater, int i, Object obj) {
        if ((i & 1) != 0) {
            deflater = new Deflater();
        }
        Intrinsics.checkNotNullParameter(sink, "<this>");
        Intrinsics.checkNotNullParameter(deflater, "deflater");
        return new DeflaterSink(sink, deflater);
    }

    public static final DeflaterSink deflate(Sink sink, Deflater deflater) {
        Intrinsics.checkNotNullParameter(sink, "<this>");
        Intrinsics.checkNotNullParameter(deflater, "deflater");
        return new DeflaterSink(sink, deflater);
    }
}
