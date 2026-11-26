package com.bumptech.glide.manager;

import android.content.Context;
import android.util.Log;
import androidx.core.content.ContextCompat;

/* JADX INFO: loaded from: classes.dex */
public class DefaultConnectivityMonitorFactory implements ConnectivityMonitorFactory {
    private static final String NETWORK_PERMISSION = "android.permission.ACCESS_NETWORK_STATE";
    private static final String TAG = "ConnectivityMonitor";

    @Override // com.bumptech.glide.manager.ConnectivityMonitorFactory
    public ConnectivityMonitor build(Context context, ConnectivityMonitor.ConnectivityListener connectivityListener) {
        String str;
        boolean z = ContextCompat.checkSelfPermission(context, NETWORK_PERMISSION) == 0;
        if (Log.isLoggable(TAG, 3)) {
            if (z) {
                str = "ACCESS_NETWORK_STATE permission granted, registering connectivity monitor";
            } else {
                str = "ACCESS_NETWORK_STATE permission missing, cannot register connectivity monitor";
            }
            Log.d(TAG, str);
        }
        if (z) {
            return new DefaultConnectivityMonitor(context, connectivityListener);
        }
        return new NullConnectivityMonitor();
    }
}
