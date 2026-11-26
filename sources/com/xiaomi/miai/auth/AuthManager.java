package com.xiaomi.miai.auth;

import com.xiaomi.miai.SDKEngine;
import com.xiaomi.miai.SDKException;
import com.xiaomi.miai.config.MiaiSdkConfig;
import com.xiaomi.miai.log.Logger;
import com.xiaomi.miai.utils.Requires;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class AuthManager implements AuthCallback {
    private AuthProvider currentProvider;
    private final SDKEngine engine;
    private final MiaiSdkConfig sdkConfig;
    private AuthTokenStorage storage;
    private final Logger logger = Logger.getLogger("AuthManager");
    private Map<String, AuthProvider> provders = new HashMap();
    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private ConcurrentMap<String, TokenRecord> cachedTokens = new ConcurrentHashMap();

    public AuthManager(MiaiSdkConfig miaiSdkConfig, SDKEngine sDKEngine) {
        this.sdkConfig = miaiSdkConfig;
        this.engine = sDKEngine;
    }

    public synchronized AuthManager register(AuthProvider authProvider) {
        Requires.notNull(authProvider, "provider is null.");
        this.logger.info("Register auth provider: type {}, {}", authProvider.getAuthType(), authProvider.getClass());
        this.provders.put(authProvider.getAuthType(), authProvider);
        authProvider.init(this.sdkConfig, this.engine, this);
        this.currentProvider = authProvider;
        return this;
    }

    synchronized void unregister(String str) {
        Requires.notNull(str, "authType is null.");
        this.logger.info("Unregister auth provider: {}", str);
        if (this.provders.remove(str) == this.currentProvider) {
            this.currentProvider = null;
        }
    }

    public String buildAuthString(UserInfo userInfo) throws SDKException {
        AuthProvider authProvider = this.currentProvider;
        Requires.notNull(authProvider, "currentProvider is not initialized! Please register an AuthProvider instance.");
        try {
            return authProvider.getAuthType() + StringUtils.SPACE + authProvider.getAuthValue(userInfo);
        } catch (SDKException e) {
            this.logger.error("Build AuthValue failed. AuthType is {}", authProvider.getAuthType());
            throw e;
        }
    }

    public void setStorage(AuthTokenStorage authTokenStorage) {
        Requires.notNull(authTokenStorage, "storage is null.");
        this.storage = authTokenStorage;
    }

    @Override // com.xiaomi.miai.auth.AuthCallback
    public void scheduleRefresh(final AuthProvider authProvider, final UserInfo userInfo, final TokenRecord tokenRecord) {
        this.executor.schedule(new Runnable() { // from class: com.xiaomi.miai.auth.AuthManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m2518lambda$scheduleRefresh$0$comxiaomimiaiauthAuthManager(authProvider, userInfo, tokenRecord);
            }
        }, 1L, TimeUnit.SECONDS);
    }

    /* JADX INFO: renamed from: lambda$scheduleRefresh$0$com-xiaomi-miai-auth-AuthManager, reason: not valid java name */
    /* synthetic */ void m2518lambda$scheduleRefresh$0$comxiaomimiaiauthAuthManager(AuthProvider authProvider, UserInfo userInfo, TokenRecord tokenRecord) {
        try {
            write(authProvider.getOrRefreshToken(userInfo, true, tokenRecord));
        } catch (SDKException e) {
            this.logger.error("scheduleRefresh failed:", e);
        }
    }

    @Override // com.xiaomi.miai.auth.AuthTokenStorage
    public void write(TokenRecord tokenRecord) {
        this.cachedTokens.put(buildTokenId(tokenRecord.getAuthType(), tokenRecord.getIdentity()), tokenRecord);
        AuthTokenStorage authTokenStorage = this.storage;
        if (authTokenStorage != null) {
            authTokenStorage.write(tokenRecord);
        }
    }

    @Override // com.xiaomi.miai.auth.AuthTokenStorage
    public TokenRecord read(String str, String str2) {
        AuthTokenStorage authTokenStorage;
        TokenRecord tokenRecord = this.cachedTokens.get(buildTokenId(str, str2));
        return (tokenRecord != null || (authTokenStorage = this.storage) == null) ? tokenRecord : authTokenStorage.read(str, str2);
    }

    private static String buildTokenId(String str, String str2) {
        return String.format("%s_%s", str, str2);
    }
}
