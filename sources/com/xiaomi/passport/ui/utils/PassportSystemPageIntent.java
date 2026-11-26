package com.xiaomi.passport.ui.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.xiaomi.accountsdk.account.URLs;
import com.xiaomi.accountsdk.account.data.Constants;
import com.xiaomi.passport.jsb.PassportJsbWebViewPageConfig;
import com.xiaomi.passport.utils.AnyUrl;
import com.xiaomi.passport.utils.HttpCookies;
import java.util.HashMap;

/* JADX INFO: loaded from: classes4.dex */
public class PassportSystemPageIntent {
    public static Intent getDeepLinkWebPageIntent(PassportJsbWebViewPageConfig passportJsbWebViewPageConfig, Bundle bundle) {
        Bundle bundle2 = new Bundle();
        passportJsbWebViewPageConfig.fillBundle(bundle2);
        if (bundle != null) {
            bundle2.putAll(bundle);
        }
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        HashMap map = new HashMap();
        for (String str : bundle2.keySet()) {
            map.put(str, String.valueOf(bundle2.get(str)));
        }
        intent.setData(Uri.parse(AnyUrl.make("xiaomiaccount://webview", map)));
        return intent;
    }

    public static Intent getAccountFamilyPageIntent(Context context) {
        Intent deepLinkWebPageIntent = getDeepLinkWebPageIntent(new PassportJsbWebViewPageConfig.Builder().url(URLs.URL_ACCOUNT_FAMILY).cookieFillConfig(PassportJsbWebViewPageConfig.CookieFillConfig.create(HttpCookies.COOKIE_URL_ACCOUNT_DOMAIN, true, Constants.PASSPORT_H5_SID)).removeAllCookies(true).build(), null);
        if (context.getPackageManager().queryIntentActivities(deepLinkWebPageIntent, 0).isEmpty()) {
            return null;
        }
        return deepLinkWebPageIntent;
    }
}
