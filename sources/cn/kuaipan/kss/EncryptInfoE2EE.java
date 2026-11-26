package cn.kuaipan.kss;

import com.xiaomi.e2ee.E2EEException;
import com.xiaomi.e2ee.utils.RecordKeyHelper;
import com.xiaomi.micloudsdk.utils.MiCloudConstants;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class EncryptInfoE2EE {
    public final long appKeyVersion;
    public final String encryptedRecordKey;
    public final String encryptedSha1;
    private String mRecordKey;
    public final String recordIV;

    public EncryptInfoE2EE(long j, String str, String str2, String str3, String str4) {
        this.appKeyVersion = j;
        this.encryptedSha1 = str;
        this.recordIV = str2;
        this.encryptedRecordKey = str3;
        this.mRecordKey = str4;
    }

    public static EncryptInfoE2EE fromJson(JSONObject jSONObject) throws JSONException {
        return new EncryptInfoE2EE(jSONObject.getLong(MiCloudConstants.PDC.J_APPKEY_VERSION), jSONObject.getString("encryptedSha1"), jSONObject.getString(MiCloudConstants.PDC.J_RECORD_IV), jSONObject.getString("encryptedRecordKey"), null);
    }

    public String toJsonString() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(MiCloudConstants.PDC.J_APPKEY_VERSION, this.appKeyVersion);
            jSONObject.put("encryptedSha1", this.encryptedSha1);
            jSONObject.put(MiCloudConstants.PDC.J_RECORD_IV, this.recordIV);
            jSONObject.put("encryptedRecordKey", this.encryptedRecordKey);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    public String getRecordKey() throws InterruptedException, E2EEException {
        String str = this.mRecordKey;
        if (str != null) {
            return str;
        }
        String strDecryptRecordKey = RecordKeyHelper.decryptRecordKey(this.encryptedRecordKey, this.recordIV, this.appKeyVersion);
        this.mRecordKey = strDecryptRecordKey;
        return strDecryptRecordKey;
    }
}
