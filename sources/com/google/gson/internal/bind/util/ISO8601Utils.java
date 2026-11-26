package com.google.gson.internal.bind.util;

import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.Field;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import org.apache.commons.lang3.time.TimeZones;

/* JADX INFO: loaded from: classes2.dex */
public class ISO8601Utils {
    private static final TimeZone TIMEZONE_UTC = TimeZone.getTimeZone("UTC");
    private static final String UTC_ID = "UTC";

    private ISO8601Utils() {
    }

    public static String format(Date date) {
        return format(date, false, TIMEZONE_UTC);
    }

    public static String format(Date date, boolean z) {
        return format(date, z, TIMEZONE_UTC);
    }

    public static String format(Date date, boolean z, TimeZone timeZone) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(timeZone, Locale.US);
        gregorianCalendar.setTime(date);
        StringBuilder sb = new StringBuilder("yyyy-MM-ddThh:mm:ss".length() + (z ? ".sss".length() : 0) + (timeZone.getRawOffset() == 0 ? Field.BOOLEAN_SIGNATURE_PRIMITIVE : "+hh:mm").length());
        padInt(sb, gregorianCalendar.get(1), "yyyy".length());
        char c = CoreConstants.DASH_CHAR;
        sb.append(CoreConstants.DASH_CHAR);
        padInt(sb, gregorianCalendar.get(2) + 1, "MM".length());
        sb.append(CoreConstants.DASH_CHAR);
        padInt(sb, gregorianCalendar.get(5), "dd".length());
        sb.append('T');
        padInt(sb, gregorianCalendar.get(11), "hh".length());
        sb.append(CoreConstants.COLON_CHAR);
        padInt(sb, gregorianCalendar.get(12), "mm".length());
        sb.append(CoreConstants.COLON_CHAR);
        padInt(sb, gregorianCalendar.get(13), "ss".length());
        if (z) {
            sb.append('.');
            padInt(sb, gregorianCalendar.get(14), "sss".length());
        }
        int offset = timeZone.getOffset(gregorianCalendar.getTimeInMillis());
        if (offset != 0) {
            int i = offset / 60000;
            int iAbs = Math.abs(i / 60);
            int iAbs2 = Math.abs(i % 60);
            if (offset >= 0) {
                c = '+';
            }
            sb.append(c);
            padInt(sb, iAbs, "hh".length());
            sb.append(CoreConstants.COLON_CHAR);
            padInt(sb, iAbs2, "mm".length());
        } else {
            sb.append('Z');
        }
        return sb.toString();
    }

    public static Date parse(String str, ParsePosition parsePosition) throws ParseException {
        int i;
        int i2;
        int i3;
        int i4;
        int length;
        TimeZone timeZone;
        char cCharAt;
        try {
            int index = parsePosition.getIndex();
            int i5 = index + 4;
            int i6 = parseInt(str, index, i5);
            if (checkOffset(str, i5, CoreConstants.DASH_CHAR)) {
                i5 = index + 5;
            }
            int i7 = i5 + 2;
            int i8 = parseInt(str, i5, i7);
            if (checkOffset(str, i7, CoreConstants.DASH_CHAR)) {
                i7 = i5 + 3;
            }
            int i9 = i7 + 2;
            int i10 = parseInt(str, i7, i9);
            boolean zCheckOffset = checkOffset(str, i9, 'T');
            if (!zCheckOffset && str.length() <= i9) {
                GregorianCalendar gregorianCalendar = new GregorianCalendar(i6, i8 - 1, i10);
                gregorianCalendar.setLenient(false);
                parsePosition.setIndex(i9);
                return gregorianCalendar.getTime();
            }
            if (zCheckOffset) {
                int i11 = i7 + 5;
                int i12 = parseInt(str, i7 + 3, i11);
                if (checkOffset(str, i11, CoreConstants.COLON_CHAR)) {
                    i11 = i7 + 6;
                }
                int i13 = i11 + 2;
                i2 = parseInt(str, i11, i13);
                if (checkOffset(str, i13, CoreConstants.COLON_CHAR)) {
                    i13 = i11 + 3;
                }
                if (str.length() <= i13 || (cCharAt = str.charAt(i13)) == 'Z' || cCharAt == '+' || cCharAt == '-') {
                    i9 = i13;
                    i = i12;
                    i3 = 0;
                    i4 = 0;
                } else {
                    int i14 = i13 + 2;
                    i4 = parseInt(str, i13, i14);
                    if (i4 > 59 && i4 < 63) {
                        i4 = 59;
                    }
                    if (checkOffset(str, i14, '.')) {
                        int i15 = i13 + 3;
                        int iIndexOfNonDigit = indexOfNonDigit(str, i13 + 4);
                        int iMin = Math.min(iIndexOfNonDigit, i13 + 6);
                        int i16 = parseInt(str, i15, iMin);
                        int i17 = iMin - i15;
                        if (i17 == 1) {
                            i16 *= 100;
                        } else if (i17 == 2) {
                            i16 *= 10;
                        }
                        i = i12;
                        i9 = iIndexOfNonDigit;
                        i3 = i16;
                    } else {
                        i = i12;
                        i9 = i14;
                        i3 = 0;
                    }
                }
            } else {
                i = 0;
                i2 = 0;
                i3 = 0;
                i4 = 0;
            }
            if (str.length() <= i9) {
                throw new IllegalArgumentException("No time zone indicator");
            }
            char cCharAt2 = str.charAt(i9);
            if (cCharAt2 == 'Z') {
                timeZone = TIMEZONE_UTC;
                length = i9 + 1;
            } else {
                if (cCharAt2 != '+' && cCharAt2 != '-') {
                    throw new IndexOutOfBoundsException("Invalid time zone indicator '" + cCharAt2 + "'");
                }
                String strSubstring = str.substring(i9);
                if (strSubstring.length() < 5) {
                    strSubstring = strSubstring + "00";
                }
                length = i9 + strSubstring.length();
                if (strSubstring.equals("+0000") || strSubstring.equals("+00:00")) {
                    timeZone = TIMEZONE_UTC;
                } else {
                    String str2 = TimeZones.GMT_ID + strSubstring;
                    TimeZone timeZone2 = TimeZone.getTimeZone(str2);
                    String id = timeZone2.getID();
                    if (!id.equals(str2) && !id.replace(":", "").equals(str2)) {
                        throw new IndexOutOfBoundsException("Mismatching time zone indicator: " + str2 + " given, resolves to " + timeZone2.getID());
                    }
                    timeZone = timeZone2;
                }
            }
            GregorianCalendar gregorianCalendar2 = new GregorianCalendar(timeZone);
            gregorianCalendar2.setLenient(false);
            gregorianCalendar2.set(1, i6);
            gregorianCalendar2.set(2, i8 - 1);
            gregorianCalendar2.set(5, i10);
            gregorianCalendar2.set(11, i);
            gregorianCalendar2.set(12, i2);
            gregorianCalendar2.set(13, i4);
            gregorianCalendar2.set(14, i3);
            parsePosition.setIndex(length);
            return gregorianCalendar2.getTime();
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            String str3 = str == null ? null : "\"" + str + '\"';
            String message = e.getMessage();
            if (message == null || message.isEmpty()) {
                message = "(" + e.getClass().getName() + ")";
            }
            ParseException parseException = new ParseException("Failed to parse date [" + str3 + "]: " + message, parsePosition.getIndex());
            parseException.initCause(e);
            throw parseException;
        }
    }

    private static boolean checkOffset(String str, int i, char c) {
        return i < str.length() && str.charAt(i) == c;
    }

    private static int parseInt(String str, int i, int i2) throws NumberFormatException {
        int i3;
        int i4;
        if (i < 0 || i2 > str.length() || i > i2) {
            throw new NumberFormatException(str);
        }
        if (i < i2) {
            i4 = i + 1;
            int iDigit = Character.digit(str.charAt(i), 10);
            if (iDigit < 0) {
                throw new NumberFormatException("Invalid number: " + str.substring(i, i2));
            }
            i3 = -iDigit;
        } else {
            i3 = 0;
            i4 = i;
        }
        while (i4 < i2) {
            int i5 = i4 + 1;
            int iDigit2 = Character.digit(str.charAt(i4), 10);
            if (iDigit2 < 0) {
                throw new NumberFormatException("Invalid number: " + str.substring(i, i2));
            }
            i3 = (i3 * 10) - iDigit2;
            i4 = i5;
        }
        return -i3;
    }

    private static void padInt(StringBuilder sb, int i, int i2) {
        String string = Integer.toString(i);
        for (int length = i2 - string.length(); length > 0; length--) {
            sb.append('0');
        }
        sb.append(string);
    }

    private static int indexOfNonDigit(String str, int i) {
        while (i < str.length()) {
            char cCharAt = str.charAt(i);
            if (cCharAt < '0' || cCharAt > '9') {
                return i;
            }
            i++;
        }
        return str.length();
    }
}
