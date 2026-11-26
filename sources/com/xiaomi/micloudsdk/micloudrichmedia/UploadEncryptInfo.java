package com.xiaomi.micloudsdk.micloudrichmedia;

import com.xiaomi.e2ee.E2EEException;
import com.xiaomi.e2ee.appkey.AppKeyInfo;
import com.xiaomi.e2ee.utils.CipherUtils;
import com.xiaomi.e2ee.utils.E2EECommonUtils;
import com.xiaomi.e2ee.utils.RecordKeyHelper;
import com.xiaomi.micloudsdk.utils.MiCloudConstants;
import javax.crypto.Cipher;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class UploadEncryptInfo {
    public final AppKeyInfo appKeyInfo;
    private Cipher cipher;
    public final String encryptedRecordKey;
    public final String encryptedSha1;
    public final String recordIV;
    public final String recordKey;

    public UploadEncryptInfo(UploadEntity uploadEntity, AppKeyInfo appKeyInfo) throws E2EEException {
        this.appKeyInfo = appKeyInfo;
        String randomRecordIV = E2EECommonUtils.getRandomRecordIV();
        this.recordIV = randomRecordIV;
        String recordKey = RecordKeyHelper.getRecordKey();
        this.recordKey = recordKey;
        this.encryptedRecordKey = RecordKeyHelper.getEncryptedRecordKey(recordKey, appKeyInfo.decryptAppKey, randomRecordIV);
        this.cipher = CipherUtils.getDecodeFileCipher(recordKey, randomRecordIV, 1);
        this.encryptedSha1 = getEncryptedSha1(uploadEntity);
    }

    public Cipher getCipher() throws E2EEException {
        return this.cipher;
    }

    public void regenerateCipher() throws E2EEException {
        this.cipher = CipherUtils.getDecodeFileCipher(this.recordKey, this.recordIV, 1);
    }

    public void regenerateCipher(int i) throws E2EEException {
        if (i == 0) {
            regenerateCipher();
        } else {
            this.cipher = CipherUtils.getDecodeFileCipher(this.recordKey, E2EECommonUtils.calculateDecodeFileIV(i, this.recordIV), 1);
        }
    }

    public String toJsonString() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(MiCloudConstants.PDC.J_APPKEY_VERSION, this.appKeyInfo.appKeyVersion);
            jSONObject.put("encryptedSha1", this.encryptedSha1);
            jSONObject.put(MiCloudConstants.PDC.J_RECORD_IV, this.recordIV);
            jSONObject.put("encryptedRecordKey", this.encryptedRecordKey);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    private String getEncryptedSha1(UploadEntity uploadEntity) throws E2EEException {
        return uploadEntity.getEncryptedSha1Hex(CipherUtils.getDecodeFileCipher(this.recordKey, this.recordIV, 1));
    }
}
