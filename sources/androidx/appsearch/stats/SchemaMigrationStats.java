package androidx.appsearch.stats;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.appsearch.safeparcel.AbstractSafeParcelable;
import androidx.appsearch.safeparcel.stub.StubCreators;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public final class SchemaMigrationStats extends AbstractSafeParcelable {
    public static final Parcelable.Creator<SchemaMigrationStats> CREATOR = new StubCreators.SchemaMigrationStatsCreator();
    public static final int FIRST_CALL_GET_INCOMPATIBLE = 1;
    public static final int NO_MIGRATION = 0;
    public static final int SECOND_CALL_APPLY_NEW_SCHEMA = 2;
    private final String mDatabase;
    private final int mExecutorAcquisitionLatencyMillis;
    private final int mFirstSetSchemaLatencyMillis;
    private final int mGetSchemaLatencyMillis;
    private final boolean mIsFirstSetSchemaSuccess;
    private final int mMigrationFailureCount;
    private final String mPackageName;
    private final int mQueryAndTransformLatencyMillis;
    private final int mSaveDocumentLatencyMillis;
    private final int mSecondSetSchemaLatencyMillis;
    private final int mStatusCode;
    private final int mTotalLatencyMillis;
    private final int mTotalNeedMigratedDocumentCount;
    private final int mTotalSuccessMigratedDocumentCount;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SchemaMigrationCallType {
    }

    public SchemaMigrationStats(String str, String str2, int i, int i2, int i3, int i4, int i5, int i6, boolean z, int i7, int i8, int i9, int i10, int i11) {
        this.mPackageName = str;
        this.mDatabase = str2;
        this.mStatusCode = i;
        this.mExecutorAcquisitionLatencyMillis = i2;
        this.mTotalLatencyMillis = i3;
        this.mGetSchemaLatencyMillis = i4;
        this.mQueryAndTransformLatencyMillis = i5;
        this.mFirstSetSchemaLatencyMillis = i6;
        this.mIsFirstSetSchemaSuccess = z;
        this.mSecondSetSchemaLatencyMillis = i7;
        this.mSaveDocumentLatencyMillis = i8;
        this.mTotalNeedMigratedDocumentCount = i9;
        this.mMigrationFailureCount = i10;
        this.mTotalSuccessMigratedDocumentCount = i11;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public String getDatabase() {
        return this.mDatabase;
    }

    public int getStatusCode() {
        return this.mStatusCode;
    }

    public int getExecutorAcquisitionLatencyMillis() {
        return this.mExecutorAcquisitionLatencyMillis;
    }

    public int getTotalLatencyMillis() {
        return this.mTotalLatencyMillis;
    }

    public int getGetSchemaLatencyMillis() {
        return this.mGetSchemaLatencyMillis;
    }

    public int getQueryAndTransformLatencyMillis() {
        return this.mQueryAndTransformLatencyMillis;
    }

    public int getFirstSetSchemaLatencyMillis() {
        return this.mFirstSetSchemaLatencyMillis;
    }

    public boolean isFirstSetSchemaSuccess() {
        return this.mIsFirstSetSchemaSuccess;
    }

    public int getSecondSetSchemaLatencyMillis() {
        return this.mSecondSetSchemaLatencyMillis;
    }

    public int getSaveDocumentLatencyMillis() {
        return this.mSaveDocumentLatencyMillis;
    }

    public int getTotalNeedMigratedDocumentCount() {
        return this.mTotalNeedMigratedDocumentCount;
    }

    public int getMigrationFailureCount() {
        return this.mMigrationFailureCount;
    }

    public int getTotalSuccessMigratedDocumentCount() {
        return this.mTotalSuccessMigratedDocumentCount;
    }

    @Override // androidx.appsearch.safeparcel.AbstractSafeParcelable
    public void writeToParcel(Parcel parcel, int i) {
        StubCreators.SchemaMigrationStatsCreator.writeToParcel(this, parcel, i);
    }

    public static class Builder {
        String mDatabase;
        int mExecutorAcquisitionLatencyMillis;
        int mFirstSetSchemaLatencyMillis;
        int mGetSchemaLatencyMillis;
        boolean mIsFirstSetSchemaSuccess;
        int mMigrationFailureCount;
        String mPackageName;
        int mQueryAndTransformLatencyMillis;
        int mSaveDocumentLatencyMillis;
        int mSecondSetSchemaLatencyMillis;
        int mStatusCode;
        int mTotalLatencyMillis;
        int mTotalNeedMigratedDocumentCount;
        int mTotalSuccessMigratedDocumentCount;

        public Builder(String str, String str2) {
            this.mPackageName = (String) Objects.requireNonNull(str);
            this.mDatabase = (String) Objects.requireNonNull(str2);
        }

        public Builder(SchemaMigrationStats schemaMigrationStats) {
            Objects.requireNonNull(schemaMigrationStats);
            this.mPackageName = schemaMigrationStats.mPackageName;
            this.mDatabase = schemaMigrationStats.mDatabase;
            this.mStatusCode = schemaMigrationStats.mStatusCode;
            this.mExecutorAcquisitionLatencyMillis = schemaMigrationStats.mExecutorAcquisitionLatencyMillis;
            this.mTotalLatencyMillis = schemaMigrationStats.mTotalLatencyMillis;
            this.mGetSchemaLatencyMillis = schemaMigrationStats.mGetSchemaLatencyMillis;
            this.mQueryAndTransformLatencyMillis = schemaMigrationStats.mQueryAndTransformLatencyMillis;
            this.mFirstSetSchemaLatencyMillis = schemaMigrationStats.mFirstSetSchemaLatencyMillis;
            this.mIsFirstSetSchemaSuccess = schemaMigrationStats.mIsFirstSetSchemaSuccess;
            this.mSecondSetSchemaLatencyMillis = schemaMigrationStats.mSecondSetSchemaLatencyMillis;
            this.mSaveDocumentLatencyMillis = schemaMigrationStats.mSaveDocumentLatencyMillis;
            this.mTotalNeedMigratedDocumentCount = schemaMigrationStats.mTotalNeedMigratedDocumentCount;
            this.mMigrationFailureCount = schemaMigrationStats.mMigrationFailureCount;
            this.mTotalSuccessMigratedDocumentCount = schemaMigrationStats.mTotalSuccessMigratedDocumentCount;
        }

        public Builder setStatusCode(int i) {
            this.mStatusCode = i;
            return this;
        }

        public Builder setExecutorAcquisitionLatencyMillis(int i) {
            this.mExecutorAcquisitionLatencyMillis = i;
            return this;
        }

        public Builder setTotalLatencyMillis(int i) {
            this.mTotalLatencyMillis = i;
            return this;
        }

        public Builder setGetSchemaLatencyMillis(int i) {
            this.mGetSchemaLatencyMillis = i;
            return this;
        }

        public Builder setQueryAndTransformLatencyMillis(int i) {
            this.mQueryAndTransformLatencyMillis = i;
            return this;
        }

        public Builder setFirstSetSchemaLatencyMillis(int i) {
            this.mFirstSetSchemaLatencyMillis = i;
            return this;
        }

        public Builder setIsFirstSetSchemaSuccess(boolean z) {
            this.mIsFirstSetSchemaSuccess = z;
            return this;
        }

        public Builder setSecondSetSchemaLatencyMillis(int i) {
            this.mSecondSetSchemaLatencyMillis = i;
            return this;
        }

        public Builder setSaveDocumentLatencyMillis(int i) {
            this.mSaveDocumentLatencyMillis = i;
            return this;
        }

        public Builder setTotalNeedMigratedDocumentCount(int i) {
            this.mTotalNeedMigratedDocumentCount = i;
            return this;
        }

        public Builder setTotalSuccessMigratedDocumentCount(int i) {
            this.mTotalSuccessMigratedDocumentCount = i;
            return this;
        }

        public Builder setMigrationFailureCount(int i) {
            this.mMigrationFailureCount = i;
            return this;
        }

        public SchemaMigrationStats build() {
            return new SchemaMigrationStats(this.mPackageName, this.mDatabase, this.mStatusCode, this.mExecutorAcquisitionLatencyMillis, this.mTotalLatencyMillis, this.mGetSchemaLatencyMillis, this.mQueryAndTransformLatencyMillis, this.mFirstSetSchemaLatencyMillis, this.mIsFirstSetSchemaSuccess, this.mSecondSetSchemaLatencyMillis, this.mSaveDocumentLatencyMillis, this.mTotalNeedMigratedDocumentCount, this.mMigrationFailureCount, this.mTotalSuccessMigratedDocumentCount);
        }
    }
}
