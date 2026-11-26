package com.xiaomi.miai.auth;

import com.xiaomi.miai.SDKException;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Response;

/* JADX INFO: loaded from: classes4.dex */
public class EnableAuthInterceptor implements Interceptor {
    private final AuthManager authManager;
    private final UserInfo userInfo;

    public EnableAuthInterceptor(AuthManager authManager, UserInfo userInfo) {
        this.authManager = authManager;
        this.userInfo = userInfo;
    }

    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        try {
            return chain.proceed(chain.request().newBuilder().header("Authorization", this.authManager.buildAuthString(this.userInfo)).build());
        } catch (SDKException e) {
            throw new IOException("Build AuthString failed.", e);
        }
    }
}
