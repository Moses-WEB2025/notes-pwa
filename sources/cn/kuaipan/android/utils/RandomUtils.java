package cn.kuaipan.android.utils;

import java.security.SecureRandom;
import java.util.Random;

/* JADX INFO: loaded from: classes.dex */
public class RandomUtils {
    public static final char[] NORMAL_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
    private static SecureRandom sRandom;

    private static Random getRandomSeed() {
        if (sRandom == null) {
            SecureRandom secureRandom = new SecureRandom();
            sRandom = secureRandom;
            secureRandom.setSeed(SecureRandom.getSeed(128));
        }
        return sRandom;
    }

    public static int getInt() {
        return getRandomSeed().nextInt();
    }

    public static long getLong() {
        return getRandomSeed().nextLong();
    }

    public static String getString(int i) {
        Random randomSeed = getRandomSeed();
        byte[] bArr = new byte[((i / 4) + (i % 4 > 0 ? 1 : 0)) * 3];
        randomSeed.nextBytes(bArr);
        String strEncodeToString = Base64.encodeToString(bArr, 2);
        return strEncodeToString.length() > i ? strEncodeToString.substring(0, i) : strEncodeToString;
    }

    public static String getString(int i, char[] cArr) {
        if (cArr == null) {
            return getString(i);
        }
        Random randomSeed = getRandomSeed();
        char[] cArr2 = new char[i];
        int length = cArr.length;
        for (int i2 = 0; i2 < i; i2++) {
            cArr2[i2] = cArr[randomSeed.nextInt(length)];
        }
        return String.copyValueOf(cArr2);
    }
}
