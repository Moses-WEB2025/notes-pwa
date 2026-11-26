package miuix.core.util;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes5.dex */
public final class HyperFeatureResolveHelper {
    private static Class<?> FEATURE_CLASS = null;
    private static final String FEATURE_NAME = "miui.os.HyperOSCustFeatureResolve";
    private static final Map<String, Method> METHOD_CACHE = new ConcurrentHashMap();
    public static final String TAG = "HyperFeatureResolveHelper";

    static {
        try {
            FEATURE_CLASS = Class.forName(FEATURE_NAME);
        } catch (ClassNotFoundException unused) {
            FEATURE_CLASS = null;
        }
    }

    private HyperFeatureResolveHelper() {
    }

    public static boolean getBoolean(String str, boolean z) {
        Boolean bool = (Boolean) invokeFeatureMethod("getBoolean", new Class[]{String.class, Boolean.TYPE}, new Object[]{str, Boolean.valueOf(z)}, Boolean.valueOf(z));
        return bool != null ? bool.booleanValue() : z;
    }

    public static int getInt(String str, int i) {
        Integer num = (Integer) invokeFeatureMethod("getInt", new Class[]{String.class, Integer.TYPE}, new Object[]{str, Integer.valueOf(i)}, Integer.valueOf(i));
        return num != null ? num.intValue() : i;
    }

    public static Float getFloat(String str, float f) {
        Float f2 = (Float) invokeFeatureMethod("getFloat", new Class[]{String.class, Float.TYPE}, new Object[]{str, Float.valueOf(f)}, Float.valueOf(f));
        if (f2 != null) {
            f = f2.floatValue();
        }
        return Float.valueOf(f);
    }

    public static String getString(String str, String str2) {
        return (String) invokeFeatureMethod("getString", new Class[]{String.class, String.class}, new Object[]{str, str2}, str2);
    }

    public static List<String> getStringArray(String str) {
        return (List) invokeFeatureMethod("getStringArray", new Class[]{String.class}, new Object[]{str}, null);
    }

    public static int[] getIntArray(String str) {
        return (int[]) invokeFeatureMethod("getIntArray", new Class[]{String.class}, new Object[]{str}, null);
    }

    public static Map<String, String> getMap(String str) {
        return (Map) invokeFeatureMethod("getMap", new Class[]{String.class}, new Object[]{str}, null);
    }

    public static boolean getBooleanForPhoneId(int i, String str, boolean z) {
        return ((Boolean) invokeFeatureMethod("getBooleanForPhoneId", new Class[]{Integer.TYPE, String.class, Boolean.TYPE}, new Object[]{Integer.valueOf(i), str, Boolean.valueOf(z)}, Boolean.valueOf(z))).booleanValue();
    }

    public static int getIntForPhoneId(int i, String str, int i2) {
        return ((Integer) invokeFeatureMethod("getIntForPhoneId", new Class[]{Integer.TYPE, String.class, Integer.TYPE}, new Object[]{Integer.valueOf(i), str, Integer.valueOf(i2)}, Integer.valueOf(i2))).intValue();
    }

    public static float getFloatForPhoneId(int i, String str, float f) {
        return ((Float) invokeFeatureMethod("getFloatForPhoneId", new Class[]{Integer.TYPE, String.class, Float.TYPE}, new Object[]{Integer.valueOf(i), str, Float.valueOf(f)}, Float.valueOf(f))).floatValue();
    }

    public static String getStringForPhoneId(int i, String str, String str2) {
        return (String) invokeFeatureMethod("getStringForPhoneId", new Class[]{Integer.TYPE, String.class, String.class}, new Object[]{Integer.valueOf(i), str, str2}, str2);
    }

    public static List<String> getStringArrayForPhoneId(int i, String str) {
        return (List) invokeFeatureMethod("getStringArrayForPhoneId", new Class[]{Integer.TYPE, String.class}, new Object[]{Integer.valueOf(i), str}, null);
    }

    public static int[] getIntArrayForPhoneId(int i, String str) {
        return (int[]) invokeFeatureMethod("getIntArrayForPhoneId", new Class[]{Integer.TYPE, String.class}, new Object[]{Integer.valueOf(i), str}, null);
    }

    public static Map<String, String> getMapForPhoneId(int i, String str) {
        return (Map) invokeFeatureMethod("getMapForPhoneId", new Class[]{Integer.TYPE, String.class, Map.class}, new Object[]{Integer.valueOf(i), str}, null);
    }

    private static <T> T invokeFeatureMethod(String str, Class<?>[] clsArr, Object[] objArr, T t) {
        Method method;
        if (FEATURE_CLASS != null && (method = getMethod(str, clsArr)) != null) {
            try {
                T t2 = (T) method.invoke(FEATURE_CLASS, objArr);
                if (t2 != null) {
                    return t2;
                }
            } catch (Exception unused) {
            }
        }
        return t;
    }

    private static Method getMethod(String str, Class<?>[] clsArr) {
        if (FEATURE_CLASS == null) {
            return null;
        }
        Map<String, Method> map = METHOD_CACHE;
        Method method = map.get(str);
        if (method != null) {
            return method;
        }
        try {
            Method declaredMethod = FEATURE_CLASS.getDeclaredMethod(str, clsArr);
            map.put(str, declaredMethod);
            return declaredMethod;
        } catch (Exception unused) {
            return null;
        }
    }
}
