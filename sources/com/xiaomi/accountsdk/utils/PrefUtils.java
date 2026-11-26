package com.xiaomi.accountsdk.utils;

import android.content.Context;
import android.content.SharedPreferences;

/* JADX INFO: loaded from: classes4.dex */
public class PrefUtils {
    static final String PREF_NAME = "pref_com_xiaomi_account";

    private PrefUtils() {
    }

    public static void saveString(Context context, String str, String str2) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(PREF_NAME, 0).edit();
        editorEdit.putString(str, str2);
        editorEdit.apply();
    }

    public static void remove(Context context, String str) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(PREF_NAME, 0).edit();
        editorEdit.remove(str);
        editorEdit.apply();
    }

    public static String getString(Context context, String str) {
        return context.getSharedPreferences(PREF_NAME, 0).getString(str, null);
    }

    public static void saveLong(Context context, String str, long j) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(PREF_NAME, 0).edit();
        editorEdit.putLong(str, j);
        editorEdit.apply();
    }

    public static long getLong(Context context, String str, long j) {
        return context.getSharedPreferences(PREF_NAME, 0).getLong(str, j);
    }

    public static void saveBoolean(Context context, String str, boolean z) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(PREF_NAME, 0).edit();
        editorEdit.putBoolean(str, z);
        editorEdit.apply();
    }

    public static boolean getBoolean(Context context, String str, boolean z) {
        return context.getSharedPreferences(PREF_NAME, 0).getBoolean(str, z);
    }

    public static void saveInt(Context context, String str, int i) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(PREF_NAME, 0).edit();
        editorEdit.putInt(str, i);
        editorEdit.apply();
    }

    public static int getInt(Context context, String str, int i) {
        return context.getSharedPreferences(PREF_NAME, 0).getInt(str, i);
    }
}
