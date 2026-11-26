package com.xiaomi.ai.mibrain;

/* JADX INFO: loaded from: classes4.dex */
public class Mibrainsdk {
    public static int MIBRAIN_DEBUG_LEVEL_DEBUG = 1;
    public static int MIBRAIN_DEBUG_LEVEL_ERROR = 3;
    public static int MIBRAIN_DEBUG_LEVEL_WARNING = 2;
    public static int MIBRAIN_LOG_CURRENT_LEVEL = 2;
    private static boolean loaded;

    public interface MibrainsdkLogHook {
        void onLog(int i, String str);
    }

    static synchronized void loadMibrainLibs() {
        if (!loaded) {
            System.loadLibrary("mibrainsdk");
            System.loadLibrary("mibrainjni");
            loaded = true;
        }
    }

    public static void setLogLevel(int i) {
        MibrainNativeRequest.setDebugLogLevel(i);
        MIBRAIN_LOG_CURRENT_LEVEL = i;
    }

    public static void setMibrainsdkLogHook(MibrainsdkLogHook mibrainsdkLogHook) {
        MibrainNativeRequest.setLogHook(mibrainsdkLogHook);
    }
}
