package org.jsoup.nodes;

import java.util.List;
import org.jsoup.helper.Validate;
import org.jsoup.internal.QuietAppendable;

/* JADX INFO: loaded from: classes6.dex */
public abstract class LeafNode extends Node {
    Object value;

    @Override // org.jsoup.nodes.Node
    public int childNodeSize() {
        return 0;
    }

    @Override // org.jsoup.nodes.Node
    protected void doSetBaseUri(String str) {
    }

    @Override // org.jsoup.nodes.Node
    public Node empty() {
        return this;
    }

    @Override // org.jsoup.nodes.Node
    void outerHtmlTail(QuietAppendable quietAppendable, Document.OutputSettings outputSettings) {
    }

    public LeafNode() {
        this.value = "";
    }

    protected LeafNode(String str) {
        Validate.notNull(str);
        this.value = str;
    }

    @Override // org.jsoup.nodes.Node
    protected final boolean hasAttributes() {
        return this.value instanceof Attributes;
    }

    @Override // org.jsoup.nodes.Node
    public final Attributes attributes() {
        ensureAttributes();
        return (Attributes) this.value;
    }

    private void ensureAttributes() {
        if (hasAttributes()) {
            return;
        }
        String str = (String) this.value;
        Attributes attributes = new Attributes();
        this.value = attributes;
        attributes.put(nodeName(), str);
    }

    String coreValue() {
        return attr(nodeName());
    }

    @Override // org.jsoup.nodes.Node
    public Element parent() {
        return this.parentNode;
    }

    @Override // org.jsoup.nodes.Node
    public String nodeValue() {
        return coreValue();
    }

    void coreValue(String str) {
        attr(nodeName(), str);
    }

    @Override // org.jsoup.nodes.Node
    public String attr(String str) {
        if (hasAttributes()) {
            return super.attr(str);
        }
        return nodeName().equals(str) ? (String) this.value : "";
    }

    @Override // org.jsoup.nodes.Node
    public Node attr(String str, String str2) {
        if (!hasAttributes() && str.equals(nodeName())) {
            this.value = str2;
            return this;
        }
        ensureAttributes();
        super.attr(str, str2);
        return this;
    }

    @Override // org.jsoup.nodes.Node
    public boolean hasAttr(String str) {
        ensureAttributes();
        return super.hasAttr(str);
    }

    @Override // org.jsoup.nodes.Node
    public Node removeAttr(String str) {
        ensureAttributes();
        return super.removeAttr(str);
    }

    @Override // org.jsoup.nodes.Node
    public String absUrl(String str) {
        ensureAttributes();
        return super.absUrl(str);
    }

    @Override // org.jsoup.nodes.Node
    public String baseUri() {
        return this.parentNode != null ? this.parentNode.baseUri() : "";
    }

    @Override // org.jsoup.nodes.Node
    protected List<Node> ensureChildNodes() {
        return EmptyNodes;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jsoup.nodes.Node
    public LeafNode doClone(Node node) {
        LeafNode leafNode = (LeafNode) super.doClone(node);
        if (hasAttributes()) {
            leafNode.value = ((Attributes) this.value).clone();
        }
        return leafNode;
    }
}
