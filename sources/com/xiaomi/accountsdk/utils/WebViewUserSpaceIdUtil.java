package com.xiaomi.accountsdk.utils;

import android.webkit.CookieManager;
import android.webkit.WebView;

/* JADX INFO: loaded from: classes4.dex */
public final class WebViewUserSpaceIdUtil extends AbstractAccountWebViewSingleCookieUtil {
    public void setupUserSpaceIdForAccountWeb(WebView webView) {
        super.setupNonNullCookieForAccountWeb(webView);
    }

    public void setupUserSpaceIdCookie(String str, CookieManager cookieManager) {
        super.setAccountCookie(cookieManager, str);
    }

    @Override // com.xiaomi.accountsdk.utils.AbstractAccountWebViewSingleCookieUtil
    protected String getCookieName() {
        return "userSpaceId";
    }

    @Override // com.xiaomi.accountsdk.utils.AbstractAccountWebViewSingleCookieUtil
    protected String getCookieValue() {
        return UserSpaceIdUtil.getNullableUserSpaceIdCookie();
    }
}
