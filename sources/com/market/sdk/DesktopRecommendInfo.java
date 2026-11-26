package com.market.sdk;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.SerializedName;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class DesktopRecommendInfo implements Parcelable {
    public static final Parcelable.Creator<DesktopRecommendInfo> CREATOR = new Parcelable.Creator<DesktopRecommendInfo>() { // from class: com.market.sdk.DesktopRecommendInfo.3
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DesktopRecommendInfo createFromParcel(Parcel parcel) {
            return new DesktopRecommendInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DesktopRecommendInfo[] newArray(int i) {
            return new DesktopRecommendInfo[i];
        }
    };
    public static final long DEFAULT_CACHE_TIME = 300000;

    @SerializedName("appInfoList")
    public List<AppstoreAppInfo> appInfoList;

    @SerializedName("backgroundImageUrl")
    public String backgroundImageUrl;

    @SerializedName("bannerList")
    public List<AdsBannerInfo> bannerList;

    @SerializedName("cacheTime")
    public long cacheTime;

    @SerializedName("description")
    public String description;

    @SerializedName("folderId")
    public long folderId;

    @SerializedName("sid")
    public String sid;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public DesktopRecommendInfo() {
        this.folderId = -1L;
        this.appInfoList = new ArrayList();
        this.bannerList = new ArrayList();
        this.backgroundImageUrl = "";
        this.description = "";
        this.sid = "";
    }

    public DesktopRecommendInfo(Parcel parcel) {
        this.folderId = -1L;
        this.appInfoList = new ArrayList();
        this.bannerList = new ArrayList();
        this.backgroundImageUrl = "";
        this.description = "";
        this.sid = "";
        this.folderId = parcel.readLong();
        parcel.readTypedList(this.appInfoList, AppstoreAppInfo.CREATOR);
        parcel.readTypedList(this.bannerList, AdsBannerInfo.CREATOR);
        this.backgroundImageUrl = parcel.readString();
        this.description = parcel.readString();
        this.sid = parcel.readString();
        this.cacheTime = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.folderId);
        parcel.writeTypedList(this.appInfoList);
        parcel.writeTypedList(this.bannerList);
        parcel.writeString(this.backgroundImageUrl);
        parcel.writeString(this.description);
        parcel.writeString(this.sid);
        parcel.writeLong(this.cacheTime);
    }

    public String convertToJson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Uri.class, new JsonSerializer<Uri>() { // from class: com.market.sdk.DesktopRecommendInfo.1
            @Override // com.google.gson.JsonSerializer
            public JsonElement serialize(Uri uri, Type type, JsonSerializationContext jsonSerializationContext) {
                return new JsonPrimitive(uri.toString());
            }
        });
        return gsonBuilder.create().toJson(this);
    }

    public static DesktopRecommendInfo restore(String str) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Uri.class, new JsonDeserializer<Uri>() { // from class: com.market.sdk.DesktopRecommendInfo.2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.google.gson.JsonDeserializer
            public Uri deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                return Uri.parse(jsonElement.getAsJsonPrimitive().getAsString());
            }
        });
        return (DesktopRecommendInfo) gsonBuilder.create().fromJson(str, DesktopRecommendInfo.class);
    }
}
