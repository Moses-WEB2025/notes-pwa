package org.apache.commons.text;

import ch.qos.logback.core.CoreConstants;
import java.util.Arrays;
import org.apache.commons.lang3.ArrayUtils;

/* JADX INFO: loaded from: classes6.dex */
@Deprecated
public abstract class StrMatcher {
    private static final StrMatcher COMMA_MATCHER;
    private static final StrMatcher DOUBLE_QUOTE_MATCHER;
    private static final StrMatcher NONE_MATCHER;
    private static final StrMatcher QUOTE_MATCHER;
    private static final StrMatcher SINGLE_QUOTE_MATCHER;
    private static final StrMatcher SPACE_MATCHER;
    private static final StrMatcher SPLIT_MATCHER;
    private static final StrMatcher TAB_MATCHER;
    private static final StrMatcher TRIM_MATCHER;

    public abstract int isMatch(char[] cArr, int i, int i2, int i3);

    private static final class CharMatcher extends StrMatcher {

        /* JADX INFO: renamed from: ch, reason: collision with root package name */
        private final char f11ch;

        private CharMatcher(char c) {
            this.f11ch = c;
        }

        @Override // org.apache.commons.text.StrMatcher
        public int isMatch(char[] cArr, int i, int i2, int i3) {
            return this.f11ch == cArr[i] ? 1 : 0;
        }
    }

    private static final class CharSetMatcher extends StrMatcher {
        private final char[] chars;

        private CharSetMatcher(char[] cArr) {
            char[] cArr2 = (char[]) cArr.clone();
            this.chars = cArr2;
            Arrays.sort(cArr2);
        }

        @Override // org.apache.commons.text.StrMatcher
        public int isMatch(char[] cArr, int i, int i2, int i3) {
            return Arrays.binarySearch(this.chars, cArr[i]) >= 0 ? 1 : 0;
        }
    }

    private static final class NoMatcher extends StrMatcher {
        @Override // org.apache.commons.text.StrMatcher
        public int isMatch(char[] cArr, int i, int i2, int i3) {
            return 0;
        }

        private NoMatcher() {
        }
    }

    private static final class StringMatcher extends StrMatcher {
        private final char[] chars;

        private StringMatcher(String str) {
            this.chars = str.toCharArray();
        }

        @Override // org.apache.commons.text.StrMatcher
        public int isMatch(char[] cArr, int i, int i2, int i3) {
            int length = this.chars.length;
            if (i + length > i3) {
                return 0;
            }
            int i4 = 0;
            while (true) {
                char[] cArr2 = this.chars;
                if (i4 >= cArr2.length) {
                    return length;
                }
                if (cArr2[i4] != cArr[i]) {
                    return 0;
                }
                i4++;
                i++;
            }
        }

        public String toString() {
            return super.toString() + ' ' + Arrays.toString(this.chars);
        }
    }

    private static final class TrimMatcher extends StrMatcher {
        private TrimMatcher() {
        }

        @Override // org.apache.commons.text.StrMatcher
        public int isMatch(char[] cArr, int i, int i2, int i3) {
            return cArr[i] <= ' ' ? 1 : 0;
        }
    }

    static {
        COMMA_MATCHER = new CharMatcher(CoreConstants.COMMA_CHAR);
        TAB_MATCHER = new CharMatcher('\t');
        SPACE_MATCHER = new CharMatcher(' ');
        SPLIT_MATCHER = new CharSetMatcher(" \t\n\r\f".toCharArray());
        TRIM_MATCHER = new TrimMatcher();
        SINGLE_QUOTE_MATCHER = new CharMatcher(CoreConstants.SINGLE_QUOTE_CHAR);
        DOUBLE_QUOTE_MATCHER = new CharMatcher('\"');
        QUOTE_MATCHER = new CharSetMatcher("'\"".toCharArray());
        NONE_MATCHER = new NoMatcher();
    }

    public static StrMatcher charMatcher(char c) {
        return new CharMatcher(c);
    }

    public static StrMatcher charSetMatcher(char... cArr) {
        if (ArrayUtils.isEmpty(cArr)) {
            return NONE_MATCHER;
        }
        if (cArr.length == 1) {
            return new CharMatcher(cArr[0]);
        }
        return new CharSetMatcher(cArr);
    }

    public static StrMatcher charSetMatcher(String str) {
        if (str == null || str.isEmpty()) {
            return NONE_MATCHER;
        }
        if (str.length() == 1) {
            return new CharMatcher(str.charAt(0));
        }
        return new CharSetMatcher(str.toCharArray());
    }

    public static StrMatcher commaMatcher() {
        return COMMA_MATCHER;
    }

    public static StrMatcher doubleQuoteMatcher() {
        return DOUBLE_QUOTE_MATCHER;
    }

    public static StrMatcher noneMatcher() {
        return NONE_MATCHER;
    }

    public static StrMatcher quoteMatcher() {
        return QUOTE_MATCHER;
    }

    public static StrMatcher singleQuoteMatcher() {
        return SINGLE_QUOTE_MATCHER;
    }

    public static StrMatcher spaceMatcher() {
        return SPACE_MATCHER;
    }

    public static StrMatcher splitMatcher() {
        return SPLIT_MATCHER;
    }

    public static StrMatcher stringMatcher(String str) {
        if (str == null || str.isEmpty()) {
            return NONE_MATCHER;
        }
        return new StringMatcher(str);
    }

    public static StrMatcher tabMatcher() {
        return TAB_MATCHER;
    }

    public static StrMatcher trimMatcher() {
        return TRIM_MATCHER;
    }

    protected StrMatcher() {
    }

    public int isMatch(char[] cArr, int i) {
        return isMatch(cArr, i, 0, cArr.length);
    }
}
