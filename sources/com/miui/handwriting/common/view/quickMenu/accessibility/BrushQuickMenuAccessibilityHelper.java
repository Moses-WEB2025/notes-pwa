package com.miui.handwriting.common.view.quickMenu.accessibility;

import android.os.Bundle;
import android.view.accessibility.AccessibilityEvent;
import android.widget.SeekBar;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.widget.ExploreByTouchHelper;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.market.sdk.utils.Constants;
import com.miui.handwriting.common.view.quickMenu.brush.BrushMenuView;
import com.miui.maml.folme.AnimatedProperty;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;

/* JADX INFO: compiled from: BrushQuickMenuAccessibilityHelper.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u0000 12\u00020\u0001:\u00011B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0004\b\u0007\u0010\bJ\u000e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0010J\u000e\u0010\u0015\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0010J\u0018\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0019H\u0014J\u0016\u0010\u001b\u001a\u00020\u00132\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00170\u001dH\u0014J\u0018\u0010\u001e\u001a\u00020\u00132\u0006\u0010\u001f\u001a\u00020\u00172\u0006\u0010 \u001a\u00020!H\u0014J\u0018\u0010\"\u001a\u00020\u00132\u0006\u0010\u001f\u001a\u00020\u00172\u0006\u0010#\u001a\u00020$H\u0014J\"\u0010%\u001a\u00020&2\u0006\u0010\u001f\u001a\u00020\u00172\u0006\u0010'\u001a\u00020\u00172\b\u0010(\u001a\u0004\u0018\u00010)H\u0014J \u0010*\u001a\u00020\u00132\u0006\u0010+\u001a\u00020\u00052\u0006\u0010#\u001a\u00020$2\u0006\u0010\u001f\u001a\u00020\u0017H\u0002J\u0018\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020\u00172\u0006\u0010\u001f\u001a\u00020\u0017H\u0002J\u0010\u0010/\u001a\u00020-2\u0006\u00100\u001a\u00020\u0017H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\n\"\u0004\b\u000e\u0010\fR\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000¨\u00062"}, d2 = {"Lcom/miui/handwriting/common/view/quickMenu/accessibility/BrushQuickMenuAccessibilityHelper;", "Landroidx/customview/widget/ExploreByTouchHelper;", "host", "Lcom/miui/handwriting/common/view/quickMenu/brush/BrushMenuView;", "mSizeSeekBarAccessibilityInfo", "Lcom/miui/handwriting/common/view/quickMenu/accessibility/SeekBarAccessibilityInfo;", "mAlphaSeekBarAccessibilityInfo", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/handwriting/common/view/quickMenu/brush/BrushMenuView;Lcom/miui/handwriting/common/view/quickMenu/accessibility/SeekBarAccessibilityInfo;Lcom/miui/handwriting/common/view/quickMenu/accessibility/SeekBarAccessibilityInfo;)V", "getMSizeSeekBarAccessibilityInfo", "()Lcom/miui/handwriting/common/view/quickMenu/accessibility/SeekBarAccessibilityInfo;", "setMSizeSeekBarAccessibilityInfo", "(Lcom/miui/handwriting/common/view/quickMenu/accessibility/SeekBarAccessibilityInfo;)V", "getMAlphaSeekBarAccessibilityInfo", "setMAlphaSeekBarAccessibilityInfo", "mSizeSeekBarAccessibilityUpdateListener", "Lcom/miui/handwriting/common/view/quickMenu/accessibility/onAccessibilitySeekBarProgressUpdateListener;", "mAlphaSeekBarAccessibilityUpdateListener", "setAccessibilitySizeSeekBarProgressUpdateListener", "", "listener", "setAccessibilityAlphaSeekBarProgressUpdateListener", "getVirtualViewAt", "", AnimatedProperty.PROPERTY_NAME_X, "", AnimatedProperty.PROPERTY_NAME_Y, "getVisibleVirtualViews", "virtualViewIds", "", "onPopulateEventForVirtualView", "virtualViewId", "event", "Landroid/view/accessibility/AccessibilityEvent;", "onPopulateNodeForVirtualView", "node", "Landroidx/core/view/accessibility/AccessibilityNodeInfoCompat;", "onPerformActionForVirtualView", "", "action", "arguments", "Landroid/os/Bundle;", "fillAccessibilityNodeInfo", Constants.JSON_FILTER_INFO, "formatStateDescription", "", "value", "getDescriptionForId", "id", "Companion", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class BrushQuickMenuAccessibilityHelper extends ExploreByTouchHelper {
    public static final int ALPHA_SEEKBAR_VIRTUAL_VIEW_ID = 2;
    public static final int SIZE_SEEKBAR_VIRTUAL_VIEW_ID = 1;
    public static final String TAG = "BrushQuickMenuAccessibilityHelper";
    private final BrushMenuView host;
    private SeekBarAccessibilityInfo mAlphaSeekBarAccessibilityInfo;
    private onAccessibilitySeekBarProgressUpdateListener mAlphaSeekBarAccessibilityUpdateListener;
    private SeekBarAccessibilityInfo mSizeSeekBarAccessibilityInfo;
    private onAccessibilitySeekBarProgressUpdateListener mSizeSeekBarAccessibilityUpdateListener;

    public final SeekBarAccessibilityInfo getMSizeSeekBarAccessibilityInfo() {
        return this.mSizeSeekBarAccessibilityInfo;
    }

    public final void setMSizeSeekBarAccessibilityInfo(SeekBarAccessibilityInfo seekBarAccessibilityInfo) {
        Intrinsics.checkNotNullParameter(seekBarAccessibilityInfo, "<set-?>");
        this.mSizeSeekBarAccessibilityInfo = seekBarAccessibilityInfo;
    }

    public final SeekBarAccessibilityInfo getMAlphaSeekBarAccessibilityInfo() {
        return this.mAlphaSeekBarAccessibilityInfo;
    }

    public final void setMAlphaSeekBarAccessibilityInfo(SeekBarAccessibilityInfo seekBarAccessibilityInfo) {
        Intrinsics.checkNotNullParameter(seekBarAccessibilityInfo, "<set-?>");
        this.mAlphaSeekBarAccessibilityInfo = seekBarAccessibilityInfo;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BrushQuickMenuAccessibilityHelper(BrushMenuView host, SeekBarAccessibilityInfo mSizeSeekBarAccessibilityInfo, SeekBarAccessibilityInfo mAlphaSeekBarAccessibilityInfo) {
        super(host);
        Intrinsics.checkNotNullParameter(host, "host");
        Intrinsics.checkNotNullParameter(mSizeSeekBarAccessibilityInfo, "mSizeSeekBarAccessibilityInfo");
        Intrinsics.checkNotNullParameter(mAlphaSeekBarAccessibilityInfo, "mAlphaSeekBarAccessibilityInfo");
        this.host = host;
        this.mSizeSeekBarAccessibilityInfo = mSizeSeekBarAccessibilityInfo;
        this.mAlphaSeekBarAccessibilityInfo = mAlphaSeekBarAccessibilityInfo;
    }

    public final void setAccessibilitySizeSeekBarProgressUpdateListener(onAccessibilitySeekBarProgressUpdateListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mSizeSeekBarAccessibilityUpdateListener = listener;
    }

    public final void setAccessibilityAlphaSeekBarProgressUpdateListener(onAccessibilitySeekBarProgressUpdateListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mAlphaSeekBarAccessibilityUpdateListener = listener;
    }

    @Override // androidx.customview.widget.ExploreByTouchHelper
    protected int getVirtualViewAt(float x, float y) {
        if (this.host.isTouchInSizeSeekBarAccessibility(x, y)) {
            return 1;
        }
        return this.host.isTouchInAlphaSeekBarAccessibility(x, y) ? 2 : Integer.MIN_VALUE;
    }

    @Override // androidx.customview.widget.ExploreByTouchHelper
    protected void getVisibleVirtualViews(List<Integer> virtualViewIds) {
        Intrinsics.checkNotNullParameter(virtualViewIds, "virtualViewIds");
        virtualViewIds.add(1);
        virtualViewIds.add(2);
    }

    @Override // androidx.customview.widget.ExploreByTouchHelper
    protected void onPopulateEventForVirtualView(int virtualViewId, AccessibilityEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        event.setContentDescription(getDescriptionForId(virtualViewId));
    }

    @Override // androidx.customview.widget.ExploreByTouchHelper
    protected void onPopulateNodeForVirtualView(int virtualViewId, AccessibilityNodeInfoCompat node) {
        Intrinsics.checkNotNullParameter(node, "node");
        if (virtualViewId == 1) {
            fillAccessibilityNodeInfo(this.mSizeSeekBarAccessibilityInfo, node, virtualViewId);
        }
        if (virtualViewId == 2) {
            fillAccessibilityNodeInfo(this.mAlphaSeekBarAccessibilityInfo, node, virtualViewId);
        }
    }

    @Override // androidx.customview.widget.ExploreByTouchHelper
    protected boolean onPerformActionForVirtualView(int virtualViewId, int action, Bundle arguments) {
        onAccessibilitySeekBarProgressUpdateListener onaccessibilityseekbarprogressupdatelistener;
        onAccessibilitySeekBarProgressUpdateListener onaccessibilityseekbarprogressupdatelistener2;
        onAccessibilitySeekBarProgressUpdateListener onaccessibilityseekbarprogressupdatelistener3;
        onAccessibilitySeekBarProgressUpdateListener onaccessibilityseekbarprogressupdatelistener4;
        if (action == 4096) {
            if (virtualViewId == 1 && (onaccessibilityseekbarprogressupdatelistener2 = this.mSizeSeekBarAccessibilityUpdateListener) != null) {
                onaccessibilityseekbarprogressupdatelistener2.update(this.mSizeSeekBarAccessibilityInfo.getStep(), virtualViewId);
            }
            if (virtualViewId == 2 && (onaccessibilityseekbarprogressupdatelistener = this.mAlphaSeekBarAccessibilityUpdateListener) != null) {
                onaccessibilityseekbarprogressupdatelistener.update(this.mAlphaSeekBarAccessibilityInfo.getStep(), virtualViewId);
            }
            return true;
        }
        if (action != 8192) {
            return false;
        }
        if (virtualViewId == 1 && (onaccessibilityseekbarprogressupdatelistener4 = this.mSizeSeekBarAccessibilityUpdateListener) != null) {
            onaccessibilityseekbarprogressupdatelistener4.update(-this.mSizeSeekBarAccessibilityInfo.getStep(), virtualViewId);
        }
        if (virtualViewId == 2 && (onaccessibilityseekbarprogressupdatelistener3 = this.mAlphaSeekBarAccessibilityUpdateListener) != null) {
            onaccessibilityseekbarprogressupdatelistener3.update(-this.mAlphaSeekBarAccessibilityInfo.getStep(), virtualViewId);
        }
        return true;
    }

    private final void fillAccessibilityNodeInfo(SeekBarAccessibilityInfo info, AccessibilityNodeInfoCompat node, int virtualViewId) {
        node.setContentDescription(getDescriptionForId(virtualViewId));
        node.setBoundsInParent(info.getBoundsInParent());
        node.setClassName(SeekBar.class.getName());
        node.setRangeInfo(info.getRangeInfo());
        node.addAction(4096);
        node.addAction(8192);
        node.setStateDescription(formatStateDescription((int) info.getRangeInfo().getCurrent(), virtualViewId));
        if (info.isUpdateProgress()) {
            node.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SET_PROGRESS);
        }
    }

    private final String formatStateDescription(int value, int virtualViewId) {
        if (virtualViewId == 1) {
            return String.valueOf(value);
        }
        if (virtualViewId == 2) {
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            String str = String.format("%s", Arrays.copyOf(new Object[]{NumberFormat.getPercentInstance().format(Float.valueOf(value / 255.0f))}, 1));
            Intrinsics.checkNotNullExpressionValue(str, "format(...)");
            return str;
        }
        return "";
    }

    private final String getDescriptionForId(int id) {
        if (id == 1) {
            return this.mSizeSeekBarAccessibilityInfo.getContentDescription();
        }
        if (id == 2) {
            return this.mAlphaSeekBarAccessibilityInfo.getContentDescription();
        }
        return "";
    }
}
