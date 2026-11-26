package com.xiaomi.uplink.data;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class UpLinkVerifyResult {
    public final String ticket;

    public UpLinkVerifyResult(JSONObject jSONObject) throws JSONException {
        this.ticket = jSONObject.getString("ticket");
    }

    public String toString() {
        return "UpLinkVerifyResult{, ticket='" + this.ticket + "'}";
    }
}
