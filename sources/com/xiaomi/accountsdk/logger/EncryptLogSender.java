package com.xiaomi.accountsdk.logger;

import android.util.Base64;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/* JADX INFO: loaded from: classes4.dex */
public class EncryptLogSender implements XLogger.LogSender {
    private static final String ASYM_ENCRYPT_ALGORITHM = "RSA";
    private static final String ASYM_ENCRYPT_ALGORITHM_USAGE = "RSA/ECB/PKCS1Padding";
    private static final String DEFAULT_ASYM_ENCRYPT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCV2gFH5NQcd1hRT5vRTGweHQZhYEtQgF821nVymHNaeSVSTEecTPpAP71djJlR7Fv9Hfaiy3ubkbre0VwFm7gbWkl4RXrEnTcOClXWUSb+lHfpPK0TmVoooYzL9ySVls8Y9U9PfF1RhgaEU0zjyewdYfGolgS/s9VK/TuLCP+YQwIDAQAB";
    private static final String DEFAULT_ASYM_ENCRYPT_PUBLIC_KEY_LABEL = "";
    private static final byte[] DEFAULT_SYM_ENCRYPT_ALGORITHM_IV = "\u0000\u0000\u0000\u0000\u0000\u0000xiaomi.com".getBytes();
    private static final String SYM_ENCRYPT_ALGORITHM = "AES";
    private static final int SYM_ENCRYPT_ALGORITHM_IV_LEN = 16;
    private static final String SYM_ENCRYPT_ALGORITHM_USAGE = "AES/CBC/PKCS5Padding";
    private static final int SYM_ENCRYPT_KEY_LEN_IN_BITS = 256;
    private String mEncryptedSecrectKey;
    private String mKeyLabel;
    private boolean mRandomIv;
    private SecureRandom mRandomIvGenerator = new SecureRandom();
    private SecretKey mSecretKey;
    private XLogger.LogSender mWrappedLogSender;

    public static EncryptLogSender getWithDefaultPublicKey(XLogger.LogSender logSender) {
        return new EncryptLogSender(logSender, DEFAULT_ASYM_ENCRYPT_PUBLIC_KEY, "", false);
    }

    public EncryptLogSender(XLogger.LogSender logSender, String str, String str2, boolean z) {
        this.mWrappedLogSender = logSender;
        this.mKeyLabel = str2;
        this.mRandomIv = z;
        byte[] bArrDecode = Base64.decode(str, 0);
        try {
            try {
                PublicKey publicKeyGeneratePublic = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(bArrDecode));
                if (!publicKeyGeneratePublic.getAlgorithm().equals("RSA")) {
                    throw new IllegalArgumentException(String.format("The input publicKey is not a %s public key. ", "RSA"));
                }
                try {
                    KeyGenerator keyGenerator = KeyGenerator.getInstance(SYM_ENCRYPT_ALGORITHM);
                    keyGenerator.init(256);
                    this.mSecretKey = keyGenerator.generateKey();
                    try {
                        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
                        cipher.init(1, publicKeyGeneratePublic);
                        try {
                            this.mEncryptedSecrectKey = Base64.encodeToString(cipher.doFinal(this.mSecretKey.getEncoded()), 2);
                        } catch (BadPaddingException e) {
                            throw new RuntimeException("Should never happen. ", e);
                        } catch (IllegalBlockSizeException e2) {
                            throw new RuntimeException("Should never happen. ", e2);
                        }
                    } catch (InvalidKeyException e3) {
                        throw new IllegalArgumentException("The input publicKey is not valid. ", e3);
                    } catch (NoSuchAlgorithmException e4) {
                        throw new RuntimeException(e4);
                    } catch (NoSuchPaddingException e5) {
                        throw new RuntimeException(e5);
                    }
                } catch (NoSuchAlgorithmException e6) {
                    throw new RuntimeException(e6);
                }
            } catch (InvalidKeySpecException e7) {
                throw new IllegalArgumentException("The input publicKey is not valid. ", e7);
            }
        } catch (NoSuchAlgorithmException e8) {
            throw new RuntimeException(e8);
        }
    }

    @Override // com.xiaomi.accountsdk.logger.XLogger.LogSender
    public void sendLog(int i, String str, String str2) {
        try {
            if (this.mRandomIv) {
                byte[] bArrGenerateRandomIv = generateRandomIv();
                str2 = String.format("#&^%s@%s!%s!%s^&#", this.mEncryptedSecrectKey, Base64.encodeToString(bArrGenerateRandomIv, 2), this.mKeyLabel, encryptString(str2, bArrGenerateRandomIv));
            } else {
                str2 = String.format("#&^%s!%s!%s^&#", this.mEncryptedSecrectKey, this.mKeyLabel, encryptString(str2, DEFAULT_SYM_ENCRYPT_ALGORITHM_IV));
            }
        } catch (Exception e) {
            this.mWrappedLogSender.sendLog(6, getClass().getName(), String.format("Failed to encrypt the message: %s. ", e));
        }
        this.mWrappedLogSender.sendLog(i, str, str2);
    }

    private String encryptString(String str, byte[] bArr) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            try {
                cipher.init(1, this.mSecretKey, new IvParameterSpec(bArr));
                try {
                    return Base64.encodeToString(cipher.doFinal(str.getBytes()), 2);
                } catch (BadPaddingException e) {
                    throw new RuntimeException("Should never happen. ", e);
                } catch (IllegalBlockSizeException e2) {
                    throw new RuntimeException("Should never happen. ", e2);
                }
            } catch (InvalidAlgorithmParameterException e3) {
                throw new RuntimeException(e3);
            } catch (InvalidKeyException e4) {
                throw new RuntimeException("Should never happen. ", e4);
            }
        } catch (NoSuchAlgorithmException e5) {
            throw new RuntimeException(e5);
        } catch (NoSuchPaddingException e6) {
            throw new RuntimeException(e6);
        }
    }

    private byte[] generateRandomIv() {
        byte[] bArr = new byte[16];
        this.mRandomIvGenerator.nextBytes(bArr);
        return bArr;
    }
}
