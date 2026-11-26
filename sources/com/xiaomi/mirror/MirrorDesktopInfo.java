package com.xiaomi.mirror;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import ch.qos.logback.core.CoreConstants;

/* JADX INFO: loaded from: classes4.dex */
public class MirrorDesktopInfo implements Parcelable {
    public static final Parcelable.Creator<MirrorDesktopInfo> CREATOR = new Parcelable.Creator<MirrorDesktopInfo>() { // from class: com.xiaomi.mirror.MirrorDesktopInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MirrorDesktopInfo createFromParcel(Parcel parcel) {
            return new MirrorDesktopInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MirrorDesktopInfo[] newArray(int i) {
            return new MirrorDesktopInfo[i];
        }
    };
    public static final String KEY_DESKTOP_HEIGHT = "desktop_height";
    public static final String KEY_DESKTOP_ORIENTATION = "desktop_orientation";
    public static final String KEY_DESKTOP_SEAT = "desktop_seat";
    public static final String KEY_DESKTOP_WIDTH = "desktop_width";
    public Bundle mBundle;

    public MirrorDesktopInfo() {
        this.mBundle = new Bundle();
    }

    public MirrorDesktopInfo(Parcel parcel) {
        this.mBundle = new Bundle();
        this.mBundle = parcel.readBundle(MirrorDesktopInfo.class.getClassLoader());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Bundle getBundle() {
        return this.mBundle;
    }

    public int getDesktopHeight() {
        return this.mBundle.getInt(KEY_DESKTOP_HEIGHT);
    }

    public int getDesktopOrientation() {
        return this.mBundle.getInt(KEY_DESKTOP_ORIENTATION);
    }

    public int getDesktopSeat() {
        return this.mBundle.getInt(KEY_DESKTOP_SEAT);
    }

    public int getDesktopWidth() {
        return this.mBundle.getInt(KEY_DESKTOP_WIDTH);
    }

    public String toString() {
        return "MirrorDesktopInfo{mBundle=" + this.mBundle + CoreConstants.CURLY_RIGHT;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBundle(this.mBundle);
    }
}
