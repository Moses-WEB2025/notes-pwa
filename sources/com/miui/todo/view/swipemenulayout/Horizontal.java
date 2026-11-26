package com.miui.todo.view.swipemenulayout;

import android.view.View;
import android.view.ViewGroup;
import android.widget.OverScroller;

/* JADX INFO: loaded from: classes4.dex */
abstract class Horizontal {
    public static final int LEFT_DIRECTION = 1;
    public static final int RIGHT_DIRECTION = -1;
    private int direction;
    protected Checker mChecker = new Checker();
    private int maxMidth;
    private View menuView;
    protected int viewRadius;

    public static final class Checker {
        public boolean shouldResetSwipe;
        public int x;
        public int y;
    }

    public abstract void autoCloseMenu(OverScroller overScroller, int i, int i2);

    public abstract void autoOpenMenu(OverScroller overScroller, int i, int i2);

    public abstract Checker checkXY(int i, int i2);

    public abstract boolean isClickOnContentView(int i, float f);

    public abstract boolean isMenuOpen(int i);

    public abstract boolean isMenuOpenNotEqual(int i);

    public Horizontal(int i, View view, int i2) {
        this.direction = i;
        this.menuView = view;
        this.viewRadius = i2;
    }

    public boolean canSwipe() {
        View view = this.menuView;
        return (view instanceof ViewGroup) && ((ViewGroup) view).getChildCount() > 0;
    }

    public boolean isCompleteClose(int i) {
        return i == 0 && (-getMenuView().getWidth()) * getDirection() != 0;
    }

    public int getDirection() {
        return this.direction;
    }

    public View getMenuView() {
        return this.menuView;
    }

    public int getMenuWidth() {
        return this.menuView.getWidth();
    }

    public void saveMenuMaxWidth(int i) {
        this.maxMidth = i;
    }

    public int getMenuMaxWidth() {
        return this.maxMidth;
    }
}
