package com.xiaomi.verificationsdk.internal;

import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.SimpleRequest;
import com.xiaomi.accountsdk.request.SimpleRequestForAccount;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.accountsdk.utils.EasyMap;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import org.apache.commons.lang3.SystemProperties;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class VerifyRequest {
    private static final String TAG = "VerifyRequest";

    public static RegisterInfo getRegisterInfo(String str) throws VerificationException {
        try {
            SimpleRequest.StringContent asString = SimpleRequestForAccount.getAsString(str, null, new EasyMap().easyPut("User-Agent", System.getProperty(SystemProperties.HTTP_AGENT) + " AndroidVerifySDK/5.3.0.release.94"), null, true);
            if (asString == null) {
                AccountLogger.log(TAG, "getRegisterInfo:stringContent is null");
                throw new VerificationException(ErrorInfo.ErrorCode.ERROR_NETWORK_EXCEPTION.getCode(), "getRegisterInfo:stringContent is null", ErrorInfo.getMsgIdGivenErrorCode(ErrorInfo.ErrorCode.ERROR_NETWORK_EXCEPTION));
            }
            JSONObject jSONObject = new JSONObject(asString.getBody());
            int iOptInt = jSONObject.optInt("code");
            if (iOptInt == 0) {
                JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                return new RegisterInfo(jSONObject2.optString("eventId"), jSONObject2.optString("clientId"));
            }
            AccountLogger.log(TAG, "getRegisterInfo :" + ("code: " + iOptInt + ", msg: " + jSONObject.optString("msg")));
            throw new VerificationException(iOptInt, "getRegisterInfo:" + jSONObject.optString("msg"), ErrorInfo.getMsgIdGivenErrorCode(ErrorInfo.ErrorCode.ERROR_SERVER));
        } catch (AccessDeniedException e) {
            e.printStackTrace();
            throw new VerificationException(ErrorInfo.ErrorCode.ERROR_ACCESSDENIED_EXCEPTION.getCode(), "getRegisterInfo:" + e.toString(), ErrorInfo.getMsgIdGivenErrorCode(ErrorInfo.ErrorCode.ERROR_ACCESSDENIED_EXCEPTION));
        } catch (AuthenticationFailureException e2) {
            e2.printStackTrace();
            throw new VerificationException(ErrorInfo.ErrorCode.ERROR_AUTHENTICATIONFAILURE_EXCEPTION.getCode(), "getRegisterInfo:" + e2.toString(), ErrorInfo.getMsgIdGivenErrorCode(ErrorInfo.ErrorCode.ERROR_AUTHENTICATIONFAILURE_EXCEPTION));
        } catch (VerificationException e3) {
            e3.printStackTrace();
            throw new VerificationException(e3.getCode(), "getRegisterInfo:" + e3.getMessage(), e3.getDialogTipMsg());
        } catch (IOException e4) {
            e4.printStackTrace();
            if (e4 instanceof ConnectException) {
                throw new VerificationException(ErrorInfo.ErrorCode.ERROR_CONNECT_UNREACHABLE_EXCEPTION.getCode(), "getRegisterInfo:" + e4.toString(), ErrorInfo.getMsgIdGivenErrorCode(ErrorInfo.ErrorCode.ERROR_CONNECT_UNREACHABLE_EXCEPTION));
            }
            if (e4 instanceof SocketTimeoutException) {
                throw new VerificationException(ErrorInfo.ErrorCode.ERROR_SOCKET_TIMEOUT_EXCEPTION.getCode(), "getRegisterInfo:" + e4.toString(), ErrorInfo.getMsgIdGivenErrorCode(ErrorInfo.ErrorCode.ERROR_SOCKET_TIMEOUT_EXCEPTION));
            }
            if (e4 instanceof ConnectTimeoutException) {
                throw new VerificationException(ErrorInfo.ErrorCode.ERROR_CONNECT_TIMEOUT_EXCEPTION.getCode(), "getRegisterInfo:" + e4.toString(), ErrorInfo.getMsgIdGivenErrorCode(ErrorInfo.ErrorCode.ERROR_CONNECT_TIMEOUT_EXCEPTION));
            }
            throw new VerificationException(ErrorInfo.ErrorCode.ERROR_IO_EXCEPTION.getCode(), "getRegisterInfo:" + e4.toString(), ErrorInfo.getMsgIdGivenErrorCode(ErrorInfo.ErrorCode.ERROR_IO_EXCEPTION));
        } catch (JSONException e5) {
            AccountLogger.log(TAG, "fail to parse JSONObject", e5);
            throw new VerificationException(ErrorInfo.ErrorCode.ERROR_JSON_EXCEPTION.getCode(), "getRegisterInfo:" + e5.toString(), ErrorInfo.getMsgIdGivenErrorCode(ErrorInfo.ErrorCode.ERROR_JSON_EXCEPTION));
        }
    }

    public static Config getConfigFromServer(String str) {
        try {
            SimpleRequest.StringContent asString = SimpleRequestForAccount.getAsString(str, new EasyMap().easyPut("type", String.valueOf(2)), null, null, true);
            if (asString == null) {
                return null;
            }
            JSONObject jSONObject = new JSONObject(asString.getBody());
            int iOptInt = jSONObject.optInt("maxDuration");
            int iOptInt2 = jSONObject.optInt(Constants.FREQUENCY);
            Config config = new Config();
            config.setMaxDuration(iOptInt);
            config.setFrequency(iOptInt2);
            return config;
        } catch (AccessDeniedException e) {
            e.printStackTrace();
            return null;
        } catch (AuthenticationFailureException e2) {
            e2.printStackTrace();
            return null;
        } catch (IOException e3) {
            e3.printStackTrace();
            return null;
        } catch (JSONException e4) {
            AccountLogger.log(TAG, "fail to parse JSONObject", e4);
            return null;
        }
    }
}
