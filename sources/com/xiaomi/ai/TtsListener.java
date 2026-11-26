package com.xiaomi.ai;

import android.media.AudioTrack;

/* JADX INFO: loaded from: classes4.dex */
public interface TtsListener {
    void onError(SpeechError speechError);

    void onPCMData(PCMInfo pCMInfo);

    void onPlayFinish();

    void onPlayStart(AudioTrack audioTrack);

    void onTtsGotURL(String str);

    void onTtsTransEnd(boolean z);

    void onTtsTransStart();
}
