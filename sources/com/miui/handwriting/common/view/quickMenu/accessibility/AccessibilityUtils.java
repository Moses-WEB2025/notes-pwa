package com.miui.handwriting.common.view.quickMenu.accessibility;

import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AccessibilityUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007¨\u0006\b"}, d2 = {"Lcom/miui/handwriting/common/view/quickMenu/accessibility/AccessibilityUtils;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "getSeekBarStep", "", "rangeInfo", "Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat$RangeInfoCompat;", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class AccessibilityUtils {
    public static final AccessibilityUtils INSTANCE = new AccessibilityUtils();

    private AccessibilityUtils() {
    }

    public final int getSeekBarStep(AccessibilityNodeInfoCompat.RangeInfoCompat rangeInfo) {
        Intrinsics.checkNotNullParameter(rangeInfo, "rangeInfo");
        float max = rangeInfo.getMax() - rangeInfo.getMin();
        if (((int) (max % 20)) == 0) {
            return ((int) max) / 20;
        }
        return 1;
    }
}
