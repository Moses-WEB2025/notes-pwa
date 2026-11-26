package com.google.common.graph;

import com.google.errorprone.annotations.DoNotMock;
import java.util.Set;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@DoNotMock("Use GraphBuilder to create a real instance")
@ElementTypesAreNonnullByDefault
public interface Graph<N> extends BaseGraph<N> {
    Set<N> adjacentNodes(N node);

    boolean allowsSelfLoops();

    @Override // com.google.common.graph.BaseGraph
    int degree(N node);

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

    boolean isDirected();

    ElementOrder<N> nodeOrder();

    Set<N> nodes();

    @Override // com.google.common.graph.BaseGraph
    int outDegree(N node);

    @Override // 
    Set<N> predecessors(N node);

    @Override // 
    Set<N> successors(N node);
}
