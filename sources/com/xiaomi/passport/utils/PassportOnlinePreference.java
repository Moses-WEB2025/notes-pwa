package com.xiaomi.passport.utils;

import com.xiaomi.accountsdk.account.URLs;
import com.xiaomi.accountsdk.account.XMPassport;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import com.xiaomi.accountsdk.request.SimpleRequest;
import com.xiaomi.accountsdk.request.SimpleRequestForAccount;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.accountsdk.utils.XMPassportUtil;
import com.xiaomi.passport.data.OnlinePreference;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class PassportOnlinePreference {
    private static final String TAG = "PassportOnlinePreference";
    static final String URL_GET_LOGIN_PREFERENCE = URLs.ACCOUNT_DOMAIN + "/pass/preference";

    public static OnlinePreference getOnlineConfig() throws AccessDeniedException, IOException, InvalidResponseException, AuthenticationFailureException {
        SimpleRequest.StringContent asString = SimpleRequestForAccount.getAsString(XMPassportUtil.buildUrlWithLocaleQueryParam(URL_GET_LOGIN_PREFERENCE), null, null, true);
        if (asString == null) {
            throw new InvalidResponseException("result content is null");
        }
        String strRemoveSafePrefixAndGetRealBody = XMPassport.removeSafePrefixAndGetRealBody(asString);
        try {
            return OnlinePreference.parse(new JSONObject(strRemoveSafePrefixAndGetRealBody));
        } catch (JSONException e) {
            AccountLogger.log(TAG, "realBody", e);
            throw new InvalidResponseException(strRemoveSafePrefixAndGetRealBody);
        }
    }
}
