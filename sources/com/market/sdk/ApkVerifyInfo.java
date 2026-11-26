package com.market.sdk;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes2.dex */
public class ApkVerifyInfo implements Parcelable {
    public static final Parcelable.Creator<ApkVerifyInfo> CREATOR = new Parcelable.Creator<ApkVerifyInfo>() { // from class: com.market.sdk.ApkVerifyInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ApkVerifyInfo createFromParcel(Parcel parcel) {
            return new ApkVerifyInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ApkVerifyInfo[] newArray(int i) {
            return new ApkVerifyInfo[i];
        }
    };
    public static final int STATUS_CONNECTION_FAILED = 3;
    public static final int STATUS_INCONSISTENT_CERTIFICATES = 2;
    public static final int STATUS_NOT_INCLUDED = 4;
    public static final int STATUS_OK = 0;
    public static final int STATUS_OLDER = 1;
    public static final int STATUS_PROCESSING = 6;
    public static final int STATUS_RESULT_INVALID = 5;
    public String mAppId;
    public String mAppName;
    public String mInstallerName;
    public Intent mIntent;
    public boolean mNeedFullScan;
    public long mNonce;
    public String mPackageName;
    public int mStatus;
    public long mTimeStamp;
    public String mToken;
    public String mUpdateLog;
    public long mUpdateTime;
    public int mVersionCode;
    public String mVersionName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ApkVerifyInfo() {
        this.mStatus = 4;
        this.mVersionName = "";
        this.mVersionCode = 0;
        this.mUpdateTime = 0L;
        this.mUpdateLog = "";
        this.mNonce = 0L;
        this.mTimeStamp = 0L;
        this.mAppName = "";
        this.mInstallerName = "";
        this.mAppId = "";
        this.mPackageName = "";
        this.mToken = "";
    }

    public ApkVerifyInfo(int i) {
        this.mVersionName = "";
        this.mVersionCode = 0;
        this.mUpdateTime = 0L;
        this.mUpdateLog = "";
        this.mNonce = 0L;
        this.mTimeStamp = 0L;
        this.mAppName = "";
        this.mInstallerName = "";
        this.mAppId = "";
        this.mPackageName = "";
        this.mToken = "";
        this.mStatus = i;
    }

    public ApkVerifyInfo(Parcel parcel) {
        this.mStatus = 4;
        this.mVersionName = "";
        this.mVersionCode = 0;
        this.mUpdateTime = 0L;
        this.mUpdateLog = "";
        this.mNonce = 0L;
        this.mTimeStamp = 0L;
        this.mAppName = "";
        this.mInstallerName = "";
        this.mAppId = "";
        this.mPackageName = "";
        this.mToken = "";
        this.mStatus = parcel.readInt();
        this.mVersionName = parcel.readString();
        this.mVersionCode = parcel.readInt();
        this.mUpdateTime = parcel.readLong();
        this.mUpdateLog = parcel.readString();
        this.mNonce = parcel.readLong();
        this.mTimeStamp = parcel.readLong();
        this.mAppName = parcel.readString();
        this.mInstallerName = parcel.readString();
        this.mAppId = parcel.readString();
        this.mPackageName = parcel.readString();
        this.mIntent = (Intent) parcel.readParcelable(null);
    }

    public boolean canReplaceByAppStore() {
        int i = this.mStatus;
        return i == 0 || i == 1 || i == 2;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mStatus);
        parcel.writeString(this.mVersionName);
        parcel.writeInt(this.mVersionCode);
        parcel.writeLong(this.mUpdateTime);
        parcel.writeString(this.mUpdateLog);
        parcel.writeLong(this.mNonce);
        parcel.writeLong(this.mTimeStamp);
        parcel.writeString(this.mAppName);
        parcel.writeString(this.mInstallerName);
        parcel.writeString(this.mAppId);
        parcel.writeString(this.mPackageName);
        parcel.writeParcelable(this.mIntent, 0);
    }
}
