package androidx.appsearch.ast.operators;

import android.text.TextUtils;
import androidx.appsearch.ast.Node;
import androidx.core.graphics.TypefaceCompat$$ExternalSyntheticBackport0;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public final class AndNode implements Node {
    private List<Node> mChildren;

    public AndNode(List<Node> list) {
        Preconditions.checkNotNull(list);
        Preconditions.checkArgument(list.size() >= 2, "Number of nodes must be at least two.");
        this.mChildren = new ArrayList(list);
    }

    public AndNode(Node node, Node node2, Node... nodeArr) {
        ArrayList arrayList = new ArrayList();
        arrayList.add((Node) Preconditions.checkNotNull(node));
        arrayList.add((Node) Preconditions.checkNotNull(node2));
        arrayList.addAll(TypefaceCompat$$ExternalSyntheticBackport0.m((Object[]) Preconditions.checkNotNull(nodeArr)));
        this.mChildren = arrayList;
    }

    @Override // androidx.appsearch.ast.Node
    public List<Node> getChildren() {
        return Collections.unmodifiableList(this.mChildren);
    }

    public int getIndexOfChild(Node node) {
        Preconditions.checkNotNull(node);
        return this.mChildren.indexOf(node);
    }

    public void setChildren(List<Node> list) {
        Preconditions.checkNotNull(list);
        Preconditions.checkArgument(list.size() >= 2, "Number of nodes must be at least two.");
        this.mChildren = new ArrayList(list);
    }

    public void addChild(Node node) {
        this.mChildren.add((Node) Preconditions.checkNotNull(node));
    }

    public void setChild(int i, Node node) {
        Preconditions.checkArgumentInRange(i, 0, this.mChildren.size() - 1, "Index");
        this.mChildren.set(i, (Node) Preconditions.checkNotNull(node));
    }

    public boolean removeChild(Node node) {
        Preconditions.checkState(this.mChildren.size() > 2, "List of child nodes must contain at least 3 nodes in order to remove.");
        Preconditions.checkNotNull(node);
        return this.mChildren.remove(node);
    }

    public String toString() {
        return "(" + TextUtils.join(" AND ", this.mChildren) + ")";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.mChildren, ((AndNode) obj).mChildren);
    }

    public int hashCode() {
        return Objects.hashCode(this.mChildren);
    }
}
