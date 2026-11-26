package com.market.sdk;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.xiaomi.mirror.synergy.CallMethod;

/* JADX INFO: loaded from: classes2.dex */
public class AdsBannerInfo implements Parcelable {
    public static final Parcelable.Creator<AdsBannerInfo> CREATOR = new Parcelable.Creator<AdsBannerInfo>() { // from class: com.market.sdk.AdsBannerInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AdsBannerInfo createFromParcel(Parcel parcel) {
            return new AdsBannerInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AdsBannerInfo[] newArray(int i) {
            return new AdsBannerInfo[i];
        }
    };

    @SerializedName("iconUri")
    public Uri iconUri;

    @SerializedName("iconUrl")
    public String iconUrl;

    @SerializedName(CallMethod.ARG_URI)
    public Uri uri;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AdsBannerInfo() {
    }

    public AdsBannerInfo(Parcel parcel) {
        this.iconUrl = parcel.readString();
        this.uri = (Uri) Uri.CREATOR.createFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.iconUrl);
        Uri.writeToParcel(parcel, this.uri);
    }
}
