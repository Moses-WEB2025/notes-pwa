package com.google.common.graph;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
public interface MutableGraph<N> extends Graph<N> {
    boolean addNode(N node);

    boolean putEdge(EndpointPair<N> endpoints);

    boolean putEdge(N nodeU, N nodeV);

    boolean removeEdge(EndpointPair<N> endpoints);

    boolean removeEdge(N nodeU, N nodeV);

    boolean removeNode(N node);
}
