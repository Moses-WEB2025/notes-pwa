package com.xiaomi.aicr.llm;

import android.os.Parcel;
import android.os.Parcelable;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AICreationResult.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\tJ\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0007HÆ\u0003J1\u0010\u0014\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0006\u0010\u0015\u001a\u00020\u0007J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019HÖ\u0003J\t\u0010\u001a\u001a\u00020\u0007HÖ\u0001J\t\u0010\u001b\u001a\u00020\u0003HÖ\u0001J\u0016\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000bR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006!"}, d2 = {"Lcom/xiaomi/aicr/llm/AICreationResult;", "Landroid/os/Parcelable;", "contentResult", "", "contentStreamResult", "theme", "code", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I)V", "getContentResult", "()Ljava/lang/String;", "getContentStreamResult", "getTheme", "getCode", "()I", "component1", "component2", "component3", "component4", "copy", "describeContents", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "toString", "writeToParcel", "", "dest", "Landroid/os/Parcel;", "flags", "aicr_AI_sdk_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
public final /* data */ class AICreationResult implements Parcelable {
    public static final Parcelable.Creator<AICreationResult> CREATOR = new Creator();
    private final int code;
    private final String contentResult;
    private final String contentStreamResult;
    private final String theme;

    /* JADX INFO: compiled from: AICreationResult.kt */
    @Metadata(k = 3, mv = {2, 0, 0}, xi = 48)
    public static final class Creator implements Parcelable.Creator<AICreationResult> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final AICreationResult createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            return new AICreationResult(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final AICreationResult[] newArray(int i) {
            return new AICreationResult[i];
        }
    }

    public static /* synthetic */ AICreationResult copy$default(AICreationResult aICreationResult, String str, String str2, String str3, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = aICreationResult.contentResult;
        }
        if ((i2 & 2) != 0) {
            str2 = aICreationResult.contentStreamResult;
        }
        if ((i2 & 4) != 0) {
            str3 = aICreationResult.theme;
        }
        if ((i2 & 8) != 0) {
            i = aICreationResult.code;
        }
        return aICreationResult.copy(str, str2, str3, i);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getContentResult() {
        return this.contentResult;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getContentStreamResult() {
        return this.contentStreamResult;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getTheme() {
        return this.theme;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final int getCode() {
        return this.code;
    }

    public final AICreationResult copy(String contentResult, String contentStreamResult, String theme, int code) {
        Intrinsics.checkNotNullParameter(contentResult, "contentResult");
        Intrinsics.checkNotNullParameter(contentStreamResult, "contentStreamResult");
        Intrinsics.checkNotNullParameter(theme, "theme");
        return new AICreationResult(contentResult, contentStreamResult, theme, code);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AICreationResult)) {
            return false;
        }
        AICreationResult aICreationResult = (AICreationResult) other;
        return Intrinsics.areEqual(this.contentResult, aICreationResult.contentResult) && Intrinsics.areEqual(this.contentStreamResult, aICreationResult.contentStreamResult) && Intrinsics.areEqual(this.theme, aICreationResult.theme) && this.code == aICreationResult.code;
    }

    public int hashCode() {
        return (((((this.contentResult.hashCode() * 31) + this.contentStreamResult.hashCode()) * 31) + this.theme.hashCode()) * 31) + Integer.hashCode(this.code);
    }

    public String toString() {
        return "AICreationResult(contentResult=" + this.contentResult + ", contentStreamResult=" + this.contentStreamResult + ", theme=" + this.theme + ", code=" + this.code + ")";
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel dest, int flags) {
        Intrinsics.checkNotNullParameter(dest, "dest");
        dest.writeString(this.contentResult);
        dest.writeString(this.contentStreamResult);
        dest.writeString(this.theme);
        dest.writeInt(this.code);
    }

    public AICreationResult(String contentResult, String contentStreamResult, String theme, int i) {
        Intrinsics.checkNotNullParameter(contentResult, "contentResult");
        Intrinsics.checkNotNullParameter(contentStreamResult, "contentStreamResult");
        Intrinsics.checkNotNullParameter(theme, "theme");
        this.contentResult = contentResult;
        this.contentStreamResult = contentStreamResult;
        this.theme = theme;
        this.code = i;
    }

    public final String getContentResult() {
        return this.contentResult;
    }

    public final String getContentStreamResult() {
        return this.contentStreamResult;
    }

    public final String getTheme() {
        return this.theme;
    }

    public final int getCode() {
        return this.code;
    }
}
