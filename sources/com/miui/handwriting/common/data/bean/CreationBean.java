package com.miui.handwriting.common.data.bean;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.creation.common.R;
import com.miui.doodle.feature.draw.DoodleBitmap;
import com.miui.handwriting.common.ai.data.CreationImage;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CreationBean.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b#\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001BS\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u000e\u0010\u000fJ\u0006\u0010$\u001a\u00020\u0003J\t\u0010%\u001a\u00020\u0003HÆ\u0003J\t\u0010&\u001a\u00020\u0005HÆ\u0003J\t\u0010'\u001a\u00020\u0007HÆ\u0003J\t\u0010(\u001a\u00020\u0007HÆ\u0003J\u000b\u0010)\u001a\u0004\u0018\u00010\nHÆ\u0003J\u000b\u0010*\u001a\u0004\u0018\u00010\fHÆ\u0003J\u000b\u0010+\u001a\u0004\u0018\u00010\u0005HÆ\u0003JU\u0010,\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0006\u0010-\u001a\u00020\u0007J\u0013\u0010.\u001a\u00020\u00032\b\u0010/\u001a\u0004\u0018\u000100HÖ\u0003J\t\u00101\u001a\u00020\u0007HÖ\u0001J\t\u00102\u001a\u00020\u0005HÖ\u0001J\u0016\u00103\u001a\u0002042\u0006\u00105\u001a\u0002062\u0006\u00107\u001a\u00020\u0007R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0002\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\b\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0018R\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u001c\u0010\r\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0014\"\u0004\b#\u0010\u0016¨\u00068"}, d2 = {"Lcom/miui/handwriting/common/data/bean/CreationBean;", "Landroid/os/Parcelable;", "isCenter", "", "resourceId", "", "type", "", "demoImage", "wrapData", "Lcom/miui/handwriting/common/ai/data/CreationImage;", DoodleBitmap.KEY_BITMAP, "Landroid/graphics/Bitmap;", "preResourceId", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(ZLjava/lang/String;IILcom/miui/handwriting/common/ai/data/CreationImage;Landroid/graphics/Bitmap;Ljava/lang/String;)V", "()Z", "setCenter", "(Z)V", "getResourceId", "()Ljava/lang/String;", "setResourceId", "(Ljava/lang/String;)V", "getType", "()I", "getDemoImage", "getWrapData", "()Lcom/miui/handwriting/common/ai/data/CreationImage;", "setWrapData", "(Lcom/miui/handwriting/common/ai/data/CreationImage;)V", "getBitmap", "()Landroid/graphics/Bitmap;", "setBitmap", "(Landroid/graphics/Bitmap;)V", "getPreResourceId", "setPreResourceId", "isThanking", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "describeContents", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "toString", "writeToParcel", "", "dest", "Landroid/os/Parcel;", "flags", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class CreationBean implements Parcelable {
    public static final Parcelable.Creator<CreationBean> CREATOR = new Creator();
    private Bitmap bitmap;
    private final int demoImage;
    private boolean isCenter;
    private String preResourceId;
    private String resourceId;
    private final int type;
    private CreationImage wrapData;

    /* JADX INFO: compiled from: CreationBean.kt */
    @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
    public static final class Creator implements Parcelable.Creator<CreationBean> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final CreationBean createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            return new CreationBean(parcel.readInt() != 0, parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt() == 0 ? null : CreationImage.CREATOR.createFromParcel(parcel), (Bitmap) parcel.readParcelable(CreationBean.class.getClassLoader()), parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final CreationBean[] newArray(int i) {
            return new CreationBean[i];
        }
    }

    public CreationBean() {
        this(false, null, 0, 0, null, null, null, 127, null);
    }

    public static /* synthetic */ CreationBean copy$default(CreationBean creationBean, boolean z, String str, int i, int i2, CreationImage creationImage, Bitmap bitmap, String str2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            z = creationBean.isCenter;
        }
        if ((i3 & 2) != 0) {
            str = creationBean.resourceId;
        }
        if ((i3 & 4) != 0) {
            i = creationBean.type;
        }
        if ((i3 & 8) != 0) {
            i2 = creationBean.demoImage;
        }
        if ((i3 & 16) != 0) {
            creationImage = creationBean.wrapData;
        }
        if ((i3 & 32) != 0) {
            bitmap = creationBean.bitmap;
        }
        if ((i3 & 64) != 0) {
            str2 = creationBean.preResourceId;
        }
        Bitmap bitmap2 = bitmap;
        String str3 = str2;
        CreationImage creationImage2 = creationImage;
        int i4 = i;
        return creationBean.copy(z, str, i4, i2, creationImage2, bitmap2, str3);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final boolean getIsCenter() {
        return this.isCenter;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getResourceId() {
        return this.resourceId;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final int getType() {
        return this.type;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final int getDemoImage() {
        return this.demoImage;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final CreationImage getWrapData() {
        return this.wrapData;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final Bitmap getBitmap() {
        return this.bitmap;
    }

    /* JADX INFO: renamed from: component7, reason: from getter */
    public final String getPreResourceId() {
        return this.preResourceId;
    }

    public final CreationBean copy(boolean isCenter, String resourceId, int type, int demoImage, CreationImage wrapData, Bitmap bitmap, String preResourceId) {
        Intrinsics.checkNotNullParameter(resourceId, "resourceId");
        return new CreationBean(isCenter, resourceId, type, demoImage, wrapData, bitmap, preResourceId);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CreationBean)) {
            return false;
        }
        CreationBean creationBean = (CreationBean) other;
        return this.isCenter == creationBean.isCenter && Intrinsics.areEqual(this.resourceId, creationBean.resourceId) && this.type == creationBean.type && this.demoImage == creationBean.demoImage && Intrinsics.areEqual(this.wrapData, creationBean.wrapData) && Intrinsics.areEqual(this.bitmap, creationBean.bitmap) && Intrinsics.areEqual(this.preResourceId, creationBean.preResourceId);
    }

    public int hashCode() {
        int iHashCode = ((((((Boolean.hashCode(this.isCenter) * 31) + this.resourceId.hashCode()) * 31) + Integer.hashCode(this.type)) * 31) + Integer.hashCode(this.demoImage)) * 31;
        CreationImage creationImage = this.wrapData;
        int iHashCode2 = (iHashCode + (creationImage == null ? 0 : creationImage.hashCode())) * 31;
        Bitmap bitmap = this.bitmap;
        int iHashCode3 = (iHashCode2 + (bitmap == null ? 0 : bitmap.hashCode())) * 31;
        String str = this.preResourceId;
        return iHashCode3 + (str != null ? str.hashCode() : 0);
    }

    public String toString() {
        return "CreationBean(isCenter=" + this.isCenter + ", resourceId=" + this.resourceId + ", type=" + this.type + ", demoImage=" + this.demoImage + ", wrapData=" + this.wrapData + ", bitmap=" + this.bitmap + ", preResourceId=" + this.preResourceId + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel dest, int flags) {
        Intrinsics.checkNotNullParameter(dest, "dest");
        dest.writeInt(this.isCenter ? 1 : 0);
        dest.writeString(this.resourceId);
        dest.writeInt(this.type);
        dest.writeInt(this.demoImage);
        CreationImage creationImage = this.wrapData;
        if (creationImage == null) {
            dest.writeInt(0);
        } else {
            dest.writeInt(1);
            creationImage.writeToParcel(dest, flags);
        }
        dest.writeParcelable(this.bitmap, flags);
        dest.writeString(this.preResourceId);
    }

    public CreationBean(boolean z, String resourceId, int i, int i2, CreationImage creationImage, Bitmap bitmap, String str) {
        Intrinsics.checkNotNullParameter(resourceId, "resourceId");
        this.isCenter = z;
        this.resourceId = resourceId;
        this.type = i;
        this.demoImage = i2;
        this.wrapData = creationImage;
        this.bitmap = bitmap;
        this.preResourceId = str;
    }

    public final boolean isCenter() {
        return this.isCenter;
    }

    public final void setCenter(boolean z) {
        this.isCenter = z;
    }

    public /* synthetic */ CreationBean(boolean z, String str, int i, int i2, CreationImage creationImage, Bitmap bitmap, String str2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? false : z, (i3 & 2) != 0 ? "" : str, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? R.drawable.ai_thing : i2, (i3 & 16) != 0 ? null : creationImage, (i3 & 32) != 0 ? null : bitmap, (i3 & 64) != 0 ? null : str2);
    }

    public final String getResourceId() {
        return this.resourceId;
    }

    public final void setResourceId(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.resourceId = str;
    }

    public final int getType() {
        return this.type;
    }

    public final int getDemoImage() {
        return this.demoImage;
    }

    public final CreationImage getWrapData() {
        return this.wrapData;
    }

    public final void setWrapData(CreationImage creationImage) {
        this.wrapData = creationImage;
    }

    public final Bitmap getBitmap() {
        return this.bitmap;
    }

    public final void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public final String getPreResourceId() {
        return this.preResourceId;
    }

    public final void setPreResourceId(String str) {
        this.preResourceId = str;
    }

    public final boolean isThanking() {
        return this.type == 3;
    }
}
