package com.android.soundrecorder.ai.base.utils;

import android.text.TextUtils;
import android.util.Log;
import ch.qos.logback.core.rolling.helper.DateTokenConverter;
import com.miui.handwriting.common.ai.anno.CreationType;
import com.miui.richeditor.schema.HtmlParser;
import com.xiaomi.mirror.synergy.CallMethod;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class Utils {
    private static final String REGION_KOREA = "KR";
    private static final String TAG = "FileUtils";
    private static final boolean IS_GLOBAL = judgeGlobalFromSystem();
    private static final String[] HEXDIGITS = {"0", "1", "2", "3", CreationType.CREATION_PART_REPAINT, CreationType.CREATION_PART_ELIMINATE, CreationType.CREATION_TEXT, CreationType.CREATION_TEXT_POSTURE, CreationType.CREATION_TEXT_FIGURE, "9", HtmlParser.LinkCardElement.TAG, "b", "c", DateTokenConverter.CONVERTER_KEY, "e", "f"};

    /* JADX WARN: Code duplicated, block: B:35:0x005e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    public static String generateFileSha1(FileDescriptor fileDescriptor) throws Throwable {
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2 = null;
        strByteArrayToHexString = null;
        String strByteArrayToHexString = null;
        try {
            fileInputStream = new FileInputStream(fileDescriptor);
            try {
                try {
                    try {
                        fileInputStream.getChannel().position(0L);
                        MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
                        byte[] bArr = new byte[8192];
                        while (true) {
                            int i = fileInputStream.read(bArr);
                            if (i < 0) {
                                break;
                            }
                            messageDigest.update(bArr, 0, i);
                        }
                        strByteArrayToHexString = byteArrayToHexString(messageDigest.digest());
                    } catch (Throwable th) {
                        th = th;
                        fileInputStream2 = fileInputStream;
                        if (fileInputStream2 != null) {
                            try {
                                fileInputStream2.close();
                            } catch (IOException e) {
                                Log.e(TAG, "Exception when close inputStream", e);
                            }
                        }
                        throw th;
                    }
                } catch (Exception e2) {
                    e = e2;
                    Log.e(TAG, "Exception:" + e);
                    if (fileInputStream != null) {
                    }
                    return strByteArrayToHexString;
                }
                fileInputStream.close();
            } catch (IOException e3) {
                Log.e(TAG, "Exception when close inputStream", e3);
            }
        } catch (Exception e4) {
            e = e4;
            fileInputStream = null;
        } catch (Throwable th2) {
            th = th2;
            if (fileInputStream2 != null) {
                fileInputStream2.close();
            }
            throw th;
        }
        return strByteArrayToHexString;
    }

    public static String getSha1FromFilePath(String str) throws Throwable {
        String sha1;
        long jCurrentTimeMillis = System.currentTimeMillis();
        try {
            sha1 = getSha1(str);
        } catch (Exception e) {
            Log.e(TAG, "Exception when get sha1", e);
            sha1 = "";
        }
        Log.d(TAG, "getSha1FromFilePath consume time:" + (System.currentTimeMillis() - jCurrentTimeMillis));
        return sha1;
    }

    /* JADX WARN: Code duplicated, block: B:42:0x003d A[EXC_TOP_SPLITTER, PHI: r3 r7
  0x003d: PHI (r3v4 java.lang.String) = (r3v9 java.lang.String), (r3v7 java.lang.String) binds: [B:28:0x004f, B:16:0x003b] A[DONT_GENERATE, DONT_INLINE]
  0x003d: PHI (r7v4 java.io.InputStream) = (r7v3 java.io.InputStream), (r7v7 java.io.InputStream) binds: [B:28:0x004f, B:16:0x003b] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:46:0x0057 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r3v2 */
    private static String getSha1(String str) throws Throwable {
        InputStream inputStreamNewInputStream;
        ?? r3 = 0;
        strByteArrayToHexString = null;
        String strByteArrayToHexString = null;
        try {
            try {
                inputStreamNewInputStream = Files.newInputStream(Paths.get(str, new String[0]), new OpenOption[0]);
                if (inputStreamNewInputStream == null) {
                    if (inputStreamNewInputStream != null) {
                        try {
                            inputStreamNewInputStream.close();
                            return null;
                        } catch (IOException e) {
                            Log.e(TAG, "Exception when close inputStream", e);
                        }
                    }
                    return null;
                }
                try {
                    MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
                    byte[] bArr = new byte[8192];
                    while (true) {
                        int i = inputStreamNewInputStream.read(bArr);
                        if (i < 0) {
                            break;
                        }
                        messageDigest.update(bArr, 0, i);
                    }
                    strByteArrayToHexString = byteArrayToHexString(messageDigest.digest());
                    if (inputStreamNewInputStream != null) {
                        try {
                            inputStreamNewInputStream.close();
                        } catch (IOException e2) {
                            Log.e(TAG, "Exception when close inputStream", e2);
                        }
                    }
                } catch (Exception e3) {
                    e = e3;
                    e.printStackTrace();
                    if (inputStreamNewInputStream != null) {
                        inputStreamNewInputStream.close();
                    }
                }
                return strByteArrayToHexString;
            } catch (Exception e4) {
                e = e4;
                inputStreamNewInputStream = null;
            } catch (Throwable th) {
                th = th;
                if (r3 != 0) {
                    try {
                        r3.close();
                    } catch (IOException e5) {
                        Log.e(TAG, "Exception when close inputStream", e5);
                    }
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            r3 = str;
            if (r3 != 0) {
                r3.close();
            }
            throw th;
        }
    }

    private static String byteArrayToHexString(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer(bArr.length * 2);
        for (int i = 0; i < bArr.length; i++) {
            String[] strArr = HEXDIGITS;
            stringBuffer.append(strArr[(bArr[i] >>> 4) & 15]);
            stringBuffer.append(strArr[bArr[i] & 15]);
        }
        return stringBuffer.toString();
    }

    private static boolean judgeGlobalFromSystem() {
        return get("ro.product.mod_device", "").contains("_global");
    }

    private static String get(String str, String str2) {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod(CallMethod.METHOD_GET, String.class, String.class).invoke(cls, str, str2);
        } catch (Exception e) {
            Log.e(TAG, "get system prop error: ", e);
            return str2;
        }
    }

    private static String getRegion() {
        String str = get("ro.miui.region", "");
        if (!TextUtils.isEmpty(str)) {
            return str;
        }
        String str2 = get("ro.product.locale.region", "");
        if (!TextUtils.isEmpty(str2)) {
            return str2;
        }
        String str3 = get("persist.sys.country", "");
        return TextUtils.isEmpty(str3) ? Locale.getDefault().getCountry() : str3;
    }

    public static boolean isKoreaRegion() {
        return !TextUtils.isEmpty(getRegion()) && "KR".equals(getRegion());
    }

    public static boolean isGlobal() {
        return IS_GLOBAL;
    }
}
