package com.xiaomi.passport.jsb;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class PassportJsbMethodResult {
    private final Object mResult;

    public PassportJsbMethodResult(long j) {
        this.mResult = Long.valueOf(j);
    }

    public PassportJsbMethodResult(double d) {
        this.mResult = Double.valueOf(d);
    }

    public PassportJsbMethodResult(boolean z) {
        this.mResult = Boolean.valueOf(z);
    }

    public PassportJsbMethodResult(String str) {
        if (str == null) {
            throw new IllegalArgumentException("params can not be null");
        }
        this.mResult = str;
    }

    public PassportJsbMethodResult(JSONObject jSONObject) {
        if (jSONObject == null) {
            throw new IllegalArgumentException("params can not be null");
        }
        this.mResult = jSONObject;
    }

    public void fillResultInJsonObject(JSONObject jSONObject, String str) {
        try {
            jSONObject.put(str, this.mResult);
        } catch (JSONException e) {
            throw new IllegalStateException("should not happen", e);
        }
    }
}
