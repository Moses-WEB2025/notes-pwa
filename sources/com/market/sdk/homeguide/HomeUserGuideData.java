package com.market.sdk.homeguide;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.market.sdk.AbsParcelable;

/* JADX INFO: loaded from: classes2.dex */
public class HomeUserGuideData extends AbsParcelable {
    public static final Parcelable.Creator<HomeUserGuideData> CREATOR = new Parcelable.Creator<HomeUserGuideData>() { // from class: com.market.sdk.homeguide.HomeUserGuideData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HomeUserGuideData createFromParcel(Parcel parcel) {
            return new HomeUserGuideData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HomeUserGuideData[] newArray(int i) {
            return new HomeUserGuideData[i];
        }
    };
    private Uri homeScreen;
    private String localFilePath;
    private ViewConfig viewConfig;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public HomeUserGuideData() {
    }

    protected HomeUserGuideData(Parcel parcel) {
        super(parcel);
        this.viewConfig = (ViewConfig) parcel.readSerializable();
        this.homeScreen = (Uri) parcel.readParcelable(Uri.class.getClassLoader());
    }

    public ViewConfig getViewConfig() {
        return this.viewConfig;
    }

    public void setViewConfig(ViewConfig viewConfig) {
        this.viewConfig = viewConfig;
    }

    public void setHomeScreenUri(Uri uri) {
        this.homeScreen = uri;
    }

    public Uri getHomeScreenUri() {
        return this.homeScreen;
    }

    public String getLocalFilePath() {
        return this.localFilePath;
    }

    public void setLocalFilePath(String str) {
        this.localFilePath = str;
    }

    public boolean isValid() {
        ViewConfig viewConfig = this.viewConfig;
        return (viewConfig == null || !viewConfig.isValid() || this.homeScreen == null) ? false : true;
    }

    @Override // com.market.sdk.AbsParcelable, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeSerializable(this.viewConfig);
        parcel.writeParcelable(this.homeScreen, 0);
    }
}
