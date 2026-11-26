package com.miui.todo.utils;

import com.miui.notes.base.utils.Logger;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes4.dex */
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
            Logger.INSTANCE.e(TAG, "Failed to call method:" + str + ",exception:" + e);
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
            Logger.INSTANCE.e(TAG, "Failed to call method:" + str + ",exception:" + e);
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
            Logger.INSTANCE.e(TAG, "Failed to call field:" + str + ",exception:" + e);
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
}
