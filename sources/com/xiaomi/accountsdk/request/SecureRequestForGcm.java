package com.xiaomi.accountsdk.request;

import com.xiaomi.accountsdk.utils.AESCoderForGcm;
import com.xiaomi.accountsdk.utils.CryptCoder;
import com.xiaomi.accountsdk.utils.SignatureCoder;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class SecureRequestForGcm {
    public static SimpleRequest.StringContent getAsString(String str, Map<String, String> map, Map<String, String> map2, boolean z, String str2) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        return getAsString(str, map, map2, z, str2, null, null, null);
    }

    public static SimpleRequest.StringContent getAsString(String str, Map<String, String> map, Map<String, String> map2, boolean z, String str2, CryptCoder cryptCoder) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        return getAsString(str, map, map2, z, str2, cryptCoder, null, null);
    }

    public static SimpleRequest.StringContent getAsString(String str, Map<String, String> map, Map<String, String> map2, boolean z, String str2, CryptCoder cryptCoder, Integer num) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        return getAsString(str, map, map2, z, str2, cryptCoder, num, null);
    }

    public static SimpleRequest.StringContent getAsString(String str, Map<String, String> map, Map<String, String> map2, boolean z, String str2, CryptCoder cryptCoder, Integer num, Map<String, String> map3) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        if (cryptCoder == null) {
            cryptCoder = new AESCoderForGcm(AESCoderForGcm.AES_GCM_ALGORITHM, AESCoderForGcm.AES_PROVIDER_BC, str2);
        }
        return processStringResponse(SimpleRequest.getAsString(str, encryptParams(PassportSimpleRequest.HTTP_METHOD_GET, str, map, str2, cryptCoder), map3, map2, z, num, new RequestWithVersionCodeControl.VersionTagInfo.Builder().setSecurity(str2).build()), str2, cryptCoder);
    }

    public static SimpleRequest.MapContent getAsMap(String str, Map<String, String> map, Map<String, String> map2, boolean z, String str2) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        return getAsMap(str, map, map2, z, str2, null, null, null);
    }

    public static SimpleRequest.MapContent getAsMap(String str, Map<String, String> map, Map<String, String> map2, boolean z, String str2, CryptCoder cryptCoder) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        return getAsMap(str, map, map2, z, str2, cryptCoder, null, null);
    }

    public static SimpleRequest.MapContent getAsMap(String str, Map<String, String> map, Map<String, String> map2, boolean z, String str2, CryptCoder cryptCoder, Integer num) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        return getAsMap(str, map, map2, z, str2, cryptCoder, num, null);
    }

    public static SimpleRequest.MapContent getAsMap(String str, Map<String, String> map, Map<String, String> map2, boolean z, String str2, CryptCoder cryptCoder, Integer num, Map<String, String> map3) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        return SimpleRequest.convertStringToMap(getAsString(str, map, map2, z, str2, cryptCoder, num, map3));
    }

    public static SimpleRequest.StringContent postAsString(String str, Map<String, String> map, Map<String, String> map2, boolean z, String str2) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        return postAsString(str, map, map2, z, str2, null, null, null);
    }

    public static SimpleRequest.StringContent postAsString(String str, Map<String, String> map, Map<String, String> map2, boolean z, String str2, CryptCoder cryptCoder) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        return postAsString(str, map, map2, z, str2, cryptCoder, null, null);
    }

    public static SimpleRequest.StringContent postAsString(String str, Map<String, String> map, Map<String, String> map2, boolean z, String str2, CryptCoder cryptCoder, Integer num) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        return postAsString(str, map, map2, z, str2, cryptCoder, num, null);
    }

    public static SimpleRequest.StringContent postAsString(String str, Map<String, String> map, Map<String, String> map2, boolean z, String str2, CryptCoder cryptCoder, Integer num, Map<String, String> map3) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        CryptCoder aESCoderForGcm = cryptCoder == null ? new AESCoderForGcm(AESCoderForGcm.AES_GCM_ALGORITHM, AESCoderForGcm.AES_PROVIDER_BC, str2) : cryptCoder;
        Map<String, String> mapEncryptParams = encryptParams(PassportSimpleRequest.HTTP_METHOD_POST, str, map, str2, aESCoderForGcm);
        Map<String, String> map4 = map3 == null ? new HashMap() : map3;
        map4.put("EVer", "V2");
        return processStringResponse(SimpleRequest.postAsString(str, mapEncryptParams, map2, map4, (Map<String, String>) null, z, num, new RequestWithVersionCodeControl.VersionTagInfo.Builder().setSecurity(str2).build()), str2, aESCoderForGcm);
    }

    public static SimpleRequest.MapContent postAsMap(String str, Map<String, String> map, Map<String, String> map2, boolean z, String str2) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        return postAsMap(str, map, map2, z, str2, null, null, null);
    }

    public static SimpleRequest.MapContent postAsMap(String str, Map<String, String> map, Map<String, String> map2, boolean z, String str2, CryptCoder cryptCoder) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        return postAsMap(str, map, map2, z, str2, cryptCoder, null, null);
    }

    public static SimpleRequest.MapContent postAsMap(String str, Map<String, String> map, Map<String, String> map2, boolean z, String str2, CryptCoder cryptCoder, Integer num) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        return postAsMap(str, map, map2, z, str2, cryptCoder, num, null);
    }

    public static SimpleRequest.MapContent postAsMap(String str, Map<String, String> map, Map<String, String> map2, boolean z, String str2, CryptCoder cryptCoder, Integer num, Map<String, String> map3) throws AccessDeniedException, CipherException, IOException, InvalidResponseException, AuthenticationFailureException {
        return SimpleRequest.convertStringToMap(postAsString(str, map, map2, z, str2, cryptCoder, num, map3));
    }

    private static SimpleRequest.StringContent processStringResponse(SimpleRequest.StringContent stringContent, String str, CryptCoder cryptCoder) throws CipherException, IOException, InvalidResponseException {
        if (stringContent == null) {
            throw new IOException("no response from server");
        }
        String body = stringContent.getBody();
        if (body == null) {
            throw new InvalidResponseException("invalid response from server");
        }
        SimpleRequest.StringContent stringContent2 = new SimpleRequest.StringContent(decryptResponse(body, cryptCoder));
        stringContent2.putHeaders(stringContent.getHeaders());
        stringContent2.setHttpCode(stringContent.getHttpCode());
        return stringContent2;
    }

    public static Map<String, String> encryptParams(String str, String str2, Map<String, String> map, String str3, CryptCoder cryptCoder) throws CipherException {
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
        map2.put("signature", SignatureCoder.generateSignature(str, str2, map2, str3));
        return map2;
    }

    public static Map<String, String> encryptParams(String str, String str2, Map<String, String> map, String str3) throws CipherException {
        return encryptParams(str, str2, map, str3, new AESCoderForGcm(AESCoderForGcm.AES_GCM_ALGORITHM, AESCoderForGcm.AES_PROVIDER_BC, str3));
    }

    private static String decryptResponse(String str, CryptCoder cryptCoder) throws CipherException, InvalidResponseException {
        if (cryptCoder == null) {
            throw new CipherException("no invalid coder");
        }
        try {
            return cryptCoder.decrypt(str);
        } catch (CipherException e) {
            throw new InvalidResponseException("failed to decrypt response", e);
        }
    }
}
