package com.xiaomi.ai;

/* JADX INFO: loaded from: classes4.dex */
public interface NlpListener {
    void onError(SpeechError speechError);

    void onResult(SpeechResult speechResult);
}
