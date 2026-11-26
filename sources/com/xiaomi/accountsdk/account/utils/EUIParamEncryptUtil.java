package com.xiaomi.accountsdk.account.utils;

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;
import com.xiaomi.accountsdk.account.XMPassport;
import com.xiaomi.accountsdk.account.XMPassportSettings;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.CipherException;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import com.xiaomi.accountsdk.utils.AESCoder;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.passport.encrypt.SHA256;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import miuix.internal.hybrid.SignUtils;

/* JADX INFO: loaded from: classes4.dex */
public class EUIParamEncryptUtil {
    private static final String LAST_FETCH_TIME_KEY = "lastFetchTime";
    private static final long PUBLIC_KEY_CACHE_DURATION = 86400000;
    private static final String PUBLIC_KEY_KEY = "rsaPublicKey";
    private static final String RSA_ENCRYPT_TRANSFORMATION = "RSA/ECB/PKCS1Padding";
    public static final String SP_RSA_PUBLIC_KEY = "sp_rsa_public_key";
    private static final String SYMMETRIC_ALGORITHM = "AES";
    private static final String TAG = "EUIParamEncryptUtil";
    private static volatile SecretKey mAesKey;
    private static volatile String mEncryptedKey;
    private static volatile PublicKey mRsaPublicKey;
    private static SharedPreferences sharedPreferences;

    public static class EncryptResult {
        public String content;
    }

    public static EncryptResult encrypt(String str) throws EncryptException {
        EncryptResult encryptResult = new EncryptResult();
        if (mAesKey == null || mEncryptedKey == null || !isPublicKeyValid()) {
            synchronized (EUIParamEncryptUtil.class) {
                if (mAesKey == null || mEncryptedKey == null || !isPublicKeyValid()) {
                    mAesKey = generateSymmetricKey();
                    mEncryptedKey = rsaEncrypt(Base64.encodeToString(mAesKey.getEncoded(), 10), getRsaPublicKey());
                }
            }
        }
        if (str == null) {
            return null;
        }
        encryptResult.content = aesEncrypt(str, mAesKey);
        return encryptResult;
    }

    private static PublicKey getRsaPublicKey() throws EncryptException {
        if (mRsaPublicKey != null && isPublicKeyValid()) {
            return mRsaPublicKey;
        }
        String publicKeyFromSP = getPublicKeyFromSP();
        if (publicKeyFromSP != null) {
            try {
                mRsaPublicKey = convertStringToPublicKey(publicKeyFromSP);
                return mRsaPublicKey;
            } catch (Exception e) {
                throw new EncryptException(e);
            }
        }
        try {
            String rsaPublicKey = XMPassport.getRsaPublicKey();
            mRsaPublicKey = convertStringToPublicKey(rsaPublicKey);
            savePublicKeyToSP(rsaPublicKey);
            return mRsaPublicKey;
        } catch (AccessDeniedException | AuthenticationFailureException | InvalidResponseException | IOException e2) {
            throw new EncryptException(e2);
        }
    }

    public static void invalidPublicKey() {
        SharedPreferences.Editor editorEdit = getSp().edit();
        editorEdit.remove(PUBLIC_KEY_KEY);
        editorEdit.remove(LAST_FETCH_TIME_KEY);
        editorEdit.apply();
        mAesKey = null;
        mEncryptedKey = null;
    }

    private static boolean isPublicKeyValid() {
        return System.currentTimeMillis() - getSp().getLong(LAST_FETCH_TIME_KEY, 0L) < 86400000;
    }

    private static void savePublicKeyToSP(String str) {
        SharedPreferences.Editor editorEdit = getSp().edit();
        editorEdit.putLong(LAST_FETCH_TIME_KEY, System.currentTimeMillis());
        editorEdit.putString(PUBLIC_KEY_KEY, str);
        editorEdit.apply();
    }

    private static String getPublicKeyFromSP() {
        if (isPublicKeyValid()) {
            return getSp().getString(PUBLIC_KEY_KEY, null);
        }
        return null;
    }

    private static PublicKey convertStringToPublicKey(String str) throws InvalidResponseException {
        try {
            return (RSAPublicKey) KeyFactory.getInstance(SignUtils.KEY_RSA).generatePublic(new X509EncodedKeySpec(Base64.decode(str, 0)));
        } catch (Exception e) {
            throw new InvalidResponseException("Error while converting string to PublicKey", e);
        }
    }

    private static SecretKey generateSymmetricKey() throws EncryptException {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(SYMMETRIC_ALGORITHM);
            keyGenerator.init(128, new SecureRandom());
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptException(e);
        }
    }

    private static String aesEncrypt(String str, SecretKey secretKey) throws EncryptException {
        try {
            return new AESCoder(secretKey.getEncoded()).encrypt(str);
        } catch (CipherException e) {
            throw new EncryptException(e);
        }
    }

    private static String rsaEncrypt(String str, PublicKey publicKey) throws EncryptException {
        return encrypt(str, publicKey, "RSA/ECB/PKCS1Padding");
    }

    private static String encrypt(String str, Key key, String str2) throws EncryptException {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        return new String(Base64.encode(encrypt(str.getBytes(StandardCharsets.UTF_8), key, str2), 10), StandardCharsets.UTF_8);
    }

    private static byte[] encrypt(byte[] bArr, Key key, String str) throws EncryptException {
        try {
            Cipher cipher = Cipher.getInstance(str);
            cipher.init(1, key);
            return cipher.doFinal(bArr);
        } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            throw new EncryptException(e);
        }
    }

    public static class EncryptException extends Exception {
        public EncryptException(Throwable th) {
            super(th);
        }
    }

    private static SharedPreferences getSp() {
        if (sharedPreferences == null) {
            sharedPreferences = XMPassportSettings.getApplicationContext().getSharedPreferences(SP_RSA_PUBLIC_KEY, 0);
        }
        return sharedPreferences;
    }

    public static String encryptParam(String str) {
        EncryptResult encryptResultEncrypt;
        try {
            encryptResultEncrypt = encrypt(str);
        } catch (EncryptException e) {
            AccountLogger.log(TAG, "EUIParamEncryptUtil>>encryptParam", e);
            encryptResultEncrypt = null;
        }
        return encryptResultEncrypt == null ? str : encryptResultEncrypt.content;
    }

    public static String generateEUI(String... strArr) {
        if (strArr.length % 2 != 0) {
            throw new IllegalArgumentException("Parameters should be in pairs of [name, value].");
        }
        LinkedList linkedList = new LinkedList();
        LinkedList linkedList2 = new LinkedList();
        for (int i = 0; i < strArr.length; i += 2) {
            String str = strArr[i];
            String str2 = strArr[i + 1];
            if (!TextUtils.isEmpty(str2)) {
                linkedList.add(str);
                linkedList2.add(str2);
            }
        }
        if (!linkedList.isEmpty() && !linkedList2.isEmpty()) {
            return generateEUIValue(linkedList, linkedList2);
        }
        return "";
    }

    public static String generateEUIValue(List<String> list, List<String> list2) {
        if (list.isEmpty() || list2.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) {
                sb.append(",");
            }
        }
        String strEncodeToString = Base64.encodeToString(sb.toString().getBytes(), 2);
        StringBuilder sb2 = new StringBuilder();
        Iterator<String> it = list2.iterator();
        while (it.hasNext()) {
            sb2.append(it.next());
        }
        String strEncodeToString2 = Base64.encodeToString(SHA256.getAsBytes(sb2.toString()), 2);
        AccountLogger.log(TAG, "mEncryptedKey:" + mEncryptedKey + " ,enParamNames:" + strEncodeToString + " ,paramSign:" + strEncodeToString2);
        return mEncryptedKey + "." + strEncodeToString + "." + strEncodeToString2;
    }
}
