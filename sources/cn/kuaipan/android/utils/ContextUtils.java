package cn.kuaipan.android.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import com.market.sdk.utils.Language;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class ContextUtils {
    private static final String LOG_TAG = "ContextUtils";
    private static volatile Context sMyContext;

    public static void init(Context context) {
        if (context != null) {
            sMyContext = context;
        }
    }

    public static Context getContext() {
        Context context;
        Context context2 = sMyContext;
        if (context2 != null) {
            return context2;
        }
        synchronized (ContextUtils.class) {
            context = sMyContext;
            if (context == null) {
                try {
                    context = (Context) JavaCalls.callMethod(JavaCalls.callStaticMethod("android.app.ActivityThread", "currentActivityThread", new Object[0]), "getApplication", new Object[0]);
                } catch (Exception unused) {
                }
                if (context == null) {
                    throw new RuntimeException("My Application havn't be call onCreate by Framework.");
                }
                sMyContext = context;
            }
        }
        return context;
    }

    public static String getPackageName() {
        return getContext().getPackageName();
    }

    public static String getAppName() {
        return getAppName(getContext());
    }

    public static String getAppName(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        CharSequence charSequenceLoadLabel = applicationInfo.loadLabel(context.getPackageManager());
        return charSequenceLoadLabel == null ? applicationInfo.packageName : charSequenceLoadLabel.toString();
    }

    public static String getAppVersion() {
        return getAppVersion(getContext());
    }

    public static String getAppVersion(Context context) {
        String packageName = context.getPackageName();
        try {
            return context.getPackageManager().getPackageInfo(packageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.w(LOG_TAG, "Exception when retrieving package:" + packageName);
            return null;
        }
    }

    public static int getAppVersionCode(Context context) {
        String packageName = context.getPackageName();
        try {
            return context.getPackageManager().getPackageInfo(packageName, 0).versionCode;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.w(LOG_TAG, "Exception when retrieving package:" + packageName);
            return -1;
        }
    }

    public static String getFrameworkVersion() {
        StringBuilder sb = new StringBuilder();
        String str = Build.VERSION.RELEASE;
        if (str.length() > 0) {
            sb.append(str);
        } else {
            sb.append("1.0");
        }
        sb.append("; ");
        Locale locale = Locale.SIMPLIFIED_CHINESE;
        String language = locale.getLanguage();
        if (language != null) {
            sb.append(language.toLowerCase());
            String country = locale.getCountry();
            if (country != null) {
                sb.append("-");
                sb.append(country.toLowerCase());
            }
        } else {
            sb.append(Language.LA_EN);
        }
        if ("REL".equals(Build.VERSION.CODENAME)) {
            String str2 = Build.MODEL;
            if (str2.length() > 0) {
                sb.append("; ");
                sb.append(str2);
            }
        }
        String str3 = Build.ID;
        if (str3.length() > 0) {
            sb.append(" Build/");
            sb.append(str3);
        }
        return sb.toString();
    }
}
