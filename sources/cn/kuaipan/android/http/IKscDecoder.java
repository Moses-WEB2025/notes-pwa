package cn.kuaipan.android.http;

/* JADX INFO: loaded from: classes.dex */
public interface IKscDecoder extends Cloneable {
    boolean canEnd();

    IKscDecoder clone();

    void decodeData(byte[] bArr, int i, int i2);

    void init();

    void skip(long j);

    boolean supportRepeat();
}
