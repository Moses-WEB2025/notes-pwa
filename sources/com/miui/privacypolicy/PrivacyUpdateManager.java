package com.miui.privacypolicy;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class PrivacyUpdateManager {
    private static final String REQUEST_URL = "https://data.sec.miui.com/privacy/get/v1";
    private static final String TAG = "Privacy_UpdateManager";

    private PrivacyUpdateManager() {
    }

    protected static String handlePrivacyUpdateTask(Context context, String str, String str2) throws Throwable {
        if (System.currentTimeMillis() - SharePreferenceUtils.getLong(context.getApplicationContext(), str + "_privacy_update_time", 0L) < 86400000) {
            String data = FileUtils.readData(context, "privacy_version", str);
            String data2 = FileUtils.readData(context, "privacy_update", str);
            if (!TextUtils.isEmpty(data2)) {
                try {
                    return compareVersion(data, new JSONObject(data2).optString("version")) ? data2 : String.valueOf(-8);
                } catch (Exception e) {
                    Log.e(TAG, "handlePrivacyUpdateTask parse temp version error, ", e);
                }
            }
            return String.valueOf(-5);
        }
        SharePreferenceUtils.putLong(context.getApplicationContext(), str + "_privacy_update_time", System.currentTimeMillis());
        HashMap map = new HashMap();
        if (TextUtils.isEmpty(str2)) {
            str2 = context.getPackageName();
        }
        map.put("pkg", str2);
        map.put("policyName", str);
        map.put("timestamp", String.valueOf(System.currentTimeMillis()));
        JSONObject jSONObject = new JSONObject();
        try {
            for (Map.Entry entry : map.entrySet()) {
                jSONObject.put((String) entry.getKey(), (String) entry.getValue());
            }
        } catch (Exception e2) {
            Log.e(TAG, "build jsonObject error, ", e2);
        }
        String strRequest = NetUtils.request(map, REQUEST_URL, NetUtils.HttpMethod.POST, jSONObject);
        if (TextUtils.isEmpty(strRequest)) {
            return String.valueOf(-2);
        }
        try {
            JSONObject jSONObject2 = new JSONObject(strRequest);
            int iOptInt = jSONObject2.optInt("code");
            String strOptString = jSONObject2.optString("message");
            if (iOptInt == 200 && "success".equals(strOptString)) {
                String strOptString2 = jSONObject2.optString("data");
                String strOptString3 = new JSONObject(strOptString2).optString("version");
                if (compareVersion(FileUtils.readData(context, "privacy_version", str), strOptString3)) {
                    FileUtils.saveData(strOptString2, context, "privacy_update", str);
                    FileUtils.saveData(strOptString3, context, "privacy_temp_update_version", str);
                    return strOptString2;
                }
                return String.valueOf(-8);
            }
        } catch (Exception e3) {
            Log.e(TAG, "handlePrivacyRevokeTask error, ", e3);
        }
        return String.valueOf(-3);
    }

    private static boolean compareVersion(String str, String str2) {
        return TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || str.compareToIgnoreCase(str2) < 0;
    }
}
