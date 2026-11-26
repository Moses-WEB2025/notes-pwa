package com.xiaomi.verificationsdk.internal;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

/* JADX INFO: loaded from: classes4.dex */
public class NetWorkUtils {
    public static Bundle parse(String str) {
        String str2 = new String(str);
        int iIndexOf = str2.indexOf(63);
        if (iIndexOf <= 0) {
            return null;
        }
        String strSubstring = str2.substring(iIndexOf + 1);
        if (strSubstring.startsWith("code=") || strSubstring.startsWith("errorCode=") || strSubstring.startsWith("errorStatus=")) {
            return parseUrl(str2);
        }
        return null;
    }

    private static Bundle parseUrl(String str) {
        Bundle bundle = new Bundle();
        if (str != null) {
            Uri uri = Uri.parse(str);
            for (String str2 : uri.getQueryParameterNames()) {
                bundle.putString(str2, uri.getQueryParameter(str2));
            }
        }
        return bundle;
    }

    public static boolean isNetworkAvalible(Context context) {
        NetworkInfo[] allNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager != null && (allNetworkInfo = connectivityManager.getAllNetworkInfo()) != null) {
            for (NetworkInfo networkInfo : allNetworkInfo) {
                if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }
}
