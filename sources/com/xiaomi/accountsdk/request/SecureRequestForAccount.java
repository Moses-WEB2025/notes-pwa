package com.xiaomi.accountsdk.request;

import com.xiaomi.accountsdk.utils.CryptCoder;
import java.io.IOException;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class SecureRequestForAccount {
    public static SimpleRequest.StringContent getAsString(String str, Map<String, String> map, Map<String, String> map2, boolean z, String str2) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        return getAsString(str, map, map2, z, str2, null, null);
    }

    public static SimpleRequest.StringContent getAsString(String str, Map<String, String> map, Map<String, String> map2, boolean z, String str2, CryptCoder cryptCoder) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        return getAsString(str, map, map2, z, str2, cryptCoder, null);
    }

    public static SimpleRequest.StringContent getAsString(String str, Map<String, String> map, Map<String, String> map2, boolean z, String str2, CryptCoder cryptCoder, Integer num) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        return SecureRequest.getAsString(str, map, SimpleRequestForAccount.addFidNonceInCookieIfNeeded(SimpleRequestForAccount.addDeviceIdInCookieIfNeeded(map2)), z, str2, cryptCoder, num, null);
    }

    public static SimpleRequest.MapContent getAsMap(String str, Map<String, String> map, Map<String, String> map2, boolean z, String str2) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        return getAsMap(str, map, map2, z, str2, null);
    }

    public static SimpleRequest.MapContent getAsMap(String str, Map<String, String> map, Map<String, String> map2, boolean z, String str2, CryptCoder cryptCoder) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        Map<String, String> mapAddFidNonceInCookieIfNeeded = SimpleRequestForAccount.addFidNonceInCookieIfNeeded(SimpleRequestForAccount.addDeviceIdInCookieIfNeeded(map2));
        return SecureRequest.getAsMap(str, map, mapAddFidNonceInCookieIfNeeded, z, str2, cryptCoder, null, null);
    }

    public static SimpleRequest.MapContent postAsMap(String str, Map<String, String> map, Map<String, String> map2, boolean z, String str2) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        return postAsMap(str, map, map2, z, str2, null);
    }

    public static SimpleRequest.MapContent postAsMap(String str, Map<String, String> map, Map<String, String> map2, boolean z, String str2, CryptCoder cryptCoder) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        Map<String, String> mapAddFidNonceInCookieIfNeeded = SimpleRequestForAccount.addFidNonceInCookieIfNeeded(SimpleRequestForAccount.addDeviceIdInCookieIfNeeded(map2));
        return SecureRequest.postAsMap(str, map, mapAddFidNonceInCookieIfNeeded, z, str2, cryptCoder, null, null);
    }

    public static SimpleRequest.MapContent postAsMap(String str, Map<String, String> map, Map<String, String> map2, boolean z, String str2, CryptCoder cryptCoder, Map<String, String> map3) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        Map<String, String> mapAddFidNonceInCookieIfNeeded = SimpleRequestForAccount.addFidNonceInCookieIfNeeded(SimpleRequestForAccount.addDeviceIdInCookieIfNeeded(map2));
        return SecureRequest.postAsMap(str, map, mapAddFidNonceInCookieIfNeeded, z, str2, cryptCoder, null, map3);
    }

    public static SimpleRequest.StringContent postAsString(String str, Map<String, String> map, Map<String, String> map2, boolean z, String str2) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        Map<String, String> mapAddFidNonceInCookieIfNeeded = SimpleRequestForAccount.addFidNonceInCookieIfNeeded(SimpleRequestForAccount.addDeviceIdInCookieIfNeeded(map2));
        return SecureRequest.postAsString(str, map, mapAddFidNonceInCookieIfNeeded, z, str2, null, null, null);
    }
}
