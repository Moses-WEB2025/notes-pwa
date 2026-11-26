package com.xiaomi.accountsdk.account;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Base64;
import android.webkit.WebView;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.accountsdk.utils.SystemPropertiesReflection;
import com.xiaomi.onetrack.util.ab;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemProperties;

/* JADX INFO: loaded from: classes4.dex */
public class XMPassportUserAgent {
    private static final String KEY_APP_DEVICE_NAME = "MK/";
    private static final String KEY_APP_PACKAGE_NAME = "APP/";
    private static final String KEY_APP_VERSION = "APPV/";
    private static final String KEY_SDK_VERSION = "SDKV/";
    private static final String TAG = "XMPassportUserAgent";
    private static final String UA_CALLING_PACKAGE_NAME = "CPN/";
    private static final String UA_DEVICE_BRAND = "BRA/";
    private static final String UA_DEVICE_SYSTEM_NAME = "DEVS/";
    private static final String UA_DEVICE_TYPE = "DEVT/";
    private static final String UA_REDUNDANT_SID_REQUEST = "RSR/";
    private static String mDeviceName;
    private static volatile String sUserAgentCache;
    private static volatile String sUserAgentForReplacement;
    private static volatile String sWebViewUserAgentCache;
    private static volatile Set<String> sExtendedUASet = new LinkedHashSet();
    private static ThreadLocal<Set<String>> sCurrentThreadExtendedUA = new ThreadLocal<>();
    private static ThreadLocal<String> sUserAgentCacheLocal = new ThreadLocal<>();
    private static ThreadLocal<String> sWebViewUserAgentCacheLocal = new ThreadLocal<>();

    private XMPassportUserAgent() {
    }

    public static synchronized void addExtendedUserAgent(String str) {
        sExtendedUASet.add(str);
        invalidateUACache();
    }

    private static synchronized String addExtendedUserAgentForCurrentThread(String str) {
        if (sCurrentThreadExtendedUA.get() == null) {
            sCurrentThreadExtendedUA.set(new LinkedHashSet());
        }
        sCurrentThreadExtendedUA.get().add(str);
        invalidateUACache();
        return str;
    }

    public static String addExtendedCallingPkgNameUserAgent(String str) {
        return addExtendedUserAgentForCurrentThread(UA_CALLING_PACKAGE_NAME + str);
    }

    public static void addSidRequestRedundancyUserAgent(boolean z) {
        addExtendedUserAgentForCurrentThread(UA_REDUNDANT_SID_REQUEST + z);
    }

    public static synchronized void removeExtendedUserAgentForCurrentThread(String str) {
        if (sCurrentThreadExtendedUA.get() != null && sCurrentThreadExtendedUA.get().contains(str)) {
            sCurrentThreadExtendedUA.get().remove(str);
            invalidateUACache();
        }
    }

    public static synchronized void removeSidRequestRedundancyUserAgent(boolean z) {
        removeExtendedUserAgentForCurrentThread(UA_REDUNDANT_SID_REQUEST + z);
    }

    static synchronized void setUserAgentForReplacement(String str) {
        sUserAgentForReplacement = str;
        invalidateUACache();
    }

    public static synchronized String getUserAgent(Context context) {
        if (TextUtils.isEmpty(sUserAgentCache)) {
            String defaultUA = TextUtils.isEmpty(sUserAgentForReplacement) ? getDefaultUA() : sUserAgentForReplacement;
            if (sCurrentThreadExtendedUA.get() == null) {
                sUserAgentCache = new UserAgentBuilder(context, defaultUA, sExtendedUASet, false, null).build();
            } else {
                HashSet hashSet = new HashSet();
                hashSet.addAll(sExtendedUASet);
                hashSet.addAll(sCurrentThreadExtendedUA.get());
                sUserAgentCacheLocal.set(new UserAgentBuilder(context, defaultUA, hashSet, false, null).build());
                return sUserAgentCacheLocal.get();
            }
        }
        return sUserAgentCache;
    }

    public static synchronized String getWebViewUserAgent(WebView webView, Context context) {
        checkThread();
        if (TextUtils.isEmpty(sWebViewUserAgentCache)) {
            String userAgentString = webView.getSettings().getUserAgentString();
            if (sCurrentThreadExtendedUA.get() == null) {
                sWebViewUserAgentCache = new UserAgentBuilder(context, userAgentString, sExtendedUASet, true, null).build();
            } else {
                HashSet hashSet = new HashSet();
                hashSet.addAll(sExtendedUASet);
                hashSet.addAll(sCurrentThreadExtendedUA.get());
                sWebViewUserAgentCacheLocal.set(new UserAgentBuilder(context, userAgentString, hashSet, true, null).build());
                return sWebViewUserAgentCacheLocal.get();
            }
        }
        return sWebViewUserAgentCache;
    }

    private static void checkThread() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalThreadStateException("cannot be called without main thread");
        }
    }

    private static synchronized void invalidateUACache() {
        sUserAgentCache = null;
        sWebViewUserAgentCache = null;
        sUserAgentCacheLocal.set(null);
        sWebViewUserAgentCacheLocal.set(null);
    }

    private static class UserAgentBuilder {
        private final Context context;
        private final Set<String> extendedUASet;
        private final boolean isWebView;
        private final String majorUserAgent;

        /* synthetic */ UserAgentBuilder(Context context, String str, Set set, boolean z, AnonymousClass1 anonymousClass1) {
            this(context, str, set, z);
        }

        private UserAgentBuilder(Context context, String str, Set<String> set, boolean z) {
            this.context = context;
            this.majorUserAgent = str;
            this.extendedUASet = set;
            this.isWebView = z;
        }

        public String build() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.majorUserAgent);
            sb.append(" APP/").append(getStrippedPackageName(this.context));
            String appVersion = getAppVersion(this.context);
            if (!TextUtils.isEmpty(appVersion)) {
                sb.append(" APPV/").append(appVersion);
            }
            if (this.isWebView) {
                sb.append(" XiaoMi/HybridView/");
            }
            String deviceName = getDeviceName();
            if (!TextUtils.isEmpty(deviceName)) {
                sb.append(" MK/").append(Base64.encodeToString(deviceName.getBytes(), 2));
            }
            sb.append(" SDKV/5.3.0.release.94");
            for (String str : this.extendedUASet) {
                if (!TextUtils.isEmpty(str)) {
                    sb.append(StringUtils.SPACE).append(str);
                }
            }
            String deviceTypeByUiMode = getDeviceTypeByUiMode();
            if (!TextUtils.isEmpty(deviceTypeByUiMode)) {
                sb.append(" DEVT/").append(Base64.encodeToString(deviceTypeByUiMode.getBytes(), 2));
            }
            sb.append(" BRA/").append(Base64.encodeToString(getDeviceBrand().getBytes(), 2));
            sb.append(" DEVS/").append(Base64.encodeToString("Android".getBytes(), 2));
            return sb.toString();
        }

        private String getDeviceBrand() {
            String str = "";
            try {
                str = SystemPropertiesReflection.get("ro.product.manufacturer", "");
                return TextUtils.isEmpty(str) ? "Unknown" : str;
            } catch (Exception unused) {
                AccountLogger.log(XMPassportUserAgent.TAG, "fail to get marketname or model");
                return str;
            }
        }

        private String getDeviceTypeByUiMode() {
            int i = AnonymousClass1.$SwitchMap$com$xiaomi$accountsdk$account$XMPassportSettings$DeviceType[XMPassportSettings.getDeviceType().ordinal()];
            if (i == 1) {
                return "smartspeaker";
            }
            if (i == 2) {
                return "smartpanel";
            }
            if (i == 3) {
                return "Watch";
            }
            return "Phone";
        }

        private String getAppVersion(Context context) {
            if (context == null) {
                return null;
            }
            try {
                return String.valueOf(context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode);
            } catch (PackageManager.NameNotFoundException unused) {
                AccountLogger.log(XMPassportUserAgent.TAG, context.getPackageName() + " NameNotFound");
                return null;
            }
        }

        private String getStrippedPackageName(Context context) {
            String packageName;
            if (context == null) {
                packageName = "unknown";
            } else {
                packageName = context.getPackageName();
            }
            String[] strArrSplit = packageName.split(ab.a);
            return strArrSplit.length > 2 ? strArrSplit[strArrSplit.length - 2] + "." + strArrSplit[strArrSplit.length - 1] : packageName;
        }

        public static String getDeviceName() {
            if (!TextUtils.isEmpty(XMPassportUserAgent.mDeviceName)) {
                return truncate(XMPassportUserAgent.mDeviceName);
            }
            try {
                String unused = XMPassportUserAgent.mDeviceName = SystemPropertiesReflection.get("ro.product.marketname", "");
                if (TextUtils.isEmpty(XMPassportUserAgent.mDeviceName)) {
                    String unused2 = XMPassportUserAgent.mDeviceName = Build.MODEL;
                }
            } catch (Exception unused3) {
                AccountLogger.log(XMPassportUserAgent.TAG, "fail to get marketname or model");
            }
            return truncate(XMPassportUserAgent.mDeviceName);
        }

        private static String truncate(String str) {
            if (str == null) {
                return "";
            }
            return str.length() > 30 ? str.substring(0, 30) : str;
        }
    }

    /* JADX INFO: renamed from: com.xiaomi.accountsdk.account.XMPassportUserAgent$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaomi$accountsdk$account$XMPassportSettings$DeviceType;

        static {
            int[] iArr = new int[XMPassportSettings.DeviceType.values().length];
            $SwitchMap$com$xiaomi$accountsdk$account$XMPassportSettings$DeviceType = iArr;
            try {
                iArr[XMPassportSettings.DeviceType.SPEAKER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaomi$accountsdk$account$XMPassportSettings$DeviceType[XMPassportSettings.DeviceType.PANEL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaomi$accountsdk$account$XMPassportSettings$DeviceType[XMPassportSettings.DeviceType.WEAR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public static void setDeviceName(String str) {
        mDeviceName = str;
    }

    private static String getDefaultUA() {
        return System.getProperty(SystemProperties.HTTP_AGENT);
    }
}
