package com.xiaomi.onetrack;

import android.content.Context;
import android.content.Intent;
import com.xiaomi.mirror.RemoteDeviceInfo;
import com.xiaomi.onetrack.api.ah;
import com.xiaomi.onetrack.api.an;
import com.xiaomi.onetrack.f.a;
import com.xiaomi.onetrack.util.j;
import com.xiaomi.onetrack.util.r;
import com.xiaomi.passport.ui.internal.SNSAuthProvider;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import miuix.android.content.MiuiIntent;
import miuix.navigation.SplitLayout;

/* JADX INFO: loaded from: classes4.dex */
public class OneTrack {
    private static boolean a;
    private static volatile int b;
    private static boolean c;
    private static boolean d;
    private static HttpRequestProperty f;
    private an e;

    public interface ICommonPropertyProvider {
        Map<String, Object> getDynamicProperty(String str);
    }

    public interface IEventHook {
        boolean isCustomDauEvent(String str);

        boolean isRecommendEvent(String str);
    }

    public enum HttpRequestProperty {
        KEEP_ALIVE("keep-alive"),
        CLOSE(SplitLayout.TAG_CLOSE);

        private String a;

        HttpRequestProperty(String str) {
            this.a = str;
        }

        public String getType() {
            return this.a;
        }
    }

    public enum Mode {
        APP("app"),
        PLUGIN("plugin"),
        SDK("sdk");

        private String a;

        Mode(String str) {
            this.a = str;
        }

        public String getType() {
            return this.a;
        }
    }

    public enum UserIdType {
        XIAOMI(RemoteDeviceInfo.MANUFACTURER_XIAOMI),
        PHONE_NUMBER("phone_number"),
        WEIXIN("weixin"),
        WEIBO(SNSAuthProvider.SNS_TYPE_WEIBO),
        QQ("qq"),
        OTHER(RemoteDeviceInfo.MANUFACTURER_OTHER);

        private String a;

        UserIdType(String str) {
            this.a = str;
        }

        public String getUserIdType() {
            return this.a;
        }
    }

    public enum NetType {
        NOT_CONNECTED("NONE"),
        MOBILE_2G("2G"),
        MOBILE_3G("3G"),
        MOBILE_4G("4G"),
        MOBILE_5G("5G"),
        WIFI(MiuiIntent.WIFI_NAME),
        ETHERNET("ETHERNET"),
        UNKNOWN("UNKNOWN"),
        CONNECTED("CONNECTED");

        private String a;

        NetType(String str) {
            this.a = str;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.a;
        }
    }

    private OneTrack(Context context, Configuration configuration) {
        a.a(context.getApplicationContext());
        this.e = new an(context, configuration);
        setEventHook(new DefaultEventHook());
    }

    public static OneTrack createInstance(Context context, Configuration configuration) {
        return new OneTrack(context, configuration);
    }

    public static void setDebugMode(boolean z) {
        r.a(z);
    }

    public static void setDisable(boolean z) {
        a = z;
    }

    public static boolean isDisable() {
        return a;
    }

    public static void setUseSystemNetTrafficOnly() {
        c = true;
    }

    public static boolean isUseSystemNetTrafficOnly() {
        return c;
    }

    public static void setTestMode(boolean z) {
        r.b(z);
    }

    public static void registerCrashHook(Context context) {
        CrashAnalysis.a(context);
    }

    public static void setAccessNetworkEnable(Context context, final boolean z) {
        a(context);
        j.a(new Runnable() { // from class: com.xiaomi.onetrack.OneTrack.1
            @Override // java.lang.Runnable
            public void run() {
                com.xiaomi.onetrack.c.j.a(z);
                com.xiaomi.onetrack.c.j.b(z);
            }
        });
    }

    public static void setRestrictGetNetworkInfo(boolean z) {
        d = z;
    }

    public static boolean isRestrictGetNetworkInfo() {
        return d;
    }

    public static void setHttpReqPropConnection(HttpRequestProperty httpRequestProperty) {
        f = httpRequestProperty;
    }

    public static HttpRequestProperty getHttpReqPropConnection() {
        return f;
    }

    public static String sdkVersion() {
        return BuildConfig.SDK_VERSION;
    }

    private static void a(Context context) {
        if (context == null) {
            throw new IllegalStateException("context is null!");
        }
        a.a(context.getApplicationContext());
    }

    public void track(String str, Map<String, Object> map) {
        this.e.a(str, map);
    }

    public void trackEventFromH5(String str) {
        this.e.a(str);
    }

    public void track(String str, List<String> list, Map<String, Object> map) {
        HashMap map2 = new HashMap();
        if (map != null) {
            map2.putAll(map);
        }
        map2.put(ah.ac, String.join(",", list));
        this.e.a(str, (Map<String, Object>) map2);
    }

    public void adTrack(String str, Map<String, Object> map) {
        this.e.a(str, map, (List<String>) null);
    }

    public void adTrack(String str, Map<String, Object> map, List<String> list) {
        this.e.a(str, map, list);
    }

    public void trackPluginEvent(String str, String str2, Map<String, Object> map) {
        this.e.a(str, str2, map);
    }

    public void login(String str, UserIdType userIdType, Map<String, Object> map, boolean z) {
        this.e.a(str, userIdType, map, z);
    }

    public void login(String str, UserIdType userIdType, Map<String, Object> map) {
        login(str, userIdType, map, false);
    }

    public void logout() {
        logout(null, false);
    }

    public void logout(Map<String, Object> map, boolean z) {
        this.e.a(map, z);
    }

    public void setCommonProperty(Map<String, Object> map) {
        this.e.c(map);
    }

    public void clearCommonProperty() {
        this.e.b();
    }

    public void removeCommonProperty(String str) {
        this.e.b(str);
    }

    public void setDynamicCommonProperty(ICommonPropertyProvider iCommonPropertyProvider) {
        this.e.a(iCommonPropertyProvider);
    }

    public void setUserProfile(Map<String, Object> map) {
        this.e.a(map);
    }

    public void setUserProfile(String str, Object obj) {
        this.e.a(str, obj);
    }

    public void userProfileIncrement(Map<String, ? extends Number> map) {
        this.e.b(map);
    }

    public void userProfileIncrement(String str, Number number) {
        this.e.a(str, number);
    }

    public void setInstanceId(String str) {
        this.e.c(str);
    }

    public String getInstanceId() throws OnMainThreadException {
        return this.e.d();
    }

    public String getOAID(Context context) throws OnMainThreadException {
        return this.e.c(context);
    }

    public String getAndroidId(Context context) throws OnMainThreadException {
        return this.e.d(context);
    }

    public void refreshGetOaidTime() {
        this.e.e();
    }

    public boolean hasMemoryOaid() {
        return this.e.f();
    }

    public void trackServiceQualityEvent(ServiceQualityEvent serviceQualityEvent) {
        this.e.a(serviceQualityEvent);
    }

    public void setEventHook(IEventHook iEventHook) {
        this.e.a(iEventHook);
    }

    public void setCustomPrivacyPolicyAccepted(boolean z) {
        this.e.b(z);
    }

    public void setBasicModeEnable(boolean z) {
        this.e.c(z);
    }

    public static void setGlobalBasicModeEnable(boolean z) {
        if (z) {
            b = 2;
        } else {
            b = 1;
        }
    }

    public static int getGlobalBasicModeEnable() {
        return b;
    }

    public void setCloseOaidDependMsaSDK(boolean z) {
        this.e.d(z);
    }

    public String appActiveBroadcast(Intent intent) throws OnMainThreadException {
        return this.e.a(intent);
    }
}
