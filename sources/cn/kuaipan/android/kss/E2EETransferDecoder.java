package cn.kuaipan.android.kss;

import android.util.Log;
import cn.kuaipan.android.http.IKscDecoder;
import com.xiaomi.e2ee.E2EEException;
import com.xiaomi.e2ee.stat.MiCloudE2EEEventStatInjector;
import com.xiaomi.e2ee.utils.CipherUtils;
import com.xiaomi.e2ee.utils.E2EECommonUtils;
import javax.crypto.Cipher;

/* JADX INFO: loaded from: classes.dex */
public class E2EETransferDecoder implements IKscDecoder {
    private Cipher mCipher;
    private final String mDecryptAppKey;
    private final int mOpMode;
    private final String mRecordIV;

    @Override // cn.kuaipan.android.http.IKscDecoder
    public boolean canEnd() {
        return true;
    }

    @Override // cn.kuaipan.android.http.IKscDecoder
    public boolean supportRepeat() {
        return true;
    }

    public E2EETransferDecoder(String str, String str2, int i) {
        this.mDecryptAppKey = str;
        this.mRecordIV = str2;
        this.mOpMode = i;
    }

    @Override // cn.kuaipan.android.http.IKscDecoder
    public void init() {
        try {
            this.mCipher = CipherUtils.getDecodeFileCipher(this.mDecryptAppKey, this.mRecordIV, this.mOpMode);
        } catch (E2EEException e) {
            MiCloudE2EEEventStatInjector.getInstance().addDecodeDataErrorEvent(e);
            Log.e("E2EETransferDecoder", "init cipher failed. " + e.getMessage());
        }
    }

    @Override // cn.kuaipan.android.http.IKscDecoder
    public void skip(long j) {
        try {
            this.mCipher = CipherUtils.getDecodeFileCipher(this.mDecryptAppKey, E2EECommonUtils.calculateDecodeFileIV(j, this.mRecordIV), this.mOpMode);
        } catch (E2EEException e) {
            MiCloudE2EEEventStatInjector.getInstance().addDecodeDataErrorEvent(e);
            Log.e("E2EETransferDecoder", "skip cipher failed. " + e.getMessage());
        }
    }

    @Override // cn.kuaipan.android.http.IKscDecoder
    public void decodeData(byte[] bArr, int i, int i2) {
        byte[] bArrUpdate = this.mCipher.update(bArr, i, i2);
        System.arraycopy(bArrUpdate, 0, bArr, 0, bArrUpdate.length);
    }

    @Override // cn.kuaipan.android.http.IKscDecoder
    /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
    public IKscDecoder m314clone() {
        return new E2EETransferDecoder(this.mDecryptAppKey, this.mRecordIV, this.mOpMode);
    }
}
