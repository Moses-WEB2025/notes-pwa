package androidx.appsearch.ast.query;

import androidx.appsearch.ast.FunctionNode;
import androidx.core.util.Preconditions;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public final class SemanticSearchNode implements FunctionNode {
    private int mDistanceMetric;
    private float mLowerBound;
    private float mUpperBound;
    private int mVectorIndex;

    public SemanticSearchNode(int i, float f, float f2, int i2) {
        Preconditions.checkArgument(i >= 0, "Vector index must be non-negative.");
        Preconditions.checkArgument(f <= f2, "Provided lower bound must be less than or equal to the provided upper bound.");
        Preconditions.checkArgumentInRange(i2, 0, 3, "Embedding search metric type");
        this.mVectorIndex = i;
        this.mLowerBound = f;
        this.mUpperBound = f2;
        this.mDistanceMetric = i2;
    }

    public SemanticSearchNode(int i, float f, float f2) {
        this(i, f, f2, 0);
    }

    public SemanticSearchNode(int i, float f) {
        this(i, f, Float.POSITIVE_INFINITY);
    }

    public SemanticSearchNode(int i) {
        this(i, Float.NEGATIVE_INFINITY);
    }

    @Override // androidx.appsearch.ast.FunctionNode
    public String getFunctionName() {
        return FunctionNode.FUNCTION_NAME_SEMANTIC_SEARCH;
    }

    public int getVectorIndex() {
        return this.mVectorIndex;
    }

    public float getLowerBound() {
        return this.mLowerBound;
    }

    public float getUpperBound() {
        return this.mUpperBound;
    }

    public int getDistanceMetric() {
        return this.mDistanceMetric;
    }

    public void setVectorIndex(int i) {
        Preconditions.checkArgument(i >= 0, "Vector Index must be non-negative.");
        this.mVectorIndex = i;
    }

    public void setBounds(float f, float f2) {
        Preconditions.checkArgument(f <= f2, "Provided lower bound must be less than or equal to the provided upper bound");
        this.mLowerBound = f;
        this.mUpperBound = f2;
    }

    public void setDistanceMetric(int i) {
        Preconditions.checkArgumentInRange(i, 0, 3, "Embedding search metric type");
        this.mDistanceMetric = i;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(FunctionNode.FUNCTION_NAME_SEMANTIC_SEARCH);
        sb.append("(getEmbeddingParameter(");
        sb.append(this.mVectorIndex);
        sb.append(")");
        if (this.mDistanceMetric != 0) {
            formatBound(sb, this.mLowerBound);
            formatBound(sb, this.mUpperBound);
            sb.append(", ");
            sb.append(getEmbeddingMetricString());
        } else if (this.mUpperBound != Float.POSITIVE_INFINITY) {
            formatBound(sb, this.mLowerBound);
            formatBound(sb, this.mUpperBound);
        } else {
            float f = this.mLowerBound;
            if (f != Float.NEGATIVE_INFINITY) {
                formatBound(sb, f);
            }
        }
        sb.append(")");
        return sb.toString();
    }

    private void formatBound(StringBuilder sb, float f) {
        sb.append(", ");
        if (Float.isFinite(f)) {
            sb.append(f);
        } else if (f == Float.NEGATIVE_INFINITY) {
            sb.append(-3.4028235E38f);
        } else {
            sb.append(Float.MAX_VALUE);
        }
    }

    private String getEmbeddingMetricString() {
        int i = this.mDistanceMetric;
        if (i == 1) {
            return "\"COSINE\"";
        }
        if (i == 2) {
            return "\"DOT_PRODUCT\"";
        }
        if (i == 3) {
            return "\"EUCLIDEAN\"";
        }
        throw new IllegalStateException("Invalid Metric Type");
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            SemanticSearchNode semanticSearchNode = (SemanticSearchNode) obj;
            if (this.mVectorIndex == semanticSearchNode.mVectorIndex && Float.compare(this.mLowerBound, semanticSearchNode.mLowerBound) == 0 && Float.compare(this.mUpperBound, semanticSearchNode.mUpperBound) == 0 && this.mDistanceMetric == semanticSearchNode.mDistanceMetric) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mVectorIndex), Float.valueOf(this.mLowerBound), Float.valueOf(this.mUpperBound), Integer.valueOf(this.mDistanceMetric));
    }
}
