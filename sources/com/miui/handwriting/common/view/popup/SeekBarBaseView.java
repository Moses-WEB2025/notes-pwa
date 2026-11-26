package com.miui.handwriting.common.view.popup;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.widget.SeekBar;
import androidx.appcompat.widget.AppCompatSeekBar;
import com.miui.creation.common.R;
import xc.engine.utils.DisplayUtils;

/* JADX INFO: loaded from: classes3.dex */
public abstract class SeekBarBaseView extends AppCompatSeekBar {
    protected GradientDrawable BackGroundGrad;
    protected Drawable BackGroundImg;
    protected boolean isSolidColor;
    private Paint mBorderPaint;
    private RectF mBorderRect;
    protected int[] mColors;
    protected int mLeftColor;
    protected ColorPickerPopupWindow.OnColorPickerColorChangeListener mOnColorChangeListener;
    protected int mRightColor;
    private float mThumbMargin;
    private Paint mThumbPaint;
    private float mThumbRadius;
    private float mThumbScale;
    private Paint mThumbStrokePaint;

    protected void addOnColorChangeListener() {
    }

    protected void initBackgroundColor() {
    }

    protected abstract void notifyColorChange(int i);

    protected abstract void notifyColorChangeOnStopTouch(int i);

    protected void setupBackground(GradientDrawable gradientDrawable) {
    }

    protected void updateCurrentBackground() {
    }

    public SeekBarBaseView(Context context) {
        super(context);
        this.mRightColor = -16777216;
        this.mLeftColor = 16777215;
        this.mColors = new int[]{16777215, -16777216};
        this.isSolidColor = false;
        this.mThumbScale = 1.0f;
        init();
    }

    public SeekBarBaseView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRightColor = -16777216;
        this.mLeftColor = 16777215;
        this.mColors = new int[]{16777215, -16777216};
        this.isSolidColor = false;
        this.mThumbScale = 1.0f;
        getAttrs(context, attributeSet);
        init();
    }

    public SeekBarBaseView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mRightColor = -16777216;
        this.mLeftColor = 16777215;
        this.mColors = new int[]{16777215, -16777216};
        this.isSolidColor = false;
        this.mThumbScale = 1.0f;
        getAttrs(context, attributeSet);
        init();
    }

    private void getAttrs(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SeekBarBaseView);
        try {
            this.mThumbScale = typedArrayObtainStyledAttributes.getFloat(R.styleable.SeekBarBaseView_thumb_scale, 1.0f);
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    protected void init() {
        initBackgroundColor();
        getProgressDrawable().setColorFilter(0, PorterDuff.Mode.SRC);
        setMinimumHeight(getResources().getDimensionPixelSize(R.dimen.creation_seekbar_minimum_height));
        setSplitTrack(false);
        setThumbOffset(0);
        setThumb(getResources().getDrawable(R.drawable.seekbar_thumb, null));
        Paint paint = new Paint();
        this.mThumbPaint = paint;
        paint.setStyle(Paint.Style.STROKE);
        this.mThumbPaint.setColor(-1);
        this.mThumbPaint.setStrokeWidth(DisplayUtils.dip2px(getContext(), this.mThumbScale * 8.14f));
        this.mThumbPaint.setAntiAlias(true);
        this.mThumbPaint.setFlags(1);
        Paint paint2 = new Paint();
        this.mThumbStrokePaint = paint2;
        paint2.setStyle(Paint.Style.STROKE);
        this.mThumbStrokePaint.setColor(getResources().getColor(R.color.creation_thumb_border, null));
        this.mThumbStrokePaint.setStrokeWidth(DisplayUtils.dip2px(getContext(), 0.7f));
        this.mThumbStrokePaint.setAntiAlias(true);
        this.mThumbStrokePaint.setFlags(1);
        this.mThumbRadius = getResources().getDimension(R.dimen.creation_seekbar_thumb_radius) * this.mThumbScale;
        this.mThumbMargin = getResources().getDimension(R.dimen.creation_seekbar_thumb_margin) * this.mThumbScale;
        this.BackGroundGrad = new GradientDrawable(DisplayUtils.isRTL() ? GradientDrawable.Orientation.RIGHT_LEFT : GradientDrawable.Orientation.LEFT_RIGHT, new int[]{this.mLeftColor, this.mRightColor});
        this.BackGroundImg = getResources().getDrawable(R.drawable.color_picker_alpha_bg, null);
        setupBackground();
        setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.miui.handwriting.common.view.popup.SeekBarBaseView.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (z) {
                    SeekBarBaseView.this.notifyColorChange(seekBar.getProgress());
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                SeekBarBaseView.this.notifyColorChangeOnStopTouch(seekBar.getProgress());
            }
        });
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(0));
        stateListDrawable.addState(new int[]{android.R.attr.state_focused}, new ColorDrawable(0));
        setProgressDrawable(stateListDrawable);
        Paint paint3 = new Paint();
        this.mBorderPaint = paint3;
        paint3.setStyle(Paint.Style.STROKE);
        this.mBorderPaint.setColor(getResources().getColor(R.color.creation_seekbar_border, null));
        this.mBorderPaint.setStrokeWidth(1.5f);
        setPadding(0, getPaddingTop(), 0, getPaddingBottom());
    }

    @Override // androidx.appcompat.widget.AppCompatSeekBar, android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int paddingLeft = getPaddingLeft();
        int width = (int) ((((getWidth() - paddingLeft) - getPaddingRight()) - this.mThumbRadius) - (this.mThumbMargin * 2.0f));
        float progress = (getProgress() - getMin()) / (getMax() - getMin());
        if (DisplayUtils.isRTL()) {
            progress = 1.0f - progress;
        }
        float f = (progress * width) + paddingLeft + (this.mThumbRadius / 2.0f) + this.mThumbMargin;
        float height = getHeight() / 2.0f;
        canvas.drawCircle(f, height, ((this.mThumbRadius / 2.0f) - (this.mThumbPaint.getStrokeWidth() / 2.0f)) - this.mThumbStrokePaint.getStrokeWidth(), this.mThumbPaint);
        canvas.drawCircle(f, height, (this.mThumbRadius / 2.0f) - this.mThumbStrokePaint.getStrokeWidth(), this.mThumbStrokePaint);
        RectF rectF = new RectF(this.mBorderPaint.getStrokeWidth(), 0.0f, getWidth(), getHeight());
        float f2 = this.mThumbRadius;
        canvas.drawRoundRect(rectF, f2, f2, this.mBorderPaint);
    }

    protected void setupBackground() {
        if (this.isSolidColor) {
            setupBackground(this.BackGroundGrad);
        } else {
            this.BackGroundGrad.setColors(this.mColors);
            this.BackGroundGrad.setShape(0);
            this.BackGroundGrad.setCornerRadius(DisplayUtils.dip2px(getContext(), 36.0f));
            this.BackGroundGrad.setStroke(0, 0);
            setBackground(new LayerDrawable(new Drawable[]{this.BackGroundImg, this.BackGroundGrad}));
        }
        invalidate();
    }

    public void setSolidColor(boolean z) {
        this.isSolidColor = z;
    }
}
