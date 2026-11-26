package org.jsoup.internal;

import ch.qos.logback.classic.spi.CallerData;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import miuix.appcompat.view.menu.HyperMenuUtils;
import net.lingala.zip4j.util.InternalZipConstants;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.Validate;

/* JADX INFO: loaded from: classes6.dex */
public final class StringUtil {
    private static final int InitBuilderSize = 1024;
    private static final int MaxBuilderSize = 8192;
    static final String[] padding = {"", StringUtils.SPACE, "  ", "   ", "    ", "     ", "      ", "       ", "        ", "         ", "          ", "           ", "            ", "             ", "              ", "               ", "                ", "                 ", "                  ", "                   ", "                    "};
    private static final Pattern extraDotSegmentsPattern = Pattern.compile("^/(?>(?>\\.\\.?/)+)");
    private static final Pattern validUriScheme = Pattern.compile("^[a-zA-Z][a-zA-Z0-9+-.]*:");
    private static final Pattern controlChars = Pattern.compile("[\\x00-\\x1f]*");
    private static final SoftPool<StringBuilder> BuilderPool = new SoftPool<>(new Supplier() { // from class: org.jsoup.internal.StringUtil$$ExternalSyntheticLambda4
        @Override // java.util.function.Supplier
        public final Object get() {
            return StringUtil.lambda$static$0();
        }
    });

    public static boolean isActuallyWhitespace(int i) {
        return i == 32 || i == 9 || i == 10 || i == 12 || i == 13 || i == 160;
    }

    public static boolean isAsciiLetter(char c) {
        if (c < 'a' || c > 'z') {
            return c >= 'A' && c <= 'Z';
        }
        return true;
    }

    public static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    public static boolean isInvisibleChar(int i) {
        return i == 8203 || i == 173;
    }

    public static boolean isWhitespace(int i) {
        return i == 32 || i == 9 || i == 10 || i == 12 || i == 13;
    }

    public static String join(Collection<?> collection, String str) {
        return join(collection.iterator(), str);
    }

    public static String join(Iterator<?> it, String str) {
        if (!it.hasNext()) {
            return "";
        }
        String string = it.next().toString();
        if (!it.hasNext()) {
            return string;
        }
        StringJoiner stringJoiner = new StringJoiner(str);
        stringJoiner.add(string);
        while (it.hasNext()) {
            stringJoiner.add(it.next());
        }
        return stringJoiner.complete();
    }

    public static String join(String[] strArr, String str) {
        return join(Arrays.asList(strArr), str);
    }

    public static class StringJoiner {
        final String separator;
        StringBuilder sb = StringUtil.borrowBuilder();
        boolean first = true;

        public StringJoiner(String str) {
            this.separator = str;
        }

        public StringJoiner add(Object obj) {
            Validate.notNull(this.sb);
            if (!this.first) {
                this.sb.append(this.separator);
            }
            this.sb.append(obj);
            this.first = false;
            return this;
        }

        public StringJoiner append(Object obj) {
            Validate.notNull(this.sb);
            this.sb.append(obj);
            return this;
        }

        public String complete() {
            String strReleaseBuilder = StringUtil.releaseBuilder(this.sb);
            this.sb = null;
            return strReleaseBuilder;
        }
    }

    public static String padding(int i) {
        return padding(i, 30);
    }

    public static String padding(int i, int i2) {
        Validate.isTrue(i >= 0, "width must be >= 0");
        Validate.isTrue(i2 >= -1);
        if (i2 != -1) {
            i = Math.min(i, i2);
        }
        String[] strArr = padding;
        if (i < strArr.length) {
            return strArr[i];
        }
        char[] cArr = new char[i];
        for (int i3 = 0; i3 < i; i3++) {
            cArr[i3] = ' ';
        }
        return String.valueOf(cArr);
    }

    public static boolean isBlank(String str) {
        if (str != null && str.length() != 0) {
            int length = str.length();
            for (int i = 0; i < length; i++) {
                if (!isWhitespace(str.codePointAt(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean startsWithNewline(String str) {
        return (str == null || str.length() == 0 || str.charAt(0) != '\n') ? false : true;
    }

    public static boolean isNumeric(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String normaliseWhitespace(String str) {
        StringBuilder sbBorrowBuilder = borrowBuilder();
        appendNormalisedWhitespace(sbBorrowBuilder, str, false);
        return releaseBuilder(sbBorrowBuilder);
    }

    public static void appendNormalisedWhitespace(StringBuilder sb, String str, boolean z) {
        int length = str.length();
        int iCharCount = 0;
        boolean z2 = false;
        boolean z3 = false;
        while (iCharCount < length) {
            int iCodePointAt = str.codePointAt(iCharCount);
            if (isActuallyWhitespace(iCodePointAt)) {
                if ((!z || z2) && !z3) {
                    sb.append(' ');
                    z3 = true;
                }
            } else if (!isInvisibleChar(iCodePointAt)) {
                sb.appendCodePoint(iCodePointAt);
                z3 = false;
                z2 = true;
            }
            iCharCount += Character.charCount(iCodePointAt);
        }
    }

    public static boolean in(String str, String... strArr) {
        for (String str2 : strArr) {
            if (str2.equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean inSorted(String str, String[] strArr) {
        return Arrays.binarySearch(strArr, str) >= 0;
    }

    public static boolean isAscii(String str) {
        Validate.notNull(str);
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) > 127) {
                return false;
            }
        }
        return true;
    }

    public static URL resolve(URL url, String str) throws MalformedURLException {
        String strStripControlChars = stripControlChars(str);
        if (strStripControlChars.startsWith(CallerData.NA)) {
            strStripControlChars = url.getPath() + strStripControlChars;
        }
        URL url2 = new URL(url, strStripControlChars);
        String strReplaceFirst = extraDotSegmentsPattern.matcher(url2.getFile()).replaceFirst(InternalZipConstants.ZIP_FILE_SEPARATOR);
        if (url2.getRef() != null) {
            strReplaceFirst = strReplaceFirst + HyperMenuUtils.GROUP_TITLE_START + url2.getRef();
        }
        return new URL(url2.getProtocol(), url2.getHost(), url2.getPort(), strReplaceFirst);
    }

    public static String resolve(String str, String str2) {
        String strStripControlChars = stripControlChars(str);
        String strStripControlChars2 = stripControlChars(str2);
        try {
            try {
                return resolve(new URL(strStripControlChars), strStripControlChars2).toExternalForm();
            } catch (MalformedURLException unused) {
                return new URL(strStripControlChars2).toExternalForm();
            }
        } catch (MalformedURLException unused2) {
            return validUriScheme.matcher(strStripControlChars2).find() ? strStripControlChars2 : "";
        }
    }

    private static String stripControlChars(String str) {
        return controlChars.matcher(str).replaceAll("");
    }

    static /* synthetic */ StringBuilder lambda$static$0() {
        return new StringBuilder(1024);
    }

    public static StringBuilder borrowBuilder() {
        return BuilderPool.borrow();
    }

    public static String releaseBuilder(StringBuilder sb) {
        Validate.notNull(sb);
        String string = sb.toString();
        releaseBuilderVoid(sb);
        return string;
    }

    public static void releaseBuilderVoid(StringBuilder sb) {
        if (sb.length() <= 8192) {
            sb.delete(0, sb.length());
            BuilderPool.release(sb);
        }
    }

    public static Collector<CharSequence, ?, String> joining(final String str) {
        return Collector.of(new Supplier() { // from class: org.jsoup.internal.StringUtil$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return StringUtil.lambda$joining$1(str);
            }
        }, new BiConsumer() { // from class: org.jsoup.internal.StringUtil$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((StringUtil.StringJoiner) obj).add((CharSequence) obj2);
            }
        }, new BinaryOperator() { // from class: org.jsoup.internal.StringUtil$$ExternalSyntheticLambda2
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return StringUtil.lambda$joining$2((StringUtil.StringJoiner) obj, (StringUtil.StringJoiner) obj2);
            }
        }, new Function() { // from class: org.jsoup.internal.StringUtil$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((StringUtil.StringJoiner) obj).complete();
            }
        }, new Collector.Characteristics[0]);
    }

    static /* synthetic */ StringJoiner lambda$joining$1(String str) {
        return new StringJoiner(str);
    }

    static /* synthetic */ StringJoiner lambda$joining$2(StringJoiner stringJoiner, StringJoiner stringJoiner2) {
        stringJoiner.append(stringJoiner2.complete());
        return stringJoiner;
    }

    public static boolean isHexDigit(char c) {
        if (isDigit(c)) {
            return true;
        }
        if (c < 'a' || c > 'f') {
            return c >= 'A' && c <= 'F';
        }
        return true;
    }
}
