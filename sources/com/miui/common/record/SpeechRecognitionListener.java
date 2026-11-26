package com.miui.common.record;

/* JADX INFO: loaded from: classes2.dex */
public interface SpeechRecognitionListener {
    void onAsrAppendTextResult(String str, boolean z);

    void onAsrMaxAmplitudeUpdate(short s);

    void onAsrSaveAudioFileName(String str);
}
