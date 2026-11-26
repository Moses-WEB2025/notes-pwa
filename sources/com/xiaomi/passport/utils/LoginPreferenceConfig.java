package com.xiaomi.passport.utils;

import com.xiaomi.accountsdk.account.XMPassport;
import com.xiaomi.accountsdk.account.exception.InvalidPhoneNumException;
import com.xiaomi.accountsdk.account.serverpassthrougherror.data.PassThroughErrorInfo;
import com.xiaomi.accountsdk.account.utils.EUIParamEncryptUtil;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import com.xiaomi.accountsdk.request.SimpleRequest;
import com.xiaomi.accountsdk.request.SimpleRequestForAccount;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.accountsdk.utils.EasyMap;
import com.xiaomi.accountsdk.utils.XMPassportUtil;
import com.xiaomi.passport.data.LoginPreference;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class LoginPreferenceConfig {
    private static final String TAG = "PhoneLoginPreferenceConfig";
    public static final String TYPE_LOGIN = "login";
    public static final String TYPE_LOGIN_OR_REGISTER = "loginOrRegister";
    public static final String TYPE_REGISTER = "register";

    public static LoginPreference getPhoneLoginConfigOnLine(String str, String str2, String str3, String str4) throws AccessDeniedException, InvalidPhoneNumException, IOException, InvalidResponseException, AuthenticationFailureException {
        SimpleRequest.StringContent stringContentPostAsString = SimpleRequestForAccount.postAsString(XMPassportUtil.buildUrlWithLocaleQueryParam(PassportOnlinePreference.URL_GET_LOGIN_PREFERENCE), new EasyMap().easyPut("processType", str3).easyPut("sid", str4).easyPut("phone", EUIParamEncryptUtil.encryptParam(str)).easyPutOpt("region", str2), null, new EasyMap().easyPut(XMPassport.EUI_KEY, EUIParamEncryptUtil.generateEUI("phone", str)), true);
        if (stringContentPostAsString == null) {
            throw new InvalidResponseException("result content is null");
        }
        String strRemoveSafePrefixAndGetRealBody = XMPassport.removeSafePrefixAndGetRealBody(stringContentPostAsString);
        try {
            JSONObject jSONObject = new JSONObject(strRemoveSafePrefixAndGetRealBody);
            int i = jSONObject.getInt("code");
            String string = jSONObject.getString("description");
            PassThroughErrorInfo passThroughErrorInfo = new PassThroughErrorInfo(jSONObject);
            if (i == 0) {
                JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                return new LoginPreference(jSONObject2.optString("idcZone"), jSONObject2.optString("userRegion"), LoginPreference.PhoneLoginType.valueOf(jSONObject2.getString("type")));
            }
            if (i == 70008) {
                throw new InvalidPhoneNumException(string);
            }
            if (i == 81004) {
                AccountLogger.log(TAG, "code:" + i + ",Invalid public key");
                EUIParamEncryptUtil.invalidPublicKey();
            }
            throw new InvalidResponseException(i, string, passThroughErrorInfo);
        } catch (JSONException e) {
            AccountLogger.log(TAG, "realBody", e);
            throw new InvalidResponseException(strRemoveSafePrefixAndGetRealBody);
        }
    }
}
