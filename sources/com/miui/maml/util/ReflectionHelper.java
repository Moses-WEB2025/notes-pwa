package com.miui.maml.util;

import android.text.TextUtils;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.xiaomi.mirror.synergy.CallMethod;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import net.lingala.zip4j.util.InternalZipConstants;

/* JADX INFO: loaded from: classes3.dex */
public class ReflectionHelper {
    static HashMap<String, Class<?>> PRIMITIVE_TYPE;
    private static Map<String, Constructor> sConstructorCache;
    private static Map<String, Field> sFieldCache;
    private static Method sForNameMethod;
    private static Method sGetDeclaredConstructorMethod;
    private static Method sGetDeclaredFieldMethod;
    private static Method sGetDeclaredMethodMethod;
    private static Method sGetMethod;
    private static Method sInvokeMethod;
    private static Map<String, Method> sMethodCache;
    private static Method sNewInstanceMethod;
    private static Method sSetAccessibleMethod;
    private static Method sSetMethod;

    static {
        HashMap<String, Class<?>> map = new HashMap<>();
        PRIMITIVE_TYPE = map;
        map.put("byte", Byte.TYPE);
        PRIMITIVE_TYPE.put("short", Short.TYPE);
        PRIMITIVE_TYPE.put("int", Integer.TYPE);
        PRIMITIVE_TYPE.put("long", Long.TYPE);
        PRIMITIVE_TYPE.put("char", Character.TYPE);
        PRIMITIVE_TYPE.put(TypedValues.Custom.S_BOOLEAN, Boolean.TYPE);
        PRIMITIVE_TYPE.put(TypedValues.Custom.S_FLOAT, Float.TYPE);
        PRIMITIVE_TYPE.put("double", Double.TYPE);
        PRIMITIVE_TYPE.put("byte[]", byte[].class);
        PRIMITIVE_TYPE.put("short[]", short[].class);
        PRIMITIVE_TYPE.put("int[]", int[].class);
        PRIMITIVE_TYPE.put("long[]", long[].class);
        PRIMITIVE_TYPE.put("char[]", char[].class);
        PRIMITIVE_TYPE.put("boolean[]", boolean[].class);
        PRIMITIVE_TYPE.put("float[]", float[].class);
        PRIMITIVE_TYPE.put("double[]", double[].class);
        sMethodCache = new HashMap();
        sFieldCache = new HashMap();
        sConstructorCache = new HashMap();
        sInvokeMethod = null;
        sGetDeclaredFieldMethod = null;
        sGetDeclaredMethodMethod = null;
        sSetAccessibleMethod = null;
        sGetDeclaredConstructorMethod = null;
        sNewInstanceMethod = null;
        sForNameMethod = null;
        sSetMethod = null;
        sGetMethod = null;
    }

    public static Class<?>[] strTypesToClass(String[] strArr) throws ClassNotFoundException {
        if (strArr == null) {
            return null;
        }
        Class<?>[] clsArr = new Class[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            clsArr[i] = strTypeToClassThrows(strArr[i]);
        }
        return clsArr;
    }

    public static Class<?> strTypeToClass(String str) {
        try {
            return strTypeToClassThrows(str);
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }

    public static Object getEnumConstant(String str, String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            try {
                return Enum.valueOf(Class.forName(str), str2);
            } catch (ClassCastException | ClassNotFoundException | IllegalArgumentException unused) {
            }
        }
        return null;
    }

    private static Class<?> strTypeToClassThrows(String str) throws ClassNotFoundException {
        if (PRIMITIVE_TYPE.containsKey(str)) {
            return PRIMITIVE_TYPE.get(str);
        }
        if (!str.contains(".")) {
            str = "java.lang." + str;
        }
        return Class.forName(str);
    }

    private static Object invokeInternal(Object obj, Object... objArr) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (sInvokeMethod == null) {
            sInvokeMethod = Method.class.getMethod("invoke", Object.class, Object[].class);
        }
        return sInvokeMethod.invoke(obj, objArr);
    }

    private static Field getDeclaredFieldInternal(Object obj, String str) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (sGetDeclaredFieldMethod == null) {
            sGetDeclaredFieldMethod = Class.class.getMethod("getDeclaredField", String.class);
        }
        return (Field) sGetDeclaredFieldMethod.invoke(obj, str);
    }

    private static Method getDeclaredMethodInternal(Object obj, String str, Class<?>... clsArr) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (sGetDeclaredMethodMethod == null) {
            sGetDeclaredMethodMethod = Class.class.getMethod("getDeclaredMethod", String.class, Class[].class);
        }
        return (Method) sGetDeclaredMethodMethod.invoke(obj, str, clsArr);
    }

    private static void setAccessibleInternal(Object obj, boolean z) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (sSetAccessibleMethod == null) {
            sSetAccessibleMethod = AccessibleObject.class.getMethod("setAccessible", Boolean.TYPE);
        }
        sSetAccessibleMethod.invoke(obj, Boolean.valueOf(z));
    }

    private static Constructor getDeclaredConstructorInternal(Object obj, Class<?>... clsArr) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (sGetDeclaredConstructorMethod == null) {
            sGetDeclaredConstructorMethod = Class.class.getMethod("getDeclaredConstructor", Class[].class);
        }
        return (Constructor) sGetDeclaredConstructorMethod.invoke(obj, clsArr);
    }

    private static <T> T newInstanceInternal(Object obj, Object... objArr) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (sNewInstanceMethod == null) {
            sNewInstanceMethod = Constructor.class.getMethod("newInstance", Object[].class);
        }
        return (T) sNewInstanceMethod.invoke(obj, objArr);
    }

    private static Class forNameInternal(String str) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (sForNameMethod == null) {
            sForNameMethod = Class.class.getMethod("forName", String.class);
        }
        return (Class) sForNameMethod.invoke(null, str);
    }

    private static void setInternal(Object obj, Object obj2, Object obj3) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (sSetMethod == null) {
            sSetMethod = Field.class.getMethod("set", Object.class, Object.class);
        }
        sSetMethod.invoke(obj, obj2, obj3);
    }

    private static Object getInternal(Object obj, Object obj2) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (sGetMethod == null) {
            sGetMethod = Field.class.getMethod(CallMethod.METHOD_GET, Object.class);
        }
        return sGetMethod.invoke(obj, obj2);
    }

    public static void invoke(Class<?> cls, Object obj, String str, Class<?>[] clsArr, Object... objArr) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Method method = getMethod(cls, str, clsArr);
        if (method != null) {
            invokeInternal(method, obj, objArr);
        }
    }

    public static <T> T invokeObject(Class<?> cls, Object obj, String str, Class<?>[] clsArr, Object... objArr) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Method method = getMethod(cls, str, clsArr);
        if (method != null) {
            return (T) invokeInternal(method, obj, objArr);
        }
        return null;
    }

    public static Field getField(Class<?> cls, String str) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        String strGenerateFieldCacheKey = generateFieldCacheKey(cls, str);
        Field field = sFieldCache.get(strGenerateFieldCacheKey);
        if (field != null) {
            return field;
        }
        Field declaredFieldInternal = getDeclaredFieldInternal(cls, str);
        setAccessibleInternal(declaredFieldInternal, true);
        sFieldCache.put(strGenerateFieldCacheKey, declaredFieldInternal);
        return declaredFieldInternal;
    }

    public static void setFieldValue(Class<?> cls, Object obj, String str, Object obj2) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Field field = getField(cls, str);
        if (field != null) {
            setInternal(field, obj, obj2);
        }
    }

    public static <T> T getFieldValue(Class<?> cls, Object obj, String str) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Field field = getField(cls, str);
        if (field == null) {
            return null;
        }
        return (T) getInternal(field, obj);
    }

    public static Method getMethod(Class<?> cls, String str, Class<?>... clsArr) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        String strGenerateMethodCacheKey = generateMethodCacheKey(cls, str, clsArr);
        Method method = sMethodCache.get(strGenerateMethodCacheKey);
        if (method != null) {
            return method;
        }
        Method declaredMethodInternal = getDeclaredMethodInternal(cls, str, clsArr);
        setAccessibleInternal(declaredMethodInternal, true);
        sMethodCache.put(strGenerateMethodCacheKey, declaredMethodInternal);
        return declaredMethodInternal;
    }

    public static <T> T getConstructorInstance(Class<?> cls, Class<?>[] clsArr, Object... objArr) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Constructor constructor = getConstructor(cls, clsArr);
        if (constructor == null) {
            return null;
        }
        return (T) newInstanceInternal(constructor, objArr);
    }

    public static Constructor getConstructor(Class<?> cls, Class<?>... clsArr) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        String strGenerateConstructorCacheKey = generateConstructorCacheKey(cls, clsArr);
        Constructor constructor = sConstructorCache.get(strGenerateConstructorCacheKey);
        if (constructor != null) {
            return constructor;
        }
        Constructor declaredConstructorInternal = getDeclaredConstructorInternal(cls, clsArr);
        setAccessibleInternal(declaredConstructorInternal, true);
        sConstructorCache.put(strGenerateConstructorCacheKey, declaredConstructorInternal);
        return declaredConstructorInternal;
    }

    private static String generateConstructorCacheKey(Class<?> cls, Class<?>... clsArr) {
        return cls.toString() + InternalZipConstants.ZIP_FILE_SEPARATOR + Arrays.toString(clsArr);
    }

    private static String generateMethodCacheKey(Class<?> cls, String str, Class<?>[] clsArr) {
        return cls.toString() + InternalZipConstants.ZIP_FILE_SEPARATOR + str + InternalZipConstants.ZIP_FILE_SEPARATOR + Arrays.toString(clsArr);
    }

    private static String generateFieldCacheKey(Class<?> cls, String str) {
        return cls.toString() + InternalZipConstants.ZIP_FILE_SEPARATOR + str;
    }

    public static Class<?> getClass(String str) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return forNameInternal(str);
    }
}
