package com.google.common.graph;

import java.util.Iterator;
import java.util.Set;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
interface GraphConnections<N, V> {
    void addPredecessor(N node, V value);

    @CheckForNull
    V addSuccessor(N node, V value);

    Set<N> adjacentNodes();

    Iterator<EndpointPair<N>> incidentEdgeIterator(N thisNode);

    Set<N> predecessors();

    void removePredecessor(N node);

    @CheckForNull
    V removeSuccessor(N node);

    Set<N> successors();

    @CheckForNull
    V value(N node);
}
