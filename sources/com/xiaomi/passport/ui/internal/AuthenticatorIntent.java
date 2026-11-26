package com.xiaomi.passport.ui.internal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import com.xiaomi.passport.LocalFeatures.LocalFeaturesManagerResponse;
import com.xiaomi.passport.interfaces.AuthenticatorIntentInterface;
import com.xiaomi.passport.jsb.PassportJsbWebViewPageConfig;
import com.xiaomi.passport.ui.action.QRCodeAuthPJWPLL;
import com.xiaomi.passport.ui.page.AccountLoginActivity;
import com.xiaomi.passport.ui.page.UserInfoActivity;
import com.xiaomi.passport.utils.HttpCookies;
import com.xiaomi.passport.webview.UrlInterceptor;
import com.xiaomi.passport.webview.UrlLoadPrepareTask;

/* JADX INFO: loaded from: classes4.dex */
public class AuthenticatorIntent extends AuthenticatorIntentInterface {
    @Override // com.xiaomi.passport.interfaces.AuthenticatorIntentInterface
    public Intent getAccountLoginActivityIntentImpl(Context context) {
        return new Intent(context, (Class<?>) AccountLoginActivity.class);
    }

    @Override // com.xiaomi.passport.interfaces.AuthenticatorIntentInterface
    public Intent getConfirmCredentialActivityIntentImpl(Context context) {
        return new Intent(context, (Class<?>) ConfirmCredentialActivity.class);
    }

    @Override // com.xiaomi.passport.interfaces.AuthenticatorIntentInterface
    public Intent getAccountNotificationActivityIntentImpl(Context context, Parcelable parcelable, String str, String str2, Bundle bundle) {
        Intent intentMakeIntent = PassportJsbWebViewActivity.makeIntent(context, new PassportJsbWebViewPageConfig.Builder().url(str).removeAllCookies(true).closeAfterLogin(true).cookieFillConfig(PassportJsbWebViewPageConfig.CookieFillConfig.create(HttpCookies.COOKIE_URL_ACCOUNT_DOMAIN, true, null)).headerFillConfig(PassportJsbWebViewPageConfig.HeaderFillConfig.create(true)).actionBarConfig(PassportJsbWebViewPageConfig.ActionBarConfig.createNoTitle()).urlLoadInteractionStrategy(PassportJsbWebViewPageConfig.UrlInteractionStrategy.create(new UrlInterceptor[0], new UrlLoadPrepareTask[0], null)).build());
        intentMakeIntent.putExtra("service_id", str2);
        intentMakeIntent.putExtra("accountAuthenticatorResponse", parcelable);
        if (bundle != null) {
            intentMakeIntent.putExtras(bundle);
        }
        return intentMakeIntent;
    }

    @Override // com.xiaomi.passport.interfaces.AuthenticatorIntentInterface
    public Intent getQRCodeAuthActivityIntentImpl(Context context, String str, Parcelable parcelable) {
        return PassportJsbWebViewActivity.makeIntent(context, new PassportJsbWebViewPageConfig.Builder().url(str).removeAllCookies(true).cookieFillConfig(PassportJsbWebViewPageConfig.CookieFillConfig.create(HttpCookies.COOKIE_URL_ACCOUNT_DOMAIN, true, null)).actionBarConfig(PassportJsbWebViewPageConfig.ActionBarConfig.create("none", null)).pageLifecycleListener(new QRCodeAuthPJWPLL(str, parcelable instanceof LocalFeaturesManagerResponse ? (LocalFeaturesManagerResponse) parcelable : null)).build());
    }

    @Override // com.xiaomi.passport.interfaces.AuthenticatorIntentInterface
    public Intent getAccountHomActivityIntentImpl(Context context) {
        return new Intent(context, (Class<?>) UserInfoActivity.class);
    }

    @Override // com.xiaomi.passport.interfaces.AuthenticatorIntentInterface
    public Intent getJsbWebViewActivityIntentImpl(Context context, PassportJsbWebViewPageConfig passportJsbWebViewPageConfig) {
        return PassportJsbWebViewActivity.makeIntent(context, passportJsbWebViewPageConfig);
    }
}
