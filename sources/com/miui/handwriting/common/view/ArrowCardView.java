package com.miui.handwriting.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.miui.creation.common.R;
import xc.engine.utils.DisplayUtils;

/* JADX INFO: loaded from: classes3.dex */
public class ArrowCardView extends ViewGroup {
    public static int ARROW_MODE_DOWN = 1;
    public static int ARROW_MODE_UP;
    private int mArrowHeight;
    private int mArrowMode;
    private int mArrowOffset;
    private int mArrowStartX;
    private int mArrowStartY;
    private int mArrowWidth;
    private int mBackgroundColor;
    private int mCardEndX;
    private int mCardEndY;
    private int mCardStartX;
    private int mCardStartY;
    private Context mContext;
    private int mHorizontalPadding;
    private int mRadius;
    private int mShadowColor;
    private int mShadowThickness;
    private int mVerticalPadding;

    public ArrowCardView(Context context) {
        this(context, null);
    }

    public ArrowCardView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ArrowCardView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mArrowStartX = 0;
        this.mArrowStartY = 0;
        this.mArrowMode = ARROW_MODE_DOWN;
        this.mCardStartX = 0;
        this.mCardStartY = 0;
        this.mCardEndX = 0;
        this.mCardEndY = 0;
        this.mArrowWidth = 100;
        this.mArrowHeight = 0;
        this.mRadius = 18;
        this.mBackgroundColor = -1;
        this.mArrowOffset = 0;
        this.mShadowColor = -7829368;
        this.mShadowThickness = 0;
        this.mHorizontalPadding = 0;
        this.mVerticalPadding = 0;
        this.mContext = context;
        this.mHorizontalPadding = DisplayUtils.dip2px(context, 12.0f);
        this.mVerticalPadding = DisplayUtils.dip2px(context, 15.0f);
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.ArrowCardView, i, 0);
        for (int i2 = 0; i2 < typedArrayObtainStyledAttributes.getIndexCount(); i2++) {
            int index = typedArrayObtainStyledAttributes.getIndex(i2);
            if (index == R.styleable.ArrowCardView_arrow_width) {
                this.mArrowWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.mArrowWidth);
            } else if (index == R.styleable.ArrowCardView_arrow_height) {
                this.mArrowHeight = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.mArrowHeight);
            } else if (index == R.styleable.ArrowCardView_radius) {
                this.mRadius = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.mRadius);
            } else if (index == R.styleable.ArrowCardView_background_color) {
                this.mBackgroundColor = typedArrayObtainStyledAttributes.getColor(index, this.mBackgroundColor);
            } else if (index == R.styleable.ArrowCardView_arrow_offset) {
                this.mArrowOffset = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.mArrowOffset);
            } else if (index == R.styleable.ArrowCardView_shadow_color) {
                this.mShadowColor = typedArrayObtainStyledAttributes.getColor(index, this.mShadowColor);
            } else if (index == R.styleable.ArrowCardView_shadow_thickness) {
                this.mShadowThickness = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.mShadowThickness);
            }
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int childCount = getChildCount();
        int iMax = 0;
        int measuredHeight = 0;
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) childAt.getLayoutParams();
            if (childAt.getVisibility() != 8) {
                measureChild(childAt, i, i2);
                iMax = Math.max(iMax, childAt.getMeasuredWidth() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin);
                measuredHeight = measuredHeight + childAt.getMeasuredHeight() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
            }
        }
        int i4 = this.mHorizontalPadding;
        this.mCardStartX = i4;
        int i5 = this.mVerticalPadding;
        this.mCardStartY = i5;
        this.mCardEndX = i4 + iMax;
        this.mCardEndY = i5 + measuredHeight;
        setMeasuredDimension(iMax + getPaddingLeft() + getPaddingRight() + (this.mHorizontalPadding * 2), measuredHeight + getPaddingTop() + getPaddingBottom() + (this.mVerticalPadding * 2));
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int childCount = getChildCount();
        int i5 = i2 + this.mVerticalPadding;
        int i6 = i + this.mHorizontalPadding;
        if (this.mArrowMode == ARROW_MODE_UP) {
            this.mArrowStartX = i6;
            this.mArrowStartY = i5;
        }
        int i7 = 0;
        int i8 = 0;
        while (i7 < childCount) {
            View childAt = getChildAt(i7);
            int measuredHeight = (childAt.getMeasuredHeight() * i7) + i5;
            int measuredHeight2 = childAt.getMeasuredHeight() + measuredHeight;
            childAt.layout(i6, measuredHeight, childAt.getMeasuredWidth() + i6, measuredHeight2);
            i7++;
            i8 = measuredHeight2;
        }
        if (this.mArrowMode == ARROW_MODE_DOWN) {
            this.mArrowStartX = i6;
            this.mArrowStartY = i8;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        setLayerType(1, null);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(this.mBackgroundColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
        paint.setColor(this.mBackgroundColor);
        RectF rectF = new RectF(this.mCardStartX, this.mCardStartY, this.mCardEndX, this.mCardEndY);
        int i = this.mRadius;
        canvas.drawRoundRect(rectF, i, i, paint);
        Path path = new Path();
        int i2 = (int) ((this.mArrowStartX + this.mArrowOffset) - (this.mArrowWidth / 2.0f));
        float f = i2;
        path.moveTo(f, this.mArrowStartY);
        path.lineTo(i2 + this.mArrowWidth, this.mArrowStartY);
        int i3 = this.mArrowMode;
        if (i3 == ARROW_MODE_UP) {
            path.lineTo(f + (this.mArrowWidth / 2.0f), this.mArrowStartY - this.mArrowHeight);
        } else if (i3 == ARROW_MODE_DOWN) {
            path.lineTo(f + (this.mArrowWidth / 2.0f), this.mArrowStartY + this.mArrowHeight);
        }
        path.close();
        canvas.drawPath(path, paint);
        if (this.mShadowThickness > 0) {
            paint.setMaskFilter(new BlurMaskFilter(this.mShadowThickness, BlurMaskFilter.Blur.OUTER));
            paint.setColor(this.mShadowColor);
            RectF rectF2 = new RectF(this.mCardStartX, this.mCardStartY, this.mCardEndX, this.mCardEndY);
            int i4 = this.mRadius;
            canvas.drawRoundRect(rectF2, i4, i4, paint);
            canvas.drawPath(path, paint);
        }
        super.dispatchDraw(canvas);
    }

    public void setArrowOffset(int i) {
        this.mArrowOffset = i;
        invalidate();
    }

    public void setArrowMode(int i) {
        this.mArrowMode = i;
        invalidate();
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new ViewGroup.MarginLayoutParams(getContext(), attributeSet);
    }
}
