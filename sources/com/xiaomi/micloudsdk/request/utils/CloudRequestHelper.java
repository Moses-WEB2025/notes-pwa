package com.xiaomi.micloudsdk.request.utils;

import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.accountsdk.request.PassportSimpleRequest;
import com.xiaomi.micloudsdk.exception.CipherException;
import com.xiaomi.micloudsdk.exception.CloudServerException;
import com.xiaomi.micloudsdk.stat.GetDownloadFileUrlFailedStatParam;
import com.xiaomi.micloudsdk.stat.MiCloudNetEventStatInjector;
import com.xiaomi.micloudsdk.stat.MiCloudStatManager;
import com.xiaomi.micloudsdk.stat.NetFailedStatParam;
import com.xiaomi.micloudsdk.stat.NetSuccessStatParam;
import com.xiaomi.micloudsdk.utils.AESCoder;
import com.xiaomi.micloudsdk.utils.CloudCoder;
import com.xiaomi.micloudsdk.utils.CryptCoder;
import com.xiaomi.micloudsdk.utils.MiCloudConstants;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

/* JADX INFO: loaded from: classes4.dex */
public class CloudRequestHelper {
    private static final String ENCODING_GZIP = "gzip";
    private static final String HEADER_ACCEPT = "Accept";
    private static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    private static final int MAX_FILTER_TAG_COUNT = 100;
    public static final String MICLOUD_TAG = "Micloud";
    private static final String PARAM_SYNC_FILTER_TAG = "filterTag";
    private static final String TAG = "CloudRequestHelper";

    public static String httpPostRequestWithDecodeData(String str, UrlEncodedFormEntity urlEncodedFormEntity, Header header, CryptCoder cryptCoder) throws CloudServerException, IOException {
        return httpPostRequestWithDecodeData(str, urlEncodedFormEntity, header, new ArrayList(), cryptCoder);
    }

    public static String httpPostRequestWithDecodeData(String str, UrlEncodedFormEntity urlEncodedFormEntity, Header header, List<Header> list, CryptCoder cryptCoder) throws CloudServerException, IOException {
        String strHttpPostRequest = httpPostRequest(str, urlEncodedFormEntity, urlEncodedFormEntity.getContentType().getValue(), header, list, cryptCoder, 0);
        if (Log.isLoggable("Micloud", 3)) {
            Log.d("Micloud", strHttpPostRequest);
        }
        return strHttpPostRequest;
    }

    public static String httpPostJSONRequestWithDecodeData(String str, StringEntity stringEntity, Header header, CryptCoder cryptCoder) throws CloudServerException, IOException {
        return httpPostJSONRequestWithDecodeData(str, stringEntity, header, new ArrayList(), cryptCoder);
    }

    public static String httpPostJSONRequestWithDecodeData(String str, StringEntity stringEntity, Header header, List<Header> list, CryptCoder cryptCoder) throws CloudServerException, IOException {
        String strHttpPostRequest = httpPostRequest(str, stringEntity, "application/json", header, list, cryptCoder, 0);
        if (Log.isLoggable("Micloud", 3)) {
            Log.d("Micloud", strHttpPostRequest);
        }
        return strHttpPostRequest;
    }

    private static String httpPostRequest(String str, HttpEntity httpEntity, String str2, Header header, List<Header> list, CryptCoder cryptCoder, int i) throws CloudServerException, IOException {
        HttpPost httpPost = new HttpPost(str);
        if (httpEntity != null) {
            httpPost.setEntity(httpEntity);
        }
        addHttpRequestHeaders(httpPost, str2, header, list, i);
        if (Log.isLoggable("Micloud", 3)) {
            Log.d("Micloud", "http post url : " + str);
            Log.d("Micloud", "http post cookies : " + (header != null ? header.toString() : "null"));
            Log.d("Micloud", "http post additionalHeaders : " + list);
        }
        HttpResponse httpResponseExcuteInternal = excuteInternal(httpPost);
        int statusCode = httpResponseExcuteInternal.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            try {
                String strDecrypt = cryptCoder.decrypt(decodeGZipToString(httpResponseExcuteInternal));
                String strCheckRedirect = CloudRelocationUtils.checkRedirect(strDecrypt, i);
                return !TextUtils.isEmpty(strCheckRedirect) ? httpPostRequest(strCheckRedirect, httpEntity, str2, header, list, cryptCoder, i + 1) : strDecrypt;
            } catch (CipherException e) {
                Log.e("Micloud", "MiCloudServerException", e);
                throw new CloudServerException(0, httpResponseExcuteInternal);
            }
        }
        if (CloudServerException.isMiCloudServerException(statusCode)) {
            Log.e("Micloud", "MiCloudServerException: " + statusCode + StringUtils.SPACE + httpResponseExcuteInternal.getStatusLine());
            throw new CloudServerException(statusCode, httpResponseExcuteInternal);
        }
        Log.e("Micloud", "Server error: " + statusCode + StringUtils.SPACE + httpResponseExcuteInternal.getStatusLine());
        throw new IOException("Server error: " + statusCode + StringUtils.SPACE + httpResponseExcuteInternal.getStatusLine());
    }

    public static String httpGetRequestWithDecodeData(String str, Header header, String str2) throws BadPaddingException, CloudServerException, IllegalBlockSizeException, IOException {
        return httpGetRequestWithDecodeData(str, header, getCryptCoder(str, str2));
    }

    public static String httpGetRequestWithDecodeData(String str, Header header, CryptCoder cryptCoder) throws CloudServerException, IOException {
        return httpGetRequestWithDecodeData(str, header, new ArrayList(), cryptCoder);
    }

    public static String httpGetRequestWithDecodeData(String str, Header header, List<Header> list, CryptCoder cryptCoder) throws CloudServerException, IOException {
        String strHttpGetRequest = httpGetRequest(str, header, list, cryptCoder, 0);
        if (Log.isLoggable("Micloud", 3)) {
            Log.d("Micloud", strHttpGetRequest);
        }
        return strHttpGetRequest;
    }

    private static String httpGetRequest(String str, Header header, List<Header> list, CryptCoder cryptCoder, int i) throws CloudServerException, IOException {
        HttpGet httpGet = new HttpGet(str);
        addHttpRequestHeaders(httpGet, null, header, list, i);
        if (Log.isLoggable("Micloud", 3)) {
            Log.d("Micloud", "http get url : " + str);
            Log.d("Micloud", "http get cookies : " + (header != null ? header.toString() : "null"));
            Log.d("Micloud", "http get additionalHeaders : " + list);
        }
        HttpResponse httpResponseExcuteInternal = excuteInternal(httpGet);
        int statusCode = httpResponseExcuteInternal.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            statGetDownloadFileUrlsFailedEvent(str, httpGet.getMethod(), statusCode, "", "");
            if (CloudServerException.isMiCloudServerException(statusCode)) {
                Log.e("Micloud", "MiCloudServerException: " + statusCode + StringUtils.SPACE + httpResponseExcuteInternal.getStatusLine());
                throw new CloudServerException(statusCode, httpResponseExcuteInternal);
            }
            Log.e("Micloud", "Server error: " + statusCode + StringUtils.SPACE + httpResponseExcuteInternal.getStatusLine());
            throw new IOException("Server error: " + statusCode + StringUtils.SPACE + httpResponseExcuteInternal.getStatusLine());
        }
        try {
            String strDecrypt = cryptCoder.decrypt(decodeGZipToString(httpResponseExcuteInternal));
            String strCheckRedirect = CloudRelocationUtils.checkRedirect(strDecrypt, i);
            if (!TextUtils.isEmpty(strCheckRedirect)) {
                return httpGetRequest(strCheckRedirect, header, list, cryptCoder, i + 1);
            }
            statGetDownloadFileUrlsFailedEventIfCloudResponseCodeError(str, httpGet.getMethod(), statusCode, strDecrypt);
            return strDecrypt;
        } catch (CipherException e) {
            Log.e("Micloud", "MiCloudServerException", e);
            statGetDownloadFileUrlsFailedEvent(str, httpGet.getMethod(), statusCode, e.getClass().getSimpleName(), "");
            throw new CloudServerException(0, httpResponseExcuteInternal);
        }
    }

    private static void addHttpRequestHeaders(HttpRequestBase httpRequestBase, String str, Header header, List<Header> list, int i) {
        if (!TextUtils.isEmpty(str)) {
            httpRequestBase.setHeader("Content-Type", str);
        }
        if (header != null) {
            httpRequestBase.setHeader(header);
        }
        httpRequestBase.setHeader("Accept", "application/json");
        httpRequestBase.setHeader("Accept-Encoding", ENCODING_GZIP);
        if (i > 0) {
            httpRequestBase.setHeader(MiCloudConstants.REQUEST.X_XIAOMI_REDIRECT_COUNT, i + "");
        }
        httpRequestBase.setHeader(MiCloudConstants.REQUEST.X_XIAOMI_SUPPORT_REDIRECT, "true, https");
        Iterator<Header> it = list.iterator();
        while (it.hasNext()) {
            httpRequestBase.setHeader(it.next());
        }
    }

    private static HttpResponse excuteInternal(HttpRequestBase httpRequestBase) throws IOException {
        HttpRequestBase httpRequestBase2;
        long jCurrentTimeMillis = System.currentTimeMillis();
        try {
            httpRequestBase2 = httpRequestBase;
            try {
                HttpResponse httpResponseExecute = HttpUtils.getHttpClient().execute(httpRequestBase2);
                long jCurrentTimeMillis2 = System.currentTimeMillis() - jCurrentTimeMillis;
                long contentLength = httpResponseExecute.getEntity() != null ? httpResponseExecute.getEntity().getContentLength() : 0L;
                int statusCode = httpResponseExecute.getStatusLine().getStatusCode();
                MiCloudStatManager.getInstance().addHttpEvent(httpRequestBase2.getURI().toString(), jCurrentTimeMillis2, contentLength, statusCode, null);
                MiCloudNetEventStatInjector.getInstance().addNetSuccessEvent(new NetSuccessStatParam(httpRequestBase2.getURI().toString(), jCurrentTimeMillis, jCurrentTimeMillis2, contentLength, statusCode, 0));
                return httpResponseExecute;
            } catch (IOException e) {
                e = e;
                IOException iOException = e;
                MiCloudStatManager.getInstance().addHttpEvent(httpRequestBase2.getURI().toString(), -1L, 0L, -1, iOException.getClass().getSimpleName());
                MiCloudNetEventStatInjector.getInstance().addNetFailedEvent(new NetFailedStatParam(httpRequestBase2.getURI().toString(), jCurrentTimeMillis, System.currentTimeMillis() - jCurrentTimeMillis, iOException, 0));
                statGetDownloadFileUrlsFailedEvent(httpRequestBase2.getURI().toString(), httpRequestBase2.getMethod(), -1, iOException.getClass().getSimpleName(), "");
                throw iOException;
            }
        } catch (IOException e2) {
            e = e2;
            httpRequestBase2 = httpRequestBase;
        }
    }

    public static List<NameValuePair> mapToPairs(Map<String, String> map) {
        if (map == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (value == null) {
                value = "";
            }
            arrayList.add(new BasicNameValuePair(key, value));
        }
        return arrayList;
    }

    public static void addFilterTagsToParams(String str, Map<String, String> map, String str2, Set<String> set) throws CipherException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        if (set == null || set.isEmpty()) {
            return;
        }
        ArrayList arrayList = new ArrayList(set);
        for (int iMin = Math.min(set.size(), 100); iMin > 0; iMin /= 2) {
            map.put("filterTag", TextUtils.join(",", arrayList.subList(0, iMin)));
            if (HttpUtils.appendUrl(str, mapToPairs(encryptParams(PassportSimpleRequest.HTTP_METHOD_GET, str, map, str2, new AESCoder(str2)))).length() < 1024) {
                return;
            }
        }
        map.remove("filterTag");
    }

    static InputStream decodeGZip(HttpResponse httpResponse) throws IllegalStateException, IOException {
        Header firstHeader = httpResponse.getFirstHeader("Content-Encoding");
        if (firstHeader != null && firstHeader.getValue().equalsIgnoreCase(ENCODING_GZIP)) {
            return new GZIPInputStream(httpResponse.getEntity().getContent());
        }
        return httpResponse.getEntity().getContent();
    }

    static String decodeGZipToString(HttpResponse httpResponse) throws IllegalStateException, IOException {
        InputStream inputStreamDecodeGZip = decodeGZip(httpResponse);
        try {
            return inputStreamToString(inputStreamDecodeGZip);
        } finally {
            if (inputStreamDecodeGZip != null) {
                inputStreamDecodeGZip.close();
            }
        }
    }

    public static String inputStreamToString(InputStream inputStream) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        char[] cArr = new char[2048];
        while (true) {
            int i = bufferedReader.read(cArr, 0, 2048);
            if (i != -1) {
                stringBuffer.append(cArr, 0, i);
            } else {
                return stringBuffer.toString();
            }
        }
    }

    public static boolean isV4Url(String str) {
        return str.indexOf("/v4") != -1;
    }

    public static CryptCoder getCryptCoder(String str, String str2) {
        return isV4Url(str) ? new CloudAESWithIVCoder(str2) : new AESCoder(str2);
    }

    public static String encodeData(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str2)) {
            throw new IllegalArgumentException("security is empty.");
        }
        if (TextUtils.isEmpty(str3)) {
            return "";
        }
        try {
            return getCryptCoder(str, str2).encrypt(str3);
        } catch (CipherException e) {
            Log.e(TAG, "encodeData failed:" + str3, e);
            return "";
        }
    }

    private static Map<String, String> encryptParams(String str, String str2, Map<String, String> map, String str3, CryptCoder cryptCoder) throws CipherException {
        if (cryptCoder == null) {
            throw new CipherException("no invalid cypt coder");
        }
        HashMap map2 = new HashMap();
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (key != null && value != null) {
                    if (!key.startsWith("_")) {
                        value = cryptCoder.encrypt(value);
                    }
                    map2.put(key, value);
                }
            }
        }
        map2.put("signature", CloudCoder.generateSignature(str, str2, map2, str3));
        return map2;
    }

    private static void statGetDownloadFileUrlsFailedEvent(String str, String str2, int i, String str3, String str4) {
        if (MiCloudNetEventStatInjector.getInstance().isGetDownloadFileRequestUrl(str, str2)) {
            MiCloudNetEventStatInjector.getInstance().addGetDownloadFileUrlsFailedEvent(GetDownloadFileUrlFailedStatParam.createGetDownloadFileUrlFailedStatParam(str, i, str3, str4));
        }
    }

    private static void statGetDownloadFileUrlsFailedEventIfCloudResponseCodeError(String str, String str2, int i, String str3) {
        if (MiCloudNetEventStatInjector.getInstance().isGetDownloadFileRequestUrl(str, str2) && HttpUtils.isHttpStatusCodeOKButCloudResponseCodeError(i, str3)) {
            MiCloudNetEventStatInjector.getInstance().addGetDownloadFileUrlsFailedEvent(GetDownloadFileUrlFailedStatParam.createGetDownloadFileUrlFailedStatParam(str, i, "", str3));
        }
    }
}
