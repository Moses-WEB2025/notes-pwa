package com.xiaomi.opensdk.file.model;

import cn.kuaipan.android.kss.download.DownloadEncryptInfo;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class DownloadParameter implements Parameter {
    private DownloadEncryptInfo mDownloadEncryptInfo;
    private String mKssDownloadString = null;
    private String mAwsDownloadString = null;

    public void setDownloadEncryptInfo(DownloadEncryptInfo downloadEncryptInfo) {
        this.mDownloadEncryptInfo = downloadEncryptInfo;
    }

    public DownloadEncryptInfo getDownloadEncryptInfo() {
        return this.mDownloadEncryptInfo;
    }

    public String getKssDownloadString() {
        return this.mKssDownloadString;
    }

    public void setKssDownloadString(String str) {
        this.mKssDownloadString = str;
    }

    public String getAwsDownloadString() {
        return this.mAwsDownloadString;
    }

    public void setAwsDownloadString(String str) {
        this.mAwsDownloadString = str;
    }

    @Override // com.xiaomi.opensdk.file.model.Parameter
    public JSONObject toJsonObject() throws JSONException {
        return new JSONObject(this.mKssDownloadString);
    }
}
