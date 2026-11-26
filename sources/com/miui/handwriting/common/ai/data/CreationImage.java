package com.miui.handwriting.common.ai.data;

import android.os.Parcel;
import android.os.Parcelable;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.handwriting.common.ai.viewmodel.cache.RecreateInfo;
import com.xiaomi.mirror.RemoteDeviceInfo;
import com.xiaomi.mirror.synergy.CallMethod;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CreationImage.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b4\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B{\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\u0012\u0010\u0013J\b\u00105\u001a\u00020\u0003H\u0016J\t\u00106\u001a\u00020\u0003HÆ\u0003J\u000b\u00107\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u0010\u00108\u001a\u0004\u0018\u00010\u0006HÆ\u0003¢\u0006\u0002\u0010\u001bJ\u000b\u00109\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010:\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010;\u001a\u0004\u0018\u00010\nHÆ\u0003J\u000b\u0010<\u001a\u0004\u0018\u00010\fHÆ\u0003J\u000b\u0010=\u001a\u0004\u0018\u00010\u000eHÆ\u0003J\u0010\u0010>\u001a\u0004\u0018\u00010\u0010HÆ\u0003¢\u0006\u0002\u0010/J\u000b\u0010?\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u0084\u0001\u0010@\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00102\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0003HÆ\u0001¢\u0006\u0002\u0010AJ\u0006\u0010B\u001a\u00020\u0006J\u0013\u0010C\u001a\u00020\u00102\b\u0010D\u001a\u0004\u0018\u00010EHÖ\u0003J\t\u0010F\u001a\u00020\u0006HÖ\u0001J\u0016\u0010G\u001a\u00020H2\u0006\u0010I\u001a\u00020J2\u0006\u0010K\u001a\u00020\u0006R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0015\"\u0004\b\u0019\u0010\u0017R\u001e\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u001e\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001c\u0010\u0007\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0015\"\u0004\b \u0010\u0017R\u001c\u0010\b\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u0015\"\u0004\b\"\u0010\u0017R\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*R\u001c\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R\u001e\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u0010\n\u0002\u00102\u001a\u0004\b\u000f\u0010/\"\u0004\b0\u00101R\u001c\u0010\u0011\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b3\u0010\u0015\"\u0004\b4\u0010\u0017¨\u0006L"}, d2 = {"Lcom/miui/handwriting/common/ai/data/CreationImage;", "Landroid/os/Parcelable;", "imageUrl", "", "prompt", "themeId", "", "theme", CallMethod.RESULT_ICON, "referenceItem", "Lcom/miui/handwriting/common/ai/data/ReferenceItem;", "themeItem", "Lcom/miui/handwriting/common/ai/data/ThemeItem;", "recreateInfo", "Lcom/miui/handwriting/common/ai/viewmodel/cache/RecreateInfo;", "isBigImage", "", "imageUrlBig", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Lcom/miui/handwriting/common/ai/data/ReferenceItem;Lcom/miui/handwriting/common/ai/data/ThemeItem;Lcom/miui/handwriting/common/ai/viewmodel/cache/RecreateInfo;Ljava/lang/Boolean;Ljava/lang/String;)V", "getImageUrl", "()Ljava/lang/String;", "setImageUrl", "(Ljava/lang/String;)V", "getPrompt", "setPrompt", "getThemeId", "()Ljava/lang/Integer;", "setThemeId", "(Ljava/lang/Integer;)V", "Ljava/lang/Integer;", "getTheme", "setTheme", "getIcon", "setIcon", "getReferenceItem", "()Lcom/miui/handwriting/common/ai/data/ReferenceItem;", "setReferenceItem", "(Lcom/miui/handwriting/common/ai/data/ReferenceItem;)V", "getThemeItem", "()Lcom/miui/handwriting/common/ai/data/ThemeItem;", "setThemeItem", "(Lcom/miui/handwriting/common/ai/data/ThemeItem;)V", "getRecreateInfo", "()Lcom/miui/handwriting/common/ai/viewmodel/cache/RecreateInfo;", "setRecreateInfo", "(Lcom/miui/handwriting/common/ai/viewmodel/cache/RecreateInfo;)V", "()Ljava/lang/Boolean;", "setBigImage", "(Ljava/lang/Boolean;)V", "Ljava/lang/Boolean;", "getImageUrlBig", "setImageUrlBig", "toString", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "component10", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Lcom/miui/handwriting/common/ai/data/ReferenceItem;Lcom/miui/handwriting/common/ai/data/ThemeItem;Lcom/miui/handwriting/common/ai/viewmodel/cache/RecreateInfo;Ljava/lang/Boolean;Ljava/lang/String;)Lcom/miui/handwriting/common/ai/data/CreationImage;", "describeContents", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "writeToParcel", "", "dest", "Landroid/os/Parcel;", "flags", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class CreationImage implements Parcelable {
    public static final Parcelable.Creator<CreationImage> CREATOR = new Creator();
    private String icon;
    private String imageUrl;
    private String imageUrlBig;
    private Boolean isBigImage;
    private String prompt;
    private RecreateInfo recreateInfo;
    private ReferenceItem referenceItem;
    private String theme;
    private Integer themeId;
    private ThemeItem themeItem;

    /* JADX INFO: compiled from: CreationImage.kt */
    @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
    public static final class Creator implements Parcelable.Creator<CreationImage> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final CreationImage createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            String string = parcel.readString();
            String string2 = parcel.readString();
            Boolean boolValueOf = null;
            Integer numValueOf = parcel.readInt() == 0 ? null : Integer.valueOf(parcel.readInt());
            String string3 = parcel.readString();
            String string4 = parcel.readString();
            ReferenceItem referenceItemCreateFromParcel = parcel.readInt() == 0 ? null : ReferenceItem.CREATOR.createFromParcel(parcel);
            ThemeItem themeItemCreateFromParcel = parcel.readInt() == 0 ? null : ThemeItem.CREATOR.createFromParcel(parcel);
            RecreateInfo recreateInfoCreateFromParcel = parcel.readInt() == 0 ? null : RecreateInfo.CREATOR.createFromParcel(parcel);
            if (parcel.readInt() != 0) {
                boolValueOf = Boolean.valueOf(parcel.readInt() != 0);
            }
            return new CreationImage(string, string2, numValueOf, string3, string4, referenceItemCreateFromParcel, themeItemCreateFromParcel, recreateInfoCreateFromParcel, boolValueOf, parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final CreationImage[] newArray(int i) {
            return new CreationImage[i];
        }
    }

    public static /* synthetic */ CreationImage copy$default(CreationImage creationImage, String str, String str2, Integer num, String str3, String str4, ReferenceItem referenceItem, ThemeItem themeItem, RecreateInfo recreateInfo, Boolean bool, String str5, int i, Object obj) {
        if ((i & 1) != 0) {
            str = creationImage.imageUrl;
        }
        if ((i & 2) != 0) {
            str2 = creationImage.prompt;
        }
        if ((i & 4) != 0) {
            num = creationImage.themeId;
        }
        if ((i & 8) != 0) {
            str3 = creationImage.theme;
        }
        if ((i & 16) != 0) {
            str4 = creationImage.icon;
        }
        if ((i & 32) != 0) {
            referenceItem = creationImage.referenceItem;
        }
        if ((i & 64) != 0) {
            themeItem = creationImage.themeItem;
        }
        if ((i & 128) != 0) {
            recreateInfo = creationImage.recreateInfo;
        }
        if ((i & 256) != 0) {
            bool = creationImage.isBigImage;
        }
        if ((i & 512) != 0) {
            str5 = creationImage.imageUrlBig;
        }
        Boolean bool2 = bool;
        String str6 = str5;
        ThemeItem themeItem2 = themeItem;
        RecreateInfo recreateInfo2 = recreateInfo;
        String str7 = str4;
        ReferenceItem referenceItem2 = referenceItem;
        return creationImage.copy(str, str2, num, str3, str7, referenceItem2, themeItem2, recreateInfo2, bool2, str6);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getImageUrl() {
        return this.imageUrl;
    }

    /* JADX INFO: renamed from: component10, reason: from getter */
    public final String getImageUrlBig() {
        return this.imageUrlBig;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getPrompt() {
        return this.prompt;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final Integer getThemeId() {
        return this.themeId;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final String getTheme() {
        return this.theme;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final String getIcon() {
        return this.icon;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final ReferenceItem getReferenceItem() {
        return this.referenceItem;
    }

    /* JADX INFO: renamed from: component7, reason: from getter */
    public final ThemeItem getThemeItem() {
        return this.themeItem;
    }

    /* JADX INFO: renamed from: component8, reason: from getter */
    public final RecreateInfo getRecreateInfo() {
        return this.recreateInfo;
    }

    /* JADX INFO: renamed from: component9, reason: from getter */
    public final Boolean getIsBigImage() {
        return this.isBigImage;
    }

    public final CreationImage copy(String imageUrl, String prompt, Integer themeId, String theme, String icon, ReferenceItem referenceItem, ThemeItem themeItem, RecreateInfo recreateInfo, Boolean isBigImage, String imageUrlBig) {
        Intrinsics.checkNotNullParameter(imageUrl, "imageUrl");
        return new CreationImage(imageUrl, prompt, themeId, theme, icon, referenceItem, themeItem, recreateInfo, isBigImage, imageUrlBig);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CreationImage)) {
            return false;
        }
        CreationImage creationImage = (CreationImage) other;
        return Intrinsics.areEqual(this.imageUrl, creationImage.imageUrl) && Intrinsics.areEqual(this.prompt, creationImage.prompt) && Intrinsics.areEqual(this.themeId, creationImage.themeId) && Intrinsics.areEqual(this.theme, creationImage.theme) && Intrinsics.areEqual(this.icon, creationImage.icon) && Intrinsics.areEqual(this.referenceItem, creationImage.referenceItem) && Intrinsics.areEqual(this.themeItem, creationImage.themeItem) && Intrinsics.areEqual(this.recreateInfo, creationImage.recreateInfo) && Intrinsics.areEqual(this.isBigImage, creationImage.isBigImage) && Intrinsics.areEqual(this.imageUrlBig, creationImage.imageUrlBig);
    }

    public int hashCode() {
        int iHashCode = this.imageUrl.hashCode() * 31;
        String str = this.prompt;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        Integer num = this.themeId;
        int iHashCode3 = (iHashCode2 + (num == null ? 0 : num.hashCode())) * 31;
        String str2 = this.theme;
        int iHashCode4 = (iHashCode3 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.icon;
        int iHashCode5 = (iHashCode4 + (str3 == null ? 0 : str3.hashCode())) * 31;
        ReferenceItem referenceItem = this.referenceItem;
        int iHashCode6 = (iHashCode5 + (referenceItem == null ? 0 : referenceItem.hashCode())) * 31;
        ThemeItem themeItem = this.themeItem;
        int iHashCode7 = (iHashCode6 + (themeItem == null ? 0 : themeItem.hashCode())) * 31;
        RecreateInfo recreateInfo = this.recreateInfo;
        int iHashCode8 = (iHashCode7 + (recreateInfo == null ? 0 : recreateInfo.hashCode())) * 31;
        Boolean bool = this.isBigImage;
        int iHashCode9 = (iHashCode8 + (bool == null ? 0 : bool.hashCode())) * 31;
        String str4 = this.imageUrlBig;
        return iHashCode9 + (str4 != null ? str4.hashCode() : 0);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel dest, int flags) {
        Intrinsics.checkNotNullParameter(dest, "dest");
        dest.writeString(this.imageUrl);
        dest.writeString(this.prompt);
        Integer num = this.themeId;
        if (num == null) {
            dest.writeInt(0);
        } else {
            dest.writeInt(1);
            dest.writeInt(num.intValue());
        }
        dest.writeString(this.theme);
        dest.writeString(this.icon);
        ReferenceItem referenceItem = this.referenceItem;
        if (referenceItem == null) {
            dest.writeInt(0);
        } else {
            dest.writeInt(1);
            referenceItem.writeToParcel(dest, flags);
        }
        ThemeItem themeItem = this.themeItem;
        if (themeItem == null) {
            dest.writeInt(0);
        } else {
            dest.writeInt(1);
            themeItem.writeToParcel(dest, flags);
        }
        RecreateInfo recreateInfo = this.recreateInfo;
        if (recreateInfo == null) {
            dest.writeInt(0);
        } else {
            dest.writeInt(1);
            recreateInfo.writeToParcel(dest, flags);
        }
        Boolean bool = this.isBigImage;
        if (bool == null) {
            dest.writeInt(0);
        } else {
            dest.writeInt(1);
            dest.writeInt(bool.booleanValue() ? 1 : 0);
        }
        dest.writeString(this.imageUrlBig);
    }

    public CreationImage(String imageUrl, String str, Integer num, String str2, String str3, ReferenceItem referenceItem, ThemeItem themeItem, RecreateInfo recreateInfo, Boolean bool, String str4) {
        Intrinsics.checkNotNullParameter(imageUrl, "imageUrl");
        this.imageUrl = imageUrl;
        this.prompt = str;
        this.themeId = num;
        this.theme = str2;
        this.icon = str3;
        this.referenceItem = referenceItem;
        this.themeItem = themeItem;
        this.recreateInfo = recreateInfo;
        this.isBigImage = bool;
        this.imageUrlBig = str4;
    }

    public final String getImageUrl() {
        return this.imageUrl;
    }

    public final void setImageUrl(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.imageUrl = str;
    }

    public final String getPrompt() {
        return this.prompt;
    }

    public final void setPrompt(String str) {
        this.prompt = str;
    }

    public final Integer getThemeId() {
        return this.themeId;
    }

    public final void setThemeId(Integer num) {
        this.themeId = num;
    }

    public final String getTheme() {
        return this.theme;
    }

    public final void setTheme(String str) {
        this.theme = str;
    }

    public final String getIcon() {
        return this.icon;
    }

    public final void setIcon(String str) {
        this.icon = str;
    }

    public final ReferenceItem getReferenceItem() {
        return this.referenceItem;
    }

    public final void setReferenceItem(ReferenceItem referenceItem) {
        this.referenceItem = referenceItem;
    }

    public final ThemeItem getThemeItem() {
        return this.themeItem;
    }

    public final void setThemeItem(ThemeItem themeItem) {
        this.themeItem = themeItem;
    }

    public final RecreateInfo getRecreateInfo() {
        return this.recreateInfo;
    }

    public final void setRecreateInfo(RecreateInfo recreateInfo) {
        this.recreateInfo = recreateInfo;
    }

    public /* synthetic */ CreationImage(String str, String str2, Integer num, String str3, String str4, ReferenceItem referenceItem, ThemeItem themeItem, RecreateInfo recreateInfo, Boolean bool, String str5, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : str2, (i & 4) != 0 ? null : num, (i & 8) != 0 ? null : str3, (i & 16) != 0 ? null : str4, (i & 32) != 0 ? null : referenceItem, (i & 64) != 0 ? null : themeItem, (i & 128) != 0 ? null : recreateInfo, (i & 256) != 0 ? false : bool, (i & 512) != 0 ? null : str5);
    }

    public final Boolean isBigImage() {
        return this.isBigImage;
    }

    public final void setBigImage(Boolean bool) {
        this.isBigImage = bool;
    }

    public final String getImageUrlBig() {
        return this.imageUrlBig;
    }

    public final void setImageUrlBig(String str) {
        this.imageUrlBig = str;
    }

    public String toString() {
        return "imageUrl:" + this.imageUrl + ",prompt:" + this.prompt + ",theme:" + this.theme + ",icon:" + this.icon + ",referenceItem:" + this.referenceItem + ",themeItem:" + this.themeItem + ",reCreateInfo:" + this.recreateInfo + ",isBigImage:" + this.isBigImage + ",imageUrlBig:" + this.imageUrlBig + CoreConstants.COMMA_CHAR;
    }
}
