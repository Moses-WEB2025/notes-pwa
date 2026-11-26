package com.xiaomi.ai;

/* JADX INFO: loaded from: classes4.dex */
public interface VoiceprintRecognizeListener {
    void onBeginningOfDataInput();

    void onBufferReceived(byte[] bArr);

    void onEndOfDataInput();

    void onError(SpeechError speechError);

    void onResults(SpeechResult speechResult);

    void onRmsChanged(float f);
}
