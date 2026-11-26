package org.jsoup.helper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import org.jsoup.Connection;
import org.jsoup.internal.StringUtil;
import org.jsoup.parser.CharacterReader;

/* JADX INFO: loaded from: classes6.dex */
class CookieUtil {
    private static final String Cookie2Name = "Cookie2";
    private static final String CookieName = "Cookie";
    private static final Map<String, List<String>> EmptyRequestHeaders = Collections.unmodifiableMap(new HashMap());
    private static final String Sep = "; ";

    CookieUtil() {
    }

    static void applyCookiesToRequest(HttpConnection.Request request, BiConsumer<String, String> biConsumer) throws IOException {
        HashSet hashSet;
        HashSet hashSet2;
        LinkedHashSet<String> linkedHashSetRequestCookieSet = requestCookieSet(request);
        HashSet hashSet3 = null;
        for (Map.Entry<String, List<String>> entry : request.cookieManager().get(asUri(request.url), EmptyRequestHeaders).entrySet()) {
            List<String> value = entry.getValue();
            if (value != null && value.size() != 0) {
                String key = entry.getKey();
                if ("Cookie".equals(key)) {
                    hashSet = hashSet3;
                    hashSet2 = linkedHashSetRequestCookieSet;
                } else if (Cookie2Name.equals(key)) {
                    hashSet2 = new HashSet();
                    hashSet = hashSet2;
                }
                hashSet2.addAll(value);
                hashSet3 = hashSet;
            }
        }
        if (linkedHashSetRequestCookieSet.size() > 0) {
            biConsumer.accept("Cookie", StringUtil.join(linkedHashSetRequestCookieSet, Sep));
        }
        if (hashSet3 == null || hashSet3.size() <= 0) {
            return;
        }
        biConsumer.accept(Cookie2Name, StringUtil.join(hashSet3, Sep));
    }

    private static LinkedHashSet<String> requestCookieSet(Connection.Request request) {
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>();
        for (Map.Entry<String, String> entry : request.cookies().entrySet()) {
            linkedHashSet.add(entry.getKey() + "=" + entry.getValue());
        }
        return linkedHashSet;
    }

    static URI asUri(URL url) throws IOException {
        try {
            return url.toURI();
        } catch (URISyntaxException e) {
            MalformedURLException malformedURLException = new MalformedURLException(e.getMessage());
            malformedURLException.initCause(e);
            throw malformedURLException;
        }
    }

    static void storeCookies(HttpConnection.Request request, HttpConnection.Response response, URL url, Map<String, List<String>> map) throws IOException {
        request.cookieManager().put(asUri(url), map);
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            String key = entry.getKey();
            List<String> value = entry.getValue();
            if (key.equalsIgnoreCase("Set-Cookie")) {
                Iterator<String> it = value.iterator();
                while (it.hasNext()) {
                    parseCookie(it.next(), response);
                }
            }
        }
    }

    static void parseCookie(String str, HttpConnection.Response response) {
        if (str == null) {
            return;
        }
        CharacterReader characterReader = new CharacterReader(str);
        String strTrim = characterReader.consumeTo('=').trim();
        characterReader.advance();
        String strTrim2 = characterReader.consumeTo(';').trim();
        if (!strTrim.isEmpty()) {
            response.cookie(strTrim, strTrim2);
        }
        characterReader.close();
    }
}
