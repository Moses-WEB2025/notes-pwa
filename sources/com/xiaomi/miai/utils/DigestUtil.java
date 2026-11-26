package com.xiaomi.miai.utils;

import com.xiaomi.miai.SDKConstants;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: classes4.dex */
public class DigestUtil {
    private static final int STREAM_BUFFER_LENGTH = 1024;

    private DigestUtil() {
    }

    public static MessageDigest getDigest(String str) {
        try {
            return MessageDigest.getInstance(str);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static byte[] digest(MessageDigest messageDigest, byte[] bArr) {
        return messageDigest.digest(bArr);
    }

    public static byte[] digest(MessageDigest messageDigest, InputStream inputStream) throws IOException {
        return updateDigest(messageDigest, inputStream).digest();
    }

    public static MessageDigest updateDigest(MessageDigest messageDigest, byte[] bArr) {
        messageDigest.update(bArr);
        return messageDigest;
    }

    public static MessageDigest updateDigest(MessageDigest messageDigest, InputStream inputStream) throws IOException {
        byte[] bArr = new byte[1024];
        int i = inputStream.read(bArr, 0, 1024);
        while (i > -1) {
            messageDigest.update(bArr, 0, i);
            i = inputStream.read(bArr, 0, 1024);
        }
        return messageDigest;
    }

    public static int adler32(String str) {
        return adler32(StringUtil.utf8Bytes(str));
    }

    public static int adler32(byte[] bArr) {
        int i = 1;
        int i2 = 0;
        for (byte b : bArr) {
            i = (i + b) % 65521;
            i2 = (i2 + i) % 65521;
        }
        return (i2 << 16) | i;
    }

    public static byte[] md5(byte[] bArr) {
        return getDigest("MD5").digest(bArr);
    }

    public static String md5Hex(byte[] bArr) {
        return Hex.encode(md5(bArr));
    }

    public static byte[] md5(String str) {
        return md5(StringUtil.utf8Bytes(str));
    }

    public static String md5Hex(String str) {
        return md5Hex(StringUtil.utf8Bytes(str));
    }

    public static byte[] sha1(byte[] bArr) {
        return getDigest("SHA-1").digest(bArr);
    }

    public static String sha1Hex(byte[] bArr) {
        return Hex.encode(sha1(bArr));
    }

    public static byte[] sha1(String str) {
        return sha1(StringUtil.utf8Bytes(str));
    }

    public static String sha1Hex(String str) {
        return sha1Hex(StringUtil.utf8Bytes(str));
    }

    public static byte[] sha256(byte[] bArr) {
        return getDigest(SDKConstants.DigestAlgorithm.SHA_256).digest(bArr);
    }

    public static String sha256Hex(byte[] bArr) {
        return Hex.encode(sha256(bArr));
    }

    public static byte[] sha256(String str) {
        return sha256(StringUtil.utf8Bytes(str));
    }

    public static String sha256Hex(String str) {
        return sha256Hex(StringUtil.utf8Bytes(str));
    }
}
