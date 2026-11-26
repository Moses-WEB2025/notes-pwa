package com.xiaomi.micloudsdk.micloudrichmedia;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
class ResponseParameters {
    public static final int CODE_FILE_TOO_LARGE = 80004;
    public static final int CODE_INVALID_POSITION = 80003;
    public static final int CODE_MISMATCH_DIGEST = 80001;
    public static final int CODE_MISSING_TMPID = 80005;
    public static final int CODE_PARAMETER_ERROR = 10008;
    public static final int CODE_SUCCESS = 0;
    public static final int CODE_WRITE_CONFLICT = 80002;
    public static final String TAG_CODE = "code";
    public static final String TAG_DATA = "data";
    public static final String TAG_DATA_CKEY = "ckey";
    public static final String TAG_DATA_DOWNLOADURL = "downloadurl";
    public static final String TAG_DATA_EXPIRE_AT = "expireAt";
    public static final String TAG_DATA_FILEID = "fileId";
    public static final String TAG_DATA_HOSTING_SERVER = "_hostingserver";
    public static final String TAG_DATA_SHA1 = "fileSha1";
    public static final String TAG_DATA_SHAREID = "shareId";
    public static final String TAG_DATA_TEMPURL = "tmpUrl";
    public static final String TAG_DATA_TMPID = "tmpid";
    public static final String TAG_DES = "description";
    public static final String TAG_REASON = "reason";
    public static final String TAG_RESULT = "result";
    public int mCode;
    public JSONObject mData;
    public String mDescription;
    public String mReason;
    public String mResult;

    public interface StorageType {
        public static final String KSS = "kss";
        public static final String MFS = "mfs";
    }

    private ResponseParameters() {
    }

    public static ResponseParameters parseResponse(JSONObject jSONObject) throws JSONException {
        MiCloudRichMediaManager2.log("The response json string is:" + jSONObject);
        ResponseParameters responseParameters = new ResponseParameters();
        responseParameters.mCode = jSONObject.optInt("code", -1);
        responseParameters.mResult = jSONObject.optString("result", "");
        responseParameters.mDescription = jSONObject.optString("description", "");
        responseParameters.mReason = jSONObject.optString("reason", "");
        if (responseParameters.mCode == 0) {
            responseParameters.mData = jSONObject.getJSONObject("data");
        }
        return responseParameters;
    }
}
