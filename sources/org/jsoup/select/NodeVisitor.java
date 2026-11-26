package org.jsoup.select;

import org.jsoup.nodes.Node;

/* JADX INFO: loaded from: classes6.dex */
@FunctionalInterface
public interface NodeVisitor {
    void head(Node node, int i);

    default void tail(Node node, int i) {
    }

    default void traverse(Node node) {
        NodeTraversor.traverse(this, node);
    }
}
