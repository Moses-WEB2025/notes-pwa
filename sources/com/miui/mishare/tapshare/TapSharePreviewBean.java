package com.miui.mishare.tapshare;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes3.dex */
public class TapSharePreviewBean implements Parcelable {
    public static final Parcelable.Creator<TapSharePreviewBean> CREATOR = new Parcelable.Creator<TapSharePreviewBean>() { // from class: com.miui.mishare.tapshare.TapSharePreviewBean.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TapSharePreviewBean createFromParcel(Parcel parcel) {
            return new TapSharePreviewBean(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TapSharePreviewBean[] newArray(int i) {
            return new TapSharePreviewBean[i];
        }
    };
    private final Uri mPreviewUri;
    private final String mSubTitle;
    private final String mTitle;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public TapSharePreviewBean(Uri uri, String str, String str2) {
        this.mPreviewUri = uri;
        this.mTitle = str;
        this.mSubTitle = str2;
    }

    protected TapSharePreviewBean(Parcel parcel) {
        this.mPreviewUri = (Uri) parcel.readParcelable(Uri.class.getClassLoader());
        this.mTitle = parcel.readString();
        this.mSubTitle = parcel.readString();
    }

    public Uri getUri() {
        return this.mPreviewUri;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public String getSubTitle() {
        return this.mSubTitle;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mPreviewUri, i);
        parcel.writeString(this.mTitle);
        parcel.writeString(this.mSubTitle);
    }
}
