package org.apache.commons.text;

import java.text.Format;
import java.text.MessageFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import org.apache.commons.text.matcher.StringMatcherFactory;

/* JADX INFO: loaded from: classes6.dex */
public class ExtendedMessageFormat extends MessageFormat {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String EMPTY_PATTERN = "";
    private static final char END_FE = '}';
    private static final char QUOTE = '\'';
    private static final char START_FE = '{';
    private static final char START_FMT = ',';
    private static final long serialVersionUID = -2362048321261811743L;
    private final Map<String, ? extends FormatFactory> registry;
    private String toPattern;

    public ExtendedMessageFormat(String str) {
        this(str, Locale.getDefault(Locale.Category.FORMAT));
    }

    public ExtendedMessageFormat(String str, Locale locale) {
        this(str, locale, null);
    }

    public ExtendedMessageFormat(String str, Locale locale, Map<String, ? extends FormatFactory> map) {
        super("");
        setLocale(locale);
        this.registry = map != null ? Collections.unmodifiableMap(new HashMap(map)) : null;
        applyPattern(str);
    }

    public ExtendedMessageFormat(String str, Map<String, ? extends FormatFactory> map) {
        this(str, Locale.getDefault(Locale.Category.FORMAT), map);
    }

    private void appendQuotedString(String str, ParsePosition parsePosition, StringBuilder sb) {
        if (sb != null) {
            sb.append('\'');
        }
        next(parsePosition);
        int index = parsePosition.getIndex();
        char[] charArray = str.toCharArray();
        for (int index2 = parsePosition.getIndex(); index2 < str.length(); index2++) {
            if (charArray[parsePosition.getIndex()] == '\'') {
                next(parsePosition);
                if (sb != null) {
                    sb.append(charArray, index, parsePosition.getIndex() - index);
                    return;
                }
                return;
            }
            next(parsePosition);
        }
        throw new IllegalArgumentException("Unterminated quoted string at position " + index);
    }

    @Override // java.text.MessageFormat
    public final void applyPattern(String str) {
        String formatDescription;
        Format format;
        if (this.registry == null) {
            super.applyPattern(str);
            this.toPattern = super.toPattern();
            return;
        }
        ArrayList<Format> arrayList = new ArrayList();
        ArrayList<String> arrayList2 = new ArrayList<>();
        StringBuilder sb = new StringBuilder(str.length());
        int i = 0;
        ParsePosition parsePosition = new ParsePosition(0);
        char[] charArray = str.toCharArray();
        int i2 = 0;
        while (parsePosition.getIndex() < str.length()) {
            char c = charArray[parsePosition.getIndex()];
            if (c == '\'') {
                appendQuotedString(str, parsePosition, sb);
            } else {
                if (c == '{') {
                    i2++;
                    seekNonWs(str, parsePosition);
                    int index = parsePosition.getIndex();
                    sb.append('{').append(readArgumentIndex(str, next(parsePosition)));
                    seekNonWs(str, parsePosition);
                    if (charArray[parsePosition.getIndex()] == ',') {
                        formatDescription = parseFormatDescription(str, next(parsePosition));
                        format = getFormat(formatDescription);
                        if (format == null) {
                            sb.append(',').append(formatDescription);
                        }
                    } else {
                        formatDescription = null;
                        format = null;
                    }
                    arrayList.add(format);
                    arrayList2.add(format != null ? formatDescription : null);
                    if (arrayList.size() != i2) {
                        throw new IllegalArgumentException("The validated expression is false");
                    }
                    if (arrayList2.size() != i2) {
                        throw new IllegalArgumentException("The validated expression is false");
                    }
                    if (charArray[parsePosition.getIndex()] != '}') {
                        throw new IllegalArgumentException("Unreadable format element at position " + index);
                    }
                }
                sb.append(charArray[parsePosition.getIndex()]);
                next(parsePosition);
            }
        }
        super.applyPattern(sb.toString());
        this.toPattern = insertFormats(super.toPattern(), arrayList2);
        if (containsElements(arrayList)) {
            Format[] formats = getFormats();
            for (Format format2 : arrayList) {
                if (format2 != null) {
                    formats[i] = format2;
                }
                i++;
            }
            super.setFormats(formats);
        }
    }

    private boolean containsElements(Collection<?> collection) {
        if (collection == null || collection.isEmpty()) {
            return false;
        }
        return collection.stream().anyMatch(new Predicate() { // from class: org.apache.commons.text.ExtendedMessageFormat$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Objects.nonNull(obj);
            }
        });
    }

    @Override // java.text.MessageFormat
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj) || !(obj instanceof ExtendedMessageFormat)) {
            return false;
        }
        ExtendedMessageFormat extendedMessageFormat = (ExtendedMessageFormat) obj;
        return Objects.equals(this.registry, extendedMessageFormat.registry) && Objects.equals(this.toPattern, extendedMessageFormat.toPattern);
    }

    private Format getFormat(String str) {
        String strTrim;
        if (this.registry != null) {
            int iIndexOf = str.indexOf(44);
            if (iIndexOf > 0) {
                String strTrim2 = str.substring(0, iIndexOf).trim();
                strTrim = str.substring(iIndexOf + 1).trim();
                str = strTrim2;
            } else {
                strTrim = null;
            }
            FormatFactory formatFactory = this.registry.get(str);
            if (formatFactory != null) {
                return formatFactory.getFormat(str, strTrim, getLocale());
            }
        }
        return null;
    }

    private void getQuotedString(String str, ParsePosition parsePosition) {
        appendQuotedString(str, parsePosition, null);
    }

    @Override // java.text.MessageFormat
    public int hashCode() {
        return (super.hashCode() * 31) + Objects.hash(this.registry, this.toPattern);
    }

    private String insertFormats(String str, ArrayList<String> arrayList) {
        String str2;
        if (!containsElements(arrayList)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str.length() * 2);
        int i = 0;
        ParsePosition parsePosition = new ParsePosition(0);
        int i2 = -1;
        while (parsePosition.getIndex() < str.length()) {
            char cCharAt = str.charAt(parsePosition.getIndex());
            if (cCharAt == '\'') {
                appendQuotedString(str, parsePosition, sb);
            } else if (cCharAt == '{') {
                i++;
                sb.append('{').append(readArgumentIndex(str, next(parsePosition)));
                if (i == 1 && (str2 = arrayList.get((i2 = i2 + 1))) != null) {
                    sb.append(',').append(str2);
                }
            } else {
                if (cCharAt == '}') {
                    i--;
                }
                sb.append(cCharAt);
                next(parsePosition);
            }
        }
        return sb.toString();
    }

    private ParsePosition next(ParsePosition parsePosition) {
        parsePosition.setIndex(parsePosition.getIndex() + 1);
        return parsePosition;
    }

    private String parseFormatDescription(String str, ParsePosition parsePosition) {
        int index = parsePosition.getIndex();
        seekNonWs(str, parsePosition);
        int index2 = parsePosition.getIndex();
        int i = 1;
        while (parsePosition.getIndex() < str.length()) {
            char cCharAt = str.charAt(parsePosition.getIndex());
            if (cCharAt == '\'') {
                getQuotedString(str, parsePosition);
            } else if (cCharAt == '{') {
                i++;
                next(parsePosition);
            } else if (cCharAt != '}') {
                next(parsePosition);
            } else {
                i--;
                if (i == 0) {
                    return str.substring(index2, parsePosition.getIndex());
                }
                next(parsePosition);
            }
        }
        throw new IllegalArgumentException("Unterminated format element at position " + index);
    }

    /* JADX WARN: Code duplicated, block: B:12:0x003d A[PHI: r2
  0x003d: PHI (r2v6 char) = (r2v5 char), (r2v10 char), (r2v10 char) binds: [B:7:0x002a, B:9:0x0037, B:10:0x0039] A[DONT_GENERATE, DONT_INLINE]] */
    private int readArgumentIndex(String str, ParsePosition parsePosition) {
        int index = parsePosition.getIndex();
        seekNonWs(str, parsePosition);
        StringBuilder sb = new StringBuilder();
        boolean z = false;
        while (!z && parsePosition.getIndex() < str.length()) {
            char cCharAt = str.charAt(parsePosition.getIndex());
            if (Character.isWhitespace(cCharAt)) {
                this.seekNonWs(str, parsePosition);
                cCharAt = str.charAt(parsePosition.getIndex());
                if (cCharAt == ',' || cCharAt == '}') {
                    if ((cCharAt != ',' || cCharAt == '}') && sb.length() > 0) {
                    }
                    boolean z2 = !Character.isDigit(cCharAt);
                    sb.append(cCharAt);
                    z = z2;
                } else {
                    z = true;
                }
            } else {
                return cCharAt != ',' ? Integer.parseInt(sb.toString()) : Integer.parseInt(sb.toString());
                boolean z3 = !Character.isDigit(cCharAt);
                sb.append(cCharAt);
                z = z3;
            }
            this.next(parsePosition);
        }
        if (z) {
            throw new IllegalArgumentException("Invalid format argument index at position " + index + ": " + str.substring(index, parsePosition.getIndex()));
        }
        throw new IllegalArgumentException("Unterminated format element at position " + index);
    }

    private void seekNonWs(String str, ParsePosition parsePosition) {
        char[] charArray = str.toCharArray();
        do {
            int iIsMatch = StringMatcherFactory.INSTANCE.splitMatcher().isMatch(charArray, parsePosition.getIndex(), 0, charArray.length);
            parsePosition.setIndex(parsePosition.getIndex() + iIsMatch);
            if (iIsMatch <= 0) {
                return;
            }
        } while (parsePosition.getIndex() < str.length());
    }

    @Override // java.text.MessageFormat
    public void setFormat(int i, Format format) {
        throw new UnsupportedOperationException();
    }

    @Override // java.text.MessageFormat
    public void setFormatByArgumentIndex(int i, Format format) {
        throw new UnsupportedOperationException();
    }

    @Override // java.text.MessageFormat
    public void setFormats(Format[] formatArr) {
        throw new UnsupportedOperationException();
    }

    @Override // java.text.MessageFormat
    public void setFormatsByArgumentIndex(Format[] formatArr) {
        throw new UnsupportedOperationException();
    }

    @Override // java.text.MessageFormat
    public String toPattern() {
        return this.toPattern;
    }
}
