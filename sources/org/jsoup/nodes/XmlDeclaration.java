package org.jsoup.nodes;

import ch.qos.logback.classic.spi.CallerData;
import org.jsoup.internal.QuietAppendable;
import org.jsoup.internal.StringUtil;

/* JADX INFO: loaded from: classes6.dex */
public class XmlDeclaration extends LeafNode {
    private final boolean isDeclaration;

    @Override // org.jsoup.nodes.LeafNode, org.jsoup.nodes.Node
    void outerHtmlTail(QuietAppendable quietAppendable, Document.OutputSettings outputSettings) {
    }

    public XmlDeclaration(String str, boolean z) {
        super(str);
        this.isDeclaration = z;
    }

    @Override // org.jsoup.nodes.Node
    public String nodeName() {
        return "#declaration";
    }

    public String name() {
        return coreValue();
    }

    public String getWholeDeclaration() {
        StringBuilder sbBorrowBuilder = StringUtil.borrowBuilder();
        getWholeDeclaration(QuietAppendable.wrap(sbBorrowBuilder), new Document.OutputSettings());
        return StringUtil.releaseBuilder(sbBorrowBuilder).trim();
    }

    private void getWholeDeclaration(QuietAppendable quietAppendable, Document.OutputSettings outputSettings) {
        for (Attribute attribute : attributes()) {
            String key = attribute.getKey();
            String value = attribute.getValue();
            if (!key.equals(nodeName())) {
                quietAppendable.append(' ');
                quietAppendable.append(key);
                if (!value.isEmpty()) {
                    quietAppendable.append("=\"");
                    Entities.escape(quietAppendable, value, outputSettings, 2);
                    quietAppendable.append('\"');
                }
            }
        }
    }

    @Override // org.jsoup.nodes.Node
    void outerHtmlHead(QuietAppendable quietAppendable, Document.OutputSettings outputSettings) {
        QuietAppendable quietAppendableAppend = quietAppendable.append("<");
        boolean z = this.isDeclaration;
        String str = CallerData.NA;
        quietAppendableAppend.append(z ? "!" : CallerData.NA).append(coreValue());
        getWholeDeclaration(quietAppendable, outputSettings);
        if (this.isDeclaration) {
            str = "";
        }
        quietAppendable.append(str).append(">");
    }

    @Override // org.jsoup.nodes.Node
    public String toString() {
        return outerHtml();
    }

    @Override // org.jsoup.nodes.Node
    /* JADX INFO: renamed from: clone */
    public XmlDeclaration mo4587clone() {
        return (XmlDeclaration) super.mo4587clone();
    }
}
