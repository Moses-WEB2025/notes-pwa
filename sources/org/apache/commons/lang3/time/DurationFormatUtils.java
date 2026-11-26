package org.apache.commons.lang3.time;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.TimeZone;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.apache.commons.lang3.Validate;

/* JADX INFO: loaded from: classes6.dex */
public class DurationFormatUtils {
    static final String H = "H";
    private static final int HOURS_PER_DAY = 24;
    public static final String ISO_EXTENDED_FORMAT_PATTERN = "'P'yyyy'Y'M'M'd'DT'H'H'm'M's.SSS'S'";
    static final String M = "M";
    private static final int MINUTES_PER_HOUR = 60;
    static final String S = "S";
    private static final int SECONDS_PER_MINUTES = 60;
    static final String d = "d";
    static final String m = "m";
    static final String s = "s";
    static final String y = "y";

    static final class Token {
        private static final Token[] EMPTY_ARRAY = new Token[0];
        private int count = 1;
        private int optionalIndex;
        private final CharSequence value;

        static boolean containsTokenWithValue(Token[] tokenArr, final Object obj) {
            return Stream.of((Object[]) tokenArr).anyMatch(new Predicate() { // from class: org.apache.commons.lang3.time.DurationFormatUtils$Token$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj2) {
                    return DurationFormatUtils.Token.lambda$containsTokenWithValue$0(obj, (DurationFormatUtils.Token) obj2);
                }
            });
        }

        static /* synthetic */ boolean lambda$containsTokenWithValue$0(Object obj, Token token) {
            return token.getValue() == obj;
        }

        Token(CharSequence charSequence, boolean z, int i) {
            this.optionalIndex = -1;
            this.value = (CharSequence) Objects.requireNonNull(charSequence, "value");
            if (z) {
                this.optionalIndex = i;
            }
        }

        public boolean equals(Object obj) {
            if (obj instanceof Token) {
                Token token = (Token) obj;
                if (this.value.getClass() != token.value.getClass() || this.count != token.count) {
                    return false;
                }
                CharSequence charSequence = this.value;
                if (charSequence instanceof StringBuilder) {
                    return charSequence.toString().equals(token.value.toString());
                }
                if (charSequence instanceof Number) {
                    return charSequence.equals(token.value);
                }
                if (charSequence == token.value) {
                    return true;
                }
            }
            return false;
        }

        int getCount() {
            return this.count;
        }

        Object getValue() {
            return this.value;
        }

        public int hashCode() {
            return this.value.hashCode();
        }

        void increment() {
            this.count++;
        }

        public String toString() {
            return StringUtils.repeat(this.value.toString(), this.count);
        }
    }

    /* JADX WARN: Code duplicated, block: B:106:0x0179 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:13:0x0058  */
    /* JADX WARN: Code duplicated, block: B:15:0x005c  */
    /* JADX WARN: Code duplicated, block: B:18:0x0068  */
    /* JADX WARN: Code duplicated, block: B:20:0x0072  */
    /* JADX WARN: Code duplicated, block: B:22:0x0076  */
    /* JADX WARN: Code duplicated, block: B:23:0x0078  */
    /* JADX WARN: Code duplicated, block: B:26:0x007e  */
    /* JADX WARN: Code duplicated, block: B:30:0x0090  */
    /* JADX WARN: Code duplicated, block: B:32:0x0098  */
    /* JADX WARN: Code duplicated, block: B:34:0x009c  */
    /* JADX WARN: Code duplicated, block: B:35:0x009e  */
    /* JADX WARN: Code duplicated, block: B:38:0x00a4  */
    /* JADX WARN: Code duplicated, block: B:39:0x00ac  */
    /* JADX WARN: Code duplicated, block: B:41:0x00b4  */
    /* JADX WARN: Code duplicated, block: B:43:0x00b8  */
    /* JADX WARN: Code duplicated, block: B:44:0x00ba  */
    /* JADX WARN: Code duplicated, block: B:47:0x00c0  */
    /* JADX WARN: Code duplicated, block: B:48:0x00c8  */
    /* JADX WARN: Code duplicated, block: B:50:0x00d0  */
    /* JADX WARN: Code duplicated, block: B:52:0x00d4  */
    /* JADX WARN: Code duplicated, block: B:53:0x00d6  */
    /* JADX WARN: Code duplicated, block: B:56:0x00dc  */
    /* JADX WARN: Code duplicated, block: B:57:0x00e4  */
    /* JADX WARN: Code duplicated, block: B:59:0x00ec  */
    /* JADX WARN: Code duplicated, block: B:61:0x00f0  */
    /* JADX WARN: Code duplicated, block: B:62:0x00f2  */
    /* JADX WARN: Code duplicated, block: B:66:0x0102  */
    /* JADX WARN: Code duplicated, block: B:68:0x010c  */
    /* JADX WARN: Code duplicated, block: B:70:0x0110  */
    /* JADX WARN: Code duplicated, block: B:71:0x0112  */
    /* JADX WARN: Code duplicated, block: B:76:0x011c  */
    /* JADX WARN: Code duplicated, block: B:78:0x012d  */
    /* JADX WARN: Code duplicated, block: B:80:0x0135  */
    /* JADX WARN: Code duplicated, block: B:82:0x0139  */
    /* JADX WARN: Code duplicated, block: B:83:0x013b  */
    /* JADX WARN: Code duplicated, block: B:88:0x0146 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:89:0x0148  */
    /* JADX WARN: Code duplicated, block: B:91:0x014b  */
    /* JADX WARN: Code duplicated, block: B:93:0x015a  */
    /* JADX WARN: Code duplicated, block: B:96:0x016a A[ADDED_TO_REGION] */
    /* JADX WARN: Instruction removed from duplicated block: B:89:0x0148, please report this as an issue */
    static String format(Token[] tokenArr, long j, long j2, long j3, long j4, long j5, long j6, long j7, boolean z) {
        int length;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        StringBuilder sb = new StringBuilder();
        int length2 = tokenArr.length;
        int i = 0;
        boolean z6 = false;
        boolean z7 = false;
        boolean z8 = false;
        boolean z9 = false;
        int i2 = -1;
        int i3 = -1;
        while (i < length2) {
            Token token = tokenArr[i];
            int i4 = i;
            Object value = token.getValue();
            boolean z10 = z6;
            boolean z11 = value instanceof StringBuilder;
            int count = token.getCount();
            int i5 = length2;
            if (i2 != token.optionalIndex) {
                i2 = token.optionalIndex;
                if (i2 > -1) {
                    z7 = false;
                    z8 = false;
                    length = sb.length();
                    z10 = true;
                } else {
                    z10 = false;
                }
                if (z11) {
                    if (value.equals("y")) {
                        if (j == 0) {
                            z5 = true;
                        } else {
                            z5 = false;
                        }
                        if (z10 || !z5) {
                            sb.append(paddedValue(j, z, count));
                        }
                    } else if (value.equals("M")) {
                        if (j2 == 0) {
                            z5 = true;
                        } else {
                            z5 = false;
                        }
                        if (z10 || !z5) {
                            sb.append(paddedValue(j2, z, count));
                        }
                    } else if (value.equals("d")) {
                        if (j3 == 0) {
                            z5 = true;
                        } else {
                            z5 = false;
                        }
                        if (z10 || !z5) {
                            sb.append(paddedValue(j3, z, count));
                        }
                    } else if (value.equals("H")) {
                        if (j4 == 0) {
                            z5 = true;
                        } else {
                            z5 = false;
                        }
                        if (z10 || !z5) {
                            sb.append(paddedValue(j4, z, count));
                        }
                    } else {
                        if (value.equals(m)) {
                            if (j5 == 0) {
                                z5 = true;
                            } else {
                                z5 = false;
                            }
                            if (z10 || !z5) {
                                sb.append(paddedValue(j5, z, count));
                            }
                            z7 = z5;
                            z9 = false;
                            z3 = true;
                        } else if (value.equals("s")) {
                            if (j6 == 0) {
                                z4 = true;
                            } else {
                                z4 = false;
                            }
                            if (z10 || !z4) {
                                sb.append(paddedValue(j6, z, count));
                            }
                            z7 = z4;
                            z3 = true;
                            z9 = true;
                        } else if (value.equals("S")) {
                            if (j7 == 0) {
                                z2 = true;
                            } else {
                                z2 = false;
                            }
                            if (!z10 && z2) {
                                z3 = true;
                            } else if (z9) {
                                z3 = true;
                                sb.append(paddedValue(j7, true, z ? Math.max(3, count) : 3));
                            } else {
                                z3 = true;
                                sb.append(paddedValue(j7, z, count));
                            }
                            z7 = z2;
                            z9 = false;
                        }
                        if (!z10 && !z11 && !z8) {
                            if (z7) {
                                sb.delete(length, sb.length());
                            }
                            z8 = z3;
                        }
                        i = i4 + 1;
                        i3 = length;
                        z6 = z10;
                        length2 = i5;
                    }
                    z7 = z5;
                    z9 = false;
                    z3 = true;
                    if (!z10) {
                    }
                    i = i4 + 1;
                    i3 = length;
                    z6 = z10;
                    length2 = i5;
                } else if (z10 || !z7) {
                    sb.append(value.toString());
                }
                z3 = true;
                if (!z10) {
                }
                i = i4 + 1;
                i3 = length;
                z6 = z10;
                length2 = i5;
            }
            length = i3;
            if (z11) {
                if (value.equals("y")) {
                    if (j == 0) {
                        z5 = true;
                    } else {
                        z5 = false;
                    }
                    if (z10) {
                        sb.append(paddedValue(j, z, count));
                    } else {
                        sb.append(paddedValue(j, z, count));
                    }
                } else if (value.equals("M")) {
                    if (j2 == 0) {
                        z5 = true;
                    } else {
                        z5 = false;
                    }
                    if (z10) {
                        sb.append(paddedValue(j2, z, count));
                    } else {
                        sb.append(paddedValue(j2, z, count));
                    }
                } else if (value.equals("d")) {
                    if (j3 == 0) {
                        z5 = true;
                    } else {
                        z5 = false;
                    }
                    if (z10) {
                        sb.append(paddedValue(j3, z, count));
                    } else {
                        sb.append(paddedValue(j3, z, count));
                    }
                } else if (value.equals("H")) {
                    if (j4 == 0) {
                        z5 = true;
                    } else {
                        z5 = false;
                    }
                    if (z10) {
                        sb.append(paddedValue(j4, z, count));
                    } else {
                        sb.append(paddedValue(j4, z, count));
                    }
                } else {
                    if (value.equals(m)) {
                        if (j5 == 0) {
                            z5 = true;
                        } else {
                            z5 = false;
                        }
                        if (z10) {
                        }
                        sb.append(paddedValue(j5, z, count));
                        z7 = z5;
                        z9 = false;
                        z3 = true;
                    } else if (value.equals("s")) {
                        if (j6 == 0) {
                            z4 = true;
                        } else {
                            z4 = false;
                        }
                        if (z10) {
                            sb.append(paddedValue(j6, z, count));
                        } else {
                            sb.append(paddedValue(j6, z, count));
                        }
                        z7 = z4;
                        z3 = true;
                        z9 = true;
                    } else if (value.equals("S")) {
                        if (j7 == 0) {
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                        if (!z10) {
                            if (z9) {
                                z3 = true;
                                sb.append(paddedValue(j7, true, z ? Math.max(3, count) : 3));
                            } else {
                                z3 = true;
                                sb.append(paddedValue(j7, z, count));
                            }
                        } else if (z9) {
                            z3 = true;
                            sb.append(paddedValue(j7, true, z ? Math.max(3, count) : 3));
                        } else {
                            z3 = true;
                            sb.append(paddedValue(j7, z, count));
                        }
                        z7 = z2;
                        z9 = false;
                    }
                    if (!z10) {
                    }
                    i = i4 + 1;
                    i3 = length;
                    z6 = z10;
                    length2 = i5;
                }
                z7 = z5;
                z9 = false;
                z3 = true;
                if (!z10) {
                }
                i = i4 + 1;
                i3 = length;
                z6 = z10;
                length2 = i5;
            } else if (z10) {
                sb.append(value.toString());
            } else {
                sb.append(value.toString());
            }
            z3 = true;
            if (!z10) {
            }
            i = i4 + 1;
            i3 = length;
            z6 = z10;
            length2 = i5;
        }
        return sb.toString();
    }

    public static String formatDuration(long j, String str) {
        return formatDuration(j, str, true);
    }

    public static String formatDuration(long j, String str, boolean z) {
        long j2;
        long j3;
        long j4;
        long j5;
        long j6;
        Validate.inclusiveBetween(0L, Long.MAX_VALUE, j, "durationMillis must not be negative");
        Token[] tokenArrLexx = lexx(str);
        if (Token.containsTokenWithValue(tokenArrLexx, "d")) {
            j3 = j / 86400000;
            j2 = j - (86400000 * j3);
        } else {
            j2 = j;
            j3 = 0;
        }
        if (Token.containsTokenWithValue(tokenArrLexx, "H")) {
            long j7 = j2 / 3600000;
            j2 -= 3600000 * j7;
            j4 = j7;
        } else {
            j4 = 0;
        }
        if (Token.containsTokenWithValue(tokenArrLexx, m)) {
            long j8 = j2 / 60000;
            j2 -= 60000 * j8;
            j5 = j8;
        } else {
            j5 = 0;
        }
        if (Token.containsTokenWithValue(tokenArrLexx, "s")) {
            j6 = j2 / 1000;
            j2 -= 1000 * j6;
        } else {
            j6 = 0;
        }
        return format(tokenArrLexx, 0L, 0L, j3, j4, j5, j6, j2, z);
    }

    public static String formatDurationHMS(long j) {
        return formatDuration(j, "HH:mm:ss.SSS");
    }

    public static String formatDurationISO(long j) {
        return formatDuration(j, ISO_EXTENDED_FORMAT_PATTERN, false);
    }

    public static String formatDurationWords(long j, boolean z, boolean z2) {
        String duration = formatDuration(j, "d' days 'H' hours 'm' minutes 's' seconds'");
        if (z) {
            duration = StringUtils.SPACE + duration;
            String strReplaceOnce = Strings.CS.replaceOnce(duration, " 0 days", "");
            if (strReplaceOnce.length() != duration.length()) {
                String strReplaceOnce2 = Strings.CS.replaceOnce(strReplaceOnce, " 0 hours", "");
                duration = strReplaceOnce2.length() != strReplaceOnce.length() ? Strings.CS.replaceOnce(strReplaceOnce2, " 0 minutes", "") : strReplaceOnce;
            }
            if (!duration.isEmpty()) {
                duration = duration.substring(1);
            }
        }
        if (z2) {
            String strReplaceOnce3 = Strings.CS.replaceOnce(duration, " 0 seconds", "");
            if (strReplaceOnce3.length() != duration.length()) {
                duration = Strings.CS.replaceOnce(strReplaceOnce3, " 0 minutes", "");
                if (duration.length() != strReplaceOnce3.length()) {
                    String strReplaceOnce4 = Strings.CS.replaceOnce(duration, " 0 hours", "");
                    if (strReplaceOnce4.length() != duration.length()) {
                        duration = Strings.CS.replaceOnce(strReplaceOnce4, " 0 days", "");
                    }
                } else {
                    duration = strReplaceOnce3;
                }
            }
        }
        return Strings.CS.replaceOnce(Strings.CS.replaceOnce(Strings.CS.replaceOnce(Strings.CS.replaceOnce(StringUtils.SPACE + duration, " 1 seconds", " 1 second"), " 1 minutes", " 1 minute"), " 1 hours", " 1 hour"), " 1 days", " 1 day").trim();
    }

    public static String formatPeriod(long j, long j2, String str) {
        return formatPeriod(j, j2, str, true, TimeZone.getDefault());
    }

    public static String formatPeriod(long j, long j2, String str, boolean z, TimeZone timeZone) {
        int i;
        Validate.isTrue(j <= j2, "startMillis must not be greater than endMillis", new Object[0]);
        Token[] tokenArrLexx = lexx(str);
        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.setTime(new Date(j));
        Calendar calendar2 = Calendar.getInstance(timeZone);
        calendar2.setTime(new Date(j2));
        long j3 = calendar2.get(14) - calendar.get(14);
        int i2 = calendar2.get(13) - calendar.get(13);
        int i3 = calendar2.get(12) - calendar.get(12);
        int i4 = calendar2.get(11) - calendar.get(11);
        int actualMaximum = calendar2.get(5) - calendar.get(5);
        int i5 = calendar2.get(2) - calendar.get(2);
        int i6 = calendar2.get(1) - calendar.get(1);
        while (j3 < 0) {
            j3 += 1000;
            i2--;
        }
        while (i2 < 0) {
            i2 += 60;
            i3--;
        }
        while (i3 < 0) {
            i3 += 60;
            i4--;
        }
        while (i4 < 0) {
            i4 += 24;
            actualMaximum--;
        }
        if (Token.containsTokenWithValue(tokenArrLexx, "M")) {
            while (actualMaximum < 0) {
                actualMaximum += calendar.getActualMaximum(5);
                i5--;
                calendar.add(2, 1);
            }
            while (i5 < 0) {
                i5 += 12;
                i6--;
            }
            if (!Token.containsTokenWithValue(tokenArrLexx, "y") && i6 != 0) {
                while (i6 != 0) {
                    i5 += i6 * 12;
                    i6 = 0;
                }
            }
        } else {
            if (!Token.containsTokenWithValue(tokenArrLexx, "y")) {
                int i7 = calendar2.get(1);
                if (i5 < 0) {
                    i7--;
                }
                while (calendar.get(1) != i7) {
                    int actualMaximum2 = actualMaximum + (calendar.getActualMaximum(6) - calendar.get(6));
                    if ((calendar instanceof GregorianCalendar) && calendar.get(2) == 1 && calendar.get(5) == 29) {
                        actualMaximum2++;
                    }
                    calendar.add(1, 1);
                    actualMaximum = actualMaximum2 + calendar.get(6);
                }
                i6 = 0;
            }
            while (calendar.get(2) != calendar2.get(2)) {
                actualMaximum += calendar.getActualMaximum(5);
                calendar.add(2, 1);
            }
            i5 = 0;
            while (actualMaximum < 0) {
                actualMaximum += calendar.getActualMaximum(5);
                i5--;
                calendar.add(2, 1);
            }
        }
        if (!Token.containsTokenWithValue(tokenArrLexx, "d")) {
            i4 += actualMaximum * 24;
            actualMaximum = 0;
        }
        if (!Token.containsTokenWithValue(tokenArrLexx, "H")) {
            i3 += i4 * 60;
            i4 = 0;
        }
        if (!Token.containsTokenWithValue(tokenArrLexx, m)) {
            i2 += i3 * 60;
            i3 = 0;
        }
        if (Token.containsTokenWithValue(tokenArrLexx, "s")) {
            i = i2;
        } else {
            j3 += ((long) i2) * 1000;
            i = 0;
        }
        return format(tokenArrLexx, i6, i5, actualMaximum, i4, i3, i, j3, z);
    }

    public static String formatPeriodISO(long j, long j2) {
        return formatPeriod(j, j2, ISO_EXTENDED_FORMAT_PATTERN, false, TimeZone.getDefault());
    }

    static Token[] lexx(String str) {
        String str2;
        ArrayList arrayList = new ArrayList(str.length());
        int i = -1;
        boolean z = false;
        boolean z2 = false;
        StringBuilder sb = null;
        Token token = null;
        for (int i2 = 0; i2 < str.length(); i2++) {
            char cCharAt = str.charAt(i2);
            if (!z || cCharAt == '\'') {
                if (cCharAt != '\'') {
                    if (cCharAt == 'H') {
                        str2 = "H";
                    } else if (cCharAt == 'M') {
                        str2 = "M";
                    } else if (cCharAt == 'S') {
                        str2 = "S";
                    } else if (cCharAt != '[') {
                        if (cCharAt == ']') {
                            if (!z2) {
                                throw new IllegalArgumentException("Attempting to close unopened optional block at index: " + i2);
                            }
                            z2 = false;
                        } else if (cCharAt == 'd') {
                            str2 = "d";
                        } else if (cCharAt == 'm') {
                            str2 = m;
                        } else if (cCharAt == 's') {
                            str2 = "s";
                        } else if (cCharAt == 'y') {
                            str2 = "y";
                        } else {
                            if (sb == null) {
                                sb = new StringBuilder();
                                arrayList.add(new Token(sb, z2, i));
                            }
                            sb.append(cCharAt);
                        }
                        str2 = null;
                    } else {
                        if (z2) {
                            throw new IllegalArgumentException("Nested optional block at index: " + i2);
                        }
                        i++;
                        str2 = null;
                        z2 = true;
                    }
                } else if (z) {
                    z = false;
                    sb = null;
                    str2 = null;
                } else {
                    sb = new StringBuilder();
                    arrayList.add(new Token(sb, z2, i));
                    str2 = null;
                    z = true;
                }
                if (str2 != null) {
                    if (token != null && token.getValue().equals(str2)) {
                        token.increment();
                    } else {
                        Token token2 = new Token(str2, z2, i);
                        arrayList.add(token2);
                        token = token2;
                    }
                    sb = null;
                }
            } else {
                sb.append(cCharAt);
            }
        }
        if (z) {
            throw new IllegalArgumentException("Unmatched quote in format: " + str);
        }
        if (!z2) {
            return (Token[]) arrayList.toArray(Token.EMPTY_ARRAY);
        }
        throw new IllegalArgumentException("Unmatched optional in format: " + str);
    }

    private static String paddedValue(long j, boolean z, int i) {
        String string = Long.toString(j);
        return z ? StringUtils.leftPad(string, i, '0') : string;
    }

    @Deprecated
    public DurationFormatUtils() {
    }
}
