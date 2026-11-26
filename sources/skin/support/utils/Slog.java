package skin.support.utils;

import android.util.Log;

/* JADX INFO: loaded from: classes6.dex */
public class Slog {
    public static boolean DEBUG = false;
    private static final String TAG = "skin-support";

    public static void i(String str) {
        if (DEBUG) {
            Log.i(TAG, str);
        }
    }

    public static void i(String str, String str2) {
        if (DEBUG) {
            Log.i(TAG, str + ": " + str2);
        }
    }

    public static void r(String str) {
        Log.i(TAG, str);
    }

    public static void r(String str, String str2) {
        Log.i(TAG, str + ": " + str2);
    }
}
