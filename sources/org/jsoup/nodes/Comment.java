package org.jsoup.nodes;

import ch.qos.logback.classic.spi.CallerData;
import java.util.List;
import org.jsoup.internal.QuietAppendable;
import org.jsoup.parser.Parser;

/* JADX INFO: loaded from: classes6.dex */
public class Comment extends LeafNode {
    public Comment(String str) {
        super(str);
    }

    @Override // org.jsoup.nodes.Node
    public String nodeName() {
        return "#comment";
    }

    public String getData() {
        return coreValue();
    }

    public Comment setData(String str) {
        coreValue(str);
        return this;
    }

    @Override // org.jsoup.nodes.Node
    void outerHtmlHead(QuietAppendable quietAppendable, Document.OutputSettings outputSettings) {
        quietAppendable.append("<!--").append(getData()).append("-->");
    }

    @Override // org.jsoup.nodes.Node
    /* JADX INFO: renamed from: clone */
    public Comment mo4587clone() {
        return (Comment) super.mo4587clone();
    }

    public boolean isXmlDeclaration() {
        return isXmlDeclarationData(getData());
    }

    private static boolean isXmlDeclarationData(String str) {
        if (str.length() > 1) {
            return str.startsWith("!") || str.startsWith(CallerData.NA);
        }
        return false;
    }

    public XmlDeclaration asXmlDeclaration() {
        List<Node> fragmentInput = Parser.xmlParser().parseFragmentInput("<" + getData() + ">", (Element) null, "");
        if (fragmentInput.isEmpty() || !(fragmentInput.get(0) instanceof XmlDeclaration)) {
            return null;
        }
        return (XmlDeclaration) fragmentInput.get(0);
    }
}
