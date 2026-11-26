package androidx.appsearch.safeparcel.stub;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.appsearch.safeparcel.SafeParcelable;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractCreator<T> implements Parcelable.Creator<T> {
    AbstractCreator() {
    }

    @Override // android.os.Parcelable.Creator
    public T createFromParcel(Parcel parcel) {
        throw new UnsupportedOperationException("createFromParcel is not implemented and should not be used.");
    }

    @Override // android.os.Parcelable.Creator
    public T[] newArray(int i) {
        throw new UnsupportedOperationException("newArray is not implemented and should not be used.");
    }

    public static void writeToParcel(SafeParcelable safeParcelable, Parcel parcel, int i) {
        throw new UnsupportedOperationException("writeToParcel is not implemented and should not be used.");
    }
}
