package androidx.appsearch.platformstorage.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.core.util.Preconditions;

/* JADX INFO: loaded from: classes.dex */
public class AppSearchVersionUtil {
    public static final long APPSEARCH_M2023_11_VERSION_CODE = 341113000;
    public static final long APPSEARCH_M2024_11_VERSION_CODE = 351112060;
    private static final String APPSEARCH_MODULE_NAME = "com.android.appsearch";
    public static final long APPSEARCH_U_BASE_VERSION_CODE = 340800000;
    public static final long APPSEARCH_V_BASE_VERSION_CODE = 350700000;
    private static volatile long sAppSearchVersionCode = -1;

    private AppSearchVersionUtil() {
    }

    public static long getAppSearchVersionCode(Context context) {
        Preconditions.checkNotNull(context);
        if (sAppSearchVersionCode != -1) {
            return sAppSearchVersionCode;
        }
        synchronized (AppSearchVersionUtil.class) {
            if (sAppSearchVersionCode == -1) {
                long packageInfoLongVersionCode = 0;
                try {
                    PackageManager packageManager = context.getPackageManager();
                    String appSearchPackageName = ApiHelperForQ.getAppSearchPackageName(packageManager);
                    if (appSearchPackageName != null) {
                        packageInfoLongVersionCode = ApiHelperForQ.getPackageInfoLongVersionCode(packageManager.getPackageInfo(appSearchPackageName, 1073741824));
                    }
                } catch (PackageManager.NameNotFoundException unused) {
                }
                sAppSearchVersionCode = packageInfoLongVersionCode;
            }
        }
        return sAppSearchVersionCode;
    }

    public static boolean isAtLeastB() {
        if (Build.VERSION.SDK_INT < 36) {
            return Build.VERSION.SDK_INT >= 35 && Build.VERSION.CODENAME.equals("Baklava");
        }
        return true;
    }

    private static class ApiHelperForQ {
        private ApiHelperForQ() {
        }

        static long getPackageInfoLongVersionCode(PackageInfo packageInfo) {
            return packageInfo.getLongVersionCode();
        }

        static String getAppSearchPackageName(PackageManager packageManager) throws PackageManager.NameNotFoundException {
            return packageManager.getModuleInfo(AppSearchVersionUtil.APPSEARCH_MODULE_NAME, 1).getPackageName();
        }
    }
}
