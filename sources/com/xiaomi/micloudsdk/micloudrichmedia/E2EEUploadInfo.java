package com.xiaomi.micloudsdk.micloudrichmedia;

import cn.kuaipan.android.utils.Base64;
import cn.kuaipan.android.utils.TransferFileCipher;
import com.xiaomi.e2ee.E2EEException;
import com.xiaomi.e2ee.appkey.AppKeyInfo;
import com.xiaomi.e2ee.utils.RecordKeyHelper;
import java.security.SecureRandom;
import javax.crypto.Cipher;

/* JADX INFO: loaded from: classes4.dex */
public class E2EEUploadInfo {
    private static final int E2EE_IV_LENGTH = 16;
    public final AppKeyInfo appKeyInfo;
    private Cipher cipher;
    public final String encryptedRecordKey;
    public final String encryptedSha1;
    public final String recordIV;
    public final String recordKey;

    public E2EEUploadInfo(UploadEntity uploadEntity, AppKeyInfo appKeyInfo) throws E2EEException {
        this.appKeyInfo = appKeyInfo;
        String recordIV = getRecordIV();
        this.recordIV = recordIV;
        String recordKey = RecordKeyHelper.getRecordKey();
        this.recordKey = recordKey;
        this.encryptedRecordKey = RecordKeyHelper.encryptRecordKeyAndSign(recordKey, appKeyInfo.decryptAppKey, recordIV);
        this.cipher = TransferFileCipher.getFileCipher(recordKey, recordIV, 1);
        this.encryptedSha1 = getEncryptedSha1(uploadEntity);
    }

    public Cipher getCipher() throws E2EEException {
        return this.cipher;
    }

    public void regenerateCipher() throws E2EEException {
        this.cipher = TransferFileCipher.getFileCipher(this.recordKey, this.recordIV, 1);
    }

    public void regenerateCipher(int i) throws E2EEException {
        if (i == 0) {
            regenerateCipher();
        } else {
            this.cipher = TransferFileCipher.getFileCipher(this.recordKey, TransferFileCipher.calculateIV(i, this.recordIV), 1);
        }
    }

    private String getRecordIV() {
        byte[] bArr = new byte[16];
        new SecureRandom().nextBytes(bArr);
        return Base64.encodeToString(bArr, 11);
    }

    private String getEncryptedSha1(UploadEntity uploadEntity) throws E2EEException {
        return uploadEntity.getEncryptedSha1Hex(TransferFileCipher.getFileCipher(this.recordKey, this.recordIV, 1));
    }
}
