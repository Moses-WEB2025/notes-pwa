package com.miui.handwriting.common.ai.data;

import android.os.Parcel;
import android.os.Parcelable;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import com.xiaomi.mirror.synergy.CallMethod;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ThemeItem.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0014\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0004\b\t\u0010\nJ\u0006\u0010\u0014\u001a\u00020\u0003J\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0018\u001a\u00020\bHÆ\u0003J1\u0010\u0019\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\bHÆ\u0001J\u0006\u0010\u001a\u001a\u00020\u0005J\u0013\u0010\u001b\u001a\u00020\b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dHÖ\u0003J\t\u0010\u001e\u001a\u00020\u0005HÖ\u0001J\t\u0010\u001f\u001a\u00020\u0003HÖ\u0001J\u0016\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020\u0005R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\fR\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013¨\u0006%"}, d2 = {"Lcom/miui/handwriting/common/ai/data/ThemeItem;", "Landroid/os/Parcelable;", "theme", "", "themeId", "", CallMethod.RESULT_ICON, "default", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;ILjava/lang/String;Z)V", "getTheme", "()Ljava/lang/String;", "getThemeId", "()I", "getIcon", "getDefault", "()Z", "setDefault", "(Z)V", "trackValue", "component1", "component2", "component3", "component4", "copy", "describeContents", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "toString", "writeToParcel", "", "dest", "Landroid/os/Parcel;", "flags", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class ThemeItem implements Parcelable {
    public static final Parcelable.Creator<ThemeItem> CREATOR = new Creator();
    private boolean default;
    private final String icon;
    private final String theme;
    private final int themeId;

    /* JADX INFO: compiled from: ThemeItem.kt */
    @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
    public static final class Creator implements Parcelable.Creator<ThemeItem> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final ThemeItem createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            return new ThemeItem(parcel.readString(), parcel.readInt(), parcel.readString(), parcel.readInt() != 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final ThemeItem[] newArray(int i) {
            return new ThemeItem[i];
        }
    }

    public static /* synthetic */ ThemeItem copy$default(ThemeItem themeItem, String str, int i, String str2, boolean z, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = themeItem.theme;
        }
        if ((i2 & 2) != 0) {
            i = themeItem.themeId;
        }
        if ((i2 & 4) != 0) {
            str2 = themeItem.icon;
        }
        if ((i2 & 8) != 0) {
            z = themeItem.default;
        }
        return themeItem.copy(str, i, str2, z);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getTheme() {
        return this.theme;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final int getThemeId() {
        return this.themeId;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getIcon() {
        return this.icon;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final boolean getDefault() {
        return this.default;
    }

    public final ThemeItem copy(String theme, int themeId, String icon, boolean z) {
        Intrinsics.checkNotNullParameter(theme, "theme");
        Intrinsics.checkNotNullParameter(icon, "icon");
        return new ThemeItem(theme, themeId, icon, z);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ThemeItem)) {
            return false;
        }
        ThemeItem themeItem = (ThemeItem) other;
        return Intrinsics.areEqual(this.theme, themeItem.theme) && this.themeId == themeItem.themeId && Intrinsics.areEqual(this.icon, themeItem.icon) && this.default == themeItem.default;
    }

    public int hashCode() {
        return (((((this.theme.hashCode() * 31) + Integer.hashCode(this.themeId)) * 31) + this.icon.hashCode()) * 31) + Boolean.hashCode(this.default);
    }

    public String toString() {
        return "ThemeItem(theme=" + this.theme + ", themeId=" + this.themeId + ", icon=" + this.icon + ", default=" + this.default + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel dest, int flags) {
        Intrinsics.checkNotNullParameter(dest, "dest");
        dest.writeString(this.theme);
        dest.writeInt(this.themeId);
        dest.writeString(this.icon);
        dest.writeInt(this.default ? 1 : 0);
    }

    public ThemeItem(String theme, int i, String icon, boolean z) {
        Intrinsics.checkNotNullParameter(theme, "theme");
        Intrinsics.checkNotNullParameter(icon, "icon");
        this.theme = theme;
        this.themeId = i;
        this.icon = icon;
        this.default = z;
    }

    public final String getTheme() {
        return this.theme;
    }

    public final int getThemeId() {
        return this.themeId;
    }

    public final String getIcon() {
        return this.icon;
    }

    public final boolean getDefault() {
        return this.default;
    }

    public final void setDefault(boolean z) {
        this.default = z;
    }

    public final String trackValue() {
        switch (this.themeId) {
            case 1:
                return "传统水彩";
            case 2:
                return "3D卡通";
            case 3:
                return "动漫风格";
            case 4:
                return "新潮国风";
            case 5:
                return "中式水墨";
            case 6:
                return "复古动漫";
            case 7:
                return "赛博朋克";
            default:
                return "";
        }
    }
}
