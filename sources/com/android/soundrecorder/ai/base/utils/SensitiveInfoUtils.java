package com.android.soundrecorder.ai.base.utils;

import android.app.ActivityManager;
import android.text.TextUtils;
import android.util.Log;
import com.android.soundrecorder.ai.base.BuildConfig;

/* JADX INFO: loaded from: classes.dex */
public class SensitiveInfoUtils {
    public static final boolean IS_DEBUG_LOG;
    public static boolean SHOULD_PRINT_DEBUG;
    private static final StringBuilder stringBuilder = new StringBuilder();

    static {
        boolean zIsLoggable = Log.isLoggable("SoundRecorderDebug", 3);
        IS_DEBUG_LOG = zIsLoggable;
        SHOULD_PRINT_DEBUG = zIsLoggable || ActivityManager.isUserAMonkey() || BuildConfig.DEBUG;
    }

    public static String sensitiveInfo(String str) {
        if (!SHOULD_PRINT_DEBUG) {
            if (TextUtils.isEmpty(str)) {
                return "";
            }
            if (str.length() > 4) {
                stringBuilder.setLength(0);
                for (int i = 0; i < str.length() - 4; i++) {
                    stringBuilder.append("*");
                }
                StringBuilder sb = stringBuilder;
                sb.append(str.substring(str.length() - 4));
                return sb.toString();
            }
        }
        return str;
    }
}
