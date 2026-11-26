package com.market.sdk.reflect;

import android.util.Log;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.lingala.zip4j.util.InternalZipConstants;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes2.dex */
public class ReflectTool {
    private static Map<Character, Class> BASIC_TYPES = null;
    private static final String TAG = "ReflectTool";

    static {
        HashMap map = new HashMap();
        BASIC_TYPES = map;
        map.put('V', Void.TYPE);
        BASIC_TYPES.put('Z', Boolean.TYPE);
        BASIC_TYPES.put('B', Byte.TYPE);
        BASIC_TYPES.put('C', Character.TYPE);
        BASIC_TYPES.put('S', Short.TYPE);
        BASIC_TYPES.put('I', Integer.TYPE);
        BASIC_TYPES.put('J', Long.TYPE);
        BASIC_TYPES.put('F', Float.TYPE);
        BASIC_TYPES.put('D', Double.TYPE);
    }

    public static Object callMethod(Class<?> cls, Object obj, String str, String str2, Object... objArr) {
        java.lang.reflect.Method method;
        try {
            try {
                method = cls.getMethod(str, parseTypesFromSignature(str2));
            } catch (java.lang.NoSuchMethodException e) {
                e.printStackTrace();
                method = null;
            }
            method.setAccessible(true);
            return method.invoke(obj, objArr);
        } catch (NoSuchMethodException e2) {
            Log.w(TAG, "ReflectUtil#callMethod ", e2);
            return null;
        } catch (ClassNotFoundException e3) {
            Log.w(TAG, "ReflectUtil#callMethod ", e3);
            return null;
        } catch (IllegalAccessException e4) {
            Log.w(TAG, "ReflectUtil#callMethod ", e4);
            return null;
        } catch (InvocationTargetException e5) {
            Log.w(TAG, "ReflectUtil#callMethod ", e5);
            return null;
        }
    }

    public static Class<?>[] parseTypesFromSignature(String str) throws ClassNotFoundException {
        if (str != null && str != "") {
            String strSubstring = str.substring(str.indexOf(40) + 1, str.indexOf(41));
            if (strSubstring != null && strSubstring != "") {
                ArrayList arrayList = new ArrayList();
                int i = -1;
                boolean z = false;
                for (int i2 = 0; i2 < strSubstring.length(); i2++) {
                    char cCharAt = strSubstring.charAt(i2);
                    if (i >= 0 || !BASIC_TYPES.containsKey(Character.valueOf(cCharAt))) {
                        if (cCharAt == '[') {
                            z = true;
                        } else if (cCharAt == 'L') {
                            if (i == -1) {
                                i = i2;
                            }
                        } else if (cCharAt == ';') {
                            String strReplaceAll = strSubstring.substring(i + 1, i2).replaceAll(InternalZipConstants.ZIP_FILE_SEPARATOR, ".");
                            if (z) {
                                arrayList.add(Array.newInstance(Class.forName(strReplaceAll), 0).getClass());
                            } else {
                                arrayList.add(Class.forName(strReplaceAll));
                            }
                            i = -1;
                        }
                    } else if (z) {
                        arrayList.add(Array.newInstance((Class<?>) BASIC_TYPES.get(Character.valueOf(cCharAt)), 0).getClass());
                    } else {
                        arrayList.add(BASIC_TYPES.get(Character.valueOf(cCharAt)));
                    }
                    z = false;
                }
                Class<?>[] clsArr = new Class[arrayList.size()];
                for (int i3 = 0; i3 < arrayList.size(); i3++) {
                    clsArr[i3] = (Class) arrayList.get(i3);
                }
                return clsArr;
            }
        }
        return null;
    }

    public static Object getFieldValue(Class<?> cls, Object obj, String str, String str2) {
        try {
            try {
                java.lang.reflect.Field declaredField = cls.getDeclaredField(str);
                declaredField.setAccessible(true);
                return declaredField.get(obj);
            } catch (NoSuchFieldException unused) {
                java.lang.reflect.Field field = cls.getField(str);
                field.setAccessible(true);
                return field.get(obj);
            } catch (IllegalAccessException e) {
                Log.w(TAG, "ReflectUtil#getFieldValue ", e);
                return null;
            } catch (java.lang.NoSuchFieldException e2) {
                e2.printStackTrace();
                return null;
            }
        } catch (NoSuchFieldException e3) {
            Log.w(TAG, "ReflectUtil#getFieldValue ", e3);
            return null;
        } catch (IllegalAccessException e4) {
            Log.w(TAG, "ReflectUtil#getFieldValue ", e4);
            return null;
        } catch (java.lang.NoSuchFieldException e5) {
            e5.printStackTrace();
            return null;
        }
    }

    public static int getFieldInt(Class<?> cls, Object obj, String str, int i) {
        Object fieldValue = getFieldValue(cls, obj, str, Field.INT_SIGNATURE_PRIMITIVE);
        return fieldValue == null ? i : ((Integer) fieldValue).intValue();
    }

    public static boolean getFieldBoolean(Class<?> cls, Object obj, String str, boolean z) {
        Object fieldValue = getFieldValue(cls, obj, str, Field.BOOLEAN_SIGNATURE_PRIMITIVE);
        return fieldValue == null ? z : ((Boolean) fieldValue).booleanValue();
    }

    private static void assertEquals(String str, String str2) {
        if (str2.equals(str)) {
            return;
        }
        System.out.println("Assert failed: expected<" + str2 + ">, but was <" + str + ">");
    }

    private static void assertEquals(int i, int i2) {
        if (i == i2) {
            return;
        }
        System.out.println("Assert failed: expected<" + i2 + ">, but was <" + i + ">");
    }

    private static void assertNull(Object obj) {
        if (obj == null) {
            return;
        }
        System.out.println("Assert failed: expected null but was " + obj);
    }

    public static void main(String[] strArr) {
        System.out.println("msg is: \nHello, world of Reflection");
        String str = (String) callMethod("Hello, world of Reflection".getClass(), "Hello, world of Reflection", "substring", "(II)Ljava/lang/String;", 2, 10);
        System.out.println("substring(2, 10) is\n" + str);
        assertEquals(str, "llo, wor");
        int iIntValue = ((Integer) callMethod(String.class, "Hello, world of Reflection", "indexOf", "(Ljava/lang/String;)I", "llo")).intValue();
        System.out.println("index is " + iIntValue);
        assertEquals(iIntValue, 2);
        int iIntValue2 = ((Integer) callMethod(String.class, "Hello, world of Reflection", "length", "()I", new Object[0])).intValue();
        System.out.println("length of it is " + iIntValue2);
        assertEquals(iIntValue2, 26);
        Object fieldValue = getFieldValue(String.class, "Hello, world of Reflection", "count", Field.INT_SIGNATURE_PRIMITIVE);
        System.out.println("field value count of string is " + (fieldValue == null ? "no such field" : fieldValue));
        assertNull(fieldValue);
        TestReflect testReflect = new TestReflect();
        String str2 = (String) getFieldValue(testReflect.getClass(), testReflect, "mTestField", "Ljava/lang/String;");
        System.out.println("test field " + str2);
        assertEquals(str2, "The quick fox jumps over the lazy dogs.");
        int fieldInt = getFieldInt(TestReflect.class, null, "STATIC", -1023);
        System.out.println(" static field " + fieldInt);
        assertEquals(fieldInt, 321);
        int[] iArr = (int[]) getFieldValue(TestReflect.class, testReflect, "mTestArray", null);
        System.out.println(iArr.getClass());
        System.out.println(new String[0].getClass());
        int i = 0;
        for (int i2 : iArr) {
            i += i2;
        }
        assertEquals(i, 15);
        assertEquals(((Integer) callMethod(TestReflect.class, testReflect, "sum", "([I)I", iArr)).intValue(), 15);
        String str3 = (String) callMethod(TestReflect.class, testReflect, "concat", "([Ljava/lang/String;)Ljava/lang/String;", new String[]{"Hello", ",", StringUtils.SPACE, "world", "!"});
        System.out.println(str3);
        assertEquals(str3, "Hello, world!");
    }

    private static class TestReflect {
        private static final int STATIC = 321;
        private String mTestField = "The quick fox jumps over the lazy dogs.";
        private int[] mTestArray = {1, 2, 3, 4, 5};

        public int sum(int[] iArr) {
            int i = 0;
            for (int i2 : iArr) {
                i += i2;
            }
            return i;
        }

        public String concat(String[] strArr) {
            StringBuilder sb = new StringBuilder();
            for (String str : strArr) {
                sb.append(str);
            }
            return sb.toString();
        }
    }
}
