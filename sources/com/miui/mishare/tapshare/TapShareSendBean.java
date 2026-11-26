package com.miui.mishare.tapshare;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class TapShareSendBean implements Parcelable {
    public static final Parcelable.Creator<TapShareSendBean> CREATOR = new Parcelable.Creator<TapShareSendBean>() { // from class: com.miui.mishare.tapshare.TapShareSendBean.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TapShareSendBean createFromParcel(Parcel parcel) {
            return new TapShareSendBean(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TapShareSendBean[] newArray(int i) {
            return new TapShareSendBean[i];
        }
    };
    private final String mAction;
    private final String mExtendParam;
    private final String mInternalParam;
    private final Uri mPreviewUri;
    private final String mReceiveAction;
    private final String mSubTitle;
    private final List<TapSharePreviewBean> mTapSharePreviewBeanList;
    private final String mTitle;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected TapShareSendBean(Parcel parcel) {
        this.mPreviewUri = (Uri) parcel.readParcelable(Uri.class.getClassLoader());
        this.mTitle = parcel.readString();
        this.mSubTitle = parcel.readString();
        this.mReceiveAction = parcel.readString();
        this.mExtendParam = parcel.readString();
        this.mInternalParam = parcel.readString();
        this.mTapSharePreviewBeanList = parcel.createTypedArrayList(TapSharePreviewBean.CREATOR);
        this.mAction = parcel.readString();
    }

    public Uri getPreviewUri() {
        return this.mPreviewUri;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public String getSubTitle() {
        return this.mSubTitle;
    }

    public String getReceiveAction() {
        return this.mReceiveAction;
    }

    public String getExtendParam() {
        return this.mExtendParam;
    }

    public String getInternalParam() {
        return this.mInternalParam;
    }

    public String getAction() {
        return this.mAction;
    }

    public List<TapSharePreviewBean> getPreviewInfoList() {
        return this.mTapSharePreviewBeanList;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mPreviewUri, i);
        parcel.writeString(this.mTitle);
        parcel.writeString(this.mSubTitle);
        parcel.writeString(this.mReceiveAction);
        parcel.writeString(this.mExtendParam);
        parcel.writeString(this.mInternalParam);
        parcel.writeTypedList(this.mTapSharePreviewBeanList);
        parcel.writeString(this.mAction);
    }

    public String toString() {
        return "TapShareSendBean{mPreviewUri='" + this.mPreviewUri + "', mTitle='" + this.mTitle + "', mSubTitle='" + this.mSubTitle + "', mReceiveAction='" + this.mReceiveAction + "', mExtendParam='" + this.mExtendParam + "', mInternalParam='" + this.mInternalParam + "', mAction='" + this.mAction + "', mPreviewInfoList='" + this.mTapSharePreviewBeanList + "'}";
    }

    public static class Builder {
        private String mAction;
        private String mExtendParam;
        private String mInternalParam;
        private Uri mPreviewUri;
        private String mReceiveAction;
        private String mSubTitle;
        private List<TapSharePreviewBean> mTapSharePreviewBeanList;
        private String mTitle;

        public Builder setPreviewInfo(Uri uri, String str, String str2) {
            this.mPreviewUri = uri;
            this.mTitle = str;
            this.mSubTitle = str2;
            return this;
        }

        public Builder setReceiveAction(String str) {
            this.mReceiveAction = str;
            return this;
        }

        public Builder setReceiveExtendParam(String str) {
            this.mExtendParam = str;
            return this;
        }

        public Builder setInternalParam(String str) {
            this.mInternalParam = str;
            return this;
        }

        public Builder setPreviewInfoList(List<TapSharePreviewBean> list) {
            this.mTapSharePreviewBeanList = list;
            return this;
        }

        public Builder setAction(String str) {
            this.mAction = str;
            return this;
        }

        public TapShareSendBean build() {
            Parcel parcelObtain = Parcel.obtain();
            try {
                parcelObtain.writeParcelable(this.mPreviewUri, 0);
                parcelObtain.writeString(this.mTitle);
                parcelObtain.writeString(this.mSubTitle);
                parcelObtain.writeString(this.mReceiveAction);
                parcelObtain.writeString(this.mExtendParam);
                parcelObtain.writeString(this.mInternalParam);
                parcelObtain.writeTypedList(this.mTapSharePreviewBeanList);
                parcelObtain.writeString(this.mAction);
                parcelObtain.setDataPosition(0);
                return TapShareSendBean.CREATOR.createFromParcel(parcelObtain);
            } finally {
                parcelObtain.recycle();
            }
        }
    }
}
