package com.xiaomi.aicr.common;

import android.util.Log;

/* JADX INFO: loaded from: classes4.dex */
public class SmartLog {
    public static final String TAG_AICR_ENGINE_PRE = "AiCrEngine_";

    public static int v(String str, String str2) {
        return Log.v(TAG_AICR_ENGINE_PRE + str, str2);
    }

    public static int w(String str, String str2) {
        return Log.w(TAG_AICR_ENGINE_PRE + str, str2);
    }

    public static int e(String str, String str2) {
        return Log.e(TAG_AICR_ENGINE_PRE + str, str2);
    }

    public static int e(String str, String str2, Exception exc) {
        return Log.e(TAG_AICR_ENGINE_PRE + str, str2, exc);
    }

    public static int i(String str, String str2) {
        return Log.i(TAG_AICR_ENGINE_PRE + str, str2);
    }

    public static int d(String str, String str2) {
        return Log.d(TAG_AICR_ENGINE_PRE + str, str2);
    }
}
