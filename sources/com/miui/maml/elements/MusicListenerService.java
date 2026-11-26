package com.miui.maml.elements;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.RemoteController;
import android.os.Binder;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import com.miui.notes.ai.stat.AITextWidgetStat;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* JADX INFO: loaded from: classes3.dex */
@Deprecated
public class MusicListenerService extends NotificationListenerService implements RemoteController.OnClientUpdateListener {
    public static final String ACTION = "android.service.notification.MusicListenerService";
    private static final int BITMAP_HEIGHT = 1024;
    private static final int BITMAP_WIDTH = 1024;
    private static final String LOG_TAG = "MusicListenerService";
    private IBinder mBinder = new RCBinder();
    private List<WeakReference<RemoteController.OnClientUpdateListener>> mClientUpdateListeners = new CopyOnWriteArrayList();
    private Context mContext;
    private RemoteController mRemoteController;
    private boolean mRemoteControllerEnabled;

    public void onClientFolderInfoBrowsedPlayer(String str) {
    }

    public void onClientNowPlayingContentChange() {
    }

    public void onClientPlayItemResponse(boolean z) {
    }

    public void onClientUpdateNowPlayingEntries(long[] jArr) {
    }

    @Override // android.service.notification.NotificationListenerService, android.app.Service
    public IBinder onBind(Intent intent) {
        if (intent.getAction().equals(ACTION)) {
            Log.d(LOG_TAG, "onBind: success");
            return this.mBinder;
        }
        Log.d(LOG_TAG, "onBind: fail");
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        this.mContext = getApplicationContext();
        this.mRemoteController = new RemoteController(this.mContext, this);
    }

    @Override // android.service.notification.NotificationListenerService, android.app.Service
    public void onDestroy() {
        disableRemoteController();
    }

    @Override // android.service.notification.NotificationListenerService
    public void onNotificationRemoved(StatusBarNotification statusBarNotification) {
        super.onNotificationRemoved(statusBarNotification);
    }

    @Override // android.service.notification.NotificationListenerService
    public void onNotificationPosted(StatusBarNotification statusBarNotification) {
        super.onNotificationPosted(statusBarNotification);
        Log.d(LOG_TAG, "onNotificationPosted: pkg = " + statusBarNotification.getPackageName());
    }

    private void enableRemoteController() {
        if (this.mRemoteControllerEnabled) {
            return;
        }
        AudioManager audioManager = (AudioManager) this.mContext.getSystemService(AITextWidgetStat.TYPE_AUDIO);
        RemoteController remoteController = new RemoteController(this.mContext, this);
        this.mRemoteController = remoteController;
        try {
            this.mRemoteControllerEnabled = audioManager.registerRemoteController(remoteController);
        } catch (Exception e) {
            Log.w(LOG_TAG, "fail to register RemoteController!", e);
        }
        if (this.mRemoteControllerEnabled) {
            this.mRemoteController.setArtworkConfiguration(1024, 1024);
            this.mRemoteController.setSynchronizationMode(1);
        } else {
            Log.w(LOG_TAG, "fail to register RemoteController!");
        }
    }

    private void disableRemoteController() {
        if (this.mRemoteControllerEnabled) {
            ((AudioManager) this.mContext.getSystemService(AITextWidgetStat.TYPE_AUDIO)).unregisterRemoteController(this.mRemoteController);
            this.mRemoteControllerEnabled = false;
        }
    }

    public void registerClientUpdateListener(RemoteController.OnClientUpdateListener onClientUpdateListener) {
        enableRemoteController();
        Iterator<WeakReference<RemoteController.OnClientUpdateListener>> it = this.mClientUpdateListeners.iterator();
        while (it.hasNext()) {
            RemoteController.OnClientUpdateListener onClientUpdateListener2 = it.next().get();
            if (onClientUpdateListener2 != null && onClientUpdateListener2.equals(onClientUpdateListener)) {
                return;
            }
        }
        this.mClientUpdateListeners.add(new WeakReference<>(onClientUpdateListener));
    }

    public void unregisterClientUpdateListener(RemoteController.OnClientUpdateListener onClientUpdateListener) {
        for (WeakReference<RemoteController.OnClientUpdateListener> weakReference : this.mClientUpdateListeners) {
            RemoteController.OnClientUpdateListener onClientUpdateListener2 = weakReference.get();
            if (onClientUpdateListener2 == null || onClientUpdateListener2.equals(onClientUpdateListener)) {
                this.mClientUpdateListeners.remove(weakReference);
            }
        }
        if (this.mClientUpdateListeners.isEmpty()) {
            disableRemoteController();
        }
    }

    public class RCBinder extends Binder {
        public RCBinder() {
        }

        public MusicListenerService getService() {
            return MusicListenerService.this;
        }
    }

    @Override // android.media.RemoteController.OnClientUpdateListener
    public void onClientChange(boolean z) {
        for (WeakReference<RemoteController.OnClientUpdateListener> weakReference : this.mClientUpdateListeners) {
            RemoteController.OnClientUpdateListener onClientUpdateListener = weakReference.get();
            if (onClientUpdateListener != null) {
                onClientUpdateListener.onClientChange(z);
            } else {
                this.mClientUpdateListeners.remove(weakReference);
            }
        }
        if (this.mClientUpdateListeners.isEmpty()) {
            disableRemoteController();
        }
    }

    @Override // android.media.RemoteController.OnClientUpdateListener
    public void onClientMetadataUpdate(RemoteController.MetadataEditor metadataEditor) {
        for (WeakReference<RemoteController.OnClientUpdateListener> weakReference : this.mClientUpdateListeners) {
            RemoteController.OnClientUpdateListener onClientUpdateListener = weakReference.get();
            if (onClientUpdateListener != null) {
                onClientUpdateListener.onClientMetadataUpdate(metadataEditor);
            } else {
                this.mClientUpdateListeners.remove(weakReference);
            }
        }
        if (this.mClientUpdateListeners.isEmpty()) {
            disableRemoteController();
        }
    }

    @Override // android.media.RemoteController.OnClientUpdateListener
    public void onClientPlaybackStateUpdate(int i) {
        for (WeakReference<RemoteController.OnClientUpdateListener> weakReference : this.mClientUpdateListeners) {
            RemoteController.OnClientUpdateListener onClientUpdateListener = weakReference.get();
            if (onClientUpdateListener != null) {
                onClientUpdateListener.onClientPlaybackStateUpdate(i);
            } else {
                this.mClientUpdateListeners.remove(weakReference);
            }
        }
        if (this.mClientUpdateListeners.isEmpty()) {
            disableRemoteController();
        }
    }

    @Override // android.media.RemoteController.OnClientUpdateListener
    public void onClientPlaybackStateUpdate(int i, long j, long j2, float f) {
        for (WeakReference<RemoteController.OnClientUpdateListener> weakReference : this.mClientUpdateListeners) {
            RemoteController.OnClientUpdateListener onClientUpdateListener = weakReference.get();
            if (onClientUpdateListener != null) {
                onClientUpdateListener.onClientPlaybackStateUpdate(i, j, j2, f);
            } else {
                this.mClientUpdateListeners.remove(weakReference);
            }
        }
        if (this.mClientUpdateListeners.isEmpty()) {
            disableRemoteController();
        }
    }

    @Override // android.media.RemoteController.OnClientUpdateListener
    public void onClientTransportControlUpdate(int i) {
        for (WeakReference<RemoteController.OnClientUpdateListener> weakReference : this.mClientUpdateListeners) {
            RemoteController.OnClientUpdateListener onClientUpdateListener = weakReference.get();
            if (onClientUpdateListener != null) {
                onClientUpdateListener.onClientTransportControlUpdate(i);
            } else {
                this.mClientUpdateListeners.remove(weakReference);
            }
        }
        if (this.mClientUpdateListeners.isEmpty()) {
            disableRemoteController();
        }
    }

    public RemoteController getRemoteController() {
        return this.mRemoteController;
    }
}
