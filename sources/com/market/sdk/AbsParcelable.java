package com.market.sdk;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes2.dex */
public abstract class AbsParcelable implements Parcelable {
    protected int version;

    protected AbsParcelable() {
        this.version = 1;
    }

    protected AbsParcelable(Parcel parcel) {
        this.version = 1;
        this.version = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.version);
    }
}
