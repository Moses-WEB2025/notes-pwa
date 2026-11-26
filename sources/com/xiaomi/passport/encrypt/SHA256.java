package com.xiaomi.passport.encrypt;

import com.xiaomi.miai.SDKConstants;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: classes4.dex */
public class SHA256 {
    public static String getAsHexString(String str) {
        return Hex.get(getAsBytes(str));
    }

    public static byte[] getAsBytes(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(SDKConstants.DigestAlgorithm.SHA_256);
            messageDigest.update(str.getBytes());
            return messageDigest.digest();
        } catch (NoSuchAlgorithmException unused) {
            return null;
        }
    }
}
