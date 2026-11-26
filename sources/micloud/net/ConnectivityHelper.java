package micloud.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

/* JADX INFO: loaded from: classes5.dex */
public class ConnectivityHelper {
    private static ConnectivityHelper sInstance;
    private final ConnectivityManager mConnectivityManager;
    private volatile String mMacAddress;
    private final WifiManager mWifiManager;

    public static synchronized ConnectivityHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ConnectivityHelper(context);
        }
        return sInstance;
    }

    private ConnectivityHelper(Context context) {
        Context applicationContext = context.getApplicationContext();
        this.mConnectivityManager = (ConnectivityManager) applicationContext.getSystemService("connectivity");
        this.mWifiManager = (WifiManager) applicationContext.getSystemService("wifi");
    }

    public ConnectivityManager getManager() {
        return this.mConnectivityManager;
    }

    public boolean isNetworkConnected() {
        NetworkInfo activeNetworkInfo = this.mConnectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public boolean isWifiConnected() {
        NetworkInfo activeNetworkInfo = this.mConnectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected() && activeNetworkInfo.getType() == 1;
    }

    public boolean isUnmeteredNetworkConnected() {
        NetworkInfo activeNetworkInfo = this.mConnectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo == null || !activeNetworkInfo.isConnected() || this.mConnectivityManager.isActiveNetworkMetered()) ? false : true;
    }

    public String getMacAddress() {
        String macAddress = this.mMacAddress;
        if (TextUtils.isEmpty(macAddress)) {
            WifiInfo connectionInfo = this.mWifiManager.getConnectionInfo();
            if (connectionInfo != null) {
                macAddress = connectionInfo.getMacAddress();
            }
            if (!TextUtils.isEmpty(macAddress)) {
                this.mMacAddress = macAddress;
            }
        }
        return macAddress;
    }
}
