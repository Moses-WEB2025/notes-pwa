package com.miui.aiengine.common.utils;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ReflectUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0016\u0010\u0004\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007J\u001a\u0010\b\u001a\u00020\t2\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u00052\u0006\u0010\u000b\u001a\u00020\u0007J\u001c\u0010\f\u001a\u0004\u0018\u00010\r2\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u00052\u0006\u0010\u000b\u001a\u00020\u0007JA\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u00052\u0006\u0010\u0010\u001a\u00020\u00072\u001e\u0010\u0011\u001a\u0010\u0012\f\b\u0001\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00050\u0012\"\b\u0012\u0002\b\u0003\u0018\u00010\u0005¢\u0006\u0002\u0010\u0013¨\u0006\u0014"}, d2 = {"Lcom/miui/aiengine/common/utils/ReflectUtils;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "loadClass", "Ljava/lang/Class;", "className", "", "getStaticFieldIntValue", "", "clazz", "fieldName", "getField", "Ljava/lang/reflect/Field;", "getMethod", "Ljava/lang/reflect/Method;", "methodName", "paramTypes", "", "(Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class ReflectUtils {
    public static final ReflectUtils INSTANCE = new ReflectUtils();

    private ReflectUtils() {
    }

    public final Class<?> loadClass(String className) {
        try {
            ClassLoader classLoader = ReflectUtils.class.getClassLoader();
            if (classLoader != null) {
                return classLoader.loadClass(className);
            }
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public final int getStaticFieldIntValue(Class<?> clazz, String fieldName) {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        Intrinsics.checkNotNullParameter(fieldName, "fieldName");
        try {
            Field declaredField = clazz.getDeclaredField(fieldName);
            declaredField.setAccessible(true);
            return declaredField.getInt(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public final Field getField(Class<?> clazz, String fieldName) {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        Intrinsics.checkNotNullParameter(fieldName, "fieldName");
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
    }

    public final Method getMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        Intrinsics.checkNotNullParameter(methodName, "methodName");
        Intrinsics.checkNotNullParameter(paramTypes, "paramTypes");
        try {
            return clazz.getDeclaredMethod(methodName, (Class[]) Arrays.copyOf(paramTypes, paramTypes.length));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }
}
