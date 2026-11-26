package com.xiaomi.passport.snscorelib.internal.utils;

import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import com.xiaomi.accountsdk.utils.AbstractAccountWebViewSingleCookieUtil;
import com.xiaomi.passport.utils.HttpCookies;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class SNSCookieManager {
    public static void setupCookiesForAccountWeb(WebView webView, Map<String, String> map) {
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

    private static void setAccountCookie(CookieManager cookieManager, String str, String str2) {
        String str3;
        if (cookieManager == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return;
        }
        if (str.equals("passToken")) {
            str3 = String.format("%s=%s;HttpOnly;", str, str2);
        } else {
            str3 = String.format("%s=%s;Secure;", str, str2);
        }
        cookieManager.setCookie(AbstractAccountWebViewSingleCookieUtil.DOMAIN_URL_SET_COOKIE, str3);
    }

    public static String getSnsBindCookie(String str) {
        return extractFromCookieString(str, "sns-bind-step");
    }

    public static String extractFromCookieString(String str, String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            for (String str3 : str.split(";")) {
                if (str3.contains(str2) && str3.split("=")[0].trim().equals(str2)) {
                    return str3.substring(str3.indexOf("=") + 1);
                }
            }
        }
        return null;
    }

    public static String getLocaleString() {
        Locale locale = Locale.getDefault();
        if (locale == null) {
            return null;
        }
        String language = locale.getLanguage();
        String country = locale.getCountry();
        return TextUtils.isEmpty(country) ? language : String.format("%s_%s", language, country);
    }
}
