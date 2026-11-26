package com.miui.maml.util.net;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import kotlin.text.Typography;

/* JADX INFO: loaded from: classes3.dex */
public class CloudCoder {
    private static final Integer INT_0 = 0;
    private static final String RC4_ALGORITHM_NAME = "RC4";

    public static Cipher newAESCipher(String str, int i) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.decode(str, 2), "AES");
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(i, secretKeySpec, new IvParameterSpec("0102030405060708".getBytes()));
            return cipher;
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e2) {
            e2.printStackTrace();
            return null;
        } catch (NoSuchAlgorithmException e3) {
            e3.printStackTrace();
            return null;
        } catch (NoSuchPaddingException e4) {
            e4.printStackTrace();
            return null;
        }
    }

    public static String hash4SHA1(String str) {
        try {
            return Base64.encodeToString(MessageDigest.getInstance("SHA1").digest(str.getBytes("UTF-8")), 2);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new IllegalStateException("failed to SHA1");
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
            throw new IllegalStateException("failed to SHA1");
        }
    }

    public static String generateSignature(String str, String str2, Map<String, String> map, String str3) {
        if (TextUtils.isEmpty(str3)) {
            throw new InvalidParameterException("security is not nullable");
        }
        ArrayList<String> arrayList = new ArrayList();
        if (str != null) {
            arrayList.add(str.toUpperCase());
        }
        if (str2 != null) {
            arrayList.add(Uri.parse(str2).getEncodedPath());
        }
        if (map != null && !map.isEmpty()) {
            for (Map.Entry entry : new TreeMap(map).entrySet()) {
                arrayList.add(String.format("%s=%s", entry.getKey(), entry.getValue()));
            }
        }
        arrayList.add(str3);
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        for (String str4 : arrayList) {
            if (!z) {
                sb.append(Typography.amp);
            }
            sb.append(str4);
            z = false;
        }
        return hash4SHA1(sb.toString());
    }
}
