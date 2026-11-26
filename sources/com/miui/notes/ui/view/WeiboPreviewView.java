package com.miui.notes.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.richeditor.share.element.ImageElement;
import com.miui.richeditor.share.render.Render;
import miuix.core.widget.NestedScrollView;
import miuix.springback.view.SpringBackLayout;

/* JADX INFO: loaded from: classes4.dex */
public class WeiboPreviewView extends View implements Render.BitmapLoadListener {
    private int mPreloadHeight;
    private Rect mRect;
    private Render mRender;
    private float mViewScale;

    public WeiboPreviewView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mViewScale = 1.0f;
        this.mRect = new Rect();
        this.mPreloadHeight = getResources().getDisplayMetrics().heightPixels;
    }

    public void setRender(Render render) {
        if (render == null) {
            Logger.INSTANCE.e("WeiboPreviewView", "render is null");
            return;
        }
        this.mRender = render;
        render.setPreloadHeight(this.mPreloadHeight);
        this.mRender.setBitmapLoadListener(this);
        invalidate();
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.mRender != null) {
            float width = (getWidth() * 1.0f) / this.mRender.getWidth();
            this.mViewScale = width;
            this.mRender.setScale(width);
            setMeasuredDimension(i, ((int) (this.mRender.getHeight() * this.mViewScale)) + UIUtils.dip2px(getContext(), 240.0f));
            return;
        }
        super.onMeasure(i, i2);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        if (isAttachedToWindow()) {
            doDraw(canvas, false);
        }
    }

    public void doDraw(Canvas canvas, boolean z) {
        if (this.mRender == null) {
            return;
        }
        float f = this.mViewScale;
        canvas.scale(f, f);
        if (getLocalVisibleRect(this.mRect)) {
            Logger.INSTANCE.d("SnsTest", "mRect.height() " + this.mRect.height() + " mRect.top " + this.mRect.top + " getY " + getY());
            this.mRender.draw(canvas, this.mRect.top, this.mRect.top + this.mRect.height(), z);
        }
    }

    @Override // com.miui.richeditor.share.render.Render.BitmapLoadListener
    public void onLoadFinish(ImageElement imageElement, Bitmap bitmap) {
        Rect bounds;
        if (isAttachedToWindow() && this.mRender != null && getLocalVisibleRect(this.mRect) && (bounds = this.mRender.getBounds(imageElement)) != null && Rect.intersects(this.mRect, bounds)) {
            ((ViewGroup) getParent()).invalidate(Math.max(this.mRect.left, bounds.left), Math.max(this.mRect.top, bounds.top), Math.min(this.mRect.right, bounds.right), Math.min(this.mRect.bottom, bounds.bottom));
        }
    }

    public static class WeiboScrollView extends NestedScrollView {
        private WeiboPreviewView mPreviewView;
        private SpringBackLayout mSblParentView;

        public WeiboScrollView(Context context) {
            super(context);
        }

        public WeiboScrollView(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public WeiboScrollView(Context context, AttributeSet attributeSet, int i) {
            super(context, attributeSet, i);
        }

        @Override // miuix.core.widget.NestedScrollView, android.view.View
        protected void onFinishInflate() {
            super.onFinishInflate();
            this.mPreviewView = (WeiboPreviewView) findViewById(R.id.preview);
        }

        @Override // miuix.core.widget.NestedScrollView, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
        protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
            super.onLayout(z, i, i2, i3, i4);
            if (this.mSblParentView == null && (getParent() instanceof SpringBackLayout)) {
                SpringBackLayout springBackLayout = (SpringBackLayout) getParent();
                this.mSblParentView = springBackLayout;
                springBackLayout.setOnScrollChangeListener(new View.OnScrollChangeListener() { // from class: com.miui.notes.ui.view.WeiboPreviewView.WeiboScrollView.1
                    @Override // android.view.View.OnScrollChangeListener
                    public void onScrollChange(View view, int i5, int i6, int i7, int i8) {
                        WeiboScrollView.this.invalidate();
                    }
                });
            }
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void dispatchDraw(Canvas canvas) {
            super.dispatchDraw(canvas);
            canvas.save();
            canvas.translate(this.mPreviewView.getLeft(), getPaddingTop());
            this.mPreviewView.invalidate();
            canvas.restore();
        }

        @Override // miuix.core.widget.NestedScrollView, android.view.View
        public void computeScroll() {
            super.computeScroll();
        }
    }
}
