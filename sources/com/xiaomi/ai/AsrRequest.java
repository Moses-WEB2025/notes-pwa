package com.xiaomi.ai;

/* JADX INFO: loaded from: classes4.dex */
public class AsrRequest {
    public static final int OPUS_BITRATES_32K = 32000;
    public static final int OPUS_BITRATES_64K = 64000;
    public static final int OPUS_FRAMESIZE_1280 = 1280;
    public static final int OPUS_FRAMESIZE_640 = 640;
    public static final short USE_ENCODER_BV32_FLOAT = 2;
    public static final short USE_ENCODER_OPUS = 4;
    public static final short USE_ENCODER_PCM = 1;
    boolean a;
    boolean b;
    String f;
    String k;
    NlpRequest n;
    String vendorStr;
    boolean c = true;
    DataInputMode d = DataInputMode.DATA_INPUT_MODE_RECORDER;
    VadMode e = VadMode.VAD_MODE_LOCAL;
    int g = 0;
    short h = 0;
    int i = 0;
    int j = 0;
    boolean l = false;
    int m = 0;

    public enum DataInputMode {
        DATA_INPUT_MODE_RECORDER,
        DATA_INPUT_MODE_BUFFER
    }

    public enum VadMode {
        VAD_MODE_LOCAL,
        VAD_MODE_CLOUD
    }

    String a() {
        return this.vendorStr;
    }

    public void enableEncoder(boolean z) {
        this.h = (short) (z ? this.h | 256 : this.h & 65279);
    }

    public void setDataInputMode(DataInputMode dataInputMode) {
        this.d = dataInputMode;
    }

    public void setDialect(String str) {
        this.k = str;
    }

    public void setDisableAsrTimeout(boolean z) {
        this.l = z;
    }

    public void setEncodeMode(short s, int i, int i2) {
        this.h = (short) (this.h | s);
        if (s == 4) {
            this.i = i;
            this.j = i2;
        }
    }

    public void setExtraNlp(NlpRequest nlpRequest) {
        this.n = nlpRequest;
    }

    public void setForSai(boolean z) {
        this.b = z;
    }

    public void setLang(String str) {
        this.f = str;
    }

    public void setMaxAudioTime(int i) {
        this.m = i;
    }

    public void setPreAsrTrack(int i) {
        this.g = i;
    }

    public void setRemoveEndPunctuation(boolean z) {
        this.c = z;
    }

    public void setUseVad(boolean z) {
        this.a = z;
    }

    public void setVadMode(VadMode vadMode) {
        this.e = vadMode;
    }
}
