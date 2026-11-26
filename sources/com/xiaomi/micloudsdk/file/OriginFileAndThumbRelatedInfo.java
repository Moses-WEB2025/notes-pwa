package com.xiaomi.micloudsdk.file;

import com.xiaomi.micloudsdk.serverinfo.ServerEncryptInfo;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class OriginFileAndThumbRelatedInfo {
    public final ServerEncryptInfo originFileEncryptInfo;
    public final String originFileSha1;

    private OriginFileAndThumbRelatedInfo(String str, ServerEncryptInfo serverEncryptInfo) throws JSONException {
        this.originFileSha1 = str;
        this.originFileEncryptInfo = serverEncryptInfo;
    }

    public static class Factory {
        public static OriginFileAndThumbRelatedInfo create(String str, JSONObject jSONObject) throws JSONException {
            return new OriginFileAndThumbRelatedInfo(str, ServerEncryptInfo.Factory.create(jSONObject));
        }
    }
}
