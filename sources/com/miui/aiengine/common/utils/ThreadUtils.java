package com.miui.aiengine.common.utils;

import android.os.Looper;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ThreadUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\u0004\u001a\u00020\u0005J\u0006\u0010\u0006\u001a\u00020\u0005¨\u0006\u0007"}, d2 = {"Lcom/miui/aiengine/common/utils/ThreadUtils;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "ensureMainThread", "", "ensureWorkerThread", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class ThreadUtils {
    public static final ThreadUtils INSTANCE = new ThreadUtils();

    private ThreadUtils() {
    }

    public final void ensureMainThread() {
        if (!Intrinsics.areEqual(Looper.myLooper(), Looper.getMainLooper())) {
            throw new IllegalArgumentException("You must call this method on the main thread".toString());
        }
    }

    public final void ensureWorkerThread() {
        if (Intrinsics.areEqual(Looper.myLooper(), Looper.getMainLooper())) {
            throw new IllegalArgumentException("You must call this method on the worker thread".toString());
        }
    }
}
