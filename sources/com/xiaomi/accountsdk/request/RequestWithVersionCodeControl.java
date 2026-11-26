package com.xiaomi.accountsdk.request;

import android.content.SharedPreferences;
import android.text.TextUtils;
import com.xiaomi.accountsdk.account.XMPassportSettings;
import com.xiaomi.accountsdk.utils.AccountLogger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
class RequestWithVersionCodeControl {
    private static final String HEADER_RESULT_CODE = "Result-Code";
    private static final String HEADER_VERSION_CODE = "Version-Code";
    private static final String LOCAL_SAVE_PARAMS_DATA = "data";
    private static final String LOCAL_SAVE_PARAMS_SECURITY = "security";
    private static final String LOCAL_SAVE_PARAMS_VERSION_CODE = "version_code";
    private static final String SP_NAME = "sp_name_request_with_version_code";
    private static final String TAG = "RequestWithVersionCodeControl";
    private static SharedPreferences sSp;
    private static final Object sLoadSpLock = new Object();
    private static volatile Set<String> sHasLoadFromSpUrlSet = new HashSet();
    private static final ConcurrentHashMap<String, VersionControlInfo> sSameVersionInfoMap = new ConcurrentHashMap<>();

    RequestWithVersionCodeControl() {
    }

    public static Map<String, String> fillRequestHeadersWithVersionCode(String str, Map<String, String> map, VersionTagInfo versionTagInfo) {
        VersionControlInfo versionInfo;
        String url = formatUrl(str);
        if (TextUtils.isEmpty(url) || (versionInfo = getVersionInfo(url)) == null) {
            return map;
        }
        if (versionTagInfo != null && versionTagInfo.isSecurityRequest()) {
            String str2 = versionTagInfo.security;
            String str3 = versionInfo.security;
            AccountLogger.log(TAG, "fillRequestHeadersWithVersionCode>>>security request:url=" + url + "  newSecurity=" + str2 + "  localSecurity=" + str3);
            if (!TextUtils.equals(str2, str3)) {
                removeDataFromSp(url);
                return map;
            }
        }
        if (map == null) {
            map = new HashMap<>();
        }
        map.put(HEADER_VERSION_CODE, versionInfo.versionCode);
        AccountLogger.log(TAG, "fillRequestHeadersWithVersionCode>>>url=" + url + "  versionCode=" + versionInfo.versionCode);
        return map;
    }

    public static void fillOrCacheRequestResult(String str, SimpleRequest.StringContent stringContent, VersionTagInfo versionTagInfo) {
        String url = formatUrl(str);
        if (TextUtils.isEmpty(url) || stringContent == null) {
            return;
        }
        String header = stringContent.getHeader(HEADER_RESULT_CODE);
        if (TextUtils.isEmpty(header)) {
            return;
        }
        if (header.equals(String.valueOf(0))) {
            String header2 = stringContent.getHeader(HEADER_VERSION_CODE);
            if (TextUtils.isEmpty(header2)) {
                return;
            }
            saveVersionInfoToCacheAndLocal(url, header2, stringContent.getBody(), versionTagInfo);
            return;
        }
        if (header.equals(String.valueOf(304))) {
            VersionControlInfo versionControlInfo = sSameVersionInfoMap.get(url);
            if (versionControlInfo == null || TextUtils.isEmpty(versionControlInfo.data)) {
                AccountLogger.log(TAG, "fillOrCacheRequestResult>>>cache data should not be empty, url=" + url);
            } else {
                AccountLogger.log(TAG, "fillOrCacheRequestResult>>>use cache data, url=" + url);
                stringContent.setBody(versionControlInfo.data);
            }
        }
    }

    private static VersionControlInfo getVersionInfo(String str) {
        VersionControlInfo versionControlInfo = sSameVersionInfoMap.get(str);
        if (versionControlInfo != null) {
            return versionControlInfo;
        }
        if (sHasLoadFromSpUrlSet.contains(str)) {
            return null;
        }
        return loadVersionInfoFromSp(str);
    }

    private static VersionControlInfo loadVersionInfoFromSp(String str) {
        VersionControlInfo versionControlInfoCreateOrNull;
        synchronized (sLoadSpLock) {
            if (sHasLoadFromSpUrlSet.contains(str)) {
                return sSameVersionInfoMap.get(str);
            }
            sHasLoadFromSpUrlSet.add(str);
            try {
                versionControlInfoCreateOrNull = VersionControlInfo.createOrNull(getSp().getString(str, ""));
            } catch (JSONException e) {
                AccountLogger.log(TAG, "loadVersionInfoFromSp>>>bad localData", e);
                removeDataFromSp(str);
                versionControlInfoCreateOrNull = null;
            }
            if (versionControlInfoCreateOrNull == null) {
                return null;
            }
            sSameVersionInfoMap.put(str, versionControlInfoCreateOrNull);
            AccountLogger.log(TAG, "loadVersionInfoFromSp>>>load sp data, url=" + str + "  versionCode=" + versionControlInfoCreateOrNull.versionCode);
            return versionControlInfoCreateOrNull;
        }
    }

    private static void saveVersionInfoToCacheAndLocal(String str, String str2, String str3, VersionTagInfo versionTagInfo) {
        AccountLogger.log(TAG, "saveVersionInfoToCacheAndLocal>>>url=" + str + "  versionCode=" + str2);
        if (TextUtils.isEmpty(str3)) {
            AccountLogger.log(TAG, "saveVersionInfoToCacheAndLocal>>>data should not be empty");
            return;
        }
        ConcurrentHashMap<String, VersionControlInfo> concurrentHashMap = sSameVersionInfoMap;
        VersionControlInfo versionControlInfo = concurrentHashMap.get(str);
        byte b = 0;
        if (versionControlInfo == null) {
            versionControlInfo = new VersionControlInfo();
            concurrentHashMap.put(str, versionControlInfo);
        }
        versionControlInfo.versionCode = str2;
        versionControlInfo.data = str3;
        versionControlInfo.security = versionTagInfo != null ? versionTagInfo.security : null;
        try {
            getSp().edit().putString(str, versionControlInfo.toJsonString()).apply();
        } catch (JSONException e) {
            AccountLogger.log(TAG, "saveVersionInfoToCacheAndLocal>>>info to jsonStr err", e);
        }
    }

    private static void removeDataFromSp(String str) {
        getSp().edit().remove(str).apply();
    }

    private static SharedPreferences getSp() {
        if (sSp == null) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            sSp = XMPassportSettings.getApplicationContext().getSharedPreferences(SP_NAME, 0);
            AccountLogger.log(TAG, "getSp>>>load sp speed time:" + (System.currentTimeMillis() - jCurrentTimeMillis));
        }
        return sSp;
    }

    private static String formatUrl(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        try {
            URL url = new URL(str);
            return url.getProtocol() + "://" + url.getHost() + url.getPath();
        } catch (MalformedURLException e) {
            AccountLogger.log(TAG, "formatUrl>>>format url err,", e);
            return str;
        }
    }

    private static class VersionControlInfo {
        String data;
        String security;
        String versionCode;

        private VersionControlInfo() {
        }

        static VersionControlInfo createOrNull(String str) throws JSONException {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            VersionControlInfo versionControlInfo = new VersionControlInfo();
            JSONObject jSONObject = new JSONObject(str);
            versionControlInfo.versionCode = jSONObject.optString("version_code");
            versionControlInfo.data = jSONObject.optString("data");
            versionControlInfo.security = jSONObject.optString(RequestWithVersionCodeControl.LOCAL_SAVE_PARAMS_SECURITY);
            return versionControlInfo;
        }

        String toJsonString() throws JSONException {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("version_code", this.versionCode);
            jSONObject.put("data", this.data);
            String str = this.security;
            if (str != null) {
                jSONObject.put(RequestWithVersionCodeControl.LOCAL_SAVE_PARAMS_SECURITY, str);
            }
            return jSONObject.toString();
        }
    }

    public static class VersionTagInfo {
        public final String security;

        private VersionTagInfo(String str) {
            this.security = str;
        }

        public boolean isSecurityRequest() {
            return !TextUtils.isEmpty(this.security);
        }

        public static class Builder {
            private String security;

            public Builder setSecurity(String str) {
                this.security = str;
                return this;
            }

            public VersionTagInfo build() {
                return new VersionTagInfo(this.security);
            }
        }
    }
}
