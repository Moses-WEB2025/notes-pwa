package androidx.appsearch.ast.operators;

import androidx.appsearch.app.PropertyPath;
import androidx.appsearch.ast.Node;
import androidx.core.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public final class ComparatorNode implements Node {
    public static final int EQUALS = 0;
    public static final int GREATER_EQUALS = 4;
    public static final int GREATER_THAN = 3;
    public static final int LESS_EQUALS = 2;
    public static final int LESS_THAN = 1;
    private static final int MAX_COMPARATOR_VALUE = 4;
    private int mComparator;
    private PropertyPath mPropertyPath;
    private long mValue;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Comparator {
    }

    public ComparatorNode(int i, PropertyPath propertyPath, long j) {
        Preconditions.checkArgumentInRange(i, 0, 4, "Comparator intDef");
        this.mComparator = i;
        this.mPropertyPath = (PropertyPath) Preconditions.checkNotNull(propertyPath);
        this.mValue = j;
    }

    public int getComparator() {
        return this.mComparator;
    }

    public PropertyPath getPropertyPath() {
        return this.mPropertyPath;
    }

    public long getValue() {
        return this.mValue;
    }

    public void setComparator(int i) {
        Preconditions.checkArgumentInRange(i, 0, 4, "Comparator intDef");
        this.mComparator = i;
    }

    public void setPropertyPath(PropertyPath propertyPath) {
        this.mPropertyPath = (PropertyPath) Preconditions.checkNotNull(propertyPath);
    }

    public void setValue(long j) {
        this.mValue = j;
    }

    public String toString() {
        String str;
        int i = this.mComparator;
        if (i == 0) {
            str = "==";
        } else if (i == 1) {
            str = "<";
        } else if (i == 2) {
            str = "<=";
        } else if (i == 3) {
            str = ">";
        } else {
            str = i != 4 ? "" : ">=";
        }
        return "(" + this.mPropertyPath + StringUtils.SPACE + str + StringUtils.SPACE + this.mValue + ")";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ComparatorNode)) {
            return false;
        }
        ComparatorNode comparatorNode = (ComparatorNode) obj;
        return this.mComparator == comparatorNode.mComparator && this.mValue == comparatorNode.mValue && Objects.equals(this.mPropertyPath, comparatorNode.mPropertyPath);
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mComparator), this.mPropertyPath, Long.valueOf(this.mValue));
    }
}
