package com.xiaomi.passport.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Scroller;

/* JADX INFO: loaded from: classes4.dex */
public class AnimateScrollLinerLayout extends LinearLayout {
    private Scroller mScroller;

    public AnimateScrollLinerLayout(Context context) {
        super(context);
        init(context);
    }

    public AnimateScrollLinerLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public AnimateScrollLinerLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    @Override // android.view.View
    public void computeScroll() {
        if (this.mScroller.computeScrollOffset()) {
            scrollTo(this.mScroller.getCurrX(), this.mScroller.getCurrY());
            postInvalidate();
        }
        super.computeScroll();
    }

    private void init(Context context) {
        this.mScroller = new Scroller(context);
    }

    public void smoothScrollTo(int i, int i2) {
        smoothScrollBy(i - this.mScroller.getFinalX(), i2 - this.mScroller.getFinalY());
    }

    public void smoothScrollBy(int i, int i2) {
        Scroller scroller = this.mScroller;
        scroller.startScroll(scroller.getFinalX(), this.mScroller.getFinalY(), i, i2);
        invalidate();
    }
}
