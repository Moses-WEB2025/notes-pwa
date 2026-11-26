package com.xiaomi.micloudsdk.request.utils;

import android.text.TextUtils;
import android.util.Log;
import ch.qos.logback.classic.spi.CallerData;
import com.xiaomi.micloudsdk.data.ExtendedAuthToken;
import com.xiaomi.micloudsdk.request.CloudHttpClient;
import com.xiaomi.micloudsdk.utils.CloudCoder;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class HttpUtils {
    private static boolean DBG = false;
    public static final int HTTP_REQUEST_TIMEOUT_MS = 30000;
    private static final String TAG = "HttpUtils";

    public enum HttpMethod {
        GET,
        POST,
        POST_JSON
    }

    public static HttpClient getHttpClient() {
        CloudHttpClient cloudHttpClientNewInstance = CloudHttpClient.newInstance();
        HttpParams params = cloudHttpClientNewInstance.getParams();
        HttpConnectionParams.setConnectionTimeout(params, 30000);
        HttpConnectionParams.setSoTimeout(params, 30000);
        ConnManagerParams.setTimeout(params, 30000L);
        return cloudHttpClientNewInstance;
    }

    public static Header getCookies(String str, ExtendedAuthToken extendedAuthToken) {
        return getCookies(str, null, extendedAuthToken);
    }

    public static Header getCookies(String str, String str2, ExtendedAuthToken extendedAuthToken) {
        StringBuilder sb = new StringBuilder("serviceToken=");
        sb.append(extendedAuthToken.authToken);
        if (!TextUtils.isEmpty(str2)) {
            sb.append("; cUserId=");
            sb.append(str2);
        }
        return new BasicHeader("Cookie", sb.toString());
    }

    public static String appendUrl(String str, List<NameValuePair> list) throws BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        if (str == null) {
            throw new NullPointerException("origin is not allowed null");
        }
        StringBuilder sb = new StringBuilder(str);
        if (!str.contains(CallerData.NA)) {
            sb.append(CallerData.NA);
        }
        for (int i = 0; i < list.size(); i++) {
            if (i != 0) {
                sb.append("&");
            }
            String name = list.get(i).getName();
            String value = list.get(i).getValue();
            sb.append(name);
            sb.append("=");
            sb.append(URLEncoder.encode(value, "UTF-8"));
        }
        String string = sb.toString();
        if (DBG) {
            Log.d(TAG, "result url length is: " + string.length());
        }
        return string;
    }

    public static String getSignature(HttpMethod httpMethod, String str, ArrayList<NameValuePair> arrayList, String str2) throws UnsupportedEncodingException {
        TreeMap treeMap = new TreeMap();
        if (arrayList != null) {
            for (NameValuePair nameValuePair : arrayList) {
                treeMap.put(nameValuePair.getName(), nameValuePair.getValue());
            }
        }
        return CloudCoder.generateSignature(httpMethod.name(), str, treeMap, str2);
    }

    public static boolean isHttpStatusCodeOKButCloudResponseCodeError(int i, String str) {
        int iOptInt = -1;
        if (str == null) {
            str = "";
        }
        try {
            iOptInt = new JSONObject(str).optInt("code", -1);
        } catch (JSONException e) {
            Log.e(TAG, "isHttpStatusCodeOKButCloudResponseCodeError: ", e);
        }
        return i == 200 && iOptInt != 0;
    }
}
