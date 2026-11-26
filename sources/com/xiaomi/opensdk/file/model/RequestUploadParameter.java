package com.xiaomi.opensdk.file.model;

import cn.kuaipan.android.kss.upload.UploadEncryptInfo;
import com.xiaomi.opensdk.file.utils.FileSDKUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class RequestUploadParameter implements Parameter {
    private static final String TAG = "RequestUploadParameter";
    private String mAwsString;
    private UploadEncryptInfo mEncryptInfo;
    private String mFileMimeType;
    private String mFileName;
    private String mFileSHA1;
    private long mFileSize;
    private String mKssString;

    public RequestUploadParameter() {
        this.mFileSize = -1L;
        this.mFileMimeType = null;
    }

    public RequestUploadParameter(String str, String str2, String str3, long j, String str4, UploadEncryptInfo uploadEncryptInfo) {
        this.mFileMimeType = null;
        this.mKssString = str;
        this.mAwsString = str2;
        this.mFileSHA1 = str3;
        this.mFileSize = j;
        this.mFileName = str4;
        this.mEncryptInfo = uploadEncryptInfo;
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

    public String getAwsString() {
        return this.mAwsString;
    }

    public String getFileSHA1() {
        return this.mFileSHA1;
    }

    public long getFileSize() {
        return this.mFileSize;
    }

    public String getFileName() {
        return this.mFileName;
    }

    public String getFileMimeType() {
        return this.mFileMimeType;
    }

    public void setFileMimeType(String str) {
        this.mFileMimeType = str;
    }

    @Override // com.xiaomi.opensdk.file.model.Parameter
    public JSONObject toJsonObject() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (this.mEncryptInfo.isNeedEncrypt()) {
            jSONObject.put("encryptInfo", this.mEncryptInfo.getRequestServerEncryptInfoJson());
        }
        jSONObject.put("kss", new JSONObject(this.mKssString));
        jSONObject.put(FileSDKUtils.J_FILENAME, this.mFileName);
        jSONObject.put("size", this.mFileSize);
        jSONObject.put("sha1", this.mFileSHA1);
        jSONObject.put("mimeType", this.mFileMimeType);
        return jSONObject;
    }
}
