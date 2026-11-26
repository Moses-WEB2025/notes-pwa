package org.apache.commons.text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.Validate;

/* JADX INFO: loaded from: classes6.dex */
@Deprecated
public class StrSubstitutor {
    public static final char DEFAULT_ESCAPE = '$';
    public static final StrMatcher DEFAULT_PREFIX = StrMatcher.stringMatcher(StringSubstitutor.DEFAULT_VAR_START);
    public static final StrMatcher DEFAULT_SUFFIX = StrMatcher.stringMatcher(StringSubstitutor.DEFAULT_VAR_END);
    public static final StrMatcher DEFAULT_VALUE_DELIMITER = StrMatcher.stringMatcher(":-");
    private boolean disableSubstitutionInValues;
    private boolean enableSubstitutionInVariables;
    private char escapeChar;
    private StrMatcher prefixMatcher;
    private boolean preserveEscapes;
    private StrMatcher suffixMatcher;
    private StrMatcher valueDelimiterMatcher;
    private StrLookup<?> variableResolver;

    public static <V> String replace(Object obj, Map<String, V> map) {
        return new StrSubstitutor(map).replace(obj);
    }

    public static <V> String replace(Object obj, Map<String, V> map, String str, String str2) {
        return new StrSubstitutor(map, str, str2).replace(obj);
    }

    public static String replace(Object obj, final Properties properties) {
        if (properties == null) {
            return obj.toString();
        }
        Stream<String> stream = properties.stringPropertyNames().stream();
        Function functionIdentity = Function.identity();
        Objects.requireNonNull(properties);
        return replace(obj, (Map) stream.collect(Collectors.toMap(functionIdentity, new Function() { // from class: org.apache.commons.text.StrSubstitutor$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj2) {
                return properties.getProperty((String) obj2);
            }
        })));
    }

    public static String replaceSystemProperties(Object obj) {
        return new StrSubstitutor(StrLookup.systemPropertiesLookup()).replace(obj);
    }

    public StrSubstitutor() {
        this((StrLookup<?>) null, DEFAULT_PREFIX, DEFAULT_SUFFIX, '$');
    }

    public <V> StrSubstitutor(Map<String, V> map) {
        this((StrLookup<?>) StrLookup.mapLookup(map), DEFAULT_PREFIX, DEFAULT_SUFFIX, '$');
    }

    public <V> StrSubstitutor(Map<String, V> map, String str, String str2) {
        this((StrLookup<?>) StrLookup.mapLookup(map), str, str2, '$');
    }

    public <V> StrSubstitutor(Map<String, V> map, String str, String str2, char c) {
        this((StrLookup<?>) StrLookup.mapLookup(map), str, str2, c);
    }

    public <V> StrSubstitutor(Map<String, V> map, String str, String str2, char c, String str3) {
        this((StrLookup<?>) StrLookup.mapLookup(map), str, str2, c, str3);
    }

    public StrSubstitutor(StrLookup<?> strLookup) {
        this(strLookup, DEFAULT_PREFIX, DEFAULT_SUFFIX, '$');
    }

    public StrSubstitutor(StrLookup<?> strLookup, String str, String str2, char c) {
        setVariableResolver(strLookup);
        setVariablePrefix(str);
        setVariableSuffix(str2);
        setEscapeChar(c);
        setValueDelimiterMatcher(DEFAULT_VALUE_DELIMITER);
    }

    public StrSubstitutor(StrLookup<?> strLookup, String str, String str2, char c, String str3) {
        setVariableResolver(strLookup);
        setVariablePrefix(str);
        setVariableSuffix(str2);
        setEscapeChar(c);
        setValueDelimiter(str3);
    }

    public StrSubstitutor(StrLookup<?> strLookup, StrMatcher strMatcher, StrMatcher strMatcher2, char c) {
        this(strLookup, strMatcher, strMatcher2, c, DEFAULT_VALUE_DELIMITER);
    }

    public StrSubstitutor(StrLookup<?> strLookup, StrMatcher strMatcher, StrMatcher strMatcher2, char c, StrMatcher strMatcher3) {
        setVariableResolver(strLookup);
        setVariablePrefixMatcher(strMatcher);
        setVariableSuffixMatcher(strMatcher2);
        setEscapeChar(c);
        setValueDelimiterMatcher(strMatcher3);
    }

    private void checkCyclicSubstitution(String str, List<String> list) {
        if (list.contains(str)) {
            StrBuilder strBuilder = new StrBuilder(256);
            strBuilder.append("Infinite loop in property interpolation of ");
            strBuilder.append(list.remove(0));
            strBuilder.append(": ");
            strBuilder.appendWithSeparators(list, "->");
            throw new IllegalStateException(strBuilder.toString());
        }
    }

    public char getEscapeChar() {
        return this.escapeChar;
    }

    public StrMatcher getValueDelimiterMatcher() {
        return this.valueDelimiterMatcher;
    }

    public StrMatcher getVariablePrefixMatcher() {
        return this.prefixMatcher;
    }

    public StrLookup<?> getVariableResolver() {
        return this.variableResolver;
    }

    public StrMatcher getVariableSuffixMatcher() {
        return this.suffixMatcher;
    }

    public boolean isDisableSubstitutionInValues() {
        return this.disableSubstitutionInValues;
    }

    public boolean isEnableSubstitutionInVariables() {
        return this.enableSubstitutionInVariables;
    }

    public boolean isPreserveEscapes() {
        return this.preserveEscapes;
    }

    public String replace(char[] cArr) {
        if (cArr == null) {
            return null;
        }
        StrBuilder strBuilderAppend = new StrBuilder(cArr.length).append(cArr);
        substitute(strBuilderAppend, 0, cArr.length);
        return strBuilderAppend.toString();
    }

    public String replace(char[] cArr, int i, int i2) {
        if (cArr == null) {
            return null;
        }
        StrBuilder strBuilderAppend = new StrBuilder(i2).append(cArr, i, i2);
        substitute(strBuilderAppend, 0, i2);
        return strBuilderAppend.toString();
    }

    public String replace(CharSequence charSequence) {
        if (charSequence == null) {
            return null;
        }
        return replace(charSequence, 0, charSequence.length());
    }

    public String replace(CharSequence charSequence, int i, int i2) {
        if (charSequence == null) {
            return null;
        }
        StrBuilder strBuilderAppend = new StrBuilder(i2).append(charSequence, i, i2);
        substitute(strBuilderAppend, 0, i2);
        return strBuilderAppend.toString();
    }

    public String replace(Object obj) {
        if (obj == null) {
            return null;
        }
        StrBuilder strBuilderAppend = new StrBuilder().append(obj);
        substitute(strBuilderAppend, 0, strBuilderAppend.length());
        return strBuilderAppend.toString();
    }

    public String replace(StrBuilder strBuilder) {
        if (strBuilder == null) {
            return null;
        }
        StrBuilder strBuilderAppend = new StrBuilder(strBuilder.length()).append(strBuilder);
        substitute(strBuilderAppend, 0, strBuilderAppend.length());
        return strBuilderAppend.toString();
    }

    public String replace(StrBuilder strBuilder, int i, int i2) {
        if (strBuilder == null) {
            return null;
        }
        StrBuilder strBuilderAppend = new StrBuilder(i2).append(strBuilder, i, i2);
        substitute(strBuilderAppend, 0, i2);
        return strBuilderAppend.toString();
    }

    public String replace(String str) {
        if (str == null) {
            return null;
        }
        StrBuilder strBuilder = new StrBuilder(str);
        return !substitute(strBuilder, 0, str.length()) ? str : strBuilder.toString();
    }

    public String replace(String str, int i, int i2) {
        if (str == null) {
            return null;
        }
        StrBuilder strBuilderAppend = new StrBuilder(i2).append(str, i, i2);
        if (!substitute(strBuilderAppend, 0, i2)) {
            return str.substring(i, i2 + i);
        }
        return strBuilderAppend.toString();
    }

    public String replace(StringBuffer stringBuffer) {
        if (stringBuffer == null) {
            return null;
        }
        StrBuilder strBuilderAppend = new StrBuilder(stringBuffer.length()).append(stringBuffer);
        substitute(strBuilderAppend, 0, strBuilderAppend.length());
        return strBuilderAppend.toString();
    }

    public String replace(StringBuffer stringBuffer, int i, int i2) {
        if (stringBuffer == null) {
            return null;
        }
        StrBuilder strBuilderAppend = new StrBuilder(i2).append(stringBuffer, i, i2);
        substitute(strBuilderAppend, 0, i2);
        return strBuilderAppend.toString();
    }

    public boolean replaceIn(StrBuilder strBuilder) {
        if (strBuilder == null) {
            return false;
        }
        return substitute(strBuilder, 0, strBuilder.length());
    }

    public boolean replaceIn(StrBuilder strBuilder, int i, int i2) {
        if (strBuilder == null) {
            return false;
        }
        return substitute(strBuilder, i, i2);
    }

    public boolean replaceIn(StringBuffer stringBuffer) {
        if (stringBuffer == null) {
            return false;
        }
        return replaceIn(stringBuffer, 0, stringBuffer.length());
    }

    public boolean replaceIn(StringBuffer stringBuffer, int i, int i2) {
        if (stringBuffer == null) {
            return false;
        }
        StrBuilder strBuilderAppend = new StrBuilder(i2).append(stringBuffer, i, i2);
        if (!substitute(strBuilderAppend, 0, i2)) {
            return false;
        }
        stringBuffer.replace(i, i2 + i, strBuilderAppend.toString());
        return true;
    }

    public boolean replaceIn(StringBuilder sb) {
        if (sb == null) {
            return false;
        }
        return replaceIn(sb, 0, sb.length());
    }

    public boolean replaceIn(StringBuilder sb, int i, int i2) {
        if (sb == null) {
            return false;
        }
        StrBuilder strBuilderAppend = new StrBuilder(i2).append(sb, i, i2);
        if (!substitute(strBuilderAppend, 0, i2)) {
            return false;
        }
        sb.replace(i, i2 + i, strBuilderAppend.toString());
        return true;
    }

    protected String resolveVariable(String str, StrBuilder strBuilder, int i, int i2) {
        StrLookup<?> variableResolver = getVariableResolver();
        if (variableResolver == null) {
            return null;
        }
        return variableResolver.apply(str);
    }

    public void setDisableSubstitutionInValues(boolean z) {
        this.disableSubstitutionInValues = z;
    }

    public void setEnableSubstitutionInVariables(boolean z) {
        this.enableSubstitutionInVariables = z;
    }

    public void setEscapeChar(char c) {
        this.escapeChar = c;
    }

    public void setPreserveEscapes(boolean z) {
        this.preserveEscapes = z;
    }

    public StrSubstitutor setValueDelimiter(char c) {
        return setValueDelimiterMatcher(StrMatcher.charMatcher(c));
    }

    public StrSubstitutor setValueDelimiter(String str) {
        if (str == null || str.isEmpty()) {
            setValueDelimiterMatcher(null);
            return this;
        }
        return setValueDelimiterMatcher(StrMatcher.stringMatcher(str));
    }

    public StrSubstitutor setValueDelimiterMatcher(StrMatcher strMatcher) {
        this.valueDelimiterMatcher = strMatcher;
        return this;
    }

    public StrSubstitutor setVariablePrefix(char c) {
        return setVariablePrefixMatcher(StrMatcher.charMatcher(c));
    }

    public StrSubstitutor setVariablePrefix(String str) {
        Validate.isTrue(str != null, "Variable prefix must not be null!", new Object[0]);
        return setVariablePrefixMatcher(StrMatcher.stringMatcher(str));
    }

    public StrSubstitutor setVariablePrefixMatcher(StrMatcher strMatcher) {
        Validate.isTrue(strMatcher != null, "Variable prefix matcher must not be null!", new Object[0]);
        this.prefixMatcher = strMatcher;
        return this;
    }

    public void setVariableResolver(StrLookup<?> strLookup) {
        this.variableResolver = strLookup;
    }

    public StrSubstitutor setVariableSuffix(char c) {
        return setVariableSuffixMatcher(StrMatcher.charMatcher(c));
    }

    public StrSubstitutor setVariableSuffix(String str) {
        Validate.isTrue(str != null, "Variable suffix must not be null!", new Object[0]);
        return setVariableSuffixMatcher(StrMatcher.stringMatcher(str));
    }

    public StrSubstitutor setVariableSuffixMatcher(StrMatcher strMatcher) {
        Validate.isTrue(strMatcher != null, "Variable suffix matcher must not be null!", new Object[0]);
        this.suffixMatcher = strMatcher;
        return this;
    }

    protected boolean substitute(StrBuilder strBuilder, int i, int i2) {
        return substitute(strBuilder, i, i2, null) > 0;
    }

    /* JADX WARN: Code duplicated, block: B:49:0x00ee  */
    /* JADX WARN: Code duplicated, block: B:53:0x0108  */
    /* JADX WARN: Code duplicated, block: B:55:0x010b  */
    /* JADX WARN: Code duplicated, block: B:57:0x0114  */
    /* JADX WARN: Code duplicated, block: B:58:0x0119  */
    private int substitute(StrBuilder strBuilder, int i, int i2, List<String> list) {
        boolean z;
        int i3;
        String strSubstring;
        String strResolveVariable;
        int length;
        int iSubstitute;
        StrMatcher variablePrefixMatcher = getVariablePrefixMatcher();
        StrMatcher variableSuffixMatcher = getVariableSuffixMatcher();
        char escapeChar = getEscapeChar();
        StrMatcher valueDelimiterMatcher = getValueDelimiterMatcher();
        boolean zIsEnableSubstitutionInVariables = isEnableSubstitutionInVariables();
        boolean zIsDisableSubstitutionInValues = isDisableSubstitutionInValues();
        boolean z2 = list == null;
        int i4 = i;
        int i5 = i + i2;
        int i6 = 0;
        int i7 = 0;
        char[] cArr = strBuilder.buffer;
        List<String> arrayList = list;
        while (i4 < i5) {
            int iIsMatch = variablePrefixMatcher.isMatch(cArr, i4, i, i5);
            if (iIsMatch == 0) {
                i4++;
                z = zIsEnableSubstitutionInVariables;
            } else {
                if (i4 > i) {
                    int i8 = i4 - 1;
                    z = zIsEnableSubstitutionInVariables;
                    if (cArr[i8] == escapeChar) {
                        if (this.preserveEscapes) {
                            i4++;
                            zIsEnableSubstitutionInVariables = z;
                        } else {
                            strBuilder.deleteCharAt(i8);
                            i6--;
                            i5--;
                            cArr = strBuilder.buffer;
                            i7 = 1;
                        }
                    }
                } else {
                    z = zIsEnableSubstitutionInVariables;
                }
                int i9 = i4 + iIsMatch;
                int iIsMatch2 = i9;
                int i10 = 0;
                while (true) {
                    if (iIsMatch2 >= i5) {
                        variableSuffixMatcher = variableSuffixMatcher;
                        escapeChar = escapeChar;
                        zIsDisableSubstitutionInValues = zIsDisableSubstitutionInValues;
                        i4 = iIsMatch2;
                        break;
                    }
                    if (z && variablePrefixMatcher.isMatch(cArr, iIsMatch2, i, i5) != 0) {
                        i10++;
                        iIsMatch2 += variablePrefixMatcher.isMatch(cArr, iIsMatch2, i, i5);
                    } else {
                        int iIsMatch3 = variableSuffixMatcher.isMatch(cArr, iIsMatch2, i, i5);
                        if (iIsMatch3 == 0) {
                            iIsMatch2++;
                        } else {
                            if (i10 == 0) {
                                variableSuffixMatcher = variableSuffixMatcher;
                                escapeChar = escapeChar;
                                String str = new String(cArr, i9, (iIsMatch2 - i4) - iIsMatch);
                                if (z) {
                                    StrBuilder strBuilder2 = new StrBuilder(str);
                                    substitute(strBuilder2, 0, strBuilder2.length());
                                    str = strBuilder2.toString();
                                }
                                int i11 = iIsMatch2 + iIsMatch3;
                                if (valueDelimiterMatcher != null) {
                                    char[] charArray = str.toCharArray();
                                    zIsDisableSubstitutionInValues = zIsDisableSubstitutionInValues;
                                    int i12 = 0;
                                    while (true) {
                                        if (i12 < charArray.length && (z || variablePrefixMatcher.isMatch(charArray, i12, i12, charArray.length) == 0)) {
                                            if (valueDelimiterMatcher.isMatch(charArray, i12) != 0) {
                                                int iIsMatch4 = valueDelimiterMatcher.isMatch(charArray, i12);
                                                i3 = 0;
                                                String strSubstring2 = str.substring(0, i12);
                                                strSubstring = str.substring(i12 + iIsMatch4);
                                                str = strSubstring2;
                                                break;
                                            }
                                            i12++;
                                        }
                                    }
                                    if (arrayList == null) {
                                        arrayList = new ArrayList<>();
                                        arrayList.add(new String(cArr, i, i2));
                                    }
                                    checkCyclicSubstitution(str, arrayList);
                                    arrayList.add(str);
                                    strResolveVariable = resolveVariable(str, strBuilder, i4, i11);
                                    if (strResolveVariable != null) {
                                        strSubstring = strResolveVariable;
                                    }
                                    if (strSubstring != null) {
                                        length = strSubstring.length();
                                        strBuilder.replace(i4, i11, strSubstring);
                                        if (zIsDisableSubstitutionInValues) {
                                            iSubstitute = i3;
                                        } else {
                                            iSubstitute = substitute(strBuilder, i4, length, arrayList);
                                        }
                                        int i13 = (iSubstitute + length) - (i11 - i4);
                                        i11 += i13;
                                        i5 += i13;
                                        i6 += i13;
                                        cArr = strBuilder.buffer;
                                        i7 = 1;
                                    }
                                    i4 = i11;
                                    arrayList.remove(arrayList.size() - 1);
                                    break;
                                }
                                zIsDisableSubstitutionInValues = zIsDisableSubstitutionInValues;
                                i3 = 0;
                                strSubstring = null;
                                if (arrayList == null) {
                                    arrayList = new ArrayList<>();
                                    arrayList.add(new String(cArr, i, i2));
                                }
                                checkCyclicSubstitution(str, arrayList);
                                arrayList.add(str);
                                strResolveVariable = resolveVariable(str, strBuilder, i4, i11);
                                if (strResolveVariable != null) {
                                    strSubstring = strResolveVariable;
                                }
                                if (strSubstring != null) {
                                    length = strSubstring.length();
                                    strBuilder.replace(i4, i11, strSubstring);
                                    if (zIsDisableSubstitutionInValues) {
                                        iSubstitute = substitute(strBuilder, i4, length, arrayList);
                                    } else {
                                        iSubstitute = i3;
                                    }
                                    int i14 = (iSubstitute + length) - (i11 - i4);
                                    i11 += i14;
                                    i5 += i14;
                                    i6 += i14;
                                    cArr = strBuilder.buffer;
                                    i7 = 1;
                                }
                                i4 = i11;
                                arrayList.remove(arrayList.size() - 1);
                                break;
                            }
                            i10--;
                            iIsMatch2 += iIsMatch3;
                            zIsDisableSubstitutionInValues = zIsDisableSubstitutionInValues;
                        }
                    }
                }
                zIsEnableSubstitutionInVariables = z;
                variableSuffixMatcher = variableSuffixMatcher;
                escapeChar = escapeChar;
                zIsDisableSubstitutionInValues = zIsDisableSubstitutionInValues;
            }
            zIsEnableSubstitutionInVariables = z;
            variableSuffixMatcher = variableSuffixMatcher;
            escapeChar = escapeChar;
            zIsDisableSubstitutionInValues = zIsDisableSubstitutionInValues;
        }
        return z2 ? i7 : i6;
    }
}
