package com.xiaomi.miai.utils;

import com.xiaomi.miai.SDKConstants;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Random;

/* JADX INFO: loaded from: classes4.dex */
public class StringUtil {
    private static Random random = new Random();

    private StringUtil() {
    }

    public static String utf8String(byte[] bArr) {
        return new String(bArr, SDKConstants.UTF_8);
    }

    public static byte[] utf8Bytes(String str) {
        return str.getBytes(StandardCharsets.UTF_8);
    }

    public static String getOrEmpty(String str) {
        return str == null ? "" : str;
    }

    public static String getStackTrace(Throwable th) {
        StringWriter stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter((Writer) stringWriter, true));
        return stringWriter.getBuffer().toString();
    }

    public static String randomNumeric(int i) {
        return random(i, true, false, false);
    }

    public static String randomAlphanumeric(int i) {
        return random(i, true, true, false);
    }

    public static String randomAlphabetic(int i) {
        return random(i, false, true, false);
    }

    public static String randomAscii(int i) {
        return random(i, true, true, true);
    }

    /* JADX WARN: Code duplicated, block: B:26:0x003d  */
    /* JADX WARN: Code duplicated, block: B:27:0x0043  */
    private static String random(int i, boolean z, boolean z2, boolean z3) {
        int i2;
        Requires.isTrue(i > 0, "count should be greater than 0");
        if (z3) {
            i2 = 127;
        } else {
            int i3 = z ? 10 : 0;
            i2 = z2 ? i3 + 52 : i3;
        }
        char[] cArr = new char[i];
        while (true) {
            int i4 = i - 1;
            if (i > 0) {
                int iNextInt = random.nextInt(i2);
                if (z3) {
                    cArr[i4] = (char) iNextInt;
                } else if (z) {
                    if (iNextInt < 10) {
                        cArr[i4] = (char) (iNextInt + 48);
                    } else {
                        iNextInt -= 10;
                        if (iNextInt < 26) {
                            cArr[i4] = (char) (iNextInt + 65);
                        } else {
                            cArr[i4] = (char) (iNextInt + 71);
                        }
                    }
                } else if (iNextInt < 26) {
                    cArr[i4] = (char) (iNextInt + 65);
                } else {
                    cArr[i4] = (char) (iNextInt + 71);
                }
                i = i4;
            } else {
                return new String(cArr);
            }
        }
    }
}
