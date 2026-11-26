package cn.kuaipan.kss;

import com.xiaomi.e2ee.E2EEException;
import com.xiaomi.e2ee.utils.RecordKeyHelper;
import com.xiaomi.micloudsdk.utils.MiCloudConstants;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class DownloadEncryptInfo {
    public final long appKeyVersion;
    public final String encryptedRecordKey;
    public final String encryptedSha1;
    private String mRecordKey;
    public final String recordIV;

    public DownloadEncryptInfo(long j, String str, String str2, String str3, String str4) {
        this.appKeyVersion = j;
        this.encryptedSha1 = str;
        this.recordIV = str2;
        this.encryptedRecordKey = str3;
        this.mRecordKey = str4;
    }

    public static DownloadEncryptInfo fromJson(JSONObject jSONObject) throws JSONException {
        return new DownloadEncryptInfo(jSONObject.getLong(MiCloudConstants.PDC.J_APPKEY_VERSION), jSONObject.getString("encryptedSha1"), jSONObject.getString(MiCloudConstants.PDC.J_RECORD_IV), jSONObject.getString("encryptedRecordKey"), null);
    }

    public String getRecordKey() throws InterruptedException, E2EEException {
        String str = this.mRecordKey;
        if (str != null) {
            return str;
        }
        String decryptRecordKey = RecordKeyHelper.getDecryptRecordKey(this.encryptedRecordKey, this.recordIV, this.appKeyVersion);
        this.mRecordKey = decryptRecordKey;
        return decryptRecordKey;
    }
}
