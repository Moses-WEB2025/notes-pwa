package com.miui.notes.feature.notelist.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewParent;
import com.miui.common.view.ShallowLayout;
import com.miui.notes.ui.view.HomepageSpringBackLayout;
import miuix.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes4.dex */
public class FolderRecyclerView extends RecyclerView {
    public HomepageSpringBackLayout homepageSpringBackLayout;

    @Override // android.view.View
    protected float getLeftFadingEdgeStrength() {
        return 0.0f;
    }

    @Override // android.view.View
    protected float getRightFadingEdgeStrength() {
        return 0.0f;
    }

    public FolderRecyclerView(Context context) {
        super(context);
    }

    public FolderRecyclerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public FolderRecyclerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setParentView(HomepageSpringBackLayout homepageSpringBackLayout) {
        this.homepageSpringBackLayout = homepageSpringBackLayout;
    }

    @Override // miuix.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        HomepageSpringBackLayout homepageSpringBackLayout = this.homepageSpringBackLayout;
        if (homepageSpringBackLayout != null) {
            homepageSpringBackLayout.internalRequestDisallowInterceptTouchEvent(true);
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override // miuix.recyclerview.widget.RecyclerView, androidx.recyclerview.widget.RecyclerView
    public void onScrolled(int i, int i2) {
        super.onScrolled(i, i2);
    }

    private void updateParentShallowStatus() {
        ViewParent parent = getParent();
        if (parent instanceof ShallowLayout) {
            if (computeHorizontalScrollExtent() + computeHorizontalScrollOffset() >= computeHorizontalScrollRange()) {
                ((ShallowLayout) parent).setShowShallow(false);
            } else {
                ((ShallowLayout) parent).setShowShallow(true);
            }
        }
    }
}
