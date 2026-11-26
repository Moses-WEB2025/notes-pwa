package com.miui.handwriting.common.view.quickMenu.eraser;

import android.content.Context;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.EditStat;
import com.miui.doodle.document.Layer;
import com.miui.handwriting.common.view.quickMenu.StylusPenQuickMenu;
import com.miui.handwriting.common.view.quickMenu.base.BaseMenuWrapper;
import com.miui.handwriting.common.view.quickMenu.base.PolarPoint;
import com.miui.handwriting.common.view.quickMenu.listener.OnQuickEraserUpdateListener;
import com.miui.handwriting.common.view.quickMenu.utils.AngleUtils;
import com.miui.handwriting.common.view.quickMenu.utils.HoverUtils;
import com.miui.handwriting.common.view.quickMenu.utils.PolarUtils;
import com.miui.handwriting.common.view.quickMenu.utils.QuickUtils;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.animation.Folme;
import miuix.animation.IStateStyle;
import xc.engine.operate.canvas.track.delete.DeleteType;

/* JADX INFO: compiled from: EraserMenuWrapper.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u001d\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0006\u0010\u0007J0\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\u0016H\u0014J\b\u0010\u001a\u001a\u00020\u0012H\u0002J\b\u0010\u001b\u001a\u00020\u0012H\u0016J\u000e\u0010\u001c\u001a\u00020\u00122\u0006\u0010\u001d\u001a\u00020\rJ\u0016\u0010\u001e\u001a\u00020\u00122\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u000e\u001a\n \u0010*\u0004\u0018\u00010\u000f0\u000fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006#"}, d2 = {"Lcom/miui/handwriting/common/view/quickMenu/eraser/EraserMenuWrapper;", "Lcom/miui/handwriting/common/view/quickMenu/base/BaseMenuWrapper;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "anchor", "Lcom/miui/handwriting/common/view/quickMenu/StylusPenQuickMenu;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;Lcom/miui/handwriting/common/view/quickMenu/StylusPenQuickMenu;)V", "mEraserMenuView", "Lcom/miui/handwriting/common/view/quickMenu/eraser/EraserMenuView;", "mEraserSwitchButton", "Lcom/miui/handwriting/common/view/quickMenu/eraser/EraserSwitchButton;", "mEraserUpdateListener", "Lcom/miui/handwriting/common/view/quickMenu/listener/OnQuickEraserUpdateListener;", "mAnimState", "Lmiuix/animation/IStateStyle;", "kotlin.jvm.PlatformType", "onLayout", "", "changed", "", "left", "", Layer.KEY_TOP, EditStat.ELEMENT_RIGHT, "bottom", "initEraserSwitchButton", "notifyMenuOpened", "setEraserUpdateListener", "listener", "setEraser", "type", "Lxc/engine/operate/canvas/track/delete/DeleteType;", "size", "", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class EraserMenuWrapper extends BaseMenuWrapper {
    private final IStateStyle mAnimState;
    private final EraserMenuView mEraserMenuView;
    private final EraserSwitchButton mEraserSwitchButton;
    private OnQuickEraserUpdateListener mEraserUpdateListener;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public EraserMenuWrapper(Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EraserMenuWrapper(Context context, StylusPenQuickMenu stylusPenQuickMenu) {
        super(context, stylusPenQuickMenu);
        Intrinsics.checkNotNullParameter(context, "context");
        EraserMenuView eraserMenuView = new EraserMenuView(context, null, 2, null);
        this.mEraserMenuView = eraserMenuView;
        EraserSwitchButton eraserSwitchButton = new EraserSwitchButton(context);
        this.mEraserSwitchButton = eraserSwitchButton;
        this.mAnimState = Folme.useAt(eraserSwitchButton).state();
        addView(eraserMenuView);
        initEraserSwitchButton();
        setShadow(QuickUtils.INSTANCE.getSectorPath(QuickUtils.getSector$default(QuickUtils.INSTANCE, getMenuRes(), getMenuRes().getEraserMenuStartAngle(), getMenuRes().getEraserMenuEndAngle(), null, 8, null)), 25.0f);
    }

    public /* synthetic */ EraserMenuWrapper(Context context, StylusPenQuickMenu stylusPenQuickMenu, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : stylusPenQuickMenu);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        EraserMenuView eraserMenuView = this.mEraserMenuView;
        eraserMenuView.layout(0, 0, eraserMenuView.getMeasuredWidth(), this.mEraserMenuView.getMeasuredHeight());
        float fAdjustAngle = AngleUtils.INSTANCE.adjustAngle(getMenuRes().getEraserMenuEndAngle());
        int measuredWidth = this.mEraserSwitchButton.getMeasuredWidth();
        PointF pointFConvertPolarToPlane = PolarUtils.INSTANCE.convertPolarToPlane(new PolarPoint(getMCenterTrackOvalRect().width() / 2, fAdjustAngle), getMCenterTrackOvalRect());
        float f = measuredWidth / 2;
        float f2 = pointFConvertPolarToPlane.x - f;
        float f3 = pointFConvertPolarToPlane.y - f;
        float f4 = measuredWidth;
        this.mEraserSwitchButton.layout((int) f2, (int) f3, (int) (f2 + f4), (int) (f4 + f3));
    }

    private final void initEraserSwitchButton() {
        addView(this.mEraserSwitchButton);
        this.mEraserSwitchButton.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.quickMenu.eraser.EraserMenuWrapper$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                EraserMenuWrapper.initEraserSwitchButton$lambda$0(this.f$0, view);
            }
        });
        this.mEraserSwitchButton.setOnHoverListener(new View.OnHoverListener() { // from class: com.miui.handwriting.common.view.quickMenu.eraser.EraserMenuWrapper$$ExternalSyntheticLambda1
            @Override // android.view.View.OnHoverListener
            public final boolean onHover(View view, MotionEvent motionEvent) {
                return EraserMenuWrapper.initEraserSwitchButton$lambda$1(this.f$0, view, motionEvent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initEraserSwitchButton$lambda$0(EraserMenuWrapper eraserMenuWrapper, View view) {
        DeleteType deleteType;
        eraserMenuWrapper.mEraserSwitchButton.toggleChecked();
        if (eraserMenuWrapper.mEraserSwitchButton.getIsChecked()) {
            deleteType = DeleteType.LINE;
        } else {
            deleteType = DeleteType.POINT;
        }
        OnQuickEraserUpdateListener onQuickEraserUpdateListener = eraserMenuWrapper.mEraserUpdateListener;
        if (onQuickEraserUpdateListener != null) {
            onQuickEraserUpdateListener.onUpdate(deleteType, eraserMenuWrapper.mEraserMenuView.getMEraserSize());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean initEraserSwitchButton$lambda$1(EraserMenuWrapper eraserMenuWrapper, View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 9) {
            HoverUtils hoverUtils = HoverUtils.INSTANCE;
            IStateStyle mAnimState = eraserMenuWrapper.mAnimState;
            Intrinsics.checkNotNullExpressionValue(mAnimState, "mAnimState");
            hoverUtils.appearAnimation(mAnimState, eraserMenuWrapper.mEraserSwitchButton);
        }
        if (motionEvent.getAction() == 10) {
            HoverUtils hoverUtils2 = HoverUtils.INSTANCE;
            IStateStyle mAnimState2 = eraserMenuWrapper.mAnimState;
            Intrinsics.checkNotNullExpressionValue(mAnimState2, "mAnimState");
            hoverUtils2.disappearAnimation(mAnimState2, eraserMenuWrapper.mEraserSwitchButton);
        }
        return super.onHoverEvent(motionEvent);
    }

    @Override // com.miui.handwriting.common.view.quickMenu.base.BaseMenuWrapper
    public void notifyMenuOpened() {
        this.mEraserMenuView.notifyMenuOpened();
    }

    public final void setEraserUpdateListener(OnQuickEraserUpdateListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mEraserUpdateListener = listener;
        this.mEraserMenuView.setEraserUpdateListener(listener);
    }

    public final void setEraser(DeleteType type, float size) {
        Intrinsics.checkNotNullParameter(type, "type");
        this.mEraserMenuView.setEraser(type, size);
        if (type == DeleteType.POINT) {
            this.mEraserSwitchButton.setIsChecked(false);
        } else {
            this.mEraserSwitchButton.setIsChecked(true);
        }
    }
}
