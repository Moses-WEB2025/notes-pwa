package cn.kuaipan.android.kss;

import cn.kuaipan.android.http.IKscDecoder;

/* JADX INFO: loaded from: classes.dex */
public class EmptyDecoder implements IKscDecoder {
    @Override // cn.kuaipan.android.http.IKscDecoder
    public boolean canEnd() {
        return true;
    }

    @Override // cn.kuaipan.android.http.IKscDecoder
    /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
    public IKscDecoder m315clone() {
        return this;
    }

    @Override // cn.kuaipan.android.http.IKscDecoder
    public void decodeData(byte[] bArr, int i, int i2) {
    }

    @Override // cn.kuaipan.android.http.IKscDecoder
    public void init() {
    }

    @Override // cn.kuaipan.android.http.IKscDecoder
    public void skip(long j) {
    }

    @Override // cn.kuaipan.android.http.IKscDecoder
    public boolean supportRepeat() {
        return true;
    }
}
