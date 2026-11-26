package org.ccil.cowan.tagsoup;

import org.apache.commons.text.lookup.StringLookupFactory;

/* JADX INFO: loaded from: classes6.dex */
public class ElementType {
    private AttributesImpl theAtts = new AttributesImpl();
    private int theFlags;
    private String theLocalName;
    private int theMemberOf;
    private int theModel;
    private String theName;
    private String theNamespace;
    private ElementType theParent;
    private Schema theSchema;

    public ElementType(String str, int i, int i2, int i3, Schema schema) {
        this.theName = str;
        this.theModel = i;
        this.theMemberOf = i2;
        this.theFlags = i3;
        this.theSchema = schema;
        this.theNamespace = namespace(str, false);
        this.theLocalName = localName(str);
    }

    public String namespace(String str, boolean z) {
        int iIndexOf = str.indexOf(58);
        if (iIndexOf == -1) {
            return z ? "" : this.theSchema.getURI();
        }
        String strSubstring = str.substring(0, iIndexOf);
        if (strSubstring.equals(StringLookupFactory.KEY_XML)) {
            return org.jsoup.parser.Parser.NamespaceXml;
        }
        return ("urn:x-prefix:" + strSubstring).intern();
    }

    public String localName(String str) {
        int iIndexOf = str.indexOf(58);
        return iIndexOf == -1 ? str : str.substring(iIndexOf + 1).intern();
    }

    public String name() {
        return this.theName;
    }

    public String namespace() {
        return this.theNamespace;
    }

    public String localName() {
        return this.theLocalName;
    }

    public int model() {
        return this.theModel;
    }

    public int memberOf() {
        return this.theMemberOf;
    }

    public int flags() {
        return this.theFlags;
    }

    public AttributesImpl atts() {
        return this.theAtts;
    }

    public ElementType parent() {
        return this.theParent;
    }

    public Schema schema() {
        return this.theSchema;
    }

    public boolean canContain(ElementType elementType) {
        return (this.theModel & elementType.theMemberOf) != 0;
    }

    public void setAttribute(AttributesImpl attributesImpl, String str, String str2, String str3) {
        if (str.equals("xmlns") || str.startsWith("xmlns:")) {
            return;
        }
        String strNamespace = namespace(str, true);
        String strLocalName = localName(str);
        int index = attributesImpl.getIndex(str);
        if (index == -1) {
            String strIntern = str.intern();
            String str4 = str2 == null ? "CDATA" : str2;
            if (!str4.equals("CDATA")) {
                str3 = normalize(str3);
            }
            attributesImpl.addAttribute(strNamespace, strLocalName, strIntern, str4, str3);
            return;
        }
        if (str2 == null) {
            str2 = attributesImpl.getType(index);
        }
        String str5 = str2;
        if (!str5.equals("CDATA")) {
            str3 = normalize(str3);
        }
        attributesImpl.setAttribute(index, strNamespace, strLocalName, str, str5, str3);
    }

    public static String normalize(String str) {
        if (str == null) {
            return str;
        }
        String strTrim = str.trim();
        if (strTrim.indexOf("  ") == -1) {
            return strTrim;
        }
        int length = strTrim.length();
        StringBuffer stringBuffer = new StringBuffer(length);
        boolean z = false;
        for (int i = 0; i < length; i++) {
            char cCharAt = strTrim.charAt(i);
            if (cCharAt == ' ') {
                if (!z) {
                    stringBuffer.append(cCharAt);
                }
                z = true;
            } else {
                stringBuffer.append(cCharAt);
                z = false;
            }
        }
        return stringBuffer.toString();
    }

    public void setAttribute(String str, String str2, String str3) {
        setAttribute(this.theAtts, str, str2, str3);
    }

    public void setModel(int i) {
        this.theModel = i;
    }

    public void setMemberOf(int i) {
        this.theMemberOf = i;
    }

    public void setFlags(int i) {
        this.theFlags = i;
    }

    public void setParent(ElementType elementType) {
        this.theParent = elementType;
    }
}
