package com.miui.privacypolicy;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class PrivacyAgreeManager {
    private static final String REQUEST_URL = "https://data.sec.miui.com/privacy/agree/v1";
    private static final String TAG = "Privacy_AgreeManager";

    private PrivacyAgreeManager() {
    }

    protected static int handlePrivacyAgreeTask(Context context, String str, String str2, String str3, String str4, String str5) throws Throwable {
        HashMap map = new HashMap();
        if (TextUtils.isEmpty(str4)) {
            str4 = context.getPackageName();
        }
        map.put("pkg", str4);
        map.put("policyName", str);
        map.put("idContent", str2);
        map.put("miuiVersion", NetUtils.MIUI_VERSION_NAME);
        if (TextUtils.isEmpty(str5)) {
            str5 = PackageUtils.getAppVersionName(context);
        }
        map.put("apkVersion", str5);
        String data = FileUtils.readData(context, "privacy_temp_update_version", str);
        if (TextUtils.isEmpty(data)) {
            data = "";
        }
        map.put("policyVersion", data);
        map.put("language", Locale.getDefault().getLanguage());
        map.put("region", Locale.getDefault().getCountry());
        map.put("timestamp", str3);
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
            FileUtils.saveData(jSONObject.toString(), context, "privacy_agree_error", str);
            return -2;
        }
        try {
            JSONObject jSONObject2 = new JSONObject(strRequest);
            int iOptInt = jSONObject2.optInt("code");
            String strOptString = jSONObject2.optString("message");
            if (iOptInt == 200 && "success".equals(strOptString)) {
                String strOptString2 = new JSONObject(jSONObject2.optString("data")).optString("latestPolicyVersion");
                if (!TextUtils.isEmpty(strOptString2)) {
                    FileUtils.saveData(strOptString2, context, "privacy_version", str);
                    FileUtils.deleteFile(context, "privacy_agree_error", str);
                    FileUtils.deleteFile(context, "privacy_temp_update_version", str);
                    FileUtils.deleteFile(context, "privacy_update", str);
                    return 1;
                }
            }
        } catch (Exception e2) {
            Log.e(TAG, "handlePrivacyAgreeTask error, ", e2);
        }
        FileUtils.saveData(jSONObject.toString(), context, "privacy_agree_error", str);
        return -3;
    }
}
