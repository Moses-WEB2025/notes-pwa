package com.miui.todo.utils;

/* JADX INFO: loaded from: classes4.dex */
public class CharUtils {
    public static final String ZERO_WIDTH_SPACE = "\ufeff";
    public static final char ZERO_WIDTH_SPACE_CHAR = 65279;

    public static String getStringBuilder(String str, char c) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != c) {
                sb.append(str.charAt(i));
            }
        }
        return sb.toString();
    }

    public static String removeCharFromString(String str, char c) {
        return str.replaceAll(Character.toString(c), "");
    }

    public static String[] getStringArrayByChar(String str, char c) {
        return str.split(Character.toString(c));
    }

    public static CharSequence removeLeadingEmptyLines(CharSequence charSequence) {
        if (charSequence == null) {
            return null;
        }
        return charSequence.toString().replaceFirst("^[\\s|\u3000]*\\n", "");
    }
}
