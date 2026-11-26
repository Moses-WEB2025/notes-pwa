package com.miui.handwriting.common.ai.viewmodel.cache;

import android.os.Parcel;
import android.os.Parcelable;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: RecreateInfo.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0016\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\b\u0010\tJ\b\u0010\u0012\u001a\u00020\u0003H\u0016J\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u0017\u001a\u0004\u0018\u00010\u0003HÆ\u0003J=\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0006\u0010\u0019\u001a\u00020\u001aJ\u0013\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eHÖ\u0003J\t\u0010\u001f\u001a\u00020\u001aHÖ\u0001J\u0016\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020\u001aR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000bR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000bR\u001c\u0010\u0007\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u000b\"\u0004\b\u0010\u0010\u0011¨\u0006%"}, d2 = {"Lcom/miui/handwriting/common/ai/viewmodel/cache/RecreateInfo;", "Landroid/os/Parcelable;", "mId", "", "requestId", "serverKey", "iv", "creationType", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getMId", "()Ljava/lang/String;", "getRequestId", "getServerKey", "getIv", "getCreationType", "setCreationType", "(Ljava/lang/String;)V", "toString", "component1", "component2", "component3", "component4", "component5", "copy", "describeContents", "", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "writeToParcel", "", "dest", "Landroid/os/Parcel;", "flags", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class RecreateInfo implements Parcelable {
    public static final Parcelable.Creator<RecreateInfo> CREATOR = new Creator();
    private String creationType;
    private final String iv;
    private final String mId;
    private final String requestId;
    private final String serverKey;

    /* JADX INFO: compiled from: RecreateInfo.kt */
    @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
    public static final class Creator implements Parcelable.Creator<RecreateInfo> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final RecreateInfo createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            return new RecreateInfo(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final RecreateInfo[] newArray(int i) {
            return new RecreateInfo[i];
        }
    }

    public static /* synthetic */ RecreateInfo copy$default(RecreateInfo recreateInfo, String str, String str2, String str3, String str4, String str5, int i, Object obj) {
        if ((i & 1) != 0) {
            str = recreateInfo.mId;
        }
        if ((i & 2) != 0) {
            str2 = recreateInfo.requestId;
        }
        if ((i & 4) != 0) {
            str3 = recreateInfo.serverKey;
        }
        if ((i & 8) != 0) {
            str4 = recreateInfo.iv;
        }
        if ((i & 16) != 0) {
            str5 = recreateInfo.creationType;
        }
        String str6 = str5;
        String str7 = str3;
        return recreateInfo.copy(str, str2, str7, str4, str6);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getMId() {
        return this.mId;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getRequestId() {
        return this.requestId;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getServerKey() {
        return this.serverKey;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final String getIv() {
        return this.iv;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final String getCreationType() {
        return this.creationType;
    }

    public final RecreateInfo copy(String mId, String requestId, String serverKey, String iv, String creationType) {
        Intrinsics.checkNotNullParameter(mId, "mId");
        Intrinsics.checkNotNullParameter(requestId, "requestId");
        Intrinsics.checkNotNullParameter(serverKey, "serverKey");
        Intrinsics.checkNotNullParameter(iv, "iv");
        return new RecreateInfo(mId, requestId, serverKey, iv, creationType);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof RecreateInfo)) {
            return false;
        }
        RecreateInfo recreateInfo = (RecreateInfo) other;
        return Intrinsics.areEqual(this.mId, recreateInfo.mId) && Intrinsics.areEqual(this.requestId, recreateInfo.requestId) && Intrinsics.areEqual(this.serverKey, recreateInfo.serverKey) && Intrinsics.areEqual(this.iv, recreateInfo.iv) && Intrinsics.areEqual(this.creationType, recreateInfo.creationType);
    }

    public int hashCode() {
        int iHashCode = ((((((this.mId.hashCode() * 31) + this.requestId.hashCode()) * 31) + this.serverKey.hashCode()) * 31) + this.iv.hashCode()) * 31;
        String str = this.creationType;
        return iHashCode + (str == null ? 0 : str.hashCode());
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel dest, int flags) {
        Intrinsics.checkNotNullParameter(dest, "dest");
        dest.writeString(this.mId);
        dest.writeString(this.requestId);
        dest.writeString(this.serverKey);
        dest.writeString(this.iv);
        dest.writeString(this.creationType);
    }

    public RecreateInfo(String mId, String requestId, String serverKey, String iv, String str) {
        Intrinsics.checkNotNullParameter(mId, "mId");
        Intrinsics.checkNotNullParameter(requestId, "requestId");
        Intrinsics.checkNotNullParameter(serverKey, "serverKey");
        Intrinsics.checkNotNullParameter(iv, "iv");
        this.mId = mId;
        this.requestId = requestId;
        this.serverKey = serverKey;
        this.iv = iv;
        this.creationType = str;
    }

    public final String getMId() {
        return this.mId;
    }

    public final String getRequestId() {
        return this.requestId;
    }

    public final String getServerKey() {
        return this.serverKey;
    }

    public final String getIv() {
        return this.iv;
    }

    public /* synthetic */ RecreateInfo(String str, String str2, String str3, String str4, String str5, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, str3, str4, (i & 16) != 0 ? "1" : str5);
    }

    public final String getCreationType() {
        return this.creationType;
    }

    public final void setCreationType(String str) {
        this.creationType = str;
    }

    public String toString() {
        return "mId:" + this.mId + ",requestId:" + this.requestId + ",serverKey:" + this.serverKey + ",iv:" + this.iv + ",creationType:" + this.creationType + CoreConstants.COMMA_CHAR;
    }
}
