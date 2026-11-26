package com.miui.notes.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import com.miui.common.tool.SmoothDrawHelper;
import com.miui.notes.NoteApp;
import com.miui.notes.R;

/* JADX INFO: loaded from: classes4.dex */
public class RoundImageView extends AppCompatImageView {
    private BitmapShader mBitmapShader;
    private Paint mBorderPaint;
    private RectF mDrawRectF;
    private float mRadius;
    private Path mRoundPath;
    private Paint mShaderPaint;
    private SmoothDrawHelper mSmoothDrawHelper;

    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RoundImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mDrawRectF = new RectF();
        this.mSmoothDrawHelper = new SmoothDrawHelper();
        this.mRoundPath = new Path();
        this.mShaderPaint = new Paint();
        this.mBorderPaint = new Paint();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.RoundView);
        int color = typedArrayObtainStyledAttributes.getColor(1, ContextCompat.getColor(NoteApp.getInstance(), R.color.smartimagespan_border_color));
        float dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(2, 4);
        int dimensionPixelSize2 = typedArrayObtainStyledAttributes.getDimensionPixelSize(0, 0);
        typedArrayObtainStyledAttributes.recycle();
        this.mRadius = dimensionPixelSize;
        this.mShaderPaint.setAntiAlias(true);
        this.mBorderPaint.setStrokeWidth(dimensionPixelSize2);
        this.mBorderPaint.setColor(color);
        this.mBorderPaint.setStyle(Paint.Style.STROKE);
        this.mBorderPaint.setAntiAlias(true);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.mDrawRectF.set(0.0f, 0.0f, i, i2);
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            BitmapShader bitmapShader = new BitmapShader(((BitmapDrawable) drawable).getBitmap(), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            this.mBitmapShader = bitmapShader;
            bitmapShader.setLocalMatrix(getImageMatrix());
            this.mShaderPaint.setShader(this.mBitmapShader);
            canvas.save();
            this.mSmoothDrawHelper.getSmoothPathFromProvider(this.mRoundPath, this.mDrawRectF, null, this.mRadius + this.mBorderPaint.getStrokeWidth(), 0.0f, 0.0f);
            canvas.drawPath(this.mRoundPath, this.mShaderPaint);
            this.mSmoothDrawHelper.getSmoothPathFromProvider(this.mRoundPath, this.mDrawRectF, null, this.mRadius, 0.0f, 0.0f);
            canvas.drawPath(this.mRoundPath, this.mBorderPaint);
            canvas.restore();
            return;
        }
        super.onDraw(canvas);
    }
}
