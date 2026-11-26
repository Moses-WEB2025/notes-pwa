package com.xiaomi.ai.mibrain;

/* JADX INFO: loaded from: classes4.dex */
public class MibrainMp3Decoder extends MibrainDecoder {
    private long mInstance;

    private static native int cancel(long j);

    private static native int deleteInstance(long j);

    private static native long newInstance();

    private static native int start(Object obj, long j);

    private static native int stop(long j);

    static {
        System.loadLibrary("mibraindec");
    }

    public MibrainMp3Decoder(MibrainDecoder.MibrainDecoderIO mibrainDecoderIO) {
        super(mibrainDecoderIO);
    }

    @Override // com.xiaomi.ai.mibrain.MibrainDecoder
    public int init() {
        long jNewInstance = newInstance();
        this.mInstance = jNewInstance;
        return jNewInstance != 0 ? 0 : -1;
    }

    @Override // com.xiaomi.ai.mibrain.MibrainDecoder
    public void release() {
        long j = this.mInstance;
        if (j != 0) {
            deleteInstance(j);
        }
    }

    @Override // com.xiaomi.ai.mibrain.MibrainDecoder
    public int cancel() {
        cancel(this.mInstance);
        return 0;
    }

    @Override // com.xiaomi.ai.mibrain.MibrainDecoder
    public int start() {
        start(this, this.mInstance);
        return 0;
    }

    @Override // com.xiaomi.ai.mibrain.MibrainDecoder
    public int stop() {
        stop(this.mInstance);
        return 0;
    }

    private void nativeCallBackDataOut(byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6) {
        this.mMibrainDecoderIO.write(bArr, i, new MibrainDecoder.AudioInfo(i2, i3, i4, i5, i6));
    }

    private int nativeCallBackDataIn(byte[] bArr, int i) {
        return this.mMibrainDecoderIO.read(bArr, i);
    }
}
