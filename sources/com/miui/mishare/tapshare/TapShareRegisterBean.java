package com.miui.mishare.tapshare;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class TapShareRegisterBean implements Parcelable {
    public static final Parcelable.Creator<TapShareRegisterBean> CREATOR = new Parcelable.Creator<TapShareRegisterBean>() { // from class: com.miui.mishare.tapshare.TapShareRegisterBean.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TapShareRegisterBean createFromParcel(Parcel parcel) {
            return new TapShareRegisterBean(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TapShareRegisterBean[] newArray(int i) {
            return new TapShareRegisterBean[i];
        }
    };
    private List<Integer> mSupportSharedPlatforms;
    private int mUseBasicChannel;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public TapShareRegisterBean() {
        this.mSupportSharedPlatforms = new ArrayList();
        this.mUseBasicChannel = 0;
    }

    protected TapShareRegisterBean(Parcel parcel) {
        ArrayList arrayList = new ArrayList();
        this.mSupportSharedPlatforms = arrayList;
        this.mUseBasicChannel = 0;
        parcel.readList(arrayList, Integer.class.getClassLoader());
        this.mUseBasicChannel = parcel.readInt();
    }

    public List<Integer> getSupportSharedPlatforms() {
        return this.mSupportSharedPlatforms;
    }

    public void setSupportSharedPlatforms(List<Integer> list) {
        this.mSupportSharedPlatforms = list;
    }

    public boolean getUseBasicChannel() {
        return this.mUseBasicChannel == 1;
    }

    private void setUseBasicChannel(int i) {
        this.mUseBasicChannel = i;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(this.mSupportSharedPlatforms);
        parcel.writeInt(this.mUseBasicChannel);
    }
}
