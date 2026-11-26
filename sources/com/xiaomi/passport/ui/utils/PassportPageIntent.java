package com.xiaomi.passport.ui.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import com.xiaomi.accountsdk.account.URLs;
import com.xiaomi.accountsdk.account.data.Constants;
import com.xiaomi.passport.accountmanager.XiaomiAccountManager;
import com.xiaomi.passport.jsb.ParcelablePassportJsbMethod;
import com.xiaomi.passport.jsb.PassportJsbWebViewPageConfig;
import com.xiaomi.passport.snscorelib.internal.entity.SNSBindParameter;
import com.xiaomi.passport.ui.internal.PassportJsbWebViewActivity;
import com.xiaomi.passport.ui.webview.SNSCodeUrlInterceptor;
import com.xiaomi.passport.ui.webview.SNSCookieBindULPT;
import com.xiaomi.passport.ui.webview.SNSCookieBindUrlInterceptor;
import com.xiaomi.passport.utils.AnyUrl;
import com.xiaomi.passport.utils.HttpCookies;
import com.xiaomi.passport.webview.UrlInterceptor;
import com.xiaomi.passport.webview.UrlLoadPrepareTask;
import java.util.HashMap;

/* JADX INFO: loaded from: classes4.dex */
public class PassportPageIntent {
    public static Intent getAccountLoginPageIntent(Context context, String str, Bundle bundle, Parcelable parcelable) {
        return XiaomiAccountManager.get(context).getAccountLoginActivityIntent(str, bundle, parcelable);
    }

    public static Intent getAccountHomePageIntent(Context context) {
        return XiaomiAccountManager.get(context).getAccountHomeActivityIntent();
    }

    public static Intent getDefaultWebPageIntent(Context context, String str) {
        return PassportJsbWebViewActivity.makeIntent(context, str);
    }

    public static Intent getExternalWebPageIntentWithBestEffort(Context context, String str) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(str));
        return context.getPackageManager().queryIntentActivities(intent, 0).isEmpty() ? getDefaultWebPageIntent(context, str) : intent;
    }

    public static Intent getHelpCenterPageIntent(Context context) {
        return getDefaultWebPageIntent(context, Constants.URL_HELP_CENTER);
    }

    public static Intent getFAQCanNotReceiveSMS(Context context) {
        return getDefaultWebPageIntent(context, URLs.URL_FAIL_RECEIVE_SMS_CODE_FAQ);
    }

    public static Intent getRegisterPageIntent(Context context, String str, String str2, String str3) {
        HashMap map = new HashMap();
        map.put("sid", str);
        map.put("_uRegion", str2);
        map.put("_bannerBiz", str3);
        return PassportJsbWebViewActivity.makeIntent(context, new PassportJsbWebViewPageConfig.Builder().url(AnyUrl.make(URLs.URL_REGISTER, map)).actionBarConfig(PassportJsbWebViewPageConfig.ActionBarConfig.createNoTitle()).removeAllCookies(true).headerFillConfig(PassportJsbWebViewPageConfig.HeaderFillConfig.create(true)).closeAfterLogin(true).build());
    }

    public static Intent getFindPasswordPageIntent(Context context, String str) {
        HashMap map = new HashMap();
        map.put("_bannerBiz", str);
        return PassportJsbWebViewActivity.makeIntent(context, new PassportJsbWebViewPageConfig.Builder().url(AnyUrl.make(com.xiaomi.passport.Constants.PASSWORD_RECOVERY_URL, map)).actionBarConfig(PassportJsbWebViewPageConfig.ActionBarConfig.createNoTitle()).removeAllCookies(true).headerFillConfig(PassportJsbWebViewPageConfig.HeaderFillConfig.create(true)).closeAfterLogin(true).build());
    }

    public static Intent getUserMainWebPageIntent(Context context) {
        return PassportJsbWebViewActivity.makeIntent(context, new PassportJsbWebViewPageConfig.Builder().url(URLs.ACCOUNT_DOMAIN + "/pass/auth/security/home").cookieFillConfig(PassportJsbWebViewPageConfig.CookieFillConfig.create(HttpCookies.COOKIE_URL_ACCOUNT_DOMAIN, false, "passportapi")).build());
    }

    public static Intent getSNSBindPageIntent(Context context, SNSBindParameter sNSBindParameter) {
        return PassportJsbWebViewActivity.makeIntent(context, new PassportJsbWebViewPageConfig.Builder().url(sNSBindParameter.snsBindUrl).removeAllCookies(true).actionBarConfig(PassportJsbWebViewPageConfig.ActionBarConfig.create("none", null)).urlLoadInteractionStrategy(PassportJsbWebViewPageConfig.UrlInteractionStrategy.create(new UrlInterceptor[]{new SNSCookieBindUrlInterceptor(sNSBindParameter.hasLocalChannel)}, new UrlLoadPrepareTask[]{new SNSCookieBindULPT(sNSBindParameter.sns_token_ph, sNSBindParameter.sns_weixin_openId)}, new ParcelablePassportJsbMethod[0])).build());
    }

    public static Intent getSNSBindPageIntent(Context context, String str) {
        return PassportJsbWebViewActivity.makeIntent(context, new PassportJsbWebViewPageConfig.Builder().url(str).removeAllCookies(true).actionBarConfig(PassportJsbWebViewPageConfig.ActionBarConfig.create("none", null)).urlLoadInteractionStrategy(PassportJsbWebViewPageConfig.UrlInteractionStrategy.create(new UrlInterceptor[]{new SNSCodeUrlInterceptor()}, new UrlLoadPrepareTask[0], new ParcelablePassportJsbMethod[0])).build());
    }

    public static Intent getAccountFamilyWebPageIntent(Context context, ParcelablePassportJsbMethod parcelablePassportJsbMethod) {
        if (parcelablePassportJsbMethod == null) {
            throw new IllegalStateException("jsb method: getServiceData can not be null");
        }
        return PassportJsbWebViewActivity.makeIntent(context, new PassportJsbWebViewPageConfig.Builder().url(URLs.URL_ACCOUNT_FAMILY).cookieFillConfig(PassportJsbWebViewPageConfig.CookieFillConfig.create(HttpCookies.COOKIE_URL_ACCOUNT_DOMAIN, false, Constants.PASSPORT_H5_SID)).urlLoadInteractionStrategy(PassportJsbWebViewPageConfig.UrlInteractionStrategy.create(new UrlInterceptor[0], new UrlLoadPrepareTask[0], new ParcelablePassportJsbMethod[]{parcelablePassportJsbMethod})).removeAllCookies(true).build());
    }

    public static Intent getModifyPasswordPageIntent(Context context) {
        return getModifySafeInfoWebPageIntent(context, "setPassword");
    }

    public static Intent getModifyBoundEmailPageIntent(Context context) {
        return getModifySafeInfoWebPageIntent(context, "bindEmail");
    }

    public static Intent getModifyBoundPhonePageIntent(Context context) {
        return getModifySafeInfoWebPageIntent(context, "bindPhone");
    }

    private static Intent getModifySafeInfoWebPageIntent(Context context, String str) {
        String str2 = URLs.ACCOUNT_DOMAIN + "/fe/service/account";
        HashMap map = new HashMap();
        map.put("_service", str);
        map.put("_dc", String.valueOf(System.currentTimeMillis()));
        return PassportJsbWebViewActivity.makeIntent(context, new PassportJsbWebViewPageConfig.Builder().url(AnyUrl.make(str2, map)).removeAllCookies(true).cookieFillConfig(PassportJsbWebViewPageConfig.CookieFillConfig.create(HttpCookies.COOKIE_URL_ACCOUNT_DOMAIN, true, "passportapi")).build());
    }
}
