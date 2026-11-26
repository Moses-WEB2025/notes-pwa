package com.aios.apptoolsdk;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class AppParam {
    private final String description;
    private final List<String> enumValues;
    private final String name;
    private final boolean required;
    private final String type;

    private AppParam(String str, String str2, String str3, boolean z, List<String> list) {
        this.name = str;
        this.type = str2;
        this.description = str3;
        this.required = z;
        this.enumValues = list;
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isRequired() {
        return this.required;
    }

    public List<String> getEnumValues() {
        return this.enumValues;
    }

    public static AppParam string(String str, String str2) {
        return new AppParam(str, TypedValues.Custom.S_STRING, str2, false, null);
    }

    public static AppParam string(String str, String str2, boolean z) {
        return new AppParam(str, TypedValues.Custom.S_STRING, str2, z, null);
    }

    public static AppParam number(String str, String str2) {
        return new AppParam(str, "number", str2, false, null);
    }

    public static AppParam number(String str, String str2, boolean z) {
        return new AppParam(str, "number", str2, z, null);
    }

    public static AppParam integer(String str, String str2) {
        return new AppParam(str, TypedValues.Custom.S_INT, str2, false, null);
    }

    public static AppParam integer(String str, String str2, boolean z) {
        return new AppParam(str, TypedValues.Custom.S_INT, str2, z, null);
    }

    public static AppParam bool(String str, String str2) {
        return new AppParam(str, TypedValues.Custom.S_BOOLEAN, str2, false, null);
    }

    public static AppParam bool(String str, String str2, boolean z) {
        return new AppParam(str, TypedValues.Custom.S_BOOLEAN, str2, z, null);
    }

    public static AppParam ofEnum(String str, String str2, List<String> list) {
        return new AppParam(str, TypedValues.Custom.S_STRING, str2, false, list);
    }

    public static AppParam ofEnum(String str, String str2, List<String> list, boolean z) {
        return new AppParam(str, TypedValues.Custom.S_STRING, str2, z, list);
    }
}
