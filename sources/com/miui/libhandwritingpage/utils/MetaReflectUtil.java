package com.miui.libhandwritingpage.utils;

import android.util.Log;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes3.dex */
public class MetaReflectUtil {
    private static final String TAG = "MetaReflectUtil";

    private MetaReflectUtil() {
    }

    public static <T> T callStaticObjectMethod(Class<?> cls, Class<T> cls2, String str, Class<?>[] clsArr, Object... objArr) {
        if (cls == null) {
            return null;
        }
        try {
            return (T) getDeclaredMethod(cls, str, clsArr).invoke(null, objArr);
        } catch (Exception e) {
            Log.e(TAG, "Failed to call method:" + str, e);
            return null;
        }
    }

    public static Object callObjectMethod(Object obj, String str, Class<?>[] clsArr, Object... objArr) {
        if (obj == null) {
            return null;
        }
        try {
            return getDeclaredMethod(obj.getClass(), str, clsArr).invoke(obj, objArr);
        } catch (Exception e) {
            Log.e(TAG, "Failed to call method:" + str, e);
            return null;
        }
    }

    public static Object callObjectMethod2(Object obj, String str, Class<?>[] clsArr, Object... objArr) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (obj == null) {
            return null;
        }
        try {
            return obj.getClass().getMethod(str, clsArr).invoke(obj, objArr);
        } catch (Exception e) {
            Log.e(TAG, "Failed to call method:" + str, e);
            return null;
        }
    }

    public static Object callObjectFiled(Object obj, String str) {
        if (obj == null) {
            return null;
        }
        try {
            return getDeclaredField(obj.getClass(), str).get(obj);
        } catch (Exception e) {
            Log.e(TAG, "Failed to call field:" + str, e);
            return null;
        }
    }

    public static Method getDeclaredMethod(Class<?> cls, String str, Class<?>[] clsArr) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Method method = (Method) Class.class.getDeclaredMethod("getDeclaredMethod", String.class, Class[].class).invoke(cls, str, clsArr);
        method.setAccessible(true);
        return method;
    }

    private static Field getDeclaredField(Class<?> cls, String str) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Field field = (Field) Class.class.getDeclaredMethod("getDeclaredField", String.class).invoke(cls, str);
        field.setAccessible(true);
        return field;
    }

    public static int getMiuixAppcompatViewId(String str) {
        return getReflectField("miuix.appcompat.R$id", str);
    }

    public static int getAndroidInternalViewId(String str) {
        return getReflectField("com.android.internal.R$id", str);
    }

    public static int getReflectField(String str, String str2) {
        try {
            Field field = Class.forName(str).getField(str2);
            field.setAccessible(true);
            return field.getInt(null);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static <T> T getStaticObjectField(Class<?> cls, String str, Class<T> cls2) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        Field declaredField = cls.getDeclaredField(str);
        declaredField.setAccessible(true);
        return (T) declaredField.get(null);
    }
}
