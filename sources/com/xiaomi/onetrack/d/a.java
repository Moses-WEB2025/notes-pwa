package com.xiaomi.onetrack.d;

import android.util.Base64;
import com.xiaomi.onetrack.util.o;
import com.xiaomi.onetrack.util.r;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes4.dex */
public class a {
    public static final String a = b();
    private static final String b = "AES";
    private static final String c = "AES/ECB/PKCS5Padding";
    private static final String d = "AES";
    private static KeyGenerator e;

    static {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            e = keyGenerator;
            keyGenerator.init(128);
        } catch (Exception e2) {
            r.b(r.a("AES"), "AesUtil e", e2);
        }
    }

    public static byte[] a() {
        return e.generateKey().getEncoded();
    }

    public static byte[] a(byte[] bArr, byte[] bArr2) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(bArr2, "AES");
            Cipher cipher = Cipher.getInstance(c);
            cipher.init(1, secretKeySpec);
            return cipher.doFinal(bArr);
        } catch (Exception e2) {
            r.b(r.a("AES"), "encrypt exception:", e2);
            return null;
        }
    }

    private static byte[] e(String str, String str2) {
        return a(str.getBytes(), str2.getBytes());
    }

    private static byte[] a(byte[] bArr, String str) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(c(str), "AES");
            Cipher cipher = Cipher.getInstance(c);
            cipher.init(2, secretKeySpec);
            return cipher.doFinal(bArr);
        } catch (Exception e2) {
            r.b(r.a("AES"), "decrypt exception:", e2);
            return null;
        }
    }

    public static byte[] b(byte[] bArr, byte[] bArr2) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(bArr2, "AES");
            Cipher cipher = Cipher.getInstance(c);
            cipher.init(2, secretKeySpec);
            return cipher.doFinal(bArr);
        } catch (Exception e2) {
            r.b("AES", "decrypt exception:", e2);
            r.b("AES", "content len=" + bArr.length + ", passwd len=" + bArr2.length);
            return null;
        }
    }

    private static byte[] c(String str) {
        if (str != null) {
            return str.getBytes();
        }
        return null;
    }

    private static byte[] d(String str) {
        if (str == null || str.length() < 1) {
            return null;
        }
        byte[] bArr = new byte[str.length() / 2];
        for (int i = 0; i < str.length() / 2; i++) {
            int i2 = i * 2;
            int i3 = i2 + 1;
            bArr[i] = (byte) ((Integer.parseInt(str.substring(i2, i3), 16) * 16) + Integer.parseInt(str.substring(i3, i2 + 2), 16));
        }
        return bArr;
    }

    public static String a(String str, String str2) {
        return o.a(e(str, str2));
    }

    public static String b(String str, String str2) {
        return Base64.encodeToString(e(str, str2), 10);
    }

    public static String c(String str, String str2) {
        return new String(a(d(str), str2));
    }

    public static String d(String str, String str2) {
        return new String(a(Base64.decode(str, 10), str2));
    }

    public static String a(String str) {
        try {
            char[] charArray = (str + b.a).toCharArray();
            for (int i = 0; i < charArray.length; i++) {
                for (int i2 = 0; i2 < charArray.length - 1; i2++) {
                    char c2 = charArray[i];
                    char c3 = charArray[i2];
                    if (c2 < c3) {
                        charArray[i] = c3;
                        charArray[i2] = c2;
                    }
                }
            }
            return d.h(new String(charArray));
        } catch (Exception unused) {
            r.b("AES", "encodeFromSalt ");
            return "";
        }
    }

    private static String b() {
        try {
            return new String("6AiSfshj3pD/9r91".getBytes(), "UTF-8");
        } catch (Exception e2) {
            r.b("AES", e2.getMessage());
            return "";
        }
    }

    public static byte[] b(String str) {
        return a(str.getBytes(), d.a(c.a(), true).getBytes());
    }

    public static String a(byte[] bArr) {
        return new String(b(bArr, d.a(c.a(), true).getBytes()));
    }
}
