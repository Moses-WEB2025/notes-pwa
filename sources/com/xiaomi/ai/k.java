package com.xiaomi.ai;

/* JADX INFO: loaded from: classes4.dex */
/* synthetic */ class k {
    static final /* synthetic */ int[] a;

    static {
        int[] iArr = new int[SpeechEngine.ProcessStage.values().length];
        a = iArr;
        try {
            iArr[SpeechEngine.ProcessStage.STAGE_REQUESTING.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            a[SpeechEngine.ProcessStage.STAGE_ASR.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            a[SpeechEngine.ProcessStage.STAGE_NLP.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            a[SpeechEngine.ProcessStage.STAGE_VP.ordinal()] = 4;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            a[SpeechEngine.ProcessStage.STAGE_VP_REG.ordinal()] = 5;
        } catch (NoSuchFieldError unused5) {
        }
        try {
            a[SpeechEngine.ProcessStage.STAGE_TTS.ordinal()] = 6;
        } catch (NoSuchFieldError unused6) {
        }
        try {
            a[SpeechEngine.ProcessStage.STAGE_VP_DEL.ordinal()] = 7;
        } catch (NoSuchFieldError unused7) {
        }
        try {
            a[SpeechEngine.ProcessStage.STAGE_VP_QRY.ordinal()] = 8;
        } catch (NoSuchFieldError unused8) {
        }
    }
}
