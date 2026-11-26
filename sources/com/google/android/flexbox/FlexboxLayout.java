package com.google.android.flexbox;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.ViewCompat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class FlexboxLayout extends ViewGroup implements FlexContainer {
    public static final int SHOW_DIVIDER_BEGINNING = 1;
    public static final int SHOW_DIVIDER_END = 4;
    public static final int SHOW_DIVIDER_MIDDLE = 2;
    public static final int SHOW_DIVIDER_NONE = 0;
    private int mAlignContent;
    private int mAlignItems;
    private Drawable mDividerDrawableHorizontal;
    private Drawable mDividerDrawableVertical;
    private int mDividerHorizontalHeight;
    private int mDividerVerticalWidth;
    private int mFlexDirection;
    private List<FlexLine> mFlexLines;
    private FlexboxHelper.FlexLinesResult mFlexLinesResult;
    private int mFlexWrap;
    private FlexboxHelper mFlexboxHelper;
    private int mJustifyContent;
    private int mMaxLine;
    private SparseIntArray mOrderCache;
    private int[] mReorderedIndices;
    private int mShowDividerHorizontal;
    private int mShowDividerVertical;

    @Retention(RetentionPolicy.SOURCE)
    public @interface DividerMode {
    }

    @Override // com.google.android.flexbox.FlexContainer
    public int getDecorationLengthCrossAxis(View view) {
        return 0;
    }

    @Override // com.google.android.flexbox.FlexContainer
    public void updateViewCache(int i, View view) {
    }

    public FlexboxLayout(Context context) {
        this(context, null);
    }

    public FlexboxLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public FlexboxLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mMaxLine = -1;
        this.mFlexboxHelper = new FlexboxHelper(this);
        this.mFlexLines = new ArrayList();
        this.mFlexLinesResult = new FlexboxHelper.FlexLinesResult();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.FlexboxLayout, i, 0);
        this.mFlexDirection = typedArrayObtainStyledAttributes.getInt(R.styleable.FlexboxLayout_flexDirection, 0);
        this.mFlexWrap = typedArrayObtainStyledAttributes.getInt(R.styleable.FlexboxLayout_flexWrap, 0);
        this.mJustifyContent = typedArrayObtainStyledAttributes.getInt(R.styleable.FlexboxLayout_justifyContent, 0);
        this.mAlignItems = typedArrayObtainStyledAttributes.getInt(R.styleable.FlexboxLayout_alignItems, 0);
        this.mAlignContent = typedArrayObtainStyledAttributes.getInt(R.styleable.FlexboxLayout_alignContent, 0);
        this.mMaxLine = typedArrayObtainStyledAttributes.getInt(R.styleable.FlexboxLayout_maxLine, -1);
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(R.styleable.FlexboxLayout_dividerDrawable);
        if (drawable != null) {
            setDividerDrawableHorizontal(drawable);
            setDividerDrawableVertical(drawable);
        }
        Drawable drawable2 = typedArrayObtainStyledAttributes.getDrawable(R.styleable.FlexboxLayout_dividerDrawableHorizontal);
        if (drawable2 != null) {
            setDividerDrawableHorizontal(drawable2);
        }
        Drawable drawable3 = typedArrayObtainStyledAttributes.getDrawable(R.styleable.FlexboxLayout_dividerDrawableVertical);
        if (drawable3 != null) {
            setDividerDrawableVertical(drawable3);
        }
        int i2 = typedArrayObtainStyledAttributes.getInt(R.styleable.FlexboxLayout_showDivider, 0);
        if (i2 != 0) {
            this.mShowDividerVertical = i2;
            this.mShowDividerHorizontal = i2;
        }
        int i3 = typedArrayObtainStyledAttributes.getInt(R.styleable.FlexboxLayout_showDividerVertical, 0);
        if (i3 != 0) {
            this.mShowDividerVertical = i3;
        }
        int i4 = typedArrayObtainStyledAttributes.getInt(R.styleable.FlexboxLayout_showDividerHorizontal, 0);
        if (i4 != 0) {
            this.mShowDividerHorizontal = i4;
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        if (this.mOrderCache == null) {
            this.mOrderCache = new SparseIntArray(getChildCount());
        }
        if (this.mFlexboxHelper.isOrderChangedFromLastMeasurement(this.mOrderCache)) {
            this.mReorderedIndices = this.mFlexboxHelper.createReorderedIndices(this.mOrderCache);
        }
        int i3 = this.mFlexDirection;
        if (i3 == 0 || i3 == 1) {
            measureHorizontal(i, i2);
        } else {
            if (i3 == 2 || i3 == 3) {
                measureVertical(i, i2);
                return;
            }
            throw new IllegalStateException("Invalid value for the flex direction is set: " + this.mFlexDirection);
        }
    }

    @Override // com.google.android.flexbox.FlexContainer
    public int getFlexItemCount() {
        return getChildCount();
    }

    @Override // com.google.android.flexbox.FlexContainer
    public View getFlexItemAt(int i) {
        return getChildAt(i);
    }

    public View getReorderedChildAt(int i) {
        if (i < 0) {
            return null;
        }
        int[] iArr = this.mReorderedIndices;
        if (i >= iArr.length) {
            return null;
        }
        return getChildAt(iArr[i]);
    }

    @Override // com.google.android.flexbox.FlexContainer
    public View getReorderedFlexItemAt(int i) {
        return getReorderedChildAt(i);
    }

    @Override // android.view.ViewGroup
    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        if (this.mOrderCache == null) {
            this.mOrderCache = new SparseIntArray(getChildCount());
        }
        this.mReorderedIndices = this.mFlexboxHelper.createReorderedIndices(view, i, layoutParams, this.mOrderCache);
        super.addView(view, i, layoutParams);
    }

    private void measureHorizontal(int i, int i2) {
        this.mFlexLines.clear();
        this.mFlexLinesResult.reset();
        this.mFlexboxHelper.calculateHorizontalFlexLines(this.mFlexLinesResult, i, i2);
        this.mFlexLines = this.mFlexLinesResult.mFlexLines;
        this.mFlexboxHelper.determineMainSize(i, i2);
        if (this.mAlignItems == 3) {
            for (FlexLine flexLine : this.mFlexLines) {
                int iMax = Integer.MIN_VALUE;
                for (int i3 = 0; i3 < flexLine.mItemCount; i3++) {
                    View reorderedChildAt = getReorderedChildAt(flexLine.mFirstIndex + i3);
                    if (reorderedChildAt != null && reorderedChildAt.getVisibility() != 8) {
                        LayoutParams layoutParams = (LayoutParams) reorderedChildAt.getLayoutParams();
                        if (this.mFlexWrap != 2) {
                            iMax = Math.max(iMax, reorderedChildAt.getMeasuredHeight() + Math.max(flexLine.mMaxBaseline - reorderedChildAt.getBaseline(), layoutParams.topMargin) + layoutParams.bottomMargin);
                        } else {
                            iMax = Math.max(iMax, reorderedChildAt.getMeasuredHeight() + layoutParams.topMargin + Math.max((flexLine.mMaxBaseline - reorderedChildAt.getMeasuredHeight()) + reorderedChildAt.getBaseline(), layoutParams.bottomMargin));
                        }
                    }
                }
                flexLine.mCrossSize = iMax;
            }
        }
        this.mFlexboxHelper.determineCrossSize(i, i2, getPaddingTop() + getPaddingBottom());
        this.mFlexboxHelper.stretchViews();
        setMeasuredDimensionForFlex(this.mFlexDirection, i, i2, this.mFlexLinesResult.mChildState);
    }

    private void measureVertical(int i, int i2) {
        this.mFlexLines.clear();
        this.mFlexLinesResult.reset();
        this.mFlexboxHelper.calculateVerticalFlexLines(this.mFlexLinesResult, i, i2);
        this.mFlexLines = this.mFlexLinesResult.mFlexLines;
        this.mFlexboxHelper.determineMainSize(i, i2);
        this.mFlexboxHelper.determineCrossSize(i, i2, getPaddingLeft() + getPaddingRight());
        this.mFlexboxHelper.stretchViews();
        setMeasuredDimensionForFlex(this.mFlexDirection, i, i2, this.mFlexLinesResult.mChildState);
    }

    private void setMeasuredDimensionForFlex(int i, int i2, int i3, int i4) {
        int sumOfCrossSize;
        int largestMainSize;
        int iResolveSizeAndState;
        int iResolveSizeAndState2;
        int mode = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i2);
        int mode2 = View.MeasureSpec.getMode(i3);
        int size2 = View.MeasureSpec.getSize(i3);
        if (i == 0 || i == 1) {
            sumOfCrossSize = getSumOfCrossSize() + getPaddingTop() + getPaddingBottom();
            largestMainSize = getLargestMainSize();
        } else if (i == 2 || i == 3) {
            sumOfCrossSize = getLargestMainSize();
            largestMainSize = getSumOfCrossSize() + getPaddingLeft() + getPaddingRight();
        } else {
            throw new IllegalArgumentException("Invalid flex direction: " + i);
        }
        if (mode == Integer.MIN_VALUE) {
            if (size < largestMainSize) {
                i4 = View.combineMeasuredStates(i4, 16777216);
            } else {
                size = largestMainSize;
            }
            iResolveSizeAndState = View.resolveSizeAndState(size, i2, i4);
        } else if (mode == 0) {
            iResolveSizeAndState = View.resolveSizeAndState(largestMainSize, i2, i4);
        } else if (mode == 1073741824) {
            if (size < largestMainSize) {
                i4 = View.combineMeasuredStates(i4, 16777216);
            }
            iResolveSizeAndState = View.resolveSizeAndState(size, i2, i4);
        } else {
            throw new IllegalStateException("Unknown width mode is set: " + mode);
        }
        if (mode2 == Integer.MIN_VALUE) {
            if (size2 < sumOfCrossSize) {
                i4 = View.combineMeasuredStates(i4, 256);
            } else {
                size2 = sumOfCrossSize;
            }
            iResolveSizeAndState2 = View.resolveSizeAndState(size2, i3, i4);
        } else if (mode2 == 0) {
            iResolveSizeAndState2 = View.resolveSizeAndState(sumOfCrossSize, i3, i4);
        } else if (mode2 == 1073741824) {
            if (size2 < sumOfCrossSize) {
                i4 = View.combineMeasuredStates(i4, 256);
            }
            iResolveSizeAndState2 = View.resolveSizeAndState(size2, i3, i4);
        } else {
            throw new IllegalStateException("Unknown height mode is set: " + mode2);
        }
        setMeasuredDimension(iResolveSizeAndState, iResolveSizeAndState2);
    }

    @Override // com.google.android.flexbox.FlexContainer
    public int getLargestMainSize() {
        Iterator<FlexLine> it = this.mFlexLines.iterator();
        int iMax = Integer.MIN_VALUE;
        while (it.hasNext()) {
            iMax = Math.max(iMax, it.next().mMainSize);
        }
        return iMax;
    }

    @Override // com.google.android.flexbox.FlexContainer
    public int getSumOfCrossSize() {
        int i;
        int i2;
        int size = this.mFlexLines.size();
        int i3 = 0;
        for (int i4 = 0; i4 < size; i4++) {
            FlexLine flexLine = this.mFlexLines.get(i4);
            if (hasDividerBeforeFlexLine(i4)) {
                if (isMainAxisDirectionHorizontal()) {
                    i2 = this.mDividerHorizontalHeight;
                } else {
                    i2 = this.mDividerVerticalWidth;
                }
                i3 += i2;
            }
            if (hasEndDividerAfterFlexLine(i4)) {
                if (isMainAxisDirectionHorizontal()) {
                    i = this.mDividerHorizontalHeight;
                } else {
                    i = this.mDividerVerticalWidth;
                }
                i3 += i;
            }
            i3 += flexLine.mCrossSize;
        }
        return i3;
    }

    @Override // com.google.android.flexbox.FlexContainer
    public boolean isMainAxisDirectionHorizontal() {
        int i = this.mFlexDirection;
        return i == 0 || i == 1;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        boolean z2;
        int layoutDirection = ViewCompat.getLayoutDirection(this);
        int i5 = this.mFlexDirection;
        if (i5 == 0) {
            layoutHorizontal(layoutDirection == 1, i, i2, i3, i4);
            return;
        }
        if (i5 == 1) {
            layoutHorizontal(layoutDirection != 1, i, i2, i3, i4);
            return;
        }
        if (i5 == 2) {
            z2 = layoutDirection == 1;
            if (this.mFlexWrap == 2) {
                z2 = !z2;
            }
            layoutVertical(z2, false, i, i2, i3, i4);
            return;
        }
        if (i5 == 3) {
            z2 = layoutDirection == 1;
            if (this.mFlexWrap == 2) {
                z2 = !z2;
            }
            layoutVertical(z2, true, i, i2, i3, i4);
            return;
        }
        throw new IllegalStateException("Invalid flex direction is set: " + this.mFlexDirection);
    }

    /* JADX WARN: Code duplicated, block: B:42:0x00d8  */
    /* JADX WARN: Code duplicated, block: B:44:0x00e3  */
    /* JADX WARN: Code duplicated, block: B:46:0x00ed  */
    /* JADX WARN: Code duplicated, block: B:47:0x00f1  */
    /* JADX WARN: Code duplicated, block: B:49:0x0105  */
    /* JADX WARN: Code duplicated, block: B:50:0x010d  */
    /* JADX WARN: Code duplicated, block: B:56:0x0122  */
    /* JADX WARN: Code duplicated, block: B:59:0x0128 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:60:0x012a  */
    /* JADX WARN: Code duplicated, block: B:61:0x0150  */
    /* JADX WARN: Code duplicated, block: B:63:0x0176  */
    /* JADX WARN: Code duplicated, block: B:65:0x0184  */
    /* JADX WARN: Code duplicated, block: B:66:0x019e  */
    /* JADX WARN: Code duplicated, block: B:69:0x01d1  */
    /* JADX WARN: Code duplicated, block: B:70:0x01dc  */
    /* JADX WARN: Code duplicated, block: B:72:0x01ed  */
    private void layoutHorizontal(boolean z, int i, int i2, int i3, int i4) {
        float measuredWidth;
        int i5;
        float f;
        float f2;
        float fMax;
        int i6;
        int i7;
        View reorderedChildAt;
        boolean z2;
        int i8;
        int i9;
        float f3;
        float f4;
        int i10;
        float f5;
        int i11;
        View view;
        FlexLine flexLine;
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int i12 = i3 - i;
        int paddingBottom = (i4 - i2) - getPaddingBottom();
        int paddingTop = getPaddingTop();
        int size = this.mFlexLines.size();
        for (int i13 = 0; i13 < size; i13++) {
            FlexLine flexLine2 = this.mFlexLines.get(i13);
            if (hasDividerBeforeFlexLine(i13)) {
                int i14 = this.mDividerHorizontalHeight;
                paddingBottom -= i14;
                paddingTop += i14;
            }
            int i15 = paddingBottom;
            int i16 = this.mJustifyContent;
            char c = 4;
            int i17 = 2;
            boolean z3 = true;
            if (i16 != 0) {
                if (i16 == 1) {
                    measuredWidth = (i12 - flexLine2.mMainSize) + paddingRight;
                    i5 = flexLine2.mMainSize - paddingLeft;
                } else if (i16 == 2) {
                    measuredWidth = paddingLeft + ((i12 - flexLine2.mMainSize) / 2.0f);
                    f = (i12 - paddingRight) - ((i12 - flexLine2.mMainSize) / 2.0f);
                    f2 = 0.0f;
                } else if (i16 == 3) {
                    measuredWidth = paddingLeft;
                    int itemCountNotGone = flexLine2.getItemCountNotGone();
                    f2 = (i12 - flexLine2.mMainSize) / (itemCountNotGone != 1 ? itemCountNotGone - 1 : 1.0f);
                    f = i12 - paddingRight;
                } else if (i16 == 4) {
                    int itemCountNotGone2 = flexLine2.getItemCountNotGone();
                    float f6 = itemCountNotGone2 != 0 ? (i12 - flexLine2.mMainSize) / itemCountNotGone2 : 0.0f;
                    float f7 = f6 / 2.0f;
                    measuredWidth = paddingLeft + f7;
                    float f8 = (i12 - paddingRight) - f7;
                    f2 = f6;
                    f = f8;
                } else if (i16 == 5) {
                    int itemCountNotGone3 = flexLine2.getItemCountNotGone();
                    f2 = itemCountNotGone3 != 0 ? (i12 - flexLine2.mMainSize) / (itemCountNotGone3 + 1) : 0.0f;
                    measuredWidth = paddingLeft + f2;
                    f = (i12 - paddingRight) - f2;
                } else {
                    throw new IllegalStateException("Invalid justifyContent is set: " + this.mJustifyContent);
                }
                fMax = Math.max(f2, 0.0f);
                i6 = 0;
                while (i6 < flexLine2.mItemCount) {
                    i7 = flexLine2.mFirstIndex + i6;
                    reorderedChildAt = getReorderedChildAt(i7);
                    char c2 = c;
                    if (reorderedChildAt != null) {
                        z2 = z3;
                        if (reorderedChildAt.getVisibility() == 8) {
                            z2 = z2;
                        } else {
                            LayoutParams layoutParams = (LayoutParams) reorderedChildAt.getLayoutParams();
                            f3 = measuredWidth + layoutParams.leftMargin;
                            f4 = f - layoutParams.rightMargin;
                            if (hasDividerBeforeChildAtAlongMainAxis(i7, i6)) {
                                int i18 = this.mDividerVerticalWidth;
                                float f9 = i18;
                                f3 += f9;
                                f4 -= f9;
                                i10 = i18;
                            } else {
                                i10 = 0;
                            }
                            f5 = f4;
                            if (i6 == flexLine2.mItemCount - 1 || (this.mShowDividerVertical & 4) <= 0) {
                                i11 = 0;
                            } else {
                                i11 = this.mDividerVerticalWidth;
                            }
                            if (this.mFlexWrap == i17) {
                                if (z) {
                                    i9 = i17;
                                    view = reorderedChildAt;
                                    this.mFlexboxHelper.layoutSingleChildHorizontal(view, flexLine2, Math.round(f5) - reorderedChildAt.getMeasuredWidth(), i15 - reorderedChildAt.getMeasuredHeight(), Math.round(f5), i15);
                                } else {
                                    view = reorderedChildAt;
                                    i9 = i17;
                                    this.mFlexboxHelper.layoutSingleChildHorizontal(view, flexLine2, Math.round(f3), i15 - view.getMeasuredHeight(), Math.round(f3) + view.getMeasuredWidth(), i15);
                                }
                                i8 = i15;
                            } else {
                                i6 = i6;
                                view = reorderedChildAt;
                                z2 = z2;
                                i9 = i17;
                                i8 = i15;
                                if (z) {
                                    this.mFlexboxHelper.layoutSingleChildHorizontal(view, flexLine2, Math.round(f5) - view.getMeasuredWidth(), paddingTop, Math.round(f5), paddingTop + view.getMeasuredHeight());
                                } else {
                                    int i19 = paddingTop;
                                    this.mFlexboxHelper.layoutSingleChildHorizontal(view, flexLine2, Math.round(f3), i19, view.getMeasuredWidth() + Math.round(f3), i19 + view.getMeasuredHeight());
                                    paddingTop = i19;
                                }
                            }
                            measuredWidth = f3 + view.getMeasuredWidth() + fMax + layoutParams.rightMargin;
                            float measuredWidth2 = f5 - ((view.getMeasuredWidth() + fMax) + layoutParams.leftMargin);
                            if (z) {
                                flexLine = flexLine2;
                                flexLine.updatePositionFromView(view, i11, 0, i10, 0);
                            } else {
                                flexLine = flexLine2;
                                flexLine.updatePositionFromView(view, i10, 0, i11, 0);
                            }
                            flexLine2 = flexLine;
                            f = measuredWidth2;
                        }
                        i6++;
                        c = c2;
                        i17 = i9;
                        z3 = z2;
                        i15 = i8;
                    } else {
                        z2 = z3;
                    }
                    i9 = i17;
                    i6 = i6;
                    i8 = i15;
                    i6++;
                    c = c2;
                    i17 = i9;
                    z3 = z2;
                    i15 = i8;
                }
                paddingTop += flexLine2.mCrossSize;
                paddingBottom = i15 - flexLine2.mCrossSize;
            } else {
                measuredWidth = paddingLeft;
                i5 = i12 - paddingRight;
            }
            f = i5;
            f2 = 0.0f;
            fMax = Math.max(f2, 0.0f);
            i6 = 0;
            while (i6 < flexLine2.mItemCount) {
                i7 = flexLine2.mFirstIndex + i6;
                reorderedChildAt = getReorderedChildAt(i7);
                char c3 = c;
                if (reorderedChildAt != null) {
                    z2 = z3;
                    if (reorderedChildAt.getVisibility() == 8) {
                        z2 = z2;
                    } else {
                        LayoutParams layoutParams2 = (LayoutParams) reorderedChildAt.getLayoutParams();
                        f3 = measuredWidth + layoutParams2.leftMargin;
                        f4 = f - layoutParams2.rightMargin;
                        if (hasDividerBeforeChildAtAlongMainAxis(i7, i6)) {
                            int i110 = this.mDividerVerticalWidth;
                            float f10 = i110;
                            f3 += f10;
                            f4 -= f10;
                            i10 = i110;
                        } else {
                            i10 = 0;
                        }
                        f5 = f4;
                        if (i6 == flexLine2.mItemCount - 1) {
                            i11 = 0;
                        } else {
                            i11 = 0;
                        }
                        if (this.mFlexWrap == i17) {
                            if (z) {
                                i9 = i17;
                                view = reorderedChildAt;
                                this.mFlexboxHelper.layoutSingleChildHorizontal(view, flexLine2, Math.round(f5) - reorderedChildAt.getMeasuredWidth(), i15 - reorderedChildAt.getMeasuredHeight(), Math.round(f5), i15);
                            } else {
                                view = reorderedChildAt;
                                i9 = i17;
                                this.mFlexboxHelper.layoutSingleChildHorizontal(view, flexLine2, Math.round(f3), i15 - view.getMeasuredHeight(), Math.round(f3) + view.getMeasuredWidth(), i15);
                            }
                            i8 = i15;
                        } else {
                            i6 = i6;
                            view = reorderedChildAt;
                            z2 = z2;
                            i9 = i17;
                            i8 = i15;
                            if (z) {
                                this.mFlexboxHelper.layoutSingleChildHorizontal(view, flexLine2, Math.round(f5) - view.getMeasuredWidth(), paddingTop, Math.round(f5), paddingTop + view.getMeasuredHeight());
                            } else {
                                int i111 = paddingTop;
                                this.mFlexboxHelper.layoutSingleChildHorizontal(view, flexLine2, Math.round(f3), i111, view.getMeasuredWidth() + Math.round(f3), i111 + view.getMeasuredHeight());
                                paddingTop = i111;
                            }
                        }
                        measuredWidth = f3 + view.getMeasuredWidth() + fMax + layoutParams2.rightMargin;
                        float measuredWidth3 = f5 - ((view.getMeasuredWidth() + fMax) + layoutParams2.leftMargin);
                        if (z) {
                            flexLine = flexLine2;
                            flexLine.updatePositionFromView(view, i11, 0, i10, 0);
                        } else {
                            flexLine = flexLine2;
                            flexLine.updatePositionFromView(view, i10, 0, i11, 0);
                        }
                        flexLine2 = flexLine;
                        f = measuredWidth3;
                    }
                    i6++;
                    c = c3;
                    i17 = i9;
                    z3 = z2;
                    i15 = i8;
                } else {
                    z2 = z3;
                }
                i9 = i17;
                i6 = i6;
                i8 = i15;
                i6++;
                c = c3;
                i17 = i9;
                z3 = z2;
                i15 = i8;
            }
            paddingTop += flexLine2.mCrossSize;
            paddingBottom = i15 - flexLine2.mCrossSize;
        }
    }

    /* JADX WARN: Code duplicated, block: B:42:0x00d8  */
    /* JADX WARN: Code duplicated, block: B:44:0x00e2  */
    /* JADX WARN: Code duplicated, block: B:47:0x00ee  */
    /* JADX WARN: Code duplicated, block: B:49:0x0102  */
    /* JADX WARN: Code duplicated, block: B:50:0x0108  */
    /* JADX WARN: Code duplicated, block: B:56:0x011b  */
    /* JADX WARN: Code duplicated, block: B:58:0x011f A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:59:0x0121  */
    /* JADX WARN: Code duplicated, block: B:60:0x0143  */
    /* JADX WARN: Code duplicated, block: B:62:0x0164  */
    /* JADX WARN: Code duplicated, block: B:64:0x016c  */
    /* JADX WARN: Code duplicated, block: B:65:0x0188  */
    /* JADX WARN: Code duplicated, block: B:68:0x01bf  */
    /* JADX WARN: Code duplicated, block: B:69:0x01c9  */
    /* JADX WARN: Code duplicated, block: B:71:0x01d6  */
    private void layoutVertical(boolean z, boolean z2, int i, int i2, int i3, int i4) {
        float measuredHeight;
        int i5;
        float f;
        float f2;
        float fMax;
        int i6;
        int i7;
        int i8;
        View reorderedChildAt;
        char c;
        int i9;
        int i10;
        float f3;
        float f4;
        int i11;
        float f5;
        int i12;
        FlexLine flexLine;
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int paddingRight = getPaddingRight();
        int paddingLeft = getPaddingLeft();
        int i13 = i4 - i2;
        int i14 = (i3 - i) - paddingRight;
        int size = this.mFlexLines.size();
        for (int i15 = 0; i15 < size; i15++) {
            FlexLine flexLine2 = this.mFlexLines.get(i15);
            if (hasDividerBeforeFlexLine(i15)) {
                int i16 = this.mDividerVerticalWidth;
                paddingLeft += i16;
                i14 -= i16;
            }
            int i17 = i14;
            int i18 = this.mJustifyContent;
            char c2 = 4;
            int i19 = 1;
            if (i18 != 0) {
                if (i18 == 1) {
                    measuredHeight = (i13 - flexLine2.mMainSize) + paddingBottom;
                    i5 = flexLine2.mMainSize - paddingTop;
                } else if (i18 == 2) {
                    measuredHeight = paddingTop + ((i13 - flexLine2.mMainSize) / 2.0f);
                    f = (i13 - paddingBottom) - ((i13 - flexLine2.mMainSize) / 2.0f);
                    f2 = 0.0f;
                } else if (i18 == 3) {
                    measuredHeight = paddingTop;
                    int itemCountNotGone = flexLine2.getItemCountNotGone();
                    f2 = (i13 - flexLine2.mMainSize) / (itemCountNotGone != 1 ? itemCountNotGone - 1 : 1.0f);
                    f = i13 - paddingBottom;
                } else if (i18 == 4) {
                    int itemCountNotGone2 = flexLine2.getItemCountNotGone();
                    f2 = itemCountNotGone2 != 0 ? (i13 - flexLine2.mMainSize) / itemCountNotGone2 : 0.0f;
                    float f6 = f2 / 2.0f;
                    measuredHeight = paddingTop + f6;
                    f = (i13 - paddingBottom) - f6;
                } else if (i18 == 5) {
                    int itemCountNotGone3 = flexLine2.getItemCountNotGone();
                    f2 = itemCountNotGone3 != 0 ? (i13 - flexLine2.mMainSize) / (itemCountNotGone3 + 1) : 0.0f;
                    measuredHeight = paddingTop + f2;
                    f = (i13 - paddingBottom) - f2;
                } else {
                    throw new IllegalStateException("Invalid justifyContent is set: " + this.mJustifyContent);
                }
                fMax = Math.max(f2, 0.0f);
                i6 = 0;
                while (i6 < flexLine2.mItemCount) {
                    i7 = flexLine2.mFirstIndex + i6;
                    i8 = i19;
                    reorderedChildAt = getReorderedChildAt(i7);
                    if (reorderedChildAt != null) {
                        c = c2;
                        if (reorderedChildAt.getVisibility() == 8) {
                            LayoutParams layoutParams = (LayoutParams) reorderedChildAt.getLayoutParams();
                            f3 = measuredHeight + layoutParams.topMargin;
                            f4 = f - layoutParams.bottomMargin;
                            if (hasDividerBeforeChildAtAlongMainAxis(i7, i6)) {
                                i11 = this.mDividerHorizontalHeight;
                                float f7 = i11;
                                f3 += f7;
                                f4 -= f7;
                            } else {
                                i11 = 0;
                            }
                            f5 = f4;
                            if (i6 == flexLine2.mItemCount - i8 || (this.mShowDividerHorizontal & 4) <= 0) {
                                i12 = 0;
                            } else {
                                i12 = this.mDividerHorizontalHeight;
                            }
                            if (z) {
                                if (z2) {
                                    i9 = i6;
                                    this.mFlexboxHelper.layoutSingleChildVertical(reorderedChildAt, flexLine2, true, i17 - reorderedChildAt.getMeasuredWidth(), Math.round(f5) - reorderedChildAt.getMeasuredHeight(), i17, Math.round(f5));
                                } else {
                                    i9 = i6;
                                    this.mFlexboxHelper.layoutSingleChildVertical(reorderedChildAt, flexLine2, true, i17 - reorderedChildAt.getMeasuredWidth(), Math.round(f3), i17, Math.round(f3) + reorderedChildAt.getMeasuredHeight());
                                }
                                i10 = i17;
                            } else {
                                i9 = i6;
                                i8 = i8;
                                i10 = i17;
                                if (z2) {
                                    this.mFlexboxHelper.layoutSingleChildVertical(reorderedChildAt, flexLine2, false, paddingLeft, Math.round(f5) - reorderedChildAt.getMeasuredHeight(), paddingLeft + reorderedChildAt.getMeasuredWidth(), Math.round(f5));
                                } else {
                                    int i20 = paddingLeft;
                                    this.mFlexboxHelper.layoutSingleChildVertical(reorderedChildAt, flexLine2, false, i20, Math.round(f3), i20 + reorderedChildAt.getMeasuredWidth(), Math.round(f3) + reorderedChildAt.getMeasuredHeight());
                                    paddingLeft = i20;
                                }
                            }
                            measuredHeight = f3 + reorderedChildAt.getMeasuredHeight() + fMax + layoutParams.bottomMargin;
                            float measuredHeight2 = f5 - ((reorderedChildAt.getMeasuredHeight() + fMax) + layoutParams.topMargin);
                            if (z2) {
                                flexLine = flexLine2;
                                flexLine.updatePositionFromView(reorderedChildAt, 0, i12, 0, i11);
                            } else {
                                flexLine = flexLine2;
                                flexLine.updatePositionFromView(reorderedChildAt, 0, i11, 0, i12);
                            }
                            flexLine2 = flexLine;
                            f = measuredHeight2;
                        }
                        i6 = i9 + 1;
                        c2 = c;
                        i19 = i8;
                        i17 = i10;
                    } else {
                        c = c2;
                    }
                    i9 = i6;
                    i8 = i8;
                    i10 = i17;
                    i6 = i9 + 1;
                    c2 = c;
                    i19 = i8;
                    i17 = i10;
                }
                paddingLeft += flexLine2.mCrossSize;
                i14 = i17 - flexLine2.mCrossSize;
            } else {
                measuredHeight = paddingTop;
                i5 = i13 - paddingBottom;
            }
            f = i5;
            f2 = 0.0f;
            fMax = Math.max(f2, 0.0f);
            i6 = 0;
            while (i6 < flexLine2.mItemCount) {
                i7 = flexLine2.mFirstIndex + i6;
                i8 = i19;
                reorderedChildAt = getReorderedChildAt(i7);
                if (reorderedChildAt != null) {
                    c = c2;
                    if (reorderedChildAt.getVisibility() == 8) {
                        LayoutParams layoutParams2 = (LayoutParams) reorderedChildAt.getLayoutParams();
                        f3 = measuredHeight + layoutParams2.topMargin;
                        f4 = f - layoutParams2.bottomMargin;
                        if (hasDividerBeforeChildAtAlongMainAxis(i7, i6)) {
                            i11 = this.mDividerHorizontalHeight;
                            float f8 = i11;
                            f3 += f8;
                            f4 -= f8;
                        } else {
                            i11 = 0;
                        }
                        f5 = f4;
                        if (i6 == flexLine2.mItemCount - i8) {
                            i12 = 0;
                        } else {
                            i12 = 0;
                        }
                        if (z) {
                            if (z2) {
                                i9 = i6;
                                this.mFlexboxHelper.layoutSingleChildVertical(reorderedChildAt, flexLine2, true, i17 - reorderedChildAt.getMeasuredWidth(), Math.round(f5) - reorderedChildAt.getMeasuredHeight(), i17, Math.round(f5));
                            } else {
                                i9 = i6;
                                this.mFlexboxHelper.layoutSingleChildVertical(reorderedChildAt, flexLine2, true, i17 - reorderedChildAt.getMeasuredWidth(), Math.round(f3), i17, Math.round(f3) + reorderedChildAt.getMeasuredHeight());
                            }
                            i10 = i17;
                        } else {
                            i9 = i6;
                            i8 = i8;
                            i10 = i17;
                            if (z2) {
                                this.mFlexboxHelper.layoutSingleChildVertical(reorderedChildAt, flexLine2, false, paddingLeft, Math.round(f5) - reorderedChildAt.getMeasuredHeight(), paddingLeft + reorderedChildAt.getMeasuredWidth(), Math.round(f5));
                            } else {
                                int i21 = paddingLeft;
                                this.mFlexboxHelper.layoutSingleChildVertical(reorderedChildAt, flexLine2, false, i21, Math.round(f3), i21 + reorderedChildAt.getMeasuredWidth(), Math.round(f3) + reorderedChildAt.getMeasuredHeight());
                                paddingLeft = i21;
                            }
                        }
                        measuredHeight = f3 + reorderedChildAt.getMeasuredHeight() + fMax + layoutParams2.bottomMargin;
                        float measuredHeight3 = f5 - ((reorderedChildAt.getMeasuredHeight() + fMax) + layoutParams2.topMargin);
                        if (z2) {
                            flexLine = flexLine2;
                            flexLine.updatePositionFromView(reorderedChildAt, 0, i12, 0, i11);
                        } else {
                            flexLine = flexLine2;
                            flexLine.updatePositionFromView(reorderedChildAt, 0, i11, 0, i12);
                        }
                        flexLine2 = flexLine;
                        f = measuredHeight3;
                    }
                    i6 = i9 + 1;
                    c2 = c;
                    i19 = i8;
                    i17 = i10;
                } else {
                    c = c2;
                }
                i9 = i6;
                i8 = i8;
                i10 = i17;
                i6 = i9 + 1;
                c2 = c;
                i19 = i8;
                i17 = i10;
            }
            paddingLeft += flexLine2.mCrossSize;
            i14 = i17 - flexLine2.mCrossSize;
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        if (this.mDividerDrawableVertical == null && this.mDividerDrawableHorizontal == null) {
            return;
        }
        if (this.mShowDividerHorizontal == 0 && this.mShowDividerVertical == 0) {
            return;
        }
        int layoutDirection = ViewCompat.getLayoutDirection(this);
        int i = this.mFlexDirection;
        if (i == 0) {
            drawDividersHorizontal(canvas, layoutDirection == 1, this.mFlexWrap == 2);
            return;
        }
        if (i == 1) {
            drawDividersHorizontal(canvas, layoutDirection != 1, this.mFlexWrap == 2);
            return;
        }
        if (i == 2) {
            boolean z = layoutDirection == 1;
            if (this.mFlexWrap == 2) {
                z = !z;
            }
            drawDividersVertical(canvas, z, false);
            return;
        }
        if (i != 3) {
            return;
        }
        boolean z2 = layoutDirection == 1;
        if (this.mFlexWrap == 2) {
            z2 = !z2;
        }
        drawDividersVertical(canvas, z2, true);
    }

    private void drawDividersHorizontal(Canvas canvas, boolean z, boolean z2) {
        int i;
        int i2;
        int right;
        int left;
        int paddingLeft = getPaddingLeft();
        int iMax = Math.max(0, (getWidth() - getPaddingRight()) - paddingLeft);
        int size = this.mFlexLines.size();
        for (int i3 = 0; i3 < size; i3++) {
            FlexLine flexLine = this.mFlexLines.get(i3);
            for (int i4 = 0; i4 < flexLine.mItemCount; i4++) {
                int i5 = flexLine.mFirstIndex + i4;
                View reorderedChildAt = getReorderedChildAt(i5);
                if (reorderedChildAt != null && reorderedChildAt.getVisibility() != 8) {
                    LayoutParams layoutParams = (LayoutParams) reorderedChildAt.getLayoutParams();
                    if (hasDividerBeforeChildAtAlongMainAxis(i5, i4)) {
                        if (z) {
                            left = reorderedChildAt.getRight() + layoutParams.rightMargin;
                        } else {
                            left = (reorderedChildAt.getLeft() - layoutParams.leftMargin) - this.mDividerVerticalWidth;
                        }
                        drawVerticalDivider(canvas, left, flexLine.mTop, flexLine.mCrossSize);
                    }
                    if (i4 == flexLine.mItemCount - 1 && (this.mShowDividerVertical & 4) > 0) {
                        if (z) {
                            right = (reorderedChildAt.getLeft() - layoutParams.leftMargin) - this.mDividerVerticalWidth;
                        } else {
                            right = reorderedChildAt.getRight() + layoutParams.rightMargin;
                        }
                        drawVerticalDivider(canvas, right, flexLine.mTop, flexLine.mCrossSize);
                    }
                }
            }
            if (hasDividerBeforeFlexLine(i3)) {
                if (z2) {
                    i2 = flexLine.mBottom;
                } else {
                    i2 = flexLine.mTop - this.mDividerHorizontalHeight;
                }
                drawHorizontalDivider(canvas, paddingLeft, i2, iMax);
            }
            if (hasEndDividerAfterFlexLine(i3) && (this.mShowDividerHorizontal & 4) > 0) {
                if (z2) {
                    i = flexLine.mTop - this.mDividerHorizontalHeight;
                } else {
                    i = flexLine.mBottom;
                }
                drawHorizontalDivider(canvas, paddingLeft, i, iMax);
            }
        }
    }

    private void drawDividersVertical(Canvas canvas, boolean z, boolean z2) {
        int i;
        int i2;
        int bottom;
        int top;
        int paddingTop = getPaddingTop();
        int iMax = Math.max(0, (getHeight() - getPaddingBottom()) - paddingTop);
        int size = this.mFlexLines.size();
        for (int i3 = 0; i3 < size; i3++) {
            FlexLine flexLine = this.mFlexLines.get(i3);
            for (int i4 = 0; i4 < flexLine.mItemCount; i4++) {
                int i5 = flexLine.mFirstIndex + i4;
                View reorderedChildAt = getReorderedChildAt(i5);
                if (reorderedChildAt != null && reorderedChildAt.getVisibility() != 8) {
                    LayoutParams layoutParams = (LayoutParams) reorderedChildAt.getLayoutParams();
                    if (hasDividerBeforeChildAtAlongMainAxis(i5, i4)) {
                        if (z2) {
                            top = reorderedChildAt.getBottom() + layoutParams.bottomMargin;
                        } else {
                            top = (reorderedChildAt.getTop() - layoutParams.topMargin) - this.mDividerHorizontalHeight;
                        }
                        drawHorizontalDivider(canvas, flexLine.mLeft, top, flexLine.mCrossSize);
                    }
                    if (i4 == flexLine.mItemCount - 1 && (this.mShowDividerHorizontal & 4) > 0) {
                        if (z2) {
                            bottom = (reorderedChildAt.getTop() - layoutParams.topMargin) - this.mDividerHorizontalHeight;
                        } else {
                            bottom = reorderedChildAt.getBottom() + layoutParams.bottomMargin;
                        }
                        drawHorizontalDivider(canvas, flexLine.mLeft, bottom, flexLine.mCrossSize);
                    }
                }
            }
            if (hasDividerBeforeFlexLine(i3)) {
                if (z) {
                    i2 = flexLine.mRight;
                } else {
                    i2 = flexLine.mLeft - this.mDividerVerticalWidth;
                }
                drawVerticalDivider(canvas, i2, paddingTop, iMax);
            }
            if (hasEndDividerAfterFlexLine(i3) && (this.mShowDividerVertical & 4) > 0) {
                if (z) {
                    i = flexLine.mLeft - this.mDividerVerticalWidth;
                } else {
                    i = flexLine.mRight;
                }
                drawVerticalDivider(canvas, i, paddingTop, iMax);
            }
        }
    }

    private void drawVerticalDivider(Canvas canvas, int i, int i2, int i3) {
        Drawable drawable = this.mDividerDrawableVertical;
        if (drawable == null) {
            return;
        }
        drawable.setBounds(i, i2, this.mDividerVerticalWidth + i, i3 + i2);
        this.mDividerDrawableVertical.draw(canvas);
    }

    private void drawHorizontalDivider(Canvas canvas, int i, int i2, int i3) {
        Drawable drawable = this.mDividerDrawableHorizontal;
        if (drawable == null) {
            return;
        }
        drawable.setBounds(i, i2, i3 + i, this.mDividerHorizontalHeight + i2);
        this.mDividerDrawableHorizontal.draw(canvas);
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LayoutParams) {
            return new LayoutParams((LayoutParams) layoutParams);
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    @Override // com.google.android.flexbox.FlexContainer
    public int getFlexDirection() {
        return this.mFlexDirection;
    }

    @Override // com.google.android.flexbox.FlexContainer
    public void setFlexDirection(int i) {
        if (this.mFlexDirection != i) {
            this.mFlexDirection = i;
            requestLayout();
        }
    }

    @Override // com.google.android.flexbox.FlexContainer
    public int getFlexWrap() {
        return this.mFlexWrap;
    }

    @Override // com.google.android.flexbox.FlexContainer
    public void setFlexWrap(int i) {
        if (this.mFlexWrap != i) {
            this.mFlexWrap = i;
            requestLayout();
        }
    }

    @Override // com.google.android.flexbox.FlexContainer
    public int getJustifyContent() {
        return this.mJustifyContent;
    }

    @Override // com.google.android.flexbox.FlexContainer
    public void setJustifyContent(int i) {
        if (this.mJustifyContent != i) {
            this.mJustifyContent = i;
            requestLayout();
        }
    }

    @Override // com.google.android.flexbox.FlexContainer
    public int getAlignItems() {
        return this.mAlignItems;
    }

    @Override // com.google.android.flexbox.FlexContainer
    public void setAlignItems(int i) {
        if (this.mAlignItems != i) {
            this.mAlignItems = i;
            requestLayout();
        }
    }

    @Override // com.google.android.flexbox.FlexContainer
    public int getAlignContent() {
        return this.mAlignContent;
    }

    @Override // com.google.android.flexbox.FlexContainer
    public void setAlignContent(int i) {
        if (this.mAlignContent != i) {
            this.mAlignContent = i;
            requestLayout();
        }
    }

    @Override // com.google.android.flexbox.FlexContainer
    public int getMaxLine() {
        return this.mMaxLine;
    }

    @Override // com.google.android.flexbox.FlexContainer
    public void setMaxLine(int i) {
        if (this.mMaxLine != i) {
            this.mMaxLine = i;
            requestLayout();
        }
    }

    @Override // com.google.android.flexbox.FlexContainer
    public List<FlexLine> getFlexLines() {
        ArrayList arrayList = new ArrayList(this.mFlexLines.size());
        for (FlexLine flexLine : this.mFlexLines) {
            if (flexLine.getItemCountNotGone() != 0) {
                arrayList.add(flexLine);
            }
        }
        return arrayList;
    }

    @Override // com.google.android.flexbox.FlexContainer
    public int getDecorationLengthMainAxis(View view, int i, int i2) {
        int i3;
        int i4;
        if (isMainAxisDirectionHorizontal()) {
            i3 = hasDividerBeforeChildAtAlongMainAxis(i, i2) ? this.mDividerVerticalWidth : 0;
            if ((this.mShowDividerVertical & 4) <= 0) {
                return i3;
            }
            i4 = this.mDividerVerticalWidth;
        } else {
            i3 = hasDividerBeforeChildAtAlongMainAxis(i, i2) ? this.mDividerHorizontalHeight : 0;
            if ((this.mShowDividerHorizontal & 4) <= 0) {
                return i3;
            }
            i4 = this.mDividerHorizontalHeight;
        }
        return i3 + i4;
    }

    @Override // com.google.android.flexbox.FlexContainer
    public void onNewFlexLineAdded(FlexLine flexLine) {
        if (isMainAxisDirectionHorizontal()) {
            if ((this.mShowDividerVertical & 4) > 0) {
                flexLine.mMainSize += this.mDividerVerticalWidth;
                flexLine.mDividerLengthInMainSize += this.mDividerVerticalWidth;
                return;
            }
            return;
        }
        if ((this.mShowDividerHorizontal & 4) > 0) {
            flexLine.mMainSize += this.mDividerHorizontalHeight;
            flexLine.mDividerLengthInMainSize += this.mDividerHorizontalHeight;
        }
    }

    @Override // com.google.android.flexbox.FlexContainer
    public int getChildWidthMeasureSpec(int i, int i2, int i3) {
        return getChildMeasureSpec(i, i2, i3);
    }

    @Override // com.google.android.flexbox.FlexContainer
    public int getChildHeightMeasureSpec(int i, int i2, int i3) {
        return getChildMeasureSpec(i, i2, i3);
    }

    @Override // com.google.android.flexbox.FlexContainer
    public void onNewFlexItemAdded(View view, int i, int i2, FlexLine flexLine) {
        if (hasDividerBeforeChildAtAlongMainAxis(i, i2)) {
            if (isMainAxisDirectionHorizontal()) {
                flexLine.mMainSize += this.mDividerVerticalWidth;
                flexLine.mDividerLengthInMainSize += this.mDividerVerticalWidth;
            } else {
                flexLine.mMainSize += this.mDividerHorizontalHeight;
                flexLine.mDividerLengthInMainSize += this.mDividerHorizontalHeight;
            }
        }
    }

    @Override // com.google.android.flexbox.FlexContainer
    public void setFlexLines(List<FlexLine> list) {
        this.mFlexLines = list;
    }

    @Override // com.google.android.flexbox.FlexContainer
    public List<FlexLine> getFlexLinesInternal() {
        return this.mFlexLines;
    }

    public Drawable getDividerDrawableHorizontal() {
        return this.mDividerDrawableHorizontal;
    }

    public Drawable getDividerDrawableVertical() {
        return this.mDividerDrawableVertical;
    }

    public void setDividerDrawable(Drawable drawable) {
        setDividerDrawableHorizontal(drawable);
        setDividerDrawableVertical(drawable);
    }

    public void setDividerDrawableHorizontal(Drawable drawable) {
        if (drawable == this.mDividerDrawableHorizontal) {
            return;
        }
        this.mDividerDrawableHorizontal = drawable;
        if (drawable != null) {
            this.mDividerHorizontalHeight = drawable.getIntrinsicHeight();
        } else {
            this.mDividerHorizontalHeight = 0;
        }
        setWillNotDrawFlag();
        requestLayout();
    }

    public void setDividerDrawableVertical(Drawable drawable) {
        if (drawable == this.mDividerDrawableVertical) {
            return;
        }
        this.mDividerDrawableVertical = drawable;
        if (drawable != null) {
            this.mDividerVerticalWidth = drawable.getIntrinsicWidth();
        } else {
            this.mDividerVerticalWidth = 0;
        }
        setWillNotDrawFlag();
        requestLayout();
    }

    public int getShowDividerVertical() {
        return this.mShowDividerVertical;
    }

    public int getShowDividerHorizontal() {
        return this.mShowDividerHorizontal;
    }

    public void setShowDivider(int i) {
        setShowDividerVertical(i);
        setShowDividerHorizontal(i);
    }

    public void setShowDividerVertical(int i) {
        if (i != this.mShowDividerVertical) {
            this.mShowDividerVertical = i;
            requestLayout();
        }
    }

    public void setShowDividerHorizontal(int i) {
        if (i != this.mShowDividerHorizontal) {
            this.mShowDividerHorizontal = i;
            requestLayout();
        }
    }

    private void setWillNotDrawFlag() {
        if (this.mDividerDrawableHorizontal == null && this.mDividerDrawableVertical == null) {
            setWillNotDraw(true);
        } else {
            setWillNotDraw(false);
        }
    }

    private boolean hasDividerBeforeChildAtAlongMainAxis(int i, int i2) {
        if (allViewsAreGoneBefore(i, i2)) {
            if (isMainAxisDirectionHorizontal()) {
                return (this.mShowDividerVertical & 1) != 0;
            }
            return (this.mShowDividerHorizontal & 1) != 0;
        }
        if (isMainAxisDirectionHorizontal()) {
            return (this.mShowDividerVertical & 2) != 0;
        }
        return (this.mShowDividerHorizontal & 2) != 0;
    }

    private boolean allViewsAreGoneBefore(int i, int i2) {
        for (int i3 = 1; i3 <= i2; i3++) {
            View reorderedChildAt = getReorderedChildAt(i - i3);
            if (reorderedChildAt != null && reorderedChildAt.getVisibility() != 8) {
                return false;
            }
        }
        return true;
    }

    private boolean hasDividerBeforeFlexLine(int i) {
        if (i >= 0 && i < this.mFlexLines.size()) {
            if (allFlexLinesAreDummyBefore(i)) {
                if (isMainAxisDirectionHorizontal()) {
                    return (this.mShowDividerHorizontal & 1) != 0;
                }
                return (this.mShowDividerVertical & 1) != 0;
            }
            if (isMainAxisDirectionHorizontal()) {
                return (this.mShowDividerHorizontal & 2) != 0;
            }
            if ((this.mShowDividerVertical & 2) != 0) {
                return true;
            }
        }
        return false;
    }

    private boolean allFlexLinesAreDummyBefore(int i) {
        for (int i2 = 0; i2 < i; i2++) {
            if (this.mFlexLines.get(i2).getItemCountNotGone() > 0) {
                return false;
            }
        }
        return true;
    }

    private boolean hasEndDividerAfterFlexLine(int i) {
        if (i >= 0 && i < this.mFlexLines.size()) {
            for (int i2 = i + 1; i2 < this.mFlexLines.size(); i2++) {
                if (this.mFlexLines.get(i2).getItemCountNotGone() > 0) {
                    return false;
                }
            }
            if (isMainAxisDirectionHorizontal()) {
                return (this.mShowDividerHorizontal & 4) != 0;
            }
            if ((this.mShowDividerVertical & 4) != 0) {
                return true;
            }
        }
        return false;
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams implements FlexItem {
        public static final Parcelable.Creator<LayoutParams> CREATOR = new Parcelable.Creator<LayoutParams>() { // from class: com.google.android.flexbox.FlexboxLayout.LayoutParams.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public LayoutParams createFromParcel(Parcel parcel) {
                return new LayoutParams(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public LayoutParams[] newArray(int i) {
                return new LayoutParams[i];
            }
        };
        private int mAlignSelf;
        private float mFlexBasisPercent;
        private float mFlexGrow;
        private float mFlexShrink;
        private int mMaxHeight;
        private int mMaxWidth;
        private int mMinHeight;
        private int mMinWidth;
        private int mOrder;
        private boolean mWrapBefore;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.mOrder = 1;
            this.mFlexGrow = 0.0f;
            this.mFlexShrink = 1.0f;
            this.mAlignSelf = -1;
            this.mFlexBasisPercent = -1.0f;
            this.mMaxWidth = 16777215;
            this.mMaxHeight = 16777215;
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.FlexboxLayout_Layout);
            this.mOrder = typedArrayObtainStyledAttributes.getInt(R.styleable.FlexboxLayout_Layout_layout_order, 1);
            this.mFlexGrow = typedArrayObtainStyledAttributes.getFloat(R.styleable.FlexboxLayout_Layout_layout_flexGrow, 0.0f);
            this.mFlexShrink = typedArrayObtainStyledAttributes.getFloat(R.styleable.FlexboxLayout_Layout_layout_flexShrink, 1.0f);
            this.mAlignSelf = typedArrayObtainStyledAttributes.getInt(R.styleable.FlexboxLayout_Layout_layout_alignSelf, -1);
            this.mFlexBasisPercent = typedArrayObtainStyledAttributes.getFraction(R.styleable.FlexboxLayout_Layout_layout_flexBasisPercent, 1, 1, -1.0f);
            this.mMinWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.FlexboxLayout_Layout_layout_minWidth, 0);
            this.mMinHeight = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.FlexboxLayout_Layout_layout_minHeight, 0);
            this.mMaxWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.FlexboxLayout_Layout_layout_maxWidth, 16777215);
            this.mMaxHeight = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.FlexboxLayout_Layout_layout_maxHeight, 16777215);
            this.mWrapBefore = typedArrayObtainStyledAttributes.getBoolean(R.styleable.FlexboxLayout_Layout_layout_wrapBefore, false);
            typedArrayObtainStyledAttributes.recycle();
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.MarginLayoutParams) layoutParams);
            this.mOrder = 1;
            this.mFlexGrow = 0.0f;
            this.mFlexShrink = 1.0f;
            this.mAlignSelf = -1;
            this.mFlexBasisPercent = -1.0f;
            this.mMaxWidth = 16777215;
            this.mMaxHeight = 16777215;
            this.mOrder = layoutParams.mOrder;
            this.mFlexGrow = layoutParams.mFlexGrow;
            this.mFlexShrink = layoutParams.mFlexShrink;
            this.mAlignSelf = layoutParams.mAlignSelf;
            this.mFlexBasisPercent = layoutParams.mFlexBasisPercent;
            this.mMinWidth = layoutParams.mMinWidth;
            this.mMinHeight = layoutParams.mMinHeight;
            this.mMaxWidth = layoutParams.mMaxWidth;
            this.mMaxHeight = layoutParams.mMaxHeight;
            this.mWrapBefore = layoutParams.mWrapBefore;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.mOrder = 1;
            this.mFlexGrow = 0.0f;
            this.mFlexShrink = 1.0f;
            this.mAlignSelf = -1;
            this.mFlexBasisPercent = -1.0f;
            this.mMaxWidth = 16777215;
            this.mMaxHeight = 16777215;
        }

        public LayoutParams(int i, int i2) {
            super(new ViewGroup.LayoutParams(i, i2));
            this.mOrder = 1;
            this.mFlexGrow = 0.0f;
            this.mFlexShrink = 1.0f;
            this.mAlignSelf = -1;
            this.mFlexBasisPercent = -1.0f;
            this.mMaxWidth = 16777215;
            this.mMaxHeight = 16777215;
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.mOrder = 1;
            this.mFlexGrow = 0.0f;
            this.mFlexShrink = 1.0f;
            this.mAlignSelf = -1;
            this.mFlexBasisPercent = -1.0f;
            this.mMaxWidth = 16777215;
            this.mMaxHeight = 16777215;
        }

        @Override // com.google.android.flexbox.FlexItem
        public int getWidth() {
            return this.width;
        }

        @Override // com.google.android.flexbox.FlexItem
        public void setWidth(int i) {
            this.width = i;
        }

        @Override // com.google.android.flexbox.FlexItem
        public int getHeight() {
            return this.height;
        }

        @Override // com.google.android.flexbox.FlexItem
        public void setHeight(int i) {
            this.height = i;
        }

        @Override // com.google.android.flexbox.FlexItem
        public int getOrder() {
            return this.mOrder;
        }

        @Override // com.google.android.flexbox.FlexItem
        public void setOrder(int i) {
            this.mOrder = i;
        }

        @Override // com.google.android.flexbox.FlexItem
        public float getFlexGrow() {
            return this.mFlexGrow;
        }

        @Override // com.google.android.flexbox.FlexItem
        public void setFlexGrow(float f) {
            this.mFlexGrow = f;
        }

        @Override // com.google.android.flexbox.FlexItem
        public float getFlexShrink() {
            return this.mFlexShrink;
        }

        @Override // com.google.android.flexbox.FlexItem
        public void setFlexShrink(float f) {
            this.mFlexShrink = f;
        }

        @Override // com.google.android.flexbox.FlexItem
        public int getAlignSelf() {
            return this.mAlignSelf;
        }

        @Override // com.google.android.flexbox.FlexItem
        public void setAlignSelf(int i) {
            this.mAlignSelf = i;
        }

        @Override // com.google.android.flexbox.FlexItem
        public int getMinWidth() {
            return this.mMinWidth;
        }

        @Override // com.google.android.flexbox.FlexItem
        public void setMinWidth(int i) {
            this.mMinWidth = i;
        }

        @Override // com.google.android.flexbox.FlexItem
        public int getMinHeight() {
            return this.mMinHeight;
        }

        @Override // com.google.android.flexbox.FlexItem
        public void setMinHeight(int i) {
            this.mMinHeight = i;
        }

        @Override // com.google.android.flexbox.FlexItem
        public int getMaxWidth() {
            return this.mMaxWidth;
        }

        @Override // com.google.android.flexbox.FlexItem
        public void setMaxWidth(int i) {
            this.mMaxWidth = i;
        }

        @Override // com.google.android.flexbox.FlexItem
        public int getMaxHeight() {
            return this.mMaxHeight;
        }

        @Override // com.google.android.flexbox.FlexItem
        public void setMaxHeight(int i) {
            this.mMaxHeight = i;
        }

        @Override // com.google.android.flexbox.FlexItem
        public boolean isWrapBefore() {
            return this.mWrapBefore;
        }

        @Override // com.google.android.flexbox.FlexItem
        public void setWrapBefore(boolean z) {
            this.mWrapBefore = z;
        }

        @Override // com.google.android.flexbox.FlexItem
        public float getFlexBasisPercent() {
            return this.mFlexBasisPercent;
        }

        @Override // com.google.android.flexbox.FlexItem
        public void setFlexBasisPercent(float f) {
            this.mFlexBasisPercent = f;
        }

        @Override // com.google.android.flexbox.FlexItem
        public int getMarginLeft() {
            return this.leftMargin;
        }

        @Override // com.google.android.flexbox.FlexItem
        public int getMarginTop() {
            return this.topMargin;
        }

        @Override // com.google.android.flexbox.FlexItem
        public int getMarginRight() {
            return this.rightMargin;
        }

        @Override // com.google.android.flexbox.FlexItem
        public int getMarginBottom() {
            return this.bottomMargin;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mOrder);
            parcel.writeFloat(this.mFlexGrow);
            parcel.writeFloat(this.mFlexShrink);
            parcel.writeInt(this.mAlignSelf);
            parcel.writeFloat(this.mFlexBasisPercent);
            parcel.writeInt(this.mMinWidth);
            parcel.writeInt(this.mMinHeight);
            parcel.writeInt(this.mMaxWidth);
            parcel.writeInt(this.mMaxHeight);
            parcel.writeByte(this.mWrapBefore ? (byte) 1 : (byte) 0);
            parcel.writeInt(this.bottomMargin);
            parcel.writeInt(this.leftMargin);
            parcel.writeInt(this.rightMargin);
            parcel.writeInt(this.topMargin);
            parcel.writeInt(this.height);
            parcel.writeInt(this.width);
        }

        protected LayoutParams(Parcel parcel) {
            super(0, 0);
            this.mOrder = 1;
            this.mFlexGrow = 0.0f;
            this.mFlexShrink = 1.0f;
            this.mAlignSelf = -1;
            this.mFlexBasisPercent = -1.0f;
            this.mMaxWidth = 16777215;
            this.mMaxHeight = 16777215;
            this.mOrder = parcel.readInt();
            this.mFlexGrow = parcel.readFloat();
            this.mFlexShrink = parcel.readFloat();
            this.mAlignSelf = parcel.readInt();
            this.mFlexBasisPercent = parcel.readFloat();
            this.mMinWidth = parcel.readInt();
            this.mMinHeight = parcel.readInt();
            this.mMaxWidth = parcel.readInt();
            this.mMaxHeight = parcel.readInt();
            this.mWrapBefore = parcel.readByte() != 0;
            this.bottomMargin = parcel.readInt();
            this.leftMargin = parcel.readInt();
            this.rightMargin = parcel.readInt();
            this.topMargin = parcel.readInt();
            this.height = parcel.readInt();
            this.width = parcel.readInt();
        }
    }
}
