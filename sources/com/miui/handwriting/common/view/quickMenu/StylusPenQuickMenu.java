package com.miui.handwriting.common.view.quickMenu;

import android.content.Context;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.market.sdk.utils.Constants;
import com.miui.common.stat.EditStat;
import com.miui.doodle.document.Layer;
import com.miui.handwriting.common.data.bean.PenBean;
import com.miui.handwriting.common.view.quickMenu.animationManager.ActionEnum;
import com.miui.handwriting.common.view.quickMenu.animationManager.StateEnum;
import com.miui.handwriting.common.view.quickMenu.animationManager.StateMachine;
import com.miui.handwriting.common.view.quickMenu.base.BaseMenuWrapper;
import com.miui.handwriting.common.view.quickMenu.base.StylusPenQuickMenuType;
import com.miui.handwriting.common.view.quickMenu.brush.BrushMenuWrapper;
import com.miui.handwriting.common.view.quickMenu.color.ColorMenuWrapper;
import com.miui.handwriting.common.view.quickMenu.eraser.EraserMenuWrapper;
import com.miui.handwriting.common.view.quickMenu.listener.OnQuickColorSelectUpdateListener;
import com.miui.handwriting.common.view.quickMenu.listener.OnQuickEraserUpdateListener;
import com.miui.handwriting.common.view.quickMenu.listener.OnQuickPenColorUpdateListener;
import com.miui.handwriting.common.view.quickMenu.listener.OnQuickPenSizeColorUpdateListener;
import com.miui.handwriting.common.view.quickMenu.listener.OnStylusPenMenuDismissListener;
import com.miui.handwriting.common.view.quickMenu.utils.PenMenuRes;
import com.miui.libhandwritingpage.model.PageInkModel;
import com.miui.maml.folme.AnimatedProperty;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.data.CurveProp;
import xc.engine.operate.canvas.track.delete.DeleteType;

/* JADX INFO: compiled from: StylusPenQuickMenu.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000¼\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000*\u0002&)\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010+\u001a\u00020,H\u0002J(\u0010-\u001a\u00020\u001f2\u0006\u0010.\u001a\u00020\u00152\u0006\u0010/\u001a\u00020\u00152\u0006\u00100\u001a\u00020\u001f2\u0006\u00101\u001a\u000202H\u0002J\u001e\u00103\u001a\u00020,2\u0006\u00104\u001a\u00020\t2\u0006\u00100\u001a\u00020\u001f2\u0006\u00101\u001a\u000202J\u0012\u00105\u001a\u00020\u001b2\b\u00106\u001a\u0004\u0018\u000107H\u0016J \u00108\u001a\u00020,2\u0006\u00104\u001a\u00020\t2\u0006\u00100\u001a\u00020\u001f2\u0006\u00101\u001a\u000202H\u0002J0\u00109\u001a\u00020,2\u0006\u0010:\u001a\u00020\u001b2\u0006\u0010;\u001a\u00020\u00152\u0006\u0010<\u001a\u00020\u00152\u0006\u0010=\u001a\u00020\u00152\u0006\u0010>\u001a\u00020\u0015H\u0014J\u0016\u0010?\u001a\u00020,2\u0006\u0010.\u001a\u00020\u00182\u0006\u0010/\u001a\u00020\u0018J\u0010\u0010@\u001a\u00020,2\u0006\u00101\u001a\u000202H\u0002J\u000e\u0010A\u001a\u00020,2\u0006\u0010B\u001a\u00020\rJ\u0016\u0010C\u001a\u00020,2\u0006\u00101\u001a\u00020D2\u0006\u0010E\u001a\u00020\u0018J\u000e\u0010F\u001a\u00020,2\u0006\u0010G\u001a\u00020HJ\u000e\u0010I\u001a\u00020,2\u0006\u0010G\u001a\u00020JJ\u000e\u0010K\u001a\u00020,2\u0006\u0010G\u001a\u00020LJ\u000e\u0010M\u001a\u00020,2\u0006\u0010G\u001a\u00020NJ\u0014\u0010O\u001a\u00020,2\n\b\u0002\u0010G\u001a\u0004\u0018\u00010#H\u0007J\u0006\u0010P\u001a\u00020,J\u000e\u0010Q\u001a\u00020,2\u0006\u0010R\u001a\u00020SJ\u0006\u0010T\u001a\u00020\u001bJ\u000e\u0010U\u001a\u00020,2\u0006\u0010V\u001a\u00020\u001bJ\u000e\u0010W\u001a\u00020,2\u0006\u0010V\u001a\u00020\u001bJ\u000e\u0010X\u001a\u00020,2\u0006\u0010Y\u001a\u00020\u001bJ\u000e\u0010Z\u001a\u00020,2\u0006\u0010[\u001a\u00020\\R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\"\u001a\u0004\u0018\u00010#X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010%\u001a\u00020&X\u0082\u0004¢\u0006\u0004\n\u0002\u0010'R\u0010\u0010(\u001a\u00020)X\u0082\u0004¢\u0006\u0004\n\u0002\u0010*¨\u0006]"}, d2 = {"Lcom/miui/handwriting/common/view/quickMenu/StylusPenQuickMenu;", "Landroid/widget/FrameLayout;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;)V", "menuRes", "Lcom/miui/handwriting/common/view/quickMenu/utils/PenMenuRes;", "mAnchor", "Landroid/view/ViewGroup;", "mCurMenuWrapper", "Lcom/miui/handwriting/common/view/quickMenu/base/BaseMenuWrapper;", "mCurPenInfo", "Lxc/engine/data/CurveProp;", "mBrushMenuWrapper", "Lcom/miui/handwriting/common/view/quickMenu/brush/BrushMenuWrapper;", "mColorMenuWrapper", "Lcom/miui/handwriting/common/view/quickMenu/color/ColorMenuWrapper;", "mEraserMenuWrapper", "Lcom/miui/handwriting/common/view/quickMenu/eraser/EraserMenuWrapper;", "mChildrenWidth", "", "mChildrenHeight", "mStylusPenHoverX", "", "mStylusPenHoverY", "mIsGlobalSelectPen", "", "mIsGlobalSelectEraser", "mIsStylusHoverInScreen", "layoutRect", "Landroid/graphics/Rect;", "mStateMachine", "Lcom/miui/handwriting/common/view/quickMenu/animationManager/StateMachine;", "mCurrentDismissListener", "Lcom/miui/handwriting/common/view/quickMenu/listener/OnStylusPenMenuDismissListener;", "host", "openMenuAction", "com/miui/handwriting/common/view/quickMenu/StylusPenQuickMenu$openMenuAction$1", "Lcom/miui/handwriting/common/view/quickMenu/StylusPenQuickMenu$openMenuAction$1;", "closeMenuAction", "com/miui/handwriting/common/view/quickMenu/StylusPenQuickMenu$closeMenuAction$1", "Lcom/miui/handwriting/common/view/quickMenu/StylusPenQuickMenu$closeMenuAction$1;", "initStateMachineTransactionInfo", "", "computeMenuPosition", AnimatedProperty.PROPERTY_NAME_X, AnimatedProperty.PROPERTY_NAME_Y, "availableRect", "type", "Lcom/miui/handwriting/common/view/quickMenu/base/StylusPenQuickMenuType;", "autoShowAndClose", "anchor", "onTouchEvent", "event", "Landroid/view/MotionEvent;", "showAtLocation", "onLayout", "changed", "left", Layer.KEY_TOP, EditStat.ELEMENT_RIGHT, "bottom", "setStylusPenHoverPosition", "switchMenu", "setCurPenInfo", Constants.JSON_FILTER_INFO, "setEraser", "Lxc/engine/operate/canvas/track/delete/DeleteType;", "size", "setAlphaUpdateListener", "listener", "Lcom/miui/handwriting/common/view/quickMenu/listener/OnQuickPenColorUpdateListener;", "setPenSizeUpdateListener", "Lcom/miui/handwriting/common/view/quickMenu/listener/OnQuickPenSizeColorUpdateListener;", "setEraserUpdateListener", "Lcom/miui/handwriting/common/view/quickMenu/listener/OnQuickEraserUpdateListener;", "setColorSelectUpdateListener", "Lcom/miui/handwriting/common/view/quickMenu/listener/OnQuickColorSelectUpdateListener;", "dismiss", "destory", "setPageInkModel", "model", "Lcom/miui/libhandwritingpage/model/PageInkModel;", "isShowing", "updatePenSelected", "state", "updateEraserSelected", "setStylusHovered", "status", "setPenBean", "bean", "Lcom/miui/handwriting/common/data/bean/PenBean;", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class StylusPenQuickMenu extends FrameLayout {
    private final StylusPenQuickMenu$closeMenuAction$1 closeMenuAction;
    private final StylusPenQuickMenu host;
    private Rect layoutRect;
    private ViewGroup mAnchor;
    private final BrushMenuWrapper mBrushMenuWrapper;
    private int mChildrenHeight;
    private int mChildrenWidth;
    private final ColorMenuWrapper mColorMenuWrapper;
    private BaseMenuWrapper mCurMenuWrapper;
    private CurveProp mCurPenInfo;
    private OnStylusPenMenuDismissListener mCurrentDismissListener;
    private final EraserMenuWrapper mEraserMenuWrapper;
    private boolean mIsGlobalSelectEraser;
    private boolean mIsGlobalSelectPen;
    private boolean mIsStylusHoverInScreen;
    private final StateMachine mStateMachine;
    private float mStylusPenHoverX;
    private float mStylusPenHoverY;
    private final PenMenuRes menuRes;
    private final StylusPenQuickMenu$openMenuAction$1 openMenuAction;

    /* JADX INFO: compiled from: StylusPenQuickMenu.kt */
    @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
    public static final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[StylusPenQuickMenuType.values().length];
            try {
                iArr[StylusPenQuickMenuType.PARAMETER_DISK.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[StylusPenQuickMenuType.COLOR_DISK.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public final void dismiss() {
        dismiss$default(this, null, 1, null);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StylusPenQuickMenu(Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        PenMenuRes penMenuRes = new PenMenuRes(context);
        this.menuRes = penMenuRes;
        this.mBrushMenuWrapper = new BrushMenuWrapper(context, this);
        this.mColorMenuWrapper = new ColorMenuWrapper(context, this);
        this.mEraserMenuWrapper = new EraserMenuWrapper(context, this);
        this.mStylusPenHoverX = -1.0f;
        this.mStylusPenHoverY = -1.0f;
        this.layoutRect = new Rect();
        this.mStateMachine = new StateMachine(StateEnum.CLOSED);
        this.host = this;
        this.openMenuAction = new StylusPenQuickMenu$openMenuAction$1(this);
        this.closeMenuAction = new StylusPenQuickMenu$closeMenuAction$1(this);
        this.mChildrenWidth = ((int) penMenuRes.getOuterCircleRadius()) * 2;
        this.mChildrenHeight = ((int) penMenuRes.getOuterCircleRadius()) * 2;
        setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        setFocusable(1);
        setFocusableInTouchMode(true);
        initStateMachineTransactionInfo();
    }

    private final void initStateMachineTransactionInfo() {
        this.mStateMachine.addStateTransaction().source(StateEnum.CLOSED).trigger(ActionEnum.OPEN).doAction(this.openMenuAction).to(StateEnum.OPEN_ANIMATING).preTransaction();
        this.mStateMachine.addStateTransaction().source(StateEnum.OPEN_ANIMATING).trigger(ActionEnum.ANIMATION_FINISH).to(StateEnum.OPENED);
        this.mStateMachine.addStateTransaction().source(StateEnum.OPEN_ANIMATING).trigger(ActionEnum.INTERRUPT).doAction(this.closeMenuAction).to(StateEnum.CLOSE_ANIMATING).preTransaction();
        this.mStateMachine.addStateTransaction().source(StateEnum.OPENED).trigger(ActionEnum.CLOSE).to(StateEnum.CLOSE_ANIMATING).doAction(this.closeMenuAction).preTransaction();
        this.mStateMachine.addStateTransaction().source(StateEnum.CLOSE_ANIMATING).trigger(ActionEnum.ANIMATION_FINISH).to(StateEnum.CLOSED);
        this.mStateMachine.addStateTransaction().source(StateEnum.CLOSE_ANIMATING).trigger(ActionEnum.INTERRUPT).doAction(this.openMenuAction).to(StateEnum.OPEN_ANIMATING).preTransaction();
    }

    private final Rect computeMenuPosition(int x, int y, Rect availableRect, StylusPenQuickMenuType type) {
        int menuLimitTop = (int) this.menuRes.getMenuLimitTop();
        int menuLimitLRB = (int) this.menuRes.getMenuLimitLRB();
        Rect rect = new Rect();
        if (type == StylusPenQuickMenuType.COLOR_DISK) {
            float colorMenuHorizontalGap = this.menuRes.getColorMenuHorizontalGap();
            this.mColorMenuWrapper.setColorPickPopupLimitRect(new Rect(availableRect.left + menuLimitLRB, availableRect.top + menuLimitTop, availableRect.right - menuLimitLRB, availableRect.bottom - menuLimitLRB));
            rect.set(availableRect.left + ((int) colorMenuHorizontalGap), availableRect.top + menuLimitTop, availableRect.right - menuLimitLRB, availableRect.bottom - menuLimitLRB);
        } else {
            rect.set(availableRect.left + menuLimitLRB, availableRect.top + menuLimitTop, availableRect.right - menuLimitLRB, availableRect.bottom - menuLimitLRB);
        }
        int i = x - (this.mChildrenWidth / 2);
        int i2 = y - (this.mChildrenHeight / 2);
        if (i < rect.left) {
            i = rect.left;
        }
        if (rect.right - this.mChildrenWidth < i) {
            i = rect.right - this.mChildrenWidth;
        }
        if (i2 < rect.top) {
            i2 = rect.top;
        }
        if (rect.bottom - this.mChildrenHeight < i2) {
            i2 = rect.bottom - this.mChildrenHeight;
        }
        return new Rect(i, i2, this.mChildrenWidth + i, this.mChildrenHeight + i2);
    }

    public final void autoShowAndClose(ViewGroup anchor, Rect availableRect, StylusPenQuickMenuType type) {
        Intrinsics.checkNotNullParameter(anchor, "anchor");
        Intrinsics.checkNotNullParameter(availableRect, "availableRect");
        Intrinsics.checkNotNullParameter(type, "type");
        if (this.mStateMachine.getCurrentState() == StateEnum.CLOSED || this.mStateMachine.getCurrentState() == StateEnum.CLOSE_ANIMATING) {
            showAtLocation(anchor, availableRect, type);
        } else if (this.mStateMachine.getCurrentState() == StateEnum.OPENED || this.mStateMachine.getCurrentState() == StateEnum.OPEN_ANIMATING) {
            dismiss$default(this, null, 1, null);
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        dismiss$default(this, null, 1, null);
        return super.onTouchEvent(event);
    }

    private final void showAtLocation(ViewGroup anchor, Rect availableRect, StylusPenQuickMenuType type) {
        boolean z = this.mIsGlobalSelectPen;
        if (z || this.mIsGlobalSelectEraser) {
            if (z || type != StylusPenQuickMenuType.COLOR_DISK) {
                int iCenterX = availableRect.centerX();
                int iCenterY = availableRect.centerY();
                if (this.mIsStylusHoverInScreen) {
                    iCenterX = (int) this.mStylusPenHoverX;
                    iCenterY = (int) this.mStylusPenHoverY;
                }
                this.mAnchor = anchor;
                if (getParent() == null) {
                    anchor.addView(this);
                }
                bringToFront();
                switchMenu(type);
                this.layoutRect.set(computeMenuPosition(iCenterX, iCenterY, availableRect, type));
                post(new Runnable() { // from class: com.miui.handwriting.common.view.quickMenu.StylusPenQuickMenu$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        StylusPenQuickMenu.showAtLocation$lambda$0(this.f$0);
                    }
                });
                if (this.mStateMachine.getCurrentState() == StateEnum.OPENED) {
                    return;
                }
                if (this.mStateMachine.getCurrentState() == StateEnum.CLOSE_ANIMATING) {
                    this.mStateMachine.transaction(ActionEnum.INTERRUPT);
                } else if (this.mStateMachine.getCurrentState() == StateEnum.CLOSED) {
                    this.mStateMachine.transaction(ActionEnum.OPEN);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showAtLocation$lambda$0(StylusPenQuickMenu stylusPenQuickMenu) {
        BaseMenuWrapper baseMenuWrapper = stylusPenQuickMenu.mCurMenuWrapper;
        if (baseMenuWrapper != null) {
            baseMenuWrapper.layout(stylusPenQuickMenu.layoutRect.left, stylusPenQuickMenu.layoutRect.top, stylusPenQuickMenu.layoutRect.right, stylusPenQuickMenu.layoutRect.bottom);
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(true, this.layoutRect.left, this.layoutRect.top, this.layoutRect.right, this.layoutRect.bottom);
    }

    public final void setStylusPenHoverPosition(float x, float y) {
        this.mStylusPenHoverX = x;
        this.mStylusPenHoverY = y;
    }

    private final void switchMenu(StylusPenQuickMenuType type) {
        int i = WhenMappings.$EnumSwitchMapping$0[type.ordinal()];
        if (i != 1) {
            if (i != 2) {
                throw new NoWhenBranchMatchedException();
            }
            if (this.mColorMenuWrapper.getParent() == null) {
                addView(this.mColorMenuWrapper);
            }
            this.mCurMenuWrapper = this.mColorMenuWrapper;
            this.mBrushMenuWrapper.cancelAnimation();
            this.mEraserMenuWrapper.cancelAnimation();
            removeView(this.mBrushMenuWrapper);
            removeView(this.mEraserMenuWrapper);
            return;
        }
        if (this.mIsGlobalSelectPen) {
            if (this.mBrushMenuWrapper.getParent() == null) {
                addView(this.mBrushMenuWrapper);
            }
            this.mCurMenuWrapper = this.mBrushMenuWrapper;
            this.mEraserMenuWrapper.cancelAnimation();
            this.mColorMenuWrapper.cancelAnimation();
            removeView(this.mEraserMenuWrapper);
            removeView(this.mColorMenuWrapper);
            return;
        }
        if (this.mIsGlobalSelectEraser) {
            if (this.mEraserMenuWrapper.getParent() == null) {
                addView(this.mEraserMenuWrapper);
            }
            this.mCurMenuWrapper = this.mEraserMenuWrapper;
            this.mBrushMenuWrapper.cancelAnimation();
            this.mColorMenuWrapper.cancelAnimation();
            removeView(this.mBrushMenuWrapper);
            removeView(this.mColorMenuWrapper);
        }
    }

    public final void setCurPenInfo(CurveProp info) {
        Intrinsics.checkNotNullParameter(info, "info");
        this.mCurPenInfo = info;
        this.mBrushMenuWrapper.setPenInfo(info);
    }

    public final void setEraser(DeleteType type, float size) {
        Intrinsics.checkNotNullParameter(type, "type");
        this.mEraserMenuWrapper.setEraser(type, size);
    }

    public final void setAlphaUpdateListener(OnQuickPenColorUpdateListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mBrushMenuWrapper.setAlphaListener(listener);
    }

    public final void setPenSizeUpdateListener(OnQuickPenSizeColorUpdateListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mBrushMenuWrapper.setPenSizeListener(listener);
    }

    public final void setEraserUpdateListener(OnQuickEraserUpdateListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mEraserMenuWrapper.setEraserUpdateListener(listener);
    }

    public final void setColorSelectUpdateListener(OnQuickColorSelectUpdateListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mColorMenuWrapper.setColorSelectUpdateListener(listener);
    }

    public static /* synthetic */ void dismiss$default(StylusPenQuickMenu stylusPenQuickMenu, OnStylusPenMenuDismissListener onStylusPenMenuDismissListener, int i, Object obj) {
        if ((i & 1) != 0) {
            onStylusPenMenuDismissListener = null;
        }
        stylusPenQuickMenu.dismiss(onStylusPenMenuDismissListener);
    }

    public final void dismiss(OnStylusPenMenuDismissListener listener) {
        this.mCurrentDismissListener = listener;
        if (this.mStateMachine.getCurrentState() == StateEnum.OPENED) {
            this.mStateMachine.transaction(ActionEnum.CLOSE);
        }
        if (this.mStateMachine.getCurrentState() == StateEnum.OPEN_ANIMATING) {
            this.mStateMachine.transaction(ActionEnum.INTERRUPT);
        }
        if (this.mStateMachine.getCurrentState() != StateEnum.CLOSED || listener == null) {
            return;
        }
        listener.onDismiss();
    }

    public final void destory() {
        this.mColorMenuWrapper.destory();
        this.mEraserMenuWrapper.destory();
        this.mBrushMenuWrapper.destory();
        ViewGroup viewGroup = this.mAnchor;
        if (viewGroup != null) {
            viewGroup.removeView(this);
        }
    }

    public final void setPageInkModel(PageInkModel model) {
        Intrinsics.checkNotNullParameter(model, "model");
        this.mBrushMenuWrapper.setPageInkModel(model);
    }

    public final boolean isShowing() {
        return this.mStateMachine.getCurrentState() == StateEnum.OPENED;
    }

    public final void updatePenSelected(boolean state) {
        this.mIsGlobalSelectPen = state;
    }

    public final void updateEraserSelected(boolean state) {
        this.mIsGlobalSelectEraser = state;
    }

    public final void setStylusHovered(boolean status) {
        this.mIsStylusHoverInScreen = status;
    }

    public final void setPenBean(PenBean bean) {
        Intrinsics.checkNotNullParameter(bean, "bean");
        this.mColorMenuWrapper.setCurPenBean(bean);
    }
}
