package org.jsoup.nodes;

import org.jsoup.helper.Validate;
import org.jsoup.internal.QuietAppendable;
import org.jsoup.internal.StringUtil;

/* JADX INFO: loaded from: classes6.dex */
public class TextNode extends LeafNode {
    public TextNode(String str) {
        super(str);
    }

    @Override // org.jsoup.nodes.Node
    public String nodeName() {
        return "#text";
    }

    public String text() {
        return StringUtil.normaliseWhitespace(getWholeText());
    }

    public TextNode text(String str) {
        coreValue(str);
        return this;
    }

    public String getWholeText() {
        return coreValue();
    }

    public boolean isBlank() {
        return StringUtil.isBlank(coreValue());
    }

    public TextNode splitText(int i) {
        String strCoreValue = coreValue();
        Validate.isTrue(i >= 0, "Split offset must be not be negative");
        Validate.isTrue(i < strCoreValue.length(), "Split offset must not be greater than current text length");
        String strSubstring = strCoreValue.substring(0, i);
        String strSubstring2 = strCoreValue.substring(i);
        text(strSubstring);
        TextNode textNode = new TextNode(strSubstring2);
        if (this.parentNode != null) {
            this.parentNode.addChildren(siblingIndex() + 1, textNode);
        }
        return textNode;
    }

    @Override // org.jsoup.nodes.Node
    void outerHtmlHead(QuietAppendable quietAppendable, Document.OutputSettings outputSettings) {
        Entities.escape(quietAppendable, coreValue(), outputSettings, 1);
    }

    @Override // org.jsoup.nodes.Node
    public String toString() {
        return outerHtml();
    }

    @Override // org.jsoup.nodes.Node
    /* JADX INFO: renamed from: clone */
    public TextNode mo4587clone() {
        return (TextNode) super.mo4587clone();
    }

    public static TextNode createFromEncoded(String str) {
        return new TextNode(Entities.unescape(str));
    }

    static String normaliseWhitespace(String str) {
        return StringUtil.normaliseWhitespace(str);
    }

    static String stripLeadingWhitespace(String str) {
        return str.replaceFirst("^\\s+", "");
    }

    static boolean lastCharIsWhitespace(StringBuilder sb) {
        return sb.length() != 0 && sb.charAt(sb.length() - 1) == ' ';
    }
}
