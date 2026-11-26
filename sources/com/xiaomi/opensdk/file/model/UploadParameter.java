package com.xiaomi.opensdk.file.model;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class UploadParameter implements Parameter {
    private String mAwsUploadString;
    private String mKssUploadString;
    private String mUploadId;

    public void setUploadId(String str) {
        this.mUploadId = str;
    }

    public String getUploadId() {
        return this.mUploadId;
    }

    public void setKssUploadString(String str) {
        this.mKssUploadString = str;
    }

    public String getKssUploadString() {
        return this.mKssUploadString;
    }

    public String getAwsUploadString() {
        return this.mAwsUploadString;
    }

    public void setAwsUploadString(String str) {
        this.mAwsUploadString = str;
    }

    @Override // com.xiaomi.opensdk.file.model.Parameter
    public JSONObject toJsonObject() throws JSONException {
        return new JSONObject(this.mKssUploadString);
    }
}
