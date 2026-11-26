package com.miui.handwriting.common.config;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes3.dex */
public class ToolbarConfig implements Parcelable {
    public static final Parcelable.Creator<ToolbarConfig> CREATOR = new Parcelable.Creator<ToolbarConfig>() { // from class: com.miui.handwriting.common.config.ToolbarConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ToolbarConfig createFromParcel(Parcel parcel) {
            return new ToolbarConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ToolbarConfig[] newArray(int i) {
            return new ToolbarConfig[i];
        }
    };
    private int showShare;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected ToolbarConfig(Parcel parcel) {
        this.showShare = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.showShare);
    }

    public int getShowShare() {
        return this.showShare;
    }

    public void setShowShare(int i) {
        this.showShare = i;
    }
}
