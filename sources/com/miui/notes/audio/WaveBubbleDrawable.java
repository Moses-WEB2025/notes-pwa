package com.miui.notes.audio;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.AnimatedVectorDrawable;
import com.miui.notes.R;

/* JADX INFO: loaded from: classes4.dex */
@Deprecated
public class WaveBubbleDrawable extends BubbleDrawable {
    protected int mLineHeight0Offset;
    protected int mLineHeight1Offset;
    protected int mLineHeight2Offset;
    protected int mWaveFrame0LineHeight0;
    protected int mWaveFrame0LineHeight1;
    protected int mWaveFrame0LineHeight2;
    protected int mWaveFrame1LineHeight0;
    protected int mWaveFrame1LineHeight1;
    protected int mWaveFrame1LineHeight2;
    protected int mWaveLineInterval;
    protected int mWaveLineWidth;
    protected int mWaveToLeftRec;
    protected AnimatedVectorDrawable mWaveVectorDrawable;
    protected int mWaveVectorHeight;
    protected int mWaveVectorWidth;

    public WaveBubbleDrawable(Context context, int i, int i2) {
        super(context, i, i2);
    }

    public WaveBubbleDrawable(Context context, int i, int i2, int i3) {
        super(context, i, i2);
    }

    public WaveBubbleDrawable(Context context) {
        super(context);
    }

    @Override // com.miui.notes.audio.BubbleDrawable
    protected void drawWave(Canvas canvas) {
        super.drawWave(canvas);
        if (isDrawWave()) {
            Paint paint = new Paint(this.mPaint);
            paint.setColor(this.mStyle.contentColor);
            paint.setStyle(Paint.Style.FILL);
            int i = this.mTextToRightRect + this.mWaveToLeftRec;
            if (isRtl()) {
                i = (this.mBubbleWidth - i) - ((this.mWaveLineWidth * 10) + (this.mWaveLineInterval * 9));
            }
            RectF rectF = new RectF(i, (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight0 + this.mLineHeight0Offset) / 2), this.mWaveLineWidth + i, this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight0 + this.mLineHeight0Offset) / 2));
            RectF rectF2 = new RectF(this.mWaveLineWidth + i + this.mWaveLineInterval, (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight1 + this.mLineHeight1Offset) / 2), (this.mWaveLineWidth * 2) + i + this.mWaveLineInterval, this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight1 + this.mLineHeight1Offset) / 2));
            RectF rectF3 = new RectF((this.mWaveLineWidth * 2) + i + (this.mWaveLineInterval * 2), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight2 + this.mLineHeight2Offset) / 2), (this.mWaveLineWidth * 3) + i + (this.mWaveLineInterval * 2), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight2 + this.mLineHeight2Offset) / 2));
            RectF rectF4 = new RectF((this.mWaveLineWidth * 3) + i + (this.mWaveLineInterval * 3), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight1 + this.mLineHeight1Offset) / 2), (this.mWaveLineWidth * 4) + i + (this.mWaveLineInterval * 3), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight1 + this.mLineHeight1Offset) / 2));
            RectF rectF5 = new RectF((this.mWaveLineWidth * 4) + i + (this.mWaveLineInterval * 4), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight0 + this.mLineHeight0Offset) / 2), (this.mWaveLineWidth * 5) + i + (this.mWaveLineInterval * 4), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight0 + this.mLineHeight0Offset) / 2));
            RectF rectF6 = new RectF((this.mWaveLineWidth * 5) + i + (this.mWaveLineInterval * 5), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight0 + this.mLineHeight0Offset) / 2), (this.mWaveLineWidth * 6) + i + (this.mWaveLineInterval * 5), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight0 + this.mLineHeight0Offset) / 2));
            int i2 = i;
            RectF rectF7 = new RectF((this.mWaveLineWidth * 6) + i + (this.mWaveLineInterval * 6), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight1 + this.mLineHeight1Offset) / 2), (this.mWaveLineWidth * 7) + i + (this.mWaveLineInterval * 6), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight1 + this.mLineHeight1Offset) / 2));
            RectF rectF8 = new RectF(i2 + (this.mWaveLineWidth * 7) + (this.mWaveLineInterval * 7), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight2 + this.mLineHeight2Offset) / 2), i2 + (this.mWaveLineWidth * 8) + (this.mWaveLineInterval * 7), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight2 + this.mLineHeight2Offset) / 2));
            RectF rectF9 = new RectF(i2 + (this.mWaveLineWidth * 8) + (this.mWaveLineInterval * 8), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight1 + this.mLineHeight1Offset) / 2), i2 + (this.mWaveLineWidth * 9) + (this.mWaveLineInterval * 8), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight1 + this.mLineHeight1Offset) / 2));
            RectF rectF10 = new RectF(i2 + (this.mWaveLineWidth * 9) + (this.mWaveLineInterval * 9), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight0 + this.mLineHeight0Offset) / 2), i2 + (this.mWaveLineWidth * 10) + (this.mWaveLineInterval * 9), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight0 + this.mLineHeight0Offset) / 2));
            int i3 = this.mWaveLineWidth;
            canvas.drawRoundRect(rectF, i3 / 2, i3 / 2, paint);
            int i4 = this.mWaveLineWidth;
            canvas.drawRoundRect(rectF2, i4 / 2, i4 / 2, paint);
            int i5 = this.mWaveLineWidth;
            canvas.drawRoundRect(rectF3, i5 / 2, i5 / 2, paint);
            int i6 = this.mWaveLineWidth;
            canvas.drawRoundRect(rectF4, i6 / 2, i6 / 2, paint);
            int i7 = this.mWaveLineWidth;
            canvas.drawRoundRect(rectF5, i7 / 2, i7 / 2, paint);
            int i8 = this.mWaveLineWidth;
            canvas.drawRoundRect(rectF6, i8 / 2, i8 / 2, paint);
            int i9 = this.mWaveLineWidth;
            canvas.drawRoundRect(rectF7, i9 / 2, i9 / 2, paint);
            int i10 = this.mWaveLineWidth;
            canvas.drawRoundRect(rectF8, i10 / 2, i10 / 2, paint);
            int i11 = this.mWaveLineWidth;
            canvas.drawRoundRect(rectF9, i11 / 2, i11 / 2, paint);
            int i12 = this.mWaveLineWidth;
            canvas.drawRoundRect(rectF10, i12 / 2, i12 / 2, paint);
        }
    }

    protected boolean isDrawWave() {
        return this.mDrawType == 0 || this.mDrawType == 1;
    }

    @Override // com.miui.notes.audio.BubbleDrawable
    protected void initBubblePadding(Context context) {
        super.initBubblePadding(context);
        this.mBubblePaddingBottom = (int) context.getResources().getDimension(R.dimen.bubble_to_bottom);
    }

    @Override // com.miui.notes.audio.BubbleDrawable
    protected void initBubbleDecoration(Context context) {
        super.initBubbleDecoration(context);
        this.mWaveLineWidth = (int) context.getResources().getDimension(R.dimen.bubble_wave_line_width);
        this.mWaveLineInterval = (int) context.getResources().getDimension(R.dimen.bubble_wave_line_interval);
        this.mWaveToLeftRec = (int) context.getResources().getDimension(R.dimen.bubble_wave_to_left_rect);
        this.mWaveVectorWidth = (int) context.getResources().getDimension(R.dimen.bubble_wave_width);
        this.mWaveVectorHeight = (int) context.getResources().getDimension(R.dimen.bubble_wave_height);
        this.mWaveFrame0LineHeight0 = (int) context.getResources().getDimension(R.dimen.bubble_wave_frame0_line_0);
        this.mWaveFrame0LineHeight1 = (int) context.getResources().getDimension(R.dimen.bubble_wave_frame0_line_1);
        this.mWaveFrame0LineHeight2 = (int) context.getResources().getDimension(R.dimen.bubble_wave_frame0_line_2);
        this.mWaveFrame1LineHeight0 = (int) context.getResources().getDimension(R.dimen.bubble_wave_frame1_line_0);
        this.mWaveFrame1LineHeight1 = (int) context.getResources().getDimension(R.dimen.bubble_wave_frame1_line_1);
        this.mWaveFrame1LineHeight2 = (int) context.getResources().getDimension(R.dimen.bubble_wave_frame1_line_2);
    }
}
