package com.miui.maml.elements.video;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.View;

/* JADX INFO: loaded from: classes3.dex */
class NormalVideoView extends BaseVideoView {
    public NormalVideoView(Context context) {
        super(context);
    }

    @Override // com.miui.maml.elements.video.BaseVideoView
    protected void updateVideoSize() {
        requestLayout();
    }

    @Override // com.miui.maml.elements.video.BaseVideoView
    protected void onSurfaceCreated(SurfaceHolder surfaceHolder) {
        this.mSurface = surfaceHolder.getSurface();
    }

    @Override // com.miui.maml.elements.video.BaseVideoView
    protected void onSurfaceDestroyed() {
        this.mSurface = null;
    }

    @Override // com.miui.maml.elements.video.BaseVideoView
    protected void setFormat(int i) {
        getHolder().setFormat(i);
    }

    @Override // com.miui.maml.elements.video.BaseVideoView
    protected void addSurfaceHolderCallback() {
        getHolder().addCallback(this.mSHCallback);
    }

    @Override // android.view.SurfaceView, android.view.View
    protected void onMeasure(int i, int i2) {
        int i3;
        if (this.mScaleMode == 3) {
            int defaultSize = getDefaultSize(this.mVideoWidth, i);
            int defaultSize2 = getDefaultSize(this.mVideoHeight, i2);
            if (this.mVideoWidth > 0 && this.mVideoHeight > 0) {
                int mode = View.MeasureSpec.getMode(i);
                int size = View.MeasureSpec.getSize(i);
                int mode2 = View.MeasureSpec.getMode(i2);
                int size2 = View.MeasureSpec.getSize(i2);
                if (mode == 1073741824 && mode2 == 1073741824) {
                    if (this.mVideoWidth * size2 < this.mVideoHeight * size) {
                        defaultSize = (this.mVideoWidth * size2) / this.mVideoHeight;
                    } else {
                        if (this.mVideoWidth * size2 > this.mVideoHeight * size) {
                            defaultSize2 = (this.mVideoHeight * size) / this.mVideoWidth;
                            defaultSize = size;
                        }
                        defaultSize = size;
                    }
                    defaultSize2 = size2;
                } else if (mode == 1073741824) {
                    int i4 = (this.mVideoHeight * size) / this.mVideoWidth;
                    if (mode2 != Integer.MIN_VALUE || i4 <= size2) {
                        defaultSize2 = i4;
                        defaultSize = size;
                    }
                    defaultSize = size;
                    defaultSize2 = size2;
                } else {
                    if (mode2 == 1073741824) {
                        i3 = (this.mVideoWidth * size2) / this.mVideoHeight;
                        if (mode == Integer.MIN_VALUE && i3 > size) {
                            defaultSize = size;
                        }
                        defaultSize2 = size2;
                    } else {
                        int i5 = this.mVideoWidth;
                        int i6 = this.mVideoHeight;
                        if (mode2 != Integer.MIN_VALUE || i6 <= size2) {
                            i3 = i5;
                            size2 = i6;
                        } else {
                            i3 = (this.mVideoWidth * size2) / this.mVideoHeight;
                        }
                        if (mode == Integer.MIN_VALUE && i3 > size) {
                            defaultSize2 = (this.mVideoHeight * size) / this.mVideoWidth;
                            defaultSize = size;
                        }
                    }
                    defaultSize = i3;
                    defaultSize2 = size2;
                }
            }
            setMeasuredDimension(defaultSize, defaultSize2);
            return;
        }
        super.onMeasure(i, i2);
    }
}
