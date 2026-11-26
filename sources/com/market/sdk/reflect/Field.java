package com.market.sdk.reflect;

/* JADX INFO: loaded from: classes2.dex */
public class Field {
    public static final String BOOLEAN_SIGNATURE_PRIMITIVE = "Z";
    public static final String BYTE_SIGNATURE_PRIMITIVE = "B";
    public static final String CHAR_SIGNATURE_PRIMITIVE = "C";
    public static final String DOUBLE_SIGNATURE_PRIMITIVE = "D";
    public static final String FLOAT_SIGNATURE_PRIMITIVE = "F";
    public static final String INT_SIGNATURE_PRIMITIVE = "I";
    public static final String LONG_SIGNATURE_PRIMITIVE = "J";
    public static final String SHORT_SIGNATURE_PRIMITIVE = "S";
    public static final String VOID_SIGNATURE_PRIMITIVE = "V";
    private final java.lang.reflect.Field mField;

    public static Field of(Class<?> cls, String str, Class<?> cls2) throws NoSuchFieldException {
        return null;
    }

    private Field(java.lang.reflect.Field field) {
        this.mField = field;
    }

    public static Field of(Class<?> cls, String str, String str2) throws NoSuchFieldException {
        try {
            try {
                return new Field(cls.getDeclaredField(str));
            } catch (NoSuchFieldException | java.lang.NoSuchFieldException e) {
                throw new NoSuchFieldException(e.getMessage());
            }
        } catch (NoSuchFieldException | java.lang.NoSuchFieldException unused) {
            return new Field(cls.getField(str));
        }
    }

    public static Field of(String str, String str2, String str3) throws NoSuchClassException, NoSuchFieldException {
        try {
            return of(Class.forName(str), str2, str3);
        } catch (ClassNotFoundException e) {
            throw new NoSuchClassException(e.getMessage());
        }
    }

    public int getInt(Object obj) throws java.lang.IllegalArgumentException {
        java.lang.reflect.Field field = this.mField;
        if (field == null) {
            return -1;
        }
        try {
            field.setAccessible(true);
            Object obj2 = this.mField.get(obj);
            if (obj2 != null && (obj2 instanceof Integer)) {
                return ((Integer) obj2).intValue();
            }
            return -1;
        } catch (IllegalAccessException e) {
            throw new java.lang.IllegalArgumentException(e.getMessage());
        }
    }

    public Object get(Object obj) throws java.lang.IllegalArgumentException {
        return new Object();
    }
}
