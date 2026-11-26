package com.aios.apptoolsdk;

import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class ToolArgs {
    private final JSONObject raw;

    public ToolArgs(JSONObject jSONObject) {
        this.raw = jSONObject == null ? new JSONObject() : jSONObject;
    }

    public JSONObject getRaw() {
        return this.raw;
    }

    public String string(String str) {
        if (this.raw.has(str)) {
            return this.raw.optString(str, null);
        }
        return null;
    }

    public Integer integer(String str) {
        if (!this.raw.has(str) || this.raw.isNull(str)) {
            return null;
        }
        return Integer.valueOf(this.raw.optInt(str, 0));
    }

    public Long longValue(String str) {
        if (!this.raw.has(str) || this.raw.isNull(str)) {
            return null;
        }
        return Long.valueOf(this.raw.optLong(str, 0L));
    }

    public Double number(String str) {
        if (!this.raw.has(str) || this.raw.isNull(str)) {
            return null;
        }
        return Double.valueOf(this.raw.optDouble(str, 0.0d));
    }

    public Boolean bool(String str) {
        if (!this.raw.has(str) || this.raw.isNull(str)) {
            return null;
        }
        return Boolean.valueOf(this.raw.optBoolean(str, false));
    }

    public JSONObject object(String str) {
        return this.raw.optJSONObject(str);
    }

    public boolean has(String str) {
        return this.raw.has(str) && !this.raw.isNull(str);
    }
}
