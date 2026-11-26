package com.miui.handwriting.common.ai.data;

import android.os.Parcel;
import android.os.Parcelable;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import com.xiaomi.mirror.synergy.CallMethod;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ReferenceData.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0014\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0004\b\t\u0010\nJ\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0018\u001a\u00020\bHÆ\u0003J1\u0010\u0019\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\bHÆ\u0001J\u0006\u0010\u001a\u001a\u00020\u0003J\u0013\u0010\u001b\u001a\u00020\b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dHÖ\u0003J\t\u0010\u001e\u001a\u00020\u0003HÖ\u0001J\t\u0010\u001f\u001a\u00020\u0005HÖ\u0001J\u0016\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020\u0003R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\u0012\"\u0004\b\u0013\u0010\u0014¨\u0006%"}, d2 = {"Lcom/miui/handwriting/common/ai/data/ReferenceItemData;", "Landroid/os/Parcelable;", "type", "", "content", "", CallMethod.RESULT_ICON, "isSelect", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(ILjava/lang/String;Ljava/lang/String;Z)V", "getType", "()I", "setType", "(I)V", "getContent", "()Ljava/lang/String;", "getIcon", "()Z", "setSelect", "(Z)V", "component1", "component2", "component3", "component4", "copy", "describeContents", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "toString", "writeToParcel", "", "dest", "Landroid/os/Parcel;", "flags", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class ReferenceItemData implements Parcelable {
    public static final Parcelable.Creator<ReferenceItemData> CREATOR = new Creator();
    private final String content;
    private final String icon;
    private boolean isSelect;
    private int type;

    /* JADX INFO: compiled from: ReferenceData.kt */
    @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
    public static final class Creator implements Parcelable.Creator<ReferenceItemData> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final ReferenceItemData createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            return new ReferenceItemData(parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readInt() != 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final ReferenceItemData[] newArray(int i) {
            return new ReferenceItemData[i];
        }
    }

    public static /* synthetic */ ReferenceItemData copy$default(ReferenceItemData referenceItemData, int i, String str, String str2, boolean z, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = referenceItemData.type;
        }
        if ((i2 & 2) != 0) {
            str = referenceItemData.content;
        }
        if ((i2 & 4) != 0) {
            str2 = referenceItemData.icon;
        }
        if ((i2 & 8) != 0) {
            z = referenceItemData.isSelect;
        }
        return referenceItemData.copy(i, str, str2, z);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final int getType() {
        return this.type;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getContent() {
        return this.content;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getIcon() {
        return this.icon;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final boolean getIsSelect() {
        return this.isSelect;
    }

    public final ReferenceItemData copy(int type, String content, String icon, boolean isSelect) {
        Intrinsics.checkNotNullParameter(content, "content");
        Intrinsics.checkNotNullParameter(icon, "icon");
        return new ReferenceItemData(type, content, icon, isSelect);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ReferenceItemData)) {
            return false;
        }
        ReferenceItemData referenceItemData = (ReferenceItemData) other;
        return this.type == referenceItemData.type && Intrinsics.areEqual(this.content, referenceItemData.content) && Intrinsics.areEqual(this.icon, referenceItemData.icon) && this.isSelect == referenceItemData.isSelect;
    }

    public int hashCode() {
        return (((((Integer.hashCode(this.type) * 31) + this.content.hashCode()) * 31) + this.icon.hashCode()) * 31) + Boolean.hashCode(this.isSelect);
    }

    public String toString() {
        return "ReferenceItemData(type=" + this.type + ", content=" + this.content + ", icon=" + this.icon + ", isSelect=" + this.isSelect + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel dest, int flags) {
        Intrinsics.checkNotNullParameter(dest, "dest");
        dest.writeInt(this.type);
        dest.writeString(this.content);
        dest.writeString(this.icon);
        dest.writeInt(this.isSelect ? 1 : 0);
    }

    public ReferenceItemData(int i, String content, String icon, boolean z) {
        Intrinsics.checkNotNullParameter(content, "content");
        Intrinsics.checkNotNullParameter(icon, "icon");
        this.type = i;
        this.content = content;
        this.icon = icon;
        this.isSelect = z;
    }

    public /* synthetic */ ReferenceItemData(int i, String str, String str2, boolean z, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, str, str2, (i2 & 8) != 0 ? false : z);
    }

    public final int getType() {
        return this.type;
    }

    public final void setType(int i) {
        this.type = i;
    }

    public final String getContent() {
        return this.content;
    }

    public final String getIcon() {
        return this.icon;
    }

    public final boolean isSelect() {
        return this.isSelect;
    }

    public final void setSelect(boolean z) {
        this.isSelect = z;
    }
}
