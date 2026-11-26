package com.miui.common.tool;

import android.telephony.PhoneNumberUtils;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes2.dex */
public class TextProcessUtils {
    public static final String ONE_OR_MULTI_SPACE_REG = "\\s+";
    private static final Pattern PATTERN_INPUT_CHECKBOX = Pattern.compile("<input [^>]*type=\"checkbox\"[^>]*>");

    public static CharSequence trimEmptyLineSequence(CharSequence charSequence) {
        String string = charSequence.toString();
        String strTrim = string.trim();
        if (strTrim.isEmpty()) {
            return charSequence.subSequence(0, 0);
        }
        int iIndexOf = string.indexOf(strTrim);
        int length = strTrim.length() + iIndexOf;
        int iLastIndexOf = string.lastIndexOf(10, iIndexOf) + 1;
        int iIndexOf2 = string.indexOf(10, iLastIndexOf);
        if (iIndexOf2 < 0) {
            iIndexOf2 = string.length();
        }
        while (iIndexOf2 >= iLastIndexOf && iLastIndexOf < length && removeCheckboxTag(string.substring(iLastIndexOf, iIndexOf2)).trim().isEmpty()) {
            int i = iIndexOf2 + 1;
            if (i >= string.length()) {
                break;
            }
            int iIndexOf3 = string.indexOf(10, i);
            if (iIndexOf3 < 0) {
                iIndexOf3 = string.length();
            }
            iIndexOf2 = iIndexOf3;
            iLastIndexOf = i;
        }
        if (iIndexOf2 >= iLastIndexOf && iLastIndexOf <= length) {
            return charSequence.subSequence(iLastIndexOf, length);
        }
        return charSequence.subSequence(0, 0);
    }

    public static int parseRegNum(String str, String str2) {
        Matcher matcher = Pattern.compile(str2).matcher(str);
        int i = 0;
        while (matcher.find()) {
            i++;
        }
        return i;
    }

    public static <T> void removeSpans(CharSequence charSequence, Class<T> cls) {
        if (charSequence instanceof Spannable) {
            Spannable spannable = (Spannable) charSequence;
            for (Object obj : spannable.getSpans(0, charSequence.length(), cls)) {
                spannable.removeSpan(obj);
            }
        }
    }

    public static CharSequence removeEditSpans(CharSequence charSequence) {
        removeSpans(charSequence, StyleSpan.class);
        removeSpans(charSequence, AbsoluteSizeSpan.class);
        removeSpans(charSequence, AlignmentSpan.class);
        return charSequence;
    }

    public static CharSequence removeNoteWidgetSpans(CharSequence charSequence) {
        removeSpans(charSequence, BackgroundColorSpan.class);
        removeSpans(charSequence, StyleSpan.class);
        removeSpans(charSequence, AbsoluteSizeSpan.class);
        removeSpans(charSequence, AlignmentSpan.class);
        removeSpans(charSequence, UnderlineSpan.class);
        return charSequence;
    }

    public static CharSequence subStringByMaxLine(CharSequence charSequence, int i) {
        if (charSequence == null || charSequence.length() == 0) {
            return "";
        }
        int i2 = 0;
        for (int i3 = 0; i3 < charSequence.length(); i3++) {
            if ((charSequence.charAt(i3) + "").equals(System.lineSeparator())) {
                i2++;
            }
            if (i2 == i) {
                return charSequence.subSequence(0, i3);
            }
        }
        return charSequence;
    }

    public static CharSequence subStringByStartAndEndLine(CharSequence charSequence, int i, boolean z) {
        int i2;
        int i3;
        int i4;
        if (charSequence != null && charSequence.length() != 0) {
            int i5 = 0;
            int i6 = 0;
            while (true) {
                if (i5 >= charSequence.length()) {
                    i5 = -1;
                    break;
                }
                if ((charSequence.charAt(i5) + "").equals(System.lineSeparator())) {
                    i6++;
                }
                if (i6 == i) {
                    break;
                }
                i5++;
            }
            if (z && (i2 = i5 + 1) < charSequence.length()) {
                while (true) {
                    if (i2 >= charSequence.length()) {
                        i2 = -1;
                        break;
                    }
                    if ((charSequence.charAt(i2) + "").equals(System.lineSeparator())) {
                        break;
                    }
                    i2++;
                }
            } else {
                i2 = -1;
                break;
            }
            if (i5 != -1 && (i3 = i5 + 1) < charSequence.length()) {
                if (i2 != -1 && (i4 = i2 + 1) < charSequence.length()) {
                    return charSequence.subSequence(i3, i4);
                }
                return charSequence.subSequence(i3, charSequence.length());
            }
        }
        return "";
    }

    public static String formatPhoneNum(String str) {
        if (str != null) {
            return str.replaceAll(ONE_OR_MULTI_SPACE_REG, "");
        }
        return null;
    }

    public static boolean hasPhoneNumbers(String str) {
        for (String str2 : str.trim().split(ONE_OR_MULTI_SPACE_REG)) {
            if (PhoneNumberUtils.isGlobalPhoneNumber(str2)) {
                return true;
            }
        }
        return false;
    }

    public static String getStandardJsonString(String str) {
        return TextUtils.isEmpty(str) ? "" : str;
    }

    public static String removeCheckboxTag(String str) {
        return PATTERN_INPUT_CHECKBOX.matcher(str).replaceAll("");
    }
}
