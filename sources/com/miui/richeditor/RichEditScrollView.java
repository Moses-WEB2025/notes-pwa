package com.miui.richeditor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import miuix.core.widget.NestedScrollView;
import miuix.springback.view.SpringBackLayout;

/* JADX INFO: loaded from: classes4.dex */
public class RichEditScrollView extends NestedScrollView {
    private final String TAG;
    protected boolean forbidComputeScroll;
    protected boolean forbidScroll;
    protected RichEditTextView mEditor;
    public boolean mIgnoreForbid;
    protected View mParentView;
    protected Rect mVisibleRect;

    protected void init() {
    }

    public RichEditScrollView(Context context) {
        super(context);
        this.TAG = "RichEditScrollView";
        this.forbidScroll = false;
        this.forbidComputeScroll = false;
        this.mIgnoreForbid = false;
        this.mVisibleRect = new Rect();
    }

    public RichEditScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.TAG = "RichEditScrollView";
        this.forbidScroll = false;
        this.forbidComputeScroll = false;
        this.mIgnoreForbid = false;
        this.mVisibleRect = new Rect();
    }

    public RichEditScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.TAG = "RichEditScrollView";
        this.forbidScroll = false;
        this.forbidComputeScroll = false;
        this.mIgnoreForbid = false;
        this.mVisibleRect = new Rect();
    }

    public void setIgnoreForbid(boolean z) {
        this.mIgnoreForbid = z;
    }

    public void setForbidScroll(boolean z) {
        this.forbidScroll = z;
    }

    @Override // miuix.core.widget.NestedScrollView, android.view.View
    protected void onFinishInflate() {
        this.mEditor = (RichEditTextView) findViewById(R.id.rich_editor);
        init();
        super.onFinishInflate();
    }

    @Override // miuix.core.widget.NestedScrollView, android.view.View
    public void scrollTo(int i, int i2) {
        if (this.mIgnoreForbid) {
            Logger.INSTANCE.i("RichEditScrollView", "scrollTo1 x=" + i + " y= " + i2);
            super.scrollTo(i, i2);
        } else {
            if (this.forbidScroll || this.forbidComputeScroll) {
                return;
            }
            super.scrollTo(i, i2);
        }
    }

    @Override // miuix.core.widget.NestedScrollView, android.view.View
    public void computeScroll() {
        if (this.mIgnoreForbid) {
            super.computeScroll();
        } else {
            if (this.forbidScroll || this.forbidComputeScroll) {
                return;
            }
            super.computeScroll();
        }
    }

    public void requestDisallowParentInterceptTouchEvent(boolean z) {
        ViewParent parent = getParent();
        parent.requestDisallowInterceptTouchEvent(z);
        while (parent != null) {
            if (parent instanceof SpringBackLayout) {
                ((SpringBackLayout) parent).internalRequestDisallowInterceptTouchEvent(z);
            }
            parent = parent.getParent();
        }
    }

    @Override // miuix.core.widget.NestedScrollView, android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        int size;
        int paddingRight;
        if (this.mEditor.getEditAreaView() != null) {
            size = ((((View.MeasureSpec.getSize(i) - getPaddingLeft()) - getPaddingRight()) - this.mEditor.getPaddingLeft()) - this.mEditor.getPaddingRight()) - this.mEditor.getEditAreaView().getPaddingLeft();
            paddingRight = this.mEditor.getEditAreaView().getPaddingRight();
        } else {
            size = ((View.MeasureSpec.getSize(i) - getPaddingLeft()) - getPaddingRight()) - this.mEditor.getPaddingLeft();
            paddingRight = this.mEditor.getPaddingRight();
        }
        this.mEditor.updateContentWidth(size - paddingRight);
        super.onMeasure(i, i2);
    }

    @Override // miuix.core.widget.NestedScrollView, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mParentView = (View) getParent();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        RichEditTextView richEditTextView = this.mEditor;
        if (richEditTextView != null) {
            richEditTextView.prepareImages();
            this.mEditor.drawImages(canvas);
        }
    }

    @Override // miuix.core.widget.NestedScrollView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.forbidComputeScroll = false;
        return super.onTouchEvent(motionEvent);
    }

    @Override // miuix.core.widget.NestedScrollView, android.view.ViewGroup, android.view.ViewParent
    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z) {
        return super.requestChildRectangleOnScreen(view, rect, z);
    }

    protected int getRealY() {
        return (int) (this.mParentView != null ? getY() + this.mParentView.getY() : getY());
    }
}
