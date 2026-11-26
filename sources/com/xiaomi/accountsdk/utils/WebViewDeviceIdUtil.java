package com.xiaomi.accountsdk.utils;

import android.webkit.CookieManager;
import android.webkit.WebView;
import com.xiaomi.accountsdk.hasheddeviceidlib.AccountDeviceIdManager;

/* JADX INFO: loaded from: classes4.dex */
public final class WebViewDeviceIdUtil extends AbstractAccountWebViewSingleCookieUtil {
    public void setupDeviceIdForAccountWeb(WebView webView) {
        super.setupNonNullCookieForAccountWeb(webView);
    }

    public void setDeviceIdCookie(String str, CookieManager cookieManager) {
        super.setAccountCookie(cookieManager, str);
    }

    @Override // com.xiaomi.accountsdk.utils.AbstractAccountWebViewSingleCookieUtil
    protected String getCookieName() {
        return "deviceId";
    }

    @Override // com.xiaomi.accountsdk.utils.AbstractAccountWebViewSingleCookieUtil
    protected String getCookieValue() {
        return AccountDeviceIdManager.getInstance().getDeviceId();
    }
}
