package com.xiaomi.micloudsdk.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes4.dex */
public class ReflectUtils {
    public static Class loadClass(String str) {
        try {
            return ReflectUtils.class.getClassLoader().loadClass(str);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int getStaticFieldIntValue(Class cls, String str) {
        try {
            Field declaredField = cls.getDeclaredField(str);
            declaredField.setAccessible(true);
            return declaredField.getInt(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Field getField(Class cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Method getMethod(Class cls, String str, Class<?>... clsArr) {
        try {
            return cls.getDeclaredMethod(str, clsArr);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }
}
