package com.xiaomi.opensdk.file.model;

import cn.kuaipan.android.kss.upload.UploadEncryptInfo;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class CommitParameter implements Parameter {
    private static final String TAG = "CommitParameter";
    private String mAwsString;
    private UploadEncryptInfo mEncryptInfo;
    private String mFileSHA1;
    private long mFileSize;
    private String mKssString;
    private String mThumbnailCommitString;
    private String mUploadId;

    public CommitParameter() {
        this.mFileSize = -1L;
    }

    public CommitParameter(String str, String str2, String str3, String str4, long j, UploadEncryptInfo uploadEncryptInfo, String str5) {
        this.mKssString = str;
        this.mAwsString = str2;
        this.mUploadId = str3;
        this.mFileSHA1 = str4;
        this.mFileSize = j;
        this.mEncryptInfo = uploadEncryptInfo;
        this.mThumbnailCommitString = str5;
    }

    public String getEncryptInfoStrOrNull() throws JSONException {
        if (this.mEncryptInfo.isNeedEncrypt()) {
            return this.mEncryptInfo.getRequestServerEncryptInfoJson().toString();
        }
        return null;
    }

    public String getKssString() {
        return this.mKssString;
    }

    public void setKssString(String str) {
        this.mKssString = str;
    }

    public String getAwsString() {
        return this.mAwsString;
    }

    public void setAwsString(String str) {
        this.mAwsString = str;
    }

    public String getUploadId() {
        return this.mUploadId;
    }

    public void setUploadId(String str) {
        this.mUploadId = str;
    }

    public String getFileSHA1() {
        return this.mFileSHA1;
    }

    public void setFileSHA1(String str) {
        this.mFileSHA1 = str;
    }

    public long getFileSize() {
        return this.mFileSize;
    }

    public void setFileSize(long j) {
        this.mFileSize = j;
    }

    public String getThumbnailCommitString() {
        return this.mThumbnailCommitString;
    }

    @Override // com.xiaomi.opensdk.file.model.Parameter
    public JSONObject toJsonObject() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (this.mKssString != null) {
            jSONObject.put("kss", new JSONObject(this.mKssString));
        }
        if (this.mEncryptInfo.isNeedEncrypt()) {
            jSONObject.put("encryptInfo", this.mEncryptInfo.getRequestServerEncryptInfoJson());
        }
        jSONObject.put("uploadId", this.mUploadId);
        jSONObject.put("size", this.mFileSize);
        jSONObject.put("sha1", this.mFileSHA1);
        return jSONObject;
    }
}
