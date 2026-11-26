package org.jsoup.parser;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.function.Supplier;
import org.jsoup.helper.Validate;
import org.jsoup.internal.SoftPool;
import org.jsoup.internal.StringUtil;

/* JADX INFO: loaded from: classes6.dex */
public final class CharacterReader implements AutoCloseable {
    static final int BufferSize = 2048;
    static final char EOF = 65535;
    private static final int MaxStringCacheLen = 12;
    static final int RefillPoint = 1024;
    private static final int RewindLimit = 1024;
    private static final int StringCacheSize = 512;
    private int bufLength;
    private int bufMark;
    private int bufPos;
    private char[] charBuf;
    private int consumed;
    private int fillPoint;
    private int lastIcIndex;
    private String lastIcSeq;
    private int lineNumberOffset;
    private ArrayList<Integer> newlinePositions;
    private boolean readFully;
    private Reader reader;
    private String[] stringCache;
    private static final SoftPool<String[]> StringPool = new SoftPool<>(new Supplier() { // from class: org.jsoup.parser.CharacterReader$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final Object get() {
            return CharacterReader.lambda$static$0();
        }
    });
    private static final SoftPool<char[]> BufferPool = new SoftPool<>(new Supplier() { // from class: org.jsoup.parser.CharacterReader$$ExternalSyntheticLambda2
        @Override // java.util.function.Supplier
        public final Object get() {
            return CharacterReader.lambda$static$1();
        }
    });

    @FunctionalInterface
    interface CharPredicate {
        boolean test(char c);
    }

    static /* synthetic */ boolean lambda$consumeAttributeQuoted$5(boolean z, char c) {
        if (c == 0 || c == '&') {
            return false;
        }
        if (z) {
            return c != '\'';
        }
        return c != '\"';
    }

    static /* synthetic */ boolean lambda$consumeData$4(char c) {
        return (c == '&' || c == '<' || c == 0) ? false : true;
    }

    static /* synthetic */ boolean lambda$consumeDigitSequence$8(char c) {
        return c >= '0' && c <= '9';
    }

    static /* synthetic */ boolean lambda$consumeRawData$6(char c) {
        return (c == '<' || c == 0) ? false : true;
    }

    static /* synthetic */ boolean lambda$consumeTagName$7(char c) {
        return (c == '\t' || c == '\n' || c == '\f' || c == '\r' || c == ' ' || c == '/' || c == '>') ? false : true;
    }

    static /* synthetic */ String[] lambda$static$0() {
        return new String[512];
    }

    static /* synthetic */ char[] lambda$static$1() {
        return new char[2048];
    }

    public CharacterReader(Reader reader, int i) {
        this(reader);
    }

    public CharacterReader(Reader reader) {
        this.fillPoint = 0;
        this.bufMark = -1;
        this.newlinePositions = null;
        this.lineNumberOffset = 1;
        Validate.notNull(reader);
        this.reader = reader;
        this.charBuf = BufferPool.borrow();
        this.stringCache = StringPool.borrow();
        bufferUp();
    }

    public CharacterReader(String str) {
        this(new StringReader(str));
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        Reader reader = this.reader;
        if (reader == null) {
            return;
        }
        try {
            reader.close();
            this.reader = null;
            Arrays.fill(this.charBuf, (char) 0);
            BufferPool.release(this.charBuf);
            this.charBuf = null;
            StringPool.release(this.stringCache);
        } catch (IOException unused) {
            this.reader = null;
            Arrays.fill(this.charBuf, (char) 0);
            BufferPool.release(this.charBuf);
            this.charBuf = null;
            StringPool.release(this.stringCache);
        } finally {
            this.reader = null;
            Arrays.fill(this.charBuf, (char) 0);
            BufferPool.release(this.charBuf);
            this.charBuf = null;
            StringPool.release(this.stringCache);
            this.stringCache = null;
        }
    }

    private void bufferUp() {
        if (this.readFully || this.bufPos < this.fillPoint || this.bufMark != -1) {
            return;
        }
        doBufferUp();
    }

    private void doBufferUp() {
        int i = this.consumed;
        int i2 = this.bufPos;
        this.consumed = i + i2;
        int i3 = this.bufLength - i2;
        this.bufLength = i3;
        if (i3 > 0) {
            char[] cArr = this.charBuf;
            System.arraycopy(cArr, i2, cArr, 0, i3);
        }
        this.bufPos = 0;
        while (true) {
            int i4 = this.bufLength;
            if (i4 >= 2048) {
                break;
            }
            try {
                Reader reader = this.reader;
                char[] cArr2 = this.charBuf;
                int i5 = reader.read(cArr2, i4, cArr2.length - i4);
                if (i5 == -1) {
                    this.readFully = true;
                    break;
                }
                this.bufLength += i5;
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
        this.fillPoint = Math.min(this.bufLength, 1024);
        scanBufferForNewlines();
        this.lastIcSeq = null;
    }

    void mark() {
        if (this.bufLength - this.bufPos < 1024) {
            this.fillPoint = 0;
        }
        bufferUp();
        this.bufMark = this.bufPos;
    }

    void unmark() {
        this.bufMark = -1;
    }

    void rewindToMark() {
        int i = this.bufMark;
        if (i == -1) {
            throw new UncheckedIOException(new IOException("Mark invalid"));
        }
        this.bufPos = i;
        unmark();
    }

    public int pos() {
        return this.consumed + this.bufPos;
    }

    boolean readFully() {
        return this.readFully;
    }

    public void trackNewlines(boolean z) {
        if (z && this.newlinePositions == null) {
            this.newlinePositions = new ArrayList<>(25);
            scanBufferForNewlines();
        } else {
            if (z) {
                return;
            }
            this.newlinePositions = null;
        }
    }

    public boolean isTrackNewlines() {
        return this.newlinePositions != null;
    }

    public int lineNumber() {
        return lineNumber(pos());
    }

    int lineNumber(int i) {
        if (!isTrackNewlines()) {
            return 1;
        }
        int iLineNumIndex = lineNumIndex(i);
        if (iLineNumIndex == -1) {
            return this.lineNumberOffset;
        }
        return iLineNumIndex + this.lineNumberOffset + 1;
    }

    public int columnNumber() {
        return columnNumber(pos());
    }

    int columnNumber(int i) {
        int iLineNumIndex;
        if (isTrackNewlines() && (iLineNumIndex = lineNumIndex(i)) != -1) {
            return (i - this.newlinePositions.get(iLineNumIndex).intValue()) + 1;
        }
        return i + 1;
    }

    String posLineCol() {
        return lineNumber() + ":" + columnNumber();
    }

    private int lineNumIndex(int i) {
        if (!isTrackNewlines()) {
            return 0;
        }
        int iBinarySearch = Collections.binarySearch(this.newlinePositions, Integer.valueOf(i));
        return iBinarySearch < -1 ? Math.abs(iBinarySearch) - 2 : iBinarySearch;
    }

    private void scanBufferForNewlines() {
        if (isTrackNewlines()) {
            if (this.newlinePositions.size() > 0) {
                int iLineNumIndex = lineNumIndex(this.consumed);
                if (iLineNumIndex == -1) {
                    iLineNumIndex = 0;
                }
                int iIntValue = this.newlinePositions.get(iLineNumIndex).intValue();
                this.lineNumberOffset += iLineNumIndex;
                this.newlinePositions.clear();
                this.newlinePositions.add(Integer.valueOf(iIntValue));
            }
            for (int i = this.bufPos; i < this.bufLength; i++) {
                if (this.charBuf[i] == '\n') {
                    this.newlinePositions.add(Integer.valueOf(this.consumed + 1 + i));
                }
            }
        }
    }

    public boolean isEmpty() {
        bufferUp();
        return this.bufPos >= this.bufLength;
    }

    private boolean isEmptyNoBufferUp() {
        return this.bufPos >= this.bufLength;
    }

    public char current() {
        bufferUp();
        if (isEmptyNoBufferUp()) {
            return (char) 65535;
        }
        return this.charBuf[this.bufPos];
    }

    public char consume() {
        bufferUp();
        char c = isEmptyNoBufferUp() ? (char) 65535 : this.charBuf[this.bufPos];
        this.bufPos++;
        return c;
    }

    void unconsume() {
        int i = this.bufPos;
        if (i < 1) {
            throw new UncheckedIOException(new IOException("WTF: No buffer left to unconsume."));
        }
        this.bufPos = i - 1;
    }

    public void advance() {
        this.bufPos++;
    }

    int nextIndexOf(char c) {
        bufferUp();
        for (int i = this.bufPos; i < this.bufLength; i++) {
            if (c == this.charBuf[i]) {
                return i - this.bufPos;
            }
        }
        return -1;
    }

    int nextIndexOf(CharSequence charSequence) {
        bufferUp();
        char cCharAt = charSequence.charAt(0);
        int i = this.bufPos;
        while (i < this.bufLength) {
            if (cCharAt != this.charBuf[i]) {
                do {
                    i++;
                    if (i >= this.bufLength) {
                        break;
                    }
                } while (cCharAt != this.charBuf[i]);
            }
            int i2 = i + 1;
            int length = (charSequence.length() + i2) - 1;
            int i3 = this.bufLength;
            if (i < i3 && length <= i3) {
                int i4 = i2;
                for (int i5 = 1; i4 < length && charSequence.charAt(i5) == this.charBuf[i4]; i5++) {
                    i4++;
                }
                if (i4 == length) {
                    return i - this.bufPos;
                }
            }
            i = i2;
        }
        return -1;
    }

    public String consumeTo(char c) {
        int iNextIndexOf = nextIndexOf(c);
        if (iNextIndexOf != -1) {
            String strCacheString = cacheString(this.charBuf, this.stringCache, this.bufPos, iNextIndexOf);
            this.bufPos += iNextIndexOf;
            return strCacheString;
        }
        return consumeToEnd();
    }

    public String consumeTo(String str) {
        int iNextIndexOf = nextIndexOf(str);
        if (iNextIndexOf != -1) {
            String strCacheString = cacheString(this.charBuf, this.stringCache, this.bufPos, iNextIndexOf);
            this.bufPos += iNextIndexOf;
            return strCacheString;
        }
        if (this.bufLength - this.bufPos < str.length()) {
            return consumeToEnd();
        }
        int length = (this.bufLength - str.length()) + 1;
        char[] cArr = this.charBuf;
        String[] strArr = this.stringCache;
        int i = this.bufPos;
        String strCacheString2 = cacheString(cArr, strArr, i, length - i);
        this.bufPos = length;
        return strCacheString2;
    }

    String consumeMatching(CharPredicate charPredicate) {
        return consumeMatching(charPredicate, -1);
    }

    String consumeMatching(CharPredicate charPredicate, int i) {
        bufferUp();
        int i2 = this.bufPos;
        int i3 = this.bufLength;
        char[] cArr = this.charBuf;
        int i4 = i2;
        while (i4 < i3 && ((i == -1 || i4 - i2 < i) && charPredicate.test(cArr[i4]))) {
            i4++;
        }
        this.bufPos = i4;
        return i4 > i2 ? cacheString(this.charBuf, this.stringCache, i2, i4 - i2) : "";
    }

    public String consumeToAny(final char... cArr) {
        return consumeMatching(new CharPredicate() { // from class: org.jsoup.parser.CharacterReader$$ExternalSyntheticLambda3
            @Override // org.jsoup.parser.CharacterReader.CharPredicate
            public final boolean test(char c) {
                return CharacterReader.lambda$consumeToAny$2(cArr, c);
            }
        });
    }

    static /* synthetic */ boolean lambda$consumeToAny$2(char[] cArr, char c) {
        for (char c2 : cArr) {
            if (c == c2) {
                return false;
            }
        }
        return true;
    }

    static /* synthetic */ boolean lambda$consumeToAnySorted$3(char[] cArr, char c) {
        return Arrays.binarySearch(cArr, c) < 0;
    }

    String consumeToAnySorted(final char... cArr) {
        return consumeMatching(new CharPredicate() { // from class: org.jsoup.parser.CharacterReader$$ExternalSyntheticLambda5
            @Override // org.jsoup.parser.CharacterReader.CharPredicate
            public final boolean test(char c) {
                return CharacterReader.lambda$consumeToAnySorted$3(cArr, c);
            }
        });
    }

    String consumeData() {
        return consumeMatching(new CharPredicate() { // from class: org.jsoup.parser.CharacterReader$$ExternalSyntheticLambda7
            @Override // org.jsoup.parser.CharacterReader.CharPredicate
            public final boolean test(char c) {
                return CharacterReader.lambda$consumeData$4(c);
            }
        });
    }

    String consumeAttributeQuoted(final boolean z) {
        return consumeMatching(new CharPredicate() { // from class: org.jsoup.parser.CharacterReader$$ExternalSyntheticLambda8
            @Override // org.jsoup.parser.CharacterReader.CharPredicate
            public final boolean test(char c) {
                return CharacterReader.lambda$consumeAttributeQuoted$5(z, c);
            }
        });
    }

    String consumeRawData() {
        return consumeMatching(new CharPredicate() { // from class: org.jsoup.parser.CharacterReader$$ExternalSyntheticLambda4
            @Override // org.jsoup.parser.CharacterReader.CharPredicate
            public final boolean test(char c) {
                return CharacterReader.lambda$consumeRawData$6(c);
            }
        });
    }

    String consumeTagName() {
        return consumeMatching(new CharPredicate() { // from class: org.jsoup.parser.CharacterReader$$ExternalSyntheticLambda6
            @Override // org.jsoup.parser.CharacterReader.CharPredicate
            public final boolean test(char c) {
                return CharacterReader.lambda$consumeTagName$7(c);
            }
        });
    }

    String consumeToEnd() {
        bufferUp();
        char[] cArr = this.charBuf;
        String[] strArr = this.stringCache;
        int i = this.bufPos;
        String strCacheString = cacheString(cArr, strArr, i, this.bufLength - i);
        this.bufPos = this.bufLength;
        return strCacheString;
    }

    String consumeLetterSequence() {
        return consumeMatching(new CharPredicate() { // from class: org.jsoup.parser.CharacterReader$$ExternalSyntheticLambda1
            @Override // org.jsoup.parser.CharacterReader.CharPredicate
            public final boolean test(char c) {
                return Character.isLetter(c);
            }
        });
    }

    String consumeLetterThenDigitSequence() {
        bufferUp();
        int i = this.bufPos;
        while (true) {
            int i2 = this.bufPos;
            if (i2 >= this.bufLength || !StringUtil.isAsciiLetter(this.charBuf[i2])) {
                break;
            }
            this.bufPos++;
        }
        while (!isEmptyNoBufferUp() && StringUtil.isDigit(this.charBuf[this.bufPos])) {
            this.bufPos++;
        }
        return cacheString(this.charBuf, this.stringCache, i, this.bufPos - i);
    }

    String consumeHexSequence() {
        return consumeMatching(new CharacterReader$$ExternalSyntheticLambda10());
    }

    String consumeDigitSequence() {
        return consumeMatching(new CharPredicate() { // from class: org.jsoup.parser.CharacterReader$$ExternalSyntheticLambda9
            @Override // org.jsoup.parser.CharacterReader.CharPredicate
            public final boolean test(char c) {
                return CharacterReader.lambda$consumeDigitSequence$8(c);
            }
        });
    }

    boolean matches(char c) {
        return !isEmpty() && this.charBuf[this.bufPos] == c;
    }

    boolean matches(String str) {
        bufferUp();
        int length = str.length();
        if (length > this.bufLength - this.bufPos) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (str.charAt(i) != this.charBuf[this.bufPos + i]) {
                return false;
            }
        }
        return true;
    }

    boolean matchesIgnoreCase(String str) {
        bufferUp();
        int length = str.length();
        if (length > this.bufLength - this.bufPos) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            char c = this.charBuf[this.bufPos + i];
            if (cCharAt != c && Character.toUpperCase(cCharAt) != Character.toUpperCase(c)) {
                return false;
            }
        }
        return true;
    }

    boolean matchesAny(char... cArr) {
        if (isEmpty()) {
            return false;
        }
        bufferUp();
        char c = this.charBuf[this.bufPos];
        for (char c2 : cArr) {
            if (c2 == c) {
                return true;
            }
        }
        return false;
    }

    boolean matchesAnySorted(char[] cArr) {
        bufferUp();
        return !isEmpty() && Arrays.binarySearch(cArr, this.charBuf[this.bufPos]) >= 0;
    }

    boolean matchesAsciiAlpha() {
        if (isEmpty()) {
            return false;
        }
        return StringUtil.isAsciiLetter(this.charBuf[this.bufPos]);
    }

    boolean matchesDigit() {
        if (isEmpty()) {
            return false;
        }
        return StringUtil.isDigit(this.charBuf[this.bufPos]);
    }

    boolean matchConsume(String str) {
        bufferUp();
        if (!matches(str)) {
            return false;
        }
        this.bufPos += str.length();
        return true;
    }

    boolean matchConsumeIgnoreCase(String str) {
        if (!matchesIgnoreCase(str)) {
            return false;
        }
        this.bufPos += str.length();
        return true;
    }

    boolean containsIgnoreCase(String str) {
        if (str.equals(this.lastIcSeq)) {
            int i = this.lastIcIndex;
            if (i == -1) {
                return false;
            }
            if (i >= this.bufPos) {
                return true;
            }
        }
        this.lastIcSeq = str;
        int iNextIndexOf = nextIndexOf(str.toLowerCase(Locale.ENGLISH));
        if (iNextIndexOf > -1) {
            this.lastIcIndex = this.bufPos + iNextIndexOf;
            return true;
        }
        int iNextIndexOf2 = nextIndexOf(str.toUpperCase(Locale.ENGLISH));
        boolean z = iNextIndexOf2 > -1;
        this.lastIcIndex = z ? this.bufPos + iNextIndexOf2 : -1;
        return z;
    }

    public String toString() {
        if (this.bufLength - this.bufPos < 0) {
            return "";
        }
        char[] cArr = this.charBuf;
        int i = this.bufPos;
        return new String(cArr, i, this.bufLength - i);
    }

    private static String cacheString(char[] cArr, String[] strArr, int i, int i2) {
        if (i2 > 12) {
            return new String(cArr, i, i2);
        }
        if (i2 < 1) {
            return "";
        }
        int i3 = i2 + i;
        int i4 = 0;
        for (int i5 = i; i5 < i3; i5++) {
            i4 = (i4 * 31) + cArr[i5];
        }
        int i6 = i4 & 511;
        String str = strArr[i6];
        if (str != null && rangeEquals(cArr, i, i2, str)) {
            return str;
        }
        String str2 = new String(cArr, i, i2);
        strArr[i6] = str2;
        return str2;
    }

    static boolean rangeEquals(char[] cArr, int i, int i2, String str) {
        if (i2 != str.length()) {
            return false;
        }
        int i3 = 0;
        while (true) {
            int i4 = i2 - 1;
            if (i2 == 0) {
                return true;
            }
            int i5 = i + 1;
            int i6 = i3 + 1;
            if (cArr[i] != str.charAt(i3)) {
                return false;
            }
            i = i5;
            i2 = i4;
            i3 = i6;
        }
    }

    boolean rangeEquals(int i, int i2, String str) {
        return rangeEquals(this.charBuf, i, i2, str);
    }
}
