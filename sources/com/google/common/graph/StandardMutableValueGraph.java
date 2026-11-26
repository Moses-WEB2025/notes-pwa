package com.google.common.graph;

import com.google.common.base.Preconditions;
import java.util.Iterator;
import java.util.Objects;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
final class StandardMutableValueGraph<N, V> extends StandardValueGraph<N, V> implements MutableValueGraph<N, V> {
    private final ElementOrder<N> incidentEdgeOrder;

    StandardMutableValueGraph(AbstractGraphBuilder<? super N> abstractGraphBuilder) {
        super(abstractGraphBuilder);
        this.incidentEdgeOrder = (ElementOrder<N>) abstractGraphBuilder.incidentEdgeOrder.cast();
    }

    @Override // com.google.common.graph.AbstractValueGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph
    public ElementOrder<N> incidentEdgeOrder() {
        return this.incidentEdgeOrder;
    }

    @Override // com.google.common.graph.MutableValueGraph
    public boolean addNode(N node) {
        Preconditions.checkNotNull(node, "node");
        if (containsNode(node)) {
            return false;
        }
        addNodeInternal(node);
        return true;
    }

    private GraphConnections<N, V> addNodeInternal(N node) {
        GraphConnections<N, V> graphConnectionsNewConnections = newConnections();
        Preconditions.checkState(this.nodeConnections.put(node, graphConnectionsNewConnections) == null);
        return graphConnectionsNewConnections;
    }

    @Override // com.google.common.graph.MutableValueGraph
    @CheckForNull
    public V putEdgeValue(N nodeU, N nodeV, V value) {
        Preconditions.checkNotNull(nodeU, "nodeU");
        Preconditions.checkNotNull(nodeV, "nodeV");
        Preconditions.checkNotNull(value, "value");
        if (!allowsSelfLoops()) {
            Preconditions.checkArgument(!nodeU.equals(nodeV), "Cannot add self-loop edge on node %s, as self-loops are not allowed. To construct a graph that allows self-loops, call allowsSelfLoops(true) on the Builder.", nodeU);
        }
        GraphConnections<N, V> graphConnectionsAddNodeInternal = this.nodeConnections.get(nodeU);
        if (graphConnectionsAddNodeInternal == null) {
            graphConnectionsAddNodeInternal = addNodeInternal(nodeU);
        }
        V vAddSuccessor = graphConnectionsAddNodeInternal.addSuccessor(nodeV, value);
        GraphConnections<N, V> graphConnectionsAddNodeInternal2 = this.nodeConnections.get(nodeV);
        if (graphConnectionsAddNodeInternal2 == null) {
            graphConnectionsAddNodeInternal2 = addNodeInternal(nodeV);
        }
        graphConnectionsAddNodeInternal2.addPredecessor(nodeU, value);
        if (vAddSuccessor == null) {
            long j = this.edgeCount + 1;
            this.edgeCount = j;
            Graphs.checkPositive(j);
        }
        return vAddSuccessor;
    }

    @Override // com.google.common.graph.MutableValueGraph
    @CheckForNull
    public V putEdgeValue(EndpointPair<N> endpoints, V value) {
        validateEndpoints(endpoints);
        return putEdgeValue(endpoints.nodeU(), endpoints.nodeV(), value);
    }

    @Override // com.google.common.graph.MutableValueGraph
    public boolean removeNode(N node) {
        Preconditions.checkNotNull(node, "node");
        GraphConnections<N, V> graphConnections = this.nodeConnections.get(node);
        if (graphConnections == null) {
            return false;
        }
        if (allowsSelfLoops() && graphConnections.removeSuccessor(node) != null) {
            graphConnections.removePredecessor(node);
            this.edgeCount--;
        }
        Iterator<N> it = graphConnections.successors().iterator();
        while (it.hasNext()) {
            ((GraphConnections) Objects.requireNonNull(this.nodeConnections.getWithoutCaching(it.next()))).removePredecessor(node);
            this.edgeCount--;
        }
        if (isDirected()) {
            Iterator<N> it2 = graphConnections.predecessors().iterator();
            while (it2.hasNext()) {
                Preconditions.checkState(((GraphConnections) Objects.requireNonNull(this.nodeConnections.getWithoutCaching(it2.next()))).removeSuccessor(node) != null);
                this.edgeCount--;
            }
        }
        this.nodeConnections.remove(node);
        Graphs.checkNonNegative(this.edgeCount);
        return true;
    }

    @Override // com.google.common.graph.MutableValueGraph
    @CheckForNull
    public V removeEdge(N nodeU, N nodeV) {
        Preconditions.checkNotNull(nodeU, "nodeU");
        Preconditions.checkNotNull(nodeV, "nodeV");
        GraphConnections<N, V> graphConnections = this.nodeConnections.get(nodeU);
        GraphConnections<N, V> graphConnections2 = this.nodeConnections.get(nodeV);
        if (graphConnections == null || graphConnections2 == null) {
            return null;
        }
        V vRemoveSuccessor = graphConnections.removeSuccessor(nodeV);
        if (vRemoveSuccessor != null) {
            graphConnections2.removePredecessor(nodeU);
            long j = this.edgeCount - 1;
            this.edgeCount = j;
            Graphs.checkNonNegative(j);
        }
        return vRemoveSuccessor;
    }

    @Override // com.google.common.graph.MutableValueGraph
    @CheckForNull
    public V removeEdge(EndpointPair<N> endpoints) {
        validateEndpoints(endpoints);
        return removeEdge(endpoints.nodeU(), endpoints.nodeV());
    }

    private GraphConnections<N, V> newConnections() {
        if (isDirected()) {
            return DirectedGraphConnections.of(this.incidentEdgeOrder);
        }
        return UndirectedGraphConnections.of(this.incidentEdgeOrder);
    }
}
