package com.xiaomi.aicr.llm;

import android.os.Parcel;
import android.os.Parcelable;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: TextEditProcessResult.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006¢\u0006\u0004\b\b\u0010\tJ\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0006HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0006HÆ\u0003J1\u0010\u0014\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u0006HÆ\u0001J\u0006\u0010\u0015\u001a\u00020\u0006J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019HÖ\u0003J\t\u0010\u001a\u001a\u00020\u0006HÖ\u0001J\t\u0010\u001b\u001a\u00020\u0003HÖ\u0001J\u0016\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0007\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000e¨\u0006!"}, d2 = {"Lcom/xiaomi/aicr/llm/TextEditProcessResult;", "Landroid/os/Parcelable;", "contentResult", "", "contentStreamResult", "code", "", "errorNum", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;II)V", "getContentResult", "()Ljava/lang/String;", "getContentStreamResult", "getCode", "()I", "getErrorNum", "component1", "component2", "component3", "component4", "copy", "describeContents", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "toString", "writeToParcel", "", "dest", "Landroid/os/Parcel;", "flags", "aicr_AI_sdk_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
public final /* data */ class TextEditProcessResult implements Parcelable {
    public static final Parcelable.Creator<TextEditProcessResult> CREATOR = new Creator();
    private final int code;
    private final String contentResult;
    private final String contentStreamResult;
    private final int errorNum;

    /* JADX INFO: compiled from: TextEditProcessResult.kt */
    @Metadata(k = 3, mv = {2, 0, 0}, xi = 48)
    public static final class Creator implements Parcelable.Creator<TextEditProcessResult> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final TextEditProcessResult createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            return new TextEditProcessResult(parcel.readString(), parcel.readString(), parcel.readInt(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final TextEditProcessResult[] newArray(int i) {
            return new TextEditProcessResult[i];
        }
    }

    public static /* synthetic */ TextEditProcessResult copy$default(TextEditProcessResult textEditProcessResult, String str, String str2, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            str = textEditProcessResult.contentResult;
        }
        if ((i3 & 2) != 0) {
            str2 = textEditProcessResult.contentStreamResult;
        }
        if ((i3 & 4) != 0) {
            i = textEditProcessResult.code;
        }
        if ((i3 & 8) != 0) {
            i2 = textEditProcessResult.errorNum;
        }
        return textEditProcessResult.copy(str, str2, i, i2);
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
    public final int getCode() {
        return this.code;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final int getErrorNum() {
        return this.errorNum;
    }

    public final TextEditProcessResult copy(String contentResult, String contentStreamResult, int code, int errorNum) {
        Intrinsics.checkNotNullParameter(contentResult, "contentResult");
        Intrinsics.checkNotNullParameter(contentStreamResult, "contentStreamResult");
        return new TextEditProcessResult(contentResult, contentStreamResult, code, errorNum);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TextEditProcessResult)) {
            return false;
        }
        TextEditProcessResult textEditProcessResult = (TextEditProcessResult) other;
        return Intrinsics.areEqual(this.contentResult, textEditProcessResult.contentResult) && Intrinsics.areEqual(this.contentStreamResult, textEditProcessResult.contentStreamResult) && this.code == textEditProcessResult.code && this.errorNum == textEditProcessResult.errorNum;
    }

    public int hashCode() {
        return (((((this.contentResult.hashCode() * 31) + this.contentStreamResult.hashCode()) * 31) + Integer.hashCode(this.code)) * 31) + Integer.hashCode(this.errorNum);
    }

    public String toString() {
        return "TextEditProcessResult(contentResult=" + this.contentResult + ", contentStreamResult=" + this.contentStreamResult + ", code=" + this.code + ", errorNum=" + this.errorNum + ")";
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel dest, int flags) {
        Intrinsics.checkNotNullParameter(dest, "dest");
        dest.writeString(this.contentResult);
        dest.writeString(this.contentStreamResult);
        dest.writeInt(this.code);
        dest.writeInt(this.errorNum);
    }

    public TextEditProcessResult(String contentResult, String contentStreamResult, int i, int i2) {
        Intrinsics.checkNotNullParameter(contentResult, "contentResult");
        Intrinsics.checkNotNullParameter(contentStreamResult, "contentStreamResult");
        this.contentResult = contentResult;
        this.contentStreamResult = contentStreamResult;
        this.code = i;
        this.errorNum = i2;
    }

    public final String getContentResult() {
        return this.contentResult;
    }

    public final String getContentStreamResult() {
        return this.contentStreamResult;
    }

    public final int getCode() {
        return this.code;
    }

    public final int getErrorNum() {
        return this.errorNum;
    }
}
