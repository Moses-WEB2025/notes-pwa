package com.market.sdk.silentupdate;

import android.util.Base64;
import com.xiaomi.verificationsdk.internal.Constants;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes2.dex */
public class SigGenerator {
    private static final String HMAC_SHA256 = "HmacSHA256";
    private static Random RANDOM = new SecureRandom();

    public static String getSignatureString(String str, String str2, String str3, String str4, String str5) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        TreeMap treeMap = new TreeMap();
        treeMap.put("appClientId", str2);
        treeMap.put(Constants.NONCE, str);
        treeMap.put("id", str3);
        treeMap.put("ref", str4);
        return getSignatureString(str5, treeMap);
    }

    private static String getSignatureString(String str, TreeMap<String, String> treeMap) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : treeMap.entrySet()) {
            String key = entry.getKey();
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(key + "=" + URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        sb.append(StringUtils.LF);
        return encodeSign(encryptHMACSha256(sb.toString().getBytes("UTF-8"), str.getBytes("UTF-8")));
    }

    public static String generateNonce() {
        return RANDOM.nextLong() + ":" + ((int) (System.currentTimeMillis() / 60000));
    }

    private static String encodeSign(byte[] bArr) {
        try {
            return new String(Base64.encode(bArr, 9), "UTF-8").trim();
        } catch (UnsupportedEncodingException unused) {
            return null;
        }
    }

    private static byte[] encryptHMACSha256(byte[] bArr, byte[] bArr2) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr2, HMAC_SHA256);
        Mac mac = Mac.getInstance(HMAC_SHA256);
        mac.init(secretKeySpec);
        mac.update(bArr);
        return mac.doFinal();
    }

    private static class NameValuePair {
        private String name;
        private String value;

        private NameValuePair() {
        }

        public String getName() {
            return this.name;
        }

        public void setName(String str) {
            this.name = str;
        }

        public String getValue() {
            return this.value;
        }

        public void setValue(String str) {
            this.value = str;
        }
    }
}
