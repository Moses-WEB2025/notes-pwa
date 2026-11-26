package com.xiaomi.micloudsdk.sync;

import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Log;

/* JADX INFO: loaded from: classes4.dex */
class SyncStatUtil {
    private static final String TAG = "SyncStatUtil";

    SyncStatUtil() {
    }

    public static void statOnSyncStart(Context context, boolean z) {
        performSyncPhoneStateStat(context, true, z);
    }

    public static void statOnSyncEnd(Context context, boolean z, boolean z2, String str, String str2, long j, long j2, long j3, long j4) {
        performSyncTimeStat(context, j3, j4, z2);
        performSyncResultStat(context, z, z2, str, str2, j, j2);
        performSyncPhoneStateStat(context, false, z);
    }

    private static void performSyncPhoneStateStat(Context context, boolean z, boolean z2) {
        Log.d(TAG, "performSyncPhoneState: ");
        ContentValues contentValues = new ContentValues();
        contentValues.put(MiCloudStatConstantsV2.SYNC_EXTRAS_FORCE, Boolean.valueOf(z2));
        contentValues.put(MiCloudStatConstantsV2.STAT_KEY_SYNC_START, Boolean.valueOf(z));
        insertStatValueInternal(context, MiCloudStatConstantsV2.OPEN_SYNC_PHONE_STATE, contentValues);
    }

    private static void performSyncResultStat(Context context, boolean z, boolean z2, String str, String str2, long j, long j2) {
        Log.d(TAG, "performSyncResultStat: ");
        ContentValues contentValues = new ContentValues();
        contentValues.put("micloud_ignore_temperature", (Boolean) false);
        contentValues.put("micloud_ignore_wifi_settings", (Boolean) false);
        contentValues.put("micloud_ignore_battery_low", (Boolean) false);
        contentValues.put(MiCloudStatConstantsV2.STAT_KEY_SYNC_RETRY, (Boolean) true);
        contentValues.put(MiCloudStatConstantsV2.SYNC_EXTRAS_FORCE, Boolean.valueOf(z));
        contentValues.put(MiCloudStatConstantsV2.STAT_KEY_SYNC_REASON, str);
        contentValues.put(MiCloudStatConstantsV2.STAT_KEY_SYNC_IS_SUCCESSFUL, Boolean.valueOf(z2));
        contentValues.put(MiCloudStatConstantsV2.STAT_KEY_SYNC_AUTHORITY, str2);
        if (j != -1 && j2 != -1) {
            contentValues.put(MiCloudStatConstantsV2.STAT_KEY_UNSYNCED_COUNT_BEFORE_SYNC, Long.valueOf(j));
            contentValues.put(MiCloudStatConstantsV2.STAT_KEY_UNSYNCED_COUNT_AFTER_SYNC, Long.valueOf(j2));
        }
        insertStatValueInternal(context, MiCloudStatConstantsV2.OPEN_SYNC_RESULT_URI, contentValues);
    }

    private static void performSyncTimeStat(Context context, long j, long j2, boolean z) {
        Log.d(TAG, "performSyncTimeStat: ");
        ContentValues contentValues = new ContentValues();
        contentValues.put(MiCloudStatConstantsV2.STAT_KEY_SYNC_TIME_CONSUME, Long.valueOf(j2 - j));
        contentValues.put(MiCloudStatConstantsV2.STAT_KEY_SYNC_TIME_START_IN_MILLIS, Long.valueOf(j));
        contentValues.put(MiCloudStatConstantsV2.STAT_KEY_SYNC_TIME_END_IN_MILLIS, Long.valueOf(j2));
        contentValues.put(MiCloudStatConstantsV2.STAT_KEY_SYNC_IS_SUCCESSFUL, Boolean.valueOf(z));
        insertStatValueInternal(context, MiCloudStatConstantsV2.OPEN_SYNC_TIME_CONSUME, contentValues);
    }

    private static void insertStatValueInternal(Context context, Uri uri, ContentValues contentValues) {
        ContentProviderClient contentProviderClientAcquireContentProviderClient = context.getContentResolver().acquireContentProviderClient(uri);
        try {
            if (contentProviderClientAcquireContentProviderClient == null) {
                Log.i(TAG, "CloudService version is too low.");
            } else {
                contentProviderClientAcquireContentProviderClient.insert(uri, contentValues);
            }
        } catch (RemoteException unused) {
            Log.e(TAG, "CloudService stat provider is unavailable.");
        } finally {
            contentProviderClientAcquireContentProviderClient.release();
        }
    }
}
