package cn.kuaipan.android.kss;

import cn.kuaipan.android.http.IKscDecoder;
import cn.kuaipan.android.utils.RC4;
import java.security.InvalidKeyException;

/* JADX INFO: loaded from: classes.dex */
public class RC4Encoder implements IKscDecoder {
    private final byte[] key;
    private final RC4 rc4;

    @Override // cn.kuaipan.android.http.IKscDecoder
    public boolean canEnd() {
        return true;
    }

    @Override // cn.kuaipan.android.http.IKscDecoder
    public boolean supportRepeat() {
        return true;
    }

    public RC4Encoder(byte[] bArr) throws InvalidKeyException {
        this.key = bArr;
        RC4 rc4 = new RC4();
        this.rc4 = rc4;
        rc4.makeKey(bArr);
    }

    @Override // cn.kuaipan.android.http.IKscDecoder
    public void init() {
        try {
            this.rc4.makeKey(this.key);
        } catch (InvalidKeyException unused) {
        }
    }

    @Override // cn.kuaipan.android.http.IKscDecoder
    public void skip(long j) {
        try {
            this.rc4.makeKey(this.key);
            this.rc4.skip(j);
        } catch (InvalidKeyException unused) {
        }
    }

    @Override // cn.kuaipan.android.http.IKscDecoder
    public void decodeData(byte[] bArr, int i, int i2) {
        this.rc4.genRC4(bArr, i, i2, bArr, i);
    }

    @Override // cn.kuaipan.android.http.IKscDecoder
    /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
    public RC4Encoder m316clone() {
        try {
            return new RC4Encoder(this.key);
        } catch (InvalidKeyException unused) {
            return null;
        }
    }
}
