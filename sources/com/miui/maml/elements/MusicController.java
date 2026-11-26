package com.miui.maml.elements;

import android.content.Context;
import android.media.MediaMetadata;
import android.media.Rating;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.MediaSessionManager;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class MusicController {
    private static final String TAG = "MAML_MusicController";
    private Context mContext;
    private Handler mHandler;
    private MediaController mMediaController;
    private MediaSessionManager mSessionManager;
    private OnClientUpdateListener mUpdateListener;
    private MediaSessionManager.OnActiveSessionsChangedListener mSessionsChangedListener = new MediaSessionManager.OnActiveSessionsChangedListener() { // from class: com.miui.maml.elements.MusicController.1
        @Override // android.media.session.MediaSessionManager.OnActiveSessionsChangedListener
        public void onActiveSessionsChanged(List<MediaController> list) {
            MusicController.this.resetMediaController(list);
            Log.d(MusicController.TAG, "onActiveSessionsChanged");
        }
    };
    private MediaController.Callback mMediaCallback = new MediaController.Callback() { // from class: com.miui.maml.elements.MusicController.2
        @Override // android.media.session.MediaController.Callback
        public void onSessionDestroyed() {
            super.onSessionDestroyed();
            Log.d(MusicController.TAG, "onSessionDestroyed");
            if (MusicController.this.mUpdateListener != null) {
                MusicController.this.mUpdateListener.onSessionDestroyed();
            }
        }

        @Override // android.media.session.MediaController.Callback
        public void onSessionEvent(String str, Bundle bundle) {
            super.onSessionEvent(str, bundle);
            Log.d(MusicController.TAG, "onSessionEvent");
        }

        @Override // android.media.session.MediaController.Callback
        public void onPlaybackStateChanged(PlaybackState playbackState) {
            super.onPlaybackStateChanged(playbackState);
            Log.d(MusicController.TAG, "onPlaybackStateChanged");
            if (MusicController.this.mUpdateListener != null) {
                if (playbackState != null) {
                    MusicController.this.mUpdateListener.onClientPlaybackStateUpdate(playbackState.getState());
                    MusicController.this.mUpdateListener.onClientPlaybackActionUpdate(playbackState.getActions());
                } else {
                    MusicController.this.mUpdateListener.onClientPlaybackStateUpdate(0);
                }
            }
        }

        @Override // android.media.session.MediaController.Callback
        public void onMetadataChanged(MediaMetadata mediaMetadata) {
            super.onMetadataChanged(mediaMetadata);
            Log.d(MusicController.TAG, "onMetadataChanged");
            if (MusicController.this.mUpdateListener != null) {
                MusicController.this.mUpdateListener.onClientMetadataUpdate(mediaMetadata);
            }
        }

        @Override // android.media.session.MediaController.Callback
        public void onQueueChanged(List<MediaSession.QueueItem> list) {
            super.onQueueChanged(list);
        }

        @Override // android.media.session.MediaController.Callback
        public void onQueueTitleChanged(CharSequence charSequence) {
            super.onQueueTitleChanged(charSequence);
        }

        @Override // android.media.session.MediaController.Callback
        public void onExtrasChanged(Bundle bundle) {
            super.onExtrasChanged(bundle);
        }

        @Override // android.media.session.MediaController.Callback
        public void onAudioInfoChanged(MediaController.PlaybackInfo playbackInfo) {
            super.onAudioInfoChanged(playbackInfo);
        }
    };

    public interface OnClientUpdateListener {
        void onClientChange();

        void onClientMetadataUpdate(MediaMetadata mediaMetadata);

        void onClientPlaybackActionUpdate(long j);

        void onClientPlaybackStateUpdate(int i);

        void onSessionDestroyed();
    }

    public MusicController(Context context, Handler handler) {
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        this.mHandler = handler;
        this.mSessionManager = (MediaSessionManager) applicationContext.getSystemService("media_session");
        init();
    }

    public void init() {
        Log.d(TAG, "init");
        resetMediaController(this.mSessionManager.getActiveSessions(null));
        this.mSessionManager.addOnActiveSessionsChangedListener(this.mSessionsChangedListener, null, this.mHandler);
    }

    public void reset() {
        resetMediaController(this.mSessionManager.getActiveSessions(null));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetMediaController(List<MediaController> list) {
        Log.d(TAG, "resetMediaController");
        clearMediaController();
        if (list != null) {
            if (list.size() > 0) {
                this.mMediaController = list.get(0);
            }
            initMediaController();
            updateInfoToListener();
        }
    }

    private void clearMediaController() {
        Log.d(TAG, "clearMediaController");
        if (this.mMediaController != null) {
            OnClientUpdateListener onClientUpdateListener = this.mUpdateListener;
            if (onClientUpdateListener != null) {
                onClientUpdateListener.onClientChange();
            }
            try {
                this.mMediaController.unregisterCallback(this.mMediaCallback);
            } catch (Exception unused) {
                Log.e(TAG, "unregister MediaController.Callback failed");
            }
            this.mMediaController = null;
        }
    }

    private void initMediaController() {
        Log.d(TAG, "initMediaController");
        MediaController mediaController = this.mMediaController;
        if (mediaController != null) {
            try {
                mediaController.registerCallback(this.mMediaCallback, this.mHandler);
            } catch (Exception unused) {
                Log.e(TAG, "register MediaController.Callback failed");
            }
        }
    }

    private void updateInfoToListener() {
        OnClientUpdateListener onClientUpdateListener;
        Log.d(TAG, "updateInfoToListener");
        if (this.mMediaController == null || (onClientUpdateListener = this.mUpdateListener) == null) {
            return;
        }
        onClientUpdateListener.onClientChange();
        PlaybackState playbackState = this.mMediaController.getPlaybackState();
        if (playbackState != null) {
            this.mUpdateListener.onClientPlaybackStateUpdate(playbackState.getState());
        }
        this.mUpdateListener.onClientMetadataUpdate(this.mMediaController.getMetadata());
    }

    public long getEstimatedMediaPosition() {
        PlaybackState playbackState;
        MediaController mediaController = this.mMediaController;
        if (mediaController == null || (playbackState = mediaController.getPlaybackState()) == null) {
            return 0L;
        }
        return playbackState.getPosition();
    }

    public String getClientPackageName() {
        MediaController mediaController = this.mMediaController;
        if (mediaController != null) {
            return mediaController.getPackageName();
        }
        return null;
    }

    public boolean seekTo(long j) {
        try {
            MediaController mediaController = this.mMediaController;
            if (mediaController == null) {
                return false;
            }
            mediaController.getTransportControls().seekTo(j);
            return true;
        } catch (Exception e) {
            Log.w(TAG, " seekTo failed: " + e);
            return false;
        }
    }

    public void rating(Rating rating) {
        try {
            MediaController mediaController = this.mMediaController;
            if (mediaController != null) {
                mediaController.getTransportControls().setRating(rating);
            }
        } catch (Exception e) {
            Log.w(TAG, "RATING_KEY_BY_USER: failed: " + e);
        }
    }

    public boolean sendMediaKeyEvent(int i, int i2) {
        try {
            if (this.mMediaController == null) {
                return false;
            }
            KeyEvent keyEvent = new KeyEvent(i, i2);
            keyEvent.setSource(4098);
            return this.mMediaController.dispatchMediaButtonEvent(keyEvent);
        } catch (Exception e) {
            Log.w(TAG, "Send media key event failed: " + e);
            return false;
        }
    }

    public void registerListener(OnClientUpdateListener onClientUpdateListener) {
        this.mUpdateListener = onClientUpdateListener;
        updateInfoToListener();
    }

    public void unregisterListener() {
        this.mUpdateListener = null;
    }

    public void finish() {
        Log.d(TAG, "finish");
        this.mSessionManager.removeOnActiveSessionsChangedListener(this.mSessionsChangedListener);
        clearMediaController();
    }

    public boolean isMusicActive() {
        PlaybackState playbackState;
        MediaController mediaController = this.mMediaController;
        if (mediaController == null || (playbackState = mediaController.getPlaybackState()) == null) {
            return false;
        }
        int state = playbackState.getState();
        return state == 3 || state == 6;
    }
}
