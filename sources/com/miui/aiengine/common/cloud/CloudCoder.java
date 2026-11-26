package com.miui.aiengine.common.cloud;

import android.text.TextUtils;
import android.util.Base64;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.aiengine.common.utils.CloudCoderUtils;
import com.miui.aiengine.common.utils.Logger;
import com.miui.richeditor.schema.HtmlParser;
import com.xiaomi.accountsdk.hasheddeviceidlib.DeviceIdHasher;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.Charsets;
import org.ccil.cowan.tagsoup.XMLWriter;

/* JADX INFO: compiled from: CloudCoder.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u000b\n\u0002\u0010$\n\u0002\b\u001b\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u001c\u0010\n\u001a\u0004\u0018\u00010\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u00052\u0006\u0010\r\u001a\u00020\u000eH\u0002J&\u0010\u000f\u001a\u0004\u0018\u00010\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u00052\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0002J\u001a\u0010\n\u001a\u00020\u000b2\b\u0010\u0012\u001a\u0004\u0018\u00010\u00112\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0016\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u000eJ\u001a\u0010\u0015\u001a\u0004\u0018\u00010\u000b2\b\u0010\u0016\u001a\u0004\u0018\u00010\u00112\u0006\u0010\r\u001a\u00020\u000eJ\u0012\u0010\u0017\u001a\u0004\u0018\u00010\u00052\b\u0010\u0018\u001a\u0004\u0018\u00010\u0005J:\u0010\u0019\u001a\u0004\u0018\u00010\u00052\b\u0010\u001a\u001a\u0004\u0018\u00010\u00052\b\u0010\u001b\u001a\u0004\u0018\u00010\u00052\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0005J\u0006\u0010\u001f\u001a\u00020\u0005J\u000e\u0010 \u001a\u00020\u00052\u0006\u0010!\u001a\u00020\u0011J\u0010\u0010\"\u001a\u00020\u00052\b\u0010\u0018\u001a\u0004\u0018\u00010\u0005J\u0018\u0010\"\u001a\u00020\u00052\b\u0010\u0018\u001a\u0004\u0018\u00010\u00052\u0006\u0010#\u001a\u00020\u000eJ\"\u0010$\u001a\u00020\u00052\b\u0010\u001e\u001a\u0004\u0018\u00010\u00052\u0006\u0010%\u001a\u00020\u00052\b\u0010&\u001a\u0004\u0018\u00010\u0005J\u0016\u0010'\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u00052\u0006\u0010(\u001a\u00020\u0005J\"\u0010$\u001a\u00020\u00052\b\u0010)\u001a\u0004\u0018\u00010\u000b2\u0006\u0010%\u001a\u00020\u00052\b\u0010*\u001a\u0004\u0018\u00010\u0005J\"\u0010+\u001a\u00020\u00052\b\u0010\u001e\u001a\u0004\u0018\u00010\u00052\u0006\u0010%\u001a\u00020\u00052\b\u0010*\u001a\u0004\u0018\u00010\u0005J\u0016\u0010,\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u00020\u0005J \u0010+\u001a\u00020\u00052\u0006\u0010)\u001a\u00020\u000b2\u0006\u0010%\u001a\u00020\u00052\b\u0010*\u001a\u0004\u0018\u00010\u0005J\u0010\u0010-\u001a\u00020\u00052\b\u0010.\u001a\u0004\u0018\u00010\u0011J\u0012\u0010/\u001a\u0004\u0018\u00010\u00112\u0006\u00100\u001a\u00020\u0005H\u0002J\u0016\u00101\u001a\u00020\u00112\u0006\u00100\u001a\u00020\u00052\u0006\u0010%\u001a\u00020\u0011J\u0010\u00102\u001a\u0004\u0018\u00010\u00052\u0006\u00103\u001a\u00020\u0005J\u0012\u00104\u001a\u0004\u0018\u00010\u00052\b\u0010%\u001a\u0004\u0018\u00010\u0011J\u0012\u00105\u001a\u0004\u0018\u00010\u00052\b\u0010%\u001a\u0004\u0018\u00010\u0011J\u0012\u00106\u001a\u0004\u0018\u00010\u00052\b\u00107\u001a\u0004\u0018\u00010\u0005J\u000e\u00108\u001a\u0002092\u0006\u0010:\u001a\u00020\tJ\u0006\u0010;\u001a\u00020\u0005J\u000e\u0010;\u001a\u00020\u00052\u0006\u0010<\u001a\u00020\tJ6\u0010=\u001a\u0004\u0018\u00010\u00052\b\u0010>\u001a\u0004\u0018\u00010\u00052\u001a\u0010?\u001a\u0016\u0012\u0004\u0012\u00020\u0005\u0018\u00010@j\n\u0012\u0004\u0012\u00020\u0005\u0018\u0001`A2\u0006\u0010B\u001a\u00020\u000eR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006C"}, d2 = {"Lcom/miui/aiengine/common/cloud/CloudCoder;", "Lcom/miui/aiengine/common/utils/Logger;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "RC4_ALGORITHM_NAME", "", "CIPHER_ALGORITHM", "TRANSFORMATION", "sServerDateOffset", "", "newAESCipher", "Ljavax/crypto/Cipher;", "aesKey", "opMode", "", "newAESCipherWithIV", "initialVector", "", "raw", "newMD5AESCipher", "keyStr", "newRC4Cipher", "rc4Key", "hash4SHA1", "plain", "generateSignature", XMLWriter.METHOD, "requestUrl", "params", "", "security", "generatePseudoDeviceId", "encodeUpperHex", "bytes", "hashDeviceInfo", "base64Flag", "decodeString", "data", "charSet", "decodeToString", "cipherText", "cipher", "charset", "encodeString", "encodeToBase64", "getHexString", "b", "randomRc4Key128", "ckeyHint", "encodeStream", "getFileSha1Digest", "filePath", "getDataSha1Digest", "getDataMd5Digest", "getMd5DigestUpperCase", HtmlParser.InputElement.TAG, "setServerDateOffset", "", "serverDateOffset", "generateNonce", "serverTime", "hashAndJoin", "delimiter", "factors", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "subLength", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class CloudCoder extends Logger {
    private static final String CIPHER_ALGORITHM = "AES";
    public static final CloudCoder INSTANCE = new CloudCoder();
    private static final String RC4_ALGORITHM_NAME = "RC4";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static long sServerDateOffset;

    private CloudCoder() {
        super(null, 1, null);
    }

    private final Cipher newAESCipher(String aesKey, int opMode) {
        byte[] bytes = "0102030405060708".getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
        return newAESCipherWithIV(aesKey, opMode, bytes);
    }

    private final Cipher newAESCipherWithIV(String aesKey, int opMode, byte[] initialVector) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.decode(aesKey, 2), CIPHER_ALGORITHM);
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(opMode, secretKeySpec, new IvParameterSpec(initialVector));
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

    private final Cipher newAESCipher(byte[] raw, int opMode) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, CIPHER_ALGORITHM);
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(opMode, secretKeySpec);
            Intrinsics.checkNotNull(cipher);
            return cipher;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("failed to init AES cipher");
        }
    }

    public final Cipher newMD5AESCipher(String keyStr, int opMode) {
        MessageDigest messageDigest;
        Intrinsics.checkNotNullParameter(keyStr, "keyStr");
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            messageDigest = null;
        }
        if (messageDigest == null) {
            throw new IllegalStateException("failed to init MD5".toString());
        }
        byte[] bytes = keyStr.getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
        return newAESCipher(messageDigest.digest(bytes), opMode);
    }

    public final Cipher newRC4Cipher(byte[] rc4Key, int opMode) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(rc4Key, RC4_ALGORITHM_NAME);
        try {
            Cipher cipher = Cipher.getInstance(RC4_ALGORITHM_NAME);
            cipher.init(opMode, secretKeySpec);
            return cipher;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public final String hash4SHA1(String plain) {
        return CloudCoderUtils.hash4SHA1$default(CloudCoderUtils.INSTANCE, plain, 0, 2, null);
    }

    public final String generateSignature(String method, String requestUrl, Map<String, String> params, String security) {
        Intrinsics.checkNotNullParameter(params, "params");
        return CloudCoderUtils.INSTANCE.generateSignature(method, requestUrl, params, security);
    }

    public final String generatePseudoDeviceId() {
        byte[] bArr = new byte[8];
        new SecureRandom().nextBytes(bArr);
        return encodeUpperHex(bArr);
    }

    public final String encodeUpperHex(byte[] bytes) {
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            String str = String.format("%02X", Arrays.copyOf(new Object[]{Byte.valueOf(b)}, 1));
            Intrinsics.checkNotNullExpressionValue(str, "format(...)");
            sb.append(str);
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    public final String hashDeviceInfo(String plain) {
        String strHashDeviceInfo = DeviceIdHasher.hashDeviceInfo(plain, 8);
        Intrinsics.checkNotNullExpressionValue(strHashDeviceInfo, "hashDeviceInfo(...)");
        return strHashDeviceInfo;
    }

    public final String hashDeviceInfo(String plain, int base64Flag) {
        String strHashDeviceInfo = DeviceIdHasher.hashDeviceInfo(plain, base64Flag);
        Intrinsics.checkNotNullExpressionValue(strHashDeviceInfo, "hashDeviceInfo(...)");
        return strHashDeviceInfo;
    }

    public final String decodeString(String security, String data, String charSet) throws BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        Intrinsics.checkNotNullParameter(data, "data");
        return decodeString(newAESCipher(security, 2), data, charSet);
    }

    public final String decodeToString(String keyStr, String cipherText) throws BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        Intrinsics.checkNotNullParameter(keyStr, "keyStr");
        Intrinsics.checkNotNullParameter(cipherText, "cipherText");
        return decodeString(newMD5AESCipher(keyStr, 2), cipherText, (String) null);
    }

    public final String decodeString(Cipher cipher, String data, String charset) throws BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        Intrinsics.checkNotNullParameter(data, "data");
        if (charset == null) {
            charset = "UTF-8";
        }
        try {
            Charset charsetForName = Charset.forName(charset);
            Intrinsics.checkNotNullExpressionValue(charsetForName, "forName(...)");
            byte[] bytes = data.getBytes(charsetForName);
            Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
            byte[] bArrDecode = Base64.decode(bytes, 2);
            Intrinsics.checkNotNullExpressionValue(bArrDecode, "decode(...)");
            Intrinsics.checkNotNull(cipher);
            byte[] bArrDoFinal = cipher.doFinal(bArrDecode);
            Intrinsics.checkNotNullExpressionValue(bArrDoFinal, "doFinal(...)");
            Charset charsetForName2 = Charset.forName(charset);
            Intrinsics.checkNotNullExpressionValue(charsetForName2, "forName(...)");
            return new String(bArrDoFinal, charsetForName2);
        } catch (IllegalArgumentException e) {
            throw new BadPaddingException(e.getMessage());
        }
    }

    public final String encodeString(String security, String data, String charset) throws BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        Intrinsics.checkNotNullParameter(data, "data");
        Cipher cipherNewAESCipher = newAESCipher(security, 1);
        return cipherNewAESCipher == null ? "" : encodeString(cipherNewAESCipher, data, charset);
    }

    public final String encodeToBase64(String keyStr, String plain) throws BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        Intrinsics.checkNotNullParameter(keyStr, "keyStr");
        Intrinsics.checkNotNullParameter(plain, "plain");
        return encodeString(newMD5AESCipher(keyStr, 1), plain, (String) null);
    }

    public final String encodeString(Cipher cipher, String data, String charset) throws BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        Intrinsics.checkNotNullParameter(cipher, "cipher");
        Intrinsics.checkNotNullParameter(data, "data");
        if (charset == null) {
            charset = "UTF-8";
        }
        Charset charsetForName = Charset.forName(charset);
        Intrinsics.checkNotNullExpressionValue(charsetForName, "forName(...)");
        byte[] bytes = data.getBytes(charsetForName);
        Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
        String strEncodeToString = Base64.encodeToString(cipher.doFinal(bytes), 2);
        Intrinsics.checkNotNullExpressionValue(strEncodeToString, "encodeToString(...)");
        return strEncodeToString;
    }

    public final String getHexString(byte[] b) {
        Intrinsics.checkNotNull(b);
        return CloudCoderUtils.getHexString(b);
    }

    private final byte[] randomRc4Key128(String ckeyHint) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] bytes = ckeyHint.getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
            messageDigest.update(bytes);
            return messageDigest.digest();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public final byte[] encodeStream(String ckeyHint, byte[] data) throws BadPaddingException, IllegalBlockSizeException, IOException {
        Intrinsics.checkNotNullParameter(ckeyHint, "ckeyHint");
        Intrinsics.checkNotNullParameter(data, "data");
        Cipher cipherNewRC4Cipher = newRC4Cipher(randomRc4Key128(ckeyHint), 1);
        Intrinsics.checkNotNull(cipherNewRC4Cipher);
        byte[] bArrDoFinal = cipherNewRC4Cipher.doFinal(data);
        Intrinsics.checkNotNullExpressionValue(bArrDoFinal, "doFinal(...)");
        if (data.length == bArrDoFinal.length) {
            return bArrDoFinal;
        }
        throw new IOException("The encoded data length is not the same with original data");
    }

    /* JADX WARN: Code duplicated, block: B:42:0x0052 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    public final String getFileSha1Digest(String filePath) throws Throwable {
        FileInputStream fileInputStream;
        MessageDigest messageDigest;
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        FileInputStream fileInputStream2 = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA1");
            fileInputStream = new FileInputStream(new File(filePath));
            try {
                try {
                    byte[] bArr = new byte[4096];
                    while (true) {
                        int i = fileInputStream.read(bArr);
                        if (i != -1) {
                            messageDigest.update(bArr, 0, i);
                        } else {
                            try {
                                break;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    fileInputStream.close();
                } catch (Throwable th) {
                    th = th;
                    fileInputStream2 = fileInputStream;
                    if (fileInputStream2 != null) {
                        try {
                            fileInputStream2.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
                e.printStackTrace();
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                }
                messageDigest = null;
            }
        } catch (Exception e5) {
            e = e5;
            fileInputStream = null;
        } catch (Throwable th2) {
            th = th2;
            if (fileInputStream2 != null) {
                fileInputStream2.close();
            }
            throw th;
        }
        if (messageDigest != null) {
            return getHexString(messageDigest.digest());
        }
        return null;
    }

    public final String getDataSha1Digest(byte[] data) {
        if (data != null && data.length != 0) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
                messageDigest.update(data);
                return getHexString(messageDigest.digest());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public final String getDataMd5Digest(byte[] data) {
        return CloudCoderUtils.getDataMd5Digest(data);
    }

    public final String getMd5DigestUpperCase(String input) {
        return CloudCoderUtils.getMd5DigestUpperCase(input);
    }

    public final void setServerDateOffset(long serverDateOffset) {
        sServerDateOffset = serverDateOffset;
    }

    public final String generateNonce() {
        return generateNonce(System.currentTimeMillis() + sServerDateOffset);
    }

    public final String generateNonce(long serverTime) {
        return CloudCoderUtils.generateNonce(serverTime);
    }

    public final String hashAndJoin(String delimiter, ArrayList<String> factors, int subLength) {
        ArrayList<String> arrayList = factors;
        if (arrayList == null || arrayList.isEmpty()) {
            return null;
        }
        ArrayList arrayList2 = new ArrayList(factors.size());
        int size = factors.size();
        for (int i = 0; i < size; i++) {
            String str = factors.get(i);
            Intrinsics.checkNotNullExpressionValue(str, "get(...)");
            String strHash4SHA1 = hash4SHA1(str);
            if (strHash4SHA1 != null) {
                if (subLength > 0 && subLength <= strHash4SHA1.length()) {
                    String strSubstring = strHash4SHA1.substring(0, subLength);
                    Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                    arrayList2.add(strSubstring);
                } else {
                    arrayList2.add(strHash4SHA1);
                }
            }
        }
        if (delimiter == null) {
            delimiter = "";
        }
        return TextUtils.join(delimiter, arrayList2);
    }
}
