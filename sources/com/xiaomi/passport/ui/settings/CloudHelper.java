package com.xiaomi.passport.ui.settings;

import ch.qos.logback.core.pattern.parser.Parser;
import com.xiaomi.accountsdk.account.URLs;
import com.xiaomi.accountsdk.account.XMPassport;
import com.xiaomi.accountsdk.account.data.MiuiActivatorInfo;
import com.xiaomi.accountsdk.account.data.PassportInfo;
import com.xiaomi.accountsdk.account.exception.InvalidCredentialException;
import com.xiaomi.accountsdk.account.exception.InvalidParameterException;
import com.xiaomi.accountsdk.account.exception.InvalidPhoneNumException;
import com.xiaomi.accountsdk.account.exception.InvalidVerifyCodeException;
import com.xiaomi.accountsdk.account.exception.NeedCaptchaException;
import com.xiaomi.accountsdk.account.exception.NeedVerificationException;
import com.xiaomi.accountsdk.account.exception.ReachLimitException;
import com.xiaomi.accountsdk.account.exception.UserRestrictedException;
import com.xiaomi.accountsdk.account.serverpassthrougherror.data.PassThroughErrorInfo;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.CipherException;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import com.xiaomi.accountsdk.request.SecureRequestForAccount;
import com.xiaomi.accountsdk.request.SimpleRequest;
import com.xiaomi.accountsdk.request.SimpleRequestForAccount;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.accountsdk.utils.EasyMap;
import com.xiaomi.accountsdk.utils.XMPassportUtil;
import com.xiaomi.passport.sim.SIMInfo;
import com.xiaomi.verificationsdk.internal.Constants;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class CloudHelper {
    private static final int RESULT_CODE_SUCCESS = 0;
    private static final String TAG = "CloudHelper";
    private static final String URL_MODIFY_SAFE_PHONE = URLs.URL_ACCOUNT_SAFE_API_BASE + "/user/modifySafePhone";
    private static final String URL_SEND_MODIFY_SAFE_PHONE_TICKET = URLs.URL_ACCOUNT_SAFE_API_BASE + "/user/sendModifySafePhoneTicket";
    private static final String URL_USER_BIND_ID_AND_LIMIT = URLs.URL_ACCOUNT_SAFE_API_BASE + "/user/getUserBindIdAndLimit";
    private static final String URL_GET_COUNTRY_CODE = URLs.URL_ACOUNT_API_BASE_SECURE + "/configuration/cc";
    private static final String URL_CHANGE_PWD = URLs.URL_ACCOUNT_SAFE_API_BASE + "/user/native/changePassword";

    public static void modifySafePhone(PassportInfo passportInfo, String str, String str2, MiuiActivatorInfo miuiActivatorInfo, boolean z, String str3, String str4) throws AccessDeniedException, InvalidVerifyCodeException, InvalidPhoneNumException, CipherException, NeedVerificationException, IOException, InvalidResponseException, UserRestrictedException, AuthenticationFailureException {
        if (passportInfo == null) {
            AccountLogger.log(TAG, "passport info should be not null");
            return;
        }
        EasyMap easyMapEasyPut = new EasyMap().easyPut("userId", passportInfo.getUserId()).easyPut("phone", str).easyPutOpt("ticket", str2).easyPutOpt("sid", str4).easyPut(Parser.REPLACE_CONVERTER_WORD, String.valueOf(z)).easyPut("authST", str3).easyPut("transId", UUID.randomUUID().toString().substring(0, 15));
        if (miuiActivatorInfo != null) {
            easyMapEasyPut.easyPutOpt(SIMInfo.SIM_INFO_TYPE_SIM_ID, miuiActivatorInfo.simId).easyPutOpt("vKey2", miuiActivatorInfo.vKey2).easyPutOpt(Constants.NONCE, miuiActivatorInfo.vKey2Nonce);
        }
        SimpleRequest.MapContent mapContentPostAsMap = SecureRequestForAccount.postAsMap(URL_MODIFY_SAFE_PHONE, easyMapEasyPut, getPassportCookie(passportInfo), true, passportInfo.getSecurity());
        if (mapContentPostAsMap == null) {
            throw new InvalidResponseException("failed to modifySafePhone");
        }
        Object fromBody = mapContentPostAsMap.getFromBody("code");
        Object fromBody2 = mapContentPostAsMap.getFromBody("description");
        PassThroughErrorInfo passThroughErrorInfo = new PassThroughErrorInfo(mapContentPostAsMap);
        String str5 = "code: " + fromBody + "; description: " + fromBody2;
        AccountLogger.log(TAG, "modifySafePhone: " + str5);
        if (fromBody instanceof Integer) {
            int iIntValue = ((Integer) fromBody).intValue();
            if (iIntValue == 0) {
                return;
            }
            if (iIntValue == 20023) {
                throw new UserRestrictedException();
            }
            if (iIntValue == 70008) {
                throw new InvalidPhoneNumException(str5);
            }
            if (iIntValue == 70012) {
                throw new NeedVerificationException(null);
            }
            if (iIntValue == 70014) {
                throw new InvalidVerifyCodeException(str5);
            }
        }
        throw new InvalidResponseException(passThroughErrorInfo);
    }

    public static String changePassword(PassportInfo passportInfo, String str, String str2, String str3, String str4) throws AccessDeniedException, InvalidParameterException, CipherException, InvalidCredentialException, IOException, InvalidResponseException, AuthenticationFailureException {
        if (passportInfo == null) {
            AccountLogger.log(TAG, "passport info should be not null");
            throw new AuthenticationFailureException("passport info is null");
        }
        SimpleRequest.StringContent stringContentPostAsString = SecureRequestForAccount.postAsString(URL_CHANGE_PWD, new EasyMap().easyPut("userId", passportInfo.getUserId()).easyPut("pwd", str2).easyPut("passToken", str).easyPutOpt("sid", str4).easyPut("authST", str3).easyPut("traceId", UUID.randomUUID().toString().substring(0, 15)).easyPut("_json", String.valueOf(true)), getPassportCookie(passportInfo), true, passportInfo.getSecurity());
        if (stringContentPostAsString == null) {
            throw new InvalidResponseException("result content is null");
        }
        String strRemoveSafePrefixAndGetRealBody = XMPassport.removeSafePrefixAndGetRealBody(stringContentPostAsString);
        try {
            JSONObject jSONObject = new JSONObject(strRemoveSafePrefixAndGetRealBody);
            int i = jSONObject.getInt("code");
            String str5 = "code: " + i + ", desc: " + jSONObject.optString("description");
            PassThroughErrorInfo passThroughErrorInfo = new PassThroughErrorInfo(jSONObject);
            AccountLogger.log(TAG, "changePassword: " + str5);
            if (i == 0) {
                return jSONObject.getJSONObject("data").getString("passToken");
            }
            if (i == 21317) {
                throw new InvalidCredentialException(i, str5, false);
            }
            if (i == 70003) {
                throw new InvalidParameterException(i, str5);
            }
            if (i == 85110) {
                throw new InvalidParameterException(i, str5);
            }
            throw new InvalidResponseException(passThroughErrorInfo);
        } catch (JSONException unused) {
            throw new InvalidResponseException("result not json: " + strRemoveSafePrefixAndGetRealBody);
        }
    }

    public static void sendModifySafePhoneTicket(PassportInfo passportInfo, String str, String str2, String str3, String str4) throws AccessDeniedException, InvalidPhoneNumException, ReachLimitException, CipherException, NeedCaptchaException, IOException, InvalidResponseException, AuthenticationFailureException {
        if (passportInfo == null) {
            AccountLogger.log(TAG, "passport info should be not null");
            return;
        }
        EasyMap easyMapEasyPut = new EasyMap().easyPut("userId", passportInfo.getUserId()).easyPut("phone", str).easyPutOpt("sid", str4).easyPutOpt("icode", str2).easyPut("transId", UUID.randomUUID().toString().substring(0, 15));
        easyMapEasyPut.putAll(XMPassportUtil.getDefaultLocaleParam());
        EasyMap<String, String> passportCookie = getPassportCookie(passportInfo);
        passportCookie.easyPutOpt("ick", str3);
        SimpleRequest.MapContent mapContentPostAsMap = SecureRequestForAccount.postAsMap(URL_SEND_MODIFY_SAFE_PHONE_TICKET, easyMapEasyPut, passportCookie, true, passportInfo.getSecurity());
        if (mapContentPostAsMap == null) {
            throw new InvalidResponseException("failed to send ticket");
        }
        int iIntValue = ((Integer) mapContentPostAsMap.getFromBody("code")).intValue();
        String str5 = (String) mapContentPostAsMap.getFromBody("description");
        String str6 = "code: " + iIntValue + "; description: " + str5;
        PassThroughErrorInfo passThroughErrorInfo = new PassThroughErrorInfo(mapContentPostAsMap);
        AccountLogger.log(TAG, "send modify ticket: " + str6);
        if (iIntValue != 0) {
            if (iIntValue != 20031) {
                if (iIntValue == 70008) {
                    throw new InvalidPhoneNumException(str6);
                }
                if (iIntValue == 70022) {
                    throw new ReachLimitException(str6);
                }
                if (iIntValue != 87001) {
                    throw new InvalidResponseException(passThroughErrorInfo);
                }
            }
            throw new NeedCaptchaException(iIntValue, str5, URLs.ACCOUNT_DOMAIN + ((String) mapContentPostAsMap.getFromBody(com.market.sdk.utils.Constants.JSON_FILTER_INFO)));
        }
    }

    private static EasyMap<String, String> getPassportCookie(PassportInfo passportInfo) {
        if (passportInfo == null) {
            throw new IllegalArgumentException("passportInfo is null");
        }
        return new EasyMap().easyPut("serviceToken", passportInfo.getServiceToken()).easyPut("cUserId", passportInfo.getEncryptedUserId());
    }

    static GetUserBindIdAndLimitTask.UserBindIdAndLimitResult getUserBindIdAndLimit(PassportInfo passportInfo, String str) throws AccessDeniedException, InvalidPhoneNumException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        return getUserBindIdAndLimit(passportInfo, str, URL_USER_BIND_ID_AND_LIMIT);
    }

    private static GetUserBindIdAndLimitTask.UserBindIdAndLimitResult getUserBindIdAndLimit(PassportInfo passportInfo, String str, String str2) throws AccessDeniedException, InvalidPhoneNumException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        String string;
        if (passportInfo == null) {
            throw new IllegalArgumentException("passportInfo is null");
        }
        SimpleRequest.MapContent asMap = SecureRequestForAccount.getAsMap(str2, new EasyMap().easyPut("userId", passportInfo.getUserId()).easyPut("type", "PH").easyPut("externalId", str), getPassportCookie(passportInfo), true, passportInfo.getSecurity());
        if (asMap == null) {
            throw new IOException("failed to getUserBindIdAndLimit");
        }
        int iIntValue = ((Integer) asMap.getFromBody("code")).intValue();
        String str3 = (String) asMap.getFromBody("description");
        PassThroughErrorInfo passThroughErrorInfo = new PassThroughErrorInfo(asMap);
        if (iIntValue == 0) {
            Object fromBody = asMap.getFromBody("data");
            if (fromBody instanceof Map) {
                Map map = (Map) fromBody;
                try {
                    int i = map.containsKey("times") ? Integer.parseInt(String.valueOf(map.get("times"))) : 0;
                    if (!map.containsKey("userId")) {
                        string = "";
                    } else {
                        string = map.get("userId").toString();
                    }
                    return new GetUserBindIdAndLimitTask.UserBindIdAndLimitResult(string, map.containsKey("ts") ? Long.parseLong(String.valueOf(map.get("ts"))) : 0L, i);
                } catch (Exception unused) {
                    return null;
                }
            }
        } else if (iIntValue != 70008) {
            throw new InvalidResponseException(passThroughErrorInfo);
        }
        throw new InvalidPhoneNumException(str3);
    }

    public static String getCountryCode(String str) throws AccessDeniedException, IOException, AuthenticationFailureException {
        return SimpleRequestForAccount.getAsString(URL_GET_COUNTRY_CODE, new EasyMap().easyPut("locale", str), null, true).getBody();
    }
}
