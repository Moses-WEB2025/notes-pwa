package com.market.sdk.compat;

import android.content.pm.IPackageManager;
import com.market.sdk.utils.ReflectUtils;

/* JADX INFO: loaded from: classes2.dex */
public class ActivityThreadCompat {
    private static Class<?> sActivityThread = ReflectUtils.getClass("android.app.ActivityThread");

    public static IPackageManager getPackageManager() {
        try {
            Class<?> cls = ReflectUtils.getClass("android.content.pm.IPackageManager");
            Class<?> cls2 = sActivityThread;
            return (IPackageManager) ReflectUtils.invokeObject(cls2, cls2, "getPackageManager", ReflectUtils.getMethodSignature(cls, new Class[0]), new Object[0]);
        } catch (Exception unused) {
            return null;
        }
    }
}
