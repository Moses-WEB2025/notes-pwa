package com.xiaomi.onetrack.d;

import com.xiaomi.onetrack.util.r;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

/* JADX INFO: loaded from: classes4.dex */
public class e {
    public static final String a = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCiH0r18h2G+lOzZz0mSZT9liZY\r6ibWUv/biAioduf0zuRbWUYGb3pHobyCOaw2LpVnlf8CeCYtbRJhxL9skOyoU1Qa\rwGtoJzvVR4GbCo1MBTmZ8XThMprr0unRfzsu9GNV4+twciOdS2cNJB7INcwAYBFQ\r9vKpgXFoEjWRhIgwMwIDAQAB\r";
    private static final String b = "RsaUtils";
    private static final String c = "RSA/ECB/PKCS1Padding";
    private static final String d = "BC";
    private static final String e = "RSA";

    public static byte[] a(byte[] bArr) throws Exception {
        try {
            RSAPublicKey rSAPublicKeyA = a(a);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
            cipher.init(1, rSAPublicKeyA);
            return cipher.doFinal(bArr);
        } catch (Exception e2) {
            r.b(r.a(b), "RsaUtils encrypt exception:", e2);
            return null;
        }
    }

    public static byte[] a(byte[] bArr, byte[] bArr2) {
        try {
            RSAPublicKey rSAPublicKeyB = b(bArr);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(1, rSAPublicKeyB);
            return cipher.doFinal(bArr2);
        } catch (Exception e2) {
            r.b(b, "RsaUtil encrypt exception:", e2);
            return null;
        }
    }

    private static RSAPublicKey a(String str) throws Exception {
        return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(c.a(str)));
    }

    private static RSAPublicKey b(byte[] bArr) throws Exception {
        return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(bArr));
    }
}
