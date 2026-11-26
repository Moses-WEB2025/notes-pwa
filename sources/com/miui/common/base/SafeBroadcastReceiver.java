package com.miui.common.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.exifinterface.media.ExifInterface;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SafeBroadcastReceiver.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u0011\u0012\b\u0010\u0003\u001a\u0004\u0018\u00018\u0000¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\f\u001a\u00020\rH\u0004J\u0006\u0010\u0011\u001a\u00020\u0012J\u001c\u0010\u0013\u001a\u00020\u00122\b\u0010\u0014\u001a\u0004\u0018\u00010\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016R\"\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00018\u00000\u0007X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u0016\u0010\u000e\u001a\u0004\u0018\u00018\u00008DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0018"}, d2 = {"Lcom/miui/common/base/SafeBroadcastReceiver;", ExifInterface.GPS_DIRECTION_TRUE, "Landroid/content/BroadcastReceiver;", "object", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/Object;)V", "mWkObject", "Ljava/lang/ref/WeakReference;", "getMWkObject", "()Ljava/lang/ref/WeakReference;", "setMWkObject", "(Ljava/lang/ref/WeakReference;)V", "hasReference", "", "wkObject", "getWkObject", "()Ljava/lang/Object;", "clear", "", "onReceive", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class SafeBroadcastReceiver<T> extends BroadcastReceiver {
    private WeakReference<T> mWkObject;

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
    }

    public SafeBroadcastReceiver(T t) {
        this.mWkObject = new WeakReference<>(t);
    }

    protected final WeakReference<T> getMWkObject() {
        return this.mWkObject;
    }

    protected final void setMWkObject(WeakReference<T> weakReference) {
        Intrinsics.checkNotNullParameter(weakReference, "<set-?>");
        this.mWkObject = weakReference;
    }

    protected final boolean hasReference() {
        return getWkObject() != null;
    }

    protected final T getWkObject() {
        return this.mWkObject.get();
    }

    public final void clear() {
        this.mWkObject.clear();
    }
}
