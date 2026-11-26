package com.miui.notes.ui.widget;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/* JADX INFO: loaded from: classes4.dex */
public class FolmeVpScroller extends Scroller {
    private int mDuration;

    public FolmeVpScroller(Context context) {
        super(context);
        this.mDuration = 1500;
    }

    public FolmeVpScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
        this.mDuration = 1500;
    }

    @Override // android.widget.Scroller
    public void startScroll(int i, int i2, int i3, int i4, int i5) {
        super.startScroll(i, i2, i3, i4, this.mDuration);
    }

    @Override // android.widget.Scroller
    public void startScroll(int i, int i2, int i3, int i4) {
        super.startScroll(i, i2, i3, i4, this.mDuration);
    }

    public int getMyDuration() {
        return this.mDuration;
    }

    public void setMyDuration(int i) {
        this.mDuration = i;
    }
}
