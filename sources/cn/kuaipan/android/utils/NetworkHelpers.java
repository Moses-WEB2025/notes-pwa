package cn.kuaipan.android.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.text.TextUtils;
import android.util.Log;
import org.apache.http.HttpHost;

/* JADX INFO: loaded from: classes.dex */
public class NetworkHelpers {
    private static final boolean LOGD = false;
    private static final String LOG_TAG = "NetworkHelpers";
    public static final int TYPE_INVALID = -1;
    public static final int TYPE_MOBILE = 0;
    private static final int TYPE_MOBILE_HIPRI = 5;
    public static final int TYPE_WIFI = 1;

    private NetworkHelpers() {
    }

    public static boolean isNetworkAvailable(Context context, boolean z, boolean z2) {
        if (context == null) {
            return true;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            Log.w(LOG_TAG, "Couldn't get connectivity manager");
            return false;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected() && (z2 || !activeNetworkInfo.isRoaming()) && (z || !isMobile(activeNetworkInfo));
    }

    public static int getCurrentNetType(Context context) {
        NetworkInfo activeNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null) {
            return -1;
        }
        return activeNetworkInfo.getType();
    }

    public static HttpHost getCurrentProxy() {
        if (getCurrentNetType(ContextUtils.getContext()) != 0) {
            return null;
        }
        String defaultHost = Proxy.getDefaultHost();
        int defaultPort = Proxy.getDefaultPort();
        if (TextUtils.isEmpty(defaultHost)) {
            return null;
        }
        return new HttpHost(defaultHost, defaultPort);
    }

    private static boolean isMobile(NetworkInfo networkInfo) {
        int type = networkInfo.getType();
        return type == 0 || (type > 1 && type <= 5);
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            Log.w(LOG_TAG, "couldn't get connectivity manager");
        } else {
            NetworkInfo[] allNetworkInfo = connectivityManager.getAllNetworkInfo();
            if (allNetworkInfo != null) {
                for (NetworkInfo networkInfo : allNetworkInfo) {
                    if (networkInfo.isConnected()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isNetworkRoaming(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            Log.w(LOG_TAG, "couldn't get connectivity manager");
            return false;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.getType() == 0 && activeNetworkInfo.isRoaming();
    }
}
