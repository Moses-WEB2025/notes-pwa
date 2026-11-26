package com.google.common.graph;

import java.util.Set;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
public interface ValueGraph<N, V> extends BaseGraph<N> {
    @Override // com.google.common.graph.BaseGraph, com.google.common.graph.Graph
    Set<N> adjacentNodes(N node);

    @Override // com.google.common.graph.BaseGraph, com.google.common.graph.Graph
    boolean allowsSelfLoops();

    Graph<N> asGraph();

    @Override // com.google.common.graph.BaseGraph
    int degree(N node);

    @CheckForNull
    V edgeValueOrDefault(EndpointPair<N> endpoints, @CheckForNull V defaultValue);

    @CheckForNull
    V edgeValueOrDefault(N nodeU, N nodeV, @CheckForNull V defaultValue);

    @Override // com.google.common.graph.BaseGraph
    Set<EndpointPair<N>> edges();

    boolean equals(@CheckForNull Object object);

    @Override // com.google.common.graph.BaseGraph
    boolean hasEdgeConnecting(EndpointPair<N> endpoints);

    @Override // com.google.common.graph.BaseGraph
    boolean hasEdgeConnecting(N nodeU, N nodeV);

    int hashCode();

    @Override // com.google.common.graph.BaseGraph
    int inDegree(N node);

    @Override // com.google.common.graph.BaseGraph
    ElementOrder<N> incidentEdgeOrder();

    @Override // com.google.common.graph.BaseGraph
    Set<EndpointPair<N>> incidentEdges(N node);

    @Override // com.google.common.graph.BaseGraph, com.google.common.graph.Graph
    boolean isDirected();

    @Override // com.google.common.graph.BaseGraph, com.google.common.graph.Graph
    ElementOrder<N> nodeOrder();

    @Override // com.google.common.graph.BaseGraph, com.google.common.graph.Graph
    Set<N> nodes();

    @Override // com.google.common.graph.BaseGraph
    int outDegree(N node);

    @Override // com.google.common.graph.BaseGraph, com.google.common.graph.PredecessorsFunction, com.google.common.graph.Graph
    Set<N> predecessors(N node);

    @Override // com.google.common.graph.BaseGraph, com.google.common.graph.SuccessorsFunction, com.google.common.graph.Graph
    Set<N> successors(N node);
}
