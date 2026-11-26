package com.miui.privacypolicy;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class PrivacyManager {
    protected static final boolean DEBUG = false;
    private static final String TAG = "Privacy_PrivacyManager";

    private PrivacyManager() {
    }

    public static synchronized int privacyAgree(Context context, String str, String str2) {
        checkThreadPermission("can not request privacy agree in main thread!");
        if (NetUtils.IS_INTERNATIONAL_BUILD) {
            return -4;
        }
        return PrivacyAgreeManager.handlePrivacyAgreeTask(context.getApplicationContext(), str, str2, String.valueOf(System.currentTimeMillis()), null, null);
    }

    public static synchronized int privacyAgree(Context context, String str, String str2, String str3, String str4) {
        checkThreadPermission("can not request privacy agree in main thread!");
        if (NetUtils.IS_INTERNATIONAL_BUILD) {
            return -4;
        }
        return PrivacyAgreeManager.handlePrivacyAgreeTask(context.getApplicationContext(), str, str2, String.valueOf(System.currentTimeMillis()), str3, str4);
    }

    public static synchronized int privacyRevoke(Context context, String str, String str2) {
        checkThreadPermission("can not request privacy revoke in main thread!");
        if (NetUtils.IS_INTERNATIONAL_BUILD) {
            return -4;
        }
        return PrivacyRevokeManager.handlePrivacyRevokeTask(context.getApplicationContext(), str, str2, null, null);
    }

    public static synchronized int privacyRevoke(Context context, String str, String str2, String str3, String str4) {
        checkThreadPermission("can not request privacy revoke in main thread!");
        if (NetUtils.IS_INTERNATIONAL_BUILD) {
            return -4;
        }
        return PrivacyRevokeManager.handlePrivacyRevokeTask(context.getApplicationContext(), str, str2, str3, str4);
    }

    public static synchronized String requestPrivacyUpdate(Context context, String str, String str2) {
        checkThreadPermission("can not request privacy update in main thread!");
        if (NetUtils.IS_INTERNATIONAL_BUILD) {
            return String.valueOf(-4);
        }
        return requestPrivacyUpdate(context, str, str2, null, null);
    }

    public static synchronized String requestPrivacyUpdate(Context context, String str, String str2, String str3, String str4) {
        checkThreadPermission("can not request privacy update in main thread!");
        if (NetUtils.IS_INTERNATIONAL_BUILD) {
            return String.valueOf(-4);
        }
        if (FileUtils.isVersionFileExit(context.getApplicationContext(), str)) {
            if (FileUtils.isAgreeErrorFileExit(context.getApplicationContext(), str)) {
                if (System.currentTimeMillis() - SharePreferenceUtils.getLong(context.getApplicationContext(), str + "_privacy_update_time", 0L) < 86400000) {
                    return String.valueOf(-5);
                }
                SharePreferenceUtils.putLong(context.getApplicationContext(), str + "_privacy_update_time", System.currentTimeMillis());
                String strValueOf = String.valueOf(System.currentTimeMillis());
                String data = FileUtils.readData(context.getApplicationContext(), "privacy_agree_error", str);
                if (!TextUtils.isEmpty(data)) {
                    try {
                        JSONObject jSONObject = new JSONObject(data);
                        str2 = jSONObject.optString("idContent", str2);
                        str = jSONObject.optString("policyName", str);
                        strValueOf = jSONObject.optString("timestamp", strValueOf);
                    } catch (Exception e) {
                        Log.e(TAG, "parse last jsonObject error, ", e);
                    }
                }
                int iHandlePrivacyAgreeTask = PrivacyAgreeManager.handlePrivacyAgreeTask(context.getApplicationContext(), str, str2, strValueOf, str3, str4);
                if (iHandlePrivacyAgreeTask == 1) {
                    iHandlePrivacyAgreeTask = -6;
                }
                return String.valueOf(iHandlePrivacyAgreeTask);
            }
            return PrivacyUpdateManager.handlePrivacyUpdateTask(context.getApplicationContext(), str, str3);
        }
        int iHandlePrivacyQueryTask = PrivacyQueryManager.handlePrivacyQueryTask(context.getApplicationContext(), str, str3);
        if (iHandlePrivacyQueryTask == 1) {
            iHandlePrivacyQueryTask = -7;
        }
        return String.valueOf(iHandlePrivacyQueryTask);
    }

    private static void checkThreadPermission(String str) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            return;
        }
        Log.w(TAG, str);
        throw new IllegalStateException(str);
    }
}
