package com.miui.notes.base.app;

import android.os.Handler;
import android.os.Looper;
import androidx.exifinterface.media.ExifInterface;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MainHandler.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0016\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B!\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0010\b\u0002\u0010\u0005\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0006¢\u0006\u0004\b\u0007\u0010\bR\u0019\u0010\u0005\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lcom/miui/notes/base/app/MainHandler;", ExifInterface.GPS_DIRECTION_TRUE, "Landroid/os/Handler;", "looper", "Landroid/os/Looper;", CoreConstants.CONTEXT_SCOPE_VALUE, "Ljava/lang/ref/WeakReference;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/os/Looper;Ljava/lang/ref/WeakReference;)V", "getContext", "()Ljava/lang/ref/WeakReference;", "common_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class MainHandler<T> extends Handler {
    private final WeakReference<T> context;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MainHandler(Looper looper, WeakReference<T> weakReference) {
        super(looper);
        Intrinsics.checkNotNullParameter(looper, "looper");
        this.context = weakReference;
    }

    public /* synthetic */ MainHandler(Looper looper, WeakReference weakReference, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(looper, (i & 2) != 0 ? null : weakReference);
    }

    public final WeakReference<T> getContext() {
        return this.context;
    }
}
