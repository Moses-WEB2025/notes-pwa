package com.miui.richeditor.style.audio;

import com.miui.richeditor.style.BaseAudioPlayListener;

/* JADX INFO: loaded from: classes4.dex */
public interface BubbleAnim {
    public static final int STATE_INCREASE = 1;
    public static final int STATE_NORMAL = 0;
    public static final int STATE_PLAY = 2;

    void onIncreaseAnimEnd();

    void onPlayAnimEnd();

    void startIncreaseAnim(int i, BaseAudioPlayListener baseAudioPlayListener);

    void startPlayAnim(BaseAudioPlayListener baseAudioPlayListener);

    void stopAnim();
}
