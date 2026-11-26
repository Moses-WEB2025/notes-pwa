package androidx.appsearch.app;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.appsearch.safeparcel.AbstractSafeParcelable;
import androidx.appsearch.safeparcel.stub.StubCreators;
import androidx.core.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public final class JoinSpec extends AbstractSafeParcelable {
    public static final int AGGREGATION_SCORING_AVG_RANKING_SIGNAL = 3;
    public static final int AGGREGATION_SCORING_MAX_RANKING_SIGNAL = 4;
    public static final int AGGREGATION_SCORING_MIN_RANKING_SIGNAL = 2;
    public static final int AGGREGATION_SCORING_OUTER_RESULT_RANKING_SIGNAL = 0;
    public static final int AGGREGATION_SCORING_RESULT_COUNT = 1;
    public static final int AGGREGATION_SCORING_SUM_RANKING_SIGNAL = 5;
    public static final Parcelable.Creator<JoinSpec> CREATOR = new StubCreators.JoinSpecCreator();
    private static final int DEFAULT_MAX_JOINED_RESULT_COUNT = 10;
    public static final String QUALIFIED_ID = "this.qualifiedId()";
    private final int mAggregationScoringStrategy;
    private final String mChildPropertyExpression;
    private final int mMaxJoinedResultCount;
    private final String mNestedQuery;
    private final SearchSpec mNestedSearchSpec;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AggregationScoringStrategy {
    }

    JoinSpec(String str, SearchSpec searchSpec, String str2, int i, int i2) {
        this.mNestedQuery = (String) Objects.requireNonNull(str);
        this.mNestedSearchSpec = (SearchSpec) Objects.requireNonNull(searchSpec);
        this.mChildPropertyExpression = (String) Objects.requireNonNull(str2);
        this.mMaxJoinedResultCount = i;
        this.mAggregationScoringStrategy = i2;
    }

    public String getNestedQuery() {
        return this.mNestedQuery;
    }

    public String getChildPropertyExpression() {
        return this.mChildPropertyExpression;
    }

    public int getMaxJoinedResultCount() {
        return this.mMaxJoinedResultCount;
    }

    public SearchSpec getNestedSearchSpec() {
        return this.mNestedSearchSpec;
    }

    public int getAggregationScoringStrategy() {
        return this.mAggregationScoringStrategy;
    }

    @Override // androidx.appsearch.safeparcel.AbstractSafeParcelable
    public void writeToParcel(Parcel parcel, int i) {
        StubCreators.JoinSpecCreator.writeToParcel(this, parcel, i);
    }

    public static final class Builder {
        private static final SearchSpec EMPTY_SEARCH_SPEC = new SearchSpec.Builder().build();
        private int mAggregationScoringStrategy;
        private String mChildPropertyExpression;
        private int mMaxJoinedResultCount;
        private String mNestedQuery;
        private SearchSpec mNestedSearchSpec;

        public Builder(String str) {
            this.mNestedQuery = "";
            this.mNestedSearchSpec = EMPTY_SEARCH_SPEC;
            this.mMaxJoinedResultCount = 10;
            this.mAggregationScoringStrategy = 0;
            Preconditions.checkNotNull(str);
            this.mChildPropertyExpression = str;
        }

        public Builder(JoinSpec joinSpec) {
            this.mNestedQuery = "";
            this.mNestedSearchSpec = EMPTY_SEARCH_SPEC;
            this.mMaxJoinedResultCount = 10;
            this.mAggregationScoringStrategy = 0;
            Preconditions.checkNotNull(joinSpec);
            this.mNestedQuery = joinSpec.getNestedQuery();
            this.mNestedSearchSpec = joinSpec.getNestedSearchSpec();
            this.mChildPropertyExpression = joinSpec.getChildPropertyExpression();
            this.mMaxJoinedResultCount = joinSpec.getMaxJoinedResultCount();
            this.mAggregationScoringStrategy = joinSpec.getAggregationScoringStrategy();
        }

        public Builder setChildPropertyExpression(String str) {
            Preconditions.checkNotNull(str);
            this.mChildPropertyExpression = str;
            return this;
        }

        public Builder setNestedSearch(String str, SearchSpec searchSpec) {
            Preconditions.checkNotNull(str);
            Preconditions.checkNotNull(searchSpec);
            this.mNestedQuery = str;
            this.mNestedSearchSpec = searchSpec;
            return this;
        }

        public Builder setMaxJoinedResultCount(int i) {
            this.mMaxJoinedResultCount = i;
            return this;
        }

        public Builder setAggregationScoringStrategy(int i) {
            Preconditions.checkArgumentInRange(i, 0, 5, "aggregationScoringStrategy");
            this.mAggregationScoringStrategy = i;
            return this;
        }

        public JoinSpec build() {
            return new JoinSpec(this.mNestedQuery, this.mNestedSearchSpec, this.mChildPropertyExpression, this.mMaxJoinedResultCount, this.mAggregationScoringStrategy);
        }
    }
}
