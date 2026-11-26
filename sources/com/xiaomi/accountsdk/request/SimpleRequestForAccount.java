package com.xiaomi.accountsdk.request;

import android.text.TextUtils;
import com.xiaomi.accountsdk.hasheddeviceidlib.AccountDeviceIdManager;
import com.xiaomi.accountsdk.utils.FidNonce;
import com.xiaomi.accountsdk.utils.UserSpaceIdUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class SimpleRequestForAccount {
    public static final String COOKIE_NAME_DEVICE_ID = "deviceId";
    public static final String COOKIE_NAME_FID_NONCE_PLAIN = "fidNonce";
    public static final String COOKIE_NAME_FID_NONCE_SIGN = "fidNonceSign";
    public static final String COOKIE_NAME_USER_SPACE_ID = "userSpaceId";

    public static SimpleRequest.StringContent getAsString(String str, Map<String, String> map, Map<String, String> map2, boolean z) throws AccessDeniedException, IOException, AuthenticationFailureException {
        return getAsString(str, map, null, map2, z, null);
    }

    public static SimpleRequest.StringContent getAsString(String str, Map<String, String> map, Map<String, String> map2, Map<String, String> map3, boolean z) throws AccessDeniedException, IOException, AuthenticationFailureException {
        return getAsString(str, map, map2, map3, z, null);
    }

    public static SimpleRequest.StringContent getAsString(String str, Map<String, String> map, Map<String, String> map2, Map<String, String> map3, boolean z, Integer num) throws AccessDeniedException, IOException, AuthenticationFailureException {
        return SimpleRequest.getAsString(str, map, map2, addFidNonceInCookieIfNeeded(addDeviceIdInCookieIfNeeded(map3)), z, num);
    }

    public static SimpleRequest.StreamContent getAsStream(String str, Map<String, String> map, Map<String, String> map2) throws AccessDeniedException, IOException, AuthenticationFailureException {
        return SimpleRequest.getAsStream(str, map, addFidNonceInCookieIfNeeded(addDeviceIdInCookieIfNeeded(map2)), null);
    }

    public static SimpleRequest.MapContent getAsMap(String str, Map<String, String> map, Map<String, String> map2, boolean z) throws AccessDeniedException, IOException, AuthenticationFailureException {
        return SimpleRequest.getAsMap(str, map, addFidNonceInCookieIfNeeded(addDeviceIdInCookieIfNeeded(map2)), null, z);
    }

    public static SimpleRequest.StringContent postAsString(String str, Map<String, String> map, Map<String, String> map2, boolean z) throws AccessDeniedException, IOException, AuthenticationFailureException {
        return postAsString(str, map, map2, (Map<String, String>) null, (Map<String, String>) null, z, (Integer) null);
    }

    public static SimpleRequest.StringContent postAsString(String str, Map<String, String> map, Map<String, String> map2, Map<String, String> map3, boolean z) throws AccessDeniedException, IOException, AuthenticationFailureException {
        return postAsString(str, map, map2, map3, (Map<String, String>) null, z, (Integer) null);
    }

    public static SimpleRequest.StringContent postAsString(String str, Map<String, String> map, Map<String, String> map2, Map<String, String> map3, Map<String, String> map4, boolean z, Integer num) throws AccessDeniedException, IOException, AuthenticationFailureException {
        return SimpleRequest.postAsString(str, map, addFidNonceInCookieIfNeeded(addDeviceIdInCookieIfNeeded(map2)), map3, map4, z, num);
    }

    public static SimpleRequest.StringContent postAsString(String str, String str2, Map<String, String> map, Map<String, String> map2, Map<String, String> map3, boolean z, Integer num) throws AccessDeniedException, IOException, AuthenticationFailureException {
        return SimpleRequest.postAsString(str, str2, addFidNonceInCookieIfNeeded(addDeviceIdInCookieIfNeeded(map)), map2, map3, z, num, (RequestWithVersionCodeControl.VersionTagInfo) null);
    }

    public static SimpleRequest.MapContent postAsMap(String str, Map<String, String> map, Map<String, String> map2, boolean z) throws AccessDeniedException, IOException, AuthenticationFailureException {
        return SimpleRequest.postAsMap(str, map, addFidNonceInCookieIfNeeded(addDeviceIdInCookieIfNeeded(map2)), null, z);
    }

    static Map<String, String> addDeviceIdInCookieIfNeeded(Map<String, String> map) {
        return addDeviceIdInCookieIfNeeded(map, AccountDeviceIdManager.getInstance().getDeviceId());
    }

    static Map<String, String> addDeviceIdInCookieIfNeeded(Map<String, String> map, String str) {
        if (map != null && map.containsKey("deviceId")) {
            return map;
        }
        HashMap map2 = map == null ? new HashMap() : new HashMap(map);
        map2.put("deviceId", str);
        String nullableUserSpaceIdCookie = UserSpaceIdUtil.getNullableUserSpaceIdCookie();
        if (!TextUtils.isEmpty(nullableUserSpaceIdCookie)) {
            map2.put("userSpaceId", nullableUserSpaceIdCookie);
        }
        return map2;
    }

    static Map<String, String> addFidNonceInCookieIfNeeded(Map<String, String> map) {
        return addFidNonceInCookieIfNeeded(map, new FidNonce.Builder());
    }

    static Map<String, String> addFidNonceInCookieIfNeeded(Map<String, String> map, FidNonce.Builder builder) {
        FidNonce fidNonceBuild;
        if (map == null || builder == null || !map.containsKey("deviceId") || map.containsKey("fidNonce") || map.containsKey("fidNonceSign") || (fidNonceBuild = builder.build(FidNonce.Type.NATIVE)) == null || TextUtils.isEmpty(fidNonceBuild.plain) || TextUtils.isEmpty(fidNonceBuild.sign)) {
            return map;
        }
        HashMap map2 = new HashMap(map);
        map2.put("fidNonce", fidNonceBuild.plain);
        map2.put("fidNonceSign", fidNonceBuild.sign);
        return map2;
    }
}
