package com.xiaomi.micloudsdk.request.utils;

import android.content.Context;
import com.xiaomi.micloudsdk.data.IAuthToken;
import micloud.compat.independent.request.QueryAuthTokenException;
import micloud.compat.independent.request.RequestEnvBuilderCompat;

/* JADX INFO: loaded from: classes4.dex */
public class RequestContext {
    private static Context sContext;
    private static String sRegion;
    private static RequestEnv sRequestEnv = RequestEnvBuilderCompat.build();

    public interface RequestEnv {
        String getAccountName();

        long getAutoRetryInterval();

        int getMaxRetryCount();

        String getUserAgent();

        void invalidateAuthToken();

        IAuthToken queryAuthToken() throws QueryAuthTokenException;

        String queryEncryptedAccountName();

        boolean shouldUpdateHost();
    }

    public static void init(Context context) {
        sContext = context;
    }

    public static Context getContext() {
        Context context = sContext;
        if (context != null) {
            return context;
        }
        throw new IllegalStateException("sContext=null! Please call Request.init(Context) at Application onCreate");
    }

    public static void setRegion(String str) {
        sRegion = str;
    }

    public static String getRegion() {
        return sRegion;
    }

    public static void setRequestEnv(RequestEnv requestEnv) {
        sRequestEnv = requestEnv;
    }

    public static RequestEnv getRequestEnv() {
        RequestEnv requestEnv = sRequestEnv;
        if (requestEnv != null) {
            return requestEnv;
        }
        throw new IllegalStateException("RequestEnv has not been initialized yet, please call Request.setRequestEnv(RequestEnv) first!");
    }

    public static String getUserAgent() {
        RequestEnv requestEnv = sRequestEnv;
        if (requestEnv == null) {
            throw new IllegalStateException("RequestEnv has not been initialized yet, please call Request.setRequestEnv(RequestEnv) first!");
        }
        return requestEnv.getUserAgent();
    }
}
