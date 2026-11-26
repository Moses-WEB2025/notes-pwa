package com.miui.floatwindow.feature.todo;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import com.miui.common.tool.UIUtils;
import com.miui.todo.view.BaseTodoRecyclerView;

/* JADX INFO: loaded from: classes2.dex */
public class FwTodoRecyclerView extends BaseTodoRecyclerView {
    private boolean mEnableScroll;
    private boolean mInDragMode;
    protected int mMaxHeight;

    public FwTodoRecyclerView(Context context) {
        this(context, null);
        init();
    }

    public FwTodoRecyclerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        init();
    }

    public FwTodoRecyclerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mEnableScroll = true;
        this.mMaxHeight = 0;
        this.mInDragMode = false;
        init();
    }

    private void init() {
        updateMaxHeight();
    }

    protected void updateMaxHeight() {
        this.mMaxHeight = UIUtils.getRealHeight(getContext());
    }

    public void setInDragMode(boolean z) {
        this.mInDragMode = z;
    }

    public boolean isInDragMode() {
        return this.mInDragMode;
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(this.mMaxHeight, Integer.MIN_VALUE));
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateMaxHeight();
        requestLayout();
    }

    @Override // com.miui.todo.view.BaseTodoRecyclerView, androidx.recyclerview.widget.SpringRecyclerView, androidx.recyclerview.widget.RemixRecyclerView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int childAdapterPosition = getChildAdapterPosition(findChildViewUnder((int) motionEvent.getX(), (int) motionEvent.getY()));
        if (childAdapterPosition == -1) {
            if (!isInSwipingMode()) {
                requestDisallowParentInterceptTouchEvent(true);
            }
            return true;
        }
        return super.onInterceptTouchEvent(motionEvent, childAdapterPosition);
    }

    @Override // com.miui.todo.view.BaseTodoRecyclerView, androidx.recyclerview.widget.SpringRecyclerView, androidx.recyclerview.widget.RemixRecyclerView, androidx.recyclerview.widget.RecyclerView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mEnableScroll) {
            return super.onTouchEvent(motionEvent);
        }
        return false;
    }

    @Override // com.miui.todo.view.BaseTodoRecyclerView
    public void handleMoveEvent(int i, ViewParent viewParent) {
        boolean z = true;
        boolean z2 = i > 0 && (this.mOldSwipedLayout.hasRightMenu() || this.mOldSwipedLayout.isLeftCompleteOpen());
        boolean z3 = i < 0 && (this.mOldSwipedLayout.hasLeftMenu() || this.mOldSwipedLayout.isRightCompleteOpen());
        if (isInSwipingMode() || ((FwTodoListAdapter) getAdapter()).isInDrag()) {
            viewParent.requestDisallowInterceptTouchEvent(z2 || z3);
            if (!z2 && !z3) {
                z = false;
            }
            requestDisallowParentInterceptTouchEvent(z);
            return;
        }
        viewParent.requestDisallowInterceptTouchEvent(false);
        requestDisallowParentInterceptTouchEvent(false);
    }

    public void setEnableScroll(boolean z) {
        this.mEnableScroll = z;
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.ViewParent
    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z) {
        return super.requestChildRectangleOnScreen(view, rect, z);
    }

    @Override // android.view.View
    protected void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (i == 8) {
            stopScroll();
        }
    }
}
