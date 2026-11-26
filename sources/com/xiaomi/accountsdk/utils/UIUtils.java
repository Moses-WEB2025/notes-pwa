package com.xiaomi.accountsdk.utils;

import android.content.Context;
import android.os.Build;
import android.util.TypedValue;
import android.webkit.WebSettings;
import android.webkit.WebView;

/* JADX INFO: loaded from: classes4.dex */
public class UIUtils {
    private static final int FORCE_DARK_AUTO = 1;
    private static final int FORCE_DARK_ON = 2;
    private static final String TAG = "UIUtils";

    private UIUtils() {
    }

    public static boolean isDarkMode(Context context) {
        return context.getResources().getConfiguration().uiMode == 32;
    }

    private static void setWebSettingsForceDark(WebSettings webSettings, int i) {
        try {
            webSettings.getClass().getMethod("setForceDark", Integer.TYPE).invoke(webSettings, Integer.valueOf(i));
        } catch (Exception e) {
            AccountLogger.log(TAG, "setWebSettingsForceDark reflect error", e);
        }
    }

    public static void adaptForceDarkInApi29(Context context, WebView webView) {
        if (webView == null) {
            throw new IllegalArgumentException("param webView shouldn't be null!");
        }
        WebSettings settings = webView.getSettings();
        if (Build.VERSION.SDK_INT >= 33) {
            settings.setAlgorithmicDarkeningAllowed(true);
        } else if (Build.VERSION.SDK_INT < 33) {
            if (isSystemNightMode(context)) {
                setWebSettingsForceDark(settings, 2);
            } else {
                setWebSettingsForceDark(settings, 1);
            }
        }
    }

    public static float dp2px(float f, Context context) {
        return TypedValue.applyDimension(1, f, context.getResources().getDisplayMetrics());
    }

    public static float sp2px(float f, Context context) {
        return TypedValue.applyDimension(2, f, context.getResources().getDisplayMetrics());
    }

    public static boolean isSystemNightMode(Context context) {
        return (context.getResources().getConfiguration().uiMode & 48) == 32;
    }
}
