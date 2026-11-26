package com.google.common.graph;

import java.util.Set;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
interface BaseGraph<N> extends SuccessorsFunction<N>, PredecessorsFunction<N> {
    Set<N> adjacentNodes(N node);

    boolean allowsSelfLoops();

    int degree(N node);

    Set<EndpointPair<N>> edges();

    boolean hasEdgeConnecting(EndpointPair<N> endpoints);

    boolean hasEdgeConnecting(N nodeU, N nodeV);

    int inDegree(N node);

    ElementOrder<N> incidentEdgeOrder();

    Set<EndpointPair<N>> incidentEdges(N node);

    boolean isDirected();

    ElementOrder<N> nodeOrder();

    Set<N> nodes();

    int outDegree(N node);

    @Override // com.google.common.graph.PredecessorsFunction, com.google.common.graph.Graph
    Set<N> predecessors(N node);

    @Override // com.google.common.graph.SuccessorsFunction, com.google.common.graph.Graph
    Set<N> successors(N node);
}
