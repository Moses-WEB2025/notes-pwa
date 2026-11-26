package com.xiaomi.passport.data;

import com.xiaomi.accountsdk.utils.AccountLogger;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class OnlinePreference {
    public String dataCenterZone;
    public String diagnosisDomain;

    public static OnlinePreference parse(JSONObject jSONObject) {
        OnlinePreference onlinePreference = new OnlinePreference();
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject("data");
            onlinePreference.diagnosisDomain = jSONObject2.optString("diagnosisDomain", null);
            onlinePreference.dataCenterZone = jSONObject2.optString("dataCenterZone", null);
            return onlinePreference;
        } catch (JSONException e) {
            AccountLogger.log("OnlinePreference", "error body json", e);
            return onlinePreference;
        }
    }
}
