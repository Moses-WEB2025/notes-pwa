package com.miui.libhandwritingpage.view;

import android.content.Context;
import android.content.res.Configuration;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.miui.libhandwritingpage.listener.PageFooterView;
import com.miui.libhandwritingpage.model.PageInkModel;
import xc.engine.core.graphics.engine.MiCurveEngine;

/* JADX INFO: loaded from: classes3.dex */
public class PageInkView extends RelativeLayout {
    private boolean isTitleFocus;
    private View mHeadView;
    private final PageInkModel mModel;

    public interface ShieldEventListener {
        void shieldEvent(boolean z);
    }

    public int getToolViewHeight() {
        return 0;
    }

    public void setTitleFocus(boolean z) {
        this.isTitleFocus = z;
    }

    public PageInkView(Context context, int i) {
        super(context);
        this.isTitleFocus = false;
        this.mModel = new PageInkModel(this, i);
        setWillNotDraw(false);
    }

    MiCurveEngine getCurveEngine() {
        PageItemView currentItemView;
        PageInkModel pageInkModel = this.mModel;
        if (pageInkModel == null || (currentItemView = pageInkModel.getCurrentItemView()) == null) {
            return null;
        }
        return currentItemView.getCurveEngine();
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        return this.mModel.dispatchTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean onHoverEvent(MotionEvent motionEvent) {
        this.mModel.hoverEvent(motionEvent);
        return false;
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (!this.isTitleFocus) {
            this.mModel.onSizeChanged(i, i2, i3, i4);
        } else {
            this.isTitleFocus = false;
        }
    }

    @Override // android.widget.RelativeLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mModel.onLayout();
        updateHeadView();
    }

    /* JADX WARN: Code duplicated, block: B:10:0x002d  */
    public void updateHeadView() {
        float scaleRatio;
        if (this.mHeadView == null) {
            return;
        }
        MiCurveEngine curveEngine = getCurveEngine();
        if (curveEngine != null) {
            curveEngine.getCamera().setTitleHeight(this.mHeadView.getHeight());
            float screenTopToPagesTopDistance = curveEngine.getPageSystem().getScreenTopToPagesTopDistance();
            if (screenTopToPagesTopDistance < 0.0f) {
                scaleRatio = (-screenTopToPagesTopDistance) * curveEngine.getScaleRatio();
            } else {
                scaleRatio = 0.0f;
            }
        } else {
            scaleRatio = 0.0f;
        }
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mHeadView.getLayoutParams();
        layoutParams.setMargins(0, (int) Math.min(0.0f, scaleRatio - this.mHeadView.getHeight()), 0, 0);
        this.mHeadView.setLayoutParams(layoutParams);
        this.mHeadView.requestLayout();
        this.mHeadView.setVisibility(scaleRatio <= 0.0f ? 8 : 0);
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mModel.onConfigurationChanged(configuration);
    }

    @Override // android.view.View
    public void computeScroll() {
        super.computeScroll();
        if (isInEditMode()) {
            return;
        }
        this.mModel.computeScroll();
    }

    public PageInkModel getPageInkFunc() {
        return this.mModel;
    }

    public void setHeadView(View view) {
        this.mHeadView = view;
        view.bringToFront();
    }

    public void setPageFootView(PageFooterView pageFooterView) {
        this.mModel.setPageFootView(pageFooterView);
    }
}
