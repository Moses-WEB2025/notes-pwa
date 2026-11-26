package com.miui.common.tool;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.exifinterface.media.ExifInterface;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.ccil.cowan.tagsoup.XMLWriter;

/* JADX INFO: compiled from: ReflectUtil.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\b\f\n\u0002\u0010\b\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003Ja\u0010\u0004\u001a\u0004\u0018\u0001H\u0005\"\u0004\b\u0000\u0010\u00052\u0006\u0010\u0006\u001a\u00020\u00012\u0010\u0010\u0007\u001a\f\u0012\u0006\u0012\u0004\u0018\u0001H\u0005\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\u0012\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\b0\f2\u0016\u0010\r\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\f\"\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\u000eJa\u0010\u000f\u001a\u0004\u0018\u0001H\u0005\"\u0004\b\u0000\u0010\u00052\u0006\u0010\u0006\u001a\u00020\u00012\u0010\u0010\u0007\u001a\f\u0012\u0006\u0012\u0004\u0018\u0001H\u0005\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\u0012\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\b0\f2\u0016\u0010\r\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\f\"\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\u000eJI\u0010\u0004\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0006\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\n2\u0012\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\b0\f2\u0016\u0010\r\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\f\"\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\u0010JK\u0010\u000f\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0006\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\n2\u0014\u0010\u000b\u001a\u0010\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\b\u0018\u00010\f2\u0016\u0010\r\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\f\"\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\u0010JW\u0010\u0004\u001a\u0004\u0018\u00010\u00012\b\u0010\u0006\u001a\u0004\u0018\u00010\u00012\u0006\u0010\t\u001a\u00020\n2\n\u0010\u0011\u001a\u0006\u0012\u0002\b\u00030\b2\u0012\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\b0\f2\u0016\u0010\r\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\f\"\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\u0012Je\u0010\u0013\u001a\u0004\u0018\u0001H\u0005\"\u0004\b\u0000\u0010\u00052\n\u0010\u0011\u001a\u0006\u0012\u0002\b\u00030\b2\u0010\u0010\u0007\u001a\f\u0012\u0006\u0012\u0004\u0018\u0001H\u0005\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\u0012\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\b0\f2\u0016\u0010\r\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\f\"\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\u0014J9\u0010\u0015\u001a\u0004\u0018\u0001H\u0005\"\u0004\b\u0000\u0010\u00052\n\u0010\u0011\u001a\u0006\u0012\u0002\b\u00030\b2\u0006\u0010\u0016\u001a\u00020\n2\u0010\u0010\u0007\u001a\f\u0012\u0006\u0012\u0004\u0018\u0001H\u0005\u0018\u00010\b¢\u0006\u0002\u0010\u0017J\u000e\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\nJ\u000e\u0010\u001b\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\nJ\u0018\u0010\u001c\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020\n2\u0006\u0010\u001e\u001a\u00020\nH\u0002¨\u0006\u001f"}, d2 = {"Lcom/miui/common/tool/ReflectUtil;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "callObjectMethod", ExifInterface.GPS_DIRECTION_TRUE, TypedValues.AttributesType.S_TARGET, "returnType", "Ljava/lang/Class;", XMLWriter.METHOD, "", "parameterTypes", "", "values", "(Ljava/lang/Object;Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Class;[Ljava/lang/Object;)Ljava/lang/Object;", "callObjectMethod2", "(Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/Class;[Ljava/lang/Object;)Ljava/lang/Object;", "clazz", "(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Class;[Ljava/lang/Class;[Ljava/lang/Object;)Ljava/lang/Object;", "callStaticObjectMethod", "(Ljava/lang/Class;Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Class;[Ljava/lang/Object;)Ljava/lang/Object;", "getStaticObjectField", "field", "(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;", "getMiuixAppcompatViewId", "", "idName", "getAndroidInternalViewId", "getReflectField", "className", "fieldName", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class ReflectUtil {
    public static final ReflectUtil INSTANCE = new ReflectUtil();

    private ReflectUtil() {
    }

    public final <T> T callObjectMethod(Object target, Class<T> returnType, String method, Class<?>[] parameterTypes, Object... values) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(parameterTypes, "parameterTypes");
        Intrinsics.checkNotNullParameter(values, "values");
        Method declaredMethod = target.getClass().getDeclaredMethod(method, (Class[]) Arrays.copyOf(parameterTypes, parameterTypes.length));
        declaredMethod.setAccessible(true);
        return (T) declaredMethod.invoke(target, Arrays.copyOf(values, values.length));
    }

    public final <T> T callObjectMethod2(Object target, Class<T> returnType, String method, Class<?>[] parameterTypes, Object... values) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(parameterTypes, "parameterTypes");
        Intrinsics.checkNotNullParameter(values, "values");
        Method method2 = target.getClass().getMethod(method, (Class[]) Arrays.copyOf(parameterTypes, parameterTypes.length));
        method2.setAccessible(true);
        return (T) method2.invoke(target, Arrays.copyOf(values, values.length));
    }

    public final Object callObjectMethod(Object target, String method, Class<?>[] parameterTypes, Object... values) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(parameterTypes, "parameterTypes");
        Intrinsics.checkNotNullParameter(values, "values");
        return target.getClass().getDeclaredMethod(method, (Class[]) Arrays.copyOf(parameterTypes, parameterTypes.length)).invoke(target, Arrays.copyOf(values, values.length));
    }

    public final Object callObjectMethod2(Object target, String method, Class<?>[] parameterTypes, Object... values) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(values, "values");
        Class<?> cls = target.getClass();
        if (parameterTypes == null) {
            return cls.getMethod(method, null).invoke(target, Arrays.copyOf(values, values.length));
        }
        return cls.getMethod(method, (Class[]) Arrays.copyOf(parameterTypes, parameterTypes.length)).invoke(target, Arrays.copyOf(values, values.length));
    }

    public final Object callObjectMethod(Object target, String method, Class<?> clazz, Class<?>[] parameterTypes, Object... values) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        Intrinsics.checkNotNullParameter(parameterTypes, "parameterTypes");
        Intrinsics.checkNotNullParameter(values, "values");
        Method declaredMethod = clazz.getDeclaredMethod(method, (Class[]) Arrays.copyOf(parameterTypes, parameterTypes.length));
        declaredMethod.setAccessible(true);
        return declaredMethod.invoke(target, Arrays.copyOf(values, values.length));
    }

    public final <T> T callStaticObjectMethod(Class<?> clazz, Class<T> returnType, String method, Class<?>[] parameterTypes, Object... values) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(parameterTypes, "parameterTypes");
        Intrinsics.checkNotNullParameter(values, "values");
        Method declaredMethod = clazz.getDeclaredMethod(method, (Class[]) Arrays.copyOf(parameterTypes, parameterTypes.length));
        declaredMethod.setAccessible(true);
        return (T) declaredMethod.invoke(null, Arrays.copyOf(values, values.length));
    }

    public final <T> T getStaticObjectField(Class<?> clazz, String field, Class<T> returnType) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        Intrinsics.checkNotNullParameter(field, "field");
        Field declaredField = clazz.getDeclaredField(field);
        declaredField.setAccessible(true);
        return (T) declaredField.get(null);
    }

    public final int getMiuixAppcompatViewId(String idName) {
        Intrinsics.checkNotNullParameter(idName, "idName");
        return getReflectField("miuix.appcompat.R$id", idName);
    }

    public final int getAndroidInternalViewId(String idName) {
        Intrinsics.checkNotNullParameter(idName, "idName");
        return getReflectField("com.android.internal.R$id", idName);
    }

    private final int getReflectField(String className, String fieldName) {
        try {
            Field field = Class.forName(className).getField(fieldName);
            field.setAccessible(true);
            return field.getInt(null);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
