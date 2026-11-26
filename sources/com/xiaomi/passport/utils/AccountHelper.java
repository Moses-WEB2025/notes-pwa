package com.xiaomi.passport.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import com.xiaomi.accountsdk.account.AccountIntent;
import com.xiaomi.accountsdk.account.URLs;
import com.xiaomi.accountsdk.account.XMPassport;
import com.xiaomi.accountsdk.account.XMPassportSettings;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.accountsdk.account.data.ExtendedAuthToken;
import com.xiaomi.accountsdk.account.data.MetaLoginData;
import com.xiaomi.accountsdk.account.data.PassTokenLoginParams;
import com.xiaomi.accountsdk.account.data.PassportInfo;
import com.xiaomi.accountsdk.account.data.PasswordLoginParams;
import com.xiaomi.accountsdk.account.data.Step2LoginParams;
import com.xiaomi.accountsdk.account.data.XiaomiUserCoreInfo;
import com.xiaomi.accountsdk.account.exception.IllegalDeviceException;
import com.xiaomi.accountsdk.account.exception.InvalidCredentialException;
import com.xiaomi.accountsdk.account.exception.InvalidUserNameException;
import com.xiaomi.accountsdk.account.exception.NeedCaptchaException;
import com.xiaomi.accountsdk.account.exception.NeedNotificationException;
import com.xiaomi.accountsdk.account.exception.NeedVerificationException;
import com.xiaomi.accountsdk.account.exception.PackageNameDeniedException;
import com.xiaomi.accountsdk.account.serverpassthrougherror.data.PassThroughErrorInfo;
import com.xiaomi.accountsdk.hasheddeviceidlib.AccountDeviceIdManager;
import com.xiaomi.accountsdk.hasheddeviceidlib.UDevIdUtil;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.CipherException;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import com.xiaomi.accountsdk.request.SecureRequestForAccount;
import com.xiaomi.accountsdk.request.SimpleRequest;
import com.xiaomi.accountsdk.request.SimpleRequestForAccount;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.accountsdk.utils.EasyMap;
import com.xiaomi.accountsdk.utils.FidSigningUtil;
import com.xiaomi.accountsdk.utils.UserSpaceIdUtil;
import com.xiaomi.mirror.RemoteDeviceInfo;
import com.xiaomi.onetrack.api.ah;
import com.xiaomi.passport.PassportUserEnvironment;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class AccountHelper {
    private static final String MI_ACCOUNT_HOST_SUFFIX = ".account.xiaomi.com";
    private static final String TAG = "AccountHelper";
    private static final Integer INT_0 = 0;
    private static final String URL_IS_SET_PASSWORD = URLs.URL_ACCOUNT_API_V2_BASE + "/safe/user/isSetPassword";

    public static AccountInfo getStsUrlByPassword(String str, String str2, String str3, String str4, String str5) throws NeedNotificationException, InvalidUserNameException, AccessDeniedException, IllegalDeviceException, NeedVerificationException, NeedCaptchaException, InvalidCredentialException, IOException, InvalidResponseException, AuthenticationFailureException {
        return XMPassport.getStsUrlByPassword(str, str2, str3, getHashedDeviceId(), str4, str5, null);
    }

    public static AccountInfo getServiceTokenByPassword(String str, String str2, String str3, String str4, String str5) throws InvalidUserNameException, AccessDeniedException, IllegalDeviceException, NeedVerificationException, NeedCaptchaException, InvalidCredentialException, IOException, InvalidResponseException, AuthenticationFailureException {
        try {
            return getServiceTokenByPassword(str, str2, str3, str4, str5, false);
        } catch (NeedNotificationException unused) {
            throw new InvalidResponseException("Unexpected NeedNotificationException");
        }
    }

    public static AccountInfo getServiceTokenByPassword(String str, String str2, String str3, String str4, String str5, boolean z) throws NeedNotificationException, InvalidUserNameException, AccessDeniedException, IllegalDeviceException, NeedVerificationException, NeedCaptchaException, InvalidCredentialException, IOException, InvalidResponseException, AuthenticationFailureException {
        return XMPassport.loginByPassword(str, str5, getHashedDeviceId(), str2, str3, str4, null, z, null);
    }

    public static AccountInfo loginByPassword(PasswordLoginParams passwordLoginParams) throws NeedNotificationException, InvalidUserNameException, AccessDeniedException, IllegalDeviceException, NeedVerificationException, NeedCaptchaException, InvalidCredentialException, IOException, InvalidResponseException, AuthenticationFailureException {
        if (passwordLoginParams == null) {
            throw new IllegalArgumentException("password login params is null");
        }
        PasswordLoginParams.Builder builderCopyFrom = PasswordLoginParams.copyFrom(passwordLoginParams);
        if (TextUtils.isEmpty(passwordLoginParams.deviceId)) {
            builderCopyFrom.setDeviceId(getHashedDeviceId());
        }
        return XMPassport.loginByPassword(builderCopyFrom.build());
    }

    public static AccountInfo getServiceTokenByPassToken(Context context, PassTokenLoginParams passTokenLoginParams) throws NeedNotificationException, InvalidUserNameException, AccessDeniedException, IllegalDeviceException, PackageNameDeniedException, InvalidCredentialException, IOException, InvalidResponseException, AuthenticationFailureException {
        PassTokenLoginParams.Builder builderBuildUpon = PassTokenLoginParams.buildUpon(passTokenLoginParams);
        if (TextUtils.isEmpty(passTokenLoginParams.deviceId)) {
            builderBuildUpon.deviceId(getHashedDeviceId());
        }
        if (TextUtils.isEmpty(passTokenLoginParams.uDevId)) {
            builderBuildUpon.uDevId(getUDevId(context, passTokenLoginParams.userId));
        }
        return XMPassport.loginByPassToken(builderBuildUpon.build());
    }

    public static AccountInfo getStsUrlByPassToken(String str, String str2, String str3) throws NeedNotificationException, InvalidUserNameException, AccessDeniedException, IllegalDeviceException, PackageNameDeniedException, InvalidCredentialException, IOException, InvalidResponseException, AuthenticationFailureException {
        return XMPassport.getStsUrlByPassToken(str, str3, getHashedDeviceId(), str2);
    }

    public static AccountInfo loginByStep2(Step2LoginParams step2LoginParams) throws InvalidUserNameException, AccessDeniedException, NeedVerificationException, InvalidCredentialException, IOException, InvalidResponseException, AuthenticationFailureException {
        return XMPassport.loginByStep2(step2LoginParams);
    }

    public static AccountInfo getStsUrlByStep2(String str, String str2, MetaLoginData metaLoginData, boolean z, String str3, String str4) throws InvalidUserNameException, AccessDeniedException, IllegalDeviceException, NeedVerificationException, InvalidCredentialException, IOException, InvalidResponseException, AuthenticationFailureException {
        return XMPassport.getStsUrlByStep2(str, str4, getHashedDeviceId(), str2, metaLoginData, z, str3);
    }

    public static AccountInfo getServiceTokenByStep2(String str, String str2, MetaLoginData metaLoginData, boolean z, String str3, String str4) throws InvalidUserNameException, AccessDeniedException, IllegalDeviceException, NeedVerificationException, InvalidCredentialException, IOException, InvalidResponseException, AuthenticationFailureException {
        return XMPassport.loginByStep2(str, str4, getHashedDeviceId(), str2, metaLoginData, z, str3);
    }

    public static String getHashedDeviceId() {
        return AccountDeviceIdManager.getInstance().getDeviceId();
    }

    @Deprecated
    public static Pair<Bitmap, String> getCaptchaImage(String str) {
        return XMPassport.getCaptchaImage(str);
    }

    @Deprecated
    public static Pair<Bitmap, String> getIckImage(String str) {
        return XMPassport.getCaptchaImageAndIck(str);
    }

    public static void sendActivateEmail(String str, String str2, String str3) throws IOException, InvalidResponseException {
        String str4 = URLs.URL_RESEND_EMAIL;
        EasyMap easyMapEasyPutOpt = new EasyMap().easyPut("userId", str).easyPut("addressType", "EM").easyPut(RemoteDeviceInfo.KEY_ADDRESS, str2).easyPutOpt("region", str3);
        SimpleRequest.MapContent asMap = null;
        try {
            asMap = SimpleRequestForAccount.getAsMap(str4, easyMapEasyPutOpt, null, true);
        } catch (AccessDeniedException e) {
            e.printStackTrace();
        } catch (AuthenticationFailureException e2) {
            e2.printStackTrace();
        }
        if (asMap == null) {
            throw new IOException("failed to register, no response");
        }
        Object fromBody = asMap.getFromBody("code");
        if (INT_0.equals(fromBody)) {
        } else {
            throw new InvalidResponseException(fromBody == null ? -1 : ((Integer) fromBody).intValue(), "invalid response, failed to send activate email", new PassThroughErrorInfo(asMap));
        }
    }

    public static Bundle getAccountAuthenticatorResponseResult(int i, String str, String str2) {
        Bundle bundle = new Bundle();
        if (!TextUtils.isEmpty(str)) {
            bundle.putString("authAccount", str);
            bundle.putString("accountType", "com.xiaomi");
        }
        if (!TextUtils.isEmpty(str2)) {
            bundle.putString("authtoken", str2);
        }
        bundle.putBoolean("booleanResult", i == -1);
        if (i == 0) {
            bundle.putInt("errorCode", 4);
        }
        return bundle;
    }

    public static Bundle getAccountAuthenticatorResponseResult(int i, AccountInfo accountInfo, boolean z) {
        Bundle authenticatorResponseBundle = getAuthenticatorResponseBundle(accountInfo, z);
        if (i == 0) {
            authenticatorResponseBundle.putInt("errorCode", 4);
        }
        return authenticatorResponseBundle;
    }

    public static Bundle getAuthenticatorResponseBundle(AccountInfo accountInfo, boolean z) {
        Bundle bundle = new Bundle();
        if (accountInfo == null || accountInfo.userId == null) {
            bundle.putBoolean("booleanResult", false);
            return bundle;
        }
        bundle.putString("authAccount", accountInfo.userId);
        bundle.putString("accountType", "com.xiaomi");
        if (!TextUtils.isEmpty(accountInfo.encryptedUserId)) {
            bundle.putString("encrypted_user_id", accountInfo.encryptedUserId);
        }
        bundle.putBoolean(AccountIntent.EXTRA_HAS_PASSWORD, accountInfo.getHasPwd());
        if (!TextUtils.isEmpty(accountInfo.autoLoginUrl)) {
            bundle.putString(AccountIntent.EXTRA_STS_URL_RESULT, accountInfo.autoLoginUrl);
            bundle.putString("sts_url", accountInfo.autoLoginUrl);
        }
        String serviceId = accountInfo.getServiceId();
        String serviceToken = accountInfo.getServiceToken();
        if (!TextUtils.isEmpty(serviceId) && !TextUtils.isEmpty(serviceToken)) {
            bundle.putString("authtoken", ExtendedAuthToken.build(serviceToken, accountInfo.getSecurity()).toPlain());
        }
        bundle.putString("stsCookies", accountInfo.stsCookies);
        bundle.putBoolean("booleanResult", true);
        bundle.putBoolean(ah.L, z);
        return bundle;
    }

    public static boolean isWebSsoAuthTokenType(String str) {
        return str != null && str.startsWith("weblogin:");
    }

    public static boolean isTrustedWebSsoUrl(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            URL url = new URL(str);
            URL url2 = new URL(XMPassport.ACCOUNT_DOMAIN);
            return url2.getProtocol().equalsIgnoreCase(url.getProtocol()) && url2.getHost().equalsIgnoreCase(url.getHost());
        } catch (MalformedURLException unused) {
        }
    }

    public static String[] getEnvInfoArray() {
        return PassportUserEnvironment.Holder.getInstance().getEnvInfoArray(XMPassportSettings.getApplicationContext());
    }

    private static URL parseUrlWithDoubleCheck(String str) {
        try {
            return new URL(new URL(str).toString());
        } catch (MalformedURLException e) {
            AccountLogger.log(TAG, e);
            return null;
        }
    }

    private static boolean isAccountDomainUrlWithHttps(String str) {
        URL urlWithDoubleCheck = parseUrlWithDoubleCheck(str);
        return urlWithDoubleCheck != null && "https".equals(urlWithDoubleCheck.getProtocol()) && urlWithDoubleCheck.getUserInfo() == null && urlWithDoubleCheck.getHost().endsWith(MI_ACCOUNT_HOST_SUFFIX);
    }

    public static boolean isMiAccountLoginQRCodeScanResult(String str) {
        return !TextUtils.isEmpty(str) && isAccountDomainUrlWithHttps(str);
    }

    public static boolean isSetPassword(PassportInfo passportInfo, String str, String str2, String str3) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        if (passportInfo == null) {
            throw new IllegalArgumentException("passport info should not be null");
        }
        SimpleRequest.StringContent asString = SecureRequestForAccount.getAsString(URL_IS_SET_PASSWORD, new EasyMap().easyPut("userId", passportInfo.getUserId()).easyPutOpt("sid", str).easyPut("transId", str3), new EasyMap().easyPut("cUserId", passportInfo.getEncryptedUserId()).easyPut("serviceToken", passportInfo.getServiceToken()).easyPut("deviceId", str2).easyPutOpt("userSpaceId", UserSpaceIdUtil.getNullableUserSpaceIdCookie()), true, passportInfo.getSecurity());
        if (asString == null) {
            throw new InvalidResponseException("http response result should not be null");
        }
        String strRemoveSafePrefixAndGetRealBody = XMPassport.removeSafePrefixAndGetRealBody(asString);
        try {
            JSONObject jSONObject = new JSONObject(strRemoveSafePrefixAndGetRealBody);
            int i = jSONObject.getInt("code");
            if (i == 0) {
                return jSONObject.getJSONObject("data").getBoolean("status");
            }
            throw new InvalidResponseException("code: " + i + "desc: " + jSONObject.optString("description"));
        } catch (JSONException unused) {
            throw new InvalidResponseException("json error: " + strRemoveSafePrefixAndGetRealBody);
        }
    }

    public static XiaomiUserCoreInfo getXiaomiUserCoreInfo(PassportInfo passportInfo, String str, List<XiaomiUserCoreInfo.Flag> list) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        if (passportInfo == null) {
            AccountLogger.log(TAG, "passportinfo is null");
            return null;
        }
        return XMPassport.getXiaomiUserCoreInfo(passportInfo, str, list);
    }

    public static String getUDevId(Context context, String str) {
        try {
            return UDevIdUtil.getUDevId(context, str);
        } catch (FidSigningUtil.FidSignException unused) {
            AccountLogger.log(TAG, " getUDevId   FidSignException ");
            return null;
        }
    }
}
