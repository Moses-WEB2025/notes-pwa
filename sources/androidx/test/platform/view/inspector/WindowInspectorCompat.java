package androidx.test.platform.view.inspector;

import android.view.View;
import android.view.inspector.WindowInspector;
import androidx.test.internal.platform.reflect.ReflectionException;
import androidx.test.internal.platform.reflect.ReflectiveField;
import androidx.test.internal.platform.reflect.ReflectiveMethod;
import androidx.test.internal.util.Checks;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class WindowInspectorCompat {
    private static final ReflectiveMethod<Object> getWindowManagerGlobalReflectiveCall = new ReflectiveMethod<>("android.view.WindowManagerGlobal", "getInstance", (Class<?>[]) new Class[0]);
    private static final ReflectiveField<List<View>> windowViewsReflectiveField = new ReflectiveField<>("android.view.WindowManagerGlobal", "mViews");

    public static class ViewRetrievalException extends Exception {
        ViewRetrievalException(Throwable cause) {
            super("failed to retrieve window views", cause);
        }
    }

    private WindowInspectorCompat() {
    }

    public static List<View> getGlobalWindowViews() throws ViewRetrievalException {
        Checks.checkMainThread();
        return WindowInspector.getGlobalWindowViews();
    }

    private static Object getWindowManager() throws ReflectionException {
        return getWindowManagerGlobalReflectiveCall.invokeStatic(new Object[0]);
    }

    private static List<View> getViews(Object windowManagerGlobal) throws ReflectionException {
        return new ArrayList(windowViewsReflectiveField.get(windowManagerGlobal));
    }
}
