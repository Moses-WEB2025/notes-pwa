package androidx.appsearch.ast.operators;

import androidx.appsearch.app.PropertyPath;
import androidx.appsearch.ast.Node;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public final class PropertyRestrictNode implements Node {
    private final List<Node> mChildren;
    private PropertyPath mPropertyPath;

    public PropertyRestrictNode(PropertyPath propertyPath, Node node) {
        ArrayList arrayList = new ArrayList(1);
        this.mChildren = arrayList;
        this.mPropertyPath = (PropertyPath) Preconditions.checkNotNull(propertyPath);
        arrayList.add((Node) Preconditions.checkNotNull(node));
    }

    public PropertyPath getPropertyPath() {
        return this.mPropertyPath;
    }

    @Override // androidx.appsearch.ast.Node
    public List<Node> getChildren() {
        return Collections.unmodifiableList(this.mChildren);
    }

    public Node getChild() {
        return this.mChildren.get(0);
    }

    public void setPropertyPath(PropertyPath propertyPath) {
        this.mPropertyPath = (PropertyPath) Preconditions.checkNotNull(propertyPath);
    }

    public void setChild(Node node) {
        this.mChildren.set(0, (Node) Preconditions.checkNotNull(node));
    }

    public String toString() {
        return "(" + this.mPropertyPath + ":" + getChild() + ")";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PropertyRestrictNode)) {
            return false;
        }
        PropertyRestrictNode propertyRestrictNode = (PropertyRestrictNode) obj;
        return Objects.equals(this.mPropertyPath, propertyRestrictNode.mPropertyPath) && Objects.equals(this.mChildren, propertyRestrictNode.mChildren);
    }

    public int hashCode() {
        return Objects.hash(this.mPropertyPath, this.mChildren);
    }
}
