package com.xiaomi.micloudsdk.sync.utils;

import android.os.Bundle;
import com.xiaomi.micloudsdk.utils.MiCloudConstants;

/* JADX INFO: loaded from: classes4.dex */
public class SyncUtils {
    public static final String TAG = "SyncUtils";

    public static final boolean isForceSync(Bundle bundle) {
        return bundle.getBoolean(MiCloudConstants.SYNC.SYNC_EXTRAS_FORCE, false);
    }

    public static final boolean isIgnoreTemperature(Bundle bundle) {
        return bundle.getBoolean(MiCloudConstants.SYNC.SYNC_EXTRAS_FORCE, false) || bundle.getBoolean("micloud_ignore_temperature", false);
    }

    public static final boolean isIgnoreWifiSettings(Bundle bundle) {
        return bundle.getBoolean(MiCloudConstants.SYNC.SYNC_EXTRAS_FORCE, false) || bundle.getBoolean("micloud_ignore_wifi_settings", false);
    }

    public static final boolean isIgnoreBatteryLow(Bundle bundle) {
        return bundle.getBoolean(MiCloudConstants.SYNC.SYNC_EXTRAS_FORCE, false) || bundle.getBoolean("micloud_ignore_battery_low", false);
    }
}
