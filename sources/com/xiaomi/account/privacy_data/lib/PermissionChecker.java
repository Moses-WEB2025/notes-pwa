package com.xiaomi.account.privacy_data.lib;

import android.content.Context;
import android.os.Process;

/* JADX INFO: loaded from: classes4.dex */
public class PermissionChecker {
    public static boolean hasPermission(Context context, String str) {
        return context.checkPermission(str, Process.myPid(), Process.myUid()) == 0;
    }

    public static boolean hasReadPhoneStatePermission(Context context) {
        return hasPermission(context, "android.permission.READ_PHONE_STATE");
    }

    public static boolean hasAccessNetworkStatePermission(Context context) {
        return hasPermission(context, "android.permission.ACCESS_NETWORK_STATE");
    }

    public static boolean hasBluetoothPermission(Context context) {
        return hasPermission(context, "android.permission.BLUETOOTH");
    }

    public static boolean hasBluetoothConnectStatePermission(Context context) {
        return hasPermission(context, "android.permission.BLUETOOTH_CONNECT");
    }

    public static boolean hasLocalMacAddressPermission(Context context) {
        return hasPermission(context, "android.permission.LOCAL_MAC_ADDRESS");
    }

    public static boolean hasAccessWifiStatePermission(Context context) {
        return hasPermission(context, "android.permission.ACCESS_WIFI_STATE");
    }

    public static boolean hasAccessFineLocationPermission(Context context) {
        return hasPermission(context, "android.permission.ACCESS_COARSE_LOCATION");
    }
}
