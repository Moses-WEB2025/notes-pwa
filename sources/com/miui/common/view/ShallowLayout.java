package com.miui.common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.miui.notes.R;

/* JADX INFO: loaded from: classes2.dex */
public class ShallowLayout extends FrameLayout {
    private boolean mIsShowShallow;
    private Paint mShaderPaint;
    private int mViewHeight;
    private int mViewWidth;

    public ShallowLayout(Context context) {
        super(context);
        this.mShaderPaint = new Paint();
        this.mViewWidth = 0;
        this.mViewHeight = 0;
        this.mIsShowShallow = false;
        init();
    }

    public ShallowLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mShaderPaint = new Paint();
        this.mViewWidth = 0;
        this.mViewHeight = 0;
        this.mIsShowShallow = false;
        init();
    }

    private void init() {
        this.mShaderPaint.setAntiAlias(true);
    }

    public void setShowShallow(boolean z) {
        if (z == this.mIsShowShallow) {
            return;
        }
        this.mIsShowShallow = z;
        invalidate();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        int color;
        int color2;
        super.dispatchDraw(canvas);
        if (this.mIsShowShallow) {
            if (this.mViewWidth != getWidth() || this.mViewHeight != getHeight()) {
                this.mViewWidth = getWidth();
                this.mViewHeight = getHeight();
                if (getResources() != null) {
                    color = getResources().getColor(R.color.index_folder_list_container_shallow_color0);
                    color2 = getResources().getColor(R.color.index_folder_list_container_shallow_color1);
                } else {
                    color = 16777215;
                    color2 = 16777215;
                }
                this.mShaderPaint.setShader(new LinearGradient(this.mViewWidth - (getResources() != null ? getResources().getDimensionPixelOffset(R.dimen.pad_folder_list_layout_shallow_width) : 0), 0.0f, this.mViewWidth, 0.0f, color, color2, Shader.TileMode.CLAMP));
            }
            canvas.drawRect(0.0f, 0.0f, this.mViewWidth, this.mViewHeight, this.mShaderPaint);
        }
    }
}
