package com.xiaomi.accountsdk.account;

import android.content.Context;
import miui.cloud.CloudRequestUtils;

/* JADX INFO: loaded from: classes4.dex */
public class URLs {
    public static final String ACCOUNT_DOMAIN;
    static final String HOST_URL_ACCOUNT_BASE;
    static final String OPEN_URL_GET_ACCESS_TOKEN;
    static final String OPEN_URL_REFRESH_ACCESS_TOKEN;
    public static final String URL_ACCOUNT_API_V2_BASE;
    public static final String URL_ACCOUNT_API_V3_BASE;
    public static final String URL_ACCOUNT_BASE;
    private static final String URL_ACCOUNT_BASE_PASS2 = "https://account.xiaomi.com/pass2";
    public static final String URL_ACCOUNT_FAMILY;
    public static final String URL_ACCOUNT_HELP_CENTER;
    public static final String URL_ACCOUNT_NOTIFICATION_CONFIG = "https://api.account.xiaomi.com/pass/notice/config";
    public static final String URL_ACCOUNT_OAUTH_BASE;
    public static final String URL_ACCOUNT_SAFE_API_BASE;
    public static final String URL_ACCOUNT_SERVICE_CONFIG;
    static final String URL_ACCOUNT_USER_PROFILE;

    @Deprecated
    public static final String URL_ACOUNT_API_BASE;
    public static final String URL_ACOUNT_API_BASE_SECURE;
    public static final String URL_ACOUNT_API_BASE_V2_SECURE;
    public static final String URL_ACTIVATOR_BASE;
    static final String URL_ADD_BIND_EMAIL;
    static final String URL_ADD_BIND_PHONE;
    static final String URL_AUTH2_AUTHORIZE;
    public static final String URL_CHANGE_PASSWORD;
    static final String URL_CHECK_PHONE_ACTIVATE_STATUS;
    static final String URL_CHECK_SAFE_EMAIL_AVAILABILITY;
    static final String URL_COMMIT_UPDATE_ICON;
    static final String URL_DELETE_BIND_PHONE;
    public static final String URL_DEVICES_SETTING;
    public static final String URL_DEVICE_SETTING_LIST;
    public static final String URL_DEV_BASE;
    public static final String URL_DEV_SETTING;
    static String URL_EXCHANGE_PHONETOKE_HTTPS = null;
    public static final String URL_FAIL_RECEIVE_SMS_CODE_FAQ = "https://account.xiaomi.com/helpcenter/faq/_/02.faqs/05.sms-and-email-verification-code/faq-1";
    static final String URL_GENERATE_RANDOM_PASSWORD;
    public static final String URL_GET_BIND_EMAIL_CAPTCODE;
    public static final String URL_GET_COUNTRY_CODE;
    public static final String URL_GET_DEVICE_MODEL_INFOS;
    public static final String URL_GET_USER_CORE_INFO;
    public static final String URL_GET_USER_INFO_AND_CHOOSE_PASSTOKEN;
    static final String URL_IDENTITY_AUTH_FOR_ADDING_EMAIL;
    static final String URL_IDENTITY_AUTH_FOR_ADDING_PHONE;
    static final String URL_IDENTITY_AUTH_FOR_CHANGE_PWD;
    static final String URL_IDENTITY_AUTH_FOR_DELETING_PHONE;
    static final String URL_IDENTITY_AUTH_FOR_MODIFY_SAFE_PHONE;
    static final String URL_IDENTITY_AUTH_FOR_REPLACING_EMAIL;
    static final String URL_IDENTITY_AUTH_FOR_REPLACING_PHONE;
    static final String URL_IDENTITY_AUTH_FOR_SEND_EMAIL_ACTIVATE_MESSAGE;
    static final String URL_IDENTITY_AUTH_FOR_SET_SECURITY_QUESTIONS;

    @Deprecated
    public static final String URL_LOGIN;
    public static final String URL_LOGIN_AUTH2;
    static String URL_LOGIN_AUTH2_HTTPS = null;
    public static final String URL_LOGIN_AUTH_STEP2;
    static String URL_LOGIN_HTTPS = null;
    public static final String URL_LOGIN_IDENTITY;
    public static final String URL_LOGIN_NOTIFY_INFO;
    public static final String URL_LOGIN_SUCCESS_UPLOAD;
    public static final String URL_LOGOUT_LOCAL_ACCOUNT;
    public static final String URL_LOGOUT_SYSTEM_DEVICE;
    public static final String URL_LOGOUT_SYS_ACCOUNT;
    public static final String URL_MI_ACCOUNT_VIP_INFO;
    public static final String URL_OPEN_ACCOUNT_THIRD_BASE;
    static final String URL_PASS2_MODIFY_TWO_FACTOR_AUTH_TYPE = "https://account.xiaomi.com/pass2/modify/twoFactorAuthType/status";
    static final String URL_PASS2_PUBLIC_KEY = "https://account.xiaomi.com/pass2/config";
    static final String URL_PASS2_USER_STATUS = "https://account.xiaomi.com/pass2/user/status";
    public static final String URL_QUERY_VERIFY_PHONE_INFO;
    public static final String URL_REFRESH_PASS_TOKEN;
    static final String URL_REG;
    public static final String URL_REGISTER;
    static final String URL_REG_CHECK_VERIFY_CODE;
    public static final String URL_REG_GET_CAPTCHA_CODE;
    static final String URL_REG_GET_VERIFY_CODE;
    static final String URL_REG_PHONE;
    static final String URL_REG_SEND_PHONE_TICKET;
    static final String URL_REG_TOKEN;
    static final String URL_REG_VERIFY_PHONE;
    static final String URL_REPLACE_BIND_EMAIL;
    static final String URL_REPLACE_BIND_PHONE;
    public static final String URL_REPORT_VERIFY_PHONE_NOTIFY_STATUS;
    static final String URL_REQUEST_UPDATE_ICON;
    public static final String URL_RESEND_EMAIL;
    static final String URL_RESET_PASSWORD;
    public static final String URL_SECONDARY_LOGIN;
    static final String URL_SEND_BIND_EMAIL_VERIFY_CODE;
    static final String URL_SEND_BIND_PHONE_VERIFY_CODE;
    static final String URL_SEND_EMAIL_ACTIVATE_MESSAGE;
    static final String URL_SET_SECURITY_QUESTIONS;
    public static final String URL_SET_USER_EDUCATION;
    public static final String URL_SET_USER_INCOME;
    public static final String URL_SET_USER_LOCATION;
    public static final String URL_SET_USER_REGION;
    public static final String URL_SNS_LOGIN;
    public static final String URL_TWO_FACTOR_AUTH_DISABLE;
    public static final String URL_TWO_FACTOR_AUTH_ENABLE;
    public static final String URL_USER_EXISTS;
    static final boolean USE_PREVIEW;

    @Deprecated
    public static void setLocalUsePreview(Context context, boolean z) {
        XMPassportSettings.setLocalStaging(context, z);
    }

    static {
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        String str7;
        String str8;
        String str9;
        boolean zIsStaging = XMPassportSettings.isStaging();
        USE_PREVIEW = zIsStaging;
        if (zIsStaging) {
            str = "http://account.preview.n.xiaomi.net";
        } else {
            str = "https://account.xiaomi.com";
        }
        ACCOUNT_DOMAIN = str;
        if (zIsStaging) {
            str2 = "account.preview.n.xiaomi.net";
        } else {
            str2 = "account.xiaomi.com";
        }
        HOST_URL_ACCOUNT_BASE = str2;
        if (zIsStaging) {
            str3 = "http://account.preview.n.xiaomi.net/pass";
        } else {
            str3 = "https://account.xiaomi.com/pass";
        }
        URL_ACCOUNT_BASE = str3;
        String str10 = "http://api.account.xiaomi.com/pass";
        String str11 = zIsStaging ? "http://api.account.xiaomi.com/pass" : "https://api.account.xiaomi.com/pass";
        URL_ACTIVATOR_BASE = str11;
        if (zIsStaging) {
            str10 = "http://api.account.preview.n.xiaomi.net/pass";
        }
        URL_ACOUNT_API_BASE = str10;
        String str12 = zIsStaging ? "http://api.account.preview.n.xiaomi.net/pass" : "https://api.account.xiaomi.com/pass";
        URL_ACOUNT_API_BASE_SECURE = str12;
        String str13 = zIsStaging ? "http://api.account.preview.n.xiaomi.net/pass/v2" : "https://api.account.xiaomi.com/pass/v2";
        URL_ACOUNT_API_BASE_V2_SECURE = str13;
        if (zIsStaging) {
            str4 = "http://api.account.preview.n.xiaomi.net/pass/v2/safe";
        } else {
            str4 = "https://api.account.xiaomi.com/pass/v2/safe";
        }
        URL_ACCOUNT_SAFE_API_BASE = str4;
        URL_ACCOUNT_API_V2_BASE = zIsStaging ? "http://api.account.preview.n.xiaomi.net/pass/v2" : "https://api.account.xiaomi.com/pass/v2";
        if (zIsStaging) {
            str5 = "http://api.account.preview.n.xiaomi.net/pass/v3";
        } else {
            str5 = "https://api.account.xiaomi.com/pass/v3";
        }
        URL_ACCOUNT_API_V3_BASE = str5;
        if (zIsStaging) {
            str6 = "http://account.preview.n.xiaomi.net/oauth2/";
        } else {
            str6 = "https://account.xiaomi.com/oauth2/";
        }
        URL_ACCOUNT_OAUTH_BASE = str6;
        if (zIsStaging) {
            str7 = "http://api.device.preview.n.xiaomi.net";
        } else {
            str7 = "https://api.device.xiaomi.net";
        }
        URL_DEV_BASE = str7;
        URL_MI_ACCOUNT_VIP_INFO = str4 + "/user/vipLevelInfo";
        if (zIsStaging) {
            str8 = "http://api.micloud.preview.n.xiaomi.net/micAnonymous/mic/account/config";
        } else {
            str8 = "https://api.g.micloud.xiaomi.net/micAnonymous/mic/account/config";
        }
        URL_ACCOUNT_SERVICE_CONFIG = str8;
        URL_GET_DEVICE_MODEL_INFOS = str7 + "/modelinfos";
        URL_DEV_SETTING = str7 + CloudRequestUtils.URL_DEV_SETTING;
        URL_DEVICES_SETTING = str7 + "/api/user/devices/setting";
        URL_LOGIN_AUTH2 = str3 + "/serviceLoginAuth2";
        URL_LOGIN_AUTH2_HTTPS = str3 + "/serviceLoginAuth2";
        URL_EXCHANGE_PHONETOKE_HTTPS = str11 + "/phoneToken/exchangePhoneToken";
        URL_LOGIN_AUTH_STEP2 = str3 + "/loginStep2";
        URL_USER_EXISTS = str5 + "/user@id";
        URL_GET_USER_CORE_INFO = str4 + "/user/coreInfo";
        if (zIsStaging) {
            str9 = "http://open.account.preview.n.xiaomi.net/third/";
        } else {
            str9 = "https://open.account.xiaomi.com/third/";
        }
        URL_OPEN_ACCOUNT_THIRD_BASE = str9;
        URL_REQUEST_UPDATE_ICON = str4 + "/user/updateIconRequest";
        URL_COMMIT_UPDATE_ICON = str4 + "/user/updateIconCommit";
        URL_REG = str13 + "/user/full";
        URL_REG_PHONE = str12 + "/user/full/@phone";
        URL_RESEND_EMAIL = str12 + "/sendActivateMessage";
        URL_REG_GET_VERIFY_CODE = str3 + "/sendPhoneTicket";
        URL_REG_GET_CAPTCHA_CODE = str3 + "/getCode?icodeType=register";
        URL_REG_CHECK_VERIFY_CODE = str3 + "/verifyPhoneRegTicket";
        URL_REG_SEND_PHONE_TICKET = str3 + "/sendPhoneRegTicket";
        URL_REG_VERIFY_PHONE = str3 + "/verifyRegPhone";
        URL_REG_TOKEN = str3 + "/tokenRegister";
        URL_RESET_PASSWORD = str3 + "/auth/resetPassword";
        URL_AUTH2_AUTHORIZE = str6 + "authorize";
        URL_LOGIN = str3 + "/serviceLogin";
        URL_LOGIN_HTTPS = str3 + "/serviceLogin";
        URL_LOGOUT_LOCAL_ACCOUNT = str3 + "/logoutApp";
        URL_LOGOUT_SYS_ACCOUNT = str3 + "/logoutAppForSysAccount";
        OPEN_URL_GET_ACCESS_TOKEN = str9 + "getToken";
        OPEN_URL_REFRESH_ACCESS_TOKEN = str9 + "refreshToken";
        URL_ACCOUNT_USER_PROFILE = str4 + "/user/profile";
        URL_CHECK_SAFE_EMAIL_AVAILABILITY = str4 + "/user/checkSafeEmailBindParams";
        URL_SEND_BIND_EMAIL_VERIFY_CODE = str4 + "/user/sendBindSafeEmailVerifyMessage";
        URL_SEND_BIND_PHONE_VERIFY_CODE = str4 + "/user/sendBindAuthPhoneVerifyMessage";
        URL_ADD_BIND_PHONE = str4 + "/user/addPhone";
        URL_REPLACE_BIND_PHONE = str4 + "/user/updatePhone";
        URL_DELETE_BIND_PHONE = str4 + "/user/deletePhone";
        URL_REPLACE_BIND_EMAIL = str4 + "/user/replaceSafeEmailAddress";
        URL_ADD_BIND_EMAIL = str4 + "/user/addSafeEmailAddress";
        URL_SEND_EMAIL_ACTIVATE_MESSAGE = str4 + "/user/sendEmailActivateMessage";
        URL_SET_SECURITY_QUESTIONS = str4 + "/user/setSafeQuestions";
        URL_IDENTITY_AUTH_FOR_ADDING_PHONE = str4 + "/user/addPhoneAuth";
        URL_IDENTITY_AUTH_FOR_REPLACING_PHONE = str4 + "/user/updatePhoneAuth";
        URL_IDENTITY_AUTH_FOR_DELETING_PHONE = str4 + "/user/deletePhoneAuth";
        URL_IDENTITY_AUTH_FOR_REPLACING_EMAIL = str4 + "/user/replaceSafeEmailAddressAuth";
        URL_IDENTITY_AUTH_FOR_ADDING_EMAIL = str4 + "/user/addSafeEmailAddressAuth";
        URL_IDENTITY_AUTH_FOR_SEND_EMAIL_ACTIVATE_MESSAGE = str4 + "/user/sendEmailActivateMessageAuth";
        URL_IDENTITY_AUTH_FOR_SET_SECURITY_QUESTIONS = str4 + "/user/setSafeQuestionsAuth";
        URL_IDENTITY_AUTH_FOR_MODIFY_SAFE_PHONE = str4 + "/user/modifySafePhoneAuth";
        URL_IDENTITY_AUTH_FOR_CHANGE_PWD = str4 + "/user/native/changePasswordAuth";
        URL_CHECK_PHONE_ACTIVATE_STATUS = str4 + "/user/checkPhoneActivateStatus";
        URL_GET_BIND_EMAIL_CAPTCODE = str3 + "/getCode?icodeType=antispam";
        URL_CHANGE_PASSWORD = str4 + "/user/changePassword";
        URL_SET_USER_REGION = str4 + "/user/region";
        URL_SET_USER_LOCATION = str4 + "/user/setLocation";
        URL_SET_USER_EDUCATION = str4 + "/user/setEducation";
        URL_SET_USER_INCOME = str4 + "/user/setIncome";
        URL_GENERATE_RANDOM_PASSWORD = str + "/appConf/randomPwd";
        URL_REGISTER = str3 + "/register";
        URL_ACCOUNT_HELP_CENTER = str + "/helpcenter";
        URL_GET_COUNTRY_CODE = str12 + "/configuration/cc";
        URL_REPORT_VERIFY_PHONE_NOTIFY_STATUS = str11 + "/confirmPhone/redPointClick";
        URL_QUERY_VERIFY_PHONE_INFO = str11 + "/confirmPhone/recyleStatus";
        URL_REFRESH_PASS_TOKEN = str3 + "/login/passtoken/refresh";
        URL_GET_USER_INFO_AND_CHOOSE_PASSTOKEN = str3 + "/login/passtoken/app/userprofile";
        URL_LOGOUT_SYSTEM_DEVICE = str3 + "/logoutDeviceWithIdentityAuth";
        URL_ACCOUNT_FAMILY = str + "/fe/family";
        URL_SNS_LOGIN = str + "/pass/sns/login/check";
        URL_SECONDARY_LOGIN = str + "/pass/secondaryLogin";
        URL_LOGIN_IDENTITY = str + "/pass/login/identity";
        URL_TWO_FACTOR_AUTH_ENABLE = str4 + "/user/twofactorauth/enable";
        URL_TWO_FACTOR_AUTH_DISABLE = str4 + "/user/twofactorauth/disable";
        URL_LOGIN_SUCCESS_UPLOAD = str7 + "/api/user/device/loginSucceed";
        URL_LOGIN_NOTIFY_INFO = str7 + "/api/user/device/LoginNotifyInfo";
        URL_DEVICE_SETTING_LIST = str + "/fe/service/account/home/device";
    }
}
