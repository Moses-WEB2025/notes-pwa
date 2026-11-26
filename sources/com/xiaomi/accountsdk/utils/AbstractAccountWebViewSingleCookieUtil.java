package com.xiaomi.accountsdk.utils;

import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import com.xiaomi.accountsdk.account.XMPassport;
import com.xiaomi.passport.utils.HttpCookies;

/* JADX INFO: loaded from: classes4.dex */
public abstract class AbstractAccountWebViewSingleCookieUtil {
    public static final String DOMAIN_URL_SET_COOKIE;

    protected abstract String getCookieName();

    protected abstract String getCookieValue();

    static {
        DOMAIN_URL_SET_COOKIE = XMPassport.USE_PREVIEW ? "http://.account.preview.n.xiaomi.net" : "https://.account.xiaomi.com";
    }

    public final void setupNonNullCookieForAccountWeb(WebView webView) {
        if (webView == null || TextUtils.isEmpty(getCookieValue())) {
            return;
        }
        CookieSyncManager.createInstance(webView.getContext());
        setAccountCookie(HttpCookies.getCookieManagerOrNull(), getCookieName(), getCookieValue());
        CookieSyncManager.getInstance().sync();
    }

    protected final void setAccountCookie(CookieManager cookieManager, String str) {
        setAccountCookie(cookieManager, getCookieName(), str);
    }

    protected static void setAccountCookie(CookieManager cookieManager, String str, String str2) {
        if (cookieManager == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return;
        }
        cookieManager.setCookie(DOMAIN_URL_SET_COOKIE, String.format("%s=%s;", str, str2));
    }
}
