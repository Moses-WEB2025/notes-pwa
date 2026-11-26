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

/* JADX INFO: compiled from: ThemeItem.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0016\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B7\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\t\u0010\nJ\u0006\u0010\u0015\u001a\u00020\u0003J\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u0018\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010\u0019\u001a\u00020\u0007HÆ\u0003J\u000b\u0010\u001a\u001a\u0004\u0018\u00010\u0003HÆ\u0003J?\u0010\u001b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0006\u0010\u001c\u001a\u00020\u0007J\u0013\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010 HÖ\u0003J\t\u0010!\u001a\u00020\u0007HÖ\u0001J\t\u0010\"\u001a\u00020\u0003HÖ\u0001J\u0016\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\u0007R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\fR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u001c\u0010\b\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\f\"\u0004\b\u0014\u0010\u000e¨\u0006("}, d2 = {"Lcom/miui/handwriting/common/ai/data/ReferenceItem;", "Landroid/os/Parcelable;", "path", "", "name", CallMethod.RESULT_ICON, "type", "", CallMethod.ARG_URI, ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;)V", "getPath", "()Ljava/lang/String;", "setPath", "(Ljava/lang/String;)V", "getName", "getIcon", "getType", "()I", "getUri", "setUri", "trackReference", "component1", "component2", "component3", "component4", "component5", "copy", "describeContents", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "toString", "writeToParcel", "", "dest", "Landroid/os/Parcel;", "flags", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class ReferenceItem implements Parcelable {
    public static final Parcelable.Creator<ReferenceItem> CREATOR = new Creator();
    private final String icon;
    private final String name;
    private String path;
    private final int type;
    private String uri;

    /* JADX INFO: compiled from: ThemeItem.kt */
    @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
    public static final class Creator implements Parcelable.Creator<ReferenceItem> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final ReferenceItem createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            return new ReferenceItem(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readInt(), parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final ReferenceItem[] newArray(int i) {
            return new ReferenceItem[i];
        }
    }

    public static /* synthetic */ ReferenceItem copy$default(ReferenceItem referenceItem, String str, String str2, String str3, int i, String str4, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = referenceItem.path;
        }
        if ((i2 & 2) != 0) {
            str2 = referenceItem.name;
        }
        if ((i2 & 4) != 0) {
            str3 = referenceItem.icon;
        }
        if ((i2 & 8) != 0) {
            i = referenceItem.type;
        }
        if ((i2 & 16) != 0) {
            str4 = referenceItem.uri;
        }
        String str5 = str4;
        String str6 = str3;
        return referenceItem.copy(str, str2, str6, i, str5);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getPath() {
        return this.path;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getIcon() {
        return this.icon;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final int getType() {
        return this.type;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final String getUri() {
        return this.uri;
    }

    public final ReferenceItem copy(String path, String name, String icon, int type, String uri) {
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(name, "name");
        return new ReferenceItem(path, name, icon, type, uri);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ReferenceItem)) {
            return false;
        }
        ReferenceItem referenceItem = (ReferenceItem) other;
        return Intrinsics.areEqual(this.path, referenceItem.path) && Intrinsics.areEqual(this.name, referenceItem.name) && Intrinsics.areEqual(this.icon, referenceItem.icon) && this.type == referenceItem.type && Intrinsics.areEqual(this.uri, referenceItem.uri);
    }

    public int hashCode() {
        int iHashCode = ((this.path.hashCode() * 31) + this.name.hashCode()) * 31;
        String str = this.icon;
        int iHashCode2 = (((iHashCode + (str == null ? 0 : str.hashCode())) * 31) + Integer.hashCode(this.type)) * 31;
        String str2 = this.uri;
        return iHashCode2 + (str2 != null ? str2.hashCode() : 0);
    }

    public String toString() {
        return "ReferenceItem(path=" + this.path + ", name=" + this.name + ", icon=" + this.icon + ", type=" + this.type + ", uri=" + this.uri + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel dest, int flags) {
        Intrinsics.checkNotNullParameter(dest, "dest");
        dest.writeString(this.path);
        dest.writeString(this.name);
        dest.writeString(this.icon);
        dest.writeInt(this.type);
        dest.writeString(this.uri);
    }

    public ReferenceItem(String path, String name, String str, int i, String str2) {
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(name, "name");
        this.path = path;
        this.name = name;
        this.icon = str;
        this.type = i;
        this.uri = str2;
    }

    public /* synthetic */ ReferenceItem(String str, String str2, String str3, int i, String str4, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i2 & 2) != 0 ? "" : str2, str3, i, (i2 & 16) != 0 ? "" : str4);
    }

    public final String getPath() {
        return this.path;
    }

    public final void setPath(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.path = str;
    }

    public final String getName() {
        return this.name;
    }

    public final String getIcon() {
        return this.icon;
    }

    public final int getType() {
        return this.type;
    }

    public final String getUri() {
        return this.uri;
    }

    public final void setUri(String str) {
        this.uri = str;
    }

    public final String trackReference() {
        int i = this.type;
        if (i == Integer.parseInt("3")) {
            return "参考人脸";
        }
        if (i == Integer.parseInt("2")) {
            return "参考姿势";
        }
        return "";
    }
}
