package com.xiaomi.miai.network;

import com.xiaomi.miai.SDKEngine;
import com.xiaomi.miai.auth.EnableAuthInterceptor;
import com.xiaomi.miai.auth.UserInfo;
import com.xiaomi.miai.config.ConfigKeys;
import com.xiaomi.miai.config.MiaiSdkConfig;
import com.xiaomi.miai.log.Logger;
import com.xiaomi.miai.utils.Requires;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

/* JADX INFO: loaded from: classes4.dex */
public class HttpClients {
    private final SDKEngine engine;
    private final MiaiSdkConfig sdkConfig;
    private final Logger logger = Logger.getLogger("HttpClients");
    private volatile OkHttpClient sharedClient = null;

    public HttpClients(MiaiSdkConfig miaiSdkConfig, SDKEngine sDKEngine) {
        this.sdkConfig = miaiSdkConfig;
        this.engine = sDKEngine;
    }

    private synchronized OkHttpClient getClient() {
        if (this.sharedClient == null) {
            long jLongValue = ((Long) this.sdkConfig.getOrElse(ConfigKeys.Network.CONNECT_TIMEOUT, 5000L)).longValue();
            long jLongValue2 = ((Long) this.sdkConfig.getOrElse(ConfigKeys.Network.READ_TIMEOUT, 2000L)).longValue();
            this.sharedClient = new OkHttpClient.Builder().connectTimeout(jLongValue, TimeUnit.MILLISECONDS).readTimeout(jLongValue2, TimeUnit.MILLISECONDS).writeTimeout(((Long) this.sdkConfig.getOrElse(ConfigKeys.Network.WRITE_TIMEOUT, 2000L)).longValue(), TimeUnit.MILLISECONDS).addInterceptor(new CommonRequestInterceptor(this.sdkConfig)).build();
        }
        return this.sharedClient;
    }

    public OkHttpClient.Builder builder() {
        return getClient().newBuilder();
    }

    public OkHttpClient.Builder builder(RequestConfig requestConfig) {
        UserInfo userInfo;
        Requires.notNull(requestConfig, "config is null");
        OkHttpClient.Builder builder = builder();
        if (requestConfig.enableAuth) {
            if (requestConfig.userInfo == null) {
                userInfo = (UserInfo) this.sdkConfig.getOrNull(ConfigKeys.USER_INFO);
            } else {
                userInfo = requestConfig.userInfo;
            }
            builder.addInterceptor(new EnableAuthInterceptor(this.engine.getAuthManager(), userInfo));
        }
        if (requestConfig.requestGzip) {
            builder.addInterceptor(new GzipRequestInterceptor());
        }
        return builder;
    }

    public Response httpRequest(Request request) throws IOException {
        Requires.notNull(request, "request is null.");
        return getClient().newCall(request).execute();
    }

    public Response httpRequest(Request request, RequestConfig requestConfig) throws IOException {
        Requires.notNull(request, "request is null.");
        return builder(requestConfig).build().newCall(request).execute();
    }

    public Response httpRequestWithAuth(Request request) throws IOException {
        return httpRequest(request, config().enableAuthoriztion());
    }

    public WebSocket websocketRequest(Request request, WebSocketListener webSocketListener) {
        Requires.notNull(request, "request is null.");
        Requires.notNull(webSocketListener, "websocket listener is null.");
        return getClient().newWebSocket(request, webSocketListener);
    }

    public WebSocket websocketRequest(Request request, RequestConfig requestConfig, WebSocketListener webSocketListener) {
        Requires.notNull(request, "request is null.");
        Requires.notNull(webSocketListener, "websocket listener is null.");
        return builder(requestConfig).build().newWebSocket(request, webSocketListener);
    }

    public WebSocket websocketRequestWithAuth(Request request, WebSocketListener webSocketListener) {
        return websocketRequest(request, config().enableAuthoriztion(), webSocketListener);
    }

    public RequestConfig config() {
        return new RequestConfig();
    }

    public static class RequestConfig {
        private boolean enableAuth;
        private boolean requestGzip;
        private UserInfo userInfo;

        private RequestConfig() {
            this.requestGzip = false;
            this.enableAuth = false;
            this.userInfo = null;
        }

        public RequestConfig enableRequestGzip() {
            this.requestGzip = true;
            return this;
        }

        public RequestConfig enableAuthoriztion() {
            this.enableAuth = true;
            return this;
        }

        public RequestConfig withUser(UserInfo userInfo) {
            this.userInfo = userInfo;
            return this;
        }
    }
}
