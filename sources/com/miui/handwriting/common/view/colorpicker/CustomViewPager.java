package com.miui.handwriting.common.view.colorpicker;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Scroller;
import com.miui.creation.common.R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class CustomViewPager extends ViewGroup {
    private static final int FLING_THRESHOLD_VELOCITY = 2000;
    private int currentIndex;
    private GestureDetector gestureDetector;
    private GradientDrawable mButtonSelect;
    private int mButtonTextSelect;
    private int mButtonTextUnSelect;
    private GradientDrawable mButtonUnselect;
    private int mChildIndex;
    private Button mGridButton;
    private OnPageChangeListener mOnPageChangeListener;
    private Scroller mScroller;
    private Button mSliderButton;
    private float mStartScrollX;
    private long mStartTime;
    private float mStartX;
    private int mViewHeight;
    private List<View> mViews;
    private float startX;

    public interface OnPageChangeListener {
        void onPageChange(int i);
    }

    public CustomViewPager(Context context) {
        super(context);
        this.mChildIndex = 0;
        this.mStartX = 0.0f;
        this.mStartScrollX = 0.0f;
        this.mStartTime = 0L;
        this.mViewHeight = 0;
        init();
    }

    public CustomViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mChildIndex = 0;
        this.mStartX = 0.0f;
        this.mStartScrollX = 0.0f;
        this.mStartTime = 0L;
        this.mViewHeight = 0;
        init();
    }

    private void init() {
        this.mViews = new ArrayList();
        this.currentIndex = 0;
        this.startX = 0.0f;
        this.mScroller = new Scroller(getContext());
        int dimensionPixelOffset = getContext().getResources().getDimensionPixelOffset(R.dimen.creation_color_picker_tab_button_radius);
        GradientDrawable gradientDrawable = new GradientDrawable();
        this.mButtonSelect = gradientDrawable;
        gradientDrawable.setShape(0);
        float f = dimensionPixelOffset;
        this.mButtonSelect.setCornerRadius(f);
        this.mButtonSelect.setColor(getContext().getResources().getColor(R.color.color_picker_button_background_select, null));
        GradientDrawable gradientDrawable2 = new GradientDrawable();
        this.mButtonUnselect = gradientDrawable2;
        gradientDrawable2.setShape(0);
        this.mButtonUnselect.setCornerRadius(f);
        this.mButtonUnselect.setColor(0);
        this.mButtonTextSelect = getContext().getResources().getColor(R.color.color_picker_button_select, null);
        this.mButtonTextUnSelect = getContext().getResources().getColor(R.color.color_picker_button_unselect, null);
    }

    public void setViews(List<View> list) {
        this.mViews = list;
        removeAllViews();
        Iterator<View> it = list.iterator();
        while (it.hasNext()) {
            addView(it.next());
        }
    }

    public void setGridButton(Button button) {
        this.mGridButton = button;
    }

    public void setSliderButton(Button button) {
        this.mSliderButton = button;
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size2 = View.MeasureSpec.getSize(i2);
        int iMax = 0;
        int iMax2 = 0;
        for (int i3 = 0; i3 < getChildCount(); i3++) {
            View childAt = getChildAt(i3);
            measureChild(childAt, i, i2);
            int measuredWidth = childAt.getMeasuredWidth();
            int measuredHeight = childAt.getMeasuredHeight();
            iMax = Math.max(iMax, measuredWidth);
            iMax2 = Math.max(iMax2, measuredHeight);
        }
        if (mode != 1073741824) {
            size = mode == Integer.MIN_VALUE ? Math.min(iMax, size) : iMax;
        }
        if (mode2 != 1073741824) {
            if (mode2 == Integer.MIN_VALUE) {
                int measuredHeight2 = 0;
                for (int i4 = 0; i4 < getChildCount(); i4++) {
                    measuredHeight2 += getChildAt(i4).getMeasuredHeight();
                }
                size2 = Math.min(iMax2, measuredHeight2);
            } else {
                size2 = iMax2;
            }
        }
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(size, mode);
        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(size2, mode2);
        this.mViewHeight = iMakeMeasureSpec2;
        super.onMeasure(iMakeMeasureSpec, iMakeMeasureSpec2);
    }

    public int getViewHeight() {
        return this.mViewHeight;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5 = 0;
        int i6 = 0;
        while (i5 < getChildCount()) {
            View childAt = getChildAt(i5);
            int measuredWidth = childAt.getMeasuredWidth() + i6;
            childAt.layout(i6, 0, measuredWidth, childAt.getMeasuredHeight());
            i5++;
            i6 = measuredWidth;
        }
    }

    private void smoothScrollTo(int i, int i2) {
        int scrollX = getScrollX();
        this.mScroller.startScroll(scrollX, 0, i - scrollX, i2);
        OnPageChangeListener onPageChangeListener = this.mOnPageChangeListener;
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageChange(this.mChildIndex);
        }
        invalidate();
    }

    public void setCurrentPage(int i) {
        int iMax = Math.max(0, Math.min(i, getChildCount() - 1));
        this.mChildIndex = iMax;
        smoothScrollTo(iMax * getWidth(), 0);
    }

    @Override // android.view.View
    public void computeScroll() {
        if (this.mScroller.computeScrollOffset()) {
            scrollTo(this.mScroller.getCurrX(), this.mScroller.getCurrY());
            postInvalidate();
        }
    }

    public void addPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.mOnPageChangeListener = onPageChangeListener;
    }
}
