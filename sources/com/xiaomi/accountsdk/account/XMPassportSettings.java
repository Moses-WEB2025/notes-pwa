package com.xiaomi.accountsdk.account;

import android.app.Application;
import android.content.Context;
import java.io.File;

/* JADX INFO: loaded from: classes4.dex */
public class XMPassportSettings {
    private static final String IS_STAGING = "is_staging";
    private static final String NAME_STAGING_SP = "staging_sp";
    private static volatile Application sApplication = null;
    private static String sDeviceId = null;
    private static volatile DeviceType sDeviceType = DeviceType.NORMAL;
    private static volatile Context sGlobalContext = null;
    private static boolean sNonNullApplicationContextContract = false;
    private static volatile String sOwnerHandleId;
    private static volatile PasswordEncryptor sPasswordEncryptor;
    private static volatile String sUserHandleId;

    public enum DeviceType {
        NORMAL,
        WEAR,
        SPEAKER,
        PANEL
    }

    public static String getUserAgent() {
        return XMPassportUserAgent.getUserAgent(sApplication);
    }

    @Deprecated
    public static void setUserAgent(String str) {
        XMPassportUserAgent.setUserAgentForReplacement(str);
    }

    public static String getDeviceId() {
        return sDeviceId;
    }

    public static void setDeviceId(String str) {
        sDeviceId = str;
    }

    public static synchronized String getUserHandleId() {
        return sUserHandleId;
    }

    public static synchronized void setUserHandleId(String str) {
        sUserHandleId = str;
    }

    public static synchronized String getOwnerHandleId() {
        return sOwnerHandleId;
    }

    public static synchronized void setOwnerHandleId(String str) {
        sOwnerHandleId = str;
    }

    public static void setNonNullApplicationContextContract(boolean z) {
        sNonNullApplicationContextContract = z;
    }

    public static synchronized Application getApplicationContext() {
        if (sNonNullApplicationContextContract && sApplication == null) {
            throw new IllegalStateException("getApplicationContext should be called after setApplicationContext() or ensureApplicationContext()");
        }
        return sApplication;
    }

    public static synchronized void setApplicationContext(Application application) {
        try {
            if (application == null) {
                throw new IllegalArgumentException("application == null");
            }
            sApplication = application;
        } catch (Throwable th) {
            throw th;
        }
    }

    public static synchronized void ensureApplicationContext(Application application) {
        try {
            if (application == null) {
                throw new IllegalArgumentException("application == null");
            }
            if (sApplication == null) {
                sApplication = application;
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public static void setLocalStaging(Context context, boolean z) {
        setGlobalContext(context);
        context.getSharedPreferences(NAME_STAGING_SP, 0).edit().putBoolean(IS_STAGING, z).apply();
    }

    public static boolean isLocalStaging(Context context) {
        setGlobalContext(context);
        return isStaging();
    }

    static boolean isStaging() {
        boolean zExists = new File("/data/system/xiaomi_account_preview").exists();
        Context globalContext = getGlobalContext();
        if (globalContext == null || AccountIntent.PACKAGE_XIAOMI_ACCOUNT.equals(globalContext.getPackageName())) {
            return zExists;
        }
        return zExists || globalContext.getSharedPreferences(NAME_STAGING_SP, 0).getBoolean(IS_STAGING, false);
    }

    private static Context getGlobalContext() {
        if (sApplication != null) {
            return sApplication;
        }
        return sGlobalContext;
    }

    private static void setGlobalContext(Context context) {
        if (context == null || context.getApplicationContext() == null) {
            throw new IllegalArgumentException("globalContext == null or globalContext.getApplicationContext() == null");
        }
        sGlobalContext = context.getApplicationContext();
    }

    public static void setPassWordEncryptor(PasswordEncryptor passwordEncryptor) {
        sPasswordEncryptor = passwordEncryptor;
    }

    public static PasswordEncryptor getPassWordEncryptor() {
        return sPasswordEncryptor;
    }

    public static DeviceType getDeviceType() {
        return sDeviceType;
    }

    public static void setDeviceType(DeviceType deviceType) {
        sDeviceType = deviceType;
    }
}
