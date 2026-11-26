package miui.cloud.sync.providers;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import miui.cloud.sync.SyncInfoProviderBase;

/* JADX INFO: loaded from: classes5.dex */
public class BluetoothSyncInfoProvider extends SyncInfoProviderBase {
    public static final String AUTHORITY = "com.android.bluetooth.ble.app.headsetdata.provider";
    private static final String TAG = "BluetoothSyncInfoProvider";
    private static final Uri URI_UNSYNCED = Uri.parse("content://com.android.bluetooth.ble.app.headsetdata.provider/unsynceddata");
    private static final Uri URI_SYNCED = Uri.parse("content://com.android.bluetooth.ble.app.headsetdata.provider/synceddata");

    public int getWifiOnlyUnsyncedCount(Context context) {
        return 0;
    }

    public int getSyncedCount(Context context) {
        return getBluetoothCount(context, URI_SYNCED, null, null);
    }

    public int getUnsyncedCount(Context context) {
        return getBluetoothCount(context, URI_UNSYNCED, null, null);
    }

    private int getBluetoothCount(Context context, Uri uri, String str, String[] strArr) {
        Log.d(TAG, "getBluetoothCount, uri: " + uri);
        Cursor cursorQuery = context.getContentResolver().query(uri, new String[]{"id"}, str, strArr, null);
        if (cursorQuery != null) {
            try {
                int count = cursorQuery.getCount();
                cursorQuery.close();
                Log.d(TAG, "queryDirtyCount = " + count);
                return count;
            } catch (Throwable th) {
                cursorQuery.close();
                throw th;
            }
        }
        Log.d(TAG, "queryDirtyCount: cursor is null");
        return 0;
    }
}
