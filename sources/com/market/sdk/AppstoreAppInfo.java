package com.market.sdk;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.google.gson.annotations.SerializedName;
import com.market.sdk.utils.VersionUtils;
import java.util.ArrayList;
import java.util.List;
import miui.os.Build;

/* JADX INFO: loaded from: classes2.dex */
public class AppstoreAppInfo implements Parcelable {
    public static final Parcelable.Creator<AppstoreAppInfo> CREATOR;
    private static final long FLAG_MASK_SHOW_AD_FLAG = 1;
    private static final int SHOW_AD_FLAG = 0;
    private static boolean sNeedInmobiParamter;

    @SerializedName("adInfoPassback")
    public String adInfoPassback;

    @SerializedName("ads")
    public int ads;

    @SerializedName("appId")
    public String appId;

    @SerializedName("appUri")
    public Uri appUri;

    @SerializedName("digest")
    public String digest;

    @SerializedName("experimentalId")
    public String experimentalId;

    @SerializedName("iconMask")
    public String iconMask;

    @SerializedName("iconUri")
    public Uri iconUri;

    @SerializedName("mApkBriefDescription")
    private String mApkBriefDescription;

    @SerializedName("mParameters")
    private String mParameters;

    @SerializedName("pkgName")
    public String pkgName;

    @SerializedName("title")
    public String title;

    @SerializedName("mApkSize")
    private long mApkSize = -1;

    @SerializedName("viewMonitorUrls")
    public List<String> viewMonitorUrls = new ArrayList();

    @SerializedName("clickMonitorUrls")
    public List<String> clickMonitorUrls = new ArrayList();

    @SerializedName("impressionMonitorUrls")
    public List<String> impressionMonitorUrls = new ArrayList();

    @SerializedName("mFlag")
    private volatile long mFlag = -1;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    static {
        try {
            if (Build.IS_DEVELOPMENT_VERSION) {
                sNeedInmobiParamter = VersionUtils.isDevVersionLaterThan("6.3.21");
            } else if (Build.IS_STABLE_VERSION) {
                sNeedInmobiParamter = VersionUtils.isStableVersionLaterThan("V7.3.0.0");
            }
        } catch (Throwable th) {
            Log.e(MarketManager.TAG, th.toString());
        }
        CREATOR = new Parcelable.Creator<AppstoreAppInfo>() { // from class: com.market.sdk.AppstoreAppInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AppstoreAppInfo createFromParcel(Parcel parcel) {
                return new AppstoreAppInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AppstoreAppInfo[] newArray(int i) {
                return new AppstoreAppInfo[i];
            }
        };
    }

    public AppstoreAppInfo() {
    }

    public String getBriefDescription() {
        return this.mApkBriefDescription;
    }

    public void setBriefDescription(String str) {
        this.mApkBriefDescription = str;
    }

    public long getApkSize() {
        return this.mApkSize;
    }

    public void setApkSize(long j) {
        this.mApkSize = j;
    }

    public String getParameters() {
        return this.mParameters;
    }

    public void setParameters(String str) {
        this.mParameters = str;
    }

    public AppstoreAppInfo(Parcel parcel) {
        this.appId = parcel.readString();
        this.pkgName = parcel.readString();
        this.title = parcel.readString();
        this.ads = parcel.readInt();
        this.digest = parcel.readString();
        this.experimentalId = parcel.readString();
        this.iconMask = parcel.readString();
        this.iconUri = (Uri) Uri.CREATOR.createFromParcel(parcel);
        this.appUri = (Uri) Uri.CREATOR.createFromParcel(parcel);
        if (sNeedInmobiParamter) {
            parcel.readStringList(this.viewMonitorUrls);
            parcel.readStringList(this.clickMonitorUrls);
            parcel.readStringList(this.impressionMonitorUrls);
            this.adInfoPassback = parcel.readString();
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.appId);
        parcel.writeString(this.pkgName);
        parcel.writeString(this.title);
        parcel.writeInt(this.ads);
        parcel.writeString(this.digest);
        parcel.writeString(this.experimentalId);
        parcel.writeString(this.iconMask);
        Uri.writeToParcel(parcel, this.iconUri);
        Uri.writeToParcel(parcel, this.appUri);
        if (sNeedInmobiParamter) {
            parcel.writeStringList(this.viewMonitorUrls);
            parcel.writeStringList(this.clickMonitorUrls);
            parcel.writeStringList(this.impressionMonitorUrls);
            parcel.writeString(this.adInfoPassback);
        }
    }

    public boolean shouldShowAdFlag() {
        return this.ads == 1 && (getFlag() & 1) == 0;
    }

    private long getFlag() {
        if (this.mFlag != -1) {
            return this.mFlag;
        }
        Uri uri = this.appUri;
        long j = 0;
        if (uri != null) {
            try {
                j = Long.parseLong(uri.getQueryParameter("config"));
            } catch (Exception unused) {
            }
        }
        this.mFlag = j;
        return this.mFlag;
    }
}
