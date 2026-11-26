package com.miui.aiengine.common.utils;

import android.util.Log;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.synergy.CallMethod;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SystemPropertiesUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0014\u0010\u0006\u001a\u0004\u0018\u00010\u00052\b\u0010\u0007\u001a\u0004\u0018\u00010\u0005H\u0007J\u001e\u0010\u0006\u001a\u0004\u0018\u00010\u00052\b\u0010\u0007\u001a\u0004\u0018\u00010\u00052\b\u0010\b\u001a\u0004\u0018\u00010\u0005H\u0007J\u001a\u0010\t\u001a\u00020\n2\b\u0010\u0007\u001a\u0004\u0018\u00010\u00052\u0006\u0010\b\u001a\u00020\nH\u0007J\u001a\u0010\u000b\u001a\u00020\f2\b\u0010\u0007\u001a\u0004\u0018\u00010\u00052\u0006\u0010\b\u001a\u00020\fH\u0007J\u001a\u0010\r\u001a\u00020\u000e2\b\u0010\u0007\u001a\u0004\u0018\u00010\u00052\u0006\u0010\b\u001a\u00020\u000eH\u0007J\u001c\u0010\u000f\u001a\u00020\u00102\b\u0010\u0007\u001a\u0004\u0018\u00010\u00052\b\u0010\u0011\u001a\u0004\u0018\u00010\u0005H\u0007J\u0010\u0010\u0012\u001a\u00020\u00102\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lcom/miui/aiengine/common/utils/SystemPropertiesUtils;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", CallMethod.METHOD_GET, "key", "defaultValue", "getInt", "", "getLong", "", "getBoolean", "", "set", "", "value", "addChangeCallback", "runnable", "Ljava/lang/Runnable;", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class SystemPropertiesUtils {
    public static final SystemPropertiesUtils INSTANCE = new SystemPropertiesUtils();
    private static final String TAG = "SystemProperties";

    private SystemPropertiesUtils() {
    }

    public final String get(String key) {
        String str = null;
        try {
            Object objInvoke = Class.forName("android.os.SystemProperties").getMethod(CallMethod.METHOD_GET, String.class).invoke(null, key);
            Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type kotlin.String");
            String str2 = (String) objInvoke;
            try {
                Log.i(TAG, "value：" + str2);
                return str2;
            } catch (Exception e) {
                e = e;
                str = str2;
                Log.e(TAG, "read SystemProperties error", e);
                return str;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    public final String get(String key, String defaultValue) {
        try {
            Object objInvoke = Class.forName("android.os.SystemProperties").getMethod(CallMethod.METHOD_GET, String.class, String.class).invoke(null, key, defaultValue);
            Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type kotlin.String");
            return (String) objInvoke;
        } catch (Exception e) {
            Log.e(TAG, "read SystemProperties error", e);
            return null;
        }
    }

    public final int getInt(String key, int defaultValue) {
        try {
            Object objInvoke = Class.forName("android.os.SystemProperties").getMethod("getInt", String.class, Integer.TYPE).invoke(null, key, Integer.valueOf(defaultValue));
            Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type kotlin.Int");
            return ((Integer) objInvoke).intValue();
        } catch (Exception e) {
            Log.e(TAG, "read SystemProperties error", e);
            return 0;
        }
    }

    public final long getLong(String key, long defaultValue) {
        try {
            Object objInvoke = Class.forName("android.os.SystemProperties").getMethod("getLong", String.class, Long.TYPE).invoke(null, key, Long.valueOf(defaultValue));
            Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type kotlin.Long");
            return ((Long) objInvoke).longValue();
        } catch (Exception e) {
            Log.e(TAG, "read SystemProperties error", e);
            return 0L;
        }
    }

    public final boolean getBoolean(String key, boolean defaultValue) {
        try {
            Object objInvoke = Class.forName("android.os.SystemProperties").getMethod("getBoolean", String.class, Boolean.TYPE).invoke(null, key, Boolean.valueOf(defaultValue));
            Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type kotlin.Boolean");
            return ((Boolean) objInvoke).booleanValue();
        } catch (Exception e) {
            Log.e(TAG, "read SystemProperties error", e);
            return false;
        }
    }

    public final void set(String key, String value) {
        try {
            Class.forName("android.os.SystemProperties").getMethod("set", String.class, String.class).invoke(null, key, value);
        } catch (Exception e) {
            Log.e(TAG, "read SystemProperties error", e);
        }
    }

    public final void addChangeCallback(Runnable runnable) {
        try {
            Class.forName("android.os.SystemProperties").getMethod("addChangeCallback", Runnable.class).invoke(null, runnable);
        } catch (Exception e) {
            Log.e(TAG, "read SystemProperties error", e);
        }
    }
}
