package com.miui.handwriting.common.ai;

import com.xiaomi.accountsdk.utils.AESCoderForGcm;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes3.dex */
public class AESUtil {
    private static final int KEY_SIZE = 256;

    public static void main(String[] strArr) throws Exception {
        SecretKey secretKeyGenerateKey = generateKey();
        GCMParameterSpec gCMParameterSpecGenerateGCMParameterSpec = generateGCMParameterSpec();
        System.out.println(Base64.getEncoder().encodeToString(secretKeyGenerateKey.getEncoded()));
        System.out.println(Base64.getEncoder().encodeToString(gCMParameterSpecGenerateGCMParameterSpec.getIV()));
        byte[] bArrEncrypt = encrypt("Hello, World!".getBytes(), secretKeyGenerateKey, gCMParameterSpecGenerateGCMParameterSpec);
        System.out.println("Encrypted: ".concat(new String(bArrEncrypt)));
        System.out.println("Decrypted: ".concat(new String(decrypt(bArrEncrypt, secretKeyGenerateKey, gCMParameterSpecGenerateGCMParameterSpec))));
    }

    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        return keyGenerator.generateKey();
    }

    public static SecretKey loadKey(byte[] bArr) {
        return new SecretKeySpec(bArr, "AES");
    }

    public static byte[] encrypt(byte[] bArr, SecretKey secretKey, GCMParameterSpec gCMParameterSpec) throws Exception {
        Cipher cipher = Cipher.getInstance(AESCoderForGcm.AES_GCM_ALGORITHM);
        cipher.init(1, secretKey, gCMParameterSpec);
        return cipher.doFinal(bArr);
    }

    public static byte[] decrypt(byte[] bArr, SecretKey secretKey, GCMParameterSpec gCMParameterSpec) throws Exception {
        Cipher cipher = Cipher.getInstance(AESCoderForGcm.AES_GCM_ALGORITHM);
        cipher.init(2, secretKey, gCMParameterSpec);
        return cipher.doFinal(bArr);
    }

    public static GCMParameterSpec generateGCMParameterSpec() throws Exception {
        byte[] bArr = new byte[12];
        new SecureRandom().nextBytes(bArr);
        return new GCMParameterSpec(128, bArr);
    }

    public static GCMParameterSpec loadGCMParameterSpec(byte[] bArr) {
        return new GCMParameterSpec(128, bArr);
    }
}
