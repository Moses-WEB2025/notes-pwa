package androidx.appsearch.usagereporting;

import androidx.core.util.Preconditions;

/* JADX INFO: loaded from: classes.dex */
public class ImpressionAction extends TakenAction {
    private final String mQuery;
    private final String mReferencedQualifiedId;
    private final int mResultRankGlobal;
    private final int mResultRankInBlock;

    ImpressionAction(String str, String str2, long j, long j2, int i, String str3, String str4, int i2, int i3) {
        super(str, str2, j, j2, i);
        this.mQuery = str3;
        this.mReferencedQualifiedId = str4;
        this.mResultRankInBlock = i2;
        this.mResultRankGlobal = i3;
    }

    public String getQuery() {
        return this.mQuery;
    }

    public String getReferencedQualifiedId() {
        return this.mReferencedQualifiedId;
    }

    public int getResultRankInBlock() {
        return this.mResultRankInBlock;
    }

    public int getResultRankGlobal() {
        return this.mResultRankGlobal;
    }

    public static final class Builder extends TakenAction.BuilderImpl<Builder> {
        private String mQuery;
        private String mReferencedQualifiedId;
        private int mResultRankGlobal;
        private int mResultRankInBlock;

        @Override // androidx.appsearch.usagereporting.TakenAction.BuilderImpl
        public /* bridge */ /* synthetic */ TakenAction.BuilderImpl setDocumentTtlMillis(long j) {
            return super.setDocumentTtlMillis(j);
        }

        public Builder(String str, String str2, long j) {
            this(str, str2, j, 3);
        }

        public Builder(ImpressionAction impressionAction) {
            super((TakenAction) Preconditions.checkNotNull(impressionAction));
            this.mQuery = impressionAction.getQuery();
            this.mReferencedQualifiedId = impressionAction.getReferencedQualifiedId();
            this.mResultRankInBlock = impressionAction.getResultRankInBlock();
            this.mResultRankGlobal = impressionAction.getResultRankGlobal();
        }

        Builder(String str, String str2, long j, int i) {
            super(str, str2, j, i);
            this.mResultRankInBlock = -1;
            this.mResultRankGlobal = -1;
        }

        public Builder setQuery(String str) {
            this.mQuery = str;
            return this;
        }

        public Builder setReferencedQualifiedId(String str) {
            this.mReferencedQualifiedId = str;
            return this;
        }

        public Builder setResultRankInBlock(int i) {
            this.mResultRankInBlock = i;
            return this;
        }

        public Builder setResultRankGlobal(int i) {
            this.mResultRankGlobal = i;
            return this;
        }

        @Override // androidx.appsearch.usagereporting.TakenAction.BuilderImpl
        public ImpressionAction build() {
            return new ImpressionAction(this.mNamespace, this.mId, this.mDocumentTtlMillis, this.mActionTimestampMillis, this.mActionType, this.mQuery, this.mReferencedQualifiedId, this.mResultRankInBlock, this.mResultRankGlobal);
        }
    }
}
