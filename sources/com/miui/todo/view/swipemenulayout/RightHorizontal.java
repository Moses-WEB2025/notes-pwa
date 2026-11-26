package com.miui.todo.view.swipemenulayout;

import android.view.View;
import android.widget.OverScroller;

/* JADX INFO: loaded from: classes4.dex */
class RightHorizontal extends Horizontal {
    public RightHorizontal(View view, int i) {
        super(-1, view, i);
    }

    @Override // com.miui.todo.view.swipemenulayout.Horizontal
    public boolean isMenuOpen(int i) {
        int direction = (-getMenuView().getWidth()) * getDirection();
        return ((double) i) >= ((double) direction) * 0.95d && direction != 0;
    }

    @Override // com.miui.todo.view.swipemenulayout.Horizontal
    public boolean isMenuOpenNotEqual(int i) {
        return i > (-getMenuView().getWidth()) * getDirection();
    }

    @Override // com.miui.todo.view.swipemenulayout.Horizontal
    public void autoOpenMenu(OverScroller overScroller, int i, int i2) {
        overScroller.startScroll(i, 0, ((-i) + getMenuView().getWidth()) - this.viewRadius, 0, i2);
    }

    @Override // com.miui.todo.view.swipemenulayout.Horizontal
    public void autoCloseMenu(OverScroller overScroller, int i, int i2) {
        overScroller.startScroll(i, 0, -i, 0, i2);
    }

    @Override // com.miui.todo.view.swipemenulayout.Horizontal
    public Horizontal.Checker checkXY(int i, int i2) {
        this.mChecker.x = i;
        this.mChecker.y = i2;
        this.mChecker.shouldResetSwipe = false;
        if (this.mChecker.x == 0) {
            this.mChecker.shouldResetSwipe = true;
        }
        if (this.mChecker.x < 0) {
            this.mChecker.x = 0;
        }
        if (this.mChecker.x > getMenuView().getWidth()) {
            this.mChecker.x = getMenuView().getWidth();
        }
        return this.mChecker;
    }

    @Override // com.miui.todo.view.swipemenulayout.Horizontal
    public boolean isClickOnContentView(int i, float f) {
        return f < ((float) (i - getMenuView().getWidth()));
    }
}
