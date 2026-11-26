package org.jsoup.nodes;

import defpackage.R2;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.jsoup.helper.Validate;
import org.jsoup.helper.W3CDom;
import org.jsoup.parser.HtmlTreeBuilder;
import org.jsoup.parser.Parser;

/* JADX INFO: loaded from: classes6.dex */
final class NodeUtils {
    NodeUtils() {
    }

    static Document.OutputSettings outputSettings(Node node) {
        Document documentOwnerDocument = node.ownerDocument();
        return documentOwnerDocument != null ? documentOwnerDocument.outputSettings() : new Document("").outputSettings();
    }

    static Parser parser(Node node) {
        Document documentOwnerDocument = node.ownerDocument();
        return documentOwnerDocument != null ? documentOwnerDocument.parser() : new Parser(new HtmlTreeBuilder());
    }

    static <T extends Node> List<T> selectXpath(String str, Element element, Class<T> cls) {
        Validate.notEmpty(str);
        Validate.notNull(element);
        Validate.notNull(cls);
        W3CDom w3CDomNamespaceAware = new W3CDom().namespaceAware(false);
        return w3CDomNamespaceAware.sourceNodes(w3CDomNamespaceAware.selectXpath(str, w3CDomNamespaceAware.contextNode(w3CDomNamespaceAware.fromJsoup(element))), cls);
    }

    static <T extends Node> Stream<T> stream(Node node, Class<T> cls) {
        return StreamSupport.stream(spliterator(new NodeIterator(node, cls)), false);
    }

    static <T extends Node> Spliterator<T> spliterator(Iterator<T> it) {
        return Spliterators.spliteratorUnknownSize(it, R2.attr.actionBarRemoveBlacklistIcon);
    }
}
