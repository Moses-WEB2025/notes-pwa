package com.xiaomi.accountsdk.account.data;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes4.dex */
public class MiuiActivatorInfo implements Parcelable {
    public static final Parcelable.Creator<MiuiActivatorInfo> CREATOR = new Parcelable.Creator<MiuiActivatorInfo>() { // from class: com.xiaomi.accountsdk.account.data.MiuiActivatorInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MiuiActivatorInfo createFromParcel(Parcel parcel) {
            return new MiuiActivatorInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MiuiActivatorInfo[] newArray(int i) {
            return new MiuiActivatorInfo[i];
        }
    };
    public final String phone;
    public final String simId;
    public final int simIndex;
    public final String vKey2;
    public final String vKey2Nonce;

    public interface Getter {
        MiuiActivatorInfo[] get(Context context);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public MiuiActivatorInfo(int i, String str, String str2, String str3, String str4) {
        this.simIndex = i;
        this.phone = str;
        this.simId = str2;
        this.vKey2 = str3;
        this.vKey2Nonce = str4;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.simIndex);
        parcel.writeString(this.phone);
        parcel.writeString(this.simId);
        parcel.writeString(this.vKey2);
        parcel.writeString(this.vKey2Nonce);
    }

    protected MiuiActivatorInfo(Parcel parcel) {
        this.simIndex = parcel.readInt();
        this.phone = parcel.readString();
        this.simId = parcel.readString();
        this.vKey2 = parcel.readString();
        this.vKey2Nonce = parcel.readString();
    }
}
