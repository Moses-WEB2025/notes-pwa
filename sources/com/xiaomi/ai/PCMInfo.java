package com.xiaomi.ai;

/* JADX INFO: loaded from: classes4.dex */
public class PCMInfo {
    int bit;
    int channels;
    boolean eof;
    byte[] pcm;
    int sample_rate;

    public PCMInfo(int i, int i2, int i3, boolean z, byte[] bArr) {
        this.sample_rate = i;
        this.channels = i2;
        this.bit = i3;
        this.eof = z;
        this.pcm = bArr;
    }

    public int getBit() {
        return this.bit;
    }

    public int getChannels() {
        return this.channels;
    }

    public boolean getEOF() {
        return this.eof;
    }

    public byte[] getPCM() {
        return this.pcm;
    }

    public int getSampleRate() {
        return this.sample_rate;
    }
}
