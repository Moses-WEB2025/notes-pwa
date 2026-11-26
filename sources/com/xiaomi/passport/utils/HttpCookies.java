package com.xiaomi.passport.utils;

import android.text.TextUtils;
import android.webkit.CookieManager;
import com.xiaomi.accountsdk.account.XMPassport;
import com.xiaomi.accountsdk.hasheddeviceidlib.AccountDeviceIdManager;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.accountsdk.utils.FidNonce;
import com.xiaomi.accountsdk.utils.UserSpaceIdUtil;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class HttpCookies {
    private static final String COOKIE_ITEM_SPLITTER = "; ";
    public static final String COOKIE_KEY_DEVICE_ID = "deviceId";
    public static final String COOKIE_KEY_FID_NONCE = "fidNonce";
    public static final String COOKIE_KEY_FID_NONCE_SIGN = "fidNonceSign";
    public static final String COOKIE_KEY_PASS_TOKEN = "passToken";
    public static final String COOKIE_KEY_USER_ID = "userId";
    public static final String COOKIE_KEY_USER_SPACE_ID = "userSpaceId";
    private static final String COOKIE_KV_SPLITTER = "=";
    public static final String COOKIE_URL_ACCOUNT_DOMAIN;
    private static final String TAG = "HttpCookies";
    private static ICookiesSetInterceptor sCookiesSetInterceptor;

    public interface ICookiesSetInterceptor {
        boolean onInterceptor(String str, String str2);
    }

    static {
        String str;
        if (XMPassport.USE_PREVIEW) {
            str = "http://.account.preview.n.xiaomi.net";
        } else {
            str = "https://.account.xiaomi.com";
        }
        COOKIE_URL_ACCOUNT_DOMAIN = str;
        sCookiesSetInterceptor = null;
    }

    public static void set(List<HttpCookie> list) {
        CookieManager cookieManagerOrNull;
        if (list == null || list.isEmpty() || (cookieManagerOrNull = getCookieManagerOrNull()) == null) {
            return;
        }
        for (HttpCookie httpCookie : list) {
            StringBuilder sb = new StringBuilder(String.format("%s=%s; ", httpCookie.getName(), httpCookie.getValue()));
            if (httpCookie.getSecure()) {
                sb.append("Secure; ");
            }
            if (httpCookie.isHttpOnly()) {
                sb.append("HttpOnly; ");
            }
            if (httpCookie.getMaxAge() > 0) {
                sb.append(String.format("Max-Age=%d; ", Long.valueOf(httpCookie.getMaxAge())));
            }
            if (!TextUtils.isEmpty(httpCookie.getPath())) {
                sb.append(String.format("Path=%s; ", httpCookie.getPath()));
            }
            setCookie(cookieManagerOrNull, httpCookie.getSecure() ? "https://" + httpCookie.getDomain() : httpCookie.getDomain(), sb.toString());
        }
    }

    public static void set(String str, Map<String, String> map) {
        CookieManager cookieManagerOrNull = getCookieManagerOrNull();
        if (cookieManagerOrNull == null) {
            return;
        }
        cookieManagerOrNull.setAcceptCookie(true);
        CookieManager.setAcceptFileSchemeCookies(true);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            AccountLogger.log(TAG, "cookie-set " + String.format("%s=%s", entry.getKey(), LogPrivacyMasker.mask(entry.getValue())));
        }
        Iterator<String> it = list(map).iterator();
        while (it.hasNext()) {
            setCookie(cookieManagerOrNull, str, it.next());
        }
        cookieManagerOrNull.flush();
        String cookie = cookieManagerOrNull.getCookie(str);
        AccountLogger.log(TAG, "cookie-after-set " + LogPrivacyMasker.mask(cookie) + " -> " + parse(cookie).keySet());
    }

    public static String get(String str) {
        CookieManager cookieManagerOrNull = getCookieManagerOrNull();
        if (cookieManagerOrNull != null) {
            return cookieManagerOrNull.getCookie(str);
        }
        return null;
    }

    public static void remove(String str, String str2) {
        HashMap map = new HashMap();
        map.put(str2, null);
        set(str, map);
    }

    public static List<String> list(Map<String, String> map) {
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (!TextUtils.isEmpty(entry.getKey())) {
                arrayList.add(String.format("%s%s%s", entry.getKey(), COOKIE_KV_SPLITTER, TextUtils.isEmpty(entry.getValue()) ? "" : entry.getValue()));
            }
        }
        return arrayList;
    }

    public static String stringify(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        for (String str : list(map)) {
            if (sb.length() > 0) {
                sb.append(COOKIE_ITEM_SPLITTER);
            }
            sb.append(str);
        }
        return sb.toString();
    }

    public static Map<String, String> parse(String str) {
        int iIndexOf;
        HashMap map = new HashMap();
        if (!TextUtils.isEmpty(str)) {
            for (String str2 : str.split(COOKIE_ITEM_SPLITTER)) {
                if (!TextUtils.isEmpty(str2) && (iIndexOf = str2.indexOf(COOKIE_KV_SPLITTER)) != -1) {
                    map.put(str2.substring(0, iIndexOf), str2.substring(iIndexOf + 1));
                }
            }
        }
        return map;
    }

    public static void clear() {
        CookieManager cookieManagerOrNull = getCookieManagerOrNull();
        if (cookieManagerOrNull != null) {
            cookieManagerOrNull.removeAllCookie();
        }
        AccountLogger.log(TAG, "cookie clear all");
    }

    public static void fillDeviceId(Map<String, String> map) {
        if (map.containsKey("deviceId")) {
            return;
        }
        map.put("deviceId", AccountDeviceIdManager.getInstance().getDeviceId());
        map.put("userSpaceId", UserSpaceIdUtil.getNullableUserSpaceIdCookie());
    }

    public static void fillFidNonce(Map<String, String> map, FidNonce.Type type) {
        FidNonce fidNonceBuild;
        if (map.containsKey("fidNonce") || map.containsKey("fidNonceSign") || (fidNonceBuild = new FidNonce.Builder().build(type)) == null || TextUtils.isEmpty(fidNonceBuild.plain) || TextUtils.isEmpty(fidNonceBuild.sign)) {
            return;
        }
        map.put("fidNonce", fidNonceBuild.plain);
        map.put("fidNonceSign", fidNonceBuild.sign);
    }

    public static CookieManager getCookieManagerOrNull() {
        try {
            return CookieManager.getInstance();
        } catch (Exception e) {
            AccountLogger.log(TAG, "getCookieManagerOrNull>>>get cookieManager err", e);
            return null;
        }
    }

    private static void setCookie(CookieManager cookieManager, String str, String str2) {
        if (cookieManager == null) {
            return;
        }
        ICookiesSetInterceptor iCookiesSetInterceptor = sCookiesSetInterceptor;
        if (iCookiesSetInterceptor != null && iCookiesSetInterceptor.onInterceptor(str, str2)) {
            AccountLogger.log(TAG, "setCookie>>>interceptor set cookies, url=" + str + "  cookie=" + str2);
        } else {
            cookieManager.setCookie(str, str2);
        }
    }

    public static void setCookiesInterceptor(ICookiesSetInterceptor iCookiesSetInterceptor) {
        AccountLogger.log(TAG, "setCookiesInterceptor>>>" + iCookiesSetInterceptor.getClass().getName());
        sCookiesSetInterceptor = iCookiesSetInterceptor;
    }
}
