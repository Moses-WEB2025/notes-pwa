package com.miui.notes.editor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;

/* JADX INFO: loaded from: classes4.dex */
public class ShadowAnimImageView extends AppCompatImageView {
    private Paint mShaderPaint;
    private int mViewHeight;
    private int mViewWidth;

    public ShadowAnimImageView(Context context) {
        super(context);
        this.mShaderPaint = new Paint();
        initBg();
    }

    public ShadowAnimImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mShaderPaint = new Paint();
        initBg();
    }

    public ShadowAnimImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mShaderPaint = new Paint();
        initBg();
    }

    private void initBg() {
        this.mShaderPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        this.mShaderPaint.setAntiAlias(true);
        setBackgroundResource(R.drawable.shadow_anim_image_view_bg);
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(Canvas canvas) {
        Bitmap bitmapDrawableToBitmap;
        if (this.mViewWidth == getWidth() && this.mViewHeight == getHeight()) {
            bitmapDrawableToBitmap = null;
        } else {
            this.mViewWidth = getWidth();
            this.mViewHeight = getHeight();
            bitmapDrawableToBitmap = UIUtils.drawableToBitmap(getBackground(), this.mViewWidth, this.mViewHeight);
            this.mShaderPaint.setShader(new BitmapShader(bitmapDrawableToBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        }
        int iSaveLayer = canvas.saveLayer(0.0f, 0.0f, this.mViewWidth, this.mViewHeight, null);
        super.onDraw(canvas);
        canvas.drawRect(0.0f, 0.0f, this.mViewWidth, this.mViewHeight, this.mShaderPaint);
        canvas.restoreToCount(iSaveLayer);
        UIUtils.releaseBitmap(bitmapDrawableToBitmap);
    }
}
