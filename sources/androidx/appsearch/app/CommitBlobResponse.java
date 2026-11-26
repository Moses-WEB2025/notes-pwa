package androidx.appsearch.app;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.appsearch.app.aidl.AppSearchBatchResultParcelV2;
import androidx.appsearch.safeparcel.AbstractSafeParcelable;
import androidx.appsearch.safeparcel.stub.StubCreators;
import androidx.core.util.Preconditions;

/* JADX INFO: loaded from: classes.dex */
public final class CommitBlobResponse extends AbstractSafeParcelable {
    public static final Parcelable.Creator<CommitBlobResponse> CREATOR = new StubCreators.CommitBlobResponseCreator();
    private final AppSearchBatchResultParcelV2<AppSearchBlobHandle, Void> mResultParcel;

    public CommitBlobResponse(AppSearchBatchResult<AppSearchBlobHandle, Void> appSearchBatchResult) {
        this(AppSearchBatchResultParcelV2.fromBlobHandleToVoid(appSearchBatchResult));
    }

    CommitBlobResponse(AppSearchBatchResultParcelV2<AppSearchBlobHandle, Void> appSearchBatchResultParcelV2) {
        this.mResultParcel = (AppSearchBatchResultParcelV2) Preconditions.checkNotNull(appSearchBatchResultParcelV2);
    }

    public AppSearchBatchResult<AppSearchBlobHandle, Void> getResult() {
        return this.mResultParcel.getResult();
    }

    public AppSearchBatchResultParcelV2<AppSearchBlobHandle, Void> getResponseParcel() {
        return this.mResultParcel;
    }

    @Override // androidx.appsearch.safeparcel.AbstractSafeParcelable
    public void writeToParcel(Parcel parcel, int i) {
        StubCreators.CommitBlobResponseCreator.writeToParcel(this, parcel, i);
    }
}
