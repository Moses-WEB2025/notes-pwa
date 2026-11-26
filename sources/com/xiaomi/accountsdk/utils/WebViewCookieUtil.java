package com.xiaomi.accountsdk.utils;

import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import com.xiaomi.passport.utils.HttpCookies;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class WebViewCookieUtil extends AbstractAccountWebViewSingleCookieUtil {
    public void setupCookiesForAccountWeb(WebView webView, Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return;
        }
        CookieSyncManager.createInstance(webView.getContext());
        CookieManager cookieManagerOrNull = HttpCookies.getCookieManagerOrNull();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            setAccountCookie(cookieManagerOrNull, entry.getKey(), entry.getValue());
        }
        CookieSyncManager.getInstance().sync();
    }

    @Override // com.xiaomi.accountsdk.utils.AbstractAccountWebViewSingleCookieUtil
    protected String getCookieName() {
        return "";
    }

    @Override // com.xiaomi.accountsdk.utils.AbstractAccountWebViewSingleCookieUtil
    protected String getCookieValue() {
        return "";
    }
}
