package com.miui.notes.component;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import com.miui.common.tool.SmoothDrawHelper;
import com.miui.notes.R;
import com.miui.notes.tool.BitmapDecoder;

/* JADX INFO: loaded from: classes4.dex */
public class PreviewImageView extends AppCompatImageView {
    public static final int RADIUS_TYPE_ALL_ROUND = 1;
    public static final int RADIUS_TYPE_TOP_ROUND = 0;
    private BitmapDecoder mBitmapDecoder;
    private BitmapShader mBitmapShader;
    private int mDensityDpi;
    private RectF mDrawRectF;
    private int mHeightSize;
    private Matrix mMatrix;
    private int mMaxHeightSize;
    private int mMinHeightSize;
    private Paint mPaint;
    private float mRadius;
    private int mRadiusType;
    private Path mRoundPath;
    private int mSetupMaxHeightSize;
    private SmoothDrawHelper mSmoothDrawHelper;
    private int mWidthSize;
    private float[] rids;

    public PreviewImageView(Context context) {
        super(context);
        this.mRadiusType = -1;
        this.mSetupMaxHeightSize = -1;
    }

    public PreviewImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRadiusType = -1;
        this.mSetupMaxHeightSize = -1;
        init();
    }

    public PreviewImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mRadiusType = -1;
        this.mSetupMaxHeightSize = -1;
        init();
    }

    private void init() {
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setAntiAlias(true);
        this.mMatrix = new Matrix();
        this.mMaxHeightSize = (int) getContext().getResources().getDimension(R.dimen.item_grid_image_max_height);
        this.mSmoothDrawHelper = new SmoothDrawHelper();
        this.mRoundPath = new Path();
        this.mDensityDpi = getContext().getResources().getDisplayMetrics().densityDpi;
        this.mDrawRectF = new RectF();
    }

    public void setBitmapDecoder(BitmapDecoder bitmapDecoder) {
        this.mBitmapDecoder = bitmapDecoder;
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onMeasure(int i, int i2) throws Throwable {
        if (this.mBitmapDecoder != null) {
            int i3 = this.mSetupMaxHeightSize;
            if (i3 != -1) {
                this.mMaxHeightSize = i3;
            }
            int iResolveSize = resolveSize(Math.max(getPaddingLeft() + getPaddingRight(), getSuggestedMinimumWidth()), i);
            this.mWidthSize = iResolveSize;
            int polishedScaledHeight = this.mBitmapDecoder.getPolishedScaledHeight(iResolveSize);
            this.mHeightSize = polishedScaledHeight;
            int paddingTop = polishedScaledHeight + getPaddingTop() + getPaddingBottom();
            this.mHeightSize = paddingTop;
            int i4 = this.mMaxHeightSize;
            if (paddingTop > i4) {
                this.mHeightSize = i4;
            }
            int i5 = this.mHeightSize;
            int i6 = this.mMinHeightSize;
            if (i5 < i6) {
                this.mHeightSize = i6;
            }
            setMeasuredDimension(View.MeasureSpec.makeMeasureSpec(this.mWidthSize, 1073741824), View.MeasureSpec.makeMeasureSpec(this.mHeightSize, 1073741824));
            return;
        }
        super.onMeasure(i, i2);
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(Canvas canvas) {
        Bitmap bitmap;
        Drawable drawable = getDrawable();
        float[] radiusArray = getRadiusArray(this.mRadius, this.mRadiusType);
        if (drawable != null && (bitmap = ((BitmapDrawable) drawable).getBitmap()) != null) {
            this.mDrawRectF.set(0.0f, 0.0f, this.mWidthSize, this.mHeightSize);
            BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            this.mBitmapShader = bitmapShader;
            bitmapShader.setLocalMatrix(getImageMatrix());
            this.mPaint.setShader(this.mBitmapShader);
            canvas.save();
            this.mRoundPath.addRoundRect(this.mDrawRectF, new float[]{radiusArray[0], radiusArray[1], radiusArray[2], radiusArray[3], radiusArray[4], radiusArray[5], radiusArray[6], radiusArray[7]}, Path.Direction.CW);
            canvas.drawPath(this.mRoundPath, this.mPaint);
            canvas.restore();
            return;
        }
        Paint paint = new Paint();
        paint.setColor(getContext().getColor(R.color.v12_theme_content_bg_color));
        paint.setStyle(Paint.Style.FILL);
        this.mDrawRectF.set(0.0f, 0.0f, this.mWidthSize, this.mMinHeightSize);
        this.mRoundPath.addRoundRect(this.mDrawRectF, new float[]{radiusArray[0], radiusArray[1], radiusArray[2], radiusArray[3], radiusArray[4], radiusArray[5], radiusArray[6], radiusArray[7]}, Path.Direction.CW);
        canvas.drawPath(this.mRoundPath, paint);
        super.onDraw(canvas);
    }

    public void setRadius(float f) {
        this.mRadius = f;
        this.mMinHeightSize = ((int) (f * 2.0f)) + 10;
    }

    public void setRadiusType(int i) {
        this.mRadiusType = i;
    }

    public void setMaxHeightSize(int i) {
        this.mSetupMaxHeightSize = i;
    }

    public float[] getRadiusArray(float f, int i) {
        if (i == 1) {
            return new float[]{f, f, f, f, f, f, f, f};
        }
        return new float[]{f, f, f, f, 0.0f, 0.0f, 0.0f, 0.0f};
    }

    public void setRoundRadius(float f, float f2, float f3, float f4) {
        this.rids = new float[]{f, f, f2, f2, f3, f3, f4, f4};
    }
}
