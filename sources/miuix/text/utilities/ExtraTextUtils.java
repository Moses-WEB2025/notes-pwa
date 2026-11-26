package miuix.text.utilities;

import android.content.Context;
import java.io.IOException;
import miuix.core.util.Pools;

/* JADX INFO: loaded from: classes6.dex */
public class ExtraTextUtils {
    public static final long GB = 1000000000;
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    public static final long KB = 1000;
    public static final long MB = 1000000;
    private static final long UNIT = 1000;

    protected ExtraTextUtils() throws InstantiationException {
        throw new InstantiationException("Cannot instantiate utility class");
    }

    public static void toHexReadable(byte[] bArr, Appendable appendable) {
        if (bArr == 0) {
            return;
        }
        try {
            for (int i : bArr) {
                if (i < 0) {
                    i += 256;
                }
                char[] cArr = HEX_DIGITS;
                appendable.append(cArr[i >> 4]).append(cArr[i & 15]);
            }
        } catch (IOException e) {
            throw new RuntimeException("Exception throw during when append", e);
        }
    }

    public static String toHexReadable(byte[] bArr) {
        StringBuilder sbAcquire = Pools.getStringBuilderPool().acquire();
        toHexReadable(bArr, sbAcquire);
        String string = sbAcquire.toString();
        Pools.getStringBuilderPool().release(sbAcquire);
        return string;
    }

    public static byte[] fromHexReadable(String str) {
        int i;
        int i2;
        int length = str.length();
        if (length % 2 != 0) {
            throw new IllegalArgumentException("s is not a readable string: " + str);
        }
        byte[] bArr = new byte[length >> 1];
        for (int i3 = 0; i3 < length; i3 += 2) {
            char cCharAt = str.charAt(i3);
            if (cCharAt >= '0' && cCharAt <= '9') {
                i = cCharAt - '0';
            } else if (cCharAt >= 'a' && cCharAt <= 'f') {
                i = cCharAt - 'W';
            } else {
                if (cCharAt < 'A' || cCharAt > 'F') {
                    throw new IllegalArgumentException("s is not a readable string: " + str);
                }
                i = cCharAt - '7';
            }
            int i4 = i << 4;
            char cCharAt2 = str.charAt(i3 + 1);
            if (cCharAt2 >= '0' && cCharAt2 <= '9') {
                i2 = cCharAt2 - '0';
            } else if (cCharAt2 >= 'a' && cCharAt2 <= 'f') {
                i2 = cCharAt2 - 'W';
            } else {
                if (cCharAt2 < 'A' || cCharAt2 > 'F') {
                    throw new IllegalArgumentException("s is not a readable string: " + str);
                }
                i2 = cCharAt2 - '7';
            }
            bArr[i3 >> 1] = (byte) (i4 + i2);
        }
        return bArr;
    }

    public static String formatFileSize(Context context, long j) {
        return formatFileSize(context, j, false);
    }

    public static String formatShortFileSize(Context context, long j) {
        return formatFileSize(context, j, true);
    }

    /* JADX WARN: Code duplicated, block: B:43:0x00c1  */
    /* JADX WARN: Code duplicated, block: B:45:0x00c4  */
    private static String formatFileSize(Context context, long j, boolean z) {
        String strSubstring;
        int i;
        if (context == null) {
            return "";
        }
        float f = j;
        int i2 = R.string.size_byte;
        if (f > 900.0f) {
            i2 = R.string.size_kilo_byte;
            f /= 1000.0f;
        }
        if (f > 900.0f) {
            i2 = R.string.size_mega_byte;
            f /= 1000.0f;
        }
        if (f > 900.0f) {
            i2 = R.string.size_giga_byte;
            f /= 1000.0f;
        }
        if (f > 900.0f) {
            i2 = R.string.size_tera_byte;
            f /= 1000.0f;
        }
        if (f > 900.0f) {
            i2 = R.string.size_peta_byte;
            f /= 1000.0f;
        }
        if (f < 1.0f) {
            strSubstring = String.format("%.2f", Float.valueOf(f));
        } else if (f < 10.0f) {
            strSubstring = z ? String.format("%.1f", Float.valueOf(f)) : String.format("%.2f", Float.valueOf(f));
        } else {
            strSubstring = (f >= 100.0f || z) ? String.format("%.0f", Float.valueOf(f)) : String.format("%.2f", Float.valueOf(f));
        }
        int length = strSubstring.length();
        if (length > 3) {
            int i3 = length - 3;
            if (strSubstring.charAt(i3) == '.' && strSubstring.charAt(length - 2) == '0' && strSubstring.charAt(length - 1) == '0') {
                strSubstring = strSubstring.substring(0, i3);
            } else if (length > 2) {
                i = length - 2;
                if (strSubstring.charAt(i) == '.' && strSubstring.charAt(length - 1) == '0') {
                    strSubstring = strSubstring.substring(0, i);
                }
            }
        } else if (length > 2) {
            i = length - 2;
            if (strSubstring.charAt(i) == '.') {
                strSubstring = strSubstring.substring(0, i);
            }
        }
        return context.getResources().getString(R.string.size_suffix, strSubstring, context.getString(i2));
    }
}
