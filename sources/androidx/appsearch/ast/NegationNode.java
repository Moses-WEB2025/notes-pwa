package androidx.appsearch.ast;

import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public final class NegationNode implements Node {
    private final List<Node> mChildren;

    public NegationNode(Node node) {
        ArrayList arrayList = new ArrayList(1);
        this.mChildren = arrayList;
        arrayList.add((Node) Preconditions.checkNotNull(node));
    }

    @Override // androidx.appsearch.ast.Node
    public List<Node> getChildren() {
        return Collections.unmodifiableList(this.mChildren);
    }

    public Node getChild() {
        return this.mChildren.get(0);
    }

    public void setChild(Node node) {
        this.mChildren.set(0, (Node) Preconditions.checkNotNull(node));
    }

    public String toString() {
        return "NOT " + getChild();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.mChildren, ((NegationNode) obj).mChildren);
    }

    public int hashCode() {
        return Objects.hashCode(this.mChildren);
    }
}
