package com.miui.handwriting.common.view.quickMenu.accessibility;

import android.graphics.Rect;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SeekBarAccessibilityInfo.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0016\b\u0086\b\u0018\u00002\u00020\u0001B1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b¢\u0006\u0004\b\f\u0010\rJ\t\u0010\u0017\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0018\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0019\u001a\u00020\u0007HÆ\u0003J\t\u0010\u001a\u001a\u00020\tHÆ\u0003J\t\u0010\u001b\u001a\u00020\u000bHÆ\u0003J;\u0010\u001c\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000bHÆ\u0001J\u0013\u0010\u001d\u001a\u00020\u000b2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001f\u001a\u00020\u0007HÖ\u0001J\t\u0010 \u001a\u00020\tHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0016¨\u0006!"}, d2 = {"Lcom/miui/handwriting/common/view/quickMenu/accessibility/SeekBarAccessibilityInfo;", "", "boundsInParent", "Landroid/graphics/Rect;", "rangeInfo", "Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat$RangeInfoCompat;", "step", "", "contentDescription", "", "isUpdateProgress", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/graphics/Rect;Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat$RangeInfoCompat;ILjava/lang/String;Z)V", "getBoundsInParent", "()Landroid/graphics/Rect;", "getRangeInfo", "()Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat$RangeInfoCompat;", "getStep", "()I", "getContentDescription", "()Ljava/lang/String;", "()Z", "component1", "component2", "component3", "component4", "component5", "copy", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "toString", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class SeekBarAccessibilityInfo {
    private final Rect boundsInParent;
    private final String contentDescription;
    private final boolean isUpdateProgress;
    private final AccessibilityNodeInfoCompat.RangeInfoCompat rangeInfo;
    private final int step;

    public static /* synthetic */ SeekBarAccessibilityInfo copy$default(SeekBarAccessibilityInfo seekBarAccessibilityInfo, Rect rect, AccessibilityNodeInfoCompat.RangeInfoCompat rangeInfoCompat, int i, String str, boolean z, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            rect = seekBarAccessibilityInfo.boundsInParent;
        }
        if ((i2 & 2) != 0) {
            rangeInfoCompat = seekBarAccessibilityInfo.rangeInfo;
        }
        if ((i2 & 4) != 0) {
            i = seekBarAccessibilityInfo.step;
        }
        if ((i2 & 8) != 0) {
            str = seekBarAccessibilityInfo.contentDescription;
        }
        if ((i2 & 16) != 0) {
            z = seekBarAccessibilityInfo.isUpdateProgress;
        }
        boolean z2 = z;
        int i3 = i;
        return seekBarAccessibilityInfo.copy(rect, rangeInfoCompat, i3, str, z2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final Rect getBoundsInParent() {
        return this.boundsInParent;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final AccessibilityNodeInfoCompat.RangeInfoCompat getRangeInfo() {
        return this.rangeInfo;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final int getStep() {
        return this.step;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final String getContentDescription() {
        return this.contentDescription;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final boolean getIsUpdateProgress() {
        return this.isUpdateProgress;
    }

    public final SeekBarAccessibilityInfo copy(Rect boundsInParent, AccessibilityNodeInfoCompat.RangeInfoCompat rangeInfo, int step, String contentDescription, boolean isUpdateProgress) {
        Intrinsics.checkNotNullParameter(boundsInParent, "boundsInParent");
        Intrinsics.checkNotNullParameter(rangeInfo, "rangeInfo");
        Intrinsics.checkNotNullParameter(contentDescription, "contentDescription");
        return new SeekBarAccessibilityInfo(boundsInParent, rangeInfo, step, contentDescription, isUpdateProgress);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SeekBarAccessibilityInfo)) {
            return false;
        }
        SeekBarAccessibilityInfo seekBarAccessibilityInfo = (SeekBarAccessibilityInfo) other;
        return Intrinsics.areEqual(this.boundsInParent, seekBarAccessibilityInfo.boundsInParent) && Intrinsics.areEqual(this.rangeInfo, seekBarAccessibilityInfo.rangeInfo) && this.step == seekBarAccessibilityInfo.step && Intrinsics.areEqual(this.contentDescription, seekBarAccessibilityInfo.contentDescription) && this.isUpdateProgress == seekBarAccessibilityInfo.isUpdateProgress;
    }

    public int hashCode() {
        return (((((((this.boundsInParent.hashCode() * 31) + this.rangeInfo.hashCode()) * 31) + Integer.hashCode(this.step)) * 31) + this.contentDescription.hashCode()) * 31) + Boolean.hashCode(this.isUpdateProgress);
    }

    public String toString() {
        return "SeekBarAccessibilityInfo(boundsInParent=" + this.boundsInParent + ", rangeInfo=" + this.rangeInfo + ", step=" + this.step + ", contentDescription=" + this.contentDescription + ", isUpdateProgress=" + this.isUpdateProgress + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    public SeekBarAccessibilityInfo(Rect boundsInParent, AccessibilityNodeInfoCompat.RangeInfoCompat rangeInfo, int i, String contentDescription, boolean z) {
        Intrinsics.checkNotNullParameter(boundsInParent, "boundsInParent");
        Intrinsics.checkNotNullParameter(rangeInfo, "rangeInfo");
        Intrinsics.checkNotNullParameter(contentDescription, "contentDescription");
        this.boundsInParent = boundsInParent;
        this.rangeInfo = rangeInfo;
        this.step = i;
        this.contentDescription = contentDescription;
        this.isUpdateProgress = z;
    }

    public /* synthetic */ SeekBarAccessibilityInfo(Rect rect, AccessibilityNodeInfoCompat.RangeInfoCompat rangeInfoCompat, int i, String str, boolean z, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(rect, rangeInfoCompat, i, str, (i2 & 16) != 0 ? false : z);
    }

    public final Rect getBoundsInParent() {
        return this.boundsInParent;
    }

    public final AccessibilityNodeInfoCompat.RangeInfoCompat getRangeInfo() {
        return this.rangeInfo;
    }

    public final int getStep() {
        return this.step;
    }

    public final String getContentDescription() {
        return this.contentDescription;
    }

    public final boolean isUpdateProgress() {
        return this.isUpdateProgress;
    }
}
