package com.xiaomi.accountsdk.guestaccount;

import com.xiaomi.accountsdk.request.PassportSimpleRequest;
import defpackage.R2;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
final class GuestAccountHttpRequesterImplDefault implements GuestAccountHttpRequester {
    private static final String BODY_STRING_SPLIT = "&";
    private static final String COOKIE_STRING_SPLIT = "; ";
    private static final int DEFAULT_CONNECT_TIME_OUT = 15000;
    private static final int DEFAULT_READ_TIME_OUT = 15000;

    GuestAccountHttpRequesterImplDefault() {
    }

    @Override // com.xiaomi.accountsdk.guestaccount.GuestAccountHttpRequester
    public GuestAccountHttpRequester.Response post(String str, Map<String, String> map, Map<String, String> map2) throws IOException {
        if (str == null) {
            throw new IllegalArgumentException("url == null");
        }
        if (map == null) {
            throw new IllegalArgumentException("params == null");
        }
        if (map2 == null) {
            throw new IllegalArgumentException("cookies == null");
        }
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        try {
            httpURLConnection.setConnectTimeout(R2.layout.passport_fragment_phone_account_login);
            httpURLConnection.setReadTimeout(R2.layout.passport_fragment_phone_account_login);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod(PassportSimpleRequest.HTTP_METHOD_POST);
            httpURLConnection.setRequestProperty("Cookie", joinMap(map2, COOKIE_STRING_SPLIT));
            httpURLConnection.connect();
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            bufferedWriter.write(joinMap(map, BODY_STRING_SPLIT));
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            int responseCode = httpURLConnection.getResponseCode();
            BufferedReader bufferedReader = null;
            if (responseCode != 200) {
                GuestAccountHttpRequester.Response response = new GuestAccountHttpRequester.Response(responseCode, null, null, map2);
                httpURLConnection.disconnect();
                return response;
            }
            try {
                BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()), 1024);
                try {
                    StringBuilder sb = new StringBuilder();
                    while (true) {
                        String line = bufferedReader2.readLine();
                        if (line != null) {
                            sb.append(line);
                        } else {
                            GuestAccountHttpRequester.Response response2 = new GuestAccountHttpRequester.Response(responseCode, sb.toString(), httpURLConnection.getHeaderField(GuestAccountHttpRequester.HEADER_KEY_EXTENSION_PRAGMA), map2);
                            bufferedReader2.close();
                            httpURLConnection.disconnect();
                            return response2;
                        }
                    }
                } catch (Throwable th) {
                    th = th;
                    bufferedReader = bufferedReader2;
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Throwable th3) {
            httpURLConnection.disconnect();
            throw th3;
        }
    }

    @Override // com.xiaomi.accountsdk.guestaccount.GuestAccountHttpRequester
    public GuestAccountHttpRequester.Response get(String str) throws IOException {
        if (str == null) {
            throw new IllegalArgumentException("url == null");
        }
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        try {
            httpURLConnection.setConnectTimeout(R2.layout.passport_fragment_phone_account_login);
            httpURLConnection.setReadTimeout(R2.layout.passport_fragment_phone_account_login);
            httpURLConnection.setRequestMethod(PassportSimpleRequest.HTTP_METHOD_GET);
            httpURLConnection.connect();
            int responseCode = httpURLConnection.getResponseCode();
            BufferedReader bufferedReader = null;
            if (responseCode != 200) {
                GuestAccountHttpRequester.Response response = new GuestAccountHttpRequester.Response(responseCode, null, null, null);
                httpURLConnection.disconnect();
                return response;
            }
            try {
                BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()), 1024);
                try {
                    StringBuilder sb = new StringBuilder();
                    while (true) {
                        String line = bufferedReader2.readLine();
                        if (line == null) {
                            break;
                        }
                        sb.append(line);
                    }
                    HashMap map = new HashMap();
                    CookieManager cookieManager = new CookieManager();
                    URI uriCreate = URI.create(str);
                    cookieManager.put(uriCreate, httpURLConnection.getHeaderFields());
                    for (HttpCookie httpCookie : cookieManager.getCookieStore().get(uriCreate)) {
                        map.put(httpCookie.getName(), httpCookie.getValue());
                    }
                    GuestAccountHttpRequester.Response response2 = new GuestAccountHttpRequester.Response(responseCode, sb.toString(), httpURLConnection.getHeaderField(GuestAccountHttpRequester.HEADER_KEY_EXTENSION_PRAGMA), map);
                    bufferedReader2.close();
                    httpURLConnection.disconnect();
                    return response2;
                } catch (Throwable th) {
                    th = th;
                    bufferedReader = bufferedReader2;
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Throwable th3) {
            httpURLConnection.disconnect();
            throw th3;
        }
    }

    static String joinMap(Map<String, String> map, String str) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key != null && value != null) {
                if (z) {
                    z = false;
                } else {
                    sb.append(str);
                }
                sb.append(key);
                sb.append("=");
                sb.append(value);
            }
        }
        return sb.toString();
    }
}
