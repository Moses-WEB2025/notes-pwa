package com.miui.notes.ai.utils;

import android.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes4.dex */
public class CryptoUtils {
    private static final String IV = "ABCDEF1234123412";
    private static final String KEY = "1234123412ABCDEF";

    public static String decrypt(String str) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(2, new SecretKeySpec(KEY.getBytes("UTF-8"), "AES"), new IvParameterSpec(IV.getBytes("UTF-8")));
        return new String(cipher.doFinal(Base64.decode(str.getBytes(), 1)), "UTF-8");
    }

    public static String encrypt(String str) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(1, new SecretKeySpec(KEY.getBytes("UTF-8"), "AES"), new IvParameterSpec(IV.getBytes("UTF-8")));
        return new String(Base64.encode(cipher.doFinal(str.getBytes("UTF-8")), 1));
    }
}
