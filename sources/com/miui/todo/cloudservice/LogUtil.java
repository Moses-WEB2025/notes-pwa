package com.miui.todo.cloudservice;

/* JADX INFO: loaded from: classes4.dex */
public class LogUtil {
    public static final int ASSERT = 6;
    public static final int DEBUG = 2;
    public static final int ERROR = 5;
    public static final int INFO = 3;
    public static final int LEVEL = 7;
    public static final int NOTHING = 7;
    public static final String TAG = "CloudDebug";
    public static final int VERBOSE = 1;
    public static final int WARN = 4;

    public static void a(String str, String str2) {
    }

    public static void d(String str, String str2) {
    }

    public static void e(String str, String str2) {
    }

    public static void e(String str, String str2, Exception exc) {
    }

    public static void i(String str, String str2) {
    }

    public static void v(String str, String str2) {
    }

    public static void w(String str, String str2) {
    }

    public static void v(String str) {
        v(TAG, str);
    }

    public static void d(String str) {
        d(TAG, str);
    }

    public static void i(String str) {
        i(TAG, str);
    }

    public static void w(String str) {
        w(TAG, str);
    }

    public static void e(String str) {
        e(TAG, str);
    }

    public static void a(String str) {
        a(TAG, str);
    }
}
