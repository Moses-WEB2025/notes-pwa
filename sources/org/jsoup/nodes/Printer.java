package org.jsoup.nodes;

import org.jsoup.internal.QuietAppendable;
import org.jsoup.internal.StringUtil;
import org.jsoup.parser.Tag;
import org.jsoup.select.NodeVisitor;

/* JADX INFO: loaded from: classes6.dex */
class Printer implements NodeVisitor {
    final QuietAppendable accum;
    final Node root;
    final Document.OutputSettings settings;

    Printer(Node node, QuietAppendable quietAppendable, Document.OutputSettings outputSettings) {
        this.root = node;
        this.accum = quietAppendable;
        this.settings = outputSettings;
    }

    void addHead(Element element, int i) {
        element.outerHtmlHead(this.accum, this.settings);
    }

    void addTail(Element element, int i) {
        element.outerHtmlTail(this.accum, this.settings);
    }

    void addText(TextNode textNode, int i, int i2) {
        Entities.escape(this.accum, textNode.coreValue(), this.settings, i | 1);
    }

    void addNode(LeafNode leafNode, int i) {
        leafNode.outerHtmlHead(this.accum, this.settings);
    }

    void indent(int i) {
        this.accum.append('\n').append(StringUtil.padding(i * this.settings.indentAmount(), this.settings.maxPaddingWidth()));
    }

    @Override // org.jsoup.select.NodeVisitor
    public void head(Node node, int i) {
        if (node.getClass() == TextNode.class) {
            addText((TextNode) node, 0, i);
        } else if (node instanceof Element) {
            addHead((Element) node, i);
        } else {
            addNode((LeafNode) node, i);
        }
    }

    @Override // org.jsoup.select.NodeVisitor
    public void tail(Node node, int i) {
        if (node instanceof Element) {
            addTail((Element) node, i);
        }
    }

    static class Pretty extends Printer {
        private static final int maxScan = 5;
        boolean preserveWhitespace;

        Pretty(Node node, QuietAppendable quietAppendable, Document.OutputSettings outputSettings) {
            super(node, quietAppendable, outputSettings);
            this.preserveWhitespace = false;
            while (node != null) {
                if (!tagIs(Tag.PreserveWhitespace, node)) {
                    node = node.parentNode();
                } else {
                    this.preserveWhitespace = true;
                    return;
                }
            }
        }

        @Override // org.jsoup.nodes.Printer
        void addHead(Element element, int i) {
            if (shouldIndent(element)) {
                indent(i);
            }
            super.addHead(element, i);
            if (tagIs(Tag.PreserveWhitespace, element)) {
                this.preserveWhitespace = true;
            }
        }

        @Override // org.jsoup.nodes.Printer
        void addTail(Element element, int i) {
            if (shouldIndent(nextNonBlank(element.firstChild()))) {
                indent(i);
            }
            super.addTail(element, i);
            if (this.preserveWhitespace && element.tag.is(Tag.PreserveWhitespace)) {
                do {
                    element = element.parent();
                    if (element == null) {
                        this.preserveWhitespace = false;
                        return;
                    }
                } while (!element.tag().preserveWhitespace());
            }
        }

        @Override // org.jsoup.nodes.Printer
        void addNode(LeafNode leafNode, int i) {
            if (shouldIndent(leafNode)) {
                indent(i);
            }
            super.addNode(leafNode, i);
        }

        @Override // org.jsoup.nodes.Printer
        void addText(TextNode textNode, int i, int i2) {
            if (!this.preserveWhitespace) {
                i = textTrim(textNode, i | 4);
                if (!textNode.isBlank() && isBlockEl(textNode.parentNode) && shouldIndent(textNode)) {
                    indent(i2);
                }
            }
            super.addText(textNode, i, i2);
        }

        int textTrim(TextNode textNode, int i) {
            if (!isBlockEl(textNode.parentNode)) {
                return i;
            }
            Node nodePreviousSibling = textNode.previousSibling();
            Node nodeNextSibling = textNode.nextSibling();
            if ((!(nodePreviousSibling instanceof Element) || isBlockEl(nodePreviousSibling)) && (nodePreviousSibling == null || (!(nodePreviousSibling instanceof TextNode) && shouldIndent(nodePreviousSibling)))) {
                i |= 8;
            }
            return (nodeNextSibling == null || (!(nodeNextSibling instanceof TextNode) && shouldIndent(nodeNextSibling))) ? i | 16 : i;
        }

        boolean shouldIndent(Node node) {
            if (node != null && node != this.root && !this.preserveWhitespace && !isBlankText(node)) {
                if (isBlockEl(node)) {
                    return true;
                }
                Node nodePreviousNonblank = previousNonblank(node);
                if (isBlockEl(nodePreviousNonblank)) {
                    return true;
                }
                Element element = node.parentNode;
                if (isBlockEl(element) && !element.tag().is(Tag.InlineContainer) && hasNonTextNodes(element)) {
                    return nodePreviousNonblank == null || (!(nodePreviousNonblank instanceof TextNode) && (isBlockEl(nodePreviousNonblank) || !(nodePreviousNonblank instanceof Element)));
                }
            }
            return false;
        }

        boolean isBlockEl(Node node) {
            if (node == null || !(node instanceof Element)) {
                return false;
            }
            Element element = (Element) node;
            return element.isBlock() || (!element.tag.isKnownTag() && ((element.parentNode instanceof Document) || hasChildBlocks(element)));
        }

        static boolean hasChildBlocks(Element element) {
            Element elementFirstElementChild = element.firstElementChild();
            for (int i = 0; i < 5 && elementFirstElementChild != null; i++) {
                if (elementFirstElementChild.isBlock() || !elementFirstElementChild.tag.isKnownTag()) {
                    return true;
                }
                elementFirstElementChild = elementFirstElementChild.nextElementSibling();
            }
            return false;
        }

        static boolean hasNonTextNodes(Element element) {
            Node nodeFirstChild = element.firstChild();
            for (int i = 0; i < 5 && nodeFirstChild != null; i++) {
                if (!(nodeFirstChild instanceof TextNode)) {
                    return true;
                }
                nodeFirstChild = nodeFirstChild.nextSibling();
            }
            return false;
        }

        static Node previousNonblank(Node node) {
            Node nodePreviousSibling = node.previousSibling();
            while (isBlankText(nodePreviousSibling)) {
                nodePreviousSibling = nodePreviousSibling.previousSibling();
            }
            return nodePreviousSibling;
        }

        static Node nextNonBlank(Node node) {
            while (isBlankText(node)) {
                node = node.nextSibling();
            }
            return node;
        }

        static boolean isBlankText(Node node) {
            return (node instanceof TextNode) && ((TextNode) node).isBlank();
        }

        static boolean tagIs(int i, Node node) {
            return (node instanceof Element) && ((Element) node).tag.is(i);
        }
    }

    static class Outline extends Pretty {
        @Override // org.jsoup.nodes.Printer.Pretty
        boolean isBlockEl(Node node) {
            return node != null;
        }

        Outline(Node node, QuietAppendable quietAppendable, Document.OutputSettings outputSettings) {
            super(node, quietAppendable, outputSettings);
        }

        @Override // org.jsoup.nodes.Printer.Pretty
        boolean shouldIndent(Node node) {
            if (node == null || node == this.root || this.preserveWhitespace || isBlankText(node)) {
                return false;
            }
            return ((node instanceof TextNode) && node.previousSibling() == null && node.nextSibling() == null) ? false : true;
        }
    }

    static Printer printerFor(Node node, QuietAppendable quietAppendable) {
        Document.OutputSettings outputSettings = NodeUtils.outputSettings(node);
        if (outputSettings.outline()) {
            return new Outline(node, quietAppendable, outputSettings);
        }
        return outputSettings.prettyPrint() ? new Pretty(node, quietAppendable, outputSettings) : new Printer(node, quietAppendable, outputSettings);
    }
}
