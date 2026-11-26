package androidx.appsearch.app;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import androidx.appsearch.app.aidl.AppSearchBatchResultParcelV2;
import androidx.appsearch.safeparcel.AbstractSafeParcelable;
import androidx.appsearch.safeparcel.stub.StubCreators;
import androidx.core.util.Preconditions;
import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public final class OpenBlobForWriteResponse extends AbstractSafeParcelable implements Closeable {
    public static final Parcelable.Creator<OpenBlobForWriteResponse> CREATOR = new StubCreators.OpenBlobForWriteResponseCreator();
    final AppSearchBatchResultParcelV2<AppSearchBlobHandle, ParcelFileDescriptor> mResultParcel;

    public OpenBlobForWriteResponse(AppSearchBatchResult<AppSearchBlobHandle, ParcelFileDescriptor> appSearchBatchResult) {
        this(AppSearchBatchResultParcelV2.fromBlobHandleToPfd(appSearchBatchResult));
    }

    OpenBlobForWriteResponse(AppSearchBatchResultParcelV2<AppSearchBlobHandle, ParcelFileDescriptor> appSearchBatchResultParcelV2) {
        this.mResultParcel = (AppSearchBatchResultParcelV2) Preconditions.checkNotNull(appSearchBatchResultParcelV2);
    }

    public AppSearchBatchResult<AppSearchBlobHandle, ParcelFileDescriptor> getResult() {
        return this.mResultParcel.getResult();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        Iterator<ParcelFileDescriptor> it = this.mResultParcel.getResult().getSuccesses().values().iterator();
        while (it.hasNext()) {
            try {
                it.next().close();
            } catch (IOException unused) {
            }
        }
    }

    @Override // androidx.appsearch.safeparcel.AbstractSafeParcelable
    public void writeToParcel(Parcel parcel, int i) {
        StubCreators.OpenBlobForWriteResponseCreator.writeToParcel(this, parcel, i);
    }
}
