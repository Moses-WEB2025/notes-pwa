package com.xiaomi.miai.auth;

import com.xiaomi.miai.SDKException;
import com.xiaomi.miai.json.JsonFacade;
import com.xiaomi.miai.log.Logger;
import com.xiaomi.miai.utils.Base64;
import com.xiaomi.miai.utils.Requires;
import com.xiaomi.miai.utils.StringUtil;
import java.time.temporal.ChronoUnit;
import okhttp3.HttpUrl;
import okhttp3.Request;

/* JADX INFO: loaded from: classes4.dex */
public abstract class OAuthProvider extends AuthProvider {
    public static final String OAUTH_REFRESH_GRANT_TYPE = "refresh_token";
    public static final String OAUTH_REQUEST_GRANT_TYPE = "authorization_code";
    public static final String OAUTH_REQUEST_PATH = "/oauth2/auth/token";
    private static final Logger logger = Logger.getLogger("OAuthProvider");
    private final String authCode;
    private final OAuthClientInfo clientInfo;
    private final OAuthType oauthType;

    protected enum OAuthType {
        DEVCIE_OAUTH("DO-TOKEN-V1"),
        APP_OAUTH("AO-TOKEN-V1");

        final String name;

        OAuthType(String str) {
            this.name = str;
        }
    }

    public static class DeviceOAuth extends OAuthProvider {
        public DeviceOAuth(OAuthClientInfo oAuthClientInfo, String str) {
            super(oAuthClientInfo, OAuthType.DEVCIE_OAUTH, str);
        }
    }

    public static class AppOAuth extends OAuthProvider {
        public AppOAuth(OAuthClientInfo oAuthClientInfo, String str) {
            super(oAuthClientInfo, OAuthType.APP_OAUTH, str);
        }
    }

    protected OAuthProvider(OAuthClientInfo oAuthClientInfo, OAuthType oAuthType, String str) {
        super(oAuthType.name);
        this.clientInfo = oAuthClientInfo;
        this.oauthType = oAuthType;
        this.authCode = str;
    }

    protected String getAuthCode(UserInfo userInfo) {
        return this.authCode;
    }

    public OAuthClientInfo getClientInfo() {
        return this.clientInfo;
    }

    @Override // com.xiaomi.miai.auth.AuthProvider
    public TokenRecord getOrRefreshToken(UserInfo userInfo, boolean z, TokenRecord tokenRecord) throws SDKException {
        Requires.notNull(userInfo, "user info is null");
        String deviceId = userInfo.getDeviceId();
        Requires.notNull(deviceId, "deviceId is null");
        String str = z ? "refresh oauth token" : "get oauth token";
        HttpUrl.Builder builderAddQueryParameter = HttpUrl.parse(getEnvEndpoints().getXiaomiAccountAddress().concat(OAUTH_REQUEST_PATH)).newBuilder().addQueryParameter("client_id", this.clientInfo.getClientId()).addQueryParameter("client_secret", this.clientInfo.getClientSecret()).addQueryParameter("redirect_uri", this.clientInfo.getRedirectUri());
        if (!z) {
            builderAddQueryParameter.addQueryParameter("grant_type", OAUTH_REQUEST_GRANT_TYPE).addQueryParameter("code", getAuthCode(userInfo));
        } else {
            Requires.notNull(tokenRecord, "old token is null.");
            builderAddQueryParameter.addQueryParameter("grant_type", OAUTH_REFRESH_GRANT_TYPE).addQueryParameter(OAUTH_REFRESH_GRANT_TYPE, GeneralToken.parse(tokenRecord.getTokenValue()).getRefreshToken());
        }
        if (this.oauthType == OAuthType.DEVCIE_OAUTH) {
            builderAddQueryParameter.addQueryParameter("pt", "1").addQueryParameter("scope_data", getScopeData(deviceId));
        } else if (this.oauthType == OAuthType.APP_OAUTH) {
            builderAddQueryParameter.addQueryParameter("pt", "0");
        }
        HttpUrl httpUrlBuild = builderAddQueryParameter.build();
        logger.info(str + " url: " + httpUrlBuild, new Object[0]);
        return GeneralToken.parseAndConvert(JsonFacade.parse(tryGetResponseString(str, new Request.Builder().url(httpUrlBuild).get().build())).asObject(), getAuthType(), getAuthStoreId(userInfo), ChronoUnit.SECONDS);
    }

    private String getScopeData(String str) {
        return Base64.encodeUrl(StringUtil.utf8Bytes(String.format("{\"d\":\"%s\"}", str)));
    }

    @Override // com.xiaomi.miai.auth.AuthProvider
    public String buildAuthValue(UserInfo userInfo, TokenRecord tokenRecord) {
        String accessToken = GeneralToken.parse(tokenRecord.getTokenValue()).getAccessToken();
        String clientId = this.clientInfo.getClientId();
        int i = AnonymousClass1.$SwitchMap$com$xiaomi$miai$auth$OAuthProvider$OAuthType[this.oauthType.ordinal()];
        if (i == 1) {
            return String.format("dev_app_id:%s,access_token:%s", clientId, accessToken);
        }
        if (i == 2) {
            return String.format("app_id:%s,scope_data:%s,access_token:%s", clientId, getScopeData(userInfo.getDeviceId()), accessToken);
        }
        throw new IllegalArgumentException("Not valid OAuthType");
    }

    /* JADX INFO: renamed from: com.xiaomi.miai.auth.OAuthProvider$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaomi$miai$auth$OAuthProvider$OAuthType;

        static {
            int[] iArr = new int[OAuthType.values().length];
            $SwitchMap$com$xiaomi$miai$auth$OAuthProvider$OAuthType = iArr;
            try {
                iArr[OAuthType.APP_OAUTH.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaomi$miai$auth$OAuthProvider$OAuthType[OAuthType.DEVCIE_OAUTH.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }
}
