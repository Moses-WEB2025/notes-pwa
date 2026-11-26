package org.jsoup.parser;

import java.util.Objects;

/* JADX INFO: loaded from: classes6.dex */
public class Tag implements Cloneable {
    public static int Block = 4;
    public static int Data = 256;
    public static int FormSubmittable = 512;
    public static int InlineContainer = 8;
    public static int Known = 1;
    public static int PreserveWhitespace = 64;
    public static int RcData = 128;
    public static int SeenSelfClose = 32;
    public static int SelfClose = 16;
    public static int Void = 2;
    String namespace;
    String normalName;
    int options;
    String tagName;

    public Tag(String str, String str2) {
        this(str, ParseSettings.normalName(str), str2);
    }

    public Tag(String str) {
        this(str, ParseSettings.normalName(str), Parser.NamespaceHtml);
    }

    Tag(String str, String str2, String str3) {
        this.options = 0;
        this.tagName = str;
        this.normalName = str2;
        this.namespace = str3;
    }

    public String getName() {
        return this.tagName;
    }

    public String name() {
        return this.tagName;
    }

    public Tag name(String str) {
        this.tagName = str;
        this.normalName = ParseSettings.normalName(str);
        return this;
    }

    public String prefix() {
        int iIndexOf = this.tagName.indexOf(58);
        if (iIndexOf == -1) {
            return "";
        }
        return this.tagName.substring(0, iIndexOf);
    }

    public String localName() {
        int iIndexOf = this.tagName.indexOf(58);
        if (iIndexOf == -1) {
            return this.tagName;
        }
        return this.tagName.substring(iIndexOf + 1);
    }

    public String normalName() {
        return this.normalName;
    }

    public String namespace() {
        return this.namespace;
    }

    public Tag namespace(String str) {
        this.namespace = str;
        return this;
    }

    public Tag set(int i) {
        this.options = i | this.options | Known;
        return this;
    }

    public boolean is(int i) {
        return (this.options & i) != 0;
    }

    public Tag clear(int i) {
        int i2 = this.options & (~i);
        this.options = i2;
        int i3 = Known;
        if (i != i3) {
            this.options = i2 | i3;
        }
        return this;
    }

    public static Tag valueOf(String str, String str2, ParseSettings parseSettings) {
        return TagSet.Html().valueOf(str, ParseSettings.normalName(str), str2, parseSettings.preserveTagCase());
    }

    public static Tag valueOf(String str) {
        return valueOf(str, Parser.NamespaceHtml, ParseSettings.preserveCase);
    }

    public static Tag valueOf(String str, ParseSettings parseSettings) {
        return valueOf(str, Parser.NamespaceHtml, parseSettings);
    }

    public boolean isBlock() {
        return (this.options & Block) != 0;
    }

    @Deprecated
    public boolean formatAsBlock() {
        return (this.options & InlineContainer) != 0;
    }

    public boolean isInline() {
        return (this.options & Block) == 0;
    }

    public boolean isEmpty() {
        return (this.options & Void) != 0;
    }

    public boolean isSelfClosing() {
        int i = this.options;
        return ((SelfClose & i) == 0 && (i & Void) == 0) ? false : true;
    }

    public boolean isKnownTag() {
        return (this.options & Known) != 0;
    }

    public static boolean isKnownTag(String str) {
        return TagSet.HtmlTagSet.get(str, Parser.NamespaceHtml) != null;
    }

    public boolean preserveWhitespace() {
        return (this.options & PreserveWhitespace) != 0;
    }

    public boolean isFormSubmittable() {
        return (this.options & FormSubmittable) != 0;
    }

    void setSeenSelfClose() {
        this.options |= SeenSelfClose;
    }

    TokeniserState textState() {
        if (is(RcData)) {
            return TokeniserState.Rcdata;
        }
        if (is(Data)) {
            return TokeniserState.Rawtext;
        }
        return null;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Tag)) {
            return false;
        }
        Tag tag = (Tag) obj;
        return Objects.equals(this.tagName, tag.tagName) && Objects.equals(this.namespace, tag.namespace) && Objects.equals(this.normalName, tag.normalName) && this.options == tag.options;
    }

    public int hashCode() {
        return Objects.hash(this.tagName, this.namespace);
    }

    public String toString() {
        return this.tagName;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Tag clone() {
        try {
            return (Tag) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
