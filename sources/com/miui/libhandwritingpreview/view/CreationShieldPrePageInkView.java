package com.miui.libhandwritingpreview.view;

import android.content.Context;
import android.content.res.Configuration;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.libhandwritingpreview.engine.PreMiCurveEngine;
import com.miui.libhandwritingpreview.interfaces.PreICreationEngine;
import com.miui.libhandwritingpreview.model.PageInkPreModel;
import com.miui.libhandwritingpreview.system.PreCamera;
import com.miui.maml.folme.AnimatedProperty;
import com.xiaomi.onetrack.b.e;
import com.xiaomi.verificationsdk.internal.Constants;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CreationShieldPrePageInkView.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0011\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J(\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\r2\u0006\u0010\u0017\u001a\u00020\r2\u0006\u0010\u0018\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\rH\u0014J0\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00020\r2\u0006\u0010\u001d\u001a\u00020\r2\u0006\u0010\u001e\u001a\u00020\r2\u0006\u0010\u001f\u001a\u00020\rH\u0014J\u0006\u0010 \u001a\u00020\u0015J\u0010\u0010!\u001a\u00020\u00152\u0006\u0010\"\u001a\u00020#H\u0014J\b\u0010$\u001a\u00020\u0015H\u0016J\u0010\u0010%\u001a\u00020\u00152\b\u0010&\u001a\u0004\u0018\u00010\u000bR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\f\u001a\u00020\r8F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f¨\u0006'"}, d2 = {"Lcom/miui/libhandwritingpreview/view/CreationShieldPrePageInkView;", "Landroid/widget/RelativeLayout;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;)V", "pageInkFunc", "Lcom/miui/libhandwritingpreview/model/PageInkPreModel;", "getPageInkFunc", "()Lcom/miui/libhandwritingpreview/model/PageInkPreModel;", "mHeadView", "Landroid/view/View;", "toolViewHeight", "", "getToolViewHeight", "()I", "dispatchTouchEvent", "", "event", "Landroid/view/MotionEvent;", "onSizeChanged", "", AnimatedProperty.PROPERTY_NAME_W, AnimatedProperty.PROPERTY_NAME_H, "oldw", "oldh", "onLayout", "changed", e.a, Constants.TIMESTAMP, Constants.RANDOM_LONG, "b", "updateHeadView", "onConfigurationChanged", "newConfig", "Landroid/content/res/Configuration;", "computeScroll", "setHeadView", "headView", "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class CreationShieldPrePageInkView extends RelativeLayout {
    private View mHeadView;
    private final PageInkPreModel pageInkFunc;

    public final int getToolViewHeight() {
        return 0;
    }

    public CreationShieldPrePageInkView(Context context) {
        super(context);
        this.pageInkFunc = new PageInkPreModel(this);
        setWillNotDraw(false);
    }

    public final PageInkPreModel getPageInkFunc() {
        return this.pageInkFunc;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        return this.pageInkFunc.dispatchTouchEvent(event);
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.pageInkFunc.onSizeChanged(w, h, oldw, oldh);
    }

    @Override // android.widget.RelativeLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        this.pageInkFunc.onLayout();
        updateHeadView();
    }

    /* JADX WARN: Code duplicated, block: B:16:0x0040  */
    public final void updateHeadView() {
        float scaleRatio;
        PreICreationEngine creationEngine;
        if (this.mHeadView == null) {
            return;
        }
        PagePreDrawView mDrawView = this.pageInkFunc.getMDrawView();
        PreMiCurveEngine curveEngine = (mDrawView == null || (creationEngine = mDrawView.getCreationEngine()) == null) ? null : creationEngine.getCurveEngine();
        if (curveEngine != null) {
            PreCamera camera = curveEngine.getCamera();
            View view = this.mHeadView;
            Intrinsics.checkNotNull(view);
            camera.setTitleHeight(view.getHeight());
            float screenTopToPagesTopDistance = curveEngine.getPageSystem().getScreenTopToPagesTopDistance();
            if (screenTopToPagesTopDistance < 0.0f) {
                scaleRatio = (-screenTopToPagesTopDistance) * curveEngine.getScaleRatio();
            } else {
                scaleRatio = 0.0f;
            }
        } else {
            scaleRatio = 0.0f;
        }
        View view2 = this.mHeadView;
        Intrinsics.checkNotNull(view2);
        ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) layoutParams;
        View view3 = this.mHeadView;
        Intrinsics.checkNotNull(view3);
        layoutParams2.setMargins(0, (int) Math.min(0.0d, scaleRatio - view3.getHeight()), 0, 0);
        View view4 = this.mHeadView;
        Intrinsics.checkNotNull(view4);
        view4.setLayoutParams(layoutParams2);
        View view5 = this.mHeadView;
        Intrinsics.checkNotNull(view5);
        view5.requestLayout();
        View view6 = this.mHeadView;
        Intrinsics.checkNotNull(view6);
        view6.setVisibility(scaleRatio <= 0.0f ? 8 : 0);
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        super.onConfigurationChanged(newConfig);
        this.pageInkFunc.onConfigurationChanged(newConfig);
    }

    @Override // android.view.View
    public void computeScroll() {
        super.computeScroll();
        if (isInEditMode()) {
            return;
        }
        this.pageInkFunc.computeScroll();
    }

    public final void setHeadView(View headView) {
        this.mHeadView = headView;
        Intrinsics.checkNotNull(headView);
        headView.bringToFront();
    }
}
