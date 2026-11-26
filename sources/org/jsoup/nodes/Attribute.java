package org.jsoup.nodes;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import org.jsoup.helper.Validate;
import org.jsoup.internal.Normalizer;
import org.jsoup.internal.QuietAppendable;
import org.jsoup.internal.SharedConstants;
import org.jsoup.internal.StringUtil;

/* JADX INFO: loaded from: classes6.dex */
public class Attribute implements Map.Entry<String, String>, Cloneable {
    private String key;
    Attributes parent;
    private String val;
    private static final String[] booleanAttributes = {"allowfullscreen", "async", "autofocus", "checked", "compact", "declare", "default", "defer", "disabled", "formnovalidate", "hidden", "inert", "ismap", "itemscope", "multiple", "muted", "nohref", "noresize", "noshade", "novalidate", "nowrap", "open", "readonly", "required", "reversed", "seamless", "selected", "sortable", "truespeed", "typemustmatch"};
    private static final Pattern xmlKeyReplace = Pattern.compile("[^-a-zA-Z0-9_:.]+");
    private static final Pattern htmlKeyReplace = Pattern.compile("[\\x00-\\x1f\\x7f-\\x9f \"'/=]+");

    public Attribute(String str, String str2) {
        this(str, str2, null);
    }

    public Attribute(String str, String str2, Attributes attributes) {
        Validate.notNull(str);
        String strTrim = str.trim();
        Validate.notEmpty(strTrim);
        this.key = strTrim;
        this.val = str2;
        this.parent = attributes;
    }

    @Override // java.util.Map.Entry
    public String getKey() {
        return this.key;
    }

    public void setKey(String str) {
        int iIndexOfKey;
        Validate.notNull(str);
        String strTrim = str.trim();
        Validate.notEmpty(strTrim);
        Attributes attributes = this.parent;
        if (attributes != null && (iIndexOfKey = attributes.indexOfKey(this.key)) != -1) {
            String str2 = this.parent.keys[iIndexOfKey];
            this.parent.keys[iIndexOfKey] = strTrim;
            Map<String, Range.AttributeRange> ranges = this.parent.getRanges();
            if (ranges != null) {
                ranges.put(strTrim, ranges.remove(str2));
            }
        }
        this.key = strTrim;
    }

    @Override // java.util.Map.Entry
    public String getValue() {
        return Attributes.checkNotNull(this.val);
    }

    public boolean hasDeclaredValue() {
        return this.val != null;
    }

    @Override // java.util.Map.Entry
    public String setValue(String str) {
        int iIndexOfKey;
        String str2 = this.val;
        Attributes attributes = this.parent;
        if (attributes != null && (iIndexOfKey = attributes.indexOfKey(this.key)) != -1) {
            str2 = this.parent.get(this.key);
            this.parent.vals[iIndexOfKey] = str;
        }
        this.val = str;
        return Attributes.checkNotNull(str2);
    }

    public String prefix() {
        int iIndexOf = this.key.indexOf(58);
        if (iIndexOf == -1) {
            return "";
        }
        return this.key.substring(0, iIndexOf);
    }

    public String localName() {
        int iIndexOf = this.key.indexOf(58);
        if (iIndexOf == -1) {
            return this.key;
        }
        return this.key.substring(iIndexOf + 1);
    }

    public String namespace() {
        String str;
        Attributes attributes = this.parent;
        return (attributes == null || (str = (String) attributes.userData(new StringBuilder(SharedConstants.XmlnsAttr).append(prefix()).toString())) == null) ? "" : str;
    }

    public String html() {
        StringBuilder sbBorrowBuilder = StringUtil.borrowBuilder();
        html(QuietAppendable.wrap(sbBorrowBuilder), new Document.OutputSettings());
        return StringUtil.releaseBuilder(sbBorrowBuilder);
    }

    public Range.AttributeRange sourceRange() {
        Attributes attributes = this.parent;
        return attributes == null ? Range.AttributeRange.UntrackedAttr : attributes.sourceRange(this.key);
    }

    void html(QuietAppendable quietAppendable, Document.OutputSettings outputSettings) {
        html(this.key, this.val, quietAppendable, outputSettings);
    }

    static void html(String str, String str2, QuietAppendable quietAppendable, Document.OutputSettings outputSettings) {
        String validKey = getValidKey(str, outputSettings.syntax());
        if (validKey == null) {
            return;
        }
        htmlNoValidate(validKey, str2, quietAppendable, outputSettings);
    }

    @Deprecated
    protected void html(Appendable appendable, Document.OutputSettings outputSettings) throws IOException {
        html(this.key, this.val, appendable, outputSettings);
    }

    @Deprecated
    protected static void html(String str, String str2, Appendable appendable, Document.OutputSettings outputSettings) throws IOException {
        html(str, str2, QuietAppendable.wrap(appendable), outputSettings);
    }

    static void htmlNoValidate(String str, String str2, QuietAppendable quietAppendable, Document.OutputSettings outputSettings) {
        quietAppendable.append(str);
        if (shouldCollapseAttribute(str, str2, outputSettings)) {
            return;
        }
        quietAppendable.append("=\"");
        Entities.escape(quietAppendable, Attributes.checkNotNull(str2), outputSettings, 2);
        quietAppendable.append('\"');
    }

    public static String getValidKey(String str, Document.OutputSettings.Syntax syntax) {
        if (syntax == Document.OutputSettings.Syntax.xml && !isValidXmlKey(str)) {
            String strReplaceAll = xmlKeyReplace.matcher(str).replaceAll("_");
            if (isValidXmlKey(strReplaceAll)) {
                return strReplaceAll;
            }
            return null;
        }
        if (syntax != Document.OutputSettings.Syntax.html || isValidHtmlKey(str)) {
            return str;
        }
        String strReplaceAll2 = htmlKeyReplace.matcher(str).replaceAll("_");
        if (isValidHtmlKey(strReplaceAll2)) {
            return strReplaceAll2;
        }
        return null;
    }

    private static boolean isValidXmlKey(String str) {
        int length = str.length();
        if (length == 0) {
            return false;
        }
        char cCharAt = str.charAt(0);
        if ((cCharAt < 'a' || cCharAt > 'z') && !((cCharAt >= 'A' && cCharAt <= 'Z') || cCharAt == '_' || cCharAt == ':')) {
            return false;
        }
        for (int i = 1; i < length; i++) {
            char cCharAt2 = str.charAt(i);
            if ((cCharAt2 < 'a' || cCharAt2 > 'z') && ((cCharAt2 < 'A' || cCharAt2 > 'Z') && !((cCharAt2 >= '0' && cCharAt2 <= '9') || cCharAt2 == '-' || cCharAt2 == '_' || cCharAt2 == ':' || cCharAt2 == '.'))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isValidHtmlKey(String str) {
        int length = str.length();
        if (length == 0) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt <= 31 || ((cCharAt >= 127 && cCharAt <= 159) || cCharAt == ' ' || cCharAt == '\"' || cCharAt == '\'' || cCharAt == '/' || cCharAt == '=')) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        return html();
    }

    public static Attribute createFromEncoded(String str, String str2) {
        return new Attribute(str, Entities.unescape(str2, true), null);
    }

    protected boolean isDataAttribute() {
        return isDataAttribute(this.key);
    }

    protected static boolean isDataAttribute(String str) {
        return str.startsWith("data-") && str.length() > "data-".length();
    }

    protected final boolean shouldCollapseAttribute(Document.OutputSettings outputSettings) {
        return shouldCollapseAttribute(this.key, this.val, outputSettings);
    }

    protected static boolean shouldCollapseAttribute(String str, String str2, Document.OutputSettings outputSettings) {
        if (outputSettings.syntax() != Document.OutputSettings.Syntax.html) {
            return false;
        }
        if (str2 != null) {
            return (str2.isEmpty() || str2.equalsIgnoreCase(str)) && isBooleanAttribute(str);
        }
        return true;
    }

    public static boolean isBooleanAttribute(String str) {
        return Arrays.binarySearch(booleanAttributes, Normalizer.lowerCase(str)) >= 0;
    }

    @Override // java.util.Map.Entry
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            Attribute attribute = (Attribute) obj;
            if (Objects.equals(this.key, attribute.key) && Objects.equals(this.val, attribute.val)) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.Map.Entry
    public int hashCode() {
        return Objects.hash(this.key, this.val);
    }

    public Attribute clone() {
        try {
            return (Attribute) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
