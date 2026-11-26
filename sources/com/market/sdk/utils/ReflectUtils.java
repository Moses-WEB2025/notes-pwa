package com.market.sdk.utils;

import com.market.sdk.MarketManager;
import com.market.sdk.reflect.Field;
import com.market.sdk.reflect.Method;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.Map;
import net.lingala.zip4j.util.InternalZipConstants;

/* JADX INFO: loaded from: classes2.dex */
public class ReflectUtils {
    private static final String TAG = "ReflectUtils";
    private static Map<String, Method> sMethodCache = CollectionUtils.newConconrrentHashMap();
    private static Map<String, Field> sFieldCache = CollectionUtils.newConconrrentHashMap();
    private static Map<String, Class> sClazzCache = CollectionUtils.newConconrrentHashMap();

    private ReflectUtils() {
    }

    public static void invoke(Class<?> cls, Object obj, String str, String str2, Object... objArr) {
        try {
            Method method = getMethod(cls, str, str2);
            if (method != null) {
                method.invoke(cls, obj, objArr);
            }
        } catch (Throwable th) {
            android.util.Log.e(TAG, "Exception: " + th);
        }
    }

    public static <T> T invokeObject(Class<?> cls, Object obj, String str, String str2, Object... objArr) {
        try {
            Method method = getMethod(cls, str, str2);
            if (method != null) {
                return (T) method.invokeObject(cls, obj, objArr);
            }
            return null;
        } catch (Throwable th) {
            android.util.Log.e(TAG, "Exception: " + th);
            return null;
        }
    }

    public static Field getField(Class<?> cls, String str, String str2) {
        try {
            String strGenerateFieldCacheKey = generateFieldCacheKey(cls, str, str2);
            Field field = sFieldCache.get(strGenerateFieldCacheKey);
            if (field != null) {
                return field;
            }
            Field fieldOf = Field.of(cls, str, str2);
            sFieldCache.put(strGenerateFieldCacheKey, fieldOf);
            return fieldOf;
        } catch (Throwable th) {
            android.util.Log.e(TAG, "Exception: " + th);
            return null;
        }
    }

    public static <T> T getFieldValue(Class<?> cls, Object obj, String str, String str2) {
        try {
            Field field = getField(cls, str, str2);
            if (field == null) {
                return null;
            }
            return (T) field.get(obj);
        } catch (Exception e) {
            android.util.Log.d(MarketManager.TAG, e.toString());
            return null;
        }
    }

    public static Method getMethod(Class<?> cls, String str, String str2) {
        try {
            String strGenerateMethodCacheKey = generateMethodCacheKey(cls, str, str2);
            Method method = sMethodCache.get(strGenerateMethodCacheKey);
            if (method != null) {
                return method;
            }
            Method methodOf = Method.of(cls, str, str2);
            sMethodCache.put(strGenerateMethodCacheKey, methodOf);
            return methodOf;
        } catch (Throwable th) {
            android.util.Log.e(TAG, "Exception e: " + th);
            return null;
        }
    }

    private static String generateMethodCacheKey(Class<?> cls, String str, String str2) {
        return cls.toString() + InternalZipConstants.ZIP_FILE_SEPARATOR + str + InternalZipConstants.ZIP_FILE_SEPARATOR + str2;
    }

    private static String generateFieldCacheKey(Class<?> cls, String str, String str2) {
        return cls.toString() + InternalZipConstants.ZIP_FILE_SEPARATOR + str + InternalZipConstants.ZIP_FILE_SEPARATOR + str2;
    }

    public static String getMethodSignature(Class<?> cls, Class<?>... clsArr) {
        try {
            return ReflectUtilsForMiui.getSignature(clsArr, cls);
        } catch (Throwable th) {
            android.util.Log.e(MarketManager.TAG, th.toString());
            return "";
        }
    }

    public static Class<?> getClass(String str) {
        Class<?> cls = sClazzCache.get(str);
        if (cls != null) {
            return cls;
        }
        try {
            cls = Class.forName(str);
            sClazzCache.put(str, cls);
            return cls;
        } catch (Exception e) {
            android.util.Log.e(TAG, e.toString(), e);
            return cls;
        }
    }
}
