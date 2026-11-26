package cn.kuaipan.android.kss.upload;

import com.xiaomi.e2ee.E2EEException;
import com.xiaomi.e2ee.appkey.AppKeyInfo;
import com.xiaomi.e2ee.appkey.AppKeyServiceManager;
import com.xiaomi.e2ee.utils.E2EECommonUtils;
import com.xiaomi.e2ee.utils.RecordKeyHelper;
import com.xiaomi.micloudsdk.serverinfo.ServerEncryptInfo;
import com.xiaomi.micloudsdk.utils.MiCloudConstants;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class UploadEncryptInfo {
    private AppKeyInfo mAppKeyInfo;
    private String mEncryptSha1;
    private String mEncryptedRecordKey;
    private final boolean mIsNeedEncrypt;
    private String mRecordIV;
    private String mRecordKey;

    private UploadEncryptInfo(AppKeyInfo appKeyInfo, String str, boolean z, String str2, String str3) {
        this.mAppKeyInfo = appKeyInfo;
        this.mRecordIV = str;
        this.mIsNeedEncrypt = z;
        this.mEncryptedRecordKey = str2;
        this.mRecordKey = str3;
    }

    private UploadEncryptInfo(boolean z) {
        this.mIsNeedEncrypt = z;
    }

    public boolean isNeedEncrypt() {
        return this.mIsNeedEncrypt;
    }

    public AppKeyInfo getAppKeyInfo() {
        return this.mAppKeyInfo;
    }

    public void setEncryptSha1(String str) {
        this.mEncryptSha1 = str;
    }

    public void setRecordIV(String str) {
        this.mRecordIV = str;
    }

    public String getRecordIV() {
        return this.mRecordIV;
    }

    public String getRecordKey() {
        return this.mRecordKey;
    }

    public String getEncryptedSha1() {
        return this.mEncryptSha1;
    }

    public void setEncryptedRecordKeyAndDecrypt(String str) throws InterruptedException, E2EEException {
        this.mEncryptedRecordKey = str;
        this.mRecordKey = RecordKeyHelper.getDecryptRecordKey(str, this.mRecordIV, this.mAppKeyInfo.appKeyVersion);
    }

    public String getEncryptedRecordKey() {
        return this.mEncryptedRecordKey;
    }

    public JSONObject getRequestServerEncryptInfoJson() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(MiCloudConstants.PDC.J_APPKEY_VERSION, this.mAppKeyInfo.appKeyVersion);
        jSONObject.put("encryptedSha1", this.mEncryptSha1);
        jSONObject.put(MiCloudConstants.PDC.J_RECORD_IV, this.mRecordIV);
        jSONObject.put("encryptedRecordKey", this.mEncryptedRecordKey);
        return jSONObject;
    }

    public static class Factory {
        private Factory() {
            throw new IllegalStateException("Factory class can not be instantiated");
        }

        public static UploadEncryptInfo create(boolean z) throws InterruptedException, E2EEException {
            if (z) {
                String randomRecordIV = E2EECommonUtils.getRandomRecordIV();
                AppKeyInfo appKeyInfo = AppKeyServiceManager.getInstance().getAppKeyInfo();
                if (appKeyInfo == null) {
                    throw new E2EEException(E2EEException.E2EE_STATUS_ERROR, "need encryptInfo but appkey is null");
                }
                String recordKey = RecordKeyHelper.getRecordKey();
                return new UploadEncryptInfo(appKeyInfo, randomRecordIV, true, RecordKeyHelper.getEncryptedRecordKey(recordKey, appKeyInfo.decryptAppKey, randomRecordIV), recordKey);
            }
            return new UploadEncryptInfo(false);
        }

        public static UploadEncryptInfo createThumbnailEncryptInfo(String str, ServerEncryptInfo serverEncryptInfo) throws JSONException, InterruptedException, E2EEException {
            AppKeyInfo appKeyInfo = AppKeyServiceManager.getInstance().getAppKeyInfo(serverEncryptInfo.appKeyVersion);
            String decryptRecordKey = RecordKeyHelper.getDecryptRecordKey(serverEncryptInfo.encryptedRecordKey, serverEncryptInfo.recordIV, appKeyInfo.appKeyVersion);
            return new UploadEncryptInfo(appKeyInfo, str, true, RecordKeyHelper.getEncryptedRecordKey(decryptRecordKey, appKeyInfo.decryptAppKey, str), decryptRecordKey);
        }
    }
}
