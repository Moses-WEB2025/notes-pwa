package com.xiaomi.passport.v2.utils;

import android.content.Context;
import com.xiaomi.accountsdk.account.URLs;
import com.xiaomi.accountsdk.account.XMPassport;
import com.xiaomi.accountsdk.account.data.ActivatorPhoneInfo;
import com.xiaomi.accountsdk.account.data.RegisterUserInfo;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import com.xiaomi.accountsdk.request.SimpleRequest;
import com.xiaomi.accountsdk.request.SimpleRequestForAccount;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.accountsdk.utils.EasyMap;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class InNetDateController {
    private static String RECYCLE_PHONE_CHECK_URL = URLs.URL_ACCOUNT_BASE + "/recyclePhoneCheck";
    private static final String TAG = "InNetDateController";

    public static RegisterUserInfo updatePhoneUserStatus(Context context, RegisterUserInfo registerUserInfo, PhoneParams phoneParams, boolean z) {
        return RegisterUserInfo.copyFrom(registerUserInfo).status(queryStatusFromServer(registerUserInfo, phoneParams)).build();
    }

    private static RegisterUserInfo compareInNetDateAndUpdateRegisterStatus(RegisterUserInfo registerUserInfo, long j) {
        int i;
        if (j > registerUserInfo.bindTime) {
            i = RegisterUserInfo.RegisterStatus.STATUS_NOT_REGISTERED.value;
        } else {
            i = RegisterUserInfo.RegisterStatus.STATUS_REGISTERED_NOT_RECYCLED.value;
        }
        return RegisterUserInfo.copyFrom(registerUserInfo).status(i).build();
    }

    private static String maskPhone(String str) {
        if (str == null || str.length() != 11) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str.substring(0, 3)).append("****").append(str.substring(7, 11));
        return sb.toString();
    }

    private static int queryStatusFromServer(RegisterUserInfo registerUserInfo, PhoneParams phoneParams) {
        String str = RECYCLE_PHONE_CHECK_URL;
        EasyMap easyMapEasyPut = new EasyMap().easyPut("_json", "true");
        EasyMap easyMapEasyPutOpt = new EasyMap().easyPutOpt("ticketToken", registerUserInfo.ticketToken);
        if (phoneParams != null) {
            easyMapEasyPut.easyPutOpt("user", phoneParams.phone).easyPutOpt("ticket", phoneParams.ticket);
            ActivatorPhoneInfo activatorPhoneInfo = phoneParams.activatorPhoneInfo;
            if (activatorPhoneInfo != null) {
                easyMapEasyPut.easyPutOpt("userHash", activatorPhoneInfo.phoneHash);
                easyMapEasyPutOpt.easyPutOpt("activatorToken", activatorPhoneInfo.activatorToken);
            }
        }
        try {
            SimpleRequest.StringContent stringContentPostAsString = SimpleRequestForAccount.postAsString(str, easyMapEasyPut, easyMapEasyPutOpt, true);
            if (stringContentPostAsString == null) {
                throw new InvalidResponseException("result content is null");
            }
            JSONObject jSONObject = new JSONObject(XMPassport.removeSafePrefixAndGetRealBody(stringContentPostAsString));
            int i = jSONObject.getInt("code");
            String str2 = "code: " + i + ", desc: " + jSONObject.optString("description");
            if (i == 0) {
                return jSONObject.getJSONObject("data").getInt("status");
            }
            throw new InvalidResponseException(str2);
        } catch (AccessDeniedException e) {
            AccountLogger.log(TAG, "queryStatusFromServer", e);
            return RegisterUserInfo.RegisterStatus.STATUS_USED_POSSIBLY_RECYCLED.value;
        } catch (AuthenticationFailureException e2) {
            AccountLogger.log(TAG, "queryStatusFromServer", e2);
            return RegisterUserInfo.RegisterStatus.STATUS_USED_POSSIBLY_RECYCLED.value;
        } catch (InvalidResponseException e3) {
            AccountLogger.log(TAG, "queryStatusFromServer", e3);
            return RegisterUserInfo.RegisterStatus.STATUS_USED_POSSIBLY_RECYCLED.value;
        } catch (IOException e4) {
            AccountLogger.log(TAG, "queryStatusFromServer", e4);
            return RegisterUserInfo.RegisterStatus.STATUS_USED_POSSIBLY_RECYCLED.value;
        } catch (JSONException e5) {
            AccountLogger.log(TAG, "queryStatusFromServer", e5);
            return RegisterUserInfo.RegisterStatus.STATUS_USED_POSSIBLY_RECYCLED.value;
        }
    }

    public static class PhoneParams {
        public final ActivatorPhoneInfo activatorPhoneInfo;
        public final String phone;
        public final String ticket;

        public PhoneParams(String str, String str2, ActivatorPhoneInfo activatorPhoneInfo) {
            this.phone = str;
            this.ticket = str2;
            this.activatorPhoneInfo = activatorPhoneInfo;
        }

        public PhoneParams newInputPhoneInstance(String str, String str2) {
            return new PhoneParams(str, str2, null);
        }

        public PhoneParams newActivatorPhoneInstance(ActivatorPhoneInfo activatorPhoneInfo) {
            return new PhoneParams(null, null, activatorPhoneInfo);
        }
    }
}
