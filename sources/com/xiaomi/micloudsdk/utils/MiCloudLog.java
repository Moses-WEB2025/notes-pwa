package com.xiaomi.micloudsdk.utils;

import android.util.Log;

/* JADX INFO: loaded from: classes4.dex */
public class MiCloudLog {
    private static final boolean DBG = true;
    private static final String TAG = "MiCloudLog";

    public static void v(String str) {
        Log.v(TAG, str);
    }

    public static void v(String str, String str2) {
        Log.v(str, str2);
    }

    public static void d(String str) {
        Log.d(TAG, str);
    }

    public static void d(String str, String str2) {
        Log.d(str, str2);
    }

    public static void i(String str) {
        Log.i(TAG, str);
    }

    public static void i(String str, String str2) {
        Log.i(str, str2);
    }

    public static void w(String str) {
        Log.w(TAG, str);
    }

    public static void w(String str, Exception exc) {
        Log.w(TAG, str, exc);
    }

    public static void w(String str, String str2) {
        Log.w(str, str2);
    }

    public static void w(String str, String str2, Exception exc) {
        Log.w(str, str2, exc);
    }

    public static void e(String str) {
        Log.e(TAG, str);
    }

    public static void e(String str, Exception exc) {
        Log.e(TAG, str, exc);
    }

    public static void e(String str, String str2) {
        Log.e(str, str2);
    }

    public static void e(String str, String str2, Exception exc) {
        Log.e(str, str2, exc);
    }
}
