package com.google.android.flexbox;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class FlexboxItemDecoration extends RecyclerView.ItemDecoration {
    public static final int BOTH = 3;
    public static final int HORIZONTAL = 1;
    private static final int[] LIST_DIVIDER_ATTRS = {android.R.attr.listDivider};
    public static final int VERTICAL = 2;
    private Drawable mDrawable;
    private int mOrientation;

    public FlexboxItemDecoration(Context context) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(LIST_DIVIDER_ATTRS);
        this.mDrawable = typedArrayObtainStyledAttributes.getDrawable(0);
        typedArrayObtainStyledAttributes.recycle();
        setOrientation(3);
    }

    public void setDrawable(Drawable drawable) {
        if (drawable == null) {
            throw new IllegalArgumentException("Drawable cannot be null.");
        }
        this.mDrawable = drawable;
    }

    public void setOrientation(int i) {
        this.mOrientation = i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void onDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        drawHorizontalDecorations(canvas, recyclerView);
        drawVerticalDecorations(canvas, recyclerView);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
        if (childAdapterPosition == 0) {
            return;
        }
        if (!needsHorizontalDecoration() && !needsVerticalDecoration()) {
            rect.set(0, 0, 0, 0);
            return;
        }
        FlexboxLayoutManager flexboxLayoutManager = (FlexboxLayoutManager) recyclerView.getLayoutManager();
        List<FlexLine> flexLines = flexboxLayoutManager.getFlexLines();
        setOffsetAlongMainAxis(rect, childAdapterPosition, flexboxLayoutManager, flexLines, flexboxLayoutManager.getFlexDirection());
        setOffsetAlongCrossAxis(rect, childAdapterPosition, flexboxLayoutManager, flexLines);
    }

    private void setOffsetAlongCrossAxis(Rect rect, int i, FlexboxLayoutManager flexboxLayoutManager, List<FlexLine> list) {
        if (list.size() == 0 || flexboxLayoutManager.getPositionToFlexLineIndex(i) == 0) {
            return;
        }
        if (flexboxLayoutManager.isMainAxisDirectionHorizontal()) {
            if (!needsHorizontalDecoration()) {
                rect.top = 0;
                rect.bottom = 0;
                return;
            } else {
                rect.top = this.mDrawable.getIntrinsicHeight();
                rect.bottom = 0;
                return;
            }
        }
        if (needsVerticalDecoration()) {
            if (flexboxLayoutManager.isLayoutRtl()) {
                rect.right = this.mDrawable.getIntrinsicWidth();
                rect.left = 0;
            } else {
                rect.left = this.mDrawable.getIntrinsicWidth();
                rect.right = 0;
            }
        }
    }

    private void setOffsetAlongMainAxis(Rect rect, int i, FlexboxLayoutManager flexboxLayoutManager, List<FlexLine> list, int i2) {
        if (isFirstItemInLine(i, list, flexboxLayoutManager)) {
            return;
        }
        if (flexboxLayoutManager.isMainAxisDirectionHorizontal()) {
            if (!needsVerticalDecoration()) {
                rect.left = 0;
                rect.right = 0;
                return;
            } else if (flexboxLayoutManager.isLayoutRtl()) {
                rect.right = this.mDrawable.getIntrinsicWidth();
                rect.left = 0;
                return;
            } else {
                rect.left = this.mDrawable.getIntrinsicWidth();
                rect.right = 0;
                return;
            }
        }
        if (!needsHorizontalDecoration()) {
            rect.top = 0;
            rect.bottom = 0;
        } else if (i2 == 3) {
            rect.bottom = this.mDrawable.getIntrinsicHeight();
            rect.top = 0;
        } else {
            rect.top = this.mDrawable.getIntrinsicHeight();
            rect.bottom = 0;
        }
    }

    private void drawVerticalDecorations(Canvas canvas, RecyclerView recyclerView) {
        int left;
        int intrinsicWidth;
        int iMax;
        int bottom;
        int i;
        int i2;
        if (needsVerticalDecoration()) {
            FlexboxLayoutManager flexboxLayoutManager = (FlexboxLayoutManager) recyclerView.getLayoutManager();
            int top = recyclerView.getTop() - recyclerView.getPaddingTop();
            int bottom2 = recyclerView.getBottom() + recyclerView.getPaddingBottom();
            int childCount = recyclerView.getChildCount();
            int flexDirection = flexboxLayoutManager.getFlexDirection();
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = recyclerView.getChildAt(i3);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childAt.getLayoutParams();
                if (flexboxLayoutManager.isLayoutRtl()) {
                    intrinsicWidth = childAt.getRight() + layoutParams.rightMargin;
                    left = this.mDrawable.getIntrinsicWidth() + intrinsicWidth;
                } else {
                    left = childAt.getLeft() - layoutParams.leftMargin;
                    intrinsicWidth = left - this.mDrawable.getIntrinsicWidth();
                }
                if (flexboxLayoutManager.isMainAxisDirectionHorizontal()) {
                    iMax = childAt.getTop() - layoutParams.topMargin;
                    bottom = childAt.getBottom();
                    i = layoutParams.bottomMargin;
                } else {
                    if (flexDirection == 3) {
                        int iMin = Math.min(childAt.getBottom() + layoutParams.bottomMargin + this.mDrawable.getIntrinsicHeight(), bottom2);
                        iMax = childAt.getTop() - layoutParams.topMargin;
                        i2 = iMin;
                    } else {
                        iMax = Math.max((childAt.getTop() - layoutParams.topMargin) - this.mDrawable.getIntrinsicHeight(), top);
                        bottom = childAt.getBottom();
                        i = layoutParams.bottomMargin;
                    }
                    this.mDrawable.setBounds(intrinsicWidth, iMax, left, i2);
                    this.mDrawable.draw(canvas);
                }
                i2 = bottom + i;
                this.mDrawable.setBounds(intrinsicWidth, iMax, left, i2);
                this.mDrawable.draw(canvas);
            }
        }
    }

    private void drawHorizontalDecorations(Canvas canvas, RecyclerView recyclerView) {
        int top;
        int intrinsicHeight;
        int left;
        int right;
        int i;
        int iMin;
        int left2;
        if (needsHorizontalDecoration()) {
            FlexboxLayoutManager flexboxLayoutManager = (FlexboxLayoutManager) recyclerView.getLayoutManager();
            int flexDirection = flexboxLayoutManager.getFlexDirection();
            int left3 = recyclerView.getLeft() - recyclerView.getPaddingLeft();
            int right2 = recyclerView.getRight() + recyclerView.getPaddingRight();
            int childCount = recyclerView.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = recyclerView.getChildAt(i2);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childAt.getLayoutParams();
                if (flexDirection == 3) {
                    intrinsicHeight = childAt.getBottom() + layoutParams.bottomMargin;
                    top = this.mDrawable.getIntrinsicHeight() + intrinsicHeight;
                } else {
                    top = childAt.getTop() - layoutParams.topMargin;
                    intrinsicHeight = top - this.mDrawable.getIntrinsicHeight();
                }
                if (flexboxLayoutManager.isMainAxisDirectionHorizontal()) {
                    if (flexboxLayoutManager.isLayoutRtl()) {
                        iMin = Math.min(childAt.getRight() + layoutParams.rightMargin + this.mDrawable.getIntrinsicWidth(), right2);
                        left2 = childAt.getLeft() - layoutParams.leftMargin;
                    } else {
                        left = Math.max((childAt.getLeft() - layoutParams.leftMargin) - this.mDrawable.getIntrinsicWidth(), left3);
                        right = childAt.getRight();
                        i = layoutParams.rightMargin;
                    }
                    this.mDrawable.setBounds(left2, intrinsicHeight, iMin, top);
                    this.mDrawable.draw(canvas);
                } else {
                    left = childAt.getLeft() - layoutParams.leftMargin;
                    right = childAt.getRight();
                    i = layoutParams.rightMargin;
                }
                int i3 = left;
                iMin = right + i;
                left2 = i3;
                this.mDrawable.setBounds(left2, intrinsicHeight, iMin, top);
                this.mDrawable.draw(canvas);
            }
        }
    }

    private boolean needsHorizontalDecoration() {
        return (this.mOrientation & 1) > 0;
    }

    private boolean needsVerticalDecoration() {
        return (this.mOrientation & 2) > 0;
    }

    private boolean isFirstItemInLine(int i, List<FlexLine> list, FlexboxLayoutManager flexboxLayoutManager) {
        int positionToFlexLineIndex = flexboxLayoutManager.getPositionToFlexLineIndex(i);
        if ((positionToFlexLineIndex == -1 || positionToFlexLineIndex >= flexboxLayoutManager.getFlexLinesInternal().size() || flexboxLayoutManager.getFlexLinesInternal().get(positionToFlexLineIndex).mFirstIndex != i) && i != 0) {
            return list.size() != 0 && list.get(list.size() - 1).mLastIndex == i - 1;
        }
        return true;
    }
}
