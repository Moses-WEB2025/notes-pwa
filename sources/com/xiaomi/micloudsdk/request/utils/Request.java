package com.xiaomi.micloudsdk.request.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;
import com.market.sdk.DesktopRecommendInfo;
import com.xiaomi.e2ee.E2EEManager;
import com.xiaomi.micloudsdk.data.ExtendedAuthToken;
import com.xiaomi.micloudsdk.data.IAuthToken;
import com.xiaomi.micloudsdk.exception.CipherException;
import com.xiaomi.micloudsdk.exception.CloudServerException;
import com.xiaomi.micloudsdk.utils.CloudCoder;
import com.xiaomi.micloudsdk.utils.CryptCoder;
import com.xiaomi.micloudsdk.utils.MiCloudConstants;
import com.xiaomi.opensdk.exception.AuthenticationException;
import com.xiaomi.opensdk.exception.RetriableException;
import com.xiaomi.opensdk.exception.UnretriableException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import micloud.compat.independent.request.NetworkAvailabilityManagerCompat;
import micloud.compat.independent.request.QueryAuthTokenException;
import micloud.compat.independent.request.RequestInjectorCompat;
import miui.cloud.log.PrivacyFilter;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class Request {
    private static final String HEADER_PARAM_REQUEST_APK_VERSION = "apk-version";
    private static final String HEADER_PARAM_REQUEST_APK_VERSION_CODE = "apk-version-code";
    private static final int MAX_FILTER_TAG_COUNT = 100;
    private static final String PARAM_REQUEST_APK_VERSION = "_apkversion";
    private static final String PARAM_REQUEST_APK_VERSION_CODE = "_apkversioncode";
    private static final String PARAM_SYNC_FILTER_TAG = "filterTag";
    private static final String PARAM_SYNC_SIGNATURE = "signature";
    private static final String TAG = "Request";

    public interface RequestEnv {
        String getAccountName();

        long getAutoRetryInterval();

        int getMaxRetryCount();

        String getUserAgent();

        void invalidateAuthToken();

        IAuthToken queryAuthToken() throws QueryAuthTokenException;

        String queryEncryptedAccountName();

        boolean shouldUpdateHost();
    }

    public static void init(Context context) {
        RequestContext.init(context);
    }

    public static Context getContext() {
        return RequestContext.getContext();
    }

    public static void setRegion(String str) {
        RequestContext.setRegion(str);
    }

    public static String getRegion() {
        return RequestContext.getRegion();
    }

    public static void setRequestEnv(RequestEnv requestEnv) {
        RequestContext.setRequestEnv(RequestEnvDelegation.toRequestContext_RequestEnv(requestEnv));
    }

    public static RequestEnv getRequestEnv() {
        return RequestEnvDelegation.toRequest_RequestEnv(RequestContext.getRequestEnv());
    }

    public static String getUserAgent() {
        return RequestContext.getUserAgent();
    }

    public static String secureGet(String str, Map<String, String> map) throws BadPaddingException, CloudServerException, IllegalBlockSizeException, IOException {
        return secureGet(str, map, null);
    }

    public static String secureGet(String str, Map<String, String> map, Map<String, String> map2) throws BadPaddingException, CloudServerException, IllegalBlockSizeException, IOException {
        return secureGet(str, null, map, map2);
    }

    public static String secureGet(String str, Map<String, String> map, Map<String, String> map2, Map<String, String> map3) throws BadPaddingException, CloudServerException, IllegalBlockSizeException, IOException {
        return execute(str, HttpUtils.HttpMethod.GET, map, map2, map3, null);
    }

    public static String secureGet(String str, Map<String, String> map, Map<String, String> map2, Map<String, String> map3, IAuthToken iAuthToken) throws BadPaddingException, CloudServerException, IllegalBlockSizeException, IOException {
        return execute(str, HttpUtils.HttpMethod.GET, map, map2, map3, iAuthToken);
    }

    public static String securePost(String str, Map<String, String> map) throws BadPaddingException, CloudServerException, IllegalBlockSizeException, IOException {
        return securePost(str, map, null);
    }

    public static String securePost(String str, Map<String, String> map, Map<String, String> map2) throws BadPaddingException, CloudServerException, IllegalBlockSizeException, IOException {
        return securePost(str, null, map, map2);
    }

    public static String securePost(String str, Map<String, String> map, Map<String, String> map2, Map<String, String> map3) throws BadPaddingException, CloudServerException, IllegalBlockSizeException, IOException {
        return execute(str, HttpUtils.HttpMethod.POST, map, map2, map3, null);
    }

    public static String securePost(String str, Map<String, String> map, Map<String, String> map2, Map<String, String> map3, IAuthToken iAuthToken) throws BadPaddingException, CloudServerException, IllegalBlockSizeException, IOException {
        return execute(str, HttpUtils.HttpMethod.POST, map, map2, map3, iAuthToken);
    }

    public static String securePostJSON(String str, Map<String, String> map, Map<String, String> map2) throws BadPaddingException, CloudServerException, IllegalBlockSizeException, IOException {
        return execute(str, HttpUtils.HttpMethod.POST_JSON, null, map, map2, null);
    }

    public static String securePostJSON(String str, Map<String, String> map, Map<String, String> map2, Map<String, String> map3, IAuthToken iAuthToken) throws BadPaddingException, CloudServerException, IllegalBlockSizeException, IOException {
        return execute(str, HttpUtils.HttpMethod.POST_JSON, map, map2, map3, iAuthToken);
    }

    public static JSONObject requestServer(String str, HttpUtils.HttpMethod httpMethod, Map<String, String> map, Map<String, String> map2) throws AuthenticationException, RetriableException, UnretriableException {
        return requestServer(str, httpMethod, null, map, map2);
    }

    public static JSONObject requestServer(String str, HttpUtils.HttpMethod httpMethod, Map<String, String> map, Map<String, String> map2, Map<String, String> map3) throws AuthenticationException, RetriableException, UnretriableException {
        try {
            return new JSONObject(execute(str, httpMethod, map, map2, map3, null));
        } catch (CloudServerException e) {
            Log.e(TAG, "requestServer error:" + e.getStatusCode() + PrivacyFilter.filterPrivacyLog(Log.getStackTraceString(e)));
            int statusCode = e.getStatusCode();
            if (statusCode == 401 || statusCode == 403) {
                throw new AuthenticationException();
            }
            if (statusCode == 500) {
                throw new RetriableException(e.getMessage(), DesktopRecommendInfo.DEFAULT_CACHE_TIME);
            }
            if (statusCode == 503) {
                throw new RetriableException(e.getMessage(), e.retryTime);
            }
            throw new UnretriableException(e);
        } catch (UnsupportedEncodingException e2) {
            Log.e(TAG, "requestServer error:" + PrivacyFilter.filterPrivacyLog(Log.getStackTraceString(e2)));
            throw new UnretriableException(e2);
        } catch (ClientProtocolException e3) {
            Log.e(TAG, "requestServer error:" + PrivacyFilter.filterPrivacyLog(Log.getStackTraceString(e3)));
            throw new UnretriableException(e3);
        } catch (IOException e4) {
            Log.e(TAG, "requestServer error:" + PrivacyFilter.filterPrivacyLog(Log.getStackTraceString(e4)));
            if (RetriableException.isRetriableException(e4)) {
                throw new RetriableException(e4.getMessage(), DesktopRecommendInfo.DEFAULT_CACHE_TIME);
            }
            throw new UnretriableException(e4);
        } catch (BadPaddingException e5) {
            Log.e(TAG, "requestServer error:" + PrivacyFilter.filterPrivacyLog(Log.getStackTraceString(e5)));
            throw new UnretriableException(e5);
        } catch (IllegalBlockSizeException e6) {
            Log.e(TAG, "requestServer error:" + PrivacyFilter.filterPrivacyLog(Log.getStackTraceString(e6)));
            throw new UnretriableException(e6);
        } catch (JSONException e7) {
            Log.e(TAG, "requestServer error:" + PrivacyFilter.filterPrivacyLog(Log.getStackTraceString(e7)));
            throw new UnretriableException(e7);
        }
    }

    private static String execute(String str, HttpUtils.HttpMethod httpMethod, Map<String, String> map, Map<String, String> map2, Map<String, String> map3, IAuthToken iAuthToken) throws CloudServerException, BadPaddingException, IllegalBlockSizeException, IOException {
        Map<String, String> map4 = map == null ? new HashMap() : map;
        Map<String, String> map5 = map2 == null ? new HashMap() : map2;
        addE2EEHeaderInfo(map4);
        addApkVersionWithinParams(map4, map5);
        SyncRequestParam.addParam(map4, map5);
        try {
            String strCheckHostLocation = checkHostLocation(str);
            String strHttpPostJSONRequestWithDecodeData = "";
            boolean zIsV4Url = CloudRequestHelper.isV4Url(strCheckHostLocation);
            int i = 0;
            while (i < 2) {
                IAuthToken.HttpRequestBuilder builderForCloudRequest = (iAuthToken == null ? getAuthTokenOrThrow() : iAuthToken).getBuilderForCloudRequest();
                CryptCoder cryptCoder = builderForCloudRequest.getCryptCoder(strCheckHostLocation);
                try {
                    ArrayList<NameValuePair> arrayListEncodeParameters = encodeParameters(cryptCoder, zIsV4Url ? CloudCoder.generateNonce() : null, map5);
                    builderForCloudRequest.addParams(arrayListEncodeParameters);
                    builderForCloudRequest.signParams(httpMethod, strCheckHostLocation, arrayListEncodeParameters);
                    Header cookies = getCookies(RequestContext.getRequestEnv().queryEncryptedAccountName(), builderForCloudRequest.getServiceToken(), map3);
                    ArrayList arrayList = new ArrayList();
                    for (Map.Entry<String, String> entry : map4.entrySet()) {
                        arrayList.add(new BasicHeader(entry.getKey(), entry.getValue()));
                        map4 = map4;
                    }
                    Map<String, String> map6 = map4;
                    builderForCloudRequest.addAdditionalHeaders(arrayList);
                    try {
                        if (HttpUtils.HttpMethod.GET.equals(httpMethod)) {
                            strHttpPostJSONRequestWithDecodeData = CloudRequestHelper.httpGetRequestWithDecodeData(HttpUtils.appendUrl(strCheckHostLocation, arrayListEncodeParameters), cookies, arrayList, cryptCoder);
                            break;
                        }
                        if (HttpUtils.HttpMethod.POST.equals(httpMethod)) {
                            strHttpPostJSONRequestWithDecodeData = CloudRequestHelper.httpPostRequestWithDecodeData(strCheckHostLocation, new UrlEncodedFormEntity(arrayListEncodeParameters, "UTF-8"), cookies, arrayList, cryptCoder);
                            break;
                        }
                        if (HttpUtils.HttpMethod.POST_JSON.equals(httpMethod)) {
                            JSONObject jSONObject = new JSONObject();
                            for (int i2 = 0; i2 < arrayListEncodeParameters.size(); i2++) {
                                try {
                                    jSONObject.put(arrayListEncodeParameters.get(i2).getName(), arrayListEncodeParameters.get(i2).getValue());
                                } catch (JSONException e) {
                                    throw new CloudServerException(0, e.toString());
                                }
                            }
                            strHttpPostJSONRequestWithDecodeData = CloudRequestHelper.httpPostJSONRequestWithDecodeData(strCheckHostLocation, new StringEntity(jSONObject.toString(), "utf-8"), cookies, arrayList, cryptCoder);
                            break;
                        }
                        throw new IllegalArgumentException("http method not supported.");
                    } catch (CloudServerException e2) {
                        Log.e("Micloud", "CloudServer Exception: " + PrivacyFilter.filterPrivacyLog(Log.getStackTraceString(e2)));
                        if (e2.getStatusCode() != 401) {
                            RequestInjectorCompat.handleCloudServerException(RequestContext.getContext(), e2);
                            throw e2;
                        }
                        if (iAuthToken != null) {
                            Log.e("Micloud", "CloudServer 401 Exception: customize token is not null, throw exception");
                            throw e2;
                        }
                        if (i == 0) {
                            Log.e("Micloud", "CloudServer 401 Exception: retry=" + i + PrivacyFilter.filterPrivacyLog(Log.getStackTraceString(e2)));
                            RequestContext.getRequestEnv().invalidateAuthToken();
                            CloudCoder.setServerDateOffset(e2.serverDate - System.currentTimeMillis());
                        } else if (i == 1) {
                            Log.e("Micloud", "CloudServer 401 Exception: retry=" + i + PrivacyFilter.filterPrivacyLog(Log.getStackTraceString(e2)));
                            throw e2;
                        }
                        i++;
                        map4 = map6;
                    }
                } catch (CipherException e3) {
                    Log.e("Micloud", "CipherException: " + PrivacyFilter.filterPrivacyLog(Log.getStackTraceString(e3)));
                    throw new CloudServerException(0, e3.toString());
                }
            }
            RequestInjectorCompat.checkResponse(RequestContext.getContext(), strHttpPostJSONRequestWithDecodeData);
            NetworkAvailabilityManagerCompat.setAvailability(RequestContext.getContext(), true);
            return strHttpPostJSONRequestWithDecodeData;
        } catch (IOException e4) {
            NetworkAvailabilityManagerCompat.setAvailability(RequestContext.getContext(), false);
            throw e4;
        }
    }

    private static String checkHostLocation(String str) throws CloudServerException {
        if (!str.startsWith(MiCloudConstants.URL.URL_RELOCATION_BASE)) {
            return CloudRelocationUtils.updateRequestHost(str, false);
        }
        Log.d(TAG, "relocation url " + MiCloudConstants.URL.URL_RELOCATION_BASE);
        return str;
    }

    @Deprecated
    public static ExtendedAuthToken queryAuthToken() throws CloudServerException {
        try {
            IAuthToken iAuthTokenQueryAuthToken = RequestContext.getRequestEnv().queryAuthToken();
            if (iAuthTokenQueryAuthToken instanceof ExtendedAuthToken) {
                return (ExtendedAuthToken) iAuthTokenQueryAuthToken;
            }
            throw new CloudServerException(0, "token instance is not ExtendedAuthToken");
        } catch (QueryAuthTokenException e) {
            throw new CloudServerException(0, e);
        }
    }

    private static IAuthToken getAuthTokenOrThrow() throws CloudServerException {
        try {
            return RequestContext.getRequestEnv().queryAuthToken();
        } catch (QueryAuthTokenException e) {
            throw new CloudServerException(0, e);
        }
    }

    private static ArrayList<NameValuePair> encodeParameters(CryptCoder cryptCoder, String str, Map<String, String> map) throws CipherException {
        ArrayList<NameValuePair> arrayList;
        if (map == null) {
            map = new HashMap<>();
        }
        if (str != null && !map.containsKey("_nonce")) {
            arrayList = new ArrayList<>(map.size() + 1);
            arrayList.add(new BasicNameValuePair("_nonce", str));
        } else {
            arrayList = new ArrayList<>(map.size());
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (!key.startsWith("_")) {
                if (value == null) {
                    value = "";
                }
                arrayList.add(new BasicNameValuePair(key, cryptCoder.encrypt(value)));
            } else {
                arrayList.add(new BasicNameValuePair(key, value));
            }
        }
        return arrayList;
    }

    private static Header getCookies(String str, String str2, Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        if (str2 != null) {
            sb.append("serviceToken=").append(str2);
        }
        if (str != null) {
            sb.append(sb.length() == 0 ? "cUserId=" : "; cUserId=");
            sb.append(str);
        }
        if (map != null && map.size() > 0) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                if (str == null || (!"cUserId".equals(key) && !"userId".equals(key) && !"serviceToken".equals(key))) {
                    if (!TextUtils.isEmpty(key)) {
                        sb.append(sb.length() == 0 ? "" : "; ");
                        sb.append(key).append("=");
                        sb.append(entry.getValue());
                    }
                }
            }
        }
        return new BasicHeader("Cookie", sb.toString());
    }

    private static void addE2EEHeaderInfo(Map<String, String> map) {
        if (!E2EEManager.getInstance().hasInitE2EE()) {
            Log.i(TAG, "not add e2ee header");
        } else {
            map.put("X-E2EE", "1.0");
            map.put("X-E2EE-STATUS", String.valueOf(E2EEManager.getInstance().isE2EEOpen()));
        }
    }

    private static void addApkVersionWithinParams(Map<String, String> map, Map<String, String> map2) {
        Context context = RequestContext.getContext();
        String packageName = context.getPackageName();
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            String str = packageInfo.versionName;
            String strValueOf = String.valueOf(packageInfo.versionCode);
            if (TextUtils.isEmpty(map.get(HEADER_PARAM_REQUEST_APK_VERSION))) {
                map.put(HEADER_PARAM_REQUEST_APK_VERSION, str);
            }
            if (TextUtils.isEmpty(map.get(HEADER_PARAM_REQUEST_APK_VERSION_CODE))) {
                map.put(HEADER_PARAM_REQUEST_APK_VERSION_CODE, strValueOf);
            }
            if (TextUtils.isEmpty(map2.get("_apkversion"))) {
                map2.put("_apkversion", str);
            }
            if (TextUtils.isEmpty(map2.get("_apkversioncode"))) {
                map2.put("_apkversioncode", strValueOf);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, packageName + " get apk version error —— Exception: " + e.getMessage());
        }
    }

    public static void addFilterTagsToParams(String str, Map<String, String> map, Set<String> set, long j) throws BadPaddingException, CloudServerException, IllegalBlockSizeException, UnsupportedEncodingException {
        addFilterTagsToParams(str, map, queryAuthToken().security, set, j);
    }

    public static void addFilterTagsToParams(String str, Map<String, String> map, String str2, Set<String> set, long j) throws BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            arrayList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        int i = 0;
        for (String str3 : set) {
            if (Long.parseLong(str3) > j) {
                if (i < 100) {
                    if (sb2.length() > 0) {
                        sb2.append(",");
                    }
                    sb2.append(str3);
                    i++;
                    if (i % 10 == 0 || i == set.size()) {
                    }
                }
                BasicNameValuePair basicNameValuePair = new BasicNameValuePair("filterTag", CloudRequestHelper.encodeData(str, str2, sb.length() == 0 ? sb2.toString() : ((Object) sb) + "," + ((Object) sb2)));
                arrayList.add(basicNameValuePair);
                BasicNameValuePair basicNameValuePair2 = new BasicNameValuePair("signature", HttpUtils.getSignature(HttpUtils.HttpMethod.GET, str, arrayList, str2));
                arrayList.add(basicNameValuePair2);
                int length = HttpUtils.appendUrl(str, arrayList).length();
                arrayList.remove(basicNameValuePair);
                arrayList.remove(basicNameValuePair2);
                if (length >= 1024) {
                    break;
                }
                if (sb.length() > 0) {
                    sb.append(",");
                }
                sb.append((CharSequence) sb2);
                sb2.setLength(0);
                if (i >= 100) {
                    break;
                }
            }
        }
        String string = sb.toString();
        if (Log.isLoggable("Micloud", 3)) {
            Log.d("Micloud", "The filterTag is :" + string);
        }
        map.put("filterTag", string);
    }
}
