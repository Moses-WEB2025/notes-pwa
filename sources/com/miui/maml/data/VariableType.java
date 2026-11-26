package com.miui.maml.data;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public enum VariableType {
    INVALID(null),
    NUM(Double.TYPE),
    STR(String.class),
    OBJ(Object.class),
    JSONO(JSONObject.class),
    JSONA(JSONArray.class),
    NUM_ARR(Double.TYPE),
    DOUBLE_ARR(Double.TYPE),
    FLOAT_ARR(Float.TYPE),
    INT_ARR(Integer.TYPE),
    SHORT_ARR(Short.TYPE),
    BYTE_ARR(Byte.TYPE),
    LONG_ARR(Long.TYPE),
    BOOLEAN_ARR(Boolean.TYPE),
    CHAR_ARR(Character.TYPE),
    STR_ARR(String.class),
    OBJ_ARR(Object.class);

    public final Class<?> mTypeClass;

    public static VariableType parseType(String str) {
        if ("number".equalsIgnoreCase(str)) {
            return NUM;
        }
        if (TypedValues.Custom.S_STRING.equalsIgnoreCase(str)) {
            return STR;
        }
        if ("object".equalsIgnoreCase(str)) {
            return OBJ;
        }
        if ("jsonO".equalsIgnoreCase(str)) {
            return JSONO;
        }
        if ("jsonA".equalsIgnoreCase(str)) {
            return JSONA;
        }
        if ("number[]".equalsIgnoreCase(str)) {
            return NUM_ARR;
        }
        if ("double[]".equalsIgnoreCase(str)) {
            return DOUBLE_ARR;
        }
        if ("float[]".equalsIgnoreCase(str)) {
            return FLOAT_ARR;
        }
        if ("int[]".equalsIgnoreCase(str)) {
            return INT_ARR;
        }
        if ("short[]".equalsIgnoreCase(str)) {
            return SHORT_ARR;
        }
        if ("byte[]".equalsIgnoreCase(str)) {
            return BYTE_ARR;
        }
        if ("long[]".equalsIgnoreCase(str)) {
            return LONG_ARR;
        }
        if ("boolean[]".equalsIgnoreCase(str)) {
            return BOOLEAN_ARR;
        }
        if ("char[]".equalsIgnoreCase(str)) {
            return CHAR_ARR;
        }
        if ("string[]".equalsIgnoreCase(str)) {
            return STR_ARR;
        }
        if ("object[]".equalsIgnoreCase(str)) {
            return OBJ_ARR;
        }
        return NUM;
    }

    VariableType(Class cls) {
        this.mTypeClass = cls;
    }

    public boolean isNumber() {
        return this == NUM;
    }

    public boolean isNumberArray() {
        return ordinal() >= NUM_ARR.ordinal() && ordinal() <= CHAR_ARR.ordinal();
    }

    public boolean isNumberOrStringArray() {
        return ordinal() >= NUM_ARR.ordinal() && ordinal() <= STR_ARR.ordinal();
    }

    public boolean isArray() {
        return ordinal() >= NUM_ARR.ordinal() && ordinal() <= OBJ_ARR.ordinal();
    }
}
