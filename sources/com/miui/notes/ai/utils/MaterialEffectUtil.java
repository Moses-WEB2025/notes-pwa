package com.miui.notes.ai.utils;

import android.graphics.Outline;
import android.graphics.Point;
import android.view.View;
import android.view.ViewOutlineProvider;
import com.miui.notes.base.utils.Logger;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import miuix.core.util.SystemProperties;

/* JADX INFO: loaded from: classes4.dex */
public class MaterialEffectUtil {
    private static final String TAG = "MaterialEffectUtil";

    public static boolean setPassWindowBlurEnabled(View view, boolean z) {
        try {
            return ((Boolean) Class.forName("android.view.View").getMethod("setPassWindowBlurEnabled", Boolean.TYPE).invoke(view, Boolean.valueOf(z))).booleanValue();
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Logger.INSTANCE.e(TAG, "setPassWindowBlurEnabled" + e);
            return false;
        } catch (Exception e2) {
            Logger.INSTANCE.e(TAG, "setPassWindowBlurEnabled final" + e2);
            return false;
        }
    }

    public static void setMiBackgroundBlurMode(View view, int i) {
        try {
            Class.forName("android.view.View").getMethod("setMiBackgroundBlurMode", Integer.TYPE).invoke(view, Integer.valueOf(i));
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Logger.INSTANCE.e(TAG, "setMiBackgroundBlurMode" + e);
        }
    }

    public static void setMiBackgroundBlurRadius(View view, int i) {
        try {
            Class.forName("android.view.View").getMethod("setMiBackgroundBlurRadius", Integer.TYPE).invoke(view, Integer.valueOf(i));
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Logger.INSTANCE.e(TAG, "setMiBackgroundBlurRadius" + e);
        }
    }

    public static void setMiViewBlurMode(View view, int i) {
        try {
            Class.forName("android.view.View").getMethod("setMiViewBlurMode", Integer.TYPE).invoke(view, Integer.valueOf(i));
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Logger.INSTANCE.e(TAG, "setMiViewBlurMode" + e);
        }
    }

    public static void setMiBackgroundBlendColors(View view, ArrayList<Point> arrayList) {
        try {
            Class.forName("android.view.View").getMethod("setMiBackgroundBlendColors", ArrayList.class).invoke(view, arrayList);
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Logger.INSTANCE.e(TAG, "setMiBackgroundBlendColors" + e);
        }
    }

    public static void setMiSelfBlur(View view, int i, ArrayList<Point> arrayList) {
        try {
            Class.forName("android.view.View").getMethod("setMiSelfBlur", Integer.TYPE, ArrayList.class).invoke(view, Integer.valueOf(i), arrayList);
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Logger.INSTANCE.e(TAG, "setMiSelfBlur" + e);
        }
    }

    public static void clearBackgroundBlurEffect(View view) {
        setMiBackgroundBlurMode(view, 0);
    }

    public static void clearViewBlurEffect(View view) {
        setMiViewBlurMode(view, 0);
    }

    public static void clearBackgroundBlendColors(View view) {
        setMiBackgroundBlendColors(view, null);
    }

    public static void setMixEffectEnable(View view, boolean z) {
        try {
            Class.forName("android.view.View").getMethod("setMixEffectEnable", Boolean.TYPE).invoke(view, Boolean.valueOf(z));
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Logger.INSTANCE.e(TAG, "setMixEffectEnable" + e);
        }
    }

    public static boolean supportMiShadow() {
        return Boolean.parseBoolean(SystemProperties.get("persist.sys.mi_shadow_supported", "false"));
    }

    public static void setMiShadow(View view, int i, float f, float f2, float f3, float f4) {
        try {
            view.getClass().getMethod("setMiShadow", Integer.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE).invoke(view, Integer.valueOf(i), Float.valueOf(f), Float.valueOf(f2), Float.valueOf(f3), Float.valueOf(f4));
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Logger.INSTANCE.e(TAG, "setMiShadow" + e);
        }
    }

    public static void clipToRoundRect(View view, final float f, final int i) {
        view.setClipToOutline(true);
        view.setOutlineProvider(new ViewOutlineProvider() { // from class: com.miui.notes.ai.utils.MaterialEffectUtil.1
            @Override // android.view.ViewOutlineProvider
            public void getOutline(View view2, Outline outline) {
                int i2 = i;
                outline.setRoundRect(i2, i2, view2.getWidth() - i, view2.getHeight() - i, f);
            }
        });
    }
}
