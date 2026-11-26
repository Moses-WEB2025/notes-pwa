package com.xiaomi.passport.ui.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.xiaomi.mirror.synergy.CallMethod;
import com.xiaomi.passport.ui.license.LoginAgreementAndPrivacy;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes4.dex */
public class CustomUtils {
    public static boolean CHECK_PHONE_NUM_LENGTH = false;
    public static boolean CUSTOM_LICENSE = false;
    public static LoginAgreementAndPrivacy CUSTOM_LOGIN_AGREEMENT_PRIVACY = null;
    public static boolean ENTER_PHAUTH_DIRECTLY = false;
    public static boolean FORMAT_PHONE_NUM = false;
    public static boolean HEADER_HELP_BY_TEXT = false;
    public static boolean HIDE_COUNTRY_CODE = false;
    public static boolean HIDE_USER_NAME = false;
    public static boolean IS_DIALOG_STYLE = false;
    public static boolean PROTOCAL_CONTENT_WITH_EXTRA = false;
    public static int QUERY_PHONE_ACCOUNT_SECOND = 10;
    public static boolean SEND_TICKET_TIME_ACCUMULATE = true;
    public static boolean SHOW_AGREENMENT_PROTOCAL_BY_DIALOG = false;
    public static boolean SHOW_CUSTOM_TOAST = false;
    public static final int SYSTEM_UI_FLAG_LIGHT_STATUS_BAR = 8192;
    public static boolean UNIFORM_LOGIN_AND_REGISTER = false;
    public static boolean USE_VERIFY_CODE_VIEW = false;

    public static boolean phoneNumberIsChinaButNotVerify(String str, String str2) {
        return str.equals("+86") && str2.length() < 11;
    }

    public static void setStatusBarTranslucent(Activity activity) {
        Window window = activity.getWindow();
        window.clearFlags(67108864);
        window.getDecorView().setSystemUiVisibility(1280);
        window.addFlags(Integer.MIN_VALUE);
        window.setStatusBarColor(0);
    }

    public static void setStatusBarDarkMode(boolean z, Activity activity) {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            Method declaredMethod = cls.getDeclaredMethod(CallMethod.METHOD_GET, String.class);
            declaredMethod.setAccessible(true);
            String str = (String) declaredMethod.invoke(cls, "ro.miui.ui.version.name");
            if ("V6".equals(str) || "V7".equals(str) || "V8".equals(str) || "V9".equals(str)) {
                Class<?> cls2 = activity.getWindow().getClass();
                try {
                    Class<?> cls3 = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                    int i = cls3.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE").getInt(cls3);
                    cls2.getMethod("setExtraFlags", Integer.TYPE, Integer.TYPE).invoke(activity.getWindow(), Integer.valueOf(z ? i : 0), Integer.valueOf(i));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        Window window = activity.getWindow();
        View decorView = window.getDecorView();
        window.addFlags(Integer.MIN_VALUE);
        int systemUiVisibility = decorView.getSystemUiVisibility();
        decorView.setSystemUiVisibility(z ? systemUiVisibility | 8192 : systemUiVisibility & (-8193));
    }

    public static int getScreeWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        if (windowManager == null) {
            return -1;
        }
        Point point = new Point();
        windowManager.getDefaultDisplay().getSize(point);
        return point.x;
    }

    public static int dip2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static boolean isChangeDensity(Context context) {
        return ((double) (((float) getScreeWidth(context)) / ((float) getHeight(context)))) >= 0.5d;
    }

    public static int getHeight(Context context) {
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getRealSize(point);
        return point.y;
    }
}
