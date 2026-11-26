package com.xiaomi.miai.auth;

import com.xiaomi.miai.SDKEngine;
import com.xiaomi.miai.SDKException;
import com.xiaomi.miai.config.ConfigKeys;
import com.xiaomi.miai.config.Endpoints;
import com.xiaomi.miai.config.MiaiSdkConfig;
import com.xiaomi.miai.log.Logger;
import com.xiaomi.miai.utils.Requires;
import com.xiaomi.miai.utils.StringUtil;
import java.time.Duration;
import java.time.LocalDateTime;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public abstract class AuthProvider {
    private static Logger logger = Logger.getLogger("AuthProvider");
    private final String authType;
    private AuthCallback callback;
    private SDKEngine engine;
    private MiaiSdkConfig sdkConfig;

    public abstract String buildAuthValue(UserInfo userInfo, TokenRecord tokenRecord) throws SDKException;

    public abstract TokenRecord getOrRefreshToken(UserInfo userInfo, boolean z, TokenRecord tokenRecord) throws SDKException;

    protected AuthProvider(String str) {
        this.authType = str;
    }

    void init(MiaiSdkConfig miaiSdkConfig, SDKEngine sDKEngine, AuthCallback authCallback) {
        this.sdkConfig = miaiSdkConfig;
        this.engine = sDKEngine;
        this.callback = authCallback;
    }

    public String getAuthType() {
        return this.authType;
    }

    public String getAuthValue(UserInfo userInfo) throws SDKException {
        Requires.notNull(this.callback, "auth manager callback is null.");
        TokenRecord orRefreshToken = this.callback.read(getAuthType(), getAuthStoreId(userInfo));
        if (orRefreshToken == null) {
            orRefreshToken = getOrRefreshToken(userInfo, false, null);
            this.callback.write(orRefreshToken);
        }
        if (orRefreshToken.isExpired()) {
            TokenRecord orRefreshToken2 = getOrRefreshToken(userInfo, true, orRefreshToken);
            this.callback.write(orRefreshToken2);
            return buildAuthValue(userInfo, orRefreshToken2);
        }
        if (Duration.between(LocalDateTime.now(), orRefreshToken.getExpiresAt()).compareTo(orRefreshToken.getExpiresIn().dividedBy(4L)) < 0) {
            this.callback.scheduleRefresh(this, userInfo, orRefreshToken);
        }
        return buildAuthValue(userInfo, orRefreshToken);
    }

    String buildAuthString(UserInfo userInfo) throws SDKException {
        return getAuthType() + StringUtils.SPACE + getAuthValue(userInfo);
    }

    protected String getAuthStoreId(UserInfo userInfo) {
        if (userInfo == null) {
            return "";
        }
        return StringUtil.getOrEmpty(userInfo.getUserId()) + "_" + StringUtil.getOrEmpty(userInfo.getDeviceId());
    }

    protected MiaiSdkConfig getSdkConfig() {
        return this.sdkConfig;
    }

    protected Endpoints getEnvEndpoints() {
        return (Endpoints) this.sdkConfig.get(ConfigKeys.ENV_ENDPOINTS);
    }

    protected SDKEngine getEngine() {
        return this.engine;
    }

    protected String tryGetResponseString(String str, Request request) throws SDKException {
        try {
            Response responseHttpRequest = getEngine().getHttpClients().httpRequest(request);
            if (responseHttpRequest != null) {
                try {
                    if (responseHttpRequest.getIsSuccessful()) {
                        ResponseBody responseBodyBody = responseHttpRequest.body();
                        String strString = responseBodyBody == null ? "" : responseBodyBody.string();
                        if (responseHttpRequest != null) {
                            responseHttpRequest.close();
                        }
                        return strString;
                    }
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        if (responseHttpRequest != null) {
                            try {
                                responseHttpRequest.close();
                            } catch (Throwable th3) {
                                th.addSuppressed(th3);
                            }
                        }
                        throw th2;
                    }
                }
            }
            throw SDKException.onErrorResponse(responseHttpRequest);
        } catch (Exception e) {
            throw new SDKException(str + ":" + request.url() + " failed.", e);
        }
    }
}
