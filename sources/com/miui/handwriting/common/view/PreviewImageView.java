package com.miui.handwriting.common.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Base64;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;
import com.miui.creation.common.R;
import com.miui.handwriting.common.ai.BitmapDecoder;

/* JADX INFO: loaded from: classes3.dex */
public class PreviewImageView extends AppCompatImageView {
    public static final int RADIUS_TYPE_ALL_ROUND = 1;
    public static final int RADIUS_TYPE_TOP_ROUND = 0;
    private BitmapDecoder mBitmapDecoder;
    private BitmapShader mBitmapShader;
    private int mDensityDpi;
    private Rect mDrawRect;
    private RectF mDrawRectF;
    private int mHeightSize;
    private String mImageDir;
    private Matrix mMatrix;
    private int mMaxHeightSize;
    private int mMinHeightSize;
    private Paint mPaint;
    private float mRadius;
    private int mRadiusType;
    private Path mRoundPath;
    private int mSetupMaxHeightSize;
    private miuix.smooth.internal.SmoothDrawHelper mSmoothDrawHelper;
    private int mStrokeColor;
    private float mStrokeWidth;
    private int mWidthSize;
    private float[] rids;
    private boolean useStroke;

    public PreviewImageView(Context context) {
        super(context);
        this.mRadiusType = -1;
        this.mSetupMaxHeightSize = -1;
        this.useStroke = false;
        this.mImageDir = null;
    }

    public PreviewImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRadiusType = -1;
        this.mSetupMaxHeightSize = -1;
        this.useStroke = false;
        this.mImageDir = null;
        init(attributeSet);
    }

    public PreviewImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mRadiusType = -1;
        this.mSetupMaxHeightSize = -1;
        this.useStroke = false;
        this.mImageDir = null;
        init(attributeSet);
    }

    private void init(AttributeSet attributeSet) {
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setAntiAlias(true);
        this.mMatrix = new Matrix();
        this.mMaxHeightSize = (int) getContext().getResources().getDimension(R.dimen.item_grid_image_max_height);
        this.mSmoothDrawHelper = new miuix.smooth.internal.SmoothDrawHelper();
        this.mRoundPath = new Path();
        this.mDensityDpi = getContext().getResources().getDisplayMetrics().densityDpi;
        this.mDrawRectF = new RectF();
        this.mDrawRect = new Rect();
        float dimension = getContext().obtainStyledAttributes(attributeSet, R.styleable.PreviewImageView).getDimension(R.styleable.PreviewImageView_cornerRadius, 0.0f);
        this.mRadius = dimension;
        this.mSmoothDrawHelper.setRadius(dimension);
    }

    public void setBitmapDecoder(BitmapDecoder bitmapDecoder) {
        this.mBitmapDecoder = bitmapDecoder;
    }

    public void setBitmap(String str, long j) {
        this.mImageDir = str;
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        bitmapCreateBitmap.eraseColor(-1);
        Glide.with(this).load(str).placeholder(new BitmapDrawable(getResources(), bitmapCreateBitmap)).signature(new ObjectKey(Long.valueOf(j))).into(this);
    }

    public void setBitmapWithBase64(String str, long j) {
        this.mImageDir = str;
        Glide.with(this).load(Base64.decode(str, 0)).signature(new ObjectKey(Long.valueOf(j))).into(this);
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
        this.mDrawRect.set(0, 0, getWidth(), getHeight());
        canvas.clipPath(this.mSmoothDrawHelper.getSmoothPath(this.mDrawRect));
        super.onDraw(canvas);
        if (this.useStroke) {
            this.mPaint.setStyle(Paint.Style.STROKE);
            this.mPaint.setStrokeWidth(this.mStrokeWidth);
            this.mPaint.setColor(this.mStrokeColor);
            canvas.drawPath(this.mSmoothDrawHelper.getSmoothPath(this.mDrawRect), this.mPaint);
        }
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
        float[] fArr = {f, f, f2, f2, f3, f3, f4, f4};
        this.rids = fArr;
        this.mSmoothDrawHelper.setRadii(fArr);
    }

    public void useStroke(boolean z, float f, int i) {
        this.useStroke = z;
        this.mStrokeWidth = f;
        this.mStrokeColor = i;
    }

    public String getImageDir() {
        return this.mImageDir;
    }

    public void setImageDir(String str) {
        this.mImageDir = str;
    }
}
