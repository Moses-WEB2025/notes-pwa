package androidx.appsearch.app.aidl;

import android.os.ParcelFileDescriptor;
import androidx.appsearch.app.AppSearchBatchResult;
import androidx.appsearch.app.AppSearchBlobHandle;
import androidx.core.util.Preconditions;

/* JADX INFO: loaded from: classes.dex */
public final class AppSearchBatchResultParcelV2<KeyType, ValueType> {
    private final AppSearchBatchResult<KeyType, ValueType> mResult;

    private AppSearchBatchResultParcelV2(AppSearchBatchResult<KeyType, ValueType> appSearchBatchResult) {
        this.mResult = (AppSearchBatchResult) Preconditions.checkNotNull(appSearchBatchResult);
    }

    public static AppSearchBatchResultParcelV2<AppSearchBlobHandle, ParcelFileDescriptor> fromBlobHandleToPfd(AppSearchBatchResult<AppSearchBlobHandle, ParcelFileDescriptor> appSearchBatchResult) {
        return new AppSearchBatchResultParcelV2<>(appSearchBatchResult);
    }

    public static AppSearchBatchResultParcelV2<AppSearchBlobHandle, Void> fromBlobHandleToVoid(AppSearchBatchResult<AppSearchBlobHandle, Void> appSearchBatchResult) {
        return new AppSearchBatchResultParcelV2<>(appSearchBatchResult);
    }

    public AppSearchBatchResult<KeyType, ValueType> getResult() {
        return this.mResult;
    }
}
