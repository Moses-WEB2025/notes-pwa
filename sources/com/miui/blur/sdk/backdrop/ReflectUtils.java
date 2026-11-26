package com.miui.blur.sdk.backdrop;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes2.dex */
public class ReflectUtils {
    public static Method getMethod(Class cls, String str, Class<?>... clsArr) {
        try {
            return cls.getDeclaredMethod(str, clsArr);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object invokeMethod(Object obj, Method method, Object... objArr) {
        if (method != null) {
            try {
                return method.invoke(obj, objArr);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
