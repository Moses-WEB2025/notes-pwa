package com.xiaomi.miai;

import com.xiaomi.miai.auth.AuthManager;
import com.xiaomi.miai.config.MiaiSdkConfig;
import com.xiaomi.miai.network.HttpClients;

/* JADX INFO: loaded from: classes4.dex */
public class SDKEngine {
    private final AuthManager authManager;
    private final MiaiSdkConfig config;
    private final HttpClients httpClients;

    private static class Holder {
        static SDKEngine instance = new SDKEngine();

        private Holder() {
        }
    }

    public static SDKEngine get() {
        return Holder.instance;
    }

    private SDKEngine() {
        this(new MiaiSdkConfig());
    }

    private SDKEngine(MiaiSdkConfig miaiSdkConfig) {
        this.config = miaiSdkConfig;
        this.httpClients = new HttpClients(miaiSdkConfig, this);
        this.authManager = new AuthManager(miaiSdkConfig, this);
    }

    public HttpClients getHttpClients() {
        return this.httpClients;
    }

    public MiaiSdkConfig getConfig() {
        return this.config;
    }

    public AuthManager getAuthManager() {
        return this.authManager;
    }
}
