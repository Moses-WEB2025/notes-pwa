package com.miui.richeditor.style.audio;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.animation.LinearInterpolator;
import androidx.core.content.ContextCompat;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.SmoothDrawHelper;
import com.miui.common.tool.UIUtils;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.richeditor.style.BaseAudioPlayListener;
import java.util.ArrayList;
import miuix.view.animation.SineEaseInOutInterpolator;

/* JADX INFO: loaded from: classes4.dex */
public class AnimAudioSpanDrawable extends WaveBubbleDrawable {
    protected int mContentEnd;
    protected int mContentStart;
    protected Bitmap mDisableTrashIcon;
    public boolean mIsPlaying;
    protected int mMarginLeft;
    protected Bitmap mMoveIndicatorBmp;
    public boolean mNeedShowGesturePointer;
    protected Bitmap mPlayStatusBmp;
    protected int mPointerWidth;
    protected ValueAnimator mRecordAnimator;
    private Path mRoundPath;
    private SmoothDrawHelper mSmoothDrawHelper;
    protected Bitmap mStopStatusBmp;
    protected int mTextPaddingStart;
    public int mThemeId;
    protected int mTmpWidth;
    protected int mTrashIconPaddingStartRect;
    protected ValueAnimator mValueAnimator;
    protected int mWaveFrame0LineHeight3;
    protected int mWaveFrame0LineHeight4;
    protected int mWaveFrame0LineHeight5;
    protected int mWaveFrame0LineHeight6;
    protected int mWavePaddingStart;
    protected int mWaveTmpFrame0LineHeight0;
    protected int mWaveTmpFrame0LineHeight1;
    protected int mWaveTmpFrame0LineHeight2;

    public AnimAudioSpanDrawable(int i, int i2, int i3, int i4) {
        super(i, i2);
        this.mContext = NoteApp.getInstance();
        this.mBubbleWidth = i4;
        updateBaseDimen(this.mContext, this.mBubbleWidth);
        onUpdateTheme(i3);
        this.mSmoothDrawHelper = new SmoothDrawHelper();
        this.mRoundPath = new Path();
    }

    public AnimAudioSpanDrawable(Context context) {
        super(context);
    }

    @Override // com.miui.richeditor.style.audio.BubbleDrawable
    public void onUpdateTheme(int i) {
        this.mThemeId = i;
        createDisableTrashIcon();
        createTrashIcon();
        this.mPaint.setColor(this.mStyle.bgColor);
        createPlayStatusBmp();
        createStopStatusBmp();
        createMoveIndicatorBmp();
    }

    protected void createPlayStatusBmp() {
        Drawable drawable = ContextCompat.getDrawable(this.mContext, R.drawable.ic_bubble_play_status_bg_default);
        switch (this.mThemeId) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 8:
            case 10:
            case 11:
                drawable = ContextCompat.getDrawable(this.mContext, R.drawable.ic_bubble_play_status_bg);
                break;
            case 5:
            case 7:
                drawable = ContextCompat.getDrawable(this.mContext, R.drawable.ic_bubble_play_status_bg_antique);
                break;
            case 6:
            case 9:
            case 12:
            case 13:
            case 14:
                drawable = ContextCompat.getDrawable(this.mContext, R.drawable.ic_bubble_play_status_bg_nightsky);
                break;
        }
        Bitmap bitmapDrawableToBitmap = UIUtils.drawableToBitmap(drawable);
        this.mPlayStatusBmp = bitmapDrawableToBitmap;
        bitmapDrawableToBitmap.setDensity(this.mDensityDpi);
    }

    protected void releasePlayStatusBmp() {
        UIUtils.releaseBitmap(this.mPlayStatusBmp);
    }

    protected void createStopStatusBmp() {
        Drawable drawable = ContextCompat.getDrawable(this.mContext, R.drawable.ic_bubble_stop_status_bg_default);
        switch (this.mThemeId) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 8:
            case 10:
            case 11:
                drawable = ContextCompat.getDrawable(this.mContext, R.drawable.ic_bubble_stop_status_bg);
                break;
            case 5:
            case 7:
                drawable = ContextCompat.getDrawable(this.mContext, R.drawable.ic_bubble_stop_status_bg_antique);
                break;
            case 6:
            case 9:
            case 12:
            case 13:
            case 14:
                drawable = ContextCompat.getDrawable(this.mContext, R.drawable.ic_bubble_stop_status_bg_nightsky);
                break;
        }
        Bitmap bitmapDrawableToBitmap = UIUtils.drawableToBitmap(drawable);
        this.mStopStatusBmp = bitmapDrawableToBitmap;
        bitmapDrawableToBitmap.setDensity(this.mDensityDpi);
    }

    protected void releaseStopStatusBmp() {
        UIUtils.releaseBitmap(this.mStopStatusBmp);
    }

    protected void createMoveIndicatorBmp() {
        Drawable drawable = ContextCompat.getDrawable(this.mContext, R.drawable.ic_bubble_audio_move_indicator_bg_default);
        switch (this.mThemeId) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 8:
            case 10:
            case 11:
                drawable = ContextCompat.getDrawable(this.mContext, R.drawable.ic_bubble_audio_move_indicator_bg);
                break;
            case 5:
            case 7:
                drawable = ContextCompat.getDrawable(this.mContext, R.drawable.ic_bubble_audio_move_indicator_bg_antique);
                break;
            case 6:
            case 9:
            case 12:
            case 13:
            case 14:
                drawable = ContextCompat.getDrawable(this.mContext, R.drawable.ic_bubble_audio_move_indicator_bg_nightsky);
                break;
        }
        Bitmap bitmapDrawableToBitmap = UIUtils.drawableToBitmap(drawable);
        this.mMoveIndicatorBmp = bitmapDrawableToBitmap;
        bitmapDrawableToBitmap.setDensity(this.mDensityDpi);
    }

    protected void releaseMoveIndicatorBmp() {
        UIUtils.releaseBitmap(this.mMoveIndicatorBmp);
    }

    @Override // com.miui.richeditor.style.audio.WaveBubbleDrawable, com.miui.richeditor.style.audio.BubbleAnim
    public void stopAnim() {
        ValueAnimator valueAnimator = this.mValueAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            this.mValueAnimator.removeAllUpdateListeners();
            this.mValueAnimator.removeAllListeners();
            this.mValueAnimator = null;
        }
        ValueAnimator valueAnimator2 = this.mRecordAnimator;
        if (valueAnimator2 != null) {
            valueAnimator2.cancel();
            this.mRecordAnimator.removeAllUpdateListeners();
            this.mRecordAnimator.removeAllListeners();
            this.mRecordAnimator = null;
        }
    }

    public void pauseAnim() {
        ValueAnimator valueAnimator = this.mValueAnimator;
        if (valueAnimator != null) {
            valueAnimator.pause();
        }
    }

    public void pauseAnimAndRemoveListener() {
        ValueAnimator valueAnimator = this.mValueAnimator;
        if (valueAnimator != null) {
            valueAnimator.pause();
            this.mValueAnimator.removeAllUpdateListeners();
            this.mValueAnimator.removeAllListeners();
            this.mValueAnimator = null;
        }
    }

    @Override // com.miui.richeditor.style.audio.BubbleDrawable
    protected void drawRoundRect(Canvas canvas) {
        canvas.save();
        canvas.setDensity(this.mDensityDpi);
        if (!isDrawText()) {
            this.mRoundPath.addRoundRect(new RectF(0.0f, this.mBubblePaddingTop, this.mTmpWidth, this.mBubblePaddingTop + this.mBubbleHeight), this.mBubbleRadius, this.mBubbleRadius, Path.Direction.CW);
            canvas.drawPath(this.mRoundPath, this.mPaint);
        } else {
            super.drawRoundRect(canvas);
        }
        canvas.restore();
    }

    public void updateBaseDimen(Context context, int i) {
        float f = i;
        if (DisplayUtils.px2dip(context, f) >= 305) {
            this.mContentStart = (int) context.getResources().getDimension(R.dimen.audio_span_padding_start_and_end_level1);
            this.mContentEnd = (int) context.getResources().getDimension(R.dimen.audio_span_padding_start_and_end_level1);
            this.mTextPaddingStart = (int) context.getResources().getDimension(R.dimen.audio_span_text_padding_start_level1);
            this.mWavePaddingStart = (int) context.getResources().getDimension(R.dimen.audio_span_wave_padding_start_level1);
        } else if (DisplayUtils.px2dip(context, f) >= 240) {
            this.mContentStart = (int) context.getResources().getDimension(R.dimen.audio_span_padding_start_and_end_level2);
            this.mContentEnd = (int) context.getResources().getDimension(R.dimen.audio_span_padding_start_and_end_level2);
            this.mTextPaddingStart = (int) context.getResources().getDimension(R.dimen.audio_span_text_padding_start_level2);
            this.mWavePaddingStart = (int) context.getResources().getDimension(R.dimen.audio_span_wave_padding_start_level2);
        } else {
            this.mContentStart = (int) context.getResources().getDimension(R.dimen.audio_span_padding_start_and_end_level3);
            this.mContentEnd = (int) context.getResources().getDimension(R.dimen.audio_span_padding_start_and_end_level3);
            this.mTextPaddingStart = (int) context.getResources().getDimension(R.dimen.audio_span_text_padding_start_level3);
            this.mWavePaddingStart = (int) context.getResources().getDimension(R.dimen.audio_span_wave_padding_start_level3);
        }
        this.mTmpWidth = (int) context.getResources().getDimension(R.dimen.bubble_audio_width_temp);
        this.mBubbleHeight = (int) this.mContext.getResources().getDimension(R.dimen.bubble_audio_height);
        this.mBubblePaddingBottom = (int) context.getResources().getDimension(R.dimen.audio_span_drawable_padding_bottom);
        this.mBubblePaddingTop = (int) context.getResources().getDimension(R.dimen.audio_span_drawable_padding_top);
        this.mRectWidth = this.mHornWidth + this.mBubbleWidth;
        this.mRectHeight = this.mBubblePaddingBottom + this.mBubbleHeight + this.mBubblePaddingTop;
        this.mBubbleRadius = (int) context.getResources().getDimension(R.dimen.notes_edit_header_margin_bottom);
        this.mWaveLineWidth = (int) context.getResources().getDimension(R.dimen.bubble_audio_wave_line_width);
        this.mWaveFrame0LineHeight0 = (int) context.getResources().getDimension(R.dimen.bubble_audio_wave_frame0_line_0);
        this.mWaveFrame0LineHeight1 = (int) context.getResources().getDimension(R.dimen.bubble_audio_wave_frame0_line_1);
        this.mWaveFrame0LineHeight2 = (int) context.getResources().getDimension(R.dimen.bubble_audio_wave_frame0_line_2);
        this.mWaveFrame0LineHeight3 = (int) context.getResources().getDimension(R.dimen.bubble_audio_wave_frame0_line_3);
        this.mWaveFrame0LineHeight4 = (int) context.getResources().getDimension(R.dimen.bubble_audio_wave_frame0_line_4);
        this.mWaveFrame0LineHeight5 = (int) context.getResources().getDimension(R.dimen.bubble_audio_wave_frame0_line_5);
        this.mWaveFrame0LineHeight6 = (int) context.getResources().getDimension(R.dimen.bubble_audio_wave_frame0_line_6);
        this.mWaveLineInterval = (int) context.getResources().getDimension(R.dimen.bubble_audio_wave_line_interval);
        this.mPointerWidth = (int) context.getResources().getDimension(R.dimen.audio_span_single_wave_width);
        this.mWaveTmpFrame0LineHeight0 = (int) context.getResources().getDimension(R.dimen.bubble_wave_frame0_line_0);
        this.mWaveTmpFrame0LineHeight1 = (int) context.getResources().getDimension(R.dimen.bubble_wave_frame0_line_1);
        this.mWaveTmpFrame0LineHeight2 = (int) context.getResources().getDimension(R.dimen.bubble_wave_frame0_line_2);
    }

    @Override // com.miui.richeditor.style.audio.BubbleDrawable
    protected void drawText(Canvas canvas) {
        canvas.save();
        canvas.setDensity(this.mDensityDpi);
        Logger.INSTANCE.i("AnimBubble", "drawText canvas density" + canvas.getDensity());
        Bitmap bitmap = this.mPlayStatusBmp;
        if (bitmap == null || bitmap.isRecycled()) {
            createPlayStatusBmp();
        }
        if (isDrawText()) {
            Paint paint = new Paint(this.mPaint);
            paint.setColor(this.mStyle.contentColor);
            paint.setTextSize(this.mTextSize);
            String strRecorderTimeToFormatText = recorderTimeToFormatText(this.mIncreaseTime);
            Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
            this.mTextToRightRect = this.mContentStart + this.mPlayStatusBmp.getWidth() + this.mTextPaddingStart + ((int) paint.measureText(strRecorderTimeToFormatText));
            canvas.drawText(strRecorderTimeToFormatText, this.mContentStart + this.mPlayStatusBmp.getWidth() + this.mTextPaddingStart, ((this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - fontMetricsInt.descent) + ((fontMetricsInt.descent - fontMetricsInt.ascent) / 2), paint);
        }
        canvas.restore();
        releasePlayStatusBmp();
    }

    @Override // com.miui.richeditor.style.audio.BubbleDrawable
    protected void drawTrashIcon(Canvas canvas) {
        canvas.save();
        canvas.setDensity(this.mDensityDpi);
        Logger.INSTANCE.i("AnimBubble", "drawTrashIcon canvas density" + canvas.getDensity());
        Bitmap bitmap = this.mDisableTrashIcon;
        if (bitmap == null || bitmap.isRecycled()) {
            createDisableTrashIcon();
        }
        if (this.mTrashIcon == null || this.mTrashIcon.isRecycled()) {
            createTrashIcon();
        }
        if (isDrawText()) {
            Paint paint = new Paint(this.mPaint);
            paint.setColor(this.mStyle.contentColor);
            int width = (this.mBubbleWidth - this.mContentEnd) - this.mTrashIcon.getWidth();
            this.mTrashIconPaddingStartRect = width;
            if (this.mIsPlaying) {
                canvas.drawBitmap(this.mDisableTrashIcon, width, this.mBubblePaddingTop + ((this.mBubbleHeight - this.mTrashIcon.getHeight()) / 2), paint);
            } else {
                canvas.drawBitmap(this.mTrashIcon, width, this.mBubblePaddingTop + ((this.mBubbleHeight - this.mTrashIcon.getHeight()) / 2), paint);
            }
        }
        canvas.restore();
        releaseDisableTrashIcon();
        releaseTrashIcon();
    }

    protected void createDisableTrashIcon() {
        Drawable drawable = ContextCompat.getDrawable(this.mContext, R.drawable.ic_audio_bubble_delete);
        drawable.setTint(this.mStyle.trashUnselectedColor);
        Bitmap bitmapDrawableToBitmap = UIUtils.drawableToBitmap(drawable);
        this.mDisableTrashIcon = bitmapDrawableToBitmap;
        bitmapDrawableToBitmap.setDensity(this.mDensityDpi);
    }

    protected void releaseDisableTrashIcon() {
        UIUtils.releaseBitmap(this.mDisableTrashIcon);
    }

    @Override // com.miui.richeditor.style.audio.BubbleDrawable
    protected void createTrashIcon() {
        Drawable drawable = ContextCompat.getDrawable(this.mContext, R.drawable.ic_audio_bubble_delete);
        drawable.setTint(this.mStyle.contentColor);
        this.mTrashIcon = UIUtils.drawableToBitmap(drawable);
        this.mTrashIcon.setDensity(this.mDensityDpi);
    }

    @Override // com.miui.richeditor.style.audio.BubbleDrawable
    protected void releaseTrashIcon() {
        UIUtils.releaseBitmap(this.mTrashIcon);
    }

    public void setPlayStatus(boolean z) {
        this.mIsPlaying = z;
    }

    public void setNeedShowGesturePointer(boolean z) {
        this.mNeedShowGesturePointer = z;
    }

    @Override // com.miui.richeditor.style.audio.BubbleDrawable
    protected void drawPlayBtn(Canvas canvas) {
        canvas.save();
        canvas.setDensity(this.mDensityDpi);
        Bitmap bitmap = this.mPlayStatusBmp;
        if (bitmap == null || bitmap.isRecycled()) {
            createPlayStatusBmp();
        }
        Bitmap bitmap2 = this.mStopStatusBmp;
        if (bitmap2 == null || bitmap2.isRecycled()) {
            createStopStatusBmp();
        }
        if (isDrawText()) {
            Paint paint = new Paint(this.mPaint);
            paint.setColor(this.mStyle.contentColor);
            if (this.mIsPlaying) {
                canvas.drawBitmap(this.mStopStatusBmp, this.mContentStart, this.mBubblePaddingTop + ((this.mBubbleHeight - this.mStopStatusBmp.getHeight()) / 2), paint);
            } else {
                canvas.drawBitmap(this.mPlayStatusBmp, this.mContentStart, this.mBubblePaddingTop + ((this.mBubbleHeight - this.mPlayStatusBmp.getHeight()) / 2), paint);
            }
        }
        canvas.restore();
        releasePlayStatusBmp();
        releaseStopStatusBmp();
    }

    public int getTouchLeftPadding() {
        return this.mTextToRightRect + this.mWavePaddingStart;
    }

    public boolean isTouchWave(int i) {
        return i <= (this.mTextToRightRect + this.mWavePaddingStart) + getWaveTotalWidth() && i >= this.mTextToRightRect + this.mWavePaddingStart;
    }

    public boolean isTouchWaveLeft(int i) {
        return i <= this.mTextToRightRect + this.mWavePaddingStart;
    }

    public boolean isTouchWaveRight(int i) {
        return i >= this.mTrashIconPaddingStartRect;
    }

    public boolean isTouchPlayIcon(int i) {
        Bitmap bitmap = this.mPlayStatusBmp;
        if (bitmap == null || bitmap.isRecycled()) {
            createPlayStatusBmp();
        }
        boolean z = i <= this.mContentStart + this.mPlayStatusBmp.getWidth() && i >= this.mContentStart;
        releasePlayStatusBmp();
        return z;
    }

    public boolean isTrashIconClicked(int i) {
        if (this.mTrashIcon == null || this.mTrashIcon.isRecycled()) {
            createTrashIcon();
        }
        boolean z = i <= this.mTrashIconPaddingStartRect + this.mTrashIcon.getWidth() && i >= this.mTrashIconPaddingStartRect;
        releaseTrashIcon();
        return z;
    }

    protected void drawRecordingWave(Canvas canvas) {
        Paint paint = new Paint(this.mPaint);
        paint.setColor(this.mStyle.contentColor);
        paint.setStyle(Paint.Style.FILL);
        int i = (this.mTmpWidth - ((this.mWaveLineWidth * 10) + (this.mWaveLineInterval * 9))) / 2;
        RectF rectF = new RectF(i, (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveTmpFrame0LineHeight0 + this.mLineHeight0Offset) / 2), this.mWaveLineWidth + i, this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveTmpFrame0LineHeight0 + this.mLineHeight0Offset) / 2));
        RectF rectF2 = new RectF(this.mWaveLineWidth + this.mWaveLineInterval + i, (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveTmpFrame0LineHeight1 + this.mLineHeight1Offset) / 2), (this.mWaveLineWidth * 2) + i + this.mWaveLineInterval, this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveTmpFrame0LineHeight1 + this.mLineHeight1Offset) / 2));
        RectF rectF3 = new RectF(((this.mWaveLineWidth + this.mWaveLineInterval) * 2) + i, (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveTmpFrame0LineHeight2 + this.mLineHeight2Offset) / 2), (this.mWaveLineWidth * 3) + i + (this.mWaveLineInterval * 2), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveTmpFrame0LineHeight2 + this.mLineHeight2Offset) / 2));
        RectF rectF4 = new RectF(((this.mWaveLineWidth + this.mWaveLineInterval) * 3) + i, (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveTmpFrame0LineHeight1 + this.mLineHeight1Offset) / 2), (this.mWaveLineWidth * 4) + i + (this.mWaveLineInterval * 3), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveTmpFrame0LineHeight1 + this.mLineHeight1Offset) / 2));
        RectF rectF5 = new RectF(((this.mWaveLineWidth + this.mWaveLineInterval) * 4) + i, (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveTmpFrame0LineHeight0 + this.mLineHeight0Offset) / 2), (this.mWaveLineWidth * 5) + i + (this.mWaveLineInterval * 4), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveTmpFrame0LineHeight0 + this.mLineHeight0Offset) / 2));
        RectF rectF6 = new RectF(((this.mWaveLineWidth + this.mWaveLineInterval) * 5) + i, (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveTmpFrame0LineHeight0 + this.mLineHeight0Offset) / 2), (this.mWaveLineWidth * 6) + i + (this.mWaveLineInterval * 5), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveTmpFrame0LineHeight0 + this.mLineHeight0Offset) / 2));
        RectF rectF7 = new RectF(((this.mWaveLineWidth + this.mWaveLineInterval) * 6) + i, (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveTmpFrame0LineHeight1 + this.mLineHeight1Offset) / 2), (this.mWaveLineWidth * 7) + i + (this.mWaveLineInterval * 6), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveTmpFrame0LineHeight1 + this.mLineHeight1Offset) / 2));
        RectF rectF8 = new RectF(i + ((this.mWaveLineWidth + this.mWaveLineInterval) * 7), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveTmpFrame0LineHeight2 + this.mLineHeight2Offset) / 2), i + (this.mWaveLineWidth * 8) + (this.mWaveLineInterval * 7), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveTmpFrame0LineHeight2 + this.mLineHeight2Offset) / 2));
        RectF rectF9 = new RectF(i + ((this.mWaveLineWidth + this.mWaveLineInterval) * 8), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveTmpFrame0LineHeight1 + this.mLineHeight1Offset) / 2), i + (this.mWaveLineWidth * 9) + (this.mWaveLineInterval * 8), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveTmpFrame0LineHeight1 + this.mLineHeight1Offset) / 2));
        RectF rectF10 = new RectF(i + ((this.mWaveLineWidth + this.mWaveLineInterval) * 9), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveTmpFrame0LineHeight0 + this.mLineHeight0Offset) / 2), i + (this.mWaveLineWidth * 10) + (this.mWaveLineInterval * 9), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveTmpFrame0LineHeight0 + this.mLineHeight0Offset) / 2));
        canvas.drawRoundRect(rectF, this.mWaveLineWidth / 2, this.mWaveLineWidth / 2, paint);
        canvas.drawRoundRect(rectF2, this.mWaveLineWidth / 2, this.mWaveLineWidth / 2, paint);
        canvas.drawRoundRect(rectF3, this.mWaveLineWidth / 2, this.mWaveLineWidth / 2, paint);
        canvas.drawRoundRect(rectF4, this.mWaveLineWidth / 2, this.mWaveLineWidth / 2, paint);
        canvas.drawRoundRect(rectF5, this.mWaveLineWidth / 2, this.mWaveLineWidth / 2, paint);
        canvas.drawRoundRect(rectF6, this.mWaveLineWidth / 2, this.mWaveLineWidth / 2, paint);
        canvas.drawRoundRect(rectF7, this.mWaveLineWidth / 2, this.mWaveLineWidth / 2, paint);
        canvas.drawRoundRect(rectF8, this.mWaveLineWidth / 2, this.mWaveLineWidth / 2, paint);
        canvas.drawRoundRect(rectF9, this.mWaveLineWidth / 2, this.mWaveLineWidth / 2, paint);
        canvas.drawRoundRect(rectF10, this.mWaveLineWidth / 2, this.mWaveLineWidth / 2, paint);
    }

    @Override // com.miui.richeditor.style.audio.WaveBubbleDrawable, com.miui.richeditor.style.audio.BubbleDrawable
    protected void drawWave(Canvas canvas) {
        Canvas canvas2;
        int i;
        Paint paint;
        int i2;
        Paint paint2;
        canvas.save();
        canvas.setDensity(this.mDensityDpi);
        if (!isDrawText()) {
            drawRecordingWave(canvas);
            onUpdateTheme(this.mThemeId);
            return;
        }
        Bitmap bitmap = this.mMoveIndicatorBmp;
        if (bitmap == null || bitmap.isRecycled()) {
            createMoveIndicatorBmp();
        }
        if (isDrawWave()) {
            Paint paint3 = new Paint(this.mPaint);
            paint3.setColor(this.mStyle.contentColor);
            paint3.setStyle(Paint.Style.FILL);
            this.mMarginLeft = this.mTextToRightRect + this.mWavePaddingStart;
            if (this.mNeedShowGesturePointer) {
                Paint paint4 = new Paint(this.mPaint);
                paint4.setColor(this.mStyle.contentColor);
                paint4.setStyle(Paint.Style.FILL);
                canvas.drawRoundRect(new RectF(this.mMarginLeft + this.mPointerOffset, ((int) this.mContext.getResources().getDimension(R.dimen.audio_span_wave_play_indicator_margin_top)) + this.mBubblePaddingTop, this.mMarginLeft + this.mPointerWidth + this.mPointerOffset, (this.mBubbleHeight + this.mBubblePaddingTop) - ((int) this.mContext.getResources().getDimension(R.dimen.ic_20px_size))), this.mWaveLineWidth / 2, this.mWaveLineWidth / 2, paint4);
                canvas.drawBitmap(this.mMoveIndicatorBmp, ((this.mMarginLeft + this.mPointerOffset) + (this.mPointerWidth * 0.5f)) - ((int) this.mContext.getResources().getDimension(R.dimen.ic_11px_size)), (this.mBubbleHeight + this.mBubblePaddingTop) - ((int) this.mContext.getResources().getDimension(R.dimen.ic_25px_size)), paint3);
                i = this.mPointerOffset / (this.mWaveLineWidth + this.mWaveLineInterval);
            } else {
                i = -1;
            }
            ArrayList arrayList = new ArrayList();
            if (DisplayUtils.px2dip(this.mContext, this.mBubbleWidth) >= 280) {
                RectF rectF = new RectF(this.mMarginLeft, (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight0 + this.mLineHeight0Offset) / 2), this.mMarginLeft + this.mWaveLineWidth, this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight0 + this.mLineHeight0Offset) / 2));
                RectF rectF2 = new RectF(this.mMarginLeft + this.mWaveLineWidth + this.mWaveLineInterval, (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight0 + this.mLineHeight1Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 2) + this.mWaveLineInterval, this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight0 + this.mLineHeight1Offset) / 2));
                RectF rectF3 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 2) + (this.mWaveLineInterval * 2), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight1 + this.mLineHeight2Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 3) + (this.mWaveLineInterval * 2), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight1 + this.mLineHeight2Offset) / 2));
                RectF rectF4 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 3) + (this.mWaveLineInterval * 3), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight0 + this.mLineHeight1Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 4) + (this.mWaveLineInterval * 3), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight0 + this.mLineHeight1Offset) / 2));
                RectF rectF5 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 4) + (this.mWaveLineInterval * 4), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight0 + this.mLineHeight0Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 5) + (this.mWaveLineInterval * 4), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight0 + this.mLineHeight0Offset) / 2));
                RectF rectF6 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 5) + (this.mWaveLineInterval * 5), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight1 + this.mLineHeight0Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 6) + (this.mWaveLineInterval * 5), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight1 + this.mLineHeight0Offset) / 2));
                RectF rectF7 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 6) + (this.mWaveLineInterval * 6), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight2 + this.mLineHeight1Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 7) + (this.mWaveLineInterval * 6), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight2 + this.mLineHeight1Offset) / 2));
                RectF rectF8 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 7) + (this.mWaveLineInterval * 7), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight3 + this.mLineHeight2Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 8) + (this.mWaveLineInterval * 7), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight3 + this.mLineHeight2Offset) / 2));
                paint = paint3;
                RectF rectF9 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 8) + (this.mWaveLineInterval * 8), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight2 + this.mLineHeight1Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 9) + (this.mWaveLineInterval * 8), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight2 + this.mLineHeight1Offset) / 2));
                i2 = i;
                RectF rectF10 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 9) + (this.mWaveLineInterval * 9), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight1 + this.mLineHeight0Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 10) + (this.mWaveLineInterval * 9), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight1 + this.mLineHeight0Offset) / 2));
                RectF rectF11 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 10) + (this.mWaveLineInterval * 10), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight0 + this.mLineHeight0Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 11) + (this.mWaveLineInterval * 10), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight0 + this.mLineHeight0Offset) / 2));
                RectF rectF12 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 11) + (this.mWaveLineInterval * 11), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight0 + this.mLineHeight1Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 12) + (this.mWaveLineInterval * 11), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight0 + this.mLineHeight1Offset) / 2));
                RectF rectF13 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 12) + (this.mWaveLineInterval * 12), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight1 + this.mLineHeight2Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 13) + (this.mWaveLineInterval * 12), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight1 + this.mLineHeight2Offset) / 2));
                RectF rectF14 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 13) + (this.mWaveLineInterval * 13), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight2 + this.mLineHeight1Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 14) + (this.mWaveLineInterval * 13), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight2 + this.mLineHeight1Offset) / 2));
                RectF rectF15 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 14) + (this.mWaveLineInterval * 14), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight6 + this.mLineHeight0Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 15) + (this.mWaveLineInterval * 14), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight6 + this.mLineHeight0Offset) / 2));
                RectF rectF16 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 15) + (this.mWaveLineInterval * 15), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight4 + this.mLineHeight0Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 16) + (this.mWaveLineInterval * 15), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight4 + this.mLineHeight0Offset) / 2));
                RectF rectF17 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 16) + (this.mWaveLineInterval * 16), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight6 + this.mLineHeight1Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 17) + (this.mWaveLineInterval * 16), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight6 + this.mLineHeight1Offset) / 2));
                RectF rectF18 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 17) + (this.mWaveLineInterval * 17), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight4 + this.mLineHeight2Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 18) + (this.mWaveLineInterval * 17), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight4 + this.mLineHeight2Offset) / 2));
                RectF rectF19 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 18) + (this.mWaveLineInterval * 18), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight6 + this.mLineHeight1Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 19) + (this.mWaveLineInterval * 18), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight6 + this.mLineHeight1Offset) / 2));
                RectF rectF20 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 19) + (this.mWaveLineInterval * 19), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight2 + this.mLineHeight0Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 20) + (this.mWaveLineInterval * 19), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight2 + this.mLineHeight0Offset) / 2));
                RectF rectF21 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 20) + (this.mWaveLineInterval * 20), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight1 + this.mLineHeight0Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 21) + (this.mWaveLineInterval * 20), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight1 + this.mLineHeight0Offset) / 2));
                RectF rectF22 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 21) + (this.mWaveLineInterval * 21), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight0 + this.mLineHeight1Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 22) + (this.mWaveLineInterval * 21), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight0 + this.mLineHeight1Offset) / 2));
                RectF rectF23 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 22) + (this.mWaveLineInterval * 22), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight0 + this.mLineHeight2Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 23) + (this.mWaveLineInterval * 22), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight0 + this.mLineHeight2Offset) / 2));
                RectF rectF24 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 23) + (this.mWaveLineInterval * 23), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight1 + this.mLineHeight1Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 24) + (this.mWaveLineInterval * 23), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight1 + this.mLineHeight1Offset) / 2));
                RectF rectF25 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 24) + (this.mWaveLineInterval * 24), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight2 + this.mLineHeight0Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 25) + (this.mWaveLineInterval * 24), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight2 + this.mLineHeight0Offset) / 2));
                RectF rectF26 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 25) + (this.mWaveLineInterval * 25), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight3 + this.mLineHeight0Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 26) + (this.mWaveLineInterval * 25), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight3 + this.mLineHeight0Offset) / 2));
                RectF rectF27 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 26) + (this.mWaveLineInterval * 26), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight2 + this.mLineHeight1Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 27) + (this.mWaveLineInterval * 26), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight2 + this.mLineHeight1Offset) / 2));
                RectF rectF28 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 27) + (this.mWaveLineInterval * 27), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight1 + this.mLineHeight2Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 28) + (this.mWaveLineInterval * 27), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight1 + this.mLineHeight2Offset) / 2));
                RectF rectF29 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 28) + (this.mWaveLineInterval * 28), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight0 + this.mLineHeight1Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 29) + (this.mWaveLineInterval * 28), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight0 + this.mLineHeight1Offset) / 2));
                RectF rectF30 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 29) + (this.mWaveLineInterval * 29), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight0 + this.mLineHeight0Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 30) + (this.mWaveLineInterval * 29), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight0 + this.mLineHeight0Offset) / 2));
                RectF rectF31 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 30) + (this.mWaveLineInterval * 30), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight1 + this.mLineHeight0Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 31) + (this.mWaveLineInterval * 30), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight1 + this.mLineHeight0Offset) / 2));
                RectF rectF32 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 31) + (this.mWaveLineInterval * 31), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight0 + this.mLineHeight0Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 32) + (this.mWaveLineInterval * 31), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight0 + this.mLineHeight0Offset) / 2));
                RectF rectF33 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 32) + (this.mWaveLineInterval * 32), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight0 + this.mLineHeight0Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 33) + (this.mWaveLineInterval * 32), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight0 + this.mLineHeight0Offset) / 2));
                arrayList.add(rectF);
                arrayList.add(rectF2);
                arrayList.add(rectF3);
                arrayList.add(rectF4);
                arrayList.add(rectF5);
                arrayList.add(rectF6);
                arrayList.add(rectF7);
                arrayList.add(rectF8);
                arrayList.add(rectF9);
                arrayList.add(rectF10);
                arrayList.add(rectF11);
                arrayList.add(rectF12);
                arrayList.add(rectF13);
                arrayList.add(rectF14);
                arrayList.add(rectF15);
                arrayList.add(rectF16);
                arrayList.add(rectF17);
                arrayList.add(rectF18);
                arrayList.add(rectF19);
                arrayList.add(rectF20);
                arrayList.add(rectF21);
                arrayList.add(rectF22);
                arrayList.add(rectF23);
                arrayList.add(rectF24);
                arrayList.add(rectF25);
                arrayList.add(rectF26);
                arrayList.add(rectF27);
                arrayList.add(rectF28);
                arrayList.add(rectF29);
                arrayList.add(rectF30);
                arrayList.add(rectF31);
                arrayList.add(rectF32);
                arrayList.add(rectF33);
            } else {
                paint = paint3;
                i2 = i;
                if (DisplayUtils.px2dip(this.mContext, this.mBubbleWidth) >= 210) {
                    RectF rectF34 = new RectF(this.mMarginLeft, (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight0 + this.mLineHeight0Offset) / 2), this.mMarginLeft + this.mWaveLineWidth, this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight0 + this.mLineHeight0Offset) / 2));
                    RectF rectF35 = new RectF(this.mMarginLeft + this.mWaveLineWidth + this.mWaveLineInterval, (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight0 + this.mLineHeight1Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 2) + this.mWaveLineInterval, this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight0 + this.mLineHeight1Offset) / 2));
                    RectF rectF36 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 2) + (this.mWaveLineInterval * 2), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight1 + this.mLineHeight2Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 3) + (this.mWaveLineInterval * 2), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight1 + this.mLineHeight2Offset) / 2));
                    RectF rectF37 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 3) + (this.mWaveLineInterval * 3), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight5 + this.mLineHeight1Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 4) + (this.mWaveLineInterval * 3), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight5 + this.mLineHeight1Offset) / 2));
                    RectF rectF38 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 4) + (this.mWaveLineInterval * 4), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight1 + this.mLineHeight0Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 5) + (this.mWaveLineInterval * 4), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight1 + this.mLineHeight0Offset) / 2));
                    RectF rectF39 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 5) + (this.mWaveLineInterval * 5), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight0 + this.mLineHeight0Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 6) + (this.mWaveLineInterval * 5), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight0 + this.mLineHeight0Offset) / 2));
                    RectF rectF40 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 6) + (this.mWaveLineInterval * 6), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight1 + this.mLineHeight1Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 7) + (this.mWaveLineInterval * 6), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight1 + this.mLineHeight1Offset) / 2));
                    RectF rectF41 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 7) + (this.mWaveLineInterval * 7), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight5 + this.mLineHeight2Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 8) + (this.mWaveLineInterval * 7), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight5 + this.mLineHeight2Offset) / 2));
                    RectF rectF42 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 8) + (this.mWaveLineInterval * 8), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight6 + this.mLineHeight1Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 9) + (this.mWaveLineInterval * 8), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight6 + this.mLineHeight1Offset) / 2));
                    RectF rectF43 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 9) + (this.mWaveLineInterval * 9), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight4 + this.mLineHeight0Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 10) + (this.mWaveLineInterval * 9), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight4 + this.mLineHeight0Offset) / 2));
                    RectF rectF44 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 10) + (this.mWaveLineInterval * 10), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight6 + this.mLineHeight0Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 11) + (this.mWaveLineInterval * 10), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight6 + this.mLineHeight0Offset) / 2));
                    RectF rectF45 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 11) + (this.mWaveLineInterval * 11), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight4 + this.mLineHeight1Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 12) + (this.mWaveLineInterval * 11), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight4 + this.mLineHeight1Offset) / 2));
                    RectF rectF46 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 12) + (this.mWaveLineInterval * 12), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight6 + this.mLineHeight2Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 13) + (this.mWaveLineInterval * 12), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight6 + this.mLineHeight2Offset) / 2));
                    RectF rectF47 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 13) + (this.mWaveLineInterval * 13), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight5 + this.mLineHeight1Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 14) + (this.mWaveLineInterval * 13), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight5 + this.mLineHeight1Offset) / 2));
                    RectF rectF48 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 14) + (this.mWaveLineInterval * 14), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight1 + this.mLineHeight0Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 15) + (this.mWaveLineInterval * 14), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight1 + this.mLineHeight0Offset) / 2));
                    RectF rectF49 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 15) + (this.mWaveLineInterval * 15), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight0 + this.mLineHeight0Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 16) + (this.mWaveLineInterval * 15), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight0 + this.mLineHeight0Offset) / 2));
                    RectF rectF50 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 16) + (this.mWaveLineInterval * 16), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight1 + this.mLineHeight1Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 17) + (this.mWaveLineInterval * 16), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight1 + this.mLineHeight1Offset) / 2));
                    RectF rectF51 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 17) + (this.mWaveLineInterval * 17), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight5 + this.mLineHeight2Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 18) + (this.mWaveLineInterval * 17), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight5 + this.mLineHeight2Offset) / 2));
                    RectF rectF52 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 18) + (this.mWaveLineInterval * 18), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight1 + this.mLineHeight1Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 19) + (this.mWaveLineInterval * 18), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight1 + this.mLineHeight1Offset) / 2));
                    RectF rectF53 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 19) + (this.mWaveLineInterval * 19), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight0 + this.mLineHeight0Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 20) + (this.mWaveLineInterval * 19), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight0 + this.mLineHeight0Offset) / 2));
                    RectF rectF54 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 20) + (this.mWaveLineInterval * 20), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight0 + this.mLineHeight0Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 21) + (this.mWaveLineInterval * 20), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight0 + this.mLineHeight0Offset) / 2));
                    arrayList.add(rectF34);
                    arrayList.add(rectF35);
                    arrayList.add(rectF36);
                    arrayList.add(rectF37);
                    arrayList.add(rectF38);
                    arrayList.add(rectF39);
                    arrayList.add(rectF40);
                    arrayList.add(rectF41);
                    arrayList.add(rectF42);
                    arrayList.add(rectF43);
                    arrayList.add(rectF44);
                    arrayList.add(rectF45);
                    arrayList.add(rectF46);
                    arrayList.add(rectF47);
                    arrayList.add(rectF48);
                    arrayList.add(rectF49);
                    arrayList.add(rectF50);
                    arrayList.add(rectF51);
                    arrayList.add(rectF52);
                    arrayList.add(rectF53);
                    arrayList.add(rectF54);
                } else {
                    RectF rectF55 = new RectF(this.mMarginLeft, (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight1 + this.mLineHeight0Offset) / 2), this.mMarginLeft + this.mWaveLineWidth, this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight1 + this.mLineHeight0Offset) / 2));
                    RectF rectF56 = new RectF(this.mMarginLeft + this.mWaveLineWidth + this.mWaveLineInterval, (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight1 + this.mLineHeight1Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 2) + this.mWaveLineInterval, this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight1 + this.mLineHeight1Offset) / 2));
                    RectF rectF57 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 2) + (this.mWaveLineInterval * 2), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight5 + this.mLineHeight2Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 3) + (this.mWaveLineInterval * 2), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight5 + this.mLineHeight2Offset) / 2));
                    RectF rectF58 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 3) + (this.mWaveLineInterval * 3), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight6 + this.mLineHeight1Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 4) + (this.mWaveLineInterval * 3), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight6 + this.mLineHeight1Offset) / 2));
                    RectF rectF59 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 4) + (this.mWaveLineInterval * 4), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight4 + this.mLineHeight0Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 5) + (this.mWaveLineInterval * 4), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight4 + this.mLineHeight0Offset) / 2));
                    RectF rectF60 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 5) + (this.mWaveLineInterval * 5), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight6 + this.mLineHeight0Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 6) + (this.mWaveLineInterval * 5), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight6 + this.mLineHeight0Offset) / 2));
                    RectF rectF61 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 6) + (this.mWaveLineInterval * 6), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight4 + this.mLineHeight1Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 7) + (this.mWaveLineInterval * 6), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight4 + this.mLineHeight1Offset) / 2));
                    RectF rectF62 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 7) + (this.mWaveLineInterval * 7), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight6 + this.mLineHeight2Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 8) + (this.mWaveLineInterval * 7), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight6 + this.mLineHeight2Offset) / 2));
                    RectF rectF63 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 8) + (this.mWaveLineInterval * 8), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight5 + this.mLineHeight1Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 9) + (this.mWaveLineInterval * 8), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight5 + this.mLineHeight1Offset) / 2));
                    RectF rectF64 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 9) + (this.mWaveLineInterval * 9), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight1 + this.mLineHeight0Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 10) + (this.mWaveLineInterval * 9), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight1 + this.mLineHeight0Offset) / 2));
                    RectF rectF65 = new RectF(this.mMarginLeft + (this.mWaveLineWidth * 10) + (this.mWaveLineInterval * 10), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveFrame0LineHeight1 + this.mLineHeight0Offset) / 2), this.mMarginLeft + (this.mWaveLineWidth * 11) + (this.mWaveLineInterval * 10), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveFrame0LineHeight1 + this.mLineHeight0Offset) / 2));
                    arrayList.add(rectF55);
                    arrayList.add(rectF56);
                    arrayList.add(rectF57);
                    arrayList.add(rectF58);
                    arrayList.add(rectF59);
                    arrayList.add(rectF60);
                    arrayList.add(rectF61);
                    arrayList.add(rectF62);
                    arrayList.add(rectF63);
                    arrayList.add(rectF64);
                    arrayList.add(rectF65);
                }
            }
            int i3 = 0;
            while (i3 < arrayList.size()) {
                int i4 = i2;
                if (i3 <= i4 || i4 == -1) {
                    paint2 = paint;
                } else {
                    paint2 = paint;
                    paint2.setColor(this.mStyle.waveUnselectedColor);
                }
                canvas.drawRoundRect((RectF) arrayList.get(i3), this.mWaveLineWidth / 2, this.mWaveLineWidth / 2, paint2);
                i3++;
                i2 = i4;
                paint = paint2;
            }
            canvas2 = canvas;
        } else {
            canvas2 = canvas;
        }
        canvas2.restore();
        releaseMoveIndicatorBmp();
    }

    @Override // com.miui.richeditor.style.audio.WaveBubbleDrawable, com.miui.richeditor.style.audio.BubbleAnim
    public void startIncreaseAnim(int i, final BaseAudioPlayListener baseAudioPlayListener) {
        this.mIncreaseTime = i;
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(0, this.mIncreaseTime);
        this.mValueAnimator = valueAnimatorOfInt;
        valueAnimatorOfInt.setDuration(300L);
        this.mValueAnimator.setRepeatCount(0);
        this.mValueAnimator.setInterpolator(new SineEaseInOutInterpolator());
        this.mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.richeditor.style.audio.AnimAudioSpanDrawable.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int iIntValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                if (iIntValue == AnimAudioSpanDrawable.this.mIncreaseTime) {
                    AnimAudioSpanDrawable.this.onIncreaseAnimEnd();
                } else {
                    AnimAudioSpanDrawable.this.updateDimension(iIntValue);
                    AnimAudioSpanDrawable animAudioSpanDrawable = AnimAudioSpanDrawable.this;
                    animAudioSpanDrawable.setBounds(0, 0, animAudioSpanDrawable.mRectWidth, AnimAudioSpanDrawable.this.mRectHeight);
                }
                baseAudioPlayListener.onAnimationUpdate(valueAnimator);
            }
        });
        this.mValueAnimator.addListener(baseAudioPlayListener);
        this.mValueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.miui.richeditor.style.audio.AnimAudioSpanDrawable.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                super.onAnimationCancel(animator);
                AnimAudioSpanDrawable.this.onIncreaseAnimEnd();
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                AnimAudioSpanDrawable.this.onIncreaseAnimEnd();
            }
        });
        this.mValueAnimator.start();
    }

    public void startPlayRecordAnim(final BaseAudioPlayListener baseAudioPlayListener) {
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mRecordAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.setRepeatCount(-1);
        this.mRecordAnimator.setRepeatMode(2);
        this.mRecordAnimator.setInterpolator(new SineEaseInOutInterpolator());
        this.mRecordAnimator.setDuration(300L);
        this.mRecordAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.richeditor.style.audio.AnimAudioSpanDrawable.3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                AnimAudioSpanDrawable.this.updateRecordAnim(((Float) valueAnimator.getAnimatedValue()).floatValue());
                baseAudioPlayListener.onAnimationUpdate(valueAnimator);
            }
        });
        this.mRecordAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.miui.richeditor.style.audio.AnimAudioSpanDrawable.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                AnimAudioSpanDrawable.this.onRecordAnimEnd();
                baseAudioPlayListener.onAnimationCancel(animator);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                super.onAnimationCancel(animator);
                AnimAudioSpanDrawable.this.onRecordAnimEnd();
                baseAudioPlayListener.onAnimationEnd(animator);
            }
        });
        this.mRecordAnimator.start();
    }

    public void cancelRecordAnim() {
        ValueAnimator valueAnimator = this.mRecordAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            this.mRecordAnimator.removeAllListeners();
            this.mRecordAnimator.removeAllUpdateListeners();
            this.mRecordAnimator = null;
        }
    }

    @Override // com.miui.richeditor.style.audio.WaveBubbleDrawable, com.miui.richeditor.style.audio.BubbleAnim
    public void startPlayAnim(final BaseAudioPlayListener baseAudioPlayListener) {
        ValueAnimator valueAnimator = this.mValueAnimator;
        if (valueAnimator != null) {
            valueAnimator.pause();
            this.mValueAnimator.removeAllUpdateListeners();
            this.mValueAnimator.removeAllListeners();
            this.mValueAnimator = null;
        }
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(this.mPointerOffset, getWaveTotalWidth());
        this.mValueAnimator = valueAnimatorOfInt;
        valueAnimatorOfInt.setDuration(this.mIncreaseTime * 1000);
        this.mValueAnimator.setRepeatCount(0);
        this.mValueAnimator.setInterpolator(new LinearInterpolator());
        final int[] iArr = {this.mPointerOffset - 1};
        this.mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.richeditor.style.audio.AnimAudioSpanDrawable.5
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                int iIntValue = ((Integer) valueAnimator2.getAnimatedValue()).intValue();
                int[] iArr2 = iArr;
                if (iArr2[0] == iIntValue) {
                    return;
                }
                iArr2[0] = iIntValue;
                AnimAudioSpanDrawable.this.mPointerOffset = iIntValue;
                baseAudioPlayListener.onAnimationUpdate(valueAnimator2);
            }
        });
        this.mValueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.miui.richeditor.style.audio.AnimAudioSpanDrawable.6
            boolean isCanceled = false;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                super.onAnimationCancel(animator);
                this.isCanceled = true;
                AnimAudioSpanDrawable.this.setPlayStatus(false);
                AnimAudioSpanDrawable.this.setNeedShowGesturePointer(false);
                AnimAudioSpanDrawable.this.onPlayAnimEnd();
                baseAudioPlayListener.onAnimationCancel(animator);
                baseAudioPlayListener.onAnimStateChanged(false);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                if (this.isCanceled) {
                    return;
                }
                if (AnimAudioSpanDrawable.this.mPointerOffset >= AnimAudioSpanDrawable.this.getWaveTotalWidth()) {
                    AnimAudioSpanDrawable.this.setPlayStatus(false);
                    AnimAudioSpanDrawable.this.setNeedShowGesturePointer(false);
                    AnimAudioSpanDrawable.this.onPlayAnimEnd();
                    baseAudioPlayListener.onAnimStateChanged(false);
                    baseAudioPlayListener.onAnimationEnd(animator);
                    return;
                }
                AnimAudioSpanDrawable.this.setNeedShowGesturePointer(true);
                baseAudioPlayListener.onAnimStateChanged(true);
            }
        });
        this.mValueAnimator.start();
    }

    public void restartPlayAnim(BaseAudioPlayListener baseAudioPlayListener) {
        ValueAnimator valueAnimator = this.mValueAnimator;
        if (valueAnimator == null) {
            return;
        }
        if (valueAnimator.isPaused()) {
            this.mValueAnimator.resume();
        } else {
            this.mValueAnimator.start();
        }
    }

    public void startPlayAnimAfterMoveRestart(final BaseAudioPlayListener baseAudioPlayListener, int i) {
        ValueAnimator valueAnimator = this.mValueAnimator;
        if (valueAnimator != null) {
            valueAnimator.pause();
            this.mValueAnimator.removeAllUpdateListeners();
            this.mValueAnimator.removeAllListeners();
            this.mValueAnimator = null;
        }
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(0, getWaveTotalWidth());
        this.mValueAnimator = valueAnimatorOfInt;
        valueAnimatorOfInt.setDuration(i);
        this.mValueAnimator.setRepeatCount(0);
        this.mValueAnimator.setInterpolator(new LinearInterpolator());
        this.mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.richeditor.style.audio.AnimAudioSpanDrawable.7
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                AnimAudioSpanDrawable.this.updateOffset(((Integer) valueAnimator2.getAnimatedValue()).intValue());
                baseAudioPlayListener.onAnimationUpdate(valueAnimator2);
            }
        });
        this.mValueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.miui.richeditor.style.audio.AnimAudioSpanDrawable.8
            boolean isCanceled = false;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                super.onAnimationCancel(animator);
                this.isCanceled = true;
                AnimAudioSpanDrawable.this.setPlayStatus(false);
                AnimAudioSpanDrawable.this.setNeedShowGesturePointer(false);
                AnimAudioSpanDrawable.this.onPlayAnimEnd();
                baseAudioPlayListener.onAnimationCancel(animator);
                baseAudioPlayListener.onAnimStateChanged(false);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                if (this.isCanceled) {
                    return;
                }
                if (AnimAudioSpanDrawable.this.mPointerOffset >= AnimAudioSpanDrawable.this.getWaveTotalWidth()) {
                    AnimAudioSpanDrawable.this.setPlayStatus(false);
                    AnimAudioSpanDrawable.this.setNeedShowGesturePointer(false);
                    AnimAudioSpanDrawable.this.onPlayAnimEnd();
                    baseAudioPlayListener.onAnimStateChanged(false);
                    baseAudioPlayListener.onAnimationEnd(animator);
                    return;
                }
                AnimAudioSpanDrawable.this.setNeedShowGesturePointer(true);
                baseAudioPlayListener.onAnimStateChanged(true);
            }
        });
        this.mValueAnimator.start();
    }

    public void startPlayAnimAfterMove(final BaseAudioPlayListener baseAudioPlayListener, int i) {
        ValueAnimator valueAnimator = this.mValueAnimator;
        if (valueAnimator != null) {
            valueAnimator.pause();
            this.mValueAnimator.removeAllUpdateListeners();
            this.mValueAnimator.removeAllListeners();
            this.mValueAnimator = null;
        }
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(this.mPointerOffset, getWaveTotalWidth());
        this.mValueAnimator = valueAnimatorOfInt;
        valueAnimatorOfInt.setDuration(i);
        this.mValueAnimator.setRepeatCount(0);
        this.mValueAnimator.setInterpolator(new LinearInterpolator());
        this.mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.richeditor.style.audio.AnimAudioSpanDrawable.9
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                AnimAudioSpanDrawable.this.updateOffset(((Integer) valueAnimator2.getAnimatedValue()).intValue());
                baseAudioPlayListener.onAnimationUpdate(valueAnimator2);
            }
        });
        this.mValueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.miui.richeditor.style.audio.AnimAudioSpanDrawable.10
            boolean isCanceled = false;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                super.onAnimationCancel(animator);
                this.isCanceled = true;
                AnimAudioSpanDrawable.this.setPlayStatus(false);
                AnimAudioSpanDrawable.this.setNeedShowGesturePointer(false);
                AnimAudioSpanDrawable.this.onPlayAnimEnd();
                baseAudioPlayListener.onAnimationCancel(animator);
                baseAudioPlayListener.onAnimStateChanged(false);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                if (this.isCanceled) {
                    return;
                }
                if (AnimAudioSpanDrawable.this.mPointerOffset >= AnimAudioSpanDrawable.this.getWaveTotalWidth()) {
                    AnimAudioSpanDrawable.this.setPlayStatus(false);
                    AnimAudioSpanDrawable.this.setNeedShowGesturePointer(false);
                    AnimAudioSpanDrawable.this.onPlayAnimEnd();
                    baseAudioPlayListener.onAnimStateChanged(false);
                    baseAudioPlayListener.onAnimationEnd(animator);
                    return;
                }
                baseAudioPlayListener.onAnimStateChanged(true);
                AnimAudioSpanDrawable.this.mNeedShowGesturePointer = true;
            }
        });
        this.mValueAnimator.start();
    }

    public void updateOffset(int i) {
        this.mPointerOffset = i;
        invalidateSelf();
    }

    public void updateRecordAnim(float f) {
        this.mLineHeight0Offset = (int) ((this.mWaveFrame1LineHeight0 - this.mWaveFrame0LineHeight0) * f);
        this.mLineHeight1Offset = (int) ((this.mWaveFrame1LineHeight1 - this.mWaveFrame0LineHeight1) * f);
        this.mLineHeight2Offset = (int) ((this.mWaveFrame1LineHeight2 - this.mWaveFrame0LineHeight2) * f);
    }

    public void onRecordAnimEnd() {
        updateRecordAnim(0.0f);
    }

    public int getWaveTotalWidth() {
        int i;
        int i2;
        if (DisplayUtils.px2dip(this.mContext, this.mBubbleWidth) >= 290) {
            i = this.mWaveLineWidth * 33;
            i2 = this.mWaveLineInterval * 32;
        } else if (DisplayUtils.px2dip(this.mContext, this.mBubbleWidth) >= 240) {
            i = this.mWaveLineWidth * 21;
            i2 = this.mWaveLineInterval * 20;
        } else {
            i = this.mWaveLineWidth * 11;
            i2 = this.mWaveLineInterval * 10;
        }
        return i + i2;
    }

    @Override // com.miui.richeditor.style.audio.WaveBubbleDrawable, com.miui.richeditor.style.audio.BubbleAnim
    public void onIncreaseAnimEnd() {
        if (!isDrawText()) {
            this.mDrawType = 0;
        }
        updateDimension(this.mIncreaseTime);
        setBounds(0, 0, this.mRectWidth, this.mRectHeight);
    }

    @Override // com.miui.richeditor.style.audio.WaveBubbleDrawable, com.miui.richeditor.style.audio.BubbleAnim
    public void onPlayAnimEnd() {
        updateOffset(0);
        invalidateSelf();
    }
}
