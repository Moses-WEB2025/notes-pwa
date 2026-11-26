package com.miui.notes.base.accessibility;

import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.market.sdk.utils.Constants;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AccessibilityDelegateUtil.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u000e"}, d2 = {"Lcom/miui/notes/base/accessibility/SelectAccessibilityDelegate;", "Landroid/view/View$AccessibilityDelegate;", "type", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Z)V", "getType", "()Z", "onInitializeAccessibilityNodeInfo", "", "host", "Landroid/view/View;", Constants.JSON_FILTER_INFO, "Landroid/view/accessibility/AccessibilityNodeInfo;", "common_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
final class SelectAccessibilityDelegate extends View.AccessibilityDelegate {
    private final boolean type;

    public SelectAccessibilityDelegate(boolean z) {
        this.type = z;
    }

    public final boolean getType() {
        return this.type;
    }

    @Override // android.view.View.AccessibilityDelegate
    public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
        Intrinsics.checkNotNullParameter(host, "host");
        Intrinsics.checkNotNullParameter(info, "info");
        super.onInitializeAccessibilityNodeInfo(host, info);
        info.setSelected(this.type);
    }
}
