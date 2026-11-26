package com.miui.pad.feature.notes.meeting;

import com.xiaomi.miai.SDKException;
import com.xiaomi.miai.api.AuthHardwareAuth;
import com.xiaomi.miai.api.Status;
import com.xiaomi.miai.api.common.APIUtils;
import com.xiaomi.miai.auth.AuthProvider;
import com.xiaomi.miai.auth.TokenRecord;
import com.xiaomi.miai.auth.UserInfo;
import com.xiaomi.miai.log.Logger;
import com.xiaomi.miai.utils.Base64;
import com.xiaomi.verificationsdk.internal.Constants;
import java.time.Duration;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

/* JADX INFO: loaded from: classes4.dex */
public class NoteHardwareAuthProvider extends AuthProvider {
    public static final String AUTH_TYPE = "HARDWARE_AUTH";
    public static final String GET_NONCE_PATH = "/auth/v1/hardwareNonce";
    public static final String GET_TOKEN_PATH = "/auth/v1/hardwareAuthToken";
    private static final String TAG = "HardwareAuthProvider";
    private static Logger logger = Logger.getLogger("NoteHardwareAuthProvider");
    private final String bid;
    private final MiuiFidSigner miuiFidSigner;
    private final String orgId;

    public NoteHardwareAuthProvider(String str, String str2) {
        super(AUTH_TYPE);
        this.orgId = str;
        this.bid = str2;
        this.miuiFidSigner = new MiuiFidSigner();
    }

    @Override // com.xiaomi.miai.auth.AuthProvider
    public String buildAuthValue(UserInfo userInfo, TokenRecord tokenRecord) {
        return tokenRecord.getTokenValue();
    }

    @Override // com.xiaomi.miai.auth.AuthProvider
    public TokenRecord getOrRefreshToken(UserInfo userInfo, boolean z, TokenRecord tokenRecord) throws SDKException {
        String strEncodeUrl;
        String securityDeviceId = getSecurityDeviceId();
        AuthHardwareAuth.NonceResponse nonce = getNonce(securityDeviceId);
        byte[] bArrSignWithDeviceCredential = signWithDeviceCredential(Base64.decode(nonce.getNonce()));
        try {
            if (bArrSignWithDeviceCredential == null) {
                com.miui.common.tool.Logger.INSTANCE.e(TAG, "get signBytes is null");
                return null;
            }
            strEncodeUrl = Base64.encodeUrl(bArrSignWithDeviceCredential);
            AuthHardwareAuth.HardwareTokenResponse token = getToken(securityDeviceId, nonce, strEncodeUrl);
            return new TokenRecord(getAuthType(), getAuthStoreId(userInfo), token.getToken(), Duration.ofMillis(token.getExpiredInMillis()));
        } catch (Exception unused) {
            com.miui.common.tool.Logger.INSTANCE.d(TAG, "get signBytes fail");
            strEncodeUrl = "";
        }
    }

    private AuthHardwareAuth.NonceResponse getNonce(String str) throws SDKException {
        return (AuthHardwareAuth.NonceResponse) tryGetResponse("getHardwareTokenNonce", new Request.Builder().url(getUrl(GET_NONCE_PATH).addQueryParameter("fid", str).addQueryParameter("orgId", this.orgId).addQueryParameter("bid", this.bid).build()).get().build(), AuthHardwareAuth.NonceResponse.class);
    }

    private AuthHardwareAuth.HardwareTokenResponse getToken(String str, AuthHardwareAuth.NonceResponse nonceResponse, String str2) throws SDKException {
        return (AuthHardwareAuth.HardwareTokenResponse) tryGetResponse("getHardwareToken", new Request.Builder().url(getUrl(GET_TOKEN_PATH).addQueryParameter("fid", str).addQueryParameter("orgId", this.orgId).addQueryParameter("bid", this.bid).addQueryParameter(Constants.NONCE, nonceResponse.getNonce()).addQueryParameter("nonceCert", nonceResponse.getNonceCert()).addQueryParameter("sign", str2).build()).get().build(), AuthHardwareAuth.HardwareTokenResponse.class);
    }

    private HttpUrl.Builder getUrl(String str) {
        return HttpUrl.parse(getEnvEndpoints().getMiaiSodApiUrl(str)).newBuilder();
    }

    private String getSecurityDeviceId() throws SDKException.LocalHardwareError {
        try {
            return this.miuiFidSigner.getFid();
        } catch (Exception e) {
            throw new SDKException.LocalHardwareError("getSecurityDeviceId failed.", e);
        }
    }

    private byte[] signWithDeviceCredential(byte[] bArr) throws SDKException.LocalHardwareError {
        try {
            return this.miuiFidSigner.sign(bArr);
        } catch (Exception e) {
            throw new SDKException.LocalHardwareError("signWithDeviceCredential failed.", e);
        }
    }

    private <T> T tryGetResponse(String str, Request request, Class<T> cls) throws SDKException {
        try {
            Response responseHttpRequest = getEngine().getHttpClients().httpRequest(request);
            try {
                com.xiaomi.miai.api.Response response = APIUtils.getResponse(responseHttpRequest.body().string(), cls);
                Status status = response.getStatus();
                if (status.getCode() != 200) {
                    logger.warn("[{}] {}: code: {}, {}", status.getTraceIdOrEmpty(), str, Integer.valueOf(status.getCode()), status.getMessageOrEmpty());
                    throw SDKException.onErrorResponse(status.getCode(), status.getMessageOrEmpty(), null);
                }
                T t = response.getData().get();
                if (responseHttpRequest != null) {
                    responseHttpRequest.close();
                }
                return t;
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
        } catch (SDKException e) {
            logger.error("get response failed. response is: ", e);
            throw e;
        } catch (Exception e2) {
            logger.error("get response failed. response is: ", e2);
            throw new SDKException(str + " failed.", e2);
        }
    }
}
