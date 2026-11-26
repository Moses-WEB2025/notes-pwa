package com.miui.libhandwritingpreview.system;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.EditStat;
import com.miui.libhandwritingpreview.engine.PreMiCurveEngine;
import com.miui.maml.folme.AnimatedProperty;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import xc.engine.core.graphics.engine.core.MiMathUtils;

/* JADX INFO: compiled from: PreCamera.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\"\n\u0002\u0010\t\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u000b\u0018\u0000 r2\u00020\u0001:\u0001rB\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0016\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020\u00142\u0006\u0010/\u001a\u00020\u0014J\u0010\u00100\u001a\u00020-2\u0006\u00101\u001a\u00020\u000eH\u0002J\u0010\u00102\u001a\u00020-2\u0006\u00101\u001a\u00020\u000eH\u0002J\b\u00103\u001a\u00020-H\u0014J\u0006\u00104\u001a\u00020-J\u0006\u00105\u001a\u00020-J\u0006\u00106\u001a\u00020-J\b\u00107\u001a\u00020-H\u0002J\u0016\u00108\u001a\u00020-2\u0006\u00109\u001a\u00020\u00122\u0006\u0010:\u001a\u00020\u000eJ\u000e\u0010;\u001a\u00020-2\u0006\u0010<\u001a\u00020\u000eJ\b\u0010=\u001a\u00020\u0012H\u0002J\b\u0010>\u001a\u00020-H\u0002J\b\u0010?\u001a\u00020-H\u0002J\b\u0010@\u001a\u00020-H\u0002J\b\u0010A\u001a\u00020-H\u0002J\b\u0010B\u001a\u00020-H\u0002J\b\u0010C\u001a\u00020-H\u0002J\u0016\u0010D\u001a\u00020-2\u0006\u0010E\u001a\u00020\u000e2\u0006\u0010F\u001a\u00020\u000eJ\u0010\u0010G\u001a\u00020-2\b\b\u0002\u0010H\u001a\u00020\u0012J\u0006\u0010I\u001a\u00020-J\b\u0010J\u001a\u00020\u0012H\u0002J\u0006\u0010K\u001a\u00020\u0012J\u0012\u0010L\u001a\u00020-2\b\b\u0002\u0010M\u001a\u00020\u0012H\u0002J\u001c\u0010N\u001a\u00020-2\b\b\u0002\u0010M\u001a\u00020\u00122\b\b\u0002\u0010O\u001a\u00020PH\u0002J\u0012\u0010Q\u001a\u00020-2\b\b\u0002\u0010M\u001a\u00020\u0012H\u0002J\u001c\u0010R\u001a\u00020-2\b\b\u0002\u0010M\u001a\u00020\u00122\b\b\u0002\u0010O\u001a\u00020PH\u0002J\u001e\u0010\r\u001a\u00020-2\u0006\u0010S\u001a\u00020\u000e2\u0006\u0010T\u001a\u00020\u000e2\u0006\u0010U\u001a\u00020\u000eJ\u0016\u0010V\u001a\u00020-2\u0006\u0010W\u001a\u00020\u000e2\u0006\u0010X\u001a\u00020\u000eJ\u0018\u0010Y\u001a\u00020\u000e2\u0006\u0010Z\u001a\u00020\u000e2\u0006\u0010[\u001a\u00020\u0012H\u0002J\u0006\u0010\\\u001a\u00020\u0012J\u0016\u0010]\u001a\u00020-2\u0006\u0010^\u001a\u00020\u00142\u0006\u0010M\u001a\u00020\u0012J\b\u0010_\u001a\u00020-H\u0002J\u0010\u0010`\u001a\u00020-2\u0006\u0010^\u001a\u00020\u0014H\u0002J\u0010\u0010a\u001a\u00020-2\u0006\u0010^\u001a\u00020\u0014H\u0002J\u0010\u0010b\u001a\u00020\u000e2\u0006\u0010^\u001a\u00020\u0014H\u0002J\u0006\u0010c\u001a\u00020\u000eJ\u0006\u0010d\u001a\u00020eJ\u0006\u0010f\u001a\u00020\u000eJ\u0006\u0010g\u001a\u00020hJ\u0006\u0010i\u001a\u00020\u000eJ\u0006\u0010j\u001a\u00020\u0012J\u0016\u0010k\u001a\u00020-2\u0006\u0010l\u001a\u00020\u000e2\u0006\u0010m\u001a\u00020\u000eJ\u0006\u0010n\u001a\u00020\u0007J\u0006\u0010o\u001a\u00020\u0012J \u0010p\u001a\u00020-2\u0006\u0010.\u001a\u00020\u000e2\u0006\u0010/\u001a\u00020\u000e2\u0006\u0010q\u001a\u00020\u0012H\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\n\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0018\u001a\u00020\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u0016\u0010\u001d\u001a\n \u001f*\u0004\u0018\u00010\u001e0\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010 \u001a\u0004\u0018\u00010!X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R\u001a\u0010&\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)R\u001a\u0010*\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010'\"\u0004\b+\u0010)¨\u0006s"}, d2 = {"Lcom/miui/libhandwritingpreview/system/PreCamera;", "Lcom/miui/libhandwritingpreview/system/PreBaseEngineStruct;", "engine", "Lcom/miui/libhandwritingpreview/engine/PreMiCurveEngine;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/libhandwritingpreview/engine/PreMiCurveEngine;)V", EditStat.ELEMENT_CENTER, "Landroid/graphics/PointF;", "centerInSaveSpace", "topCenterInSaveSpace", "sizeInPixel", "getSizeInPixel", "()Landroid/graphics/PointF;", "scale", "", "mScaleFactor", "lastDrawRatio", "bViewportInitialized", "", "topPageIndex", "", "titleHeightInPixels", "bCameraOperated", "bIsFlinging", "realtimeDrawRatio", "getRealtimeDrawRatio", "()F", "setRealtimeDrawRatio", "(F)V", "animator", "Landroid/animation/ValueAnimator;", "kotlin.jvm.PlatformType", "viewportEventListener", "Lcom/miui/libhandwritingpreview/system/GLViewportEventListener;", "getViewportEventListener", "()Lcom/miui/libhandwritingpreview/system/GLViewportEventListener;", "setViewportEventListener", "(Lcom/miui/libhandwritingpreview/system/GLViewportEventListener;)V", "isTitleFocus", "()Z", "setTitleFocus", "(Z)V", "isRefreshCheckView", "setRefreshCheckView", "setScreenSize", "", "newWidth", "newHeight", "setCenterX", "value", "setCenterY", "onInit", "initViewPort", "onUnlimitedToPaginated", "resetViewPortCenter", "initIfPortraitScreen", "resetScale", "isRestRecordScale", "recordScale", "setTitleHeight", "pixels", "isNeedCentralizeView", "checkAndCentralizeView", "limitLeftBorder", "limitRightBorder", "limitInfinite", "checkTopPageIndex", "checkShouldShowTitleView", "scrollByOffsetSS", "xOffset", "yOffset", "refreshView", "bUpdateCurve", "onScrollEnd", "isNeedToScrollBackToTop", "isNeedToScrollBackToBottom", "checkAndScrollBackToTop", "bAnim", "checkAndScrollBackToBottom", "duration", "", "scrollBackToTop", "scrollBackToBottom", "scaleFactor", "scaleCenterX", "scaleCenterY", "fling", "velocityX", "velocityY", "calculateEdgeDistance", "deltaSpeed", "bFlingVertically", "stopFling", "jumpOrScrollToPage", "pageIndex", "stopAnimator", "jumpToPage", "scrollToPage", "getDistanceWSToTargetPage", "getCurrentTop", "getViewArea", "Landroid/graphics/RectF;", "getScaleRatio", "getScaleToDouble", "", "getFactorScale", "isFlinging", "trySetCenterTo", AnimatedProperty.PROPERTY_NAME_X, AnimatedProperty.PROPERTY_NAME_Y, "getCenter", "isViewPortInitialized", "checkView", "isRefresh", "Companion", "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PreCamera extends PreBaseEngineStruct {
    private static final float SCALE_MAX = 3.0f;
    private static final float SCALE_MIN = 0.55f;
    private final ValueAnimator animator;
    private boolean bCameraOperated;
    private boolean bIsFlinging;
    private boolean bViewportInitialized;
    private final PointF center;
    private final PointF centerInSaveSpace;
    private boolean isRefreshCheckView;
    private boolean isTitleFocus;
    private float lastDrawRatio;
    private float mScaleFactor;
    private float realtimeDrawRatio;
    private float scale;
    private final PointF sizeInPixel;
    private float titleHeightInPixels;
    private final PointF topCenterInSaveSpace;
    private int topPageIndex;
    private GLViewportEventListener viewportEventListener;
    private static final String TAG = "PreCamera";

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PreCamera(PreMiCurveEngine engine) {
        super(engine);
        Intrinsics.checkNotNullParameter(engine, "engine");
        this.center = new PointF();
        this.centerInSaveSpace = new PointF();
        this.topCenterInSaveSpace = new PointF();
        this.sizeInPixel = new PointF();
        this.scale = 1.0f;
        this.mScaleFactor = 1.0f;
        this.lastDrawRatio = 1.0f;
        this.realtimeDrawRatio = 1.0f;
        this.animator = ValueAnimator.ofFloat(0.0f, 1.0f);
    }

    public final PointF getSizeInPixel() {
        return this.sizeInPixel;
    }

    public final float getRealtimeDrawRatio() {
        return this.realtimeDrawRatio;
    }

    public final void setRealtimeDrawRatio(float f) {
        this.realtimeDrawRatio = f;
    }

    public final GLViewportEventListener getViewportEventListener() {
        return this.viewportEventListener;
    }

    public final void setViewportEventListener(GLViewportEventListener gLViewportEventListener) {
        this.viewportEventListener = gLViewportEventListener;
    }

    /* JADX INFO: renamed from: isTitleFocus, reason: from getter */
    public final boolean getIsTitleFocus() {
        return this.isTitleFocus;
    }

    public final void setTitleFocus(boolean z) {
        this.isTitleFocus = z;
    }

    /* JADX INFO: renamed from: isRefreshCheckView, reason: from getter */
    public final boolean getIsRefreshCheckView() {
        return this.isRefreshCheckView;
    }

    public final void setRefreshCheckView(boolean z) {
        this.isRefreshCheckView = z;
    }

    public final void setScreenSize(int newWidth, int newHeight) {
        Log.d(TAG, "xl-- [camera] new setScreenSize " + newWidth + ' ' + newHeight);
        if (newWidth <= 0 || newHeight <= 0) {
            return;
        }
        if (!this.bViewportInitialized) {
            this.sizeInPixel.x = newWidth;
            this.sizeInPixel.y = newHeight;
            initViewPort();
            return;
        }
        stopAnimator();
        checkView(newWidth, newHeight, false);
    }

    private final void setCenterX(float value) {
        this.center.x = value;
        this.centerInSaveSpace.x = value / this.realtimeDrawRatio;
        this.topCenterInSaveSpace.x = value / this.realtimeDrawRatio;
    }

    private final void setCenterY(float value) {
        this.center.y = value;
        this.centerInSaveSpace.y = value / this.realtimeDrawRatio;
        this.topCenterInSaveSpace.y = (value - ((this.sizeInPixel.y * 0.5f) / this.scale)) / this.realtimeDrawRatio;
    }

    @Override // com.miui.libhandwritingpreview.system.BaseStruct
    protected void onInit() {
        this.animator.addListener(new Animator.AnimatorListener() { // from class: com.miui.libhandwritingpreview.system.PreCamera.onInit.1
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
                PreCamera.this.refreshView(true);
                PreCamera.this.bIsFlinging = false;
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
                PreCamera.this.refreshView(true);
                PreCamera.this.bIsFlinging = false;
            }
        });
    }

    public final void initViewPort() {
        if (this.bViewportInitialized) {
            return;
        }
        Log.d(TAG, "xl-- [Camera] initViewPort");
        if (this.sizeInPixel.x <= 0.0f || this.sizeInPixel.y <= 0.0f) {
            return;
        }
        setCenterY(this.sizeInPixel.y * 0.5f);
        if (getCurveEngine().isInfinite()) {
            setCenterX(this.sizeInPixel.x * 0.5f);
            getCurveEngine().getCurveSystem().update();
        } else {
            setCenterX(0.0f);
            initIfPortraitScreen();
        }
        this.lastDrawRatio = this.realtimeDrawRatio;
        this.bViewportInitialized = true;
    }

    public final void onUnlimitedToPaginated() {
        if (this.center.x == this.sizeInPixel.x * 0.5f && this.center.y == this.sizeInPixel.y * 0.5f) {
            setCenterX(0.0f);
            initIfPortraitScreen();
        }
        if (this.isRefreshCheckView) {
            Log.d(TAG, "refreshCheckView");
            checkView(this.sizeInPixel.x, this.sizeInPixel.y, true);
        }
    }

    public final void resetViewPortCenter() {
        setCenterX(getCurveEngine().isInfinite() ? (this.sizeInPixel.x / this.scale) * 0.5f : 0.0f);
        setCenterY((this.sizeInPixel.y / this.scale) * 0.5f);
    }

    private final void initIfPortraitScreen() {
        float aspectRatio = getCurveEngine().getAspectRatio();
        if (aspectRatio < 1.0f) {
            this.mScaleFactor = aspectRatio;
            this.scale *= aspectRatio;
            setCenterY((this.sizeInPixel.y / this.scale) * 0.5f);
        }
    }

    public final void resetScale(boolean isRestRecordScale, float recordScale) {
        Log.d(TAG, "xl-- [Camera] resetScale: isNeedReset:" + isRestRecordScale + ", Scale:" + recordScale);
        if (isRestRecordScale) {
            this.scale = recordScale * this.mScaleFactor;
            checkAndScrollBackToTop(false);
            checkAndScrollBackToBottom$default(this, false, 0L, 2, null);
            refreshView(true);
        }
    }

    public final void setTitleHeight(float pixels) {
        this.titleHeightInPixels = pixels;
    }

    private final boolean isNeedCentralizeView() {
        if (getCurveEngine().isInfinite()) {
            return false;
        }
        return getCurveEngine().getPageSystem().getCurrentViewportPagesArea().width() < getViewArea().width();
    }

    private final void checkAndCentralizeView() {
        if (getCurveEngine().isInfinite()) {
            return;
        }
        RectF currentViewportPagesArea = getCurveEngine().getPageSystem().getCurrentViewportPagesArea();
        if (currentViewportPagesArea.width() < getViewArea().width()) {
            setCenterX(currentViewportPagesArea.centerX());
        }
    }

    private final void limitLeftBorder() {
        if (getCurveEngine().isInfinite()) {
            return;
        }
        float allowedMaxLeft = getCurveEngine().getPageSystem().getAllowedMaxLeft();
        float f = this.center.x - ((this.sizeInPixel.x * 0.5f) / this.scale);
        if (f < allowedMaxLeft) {
            setCenterX(this.center.x + (allowedMaxLeft - f));
        }
    }

    private final void limitRightBorder() {
        if (getCurveEngine().isInfinite()) {
            return;
        }
        float allowedMaxRight = getCurveEngine().getPageSystem().getAllowedMaxRight();
        float f = this.center.x + ((this.sizeInPixel.x * 0.5f) / this.scale);
        if (f > allowedMaxRight) {
            setCenterX(this.center.x - (f - allowedMaxRight));
        }
    }

    private final void limitInfinite() {
        if (getCurveEngine().isInfinite()) {
            RectF infiniteBoundary = getCurveEngine().getDataManager().getInfiniteBoundary();
            if (this.center.x < infiniteBoundary.left) {
                setCenterX(infiniteBoundary.left);
            }
            if (this.center.x > infiniteBoundary.right) {
                setCenterX(infiniteBoundary.right);
            }
            float scaleRatio = (infiniteBoundary.top - (this.titleHeightInPixels / getCurveEngine().getScaleRatio())) - 1.0f;
            if (this.center.y < scaleRatio) {
                setCenterY(scaleRatio);
            }
            if (this.center.y > infiniteBoundary.bottom) {
                setCenterY(infiniteBoundary.bottom);
            }
        }
    }

    private final void checkTopPageIndex() {
        RectF viewArea = getViewArea();
        int pageCount = getCurveEngine().getPageSystem().getPageCount();
        float f = 0.0f;
        int i = 0;
        for (int i2 = 0; i2 < pageCount; i2++) {
            RectF area = getCurveEngine().getPageSystem().getPageData(i2).getArea();
            if (area.bottom >= viewArea.top && area.top <= viewArea.bottom) {
                float fMin = Math.min(area.bottom, viewArea.bottom) - Math.max(area.top, viewArea.top);
                if (fMin > f) {
                    i = i2;
                    f = fMin;
                } else if (fMin == f && area.top > getCurveEngine().getPageSystem().getPageData(i).getArea().top) {
                    i = i2;
                }
            }
        }
        if (i != this.topPageIndex) {
            this.topPageIndex = i;
            GLViewportEventListener gLViewportEventListener = this.viewportEventListener;
            if (gLViewportEventListener != null) {
                gLViewportEventListener.onTopPageChanged(i);
            }
        }
    }

    private final void checkShouldShowTitleView() {
        GLViewportEventListener gLViewportEventListener;
        if (getCurveEngine().getPageSystem().getScreenTopToPagesTopDistance() >= 240.0f || (gLViewportEventListener = this.viewportEventListener) == null) {
            return;
        }
        gLViewportEventListener.onTitleChanged();
    }

    public final void scrollByOffsetSS(float xOffset, float yOffset) {
        this.animator.cancel();
        setCenterX(this.center.x + (xOffset / this.scale));
        setCenterY(this.center.y + (yOffset / this.scale));
        limitInfinite();
        checkAndCentralizeView();
        refreshView(false);
        this.bCameraOperated = true;
    }

    public static /* synthetic */ void refreshView$default(PreCamera preCamera, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        preCamera.refreshView(z);
    }

    public final void refreshView(final boolean bUpdateCurve) {
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.miui.libhandwritingpreview.system.PreCamera$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                PreCamera.refreshView$lambda$0(this.f$0, bUpdateCurve);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void refreshView$lambda$0(PreCamera preCamera, boolean z) {
        if (!preCamera.getCurveEngine().isInfinite()) {
            if (preCamera.isNeedCentralizeView()) {
                preCamera.checkAndCentralizeView();
            } else {
                preCamera.limitLeftBorder();
                preCamera.limitRightBorder();
            }
            preCamera.checkTopPageIndex();
        }
        preCamera.checkShouldShowTitleView();
        if (z) {
            preCamera.getCurveEngine().getCurveSystem().update();
        }
        PreMiCurveEngine.requestMultiBufferRender$default(preCamera.getCurveEngine(), null, 1, null);
    }

    public final void onScrollEnd() {
        if (!getCurveEngine().isInfinite() && this.bCameraOperated) {
            this.bCameraOperated = false;
            if (isNeedToScrollBackToTop()) {
                scrollBackToTop$default(this, false, 1, null);
            } else if (isNeedToScrollBackToBottom()) {
                scrollBackToBottom$default(this, false, 0L, 3, null);
            } else {
                if (this.bIsFlinging) {
                    return;
                }
                refreshView(true);
            }
        }
    }

    private final boolean isNeedToScrollBackToTop() {
        return getViewArea().top < 0.0f;
    }

    public final boolean isNeedToScrollBackToBottom() {
        return getViewArea().bottom > getCurveEngine().getPageSystem().getAllowedMaxHeight();
    }

    static /* synthetic */ void checkAndScrollBackToTop$default(PreCamera preCamera, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        preCamera.checkAndScrollBackToTop(z);
    }

    private final void checkAndScrollBackToTop(boolean bAnim) {
        if (isNeedToScrollBackToTop()) {
            scrollBackToTop(bAnim);
        }
    }

    static /* synthetic */ void checkAndScrollBackToBottom$default(PreCamera preCamera, boolean z, long j, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        if ((i & 2) != 0) {
            j = 300;
        }
        preCamera.checkAndScrollBackToBottom(z, j);
    }

    private final void checkAndScrollBackToBottom(boolean bAnim, long duration) {
        if (isNeedToScrollBackToBottom()) {
            scrollBackToBottom(bAnim, duration);
        }
    }

    static /* synthetic */ void scrollBackToTop$default(PreCamera preCamera, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        preCamera.scrollBackToTop(z);
    }

    private final void scrollBackToTop(boolean bAnim) {
        final float f = this.center.y;
        final Ref.FloatRef floatRef = new Ref.FloatRef();
        floatRef.element = (this.sizeInPixel.y / this.scale) * 0.5f;
        if (this.titleHeightInPixels > 0.0f && (-getCurrentTop()) * this.scale > this.titleHeightInPixels) {
            floatRef.element -= this.titleHeightInPixels / this.scale;
        }
        if (!bAnim) {
            setCenterY(floatRef.element);
            return;
        }
        this.animator.cancel();
        this.animator.setDuration(300L);
        this.animator.removeAllUpdateListeners();
        this.animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.libhandwritingpreview.system.PreCamera$$ExternalSyntheticLambda3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                PreCamera.scrollBackToTop$lambda$1(this.f$0, f, floatRef, valueAnimator);
            }
        });
        this.animator.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void scrollBackToTop$lambda$1(PreCamera preCamera, float f, Ref.FloatRef floatRef, ValueAnimator animation) {
        Intrinsics.checkNotNullParameter(animation, "animation");
        Object animatedValue = animation.getAnimatedValue();
        Intrinsics.checkNotNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
        preCamera.setCenterY(f + ((floatRef.element - f) * ((float) Math.sqrt(((Float) animatedValue).floatValue()))));
        preCamera.checkShouldShowTitleView();
        preCamera.getCurveEngine().requestMultiBufferRender(PreMiCurveEngine.ERefreshReason.Force);
    }

    static /* synthetic */ void scrollBackToBottom$default(PreCamera preCamera, boolean z, long j, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        if ((i & 2) != 0) {
            j = 300;
        }
        preCamera.scrollBackToBottom(z, j);
    }

    private final void scrollBackToBottom(boolean bAnim, long duration) {
        float allowedMaxHeight = getCurveEngine().getPageSystem().getAllowedMaxHeight();
        final float f = this.center.y;
        final float f2 = allowedMaxHeight - ((this.sizeInPixel.y / this.scale) * 0.5f);
        if (!bAnim) {
            setCenterY(f2);
            return;
        }
        this.animator.cancel();
        this.animator.setDuration(duration);
        this.animator.removeAllUpdateListeners();
        this.animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.libhandwritingpreview.system.PreCamera$$ExternalSyntheticLambda5
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                PreCamera.scrollBackToBottom$lambda$3(this.f$0, f, f2, valueAnimator);
            }
        });
        this.animator.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void scrollBackToBottom$lambda$3(final PreCamera preCamera, float f, float f2, ValueAnimator animation) {
        Intrinsics.checkNotNullParameter(animation, "animation");
        Object animatedValue = animation.getAnimatedValue();
        Intrinsics.checkNotNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
        preCamera.setCenterY(f + ((f2 - f) * ((float) Math.sqrt(((Float) animatedValue).floatValue()))));
        preCamera.getCurveEngine().requestMultiBufferRender(PreMiCurveEngine.ERefreshReason.Force);
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.miui.libhandwritingpreview.system.PreCamera$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                PreCamera.scrollBackToBottom$lambda$3$lambda$2(this.f$0);
            }
        });
        if (preCamera.getViewArea().bottom < preCamera.getCurveEngine().getPageSystem().getAllowedMaxHeight()) {
            preCamera.animator.cancel();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void scrollBackToBottom$lambda$3$lambda$2(PreCamera preCamera) {
        GLViewportEventListener gLViewportEventListener = preCamera.viewportEventListener;
        if (gLViewportEventListener != null) {
            gLViewportEventListener.onScrollBack();
        }
    }

    public final void scale(float scaleFactor, float scaleCenterX, float scaleCenterY) {
        this.animator.cancel();
        float f = this.scale;
        MiMathUtils.Companion companion = MiMathUtils.INSTANCE;
        float f2 = this.scale * scaleFactor;
        float f3 = this.mScaleFactor;
        float fClamp = companion.clamp(f2, 0.55f * f3, f3 * 3.0f);
        this.scale = fClamp;
        float f4 = fClamp / f;
        PointF worldSpacePointFromScreenSpacePoint = getCurveEngine().getWorldSpacePointFromScreenSpacePoint(new PointF(scaleCenterX, scaleCenterY));
        setCenterX(worldSpacePointFromScreenSpacePoint.x + ((this.center.x - worldSpacePointFromScreenSpacePoint.x) / f4));
        setCenterY(worldSpacePointFromScreenSpacePoint.y + ((this.center.y - worldSpacePointFromScreenSpacePoint.y) / f4));
        checkAndCentralizeView();
        refreshView(false);
        this.bCameraOperated = true;
    }

    public final void fling(final float velocityX, float velocityY) {
        if (getCurveEngine().isInfinite() || isNeedToScrollBackToTop() || isNeedToScrollBackToBottom() || Math.abs(velocityX) < 5.0f || Math.abs(velocityY) < 5.0f) {
            return;
        }
        final boolean z = Math.abs(velocityY) > Math.abs(velocityX);
        if (z || !isNeedCentralizeView()) {
            if (z) {
                velocityX = velocityY;
            }
            this.animator.cancel();
            this.animator.setDuration(1000L);
            this.animator.removeAllUpdateListeners();
            this.animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.libhandwritingpreview.system.PreCamera$$ExternalSyntheticLambda6
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    PreCamera.fling$lambda$4(velocityX, this, z, valueAnimator);
                }
            });
            this.bIsFlinging = true;
            this.animator.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void fling$lambda$4(float f, PreCamera preCamera, boolean z, ValueAnimator animation) {
        Intrinsics.checkNotNullParameter(animation, "animation");
        Object animatedValue = animation.getAnimatedValue();
        Intrinsics.checkNotNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
        float scaleRatio = (f / preCamera.getCurveEngine().getScaleRatio()) * (1.0f - ((Float) animatedValue).floatValue()) * 0.004f;
        float fCalculateEdgeDistance = preCamera.calculateEdgeDistance(scaleRatio, z);
        if (fCalculateEdgeDistance < 1000.0f) {
            scaleRatio *= fCalculateEdgeDistance / 1000.0f;
        }
        if (z) {
            preCamera.setCenterY(preCamera.center.y - scaleRatio);
        } else {
            preCamera.setCenterX(preCamera.center.x - scaleRatio);
        }
        preCamera.getCurveEngine().requestMultiBufferRender(PreMiCurveEngine.ERefreshReason.Force);
        if (Math.abs(scaleRatio) < 0.5f) {
            preCamera.animator.cancel();
        }
        if (z || !preCamera.isNeedCentralizeView()) {
            return;
        }
        preCamera.animator.cancel();
    }

    private final float calculateEdgeDistance(float deltaSpeed, boolean bFlingVertically) {
        if (bFlingVertically) {
            if (deltaSpeed > 0.0f) {
                return getCurveEngine().getPageSystem().getScreenTopToPagesTopDistance();
            }
            return Math.abs(getCurveEngine().getPageSystem().getScreenBottomToPagesBottomDistance());
        }
        if (deltaSpeed > 0.0f) {
            return Math.abs(getCurveEngine().getPageSystem().getAllowedMaxLeft() - (this.center.x - ((this.sizeInPixel.x * 0.5f) / this.scale)));
        }
        return Math.abs(getCurveEngine().getPageSystem().getAllowedMaxRight() - (this.center.x + ((this.sizeInPixel.x * 0.5f) / this.scale)));
    }

    public final boolean stopFling() {
        if (!this.animator.isRunning()) {
            return false;
        }
        this.animator.cancel();
        onScrollEnd();
        return true;
    }

    public final void jumpOrScrollToPage(int pageIndex, boolean bAnim) {
        Log.d(TAG, "xl-- [Camera] jumpOrScrollToPage: " + pageIndex);
        if (!getCurveEngine().isInfinite() && getCurveEngine().getPageSystem().isValidPageIndex(pageIndex)) {
            float distanceWSToTargetPage = getDistanceWSToTargetPage(pageIndex) * this.scale;
            if (this.animator.isRunning()) {
                this.animator.cancel();
            }
            if (!bAnim || Math.abs(distanceWSToTargetPage) > this.sizeInPixel.y * 2.0f) {
                jumpToPage(pageIndex);
            } else {
                scrollToPage(pageIndex);
            }
        }
    }

    private final void stopAnimator() {
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.miui.libhandwritingpreview.system.PreCamera$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                PreCamera.stopAnimator$lambda$5(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void stopAnimator$lambda$5(PreCamera preCamera) {
        if (preCamera.animator.isRunning()) {
            preCamera.animator.cancel();
        }
    }

    private final void jumpToPage(int pageIndex) {
        if (!getCurveEngine().isInfinite() && getCurveEngine().getPageSystem().isValidPageIndex(pageIndex)) {
            stopAnimator();
            setCenterY(this.center.y + getDistanceWSToTargetPage(pageIndex));
            refreshView$default(this, false, 1, null);
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.miui.libhandwritingpreview.system.PreCamera$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    PreCamera.jumpToPage$lambda$6(this.f$0);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void jumpToPage$lambda$6(PreCamera preCamera) {
        GLViewportEventListener gLViewportEventListener = preCamera.viewportEventListener;
        if (gLViewportEventListener != null) {
            gLViewportEventListener.onScrollBack();
        }
    }

    private final void scrollToPage(int pageIndex) {
        if (!getCurveEngine().isInfinite() && getCurveEngine().getPageSystem().isValidPageIndex(pageIndex)) {
            float distanceWSToTargetPage = getDistanceWSToTargetPage(pageIndex);
            final float f = this.center.y;
            final float f2 = distanceWSToTargetPage + f;
            this.animator.cancel();
            this.animator.setDuration(700L);
            this.animator.removeAllUpdateListeners();
            this.animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.libhandwritingpreview.system.PreCamera$$ExternalSyntheticLambda1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    PreCamera.scrollToPage$lambda$8(this.f$0, f, f2, valueAnimator);
                }
            });
            this.animator.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void scrollToPage$lambda$8(final PreCamera preCamera, float f, float f2, ValueAnimator animation) {
        Intrinsics.checkNotNullParameter(animation, "animation");
        Object animatedValue = animation.getAnimatedValue();
        Intrinsics.checkNotNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
        preCamera.setCenterY(f + ((f2 - f) * ((float) Math.sqrt(((Float) animatedValue).floatValue()))));
        preCamera.getCurveEngine().requestMultiBufferRender(PreMiCurveEngine.ERefreshReason.Force);
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.miui.libhandwritingpreview.system.PreCamera$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                PreCamera.scrollToPage$lambda$8$lambda$7(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void scrollToPage$lambda$8$lambda$7(PreCamera preCamera) {
        GLViewportEventListener gLViewportEventListener = preCamera.viewportEventListener;
        if (gLViewportEventListener != null) {
            gLViewportEventListener.onScrollBack();
        }
    }

    private final float getDistanceWSToTargetPage(int pageIndex) {
        if (!getCurveEngine().getPageSystem().isValidPageIndex(pageIndex)) {
            return Float.MAX_VALUE;
        }
        float f = getCurveEngine().getPageSystem().getPageData(pageIndex).getArea().top;
        float f2 = this.sizeInPixel.y / this.scale;
        float f3 = this.center.y - (0.5f * f2);
        float allowedMaxHeight = getCurveEngine().getPageSystem().getAllowedMaxHeight();
        if (f + f2 > allowedMaxHeight) {
            f = allowedMaxHeight - f2;
        }
        return f - f3;
    }

    public final float getCurrentTop() {
        return this.center.y - ((this.sizeInPixel.y / this.scale) * 0.5f);
    }

    public final RectF getViewArea() {
        float f = (this.sizeInPixel.x * 0.5f) / this.scale;
        float f2 = (this.sizeInPixel.y * 0.5f) / this.scale;
        return new RectF(this.center.x - f, this.center.y - f2, this.center.x + f, this.center.y + f2);
    }

    /* JADX INFO: renamed from: getScaleRatio, reason: from getter */
    public final float getScale() {
        return this.scale;
    }

    public final double getScaleToDouble() {
        return this.scale;
    }

    public final float getFactorScale() {
        return this.scale / this.mScaleFactor;
    }

    /* JADX INFO: renamed from: isFlinging, reason: from getter */
    public final boolean getBIsFlinging() {
        return this.bIsFlinging;
    }

    public final void trySetCenterTo(float x, float y) {
        PreCamera preCamera;
        Log.d(TAG, "xl-- [camera] trySetCenterTo: " + x + ' ' + y);
        setCenterX(x);
        setCenterY(y);
        if (getCurveEngine().isInfinite()) {
            preCamera = this;
        } else {
            checkAndCentralizeView();
            checkAndScrollBackToTop(false);
            preCamera = this;
            checkAndScrollBackToBottom$default(preCamera, false, 0L, 2, null);
        }
        preCamera.checkView(preCamera.sizeInPixel.x, preCamera.sizeInPixel.y, true);
    }

    public final PointF getCenter() {
        return new PointF(this.center);
    }

    /* JADX INFO: renamed from: isViewPortInitialized, reason: from getter */
    public final boolean getBViewportInitialized() {
        return this.bViewportInitialized;
    }

    private final void checkView(float newWidth, float newHeight, boolean isRefresh) {
        PreCamera preCamera;
        if (this.bViewportInitialized) {
            this.sizeInPixel.x = newWidth;
            this.sizeInPixel.y = newHeight;
            float factorScale = getFactorScale();
            this.mScaleFactor = 1.0f;
            boolean zIsInfinite = getCurveEngine().isInfinite();
            Log.d(TAG, "isInfinite = " + zIsInfinite);
            if (zIsInfinite) {
                this.isRefreshCheckView = true;
                PointF pointF = new PointF(this.centerInSaveSpace);
                setCenterX(pointF.x * this.realtimeDrawRatio);
                setCenterY(pointF.y * this.realtimeDrawRatio);
                preCamera = this;
            } else {
                this.isRefreshCheckView = false;
                float toolbarHeight = newWidth / (newHeight + getCurveEngine().getToolbarHeight());
                if (toolbarHeight < 1.0f) {
                    this.mScaleFactor = toolbarHeight;
                }
                this.scale = factorScale * this.mScaleFactor;
                PointF pointF2 = new PointF(this.topCenterInSaveSpace);
                setCenterX(pointF2.x * this.realtimeDrawRatio);
                setCenterY((pointF2.y * this.realtimeDrawRatio) + ((this.sizeInPixel.y * 0.5f) / this.scale));
                checkAndCentralizeView();
                if (!this.isTitleFocus) {
                    checkAndScrollBackToTop(false);
                }
                preCamera = this;
                checkAndScrollBackToBottom$default(preCamera, false, 0L, 2, null);
            }
            preCamera.refreshView(isRefresh);
            preCamera.lastDrawRatio = preCamera.realtimeDrawRatio;
        }
    }
}
