package com.android.soundrecorder.ai.base.aop;

import android.content.Context;
import android.util.Log;
import androidx.core.content.ContextCompat;
import ch.qos.logback.core.CoreConstants;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: BaseInterceptor.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\n\u0010\t\u001a\u0004\u0018\u00010\nH\u0016J\n\u0010\u000b\u001a\u0004\u0018\u00010\u0001H\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u000fH\u0016J\b\u0010\u0014\u001a\u00020\u0012H\u0016R$\u0010\u0003\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0000\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\u0015"}, d2 = {"Lcom/android/soundrecorder/ai/base/aop/BaseInterceptor;", "", "()V", "next", "Ljava/lang/Class;", "getNext", "()Ljava/lang/Class;", "setNext", "(Ljava/lang/Class;)V", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "interceptReturnValue", "logd", "", "msg", "", "loge", "permissionIsNotGranted", "", "permission", "process", "base_debug"}, k = 1, mv = {1, 8, 0}, xi = 48)
public class BaseInterceptor {
    private Class<? extends BaseInterceptor> next;

    public Context context() {
        return null;
    }

    public Object interceptReturnValue() {
        return null;
    }

    public boolean process() {
        return false;
    }

    public final Class<? extends BaseInterceptor> getNext() {
        return this.next;
    }

    public final void setNext(Class<? extends BaseInterceptor> cls) {
        this.next = cls;
    }

    public void logd(String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        Log.d("AIRecorder", msg);
    }

    public void loge(String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        Log.e("AIRecorder", msg);
    }

    public boolean permissionIsNotGranted(String permission) {
        Intrinsics.checkNotNullParameter(permission, "permission");
        if (context() == null) {
            return true;
        }
        Context context = context();
        Intrinsics.checkNotNull(context);
        if (ContextCompat.checkSelfPermission(context, permission) == 0) {
            return false;
        }
        loge("permission: " + permission + ", not granted yet!");
        return true;
    }
}
