package com.miui.doodle.base;

import android.os.Parcel;
import android.os.Parcelable;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DoodleParams.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u0000 %2\u00020\u0001:\u0001%B\u0007¢\u0006\u0004\b\u0002\u0010\u0003B\u0011\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0002\u0010\u0006J\u0018\u0010 \u001a\u00020!2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\"\u001a\u00020#H\u0016J\b\u0010$\u001a\u00020#H\u0016R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001c\u0010\r\u001a\u0004\u0018\u00010\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\n\"\u0004\b\u000f\u0010\fR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0013\"\u0004\b\u0017\u0010\u0015R\u001a\u0010\u0018\u001a\u00020\u0019X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001a\u0010\u001e\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u0013\"\u0004\b\u001f\u0010\u0015¨\u0006&"}, d2 = {"Lcom/miui/doodle/base/DoodleParams;", "Landroid/os/Parcelable;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "parcel", "Landroid/os/Parcel;", "(Landroid/os/Parcel;)V", "imagePath", "", "getImagePath", "()Ljava/lang/String;", "setImagePath", "(Ljava/lang/String;)V", "savePath", "getSavePath", "setSavePath", "savePathIsDir", "", "getSavePathIsDir", "()Z", "setSavePathIsDir", "(Z)V", "isDrawableOutside", "setDrawableOutside", "changePanelVisibilityDelay", "", "getChangePanelVisibilityDelay", "()J", "setChangePanelVisibilityDelay", "(J)V", "isFullScreen", "setFullScreen", "writeToParcel", "", "flags", "", "describeContents", "CREATOR", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class DoodleParams implements Parcelable {

    /* JADX INFO: renamed from: CREATOR, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private long changePanelVisibilityDelay;
    private String imagePath;
    private boolean isDrawableOutside;
    private boolean isFullScreen;
    private String savePath;
    private boolean savePathIsDir;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public DoodleParams() {
        this.changePanelVisibilityDelay = 200L;
    }

    public final String getImagePath() {
        return this.imagePath;
    }

    public final void setImagePath(String str) {
        this.imagePath = str;
    }

    public final String getSavePath() {
        return this.savePath;
    }

    public final void setSavePath(String str) {
        this.savePath = str;
    }

    public final boolean getSavePathIsDir() {
        return this.savePathIsDir;
    }

    public final void setSavePathIsDir(boolean z) {
        this.savePathIsDir = z;
    }

    /* JADX INFO: renamed from: isDrawableOutside, reason: from getter */
    public final boolean getIsDrawableOutside() {
        return this.isDrawableOutside;
    }

    public final void setDrawableOutside(boolean z) {
        this.isDrawableOutside = z;
    }

    public final long getChangePanelVisibilityDelay() {
        return this.changePanelVisibilityDelay;
    }

    public final void setChangePanelVisibilityDelay(long j) {
        this.changePanelVisibilityDelay = j;
    }

    /* JADX INFO: renamed from: isFullScreen, reason: from getter */
    public final boolean getIsFullScreen() {
        return this.isFullScreen;
    }

    public final void setFullScreen(boolean z) {
        this.isFullScreen = z;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DoodleParams(Parcel parcel) {
        this();
        Intrinsics.checkNotNullParameter(parcel, "parcel");
        this.imagePath = parcel.readString();
        this.savePath = parcel.readString();
        this.savePathIsDir = parcel.readByte() != 0;
        this.isDrawableOutside = parcel.readByte() != 0;
        this.changePanelVisibilityDelay = parcel.readLong();
        this.isFullScreen = parcel.readByte() != 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        Intrinsics.checkNotNullParameter(parcel, "parcel");
        parcel.writeString(this.imagePath);
        parcel.writeString(this.savePath);
        parcel.writeByte(this.savePathIsDir ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isDrawableOutside ? (byte) 1 : (byte) 0);
        parcel.writeLong(this.changePanelVisibilityDelay);
        parcel.writeByte(this.isFullScreen ? (byte) 1 : (byte) 0);
    }

    /* JADX INFO: renamed from: com.miui.doodle.base.DoodleParams$CREATOR, reason: from kotlin metadata */
    /* JADX INFO: compiled from: DoodleParams.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u001d\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010\f¨\u0006\r"}, d2 = {"Lcom/miui/doodle/base/DoodleParams$CREATOR;", "Landroid/os/Parcelable$Creator;", "Lcom/miui/doodle/base/DoodleParams;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "createFromParcel", "parcel", "Landroid/os/Parcel;", "newArray", "", "size", "", "(I)[Lcom/miui/doodle/base/DoodleParams;", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion implements Parcelable.Creator<DoodleParams> {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DoodleParams createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            return new DoodleParams(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DoodleParams[] newArray(int size) {
            return new DoodleParams[size];
        }
    }
}
