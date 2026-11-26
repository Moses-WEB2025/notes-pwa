package com.xiaomi.accountsdk.request;

import android.net.Uri;
import android.text.TextUtils;
import ch.qos.logback.core.CoreConstants;
import com.xiaomi.accountsdk.account.XMPassportSettings;
import com.xiaomi.accountsdk.request.intercept.CTANetworkBaseControl;
import com.xiaomi.accountsdk.request.intercept.NetworkInterceptCallback;
import com.xiaomi.accountsdk.request.intercept.NetworkInterceptor;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.accountsdk.utils.EasyMap;
import com.xiaomi.accountsdk.utils.IOUtils;
import com.xiaomi.accountsdk.utils.ObjectUtils;
import com.xiaomi.accountsdk.utils.ServerTimeUtil;
import com.xiaomi.passport.utils.HttpCookies;
import com.xiaomi.passport.utils.HttpHeaders;
import com.xiaomi.phonenum.utils.TraceId;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public final class SimpleRequest {
    private static final boolean DEBUG = false;
    public static final String HEADER_KEY_USER_AGENT = "User-Agent";
    public static final String LOCATION = "Location";
    private static final String NAME_VALUE_SEPARATOR = "=";
    private static final String PARAMETER_SEPARATOR = "&";
    private static final String TAG = "SimpleRequest";
    private static final int TIMEOUT = 30000;
    public static final String UTF8 = "utf-8";
    private static ConnectivityListener sConnectivityListener;
    private static CTANetworkBaseControl sCtaNetworkBaseControl = new CTANetworkBaseControl() { // from class: com.xiaomi.accountsdk.request.SimpleRequest.1
        @Override // com.xiaomi.accountsdk.request.intercept.CTANetworkBaseControl
        public boolean isCTANetworkEnable() {
            return true;
        }
    };
    private static HttpURLConnectionFactory sHttpURLConnectionFactory = new HttpURLConnectionFactory() { // from class: com.xiaomi.accountsdk.request.SimpleRequest.2
        @Override // com.xiaomi.accountsdk.request.SimpleRequest.HttpURLConnectionFactory
        public HttpURLConnection makeConn(URL url) throws IOException {
            if (SimpleRequest.sConnectivityListener != null) {
                SimpleRequest.sConnectivityListener.onOpenUrlConnection(url);
            }
            return (HttpURLConnection) url.openConnection();
        }
    };

    public interface HttpURLConnectionFactory {
        HttpURLConnection makeConn(URL url) throws IOException;
    }

    public static void setCtaNetworkBaseControl(CTANetworkBaseControl cTANetworkBaseControl) {
        sCtaNetworkBaseControl = cTANetworkBaseControl;
    }

    protected static void checkCTANetworkEnable() throws IOException {
        CTANetworkBaseControl cTANetworkBaseControl = sCtaNetworkBaseControl;
        if (cTANetworkBaseControl != null && !cTANetworkBaseControl.isCTANetworkEnable()) {
            throw new IOException("must pass XiaomiAccount CTA!!!!");
        }
    }

    public static void setConnectivityListener(ConnectivityListener connectivityListener) {
        sConnectivityListener = connectivityListener;
    }

    static void injectHttpURLConnectionFactoryForTest(HttpURLConnectionFactory httpURLConnectionFactory) {
        sHttpURLConnectionFactory = httpURLConnectionFactory;
    }

    static HttpURLConnectionFactory getHttpURLConnectionFactoryForTest() {
        return sHttpURLConnectionFactory;
    }

    private static String appendUrl(String str, Map<String, String> map) {
        if (str == null) {
            throw new NullPointerException("origin is not allowed null");
        }
        Uri.Builder builderBuildUpon = Uri.parse(str).buildUpon();
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                builderBuildUpon.appendQueryParameter(entry.getKey(), entry.getValue());
            }
        }
        return builderBuildUpon.build().toString();
    }

    public static StringContent getAsString(String str, Map<String, String> map, Map<String, String> map2, boolean z) throws AccessDeniedException, IOException, AuthenticationFailureException {
        return getAsString(str, map, null, map2, z, null);
    }

    public static StringContent getAsString(String str, Map<String, String> map, Map<String, String> map2, Map<String, String> map3, boolean z) throws AccessDeniedException, IOException, AuthenticationFailureException {
        return getAsString(str, map, map2, map3, z, null);
    }

    public static StringContent getAsString(String str, Map<String, String> map, Map<String, String> map2, Map<String, String> map3, boolean z, Integer num) throws AccessDeniedException, IOException, AuthenticationFailureException {
        return getAsString(str, map, map2, map3, z, num, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.xiaomi.accountsdk.request.intercept.NetworkInterceptCallback] */
    /* JADX WARN: Type inference failed for: r1v0, types: [com.xiaomi.accountsdk.request.RequestWithVersionCodeControl$VersionTagInfo] */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v28 */
    /* JADX WARN: Type inference failed for: r1v29 */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r1v8 */
    /* JADX WARN: Type inference failed for: r1v9 */
    /* JADX WARN: Type inference failed for: r4v1, types: [java.util.Map] */
    /* JADX WARN: Type inference failed for: r4v10 */
    /* JADX WARN: Type inference failed for: r4v11 */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v3, types: [long] */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r4v9 */
    /* JADX WARN: Type inference failed for: r9v1, types: [com.xiaomi.accountsdk.utils.AccountLogger$HttpRequestLog] */
    public static StringContent getAsString(String str, Map<String, String> map, Map<String, String> map2, Map<String, String> map3, boolean z, Integer num, RequestWithVersionCodeControl.VersionTagInfo versionTagInfo) throws Throwable {
        ?? r1;
        ?? r4;
        Exception exc;
        Integer num2;
        ?? r5;
        ?? r2;
        ?? r3 = versionTagInfo;
        checkCTANetworkEnable();
        ?? FillRequestHeadersWithVersionCode = RequestWithVersionCodeControl.fillRequestHeadersWithVersionCode(str, map2, r3);
        String strAppendUrl = appendUrl(str, map);
        String strAny = TraceId.any();
        long jCurrentTimeMillis = System.currentTimeMillis();
        NetworkInterceptor.get().onBegin(strAny, PassportSimpleRequest.HTTP_METHOD_GET, str);
        AccountLogger.HttpRequestLog httpRequestLogRequest = new AccountLogger.HttpRequestLog(strAppendUrl, PassportSimpleRequest.HTTP_METHOD_GET).request(FillRequestHeadersWithVersionCode, map3);
        HttpURLConnection httpURLConnection = null;
        numValueOf = null;
        numValueOf = null;
        Integer numValueOf = null;
        httpURLConnection = null;
        try {
            try {
                HttpURLConnection httpURLConnectionMakeConn = makeConn(strAppendUrl, map3, FillRequestHeadersWithVersionCode, num);
                try {
                    try {
                        if (httpURLConnectionMakeConn == null) {
                            throw new IOException("failed to create connection");
                        }
                        try {
                            httpURLConnectionMakeConn.setDoInput(true);
                            httpURLConnectionMakeConn.setRequestMethod(PassportSimpleRequest.HTTP_METHOD_GET);
                            httpURLConnectionMakeConn.connect();
                            try {
                                int responseCode = httpURLConnectionMakeConn.getResponseCode();
                                numValueOf = Integer.valueOf(responseCode);
                                if (ServerTimeUtil.getComputer() != null) {
                                    try {
                                        String headerField = httpURLConnectionMakeConn.getHeaderField("Date");
                                        if (headerField != null) {
                                            ServerTimeUtil.getComputer().alignWithServerDateHeader(str, headerField);
                                        }
                                    } catch (Exception e) {
                                        exc = e;
                                        r2 = strAny;
                                        r5 = jCurrentTimeMillis;
                                    }
                                }
                                if (responseCode != 200 && responseCode != 302) {
                                    if (responseCode == 403) {
                                        throw new AccessDeniedException(responseCode, "access denied, encrypt error or user is forbidden to access the resource");
                                    }
                                    if (responseCode != 401 && responseCode != 400) {
                                        if (responseCode == 301) {
                                            throw new IOException("http status error when GET: " + responseCode + "\nunexpected redirect from " + httpURLConnectionMakeConn.getURL().getHost() + " to " + httpURLConnectionMakeConn.getHeaderField("Location"));
                                        }
                                        throw new IOException("http status error when GET: " + responseCode);
                                    }
                                    AuthenticationFailureException authenticationFailureException = new AuthenticationFailureException(responseCode, "authentication failure for get, code: " + responseCode);
                                    authenticationFailureException.setWwwAuthenticateHeader(httpURLConnectionMakeConn.getHeaderField("WWW-Authenticate"));
                                    authenticationFailureException.setCaDisableSecondsHeader(httpURLConnectionMakeConn.getHeaderField("CA-DISABLE-SECONDS"));
                                    throw authenticationFailureException;
                                }
                                Map<String, List<String>> headerFields = httpURLConnectionMakeConn.getHeaderFields();
                                CookieManager cookieManager = new CookieManager();
                                URI uriCreate = URI.create(strAppendUrl);
                                cookieManager.put(uriCreate, headerFields);
                                List<HttpCookie> list = cookieManager.getCookieStore().get(uriCreate);
                                Map<String, String> cookies = parseCookies(list);
                                StringBuilder sb = new StringBuilder();
                                if (z) {
                                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnectionMakeConn.getInputStream()), 1024);
                                    while (true) {
                                        try {
                                            String line = bufferedReader.readLine();
                                            if (line == null) {
                                                break;
                                            }
                                            sb.append(line);
                                        } catch (Throwable th) {
                                            IOUtils.closeQuietly(bufferedReader);
                                            throw th;
                                        }
                                    }
                                    IOUtils.closeQuietly(bufferedReader);
                                }
                                String string = sb.toString();
                                StringContent stringContent = new StringContent(string);
                                stringContent.putCookies(cookies);
                                stringContent.putHeaders(ObjectUtils.listToMap(headerFields));
                                stringContent.setHttpCode(responseCode);
                                RequestWithVersionCodeControl.fillOrCacheRequestResult(str, stringContent, r3);
                                NetworkInterceptor.get().onSuccess(strAny, PassportSimpleRequest.HTTP_METHOD_GET, str, jCurrentTimeMillis, responseCode, string.length());
                                HttpCookies.set(list);
                                httpRequestLogRequest.result(responseCode, string, headerFields, list);
                                if (httpURLConnectionMakeConn != null) {
                                    httpURLConnectionMakeConn.disconnect();
                                }
                                AccountLogger.log(httpRequestLogRequest);
                                return stringContent;
                            } catch (Exception e2) {
                                e = e2;
                                r3 = strAny;
                                FillRequestHeadersWithVersionCode = jCurrentTimeMillis;
                                exc = e;
                                r2 = r3;
                                r5 = FillRequestHeadersWithVersionCode;
                            }
                        } catch (Exception e3) {
                            e = e3;
                            r3 = strAny;
                        }
                        num2 = numValueOf;
                        httpURLConnection = httpURLConnectionMakeConn;
                        r1 = r2;
                        r4 = r5;
                        NetworkInterceptor.get().onException(r1, PassportSimpleRequest.HTTP_METHOD_GET, str, r4, exc, num2);
                        httpRequestLogRequest.result(exc);
                        throw exc;
                    } catch (Throwable th2) {
                        th = th2;
                        httpURLConnection = httpURLConnectionMakeConn;
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                        AccountLogger.log(httpRequestLogRequest);
                        throw th;
                    }
                } catch (Exception e4) {
                    e = e4;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (Exception e5) {
            r1 = strAny;
            r4 = jCurrentTimeMillis;
            exc = e5;
            num2 = null;
        }
    }

    public static StreamContent getAsStream(String str, Map<String, String> map, Map<String, String> map2) throws AccessDeniedException, IOException, AuthenticationFailureException {
        return getAsStream(str, map, map2, null);
    }

    public static StreamContent getAsStream(String str, Map<String, String> map, Map<String, String> map2, Map<String, String> map3) throws AccessDeniedException, IOException, AuthenticationFailureException {
        String str2;
        Exception exc;
        Integer num;
        ProtocolException protocolException;
        Integer num2;
        checkCTANetworkEnable();
        String strAppendUrl = appendUrl(str, map);
        String strAny = TraceId.any();
        long jCurrentTimeMillis = System.currentTimeMillis();
        NetworkInterceptor.get().onBegin(strAny, PassportSimpleRequest.HTTP_METHOD_GET, str);
        AccountLogger.HttpRequestLog httpRequestLogRequest = new AccountLogger.HttpRequestLog(strAppendUrl, PassportSimpleRequest.HTTP_METHOD_GET).request(map3, map2);
        try {
            try {
                HttpURLConnection httpURLConnectionMakeConn = makeConn(strAppendUrl, map2, map3, null);
                if (httpURLConnectionMakeConn == null) {
                    str2 = str;
                    try {
                        throw new IOException("failed to create connection");
                    } catch (ProtocolException e) {
                        e = e;
                    } catch (Exception e2) {
                        e = e2;
                        exc = e;
                        num = null;
                        NetworkInterceptor.get().onException(strAny, PassportSimpleRequest.HTTP_METHOD_GET, str2, jCurrentTimeMillis, exc, num);
                        httpRequestLogRequest.result(exc);
                        throw exc;
                    }
                } else {
                    httpURLConnectionMakeConn.setDoInput(true);
                    httpURLConnectionMakeConn.setRequestMethod(PassportSimpleRequest.HTTP_METHOD_GET);
                    httpURLConnectionMakeConn.setInstanceFollowRedirects(true);
                    httpURLConnectionMakeConn.connect();
                    int responseCode = httpURLConnectionMakeConn.getResponseCode();
                    Integer numValueOf = Integer.valueOf(responseCode);
                    try {
                        if (responseCode != 200) {
                            if (responseCode == 403) {
                                throw new AccessDeniedException(responseCode, "access denied, encrypt error or user is forbidden to access the resource");
                            }
                            if (responseCode != 401 && responseCode != 400) {
                                if (responseCode == 301) {
                                    throw new IOException("http status error when GET: " + responseCode + "\nunexpected redirect from " + httpURLConnectionMakeConn.getURL().getHost() + " to " + httpURLConnectionMakeConn.getHeaderField("Location"));
                                }
                                throw new IOException("http status error when GET: " + responseCode);
                            }
                            AuthenticationFailureException authenticationFailureException = new AuthenticationFailureException(responseCode, "authentication failure for get, code: " + responseCode);
                            authenticationFailureException.setWwwAuthenticateHeader(httpURLConnectionMakeConn.getHeaderField("WWW-Authenticate"));
                            authenticationFailureException.setCaDisableSecondsHeader(httpURLConnectionMakeConn.getHeaderField("CA-DISABLE-SECONDS"));
                            throw authenticationFailureException;
                        }
                        try {
                            Map<String, List<String>> headerFields = httpURLConnectionMakeConn.getHeaderFields();
                            CookieManager cookieManager = new CookieManager();
                            URI uriCreate = URI.create(strAppendUrl);
                            cookieManager.put(uriCreate, headerFields);
                            List<HttpCookie> list = cookieManager.getCookieStore().get(uriCreate);
                            Map<String, String> cookies = parseCookies(list);
                            cookies.putAll(ObjectUtils.listToMap(headerFields));
                            StreamContent streamContent = new StreamContent(httpURLConnectionMakeConn.getInputStream());
                            streamContent.putHeaders(cookies);
                            NetworkInterceptor.get().onSuccess(strAny, PassportSimpleRequest.HTTP_METHOD_GET, str, jCurrentTimeMillis, responseCode, 0);
                            HttpCookies.set(list);
                            httpRequestLogRequest.result(responseCode, headerFields, list);
                            AccountLogger.log(httpRequestLogRequest);
                            return streamContent;
                        } catch (ProtocolException e3) {
                            e = e3;
                            str2 = str;
                            protocolException = e;
                            num2 = numValueOf;
                            NetworkInterceptor.get().onException(strAny, PassportSimpleRequest.HTTP_METHOD_GET, str2, jCurrentTimeMillis, protocolException, num2);
                            httpRequestLogRequest.result(protocolException);
                            throw new IOException("protocol error");
                        } catch (Exception e4) {
                            e = e4;
                            str2 = str;
                            exc = e;
                            num = numValueOf;
                            NetworkInterceptor.get().onException(strAny, PassportSimpleRequest.HTTP_METHOD_GET, str2, jCurrentTimeMillis, exc, num);
                            httpRequestLogRequest.result(exc);
                            throw exc;
                        }
                    } catch (ProtocolException e5) {
                        e = e5;
                    } catch (Exception e6) {
                        e = e6;
                    }
                }
            } catch (ProtocolException e7) {
                e = e7;
                str2 = str;
            } catch (Exception e8) {
                e = e8;
                str2 = str;
            }
        } catch (Throwable th) {
            AccountLogger.log(httpRequestLogRequest);
            throw th;
        }
        protocolException = e;
        num2 = null;
        NetworkInterceptor.get().onException(strAny, PassportSimpleRequest.HTTP_METHOD_GET, str2, jCurrentTimeMillis, protocolException, num2);
        httpRequestLogRequest.result(protocolException);
        throw new IOException("protocol error");
    }

    public static MapContent getAsMap(String str, Map<String, String> map, Map<String, String> map2, boolean z) throws AccessDeniedException, IOException, AuthenticationFailureException {
        return getAsMap(str, map, map2, null, z);
    }

    public static MapContent getAsMap(String str, Map<String, String> map, Map<String, String> map2, Map<String, String> map3, boolean z) throws AccessDeniedException, IOException, AuthenticationFailureException {
        return convertStringToMap(getAsString(str, map, map3, map2, z));
    }

    public static StringContent postAsString(String str, Map<String, String> map, Map<String, String> map2, boolean z) throws AccessDeniedException, IOException, AuthenticationFailureException {
        return postAsString(str, map, map2, null, null, z, null);
    }

    public static StringContent postAsString(String str, Map<String, String> map, Map<String, String> map2, Map<String, String> map3, boolean z, Integer num) throws AccessDeniedException, IOException, AuthenticationFailureException {
        return postAsString(str, map, map2, map3, null, z, num);
    }

    public static StringContent postAsString(String str, Map<String, String> map, Map<String, String> map2, Map<String, String> map3, Map<String, String> map4, boolean z) throws AccessDeniedException, IOException, AuthenticationFailureException {
        return postAsString(str, map, map2, map3, map4, z, null);
    }

    public static StringContent postAsString(String str, Map<String, String> map, Map<String, String> map2, Map<String, String> map3, Map<String, String> map4, boolean z, Integer num) throws AccessDeniedException, IOException, AuthenticationFailureException {
        return postAsString(str, map, map2, map3, map4, z, num, (RequestWithVersionCodeControl.VersionTagInfo) null);
    }

    public static StringContent postAsString(String str, Map<String, String> map, Map<String, String> map2, Map<String, String> map3, Map<String, String> map4, boolean z, Integer num, RequestWithVersionCodeControl.VersionTagInfo versionTagInfo) throws AccessDeniedException, IOException, AuthenticationFailureException {
        return postAsString(str, encodeParamsMap(map), map2, map3, map4, z, num, versionTagInfo);
    }

    public static StringContent postAsString(String str, String str2, Map<String, String> map, Map<String, String> map2, Map<String, String> map3, boolean z, Integer num, RequestWithVersionCodeControl.VersionTagInfo versionTagInfo) throws Throwable {
        HttpURLConnection httpURLConnection;
        String str3;
        long j;
        String str4;
        Exception exc;
        Integer num2;
        ProtocolException protocolException;
        Integer num3;
        Integer num4;
        checkCTANetworkEnable();
        Map<String, String> mapFillRequestHeadersWithVersionCode = RequestWithVersionCodeControl.fillRequestHeadersWithVersionCode(str, map2, versionTagInfo);
        String strAppendUrl = map3 != null ? appendUrl(str, map3) : str;
        String strAny = TraceId.any();
        long jCurrentTimeMillis = System.currentTimeMillis();
        NetworkInterceptor.get().onBegin(strAny, PassportSimpleRequest.HTTP_METHOD_POST, str);
        AccountLogger.HttpRequestLog httpRequestLogRequest = new AccountLogger.HttpRequestLog(strAppendUrl, PassportSimpleRequest.HTTP_METHOD_POST).request(mapFillRequestHeadersWithVersionCode, map);
        try {
            try {
                HttpURLConnection httpURLConnectionMakeConn = makeConn(strAppendUrl, map, mapFillRequestHeadersWithVersionCode, num);
                try {
                    if (httpURLConnectionMakeConn == null) {
                        str3 = strAny;
                        j = jCurrentTimeMillis;
                        str4 = PassportSimpleRequest.HTTP_METHOD_POST;
                        num4 = null;
                        try {
                            throw new IOException("failed to create URLConnection");
                        } catch (ProtocolException e) {
                            e = e;
                            protocolException = e;
                            num3 = num4;
                            NetworkInterceptor.get().onException(str3, str4, str, j, protocolException, num3);
                            httpRequestLogRequest.result(protocolException);
                            throw new IOException("protocol error");
                        } catch (Exception e2) {
                            e = e2;
                            exc = e;
                            num2 = num4;
                            NetworkInterceptor.get().onException(str3, str4, str, j, exc, num2);
                            httpRequestLogRequest.result(exc);
                            throw exc;
                        }
                    }
                    try {
                        httpURLConnectionMakeConn.setDoInput(true);
                        httpURLConnectionMakeConn.setDoOutput(true);
                        httpURLConnectionMakeConn.setRequestMethod(PassportSimpleRequest.HTTP_METHOD_POST);
                        httpURLConnectionMakeConn.connect();
                        if (!TextUtils.isEmpty(str2)) {
                            try {
                                httpRequestLogRequest.request(str2);
                                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(httpURLConnectionMakeConn.getOutputStream());
                                try {
                                    bufferedOutputStream.write(str2.getBytes("utf-8"));
                                    IOUtils.closeQuietly(bufferedOutputStream);
                                } catch (Throwable th) {
                                    IOUtils.closeQuietly(bufferedOutputStream);
                                    throw th;
                                }
                            } catch (ProtocolException e3) {
                                protocolException = e3;
                                str3 = strAny;
                                j = jCurrentTimeMillis;
                                str4 = PassportSimpleRequest.HTTP_METHOD_POST;
                                num3 = null;
                                NetworkInterceptor.get().onException(str3, str4, str, j, protocolException, num3);
                                httpRequestLogRequest.result(protocolException);
                                throw new IOException("protocol error");
                            } catch (Exception e4) {
                                exc = e4;
                                str3 = strAny;
                                j = jCurrentTimeMillis;
                                str4 = PassportSimpleRequest.HTTP_METHOD_POST;
                                num2 = null;
                                NetworkInterceptor.get().onException(str3, str4, str, j, exc, num2);
                                httpRequestLogRequest.result(exc);
                                throw exc;
                            }
                        }
                        int responseCode = httpURLConnectionMakeConn.getResponseCode();
                        Integer numValueOf = Integer.valueOf(responseCode);
                        if (responseCode != 200 && responseCode != 302) {
                            try {
                                if (responseCode == 403) {
                                    throw new AccessDeniedException(responseCode, "access denied, encrypt error or user is forbidden to access the resource");
                                }
                                if (responseCode != 401 && responseCode != 400) {
                                    if (responseCode == 301) {
                                        throw new IOException("http status error when POST: " + responseCode + "\nunexpected redirect from " + httpURLConnectionMakeConn.getURL().getHost() + " to " + httpURLConnectionMakeConn.getHeaderField("Location"));
                                    }
                                    throw new IOException("http status error when POST: " + responseCode);
                                }
                                AuthenticationFailureException authenticationFailureException = new AuthenticationFailureException(responseCode, "authentication failure for post, code: " + responseCode);
                                authenticationFailureException.setWwwAuthenticateHeader(httpURLConnectionMakeConn.getHeaderField("WWW-Authenticate"));
                                authenticationFailureException.setCaDisableSecondsHeader(httpURLConnectionMakeConn.getHeaderField("CA-DISABLE-SECONDS"));
                                throw authenticationFailureException;
                            } catch (ProtocolException e5) {
                                protocolException = e5;
                                str3 = strAny;
                                j = jCurrentTimeMillis;
                                str4 = PassportSimpleRequest.HTTP_METHOD_POST;
                                num3 = numValueOf;
                                NetworkInterceptor.get().onException(str3, str4, str, j, protocolException, num3);
                                httpRequestLogRequest.result(protocolException);
                                throw new IOException("protocol error");
                            } catch (Exception e6) {
                                exc = e6;
                                str3 = strAny;
                                j = jCurrentTimeMillis;
                                str4 = PassportSimpleRequest.HTTP_METHOD_POST;
                                num2 = numValueOf;
                                NetworkInterceptor.get().onException(str3, str4, str, j, exc, num2);
                                httpRequestLogRequest.result(exc);
                                throw exc;
                            }
                        }
                        try {
                            Map<String, List<String>> headerFields = httpURLConnectionMakeConn.getHeaderFields();
                            ArrayList arrayList = new ArrayList();
                            URI uriCreate = URI.create(strAppendUrl);
                            String host = uriCreate.getHost();
                            try {
                                final HashSet hashSet = new HashSet();
                                hashSet.add(host);
                                if (mapFillRequestHeadersWithVersionCode != null) {
                                    try {
                                        if (mapFillRequestHeadersWithVersionCode.containsKey("host")) {
                                            hashSet.add(mapFillRequestHeadersWithVersionCode.get("host"));
                                        }
                                    } catch (ProtocolException e7) {
                                        str3 = strAny;
                                        protocolException = e7;
                                        j = jCurrentTimeMillis;
                                        str4 = PassportSimpleRequest.HTTP_METHOD_POST;
                                        num3 = numValueOf;
                                        NetworkInterceptor.get().onException(str3, str4, str, j, protocolException, num3);
                                        httpRequestLogRequest.result(protocolException);
                                        throw new IOException("protocol error");
                                    } catch (Exception e8) {
                                        str3 = strAny;
                                        exc = e8;
                                        j = jCurrentTimeMillis;
                                        str4 = PassportSimpleRequest.HTTP_METHOD_POST;
                                        num2 = numValueOf;
                                        NetworkInterceptor.get().onException(str3, str4, str, j, exc, num2);
                                        httpRequestLogRequest.result(exc);
                                        throw exc;
                                    }
                                }
                                CookieManager cookieManager = new CookieManager(null, new CookiePolicy() { // from class: com.xiaomi.accountsdk.request.SimpleRequest.3
                                    @Override // java.net.CookiePolicy
                                    public boolean shouldAccept(URI uri, HttpCookie httpCookie) {
                                        String domain = httpCookie.getDomain();
                                        Iterator it = hashSet.iterator();
                                        while (it.hasNext()) {
                                            if (HttpCookie.domainMatches(domain, (String) it.next())) {
                                                return true;
                                            }
                                        }
                                        return false;
                                    }
                                });
                                cookieManager.put(uriCreate, headerFields);
                                HashMap map4 = new HashMap();
                                CookieStore cookieStore = cookieManager.getCookieStore();
                                Iterator it = hashSet.iterator();
                                while (it.hasNext()) {
                                    arrayList.addAll(cookieStore.get(URI.create(strAppendUrl.replaceFirst(host, (String) it.next()))));
                                }
                                Map<String, String> cookies = parseCookies(arrayList);
                                if (cookies != null) {
                                    map4.putAll(cookies);
                                }
                                StringBuilder sb = new StringBuilder();
                                if (z) {
                                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnectionMakeConn.getInputStream()), 1024);
                                    while (true) {
                                        try {
                                            String line = bufferedReader.readLine();
                                            if (line == null) {
                                                break;
                                            }
                                            sb.append(line);
                                        } catch (Throwable th2) {
                                            IOUtils.closeQuietly(bufferedReader);
                                            throw th2;
                                        }
                                    }
                                    IOUtils.closeQuietly(bufferedReader);
                                }
                                String string = sb.toString();
                                StringContent stringContent = new StringContent(string);
                                stringContent.putCookies(map4);
                                stringContent.setHttpCode(responseCode);
                                stringContent.putHeaders(ObjectUtils.listToMap(headerFields));
                                RequestWithVersionCodeControl.fillOrCacheRequestResult(strAppendUrl, stringContent, versionTagInfo);
                                NetworkInterceptCallback networkInterceptCallback = NetworkInterceptor.get();
                                int length = string.length();
                                j = jCurrentTimeMillis;
                                str4 = PassportSimpleRequest.HTTP_METHOD_POST;
                                str3 = strAny;
                                try {
                                    networkInterceptCallback.onSuccess(str3, str4, str, j, responseCode, length);
                                    HttpCookies.set(arrayList);
                                    httpRequestLogRequest.result(responseCode, string, headerFields, arrayList);
                                    if (httpURLConnectionMakeConn != null) {
                                        httpURLConnectionMakeConn.disconnect();
                                    }
                                    AccountLogger.log(httpRequestLogRequest);
                                    return stringContent;
                                } catch (ProtocolException e9) {
                                    e = e9;
                                    protocolException = e;
                                    num3 = numValueOf;
                                    NetworkInterceptor.get().onException(str3, str4, str, j, protocolException, num3);
                                    httpRequestLogRequest.result(protocolException);
                                    throw new IOException("protocol error");
                                } catch (Exception e10) {
                                    e = e10;
                                    exc = e;
                                    num2 = numValueOf;
                                    NetworkInterceptor.get().onException(str3, str4, str, j, exc, num2);
                                    httpRequestLogRequest.result(exc);
                                    throw exc;
                                }
                            } catch (ProtocolException e11) {
                                e = e11;
                                str3 = strAny;
                                j = jCurrentTimeMillis;
                                str4 = PassportSimpleRequest.HTTP_METHOD_POST;
                                protocolException = e;
                                num3 = numValueOf;
                                NetworkInterceptor.get().onException(str3, str4, str, j, protocolException, num3);
                                httpRequestLogRequest.result(protocolException);
                                throw new IOException("protocol error");
                            } catch (Exception e12) {
                                e = e12;
                                str3 = strAny;
                                j = jCurrentTimeMillis;
                                str4 = PassportSimpleRequest.HTTP_METHOD_POST;
                                exc = e;
                                num2 = numValueOf;
                                NetworkInterceptor.get().onException(str3, str4, str, j, exc, num2);
                                httpRequestLogRequest.result(exc);
                                throw exc;
                            }
                        } catch (ProtocolException e13) {
                            e = e13;
                            str3 = strAny;
                        } catch (Exception e14) {
                            e = e14;
                            str3 = strAny;
                        }
                    } catch (ProtocolException e15) {
                        e = e15;
                        str3 = strAny;
                        j = jCurrentTimeMillis;
                        str4 = PassportSimpleRequest.HTTP_METHOD_POST;
                        num4 = null;
                        protocolException = e;
                        num3 = num4;
                        NetworkInterceptor.get().onException(str3, str4, str, j, protocolException, num3);
                        httpRequestLogRequest.result(protocolException);
                        throw new IOException("protocol error");
                    } catch (Exception e16) {
                        e = e16;
                        str3 = strAny;
                        j = jCurrentTimeMillis;
                        str4 = PassportSimpleRequest.HTTP_METHOD_POST;
                        num4 = null;
                        exc = e;
                        num2 = num4;
                        NetworkInterceptor.get().onException(str3, str4, str, j, exc, num2);
                        httpRequestLogRequest.result(exc);
                        throw exc;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    httpURLConnection = httpURLConnectionMakeConn;
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    AccountLogger.log(httpRequestLogRequest);
                    throw th;
                }
            } catch (ProtocolException e17) {
                str3 = strAny;
                j = jCurrentTimeMillis;
                str4 = PassportSimpleRequest.HTTP_METHOD_POST;
                protocolException = e17;
                num3 = null;
            } catch (Exception e18) {
                str3 = strAny;
                j = jCurrentTimeMillis;
                str4 = PassportSimpleRequest.HTTP_METHOD_POST;
                exc = e18;
                num2 = null;
            } catch (Throwable th4) {
                th = th4;
                httpURLConnection = null;
            }
        } catch (Throwable th5) {
            th = th5;
        }
    }

    public static MapContent postAsMap(String str, Map<String, String> map, Map<String, String> map2, boolean z) throws AccessDeniedException, IOException, AuthenticationFailureException {
        return postAsMap(str, map, map2, null, z);
    }

    public static MapContent postAsMap(String str, Map<String, String> map, Map<String, String> map2, Map<String, String> map3, boolean z) throws AccessDeniedException, IOException, AuthenticationFailureException {
        return convertStringToMap(postAsString(str, map, map2, map3, (Map<String, String>) null, z));
    }

    protected static MapContent convertStringToMap(StringContent stringContent) {
        JSONObject jSONObject;
        if (stringContent == null) {
            return null;
        }
        try {
            jSONObject = new JSONObject(stringContent.getBody());
        } catch (JSONException e) {
            e.printStackTrace();
            jSONObject = null;
        }
        if (jSONObject == null) {
            return null;
        }
        MapContent mapContent = new MapContent(ObjectUtils.jsonToMap(jSONObject));
        mapContent.putHeaders(stringContent.getHeaders());
        return mapContent;
    }

    protected static HttpURLConnection makeConn(String str, Map<String, String> map, Map<String, String> map2, Integer num) {
        URL url;
        try {
            url = new URL(str);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            url = null;
        }
        if (url == null) {
            AccountLogger.log(TAG, "failed to init url");
            return null;
        }
        if (num == null) {
            num = 30000;
        }
        try {
            CookieHandler.setDefault(null);
            HttpURLConnection httpURLConnectionMakeConn = sHttpURLConnectionFactory.makeConn(url);
            httpURLConnectionMakeConn.setInstanceFollowRedirects(false);
            httpURLConnectionMakeConn.setConnectTimeout(num.intValue());
            httpURLConnectionMakeConn.setReadTimeout(num.intValue());
            httpURLConnectionMakeConn.setUseCaches(false);
            httpURLConnectionMakeConn.setRequestProperty("Content-Type", HttpHeaders.getContentType(map2, "application/x-www-form-urlencoded"));
            if ((map2 == null || TextUtils.isEmpty(map2.get("User-Agent"))) && !TextUtils.isEmpty(XMPassportSettings.getUserAgent())) {
                httpURLConnectionMakeConn.setRequestProperty("User-Agent", XMPassportSettings.getUserAgent());
            }
            if (map == null) {
                map = new EasyMap<>();
            }
            httpURLConnectionMakeConn.setRequestProperty("Cookie", joinMap(map, "; "));
            if (map2 != null) {
                for (String str2 : map2.keySet()) {
                    httpURLConnectionMakeConn.setRequestProperty(str2, map2.get(str2));
                }
            }
            return httpURLConnectionMakeConn;
        } catch (Exception e2) {
            AccountLogger.log(TAG, e2);
            return null;
        }
    }

    public static Map<String, String> decodeParamsMap(String str) {
        HashMap map = new HashMap();
        if (!TextUtils.isEmpty(str)) {
            for (String str2 : str.split(PARAMETER_SEPARATOR)) {
                String[] strArrSplit = str2.split(NAME_VALUE_SEPARATOR);
                if (strArrSplit.length == 2) {
                    map.put(decode(strArrSplit[0]), decode(strArrSplit[1]));
                }
            }
        }
        return map;
    }

    public static String encodeParamsMap(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append(PARAMETER_SEPARATOR);
            }
            String strEncode = encode(entry.getKey());
            String strEncode2 = !TextUtils.isEmpty(entry.getValue()) ? encode(entry.getValue()) : "";
            sb.append(strEncode);
            sb.append(NAME_VALUE_SEPARATOR);
            sb.append(strEncode2);
        }
        return sb.toString();
    }

    private static String encode(String str) {
        try {
            return URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static String decode(String str) {
        try {
            return URLDecoder.decode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    protected static String joinMap(Map<String, String> map, String str) {
        if (map == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append(str);
            }
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key);
            sb.append(NAME_VALUE_SEPARATOR);
            sb.append(value);
        }
        return sb.toString();
    }

    protected static Map<String, String> parseCookies(List<HttpCookie> list) {
        HashMap map = new HashMap();
        for (HttpCookie httpCookie : list) {
            if (!httpCookie.hasExpired()) {
                String name = httpCookie.getName();
                String value = httpCookie.getValue();
                if (name != null) {
                    map.put(name, value);
                }
            }
        }
        return map;
    }

    public static class HeaderContent {
        private int httpCode = -1;
        private final Set<String> cookieKeys = new HashSet();
        private final Map<String, String> headers = new HashMap();

        public void putHeader(String str, String str2) {
            this.headers.put(str, str2);
        }

        public String getHeader(String str) {
            return this.headers.get(str);
        }

        public Map<String, String> getHeaders() {
            return this.headers;
        }

        public void putHeaders(Map<String, String> map) {
            this.headers.putAll(map);
        }

        public void putCookies(Map<String, String> map) {
            putHeaders(map);
            if (map != null) {
                this.cookieKeys.addAll(map.keySet());
            }
        }

        public Set<String> getCookieKeys() {
            return this.cookieKeys;
        }

        public int getHttpCode() {
            return this.httpCode;
        }

        public void setHttpCode(int i) {
            this.httpCode = i;
        }

        public String toString() {
            return "HeaderContent{headers=" + this.headers + CoreConstants.CURLY_RIGHT;
        }
    }

    public static class StringContent extends HeaderContent {
        private String body;

        public StringContent(String str) {
            this.body = str;
        }

        public String getBody() {
            return this.body;
        }

        void setBody(String str) {
            this.body = str;
        }

        @Override // com.xiaomi.accountsdk.request.SimpleRequest.HeaderContent
        public String toString() {
            return "StringContent{body='" + this.body + "'}";
        }
    }

    public static class MapContent extends HeaderContent {
        private Map<String, Object> bodies;

        public MapContent(Map<String, Object> map) {
            this.bodies = map;
        }

        public Object getFromBody(String str) {
            return this.bodies.get(str);
        }

        public Map<String, Object> getBodies() {
            return this.bodies;
        }

        @Override // com.xiaomi.accountsdk.request.SimpleRequest.HeaderContent
        public String toString() {
            return "MapContent{bodies=" + this.bodies + CoreConstants.CURLY_RIGHT;
        }
    }

    public static class StreamContent extends HeaderContent {
        private InputStream stream;

        public StreamContent(InputStream inputStream) {
            this.stream = inputStream;
        }

        public InputStream getStream() {
            return this.stream;
        }

        public void closeStream() {
            IOUtils.closeQuietly(this.stream);
        }
    }
}
