package org.jsoup.nodes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.jsoup.helper.Validate;
import org.jsoup.internal.QuietAppendable;
import org.jsoup.internal.StringUtil;
import org.jsoup.parser.ParseSettings;
import org.jsoup.select.NodeFilter;
import org.jsoup.select.NodeVisitor;

/* JADX INFO: loaded from: classes6.dex */
public abstract class Node implements Cloneable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final List<Node> EmptyNodes = Collections.emptyList();
    static final String EmptyString = "";
    Element parentNode;
    int siblingIndex;

    public abstract Attributes attributes();

    public abstract String baseUri();

    public abstract int childNodeSize();

    protected abstract void doSetBaseUri(String str);

    public abstract Node empty();

    protected abstract List<Node> ensureChildNodes();

    public boolean equals(Object obj) {
        return this == obj;
    }

    protected abstract boolean hasAttributes();

    public abstract String nodeName();

    abstract void outerHtmlHead(QuietAppendable quietAppendable, Document.OutputSettings outputSettings);

    abstract void outerHtmlTail(QuietAppendable quietAppendable, Document.OutputSettings outputSettings);

    protected Node() {
    }

    public String normalName() {
        return nodeName();
    }

    public String nodeValue() {
        return "";
    }

    public boolean nameIs(String str) {
        return normalName().equals(str);
    }

    public boolean parentNameIs(String str) {
        Element element = this.parentNode;
        return element != null && element.normalName().equals(str);
    }

    public boolean parentElementIs(String str, String str2) {
        Element element = this.parentNode;
        return element != null && (element instanceof Element) && element.elementIs(str, str2);
    }

    public boolean hasParent() {
        return this.parentNode != null;
    }

    public String attr(String str) {
        Validate.notNull(str);
        if (!hasAttributes()) {
            return "";
        }
        String ignoreCase = attributes().getIgnoreCase(str);
        if (ignoreCase.length() > 0) {
            return ignoreCase;
        }
        return str.startsWith("abs:") ? absUrl(str.substring("abs:".length())) : "";
    }

    public int attributesSize() {
        if (hasAttributes()) {
            return attributes().size();
        }
        return 0;
    }

    public Node attr(String str, String str2) {
        Document documentOwnerDocument = ownerDocument();
        attributes().putIgnoreCase((documentOwnerDocument != null ? documentOwnerDocument.parser().settings() : ParseSettings.htmlDefault).normalizeAttribute(str), str2);
        return this;
    }

    public boolean hasAttr(String str) {
        Validate.notNull(str);
        if (!hasAttributes()) {
            return false;
        }
        if (str.startsWith("abs:")) {
            String strSubstring = str.substring("abs:".length());
            if (attributes().hasKeyIgnoreCase(strSubstring) && !absUrl(strSubstring).isEmpty()) {
                return true;
            }
        }
        return attributes().hasKeyIgnoreCase(str);
    }

    public Node removeAttr(String str) {
        Validate.notNull(str);
        if (hasAttributes()) {
            attributes().removeIgnoreCase(str);
        }
        return this;
    }

    public Node clearAttributes() {
        if (hasAttributes()) {
            Iterator<Attribute> it = attributes().iterator();
            while (it.hasNext()) {
                it.next();
                it.remove();
            }
        }
        return this;
    }

    public void setBaseUri(String str) {
        Validate.notNull(str);
        doSetBaseUri(str);
    }

    public String absUrl(String str) {
        Validate.notEmpty(str);
        if (!hasAttributes() || !attributes().hasKeyIgnoreCase(str)) {
            return "";
        }
        return StringUtil.resolve(baseUri(), attributes().getIgnoreCase(str));
    }

    public Node childNode(int i) {
        return ensureChildNodes().get(i);
    }

    public List<Node> childNodes() {
        if (childNodeSize() == 0) {
            return EmptyNodes;
        }
        List<Node> listEnsureChildNodes = ensureChildNodes();
        ArrayList arrayList = new ArrayList(listEnsureChildNodes.size());
        arrayList.addAll(listEnsureChildNodes);
        return Collections.unmodifiableList(arrayList);
    }

    public List<Node> childNodesCopy() {
        List<Node> listEnsureChildNodes = ensureChildNodes();
        ArrayList arrayList = new ArrayList(listEnsureChildNodes.size());
        Iterator<Node> it = listEnsureChildNodes.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().mo4587clone());
        }
        return arrayList;
    }

    protected Node[] childNodesAsArray() {
        return (Node[]) ensureChildNodes().toArray(new Node[0]);
    }

    public Node parent() {
        return this.parentNode;
    }

    public Element parentElement() {
        return this.parentNode;
    }

    public final Node parentNode() {
        return this.parentNode;
    }

    public Node root() {
        while (true) {
            Element element = this.parentNode;
            if (element == null) {
                return this;
            }
            this = element;
        }
    }

    public Document ownerDocument() {
        Node nodeRoot = root();
        if (nodeRoot instanceof Document) {
            return (Document) nodeRoot;
        }
        return null;
    }

    public void remove() {
        Element element = this.parentNode;
        if (element != null) {
            element.removeChild(this);
        }
    }

    public Node before(String str) {
        addSiblingHtml(this.siblingIndex, str);
        return this;
    }

    public Node before(Node node) {
        Validate.notNull(node);
        Validate.notNull(this.parentNode);
        if (node.parentNode == this.parentNode) {
            node.remove();
        }
        this.parentNode.addChildren(this.siblingIndex, node);
        return this;
    }

    public Node after(String str) {
        addSiblingHtml(this.siblingIndex + 1, str);
        return this;
    }

    public Node after(Node node) {
        Validate.notNull(node);
        Validate.notNull(this.parentNode);
        if (node.parentNode == this.parentNode) {
            node.remove();
        }
        this.parentNode.addChildren(this.siblingIndex + 1, node);
        return this;
    }

    private void addSiblingHtml(int i, String str) {
        Validate.notNull(str);
        Validate.notNull(this.parentNode);
        Element element = this.parentNode;
        if (!(element instanceof Element)) {
            element = null;
        }
        this.parentNode.addChildren(i, (Node[]) NodeUtils.parser(this).parseFragmentInput(str, element, baseUri()).toArray(new Node[0]));
    }

    public Node wrap(String str) {
        Validate.notEmpty(str);
        Element element = this.parentNode;
        if (element == null || !(element instanceof Element)) {
            element = this instanceof Element ? (Element) this : null;
        }
        List<Node> fragmentInput = NodeUtils.parser(this).parseFragmentInput(str, element, baseUri());
        Node node = fragmentInput.get(0);
        if (node instanceof Element) {
            Element element2 = (Element) node;
            Element deepChild = getDeepChild(element2);
            Element element3 = this.parentNode;
            if (element3 != null) {
                element3.replaceChild(this, element2);
            }
            deepChild.addChildren(this);
            if (fragmentInput.size() > 0) {
                for (int i = 0; i < fragmentInput.size(); i++) {
                    Node node2 = fragmentInput.get(i);
                    if (element2 != node2) {
                        Element element4 = node2.parentNode;
                        if (element4 != null) {
                            element4.removeChild(node2);
                        }
                        element2.after(node2);
                    }
                }
            }
        }
        return this;
    }

    public Node unwrap() {
        Validate.notNull(this.parentNode);
        Node nodeFirstChild = firstChild();
        this.parentNode.addChildren(this.siblingIndex, childNodesAsArray());
        remove();
        return nodeFirstChild;
    }

    private static Element getDeepChild(Element element) {
        Element elementFirstElementChild = element.firstElementChild();
        while (true) {
            Element element2 = elementFirstElementChild;
            Element element3 = element;
            element = element2;
            if (element == null) {
                return element3;
            }
            elementFirstElementChild = element.firstElementChild();
        }
    }

    public void replaceWith(Node node) {
        Validate.notNull(node);
        if (this.parentNode == null) {
            this.parentNode = node.parentNode;
        }
        Validate.notNull(this.parentNode);
        this.parentNode.replaceChild(this, node);
    }

    protected void setParentNode(Node node) {
        Validate.notNull(node);
        Element element = this.parentNode;
        if (element != null) {
            element.removeChild(this);
        }
        this.parentNode = (Element) node;
    }

    protected void replaceChild(Node node, Node node2) {
        Validate.isTrue(node.parentNode == this);
        Validate.notNull(node2);
        if (node == node2) {
            return;
        }
        Element element = node2.parentNode;
        if (element != null) {
            element.removeChild(node2);
        }
        int i = node.siblingIndex;
        ensureChildNodes().set(i, node2);
        node2.parentNode = (Element) this;
        node2.setSiblingIndex(i);
        node.parentNode = null;
    }

    protected void removeChild(Node node) {
        Validate.isTrue(node.parentNode == this);
        int i = node.siblingIndex;
        ensureChildNodes().remove(i);
        reindexChildren(i);
        node.parentNode = null;
    }

    protected void addChildren(Node... nodeArr) {
        List<Node> listEnsureChildNodes = ensureChildNodes();
        for (Node node : nodeArr) {
            reparentChild(node);
            listEnsureChildNodes.add(node);
            node.setSiblingIndex(listEnsureChildNodes.size() - 1);
        }
    }

    protected void addChildren(int i, Node... nodeArr) {
        Validate.notNull(nodeArr);
        if (nodeArr.length == 0) {
            return;
        }
        List<Node> listEnsureChildNodes = ensureChildNodes();
        Node nodeParent = nodeArr[0].parent();
        if (nodeParent != null && nodeParent.childNodeSize() == nodeArr.length) {
            List<Node> listEnsureChildNodes2 = nodeParent.ensureChildNodes();
            int length = nodeArr.length;
            while (true) {
                int i2 = length - 1;
                if (length > 0) {
                    if (nodeArr[i2] != listEnsureChildNodes2.get(i2)) {
                        break;
                    } else {
                        length = i2;
                    }
                } else {
                    boolean z = childNodeSize() == 0;
                    nodeParent.empty();
                    listEnsureChildNodes.addAll(i, Arrays.asList(nodeArr));
                    int length2 = nodeArr.length;
                    while (true) {
                        int i3 = length2 - 1;
                        if (length2 <= 0) {
                            break;
                        }
                        nodeArr[i3].parentNode = (Element) this;
                        length2 = i3;
                    }
                    if (z && nodeArr[0].siblingIndex == 0) {
                        return;
                    }
                    reindexChildren(i);
                    return;
                }
            }
        }
        Validate.noNullElements(nodeArr);
        for (Node node : nodeArr) {
            reparentChild(node);
        }
        listEnsureChildNodes.addAll(i, Arrays.asList(nodeArr));
        reindexChildren(i);
    }

    protected void reparentChild(Node node) {
        node.setParentNode(this);
    }

    private void reindexChildren(int i) {
        int iChildNodeSize = childNodeSize();
        if (iChildNodeSize == 0) {
            return;
        }
        List<Node> listEnsureChildNodes = ensureChildNodes();
        while (i < iChildNodeSize) {
            listEnsureChildNodes.get(i).setSiblingIndex(i);
            i++;
        }
    }

    public List<Node> siblingNodes() {
        Element element = this.parentNode;
        if (element == null) {
            return Collections.emptyList();
        }
        List<Node> listEnsureChildNodes = element.ensureChildNodes();
        ArrayList arrayList = new ArrayList(listEnsureChildNodes.size() - 1);
        for (Node node : listEnsureChildNodes) {
            if (node != this) {
                arrayList.add(node);
            }
        }
        return arrayList;
    }

    public Node nextSibling() {
        Element element = this.parentNode;
        if (element == null) {
            return null;
        }
        List<Node> listEnsureChildNodes = element.ensureChildNodes();
        int i = this.siblingIndex + 1;
        if (listEnsureChildNodes.size() > i) {
            return listEnsureChildNodes.get(i);
        }
        return null;
    }

    public Node previousSibling() {
        Element element = this.parentNode;
        if (element != null && this.siblingIndex > 0) {
            return element.ensureChildNodes().get(this.siblingIndex - 1);
        }
        return null;
    }

    public int siblingIndex() {
        return this.siblingIndex;
    }

    protected void setSiblingIndex(int i) {
        this.siblingIndex = i;
    }

    public Node firstChild() {
        if (childNodeSize() == 0) {
            return null;
        }
        return ensureChildNodes().get(0);
    }

    public Node lastChild() {
        int iChildNodeSize = childNodeSize();
        if (iChildNodeSize == 0) {
            return null;
        }
        return ensureChildNodes().get(iChildNodeSize - 1);
    }

    public Node firstSibling() {
        Element element = this.parentNode;
        return element != null ? element.firstChild() : this;
    }

    public Node lastSibling() {
        Element element = this.parentNode;
        return element != null ? element.lastChild() : this;
    }

    public Element nextElementSibling() {
        do {
            this = this.nextSibling();
            if (this == null) {
                return null;
            }
        } while (!(this instanceof Element));
        return (Element) this;
    }

    public Element previousElementSibling() {
        do {
            this = this.previousSibling();
            if (this == null) {
                return null;
            }
        } while (!(this instanceof Element));
        return (Element) this;
    }

    public Node traverse(NodeVisitor nodeVisitor) {
        Validate.notNull(nodeVisitor);
        nodeVisitor.traverse(this);
        return this;
    }

    public Node forEachNode(Consumer<? super Node> consumer) {
        Validate.notNull(consumer);
        nodeStream().forEach(consumer);
        return this;
    }

    public Node filter(NodeFilter nodeFilter) {
        Validate.notNull(nodeFilter);
        nodeFilter.traverse(this);
        return this;
    }

    public Stream<Node> nodeStream() {
        return NodeUtils.stream(this, Node.class);
    }

    public <T extends Node> Stream<T> nodeStream(Class<T> cls) {
        return NodeUtils.stream(this, cls);
    }

    public String outerHtml() {
        StringBuilder sbBorrowBuilder = StringUtil.borrowBuilder();
        outerHtml(QuietAppendable.wrap(sbBorrowBuilder));
        return StringUtil.releaseBuilder(sbBorrowBuilder);
    }

    protected void outerHtml(Appendable appendable) {
        outerHtml(QuietAppendable.wrap(appendable));
    }

    protected void outerHtml(QuietAppendable quietAppendable) {
        Printer.printerFor(this, quietAppendable).traverse(this);
    }

    public <T extends Appendable> T html(T t) {
        outerHtml(t);
        return t;
    }

    public Range sourceRange() {
        return Range.of(this, true);
    }

    public String toString() {
        return outerHtml();
    }

    @Deprecated
    protected void indent(Appendable appendable, int i, Document.OutputSettings outputSettings) throws IOException {
        appendable.append('\n').append(StringUtil.padding(i * outputSettings.indentAmount(), outputSettings.maxPaddingWidth()));
    }

    public int hashCode() {
        return super.hashCode();
    }

    public boolean hasSameValue(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return outerHtml().equals(((Node) obj).outerHtml());
    }

    @Override // 
    /* JADX INFO: renamed from: clone */
    public Node mo4587clone() {
        Node nodeDoClone = doClone(null);
        LinkedList linkedList = new LinkedList();
        linkedList.add(nodeDoClone);
        while (!linkedList.isEmpty()) {
            Node node = (Node) linkedList.remove();
            int iChildNodeSize = node.childNodeSize();
            for (int i = 0; i < iChildNodeSize; i++) {
                List<Node> listEnsureChildNodes = node.ensureChildNodes();
                Node nodeDoClone2 = listEnsureChildNodes.get(i).doClone(node);
                listEnsureChildNodes.set(i, nodeDoClone2);
                linkedList.add(nodeDoClone2);
            }
        }
        return nodeDoClone;
    }

    public Node shallowClone() {
        return doClone(null);
    }

    protected Node doClone(Node node) {
        Document documentOwnerDocument;
        try {
            Node node2 = (Node) super.clone();
            node2.parentNode = (Element) node;
            node2.siblingIndex = node == null ? 0 : this.siblingIndex;
            if (node == null && !(this instanceof Document) && (documentOwnerDocument = ownerDocument()) != null) {
                Document documentShallowClone = documentOwnerDocument.shallowClone();
                node2.parentNode = documentShallowClone;
                documentShallowClone.ensureChildNodes().add(node2);
            }
            return node2;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
