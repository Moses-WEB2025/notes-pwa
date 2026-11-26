package com.xiaomi.micloudsdk.stat;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class GetDownloadFileUrlFailedStatParam {
    private static final String TAG = "GetDownloadFileUrlFailedStatParam";
    public final String description;
    public final String exceptionName;
    public final int httpStatusCode;
    public final String reason;
    public final int responseCode;
    public final String url;

    public GetDownloadFileUrlFailedStatParam(String str, int i, String str2, int i2, String str3, String str4) {
        this.url = str;
        this.httpStatusCode = i;
        this.exceptionName = str2;
        this.responseCode = i2;
        this.description = str3;
        this.reason = str4;
    }

    public static GetDownloadFileUrlFailedStatParam createGetDownloadFileUrlFailedStatParam(String str, int i, String str2, String str3) {
        GetDownloadFileUrlFailedResponse getDownloadFileUrlFailedResponseCreateGetDownloadFileUrlFailedResponse = GetDownloadFileUrlFailedResponse.createGetDownloadFileUrlFailedResponse(str3);
        return new GetDownloadFileUrlFailedStatParam(str, i, str2, getDownloadFileUrlFailedResponseCreateGetDownloadFileUrlFailedResponse.responseCode, getDownloadFileUrlFailedResponseCreateGetDownloadFileUrlFailedResponse.description, getDownloadFileUrlFailedResponseCreateGetDownloadFileUrlFailedResponse.reason);
    }

    public String toString() {
        return "GetDownloadFileUrlFailedStatParam{url='" + this.url + "', httpStatusCode=" + this.httpStatusCode + ", exceptionName='" + this.exceptionName + "', responseCode=" + this.responseCode + ", description='" + this.description + "', reason='" + this.reason + "'}";
    }

    private static class GetDownloadFileUrlFailedResponse {
        public final String description;
        public final String reason;
        public final int responseCode;

        private GetDownloadFileUrlFailedResponse(int i, String str, String str2) {
            this.responseCode = i;
            this.description = str;
            this.reason = str2;
        }

        public static GetDownloadFileUrlFailedResponse createGetDownloadFileUrlFailedResponse(String str) {
            if (str == null) {
                str = "";
            }
            try {
                JSONObject jSONObject = new JSONObject(str);
                return new GetDownloadFileUrlFailedResponse(jSONObject.optInt("code", -1), jSONObject.optString("description", ""), jSONObject.optString("reason", ""));
            } catch (JSONException e) {
                Log.e(GetDownloadFileUrlFailedStatParam.TAG, "createGetDownloadFileUrlFailedResponse: ", e);
                return new GetDownloadFileUrlFailedResponse(-1, "", "");
            }
        }
    }
}
