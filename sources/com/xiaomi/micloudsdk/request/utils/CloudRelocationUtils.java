package com.xiaomi.micloudsdk.request.utils;

import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.micloudsdk.data.IAuthToken;
import com.xiaomi.micloudsdk.exception.CloudServerException;
import com.xiaomi.micloudsdk.utils.JsonUtils;
import com.xiaomi.micloudsdk.utils.MiCloudConstants;
import com.xiaomi.micloudsdk.utils.NetworkUtils;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import micloud.compat.independent.utils.RelocationCacheCompat;
import miui.cloud.common.XLogger;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class CloudRelocationUtils {
    private static final String CODE = "code";
    private static final int CODE_EXCEED_MAX_REDIRECT_COUNT = 10034;
    public static final int CODE_IS_INTERNATIONAL_ACCOUNT = 1;
    public static final int CODE_NOT_INTERNATIONAL_ACCOUNT = 0;
    private static final int CODE_REDIRECT = 308;
    public static final int CODE_UNKOWN_ERROR = 2;
    private static final String DATA = "data";
    private static final int MAX_REDIRECT_COUNT = 15;
    private static final String REDIRECT_URL = "redirectUrl";
    private static final int RESUME_SYNC_INTERVAL_SECOND = 10;
    private static final String RETRY_AFTER = "retryAfter";
    private static volatile Object sNeedInitHostList;
    private static volatile boolean sNeedUpdateHostList;
    private static UpdateStatus sUpdateStatus;
    private static final int[] SERVER_RETRY_INTERVALS = {1000, 2000, 5000, 10000};
    private static final String URL_RELOCATION_QUERY = MiCloudConstants.URL.URL_RELOCATION_BASE + "/mic/relocation/v3/user/record";
    private static final String URL_RELOCATION_QUERY_NEW = MiCloudConstants.URL.URL_RELOCATION_BASE + "/mic/relocation/v3/user/record/upgrade";
    private static volatile Map<String, Object> sHostsCacheInner = new HashMap();
    private static Object sUpdateMiCloudHostsLock = new Object();

    private enum UpdateStatus {
        UPDATING,
        SUCCESS,
        FAILED
    }

    private static void updateHostCache(Map<String, Object> map) {
        sHostsCacheInner = new HashMap(map);
    }

    private static Map<String, Object> getHostCache() {
        return new HashMap(sHostsCacheInner);
    }

    private static boolean needUpdateHostListAndInitIfNeeded() {
        if (sNeedInitHostList == null) {
            synchronized (sUpdateMiCloudHostsLock) {
                if (sNeedInitHostList == null) {
                    sNeedInitHostList = new Object();
                    sNeedUpdateHostList = TextUtils.isEmpty(RelocationCacheCompat.getCachedHostList(RequestContext.getContext()));
                }
            }
        }
        return sNeedUpdateHostList;
    }

    private static void setNeedUpdateHostsList(boolean z) {
        sNeedUpdateHostList = z;
    }

    public static String checkRedirect(String str, int i) throws CloudServerException {
        if (i >= 15) {
            throw new CloudServerException(503, 10034, 10);
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.getInt("code") == 308) {
                if (jSONObject.getJSONObject("data").optBoolean("isPermanent")) {
                    setNeedUpdateHostsList(true);
                }
                return jSONObject.getJSONObject("data").getString(REDIRECT_URL);
            }
            if (jSONObject.getInt("code") == 503) {
                throw new CloudServerException(503, 503, jSONObject.getJSONObject("data").getInt(RETRY_AFTER));
            }
            if (jSONObject.getInt("code") != 10034) {
                return null;
            }
            throw new CloudServerException(503, 10034, jSONObject.getJSONObject("data").getInt(RETRY_AFTER));
        } catch (JSONException e) {
            Log.e("Micloud", "JSONException in checkRedirect():" + str, e);
            return null;
        }
    }

    public static int isInternationalAccount(boolean z) {
        String host;
        if (z) {
            updateHostCache(new HashMap());
        }
        if (getHostCache().isEmpty()) {
            setNeedUpdateHostsList(true);
            try {
                updateMiCloudHosts(z);
                if (getHostCache().isEmpty()) {
                    return 2;
                }
            } catch (CloudServerException e) {
                Log.e("Micloud", "CloudServerException in isInternationalAccount", e);
                return 2;
            }
        }
        for (Map.Entry<String, Object> entry : getHostCache().entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if ((value instanceof String) && (host = Uri.parse((String) value).getHost()) != null) {
                return host.equals(key) ? 0 : 1;
            }
        }
        return 2;
    }

    private static void updateMiCloudHosts(boolean z) throws CloudServerException {
        updateMiCloudHosts(z, null, null);
    }

    public static void updateMiCloudHosts(boolean z, Map<String, String> map, IAuthToken iAuthToken) throws CloudServerException {
        boolean z2;
        IOException iOException;
        needUpdateHostListAndInitIfNeeded();
        if (Log.isLoggable("Micloud", 3)) {
            Log.d("Micloud", "enter updateMiCloudHosts, sNeedUpdateHosts: " + needUpdateHostListAndInitIfNeeded());
        }
        String cachedXiaomiAccountName = RelocationCacheCompat.getCachedXiaomiAccountName(RequestContext.getContext());
        if (!TextUtils.isEmpty(cachedXiaomiAccountName) && !cachedXiaomiAccountName.equals(RequestContext.getRequestEnv().getAccountName())) {
            Log.d("Micloud", "change sNeedUpdateHosts to true");
            setNeedUpdateHostsList(true);
        }
        if (needUpdateHostListAndInitIfNeeded() || z) {
            try {
                synchronized (sUpdateMiCloudHostsLock) {
                    loop0: while (true) {
                        z2 = true;
                        while (true) {
                            if (sUpdateStatus != UpdateStatus.UPDATING) {
                                break loop0;
                            }
                            Log.v("Micloud", "Waiting for an existing updateMiCloudHosts to finish " + Thread.currentThread().getName());
                            sUpdateMiCloudHostsLock.wait();
                            Log.v("Micloud", "The existing updateMiCloudHosts finished " + Thread.currentThread().getName());
                            if (sUpdateStatus != UpdateStatus.SUCCESS) {
                                break;
                            } else {
                                z2 = false;
                            }
                        }
                    }
                    if (z2) {
                        sUpdateStatus = UpdateStatus.UPDATING;
                    }
                }
                if (z2) {
                    try {
                        Log.d("Micloud", "updateMiCloudHosts " + Thread.currentThread().getName());
                        HashMap map2 = new HashMap();
                        String romCountry = RomCountryUtil.getRomCountry();
                        if (romCountry != null) {
                            RequestContext.setRegion(romCountry);
                            map2.put("romCountry", romCountry);
                        } else {
                            map2.put("romCountry", RequestContext.getRegion());
                        }
                        map2.put("androidVersion", Build.VERSION.SDK_INT + "");
                        map2.put("MIUIVersion", "MIUI/" + Build.VERSION.INCREMENTAL);
                        int i = 0;
                        while (true) {
                            if (Thread.currentThread().isInterrupted()) {
                                throw new InterruptedException();
                            }
                            try {
                                try {
                                    try {
                                        try {
                                            String accountName = RequestContext.getRequestEnv().getAccountName();
                                            if (TextUtils.isEmpty(accountName)) {
                                                synchronized (sUpdateMiCloudHostsLock) {
                                                    sUpdateStatus = UpdateStatus.SUCCESS;
                                                    sUpdateMiCloudHostsLock.notifyAll();
                                                }
                                                return;
                                            }
                                            iOException = null;
                                            JSONObject jSONObject = new JSONObject(Request.securePost(URL_RELOCATION_QUERY_NEW, null, map2, map, iAuthToken));
                                            if (jSONObject.getInt("code") == 0) {
                                                JSONObject jSONObject2 = jSONObject.getJSONObject("data").getJSONObject("hostList");
                                                if (jSONObject2 == null) {
                                                    break;
                                                }
                                                Log.d("Micloud", "getHostList set sNeedUpdateHosts to false " + Thread.currentThread().getName());
                                                RelocationCacheCompat.cacheHostList(RequestContext.getContext(), jSONObject2.toString());
                                                updateHostCache(JsonUtils.jsonToMap(jSONObject2));
                                                setNeedUpdateHostsList(false);
                                                if (!TextUtils.isEmpty(accountName)) {
                                                    RelocationCacheCompat.cacheXiaomiAccountName(RequestContext.getContext(), accountName);
                                                    break;
                                                }
                                                break;
                                            }
                                            int[] iArr = SERVER_RETRY_INTERVALS;
                                            if (i >= iArr.length) {
                                                if (iOException == null) {
                                                    throw new CloudServerException(0);
                                                }
                                                throw new CloudServerException(0, iOException);
                                            }
                                            Log.e("Micloud", "Wait " + iArr[i] + " ms for retry");
                                            Thread.sleep(iArr[i]);
                                            i++;
                                        } catch (IOException e) {
                                            iOException = e;
                                            Log.e("Micloud", "IOException in updateMiCloudHosts", iOException);
                                            if (!NetworkUtils.isNetworkAvailable(RequestContext.getContext())) {
                                                Log.d("Micloud", "No network in IOException");
                                                throw new CloudServerException(0, iOException);
                                            }
                                        } catch (JSONException e2) {
                                            Log.e("Micloud", "JSONException in updateMiCloudHosts", e2);
                                            throw new CloudServerException(0, e2);
                                        }
                                    } catch (BadPaddingException e3) {
                                        Log.e("Micloud", "BadPaddingException in updateMiCloudHosts", e3);
                                        throw new CloudServerException(0, e3);
                                    }
                                } catch (ClientProtocolException e4) {
                                    Log.e("Micloud", "ClientProtocolException in updateMiCloudHosts", e4);
                                    throw new CloudServerException(0, e4);
                                }
                            } catch (IllegalBlockSizeException e5) {
                                Log.e("Micloud", "IllegalBlockSizeException in updateMiCloudHosts", e5);
                                throw new CloudServerException(0, e5);
                            }
                            Log.e("Micloud", "InterruptedException in updateMiCloudHosts", e);
                            throw new CloudServerException(0, e);
                        }
                        synchronized (sUpdateMiCloudHostsLock) {
                            sUpdateStatus = UpdateStatus.SUCCESS;
                            sUpdateMiCloudHostsLock.notifyAll();
                        }
                    } catch (Throwable th) {
                        synchronized (sUpdateMiCloudHostsLock) {
                            sUpdateStatus = UpdateStatus.FAILED;
                            sUpdateMiCloudHostsLock.notifyAll();
                            throw th;
                        }
                    }
                }
            } catch (InterruptedException e6) {
                Log.e("Micloud", "InterruptedException in updateMiCloudHosts", e6);
                throw new CloudServerException(0, e6);
            }
        }
    }

    public static boolean shouldUpdateHost() {
        return RequestContext.getRequestEnv().shouldUpdateHost();
    }

    public static String getRelocatedHost(String str, boolean z) throws CloudServerException {
        updateMiCloudHosts(z);
        String hostWithScheme = getHostWithScheme(str);
        Log.i("Micloud", "Host with scheme: " + hostWithScheme);
        return hostWithScheme != null ? Uri.parse(hostWithScheme).getHost() : hostWithScheme;
    }

    public static String updateRequestHost(String str, boolean z) throws CloudServerException {
        updateMiCloudHosts(z);
        try {
            XLogger.logi("Micloud", "Original URL: " + str + ". ");
            URL url = new URL(str);
            String hostWithScheme = getHostWithScheme(url.getHost());
            if (!TextUtils.isEmpty(hostWithScheme)) {
                XLogger.logi("Micloud", "New URL: " + hostWithScheme + ". ");
                URL url2 = new URL(hostWithScheme);
                str = new URL(url2.getProtocol(), url2.getHost(), url.getFile()).toString();
            }
        } catch (MalformedURLException e) {
            XLogger.error("Micloud", "MalformedURLException in updateHost %s", e);
        }
        XLogger.logi("Micloud", "Final URL: " + str + ". ");
        return str;
    }

    private static String getHostWithScheme(String str) {
        if (Log.isLoggable("Micloud", 3)) {
            Log.d("Micloud", "Enter getHost key=" + str);
        }
        Object obj = getHostCache().get(str);
        String str2 = (obj == null || !(obj instanceof String)) ? null : (String) obj;
        if (!TextUtils.isEmpty(str2)) {
            if (Log.isLoggable("Micloud", 3)) {
                Log.d("Micloud", "Hit host cache directly return host = " + str2);
            }
            return str2;
        }
        String cachedHostList = RelocationCacheCompat.getCachedHostList(RequestContext.getContext());
        if (TextUtils.isEmpty(cachedHostList)) {
            if (Log.isLoggable("Micloud", 3)) {
                Log.d("Micloud", "Hosts in SystemSettings/sp = null, return null");
            }
            return null;
        }
        try {
            updateHostCache(JsonUtils.jsonToMap(new JSONObject(cachedHostList)));
            Object obj2 = getHostCache().get(str);
            if (obj2 != null && (obj2 instanceof String)) {
                str2 = (String) obj2;
            }
            if (Log.isLoggable("Micloud", 3)) {
                Log.d("Micloud", "find host in SystemSettings/sp return host = " + str2);
            }
            return str2;
        } catch (JSONException e) {
            Log.e("Micloud", "JSONException in getHost, return null", e);
            return null;
        }
    }
}
