package okio.internal;

import androidx.exifinterface.media.ExifInterface;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okio.TypedOptions;

/* JADX INFO: renamed from: okio.internal.-BufferedSource, reason: invalid class name */
/* JADX INFO: compiled from: BufferedSource.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a,\u0010\u0000\u001a\u0004\u0018\u0001H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002*\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0005H\u0080\b¢\u0006\u0002\u0010\u0006¨\u0006\u0007"}, d2 = {"commonSelect", ExifInterface.GPS_DIRECTION_TRUE, "", "Lokio/BufferedSource;", "options", "Lokio/TypedOptions;", "(Lokio/BufferedSource;Lokio/TypedOptions;)Ljava/lang/Object;", "okio"}, k = 2, mv = {2, 1, 0}, xi = 48)
public final class BufferedSource {
    public static final <T> T commonSelect(okio.BufferedSource bufferedSource, TypedOptions<T> options) throws IOException {
        Intrinsics.checkNotNullParameter(bufferedSource, "<this>");
        Intrinsics.checkNotNullParameter(options, "options");
        int iSelect = bufferedSource.select(options.getOptions());
        if (iSelect == -1) {
            return null;
        }
        return options.get(iSelect);
    }
}
