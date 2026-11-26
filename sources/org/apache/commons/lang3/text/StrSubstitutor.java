package org.apache.commons.lang3.text;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;

/* JADX INFO: loaded from: classes6.dex */
@Deprecated
public class StrSubstitutor {
    public static final char DEFAULT_ESCAPE = '$';
    public static final StrMatcher DEFAULT_PREFIX = StrMatcher.stringMatcher(StringSubstitutor.DEFAULT_VAR_START);
    public static final StrMatcher DEFAULT_SUFFIX = StrMatcher.stringMatcher(StringSubstitutor.DEFAULT_VAR_END);
    public static final StrMatcher DEFAULT_VALUE_DELIMITER = StrMatcher.stringMatcher(":-");
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

    public static String replace(Object obj, Properties properties) {
        if (properties == null) {
            return obj.toString();
        }
        HashMap map = new HashMap();
        Enumeration<?> enumerationPropertyNames = properties.propertyNames();
        while (enumerationPropertyNames.hasMoreElements()) {
            String strValueOf = String.valueOf(enumerationPropertyNames.nextElement());
            map.put(strValueOf, properties.getProperty(strValueOf));
        }
        return replace(obj, map);
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
        return variableResolver.lookup(str);
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
        if (StringUtils.isEmpty(str)) {
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
        return setVariablePrefixMatcher(StrMatcher.stringMatcher((String) Objects.requireNonNull(str)));
    }

    public StrSubstitutor setVariablePrefixMatcher(StrMatcher strMatcher) {
        this.prefixMatcher = (StrMatcher) Objects.requireNonNull(strMatcher, "prefixMatcher");
        return this;
    }

    public void setVariableResolver(StrLookup<?> strLookup) {
        this.variableResolver = strLookup;
    }

    public StrSubstitutor setVariableSuffix(char c) {
        return setVariableSuffixMatcher(StrMatcher.charMatcher(c));
    }

    public StrSubstitutor setVariableSuffix(String str) {
        return setVariableSuffixMatcher(StrMatcher.stringMatcher((String) Objects.requireNonNull(str)));
    }

    public StrSubstitutor setVariableSuffixMatcher(StrMatcher strMatcher) {
        this.suffixMatcher = (StrMatcher) Objects.requireNonNull(strMatcher);
        return this;
    }

    protected boolean substitute(StrBuilder strBuilder, int i, int i2) {
        return substitute(strBuilder, i, i2, null) > 0;
    }

    /* JADX WARN: Code duplicated, block: B:49:0x00f1  */
    /* JADX WARN: Code duplicated, block: B:53:0x010b  */
    /* JADX WARN: Code duplicated, block: B:55:0x010e  */
    private int substitute(StrBuilder strBuilder, int i, int i2, List<String> list) {
        StrMatcher strMatcher;
        boolean z;
        int i3;
        String strSubstring;
        String strResolveVariable;
        int iIsMatch;
        StrMatcher variablePrefixMatcher = getVariablePrefixMatcher();
        StrMatcher variableSuffixMatcher = getVariableSuffixMatcher();
        char escapeChar = getEscapeChar();
        StrMatcher valueDelimiterMatcher = getValueDelimiterMatcher();
        boolean zIsEnableSubstitutionInVariables = isEnableSubstitutionInVariables();
        boolean z2 = list == null;
        int i4 = i;
        int i5 = i + i2;
        int i6 = 0;
        int i7 = 0;
        char[] cArr = strBuilder.buffer;
        List<String> arrayList = list;
        while (i4 < i5) {
            int iIsMatch2 = variablePrefixMatcher.isMatch(cArr, i4, i, i5);
            if (iIsMatch2 != 0) {
                if (i4 > i) {
                    i3 = 1;
                    int i8 = i4 - 1;
                    if (cArr[i8] == escapeChar) {
                        if (this.preserveEscapes) {
                            i4++;
                        } else {
                            strBuilder.deleteCharAt(i8);
                            i6--;
                            i5--;
                            strMatcher = variablePrefixMatcher;
                            z = zIsEnableSubstitutionInVariables;
                            cArr = strBuilder.buffer;
                            i7 = 1;
                        }
                    }
                } else {
                    i3 = 1;
                }
                int i9 = i4 + iIsMatch2;
                int i10 = i9;
                int i11 = 0;
                while (true) {
                    if (i10 >= i5) {
                        strMatcher = variablePrefixMatcher;
                        z = zIsEnableSubstitutionInVariables;
                        i4 = i10;
                        break;
                    }
                    if (!zIsEnableSubstitutionInVariables || (iIsMatch = variablePrefixMatcher.isMatch(cArr, i10, i, i5)) == 0) {
                        int iIsMatch3 = variableSuffixMatcher.isMatch(cArr, i10, i, i5);
                        if (iIsMatch3 == 0) {
                            i10++;
                        } else {
                            if (i11 == 0) {
                                String str = new String(cArr, i9, (i10 - i4) - iIsMatch2);
                                if (zIsEnableSubstitutionInVariables) {
                                    StrBuilder strBuilder2 = new StrBuilder(str);
                                    substitute(strBuilder2, 0, strBuilder2.length());
                                    str = strBuilder2.toString();
                                }
                                int i12 = i10 + iIsMatch3;
                                if (valueDelimiterMatcher != null) {
                                    char[] charArray = str.toCharArray();
                                    z = zIsEnableSubstitutionInVariables;
                                    int i13 = 0;
                                    while (true) {
                                        if (i13 >= charArray.length || !(z || variablePrefixMatcher.isMatch(charArray, i13, i13, charArray.length) == 0)) {
                                            strMatcher = variablePrefixMatcher;
                                        } else {
                                            int iIsMatch4 = valueDelimiterMatcher.isMatch(charArray, i13);
                                            if (iIsMatch4 != 0) {
                                                strMatcher = variablePrefixMatcher;
                                                String strSubstring2 = str.substring(0, i13);
                                                strSubstring = str.substring(i13 + iIsMatch4);
                                                str = strSubstring2;
                                                break;
                                            }
                                            i13++;
                                            variablePrefixMatcher = variablePrefixMatcher;
                                        }
                                    }
                                    if (arrayList == null) {
                                        arrayList = new ArrayList<>();
                                        arrayList.add(new String(cArr, i, i2));
                                    }
                                    checkCyclicSubstitution(str, arrayList);
                                    arrayList.add(str);
                                    strResolveVariable = resolveVariable(str, strBuilder, i4, i12);
                                    if (strResolveVariable != null) {
                                        strSubstring = strResolveVariable;
                                    }
                                    if (strSubstring != null) {
                                        int length = strSubstring.length();
                                        strBuilder.replace(i4, i12, strSubstring);
                                        int iSubstitute = (substitute(strBuilder, i4, length, arrayList) + length) - (i12 - i4);
                                        i12 += iSubstitute;
                                        i5 += iSubstitute;
                                        i6 += iSubstitute;
                                        cArr = strBuilder.buffer;
                                        i7 = i3;
                                    }
                                    i4 = i12;
                                    arrayList.remove(arrayList.size() - 1);
                                    break;
                                }
                                strMatcher = variablePrefixMatcher;
                                z = zIsEnableSubstitutionInVariables;
                                strSubstring = null;
                                if (arrayList == null) {
                                    arrayList = new ArrayList<>();
                                    arrayList.add(new String(cArr, i, i2));
                                }
                                checkCyclicSubstitution(str, arrayList);
                                arrayList.add(str);
                                strResolveVariable = resolveVariable(str, strBuilder, i4, i12);
                                if (strResolveVariable != null) {
                                    strSubstring = strResolveVariable;
                                }
                                if (strSubstring != null) {
                                    int length2 = strSubstring.length();
                                    strBuilder.replace(i4, i12, strSubstring);
                                    int iSubstitute2 = (substitute(strBuilder, i4, length2, arrayList) + length2) - (i12 - i4);
                                    i12 += iSubstitute2;
                                    i5 += iSubstitute2;
                                    i6 += iSubstitute2;
                                    cArr = strBuilder.buffer;
                                    i7 = i3;
                                }
                                i4 = i12;
                                arrayList.remove(arrayList.size() - 1);
                                break;
                            }
                            i11--;
                            i10 += iIsMatch3;
                            variablePrefixMatcher = variablePrefixMatcher;
                        }
                    } else {
                        i11++;
                        i10 += iIsMatch;
                    }
                }
            } else {
                i4++;
                strMatcher = variablePrefixMatcher;
                z = zIsEnableSubstitutionInVariables;
            }
            variableSuffixMatcher = variableSuffixMatcher;
            escapeChar = escapeChar;
            zIsEnableSubstitutionInVariables = z;
            variablePrefixMatcher = strMatcher;
        }
        return z2 ? i7 : i6;
    }
}
