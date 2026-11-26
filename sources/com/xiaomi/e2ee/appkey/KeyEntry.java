package com.xiaomi.e2ee.appkey;

import ch.qos.logback.core.CoreConstants;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class KeyEntry implements Serializable {
    private static final String APPKEY_VERSION = "appKeyVersion";
    private static final String ENCRYPTED_RECORD_KEY = "encryptedRecordKey";
    private static final String RECORD_IV = "recordIV";
    public final long appKeyVersion;
    public final String encryptedRecordKey;
    public final String recordIV;

    public KeyEntry(String str, String str2, long j) {
        this.encryptedRecordKey = str;
        this.recordIV = str2;
        this.appKeyVersion = j;
    }

    public JSONObject toServerJson() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(ENCRYPTED_RECORD_KEY, this.encryptedRecordKey);
        jSONObject.put("recordIV", this.recordIV);
        jSONObject.put("appKeyVersion", this.appKeyVersion);
        return jSONObject;
    }

    public static KeyEntry fromServerJson(JSONObject jSONObject) throws JSONException {
        return new KeyEntry(jSONObject.getString(ENCRYPTED_RECORD_KEY), jSONObject.getString("recordIV"), jSONObject.getLong("appKeyVersion"));
    }

    public String toString() {
        return "KeyEntry{encryptedRecordKey='" + this.encryptedRecordKey + "', recordIV='" + this.recordIV + "', appKeyVersion=" + this.appKeyVersion + CoreConstants.CURLY_RIGHT;
    }
}
