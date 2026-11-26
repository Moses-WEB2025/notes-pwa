package com.miui.handwriting.common.view.quickMenu.brush;

import android.content.Context;
import android.view.View;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.market.sdk.utils.Constants;
import com.miui.common.stat.EditStat;
import com.miui.doodle.document.Layer;
import com.miui.handwriting.common.view.quickMenu.StylusPenQuickMenu;
import com.miui.handwriting.common.view.quickMenu.base.BaseMenuWrapper;
import com.miui.handwriting.common.view.quickMenu.listener.OnQuickPenColorUpdateListener;
import com.miui.handwriting.common.view.quickMenu.listener.OnQuickPenSizeColorUpdateListener;
import com.miui.handwriting.common.view.quickMenu.utils.QuickUtils;
import com.miui.libhandwritingpage.model.PageInkModel;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.data.CurveProp;

/* JADX INFO: compiled from: BrushMenuWrapper.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\rH\u0014J0\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\r2\u0006\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\rH\u0014J\b\u0010\u0016\u001a\u00020\u000bH\u0016J\u000e\u0010\u0017\u001a\u00020\u000b2\u0006\u0010\u0018\u001a\u00020\u0019J\u000e\u0010\u001a\u001a\u00020\u000b2\u0006\u0010\u001b\u001a\u00020\u001cJ\u000e\u0010\u001d\u001a\u00020\u000b2\u0006\u0010\u001b\u001a\u00020\u001eJ\u000e\u0010\u001f\u001a\u00020\u000b2\u0006\u0010 \u001a\u00020!R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lcom/miui/handwriting/common/view/quickMenu/brush/BrushMenuWrapper;", "Lcom/miui/handwriting/common/view/quickMenu/base/BaseMenuWrapper;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "anchor", "Lcom/miui/handwriting/common/view/quickMenu/StylusPenQuickMenu;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;Lcom/miui/handwriting/common/view/quickMenu/StylusPenQuickMenu;)V", "mBrushMenuView", "Lcom/miui/handwriting/common/view/quickMenu/brush/BrushMenuView;", "onMeasure", "", "widthMeasureSpec", "", "heightMeasureSpec", "onLayout", "changed", "", "left", Layer.KEY_TOP, EditStat.ELEMENT_RIGHT, "bottom", "notifyMenuOpened", "setPenInfo", Constants.JSON_FILTER_INFO, "Lxc/engine/data/CurveProp;", "setAlphaListener", "listener", "Lcom/miui/handwriting/common/view/quickMenu/listener/OnQuickPenColorUpdateListener;", "setPenSizeListener", "Lcom/miui/handwriting/common/view/quickMenu/listener/OnQuickPenSizeColorUpdateListener;", "setPageInkModel", "model", "Lcom/miui/libhandwritingpage/model/PageInkModel;", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class BrushMenuWrapper extends BaseMenuWrapper {
    private final BrushMenuView mBrushMenuView;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public BrushMenuWrapper(Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BrushMenuWrapper(Context context, StylusPenQuickMenu stylusPenQuickMenu) {
        super(context, stylusPenQuickMenu);
        Intrinsics.checkNotNullParameter(context, "context");
        BrushMenuView brushMenuView = new BrushMenuView(context, null, 2, 0 == true ? 1 : 0);
        this.mBrushMenuView = brushMenuView;
        addView(brushMenuView);
        setFocusable(1);
        setFocusableInTouchMode(true);
        setShadow(QuickUtils.INSTANCE.getSectorPath(QuickUtils.getSector$default(QuickUtils.INSTANCE, getMenuRes(), getMenuRes().getPencilMenuStartAngle(), getMenuRes().getPencilMenuEndAngle(), null, 8, null)), 30.0f);
    }

    public /* synthetic */ BrushMenuWrapper(Context context, StylusPenQuickMenu stylusPenQuickMenu, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : stylusPenQuickMenu);
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(View.MeasureSpec.makeMeasureSpec(((int) getMenuRes().getOuterCircleRadius()) * 2, 1073741824), View.MeasureSpec.makeMeasureSpec(((int) getMenuRes().getOuterCircleRadius()) * 2, 1073741824));
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        BrushMenuView brushMenuView = this.mBrushMenuView;
        brushMenuView.layout(0, 0, brushMenuView.getMeasuredWidth(), this.mBrushMenuView.getMeasuredHeight());
    }

    @Override // com.miui.handwriting.common.view.quickMenu.base.BaseMenuWrapper
    public void notifyMenuOpened() {
        this.mBrushMenuView.notifyMenuOpened();
    }

    public final void setPenInfo(CurveProp info) {
        Intrinsics.checkNotNullParameter(info, "info");
        this.mBrushMenuView.setPenInfo(info);
    }

    public final void setAlphaListener(OnQuickPenColorUpdateListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mBrushMenuView.setAlphaListener(listener);
    }

    public final void setPenSizeListener(OnQuickPenSizeColorUpdateListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mBrushMenuView.setPenSizeListener(listener);
    }

    public final void setPageInkModel(PageInkModel model) {
        Intrinsics.checkNotNullParameter(model, "model");
        this.mBrushMenuView.setPageInkModel(model);
    }
}
