package com.xiaomi.micloudsdk.micloudrichmedia;

import android.text.TextUtils;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class UploadResult {
    public final String downloadUrl;
    public final long expireAt;
    public final String fileId;
    public final String shareId;

    private UploadResult(String str, String str2, long j, String str3) {
        this.fileId = str;
        this.shareId = str2;
        this.expireAt = j;
        this.downloadUrl = str3;
    }

    public static UploadResult fromJson(JSONObject jSONObject) {
        String strOptString = jSONObject.optString("fileId", null);
        String strOptString2 = jSONObject.optString("shareId", null);
        String strOptString3 = jSONObject.optString(ResponseParameters.TAG_DATA_DOWNLOADURL, null);
        long jOptLong = jSONObject.optLong("expireAt", 0L);
        if (TextUtils.isEmpty(strOptString) && TextUtils.isEmpty(strOptString2)) {
            return null;
        }
        return new UploadResult(strOptString, strOptString2, jOptLong, strOptString3);
    }
}
