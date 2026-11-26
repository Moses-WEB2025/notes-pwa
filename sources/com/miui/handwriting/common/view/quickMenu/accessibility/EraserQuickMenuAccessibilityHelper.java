package com.miui.handwriting.common.view.quickMenu.accessibility;

import android.os.Bundle;
import android.widget.SeekBar;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.widget.ExploreByTouchHelper;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.market.sdk.utils.Constants;
import com.miui.handwriting.common.view.quickMenu.eraser.EraserMenuView;
import com.miui.maml.folme.AnimatedProperty;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: EraserQuickMenuAccessibilityHelper.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 $2\u00020\u0001:\u0001$B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\rJ\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014H\u0014J\u0016\u0010\u0016\u001a\u00020\u000f2\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00120\u0018H\u0014J\u0018\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020\u001cH\u0014J\"\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001a\u001a\u00020\u00122\u0006\u0010\u001f\u001a\u00020\u00122\b\u0010 \u001a\u0004\u0018\u00010!H\u0014J \u0010\"\u001a\u00020\u000f2\u0006\u0010#\u001a\u00020\u00052\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001a\u001a\u00020\u0012H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lcom/miui/handwriting/common/view/quickMenu/accessibility/EraserQuickMenuAccessibilityHelper;", "Landroidx/customview/widget/ExploreByTouchHelper;", "host", "Lcom/miui/handwriting/common/view/quickMenu/eraser/EraserMenuView;", "mEraserSeekBarAccessibilityInfo", "Lcom/miui/handwriting/common/view/quickMenu/accessibility/SeekBarAccessibilityInfo;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/handwriting/common/view/quickMenu/eraser/EraserMenuView;Lcom/miui/handwriting/common/view/quickMenu/accessibility/SeekBarAccessibilityInfo;)V", "getMEraserSeekBarAccessibilityInfo", "()Lcom/miui/handwriting/common/view/quickMenu/accessibility/SeekBarAccessibilityInfo;", "setMEraserSeekBarAccessibilityInfo", "(Lcom/miui/handwriting/common/view/quickMenu/accessibility/SeekBarAccessibilityInfo;)V", "mEraserSeekBarAccessibilityUpdateListener", "Lcom/miui/handwriting/common/view/quickMenu/accessibility/onAccessibilitySeekBarProgressUpdateListener;", "setAccessibilityEraserSeekBarProgressUpdateListener", "", "listener", "getVirtualViewAt", "", AnimatedProperty.PROPERTY_NAME_X, "", AnimatedProperty.PROPERTY_NAME_Y, "getVisibleVirtualViews", "virtualViewIds", "", "onPopulateNodeForVirtualView", "virtualViewId", "node", "Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat;", "onPerformActionForVirtualView", "", "action", "arguments", "Landroid/os/Bundle;", "fillAccessibilityNodeInfo", Constants.JSON_FILTER_INFO, "Companion", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class EraserQuickMenuAccessibilityHelper extends ExploreByTouchHelper {
    public static final int ERASER_SEEKBAR_VIRTUAL_VIEW_ID = 3;
    public static final String TAG = "EraserQuickMenuAccessibilityHelper";
    private final EraserMenuView host;
    private SeekBarAccessibilityInfo mEraserSeekBarAccessibilityInfo;
    private onAccessibilitySeekBarProgressUpdateListener mEraserSeekBarAccessibilityUpdateListener;

    public final SeekBarAccessibilityInfo getMEraserSeekBarAccessibilityInfo() {
        return this.mEraserSeekBarAccessibilityInfo;
    }

    public final void setMEraserSeekBarAccessibilityInfo(SeekBarAccessibilityInfo seekBarAccessibilityInfo) {
        Intrinsics.checkNotNullParameter(seekBarAccessibilityInfo, "<set-?>");
        this.mEraserSeekBarAccessibilityInfo = seekBarAccessibilityInfo;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EraserQuickMenuAccessibilityHelper(EraserMenuView host, SeekBarAccessibilityInfo mEraserSeekBarAccessibilityInfo) {
        super(host);
        Intrinsics.checkNotNullParameter(host, "host");
        Intrinsics.checkNotNullParameter(mEraserSeekBarAccessibilityInfo, "mEraserSeekBarAccessibilityInfo");
        this.host = host;
        this.mEraserSeekBarAccessibilityInfo = mEraserSeekBarAccessibilityInfo;
    }

    public final void setAccessibilityEraserSeekBarProgressUpdateListener(onAccessibilitySeekBarProgressUpdateListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mEraserSeekBarAccessibilityUpdateListener = listener;
    }

    @Override // androidx.customview.widget.ExploreByTouchHelper
    protected int getVirtualViewAt(float x, float y) {
        return this.host.isTouchInEraserSeekBarAccessibility(x, y) ? 3 : Integer.MIN_VALUE;
    }

    @Override // androidx.customview.widget.ExploreByTouchHelper
    protected void getVisibleVirtualViews(List<Integer> virtualViewIds) {
        Intrinsics.checkNotNullParameter(virtualViewIds, "virtualViewIds");
        virtualViewIds.add(3);
    }

    @Override // androidx.customview.widget.ExploreByTouchHelper
    protected void onPopulateNodeForVirtualView(int virtualViewId, AccessibilityNodeInfoCompat node) {
        Intrinsics.checkNotNullParameter(node, "node");
        if (virtualViewId == 3) {
            fillAccessibilityNodeInfo(this.mEraserSeekBarAccessibilityInfo, node, virtualViewId);
        }
    }

    @Override // androidx.customview.widget.ExploreByTouchHelper
    protected boolean onPerformActionForVirtualView(int virtualViewId, int action, Bundle arguments) {
        onAccessibilitySeekBarProgressUpdateListener onaccessibilityseekbarprogressupdatelistener;
        onAccessibilitySeekBarProgressUpdateListener onaccessibilityseekbarprogressupdatelistener2;
        if (action == 4096) {
            if (virtualViewId == 3 && (onaccessibilityseekbarprogressupdatelistener = this.mEraserSeekBarAccessibilityUpdateListener) != null) {
                onaccessibilityseekbarprogressupdatelistener.update(this.mEraserSeekBarAccessibilityInfo.getStep(), virtualViewId);
            }
            return true;
        }
        if (action != 8192) {
            return false;
        }
        if (virtualViewId == 3 && (onaccessibilityseekbarprogressupdatelistener2 = this.mEraserSeekBarAccessibilityUpdateListener) != null) {
            onaccessibilityseekbarprogressupdatelistener2.update(-this.mEraserSeekBarAccessibilityInfo.getStep(), virtualViewId);
        }
        return true;
    }

    private final void fillAccessibilityNodeInfo(SeekBarAccessibilityInfo info, AccessibilityNodeInfoCompat node, int virtualViewId) {
        node.setContentDescription("");
        node.setBoundsInParent(info.getBoundsInParent());
        node.setClassName(SeekBar.class.getName());
        node.setRangeInfo(info.getRangeInfo());
        node.addAction(4096);
        node.addAction(8192);
        node.setStateDescription(String.valueOf((int) info.getRangeInfo().getCurrent()));
        if (info.isUpdateProgress()) {
            node.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SET_PROGRESS);
        }
    }
}
