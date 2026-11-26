package com.miui.privacypolicy;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class PrivacyQueryManager {
    private static final String REQUEST_URL = "https://data.sec.miui.com/privacy/latestVersion";
    private static final String TAG = "Privacy_QueryManager";

    private PrivacyQueryManager() {
    }

    protected static int handlePrivacyQueryTask(Context context, String str, String str2) throws Throwable {
        if (System.currentTimeMillis() - SharePreferenceUtils.getLong(context, str + "_privacy_query_time", 0L) < 86400000) {
            return -5;
        }
        SharePreferenceUtils.putLong(context, str + "_privacy_query_time", System.currentTimeMillis());
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
        } catch (Exception e) {
            Log.e(TAG, "build jsonObject error, ", e);
        }
        String strRequest = NetUtils.request(map, REQUEST_URL, NetUtils.HttpMethod.POST, jSONObject);
        if (TextUtils.isEmpty(strRequest)) {
            return -2;
        }
        try {
            JSONObject jSONObject2 = new JSONObject(strRequest);
            int iOptInt = jSONObject2.optInt("code");
            String strOptString = jSONObject2.optString("message");
            if (iOptInt != 200 || !"success".equals(strOptString)) {
                return -3;
            }
            String strOptString2 = jSONObject2.optString("data");
            if (TextUtils.isEmpty(strOptString2)) {
                return -3;
            }
            FileUtils.saveData(strOptString2, context, "privacy_version", str);
            return 1;
        } catch (Exception e2) {
            Log.e(TAG, "handlePrivacyAgreeTask error, ", e2);
            return -3;
        }
    }
}
