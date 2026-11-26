package androidx.appsearch.usagereporting;

import androidx.core.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes.dex */
public abstract class TakenAction {
    public static final long DEFAULT_DOCUMENT_TTL_MILLIS = 5184000000L;
    private final long mActionTimestampMillis;
    private final int mActionType;
    private final long mDocumentTtlMillis;
    private final String mId;
    private final String mNamespace;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ActionType {
    }

    TakenAction(String str, String str2, long j, long j2, int i) {
        this.mNamespace = (String) Preconditions.checkNotNull(str);
        this.mId = (String) Preconditions.checkNotNull(str2);
        this.mDocumentTtlMillis = j;
        this.mActionTimestampMillis = j2;
        this.mActionType = i;
    }

    public String getNamespace() {
        return this.mNamespace;
    }

    public String getId() {
        return this.mId;
    }

    public long getDocumentTtlMillis() {
        return this.mDocumentTtlMillis;
    }

    public long getActionTimestampMillis() {
        return this.mActionTimestampMillis;
    }

    public int getActionType() {
        return this.mActionType;
    }

    static final class Builder extends BuilderImpl<Builder> {
        Builder(String str, String str2, long j, int i) {
            super(str, str2, j, i);
        }

        Builder(TakenAction takenAction) {
            super(takenAction);
        }
    }

    static class BuilderImpl<T extends BuilderImpl<T>> {
        protected long mActionTimestampMillis;
        protected int mActionType;
        protected long mDocumentTtlMillis;
        protected final String mId;
        protected final String mNamespace;

        BuilderImpl(String str, String str2, long j, int i) {
            this.mNamespace = (String) Preconditions.checkNotNull(str);
            this.mId = (String) Preconditions.checkNotNull(str2);
            this.mActionTimestampMillis = j;
            this.mActionType = i;
            this.mDocumentTtlMillis = TakenAction.DEFAULT_DOCUMENT_TTL_MILLIS;
        }

        BuilderImpl(TakenAction takenAction) {
            this(takenAction.getNamespace(), takenAction.getId(), takenAction.getActionTimestampMillis(), takenAction.getActionType());
            this.mDocumentTtlMillis = takenAction.getDocumentTtlMillis();
        }

        public T setDocumentTtlMillis(long j) {
            this.mDocumentTtlMillis = j;
            return this;
        }

        public TakenAction build() {
            throw new UnsupportedOperationException();
        }
    }
}
