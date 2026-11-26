package androidx.test.platform.graphics;

import android.os.Build;
import android.util.Log;
import androidx.test.internal.platform.reflect.ReflectionException;
import androidx.test.internal.platform.reflect.ReflectiveMethod;

/* JADX INFO: loaded from: classes.dex */
public class HardwareRendererCompat {
    private static final String TAG = "HardwareRendererCompat";
    private static final ReflectiveMethod<Boolean> isDrawingEnabledReflectiveCall = new ReflectiveMethod<>("android.graphics.HardwareRenderer", "isDrawingEnabled", (Class<?>[]) new Class[0]);
    private static final ReflectiveMethod<Void> setDrawingEnabledReflectiveCall = new ReflectiveMethod<>("android.graphics.HardwareRenderer", "setDrawingEnabled", (Class<?>[]) new Class[]{Boolean.TYPE});

    private HardwareRendererCompat() {
    }

    public static boolean isDrawingEnabled() {
        if (Build.VERSION.SDK_INT < 30) {
            return true;
        }
        try {
            return isDrawingEnabledReflectiveCall.invokeStatic(new Object[0]).booleanValue();
        } catch (ReflectionException unused) {
            Log.d(TAG, "Failed to reflectively call HardwareRenderer#isDrawingEnabled. It probably doesn't exist on this platform. Returning true.");
            return true;
        }
    }

    public static void setDrawingEnabled(boolean renderingEnabled) {
        if (Build.VERSION.SDK_INT < 30) {
            return;
        }
        try {
            setDrawingEnabledReflectiveCall.invokeStatic(Boolean.valueOf(renderingEnabled));
        } catch (ReflectionException unused) {
            Log.i(TAG, "Failed to reflectively call HardwareRenderer#setDrawingEnabled.  It probably doesn't exist on this platform. Ignoring.");
        }
    }
}
