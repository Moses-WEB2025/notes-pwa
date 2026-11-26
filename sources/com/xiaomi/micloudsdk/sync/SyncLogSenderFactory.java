package com.xiaomi.micloudsdk.sync;

import android.content.ContentProviderClient;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import com.xiaomi.micloudsdk.utils.MiCloudConstants;

/* JADX INFO: loaded from: classes4.dex */
class SyncLogSenderFactory {
    private static final Uri URI_SYNC_LOG_PROVIDER = MiCloudConstants.SYNC_LOG.URI_PROVIDER;

    private SyncLogSenderFactory() {
    }

    static SyncLogSender newSyncLogSender(Context context, String str) {
        ContentProviderClient contentProviderClientAcquireContentProviderClient = context.getContentResolver().acquireContentProviderClient(URI_SYNC_LOG_PROVIDER);
        if (contentProviderClientAcquireContentProviderClient == null) {
            Log.i("SyncLogSender", "Create sync log sender: logcat.");
            return new LogcatSyncLogSender();
        }
        Log.i("SyncLogSender", "Create sync log sender: file.");
        return new FileSyncLogSender(contentProviderClientAcquireContentProviderClient, str);
    }
}
