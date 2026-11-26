package okio;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: renamed from: okio.-DeprecatedUtf8, reason: invalid class name */
/* JADX INFO: compiled from: -DeprecatedUtf8.kt */
/* JADX INFO: loaded from: classes6.dex */
@Deprecated(message = "changed in Okio 2.x")
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0007J \u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH\u0007¨\u0006\u000b"}, d2 = {"Lokio/-DeprecatedUtf8;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "size", "", TypedValues.Custom.S_STRING, "", "beginIndex", "", "endIndex", "okio"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class DeprecatedUtf8 {
    public static final DeprecatedUtf8 INSTANCE = new DeprecatedUtf8();

    private DeprecatedUtf8() {
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "string.utf8Size()", imports = {"okio.utf8Size"}))
    public final long size(String string) {
        Intrinsics.checkNotNullParameter(string, "string");
        return Utf8.size$default(string, 0, 0, 3, null);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "string.utf8Size(beginIndex, endIndex)", imports = {"okio.utf8Size"}))
    public final long size(String string, int beginIndex, int endIndex) {
        Intrinsics.checkNotNullParameter(string, "string");
        return Utf8.size(string, beginIndex, endIndex);
    }
}
