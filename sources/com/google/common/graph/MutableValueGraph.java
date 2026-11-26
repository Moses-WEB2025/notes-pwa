package com.google.common.graph;

import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
public interface MutableValueGraph<N, V> extends ValueGraph<N, V> {
    boolean addNode(N node);

    @CheckForNull
    V putEdgeValue(EndpointPair<N> endpoints, V value);

    @CheckForNull
    V putEdgeValue(N nodeU, N nodeV, V value);

    @CheckForNull
    V removeEdge(EndpointPair<N> endpoints);

    @CheckForNull
    V removeEdge(N nodeU, N nodeV);

    boolean removeNode(N node);
}
