package com.xiaomi.ai.streamplayer;

import com.xiaomi.ai.mibrain.MibrainDecoder;
import com.xiaomi.ai.utils.Log;
import java.io.IOException;

/* JADX INFO: loaded from: classes4.dex */
class g implements MibrainDecoder.MibrainDecoderIO {
    final /* synthetic */ f a;

    g(f fVar) {
        this.a = fVar;
    }

    @Override // com.xiaomi.ai.mibrain.MibrainDecoder.MibrainDecoderIO
    public int read(byte[] bArr, int i) {
        try {
            return this.a.c.read(bArr, 0, i);
        } catch (IOException e) {
            Log.e("Mp3SoftDecoder", "MibrainMp3Decoder read ", e);
            return -1;
        }
    }

    @Override // com.xiaomi.ai.mibrain.MibrainDecoder.MibrainDecoderIO
    public int write(byte[] bArr, int i, MibrainDecoder.AudioInfo audioInfo) {
        a.C0118a c0118a = new a.C0118a();
        c0118a.b = audioInfo.chinels;
        c0118a.c = audioInfo.bit;
        c0118a.a = audioInfo.sample_rate;
        byte[] bArr2 = new byte[i];
        System.arraycopy(bArr, 0, bArr2, 0, i);
        c0118a.e = bArr2;
        try {
            this.a.e.put(c0118a);
            return i;
        } catch (InterruptedException e) {
            Log.e("Mp3SoftDecoder", "e", e);
            return i;
        }
    }
}
