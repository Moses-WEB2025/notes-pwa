package com.miui.notes.ai.utils;

import ch.qos.logback.core.CoreConstants;
import com.miui.notes.base.utils.Logger;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.lingala.zip4j.util.InternalZipConstants;

/* JADX INFO: loaded from: classes4.dex */
public class ReflectUtils {
    private static final String TAG = "ReflectUtils";
    private static final Map<String, Method> sMethodCache = new HashMap();
    private static final Map<String, Field> sFieldCache = new HashMap();
    private static final Class<?>[] PRIMITIVE_CLASSES = {Boolean.TYPE, Byte.TYPE, Character.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE, Void.TYPE};
    private static final String[] SIGNATURE_OF_PRIMITIVE_CLASSES = {com.market.sdk.reflect.Field.BOOLEAN_SIGNATURE_PRIMITIVE, "B", com.market.sdk.reflect.Field.CHAR_SIGNATURE_PRIMITIVE, "S", com.market.sdk.reflect.Field.INT_SIGNATURE_PRIMITIVE, com.market.sdk.reflect.Field.LONG_SIGNATURE_PRIMITIVE, com.market.sdk.reflect.Field.FLOAT_SIGNATURE_PRIMITIVE, com.market.sdk.reflect.Field.DOUBLE_SIGNATURE_PRIMITIVE, "V"};

    private ReflectUtils() {
    }

    public static <T> Optional<T> createNewInstance(Class<T> cls, Class<?>[] clsArr, Object... objArr) {
        try {
            return Optional.of(cls.getConstructor(clsArr).newInstance(objArr));
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "createNewInstance" + e);
            return Optional.empty();
        }
    }

    public static <T> Optional<T> callMethod(Class<?> cls, Object obj, String str, Class<?>[] clsArr, Object... objArr) {
        try {
            Method method = getMethod(cls, str, getMethodSignature(clsArr), clsArr);
            if (method != null) {
                return Optional.ofNullable(method.invoke(obj, objArr));
            }
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "callMethod" + e);
        }
        return Optional.empty();
    }

    public static <T> Optional<T> callMethod(Object obj, String str, Class<?>[] clsArr, Object... objArr) {
        return callMethod(obj.getClass(), obj, str, clsArr, objArr);
    }

    public static <T> Optional<T> callStaticMethod(Class<?> cls, String str, Class<?>[] clsArr, Object... objArr) {
        return callMethod(cls, null, str, clsArr, objArr);
    }

    public static <T> Optional<T> callStaticMethod(String str, String str2, Class<?>[] clsArr, Object... objArr) {
        try {
            return callStaticMethod(Class.forName(str), str2, clsArr, objArr);
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "callStaticMethod" + e);
            return Optional.empty();
        }
    }

    public static Field getField(Class<?> cls, String str, String str2) {
        try {
            String strGenerateFieldCacheKey = generateFieldCacheKey(cls, str, str2);
            Field declaredField = sFieldCache.get(strGenerateFieldCacheKey);
            if (declaredField == null) {
                while (cls != null) {
                    try {
                        declaredField = cls.getDeclaredField(str);
                    } catch (Throwable unused) {
                    }
                    if (declaredField != null) {
                        declaredField.setAccessible(true);
                        break;
                    }
                    cls = cls.getSuperclass();
                }
                sFieldCache.put(strGenerateFieldCacheKey, declaredField);
            }
            return declaredField;
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "getField" + e);
            return null;
        }
    }

    public static Field getField(Class<?> cls, String str, Class<?> cls2) {
        return getField(cls, str, getSignature(cls2));
    }

    public static <T> Optional<T> getFieldValue(Class<?> cls, Object obj, String str, Class<?> cls2) {
        Field field = getField(cls, str, cls2);
        if (field == null) {
            return Optional.empty();
        }
        try {
            return Optional.ofNullable(field.get(obj));
        } catch (IllegalAccessException | IllegalArgumentException e) {
            Logger.INSTANCE.e(TAG, "getFieldValue" + e);
            return Optional.empty();
        }
    }

    public static <T> Optional<T> getFieldValue(Object obj, String str, Class<?> cls) {
        return getFieldValue(obj.getClass(), obj, str, cls);
    }

    public static <T> Optional<T> getStaticFieldValue(Class<?> cls, String str, Class<?> cls2) {
        return getFieldValue(cls, null, str, cls2);
    }

    public static Method getMethod(Class<?> cls, String str, String str2, Class<?>... clsArr) {
        try {
            String strGenerateMethodCacheKey = generateMethodCacheKey(cls, str, str2);
            Map<String, Method> map = sMethodCache;
            Method method = map.get(strGenerateMethodCacheKey);
            if (method != null) {
                return method;
            }
            Method method2 = cls.getMethod(str, clsArr);
            map.put(strGenerateMethodCacheKey, method2);
            return method2;
        } catch (Exception e) {
            Logger.INSTANCE.d(TAG, "getMethod" + e);
            return null;
        }
    }

    private static String generateMethodCacheKey(Class<?> cls, String str, String str2) {
        return cls.toString() + InternalZipConstants.ZIP_FILE_SEPARATOR + str + InternalZipConstants.ZIP_FILE_SEPARATOR + str2;
    }

    private static String generateFieldCacheKey(Class<?> cls, String str, String str2) {
        return cls.toString() + InternalZipConstants.ZIP_FILE_SEPARATOR + str + InternalZipConstants.ZIP_FILE_SEPARATOR + str2;
    }

    public static String getMethodSignature(Class<?>... clsArr) {
        StringBuilder sb = new StringBuilder("(");
        if (clsArr != null) {
            for (Class<?> cls : clsArr) {
                sb.append(getSignature(cls));
            }
        }
        sb.append(CoreConstants.RIGHT_PARENTHESIS_CHAR);
        return sb.toString();
    }

    public static String getSignature(Class<?> cls) {
        int i = 0;
        while (true) {
            Class<?>[] clsArr = PRIMITIVE_CLASSES;
            if (i < clsArr.length) {
                if (cls == clsArr[i]) {
                    return SIGNATURE_OF_PRIMITIVE_CLASSES[i];
                }
                i++;
            } else {
                return getSignature(cls.getName());
            }
        }
    }

    public static String getSignature(String str) {
        int i = 0;
        while (true) {
            Class<?>[] clsArr = PRIMITIVE_CLASSES;
            if (i >= clsArr.length) {
                break;
            }
            if (clsArr[i].getName().equals(str)) {
                str = SIGNATURE_OF_PRIMITIVE_CLASSES[i];
            }
            i++;
        }
        String strReplace = str.replace(".", InternalZipConstants.ZIP_FILE_SEPARATOR);
        return strReplace.startsWith("[") ? strReplace : "L" + strReplace + ";";
    }

    public static Class<?> getClass(String str) {
        try {
            return Class.forName(str);
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "getClass" + e);
            return null;
        }
    }

    public static void setValue(Object obj, String str, Class<?> cls, Object obj2) {
        try {
            Field field = getField(obj.getClass(), str, cls);
            if (cls == Integer.TYPE) {
                field.set(obj, obj2);
                return;
            }
            if (cls == Float.TYPE) {
                field.set(obj, obj2);
                return;
            }
            if (cls == Long.TYPE) {
                field.set(obj, Long.valueOf(((Long) obj2).longValue()));
                return;
            }
            if (cls == Boolean.TYPE) {
                field.set(obj, obj2);
                return;
            }
            if (cls == Double.TYPE) {
                field.set(obj, obj2);
                return;
            }
            if (cls == Character.TYPE) {
                field.set(obj, obj2);
                return;
            }
            if (cls == Byte.TYPE) {
                field.set(obj, obj2);
            } else if (cls == Short.TYPE) {
                field.set(obj, obj2);
            } else {
                field.set(obj, cls.cast(obj2));
            }
        } catch (Exception e) {
            Logger.INSTANCE.d(TAG, "setValue" + e);
        }
    }
}
