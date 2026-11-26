package androidx.appsearch.ast.query;

import androidx.appsearch.ast.FunctionNode;
import androidx.core.util.Preconditions;

/* JADX INFO: loaded from: classes.dex */
public final class GetSearchStringParameterNode implements FunctionNode {
    private int mSearchStringIndex;

    public GetSearchStringParameterNode(int i) {
        Preconditions.checkArgument(i >= 0, "SearchStringIndex must be non-negative.");
        this.mSearchStringIndex = i;
    }

    @Override // androidx.appsearch.ast.FunctionNode
    public String getFunctionName() {
        return FunctionNode.FUNCTION_NAME_GET_SEARCH_STRING_PARAMETER;
    }

    public int getSearchStringIndex() {
        return this.mSearchStringIndex;
    }

    public void setSearchStringIndex(int i) {
        Preconditions.checkArgument(i >= 0, "SearchStringIndex must be non-negative.");
        this.mSearchStringIndex = i;
    }

    public String toString() {
        return "getSearchStringParameter(" + this.mSearchStringIndex + ")";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.mSearchStringIndex == ((GetSearchStringParameterNode) obj).mSearchStringIndex;
    }

    public int hashCode() {
        return Integer.hashCode(this.mSearchStringIndex);
    }
}
