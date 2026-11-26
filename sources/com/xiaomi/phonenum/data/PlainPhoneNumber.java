package com.xiaomi.phonenum.data;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes4.dex */
public class PlainPhoneNumber implements Parcelable {
    public static final Parcelable.Creator<PlainPhoneNumber> CREATOR = new Parcelable.Creator<PlainPhoneNumber>() { // from class: com.xiaomi.phonenum.data.PlainPhoneNumber.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PlainPhoneNumber createFromParcel(Parcel parcel) {
            return new PlainPhoneNumber(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PlainPhoneNumber[] newArray(int i) {
            return new PlainPhoneNumber[i];
        }
    };
    public final String phoneNumber;
    public final int subId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PlainPhoneNumber(int i, String str) {
        this.subId = i;
        this.phoneNumber = str;
    }

    protected PlainPhoneNumber(Parcel parcel) {
        this.subId = parcel.readInt();
        this.phoneNumber = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.subId);
        parcel.writeString(this.phoneNumber);
    }
}
