package miui.cloud.sync.providers;

import android.content.Context;
import miui.cloud.sync.SyncInfoProviderBase;

/* JADX INFO: loaded from: classes5.dex */
public class CreationSyncInfoProvider extends SyncInfoProviderBase {
    public static final String AUTHORITY = "com.miui.creation.provider";

    public int getSyncedCount(Context context) {
        return -1;
    }

    public int getUnSyncedSecretCount(Context context) {
        return -1;
    }

    public int getUnsyncedCount(Context context) {
        return -1;
    }

    public int getWifiOnlyUnsyncedCount(Context context) {
        return -1;
    }
}
