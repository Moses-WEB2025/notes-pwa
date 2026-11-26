package com.miui.privacypolicy;

import android.content.Context;
import android.content.SharedPreferences;

/* JADX INFO: loaded from: classes4.dex */
public class SharePreferenceUtils {
    private static SharedPreferences mInstance;

    private SharePreferenceUtils() {
    }

    private static SharedPreferences getInstance(Context context) {
        if (mInstance == null) {
            mInstance = context.getSharedPreferences("privacy_sdk", 0);
        }
        return mInstance;
    }

    protected static void putString(Context context, String str, String str2) {
        SharedPreferences.Editor editorEdit = getInstance(context).edit();
        editorEdit.putString(str, str2);
        editorEdit.commit();
    }

    protected static String getString(Context context, String str) {
        return getInstance(context).getString(str, "");
    }

    protected static String getString(Context context, String str, String str2) {
        return getInstance(context).getString(str, str2);
    }

    protected static void putBoolean(Context context, String str, boolean z) {
        SharedPreferences.Editor editorEdit = getInstance(context).edit();
        editorEdit.putBoolean(str, z);
        editorEdit.commit();
    }

    protected static boolean getBoolean(Context context, String str, boolean z) {
        return getInstance(context).getBoolean(str, z);
    }

    protected static void putLong(Context context, String str, long j) {
        SharedPreferences.Editor editorEdit = getInstance(context).edit();
        editorEdit.putLong(str, j);
        editorEdit.commit();
    }

    protected static long getLong(Context context, String str, long j) {
        return getInstance(context).getLong(str, j);
    }

    protected static void putInt(Context context, String str, int i) {
        SharedPreferences.Editor editorEdit = getInstance(context).edit();
        editorEdit.putInt(str, i);
        editorEdit.commit();
    }

    protected static int getInt(Context context, String str, int i) {
        return getInstance(context).getInt(str, i);
    }

    protected static void clear(Context context) {
        SharedPreferences.Editor editorEdit = getInstance(context).edit();
        editorEdit.clear();
        editorEdit.commit();
    }
}
