package com.xiaomi.accountsdk.utils;

import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.xiaomi.accountsdk.account.XMPassport;
import com.xiaomi.accountsdk.request.SimpleRequest;
import com.xiaomi.verificationsdk.internal.Constants;
import java.net.HttpCookie;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public abstract class AccountLogger {
    private static final Logger sAndroidBase64Logger;
    private static volatile Logger sInstance;

    public interface Logger {
        void log(HttpRequestLog httpRequestLog);

        void log(String str, String str2);

        void log(String str, String str2, Throwable th);

        void log(String str, Throwable th);
    }

    public static class HttpRequestLog {
        private static final String LOG_KEY_BODY = "@body";
        private static final String LOG_KEY_COOKIES = "@cookies";
        private static final String LOG_KEY_ERROR = "@error";
        private static final String LOG_KEY_HEADERS = "@headers";
        private static final String LOG_KEY_HTTP_CODE = "@httpCode";
        private static final String LOG_KEY_PARAMS = "@params";
        private static final String LOG_KEY_REQUEST = "#request#";
        private static final String LOG_KEY_RESPONSE = "#response#";
        private static final String LOG_KEY_TIMECOST = "@timecost";
        private static final String LOG_KEY_URL = "#url#";
        public static final String REQUEST_LOG_TAG = "AccountRequest";
        private String method;
        private JSONObject requestBody;
        private Map<String, String> requestCookies;
        private Map<String, String> requestHeaders;
        private Map<String, String> requestParams;
        private long requestTimeCostMs;
        private long requestTsMs;
        private JSONObject responseBody;
        private int responseCode = Integer.MIN_VALUE;
        private Map<String, String> responseCookies;
        private Throwable responseError;
        private Map<String, String> responseHeaders;
        private String url;

        public HttpRequestLog(String str, String str2) {
            this.url = str;
            this.method = str2;
        }

        public HttpRequestLog request(Map<String, String> map, Map<String, String> map2) {
            this.requestTsMs = SystemClock.elapsedRealtime();
            if (map == null) {
                map = new HashMap<>();
            }
            this.requestHeaders = new HashMap(map);
            if (map2 == null) {
                map2 = new HashMap<>();
            }
            this.requestCookies = new HashMap(map2);
            this.requestTsMs = SystemClock.elapsedRealtime();
            return this;
        }

        public HttpRequestLog request(String str) {
            this.requestBody = AccountLogger.maskPrivacyAsJSON(str);
            this.requestTsMs = SystemClock.elapsedRealtime();
            return this;
        }

        public HttpRequestLog request(Map<String, String> map) {
            this.requestParams = map;
            this.requestTsMs = SystemClock.elapsedRealtime();
            return this;
        }

        public HttpRequestLog result(int i, Map<String, List<String>> map, List<HttpCookie> list) {
            return result(i, "[no string body]", map, list);
        }

        public HttpRequestLog result(int i, String str, Map<String, List<String>> map, List<HttpCookie> list) {
            this.responseCode = i;
            this.responseBody = AccountLogger.maskPrivacyAsJSON(str);
            this.responseHeaders = new HashMap();
            if (map != null && !map.isEmpty()) {
                for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                    String key = entry.getKey();
                    if (!TextUtils.isEmpty(key)) {
                        List<String> value = entry.getValue();
                        if (!"Set-Cookie".equalsIgnoreCase(key)) {
                            if (value.size() == 1) {
                                this.responseHeaders.put(key, value.get(0));
                            } else {
                                for (String str2 : value) {
                                    this.responseHeaders.put(String.format("%s(%s)", key, Integer.toHexString(str2.hashCode())), str2);
                                }
                            }
                        }
                    }
                }
            }
            this.responseCookies = new HashMap();
            if (list != null && !list.isEmpty()) {
                for (HttpCookie httpCookie : list) {
                    this.responseCookies.put(httpCookie.getName(), httpCookie.getValue());
                }
            }
            this.requestTimeCostMs = SystemClock.elapsedRealtime() - this.requestTsMs;
            return this;
        }

        public HttpRequestLog result(Throwable th) {
            this.responseError = th;
            this.requestTimeCostMs = SystemClock.elapsedRealtime() - this.requestTsMs;
            return this;
        }

        private Object getMapLog(Map<String, String> map) throws JSONException {
            if (map == null || map.isEmpty()) {
                return "[empty]";
            }
            JSONObject jSONObject = new JSONObject();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                if (key == null) {
                    key = "[null]";
                }
                String value = entry.getValue();
                if (AccountLogger.needMaskPrivacy(key)) {
                    value = AccountLogger.getMaskedPrivacyData(value);
                }
                jSONObject.put(key, value);
            }
            return jSONObject;
        }

        private JSONObject makeRequestLog() throws JSONException {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(LOG_KEY_HEADERS, getMapLog(this.requestHeaders));
            jSONObject.put(LOG_KEY_COOKIES, getMapLog(this.requestCookies));
            jSONObject.put(LOG_KEY_PARAMS, getMapLog(this.requestParams));
            Object obj = this.requestBody;
            if (obj == null) {
                obj = "[empty]";
            }
            jSONObject.put(LOG_KEY_BODY, obj);
            return jSONObject;
        }

        private Object makeResponseLog() throws JSONException {
            if (this.responseError == null && this.responseCode == Integer.MIN_VALUE) {
                return "[no response]";
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(LOG_KEY_TIMECOST, this.requestTimeCostMs);
            Throwable th = this.responseError;
            if (th != null) {
                if (th instanceof UnknownHostException) {
                    jSONObject.put(LOG_KEY_ERROR, "special http exception: " + ((UnknownHostException) th).getMessage());
                    return jSONObject;
                }
                jSONObject.put(LOG_KEY_ERROR, Log.getStackTraceString(th));
                return jSONObject;
            }
            jSONObject.put(LOG_KEY_HTTP_CODE, this.responseCode);
            jSONObject.put(LOG_KEY_HEADERS, getMapLog(this.responseHeaders));
            jSONObject.put(LOG_KEY_COOKIES, getMapLog(this.responseCookies));
            Object obj = this.responseBody;
            if (obj == null) {
                obj = "[empty]";
            }
            jSONObject.put(LOG_KEY_BODY, obj);
            return jSONObject;
        }

        public String toString() {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(LOG_KEY_URL, String.format("%s: %s", this.method.toUpperCase(), this.url));
                jSONObject.put(LOG_KEY_REQUEST, makeRequestLog());
                jSONObject.put(LOG_KEY_RESPONSE, makeResponseLog());
                return jSONObject.toString();
            } catch (JSONException e) {
                throw new IllegalStateException("should never happen", e);
            }
        }
    }

    static {
        Logger logger = new Logger() { // from class: com.xiaomi.accountsdk.utils.AccountLogger.1
            @Override // com.xiaomi.accountsdk.utils.AccountLogger.Logger
            public void log(String str, String str2) {
                Log.e(str, AccountLogger.encryptWithBase64(str2));
            }

            @Override // com.xiaomi.accountsdk.utils.AccountLogger.Logger
            public void log(String str, String str2, Throwable th) {
                Log.e(str, AccountLogger.encryptWithBase64(str2 + StringUtils.LF + Log.getStackTraceString(th)));
            }

            @Override // com.xiaomi.accountsdk.utils.AccountLogger.Logger
            public void log(String str, Throwable th) {
                Log.e(str, AccountLogger.encryptWithBase64(Log.getStackTraceString(th)));
            }

            @Override // com.xiaomi.accountsdk.utils.AccountLogger.Logger
            public void log(HttpRequestLog httpRequestLog) {
                Log.e(HttpRequestLog.REQUEST_LOG_TAG, AccountLogger.encryptWithBase64(httpRequestLog.toString()));
            }
        };
        sAndroidBase64Logger = logger;
        sInstance = logger;
    }

    public static void setInstance(Logger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("log == null");
        }
        sInstance = logger;
    }

    public static void log(String str, String str2) {
        sInstance.log(str, str2);
    }

    public static void log(String str, String str2, Throwable th) {
        sInstance.log(str, str2, th);
    }

    public static void log(String str, Throwable th) {
        sInstance.log(str, th);
    }

    public static void log(HttpRequestLog httpRequestLog) {
        sInstance.log(httpRequestLog);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String encryptWithBase64(String str) {
        if (TextUtils.isEmpty(str)) {
            return "[empty]";
        }
        return String.format("PassportLog@A2^6C[%s]", Base64.encodeToString(str.getBytes(), 2));
    }

    public static String getMaskedPrivacyData(String str) {
        if (TextUtils.isEmpty(str) || str.trim().length() == 0) {
            return "[empty]";
        }
        return String.format("[@L%d:H%s]", Integer.valueOf(str.length()), Integer.toHexString(str.hashCode()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean needMaskPrivacy(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String lowerCase = str.toLowerCase();
        return lowerCase.contains(Constants.VERIFY_SUCESS_TOKEN) || lowerCase.contains("ticket") || lowerCase.contains("security") || lowerCase.contains("password");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static JSONObject maskPrivacyAsJSON(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (str.startsWith(XMPassport.PASSPORT_SAFE_PREFIX)) {
            str = str.substring(XMPassport.PASSPORT_SAFE_PREFIX.length());
        }
        JSONObject jSONObject = new JSONObject();
        try {
            try {
                try {
                    jSONObject.put("jsonObject", maskPrivacy(new JSONObject(str)));
                    return jSONObject;
                } catch (JSONException unused) {
                    Map<String, String> mapDecodeParamsMap = SimpleRequest.decodeParamsMap(str);
                    if (mapDecodeParamsMap.isEmpty()) {
                        jSONObject.put("plainText", str);
                    } else {
                        jSONObject.put("keyValue", maskPrivacy(mapDecodeParamsMap));
                    }
                    return jSONObject;
                }
            } catch (JSONException unused2) {
                JSONArray jSONArray = new JSONArray(str);
                JSONArray jSONArray2 = new JSONArray();
                for (int i = 0; i < jSONArray.length(); i++) {
                    Object obj = jSONArray.get(i);
                    if (obj instanceof JSONObject) {
                        jSONArray2.put(maskPrivacy((JSONObject) obj));
                    } else {
                        jSONArray2.put(String.valueOf(obj));
                    }
                }
                jSONObject.put("jsonArray", jSONArray2);
                return jSONObject;
            }
        } catch (JSONException e) {
            throw new IllegalStateException("should never happen", e);
        }
    }

    private static JSONObject maskPrivacy(JSONObject jSONObject) {
        JSONObject jSONObject2 = new JSONObject();
        try {
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                Object obj = jSONObject.get(next);
                if (obj instanceof JSONObject) {
                    jSONObject2.put(next, maskPrivacy((JSONObject) obj));
                } else if (obj instanceof JSONArray) {
                    if (needMaskPrivacy(next)) {
                        jSONObject2.put(next, String.format("[privacy data length=%d]", Integer.valueOf(((JSONArray) obj).length())));
                    } else {
                        jSONObject2.put(next, obj);
                    }
                } else if (needMaskPrivacy(next)) {
                    jSONObject2.put(next, "[privacy data]");
                } else {
                    jSONObject2.put(next, jSONObject.get(next));
                }
            }
            return jSONObject2;
        } catch (JSONException e) {
            throw new IllegalStateException("should never happen", e);
        }
    }

    private static JSONObject maskPrivacy(Map<String, String> map) {
        JSONObject jSONObject = new JSONObject();
        try {
            for (String str : map.keySet()) {
                if (needMaskPrivacy(str)) {
                    jSONObject.put(str, getMaskedPrivacyData(map.get(str)));
                } else {
                    jSONObject.put(str, map.get(str));
                }
            }
            return jSONObject;
        } catch (JSONException e) {
            throw new IllegalStateException("should never happen", e);
        }
    }
}
