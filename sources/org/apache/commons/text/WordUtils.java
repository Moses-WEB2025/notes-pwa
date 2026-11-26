package org.apache.commons.text;

import androidx.collection.SieveCacheKt;
import java.util.HashSet;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/* JADX INFO: loaded from: classes6.dex */
public class WordUtils {
    static /* synthetic */ boolean lambda$generateIsDelimiterFunction$0(Integer num) {
        return false;
    }

    public static String abbreviate(String str, int i, int i2, String str2) {
        boolean z = true;
        Validate.isTrue(i2 >= -1, "upper value cannot be less than -1", new Object[0]);
        if (i2 < i && i2 != -1) {
            z = false;
        }
        Validate.isTrue(z, "upper value is less than lower value", new Object[0]);
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        if (i > str.length()) {
            i = str.length();
        }
        if (i2 == -1 || i2 > str.length()) {
            i2 = str.length();
        }
        StringBuilder sb = new StringBuilder();
        int iIndexOf = StringUtils.indexOf(str, StringUtils.SPACE, i);
        if (iIndexOf == -1) {
            sb.append((CharSequence) str, 0, i2);
            if (i2 != str.length()) {
                sb.append(StringUtils.defaultString(str2));
            }
        } else {
            sb.append((CharSequence) str, 0, Math.min(iIndexOf, i2));
            sb.append(StringUtils.defaultString(str2));
        }
        return sb.toString();
    }

    public static String capitalize(String str) {
        return capitalize(str, null);
    }

    public static String capitalize(String str, char... cArr) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        Predicate<Integer> predicateGenerateIsDelimiterFunction = generateIsDelimiterFunction(cArr);
        int length = str.length();
        int[] iArr = new int[length];
        int iCharCount = 0;
        int i = 0;
        while (true) {
            boolean z = true;
            while (iCharCount < length) {
                int iCodePointAt = str.codePointAt(iCharCount);
                if (predicateGenerateIsDelimiterFunction.test(Integer.valueOf(iCodePointAt))) {
                    iArr[i] = iCodePointAt;
                    iCharCount += Character.charCount(iCodePointAt);
                    i++;
                } else if (z) {
                    int titleCase = Character.toTitleCase(iCodePointAt);
                    iArr[i] = titleCase;
                    iCharCount += Character.charCount(titleCase);
                    z = false;
                    i++;
                } else {
                    iArr[i] = iCodePointAt;
                    iCharCount += Character.charCount(iCodePointAt);
                    i++;
                }
            }
            return new String(iArr, 0, i);
        }
    }

    public static String capitalizeFully(String str) {
        return capitalizeFully(str, null);
    }

    public static String capitalizeFully(String str, char... cArr) {
        return StringUtils.isEmpty(str) ? str : capitalize(str.toLowerCase(), cArr);
    }

    public static boolean containsAllWords(CharSequence charSequence, CharSequence... charSequenceArr) {
        if (StringUtils.isEmpty(charSequence) || ArrayUtils.isEmpty(charSequenceArr)) {
            return false;
        }
        for (CharSequence charSequence2 : charSequenceArr) {
            if (StringUtils.isBlank(charSequence2) || !Pattern.compile(".*\\b" + Pattern.quote(charSequence2.toString()) + "\\b.*").matcher(charSequence).matches()) {
                return false;
            }
        }
        return true;
    }

    private static Predicate<Integer> generateIsDelimiterFunction(char[] cArr) {
        if (cArr == null || cArr.length == 0) {
            return cArr == null ? new Predicate() { // from class: org.apache.commons.text.WordUtils$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return Character.isWhitespace(((Integer) obj).intValue());
                }
            } : new Predicate() { // from class: org.apache.commons.text.WordUtils$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return WordUtils.lambda$generateIsDelimiterFunction$0((Integer) obj);
                }
            };
        }
        final HashSet hashSet = new HashSet();
        for (int i = 0; i < cArr.length; i++) {
            hashSet.add(Integer.valueOf(Character.codePointAt(cArr, i)));
        }
        Objects.requireNonNull(hashSet);
        return new Predicate() { // from class: org.apache.commons.text.WordUtils$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return hashSet.contains((Integer) obj);
            }
        };
    }

    public static String initials(String str) {
        return initials(str, null);
    }

    public static String initials(String str, char... cArr) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        if (cArr != null && cArr.length == 0) {
            return "";
        }
        Predicate<Integer> predicateGenerateIsDelimiterFunction = generateIsDelimiterFunction(cArr);
        int length = str.length();
        int[] iArr = new int[(length / 2) + 1];
        boolean z = true;
        int iCharCount = 0;
        int i = 0;
        while (iCharCount < length) {
            int iCodePointAt = str.codePointAt(iCharCount);
            if (predicateGenerateIsDelimiterFunction.test(Integer.valueOf(iCodePointAt))) {
                z = true;
            } else if (z) {
                iArr[i] = iCodePointAt;
                i++;
                z = false;
            }
            iCharCount += Character.charCount(iCodePointAt);
        }
        return new String(iArr, 0, i);
    }

    @Deprecated
    public static boolean isDelimiter(char c, char[] cArr) {
        if (cArr == null) {
            return Character.isWhitespace(c);
        }
        for (char c2 : cArr) {
            if (c == c2) {
                return true;
            }
        }
        return false;
    }

    @Deprecated
    public static boolean isDelimiter(int i, char[] cArr) {
        if (cArr == null) {
            return Character.isWhitespace(i);
        }
        for (int i2 = 0; i2 < cArr.length; i2++) {
            if (Character.codePointAt(cArr, i2) == i) {
                return true;
            }
        }
        return false;
    }

    public static String swapCase(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        int length = str.length();
        int[] iArr = new int[length];
        boolean zIsWhitespace = true;
        int iCharCount = 0;
        int i = 0;
        while (iCharCount < length) {
            int iCodePointAt = str.codePointAt(iCharCount);
            if (Character.isUpperCase(iCodePointAt) || Character.isTitleCase(iCodePointAt)) {
                iCodePointAt = Character.toLowerCase(iCodePointAt);
            } else {
                if (!Character.isLowerCase(iCodePointAt)) {
                    zIsWhitespace = Character.isWhitespace(iCodePointAt);
                } else if (zIsWhitespace) {
                    iCodePointAt = Character.toTitleCase(iCodePointAt);
                } else {
                    iCodePointAt = Character.toUpperCase(iCodePointAt);
                }
                iArr[i] = iCodePointAt;
                iCharCount += Character.charCount(iCodePointAt);
                i++;
            }
            zIsWhitespace = false;
            iArr[i] = iCodePointAt;
            iCharCount += Character.charCount(iCodePointAt);
            i++;
        }
        return new String(iArr, 0, i);
    }

    public static String uncapitalize(String str) {
        return uncapitalize(str, null);
    }

    public static String uncapitalize(String str, char... cArr) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        Predicate<Integer> predicateGenerateIsDelimiterFunction = generateIsDelimiterFunction(cArr);
        int length = str.length();
        int[] iArr = new int[length];
        int iCharCount = 0;
        int i = 0;
        while (true) {
            boolean z = true;
            while (iCharCount < length) {
                int iCodePointAt = str.codePointAt(iCharCount);
                if (predicateGenerateIsDelimiterFunction.test(Integer.valueOf(iCodePointAt))) {
                    iArr[i] = iCodePointAt;
                    iCharCount += Character.charCount(iCodePointAt);
                    i++;
                } else if (z) {
                    int lowerCase = Character.toLowerCase(iCodePointAt);
                    iArr[i] = lowerCase;
                    iCharCount += Character.charCount(lowerCase);
                    z = false;
                    i++;
                } else {
                    iArr[i] = iCodePointAt;
                    iCharCount += Character.charCount(iCodePointAt);
                    i++;
                }
            }
            return new String(iArr, 0, i);
        }
    }

    public static String wrap(String str, int i) {
        return wrap(str, i, null, false);
    }

    public static String wrap(String str, int i, String str2, boolean z) {
        return wrap(str, i, str2, z, StringUtils.SPACE);
    }

    public static String wrap(String str, int i, String str2, boolean z, String str3) {
        int iEnd;
        int iStart;
        if (str == null) {
            return null;
        }
        if (str2 == null) {
            str2 = System.lineSeparator();
        }
        if (i < 1) {
            i = 1;
        }
        if (StringUtils.isBlank(str3)) {
            str3 = StringUtils.SPACE;
        }
        Pattern patternCompile = Pattern.compile(str3);
        int length = str.length();
        StringBuilder sb = new StringBuilder(length + 32);
        int iEnd2 = 0;
        loop0: while (true) {
            iEnd = -1;
            while (true) {
                if (iEnd2 >= length) {
                    break loop0;
                }
                Matcher matcher = patternCompile.matcher(str.substring(iEnd2, Math.min((int) Math.min(SieveCacheKt.NodeLinkMask, ((long) (iEnd2 + i)) + 1), length)));
                if (matcher.find()) {
                    if (matcher.start() == 0) {
                        iEnd = matcher.end();
                        if (iEnd != 0) {
                            iEnd2 += matcher.end();
                        } else {
                            iEnd2++;
                        }
                    }
                    iStart = matcher.start() + iEnd2;
                } else {
                    iStart = -1;
                }
                if (length - iEnd2 <= i) {
                    break loop0;
                }
                while (matcher.find()) {
                    iStart = matcher.start() + iEnd2;
                }
                if (iStart < iEnd2) {
                    if (z) {
                        if (iEnd == 0) {
                            iEnd2--;
                        }
                        int i2 = i + iEnd2;
                        sb.append((CharSequence) str, iEnd2, i2);
                        sb.append(str2);
                        iEnd2 = i2;
                        break;
                    }
                    Matcher matcher2 = patternCompile.matcher(str.substring(iEnd2 + i));
                    if (matcher2.find()) {
                        iEnd = matcher2.end() - matcher2.start();
                        iStart = matcher2.start() + iEnd2 + i;
                    }
                    if (iStart >= 0) {
                        if (iEnd == 0 && iEnd2 != 0) {
                            iEnd2--;
                        }
                        sb.append((CharSequence) str, iEnd2, iStart);
                        sb.append(str2);
                    } else {
                        if (iEnd == 0 && iEnd2 != 0) {
                            iEnd2--;
                        }
                        sb.append((CharSequence) str, iEnd2, str.length());
                        iEnd2 = length;
                        break;
                    }
                } else {
                    sb.append((CharSequence) str, iEnd2, iStart);
                    sb.append(str2);
                }
                iEnd2 = iStart + 1;
            }
        }
        if (iEnd == 0 && iEnd2 < length) {
            iEnd2--;
        }
        sb.append((CharSequence) str, iEnd2, str.length());
        return sb.toString();
    }
}
