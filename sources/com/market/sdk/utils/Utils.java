package com.market.sdk.utils;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.PowerManager;
import android.text.TextUtils;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.android.appfunctions.schema.nearbysharing.v1.NearbyDevice;
import com.market.sdk.MarketManager;
import com.market.sdk.Singleton;
import com.market.sdk.compat.ActivityThreadCompat;
import com.market.sdk.compat.UserHandleCompat;
import com.xiaomi.mirror.synergy.CallMethod;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;

/* JADX INFO: loaded from: classes2.dex */
public class Utils {
    protected static final int BYTES_IN_KILO = 1000;
    protected static final int BYTES_IN_MEGA = 1000000;
    public static final String TAG = "MarketSdkUtils";
    public static boolean DEBUG = new File("/sdcard/com.xiaomi.market.sdk/sdk_debug").exists();
    private static Singleton<String> marketPkgName = new Singleton<String>() { // from class: com.market.sdk.utils.Utils.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.market.sdk.Singleton
        public String create() {
            return (!Client.isMiui() || Client.isInternationalMiui()) ? "" : "com.xiaomi.market";
        }
    };
    private static volatile Singleton<Boolean> isMiMarketExists = new Singleton<Boolean>() { // from class: com.market.sdk.utils.Utils.2
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.market.sdk.Singleton
        public Boolean create() {
            String str = (String) Utils.marketPkgName.get();
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            try {
                int applicationEnabledSetting = AppGlobal.getContext().getPackageManager().getApplicationEnabledSetting(str);
                return Boolean.valueOf(applicationEnabledSetting == 0 || applicationEnabledSetting == 1);
            } catch (Exception unused) {
                return false;
            }
        }
    };

    public static String getMarketPackageName() {
        return marketPkgName.get();
    }

    public static boolean isMiuiMarketExisted(Context context) {
        return isMiMarketExists.get().booleanValue();
    }

    public static boolean isMiuiPad() {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return ((String) cls.getDeclaredMethod(CallMethod.METHOD_GET, String.class).invoke(cls, "ro.build.characteristics")).contains(NearbyDevice.DEVICE_TYPE_TABLET);
        } catch (Exception e) {
            android.util.Log.e(TAG, e.getMessage(), e);
            return false;
        }
    }

    public static String getByteString(long j, Context context) {
        String strValueOf;
        if (context == null || j < 0) {
            return "";
        }
        String str = "%1$sKB";
        if (j > 1000000) {
            strValueOf = String.format("%.1f", Double.valueOf((j * 1.0d) / 1000000.0d));
        } else if (j > 1000) {
            strValueOf = String.format("%.1f", Double.valueOf((j * 1.0d) / 1000.0d));
        } else {
            strValueOf = String.valueOf(j);
            str = "%1$sMB";
        }
        return String.format(str, strValueOf);
    }

    public static boolean isWifiConnected(Context context) {
        NetworkInfo activeNetworkInfo;
        return (context == null || (activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo()) == null || activeNetworkInfo.getType() != 1) ? false : true;
    }

    public static boolean isMobileConnected(Context context) {
        NetworkInfo activeNetworkInfo;
        return (context == null || (activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo()) == null || activeNetworkInfo.getType() != 0) ? false : true;
    }

    public static boolean hasExternalStorage(boolean z) {
        String externalStorageState = Environment.getExternalStorageState();
        if ("mounted".equals(externalStorageState)) {
            return true;
        }
        return "mounted_ro".equals(externalStorageState) && !z;
    }

    public static boolean isConnected(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public static String getStringResources(String str) {
        try {
            Resources resourcesForApplication = MarketManager.getContext().getPackageManager().getResourcesForApplication(MarketManager.MARKET_PACKAGE_NAME);
            return resourcesForApplication.getString(resourcesForApplication.getIdentifier(str, TypedValues.Custom.S_STRING, MarketManager.MARKET_PACKAGE_NAME));
        } catch (Throwable th) {
            android.util.Log.e(MarketManager.TAG, th.toString());
            return null;
        }
    }

    public static String[] getStringArray(String str, String str2) {
        try {
            Context contextCreatePackageContext = MarketManager.getContext().createPackageContext(str, 0);
            return contextCreatePackageContext.getResources().getStringArray(contextCreatePackageContext.getResources().getIdentifier(str2, "array", str));
        } catch (Throwable th) {
            android.util.Log.e(MarketManager.TAG, th.toString());
            return null;
        }
    }

    public static boolean isScreenOff() {
        return !((PowerManager) MarketManager.getContext().getSystemService("power")).isScreenOn();
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                android.util.Log.e(MarketManager.TAG, e.toString(), e);
            }
        }
    }

    public static boolean isXSpace() {
        return UserHandleCompat.myUserId() == com.market.sdk.compat.SettingsCompat.getSecureIntForUser(MarketManager.getContext(), "second_user_id", UserHandleCompat.USER_CURRENT, UserHandleCompat.USER_OWNER);
    }

    public static boolean isInternationalBuild() {
        try {
            return miuix.os.Build.IS_INTERNATIONAL_BUILD;
        } catch (Throwable th) {
            android.util.Log.d(MarketManager.TAG, th.toString());
            return false;
        }
    }

    public static boolean isFirstBoot() {
        try {
            return ActivityThreadCompat.getPackageManager().isFirstBoot();
        } catch (Exception e) {
            android.util.Log.d(MarketManager.TAG, e.toString());
            return false;
        }
    }
}
