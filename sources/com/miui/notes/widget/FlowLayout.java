package com.miui.notes.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.core.text.TextUtilsCompat;
import com.miui.notes.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/* JADX INFO: loaded from: classes4.dex */
public class FlowLayout extends LinearLayout {
    private static final int CENTER = 0;
    private static final int LEFT = -1;
    private static final int RIGHT = 1;
    private List<View> lineViews;
    protected List<List<View>> mAllViews;
    private int mGravity;
    protected List<Integer> mLineHeight;
    protected List<Integer> mLineWidth;

    public FlowLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mAllViews = new ArrayList();
        this.mLineHeight = new ArrayList();
        this.mLineWidth = new ArrayList();
        this.lineViews = new ArrayList();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.TagFlowLayout);
        this.mGravity = typedArrayObtainStyledAttributes.getInt(1, -1);
        if (TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == 1) {
            if (this.mGravity == -1) {
                this.mGravity = 1;
            } else {
                this.mGravity = -1;
            }
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    public FlowLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public FlowLayout(Context context) {
        this(context, null);
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        int paddingRight;
        int i3;
        int size = View.MeasureSpec.getSize(i);
        int mode = View.MeasureSpec.getMode(i);
        int size2 = View.MeasureSpec.getSize(i2);
        int mode2 = View.MeasureSpec.getMode(i2);
        int childCount = getChildCount();
        int i4 = 0;
        int iMax = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        while (i4 < childCount) {
            View childAt = getChildAt(i4);
            if (childAt.getVisibility() == 8) {
                if (i4 == childCount - 1) {
                    iMax = Math.max(i5, iMax);
                    i7 += i6;
                }
                i3 = size;
            } else {
                measureChild(childAt, i, i2);
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) childAt.getLayoutParams();
                i3 = size;
                int measuredWidth = childAt.getMeasuredWidth() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
                int measuredHeight = childAt.getMeasuredHeight() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
                int i8 = i5 + measuredWidth;
                if (i8 > (i3 - getPaddingLeft()) - getPaddingRight()) {
                    iMax = Math.max(iMax, i5);
                    i7 += i6;
                } else {
                    measuredHeight = Math.max(i6, measuredHeight);
                    measuredWidth = i8;
                }
                if (i4 == childCount - 1) {
                    iMax = Math.max(measuredWidth, iMax);
                    i7 += measuredHeight;
                }
                i6 = measuredHeight;
                i5 = measuredWidth;
            }
            i4++;
            size = i3;
        }
        int i9 = size;
        if (mode == 1073741824) {
            paddingRight = i9;
        } else {
            paddingRight = getPaddingRight() + iMax + getPaddingLeft();
        }
        if (mode2 != 1073741824) {
            size2 = i7 + getPaddingTop() + getPaddingBottom();
        }
        setMeasuredDimension(paddingRight, size2);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int paddingLeft;
        super.onLayout(z, i, i2, i3, i4);
        this.mAllViews.clear();
        this.mLineHeight.clear();
        this.mLineWidth.clear();
        this.lineViews.clear();
        int width = getWidth();
        int childCount = getChildCount();
        int iMax = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = getChildAt(i6);
            if (childAt.getVisibility() != 8) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) childAt.getLayoutParams();
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight = childAt.getMeasuredHeight();
                if (measuredWidth + i5 + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin > (width - getPaddingLeft()) - getPaddingRight()) {
                    this.mLineHeight.add(Integer.valueOf(iMax));
                    this.mAllViews.add(this.lineViews);
                    this.mLineWidth.add(Integer.valueOf(i5));
                    iMax = marginLayoutParams.topMargin + measuredHeight + marginLayoutParams.bottomMargin;
                    this.lineViews = new ArrayList();
                    i5 = 0;
                }
                i5 += measuredWidth + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
                iMax = Math.max(iMax, measuredHeight + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin);
                this.lineViews.add(childAt);
            }
        }
        this.mLineHeight.add(Integer.valueOf(iMax));
        this.mLineWidth.add(Integer.valueOf(i5));
        this.mAllViews.add(this.lineViews);
        int paddingTop = getPaddingTop();
        int size = this.mAllViews.size();
        for (int i7 = 0; i7 < size; i7++) {
            this.lineViews = this.mAllViews.get(i7);
            int iIntValue = this.mLineHeight.get(i7).intValue();
            int iIntValue2 = this.mLineWidth.get(i7).intValue();
            int i8 = this.mGravity;
            if (i8 == -1) {
                paddingLeft = getPaddingLeft();
            } else if (i8 != 0) {
                if (i8 == 1) {
                    paddingLeft = (width - (iIntValue2 + getPaddingLeft())) - getPaddingRight();
                    Collections.reverse(this.lineViews);
                }
            } else {
                paddingLeft = ((width - iIntValue2) / 2) + getPaddingLeft();
            }
            for (int i9 = 0; i9 < this.lineViews.size(); i9++) {
                View view = this.lineViews.get(i9);
                if (view.getVisibility() != 8) {
                    int height = ((iIntValue - view.getHeight()) >>> 1) + paddingTop;
                    view.layout(paddingLeft, height, view.getMeasuredWidth() + paddingLeft, view.getMeasuredHeight() + height);
                    paddingLeft += view.getMeasuredWidth();
                }
            }
            paddingTop += iIntValue;
        }
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public LinearLayout.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LinearLayout.LayoutParams(getContext(), attributeSet);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public LinearLayout.LayoutParams generateDefaultLayoutParams() {
        return new LinearLayout.LayoutParams(-2, -2);
    }
}
