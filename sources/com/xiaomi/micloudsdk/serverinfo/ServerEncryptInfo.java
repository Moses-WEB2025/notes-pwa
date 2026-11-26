package com.xiaomi.micloudsdk.serverinfo;

import com.xiaomi.micloudsdk.utils.MiCloudConstants;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class ServerEncryptInfo {
    public final long appKeyVersion;
    public final String encryptedRecordKey;
    public final String encryptedSha1;
    public final String recordIV;

    private ServerEncryptInfo(long j, String str, String str2, String str3) {
        this.appKeyVersion = j;
        this.recordIV = str;
        this.encryptedRecordKey = str2;
        this.encryptedSha1 = str3;
    }

    public static class Factory {
        public static ServerEncryptInfo create(JSONObject jSONObject) throws JSONException {
            return new ServerEncryptInfo(jSONObject.getLong(MiCloudConstants.PDC.J_APPKEY_VERSION), jSONObject.getString(MiCloudConstants.PDC.J_RECORD_IV), jSONObject.getString("encryptedRecordKey"), jSONObject.optString("encryptedSha1"));
        }
    }
}
