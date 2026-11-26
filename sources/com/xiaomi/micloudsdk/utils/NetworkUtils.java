package com.xiaomi.micloudsdk.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/* JADX INFO: loaded from: classes4.dex */
public class NetworkUtils {
    public static boolean checkWifiAvailability(Context context) {
        NetworkInfo.State state = ((ConnectivityManager) context.getSystemService("connectivity")).getNetworkInfo(1).getState();
        return state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING;
    }

    public static boolean isNetworkAvailable(Context context) {
        NetworkInfo[] allNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager != null && (allNetworkInfo = connectivityManager.getAllNetworkInfo()) != null) {
            for (NetworkInfo networkInfo : allNetworkInfo) {
                if (networkInfo.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isNetworkMetered(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            return false;
        }
        return connectivityManager.isActiveNetworkMetered();
    }
}
