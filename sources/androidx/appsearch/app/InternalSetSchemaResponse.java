package androidx.appsearch.app;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.appsearch.safeparcel.AbstractSafeParcelable;
import androidx.appsearch.safeparcel.stub.StubCreators;
import androidx.core.util.Preconditions;

/* JADX INFO: loaded from: classes.dex */
public class InternalSetSchemaResponse extends AbstractSafeParcelable {
    public static final Parcelable.Creator<InternalSetSchemaResponse> CREATOR = new StubCreators.InternalSetSchemaResponseCreator();
    private final String mErrorMessage;
    private final boolean mIsSuccess;
    private final SetSchemaResponse mSetSchemaResponse;

    public InternalSetSchemaResponse(boolean z, SetSchemaResponse setSchemaResponse, String str) {
        Preconditions.checkNotNull(setSchemaResponse);
        this.mIsSuccess = z;
        this.mSetSchemaResponse = setSchemaResponse;
        this.mErrorMessage = str;
    }

    public static InternalSetSchemaResponse newSuccessfulSetSchemaResponse(SetSchemaResponse setSchemaResponse) {
        return new InternalSetSchemaResponse(true, setSchemaResponse, null);
    }

    public static InternalSetSchemaResponse newFailedSetSchemaResponse(SetSchemaResponse setSchemaResponse, String str) {
        return new InternalSetSchemaResponse(false, setSchemaResponse, str);
    }

    public boolean isSuccess() {
        return this.mIsSuccess;
    }

    public SetSchemaResponse getSetSchemaResponse() {
        return this.mSetSchemaResponse;
    }

    public String getErrorMessage() {
        return this.mErrorMessage;
    }

    @Override // androidx.appsearch.safeparcel.AbstractSafeParcelable
    public void writeToParcel(Parcel parcel, int i) {
        StubCreators.InternalSetSchemaResponseCreator.writeToParcel(this, parcel, i);
    }
}
