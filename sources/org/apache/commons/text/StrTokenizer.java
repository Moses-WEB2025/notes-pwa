package org.apache.commons.text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.apache.commons.lang3.ArrayUtils;

/* JADX INFO: loaded from: classes6.dex */
@Deprecated
public class StrTokenizer implements ListIterator<String>, Cloneable {
    private static final StrTokenizer CSV_TOKENIZER_PROTOTYPE = new StrTokenizer().setDelimiterMatcher(StrMatcher.commaMatcher()).setQuoteMatcher(StrMatcher.doubleQuoteMatcher()).setIgnoredMatcher(StrMatcher.noneMatcher()).setTrimmerMatcher(StrMatcher.trimMatcher()).setEmptyTokenAsNull(false).setIgnoreEmptyTokens(false);
    private static final StrTokenizer TSV_TOKENIZER_PROTOTYPE = new StrTokenizer().setDelimiterMatcher(StrMatcher.tabMatcher()).setQuoteMatcher(StrMatcher.doubleQuoteMatcher()).setIgnoredMatcher(StrMatcher.noneMatcher()).setTrimmerMatcher(StrMatcher.trimMatcher()).setEmptyTokenAsNull(false).setIgnoreEmptyTokens(false);
    private char[] chars;
    private StrMatcher delimMatcher;
    private boolean emptyAsNull;
    private boolean ignoreEmptyTokens;
    private StrMatcher ignoredMatcher;
    private StrMatcher quoteMatcher;
    private int tokenPos;
    private String[] tokens;
    private StrMatcher trimmerMatcher;

    private static StrTokenizer getCSVClone() {
        return (StrTokenizer) CSV_TOKENIZER_PROTOTYPE.clone();
    }

    public static StrTokenizer getCSVInstance() {
        return getCSVClone();
    }

    public static StrTokenizer getCSVInstance(char[] cArr) {
        StrTokenizer cSVClone = getCSVClone();
        cSVClone.reset(cArr);
        return cSVClone;
    }

    public static StrTokenizer getCSVInstance(String str) {
        StrTokenizer cSVClone = getCSVClone();
        cSVClone.reset(str);
        return cSVClone;
    }

    private static StrTokenizer getTSVClone() {
        return (StrTokenizer) TSV_TOKENIZER_PROTOTYPE.clone();
    }

    public static StrTokenizer getTSVInstance() {
        return getTSVClone();
    }

    public static StrTokenizer getTSVInstance(char[] cArr) {
        StrTokenizer tSVClone = getTSVClone();
        tSVClone.reset(cArr);
        return tSVClone;
    }

    public static StrTokenizer getTSVInstance(String str) {
        StrTokenizer tSVClone = getTSVClone();
        tSVClone.reset(str);
        return tSVClone;
    }

    public StrTokenizer() {
        this.delimMatcher = StrMatcher.splitMatcher();
        this.quoteMatcher = StrMatcher.noneMatcher();
        this.ignoredMatcher = StrMatcher.noneMatcher();
        this.trimmerMatcher = StrMatcher.noneMatcher();
        this.ignoreEmptyTokens = true;
        this.chars = null;
    }

    public StrTokenizer(char[] cArr) {
        this.delimMatcher = StrMatcher.splitMatcher();
        this.quoteMatcher = StrMatcher.noneMatcher();
        this.ignoredMatcher = StrMatcher.noneMatcher();
        this.trimmerMatcher = StrMatcher.noneMatcher();
        this.ignoreEmptyTokens = true;
        if (cArr == null) {
            this.chars = null;
        } else {
            this.chars = (char[]) cArr.clone();
        }
    }

    public StrTokenizer(char[] cArr, char c) {
        this(cArr);
        setDelimiterChar(c);
    }

    public StrTokenizer(char[] cArr, char c, char c2) {
        this(cArr, c);
        setQuoteChar(c2);
    }

    public StrTokenizer(char[] cArr, String str) {
        this(cArr);
        setDelimiterString(str);
    }

    public StrTokenizer(char[] cArr, StrMatcher strMatcher) {
        this(cArr);
        setDelimiterMatcher(strMatcher);
    }

    public StrTokenizer(char[] cArr, StrMatcher strMatcher, StrMatcher strMatcher2) {
        this(cArr, strMatcher);
        setQuoteMatcher(strMatcher2);
    }

    public StrTokenizer(String str) {
        this.delimMatcher = StrMatcher.splitMatcher();
        this.quoteMatcher = StrMatcher.noneMatcher();
        this.ignoredMatcher = StrMatcher.noneMatcher();
        this.trimmerMatcher = StrMatcher.noneMatcher();
        this.ignoreEmptyTokens = true;
        if (str != null) {
            this.chars = str.toCharArray();
        } else {
            this.chars = null;
        }
    }

    public StrTokenizer(String str, char c) {
        this(str);
        setDelimiterChar(c);
    }

    public StrTokenizer(String str, char c, char c2) {
        this(str, c);
        setQuoteChar(c2);
    }

    public StrTokenizer(String str, String str2) {
        this(str);
        setDelimiterString(str2);
    }

    public StrTokenizer(String str, StrMatcher strMatcher) {
        this(str);
        setDelimiterMatcher(strMatcher);
    }

    public StrTokenizer(String str, StrMatcher strMatcher, StrMatcher strMatcher2) {
        this(str, strMatcher);
        setQuoteMatcher(strMatcher2);
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
        if (this.tokens == null) {
            char[] cArr = this.chars;
            if (cArr == null) {
                this.tokens = (String[]) tokenize(null, 0, 0).toArray(ArrayUtils.EMPTY_STRING_ARRAY);
            } else {
                this.tokens = (String[]) tokenize(cArr, 0, cArr.length).toArray(ArrayUtils.EMPTY_STRING_ARRAY);
            }
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
        StrTokenizer strTokenizer = (StrTokenizer) super.clone();
        char[] cArr = strTokenizer.chars;
        if (cArr != null) {
            strTokenizer.chars = (char[]) cArr.clone();
        }
        strTokenizer.reset();
        return strTokenizer;
    }

    public String getContent() {
        if (this.chars == null) {
            return null;
        }
        return new String(this.chars);
    }

    public StrMatcher getDelimiterMatcher() {
        return this.delimMatcher;
    }

    public StrMatcher getIgnoredMatcher() {
        return this.ignoredMatcher;
    }

    public StrMatcher getQuoteMatcher() {
        return this.quoteMatcher;
    }

    public String[] getTokenArray() {
        checkTokenized();
        return (String[]) this.tokens.clone();
    }

    public List<String> getTokenList() {
        checkTokenized();
        ArrayList arrayList = new ArrayList(this.tokens.length);
        Collections.addAll(arrayList, this.tokens);
        return arrayList;
    }

    public StrMatcher getTrimmerMatcher() {
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

    private int readNextToken(char[] cArr, int i, int i2, StrBuilder strBuilder, List<String> list) {
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
            return readWithQuotes(cArr, i4 + iIsMatch2, i2, strBuilder, list, i4, iIsMatch2);
        }
        return readWithQuotes(cArr, i3, i2, strBuilder, list, 0, 0);
    }

    private int readWithQuotes(char[] cArr, int i, int i2, StrBuilder strBuilder, List<String> list, int i3, int i4) {
        char[] cArr2;
        int i5;
        int i6 = i2;
        strBuilder.clear();
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
                        strBuilder.append(cArr, i8, i4);
                        i7 = (i4 * 2) + i8;
                        size = strBuilder.size();
                    } else {
                        z = false;
                    }
                } else {
                    cArr2 = cArr;
                    i6 = i2;
                    i5 = i8;
                    strBuilder.append(cArr2[i5]);
                    size = strBuilder.size();
                    i7 = i5 + 1;
                }
            } else {
                cArr2 = cArr;
                int i9 = i7;
                int iIsMatch = getDelimiterMatcher().isMatch(cArr2, i9, i, i6);
                if (iIsMatch > 0) {
                    addToken(list, strBuilder.substring(0, size));
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
                        strBuilder.append(cArr2, i5, iIsMatch2);
                    } else {
                        strBuilder.append(cArr2[i5]);
                        size = strBuilder.size();
                        i7 = i5 + 1;
                    }
                }
                i7 = i5 + iIsMatch2;
            }
        }
        addToken(list, strBuilder.substring(0, size));
        return -1;
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("remove() is unsupported");
    }

    public StrTokenizer reset() {
        this.tokenPos = 0;
        this.tokens = null;
        return this;
    }

    public StrTokenizer reset(char[] cArr) {
        reset();
        if (cArr != null) {
            this.chars = (char[]) cArr.clone();
            return this;
        }
        this.chars = null;
        return this;
    }

    public StrTokenizer reset(String str) {
        reset();
        if (str != null) {
            this.chars = str.toCharArray();
            return this;
        }
        this.chars = null;
        return this;
    }

    @Override // java.util.ListIterator
    public void set(String str) {
        throw new UnsupportedOperationException("set() is unsupported");
    }

    public StrTokenizer setDelimiterChar(char c) {
        return setDelimiterMatcher(StrMatcher.charMatcher(c));
    }

    public StrTokenizer setDelimiterMatcher(StrMatcher strMatcher) {
        if (strMatcher == null) {
            this.delimMatcher = StrMatcher.noneMatcher();
            return this;
        }
        this.delimMatcher = strMatcher;
        return this;
    }

    public StrTokenizer setDelimiterString(String str) {
        return setDelimiterMatcher(StrMatcher.stringMatcher(str));
    }

    public StrTokenizer setEmptyTokenAsNull(boolean z) {
        this.emptyAsNull = z;
        return this;
    }

    public StrTokenizer setIgnoredChar(char c) {
        return setIgnoredMatcher(StrMatcher.charMatcher(c));
    }

    public StrTokenizer setIgnoredMatcher(StrMatcher strMatcher) {
        if (strMatcher != null) {
            this.ignoredMatcher = strMatcher;
        }
        return this;
    }

    public StrTokenizer setIgnoreEmptyTokens(boolean z) {
        this.ignoreEmptyTokens = z;
        return this;
    }

    public StrTokenizer setQuoteChar(char c) {
        return setQuoteMatcher(StrMatcher.charMatcher(c));
    }

    public StrTokenizer setQuoteMatcher(StrMatcher strMatcher) {
        if (strMatcher != null) {
            this.quoteMatcher = strMatcher;
        }
        return this;
    }

    public StrTokenizer setTrimmerMatcher(StrMatcher strMatcher) {
        if (strMatcher != null) {
            this.trimmerMatcher = strMatcher;
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
        StrBuilder strBuilder = new StrBuilder();
        ArrayList arrayList = new ArrayList();
        int nextToken = i;
        while (nextToken >= 0 && nextToken < i2) {
            StrTokenizer strTokenizer = this;
            char[] cArr2 = cArr;
            int i3 = i2;
            nextToken = strTokenizer.readNextToken(cArr2, nextToken, i3, strBuilder, arrayList);
            if (nextToken >= i3) {
                strTokenizer.addToken(arrayList, "");
            }
            this = strTokenizer;
            cArr = cArr2;
            i2 = i3;
        }
        return arrayList;
    }

    public String toString() {
        if (this.tokens == null) {
            return "StrTokenizer[not tokenized yet]";
        }
        return "StrTokenizer" + getTokenList();
    }
}
