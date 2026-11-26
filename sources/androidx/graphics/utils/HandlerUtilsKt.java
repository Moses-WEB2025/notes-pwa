package androidx.graphics.utils;

import android.os.Handler;
import android.os.SystemClock;
import com.xiaomi.verificationsdk.internal.Constants;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: HandlerUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0000¨\u0006\u0007"}, d2 = {"post", "", "Landroid/os/Handler;", Constants.VERIFY_SUCESS_TOKEN, "", "runnable", "Ljava/lang/Runnable;", "libXCEngine_release"}, k = 2, mv = {2, 2, 0}, xi = 48)
public final class HandlerUtilsKt {
    public static final void post(Handler handler, Object obj, Runnable runnable) {
        Intrinsics.checkNotNullParameter(handler, "<this>");
        Intrinsics.checkNotNullParameter(runnable, "runnable");
        handler.postAtTime(runnable, obj, SystemClock.uptimeMillis());
    }
}
