package com.miui.blur.sdk.backdrop;

import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;

/* JADX INFO: loaded from: classes2.dex */
public class BlurLayerHolderForGoogle {
    private final Paint mBlendPaint;
    private boolean mIsEnable;
    private final Outline mOutline;
    private final Rect mTmpRect;

    public BlurLayerHolderForGoogle() {
        Paint paint = new Paint();
        this.mBlendPaint = paint;
        this.mOutline = new Outline();
        this.mTmpRect = new Rect();
        this.mIsEnable = BlurManager.GOOGLE_BACKGROUND_BLUR_SUPPORTED;
        paint.setAntiAlias(true);
    }

    public void draw(Canvas canvas, BlurDrawInfo blurDrawInfo, float f) {
        if (!this.mIsEnable || blurDrawInfo.getBlurStyle() == null || blurDrawInfo.getBlurStyle().getConfigs() == null) {
            return;
        }
        blurDrawInfo.getBlurOutline(this.mOutline);
        int i = this.mOutline.mMode;
        if (i == 2) {
            drawPath(canvas, this.mOutline.mPath, blurDrawInfo);
        } else if (i == 1) {
            this.mOutline.getRect(this.mTmpRect);
            drawRect(canvas, this.mTmpRect, f, blurDrawInfo);
        } else {
            drawEmpty(canvas, blurDrawInfo);
        }
    }

    private void drawPath(Canvas canvas, Path path, BlurDrawInfo blurDrawInfo) {
        for (BlurStyle.BlendConfig blendConfig : blurDrawInfo.getBlurStyle().getConfigs()) {
            setupBlendMode(blendConfig.mColor, blendConfig.mBlendMode);
            canvas.drawPath(path, this.mBlendPaint);
        }
    }

    private void drawRect(Canvas canvas, Rect rect, float f, BlurDrawInfo blurDrawInfo) {
        for (BlurStyle.BlendConfig blendConfig : blurDrawInfo.getBlurStyle().getConfigs()) {
            setupBlendMode(blendConfig.mColor, blendConfig.mBlendMode);
            canvas.drawRoundRect(rect.left, rect.top, rect.right, rect.bottom, f, f, this.mBlendPaint);
        }
    }

    private void drawEmpty(Canvas canvas, BlurDrawInfo blurDrawInfo) {
        for (BlurStyle.BlendConfig blendConfig : blurDrawInfo.getBlurStyle().getConfigs()) {
            setupBlendMode(blendConfig.mColor, blendConfig.mBlendMode);
            canvas.drawRect(0.0f, 0.0f, blurDrawInfo.getWidth(), blurDrawInfo.getHeight(), this.mBlendPaint);
        }
    }

    private void setupBlendMode(int i, BlendMode blendMode) {
        this.mBlendPaint.setColor(i);
        this.mBlendPaint.setBlendMode(blendMode);
    }

    public void setEnable(boolean z) {
        this.mIsEnable = z;
    }
}
