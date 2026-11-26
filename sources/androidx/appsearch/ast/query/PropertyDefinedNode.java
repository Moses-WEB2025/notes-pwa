package androidx.appsearch.ast.query;

import androidx.appsearch.app.PropertyPath;
import androidx.appsearch.ast.FunctionNode;
import androidx.core.util.Preconditions;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public final class PropertyDefinedNode implements FunctionNode {
    private PropertyPath mPropertyPath;

    public PropertyDefinedNode(PropertyPath propertyPath) {
        this.mPropertyPath = (PropertyPath) Preconditions.checkNotNull(propertyPath);
    }

    @Override // androidx.appsearch.ast.FunctionNode
    public String getFunctionName() {
        return FunctionNode.FUNCTION_NAME_PROPERTY_DEFINED;
    }

    public PropertyPath getPropertyPath() {
        return this.mPropertyPath;
    }

    public void setPropertyPath(PropertyPath propertyPath) {
        this.mPropertyPath = (PropertyPath) Preconditions.checkNotNull(propertyPath);
    }

    public String toString() {
        return "propertyDefined(\"" + this.mPropertyPath + "\")";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.mPropertyPath, ((PropertyDefinedNode) obj).mPropertyPath);
    }

    public int hashCode() {
        return Objects.hashCode(this.mPropertyPath);
    }
}
