package androidx.appsearch.app;

import androidx.core.util.Preconditions;

/* JADX INFO: loaded from: classes.dex */
public final class ReportSystemUsageRequest {
    private final String mDatabase;
    private final String mDocumentId;
    private final String mNamespace;
    private final String mPackageName;
    private final long mUsageTimestampMillis;

    ReportSystemUsageRequest(String str, String str2, String str3, String str4, long j) {
        this.mPackageName = (String) Preconditions.checkNotNull(str);
        this.mDatabase = (String) Preconditions.checkNotNull(str2);
        this.mNamespace = (String) Preconditions.checkNotNull(str3);
        this.mDocumentId = (String) Preconditions.checkNotNull(str4);
        this.mUsageTimestampMillis = j;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public String getDatabaseName() {
        return this.mDatabase;
    }

    public String getNamespace() {
        return this.mNamespace;
    }

    public String getDocumentId() {
        return this.mDocumentId;
    }

    public long getUsageTimestampMillis() {
        return this.mUsageTimestampMillis;
    }

    public static final class Builder {
        private final String mDatabase;
        private final String mDocumentId;
        private final String mNamespace;
        private final String mPackageName;
        private Long mUsageTimestampMillis;

        public Builder(String str, String str2, String str3, String str4) {
            this.mPackageName = (String) Preconditions.checkNotNull(str);
            this.mDatabase = (String) Preconditions.checkNotNull(str2);
            this.mNamespace = (String) Preconditions.checkNotNull(str3);
            this.mDocumentId = (String) Preconditions.checkNotNull(str4);
        }

        public Builder setUsageTimestampMillis(long j) {
            this.mUsageTimestampMillis = Long.valueOf(j);
            return this;
        }

        public ReportSystemUsageRequest build() {
            if (this.mUsageTimestampMillis == null) {
                this.mUsageTimestampMillis = Long.valueOf(System.currentTimeMillis());
            }
            return new ReportSystemUsageRequest(this.mPackageName, this.mDatabase, this.mNamespace, this.mDocumentId, this.mUsageTimestampMillis.longValue());
        }
    }
}
