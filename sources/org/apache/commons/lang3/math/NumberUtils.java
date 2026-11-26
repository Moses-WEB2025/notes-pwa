package org.apache.commons.lang3.math;

import androidx.collection.SieveCacheKt;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Objects;
import miuix.appcompat.view.menu.HyperMenuUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/* JADX INFO: loaded from: classes6.dex */
public class NumberUtils {
    public static final Long LONG_ZERO = 0L;
    public static final Long LONG_ONE = 1L;
    public static final Long LONG_MINUS_ONE = -1L;
    public static final Integer INTEGER_ZERO = 0;
    public static final Integer INTEGER_ONE = 1;
    public static final Integer INTEGER_TWO = 2;
    public static final Integer INTEGER_MINUS_ONE = -1;
    public static final Short SHORT_ZERO = 0;
    public static final Short SHORT_ONE = 1;
    public static final Short SHORT_MINUS_ONE = -1;
    public static final Byte BYTE_ZERO = (byte) 0;
    public static final Byte BYTE_ONE = (byte) 1;
    public static final Byte BYTE_MINUS_ONE = (byte) -1;
    public static final Double DOUBLE_ZERO = Double.valueOf(0.0d);
    public static final Double DOUBLE_ONE = Double.valueOf(1.0d);
    public static final Double DOUBLE_MINUS_ONE = Double.valueOf(-1.0d);
    public static final Float FLOAT_ZERO = Float.valueOf(0.0f);
    public static final Float FLOAT_ONE = Float.valueOf(1.0f);
    public static final Float FLOAT_MINUS_ONE = Float.valueOf(-1.0f);
    public static final Long LONG_INT_MAX_VALUE = Long.valueOf(SieveCacheKt.NodeLinkMask);
    public static final Long LONG_INT_MIN_VALUE = Long.valueOf(SieveCacheKt.NodeMetaAndPreviousMask);

    public static int compare(byte b, byte b2) {
        return b - b2;
    }

    public static int compare(int i, int i2) {
        if (i == i2) {
            return 0;
        }
        return i < i2 ? -1 : 1;
    }

    public static int compare(long j, long j2) {
        if (j == j2) {
            return 0;
        }
        return j < j2 ? -1 : 1;
    }

    public static int compare(short s, short s2) {
        if (s == s2) {
            return 0;
        }
        return s < s2 ? -1 : 1;
    }

    private static boolean isSign(char c) {
        return c == '-' || c == '+';
    }

    public static byte max(byte b, byte b2, byte b3) {
        if (b2 > b) {
            b = b2;
        }
        return b3 > b ? b3 : b;
    }

    public static int max(int i, int i2, int i3) {
        if (i2 > i) {
            i = i2;
        }
        return i3 > i ? i3 : i;
    }

    public static long max(long j, long j2, long j3) {
        if (j2 > j) {
            j = j2;
        }
        return j3 > j ? j3 : j;
    }

    public static short max(short s, short s2, short s3) {
        if (s2 > s) {
            s = s2;
        }
        return s3 > s ? s3 : s;
    }

    public static byte min(byte b, byte b2, byte b3) {
        if (b2 < b) {
            b = b2;
        }
        return b3 < b ? b3 : b;
    }

    public static int min(int i, int i2, int i3) {
        if (i2 < i) {
            i = i2;
        }
        return i3 < i ? i3 : i;
    }

    public static long min(long j, long j2, long j3) {
        if (j2 < j) {
            j = j2;
        }
        return j3 < j ? j3 : j;
    }

    public static short min(short s, short s2, short s3) {
        if (s2 < s) {
            s = s2;
        }
        return s3 < s ? s3 : s;
    }

    public static BigDecimal createBigDecimal(String str) {
        if (str == null) {
            return null;
        }
        if (StringUtils.isBlank(str)) {
            throw new NumberFormatException("A blank string is not a valid number");
        }
        return new BigDecimal(str);
    }

    public static BigInteger createBigInteger(String str) {
        int i;
        if (str == null) {
            return null;
        }
        if (str.isEmpty()) {
            throw new NumberFormatException("An empty string is not a valid number");
        }
        int i2 = 0;
        char cCharAt = str.charAt(0);
        boolean z = true;
        if (cCharAt == '-') {
            i2 = 1;
        } else if (cCharAt == '+') {
            z = false;
            i2 = 1;
        } else {
            z = false;
        }
        int i3 = 16;
        if (str.startsWith("0x", i2) || str.startsWith("0X", i2)) {
            i2 += 2;
        } else if (str.startsWith(HyperMenuUtils.GROUP_TITLE_START, i2)) {
            i2++;
        } else if (!str.startsWith("0", i2) || str.length() <= (i = i2 + 1)) {
            i3 = 10;
        } else {
            i3 = 8;
            i2 = i;
        }
        BigInteger bigInteger = new BigInteger(str.substring(i2), i3);
        return z ? bigInteger.negate() : bigInteger;
    }

    public static Double createDouble(String str) {
        if (str == null) {
            return null;
        }
        return Double.valueOf(str);
    }

    public static Float createFloat(String str) {
        if (str == null) {
            return null;
        }
        return Float.valueOf(str);
    }

    public static Integer createInteger(String str) {
        if (str == null) {
            return null;
        }
        return Integer.decode(str);
    }

    public static Long createLong(String str) {
        if (str == null) {
            return null;
        }
        return Long.decode(str);
    }

    /* JADX WARN: Code duplicated, block: B:108:0x018d A[Catch: NumberFormatException -> 0x019c, TryCatch #6 {NumberFormatException -> 0x019c, blocks: (B:106:0x0183, B:108:0x018d, B:110:0x0195), top: B:165:0x0183 }] */
    /* JADX WARN: Code duplicated, block: B:159:0x016a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:165:0x0183 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x0125, code lost:
    
        if (r3 == 'l') goto L83;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.Number createNumber(java.lang.String r17) {
        /*
            Method dump skipped, instruction units count: 564
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang3.math.NumberUtils.createNumber(java.lang.String):java.lang.Number");
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [boolean] */
    private static String getMantissa(String str, int i) {
        ?? IsSign = isSign(str.charAt(0));
        int length = str.length();
        if (length <= IsSign || length < i) {
            throw new NumberFormatException(str + " is not a valid number.");
        }
        return IsSign != 0 ? str.substring(1, i) : str.substring(0, i);
    }

    private static boolean isAllZeros(String str) {
        if (str == null) {
            return true;
        }
        for (int length = str.length() - 1; length >= 0; length--) {
            if (str.charAt(length) != '0') {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r3v2, types: [int] */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Type inference failed for: r3v8 */
    public static boolean isCreatable(String str) {
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        char[] charArray = str.toCharArray();
        int length = charArray.length;
        ?? IsSign = isSign(charArray[0]);
        int i = IsSign + 1;
        if (length > i && charArray[IsSign] == '0' && !StringUtils.contains(str, 46)) {
            char c = charArray[i];
            if (c == 'x' || c == 'X') {
                int i2 = IsSign + 2;
                if (i2 == length) {
                    return false;
                }
                while (i2 < charArray.length) {
                    if (!CharUtils.isHex(charArray[i2])) {
                        return false;
                    }
                    i2++;
                }
                return true;
            }
            if (Character.isDigit(c)) {
                while (i < charArray.length) {
                    if (!CharUtils.isOctal(charArray[i])) {
                        return false;
                    }
                    i++;
                }
                return true;
            }
        }
        int i3 = length - 1;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        ?? r3 = IsSign;
        while (true) {
            if (r3 < i3 || (r3 < length && z && !z2)) {
                if (CharUtils.isAsciiNumeric(charArray[r3])) {
                    z = false;
                    z2 = true;
                } else {
                    char c2 = charArray[r3];
                    if (c2 == '.') {
                        if (z4 || z3) {
                            break;
                        }
                        z4 = true;
                    } else if (c2 == 'e' || c2 == 'E') {
                        if (z3 || !z2) {
                            return false;
                        }
                        z = true;
                        z3 = true;
                    } else {
                        if (!isSign(c2) || !z) {
                            return false;
                        }
                        z = false;
                        z2 = false;
                    }
                }
                r3++;
            } else {
                if (r3 >= charArray.length) {
                    return !z && z2;
                }
                if (CharUtils.isAsciiNumeric(charArray[r3])) {
                    return true;
                }
                char c3 = charArray[r3];
                if (c3 != 'e' && c3 != 'E') {
                    if (c3 == '.') {
                        if (z4 || z3) {
                            return false;
                        }
                    } else if (z || (c3 != 'd' && c3 != 'D' && c3 != 'f' && c3 != 'F')) {
                        if ((c3 == 'l' || c3 == 'L') && z2 && !z3 && !z4) {
                            return true;
                        }
                    }
                    return z2;
                }
                return false;
            }
        }
        return false;
    }

    public static boolean isDigits(String str) {
        return StringUtils.isNumeric(str);
    }

    @Deprecated
    public static boolean isNumber(String str) {
        return isCreatable(str);
    }

    public static boolean isParsable(String str) {
        if (StringUtils.isEmpty(str) || str.charAt(str.length() - 1) == '.') {
            return false;
        }
        if (str.charAt(0) == '-') {
            if (str.length() == 1) {
                return false;
            }
            return withDecimalsParsing(str, 1);
        }
        return withDecimalsParsing(str, 0);
    }

    private static boolean isZero(String str, String str2) {
        return isAllZeros(str) && isAllZeros(str2);
    }

    public static byte max(byte... bArr) {
        validateArray(bArr);
        byte b = bArr[0];
        for (int i = 1; i < bArr.length; i++) {
            byte b2 = bArr[i];
            if (b2 > b) {
                b = b2;
            }
        }
        return b;
    }

    public static double max(double... dArr) {
        validateArray(dArr);
        double d = dArr[0];
        for (int i = 1; i < dArr.length; i++) {
            if (Double.isNaN(dArr[i])) {
                return Double.NaN;
            }
            double d2 = dArr[i];
            if (d2 > d) {
                d = d2;
            }
        }
        return d;
    }

    public static double max(double d, double d2, double d3) {
        return Math.max(Math.max(d, d2), d3);
    }

    public static float max(float... fArr) {
        validateArray(fArr);
        float f = fArr[0];
        for (int i = 1; i < fArr.length; i++) {
            if (Float.isNaN(fArr[i])) {
                return Float.NaN;
            }
            float f2 = fArr[i];
            if (f2 > f) {
                f = f2;
            }
        }
        return f;
    }

    public static float max(float f, float f2, float f3) {
        return Math.max(Math.max(f, f2), f3);
    }

    public static int max(int... iArr) {
        validateArray(iArr);
        int i = iArr[0];
        for (int i2 = 1; i2 < iArr.length; i2++) {
            int i3 = iArr[i2];
            if (i3 > i) {
                i = i3;
            }
        }
        return i;
    }

    public static long max(long... jArr) {
        validateArray(jArr);
        long j = jArr[0];
        for (int i = 1; i < jArr.length; i++) {
            long j2 = jArr[i];
            if (j2 > j) {
                j = j2;
            }
        }
        return j;
    }

    public static short max(short... sArr) {
        validateArray(sArr);
        short s = sArr[0];
        for (int i = 1; i < sArr.length; i++) {
            short s2 = sArr[i];
            if (s2 > s) {
                s = s2;
            }
        }
        return s;
    }

    public static byte min(byte... bArr) {
        validateArray(bArr);
        byte b = bArr[0];
        for (int i = 1; i < bArr.length; i++) {
            byte b2 = bArr[i];
            if (b2 < b) {
                b = b2;
            }
        }
        return b;
    }

    public static double min(double... dArr) {
        validateArray(dArr);
        double d = dArr[0];
        for (int i = 1; i < dArr.length; i++) {
            if (Double.isNaN(dArr[i])) {
                return Double.NaN;
            }
            double d2 = dArr[i];
            if (d2 < d) {
                d = d2;
            }
        }
        return d;
    }

    public static double min(double d, double d2, double d3) {
        return Math.min(Math.min(d, d2), d3);
    }

    public static float min(float... fArr) {
        validateArray(fArr);
        float f = fArr[0];
        for (int i = 1; i < fArr.length; i++) {
            if (Float.isNaN(fArr[i])) {
                return Float.NaN;
            }
            float f2 = fArr[i];
            if (f2 < f) {
                f = f2;
            }
        }
        return f;
    }

    public static float min(float f, float f2, float f3) {
        return Math.min(Math.min(f, f2), f3);
    }

    public static int min(int... iArr) {
        validateArray(iArr);
        int i = iArr[0];
        for (int i2 = 1; i2 < iArr.length; i2++) {
            int i3 = iArr[i2];
            if (i3 < i) {
                i = i3;
            }
        }
        return i;
    }

    public static long min(long... jArr) {
        validateArray(jArr);
        long j = jArr[0];
        for (int i = 1; i < jArr.length; i++) {
            long j2 = jArr[i];
            if (j2 < j) {
                j = j2;
            }
        }
        return j;
    }

    public static short min(short... sArr) {
        validateArray(sArr);
        short s = sArr[0];
        for (int i = 1; i < sArr.length; i++) {
            short s2 = sArr[i];
            if (s2 < s) {
                s = s2;
            }
        }
        return s;
    }

    public static byte toByte(String str) {
        return toByte(str, (byte) 0);
    }

    public static byte toByte(String str, byte b) {
        try {
            return Byte.parseByte(str);
        } catch (RuntimeException unused) {
            return b;
        }
    }

    public static double toDouble(BigDecimal bigDecimal) {
        return toDouble(bigDecimal, 0.0d);
    }

    public static double toDouble(BigDecimal bigDecimal, double d) {
        return bigDecimal == null ? d : bigDecimal.doubleValue();
    }

    public static double toDouble(String str) {
        return toDouble(str, 0.0d);
    }

    public static double toDouble(String str, double d) {
        try {
            return Double.parseDouble(str);
        } catch (RuntimeException unused) {
            return d;
        }
    }

    public static float toFloat(String str) {
        return toFloat(str, 0.0f);
    }

    public static float toFloat(String str, float f) {
        try {
            return Float.parseFloat(str);
        } catch (RuntimeException unused) {
            return f;
        }
    }

    public static int toInt(String str) {
        return toInt(str, 0);
    }

    public static int toInt(String str, int i) {
        try {
            return Integer.parseInt(str);
        } catch (RuntimeException unused) {
            return i;
        }
    }

    public static long toLong(String str) {
        return toLong(str, 0L);
    }

    public static long toLong(String str, long j) {
        try {
            return Long.parseLong(str);
        } catch (RuntimeException unused) {
            return j;
        }
    }

    public static BigDecimal toScaledBigDecimal(BigDecimal bigDecimal) {
        return toScaledBigDecimal(bigDecimal, INTEGER_TWO.intValue(), RoundingMode.HALF_EVEN);
    }

    public static BigDecimal toScaledBigDecimal(BigDecimal bigDecimal, int i, RoundingMode roundingMode) {
        if (bigDecimal == null) {
            return BigDecimal.ZERO;
        }
        if (roundingMode == null) {
            roundingMode = RoundingMode.HALF_EVEN;
        }
        return bigDecimal.setScale(i, roundingMode);
    }

    public static BigDecimal toScaledBigDecimal(Double d) {
        return toScaledBigDecimal(d, INTEGER_TWO.intValue(), RoundingMode.HALF_EVEN);
    }

    public static BigDecimal toScaledBigDecimal(Double d, int i, RoundingMode roundingMode) {
        if (d == null) {
            return BigDecimal.ZERO;
        }
        return toScaledBigDecimal(BigDecimal.valueOf(d.doubleValue()), i, roundingMode);
    }

    public static BigDecimal toScaledBigDecimal(Float f) {
        return toScaledBigDecimal(f, INTEGER_TWO.intValue(), RoundingMode.HALF_EVEN);
    }

    public static BigDecimal toScaledBigDecimal(Float f, int i, RoundingMode roundingMode) {
        if (f == null) {
            return BigDecimal.ZERO;
        }
        return toScaledBigDecimal(BigDecimal.valueOf(f.floatValue()), i, roundingMode);
    }

    public static BigDecimal toScaledBigDecimal(String str) {
        return toScaledBigDecimal(str, INTEGER_TWO.intValue(), RoundingMode.HALF_EVEN);
    }

    public static BigDecimal toScaledBigDecimal(String str, int i, RoundingMode roundingMode) {
        if (str == null) {
            return BigDecimal.ZERO;
        }
        return toScaledBigDecimal(createBigDecimal(str), i, roundingMode);
    }

    public static short toShort(String str) {
        return toShort(str, (short) 0);
    }

    public static short toShort(String str, short s) {
        try {
            return Short.parseShort(str);
        } catch (RuntimeException unused) {
            return s;
        }
    }

    private static void validateArray(Object obj) {
        Objects.requireNonNull(obj, "array");
        Validate.isTrue(Array.getLength(obj) != 0, "Array cannot be empty.", new Object[0]);
    }

    private static boolean withDecimalsParsing(String str, int i) {
        int i2 = 0;
        while (i < str.length()) {
            char cCharAt = str.charAt(i);
            boolean z = cCharAt == '.';
            if (z) {
                i2++;
            }
            if (i2 > 1) {
                return false;
            }
            if (!z && !Character.isDigit(cCharAt)) {
                return false;
            }
            i++;
        }
        return true;
    }

    @Deprecated
    public NumberUtils() {
    }
}
