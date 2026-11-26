package com.miui.notes.ai.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Build;
import com.miui.notes.base.utils.Logger;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class FreeModeUtils {
    private static final String TAG = "FreeModeUtils";

    public static Rect isHostInFreeMode(int i) {
        try {
            Method method = Class.forName("miui.app.MiuiFreeFormManager").getMethod("getFreeFormStackInfoByStackId", Integer.TYPE);
            method.setAccessible(true);
            Object objInvoke = method.invoke(null, Integer.valueOf(i));
            if (objInvoke == null) {
                return null;
            }
            Class<?> cls = Class.forName("miui.app.MiuiFreeFormManager$MiuiFreeFormStackInfo");
            Field declaredField = cls.getDeclaredField("packageName");
            declaredField.setAccessible(true);
            Field declaredField2 = cls.getDeclaredField("bounds");
            declaredField2.setAccessible(true);
            Rect rect = (Rect) declaredField2.get(objInvoke);
            Field declaredField3 = cls.getDeclaredField("freeFormScale");
            declaredField3.setAccessible(true);
            return scaleBounds(rect, declaredField3.getFloat(objInvoke));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<String> getAllPackageInFreeMode(Context context) {
        try {
            Method method = Class.forName("miui.app.MiuiFreeFormManager").getMethod("getAllFreeFormStackInfosOnDisplay", Integer.TYPE);
            method.setAccessible(true);
            Object objInvoke = method.invoke(null, Integer.valueOf((Build.VERSION.SDK_INT < 30 || context.getDisplay() == null) ? 0 : context.getDisplay().getDisplayId()));
            if (objInvoke == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            if (objInvoke instanceof List) {
                Class<?> cls = Class.forName("miui.app.MiuiFreeFormManager$MiuiFreeFormStackInfo");
                List list = (List) objInvoke;
                if (!list.isEmpty()) {
                    for (int i = 0; i < list.size(); i++) {
                        Object obj = list.get(i);
                        Field declaredField = cls.getDeclaredField("packageName");
                        declaredField.setAccessible(true);
                        String str = (String) declaredField.get(obj);
                        Logger.INSTANCE.i(TAG, " in free mode package is " + str);
                        arrayList.add(str);
                    }
                }
            }
            return arrayList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int getRotationFreeMode(int i) {
        try {
            Method method = Class.forName("miui.app.MiuiFreeFormManager").getMethod("getFreeFormStackInfoByStackId", Integer.TYPE);
            method.setAccessible(true);
            Object objInvoke = method.invoke(null, Integer.valueOf(i));
            if (objInvoke == null) {
                Logger.INSTANCE.i(TAG, "checkHostFreeMode get null");
                return -1;
            }
            Field declaredField = Class.forName("miui.app.MiuiFreeFormManager$MiuiFreeFormStackInfo").getDeclaredField("configuration");
            declaredField.setAccessible(true);
            Configuration configuration = (Configuration) declaredField.get(objInvoke);
            Field declaredField2 = configuration.getClass().getDeclaredField("windowConfiguration");
            declaredField2.setAccessible(true);
            Object obj = declaredField2.get(configuration);
            Field declaredField3 = obj.getClass().getDeclaredField("mRotation");
            declaredField3.setAccessible(true);
            int iIntValue = ((Integer) declaredField3.get(obj)).intValue();
            Logger.INSTANCE.i(TAG, " now rotate is " + iIntValue);
            return iIntValue;
        } catch (Exception e) {
            Logger.INSTANCE.i(TAG, "checkHostFreeMode exception");
            e.printStackTrace();
            return -1;
        }
    }

    public static Rect scaleBounds(Rect rect, float f) {
        Rect rect2 = new Rect(rect);
        int i = rect.left;
        int i2 = rect.top;
        rect2.set(i, i2, ((int) (rect.width() * f)) + i, ((int) (f * rect.height())) + i2);
        return rect2;
    }

    public static boolean isFreeFormScreen(Configuration configuration) {
        return configuration != null && configuration.toString().contains("mWindowingMode=freeform");
    }
}
