package com.xiaomi.micloudsdk.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/* JADX INFO: loaded from: classes4.dex */
public class PrefUtils {
    public static void putLong(Context context, String str, Long l) {
        SharedPreferences.Editor editorEdit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editorEdit.putLong(str, l.longValue());
        editorEdit.commit();
    }

    public static Long getLong(Context context, String str, Long l) {
        return Long.valueOf(PreferenceManager.getDefaultSharedPreferences(context).getLong(str, l.longValue()));
    }

    public static void putInt(Context context, String str, Integer num) {
        SharedPreferences.Editor editorEdit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editorEdit.putInt(str, num.intValue());
        editorEdit.commit();
    }

    public static Integer getInt(Context context, String str, Integer num) {
        return Integer.valueOf(PreferenceManager.getDefaultSharedPreferences(context).getInt(str, num.intValue()));
    }

    public static void putString(Context context, String str, String str2) {
        SharedPreferences.Editor editorEdit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editorEdit.putString(str, str2);
        editorEdit.commit();
    }

    public static String getString(Context context, String str, String str2) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(str, str2);
    }

    public static String getString(Context context, String str) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(str, "");
    }
}
