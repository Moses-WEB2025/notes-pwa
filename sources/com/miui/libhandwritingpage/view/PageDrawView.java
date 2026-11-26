package com.miui.libhandwritingpage.view;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import xc.engine.api.ICreationEngine;

/* JADX INFO: loaded from: classes3.dex */
public class PageDrawView {
    int mType;
    PageSurfaceView surfaceView;
    PageTextureView textureView;

    public PageDrawView(Context context, int i) {
        this.mType = i;
        if (i == 0) {
            this.surfaceView = new PageSurfaceView(context);
        } else if (i == 1) {
            this.textureView = new PageTextureView(context);
        }
    }

    public View getView() {
        return this.mType == 0 ? this.surfaceView : this.textureView;
    }

    public ICreationEngine getCreationEngine() {
        return this.mType == 0 ? this.surfaceView.getCreationEngine() : this.textureView.getCreationEngine();
    }

    public void bindItemView(PageItemView pageItemView) {
        int i = this.mType;
        if (i == 0) {
            this.surfaceView.bindItemView(pageItemView);
        } else if (i == 1) {
            this.textureView.bindItemView(pageItemView);
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent, boolean z) {
        return this.mType == 0 ? this.surfaceView.onTouchEvent(motionEvent, z) : this.textureView.onTouchEvent(motionEvent, z);
    }

    public void onCanvasDataChanged(Object obj, int i) {
        int i2 = this.mType;
        if (i2 == 0) {
            this.surfaceView.onCanvasDataChanged(null, i);
        } else if (i2 == 1) {
            this.textureView.onCanvasDataChanged(null, i);
        }
    }

    public void setCanvasSize(float f, float f2) {
        int i = this.mType;
        if (i == 0) {
            this.surfaceView.setCanvasSize(f, f2);
        } else if (i == 1) {
            this.textureView.setCanvasSize(f, f2);
        }
    }
}
