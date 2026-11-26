package com.xiaomi.ai;

/* JADX INFO: loaded from: classes4.dex */
public interface VoiceprintQueryListener {
    void onError(SpeechError speechError);

    void onResults(SpeechResult speechResult);
}
