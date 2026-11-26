package com.market.sdk.utils;

import android.text.TextUtils;
import android.util.Base64;
import ch.qos.logback.core.rolling.helper.DateTokenConverter;
import com.market.sdk.MarketManager;
import com.miui.handwriting.common.ai.anno.CreationType;
import com.miui.richeditor.schema.HtmlParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes2.dex */
public class Coder {
    public static final String AES_ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String[] hexDigits = {"0", "1", "2", "3", CreationType.CREATION_PART_REPAINT, CreationType.CREATION_PART_ELIMINATE, CreationType.CREATION_TEXT, CreationType.CREATION_TEXT_POSTURE, CreationType.CREATION_TEXT_FIGURE, "9", HtmlParser.LinkCardElement.TAG, "b", "c", DateTokenConverter.CONVERTER_KEY, "e", "f"};

    public static final String encodeMD5(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes());
            return byteArrayToString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            Log.e(MarketManager.TAG, e.toString());
            return null;
        }
    }

    public static final String encodeMD5(File file) {
        byte[] bArr = new byte[1024];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                try {
                    try {
                        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                        while (true) {
                            int i = fileInputStream.read(bArr);
                            if (i > 0) {
                                messageDigest.update(bArr, 0, i);
                            } else {
                                try {
                                    break;
                                } catch (IOException e) {
                                    Log.e(MarketManager.TAG, e.toString());
                                }
                            }
                        }
                        fileInputStream.close();
                        return byteArrayToString(messageDigest.digest());
                    } catch (NoSuchAlgorithmException e2) {
                        Log.e(MarketManager.TAG, e2.toString());
                        try {
                            fileInputStream.close();
                        } catch (IOException e3) {
                            Log.e(MarketManager.TAG, e3.toString());
                        }
                        return null;
                    }
                } catch (IOException e4) {
                    Log.e(MarketManager.TAG, e4.toString());
                    try {
                        fileInputStream.close();
                    } catch (IOException e5) {
                        Log.e(MarketManager.TAG, e5.toString());
                    }
                    return null;
                }
            } catch (Throwable th) {
                try {
                    fileInputStream.close();
                } catch (IOException e6) {
                    Log.e(MarketManager.TAG, e6.toString());
                }
                throw th;
            }
        } catch (FileNotFoundException e7) {
            Log.e(MarketManager.TAG, e7.toString());
            return null;
        }
    }

    private static String byteArrayToString(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : bArr) {
            stringBuffer.append(byteToHexString(b));
        }
        return stringBuffer.toString();
    }

    private static String byteToHexString(byte b) {
        int i = b;
        if (b < 0) {
            i = b + 256;
        }
        int i2 = i / 16;
        StringBuilder sb = new StringBuilder();
        String[] strArr = hexDigits;
        return sb.append(strArr[i2]).append(strArr[i % 16]).toString();
    }

    public static byte[] hex2Byte(String str) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        if (length % 2 == 1) {
            return null;
        }
        byte[] bArr = new byte[length / 2];
        for (int i = 0; i != length / 2; i++) {
            try {
                int i2 = i * 2;
                bArr[i] = (byte) Integer.parseInt(str.substring(i2, i2 + 2), 16);
            } catch (NumberFormatException unused) {
                return null;
            }
        }
        return bArr;
    }

    public static final String encodeSHA(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA");
            messageDigest.update(str.getBytes());
            return byteArrayToString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            Log.e(MarketManager.TAG, e.toString());
            return null;
        }
    }

    public static final byte[] encodeSHABytes(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA");
            messageDigest.update(str.getBytes());
            return messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            Log.e(MarketManager.TAG, e.toString());
            return null;
        }
    }

    public static final String encodeBase64(String str) {
        return Base64.encodeToString(str.getBytes(), 2);
    }

    public static final String encodeBase64(byte[] bArr) {
        return Base64.encodeToString(bArr, 2);
    }

    public static final byte[] encodeBase64Bytes(String str) {
        return Base64.encode(str.getBytes(), 2);
    }

    public static final String decodeBase64(String str) {
        return new String(Base64.decode(str, 0));
    }

    public static final byte[] decodeBase64Bytes(String str) {
        return Base64.decode(str, 0);
    }

    public static final String encodeAES(String str, String str2) {
        byte[] bArrDecodeBase64Bytes;
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && (bArrDecodeBase64Bytes = decodeBase64Bytes(str2)) != null && bArrDecodeBase64Bytes.length == 16) {
            SecretKeySpec secretKeySpec = new SecretKeySpec(bArrDecodeBase64Bytes, "AES");
            try {
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                cipher.init(1, secretKeySpec, new IvParameterSpec("0102030405060708".getBytes()));
                return encodeBase64(cipher.doFinal(str.getBytes()));
            } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException unused) {
            }
        }
        return null;
    }

    public static final String decodeBase64AndAES(String str, String str2) {
        byte[] bArrDecodeBase64Bytes;
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && (bArrDecodeBase64Bytes = decodeBase64Bytes(str2)) != null && bArrDecodeBase64Bytes.length == 16) {
            SecretKeySpec secretKeySpec = new SecretKeySpec(bArrDecodeBase64Bytes, "AES");
            try {
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                cipher.init(2, secretKeySpec, new IvParameterSpec("0102030405060708".getBytes()));
                byte[] bArrDecodeBase64Bytes2 = decodeBase64Bytes(str);
                if (bArrDecodeBase64Bytes2 == null) {
                    return null;
                }
                return new String(cipher.doFinal(bArrDecodeBase64Bytes2));
            } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException unused) {
            }
        }
        return null;
    }
}
