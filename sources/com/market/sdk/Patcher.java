package com.market.sdk;

import com.market.sdk.utils.Log;

/* JADX INFO: loaded from: classes2.dex */
public class Patcher {
    private static final String LIBRARY_NAME = "sdk_patcher_jni";
    private static final String TAG = "MarketPatcher";

    public native int applyPatch(String str, String str2, String str3);

    public static boolean tryLoadLibrary() {
        try {
            System.loadLibrary(LIBRARY_NAME);
            return true;
        } catch (Throwable th) {
            Log.e(TAG, "load patcher library failed : " + th.toString());
            return false;
        }
    }

    public static int patch(String str, String str2, String str3) {
        return new Patcher().applyPatch(str, str2, str3);
    }
}
