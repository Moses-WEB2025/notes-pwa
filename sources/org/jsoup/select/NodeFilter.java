package org.jsoup.select;

import org.jsoup.nodes.Node;

/* JADX INFO: loaded from: classes6.dex */
public interface NodeFilter {

    public enum FilterResult {
        CONTINUE,
        SKIP_CHILDREN,
        SKIP_ENTIRELY,
        REMOVE,
        STOP
    }

    FilterResult head(Node node, int i);

    default FilterResult tail(Node node, int i) {
        return FilterResult.CONTINUE;
    }

    default void traverse(Node node) {
        NodeTraversor.filter(this, node);
    }
}
