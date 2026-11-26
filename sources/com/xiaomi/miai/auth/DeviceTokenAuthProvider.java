package com.xiaomi.miai.auth;

import com.market.sdk.utils.Constants;
import com.xiaomi.miai.SDKException;
import com.xiaomi.miai.json.JsObject;
import com.xiaomi.miai.json.JsonFacade;
import com.xiaomi.miai.log.Logger;
import com.xiaomi.miai.utils.Base64;
import com.xiaomi.miai.utils.Requires;
import com.xiaomi.miai.utils.StringUtil;
import java.time.temporal.ChronoUnit;
import okhttp3.FormBody;
import okhttp3.Request;

/* JADX INFO: loaded from: classes4.dex */
public class DeviceTokenAuthProvider extends AuthProvider {
    public static final String AUTH_TYPE = "TP-TOKEN-V1";
    public static final String DEVICE_TOKEN_REFRESH_PATH = "/thirdparty/refresh/token";
    public static final String DEVICE_TOKEN_REQUEST_PATH = "/thirdparty/auth/token";
    private final String clientId;
    private final Logger logger;
    private final String sign;

    public DeviceTokenAuthProvider(String str, String str2) {
        super(AUTH_TYPE);
        this.logger = Logger.getLogger("DeviceTokenAuthProvider");
        this.clientId = str;
        this.sign = str2;
    }

    @Override // com.xiaomi.miai.auth.AuthProvider
    public TokenRecord getOrRefreshToken(UserInfo userInfo, boolean z, TokenRecord tokenRecord) throws SDKException {
        Requires.notNull(userInfo, "user info is null");
        String deviceId = userInfo.getDeviceId();
        Requires.notNull(deviceId, "deviceId is null");
        String str = z ? "refresh device token" : "get device token";
        String str2 = z ? DEVICE_TOKEN_REFRESH_PATH : DEVICE_TOKEN_REQUEST_PATH;
        this.logger.info("start {}", str);
        String strConcat = getEnvEndpoints().getAIAccountAddreses().concat(str2);
        FormBody.Builder builderAdd = new FormBody.Builder().add("client_id", this.clientId).add(Constants.JSON_DEVICE, Base64.encodeUrl(StringUtil.utf8Bytes(deviceId))).add("sign", this.sign);
        if (z) {
            Requires.notNull(tokenRecord, "old token for refreshing is null");
            builderAdd.add(OAuthProvider.OAUTH_REFRESH_GRANT_TYPE, GeneralToken.parse(tokenRecord.getTokenValue()).getRefreshToken());
        }
        String strTryGetResponseString = tryGetResponseString(str, new Request.Builder().url(strConcat).post(builderAdd.build()).build());
        JsObject jsObjectAsObject = JsonFacade.parse(strTryGetResponseString).asObject();
        Requires.isValidResult(jsObjectAsObject.getInt("code") == 0, "invalid code in body %s", strTryGetResponseString);
        return GeneralToken.parseAndConvert(jsObjectAsObject.getObject("result"), getAuthType(), getAuthStoreId(userInfo), ChronoUnit.SECONDS);
    }

    @Override // com.xiaomi.miai.auth.AuthProvider
    public String buildAuthValue(UserInfo userInfo, TokenRecord tokenRecord) {
        return String.format("app_id:%s,access_token:%s", this.clientId, GeneralToken.parse(tokenRecord.getTokenValue()).getAccessToken());
    }
}
