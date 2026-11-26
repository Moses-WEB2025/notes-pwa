package com.miui.handwriting.common.ai.data;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CreationImageForOS1.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\t\u001a\u00020\u0003H\u0016J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\u0005¨\u0006\u0011"}, d2 = {"Lcom/miui/handwriting/common/ai/data/CreationImageForOS1;", "", "imageData", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;)V", "getImageData", "()Ljava/lang/String;", "setImageData", "toString", "component1", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class CreationImageForOS1 {
    private String imageData;

    public static /* synthetic */ CreationImageForOS1 copy$default(CreationImageForOS1 creationImageForOS1, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = creationImageForOS1.imageData;
        }
        return creationImageForOS1.copy(str);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getImageData() {
        return this.imageData;
    }

    public final CreationImageForOS1 copy(String imageData) {
        Intrinsics.checkNotNullParameter(imageData, "imageData");
        return new CreationImageForOS1(imageData);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof CreationImageForOS1) && Intrinsics.areEqual(this.imageData, ((CreationImageForOS1) other).imageData);
    }

    public int hashCode() {
        return this.imageData.hashCode();
    }

    public CreationImageForOS1(String imageData) {
        Intrinsics.checkNotNullParameter(imageData, "imageData");
        this.imageData = imageData;
    }

    public final String getImageData() {
        return this.imageData;
    }

    public final void setImageData(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.imageData = str;
    }

    public String toString() {
        return "imageData size:" + this.imageData.length();
    }
}
