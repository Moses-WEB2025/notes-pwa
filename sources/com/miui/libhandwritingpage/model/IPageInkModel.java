package com.miui.libhandwritingpage.model;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import com.miui.libhandwritingpage.adapter.PageAdapter;
import com.miui.libhandwritingpage.adapter.PageLayoutManager;
import com.miui.libhandwritingpage.listener.PageFooterView;
import com.miui.libhandwritingpage.listener.PageLoadListener;
import com.miui.libhandwritingpage.listener.PageStateListener;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PageBean;
import com.miui.libhandwritingpage.utils.Utils;
import com.miui.libhandwritingpage.view.GlobalRulerView;
import com.miui.libhandwritingpage.view.PageInkView;
import com.miui.libhandwritingpage.view.PageItemView;
import java.lang.ref.WeakReference;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.engine.utils.EngineConfig;

/* JADX INFO: loaded from: classes3.dex */
public abstract class IPageInkModel implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, ScaleGestureDetector.OnScaleGestureListener {
    private static final String TAG = "IPageInkModel";
    public static final int VISIBLE_SIZE = 6;
    protected PageAdapter adapter;
    protected IPreviewClickListener clickListener;
    protected PageItemView currentItemView;
    int currentPosition;
    private float downPosX;
    private float downPosY;
    protected boolean enablePenOnlyMode;
    protected boolean isCancelTouch;
    protected boolean isDownWithNavigationBar;
    protected boolean isScrollGesture;
    protected PageLayoutManager layoutManager;
    protected WeakReference<Context> mContextRef;
    protected String mEntFilePath;
    protected PageFooterView mFootView;
    protected GestureDetector mGestureDetector;
    protected GlobalRulerView mGlobalRulerView;
    protected PageLoadListener mLoadListener;
    protected PageBean mPageBean;
    protected final PageInkView mPageInkView;
    protected ScaleGestureDetector mScaleGestureDetector;
    protected PageStateListener mStateListener;
    protected int mVisibleHeight;
    protected int mVisibleWidth;
    protected int orientation;
    protected int pointCount;
    protected boolean mCanWrite = true;
    protected boolean mIsFirstjump = true;
    protected int mFirstJumpPosition = -1;
    protected boolean undoWhenUp = false;
    protected boolean isDoubleFingerClickUndo = true;
    protected long hasDownTime = 0;
    protected boolean isDrawing = false;
    protected boolean isDownFlinging = false;
    protected EngineConfig engineConfig = new EngineConfig();
    protected int downPos = -1;
    protected boolean mIsScrollByTouch = false;
    protected boolean isUnlimited = true;
    protected boolean isScaling = false;
    private boolean canSingerFingerScroll = true;
    private boolean isLongDistance = false;

    public interface IPreviewClickListener {
        void onClick();
    }

    public boolean isInEditAction() {
        return false;
    }

    public boolean isInSelectedAction() {
        return false;
    }

    public abstract boolean isSelect();

    @Override // android.view.GestureDetector.OnDoubleTapListener
    public boolean onDoubleTap(MotionEvent motionEvent) {
        return false;
    }

    @Override // android.view.GestureDetector.OnDoubleTapListener
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public void onLongPress(MotionEvent motionEvent) {
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public void onShowPress(MotionEvent motionEvent) {
    }

    @Override // android.view.GestureDetector.OnDoubleTapListener
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        return false;
    }

    public PageItemView getCurrentItemView() {
        return this.currentItemView;
    }

    public IPageInkModel(PageInkView pageInkView) {
        this.mPageInkView = pageInkView;
        this.mContextRef = new WeakReference<>(pageInkView.getContext());
        this.mGestureDetector = new GestureDetector(this.mContextRef.get(), this);
        this.mScaleGestureDetector = new ScaleGestureDetector(this.mContextRef.get(), this);
        this.orientation = this.mContextRef.get().getResources().getConfiguration().orientation;
    }

    public void doubleClickUndo(MotionEvent motionEvent) {
        if (this.mCanWrite) {
            int actionMasked = motionEvent.getActionMasked();
            boolean z = false;
            if (actionMasked == 0) {
                this.undoWhenUp = false;
                this.isLongDistance = false;
                this.downPosX = motionEvent.getX();
                this.downPosY = motionEvent.getY();
                return;
            }
            if (actionMasked != 1) {
                if (actionMasked == 5) {
                    if (motionEvent.getPointerCount() == 2 && !this.mGlobalRulerView.isUsingRuler() && !isSelect()) {
                        z = true;
                    }
                    this.undoWhenUp = z;
                    return;
                }
                if (actionMasked == 6 && motionEvent.getPointerCount() == 2) {
                    this.isLongDistance = Math.min(Math.abs(motionEvent.getX(0) - this.downPosX), Math.abs(motionEvent.getX(1) - this.downPosX)) > 50.0f || Math.min(Math.abs(motionEvent.getY(0) - this.downPosY), Math.abs(motionEvent.getY(1) - this.downPosY)) > 50.0f;
                    return;
                }
                return;
            }
            long eventTime = motionEvent.getEventTime() - motionEvent.getDownTime();
            String str = TAG;
            Log.d(str, "doubleClickUndo undo time = " + eventTime);
            if (this.isDoubleFingerClickUndo && this.undoWhenUp && eventTime < 100 && this.adapter.canUndo() && !this.isLongDistance && !Utils.isFastClick()) {
                PageStateListener pageStateListener = this.mStateListener;
                if (pageStateListener != null) {
                    pageStateListener.onDoubleUndo();
                }
                Log.d(str, "doubleClickUndo undo");
                undo();
            }
            this.isLongDistance = false;
        }
    }

    protected boolean isDoubleClickNoNeedRefresh() {
        boolean z = this.isDoubleFingerClickUndo;
        if (z && this.undoWhenUp) {
            return true;
        }
        return (z || this.isScrollGesture) ? false : true;
    }

    public void onConfigurationChanged(Configuration configuration) {
        if (this.orientation == configuration.orientation) {
            return;
        }
        if (this.mPageInkView.getScrollY() != 0) {
            this.mPageInkView.scrollTo(0, 0);
        }
        this.orientation = configuration.orientation;
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public void onLayout() {
        MiCurveEngine curveEngine;
        PageItemView pageItemView = this.currentItemView;
        if (pageItemView != null) {
            pageItemView.updateLayout(this.mVisibleWidth, this.mVisibleHeight, this.layoutManager.getStandardWidth());
        }
        if (!this.mIsScrollByTouch || this.isUnlimited || this.mFootView == null || (curveEngine = getCurveEngine()) == null) {
            return;
        }
        this.layoutManager.setDraggingState(this.mFootView.UpdateFootView(curveEngine.getViewportOffset().y, curveEngine.getPageSystem().getAllowedMaxHeight() * curveEngine.getScaleRatio(), this.mVisibleWidth, this.mVisibleHeight) ? 1 : 0);
    }

    public void computeScroll() {
        this.layoutManager.computeScroll();
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onDown(MotionEvent motionEvent) {
        this.layoutManager.onTouchDown();
        this.canSingerFingerScroll = true;
        return false;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        IPreviewClickListener iPreviewClickListener = this.clickListener;
        if (iPreviewClickListener == null) {
            return false;
        }
        iPreviewClickListener.onClick();
        return false;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        this.mIsScrollByTouch = true;
        if (this.hasDownTime < 100) {
            return false;
        }
        if (this.mGlobalRulerView.isUsingRuler()) {
            this.undoWhenUp = false;
            return false;
        }
        if (motionEvent2.getPointerCount() == 1) {
            this.undoWhenUp = false;
            if ((this.downPos != -1 && ((!this.enablePenOnlyMode || (motionEvent2.getToolType(0) & 1) == 0) && this.mCanWrite)) || isInSelectedAction() || isInEditAction()) {
                return false;
            }
            if (this.canSingerFingerScroll) {
                doScroll(f, f2);
                this.isScrollGesture = true;
            }
            this.canSingerFingerScroll = true;
            return true;
        }
        if (this.currentItemView != null && motionEvent2.getPointerCount() != 2) {
            return false;
        }
        doScroll(f, f2);
        this.undoWhenUp = false;
        this.isScrollGesture = true;
        this.canSingerFingerScroll = false;
        return true;
    }

    private void doScroll(float f, float f2) {
        MiCurveEngine curveEngine = getCurveEngine();
        if (curveEngine == null) {
            return;
        }
        curveEngine.getCamera().scrollByOffsetSS(f, f2);
        if (curveEngine.getPageSystem().hasReachedBottom()) {
            onLayout();
        }
    }

    private MiCurveEngine getCurveEngine() {
        PageItemView pageItemView = this.currentItemView;
        if (pageItemView == null) {
            return null;
        }
        return pageItemView.getCurveEngine();
    }

    private void doFling(float f, float f2) {
        MiCurveEngine curveEngine = getCurveEngine();
        if (curveEngine == null) {
            return;
        }
        curveEngine.getCamera().fling(f, f2);
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        if (this.mGlobalRulerView.isUsingRuler()) {
            this.undoWhenUp = false;
            return false;
        }
        if (this.layoutManager.isDragging() || isInEditAction() || this.isScaling) {
            return false;
        }
        if (this.isCancelTouch || this.downPos == -1 || (this.enablePenOnlyMode && (motionEvent2.getToolType(0) & 1) != 0 && !isInSelectedAction())) {
            if (!this.isUnlimited) {
                doFling(f, f2);
            }
            this.undoWhenUp = false;
        }
        return true;
    }

    @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
    public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
        if (this.pointCount != 2 || this.hasDownTime < 100) {
            return false;
        }
        float scaleFactor = scaleGestureDetector.getScaleFactor();
        if (getCurveEngine() != null) {
            getCurveEngine().getCamera().scale(scaleFactor, scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY());
            this.layoutManager.notifyDataChangedNew();
        }
        this.isScaling = true;
        this.undoWhenUp = false;
        return true;
    }

    @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
    public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
        if (this.mGlobalRulerView.isUsingRuler() || this.pointCount != 2 || this.hasDownTime < 100) {
            return false;
        }
        float scaleFactor = scaleGestureDetector.getScaleFactor();
        if (getCurveEngine() == null) {
            return true;
        }
        getCurveEngine().getCamera().scale(scaleFactor, scaleGestureDetector.getPreviousSpanX(), scaleGestureDetector.getFocusY());
        this.layoutManager.notifyDataChangedNew();
        return true;
    }

    @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
    public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
        if (this.hasDownTime < 100) {
            return;
        }
        float scaleFactor = scaleGestureDetector.getScaleFactor();
        if (getCurveEngine() != null) {
            getCurveEngine().getCamera().scale(scaleFactor, scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY());
            this.layoutManager.notifyDataChangedNew();
        }
    }

    public void enablePenOnlyMode(boolean z) {
        this.enablePenOnlyMode = z;
        this.engineConfig.enablePenOnlyMode = z;
    }

    public void undo() {
        stopFling();
        this.adapter.undo();
    }

    public void redo() {
        stopFling();
        this.adapter.redo();
    }

    public void setPageStateListener(PageStateListener pageStateListener) {
        this.mStateListener = pageStateListener;
        PageAdapter pageAdapter = this.adapter;
        if (pageAdapter != null) {
            pageAdapter.setPageStateListener(pageStateListener);
        }
    }

    public int getCurrentPosition() {
        return this.currentPosition;
    }

    public boolean isDrawing() {
        return this.isDrawing;
    }

    public void setPadding(int i, int i2, int i3, int i4) {
        this.layoutManager.setPadding(i, i2, i3, i4);
    }

    public GlobalRulerView getGlobalRulerView() {
        return this.mGlobalRulerView;
    }

    public void setPageFootView(PageFooterView pageFooterView) {
        this.mFootView = pageFooterView;
    }

    private void stopFling() {
        if (this.layoutManager.isFlinging()) {
            this.layoutManager.stopFling();
            this.layoutManager.onScrollEnd();
        }
    }

    public void setClickListener(IPreviewClickListener iPreviewClickListener) {
        this.clickListener = iPreviewClickListener;
    }

    public void updateTitle() {
        this.mPageInkView.updateHeadView();
    }

    public int getToolViewHeight() {
        return this.mPageInkView.getToolViewHeight();
    }
}
