package org.jsoup.nodes;

import org.apache.commons.text.lookup.StringLookupFactory;
import org.jsoup.internal.QuietAppendable;

/* JADX INFO: loaded from: classes6.dex */
public class DataNode extends LeafNode {
    public DataNode(String str) {
        super(str);
    }

    @Override // org.jsoup.nodes.Node
    public String nodeName() {
        return "#data";
    }

    public String getWholeData() {
        return coreValue();
    }

    public DataNode setWholeData(String str) {
        coreValue(str);
        return this;
    }

    @Override // org.jsoup.nodes.Node
    void outerHtmlHead(QuietAppendable quietAppendable, Document.OutputSettings outputSettings) {
        String wholeData = getWholeData();
        if (outputSettings.syntax() == Document.OutputSettings.Syntax.xml && !wholeData.contains("<![CDATA[")) {
            if (parentNameIs(StringLookupFactory.KEY_SCRIPT)) {
                quietAppendable.append("//<![CDATA[\n").append(wholeData).append("\n//]]>");
                return;
            } else if (parentNameIs("style")) {
                quietAppendable.append("/*<![CDATA[*/\n").append(wholeData).append("\n/*]]>*/");
                return;
            } else {
                quietAppendable.append("<![CDATA[").append(wholeData).append("]]>");
                return;
            }
        }
        quietAppendable.append(wholeData);
    }

    @Override // org.jsoup.nodes.Node
    /* JADX INFO: renamed from: clone */
    public DataNode mo4587clone() {
        return (DataNode) super.mo4587clone();
    }
}
