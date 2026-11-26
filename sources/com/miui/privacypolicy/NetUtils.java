package com.miui.privacypolicy;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import ch.qos.logback.classic.spi.CallerData;
import com.xiaomi.accountsdk.request.PassportSimpleRequest;
import com.xiaomi.mirror.synergy.CallMethod;
import com.xiaomi.passport.utils.HttpHeaders;
import defpackage.R2;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import kotlin.text.Typography;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class NetUtils {
    public static final String BASE_URL = "https://data.sec.miui.com";
    private static final boolean DEBUG = false;
    private static final String ENCODE_UTF_8 = "UTF-8";
    private static final String SALT = "2dcd9s0c-ad3f-2fas-0l3a-abzo301jd0s9";
    private static final String TAG = "Privacy_NetUtil";
    private static final String UNKNOWN_STR = "unknown";
    protected static final String MIUI_VERSION_NAME = getSystemProperty("ro.miui.ui.version.name", "unknown");
    protected static final boolean IS_INTERNATIONAL_BUILD = getSystemProperty("ro.product.mod_device", "").contains("_global");

    protected enum HttpMethod {
        GET,
        POST,
        PUT,
        DELETE
    }

    private NetUtils() {
    }

    protected static String request(Map<String, String> map, String str, HttpMethod httpMethod, JSONObject jSONObject) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream;
        InputStream inputStream = null;
        try {
            if (httpMethod == HttpMethod.GET && map != null) {
                String strEncodeParameters = encodeParameters(map);
                str = str.contains(CallerData.NA) ? str.concat(strEncodeParameters) : str.concat(CallerData.NA).concat(strEncodeParameters);
            }
            HttpURLConnection httpURLConnectionOpenConnection = openConnection(new URL(str));
            setConnectionParametersForRequest(httpURLConnectionOpenConnection, httpMethod, map, jSONObject);
            if (httpURLConnectionOpenConnection.getResponseCode() != 200) {
                FileUtils.closeQuietly((InputStream) null);
                FileUtils.closeQuietly((OutputStream) null);
                return "";
            }
            InputStream inputStream2 = httpURLConnectionOpenConnection.getInputStream();
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    byte[] bArr = new byte[4096];
                    while (true) {
                        int i = inputStream2.read(bArr);
                        if (i == -1) {
                            String string = byteArrayOutputStream.toString();
                            FileUtils.closeQuietly(inputStream2);
                            FileUtils.closeQuietly(byteArrayOutputStream);
                            return string;
                        }
                        byteArrayOutputStream.write(bArr, 0, i);
                    }
                } catch (Exception e) {
                    inputStream = inputStream2;
                    e = e;
                } catch (Throwable th) {
                    inputStream = inputStream2;
                    th = th;
                    FileUtils.closeQuietly(inputStream);
                    FileUtils.closeQuietly(byteArrayOutputStream);
                    throw th;
                }
            } catch (Exception e2) {
                inputStream = inputStream2;
                e = e2;
                byteArrayOutputStream = null;
            } catch (Throwable th2) {
                inputStream = inputStream2;
                th = th2;
                byteArrayOutputStream = null;
            }
        } catch (Exception e3) {
            e = e3;
            byteArrayOutputStream = null;
        } catch (Throwable th3) {
            th = th3;
            byteArrayOutputStream = null;
        }
        try {
            e.printStackTrace();
            FileUtils.closeQuietly(inputStream);
            FileUtils.closeQuietly(byteArrayOutputStream);
            return "";
        } catch (Throwable th4) {
            th = th4;
            FileUtils.closeQuietly(inputStream);
            FileUtils.closeQuietly(byteArrayOutputStream);
            throw th;
        }
    }

    private static HttpURLConnection openConnection(URL url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setConnectTimeout(R2.layout.passport_fragment_phone_account_login);
        httpURLConnection.setReadTimeout(R2.layout.passport_fragment_phone_account_login);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setDoInput(true);
        return httpURLConnection;
    }

    private static void setConnectionParametersForRequest(HttpURLConnection httpURLConnection, HttpMethod httpMethod, Map<String, String> map, JSONObject jSONObject) throws IOException {
        httpURLConnection.addRequestProperty("sign", getParamsSignature(map, SALT));
        httpURLConnection.addRequestProperty("timestamp", map.get("timestamp"));
        httpURLConnection.addRequestProperty("source", "sdk");
        int i = AnonymousClass1.$SwitchMap$com$miui$privacypolicy$NetUtils$HttpMethod[httpMethod.ordinal()];
        if (i == 1) {
            httpURLConnection.setRequestMethod(PassportSimpleRequest.HTTP_METHOD_GET);
            return;
        }
        if (i == 2) {
            httpURLConnection.setRequestMethod("DELETE");
            return;
        }
        if (i == 3) {
            httpURLConnection.setRequestMethod(PassportSimpleRequest.HTTP_METHOD_POST);
            addBodyIfExists(httpURLConnection, jSONObject);
        } else {
            if (i == 4) {
                httpURLConnection.setRequestMethod("PUT");
                return;
            }
            throw new IllegalStateException("Unknown method type.");
        }
    }

    /* JADX INFO: renamed from: com.miui.privacypolicy.NetUtils$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$miui$privacypolicy$NetUtils$HttpMethod;

        static {
            int[] iArr = new int[HttpMethod.values().length];
            $SwitchMap$com$miui$privacypolicy$NetUtils$HttpMethod = iArr;
            try {
                iArr[HttpMethod.GET.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$privacypolicy$NetUtils$HttpMethod[HttpMethod.DELETE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$privacypolicy$NetUtils$HttpMethod[HttpMethod.POST.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$privacypolicy$NetUtils$HttpMethod[HttpMethod.PUT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private static void addBodyIfExists(HttpURLConnection httpURLConnection, JSONObject jSONObject) throws IOException {
        String string = jSONObject.toString();
        if (TextUtils.isEmpty(string)) {
            return;
        }
        httpURLConnection.setDoOutput(true);
        httpURLConnection.addRequestProperty("Content-Type", HttpHeaders.CONTENT_TYPE_JSON);
        DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
        dataOutputStream.writeBytes(string);
        dataOutputStream.flush();
        dataOutputStream.close();
    }

    private static String encodeParameters(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        try {
            boolean z = true;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (!z) {
                    sb.append(Typography.amp);
                }
                sb.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                sb.append('=');
                sb.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                z = false;
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Encoding not supported: " + ((Object) sb), e);
        }
    }

    private static String getParamsSignature(Map<String, String> map, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            arrayList.add(new NameValuePair(entry.getKey(), entry.getValue()));
        }
        Collections.sort(arrayList);
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            NameValuePair nameValuePair = (NameValuePair) arrayList.get(i);
            sb.append(nameValuePair.name).append("=").append(nameValuePair.value);
        }
        sb.append(str);
        return getMd5Digest(new String(Base64.encodeToString(getBytes(sb.toString()), 2))).toUpperCase();
    }

    private static byte[] getBytes(String str) {
        try {
            return str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException unused) {
            return str.getBytes();
        }
    }

    private static String getMd5Digest(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(getBytes(str));
            return String.format("%1$032X", new BigInteger(1, messageDigest.digest()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    protected static class NameValuePair implements Comparable<NameValuePair> {
        private String name;
        private String value;

        NameValuePair(String str, String str2) {
            this.name = str;
            this.value = str2;
        }

        @Override // java.lang.Comparable
        public int compareTo(NameValuePair nameValuePair) {
            return this.name.compareTo(nameValuePair.name);
        }
    }

    protected static String getSystemProperty(String str, String str2) {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod(CallMethod.METHOD_GET, String.class, String.class).invoke(cls, str, str2);
        } catch (Exception e) {
            Log.e(TAG, "getSystemProperty error, ", e);
            return str2;
        }
    }
}
