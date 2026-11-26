package com.miui.floatwindow.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import com.miui.notes.base.utils.Logger;
import miuix.core.widget.NestedScrollView;
import miuix.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes2.dex */
public class FwFullScrollView extends NestedScrollView {
    private static final String TAG = "FwFullScrollView";
    public static boolean sForbidScrollY = false;
    RecyclerView mListContentRv;
    View mParentScrollBannerView;
    int mParentScrollHeight;
    private float mStartMotionY;

    public FwFullScrollView(Context context) {
        super(context);
        this.mParentScrollBannerView = null;
        this.mListContentRv = null;
    }

    public FwFullScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mParentScrollBannerView = null;
        this.mListContentRv = null;
    }

    public FwFullScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mParentScrollBannerView = null;
        this.mListContentRv = null;
    }

    public void setBannerView(View view) {
        this.mParentScrollBannerView = view;
        this.mParentScrollHeight = view.getHeight();
    }

    public void setListContentRv(RecyclerView recyclerView) {
        this.mListContentRv = recyclerView;
    }

    @Override // miuix.core.widget.NestedScrollView, android.view.View
    protected void onOverScrolled(int i, int i2, boolean z, boolean z2) {
        View view = this.mParentScrollBannerView;
        if (view != null) {
            int height = view.getHeight();
            this.mParentScrollHeight = height;
            i2 = Math.min(height, i2);
        }
        super.onOverScrolled(i, i2, z, z2);
    }

    @Override // miuix.core.widget.NestedScrollView, android.view.View
    public void scrollTo(int i, int i2) {
        View view = this.mParentScrollBannerView;
        if (view != null) {
            int height = view.getHeight();
            this.mParentScrollHeight = height;
            i2 = Math.min(height, i2);
        }
        Logger.INSTANCE.d(TAG, "scrollTo x = " + i + " y = " + i2);
        super.scrollTo(i, i2);
    }

    @Override // miuix.core.widget.NestedScrollView, android.view.View
    protected void onScrollChanged(int i, int i2, int i3, int i4) {
        View view = this.mParentScrollBannerView;
        if (view != null) {
            int height = view.getHeight();
            this.mParentScrollHeight = height;
            i2 = Math.min(height, i2);
        }
        super.onScrollChanged(i, i2, i3, i4);
    }

    @Override // miuix.core.widget.NestedScrollView, androidx.core.view.NestedScrollingParent2
    public void onNestedPreScroll(View view, int i, int i2, int[] iArr, int i3) {
        View view2 = this.mParentScrollBannerView;
        if (view2 == null) {
            return;
        }
        this.mParentScrollHeight = view2.getHeight();
        int scrollY = getScrollY();
        if (i2 >= 0) {
            int i4 = scrollY + i2;
            int i5 = this.mParentScrollHeight;
            if (i4 < i5) {
                iArr[1] = i2;
            } else {
                iArr[1] = i2 - (i4 - i5);
            }
            smoothScrollBy(0, iArr[1]);
        }
    }

    @Override // miuix.core.widget.NestedScrollView, androidx.core.view.NestedScrollingChild2
    public boolean dispatchNestedPreScroll(int i, int i2, int[] iArr, int[] iArr2, int i3) {
        View view = this.mParentScrollBannerView;
        if (view != null) {
            this.mParentScrollHeight = view.getHeight();
            int scrollY = getScrollY();
            if (i2 >= 0) {
                int i4 = scrollY + i2;
                int i5 = this.mParentScrollHeight;
                if (i4 < i5) {
                    iArr[1] = i2;
                } else {
                    iArr[1] = i2 - (i4 - i5);
                }
                smoothScrollBy(0, iArr[1]);
                if (iArr[1] > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // miuix.core.widget.NestedScrollView, android.view.ViewGroup, android.view.ViewParent
    public void requestChildFocus(View view, View view2) {
        if (view2 instanceof RecyclerView) {
            return;
        }
        super.requestChildFocus(view, view2);
    }

    @Override // miuix.core.widget.NestedScrollView
    protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        if (getChildCount() != 0 && !sForbidScrollY) {
            getHeight();
            int scrollY = getScrollY();
            int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
            if (rect.top > 0) {
                scrollY += verticalFadingEdgeLength;
            }
            View childAt = getChildAt(0);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
            int i = rect.bottom;
            childAt.getHeight();
            int i2 = layoutParams.topMargin;
            int i3 = layoutParams.bottomMargin;
            if (rect.top > scrollY) {
                int i4 = rect.top - scrollY;
                View view = this.mParentScrollBannerView;
                if (view != null) {
                    this.mParentScrollHeight = view.getHeight();
                    int scrollY2 = getScrollY();
                    int i5 = this.mParentScrollHeight;
                    if (scrollY2 >= i5) {
                        return 0;
                    }
                    if (scrollY2 + i4 > i5) {
                        return i5 - scrollY2;
                    }
                }
                return i4;
            }
        }
        return 0;
    }

    @Override // miuix.core.widget.NestedScrollView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            this.mStartMotionY = motionEvent.getY();
        } else if (motionEvent.getAction() == 2 && Math.abs(this.mStartMotionY - motionEvent.getY()) < 35.0f) {
            return false;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }
}
