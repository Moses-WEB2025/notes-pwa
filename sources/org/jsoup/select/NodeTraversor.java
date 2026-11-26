package org.jsoup.select;

import java.util.Iterator;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

/* JADX INFO: loaded from: classes6.dex */
public class NodeTraversor {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    public static void traverse(NodeVisitor nodeVisitor, Node node) {
        Validate.notNull(nodeVisitor);
        Validate.notNull(node);
        Node nodeChildNode = node;
        int i = 0;
        while (nodeChildNode != null) {
            Node nodeParentNode = nodeChildNode.parentNode();
            int iChildNodeSize = nodeParentNode != null ? nodeParentNode.childNodeSize() : 0;
            Node nodeNextSibling = nodeChildNode.nextSibling();
            nodeVisitor.head(nodeChildNode, i);
            if (nodeParentNode != null && !nodeChildNode.hasParent()) {
                if (iChildNodeSize == nodeParentNode.childNodeSize()) {
                    nodeChildNode = nodeParentNode.childNode(nodeChildNode.siblingIndex());
                } else if (nodeNextSibling == null) {
                    i--;
                    nodeChildNode = nodeParentNode;
                } else {
                    nodeChildNode = nodeNextSibling;
                }
            }
            if (nodeChildNode.childNodeSize() > 0) {
                nodeChildNode = nodeChildNode.childNode(0);
                i++;
            } else {
                while (nodeChildNode.nextSibling() == null && i > 0) {
                    nodeVisitor.tail(nodeChildNode, i);
                    nodeChildNode = nodeChildNode.parentNode();
                    i--;
                }
                nodeVisitor.tail(nodeChildNode, i);
                if (nodeChildNode == node) {
                    return;
                } else {
                    nodeChildNode = nodeChildNode.nextSibling();
                }
            }
        }
    }

    public static void traverse(NodeVisitor nodeVisitor, Elements elements) {
        Validate.notNull(nodeVisitor);
        Validate.notNull(elements);
        Iterator it = elements.iterator();
        while (it.hasNext()) {
            traverse(nodeVisitor, (Element) it.next());
        }
    }

    public static NodeFilter.FilterResult filter(NodeFilter nodeFilter, Node node) {
        Node nodeChildNode = node;
        int i = 0;
        while (nodeChildNode != null) {
            NodeFilter.FilterResult filterResultHead = nodeFilter.head(nodeChildNode, i);
            if (filterResultHead == NodeFilter.FilterResult.STOP) {
                return filterResultHead;
            }
            if (filterResultHead == NodeFilter.FilterResult.CONTINUE && nodeChildNode.childNodeSize() > 0) {
                nodeChildNode = nodeChildNode.childNode(0);
                i++;
            } else {
                while (nodeChildNode.nextSibling() == null && i > 0) {
                    if ((filterResultHead == NodeFilter.FilterResult.CONTINUE || filterResultHead == NodeFilter.FilterResult.SKIP_CHILDREN) && (filterResultHead = nodeFilter.tail(nodeChildNode, i)) == NodeFilter.FilterResult.STOP) {
                        return filterResultHead;
                    }
                    Node nodeParentNode = nodeChildNode.parentNode();
                    i--;
                    if (filterResultHead == NodeFilter.FilterResult.REMOVE) {
                        nodeChildNode.remove();
                    }
                    filterResultHead = NodeFilter.FilterResult.CONTINUE;
                    nodeChildNode = nodeParentNode;
                }
                if ((filterResultHead == NodeFilter.FilterResult.CONTINUE || filterResultHead == NodeFilter.FilterResult.SKIP_CHILDREN) && (filterResultHead = nodeFilter.tail(nodeChildNode, i)) == NodeFilter.FilterResult.STOP) {
                    return filterResultHead;
                }
                if (nodeChildNode == node) {
                    return filterResultHead;
                }
                Node nodeNextSibling = nodeChildNode.nextSibling();
                if (filterResultHead == NodeFilter.FilterResult.REMOVE) {
                    nodeChildNode.remove();
                }
                nodeChildNode = nodeNextSibling;
            }
        }
        return NodeFilter.FilterResult.CONTINUE;
    }

    public static void filter(NodeFilter nodeFilter, Elements elements) {
        Validate.notNull(nodeFilter);
        Validate.notNull(elements);
        Iterator it = elements.iterator();
        while (it.hasNext() && filter(nodeFilter, (Element) it.next()) != NodeFilter.FilterResult.STOP) {
        }
    }
}
