package org.apache.commons.text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.text.matcher.StringMatcher;
import org.apache.commons.text.matcher.StringMatcherFactory;

/* JADX INFO: loaded from: classes6.dex */
public class StringTokenizer implements ListIterator<String>, Cloneable {
    private static final StringTokenizer CSV_TOKENIZER_PROTOTYPE = new StringTokenizer().setDelimiterMatcher(StringMatcherFactory.INSTANCE.commaMatcher()).setQuoteMatcher(StringMatcherFactory.INSTANCE.doubleQuoteMatcher()).setIgnoredMatcher(StringMatcherFactory.INSTANCE.noneMatcher()).setTrimmerMatcher(StringMatcherFactory.INSTANCE.trimMatcher()).setEmptyTokenAsNull(false).setIgnoreEmptyTokens(false);
    private static final StringTokenizer TSV_TOKENIZER_PROTOTYPE = new StringTokenizer().setDelimiterMatcher(StringMatcherFactory.INSTANCE.tabMatcher()).setQuoteMatcher(StringMatcherFactory.INSTANCE.doubleQuoteMatcher()).setIgnoredMatcher(StringMatcherFactory.INSTANCE.noneMatcher()).setTrimmerMatcher(StringMatcherFactory.INSTANCE.trimMatcher()).setEmptyTokenAsNull(false).setIgnoreEmptyTokens(false);
    private char[] chars;
    private StringMatcher delimMatcher;
    private boolean emptyAsNull;
    private boolean ignoreEmptyTokens;
    private StringMatcher ignoredMatcher;
    private StringMatcher quoteMatcher;
    private int tokenPos;
    private String[] tokens;
    private StringMatcher trimmerMatcher;

    private static StringTokenizer getCSVClone() {
        return (StringTokenizer) CSV_TOKENIZER_PROTOTYPE.clone();
    }

    public static StringTokenizer getCSVInstance() {
        return getCSVClone();
    }

    public static StringTokenizer getCSVInstance(char[] cArr) {
        return getCSVClone().reset(cArr);
    }

    public static StringTokenizer getCSVInstance(String str) {
        return getCSVClone().reset(str);
    }

    private static StringTokenizer getTSVClone() {
        return (StringTokenizer) TSV_TOKENIZER_PROTOTYPE.clone();
    }

    public static StringTokenizer getTSVInstance() {
        return getTSVClone();
    }

    public static StringTokenizer getTSVInstance(char[] cArr) {
        return getTSVClone().reset(cArr);
    }

    public static StringTokenizer getTSVInstance(String str) {
        return getTSVClone().reset(str);
    }

    public StringTokenizer() {
        this.delimMatcher = StringMatcherFactory.INSTANCE.splitMatcher();
        this.quoteMatcher = StringMatcherFactory.INSTANCE.noneMatcher();
        this.ignoredMatcher = StringMatcherFactory.INSTANCE.noneMatcher();
        this.trimmerMatcher = StringMatcherFactory.INSTANCE.noneMatcher();
        this.ignoreEmptyTokens = true;
        this.chars = null;
    }

    public StringTokenizer(char[] cArr) {
        this.delimMatcher = StringMatcherFactory.INSTANCE.splitMatcher();
        this.quoteMatcher = StringMatcherFactory.INSTANCE.noneMatcher();
        this.ignoredMatcher = StringMatcherFactory.INSTANCE.noneMatcher();
        this.trimmerMatcher = StringMatcherFactory.INSTANCE.noneMatcher();
        this.ignoreEmptyTokens = true;
        this.chars = cArr != null ? (char[]) cArr.clone() : null;
    }

    public StringTokenizer(char[] cArr, char c) {
        this(cArr);
        setDelimiterChar(c);
    }

    public StringTokenizer(char[] cArr, char c, char c2) {
        this(cArr, c);
        setQuoteChar(c2);
    }

    public StringTokenizer(char[] cArr, String str) {
        this(cArr);
        setDelimiterString(str);
    }

    public StringTokenizer(char[] cArr, StringMatcher stringMatcher) {
        this(cArr);
        setDelimiterMatcher(stringMatcher);
    }

    public StringTokenizer(char[] cArr, StringMatcher stringMatcher, StringMatcher stringMatcher2) {
        this(cArr, stringMatcher);
        setQuoteMatcher(stringMatcher2);
    }

    public StringTokenizer(String str) {
        this.delimMatcher = StringMatcherFactory.INSTANCE.splitMatcher();
        this.quoteMatcher = StringMatcherFactory.INSTANCE.noneMatcher();
        this.ignoredMatcher = StringMatcherFactory.INSTANCE.noneMatcher();
        this.trimmerMatcher = StringMatcherFactory.INSTANCE.noneMatcher();
        this.ignoreEmptyTokens = true;
        this.chars = str != null ? str.toCharArray() : null;
    }

    public StringTokenizer(String str, char c) {
        this(str);
        setDelimiterChar(c);
    }

    public StringTokenizer(String str, char c, char c2) {
        this(str, c);
        setQuoteChar(c2);
    }

    public StringTokenizer(String str, String str2) {
        this(str);
        setDelimiterString(str2);
    }

    public StringTokenizer(String str, StringMatcher stringMatcher) {
        this(str);
        setDelimiterMatcher(stringMatcher);
    }

    public StringTokenizer(String str, StringMatcher stringMatcher, StringMatcher stringMatcher2) {
        this(str, stringMatcher);
        setQuoteMatcher(stringMatcher2);
    }

    @Override // java.util.ListIterator
    public void add(String str) {
        throw new UnsupportedOperationException("add() is unsupported");
    }

    private void addToken(List<String> list, String str) {
        if (str == null || str.isEmpty()) {
            if (isIgnoreEmptyTokens()) {
                return;
            }
            if (isEmptyTokenAsNull()) {
                str = null;
            }
        }
        list.add(str);
    }

    private void checkTokenized() {
        List<String> list;
        if (this.tokens == null) {
            char[] cArr = this.chars;
            if (cArr == null) {
                list = tokenize(null, 0, 0);
            } else {
                list = tokenize(cArr, 0, cArr.length);
            }
            this.tokens = (String[]) list.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
        }
    }

    public Object clone() {
        try {
            return cloneReset();
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }

    Object cloneReset() throws CloneNotSupportedException {
        StringTokenizer stringTokenizer = (StringTokenizer) super.clone();
        char[] cArr = stringTokenizer.chars;
        if (cArr != null) {
            stringTokenizer.chars = (char[]) cArr.clone();
        }
        stringTokenizer.reset();
        return stringTokenizer;
    }

    public String getContent() {
        if (this.chars == null) {
            return null;
        }
        return new String(this.chars);
    }

    public StringMatcher getDelimiterMatcher() {
        return this.delimMatcher;
    }

    public StringMatcher getIgnoredMatcher() {
        return this.ignoredMatcher;
    }

    public StringMatcher getQuoteMatcher() {
        return this.quoteMatcher;
    }

    public String[] getTokenArray() {
        checkTokenized();
        return (String[]) this.tokens.clone();
    }

    public List<String> getTokenList() {
        checkTokenized();
        return new ArrayList(Arrays.asList(this.tokens));
    }

    public StringMatcher getTrimmerMatcher() {
        return this.trimmerMatcher;
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public boolean hasNext() {
        checkTokenized();
        return this.tokenPos < this.tokens.length;
    }

    @Override // java.util.ListIterator
    public boolean hasPrevious() {
        checkTokenized();
        return this.tokenPos > 0;
    }

    public boolean isEmptyTokenAsNull() {
        return this.emptyAsNull;
    }

    public boolean isIgnoreEmptyTokens() {
        return this.ignoreEmptyTokens;
    }

    private boolean isQuote(char[] cArr, int i, int i2, int i3, int i4) {
        for (int i5 = 0; i5 < i4; i5++) {
            int i6 = i + i5;
            if (i6 >= i2 || cArr[i6] != cArr[i3 + i5]) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public String next() {
        if (hasNext()) {
            String[] strArr = this.tokens;
            int i = this.tokenPos;
            this.tokenPos = i + 1;
            return strArr[i];
        }
        throw new NoSuchElementException();
    }

    @Override // java.util.ListIterator
    public int nextIndex() {
        return this.tokenPos;
    }

    public String nextToken() {
        if (!hasNext()) {
            return null;
        }
        String[] strArr = this.tokens;
        int i = this.tokenPos;
        this.tokenPos = i + 1;
        return strArr[i];
    }

    @Override // java.util.ListIterator
    public String previous() {
        if (hasPrevious()) {
            String[] strArr = this.tokens;
            int i = this.tokenPos - 1;
            this.tokenPos = i;
            return strArr[i];
        }
        throw new NoSuchElementException();
    }

    @Override // java.util.ListIterator
    public int previousIndex() {
        return this.tokenPos - 1;
    }

    public String previousToken() {
        if (!hasPrevious()) {
            return null;
        }
        String[] strArr = this.tokens;
        int i = this.tokenPos - 1;
        this.tokenPos = i;
        return strArr[i];
    }

    private int readNextToken(char[] cArr, int i, int i2, TextStringBuilder textStringBuilder, List<String> list) {
        int i3 = i;
        while (i3 < i2) {
            int iMax = Math.max(getIgnoredMatcher().isMatch(cArr, i3, i3, i2), getTrimmerMatcher().isMatch(cArr, i3, i3, i2));
            if (iMax == 0 || getDelimiterMatcher().isMatch(cArr, i3, i3, i2) > 0 || getQuoteMatcher().isMatch(cArr, i3, i3, i2) > 0) {
                break;
            }
            i3 += iMax;
        }
        if (i3 >= i2) {
            addToken(list, "");
            return -1;
        }
        int iIsMatch = getDelimiterMatcher().isMatch(cArr, i3, i3, i2);
        if (iIsMatch > 0) {
            addToken(list, "");
            return i3 + iIsMatch;
        }
        int iIsMatch2 = getQuoteMatcher().isMatch(cArr, i3, i3, i2);
        if (iIsMatch2 > 0) {
            int i4 = i3;
            return readWithQuotes(cArr, i4 + iIsMatch2, i2, textStringBuilder, list, i4, iIsMatch2);
        }
        return readWithQuotes(cArr, i3, i2, textStringBuilder, list, 0, 0);
    }

    private int readWithQuotes(char[] cArr, int i, int i2, TextStringBuilder textStringBuilder, List<String> list, int i3, int i4) {
        char[] cArr2;
        int i5;
        int i6 = i2;
        textStringBuilder.clear();
        boolean z = i4 > 0;
        int i7 = i;
        int size = 0;
        while (i7 < i6) {
            if (z) {
                int i8 = i7;
                if (isQuote(cArr, i7, i6, i3, i4)) {
                    i7 = i8 + i4;
                    i6 = i2;
                    if (isQuote(cArr, i7, i6, i3, i4)) {
                        textStringBuilder.append(cArr, i8, i4);
                        i7 = (i4 * 2) + i8;
                        size = textStringBuilder.size();
                    } else {
                        z = false;
                    }
                } else {
                    cArr2 = cArr;
                    i6 = i2;
                    i5 = i8;
                    textStringBuilder.append(cArr2[i5]);
                    size = textStringBuilder.size();
                    i7 = i5 + 1;
                }
            } else {
                cArr2 = cArr;
                int i9 = i7;
                int iIsMatch = getDelimiterMatcher().isMatch(cArr2, i9, i, i6);
                if (iIsMatch > 0) {
                    addToken(list, textStringBuilder.substring(0, size));
                    return iIsMatch + i9;
                }
                if (i4 > 0) {
                    i5 = i9;
                    if (isQuote(cArr2, i5, i6, i3, i4)) {
                        i7 = i5 + i4;
                        z = true;
                    }
                } else {
                    i5 = i9;
                }
                int iIsMatch2 = getIgnoredMatcher().isMatch(cArr2, i5, i, i6);
                if (iIsMatch2 <= 0) {
                    iIsMatch2 = getTrimmerMatcher().isMatch(cArr2, i5, i, i6);
                    if (iIsMatch2 > 0) {
                        textStringBuilder.append(cArr2, i5, iIsMatch2);
                    } else {
                        textStringBuilder.append(cArr2[i5]);
                        size = textStringBuilder.size();
                        i7 = i5 + 1;
                    }
                }
                i7 = i5 + iIsMatch2;
            }
        }
        addToken(list, textStringBuilder.substring(0, size));
        return -1;
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("remove() is unsupported");
    }

    public StringTokenizer reset() {
        this.tokenPos = 0;
        this.tokens = null;
        return this;
    }

    public StringTokenizer reset(char[] cArr) {
        reset();
        this.chars = cArr != null ? (char[]) cArr.clone() : null;
        return this;
    }

    public StringTokenizer reset(String str) {
        reset();
        this.chars = str != null ? str.toCharArray() : null;
        return this;
    }

    @Override // java.util.ListIterator
    public void set(String str) {
        throw new UnsupportedOperationException("set() is unsupported");
    }

    public StringTokenizer setDelimiterChar(char c) {
        return setDelimiterMatcher(StringMatcherFactory.INSTANCE.charMatcher(c));
    }

    public StringTokenizer setDelimiterMatcher(StringMatcher stringMatcher) {
        if (stringMatcher == null) {
            stringMatcher = StringMatcherFactory.INSTANCE.noneMatcher();
        }
        this.delimMatcher = stringMatcher;
        return this;
    }

    public StringTokenizer setDelimiterString(String str) {
        return setDelimiterMatcher(StringMatcherFactory.INSTANCE.stringMatcher(str));
    }

    public StringTokenizer setEmptyTokenAsNull(boolean z) {
        this.emptyAsNull = z;
        return this;
    }

    public StringTokenizer setIgnoredChar(char c) {
        return setIgnoredMatcher(StringMatcherFactory.INSTANCE.charMatcher(c));
    }

    public StringTokenizer setIgnoredMatcher(StringMatcher stringMatcher) {
        if (stringMatcher != null) {
            this.ignoredMatcher = stringMatcher;
        }
        return this;
    }

    public StringTokenizer setIgnoreEmptyTokens(boolean z) {
        this.ignoreEmptyTokens = z;
        return this;
    }

    public StringTokenizer setQuoteChar(char c) {
        return setQuoteMatcher(StringMatcherFactory.INSTANCE.charMatcher(c));
    }

    public StringTokenizer setQuoteMatcher(StringMatcher stringMatcher) {
        if (stringMatcher != null) {
            this.quoteMatcher = stringMatcher;
        }
        return this;
    }

    public StringTokenizer setTrimmerMatcher(StringMatcher stringMatcher) {
        if (stringMatcher != null) {
            this.trimmerMatcher = stringMatcher;
        }
        return this;
    }

    public int size() {
        checkTokenized();
        return this.tokens.length;
    }

    protected List<String> tokenize(char[] cArr, int i, int i2) {
        if (cArr == null || i2 == 0) {
            return Collections.emptyList();
        }
        TextStringBuilder textStringBuilder = new TextStringBuilder();
        ArrayList arrayList = new ArrayList();
        int nextToken = i;
        while (nextToken >= 0 && nextToken < i2) {
            StringTokenizer stringTokenizer = this;
            char[] cArr2 = cArr;
            int i3 = i2;
            nextToken = stringTokenizer.readNextToken(cArr2, nextToken, i3, textStringBuilder, arrayList);
            if (nextToken >= i3) {
                stringTokenizer.addToken(arrayList, "");
            }
            this = stringTokenizer;
            cArr = cArr2;
            i2 = i3;
        }
        return arrayList;
    }

    public String toString() {
        if (this.tokens == null) {
            return "StringTokenizer[not tokenized yet]";
        }
        return "StringTokenizer" + getTokenList();
    }
}
