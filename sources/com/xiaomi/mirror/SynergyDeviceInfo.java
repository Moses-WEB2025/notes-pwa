package com.xiaomi.mirror;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import ch.qos.logback.core.CoreConstants;

/* JADX INFO: loaded from: classes4.dex */
public class SynergyDeviceInfo implements Parcelable {
    public static final Parcelable.Creator<SynergyDeviceInfo> CREATOR = new Parcelable.Creator<SynergyDeviceInfo>() { // from class: com.xiaomi.mirror.SynergyDeviceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SynergyDeviceInfo createFromParcel(Parcel parcel) {
            return new SynergyDeviceInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SynergyDeviceInfo[] newArray(int i) {
            return new SynergyDeviceInfo[i];
        }
    };
    public static final String KEY_DISPLAY_NAME = "display_name";
    public static final String KEY_ID = "id";
    public Bundle mBundle;

    public SynergyDeviceInfo() {
        this.mBundle = new Bundle();
    }

    public SynergyDeviceInfo(Parcel parcel) {
        this.mBundle = new Bundle();
        this.mBundle = parcel.readBundle(SynergyDeviceInfo.class.getClassLoader());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Bundle getBundle() {
        return new Bundle(this.mBundle);
    }

    public String getDeviceId() {
        return this.mBundle.getString("id");
    }

    public String getDisplayName() {
        return this.mBundle.getString("display_name");
    }

    public String toString() {
        return "SynergyDeviceInfo{mBundle=" + this.mBundle + CoreConstants.CURLY_RIGHT;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBundle(this.mBundle);
    }
}
