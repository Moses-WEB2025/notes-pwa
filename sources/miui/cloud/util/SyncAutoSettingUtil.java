package miui.cloud.util;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;

/* JADX INFO: loaded from: classes5.dex */
public class SyncAutoSettingUtil {
    private static final String DEVICE_FEATURE_EXEMPT_MASTER_SYNC_AUTO = "exempt_master_sync_auto";
    private static final String SYNC_AUTO_VERSION_KEY_SUFFIX = "_sync_auto_version";
    public static final int SYNC_AUTO_VERSION_NOT_FOUND_DEFAULT = -1;

    public static boolean getXiaomiGlobalSyncAutomatically() {
        if (DeviceFeatureUtils.hasDeviceFeature("exempt_master_sync_auto")) {
            return true;
        }
        return ContentResolver.getMasterSyncAutomatically();
    }

    public static int getSyncAutomaticallyVersion(Context context, String str, int i) {
        return Settings.Global.getInt(context.getContentResolver(), makeSyncAutomaticallyVersionKey(str), i);
    }

    public static void setSyncAutomaticallyVersion(Context context, String str, int i) {
        Settings.Global.putInt(context.getContentResolver(), makeSyncAutomaticallyVersionKey(str), i);
    }

    private static String makeSyncAutomaticallyVersionKey(String str) {
        return str + SYNC_AUTO_VERSION_KEY_SUFFIX;
    }
}
