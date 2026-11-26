package com.xiaomi.accountsdk.request.intercept;

/* JADX INFO: loaded from: classes4.dex */
public class NetworkInterceptor {

    private static class Holder {
        private static volatile NetworkInterceptCallback sInstance = new EmptyNetworkInterceptCallback();

        private Holder() {
        }
    }

    public static void set(NetworkInterceptCallback networkInterceptCallback) {
        NetworkInterceptCallback unused = Holder.sInstance = networkInterceptCallback;
    }

    public static NetworkInterceptCallback get() {
        return Holder.sInstance;
    }
}
