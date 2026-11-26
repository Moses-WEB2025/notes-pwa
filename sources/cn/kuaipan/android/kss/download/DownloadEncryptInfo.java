package cn.kuaipan.android.kss.download;

import com.xiaomi.e2ee.E2EEException;
import com.xiaomi.e2ee.utils.RecordKeyHelper;
import com.xiaomi.micloudsdk.utils.MiCloudConstants;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class DownloadEncryptInfo {
    private long mAppKeyVersion;
    private String mEncryptSha1;
    private String mEncryptedRecordKey;
    private String mRecordIV;
    private String mRecordKey;

    public void setEncryptedRecordKey(String str) {
        this.mEncryptedRecordKey = str;
    }

    public String getRecordKey() {
        return this.mRecordKey;
    }

    public void setEncryptSha1(String str) {
        this.mEncryptSha1 = str;
    }

    public String getRecordIV() {
        return this.mRecordIV;
    }

    public void setAppKeyVersion(long j) {
        this.mAppKeyVersion = j;
    }

    public void setRecordIV(String str) {
        this.mRecordIV = str;
    }

    public void fillRecordKey() throws InterruptedException, E2EEException {
        this.mRecordKey = RecordKeyHelper.getDecryptRecordKey(this.mEncryptedRecordKey, this.mRecordIV, this.mAppKeyVersion);
    }

    public static DownloadEncryptInfo parseDownloadEncryptOrNull(JSONObject jSONObject) throws JSONException {
        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("encryptInfo");
        if (jSONObjectOptJSONObject == null) {
            return null;
        }
        DownloadEncryptInfo downloadEncryptInfo = new DownloadEncryptInfo();
        downloadEncryptInfo.setAppKeyVersion(jSONObjectOptJSONObject.getLong(MiCloudConstants.PDC.J_APPKEY_VERSION));
        downloadEncryptInfo.setEncryptSha1(jSONObjectOptJSONObject.getString("encryptedSha1"));
        downloadEncryptInfo.setRecordIV(jSONObjectOptJSONObject.getString(MiCloudConstants.PDC.J_RECORD_IV));
        downloadEncryptInfo.setEncryptedRecordKey(jSONObjectOptJSONObject.getString("encryptedRecordKey"));
        return downloadEncryptInfo;
    }
}
