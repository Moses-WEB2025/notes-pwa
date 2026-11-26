package com.xiaomi.accountsdk.account;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Pair;
import androidx.core.app.NotificationCompat;
import ch.qos.logback.core.joran.action.Action;
import com.miui.richeditor.schema.HtmlParser;
import com.xiaomi.account.exception.PassportCAException;
import com.xiaomi.account.privacy_data.master.PrivacyDataMaster;
import com.xiaomi.account.privacy_data.master.PrivacyDataType;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.accountsdk.account.data.AccountVipInfo;
import com.xiaomi.accountsdk.account.data.ActivatorPhoneInfo;
import com.xiaomi.accountsdk.account.data.BindingType;
import com.xiaomi.accountsdk.account.data.CheckRegPhoneParams;
import com.xiaomi.accountsdk.account.data.DevInfoKeys;
import com.xiaomi.accountsdk.account.data.DeviceModelInfo;
import com.xiaomi.accountsdk.account.data.EmailRegisterParams;
import com.xiaomi.accountsdk.account.data.ExtendedAuthToken;
import com.xiaomi.accountsdk.account.data.Gender;
import com.xiaomi.accountsdk.account.data.IdentityAuthReason;
import com.xiaomi.accountsdk.account.data.MetaLoginData;
import com.xiaomi.accountsdk.account.data.MiCloudAuthInfo;
import com.xiaomi.accountsdk.account.data.MiuiActivatorInfo;
import com.xiaomi.accountsdk.account.data.OAuthParameter;
import com.xiaomi.accountsdk.account.data.OtherAppAccount;
import com.xiaomi.accountsdk.account.data.PassTokenLoginParams;
import com.xiaomi.accountsdk.account.data.PassportInfo;
import com.xiaomi.accountsdk.account.data.PasswordLoginParams;
import com.xiaomi.accountsdk.account.data.PhoneTicketLoginParams;
import com.xiaomi.accountsdk.account.data.PhoneTokenRegisterParams;
import com.xiaomi.accountsdk.account.data.QRLoginUrlInfo;
import com.xiaomi.accountsdk.account.data.QueryPhoneInfoParams;
import com.xiaomi.accountsdk.account.data.RegisterUserInfo;
import com.xiaomi.accountsdk.account.data.SecondaryDeviceInfo;
import com.xiaomi.accountsdk.account.data.SecurityQuestion;
import com.xiaomi.accountsdk.account.data.SendEmailActMsgParams;
import com.xiaomi.accountsdk.account.data.SendPhoneTicketParams;
import com.xiaomi.accountsdk.account.data.SetPasswordParams;
import com.xiaomi.accountsdk.account.data.Step2LoginParams;
import com.xiaomi.accountsdk.account.data.XiaomiUserCoreInfo;
import com.xiaomi.accountsdk.account.data.XiaomiUserInfo;
import com.xiaomi.accountsdk.account.data.XiaomiUserProfile;
import com.xiaomi.accountsdk.account.exception.DeleteSafeAddressException;
import com.xiaomi.accountsdk.account.exception.InvalidBindAddressException;
import com.xiaomi.accountsdk.account.exception.InvalidCredentialException;
import com.xiaomi.accountsdk.account.exception.InvalidParameterException;
import com.xiaomi.accountsdk.account.exception.InvalidPhoneNumException;
import com.xiaomi.accountsdk.account.exception.InvalidPhoneOrTicketException;
import com.xiaomi.accountsdk.account.exception.InvalidTzSignException;
import com.xiaomi.accountsdk.account.exception.InvalidUserNameException;
import com.xiaomi.accountsdk.account.exception.InvalidVerifyCodeException;
import com.xiaomi.accountsdk.account.exception.NeedCaptchaException;
import com.xiaomi.accountsdk.account.exception.NeedNotificationException;
import com.xiaomi.accountsdk.account.exception.NeedOAuthorizeException;
import com.xiaomi.accountsdk.account.exception.NeedVerificationException;
import com.xiaomi.accountsdk.account.exception.PackageNameDeniedException;
import com.xiaomi.accountsdk.account.exception.PassportIOException;
import com.xiaomi.accountsdk.account.exception.ReachLimitException;
import com.xiaomi.accountsdk.account.exception.RegisteredPhoneException;
import com.xiaomi.accountsdk.account.exception.RequestSecondaryDeviceLoginDataException;
import com.xiaomi.accountsdk.account.exception.SendVerifyCodeExceedLimitException;
import com.xiaomi.accountsdk.account.exception.TokenExpiredException;
import com.xiaomi.accountsdk.account.exception.UsedEmailAddressException;
import com.xiaomi.accountsdk.account.exception.UserRestrictedException;
import com.xiaomi.accountsdk.account.serverpassthrougherror.data.PassThroughErrorInfo;
import com.xiaomi.accountsdk.account.utils.EUIParamEncryptUtil;
import com.xiaomi.accountsdk.guestaccount.GuestAccountHttpRequester;
import com.xiaomi.accountsdk.hasheddeviceidlib.AccountDeviceIdManager;
import com.xiaomi.accountsdk.hasheddeviceidlib.FidManager;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.CipherException;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import com.xiaomi.accountsdk.request.PassportLoginRequest;
import com.xiaomi.accountsdk.request.PassportRequestArguments;
import com.xiaomi.accountsdk.request.SecureRequestForAccount;
import com.xiaomi.accountsdk.request.SimpleRequest;
import com.xiaomi.accountsdk.request.SimpleRequestForAccount;
import com.xiaomi.accountsdk.request.UploadFileRequest;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.accountsdk.utils.AssertionUtils;
import com.xiaomi.accountsdk.utils.CloudCoder;
import com.xiaomi.accountsdk.utils.EasyMap;
import com.xiaomi.accountsdk.utils.FidSigningUtil;
import com.xiaomi.accountsdk.utils.UserSpaceIdUtil;
import com.xiaomi.accountsdk.utils.XMPassportUtil;
import com.xiaomi.mirror.RemoteDeviceInfo;
import com.xiaomi.mirror.synergy.CallMethod;
import com.xiaomi.onetrack.api.ba;
import com.xiaomi.onetrack.g.a;
import com.xiaomi.opensdk.pdc.asset.AssetConsts;
import com.xiaomi.passport.SecurityDeviceSignManager;
import com.xiaomi.passport.sim.SIMInfo;
import com.xiaomi.passport.utils.AccountHelper;
import com.xiaomi.passport.utils.AccountLoginUploader;
import com.xiaomi.passport.utils.HttpCookies;
import com.xiaomi.passport.utils.HttpHeaders;
import com.xiaomi.uplink.data.UpLinkVerifyResult;
import com.xiaomi.uplink.data.UplinkRemoteConfigInfo;
import com.xiaomi.verificationsdk.internal.Constants;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import org.ccil.cowan.tagsoup.XMLWriter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class XMPassport {
    public static final String EUI_KEY = "EUI";
    private static final String EXTRA_SCOPE = "extra_scope";
    private static final String ICON_SIZE_SUFFIX_320 = "_320";
    public static final String PASSPORT_SAFE_PREFIX = "&&&START&&&";
    private static final String PASSPORT_SID = "passport";
    private static final int RESULT_CODE_ADDRESS_USED_BY_OTHERS = 70013;
    private static final int RESULT_CODE_ADDRESS_USED_BY_SELF = 70021;
    private static final int RESULT_CODE_APP_NAME_FORBIDDEN = 22009;
    public static final int RESULT_CODE_AUTHENTICATE_FAILED = 70016;
    private static final int RESULT_CODE_DELETE_SECURE_ADDRESS = 25009;
    private static final int RESULT_CODE_EMPTY_VERIFY_CODE = 70012;
    private static final long RESULT_CODE_ERROR_INVALID_PWD = 110021001;
    private static final int RESULT_CODE_ERROR_OLD_PWD = 70001;
    private static final long RESULT_CODE_ERROR_PWD_SAME_AS_EMAIL = 110071001;
    private static final int RESULT_CODE_ERROR_REACH_LIMIT = 70022;
    private static final int RESULT_CODE_ERROR_TZ_SIGN = 70069;
    private static final int RESULT_CODE_ERROR_VERIFY_CODE = 70014;
    private static final int RESULT_CODE_EXTERNAL_ADDRESS_USED = 25005;
    private static final int RESULT_CODE_ILLEGAL_PARAM = 10017;
    private static final int RESULT_CODE_INVALID_EMAIL_ADDRESS = 70006;
    private static final int RESULT_CODE_INVALID_PWD_SAME_AS_EMAIL = 10017;
    private static final int RESULT_CODE_LACK_OF_PARAM = 10016;
    private static final int RESULT_CODE_REGISTERED_PHONE = 25001;
    private static final int RESULT_CODE_REQUEST_RESTRICTED = 10031;
    private static final int RESULT_CODE_SUCCESS = 0;
    private static final int RESULT_CODE_USERNAME = 20003;
    private static final int RESULT_CODE_USER_CREATION_OVER_LIMIT = 25004;
    private static final int RESULT_CODE_USER_RESTRICTED = 20023;
    private static final int RESULT_CODE_VERIFICATION = 81003;
    public static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd";
    private static final String TAG = "XMPassport";
    private static final int USER_ADDR_TYPE_EMAIL = 2;
    private static final int USER_ADDR_TYPE_MIUI_FORUM_ALIAS = 9;
    private static final int USER_ADDR_TYPE_PHONE = 1;
    private static final String USER_ID_EXIST = "1";
    private static final String USER_ID_NOT_EXIST = "-1";
    public static final boolean USE_PREVIEW = URLs.USE_PREVIEW;

    @Deprecated
    public static final String ACCOUNT_DOMAIN = URLs.ACCOUNT_DOMAIN;

    @Deprecated
    public static final String URL_ACCOUNT_BASE = URLs.URL_ACCOUNT_BASE;

    @Deprecated
    public static final String URL_ACOUNT_API_BASE = URLs.URL_ACOUNT_API_BASE;

    @Deprecated
    public static final String URL_ACOUNT_API_BASE_SECURE = URLs.URL_ACOUNT_API_BASE_SECURE;

    @Deprecated
    public static final String URL_ACOUNT_API_BASE_V2_SECURE = URLs.URL_ACOUNT_API_BASE_V2_SECURE;

    @Deprecated
    public static final String URL_ACCOUNT_SAFE_API_BASE = URLs.URL_ACCOUNT_SAFE_API_BASE;

    @Deprecated
    public static final String URL_ACCOUNT_API_V2_BASE = URLs.URL_ACCOUNT_API_V2_BASE;

    @Deprecated
    public static final String URL_ACCOUNT_API_V3_BASE = URLs.URL_ACCOUNT_API_V3_BASE;
    private static final Integer INT_0 = 0;
    static boolean sDisableLoginFallbackForTest = false;
    private static final Integer TIMEOUT_LONG_POLLING = 300000;

    private enum CheckAvailibilityType {
        EMAIL,
        PHONE
    }

    public static AccountInfo loginByPassword(String str, String str2, String str3, String str4, String str5, String str6, MetaLoginData metaLoginData) throws InvalidUserNameException, AccessDeniedException, NeedVerificationException, InvalidCredentialException, NeedCaptchaException, IOException, InvalidResponseException, AuthenticationFailureException {
        try {
            return loginByPassword(str, str2, str3, str4, str5, str6, metaLoginData, false);
        } catch (NeedNotificationException unused) {
            throw new InvalidResponseException("Unexpected NeedNotificationException");
        }
    }

    public static AccountInfo loginByPassword(String str, String str2, String str3, String str4, String str5, String str6, MetaLoginData metaLoginData, boolean z) throws NeedNotificationException, InvalidUserNameException, AccessDeniedException, NeedVerificationException, InvalidCredentialException, NeedCaptchaException, IOException, InvalidResponseException, AuthenticationFailureException {
        return loginByPassword(str, str2, str3, str4, str5, str6, metaLoginData, z, null);
    }

    public static AccountInfo loginByPassword(String str, String str2, String str3, String str4, String str5, String str6, MetaLoginData metaLoginData, boolean z, String[] strArr) throws NeedNotificationException, InvalidUserNameException, AccessDeniedException, NeedVerificationException, InvalidCredentialException, NeedCaptchaException, IOException, InvalidResponseException, AuthenticationFailureException {
        return loginByPassword(str, str2, str3, str4, str5, str6, metaLoginData, z, strArr, false);
    }

    public static AccountInfo getStsUrlByPassword(String str, String str2, String str3, String str4, String str5, String str6, String[] strArr) throws NeedNotificationException, InvalidUserNameException, AccessDeniedException, NeedVerificationException, InvalidCredentialException, NeedCaptchaException, IOException, InvalidResponseException, AuthenticationFailureException {
        return loginByPassword(str, str3, str4, str2, str5, str6, null, true, strArr, true);
    }

    @Deprecated
    public static AccountInfo confirmPassword(String str, String str2, String str3, String str4, String str5, String str6) throws NeedNotificationException, InvalidUserNameException, AccessDeniedException, NeedVerificationException, InvalidCredentialException, NeedCaptchaException, IOException, InvalidResponseException, AuthenticationFailureException {
        try {
            return loginByPassword(str, str3, str4, str2, str5, str6, null, false, null, true);
        } catch (NeedNotificationException unused) {
            throw new InvalidResponseException("Unexpected NeedNotificationException");
        }
    }

    static AccountInfo loginByPassword(String str, String str2, String str3, String str4, String str5, String str6, MetaLoginData metaLoginData, boolean z, String[] strArr, boolean z2) throws NeedNotificationException, InvalidUserNameException, AccessDeniedException, NeedVerificationException, InvalidCredentialException, NeedCaptchaException, IOException, InvalidResponseException, AuthenticationFailureException {
        return loginByPassword(new PasswordLoginParams.Builder().setUserId(str).setPassword(str4).setDeviceId(str3).setCaptCode(str5).setCaptIck(str6).setServiceId(str2).setMetaLoginData(metaLoginData).setNeedProcessNotification(z).setIsReturnStsUrl(z2).setHashedEnvFactors(strArr).build());
    }

    /* JADX WARN: Code duplicated, block: B:20:0x0057  */
    /* JADX WARN: Code duplicated, block: B:21:0x005d  */
    /* JADX WARN: Code duplicated, block: B:24:0x00a2  */
    /* JADX WARN: Code duplicated, block: B:27:0x00d6  */
    /* JADX WARN: Code duplicated, block: B:35:0x00f5  */
    /* JADX WARN: Code duplicated, block: B:45:0x00e8 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    public static AccountInfo loginByPassword(PasswordLoginParams passwordLoginParams) throws NeedNotificationException, InvalidUserNameException, AccessDeniedException, NeedVerificationException, InvalidCredentialException, NeedCaptchaException, IOException, InvalidResponseException, AuthenticationFailureException {
        PasswordEncryptor.EncryptedValue encryptedValue;
        EasyMap easyMapEasyPutOpt;
        PassportRequestArguments passportRequestArguments;
        SimpleRequest.StringContent stringContentExecuteEx;
        if (passwordLoginParams == null || passwordLoginParams.password == null) {
            throw new IllegalArgumentException("password params should not be null");
        }
        String str = passwordLoginParams.userId;
        String str2 = passwordLoginParams.password;
        String str3 = passwordLoginParams.deviceId;
        String str4 = TextUtils.isEmpty(passwordLoginParams.serviceId) ? "passport" : passwordLoginParams.serviceId;
        String str5 = passwordLoginParams.verifyToken;
        String str6 = passwordLoginParams.captIck;
        String str7 = passwordLoginParams.captCode;
        String[] strArr = passwordLoginParams.hashedEnvFactors;
        boolean z = passwordLoginParams.returnStsUrl;
        boolean z2 = passwordLoginParams.needProcessNotification;
        MetaLoginData metaLoginData = passwordLoginParams.metaLoginData;
        ActivatorPhoneInfo activatorPhoneInfo = passwordLoginParams.activatorPhoneInfo;
        String str8 = passwordLoginParams.countryCode;
        EasyMap easyMap = new EasyMap();
        PasswordEncryptor passWordEncryptor = XMPassportSettings.getPassWordEncryptor();
        try {
            if (passWordEncryptor != null) {
                try {
                    encryptedValue = passWordEncryptor.getEncryptedValue(CloudCoder.getMd5DigestUpperCase(str2));
                    str2 = str2;
                } catch (PasswordEncryptor.PasswordEncryptorException e) {
                    AccountLogger.log(TAG, "PasswordEncryptorException", e);
                    encryptedValue = null;
                }
                if (encryptedValue != null) {
                    easyMap.easyPut(a.e, encryptedValue.encryptedPassword);
                } else {
                    easyMap.easyPut(a.e, CloudCoder.getMd5DigestUpperCase(str2));
                }
                easyMap.easyPutOpt("user", str).easyPutOpt("sid", str4).easyPutOpt("captCode", str7).easyPutOpt("cc", str8).easyPut("_json", "true");
                fillCommonParams(easyMap);
                easyMapEasyPutOpt = new EasyMap().easyPutOpt("ick", str6).easyPutOpt("ticketToken", passwordLoginParams.ticketToken);
                addDeviceIdInCookies(easyMapEasyPutOpt, str3);
                if (activatorPhoneInfo != null) {
                    easyMap.easyPutOpt("userHash", activatorPhoneInfo.phoneHash);
                    easyMapEasyPutOpt.easyPutOpt("activatorToken", activatorPhoneInfo.activatorToken);
                }
                EasyMap easyMapEasyPutOpt2 = new EasyMap().easyPutOpt("vToken", str5);
                String str9 = URLs.URL_LOGIN_AUTH2_HTTPS;
                passportRequestArguments = new PassportRequestArguments();
                passportRequestArguments.putAllParams(easyMap);
                passportRequestArguments.putAllCookies(easyMapEasyPutOpt);
                passportRequestArguments.putAllHeaders(easyMapEasyPutOpt2);
                passportRequestArguments.setUrl(str9);
                passportRequestArguments.setReadBody(true);
                if (encryptedValue != null) {
                    passportRequestArguments.putHeaderOpt(EUI_KEY, encryptedValue.encryptedEui);
                }
                stringContentExecuteEx = new PassportLoginRequest.ByPassword(passportRequestArguments, str, str4, metaLoginData).executeEx();
                if (stringContentExecuteEx != null) {
                    throw new IOException("failed to get response from server");
                }
                try {
                    return processLoginContent(stringContentExecuteEx, str4, z2, z);
                } catch (PackageNameDeniedException unused) {
                    throw new InvalidResponseException("It's not loginByPassToken(), PackageNameDeniedException is unexpected");
                }
            }
            stringContentExecuteEx = new PassportLoginRequest.ByPassword(passportRequestArguments, str, str4, metaLoginData).executeEx();
            if (stringContentExecuteEx != null) {
                throw new IOException("failed to get response from server");
            }
            return processLoginContent(stringContentExecuteEx, str4, z2, z);
        } catch (PassportCAException unused2) {
            throw new IllegalStateException("this should never happen in product environment.Have you set sDisableLoginFallbackForTest to be true? ");
        }
        encryptedValue = null;
        if (encryptedValue != null) {
            easyMap.easyPut(a.e, encryptedValue.encryptedPassword);
        } else {
            easyMap.easyPut(a.e, CloudCoder.getMd5DigestUpperCase(str2));
        }
        easyMap.easyPutOpt("user", str).easyPutOpt("sid", str4).easyPutOpt("captCode", str7).easyPutOpt("cc", str8).easyPut("_json", "true");
        fillCommonParams(easyMap);
        easyMapEasyPutOpt = new EasyMap().easyPutOpt("ick", str6).easyPutOpt("ticketToken", passwordLoginParams.ticketToken);
        addDeviceIdInCookies(easyMapEasyPutOpt, str3);
        if (activatorPhoneInfo != null) {
            easyMap.easyPutOpt("userHash", activatorPhoneInfo.phoneHash);
            easyMapEasyPutOpt.easyPutOpt("activatorToken", activatorPhoneInfo.activatorToken);
        }
        EasyMap easyMapEasyPutOpt3 = new EasyMap().easyPutOpt("vToken", str5);
        String str10 = URLs.URL_LOGIN_AUTH2_HTTPS;
        passportRequestArguments = new PassportRequestArguments();
        passportRequestArguments.putAllParams(easyMap);
        passportRequestArguments.putAllCookies(easyMapEasyPutOpt);
        passportRequestArguments.putAllHeaders(easyMapEasyPutOpt3);
        passportRequestArguments.setUrl(str10);
        passportRequestArguments.setReadBody(true);
        if (encryptedValue != null) {
            passportRequestArguments.putHeaderOpt(EUI_KEY, encryptedValue.encryptedEui);
        }
    }

    public static String exchangePhoneTokenByTempPhoneToken(String str, String str2, String str3, String str4) throws AccessDeniedException, IOException, InvalidResponseException, AuthenticationFailureException {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            throw new IllegalArgumentException("invalid params");
        }
        try {
            SimpleRequest.StringContent stringContentPostAsString = SimpleRequestForAccount.postAsString(URLs.URL_EXCHANGE_PHONETOKE_HTTPS, new EasyMap().easyPutOpt("tmpPhoneToken", str).easyPutOpt(DevInfoKeys.DEVICEID, getHashedDeviceId()).easyPutOpt(SIMInfo.SIM_INFO_TYPE_SIM_ID, str2).easyPutOpt("iccId", str3).easyPutOpt(SIMInfo.SIM_INFO_TYPE_MCCMNC, str4).easyPutOpt("_json", "true"), null, true);
            if (stringContentPostAsString == null) {
                throw new InvalidResponseException("result content is null");
            }
            JSONObject jSONObject = new JSONObject(removeSafePrefixAndGetRealBody(stringContentPostAsString));
            int i = jSONObject.getInt("code");
            String str5 = "code: " + i + ", desc: " + jSONObject.optString("description");
            AccountLogger.log(TAG, "exchangePhoneTokenBytempPhoneToken: " + str5);
            if (i == 0) {
                return jSONObject.getJSONObject("data").optString("phoneToken");
            }
            throw new InvalidResponseException(str5);
        } catch (JSONException unused) {
            throw new InvalidResponseException("result not json");
        }
    }

    public static AccountInfo getStsUrlByPassToken(String str, String str2, String str3, String str4) throws NeedNotificationException, InvalidUserNameException, AccessDeniedException, PackageNameDeniedException, InvalidCredentialException, IOException, InvalidResponseException, AuthenticationFailureException {
        return loginByPassToken(new PassTokenLoginParams.Builder().userId(str).passToken(str4).serviceId(str2).loginRequestUrl(URLs.URL_LOGIN_HTTPS).deviceId(str3).isReturnStsUrl(true).isGetPhoneTicketLoginMetaData(false).build());
    }

    public static AccountInfo loginByPassTokenNE(String str, String str2, String str3, String str4) throws NeedNotificationException, InvalidUserNameException, AccessDeniedException, PackageNameDeniedException, InvalidCredentialException, IOException, InvalidResponseException, AuthenticationFailureException {
        return loginByPassTokenNE(str, str2, str3, str4, URLs.URL_LOGIN_HTTPS);
    }

    public static AccountInfo loginByPassTokenNE(String str, String str2, String str3, String str4, String str5) throws NeedNotificationException, InvalidUserNameException, AccessDeniedException, PackageNameDeniedException, InvalidCredentialException, IOException, InvalidResponseException, AuthenticationFailureException {
        return loginByPassToken(new PassTokenLoginParams.Builder().userId(str).passToken(str4).serviceId(str2).loginRequestUrl(str5).deviceId(str3).isReturnStsUrl(false).isGetPhoneTicketLoginMetaData(false).build());
    }

    public static AccountInfo loginByPassToken(String str, String str2, String str3, String str4) throws InvalidUserNameException, AccessDeniedException, PackageNameDeniedException, InvalidCredentialException, IOException, InvalidResponseException, AuthenticationFailureException {
        return loginByPassToken(str, str2, str3, str4, URLs.URL_LOGIN_HTTPS);
    }

    public static AccountInfo loginByPassToken(String str, String str2, String str3, String str4, String str5) throws InvalidUserNameException, AccessDeniedException, PackageNameDeniedException, InvalidCredentialException, IOException, InvalidResponseException, AuthenticationFailureException {
        try {
            return loginByPassTokenNE(str, str2, str3, str4, str5);
        } catch (NeedNotificationException unused) {
            throw new InvalidResponseException("Unexpected NeedNotificationException");
        }
    }

    public static AccountInfo loginByPassToken(PassTokenLoginParams passTokenLoginParams) throws NeedNotificationException, InvalidUserNameException, AccessDeniedException, PackageNameDeniedException, InvalidCredentialException, IOException, InvalidResponseException, AuthenticationFailureException {
        if (passTokenLoginParams == null) {
            throw new IllegalArgumentException("passToken login params can not be empty");
        }
        String str = passTokenLoginParams.loginRequestUrl;
        if (TextUtils.isEmpty(str)) {
            str = URLs.URL_LOGIN_HTTPS;
        }
        String str2 = passTokenLoginParams.serviceId;
        if (TextUtils.isEmpty(str2)) {
            str2 = "passport";
        }
        String str3 = str2;
        String str4 = passTokenLoginParams.userId;
        String str5 = passTokenLoginParams.passToken;
        String str6 = passTokenLoginParams.deviceId;
        String str7 = passTokenLoginParams.uDevId;
        boolean z = passTokenLoginParams.returnStsUrl;
        String queryParameter = Uri.parse(str).getQueryParameter("sid");
        EasyMap easyMap = new EasyMap();
        HashSet hashSet = new HashSet();
        if (TextUtils.isEmpty(queryParameter)) {
            easyMap.easyPut("sid", str3);
        }
        easyMap.easyPut("_json", "true");
        if (!TextUtils.isEmpty(passTokenLoginParams.packageName)) {
            easyMap.easyPut(CallMethod.ARG_APP_NAME, passTokenLoginParams.packageName);
        }
        if (passTokenLoginParams.isGetPhoneTicketLoginMetaData) {
            easyMap.put("_loginSign", "ticket");
        }
        fillCommonParams(easyMap);
        EasyMap easyMapEasyPutOpt = new EasyMap().easyPut("userId", str4).easyPutOpt("passToken", str5).easyPutOpt("uDevId", str7);
        addDeviceIdInCookies(easyMapEasyPutOpt, str6);
        PassportRequestArguments passportRequestArguments = new PassportRequestArguments();
        passportRequestArguments.setUrl(str);
        passportRequestArguments.putAllCookies(easyMapEasyPutOpt);
        passportRequestArguments.putAllParams(easyMap);
        passportRequestArguments.setReadBody(true);
        PassportLoginRequest.ByPassToken byPassToken = new PassportLoginRequest.ByPassToken(passportRequestArguments);
        try {
            try {
                if (!TextUtils.isEmpty(passTokenLoginParams.packageName)) {
                    hashSet.add(XMPassportUserAgent.addExtendedCallingPkgNameUserAgent(passTokenLoginParams.packageName));
                }
                SimpleRequest.StringContent stringContentExecuteEx = byPassToken.executeEx();
                if (stringContentExecuteEx == null) {
                    throw new IOException("failed to get response from service server");
                }
                AccountInfo accountInfoProcessLoginContent = processLoginContent(str4, stringContentExecuteEx, str3, true, byPassToken.isResultFromCA(), z);
                AccountLoginUploader.uploadAccountLogin(accountInfoProcessLoginContent.userId, str3);
                Iterator it = hashSet.iterator();
                while (it.hasNext()) {
                    XMPassportUserAgent.removeExtendedUserAgentForCurrentThread((String) it.next());
                }
                return accountInfoProcessLoginContent;
            } catch (PassportCAException unused) {
                throw new IllegalStateException();
            } catch (NeedCaptchaException unused2) {
                throw new InvalidResponseException("Unexpected NeedCaptchaException");
            } catch (NeedVerificationException unused3) {
                throw new InvalidResponseException("Unexpected NeedVerificationException");
            }
        } catch (Throwable th) {
            Iterator it2 = hashSet.iterator();
            while (it2.hasNext()) {
                XMPassportUserAgent.removeExtendedUserAgentForCurrentThread((String) it2.next());
            }
            throw th;
        }
    }

    public static boolean uploadLoginSucceed(String str, String str2, String str3, String str4, String str5, boolean z, String str6, int i, boolean z2) {
        if (TextUtils.isEmpty(str2)) {
            AccountLogger.log(TAG, "uploadLoginSucceed>>>bad serviceToken");
            return false;
        }
        String str7 = URLs.URL_LOGIN_SUCCESS_UPLOAD;
        EasyMap easyMapEasyPut = new EasyMap().easyPut("userId", str).easyPut("deviceId", str3).easyPut("sid", str5).easyPut("userSpaceId", str4).easyPut("notifyNow", String.valueOf(z)).easyPut("oldUploadDeviceId", str6).easyPut("deviceIdSource", String.valueOf(i)).easyPut("supportSystemAccountDevId", String.valueOf(z2)).easyPut("uDevId", AccountHelper.getUDevId(XMPassportSettings.getApplicationContext(), str));
        EasyMap easyMap = new EasyMap();
        easyMap.easyPut("serviceToken", str2);
        try {
            return new JSONObject(removeSafePrefixAndGetRealBody(SimpleRequestForAccount.postAsString(str7, easyMapEasyPut, easyMap, true))).getInt("code") == INT_0.intValue();
        } catch (AccessDeniedException | AuthenticationFailureException | IOException | JSONException e) {
            AccountLogger.log(TAG, "uploadLoginSucceed>>>error", e);
            return false;
        }
    }

    public static JSONObject getNotificationContentByLocale(String str, String str2, String str3, String str4) {
        try {
            JSONObject jSONObject = new JSONObject(removeSafePrefixAndGetRealBody(SimpleRequestForAccount.getAsString(URLs.URL_LOGIN_NOTIFY_INFO, new EasyMap().easyPut("userId", str).easyPut("language", str3).easyPut(DevInfoKeys.DEVICEID, str4).easyPut("loginDevice", str2), null, true)));
            if (jSONObject.getInt("code") == INT_0.intValue()) {
                AccountLogger.log(TAG, "success get notification content");
                return jSONObject.getJSONObject("data");
            }
        } catch (AccessDeniedException | AuthenticationFailureException | IOException | JSONException e) {
            AccountLogger.log(TAG, "getNotificationContentByLocale error", e);
        }
        return null;
    }

    public static AccountInfo loginByStep2(String str, String str2, String str3, String str4, MetaLoginData metaLoginData, boolean z, String str5) throws AccessDeniedException, NeedVerificationException, IOException, InvalidResponseException, AuthenticationFailureException {
        return loginByStep2(str, str2, str3, str4, metaLoginData, z, str5, false);
    }

    public static AccountInfo getStsUrlByStep2(String str, String str2, String str3, String str4, MetaLoginData metaLoginData, boolean z, String str5) throws AccessDeniedException, NeedVerificationException, IOException, InvalidResponseException, AuthenticationFailureException {
        return loginByStep2(str, str2, str3, str4, metaLoginData, z, str5, true);
    }

    private static AccountInfo loginByStep2(String str, String str2, String str3, String str4, MetaLoginData metaLoginData, boolean z, String str5, boolean z2) throws AccessDeniedException, NeedVerificationException, IOException, InvalidResponseException, AuthenticationFailureException {
        return loginByStep2(new Step2LoginParams.Builder().setUserId(str).setServiceId(str2).setDeviceId(str3).setStep2code(str4).setMetaLoginData(metaLoginData).setTrust(z).setStep1Token(str5).setReturnStsUrl(z2).build());
    }

    public static AccountInfo loginByStep2(Step2LoginParams step2LoginParams) throws AccessDeniedException, NeedVerificationException, IOException, InvalidResponseException, AuthenticationFailureException {
        if (step2LoginParams == null) {
            throw new IllegalArgumentException("step2 params is null");
        }
        String str = step2LoginParams.userId;
        String str2 = step2LoginParams.step2code;
        MetaLoginData metaLoginData = step2LoginParams.metaLoginData;
        String str3 = TextUtils.isEmpty(step2LoginParams.serviceId) ? "passport" : step2LoginParams.serviceId;
        boolean z = step2LoginParams.trust;
        boolean z2 = step2LoginParams.returnStsUrl;
        String str4 = step2LoginParams.deviceId;
        String str5 = step2LoginParams.step1Token;
        if (str == null || str2 == null || metaLoginData == null) {
            throw new NullPointerException("invalid params");
        }
        String str6 = URLs.URL_LOGIN_AUTH_STEP2;
        EasyMap easyMapEasyPut = new EasyMap().easyPut("user", str).easyPut("code", str2).easyPut("_sign", metaLoginData.sign).easyPut("qs", metaLoginData.qs).easyPut(CallMethod.ARG_CALLBACK, metaLoginData.callback).easyPut("trust", z ? "true" : "false").easyPutOpt("sid", str3).easyPut("_json", "true");
        fillCommonParams(easyMapEasyPut);
        EasyMap easyMapEasyPut2 = new EasyMap().easyPut("step1Token", str5);
        addDeviceIdInCookies(easyMapEasyPut2, str4);
        SimpleRequest.StringContent stringContentPostAsString = SimpleRequestForAccount.postAsString(str6, easyMapEasyPut, easyMapEasyPut2, true);
        if (stringContentPostAsString == null) {
            throw new IOException("failed to get response from service server");
        }
        try {
            return processLoginContent(stringContentPostAsString, str3, false, z2);
        } catch (InvalidCredentialException unused) {
            throw new InvalidResponseException("Unexpected InvalidCredentialException");
        } catch (InvalidUserNameException unused2) {
            throw new InvalidResponseException("Unexpected InvalidUserNameException");
        } catch (NeedCaptchaException unused3) {
            throw new InvalidResponseException("Unexpected NeedCaptchaException");
        } catch (NeedNotificationException unused4) {
            throw new InvalidResponseException("Unexpected NeedNotificationException");
        } catch (PackageNameDeniedException unused5) {
            throw new InvalidResponseException("It's not loginByPassToken(), PackageNameDeniedException is unexpected");
        }
    }

    public static Pair<Bitmap, String> getCaptchaImage(String str) {
        return getCaptchaImageAndIck(ACCOUNT_DOMAIN + str);
    }

    public static Pair<Bitmap, String> getCaptchaImageAndIck(String str) {
        SimpleRequest.StreamContent asStream;
        try {
            asStream = SimpleRequestForAccount.getAsStream(str, null, null);
        } catch (AccessDeniedException e) {
            AccountLogger.log(TAG, "getCaptchaImageAndIck", e);
            asStream = null;
        } catch (AuthenticationFailureException e2) {
            AccountLogger.log(TAG, "getCaptchaImageAndIck", e2);
            asStream = null;
        } catch (IOException e3) {
            AccountLogger.log(TAG, "getCaptchaImageAndIck", e3);
            asStream = null;
        }
        if (asStream == null) {
            return null;
        }
        try {
            return Pair.create(BitmapFactory.decodeStream(asStream.getStream()), asStream.getHeader("ick"));
        } finally {
            asStream.closeStream();
        }
    }

    protected static String getClientSign(Long l, String str) {
        TreeMap treeMap = new TreeMap();
        treeMap.put(Constants.NONCE, String.valueOf(l));
        return CloudCoder.generateSignature(null, null, treeMap, str);
    }

    public static MetaLoginData getMetaLoginData(String str, String str2) throws InvalidUserNameException, AccessDeniedException, IOException, InvalidResponseException, AuthenticationFailureException {
        try {
            loginByPassToken(str, str2, null, null);
            throw new InvalidResponseException("Unexpected login success with empty pass token");
        } catch (InvalidCredentialException e) {
            return e.getMetaLoginData();
        } catch (PackageNameDeniedException unused) {
            throw new InvalidResponseException("PackageNameDeniedException is unexpected with empty userId or passToken");
        }
    }

    private static MetaLoginData getMetaLoginData(String str) throws AccessDeniedException, IOException, InvalidResponseException, AuthenticationFailureException {
        try {
            loginByPassToken(null, str, null, null);
            throw new InvalidResponseException("Unexpected login success with empty pass token");
        } catch (InvalidCredentialException e) {
            return e.getMetaLoginData();
        } catch (InvalidUserNameException unused) {
            throw new InvalidResponseException("should not be throw this exception");
        } catch (PackageNameDeniedException unused2) {
            throw new InvalidResponseException("PackageNameDeniedException is unexpected with empty userId or passToken");
        }
    }

    private static AccountInfo processLoginContent(SimpleRequest.StringContent stringContent, String str, boolean z, boolean z2) throws NeedNotificationException, InvalidUserNameException, AccessDeniedException, PackageNameDeniedException, NeedVerificationException, InvalidCredentialException, NeedCaptchaException, IOException, InvalidResponseException, AuthenticationFailureException {
        return processLoginContent(stringContent, str, z, false, z2);
    }

    private static AccountInfo processLoginContent(SimpleRequest.StringContent stringContent, String str, boolean z, boolean z2, boolean z3) throws NeedNotificationException, InvalidUserNameException, AccessDeniedException, PackageNameDeniedException, NeedVerificationException, InvalidCredentialException, NeedCaptchaException, IOException, InvalidResponseException, AuthenticationFailureException {
        return processLoginContent(null, stringContent, str, z, z2, z3);
    }

    private static AccountInfo processLoginContent(String str, SimpleRequest.StringContent stringContent, String str2, boolean z, boolean z2, boolean z3) throws NeedNotificationException, InvalidUserNameException, AccessDeniedException, PackageNameDeniedException, NeedVerificationException, NeedCaptchaException, InvalidCredentialException, IOException, InvalidResponseException, AuthenticationFailureException {
        String header;
        String header2;
        try {
            JSONObject jSONObject = new JSONObject(removeSafePrefixAndGetRealBody(stringContent));
            int i = jSONObject.getInt("code");
            String string = jSONObject.getString("desc");
            PassThroughErrorInfo passThroughErrorInfo = new PassThroughErrorInfo(jSONObject);
            AccountLogger.log(TAG, "processLoginContent, code: " + i + ", desc: " + string);
            if (i == 0) {
                if (z2) {
                    header = jSONObject.optString("userId");
                    header2 = jSONObject.optString("passToken");
                } else {
                    header = stringContent.getHeader("userId");
                    header2 = stringContent.getHeader("passToken");
                }
                if (z && str != null && jSONObject.optBoolean("disableHotfixMiui73508", false)) {
                    z = false;
                }
                int iOptInt = jSONObject.optInt("securityStatus", 0);
                AccountLogger.log(TAG, "securityStatus: " + iOptInt);
                if (!z || iOptInt == 0) {
                    if (TextUtils.isEmpty(header)) {
                        throw new InvalidResponseException("no user Id");
                    }
                    if (TextUtils.isEmpty(header2)) {
                        throw new InvalidResponseException("no passToken in login response");
                    }
                    return parseLoginResult(header, stringContent, str2, null, z2, z3);
                }
                String string2 = jSONObject.getString("notificationUrl");
                if (string2 == null) {
                    throw new InvalidResponseException("noticationUrl is null");
                }
                if (string2.startsWith("http")) {
                    throw new NeedNotificationException(header, string2, stringContent);
                }
                throw new NeedNotificationException(header, ACCOUNT_DOMAIN + string2, stringContent);
            }
            if (i == 20003) {
                throw new InvalidUserNameException();
            }
            if (i == 22009) {
                PackageNameDeniedException packageNameDeniedException = new PackageNameDeniedException(i, string);
                AccountLogger.log(TAG, packageNameDeniedException);
                throw packageNameDeniedException;
            }
            if (i == 70002) {
                throw new InvalidCredentialException(i, string, false);
            }
            if (i == 70016) {
                String string3 = jSONObject.getString("_sign");
                String string4 = jSONObject.getString("qs");
                String string5 = jSONObject.getString(CallMethod.ARG_CALLBACK);
                String string6 = jSONObject.getString("captchaUrl");
                if (TextUtils.equals("null", string6)) {
                    string6 = null;
                }
                throw new InvalidCredentialException(i, string, true).metaLoginData(new MetaLoginData(string3, string4, string5)).captchaUrl(string6);
            }
            if (i != RESULT_CODE_VERIFICATION) {
                if (i != 87001) {
                    throw new InvalidResponseException(i, string, passThroughErrorInfo);
                }
                throw new NeedCaptchaException(i, string, jSONObject.getString("captchaUrl"), jSONObject.optString("type"));
            }
            throw new NeedVerificationException(new MetaLoginData(jSONObject.getString("_sign"), jSONObject.getString("qs"), jSONObject.getString(CallMethod.ARG_CALLBACK)), stringContent.getHeader("step1Token"), jSONObject.optString("userId"));
        } catch (JSONException unused) {
            AccountLogger.log(TAG, "processLoginContent: " + stringContent);
            throw new InvalidResponseException("processLoginContent JSONException");
        }
    }

    private static AccountInfo parseLoginResult(String str, SimpleRequest.StringContent stringContent, String str2, String str3, boolean z, boolean z2) throws AccessDeniedException, IOException, InvalidResponseException, AuthenticationFailureException {
        String header;
        String header2;
        Boolean boolValueOf;
        try {
            JSONObject jSONObject = new JSONObject(removeSafePrefixAndGetRealBody(stringContent));
            if (z) {
                header = jSONObject.optString("passToken");
                header2 = jSONObject.optString("cUserId");
            } else {
                header = stringContent.getHeader("passToken");
                header2 = stringContent.getHeader("cUserId");
            }
            String strOptString = jSONObject.optString("ssecurity");
            Long lValueOf = Long.valueOf(jSONObject.optLong(Constants.NONCE));
            String strOptString2 = jSONObject.optString("psecurity");
            if (strOptString == null || lValueOf == null || strOptString2 == null) {
                try {
                    String header3 = stringContent.getHeader(GuestAccountHttpRequester.HEADER_KEY_EXTENSION_PRAGMA);
                    if (TextUtils.isEmpty(header3)) {
                        header3 = stringContent.getHeader("Extension-Pragma");
                        if (TextUtils.isEmpty(header3)) {
                            throw new InvalidResponseException("empty extension-pragma");
                        }
                    }
                    JSONObject jSONObject2 = new JSONObject(header3);
                    strOptString = jSONObject2.optString("ssecurity");
                    lValueOf = Long.valueOf(jSONObject2.optLong(Constants.NONCE));
                    strOptString2 = jSONObject2.optString("psecurity");
                } catch (JSONException unused) {
                }
            }
            if (strOptString == null || lValueOf == null || strOptString2 == null) {
                throw new InvalidResponseException("security, nonce or psecurity is null");
            }
            String header4 = stringContent.getHeader("re-pass-token");
            boolean z3 = jSONObject.optInt("pwd") == 1;
            int iOptInt = jSONObject.optInt("child", -1);
            String header5 = stringContent.getHeader("haveLocalUpChannel");
            String string = jSONObject.getString("location");
            AccountInfo.Builder builderPsecurity = new AccountInfo.Builder().userId(str).encryptedUserId(header2).serviceId(str2).passToken(header).psecurity(strOptString2);
            if (str3 != null) {
                string = str3;
            }
            AccountInfo.Builder builderHasPwd = builderPsecurity.autoLoginUrl(string).rePassToken(header4).hasPwd(z3);
            if (iOptInt == -1) {
                boolValueOf = null;
            } else {
                boolean z4 = true;
                if (iOptInt != 1) {
                    z4 = false;
                }
                boolValueOf = Boolean.valueOf(z4);
            }
            AccountInfo accountInfoBuild = builderHasPwd.isChild(boolValueOf).security(strOptString).hasLocalChannel(TextUtils.isEmpty(header5) ? null : Boolean.valueOf(Boolean.parseBoolean(header5))).build();
            if (TextUtils.isEmpty(str2) || "passport".equals(str2) || z2) {
                return accountInfoBuild;
            }
            try {
                try {
                    return getServiceTokenByStsUrl(accountInfoBuild, lValueOf);
                } catch (AuthenticationFailureException e) {
                    AccountLogger.log(TAG, "sts url request error", e);
                    e.stsUrlRequestError(str2);
                    throw e;
                } catch (InvalidResponseException e2) {
                    AccountLogger.log(TAG, "sts url request error", e2);
                    e2.stsUrlRequestError(str2);
                    throw e2;
                }
            } catch (AccessDeniedException e3) {
                AccountLogger.log(TAG, "sts url request error", e3);
                e3.stsUrlRequestError(str2);
                throw e3;
            } catch (IOException e4) {
                AccountLogger.log(TAG, "sts url request error", e4);
                PassportIOException passportIOException = new PassportIOException(e4);
                passportIOException.stsUrlRequestError(str2);
                throw passportIOException;
            }
        } catch (JSONException unused2) {
            AccountLogger.log(TAG, "parseLoginResult: " + stringContent);
            throw new InvalidResponseException("parseLoginResult JSONException");
        }
    }

    private static AccountInfo getServiceTokenByStsUrl(AccountInfo accountInfo, Long l) throws AccessDeniedException, IOException, InvalidResponseException, AuthenticationFailureException {
        String str = accountInfo.serviceId;
        AccountLogger.log(TAG, "start sts request: " + str);
        String clientSign = getClientSign(l, accountInfo.security);
        if (clientSign == null) {
            AccountLogger.log(TAG, "failed to get client sign");
            throw new InvalidResponseException(0, "sign parameters failure");
        }
        SimpleRequest.StringContent asString = SimpleRequestForAccount.getAsString(accountInfo.getAutoLoginUrl(), new EasyMap().easyPut("clientSign", clientSign).easyPut("_userIdNeedEncrypt", "true"), null, false);
        if (asString == null) {
            throw new InvalidResponseException(0, "no response when get service token");
        }
        String header = asString.getHeader(String.format("%s_serviceToken", str));
        if (TextUtils.isEmpty(header)) {
            header = asString.getHeader("serviceToken");
            if (TextUtils.isEmpty(header)) {
                throw new InvalidResponseException(0, "no service token contained in callback cookies: " + str);
            }
        }
        String header2 = asString.getHeader(str + "_slh");
        String header3 = asString.getHeader(str + "_ph");
        Set<String> cookieKeys = asString.getCookieKeys();
        HashMap map = new HashMap();
        for (String str2 : cookieKeys) {
            map.put(str2, asString.getHeader(str2));
        }
        return new AccountInfo.Builder().userId(accountInfo.userId).serviceId(str).passToken(accountInfo.passToken).encryptedUserId(accountInfo.encryptedUserId).serviceToken(header).stsCookies(HttpCookies.stringify(map)).security(accountInfo.security).psecurity(accountInfo.psecurity).hasLocalChannel(accountInfo.hasLocalChannel).slh(header2).ph(header3).rePassToken(accountInfo.rePassToken).hasPwd(accountInfo.hasPwd).isChild(accountInfo.isChild).build();
    }

    @Deprecated
    public static String regByPhone(String str, String str2, String str3) throws IOException, InvalidResponseException {
        Object obj;
        String str4 = URLs.URL_REG_PHONE;
        EasyMap easyMapEasyPut = new EasyMap().easyPut("phone", str).easyPut("password", str2).easyPut("ticket", str3);
        fillCommonParams(easyMapEasyPut);
        EasyMap easyMap = new EasyMap();
        SimpleRequest.MapContent mapContentPostAsMap = null;
        addDeviceIdInCookies(easyMap, null);
        try {
            mapContentPostAsMap = SimpleRequestForAccount.postAsMap(str4, easyMapEasyPut, easyMap, true);
        } catch (AccessDeniedException e) {
            e.printStackTrace();
        } catch (AuthenticationFailureException e2) {
            e2.printStackTrace();
        }
        if (mapContentPostAsMap == null) {
            throw new IOException("failed to register, no response");
        }
        Object fromBody = mapContentPostAsMap.getFromBody("code");
        if (INT_0.equals(fromBody)) {
            Object fromBody2 = mapContentPostAsMap.getFromBody("data");
            if ((fromBody2 instanceof Map) && (obj = ((Map) fromBody2).get("userId")) != null) {
                return obj.toString();
            }
        }
        if (USE_PREVIEW) {
            AccountLogger.log(TAG, String.format("register failed, code: %s, description: %s", fromBody, mapContentPostAsMap.getFromBody("description")));
        }
        throw new InvalidResponseException("failed to register due to invalid response from server");
    }

    @Deprecated
    public static String regByEmail(String str, String str2, String str3, String str4) throws NeedCaptchaException, IOException, InvalidResponseException {
        try {
            return regByEmail(new EmailRegisterParams.Builder().email(str).password(str2).captchaCode(str3, str4).build());
        } catch (UsedEmailAddressException e) {
            AccountLogger.log(TAG, "email used");
            throw new InvalidResponseException(e.getMessage());
        }
    }

    public static String regByEmail(EmailRegisterParams emailRegisterParams) throws UsedEmailAddressException, NeedCaptchaException, IOException, InvalidResponseException {
        if (emailRegisterParams == null) {
            throw new IllegalArgumentException("email params should not be null");
        }
        String str = emailRegisterParams.emailAddress;
        String str2 = emailRegisterParams.password;
        String str3 = emailRegisterParams.captCode;
        String str4 = emailRegisterParams.captIck;
        String str5 = emailRegisterParams.region;
        String str6 = emailRegisterParams.serviceId;
        String strReplaceUrlHost = replaceUrlHost(URLs.URL_REGISTER, str5);
        EasyMap easyMapEasyPut = new EasyMap().easyPut(NotificationCompat.CATEGORY_EMAIL, str).easyPut("password", str2).easyPut("_json", "true").easyPutOpt("inputcode", str3).easyPutOpt("region", str5).easyPutOpt("sid", str6).easyPut("acceptLicense", "true");
        fillCommonParams(easyMapEasyPut);
        EasyMap easyMapEasyPutOpt = new EasyMap().easyPutOpt("ick", str4);
        SimpleRequest.StringContent stringContentPostAsString = null;
        addDeviceIdInCookies(easyMapEasyPutOpt, null);
        try {
            stringContentPostAsString = SimpleRequestForAccount.postAsString(strReplaceUrlHost, (Map<String, String>) easyMapEasyPut, (Map<String, String>) easyMapEasyPutOpt, (Map<String, String>) new EasyMap().easyPutOpt("vToken", emailRegisterParams.verifyToken), (Map<String, String>) null, true, (Integer) null);
        } catch (AccessDeniedException e) {
            e.printStackTrace();
        } catch (AuthenticationFailureException e2) {
            e2.printStackTrace();
        }
        if (stringContentPostAsString == null) {
            throw new IOException("failed to register, no response");
        }
        try {
            JSONObject jSONObject = new JSONObject(removeSafePrefixAndGetRealBody(stringContentPostAsString));
            int i = jSONObject.getInt("code");
            PassThroughErrorInfo passThroughErrorInfo = new PassThroughErrorInfo(jSONObject);
            String strOptString = jSONObject.optString("description");
            if (i == 0) {
                return jSONObject.getString("userId");
            }
            if (i == RESULT_CODE_EXTERNAL_ADDRESS_USED) {
                throw new UsedEmailAddressException(strOptString);
            }
            if (i == 87001) {
                throw new NeedCaptchaException(i, strOptString, URLs.URL_REG_GET_CAPTCHA_CODE, jSONObject.optString("type"));
            }
            throw new InvalidResponseException(i, strOptString, passThroughErrorInfo);
        } catch (JSONException e3) {
            AccountLogger.log(TAG, "json error", e3);
            throw new InvalidResponseException("json error");
        }
    }

    private static String replaceUrlHost(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return str;
        }
        Application applicationContext = XMPassportSettings.getApplicationContext();
        String strBlockingGetRegHostName = applicationContext == null ? null : new RegionConfig(applicationContext).blockingGetRegHostName(str2);
        return TextUtils.isEmpty(strBlockingGetRegHostName) ? str : str.replaceFirst(URLs.HOST_URL_ACCOUNT_BASE, strBlockingGetRegHostName);
    }

    public static void getRegisterVerifyCode(String str, String str2, String str3) throws AccessDeniedException, RegisteredPhoneException, NeedCaptchaException, IOException, InvalidResponseException, AuthenticationFailureException {
        String str4 = URLs.URL_REG_GET_VERIFY_CODE;
        EasyMap easyMapEasyPut = new EasyMap().easyPut("phone", str);
        if (str2 != null) {
            easyMapEasyPut.easyPut("icode", str2);
        }
        try {
            JSONObject jSONObject = new JSONObject(removeSafePrefixAndGetRealBody(SimpleRequestForAccount.postAsString(str4, easyMapEasyPut, str3 != null ? new EasyMap().easyPutOpt("ick", str3) : null, true)));
            int i = jSONObject.getInt("code");
            if (i != 0) {
                if (i == 20031) {
                    throw new NeedCaptchaException(i, "", jSONObject.getString(com.market.sdk.utils.Constants.JSON_FILTER_INFO));
                }
                if (i == RESULT_CODE_REGISTERED_PHONE) {
                    throw new RegisteredPhoneException("phone is registered");
                }
                throw new InvalidResponseException("process result is failed");
            }
        } catch (JSONException e) {
            AccountLogger.log(TAG, "getRegisterVerifyCode ", e);
            throw new InvalidResponseException("process result is failed");
        }
    }

    public static void checkRegisterVerifyCode(String str, String str2) throws AccessDeniedException, IOException, InvalidResponseException, AuthenticationFailureException {
        try {
            if (new JSONObject(removeSafePrefixAndGetRealBody(SimpleRequestForAccount.getAsString(URLs.URL_REG_CHECK_VERIFY_CODE, new EasyMap().easyPut("phone", str).easyPut("ticket", str2), null, true))).getInt("code") == 0) {
            } else {
                throw new InvalidResponseException("invalid response, failed to check register verify code");
            }
        } catch (JSONException unused) {
            throw new InvalidResponseException("invalid response, fail to convert to JSON");
        }
    }

    public static boolean checkEmailAvailability(String str) throws IOException, InvalidResponseException {
        String userIdForAddress = getUserIdForAddress(str, CheckAvailibilityType.EMAIL);
        if ("1".equals(userIdForAddress)) {
            return false;
        }
        if (USER_ID_NOT_EXIST.equals(userIdForAddress)) {
            return true;
        }
        throw new InvalidResponseException(String.format("url %s should only return 1 or -1 as user id, but actually return %s", URLs.URL_USER_EXISTS, userIdForAddress));
    }

    public static boolean checkPhoneAvailability(Context context, String str, String str2) throws IOException, InvalidResponseException {
        String userIdForAddress = getUserIdForAddress(str, CheckAvailibilityType.PHONE);
        if ("1".equals(userIdForAddress)) {
            return false;
        }
        if (USER_ID_NOT_EXIST.equals(userIdForAddress)) {
            return true;
        }
        throw new InvalidResponseException(String.format("url %s should only return 1 or -1 as user id, but actually return %s", URLs.URL_USER_EXISTS, userIdForAddress));
    }

    private static String getUserIdForAddress(String str, CheckAvailibilityType checkAvailibilityType) throws IOException, InvalidResponseException {
        Object obj;
        SimpleRequest.MapContent asMap = null;
        try {
            asMap = SimpleRequestForAccount.getAsMap(URLs.URL_USER_EXISTS, new EasyMap().easyPut("type", checkAvailibilityType == CheckAvailibilityType.EMAIL ? "EM" : "PH").easyPut("externalId", str), null, true);
        } catch (AccessDeniedException e) {
            e.printStackTrace();
        } catch (AuthenticationFailureException e2) {
            e2.printStackTrace();
        }
        if (asMap == null) {
            throw new IOException("failed to get response when getting user id");
        }
        if (INT_0.equals(asMap.getFromBody("code"))) {
            Object fromBody = asMap.getFromBody("data");
            if ((fromBody instanceof Map) && (obj = ((Map) fromBody).get("userId")) != null) {
                return obj.toString();
            }
        }
        throw new InvalidResponseException(String.format("server error when getting user id, reason:%s, description:%s, code:%s", asMap.getFromBody("reason"), asMap.getFromBody("description"), asMap.getFromBody("code")));
    }

    @Deprecated
    public static void sendActivateEmail(String str, String str2) throws IOException, InvalidResponseException {
        SimpleRequest.MapContent asMap = null;
        try {
            asMap = SimpleRequestForAccount.getAsMap(URLs.URL_RESEND_EMAIL, new EasyMap().easyPut("userId", str).easyPut("addressType", "EM").easyPut(RemoteDeviceInfo.KEY_ADDRESS, str2), null, true);
        } catch (AccessDeniedException e) {
            e.printStackTrace();
        } catch (AuthenticationFailureException e2) {
            e2.printStackTrace();
        }
        if (asMap == null) {
            throw new IOException("failed to register, no response");
        }
        if (!INT_0.equals(asMap.getFromBody("code"))) {
            throw new InvalidResponseException("invalid response, failed to send activate email");
        }
    }

    @Deprecated
    public static void sendPhoneRegTicket(String str, String str2, String str3, String str4) throws AccessDeniedException, SendVerifyCodeExceedLimitException, NeedCaptchaException, IOException, InvalidResponseException, AuthenticationFailureException {
        sendPhoneRegTicket(new SendPhoneTicketParams.Builder().phone(str).deviceId(str2).captchaCode(str3, str4).build());
    }

    public static void sendPhoneRegTicket(SendPhoneTicketParams sendPhoneTicketParams) throws AccessDeniedException, SendVerifyCodeExceedLimitException, NeedCaptchaException, IOException, InvalidResponseException, AuthenticationFailureException {
        if (sendPhoneTicketParams == null) {
            throw new IllegalArgumentException("send phone reg ticket params should not be null");
        }
        String str = sendPhoneTicketParams.phone;
        String str2 = sendPhoneTicketParams.deviceId;
        String str3 = sendPhoneTicketParams.region;
        String str4 = sendPhoneTicketParams.verifyToken;
        String str5 = sendPhoneTicketParams.captCode;
        String str6 = sendPhoneTicketParams.captIck;
        String strReplaceUrlHost = replaceUrlHost(URLs.URL_REG_SEND_PHONE_TICKET, str3);
        EasyMap easyMapEasyPutOpt = new EasyMap().easyPut("phone", str).easyPutOpt("icode", str5).easyPutOpt("region", str3);
        fillCommonParams(easyMapEasyPutOpt);
        EasyMap easyMap = new EasyMap();
        easyMap.easyPutOpt("ick", str6);
        addDeviceIdInCookies(easyMap, str2);
        try {
            JSONObject jSONObject = new JSONObject(removeSafePrefixAndGetRealBody(SimpleRequestForAccount.postAsString(strReplaceUrlHost, (Map<String, String>) easyMapEasyPutOpt, (Map<String, String>) easyMap, (Map<String, String>) new EasyMap().easyPut("vToken", str4), (Map<String, String>) null, true, (Integer) null)));
            int i = jSONObject.getInt("code");
            String strOptString = jSONObject.optString("description");
            PassThroughErrorInfo passThroughErrorInfo = new PassThroughErrorInfo(jSONObject);
            if (i != 0) {
                if (i != 20031) {
                    if (i == 70022) {
                        throw new SendVerifyCodeExceedLimitException(strOptString);
                    }
                    if (i != 87001) {
                        throw new InvalidResponseException(i, strOptString, passThroughErrorInfo);
                    }
                }
                throw new NeedCaptchaException(i, strOptString, jSONObject.getString(com.market.sdk.utils.Constants.JSON_FILTER_INFO), jSONObject.optString("type"));
            }
        } catch (JSONException e) {
            throw new InvalidResponseException("JSON error", e);
        }
    }

    @Deprecated
    public static RegisterUserInfo checkRegisterPhone(String str, String str2, String str3, String str4) throws AccessDeniedException, InvalidPhoneOrTicketException, IOException, InvalidResponseException, UserRestrictedException, AuthenticationFailureException {
        return checkRegisterPhone(new CheckRegPhoneParams.Builder().phoneTicket(str, str2).activatedPhone(str3, null, null).deviceId(str4).build());
    }

    @Deprecated
    public static RegisterUserInfo checkRegisterPhone(String str, String str2, String str3, String str4, String str5, String str6) throws AccessDeniedException, InvalidPhoneOrTicketException, IOException, InvalidResponseException, UserRestrictedException, AuthenticationFailureException {
        return checkRegisterPhone(new CheckRegPhoneParams.Builder().phoneTicket(str, str2).activatedPhone(str3, str5, str6).deviceId(str4).build());
    }

    public static RegisterUserInfo checkRegisterPhone(CheckRegPhoneParams checkRegPhoneParams) throws AccessDeniedException, InvalidPhoneOrTicketException, IOException, InvalidResponseException, UserRestrictedException, AuthenticationFailureException {
        if (checkRegPhoneParams == null) {
            throw new IllegalArgumentException("check reg phone params can not be null");
        }
        String str = checkRegPhoneParams.phone;
        String str2 = checkRegPhoneParams.ticket;
        String str3 = checkRegPhoneParams.hashedSimId;
        String str4 = checkRegPhoneParams.vKey2;
        String str5 = checkRegPhoneParams.vKey2Nonce;
        String str6 = checkRegPhoneParams.deviceId;
        String str7 = checkRegPhoneParams.region;
        String strReplaceUrlHost = replaceUrlHost(URLs.URL_REG_VERIFY_PHONE, str7);
        EasyMap easyMapEasyPutOpt = new EasyMap().easyPut("phone", str).easyPutOpt("ticket", str2).easyPutOpt(SIMInfo.SIM_INFO_TYPE_SIM_ID, str3).easyPutOpt("vkey2", str4).easyPutOpt(Constants.NONCE, str5).easyPutOpt("region", str7);
        fillCommonParams(easyMapEasyPutOpt);
        EasyMap easyMap = new EasyMap();
        addDeviceIdInCookies(easyMap, str6);
        SimpleRequest.StringContent stringContentPostAsString = SimpleRequestForAccount.postAsString(strReplaceUrlHost, easyMapEasyPutOpt, easyMap, true);
        try {
            JSONObject jSONObject = new JSONObject(removeSafePrefixAndGetRealBody(stringContentPostAsString));
            PassThroughErrorInfo passThroughErrorInfo = new PassThroughErrorInfo(jSONObject);
            int i = jSONObject.getInt("code");
            String str8 = "code: " + i + ", desc: " + jSONObject.optString("description");
            if (i == 0) {
                JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                String header = stringContentPostAsString.getHeader("ticketToken");
                if (header == null) {
                    throw new InvalidResponseException("fail to get ticketToken");
                }
                return new RegisterUserInfo.Builder(jSONObject2.getInt("status")).phone(str).userId(jSONObject2.optString("userId", null)).maskedUserId(jSONObject2.optString("maskedUserId", null)).userName(jSONObject2.optString("userName", null)).avatarAddress(jSONObject2.optString("portraitUrl", null)).bindTime(jSONObject2.optLong("bindTime", 0L)).needGetActiveTime(jSONObject2.optBoolean("needGetActiveTime", false)).needToast(jSONObject2.optBoolean("needToast", false)).ticketToken(header).build();
            }
            if (i == 10017) {
                throw new InvalidPhoneOrTicketException(str8);
            }
            if (i == 20023) {
                throw new UserRestrictedException(str8);
            }
            throw new InvalidResponseException(i, str8, passThroughErrorInfo);
        } catch (JSONException e) {
            throw new InvalidResponseException("process result is failed", e);
        }
    }

    @Deprecated
    public static String regByPhoneWithToken(String str, String str2, String str3, String str4) throws AccessDeniedException, InvalidParameterException, IOException, InvalidResponseException, TokenExpiredException, AuthenticationFailureException {
        try {
            return regByPhoneWithToken(new PhoneTokenRegisterParams.Builder().phoneTicketToken(str, str3).password(str2).region(str4).build()).getUserId();
        } catch (ReachLimitException e) {
            throw new InvalidResponseException(e.getMessage());
        } catch (UserRestrictedException e2) {
            throw new InvalidResponseException(e2.getMessage());
        }
    }

    public static AccountInfo regByPhoneWithToken(PhoneTokenRegisterParams phoneTokenRegisterParams) throws AccessDeniedException, InvalidParameterException, ReachLimitException, IOException, InvalidResponseException, TokenExpiredException, UserRestrictedException, AuthenticationFailureException {
        if (phoneTokenRegisterParams == null) {
            throw new IllegalArgumentException("phone can not be empty");
        }
        String str = phoneTokenRegisterParams.phone;
        String str2 = phoneTokenRegisterParams.phoneHash;
        String str3 = phoneTokenRegisterParams.password;
        String str4 = phoneTokenRegisterParams.region;
        String str5 = phoneTokenRegisterParams.ticketToken;
        String str6 = phoneTokenRegisterParams.activatorToken;
        boolean z = phoneTokenRegisterParams.noPwd;
        String str7 = phoneTokenRegisterParams.serviceId;
        String strReplaceUrlHost = replaceUrlHost(URLs.URL_REG_TOKEN, str4);
        EasyMap easyMapEasyPut = new EasyMap().easyPutOpt("phone", str).easyPutOpt("phoneHash", str2).easyPutOpt("password", str3).easyPut("noPwd", String.valueOf(z)).easyPut("_locale", XMPassportUtil.getISOLocaleString(Locale.getDefault())).easyPutOpt("region", str4).easyPutOpt("sid", str7).easyPut("_json", "true").easyPut("acceptLicense", "true");
        fillCommonParams(easyMapEasyPut);
        EasyMap easyMapEasyPutOpt = new EasyMap().easyPutOpt("activatorToken", str6).easyPutOpt("ticketToken", str5);
        addDeviceIdInCookies(easyMapEasyPutOpt, null);
        SimpleRequest.StringContent stringContentPostAsString = SimpleRequestForAccount.postAsString(strReplaceUrlHost, easyMapEasyPut, easyMapEasyPutOpt, true);
        try {
            JSONObject jSONObject = new JSONObject(removeSafePrefixAndGetRealBody(stringContentPostAsString));
            int i = jSONObject.getInt("code");
            String strOptString = jSONObject.optString("description");
            String str8 = "code: " + i + ", desc: " + strOptString;
            if (i == 0) {
                String header = stringContentPostAsString.getHeader("userId");
                String header2 = stringContentPostAsString.getHeader("cUserId");
                String header3 = stringContentPostAsString.getHeader("passToken");
                String strOptString2 = jSONObject.optString("user_synced_url");
                String header4 = stringContentPostAsString.getHeader("haveLocalUpChannel");
                return new AccountInfo.Builder().userId(header).encryptedUserId(header2).passToken(header3).hasPwd(!TextUtils.isEmpty(str3)).userSyncedUrl(strOptString2).hasLocalChannel(TextUtils.isEmpty(header4) ? null : Boolean.valueOf(Boolean.parseBoolean(header4))).build();
            }
            if (i == 10017) {
                throw new InvalidParameterException(i, strOptString);
            }
            if (i == 21317 || i == 21327) {
                throw new TokenExpiredException(str8);
            }
            if (i == 20023) {
                throw new UserRestrictedException(str8);
            }
            if (i == RESULT_CODE_USER_CREATION_OVER_LIMIT) {
                throw new ReachLimitException(str8);
            }
            throw new InvalidResponseException(i, str8, new PassThroughErrorInfo(jSONObject));
        } catch (JSONException e) {
            throw new InvalidResponseException("process result is failed", e);
        }
    }

    public static void resetPassword(String str, String str2, String str3, String str4, String str5) throws InvalidParameterException, AccessDeniedException, IOException, InvalidResponseException, AuthenticationFailureException {
        try {
            String strRemoveSafePrefixAndGetRealBody = removeSafePrefixAndGetRealBody(SimpleRequestForAccount.postAsString(URLs.URL_RESET_PASSWORD, new EasyMap().easyPut("userId", str).easyPut("password", str2).easyPut("_json", "true").easyPutOpt("passportsecurity_ph", str4), new EasyMap().easyPut("userId", str).easyPutOpt("serviceToken", str3).easyPutOpt("passportsecurity_ph", str4).easyPutOpt("passportsecurity_slh", str5), true));
            JSONObject jSONObject = new JSONObject(strRemoveSafePrefixAndGetRealBody);
            long j = jSONObject.getLong("result");
            if (j == 0) {
                return;
            }
            if (j != RESULT_CODE_ERROR_INVALID_PWD && j != RESULT_CODE_ERROR_PWD_SAME_AS_EMAIL) {
                throw new InvalidResponseException((int) j, "reset password fail: " + strRemoveSafePrefixAndGetRealBody, new PassThroughErrorInfo(jSONObject));
            }
            throw new InvalidParameterException("invalid password");
        } catch (JSONException e) {
            throw new InvalidResponseException("process result is failed", e);
        }
    }

    @Deprecated
    public static XiaomiUserInfo getXiaomiUserInfo(String str, String str2, String str3) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        return getXiaomiUserInfo(new PassportInfo(str, null, null, str2, str3));
    }

    @Deprecated
    public static XiaomiUserInfo getXiaomiUserInfo(String str, String str2, String str3, String str4) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        return getXiaomiUserInfo(new PassportInfo(str, str2, null, str3, str4));
    }

    public static XiaomiUserInfo getXiaomiUserInfo(PassportInfo passportInfo) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        ArrayList arrayList = new ArrayList();
        arrayList.add(XiaomiUserCoreInfo.Flag.BASE_INFO);
        arrayList.add(XiaomiUserCoreInfo.Flag.BIND_ADDRESS);
        return new XiaomiUserInfo(passportInfo.getUserId(), getXiaomiUserCoreInfo(passportInfo, null, arrayList));
    }

    @Deprecated
    public static XiaomiUserProfile getXiaomiUserProfile(String str, String str2, String str3, String str4, String str5) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        return getXiaomiUserProfile(new PassportInfo(str, str2, str3, str4, str5));
    }

    public static XiaomiUserProfile getXiaomiUserProfile(PassportInfo passportInfo) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        ArrayList arrayList = new ArrayList();
        arrayList.add(XiaomiUserCoreInfo.Flag.BASE_INFO);
        arrayList.add(XiaomiUserCoreInfo.Flag.EXTRA_INFO);
        XiaomiUserCoreInfo xiaomiUserCoreInfo = getXiaomiUserCoreInfo(passportInfo, null, arrayList);
        if (xiaomiUserCoreInfo == null) {
            return null;
        }
        XiaomiUserProfile xiaomiUserProfile = new XiaomiUserProfile(passportInfo.getUserId());
        xiaomiUserProfile.setUserName(xiaomiUserCoreInfo.userName);
        xiaomiUserProfile.setBirthday(xiaomiUserCoreInfo.birthday);
        xiaomiUserProfile.setGender(xiaomiUserCoreInfo.gender);
        return xiaomiUserProfile;
    }

    @Deprecated
    public static MiCloudAuthInfo getOAuthInfo(Context context, String str, String str2, String str3, String str4, Bundle bundle, String str5, String str6) throws NeedOAuthorizeException, AccessDeniedException, IOException, AuthenticationFailureException {
        String string = bundle.getString(EXTRA_SCOPE);
        if (string == null || !string.equals("3")) {
            throw new NeedOAuthorizeException();
        }
        try {
            return tryGetOAuthInfoQuietly(new OAuthParameter.Builder().userId(str).clientId(str2).redirectUri(str3).deviceId(str4).scope("3").serviceToken(str5).responseType(Constants.VERIFY_SUCESS_TOKEN).build());
        } catch (InvalidResponseException unused) {
            throw new NeedOAuthorizeException();
        }
    }

    public static MiCloudAuthInfo tryGetOAuthInfoQuietly(OAuthParameter oAuthParameter) throws NeedOAuthorizeException, AccessDeniedException, IOException, InvalidResponseException, AuthenticationFailureException {
        String str = URLs.URL_AUTH2_AUTHORIZE;
        EasyMap easyMapEasyPutOpt = new EasyMap().easyPutOpt("serviceToken", oAuthParameter.serviceToken);
        if (oAuthParameter.useCUserId && !TextUtils.isEmpty(oAuthParameter.cUserId)) {
            easyMapEasyPutOpt.easyPutOpt("cUserId", oAuthParameter.cUserId);
        } else {
            easyMapEasyPutOpt.easyPutOpt("userId", oAuthParameter.userId);
        }
        easyMapEasyPutOpt.easyPutOpt("deviceId", oAuthParameter.deviceId);
        easyMapEasyPutOpt.easyPutOpt("userSpaceId", UserSpaceIdUtil.getNullableUserSpaceIdCookie());
        if (TextUtils.isEmpty(oAuthParameter.responseType)) {
            oAuthParameter.responseType = Constants.VERIFY_SUCESS_TOKEN;
        }
        EasyMap easyMapEasyPut = new EasyMap().easyPut("client_id", oAuthParameter.clientId).easyPut("redirect_uri", oAuthParameter.redirectUri).easyPut("response_type", oAuthParameter.responseType).easyPut(Action.SCOPE_ATTRIBUTE, oAuthParameter.scope).easyPut("skip_confirm", "true").easyPut("state", oAuthParameter.state).easyPut("package_data", oAuthParameter.packageData).easyPut("_json", "true");
        if (!TextUtils.isEmpty(oAuthParameter.aUthorizedDeviceId) && !TextUtils.isEmpty(oAuthParameter.aUthorizedDeviceId.trim())) {
            easyMapEasyPut.easyPutOpt("device_id", oAuthParameter.aUthorizedDeviceId);
        }
        easyMapEasyPut.easyPutOpt("pt", oAuthParameter.platform);
        return parseOAuthInfoResult(oAuthParameter.responseType, SimpleRequestForAccount.postAsString(str, easyMapEasyPut, easyMapEasyPutOpt, true));
    }

    static MiCloudAuthInfo parseOAuthInfoResult(String str, SimpleRequest.StringContent stringContent) throws NeedOAuthorizeException, IOException, InvalidResponseException, AuthenticationFailureException {
        String string;
        String string2;
        String string3;
        String string4;
        String string5;
        String queryParameter;
        if (str == null) {
            throw new IllegalArgumentException("response type is null");
        }
        if (stringContent == null) {
            throw new IOException("failed to get response to get Auth2 auth info");
        }
        String str2 = stringContent.getHeaders().get("Content-Type");
        if (str2 == null || !str2.toLowerCase().contains("json")) {
            throw new NeedOAuthorizeException("contentType error : " + str2);
        }
        try {
            String strRemoveSafePrefixAndGetRealBody = removeSafePrefixAndGetRealBody(stringContent);
            if (strRemoveSafePrefixAndGetRealBody == null) {
                throw new InvalidResponseException("empty response");
            }
            JSONObject jSONObject = new JSONObject(strRemoveSafePrefixAndGetRealBody);
            int i = jSONObject.getInt("code");
            if (i == 70016) {
                throw new AuthenticationFailureException(strRemoveSafePrefixAndGetRealBody);
            }
            if (i != 0 || !str.equals(jSONObject.getString("result"))) {
                throw new NeedOAuthorizeException();
            }
            JSONObject jSONObject2 = jSONObject.getJSONObject("data");
            String str3 = null;
            if ("code".equals(str)) {
                queryParameter = Uri.parse(jSONObject2.getString("redirectUrl")).getQueryParameter("code");
                if (TextUtils.isEmpty(queryParameter)) {
                    throw new NeedOAuthorizeException();
                }
                string5 = null;
                string = null;
                string2 = null;
                string3 = null;
                string4 = null;
            } else {
                String string6 = jSONObject2.getString("access_token");
                if (TextUtils.isEmpty(string6)) {
                    throw new NeedOAuthorizeException();
                }
                string = jSONObject2.getString("expires_in");
                string2 = jSONObject2.getString(Action.SCOPE_ATTRIBUTE);
                string3 = jSONObject2.getString("token_type");
                string4 = jSONObject2.getString("mac_key");
                string5 = jSONObject2.getString("mac_algorithm");
                str3 = string6;
                queryParameter = null;
            }
            MiCloudAuthInfo miCloudAuthInfo = new MiCloudAuthInfo();
            miCloudAuthInfo.setAccessToken(str3);
            if (string != null) {
                miCloudAuthInfo.setExpires(Integer.valueOf(string).intValue());
            }
            miCloudAuthInfo.setScope(string2);
            miCloudAuthInfo.setTokenType(string3);
            miCloudAuthInfo.setMacKey(string4);
            miCloudAuthInfo.setMacAlgorithm(string5);
            miCloudAuthInfo.setCode(queryParameter);
            return miCloudAuthInfo;
        } catch (JSONException e) {
            throw new NeedOAuthorizeException(e.getMessage());
        }
    }

    public static String removeSafePrefixAndGetRealBody(SimpleRequest.StringContent stringContent) throws IOException {
        if (stringContent == null) {
            throw new IOException("failed to get response to check register verify code");
        }
        String body = stringContent.getBody();
        return body.startsWith(PASSPORT_SAFE_PREFIX) ? body.substring(PASSPORT_SAFE_PREFIX.length()) : body;
    }

    public static String getThirdPartyAccessToken(String str, String str2, String str3, String str4) throws AccessDeniedException, IOException, AuthenticationFailureException {
        SimpleRequest.StringContent asString = SimpleRequestForAccount.getAsString(URLs.OPEN_URL_GET_ACCESS_TOKEN, new EasyMap().easyPut("userId", str).easyPut("snsType", str3).easyPut("sid", str2), new EasyMap().easyPut("userId", str).easyPut("serviceToken", str4), true);
        if (asString == null) {
            throw new IOException("failed to get response to get access token");
        }
        return asString.toString();
    }

    public static String refreshThirdPartyAccessToken(String str, String str2, String str3, String str4) throws AccessDeniedException, IOException, AuthenticationFailureException {
        SimpleRequest.StringContent asString = SimpleRequestForAccount.getAsString(URLs.OPEN_URL_REFRESH_ACCESS_TOKEN, new EasyMap().easyPut("userId", str).easyPut("snsType", str3).easyPut("sid", str2), new EasyMap().easyPut("userId", str).easyPut("serviceToken", str4), true);
        if (asString == null) {
            throw new IOException("failed to get response to refresh access token");
        }
        return asString.toString();
    }

    public static HashMap<String, Object> getDeviceInfo(PassportInfo passportInfo, String str, List<String> list) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        return DeviceInfoHelper.getDeviceInfo(passportInfo, str, list);
    }

    public static ArrayList<HashMap<String, Object>> getAllDevicesInfo(PassportInfo passportInfo, ArrayList<String> arrayList) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        return DeviceInfoHelper.getAllDevicesInfo(passportInfo, arrayList);
    }

    @Deprecated
    public static boolean uploadDeviceInfo(String str, String str2, String str3, String str4, String str5, Map<String, Object> map) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        return uploadDeviceInfo(new PassportInfo(str, str2, null, str3, str4), str5, map);
    }

    public static boolean uploadDeviceInfo(PassportInfo passportInfo, String str, Map<String, Object> map) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        return DeviceInfoHelper.uploadDeviceInfo(passportInfo, str, map);
    }

    @Deprecated
    public static void uploadXiaomiUserName(String str, String str2, String str3, String str4, String str5, String str6) throws AccessDeniedException, InvalidParameterException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        uploadXiaomiUserProfile(str, str2, str3, str4, str5, str6, null, null);
    }

    @Deprecated
    public static void uploadXiaomiUserBirthday(String str, String str2, String str3, String str4, String str5, Calendar calendar) throws AccessDeniedException, InvalidParameterException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        uploadXiaomiUserProfile(str, str2, str3, str4, str5, null, calendar, null);
    }

    @Deprecated
    public static void uploadXiaomiUserGender(String str, String str2, String str3, String str4, String str5, Gender gender) throws AccessDeniedException, InvalidParameterException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        uploadXiaomiUserProfile(str, str2, str3, str4, str5, null, null, gender);
    }

    @Deprecated
    public static void uploadXiaomiUserProfile(String str, String str2, String str3, String str4, String str5, String str6, Calendar calendar, Gender gender) throws AccessDeniedException, InvalidParameterException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        uploadXiaomiUserProfile(new PassportInfo(str, str2, str3, str4, str5), new XiaomiUserProfile(str, str6, calendar, gender));
    }

    public static void uploadXiaomiUserProfile(PassportInfo passportInfo, XiaomiUserProfile xiaomiUserProfile) throws AccessDeniedException, InvalidParameterException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        if (passportInfo == null || xiaomiUserProfile == null) {
            throw new IllegalArgumentException("invalid parameter");
        }
        String str = URLs.URL_ACCOUNT_USER_PROFILE;
        Calendar birthday = xiaomiUserProfile.getBirthday();
        SimpleRequest.MapContent mapContentPostAsMap = SecureRequestForAccount.postAsMap(str, new EasyMap().easyPut("userId", passportInfo.getUserId()).easyPut("sid", passportInfo.getServiceId()).easyPut("transId", UUID.randomUUID().toString().substring(0, 15)).easyPut("userName", xiaomiUserProfile.getUserName()).easyPut(ba.c, birthday != null ? new SimpleDateFormat("yyyy-MM-dd").format(birthday.getTime()) : null).easyPut(ba.b, xiaomiUserProfile.getGender() != null ? xiaomiUserProfile.getGender().getType() : null), getPassportCookie(passportInfo), true, passportInfo.getSecurity());
        if (mapContentPostAsMap == null) {
            throw new InvalidResponseException("failed to upload xiaomi user profile");
        }
        Integer num = (Integer) mapContentPostAsMap.getFromBody("code");
        PassThroughErrorInfo passThroughErrorInfo = new PassThroughErrorInfo(mapContentPostAsMap);
        if (INT_0.equals(num)) {
            return;
        }
        String str2 = (String) mapContentPostAsMap.getFromBody("description");
        String str3 = "code: " + num + ", desc: " + str2;
        AccountLogger.log(TAG, "failed to upload xiaomi user info, " + str3);
        int iIntValue = num.intValue();
        if (iIntValue == 10017) {
            throw new InvalidParameterException(num.intValue(), str2);
        }
        if (iIntValue == 66108) {
            throw new InvalidParameterException(num.intValue(), str2);
        }
        throw new InvalidResponseException(str3, passThroughErrorInfo);
    }

    @Deprecated
    public static String uploadXiaomiUserIcon(String str, String str2, String str3, String str4, String str5, Bitmap bitmap) throws AccessDeniedException, InvalidParameterException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        return uploadXiaomiUserIcon(new PassportInfo(str, str2, str3, str4, str5), bitmap);
    }

    public static String uploadXiaomiUserIcon(PassportInfo passportInfo, Bitmap bitmap) throws Throwable {
        if (passportInfo == null || bitmap == null) {
            throw new IllegalArgumentException("invalid parameter");
        }
        AccountLogger.log(TAG, "requestUploadUserIcon start: ");
        String strRequestUploadUserIcon = requestUploadUserIcon(passportInfo);
        AccountLogger.log(TAG, "uploadIconToServer start: ");
        JSONObject jSONObjectUploadIconToServer = uploadIconToServer(strRequestUploadUserIcon, bitmap);
        AccountLogger.log(TAG, "commitUploadUserIcon start: ");
        return commitUploadUserIcon(passportInfo, jSONObjectUploadIconToServer);
    }

    private static String requestUploadUserIcon(PassportInfo passportInfo) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        SimpleRequest.MapContent asMap = SecureRequestForAccount.getAsMap(URLs.URL_REQUEST_UPDATE_ICON, new EasyMap().easyPut("userId", passportInfo.getUserId()).easyPut(XMLWriter.METHOD, "json"), getPassportCookie(passportInfo), true, passportInfo.getSecurity());
        if (asMap == null) {
            throw new InvalidResponseException("requestUploadUserIcon request content is null");
        }
        Object fromBody = asMap.getFromBody("code");
        PassThroughErrorInfo passThroughErrorInfo = new PassThroughErrorInfo(asMap);
        if (INT_0.equals(fromBody)) {
            Object fromBody2 = asMap.getFromBody("data");
            if (fromBody2 instanceof Map) {
                Object obj = ((Map) fromBody2).get("uploadUrl");
                if (obj == null) {
                    throw new InvalidResponseException("uploadUrl is null");
                }
                return obj.toString();
            }
        }
        Object fromBody3 = asMap.getFromBody("description");
        AccountLogger.log(TAG, "requestUploadUserIcon failed, code: " + fromBody + "; description: " + fromBody3);
        throw new InvalidResponseException("requestUploadUserIcon failed, description: " + fromBody3, passThroughErrorInfo);
    }

    private static JSONObject uploadIconToServer(String str, Bitmap bitmap) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        String strExecute = UploadFileRequest.execute(str, new ByteArrayInputStream(byteArrayOutputStream.toByteArray()), "userfile", "icon.jpg");
        try {
            if (!TextUtils.isEmpty(strExecute)) {
                return new JSONObject(strExecute);
            }
        } catch (JSONException e) {
            AccountLogger.log(TAG, "uploadIconToServer error", e);
        }
        throw new InvalidResponseException("upload error: " + strExecute);
    }

    private static String commitUploadUserIcon(PassportInfo passportInfo, JSONObject jSONObject) throws AccessDeniedException, InvalidParameterException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        SimpleRequest.MapContent mapContentPostAsMap = SecureRequestForAccount.postAsMap(URLs.URL_COMMIT_UPDATE_ICON, new EasyMap().easyPut("userId", passportInfo.getUserId()).easyPut("sid", passportInfo.getServiceId()).easyPut("transId", UUID.randomUUID().toString().substring(0, 15)).easyPut("json", Base64.encodeToString(jSONObject.toString().getBytes(), 2)), getPassportCookie(passportInfo), true, passportInfo.getSecurity());
        if (mapContentPostAsMap == null) {
            throw new InvalidResponseException("commitUploadUserIcon content is null");
        }
        Integer num = (Integer) mapContentPostAsMap.getFromBody("code");
        String str = (String) mapContentPostAsMap.getFromBody("description");
        PassThroughErrorInfo passThroughErrorInfo = new PassThroughErrorInfo(mapContentPostAsMap);
        AccountLogger.log(TAG, "commitUploadUserIcon failed, code: " + num + "; description: " + str);
        int iIntValue = num.intValue();
        if (iIntValue != 0) {
            if (iIntValue == 66108) {
                throw new InvalidParameterException(num.intValue(), str);
            }
            throw new InvalidResponseException(num.intValue(), str, passThroughErrorInfo);
        }
        Object fromBody = mapContentPostAsMap.getFromBody("data");
        if (!(fromBody instanceof Map)) {
            return null;
        }
        Object obj = ((Map) fromBody).get(AssetConsts.J_DOWNLOAD_URL);
        if (obj == null) {
            throw new InvalidResponseException("downloadUrl is null");
        }
        return obj.toString();
    }

    public static String getIdentityAuthUrl(PassportInfo passportInfo, String str, IdentityAuthReason identityAuthReason) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        return getIdentityAuthUrl(passportInfo, str, getUrlForIdentityAuth(identityAuthReason));
    }

    private static String getIdentityAuthUrl(PassportInfo passportInfo, String str, String str2) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        if (passportInfo == null) {
            throw new IllegalArgumentException("passportInfo is null");
        }
        String strSubstring = UUID.randomUUID().toString().substring(0, 15);
        SimpleRequest.MapContent asMap = SecureRequestForAccount.getAsMap(str2, new EasyMap().easyPut("userId", passportInfo.getUserId()).easyPut("_json", String.valueOf(true)).easyPut("authST", str).easyPut("transId", strSubstring).easyPut("traceId", strSubstring), getPassportCookie(passportInfo), true, passportInfo.getSecurity());
        if (asMap == null) {
            throw new IOException("getIdentityAuthUrl result should not be null");
        }
        Object fromBody = asMap.getFromBody("code");
        String str3 = "code: " + fromBody + ", desc: " + asMap.getFromBody("description");
        PassThroughErrorInfo passThroughErrorInfo = new PassThroughErrorInfo(asMap);
        AccountLogger.log(TAG, "getIdentityAuthUrl" + str3);
        if (fromBody instanceof Integer) {
            int iIntValue = ((Integer) fromBody).intValue();
            if (iIntValue == 0) {
                return null;
            }
            if (iIntValue == 2) {
                Object fromBody2 = asMap.getFromBody("url");
                if (fromBody2 == null) {
                    throw new InvalidResponseException("identityUrl is null");
                }
                return fromBody2.toString();
            }
        }
        throw new InvalidResponseException("getIdentityAuthUrl: " + str3, passThroughErrorInfo);
    }

    private static String getUrlForIdentityAuth(IdentityAuthReason identityAuthReason) {
        if (identityAuthReason == null) {
            throw new IllegalArgumentException("identityAuthReason is null");
        }
        switch (AnonymousClass1.$SwitchMap$com$xiaomi$accountsdk$account$data$IdentityAuthReason[identityAuthReason.ordinal()]) {
            case 1:
                return URLs.URL_IDENTITY_AUTH_FOR_ADDING_EMAIL;
            case 2:
                return URLs.URL_IDENTITY_AUTH_FOR_REPLACING_EMAIL;
            case 3:
                return URLs.URL_IDENTITY_AUTH_FOR_ADDING_PHONE;
            case 4:
                return URLs.URL_IDENTITY_AUTH_FOR_REPLACING_PHONE;
            case 5:
                return URLs.URL_IDENTITY_AUTH_FOR_DELETING_PHONE;
            case 6:
                return URLs.URL_IDENTITY_AUTH_FOR_SEND_EMAIL_ACTIVATE_MESSAGE;
            case 7:
                return URLs.URL_IDENTITY_AUTH_FOR_SET_SECURITY_QUESTIONS;
            case 8:
                return URLs.URL_IDENTITY_AUTH_FOR_MODIFY_SAFE_PHONE;
            case 9:
                return URLs.URL_IDENTITY_AUTH_FOR_CHANGE_PWD;
            default:
                throw new IllegalArgumentException("invalid identityAuthReason");
        }
    }

    private static JSONArray convertSQsToJsonArray(List<SecurityQuestion> list) {
        JSONArray jSONArray = new JSONArray();
        for (SecurityQuestion securityQuestion : list) {
            if (securityQuestion != null) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("q", securityQuestion.question);
                    jSONObject.put(HtmlParser.LinkCardElement.TAG, securityQuestion.answer);
                } catch (JSONException e) {
                    AccountLogger.log(TAG, "convertSQsToJsonArray", e);
                }
                jSONArray.put(jSONObject);
            }
        }
        return jSONArray;
    }

    @Deprecated
    public static void setSecurityQuestions(PassportInfo passportInfo, List<SecurityQuestion> list, String str) throws AccessDeniedException, InvalidParameterException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        if (passportInfo == null || list == null || list.size() == 0) {
            throw new IllegalArgumentException("invalid param");
        }
        String str2 = URLs.URL_SET_SECURITY_QUESTIONS;
        JSONArray jSONArrayConvertSQsToJsonArray = convertSQsToJsonArray(list);
        SimpleRequest.MapContent mapContentPostAsMap = SecureRequestForAccount.postAsMap(str2, new EasyMap().easyPut("userId", passportInfo.getUserId()).easyPut("questions", jSONArrayConvertSQsToJsonArray != null ? jSONArrayConvertSQsToJsonArray.toString() : null).easyPut("sid", passportInfo.getServiceId()).easyPut("authST", str), getPassportCookie(passportInfo), true, passportInfo.getSecurity());
        if (mapContentPostAsMap == null) {
            throw new IOException("failed to setSecurityQuestions");
        }
        Object fromBody = mapContentPostAsMap.getFromBody("code");
        Object fromBody2 = mapContentPostAsMap.getFromBody("description");
        if (fromBody instanceof Integer) {
            int iIntValue = ((Integer) fromBody).intValue();
            if (iIntValue == 0) {
                return;
            }
            if (iIntValue == 10016 || iIntValue == 10017) {
                throw new InvalidParameterException("code: " + fromBody + " ;description: " + fromBody2);
            }
        }
        throw new InvalidResponseException("code: " + fromBody + " ;description: " + fromBody2);
    }

    public static void sendEmailActivateMessage(PassportInfo passportInfo, String str, String str2, String str3, String str4, String str5) throws AccessDeniedException, UsedEmailAddressException, ReachLimitException, CipherException, NeedCaptchaException, IOException, InvalidResponseException, InvalidBindAddressException, AuthenticationFailureException {
        sendEmailActivateMessage(passportInfo, str, str2, str3, str4, str5, null);
    }

    public static void sendEmailActivateMessage(PassportInfo passportInfo, String str, String str2, String str3, String str4, String str5, String str6) throws AccessDeniedException, UsedEmailAddressException, ReachLimitException, CipherException, NeedCaptchaException, IOException, InvalidResponseException, InvalidBindAddressException, AuthenticationFailureException {
        sendEmailActivateMessage(new SendEmailActMsgParams.Builder().passportInfo(passportInfo).emailAddress(str).deviceId(CloudCoder.hashDeviceInfo(str3)).identityAuthToken(str2).captcha(str4, str5).verifyToken(str6).build());
    }

    public static void sendEmailActivateMessage(SendEmailActMsgParams sendEmailActMsgParams) throws AccessDeniedException, UsedEmailAddressException, ReachLimitException, CipherException, NeedCaptchaException, IOException, InvalidResponseException, InvalidBindAddressException, AuthenticationFailureException {
        if (sendEmailActMsgParams == null || sendEmailActMsgParams.passportInfo == null) {
            throw new IllegalArgumentException("params should not be null!");
        }
        String str = URLs.URL_SEND_EMAIL_ACTIVATE_MESSAGE;
        PassportInfo passportInfo = sendEmailActMsgParams.passportInfo;
        String str2 = sendEmailActMsgParams.email;
        String str3 = sendEmailActMsgParams.deviceId;
        String str4 = sendEmailActMsgParams.identityAuthToken;
        String str5 = sendEmailActMsgParams.captcha;
        String str6 = sendEmailActMsgParams.captIck;
        String str7 = sendEmailActMsgParams.verifyToken;
        EasyMap easyMapEasyPut = new EasyMap().easyPut("userId", passportInfo.getUserId()).easyPut(RemoteDeviceInfo.KEY_ADDRESS, str2).easyPut("sid", passportInfo.getServiceId()).easyPut("deviceId", str3).easyPutOpt("userSpaceId", UserSpaceIdUtil.getNullableUserSpaceIdCookie()).easyPut("authST", str4).easyPut("icode", str5);
        EasyMap<String, String> passportCookie = getPassportCookie(passportInfo);
        passportCookie.easyPut("ick", str6);
        EasyMap easyMap = new EasyMap();
        easyMap.easyPut("vToken", str7);
        SimpleRequest.MapContent mapContentPostAsMap = SecureRequestForAccount.postAsMap(str, easyMapEasyPut, passportCookie, true, passportInfo.getSecurity(), null, easyMap);
        if (mapContentPostAsMap == null) {
            throw new IOException("failed to checkAvailabilityOfBindingEmail");
        }
        Integer num = (Integer) mapContentPostAsMap.getFromBody("code");
        String str8 = (String) mapContentPostAsMap.getFromBody("description");
        String str9 = "code: " + num + " ;description: " + str8;
        PassThroughErrorInfo passThroughErrorInfo = new PassThroughErrorInfo(mapContentPostAsMap);
        switch (num.intValue()) {
            case 0:
                return;
            case 20031:
            case ServerErrorCode.ERROR_WRONG_CAPTCHA /* 87001 */:
                throw new NeedCaptchaException(num.intValue(), str8, (String) mapContentPostAsMap.getFromBody(com.market.sdk.utils.Constants.JSON_FILTER_INFO), (String) mapContentPostAsMap.getFromBody("type"));
            case RESULT_CODE_INVALID_EMAIL_ADDRESS /* 70006 */:
                throw new InvalidBindAddressException(str9);
            case RESULT_CODE_ADDRESS_USED_BY_OTHERS /* 70013 */:
            case RESULT_CODE_ADDRESS_USED_BY_SELF /* 70021 */:
                throw new UsedEmailAddressException(str9);
            case 70022:
                throw new ReachLimitException(str9);
            default:
                throw new InvalidResponseException(num.intValue(), str9, passThroughErrorInfo);
        }
    }

    @Deprecated
    public static boolean checkEmailAvailabilityForBinding(PassportInfo passportInfo, String str, String str2, String str3) throws AccessDeniedException, CipherException, NeedCaptchaException, IOException, InvalidResponseException, InvalidBindAddressException, AuthenticationFailureException {
        if (passportInfo == null) {
            throw new IllegalArgumentException("passportInfo is null");
        }
        String str4 = URLs.URL_CHECK_SAFE_EMAIL_AVAILABILITY;
        EasyMap easyMapEasyPut = new EasyMap().easyPut("userId", passportInfo.getUserId()).easyPut(RemoteDeviceInfo.KEY_ADDRESS, str).easyPut("icode", str2);
        EasyMap<String, String> passportCookie = getPassportCookie(passportInfo);
        passportCookie.easyPut("ick", str3);
        SimpleRequest.MapContent mapContentPostAsMap = SecureRequestForAccount.postAsMap(str4, easyMapEasyPut, passportCookie, true, passportInfo.getSecurity());
        if (mapContentPostAsMap == null) {
            throw new IOException("failed to checkAvailabilityOfBindingEmail");
        }
        int iIntValue = ((Integer) mapContentPostAsMap.getFromBody("code")).intValue();
        String str5 = (String) mapContentPostAsMap.getFromBody("description");
        int iIntValue2 = Integer.valueOf(iIntValue).intValue();
        if (iIntValue2 == 0) {
            return true;
        }
        if (iIntValue2 == RESULT_CODE_INVALID_EMAIL_ADDRESS) {
            throw new InvalidBindAddressException("code: " + iIntValue + " ;description: " + str5);
        }
        if (iIntValue2 == RESULT_CODE_ADDRESS_USED_BY_OTHERS || iIntValue2 == RESULT_CODE_ADDRESS_USED_BY_SELF) {
            return false;
        }
        if (iIntValue2 == 87001) {
            throw new NeedCaptchaException(iIntValue, str5, null);
        }
        throw new InvalidResponseException(iIntValue, str5);
    }

    @Deprecated
    public static void sendVerifyCodeForBindingPhoneOrEmail(PassportInfo passportInfo, String str, BindingType bindingType) throws AccessDeniedException, SendVerifyCodeExceedLimitException, CipherException, IOException, InvalidResponseException, InvalidBindAddressException, AuthenticationFailureException {
        if (bindingType == null) {
            throw new IllegalArgumentException("type is null");
        }
        sendVerifyCode(passportInfo, str, bindingType.isBindingEmail() ? URLs.URL_SEND_BIND_EMAIL_VERIFY_CODE : URLs.URL_SEND_BIND_PHONE_VERIFY_CODE);
    }

    private static void sendVerifyCode(PassportInfo passportInfo, String str, String str2) throws AccessDeniedException, SendVerifyCodeExceedLimitException, CipherException, IOException, InvalidResponseException, InvalidBindAddressException, AuthenticationFailureException {
        if (passportInfo == null) {
            throw new IllegalArgumentException("passportInfo is null");
        }
        EasyMap easyMapEasyPut = new EasyMap().easyPut("userId", passportInfo.getUserId()).easyPut(RemoteDeviceInfo.KEY_ADDRESS, str);
        easyMapEasyPut.putAll(XMPassportUtil.getDefaultLocaleParam());
        SimpleRequest.MapContent mapContentPostAsMap = SecureRequestForAccount.postAsMap(str2, easyMapEasyPut, getPassportCookie(passportInfo), true, passportInfo.getSecurity());
        if (mapContentPostAsMap == null) {
            throw new IOException("failed to sendVerifyCode");
        }
        Object fromBody = mapContentPostAsMap.getFromBody("code");
        Object fromBody2 = mapContentPostAsMap.getFromBody("description");
        if (fromBody instanceof Integer) {
            int iIntValue = ((Integer) fromBody).intValue();
            if (iIntValue == 0) {
                return;
            }
            if (iIntValue == RESULT_CODE_INVALID_EMAIL_ADDRESS || iIntValue == 70008) {
                throw new InvalidBindAddressException("code: " + fromBody + " ;description: " + fromBody2);
            }
            if (iIntValue == 70022) {
                throw new SendVerifyCodeExceedLimitException("code: " + fromBody + " ;description: " + fromBody2);
            }
        }
        throw new InvalidResponseException("code: " + fromBody + "; description: " + fromBody2);
    }

    @Deprecated
    public static String updateBindedPhoneOrEmail(PassportInfo passportInfo, String str, BindingType bindingType, String str2, String str3, String str4) throws AccessDeniedException, InvalidVerifyCodeException, CipherException, IOException, InvalidResponseException, InvalidBindAddressException, UserRestrictedException, AuthenticationFailureException {
        return updateBindedPhoneOrEmail(passportInfo, getUrlForBindingPhoneOrEmail(bindingType), generateUpdateBindingParams(passportInfo, str, bindingType, str2, str3, str4));
    }

    /* JADX INFO: renamed from: com.xiaomi.accountsdk.account.XMPassport$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaomi$accountsdk$account$data$BindingType;
        static final /* synthetic */ int[] $SwitchMap$com$xiaomi$accountsdk$account$data$IdentityAuthReason;

        static {
            int[] iArr = new int[BindingType.values().length];
            $SwitchMap$com$xiaomi$accountsdk$account$data$BindingType = iArr;
            try {
                iArr[BindingType.ADD_SAFE_EMAIL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaomi$accountsdk$account$data$BindingType[BindingType.REPLACE_SAFE_EMAIL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaomi$accountsdk$account$data$BindingType[BindingType.ADD_PHONE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaomi$accountsdk$account$data$BindingType[BindingType.REPLACE_PHONE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            int[] iArr2 = new int[IdentityAuthReason.values().length];
            $SwitchMap$com$xiaomi$accountsdk$account$data$IdentityAuthReason = iArr2;
            try {
                iArr2[IdentityAuthReason.ADD_SAFE_EMAIL.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$xiaomi$accountsdk$account$data$IdentityAuthReason[IdentityAuthReason.REPLACE_SAFE_EMAIL.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$xiaomi$accountsdk$account$data$IdentityAuthReason[IdentityAuthReason.ADD_PHONE.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$xiaomi$accountsdk$account$data$IdentityAuthReason[IdentityAuthReason.REPLACE_PHONE.ordinal()] = 4;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$xiaomi$accountsdk$account$data$IdentityAuthReason[IdentityAuthReason.DELETE_PHONE.ordinal()] = 5;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$xiaomi$accountsdk$account$data$IdentityAuthReason[IdentityAuthReason.SEND_EMAIL_ACTIVATE_MESSAGE.ordinal()] = 6;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$xiaomi$accountsdk$account$data$IdentityAuthReason[IdentityAuthReason.SET_SECURITY_QUESTIONS.ordinal()] = 7;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$xiaomi$accountsdk$account$data$IdentityAuthReason[IdentityAuthReason.MODIFY_SAFE_PHONE.ordinal()] = 8;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$xiaomi$accountsdk$account$data$IdentityAuthReason[IdentityAuthReason.CHANGE_PASSWORD.ordinal()] = 9;
            } catch (NoSuchFieldError unused13) {
            }
        }
    }

    private static String getUrlForBindingPhoneOrEmail(BindingType bindingType) {
        if (bindingType == null) {
            throw new IllegalArgumentException("bindingType is null");
        }
        int i = AnonymousClass1.$SwitchMap$com$xiaomi$accountsdk$account$data$BindingType[bindingType.ordinal()];
        if (i == 1) {
            return URLs.URL_ADD_BIND_EMAIL;
        }
        if (i == 2) {
            return URLs.URL_REPLACE_BIND_EMAIL;
        }
        if (i == 3) {
            return URLs.URL_ADD_BIND_PHONE;
        }
        if (i == 4) {
            return URLs.URL_REPLACE_BIND_PHONE;
        }
        throw new IllegalArgumentException("invalid bindingType");
    }

    private static EasyMap<String, String> generateUpdateBindingParams(PassportInfo passportInfo, String str, BindingType bindingType, String str2, String str3, String str4) {
        if (bindingType == null) {
            throw new IllegalArgumentException("type is null");
        }
        EasyMap<String, String> easyMapEasyPut = new EasyMap().easyPut("userId", passportInfo.getUserId()).easyPut("sid", passportInfo.getServiceId()).easyPut("vkey", str2).easyPut("authST", str3);
        if (bindingType == BindingType.REPLACE_PHONE) {
            easyMapEasyPut.easyPut("oldAuthPhone", str4).easyPut("newAuthPhone", str);
            return easyMapEasyPut;
        }
        easyMapEasyPut.easyPut(RemoteDeviceInfo.KEY_ADDRESS, str);
        return easyMapEasyPut;
    }

    private static String updateBindedPhoneOrEmail(PassportInfo passportInfo, String str, EasyMap<String, String> easyMap) throws AccessDeniedException, InvalidVerifyCodeException, CipherException, IOException, InvalidResponseException, InvalidBindAddressException, UserRestrictedException, AuthenticationFailureException {
        if (passportInfo == null) {
            throw new IllegalArgumentException("passportInfo is null");
        }
        SimpleRequest.MapContent mapContentPostAsMap = SecureRequestForAccount.postAsMap(str, easyMap, getPassportCookie(passportInfo), true, passportInfo.getSecurity());
        if (mapContentPostAsMap == null) {
            throw new IOException("failed to updateBindedPhoneOrEmail");
        }
        Object fromBody = mapContentPostAsMap.getFromBody("code");
        Object fromBody2 = mapContentPostAsMap.getFromBody("description");
        if (fromBody instanceof Integer) {
            int iIntValue = ((Integer) fromBody).intValue();
            if (iIntValue == 0) {
                Object fromBody3 = mapContentPostAsMap.getFromBody("data");
                if (fromBody3 instanceof Map) {
                    Object obj = ((Map) fromBody3).get(RemoteDeviceInfo.KEY_ADDRESS);
                    if (obj == null) {
                        throw new InvalidResponseException("address is null");
                    }
                    return obj.toString();
                }
            } else {
                if (iIntValue == 20023) {
                    throw new UserRestrictedException();
                }
                if (iIntValue != RESULT_CODE_INVALID_EMAIL_ADDRESS && iIntValue != 70008) {
                    if (iIntValue == RESULT_CODE_ERROR_VERIFY_CODE) {
                        throw new InvalidVerifyCodeException("code: " + fromBody + "; description: " + fromBody2);
                    }
                }
            }
            throw new InvalidBindAddressException("code: " + fromBody + " ;description: " + fromBody2);
        }
        throw new InvalidResponseException("code: " + fromBody + "; description: " + fromBody2);
    }

    @Deprecated
    public static void deleteBindedPhone(PassportInfo passportInfo, String str, String str2) throws AccessDeniedException, CipherException, DeleteSafeAddressException, IOException, InvalidResponseException, InvalidBindAddressException, AuthenticationFailureException {
        if (passportInfo == null) {
            throw new IllegalArgumentException("passportInfo is null");
        }
        SimpleRequest.MapContent mapContentPostAsMap = SecureRequestForAccount.postAsMap(URLs.URL_DELETE_BIND_PHONE, new EasyMap().easyPut("userId", passportInfo.getUserId()).easyPut("sid", passportInfo.getServiceId()).easyPut(RemoteDeviceInfo.KEY_ADDRESS, str).easyPut("authST", str2), getPassportCookie(passportInfo), true, passportInfo.getSecurity());
        if (mapContentPostAsMap == null) {
            throw new IOException("failed to deleteBindedPhone");
        }
        Object fromBody = mapContentPostAsMap.getFromBody("code");
        Object fromBody2 = mapContentPostAsMap.getFromBody("description");
        if (fromBody instanceof Integer) {
            int iIntValue = ((Integer) fromBody).intValue();
            if (iIntValue == 0) {
                return;
            }
            if (iIntValue == RESULT_CODE_DELETE_SECURE_ADDRESS) {
                throw new DeleteSafeAddressException("code: " + fromBody + " ;description: " + fromBody2);
            }
            if (iIntValue == 70008) {
                throw new InvalidBindAddressException("code: " + fromBody + " ;description: " + fromBody2);
            }
        }
        throw new InvalidResponseException("code: " + fromBody + "; description: " + fromBody2);
    }

    @Deprecated
    public static String checkPhoneActivateStatus(PassportInfo passportInfo, String str, String str2, String str3) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, InvalidBindAddressException, AuthenticationFailureException {
        if (passportInfo == null) {
            throw new IllegalArgumentException("passportInfo is null");
        }
        SimpleRequest.MapContent mapContentPostAsMap = SecureRequestForAccount.postAsMap(URLs.URL_CHECK_PHONE_ACTIVATE_STATUS, new EasyMap().easyPut("userId", passportInfo.getUserId()).easyPut(RemoteDeviceInfo.KEY_ADDRESS, str).easyPut(SIMInfo.SIM_INFO_TYPE_SIM_ID, CloudCoder.hashDeviceInfo(str2)).easyPutOpt("userSpaceId", UserSpaceIdUtil.getNullableUserSpaceIdCookie()).easyPut("deviceId", CloudCoder.hashDeviceInfo(str3)), getPassportCookie(passportInfo), true, passportInfo.getSecurity());
        if (mapContentPostAsMap == null) {
            throw new IOException("failed to checkPhoneActivateStatus");
        }
        Object fromBody = mapContentPostAsMap.getFromBody("code");
        Object fromBody2 = mapContentPostAsMap.getFromBody("description");
        if (fromBody instanceof Integer) {
            int iIntValue = ((Integer) fromBody).intValue();
            if (iIntValue == 0) {
                Object fromBody3 = mapContentPostAsMap.getFromBody("data");
                if (!(fromBody3 instanceof Map)) {
                    return null;
                }
                Object obj = ((Map) fromBody3).get("key");
                if (obj == null) {
                    throw new InvalidResponseException("key is null");
                }
                return obj.toString();
            }
            if (iIntValue == 10017) {
                return null;
            }
            if (iIntValue == 70008) {
                throw new InvalidBindAddressException("code: " + fromBody + " ;description: " + fromBody2);
            }
        }
        throw new InvalidResponseException("code: " + fromBody + "; description: " + fromBody2);
    }

    public static void changePassword(PassportInfo passportInfo, String str, String str2, String str3, String str4) throws AccessDeniedException, InvalidParameterException, CipherException, InvalidCredentialException, NeedCaptchaException, IOException, InvalidResponseException, UserRestrictedException, AuthenticationFailureException {
        if (passportInfo == null) {
            throw new IllegalArgumentException("passportInfo is null");
        }
        String str5 = URLs.URL_CHANGE_PASSWORD;
        EasyMap easyMapEasyPut = new EasyMap().easyPut("userId", passportInfo.getUserId()).easyPut("oldPassword", str).easyPut("password", str2).easyPut("icode", str3);
        EasyMap<String, String> passportCookie = getPassportCookie(passportInfo);
        passportCookie.easyPut("ick", str4);
        SimpleRequest.MapContent mapContentPostAsMap = SecureRequestForAccount.postAsMap(str5, easyMapEasyPut, passportCookie, true, passportInfo.getSecurity());
        if (mapContentPostAsMap == null) {
            throw new IOException("failed to changePassword");
        }
        int iIntValue = ((Integer) mapContentPostAsMap.getFromBody("code")).intValue();
        String str6 = (String) mapContentPostAsMap.getFromBody("description");
        String str7 = "code: " + iIntValue + " ;description: " + str6;
        if (iIntValue != 0) {
            if (iIntValue != 10017) {
                if (iIntValue != 20023) {
                    if (iIntValue != 20031) {
                        if (iIntValue == RESULT_CODE_ERROR_OLD_PWD) {
                            throw new InvalidCredentialException(Integer.valueOf(iIntValue).intValue(), str7, true);
                        }
                        if (iIntValue != 70003) {
                            if (iIntValue != 87001) {
                                throw new InvalidResponseException(iIntValue, str6);
                            }
                        }
                    }
                    throw new NeedCaptchaException(iIntValue, str6, (String) mapContentPostAsMap.getFromBody(com.market.sdk.utils.Constants.JSON_FILTER_INFO));
                }
                throw new UserRestrictedException();
            }
            throw new InvalidParameterException(str7);
        }
    }

    private static EasyMap<String, String> getPassportCookie(PassportInfo passportInfo) {
        if (passportInfo == null) {
            throw new IllegalArgumentException("passportInfo is null");
        }
        EasyMap<String, String> easyMapEasyPut = new EasyMap().easyPut("serviceToken", passportInfo.getServiceToken());
        if (TextUtils.isEmpty(passportInfo.getEncryptedUserId())) {
            easyMapEasyPut.easyPut("userId", passportInfo.getUserId());
        } else {
            easyMapEasyPut.easyPut("cUserId", passportInfo.getEncryptedUserId());
        }
        easyMapEasyPut.easyPut("uLocale", Locale.getDefault().toString());
        return easyMapEasyPut;
    }

    public static ExtendedAuthToken refreshPassToken(String str, String str2, String str3) throws AccessDeniedException, IOException, InvalidResponseException, AuthenticationFailureException {
        String str4 = URLs.URL_REFRESH_PASS_TOKEN;
        HashMap map = new HashMap();
        map.put("reason", str3);
        EasyMap easyMap = new EasyMap();
        addDeviceIdInCookies(easyMap, null);
        easyMap.put("userId", str);
        easyMap.put("passToken", str2);
        easyMap.put("uLocale", Locale.getDefault().toString());
        try {
            easyMap.put("fid", FidManager.getInstance().getFid(XMPassportSettings.getApplicationContext()));
        } catch (FidSigningUtil.FidSignException unused) {
            AccountLogger.log(TAG, "refreshPassToken: can not get dif");
        }
        SimpleRequest.StringContent asString = SimpleRequestForAccount.getAsString(str4, map, easyMap, true);
        try {
            String strRemoveSafePrefixAndGetRealBody = removeSafePrefixAndGetRealBody(asString);
            JSONObject jSONObject = new JSONObject(strRemoveSafePrefixAndGetRealBody);
            int i = jSONObject.getInt("code");
            if (i == 0) {
                return ExtendedAuthToken.build(asString.getHeader("passToken"), jSONObject.getJSONObject("data").getString("psecurity"));
            }
            if (i == 70016) {
                throw new AuthenticationFailureException(strRemoveSafePrefixAndGetRealBody);
            }
            throw new InvalidResponseException("refresh token unexpected code: " + i);
        } catch (JSONException e) {
            throw new InvalidResponseException("process result failed", e);
        }
    }

    public static HashMap<String, DeviceModelInfo> getDeviceModelInfos(ArrayList<String> arrayList) throws AccessDeniedException, IOException, InvalidResponseException, AuthenticationFailureException {
        return DeviceInfoHelper.getDeviceModelInfos(arrayList);
    }

    public static void setUserRegion(PassportInfo passportInfo, String str, String str2) throws AccessDeniedException, InvalidParameterException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        if (passportInfo == null) {
            throw new IllegalArgumentException("null passport info");
        }
        processPostSecureRequestForSetUserInfo(URLs.URL_SET_USER_REGION, new EasyMap().easyPut("userId", passportInfo.getUserId()).easyPut("region", str).easyPut("sid", str2).easyPut("transId", UUID.randomUUID().toString().substring(0, 15)), getPassportCookie(passportInfo), true, passportInfo.getSecurity());
    }

    public static void setUserLocation(PassportInfo passportInfo, String str, String str2, String str3) throws AccessDeniedException, InvalidParameterException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        if (passportInfo == null) {
            throw new IllegalArgumentException("null passport info");
        }
        processPostSecureRequestForSetUserInfo(URLs.URL_SET_USER_LOCATION, new EasyMap().easyPut("userId", passportInfo.getUserId()).easyPut("region", str).easyPut("location", str2).easyPut("sid", str3).easyPut("transId", UUID.randomUUID().toString().substring(0, 15)), getPassportCookie(passportInfo), true, passportInfo.getSecurity());
    }

    public static void setUserEducation(PassportInfo passportInfo, XiaomiUserCoreInfo.Education education, String str) throws AccessDeniedException, InvalidParameterException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        if (passportInfo == null || education == null) {
            throw new IllegalArgumentException("invalid params");
        }
        processPostSecureRequestForSetUserInfo(URLs.URL_SET_USER_EDUCATION, new EasyMap().easyPut("userId", passportInfo.getUserId()).easyPut("education", education.level).easyPut("sid", str).easyPut("transId", UUID.randomUUID().toString().substring(0, 15)), getPassportCookie(passportInfo), true, passportInfo.getSecurity());
    }

    public static void setUserIncome(PassportInfo passportInfo, XiaomiUserCoreInfo.Income income, String str) throws AccessDeniedException, InvalidParameterException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        if (passportInfo == null || income == null) {
            throw new IllegalArgumentException("invalid params");
        }
        processPostSecureRequestForSetUserInfo(URLs.URL_SET_USER_INCOME, new EasyMap().easyPut("userId", passportInfo.getUserId()).easyPut("income", income.level).easyPut("sid", str).easyPut("transId", UUID.randomUUID().toString().substring(0, 15)), getPassportCookie(passportInfo), true, passportInfo.getSecurity());
    }

    private static void processPostSecureRequestForSetUserInfo(String str, Map<String, String> map, Map<String, String> map2, boolean z, String str2) throws AccessDeniedException, InvalidParameterException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        SimpleRequest.MapContent mapContentPostAsMap = SecureRequestForAccount.postAsMap(str, map, map2, z, str2);
        if (mapContentPostAsMap == null) {
            throw new InvalidResponseException("invalid response content");
        }
        Object fromBody = mapContentPostAsMap.getFromBody("code");
        Object fromBody2 = mapContentPostAsMap.getFromBody("description");
        if (fromBody instanceof Integer) {
            int iIntValue = ((Integer) fromBody).intValue();
            if (iIntValue == 0) {
                return;
            }
            if (iIntValue == 10016 || iIntValue == 10017) {
                throw new InvalidParameterException(fromBody2 != null ? fromBody2.toString() : "invalid params");
            }
        }
        throw new InvalidResponseException("code: " + fromBody + "description: " + fromBody2);
    }

    public static XiaomiUserCoreInfo getXiaomiUserCoreInfo(PassportInfo passportInfo, String str, List<XiaomiUserCoreInfo.Flag> list) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        int i;
        if (passportInfo == null) {
            throw new IllegalArgumentException("passportInfo is null");
        }
        if (list != null) {
            Iterator<XiaomiUserCoreInfo.Flag> it = list.iterator();
            i = 0;
            while (it.hasNext()) {
                i |= it.next().value;
            }
        } else {
            i = 0;
        }
        String strSubstring = UUID.randomUUID().toString().substring(0, 15);
        String str2 = URLs.URL_GET_USER_CORE_INFO;
        EasyMap easyMapEasyPut = new EasyMap().easyPut("userId", passportInfo.getUserId()).easyPut("sid", str).easyPut("transId", strSubstring);
        if (i != 0) {
            easyMapEasyPut.easyPut("flags", String.valueOf(i));
        }
        return processCoreInfoContent(passportInfo.getUserId(), SecureRequestForAccount.getAsMap(str2, easyMapEasyPut, getPassportCookie(passportInfo), true, passportInfo.getSecurity()));
    }

    public static JSONObject getXiaomiUserStatus(PassportInfo passportInfo, String str, String str2) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        if (passportInfo == null) {
            throw new IllegalArgumentException("passportInfo is null");
        }
        if (TextUtils.isEmpty(str2)) {
            throw new IllegalArgumentException("securityFlag is empty");
        }
        EasyMap easyMapEasyPut = new EasyMap().easyPut("userId", passportInfo.getUserId()).easyPut("sid", str).easyPut("transId", UUID.randomUUID().toString().substring(0, 15));
        easyMapEasyPut.easyPut("securityFlag", str2);
        return processJsonContent(SimpleRequestForAccount.getAsString("https://account.xiaomi.com/pass2/user/status", easyMapEasyPut, getPassportCookie(passportInfo), true)).getJsonContent();
    }

    public static boolean modifyUserTwoFactorAuthType(PassportInfo passportInfo, String str, String str2) throws AccessDeniedException, IOException, InvalidResponseException, AuthenticationFailureException {
        if (passportInfo == null) {
            throw new IllegalArgumentException("passportInfo is null");
        }
        if (TextUtils.isEmpty(str2)) {
            throw new IllegalArgumentException("authMethod is empty");
        }
        int resultCode = processJsonContent(SimpleRequestForAccount.postAsString("https://account.xiaomi.com/pass2/modify/twoFactorAuthType/status", new EasyMap().easyPut("userId", passportInfo.getUserId()).easyPut("type", str2).easyPut("transId", UUID.randomUUID().toString().substring(0, 15)), getPassportCookie(passportInfo), true)).getResultCode();
        AccountLogger.log(TAG, "modifyUserTwoFactorAuthType: code=" + resultCode);
        return resultCode == 0;
    }

    private static class GeneralJsonResult {
        private final JSONObject jsonContent;
        private final int resultCode;

        GeneralJsonResult(JSONObject jSONObject) {
            this.jsonContent = jSONObject;
            this.resultCode = jSONObject.optInt("code", -1);
        }

        public JSONObject getJsonContent() {
            return this.jsonContent;
        }

        public int getResultCode() {
            return this.resultCode;
        }
    }

    private static GeneralJsonResult processJsonContent(SimpleRequest.StringContent stringContent) throws IOException, InvalidResponseException {
        try {
            GeneralJsonResult generalJsonResult = new GeneralJsonResult(new JSONObject(removeSafePrefixAndGetRealBody(stringContent)));
            if (generalJsonResult.getResultCode() == 0) {
                return generalJsonResult;
            }
            throw new InvalidResponseException(stringContent.toString());
        } catch (JSONException e) {
            AccountLogger.log(TAG, "JSON ERROR", e);
            throw new InvalidResponseException(e.getMessage());
        }
    }

    private static XiaomiUserCoreInfo processCoreInfoContent(String str, SimpleRequest.MapContent mapContent) throws InvalidResponseException {
        if (mapContent == null) {
            throw new InvalidResponseException("result content is null");
        }
        Object fromBody = mapContent.getFromBody("code");
        if (INT_0.equals(fromBody)) {
            XiaomiUserCoreInfo.Builder builder = new XiaomiUserCoreInfo.Builder(str);
            Object fromBody2 = mapContent.getFromBody("data");
            if (fromBody2 instanceof Map) {
                Map map = (Map) fromBody2;
                Object obj = map.get("userName");
                if (obj instanceof String) {
                    builder.setUserName((String) obj);
                }
                Object obj2 = map.get(CallMethod.RESULT_ICON);
                if (obj2 instanceof String) {
                    String str2 = (String) obj2;
                    int iLastIndexOf = str2.lastIndexOf(".");
                    if (str2.length() > 0 && iLastIndexOf > 0) {
                        builder.setAvatarAddress(str2.substring(0, iLastIndexOf) + ICON_SIZE_SUFFIX_320 + str2.substring(str2.lastIndexOf(".")));
                    }
                }
                Object obj3 = map.get("sns");
                if (obj3 instanceof List) {
                    builder.setSnsInfoList(XiaomiUserCoreInfo.SnsInfo.parseSnsList((List) obj3));
                }
                Object obj4 = map.get("userAddresses");
                if (obj4 instanceof List) {
                    ArrayList<String> arrayList = new ArrayList<>();
                    for (Object obj5 : (List) obj4) {
                        if (obj5 instanceof Map) {
                            Map map2 = (Map) obj5;
                            Object obj6 = map2.get("addressType");
                            Object obj7 = map2.get(RemoteDeviceInfo.KEY_ADDRESS);
                            Object obj8 = map2.get("flags");
                            if ((obj6 instanceof Integer) && (obj7 instanceof String)) {
                                Integer num = (Integer) obj6;
                                String strSubstring = (String) obj7;
                                Integer num2 = INT_0;
                                if (obj8 instanceof Integer) {
                                    num2 = (Integer) obj8;
                                }
                                boolean z = (num2.intValue() & 2) != 0;
                                int iIntValue = num.intValue();
                                if (iIntValue != 1) {
                                    if (iIntValue != 2) {
                                        if (iIntValue == 9) {
                                            int iLastIndexOf2 = strSubstring.lastIndexOf("@ALIAS");
                                            if (iLastIndexOf2 > 0) {
                                                strSubstring = strSubstring.substring(0, iLastIndexOf2);
                                            }
                                            builder.setNickName(strSubstring);
                                        }
                                    } else if (z) {
                                        builder.setEmailAddress(strSubstring);
                                    }
                                } else if (z) {
                                    builder.setSafePhone(strSubstring);
                                    arrayList.add(0, strSubstring);
                                } else if (num2.intValue() == 8) {
                                    arrayList.add(strSubstring);
                                }
                            }
                        }
                    }
                    builder.setPhoneList(arrayList);
                }
                Object obj9 = map.get(ba.c);
                if ((obj9 instanceof String) && !TextUtils.isEmpty((String) obj9)) {
                    Calendar calendar = Calendar.getInstance();
                    try {
                        calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse((String) obj9));
                        builder.setBirthday(calendar);
                    } catch (ParseException e) {
                        AccountLogger.log(TAG, "getXiaomiUserProfile", e);
                    }
                }
                Object obj10 = map.get(ba.b);
                if (obj10 instanceof String) {
                    String str3 = (String) obj10;
                    if (!TextUtils.isEmpty(str3)) {
                        if ("m".equals(str3)) {
                            builder.setGender(Gender.MALE);
                        } else if ("f".equals(str3)) {
                            builder.setGender(Gender.FEMALE);
                        }
                    }
                }
                Object obj11 = map.get("isSetSafeQuestions");
                if (obj11 != null && (obj11 instanceof Boolean)) {
                    builder.setIsSetSafeQuestions(((Boolean) obj11).booleanValue());
                }
                Object obj12 = map.get("locale");
                if (obj12 instanceof String) {
                    String str4 = (String) obj12;
                    if (!TextUtils.isEmpty(str4)) {
                        builder.setLocale(str4);
                    }
                }
                Object obj13 = map.get("region");
                if (obj13 instanceof String) {
                    String str5 = (String) obj13;
                    if (!TextUtils.isEmpty(str5)) {
                        builder.setRegion(str5);
                    }
                }
                Object obj14 = map.get("location");
                if (obj14 != null && (obj14 instanceof String)) {
                    builder.setLocationZipCode((String) obj14);
                }
                Object obj15 = map.get("education");
                if (obj15 instanceof String) {
                    String str6 = (String) obj15;
                    if (!TextUtils.isEmpty(str6)) {
                        XiaomiUserCoreInfo.Education educationTypeByName = XiaomiUserCoreInfo.Education.getEducationTypeByName(str6);
                        if (educationTypeByName == null) {
                            throw new InvalidResponseException("invalid education value: " + obj15);
                        }
                        builder.setEducation(educationTypeByName);
                    }
                }
                Object obj16 = map.get("income");
                if (obj16 instanceof String) {
                    String str7 = (String) obj16;
                    if (!TextUtils.isEmpty(str7)) {
                        XiaomiUserCoreInfo.Income incomeTypeByName = XiaomiUserCoreInfo.Income.getIncomeTypeByName(str7);
                        if (incomeTypeByName == null) {
                            throw new InvalidResponseException("invalid income value: " + obj16);
                        }
                        builder.setIncome(incomeTypeByName);
                    }
                }
                Object obj17 = map.get("child");
                if (obj17 instanceof Boolean) {
                    builder.setChild(((Boolean) obj17).booleanValue());
                }
                Object obj18 = map.get("familyMemberCount");
                if (obj18 instanceof Integer) {
                    builder.setFamilyMemberCount(String.valueOf(obj18));
                }
            }
            return builder.build();
        }
        throw new InvalidResponseException("code: " + fromBody + "; description: " + mapContent.getFromBody("description"));
    }

    public static String generateRandomPasswordFromServer() throws AccessDeniedException, IOException, InvalidResponseException, AuthenticationFailureException {
        SimpleRequest.StringContent asString = SimpleRequestForAccount.getAsString(URLs.URL_GENERATE_RANDOM_PASSWORD, null, null, true);
        if (asString == null) {
            throw new InvalidResponseException("result content is null");
        }
        try {
            JSONObject jSONObject = new JSONObject(removeSafePrefixAndGetRealBody(asString));
            if (jSONObject.getInt("code") == 0) {
                return new JSONObject(jSONObject.getString("data")).optString("pwd");
            }
            throw new InvalidResponseException(asString.toString());
        } catch (JSONException e) {
            AccountLogger.log(TAG, "JSON ERROR", e);
            throw new InvalidResponseException(e.getMessage());
        }
    }

    public static int sendPhoneLoginTicket(SendPhoneTicketParams sendPhoneTicketParams) throws AccessDeniedException, InvalidPhoneNumException, ReachLimitException, NeedCaptchaException, IOException, InvalidResponseException, TokenExpiredException, AuthenticationFailureException {
        if (sendPhoneTicketParams == null) {
            throw new IllegalArgumentException("send phone ticket params is null");
        }
        String str = URLs.URL_ACCOUNT_BASE + "/sendServiceLoginTicket";
        EasyMap easyMapEasyPut = new EasyMap().easyPutOpt("user", EUIParamEncryptUtil.encryptParam(sendPhoneTicketParams.phone)).easyPutOpt("userHash", sendPhoneTicketParams.phoneHash).easyPutOpt("sid", sendPhoneTicketParams.serviceId).easyPutOpt("captCode", sendPhoneTicketParams.captCode).easyPutOpt("type", sendPhoneTicketParams.ticketType).easyPut("_json", "true");
        fillCommonParams(easyMapEasyPut);
        EasyMap easyMapEasyPutOpt = new EasyMap().easyPutOpt("activatorToken", sendPhoneTicketParams.activatorToken).easyPutOpt("ick", sendPhoneTicketParams.captIck).easyPutOpt("vToken", sendPhoneTicketParams.token).easyPutOpt("vAction", sendPhoneTicketParams.action);
        addDeviceIdInCookies(easyMapEasyPutOpt, sendPhoneTicketParams.deviceId);
        SimpleRequest.StringContent stringContentPostAsString = SimpleRequestForAccount.postAsString(str, (Map<String, String>) easyMapEasyPut, (Map<String, String>) easyMapEasyPutOpt, (Map<String, String>) new EasyMap().easyPutOpt("vToken", sendPhoneTicketParams.verifyToken).easyPut(EUI_KEY, EUIParamEncryptUtil.generateEUI("user", sendPhoneTicketParams.phone)), (Map<String, String>) null, true, (Integer) null);
        if (stringContentPostAsString == null) {
            throw new InvalidResponseException("result content is null");
        }
        try {
            JSONObject jSONObject = new JSONObject(removeSafePrefixAndGetRealBody(stringContentPostAsString));
            int i = jSONObject.getInt("code");
            String strOptString = jSONObject.optString("description");
            PassThroughErrorInfo passThroughErrorInfo = new PassThroughErrorInfo(jSONObject);
            String str2 = "code: " + i + ", desc: " + strOptString;
            AccountLogger.log(TAG, "sendPhoneLoginTicket: " + str2);
            if (i == 0) {
                return jSONObject.getJSONObject("data").optInt("vCodeLen");
            }
            if (i == 21317) {
                throw new TokenExpiredException(str2);
            }
            if (i == 70008) {
                throw new InvalidPhoneNumException(strOptString);
            }
            if (i == 70022) {
                throw new ReachLimitException(str2);
            }
            if (i == 81004) {
                AccountLogger.log(TAG, "code:" + i + ",Invalid public key");
                EUIParamEncryptUtil.invalidPublicKey();
            } else if (i == 87001) {
                throw new NeedCaptchaException(i, strOptString, jSONObject.getString("captchaUrl"), jSONObject.optString("type"));
            }
            throw new InvalidResponseException(i, strOptString, passThroughErrorInfo);
        } catch (JSONException unused) {
            throw new InvalidResponseException("result not json");
        }
    }

    public static String getRsaPublicKey() throws AccessDeniedException, IOException, InvalidResponseException, AuthenticationFailureException {
        try {
            JSONObject jSONObject = new JSONObject(removeSafePrefixAndGetRealBody(SimpleRequestForAccount.getAsString("https://account.xiaomi.com/pass2/config", new EasyMap().easyPut("key", "eui"), null, null, true)));
            if (jSONObject.has("euiPublicKey")) {
                return jSONObject.getString("euiPublicKey");
            }
            AccountLogger.log(TAG, "No euiPublicKey found in the response");
            throw new InvalidResponseException("No euiPublicKey found in the response");
        } catch (JSONException e) {
            AccountLogger.log(TAG, "json error", e);
            throw new InvalidResponseException("json error");
        }
    }

    public static RegisterUserInfo queryPhoneUserInfo(QueryPhoneInfoParams queryPhoneInfoParams) throws AccessDeniedException, InvalidPhoneNumException, InvalidVerifyCodeException, IOException, InvalidResponseException, AuthenticationFailureException {
        if (queryPhoneInfoParams == null) {
            throw new IllegalArgumentException("invalid params");
        }
        String str = URLs.URL_ACCOUNT_BASE + "/phoneInfo";
        String str2 = queryPhoneInfoParams.phone;
        String str3 = queryPhoneInfoParams.ticket;
        EasyMap easyMapEasyPut = new EasyMap().easyPutOpt("user", EUIParamEncryptUtil.encryptParam(str2)).easyPutOpt("ticket", EUIParamEncryptUtil.encryptParam(str3)).easyPutOpt("userHash", queryPhoneInfoParams.phoneHash).easyPutOpt("sid", queryPhoneInfoParams.serviceId).easyPutOpt("type", queryPhoneInfoParams.ticketType).easyPut("_json", "true");
        fillCommonParams(easyMapEasyPut);
        EasyMap easyMapEasyPutOpt = new EasyMap().easyPutOpt("activatorToken", queryPhoneInfoParams.activatorToken);
        addDeviceIdInCookies(easyMapEasyPutOpt, queryPhoneInfoParams.deviceId);
        SimpleRequest.StringContent stringContentPostAsString = SimpleRequestForAccount.postAsString(str, easyMapEasyPut, easyMapEasyPutOpt, new EasyMap().easyPutOpt(EUI_KEY, EUIParamEncryptUtil.generateEUI("user", str2, "ticket", str3)), true);
        if (stringContentPostAsString == null) {
            throw new InvalidResponseException("result content is null");
        }
        try {
            JSONObject jSONObject = new JSONObject(removeSafePrefixAndGetRealBody(stringContentPostAsString));
            int i = jSONObject.getInt("code");
            String str4 = "code: " + i + ", desc: " + jSONObject.optString("description");
            AccountLogger.log(TAG, "queryPhoneUserInfo: " + str4);
            if (i == 0) {
                String header = stringContentPostAsString.getHeader("ticketToken");
                JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                String strOptString = jSONObject2.optString("tmpPhoneToken");
                if (TextUtils.isEmpty(strOptString)) {
                    AccountLogger.log(TAG, "tmpPhoneToken is null");
                }
                return new RegisterUserInfo.Builder(jSONObject2.getInt("status")).userId(jSONObject2.getString("id")).userName(jSONObject2.optString("nickname")).avatarAddress(jSONObject2.optString("portrait")).phone(jSONObject2.optString("phone")).tmpPhoneToken(strOptString).ticketToken(header).maskedUserId(jSONObject2.optString("maskedUserId")).hasPwd(jSONObject2.optInt("pwd") == 1).bindTime(jSONObject2.optLong("bindTime", 0L)).needGetActiveTime(jSONObject2.optBoolean("needGetActiveTime", false)).needToast(jSONObject2.optBoolean("needToast", false)).registerPwd(jSONObject2.optInt("registerPwd") == 1).build();
            }
            if (i == 10031) {
                throw new InvalidVerifyCodeException(str4);
            }
            if (i == 70008) {
                throw new InvalidPhoneNumException(str4);
            }
            if (i == 81004) {
                AccountLogger.log(TAG, "code:" + i + ",Invalid public key");
                EUIParamEncryptUtil.invalidPublicKey();
            }
            throw new InvalidResponseException(i, str4, new PassThroughErrorInfo(jSONObject));
        } catch (JSONException unused) {
            throw new InvalidResponseException("result not json");
        }
    }

    public static AccountInfo loginByPhone(PhoneTicketLoginParams phoneTicketLoginParams) throws NeedNotificationException, InvalidUserNameException, AccessDeniedException, InvalidVerifyCodeException, InvalidPhoneNumException, InvalidTzSignException, IOException, InvalidResponseException, AuthenticationFailureException {
        if (phoneTicketLoginParams == null) {
            throw new IllegalArgumentException("null phone ticket login params");
        }
        String str = URLs.URL_ACCOUNT_BASE + "/serviceLoginTicketAuth";
        MetaLoginData phoneLoginMetaLoginData = getPhoneLoginMetaLoginData(phoneTicketLoginParams.phone, phoneTicketLoginParams.serviceId);
        String str2 = TextUtils.isEmpty(phoneTicketLoginParams.serviceId) ? "passport" : phoneTicketLoginParams.serviceId;
        EasyMap easyMapEasyPut = new EasyMap().easyPutOpt("user", EUIParamEncryptUtil.encryptParam(phoneTicketLoginParams.phone)).easyPutOpt("userHash", phoneTicketLoginParams.phoneHash).easyPutOpt("ticket", phoneTicketLoginParams.ticket).easyPut("sid", str2).easyPut("_json", "true").easyPut("_sign", phoneLoginMetaLoginData.sign).easyPut("qs", phoneLoginMetaLoginData.qs).easyPut(CallMethod.ARG_CALLBACK, phoneLoginMetaLoginData.callback);
        fillCommonParams(easyMapEasyPut);
        EasyMap easyMapEasyPutOpt = new EasyMap().easyPutOpt("activatorToken", phoneTicketLoginParams.activatorToken).easyPutOpt("ticketToken", phoneTicketLoginParams.ticketToken);
        String strAddDeviceIdInCookies = addDeviceIdInCookies(easyMapEasyPutOpt, phoneTicketLoginParams.deviceId);
        EasyMap easyMapEasyPut2 = new EasyMap().easyPut(EUI_KEY, EUIParamEncryptUtil.generateEUI("user", phoneTicketLoginParams.phone));
        Application applicationContext = XMPassportSettings.getApplicationContext();
        if (applicationContext != null && !TextUtils.isEmpty(strAddDeviceIdInCookies)) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(strAddDeviceIdInCookies);
            if (!TextUtils.isEmpty(phoneTicketLoginParams.phoneHash)) {
                arrayList.add(phoneTicketLoginParams.phoneHash);
            } else if (!TextUtils.isEmpty(phoneTicketLoginParams.phone)) {
                arrayList.add(phoneTicketLoginParams.phone);
            }
            String strSyncSignStringArray = SecurityDeviceSignManager.syncSignStringArray(applicationContext, (String[]) arrayList.toArray(new String[0]), null, 10000L);
            if (strSyncSignStringArray != null) {
                easyMapEasyPut.easyPut("tzSign", strSyncSignStringArray);
            }
        }
        SimpleRequest.StringContent stringContentPostAsString = SimpleRequestForAccount.postAsString(str, easyMapEasyPut, easyMapEasyPutOpt, easyMapEasyPut2, true);
        if (stringContentPostAsString == null) {
            throw new InvalidResponseException("result content is null");
        }
        return processPhoneLoginContent(stringContentPostAsString, str2, phoneTicketLoginParams.returnStsUrl);
    }

    private static MetaLoginData getPhoneLoginMetaLoginData(String str, String str2) throws NeedNotificationException, InvalidUserNameException, AccessDeniedException, IOException, InvalidResponseException, AuthenticationFailureException {
        try {
            loginByPassToken(new PassTokenLoginParams.Builder().userId(str).passToken(null).serviceId(str2).isGetPhoneTicketLoginMetaData(true).build());
            throw new InvalidResponseException("Unexpected login success with empty pass token");
        } catch (InvalidCredentialException e) {
            return e.getMetaLoginData();
        } catch (PackageNameDeniedException unused) {
            throw new InvalidResponseException("PackageNameDeniedException is unexpected with empty userId or passToken");
        }
    }

    private static AccountInfo processPhoneLoginContent(SimpleRequest.StringContent stringContent, String str, boolean z) throws NeedNotificationException, InvalidVerifyCodeException, InvalidPhoneNumException, AccessDeniedException, InvalidTzSignException, IOException, InvalidResponseException, AuthenticationFailureException {
        try {
            JSONObject jSONObject = new JSONObject(removeSafePrefixAndGetRealBody(stringContent));
            int i = jSONObject.getInt("code");
            String str2 = "code: " + i + ", desc: " + jSONObject.optString("desc");
            AccountLogger.log(TAG, "processPhoneLoginContent: " + str2);
            if (i == 0) {
                return handlePhoneLoginSuccess(stringContent, jSONObject, str, z);
            }
            if (i == 70008) {
                throw new InvalidPhoneNumException(str2);
            }
            if (i == RESULT_CODE_ERROR_VERIFY_CODE) {
                throw new InvalidVerifyCodeException(str2);
            }
            if (i == RESULT_CODE_ERROR_TZ_SIGN) {
                throw new InvalidTzSignException(str2);
            }
            if (i == 81004) {
                AccountLogger.log(TAG, "code:" + i + ",Invalid public key");
                EUIParamEncryptUtil.invalidPublicKey();
            }
            throw new InvalidResponseException(i, str2, new PassThroughErrorInfo(jSONObject));
        } catch (JSONException unused) {
            throw new InvalidResponseException("result not json");
        }
    }

    private static AccountInfo handlePhoneLoginSuccess(SimpleRequest.StringContent stringContent, JSONObject jSONObject, String str, boolean z) throws NeedNotificationException, JSONException, AccessDeniedException, IOException, InvalidResponseException, AuthenticationFailureException {
        String header = stringContent.getHeader("userId");
        String header2 = stringContent.getHeader("passToken");
        if (jSONObject.optInt("securityStatus", 0) != 0) {
            String string = jSONObject.getString("notificationUrl");
            if (string == null) {
                throw new InvalidResponseException("notificationUrl is null");
            }
            if (!string.startsWith("http")) {
                string = ACCOUNT_DOMAIN + string;
            }
            throw new NeedNotificationException(header, string, stringContent);
        }
        if (TextUtils.isEmpty(header)) {
            throw new InvalidResponseException("no user Id in login response");
        }
        if (TextUtils.isEmpty(header2)) {
            throw new InvalidResponseException("no passToken in login response");
        }
        return parseLoginResult(header, stringContent, str, null, false, z);
    }

    public static String sendSetPasswordTicket(PassportInfo passportInfo, String str) throws AccessDeniedException, InvalidPhoneNumException, ReachLimitException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        if (passportInfo == null) {
            throw new IllegalArgumentException("passport info should not be null");
        }
        String str2 = URLs.URL_ACCOUNT_SAFE_API_BASE + "/user/sendSetPasswordTicket";
        EasyMap easyMapEasyPut = new EasyMap().easyPut("userId", passportInfo.getUserId()).easyPutOpt("sid", str).easyPut("transId", UUID.randomUUID().toString().substring(0, 15));
        fillCommonParams(easyMapEasyPut);
        EasyMap<String, String> passportCookie = getPassportCookie(passportInfo);
        addDeviceIdInCookies(passportCookie, null);
        SimpleRequest.StringContent stringContentPostAsString = SecureRequestForAccount.postAsString(str2, easyMapEasyPut, passportCookie, true, passportInfo.getSecurity());
        if (stringContentPostAsString == null) {
            throw new InvalidResponseException("result content is null");
        }
        try {
            JSONObject jSONObject = new JSONObject(removeSafePrefixAndGetRealBody(stringContentPostAsString));
            int i = jSONObject.getInt("code");
            String str3 = "code: " + i + ", desc: " + jSONObject.optString("description");
            AccountLogger.log(TAG, "requestSetPassword: " + str3);
            if (i == 0) {
                return jSONObject.getJSONObject("data").getString("maskedPhone");
            }
            if (i == 70009) {
                throw new InvalidPhoneNumException(str3);
            }
            if (i == 70022) {
                throw new ReachLimitException(str3);
            }
            throw new InvalidResponseException(i, str3, new PassThroughErrorInfo(jSONObject));
        } catch (JSONException unused) {
            throw new InvalidResponseException("result not json");
        }
    }

    public static String setPassword(SetPasswordParams setPasswordParams) throws AccessDeniedException, InvalidVerifyCodeException, InvalidParameterException, CipherException, InvalidCredentialException, IOException, InvalidResponseException, UserRestrictedException, AuthenticationFailureException {
        if (setPasswordParams == null) {
            throw new IllegalArgumentException("set password params should not be null");
        }
        PassportInfo passportInfo = setPasswordParams.passportApiInfo;
        if (passportInfo == null) {
            throw new IllegalArgumentException("passport info should not be null");
        }
        String str = URLs.URL_ACCOUNT_API_V2_BASE + "/safe/user/setPassword";
        EasyMap easyMapEasyPut = new EasyMap().easyPut("userId", setPasswordParams.userId).easyPut("pwd", setPasswordParams.pwd).easyPut("passToken", setPasswordParams.passToken).easyPutOpt("sid", setPasswordParams.serviceId).easyPutOpt("ticket", setPasswordParams.ticket).easyPut("transId", UUID.randomUUID().toString().substring(0, 15));
        MiuiActivatorInfo miuiActivatorInfo = setPasswordParams.miuiActivatorInfo;
        if (miuiActivatorInfo != null) {
            easyMapEasyPut.easyPutOpt("phone", miuiActivatorInfo.phone).easyPutOpt(SIMInfo.SIM_INFO_TYPE_SIM_ID, miuiActivatorInfo.simId).easyPutOpt("vKey2", miuiActivatorInfo.vKey2).easyPutOpt(Constants.NONCE, miuiActivatorInfo.vKey2Nonce);
        }
        fillCommonParams(easyMapEasyPut);
        EasyMap<String, String> passportCookie = getPassportCookie(passportInfo);
        addDeviceIdInCookies(passportCookie, setPasswordParams.deviceId);
        SimpleRequest.StringContent stringContentPostAsString = SecureRequestForAccount.postAsString(str, easyMapEasyPut, passportCookie, true, passportInfo.getSecurity());
        if (stringContentPostAsString == null) {
            throw new InvalidResponseException("result content is null");
        }
        try {
            JSONObject jSONObject = new JSONObject(removeSafePrefixAndGetRealBody(stringContentPostAsString));
            int i = jSONObject.getInt("code");
            String str2 = "code: " + i + ", desc: " + jSONObject.optString("description");
            AccountLogger.log(TAG, "requestSetPassword: " + str2);
            if (i == 0) {
                return jSONObject.getJSONObject("data").getString("passToken");
            }
            if (i == 10031) {
                throw new UserRestrictedException(str2);
            }
            if (i == 21317) {
                throw new InvalidCredentialException(i, str2, false);
            }
            if (i == 70003) {
                throw new InvalidParameterException(str2);
            }
            if (i == RESULT_CODE_EMPTY_VERIFY_CODE || i == RESULT_CODE_ERROR_VERIFY_CODE) {
                throw new InvalidVerifyCodeException(str2);
            }
            throw new InvalidResponseException(i, str2, new PassThroughErrorInfo(jSONObject));
        } catch (JSONException unused) {
            throw new InvalidResponseException("result not json");
        }
    }

    public static QRLoginUrlInfo getQRLoginUrl(String str, String str2, String str3, String str4) throws AccessDeniedException, IOException, InvalidResponseException, AuthenticationFailureException {
        if (TextUtils.isEmpty(str)) {
            str = "passport";
        }
        String str5 = URLs.ACCOUNT_DOMAIN + "/longPolling/loginUrl";
        EasyMap easyMap = new EasyMap();
        easyMap.easyPut("sid", str).easyPutOpt(CallMethod.ARG_CALLBACK, str2).easyPutOpt("csid", str3).easyPutOpt("ccallback", str4);
        SimpleRequest.StringContent asString = SimpleRequestForAccount.getAsString(str5, easyMap, null, true);
        if (asString == null) {
            throw new InvalidResponseException("qr login url content is null");
        }
        String strRemoveSafePrefixAndGetRealBody = removeSafePrefixAndGetRealBody(asString);
        try {
            JSONObject jSONObject = new JSONObject(strRemoveSafePrefixAndGetRealBody);
            int i = jSONObject.getInt("code");
            String string = jSONObject.getString("description");
            AccountLogger.log(TAG, "getQRLoginUrl code: " + i + ", desc: " + string);
            if (i == 0) {
                return new QRLoginUrlInfo(str, jSONObject.getString("lp"), jSONObject.getString("loginUrl"), jSONObject.getString("qr"));
            }
            throw new InvalidResponseException(i, string);
        } catch (JSONException unused) {
            throw new InvalidResponseException("JSONException: " + strRemoveSafePrefixAndGetRealBody);
        }
    }

    public static AccountInfo loginByLongPolling(QRLoginUrlInfo qRLoginUrlInfo) throws NeedNotificationException, AccessDeniedException, InvalidCredentialException, IOException, InvalidResponseException, AuthenticationFailureException {
        if (qRLoginUrlInfo == null) {
            throw new IllegalArgumentException("null long polling para");
        }
        SimpleRequest.StringContent asString = SimpleRequestForAccount.getAsString(qRLoginUrlInfo.lp, null, null, null, true, TIMEOUT_LONG_POLLING);
        if (asString == null) {
            throw new InvalidResponseException("long polling result is null");
        }
        try {
            return processLoginContent(asString, qRLoginUrlInfo.serviceId, true, true, false);
        } catch (InvalidUserNameException | NeedCaptchaException | NeedVerificationException e) {
            throw new InvalidResponseException("should not reach here!", e);
        } catch (PackageNameDeniedException unused) {
            throw new InvalidResponseException("It's not loginByPassToken(), PackageNameDeniedException is unexpected");
        }
    }

    public static String getCountryCode(String str) throws AccessDeniedException, IOException, AuthenticationFailureException {
        return SimpleRequestForAccount.getAsString(URLs.URL_GET_COUNTRY_CODE, new EasyMap().easyPut("locale", str), null, true).getBody();
    }

    private static String addDeviceIdInCookies(EasyMap<String, String> easyMap, String str) {
        if (easyMap == null) {
            throw new IllegalArgumentException("cookie params should not be null");
        }
        Application applicationContext = XMPassportSettings.getApplicationContext();
        String str2 = PrivacyDataMaster.get(applicationContext, PrivacyDataType.OAID, new String[0]);
        if (TextUtils.isEmpty(str)) {
            str = getHashedDeviceId();
        }
        if (applicationContext != null) {
            AssertionUtils.checkCondition(applicationContext, !TextUtils.isEmpty(str), "deviceId cannot be empty", true);
        }
        easyMap.easyPutOpt("deviceId", str).easyPutOpt("pass_o", str2).easyPutOpt("userSpaceId", UserSpaceIdUtil.getNullableUserSpaceIdCookie());
        return str;
    }

    private static void fillCommonParams(EasyMap<String, String> easyMap) {
        if (easyMap != null) {
            easyMap.putAll(XMPassportUtil.getDefaultLocaleParam());
        }
    }

    private static String getHashedDeviceId() {
        return AccountDeviceIdManager.getInstance().getDeviceId();
    }

    public static UplinkRemoteConfigInfo querySmsGateway(String str, String str2, String str3, String str4, String str5) throws AccessDeniedException, IOException, InvalidResponseException, AuthenticationFailureException {
        String str6 = URLs.ACCOUNT_DOMAIN + "/pass2/mobileOriginal/config";
        EasyMap easyMapEasyPutOpt = new EasyMap().easyPut("user", str).easyPut("extraType", str2).easyPut("sceneId", str3).easyPut("countryCode", str4).easyPutOpt("sid", str5);
        easyMapEasyPutOpt.putAll(XMPassportUtil.getDefaultLocaleParam());
        SimpleRequest.StringContent asString = SimpleRequestForAccount.getAsString(str6, easyMapEasyPutOpt, null, null, true);
        if (asString == null) {
            throw new InvalidResponseException("result content is null");
        }
        try {
            JSONObject jSONObject = new JSONObject(removeSafePrefixAndGetRealBody(asString));
            int i = jSONObject.getInt("code");
            String str7 = "code: " + i + ", desc: " + jSONObject.optString("description");
            AccountLogger.log(TAG, "get mobileOriginal config: " + str7);
            if (i == 0) {
                boolean zOptBoolean = jSONObject.optBoolean("enableMo");
                JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("config");
                for (int i2 = 0; i2 < jSONArrayOptJSONArray.length(); i2++) {
                    if ("default".equals(jSONArrayOptJSONArray.getJSONObject(i2).optString("operator"))) {
                        JSONArray jSONArrayOptJSONArray2 = jSONArrayOptJSONArray.optJSONObject(i2).optJSONArray("gateways");
                        if (jSONArrayOptJSONArray2.length() > 0) {
                            return new UplinkRemoteConfigInfo(zOptBoolean, jSONArrayOptJSONArray2.getString(0));
                        }
                    }
                }
                throw new InvalidResponseException(jSONObject.toString());
            }
            throw new InvalidResponseException(i, str7);
        } catch (JSONException unused) {
            throw new InvalidResponseException("result not json");
        }
    }

    public static UpLinkVerifyResult verifyMobileOrignal(String str, String str2, String str3, String str4, String str5) throws InvalidParameterException, AccessDeniedException, InvalidCredentialException, IOException, InvalidResponseException, AuthenticationFailureException {
        String str6 = URLs.ACCOUNT_DOMAIN + "/pass2/mobileOriginal/verify";
        EasyMap easyMap = new EasyMap();
        easyMap.easyPut("user", str);
        easyMap.easyPut("extraType", str2);
        easyMap.easyPut("sceneId", str3);
        easyMap.easyPut("ticket", str5);
        easyMap.put("sid", str4);
        try {
            JSONObject jSONObject = new JSONObject(removeSafePrefixAndGetRealBody(SimpleRequestForAccount.postAsString(str6, easyMap, null, true)));
            int i = jSONObject.getInt("code");
            String str7 = "code: " + i + ", desc: " + jSONObject.optString("description");
            AccountLogger.log(TAG, "verifyMobileOrignal config: " + str7);
            if (i == 0) {
                return new UpLinkVerifyResult(jSONObject);
            }
            if (i == 21317) {
                throw new InvalidCredentialException(i, str7, false);
            }
            if (i == 60018) {
                throw new InvalidParameterException(i, str7);
            }
            throw new InvalidResponseException(i, str7, new PassThroughErrorInfo(jSONObject));
        } catch (JSONException unused) {
            throw new InvalidResponseException("result not json");
        }
    }

    public static AccountVipInfo queryUserVipInfo(PassportInfo passportInfo, String str) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        try {
            JSONObject jSONObject = new JSONObject(SecureRequestForAccount.getAsString(URLs.URL_MI_ACCOUNT_VIP_INFO, new EasyMap().easyPut("userId", passportInfo.getUserId()).easyPut("sid", str).easyPut("transId", UUID.randomUUID().toString().substring(0, 15)), new EasyMap().easyPut("cUserId", passportInfo.getEncryptedUserId()).easyPut("serviceToken", passportInfo.getServiceToken()), true, passportInfo.getSecurity()).getBody());
            int iOptInt = jSONObject.optInt("status");
            if (iOptInt == 0 && jSONObject.has("data")) {
                JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                return new AccountVipInfo(jSONObject2.optString("level"), jSONObject2.optLong("activePoints"));
            }
            throw new InvalidResponseException(iOptInt, jSONObject.optString("message"));
        } catch (JSONException unused) {
            throw new InvalidResponseException("result not json");
        }
    }

    public static String queryRevokePrivacyPolicyLocation(String str, String str2) throws InvalidParameterException, AccessDeniedException, InvalidCredentialException, IOException, InvalidResponseException, AuthenticationFailureException {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            throw new InvalidParameterException(" no user id or passtoken ! ");
        }
        String str3 = URLs.ACCOUNT_DOMAIN + "/privacy/policy/agreement/recall/start";
        EasyMap easyMap = new EasyMap();
        easyMap.put("passToken", str2);
        easyMap.put("userId", str);
        try {
            JSONObject jSONObject = new JSONObject(removeSafePrefixAndGetRealBody(SimpleRequestForAccount.getAsString(str3, null, easyMap, true)));
            int i = jSONObject.getInt("code");
            String str4 = "code: " + i + ", desc: " + jSONObject.optString("description");
            AccountLogger.log(TAG, "queryRevokePrivacyPolicyLocation : " + str4);
            if (i == 0) {
                return jSONObject.optString("location");
            }
            if (i == 70016) {
                throw new InvalidCredentialException(i, str4, false);
            }
            throw new InvalidResponseException(i, str4, new PassThroughErrorInfo(jSONObject));
        } catch (JSONException unused) {
            throw new InvalidResponseException("result not json");
        }
    }

    public static String queryVerifyPhoneInfo(PassportInfo passportInfo) throws AccessDeniedException, IOException, InvalidResponseException, AuthenticationFailureException {
        return SimpleRequestForAccount.getAsString(URLs.URL_QUERY_VERIFY_PHONE_INFO, new EasyMap().easyPut("deviceId", getHashedDeviceId()).easyPut("userId", passportInfo.getUserId()).easyPut("locale", XMPassportUtil.getISOLocaleString(Locale.getDefault())), getPassportCookie(passportInfo), true).getBody();
    }

    public static boolean reportVerifyPhoneClickNotifyType(PassportInfo passportInfo, int i) throws AccessDeniedException, IOException, InvalidResponseException, AuthenticationFailureException {
        try {
            return new JSONObject(SimpleRequestForAccount.postAsString(URLs.URL_REPORT_VERIFY_PHONE_NOTIFY_STATUS, new EasyMap().easyPut("userId", passportInfo.getUserId()).easyPut("type", String.valueOf(i)), getPassportCookie(passportInfo), true).getBody()).optInt("status") == 0;
        } catch (JSONException unused) {
            throw new InvalidResponseException("result not json");
        }
    }

    public static List<OtherAppAccount> getUserInfoAndChoosePassToken(Map<String, List<String>> map) throws AccessDeniedException, IOException, InvalidResponseException, AuthenticationFailureException {
        String str = URLs.URL_GET_USER_INFO_AND_CHOOSE_PASSTOKEN;
        EasyMap easyMap = new EasyMap();
        easyMap.put("uLocale", Locale.getDefault().toString());
        try {
            easyMap.put("fid", FidManager.getInstance().getFid(XMPassportSettings.getApplicationContext()));
        } catch (FidSigningUtil.FidSignException e) {
            AccountLogger.log(TAG, "get fid failed: " + e.getMessage());
        }
        JSONArray jSONArray = new JSONArray();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            JSONObject jSONObject = new JSONObject();
            JSONArray jSONArray2 = new JSONArray();
            Iterator<String> it = entry.getValue().iterator();
            while (it.hasNext()) {
                jSONArray2.put(it.next());
            }
            try {
                jSONObject.put("userId", entry.getKey());
                jSONObject.put("passTokens", jSONArray2);
                jSONArray.put(jSONObject);
            } catch (JSONException unused) {
                throw new IllegalStateException("should never happen");
            }
        }
        new HashMap().put("postBody", jSONArray.toString());
        HashMap map2 = new HashMap();
        map2.put("Content-Type", HttpHeaders.CONTENT_TYPE_JSON);
        try {
            JSONObject jSONObject2 = new JSONObject(removeSafePrefixAndGetRealBody(SimpleRequestForAccount.postAsString(str, jSONArray.toString(), (Map<String, String>) easyMap, (Map<String, String>) map2, (Map<String, String>) null, true, (Integer) null)));
            if (jSONObject2.getInt("code") == 0) {
                JSONArray jSONArray3 = jSONObject2.getJSONArray("data");
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < jSONArray3.length(); i++) {
                    JSONObject jSONObject3 = jSONArray3.getJSONObject(i);
                    arrayList.add(new OtherAppAccount(String.valueOf(jSONObject3.getLong("userId")), jSONObject3.getString("passToken"), jSONObject3.getString("nickname"), jSONObject3.getString(RemoteDeviceInfo.KEY_ADDRESS), jSONObject3.getString(CallMethod.RESULT_ICON)));
                }
                return arrayList;
            }
            throwResponseContentException(jSONObject2);
            throw new IllegalStateException("should never happen");
        } catch (JSONException e2) {
            throw new InvalidResponseException("process result is failed", e2);
        }
    }

    private static void throwResponseContentException(JSONObject jSONObject) throws JSONException, InvalidResponseException, AuthenticationFailureException {
        int i = jSONObject.getInt("code");
        if (i == 70016) {
            throw new AuthenticationFailureException(jSONObject.toString());
        }
        throw new InvalidResponseException(i, jSONObject.optString("description"), new PassThroughErrorInfo(jSONObject));
    }

    public static boolean isSNSLoginAvailable(String str, String str2, String str3) throws AccessDeniedException, IOException, InvalidResponseException, AuthenticationFailureException {
        EasyMap easyMap = new EasyMap();
        easyMap.put("sid", str);
        easyMap.put("appid", str2);
        easyMap.put("_locale", str3);
        try {
            JSONObject jSONObject = new JSONObject(removeSafePrefixAndGetRealBody(SimpleRequestForAccount.getAsString(URLs.URL_SNS_LOGIN, easyMap, null, true)));
            int i = jSONObject.getInt("code");
            PassThroughErrorInfo passThroughErrorInfo = new PassThroughErrorInfo(jSONObject);
            String strOptString = jSONObject.optString("description");
            if (i == 0) {
                return true;
            }
            throw new InvalidResponseException(i, strOptString, passThroughErrorInfo);
        } catch (JSONException e) {
            AccountLogger.log(TAG, "json error", e);
            throw new InvalidResponseException("json error");
        }
    }

    public static AccountInfo secondaryDeviceLogin(PassTokenLoginParams passTokenLoginParams, SecondaryDeviceInfo secondaryDeviceInfo) throws AccessDeniedException, RequestSecondaryDeviceLoginDataException, InvalidCredentialException, IOException, AuthenticationFailureException {
        EasyMap easyMapEasyPut = new EasyMap().easyPut("Secondary-UA", secondaryDeviceInfo.ua);
        EasyMap easyMapEasyPut2 = new EasyMap().easyPut("passToken", passTokenLoginParams.passToken).easyPut("userId", passTokenLoginParams.userId).easyPut("uLocale", Locale.getDefault().toString());
        addDeviceIdInCookies(easyMapEasyPut2, null);
        EasyMap easyMap = new EasyMap();
        easyMap.put("secondaryBindUid", secondaryDeviceInfo.bindUid);
        easyMap.put("secondaryDid", secondaryDeviceInfo.deviceId);
        easyMap.put("secondaryTs", secondaryDeviceInfo.nonce);
        easyMap.put("secondarySign", secondaryDeviceInfo.sign);
        easyMap.put("secondaryExceptPwdChange", String.valueOf(!secondaryDeviceInfo.exceptPwdChange ? 1 : 0));
        easyMap.put("bizIdcInfo", secondaryDeviceInfo.bizIdcInfo);
        try {
            JSONObject jSONObject = new JSONObject(removeSafePrefixAndGetRealBody(SimpleRequestForAccount.getAsString(URLs.URL_SECONDARY_LOGIN, easyMap, easyMapEasyPut, easyMapEasyPut2, true)));
            int i = jSONObject.getInt("code");
            String strOptString = jSONObject.optString("desc");
            if (i != 0) {
                if (i == 21327 || i == 70016) {
                    throw new InvalidCredentialException(i, strOptString, false);
                }
                throw new RequestSecondaryDeviceLoginDataException(strOptString, RequestSecondaryDeviceLoginDataException.Code.SERVER_ERR, i);
            }
            JSONObject jSONObject2 = jSONObject.getJSONObject("data");
            String string = jSONObject2.getString("userId");
            String string2 = jSONObject2.getString("psecurity");
            String string3 = jSONObject2.getString("cUserId");
            return new AccountInfo.Builder().userId(string).psecurity(string2).encryptedUserId(string3).passToken(jSONObject2.getString("secondaryPassToken")).build();
        } catch (JSONException e) {
            throw new IOException(e);
        }
    }
}
