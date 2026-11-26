package org.apache.commons.text;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.Validate;
import org.apache.commons.text.lookup.StringLookup;
import org.apache.commons.text.lookup.StringLookupFactory;
import org.apache.commons.text.matcher.StringMatcher;
import org.apache.commons.text.matcher.StringMatcherFactory;

/* JADX INFO: loaded from: classes6.dex */
public class StringSubstitutor {
    public static final char DEFAULT_ESCAPE = '$';
    public static final String DEFAULT_VAR_DEFAULT = ":-";
    private boolean disableSubstitutionInValues;
    private boolean enableSubstitutionInVariables;
    private char escapeChar;
    private boolean failOnUndefinedVariable;
    private StringMatcher prefixMatcher;
    private boolean preserveEscapes;
    private StringMatcher suffixMatcher;
    private StringMatcher valueDelimiterMatcher;
    private StringLookup variableResolver;
    public static final String DEFAULT_VAR_START = "${";
    public static final StringMatcher DEFAULT_PREFIX = StringMatcherFactory.INSTANCE.stringMatcher(DEFAULT_VAR_START);
    public static final String DEFAULT_VAR_END = "}";
    public static final StringMatcher DEFAULT_SUFFIX = StringMatcherFactory.INSTANCE.stringMatcher(DEFAULT_VAR_END);
    public static final StringMatcher DEFAULT_VALUE_DELIMITER = StringMatcherFactory.INSTANCE.stringMatcher(":-");

    private static final class Result {
        public final boolean altered;
        public final int lengthChange;

        private Result(boolean z, int i) {
            this.altered = z;
            this.lengthChange = i;
        }

        public String toString() {
            return "Result [altered=" + this.altered + ", lengthChange=" + this.lengthChange + "]";
        }
    }

    public static StringSubstitutor createInterpolator() {
        return new StringSubstitutor(StringLookupFactory.INSTANCE.interpolatorStringLookup());
    }

    public static <V> String replace(Object obj, Map<String, V> map) {
        return new StringSubstitutor(map).replace(obj);
    }

    public static <V> String replace(Object obj, Map<String, V> map, String str, String str2) {
        return new StringSubstitutor(map, str, str2).replace(obj);
    }

    public static String replace(Object obj, final Properties properties) {
        if (properties == null) {
            return obj.toString();
        }
        Stream<String> stream = properties.stringPropertyNames().stream();
        Function functionIdentity = Function.identity();
        Objects.requireNonNull(properties);
        return replace(obj, (Map) stream.collect(Collectors.toMap(functionIdentity, new Function() { // from class: org.apache.commons.text.StringSubstitutor$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj2) {
                return properties.getProperty((String) obj2);
            }
        })));
    }

    public static String replaceSystemProperties(Object obj) {
        return new StringSubstitutor(StringLookupFactory.INSTANCE.systemPropertyStringLookup()).replace(obj);
    }

    public StringSubstitutor() {
        this((StringLookup) null, DEFAULT_PREFIX, DEFAULT_SUFFIX, '$');
    }

    public <V> StringSubstitutor(Map<String, V> map) {
        this(StringLookupFactory.INSTANCE.mapStringLookup(map), DEFAULT_PREFIX, DEFAULT_SUFFIX, '$');
    }

    public <V> StringSubstitutor(Map<String, V> map, String str, String str2) {
        this(StringLookupFactory.INSTANCE.mapStringLookup(map), str, str2, '$');
    }

    public <V> StringSubstitutor(Map<String, V> map, String str, String str2, char c) {
        this(StringLookupFactory.INSTANCE.mapStringLookup(map), str, str2, c);
    }

    public <V> StringSubstitutor(Map<String, V> map, String str, String str2, char c, String str3) {
        this(StringLookupFactory.INSTANCE.mapStringLookup(map), str, str2, c, str3);
    }

    public StringSubstitutor(StringLookup stringLookup) {
        this(stringLookup, DEFAULT_PREFIX, DEFAULT_SUFFIX, '$');
    }

    public StringSubstitutor(StringLookup stringLookup, String str, String str2, char c) {
        setVariableResolver(stringLookup);
        setVariablePrefix(str);
        setVariableSuffix(str2);
        setEscapeChar(c);
        setValueDelimiterMatcher(DEFAULT_VALUE_DELIMITER);
    }

    public StringSubstitutor(StringLookup stringLookup, String str, String str2, char c, String str3) {
        setVariableResolver(stringLookup);
        setVariablePrefix(str);
        setVariableSuffix(str2);
        setEscapeChar(c);
        setValueDelimiter(str3);
    }

    public StringSubstitutor(StringLookup stringLookup, StringMatcher stringMatcher, StringMatcher stringMatcher2, char c) {
        this(stringLookup, stringMatcher, stringMatcher2, c, DEFAULT_VALUE_DELIMITER);
    }

    public StringSubstitutor(StringLookup stringLookup, StringMatcher stringMatcher, StringMatcher stringMatcher2, char c, StringMatcher stringMatcher3) {
        setVariableResolver(stringLookup);
        setVariablePrefixMatcher(stringMatcher);
        setVariableSuffixMatcher(stringMatcher2);
        setEscapeChar(c);
        setValueDelimiterMatcher(stringMatcher3);
    }

    public StringSubstitutor(StringSubstitutor stringSubstitutor) {
        this.disableSubstitutionInValues = stringSubstitutor.isDisableSubstitutionInValues();
        this.enableSubstitutionInVariables = stringSubstitutor.isEnableSubstitutionInVariables();
        this.failOnUndefinedVariable = stringSubstitutor.isEnableUndefinedVariableException();
        this.escapeChar = stringSubstitutor.getEscapeChar();
        this.prefixMatcher = stringSubstitutor.getVariablePrefixMatcher();
        this.preserveEscapes = stringSubstitutor.isPreserveEscapes();
        this.suffixMatcher = stringSubstitutor.getVariableSuffixMatcher();
        this.valueDelimiterMatcher = stringSubstitutor.getValueDelimiterMatcher();
        this.variableResolver = stringSubstitutor.getStringLookup();
    }

    private void checkCyclicSubstitution(String str, List<String> list) {
        if (list.contains(str)) {
            TextStringBuilder textStringBuilder = new TextStringBuilder(256);
            textStringBuilder.append("Infinite loop in property interpolation of ");
            textStringBuilder.append(list.remove(0));
            textStringBuilder.append(": ");
            textStringBuilder.appendWithSeparators(list, "->");
            throw new IllegalStateException(textStringBuilder.toString());
        }
    }

    public char getEscapeChar() {
        return this.escapeChar;
    }

    public StringLookup getStringLookup() {
        return this.variableResolver;
    }

    public StringMatcher getValueDelimiterMatcher() {
        return this.valueDelimiterMatcher;
    }

    public StringMatcher getVariablePrefixMatcher() {
        return this.prefixMatcher;
    }

    public StringMatcher getVariableSuffixMatcher() {
        return this.suffixMatcher;
    }

    public boolean isDisableSubstitutionInValues() {
        return this.disableSubstitutionInValues;
    }

    public boolean isEnableSubstitutionInVariables() {
        return this.enableSubstitutionInVariables;
    }

    public boolean isEnableUndefinedVariableException() {
        return this.failOnUndefinedVariable;
    }

    public boolean isPreserveEscapes() {
        return this.preserveEscapes;
    }

    public String replace(char[] cArr) {
        if (cArr == null) {
            return null;
        }
        TextStringBuilder textStringBuilderAppend = new TextStringBuilder(cArr.length).append(cArr);
        substitute(textStringBuilderAppend, 0, cArr.length);
        return textStringBuilderAppend.toString();
    }

    public String replace(char[] cArr, int i, int i2) {
        if (cArr == null) {
            return null;
        }
        TextStringBuilder textStringBuilderAppend = new TextStringBuilder(i2).append(cArr, i, i2);
        substitute(textStringBuilderAppend, 0, i2);
        return textStringBuilderAppend.toString();
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
        TextStringBuilder textStringBuilderAppend = new TextStringBuilder(i2).append(charSequence.toString(), i, i2);
        substitute(textStringBuilderAppend, 0, i2);
        return textStringBuilderAppend.toString();
    }

    public String replace(Object obj) {
        if (obj == null) {
            return null;
        }
        TextStringBuilder textStringBuilderAppend = new TextStringBuilder().append(obj);
        substitute(textStringBuilderAppend, 0, textStringBuilderAppend.length());
        return textStringBuilderAppend.toString();
    }

    public String replace(String str) {
        if (str == null) {
            return null;
        }
        TextStringBuilder textStringBuilder = new TextStringBuilder(str);
        return !substitute(textStringBuilder, 0, str.length()) ? str : textStringBuilder.toString();
    }

    public String replace(String str, int i, int i2) {
        if (str == null) {
            return null;
        }
        TextStringBuilder textStringBuilderAppend = new TextStringBuilder(i2).append(str, i, i2);
        if (!substitute(textStringBuilderAppend, 0, i2)) {
            return str.substring(i, i2 + i);
        }
        return textStringBuilderAppend.toString();
    }

    public String replace(StringBuffer stringBuffer) {
        if (stringBuffer == null) {
            return null;
        }
        TextStringBuilder textStringBuilderAppend = new TextStringBuilder(stringBuffer.length()).append(stringBuffer);
        substitute(textStringBuilderAppend, 0, textStringBuilderAppend.length());
        return textStringBuilderAppend.toString();
    }

    public String replace(StringBuffer stringBuffer, int i, int i2) {
        if (stringBuffer == null) {
            return null;
        }
        TextStringBuilder textStringBuilderAppend = new TextStringBuilder(i2).append(stringBuffer, i, i2);
        substitute(textStringBuilderAppend, 0, i2);
        return textStringBuilderAppend.toString();
    }

    public String replace(TextStringBuilder textStringBuilder) {
        if (textStringBuilder == null) {
            return null;
        }
        TextStringBuilder textStringBuilderAppend = new TextStringBuilder(textStringBuilder.length()).append(textStringBuilder);
        substitute(textStringBuilderAppend, 0, textStringBuilderAppend.length());
        return textStringBuilderAppend.toString();
    }

    public String replace(TextStringBuilder textStringBuilder, int i, int i2) {
        if (textStringBuilder == null) {
            return null;
        }
        TextStringBuilder textStringBuilderAppend = new TextStringBuilder(i2).append(textStringBuilder, i, i2);
        substitute(textStringBuilderAppend, 0, i2);
        return textStringBuilderAppend.toString();
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
        TextStringBuilder textStringBuilderAppend = new TextStringBuilder(i2).append(stringBuffer, i, i2);
        if (!substitute(textStringBuilderAppend, 0, i2)) {
            return false;
        }
        stringBuffer.replace(i, i2 + i, textStringBuilderAppend.toString());
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
        TextStringBuilder textStringBuilderAppend = new TextStringBuilder(i2).append(sb, i, i2);
        if (!substitute(textStringBuilderAppend, 0, i2)) {
            return false;
        }
        sb.replace(i, i2 + i, textStringBuilderAppend.toString());
        return true;
    }

    public boolean replaceIn(TextStringBuilder textStringBuilder) {
        if (textStringBuilder == null) {
            return false;
        }
        return substitute(textStringBuilder, 0, textStringBuilder.length());
    }

    public boolean replaceIn(TextStringBuilder textStringBuilder, int i, int i2) {
        if (textStringBuilder == null) {
            return false;
        }
        return substitute(textStringBuilder, i, i2);
    }

    protected String resolveVariable(String str, TextStringBuilder textStringBuilder, int i, int i2) {
        StringLookup stringLookup = getStringLookup();
        if (stringLookup == null) {
            return null;
        }
        return stringLookup.apply(str);
    }

    public StringSubstitutor setDisableSubstitutionInValues(boolean z) {
        this.disableSubstitutionInValues = z;
        return this;
    }

    public StringSubstitutor setEnableSubstitutionInVariables(boolean z) {
        this.enableSubstitutionInVariables = z;
        return this;
    }

    public StringSubstitutor setEnableUndefinedVariableException(boolean z) {
        this.failOnUndefinedVariable = z;
        return this;
    }

    public StringSubstitutor setEscapeChar(char c) {
        this.escapeChar = c;
        return this;
    }

    public StringSubstitutor setPreserveEscapes(boolean z) {
        this.preserveEscapes = z;
        return this;
    }

    public StringSubstitutor setValueDelimiter(char c) {
        return setValueDelimiterMatcher(StringMatcherFactory.INSTANCE.charMatcher(c));
    }

    public StringSubstitutor setValueDelimiter(String str) {
        if (str == null || str.isEmpty()) {
            setValueDelimiterMatcher(null);
            return this;
        }
        return setValueDelimiterMatcher(StringMatcherFactory.INSTANCE.stringMatcher(str));
    }

    public StringSubstitutor setValueDelimiterMatcher(StringMatcher stringMatcher) {
        this.valueDelimiterMatcher = stringMatcher;
        return this;
    }

    public StringSubstitutor setVariablePrefix(char c) {
        return setVariablePrefixMatcher(StringMatcherFactory.INSTANCE.charMatcher(c));
    }

    public StringSubstitutor setVariablePrefix(String str) {
        Validate.isTrue(str != null, "Variable prefix must not be null!", new Object[0]);
        return setVariablePrefixMatcher(StringMatcherFactory.INSTANCE.stringMatcher(str));
    }

    public StringSubstitutor setVariablePrefixMatcher(StringMatcher stringMatcher) {
        Validate.isTrue(stringMatcher != null, "Variable prefix matcher must not be null!", new Object[0]);
        this.prefixMatcher = stringMatcher;
        return this;
    }

    public StringSubstitutor setVariableResolver(StringLookup stringLookup) {
        this.variableResolver = stringLookup;
        return this;
    }

    public StringSubstitutor setVariableSuffix(char c) {
        return setVariableSuffixMatcher(StringMatcherFactory.INSTANCE.charMatcher(c));
    }

    public StringSubstitutor setVariableSuffix(String str) {
        Validate.isTrue(str != null, "Variable suffix must not be null!", new Object[0]);
        return setVariableSuffixMatcher(StringMatcherFactory.INSTANCE.stringMatcher(str));
    }

    public StringSubstitutor setVariableSuffixMatcher(StringMatcher stringMatcher) {
        Validate.isTrue(stringMatcher != null, "Variable suffix matcher must not be null!", new Object[0]);
        this.suffixMatcher = stringMatcher;
        return this;
    }

    protected boolean substitute(TextStringBuilder textStringBuilder, int i, int i2) {
        return substitute(textStringBuilder, i, i2, null).altered;
    }

    /* JADX WARN: Code duplicated, block: B:16:0x006e  */
    /* JADX WARN: Code duplicated, block: B:29:0x00aa  */
    /* JADX WARN: Code duplicated, block: B:30:0x00be  */
    /* JADX WARN: Code duplicated, block: B:33:0x00c4  */
    /* JADX WARN: Code duplicated, block: B:48:0x0106  */
    /* JADX WARN: Code duplicated, block: B:52:0x011f  */
    /* JADX WARN: Code duplicated, block: B:54:0x0122  */
    /* JADX WARN: Code duplicated, block: B:56:0x012b  */
    /* JADX WARN: Code duplicated, block: B:57:0x0132  */
    /* JADX WARN: Code duplicated, block: B:59:0x013d A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:71:0x008e A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:72:0x0149 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:75:0x00a0 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:86:0x0088 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:87:0x0085 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:88:0x008a A[SYNTHETIC] */
    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not found exit edge by exit block: B:35:0x00cc
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.checkLoopExits(LoopRegionMaker.java:272)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeLoopRegion(LoopRegionMaker.java:237)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:80)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:92)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:111)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:117)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:111)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:117)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:111)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeEndlessLoop(LoopRegionMaker.java:590)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:82)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:92)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:117)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:162)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:92)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:49)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    private org.apache.commons.text.StringSubstitutor.Result substitute(org.apache.commons.text.TextStringBuilder r27, int r28, int r29, java.util.List<java.lang.String> r30) {
        /*
            Method dump skipped, instruction units count: 395
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.text.StringSubstitutor.substitute(org.apache.commons.text.TextStringBuilder, int, int, java.util.List):org.apache.commons.text.StringSubstitutor$Result");
    }

    public String toString() {
        return "StringSubstitutor [disableSubstitutionInValues=" + this.disableSubstitutionInValues + ", enableSubstitutionInVariables=" + this.enableSubstitutionInVariables + ", enableUndefinedVariableException=" + this.failOnUndefinedVariable + ", escapeChar=" + this.escapeChar + ", prefixMatcher=" + this.prefixMatcher + ", preserveEscapes=" + this.preserveEscapes + ", suffixMatcher=" + this.suffixMatcher + ", valueDelimiterMatcher=" + this.valueDelimiterMatcher + ", variableResolver=" + this.variableResolver + "]";
    }
}
