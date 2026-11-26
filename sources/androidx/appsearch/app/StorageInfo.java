package androidx.appsearch.app;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.appsearch.safeparcel.AbstractSafeParcelable;
import androidx.appsearch.safeparcel.stub.StubCreators;

/* JADX INFO: loaded from: classes.dex */
public final class StorageInfo extends AbstractSafeParcelable {
    public static final Parcelable.Creator<StorageInfo> CREATOR = new StubCreators.StorageInfoCreator();
    private int mAliveDocumentsCount;
    private int mAliveNamespacesCount;
    private int mBlobsCount;
    private long mBlobsSizeBytes;
    private long mSizeBytes;

    StorageInfo(long j, int i, int i2, long j2, int i3) {
        this.mSizeBytes = j;
        this.mAliveDocumentsCount = i;
        this.mAliveNamespacesCount = i2;
        this.mBlobsSizeBytes = j2;
        this.mBlobsCount = i3;
    }

    public long getSizeBytes() {
        return this.mSizeBytes;
    }

    public int getAliveDocumentsCount() {
        return this.mAliveDocumentsCount;
    }

    public int getAliveNamespacesCount() {
        return this.mAliveNamespacesCount;
    }

    public long getBlobsSizeBytes() {
        return this.mBlobsSizeBytes;
    }

    public int getBlobsCount() {
        return this.mBlobsCount;
    }

    public static final class Builder {
        private int mAliveDocumentsCount;
        private int mAliveNamespacesCount;
        private int mBlobsCount;
        private long mBlobsSizeBytes;
        private long mSizeBytes;

        public Builder setSizeBytes(long j) {
            this.mSizeBytes = j;
            return this;
        }

        public Builder setAliveDocumentsCount(int i) {
            this.mAliveDocumentsCount = i;
            return this;
        }

        public Builder setAliveNamespacesCount(int i) {
            this.mAliveNamespacesCount = i;
            return this;
        }

        public Builder setBlobsSizeBytes(long j) {
            this.mBlobsSizeBytes = j;
            return this;
        }

        public Builder setBlobsCount(int i) {
            this.mBlobsCount = i;
            return this;
        }

        public StorageInfo build() {
            return new StorageInfo(this.mSizeBytes, this.mAliveDocumentsCount, this.mAliveNamespacesCount, this.mBlobsSizeBytes, this.mBlobsCount);
        }
    }

    @Override // androidx.appsearch.safeparcel.AbstractSafeParcelable
    public void writeToParcel(Parcel parcel, int i) {
        StubCreators.StorageInfoCreator.writeToParcel(this, parcel, i);
    }
}
