package com.xiaomi.passport.snscorelib.internal.request;

import android.text.TextUtils;
import com.xiaomi.accountsdk.account.URLs;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.accountsdk.account.exception.NeedNotificationException;
import com.xiaomi.accountsdk.account.serverpassthrougherror.data.PassThroughErrorInfo;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import com.xiaomi.accountsdk.request.SimpleRequest;
import com.xiaomi.accountsdk.request.SimpleRequestForAccount;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.accountsdk.utils.EasyMap;
import com.xiaomi.mirror.synergy.CallMethod;
import com.xiaomi.passport.snscorelib.internal.entity.SNSBindParameter;
import com.xiaomi.passport.snscorelib.internal.entity.SNSLoginParameter;
import com.xiaomi.passport.snscorelib.internal.entity.SNSTokenLoginResult;
import com.xiaomi.passport.snscorelib.internal.exception.SNSLoginException;
import com.xiaomi.passport.snscorelib.internal.utils.CodeUtil;
import com.xiaomi.passport.snscorelib.internal.utils.SNSCookieManager;
import com.xiaomi.verificationsdk.internal.Constants;
import java.io.IOException;
import java.net.URLEncoder;
import org.apache.commons.lang3.SystemProperties;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class SNSRequest {
    private static final int INVALID_RESPONSE_EXCEPTION = 3;
    private static final String TAG = "SNSRequest";
    private static final String URL_GET_SNS_ACCESS_TOKEN = URLs.URL_ACCOUNT_BASE + "/sns/login/load";
    private static final String URL_SNS_TOKEN_LOGIN = URLs.URL_ACCOUNT_BASE + "/sns/login/load/token";
    public static final String URL_UNBIND_SNS = URLs.URL_ACCOUNT_API_V2_BASE + "/safe/user/accessToken/full/delete";
    private static final String URL_BIND_SNS_BY_CODE = URLs.URL_ACCOUNT_BASE + "/sns/bind/bindSns";
    private static final String URL_BIND_SNS_BY_TOKENE = URLs.URL_ACCOUNT_BASE + "/sns/token/bind/try";

    public static class BindLimitException extends Exception {
    }

    public static AccountInfo snsLoginByCode(SNSLoginParameter sNSLoginParameter) throws NeedNotificationException, AccessDeniedException, NeedLoginForBindException, RedirectToWebLoginException, BindLimitException, IOException, InvalidResponseException, AuthenticationFailureException, SNSLoginException {
        String sNSTokenLoginUrl = getSNSTokenLoginUrl(sNSLoginParameter);
        StringBuilder sb = new StringBuilder();
        sb.append(sNSTokenLoginUrl).append("&").append("_auto=" + String.valueOf(sNSLoginParameter.autoGenerateAccount));
        if (!TextUtils.isEmpty(sNSLoginParameter.phones)) {
            sb.append("&").append("_phones=" + String.valueOf(URLEncoder.encode(sNSLoginParameter.phones)));
        }
        sb.append("&").append("_snsQuickLogin=" + String.valueOf(sNSLoginParameter.snsQuickLogin));
        return getAccountInfo(SimpleRequestForAccount.getAsString(sb.toString(), null, new EasyMap().easyPut("User-Agent", System.getProperty(SystemProperties.HTTP_AGENT) + " AndroidSnsSDK/5.3.0.release.94 " + sNSLoginParameter.snsVersionName), null, true));
    }

    public static AccountInfo snsLoginByAccessToken(SNSLoginParameter sNSLoginParameter) throws NeedNotificationException, AccessDeniedException, NeedLoginForBindException, RedirectToWebLoginException, BindLimitException, IOException, InvalidResponseException, AuthenticationFailureException, SNSLoginException {
        String str;
        if (TextUtils.isEmpty(sNSLoginParameter.expires_in)) {
            str = "-1";
        } else {
            str = sNSLoginParameter.expires_in;
        }
        EasyMap easyMapEasyPutOpt = new EasyMap().easyPutOpt("enToken", sNSLoginParameter.enToken).easyPutOpt(Constants.VERIFY_SUCESS_TOKEN, sNSLoginParameter.token).easyPutOpt("expires_in", str).easyPutOpt("openId", sNSLoginParameter.openId);
        if (!TextUtils.isEmpty(sNSLoginParameter.phones)) {
            easyMapEasyPutOpt.easyPutOpt("_phones", sNSLoginParameter.phones);
        }
        easyMapEasyPutOpt.easyPut("_auto", String.valueOf(sNSLoginParameter.autoGenerateAccount)).easyPut("_snsQuickLogin", String.valueOf(sNSLoginParameter.snsQuickLogin)).easyPut("_json", "true");
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("sid", sNSLoginParameter.sid);
            jSONObject.put(CallMethod.ARG_CALLBACK, URLEncoder.encode(sNSLoginParameter.callback == null ? "" : sNSLoginParameter.callback, "UTF-8"));
            jSONObject.put("appid", sNSLoginParameter.appid);
            jSONObject.put("locale", SNSCookieManager.getLocaleString());
            jSONObject.put("region", sNSLoginParameter.region);
            easyMapEasyPutOpt.easyPutOpt("state", CodeUtil.bytesToHexString(jSONObject.toString().getBytes()));
            easyMapEasyPutOpt.easyPutOpt(Constants.TIMESTAMP, sNSLoginParameter.requestStartTime);
            SimpleRequest.StringContent asString = SimpleRequestForAccount.getAsString(URL_SNS_TOKEN_LOGIN, easyMapEasyPutOpt, new EasyMap().easyPut("User-Agent", System.getProperty(SystemProperties.HTTP_AGENT) + " AndroidSnsSDK/5.3.0.release.94 " + sNSLoginParameter.snsVersionName), null, true);
            if (asString == null) {
                throw new SNSLoginException(3, "failed to snsLoginByAccessToken : stringContent is null");
            }
            return getAccountInfo(asString);
        } catch (JSONException e) {
            e.printStackTrace();
            AccountLogger.log(TAG, "snsLoginByAccessToken :invalid state params", e);
            throw new SNSLoginException(3, "snsLoginByAccessToken :invalid state params:" + e.toString());
        }
    }

    private static AccountInfo getAccountInfo(SimpleRequest.StringContent stringContent) throws NeedNotificationException, NeedLoginForBindException, RedirectToWebLoginException, BindLimitException, SNSLoginException {
        try {
            JSONObject jSONObject = new JSONObject(stringContent.getBody());
            int iOptInt = jSONObject.optInt("code");
            String strOptString = jSONObject.optString("description");
            PassThroughErrorInfo passThroughErrorInfo = new PassThroughErrorInfo(jSONObject);
            if (iOptInt == 0) {
                String header = stringContent.getHeader("haveLocalUpChannel");
                return getAccountInfo(new SNSTokenLoginResult.Builder().status(jSONObject.optInt("Status")).sid(jSONObject.optString("Sid")).webViewCallback(jSONObject.optString("WebViewCallback")).callback(jSONObject.optString("Callback")).notificationUrl(jSONObject.optString("NotificationUrl")).userId(jSONObject.optString("userId")).passToken(jSONObject.optString("passToken")).snsBindTryUrl(jSONObject.optString("snsBindTryUrl")).snsTokenPh(jSONObject.optString("sns_token_ph")).openId(jSONObject.optString("openId")).snsLoginUrl(jSONObject.optString("snsLoginUrl")).bindLimit(jSONObject.optBoolean("bindLimit")).hasLocalChannel(!TextUtils.isEmpty(header) ? Boolean.valueOf(Boolean.parseBoolean(header)) : null).build());
            }
            AccountLogger.log(TAG, "getAccountInfo :code=" + iOptInt + ";message = " + strOptString);
            throw new SNSLoginException(iOptInt, strOptString, passThroughErrorInfo);
        } catch (JSONException e) {
            AccountLogger.log(TAG, "getAccountInfo:fail to parse JSONObject " + stringContent, e);
            throw new SNSLoginException(3, "getAccountInfo:fail to parse JSONObject: " + stringContent);
        }
    }

    public static SNSBindParameter getSNSBindParameterByCode(SNSLoginParameter sNSLoginParameter, AccountInfo accountInfo) throws AccessDeniedException, IOException, InvalidResponseException, AuthenticationFailureException, SNSLoginException {
        EasyMap easyMapEasyPut = new EasyMap().easyPut("passToken", accountInfo.passToken).easyPut("userId", accountInfo.userId);
        EasyMap easyMapEasyPut2 = new EasyMap().easyPutOpt("code", sNSLoginParameter.code).easyPut("_json", "true").easyPut("userId", accountInfo.userId);
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("sid", sNSLoginParameter.sid);
            jSONObject.put(CallMethod.ARG_CALLBACK, URLEncoder.encode(sNSLoginParameter.callback == null ? "" : sNSLoginParameter.callback, "UTF-8"));
            jSONObject.put("appid", sNSLoginParameter.appid);
            easyMapEasyPut2.easyPutOpt("state", CodeUtil.bytesToHexString(jSONObject.toString().getBytes()));
            easyMapEasyPut2.easyPutOpt(Constants.TIMESTAMP, sNSLoginParameter.requestStartTime);
            SimpleRequest.StringContent asString = SimpleRequestForAccount.getAsString(URL_BIND_SNS_BY_CODE, easyMapEasyPut2, new EasyMap().easyPut("User-Agent", System.getProperty(SystemProperties.HTTP_AGENT) + " AndroidSnsSDK/5.3.0.release.94 " + sNSLoginParameter.snsVersionName), easyMapEasyPut, true);
            if (asString == null) {
                throw new SNSLoginException(3, "failed to getSNSBindParameterByCode : stringContent is null");
            }
            return getSNSBindParameter(asString.getBody());
        } catch (JSONException e) {
            e.printStackTrace();
            AccountLogger.log(TAG, "getSNSBindParameterByCode :invalid state params", e);
            throw new SNSLoginException(3, "getSNSBindParameterByCode:invalid state params:" + e.toString());
        }
    }

    public static SNSBindParameter getSNSBindParameterByToken(SNSLoginParameter sNSLoginParameter, AccountInfo accountInfo) throws AccessDeniedException, IOException, InvalidResponseException, AuthenticationFailureException, SNSLoginException {
        String str;
        if (TextUtils.isEmpty(sNSLoginParameter.expires_in)) {
            str = "-1";
        } else {
            str = sNSLoginParameter.expires_in;
        }
        EasyMap easyMapEasyPut = new EasyMap().easyPut("passToken", accountInfo.passToken).easyPut("userId", accountInfo.userId);
        EasyMap easyMapEasyPut2 = new EasyMap().easyPutOpt("enToken", sNSLoginParameter.enToken).easyPutOpt(Constants.VERIFY_SUCESS_TOKEN, sNSLoginParameter.token).easyPutOpt("expires_in", str).easyPutOpt("openId", sNSLoginParameter.openId).easyPutOpt("userId", accountInfo.userId).easyPut("_json", "true");
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("sid", sNSLoginParameter.sid);
            jSONObject.put(CallMethod.ARG_CALLBACK, URLEncoder.encode(sNSLoginParameter.callback == null ? "" : sNSLoginParameter.callback, "UTF-8"));
            jSONObject.put("appid", sNSLoginParameter.appid);
            easyMapEasyPut2.easyPutOpt("state", CodeUtil.bytesToHexString(jSONObject.toString().getBytes()));
            easyMapEasyPut2.easyPutOpt(Constants.TIMESTAMP, sNSLoginParameter.requestStartTime);
            SimpleRequest.StringContent asString = SimpleRequestForAccount.getAsString(URL_BIND_SNS_BY_TOKENE, easyMapEasyPut2, new EasyMap().easyPut("User-Agent", System.getProperty(SystemProperties.HTTP_AGENT) + " AndroidSnsSDK/5.3.0.release.94 " + sNSLoginParameter.snsVersionName), easyMapEasyPut, true);
            if (asString == null) {
                throw new SNSLoginException(3, "failed to getSNSBindParameterByToken : stringContent is null");
            }
            return getSNSBindParameter(asString.getBody());
        } catch (JSONException e) {
            e.printStackTrace();
            AccountLogger.log(TAG, "getSNSBindParameterByToken :invalid state params", e);
            throw new SNSLoginException(3, "getSNSBindParameterByToken :invalid state params:" + e.toString());
        }
    }

    private static SNSBindParameter getSNSBindParameter(String str) throws SNSLoginException {
        try {
            JSONObject jSONObject = new JSONObject(str);
            int iOptInt = jSONObject.optInt("code");
            String strOptString = jSONObject.optString("description");
            PassThroughErrorInfo passThroughErrorInfo = new PassThroughErrorInfo(jSONObject);
            if (iOptInt == 0) {
                return new SNSBindParameter.Builder().snsBindUrl(jSONObject.optString("snsBindTryUrl")).sns_token_ph(jSONObject.optString("sns_token_ph")).sns_weixin_openId(jSONObject.optString("openId")).build();
            }
            AccountLogger.log(TAG, "getSNSBindParameter :code=" + iOptInt + ";message = " + strOptString);
            throw new SNSLoginException(iOptInt, strOptString, passThroughErrorInfo);
        } catch (JSONException e) {
            AccountLogger.log(TAG, "getSNSBindParameter: fail to parse JSONObject " + str, e);
            throw new SNSLoginException(3, "getSNSBindParameter: fail to parse JSONObject:" + e.toString());
        }
    }

    private static AccountInfo getAccountInfo(SNSTokenLoginResult sNSTokenLoginResult) throws NeedNotificationException, NeedLoginForBindException, RedirectToWebLoginException, BindLimitException {
        int i = sNSTokenLoginResult.status;
        if (i == 0) {
            String str = sNSTokenLoginResult.notificationUrl;
            String str2 = sNSTokenLoginResult.sid;
            if (!TextUtils.isEmpty(str)) {
                throw new NeedNotificationException(str2, str);
            }
            return new AccountInfo.Builder().userId(sNSTokenLoginResult.userId).passToken(sNSTokenLoginResult.passToken).build();
        }
        if (i == 1) {
            String str3 = sNSTokenLoginResult.snsLoginUrl;
            boolean z = sNSTokenLoginResult.bindLimit;
            String str4 = sNSTokenLoginResult.snsBindTryUrl;
            String str5 = sNSTokenLoginResult.snsTokenPh;
            String str6 = sNSTokenLoginResult.openId;
            String str7 = sNSTokenLoginResult.sid;
            Boolean bool = sNSTokenLoginResult.hasLocalChannel;
            if (z) {
                throw new BindLimitException();
            }
            if (!TextUtils.isEmpty(str3)) {
                throw new RedirectToWebLoginException(new SNSBindParameter.Builder().snsBindUrl(str3).sns_token_ph(str5).sns_weixin_openId(str6).snsSid(str7).hasLocalChannel(bool).build());
            }
            throw new NeedLoginForBindException(new SNSBindParameter.Builder().snsBindUrl(str4).sns_token_ph(str5).sns_weixin_openId(str6).snsSid(str7).hasLocalChannel(bool).build());
        }
        throw new IllegalStateException("unknown error:status=" + i);
    }

    private static String getSNSTokenLoginUrl(SNSLoginParameter sNSLoginParameter) throws AccessDeniedException, IOException, InvalidResponseException, AuthenticationFailureException, SNSLoginException {
        EasyMap easyMapEasyPut = new EasyMap().easyPutOpt("code", sNSLoginParameter.code).easyPut("_json", "true");
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("sid", sNSLoginParameter.sid);
            jSONObject.put(CallMethod.ARG_CALLBACK, URLEncoder.encode(sNSLoginParameter.callback == null ? "" : sNSLoginParameter.callback, "UTF-8"));
            jSONObject.put("appid", sNSLoginParameter.appid);
            jSONObject.put("locale", SNSCookieManager.getLocaleString());
            jSONObject.put("region", sNSLoginParameter.region);
            easyMapEasyPut.easyPutOpt("state", CodeUtil.bytesToHexString(jSONObject.toString().getBytes()));
            easyMapEasyPut.easyPutOpt(Constants.TIMESTAMP, sNSLoginParameter.requestStartTime);
            SimpleRequest.StringContent asString = SimpleRequestForAccount.getAsString(URL_GET_SNS_ACCESS_TOKEN, easyMapEasyPut, new EasyMap().easyPut("User-Agent", System.getProperty(SystemProperties.HTTP_AGENT) + " AndroidSnsSDK/5.3.0.release.94 " + sNSLoginParameter.snsVersionName), null, true);
            if (asString == null) {
                throw new SNSLoginException(3, "failed to getSNSTokenLoginUrl : stringContent is null");
            }
            try {
                JSONObject jSONObject2 = new JSONObject(asString.getBody());
                int iOptInt = jSONObject2.optInt("code");
                String strOptString = jSONObject2.optString("description");
                PassThroughErrorInfo passThroughErrorInfo = new PassThroughErrorInfo(jSONObject2);
                if (iOptInt == 0) {
                    return jSONObject2.getJSONObject("data").optString("location");
                }
                AccountLogger.log(TAG, "getSNSTokenLoginUrl :code=" + iOptInt + ";message = " + strOptString);
                throw new SNSLoginException(iOptInt, strOptString, passThroughErrorInfo);
            } catch (JSONException e) {
                AccountLogger.log(TAG, "getSNSTokenLoginUrl: fail to parse JSONObject " + asString.toString(), e);
                throw new SNSLoginException(3, "getSNSTokenLoginUrl: fail to parse JSONObject:" + e.toString());
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
            AccountLogger.log(TAG, "getSNSTokenLoginUrl :invalid state params", e2);
            throw new SNSLoginException(3, "getSNSTokenLoginUrl:invalid state params:" + e2.toString());
        }
    }

    public static class NeedLoginForBindException extends Exception {
        private final SNSBindParameter mSNSBindParameter;

        public NeedLoginForBindException(SNSBindParameter sNSBindParameter) {
            this.mSNSBindParameter = sNSBindParameter;
        }

        public SNSBindParameter getSNSBindParameter() {
            return this.mSNSBindParameter;
        }
    }

    public static class RedirectToWebLoginException extends Exception {
        private final SNSBindParameter snsBindParameter;

        public RedirectToWebLoginException(SNSBindParameter sNSBindParameter) {
            this.snsBindParameter = sNSBindParameter;
        }

        public SNSBindParameter getSNSBindParameter() {
            return this.snsBindParameter;
        }
    }
}
