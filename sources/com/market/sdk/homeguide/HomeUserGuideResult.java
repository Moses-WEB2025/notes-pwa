package com.market.sdk.homeguide;

import android.os.Parcel;
import android.os.Parcelable;
import com.market.sdk.AbsParcelable;

/* JADX INFO: loaded from: classes2.dex */
public class HomeUserGuideResult extends AbsParcelable {
    public static final Parcelable.Creator<HomeUserGuideResult> CREATOR = new Parcelable.Creator<HomeUserGuideResult>() { // from class: com.market.sdk.homeguide.HomeUserGuideResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HomeUserGuideResult createFromParcel(Parcel parcel) {
            return new HomeUserGuideResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HomeUserGuideResult[] newArray(int i) {
            return new HomeUserGuideResult[i];
        }
    };
    public static final int ERROR_INVALID_HOME_SCREEN = 1;
    public static final int ERROR_INVALID_ICON_POS = 2;
    public static final int OK = 0;
    private int errorCode;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static HomeUserGuideResult of(int i) {
        return new HomeUserGuideResult(i);
    }

    public HomeUserGuideResult(int i) {
        this.errorCode = i;
    }

    public HomeUserGuideResult(Parcel parcel) {
        super(parcel);
        this.errorCode = 0;
        this.errorCode = parcel.readInt();
    }

    @Override // com.market.sdk.AbsParcelable, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.errorCode);
    }

    public int getErrorCode() {
        return this.errorCode;
    }
}
