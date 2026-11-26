package com.miui.richeditor;

import android.media.MediaPlayer;
import com.miui.richeditor.style.BaseAudioSpan;

/* JADX INFO: loaded from: classes4.dex */
public interface IAudioEditorContext extends IEditorContext {
    public static final int ERROR_CODE_INVALID = 0;
    public static final int ERROR_CODE_UNDOWNLOADED = 1;

    void cancelPlayAnimIfNeed();

    void clearMediaPlayer();

    String getAudioFileDir();

    String getLastPlayedAudioFile();

    MediaPlayer getMediaPlayer();

    BaseAudioSpan getPlayingAudioSpan();

    boolean isAudioGestureMoveInWave();

    boolean isRecording();

    void onAudioEndAndFileSaved(String str);

    void onAudioTextAppend(String str, boolean z);

    void onPlayAudioError(int i);

    void pauseAudio();

    void pauseAudioAnim();

    void pauseAudioAnimAndRemoveListener();

    void playAudio();

    void requestDisallowInterceptTouchEvent(boolean z);

    void setAudioGestureMoveInWave(boolean z);

    void setLastPlayedAudioFile(String str);

    void startAudioInput();

    void startDragAudioSpan(BaseAudioSpan baseAudioSpan, int[] iArr);

    void startMediaPlayer(BaseAudioSpan baseAudioSpan, String str);

    void stopAudioInput();

    void stopMediaPlayerIfNeed();

    void updateAllAudioSpans();
}
