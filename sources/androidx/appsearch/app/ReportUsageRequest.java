package androidx.appsearch.app;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.appsearch.safeparcel.AbstractSafeParcelable;
import androidx.appsearch.safeparcel.stub.StubCreators;
import androidx.core.util.Preconditions;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public final class ReportUsageRequest extends AbstractSafeParcelable {
    public static final Parcelable.Creator<ReportUsageRequest> CREATOR = new StubCreators.ReportUsageRequestCreator();
    private final String mDocumentId;
    private final String mNamespace;
    private final long mUsageTimestampMillis;

    ReportUsageRequest(String str, String str2, long j) {
        this.mNamespace = (String) Objects.requireNonNull(str);
        this.mDocumentId = (String) Objects.requireNonNull(str2);
        this.mUsageTimestampMillis = j;
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

    @Override // androidx.appsearch.safeparcel.AbstractSafeParcelable
    public void writeToParcel(Parcel parcel, int i) {
        StubCreators.ReportUsageRequestCreator.writeToParcel(this, parcel, i);
    }

    public static final class Builder {
        private final String mDocumentId;
        private final String mNamespace;
        private Long mUsageTimestampMillis;

        public Builder(String str, String str2) {
            this.mNamespace = (String) Preconditions.checkNotNull(str);
            this.mDocumentId = (String) Preconditions.checkNotNull(str2);
        }

        public Builder setUsageTimestampMillis(long j) {
            this.mUsageTimestampMillis = Long.valueOf(j);
            return this;
        }

        public ReportUsageRequest build() {
            if (this.mUsageTimestampMillis == null) {
                this.mUsageTimestampMillis = Long.valueOf(System.currentTimeMillis());
            }
            return new ReportUsageRequest(this.mNamespace, this.mDocumentId, this.mUsageTimestampMillis.longValue());
        }
    }
}
