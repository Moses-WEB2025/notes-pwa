package org.jsoup.nodes;

import org.jsoup.internal.QuietAppendable;

/* JADX INFO: loaded from: classes6.dex */
public class CDataNode extends TextNode {
    public CDataNode(String str) {
        super(str);
    }

    @Override // org.jsoup.nodes.TextNode, org.jsoup.nodes.Node
    public String nodeName() {
        return "#cdata";
    }

    @Override // org.jsoup.nodes.TextNode
    public String text() {
        return getWholeText();
    }

    @Override // org.jsoup.nodes.TextNode, org.jsoup.nodes.Node
    void outerHtmlHead(QuietAppendable quietAppendable, Document.OutputSettings outputSettings) {
        quietAppendable.append("<![CDATA[").append(getWholeText()).append("]]>");
    }

    @Override // org.jsoup.nodes.TextNode, org.jsoup.nodes.Node
    /* JADX INFO: renamed from: clone */
    public CDataNode mo4587clone() {
        return (CDataNode) super.mo4587clone();
    }
}
