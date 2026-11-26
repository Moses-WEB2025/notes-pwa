package com.xiaomi.micloudsdk.sync;

import android.content.ContentProviderClient;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.util.Log;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import miui.cloud.sync.IRemoteFileSyncLogger;

/* JADX INFO: loaded from: classes4.dex */
class FileSyncLogSender extends SyncLogSender {
    private static final String SYNC_LOG_KEY_AUTHORITY = "sync_log_key_authority";
    private static final String SYNC_LOG_KEY_REMOTE_LOGGER = "sync_log_key_remote_logger";
    private static final String SYNC_LOG_METHOD_OPEN_SYNC_LOG = "OPEN_SYNC_LOG";
    private static final long WAIT_LOGGER_RELEASE_TIME_IN_MILLIS = 10000;
    private final String mAuthority;
    private IRemoteFileSyncLogger mRemoteLogger;
    private final ContentProviderClient mSyncProvider;

    public FileSyncLogSender(ContentProviderClient contentProviderClient, String str) {
        if (contentProviderClient == null) {
            throw new IllegalArgumentException("Must give a valid provider client.");
        }
        this.mSyncProvider = contentProviderClient;
        this.mAuthority = str;
    }

    @Override // com.xiaomi.micloudsdk.sync.SyncLogSender
    void openSyncLog() {
        Bundle bundle = new Bundle();
        bundle.putString("sync_log_key_authority", this.mAuthority);
        IBinder binder = null;
        try {
            binder = this.mSyncProvider.call("OPEN_SYNC_LOG", null, bundle).getBinder("sync_log_key_remote_logger");
        } catch (RemoteException unused) {
            Log.e("SyncLogSender", "open sync log: server is died.");
        }
        if (binder == null) {
            Log.e("SyncLogSender", "open sync log: binder is null.");
            this.mSyncProvider.release();
        } else {
            this.mRemoteLogger = IRemoteFileSyncLogger.Stub.asInterface(binder);
        }
    }

    @Override // com.xiaomi.micloudsdk.sync.SyncLogSender
    void sendLog(String str, String str2) {
        IRemoteFileSyncLogger iRemoteFileSyncLogger = this.mRemoteLogger;
        if (iRemoteFileSyncLogger == null) {
            Log.i(str, str2);
            return;
        }
        try {
            iRemoteFileSyncLogger.syncLog(str, str2);
        } catch (RemoteException unused) {
            Log.e("SyncLogSender", "send log: server is died.");
            Log.i(str, str2);
        }
    }

    @Override // com.xiaomi.micloudsdk.sync.SyncLogSender
    void release() {
        if (this.mRemoteLogger == null) {
            return;
        }
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            this.mRemoteLogger.release(new ResultReceiver(null) { // from class: com.xiaomi.micloudsdk.sync.FileSyncLogSender.1
                @Override // android.os.ResultReceiver
                protected void onReceiveResult(int i, Bundle bundle) {
                    countDownLatch.countDown();
                }
            });
            if (!countDownLatch.await(10000L, TimeUnit.MILLISECONDS)) {
                Log.e("SyncLogSender", "Too long to wait for releasing provider. Release now.");
            }
        } catch (RemoteException unused) {
            Log.e("SyncLogSender", "Remote sync provider is died! Release now.");
        } catch (InterruptedException unused2) {
            Log.e("SyncLogSender", "Release is interrupted by cancel sync.");
            Thread.currentThread().interrupt();
        }
        this.mSyncProvider.release();
        this.mRemoteLogger = null;
    }
}
