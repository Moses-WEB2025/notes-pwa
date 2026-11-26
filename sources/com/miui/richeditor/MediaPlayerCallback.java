package com.miui.richeditor;

import android.media.MediaPlayer;
import com.miui.notes.base.utils.Logger;
import defpackage.R2;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes4.dex */
class MediaPlayerCallback implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener {
    private static final String TAG = "MediaPlayerCallback";
    private WeakReference<RichEditTextView> mAudioEditorRef;

    public MediaPlayerCallback(RichEditTextView richEditTextView) {
        this.mAudioEditorRef = new WeakReference<>(richEditTextView);
    }

    @Override // android.media.MediaPlayer.OnCompletionListener
    public void onCompletion(MediaPlayer mediaPlayer) {
        if (this.mAudioEditorRef.get() == null || !this.mAudioEditorRef.get().isSameComplete()) {
            return;
        }
        this.mAudioEditorRef.get().cancelPlayAnimIfNeed();
        this.mAudioEditorRef.get().clearMediaPlayer();
    }

    @Override // android.media.MediaPlayer.OnErrorListener
    public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        Logger.INSTANCE.d(TAG, "OnError - Error code: " + i + " Extra code: " + i2);
        if (i == -1010) {
            Logger.INSTANCE.d(TAG, "MEDIA_ERROR_UNSUPPORTED");
        } else if (i == -1007) {
            Logger.INSTANCE.d(TAG, "MEDIA_ERROR_MALFORMED");
        } else if (i == -1004) {
            Logger.INSTANCE.d(TAG, "MEDIA_ERROR_IO");
        } else if (i == -110) {
            Logger.INSTANCE.d(TAG, "MEDIA_ERROR_TIMED_OUT");
        } else if (i == 1) {
            Logger.INSTANCE.d(TAG, "MEDIA_ERROR_UNKNOWN");
        } else if (i == 100) {
            Logger.INSTANCE.d(TAG, "MEDIA_ERROR_SERVER_DIED");
        } else if (i == 200) {
            Logger.INSTANCE.d(TAG, "MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK");
        }
        if (i2 == 1) {
            Logger.INSTANCE.d(TAG, "MEDIA_INFO_UNKNOWN");
            return false;
        }
        if (i2 == 3) {
            Logger.INSTANCE.d(TAG, "MEDIA_INFO_VIDEO_RENDERING_START");
            return false;
        }
        switch (i2) {
            case 700:
                Logger.INSTANCE.d(TAG, "MEDIA_INFO_VIDEO_TRACK_LAGGING");
                return false;
            case 701:
                break;
            case 702:
                Logger.INSTANCE.d(TAG, "MEDIA_INFO_BUFFERING_END");
                return false;
            default:
                switch (i2) {
                    case 800:
                        Logger.INSTANCE.d(TAG, "MEDIA_INFO_BAD_INTERLEAVING");
                        break;
                    case R2.attr.column_count /* 801 */:
                        Logger.INSTANCE.d(TAG, "MEDIA_INFO_NOT_SEEKABLE");
                        break;
                }
                return false;
        }
        Logger.INSTANCE.d(TAG, "MEDIA_INFO_METADATA_UPDATE");
        return false;
    }

    @Override // android.media.MediaPlayer.OnPreparedListener
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
        if (this.mAudioEditorRef.get() != null) {
            this.mAudioEditorRef.get().onPlayingRecord(true);
        }
    }
}
