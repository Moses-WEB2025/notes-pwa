package com.miui.pad.richeditor.style.audio;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.richeditor.style.audio.AnimAudioSpanDrawable;

/* JADX INFO: loaded from: classes4.dex */
public class PadAnimBubbleDrawable extends AnimAudioSpanDrawable {
    private final boolean USE_MEETING_AUDIO_STYLE;
    private int mRecordingTime;
    private int mRecordingTimeMarginEnd;
    private int mRecordingTimeMarginStart;
    private int mRecordingTimeTextToRightRect;
    private int mRecordingTimeTextZoneWidth;
    private Bitmap mXiaoaiIcon;
    private int mXiaoaiIconStart;

    public PadAnimBubbleDrawable(int i, int i2, int i3, int i4) {
        super(i, i2, i3, i4);
        this.USE_MEETING_AUDIO_STYLE = true;
    }

    @Override // com.miui.richeditor.style.audio.AnimAudioSpanDrawable, com.miui.richeditor.style.audio.BubbleDrawable
    public void onUpdateTheme(int i) {
        this.mThemeId = i;
        createDisableTrashIcon();
        createTrashIcon();
        this.mPaint.setColor(this.mStyle.bgColor);
        createPlayStatusBmp();
        createStopStatusBmp();
        createMoveIndicatorBmp();
        createXiaoaiIcon();
    }

    @Override // com.miui.richeditor.style.audio.AnimAudioSpanDrawable
    protected void createPlayStatusBmp() {
        this.mPlayStatusBmp = UIUtils.drawableToBitmap(ContextCompat.getDrawable(this.mContext, R.drawable.pad_ic_bubble_play_status_bg_default));
        this.mPlayStatusBmp.setDensity(this.mDensityDpi);
    }

    @Override // com.miui.richeditor.style.audio.AnimAudioSpanDrawable
    protected void releasePlayStatusBmp() {
        UIUtils.releaseBitmap(this.mPlayStatusBmp);
    }

    @Override // com.miui.richeditor.style.audio.AnimAudioSpanDrawable
    protected void createStopStatusBmp() {
        this.mStopStatusBmp = UIUtils.drawableToBitmap(ContextCompat.getDrawable(this.mContext, R.drawable.pad_ic_bubble_stop_status_bg_default));
        this.mStopStatusBmp.setDensity(this.mDensityDpi);
    }

    @Override // com.miui.richeditor.style.audio.AnimAudioSpanDrawable
    protected void releaseStopStatusBmp() {
        UIUtils.releaseBitmap(this.mStopStatusBmp);
    }

    @Override // com.miui.richeditor.style.audio.AnimAudioSpanDrawable
    protected void createMoveIndicatorBmp() {
        this.mMoveIndicatorBmp = UIUtils.drawableToBitmap(ContextCompat.getDrawable(this.mContext, R.drawable.ic_bubble_audio_move_indicator_bg_default));
        this.mMoveIndicatorBmp.setDensity(this.mDensityDpi);
    }

    @Override // com.miui.richeditor.style.audio.AnimAudioSpanDrawable
    protected void releaseMoveIndicatorBmp() {
        UIUtils.releaseBitmap(this.mMoveIndicatorBmp);
    }

    private void createXiaoaiIcon() {
        Bitmap bitmapDrawableToBitmap = UIUtils.drawableToBitmap(ContextCompat.getDrawable(this.mContext, R.drawable.pad_ic_xiaoai));
        this.mXiaoaiIcon = bitmapDrawableToBitmap;
        bitmapDrawableToBitmap.setDensity(this.mDensityDpi);
    }

    private void releaseXiaoaiIcon() {
        UIUtils.releaseBitmap(this.mXiaoaiIcon);
    }

    @Override // com.miui.richeditor.style.audio.AnimAudioSpanDrawable
    public void updateBaseDimen(Context context, int i) {
        this.mContentStart = (int) context.getResources().getDimension(R.dimen.pad_audio_span_padding_start_and_end);
        this.mContentEnd = (int) context.getResources().getDimension(R.dimen.pad_audio_span_padding_end);
        this.mTextPaddingStart = (int) context.getResources().getDimension(R.dimen.pad_audio_span_text_padding_start);
        this.mWavePaddingStart = (int) context.getResources().getDimension(R.dimen.pad_audio_span_wave_padding_start);
        this.mTmpWidth = (int) context.getResources().getDimension(R.dimen.pad_meeting_audio_bubble_width_temp);
        this.mXiaoaiIconStart = (int) context.getResources().getDimension(R.dimen.pad_meeting_audio_span_xiaoai_icon_margin_start);
        this.mRecordingTimeMarginStart = (int) context.getResources().getDimension(R.dimen.pad_meeting_audio_span_recording_time_margin_start);
        this.mRecordingTimeMarginEnd = (int) context.getResources().getDimension(R.dimen.pad_meeting_audio_span_recording_time_margin_end);
        this.mRecordingTimeTextZoneWidth = (int) context.getResources().getDimension(R.dimen.pad_meeting_audio_span_time_zone_width);
        this.mBubbleHeight = (int) this.mContext.getResources().getDimension(R.dimen.pad_audio_bubble_height);
        this.mBubblePaddingBottom = (int) context.getResources().getDimension(R.dimen.pad_audio_span_drawable_padding_bottom);
        this.mRectWidth = this.mHornWidth + this.mBubbleWidth;
        this.mRectHeight = this.mBubblePaddingBottom + this.mBubbleHeight + this.mBubblePaddingTop;
        this.mBubbleRadius = (int) context.getResources().getDimension(R.dimen.pad_audio_bubble_radius);
        this.mWaveLineWidth = (int) context.getResources().getDimension(R.dimen.pad_audio_bubble_wave_line_width);
        this.mWaveFrame0LineHeight0 = (int) context.getResources().getDimension(R.dimen.pad_audio_bubble_wave_frame0_line_0);
        this.mWaveFrame0LineHeight1 = (int) context.getResources().getDimension(R.dimen.pad_audio_bubble_wave_frame0_line_1);
        this.mWaveFrame0LineHeight2 = (int) context.getResources().getDimension(R.dimen.pad_audio_bubble_wave_frame0_line_2);
        this.mWaveFrame0LineHeight3 = (int) context.getResources().getDimension(R.dimen.pad_audio_bubble_wave_frame0_line_3);
        this.mWaveFrame0LineHeight4 = (int) context.getResources().getDimension(R.dimen.pad_audio_bubble_wave_frame0_line_4);
        this.mWaveFrame0LineHeight5 = (int) context.getResources().getDimension(R.dimen.pad_audio_bubble_wave_frame0_line_5);
        this.mWaveFrame0LineHeight6 = (int) context.getResources().getDimension(R.dimen.pad_audio_bubble_wave_frame0_line_6);
        this.mWaveLineInterval = (int) context.getResources().getDimension(R.dimen.pad_audio_bubble_wave_line_interval);
        if ((RomUtils.isFoldDevice() && UIUtils.isInNarrowScreen(context)) || RomUtils.isJ18()) {
            this.mWaveLineInterval = (int) context.getResources().getDimension(R.dimen.pad_audio_bubble_wave_line_interval_narrow);
        }
        this.mPointerWidth = (int) context.getResources().getDimension(R.dimen.audio_span_single_wave_width);
        this.mWaveTmpFrame0LineHeight0 = (int) context.getResources().getDimension(R.dimen.pad_audio_bubble_temp_wave_frame0_line_0);
        this.mWaveTmpFrame0LineHeight1 = (int) context.getResources().getDimension(R.dimen.pad_audio_bubble_temp_wave_frame0_line_1);
        this.mWaveTmpFrame0LineHeight2 = (int) context.getResources().getDimension(R.dimen.pad_audio_bubble_temp_wave_frame0_line_2);
    }

    @Override // com.miui.richeditor.style.audio.WaveBubbleDrawable, com.miui.richeditor.style.audio.BubbleDrawable
    protected void initBubbleDecoration(Context context) {
        this.mTextSize = (int) context.getResources().getDimension(R.dimen.pad_audio_bubble_text_size);
        this.mTextToLeftBound = (int) context.getResources().getDimension(R.dimen.bubble_text_to_left_bound);
        this.mMaskRectWidth = (int) context.getResources().getDimension(R.dimen.bubble_mask_width);
    }

    @Override // com.miui.richeditor.style.audio.AnimAudioSpanDrawable
    protected void createDisableTrashIcon() {
        Drawable drawable = ContextCompat.getDrawable(this.mContext, R.drawable.pad_ic_audio_bubble_delete);
        drawable.setTint(this.mStyle.trashUnselectedColor);
        this.mDisableTrashIcon = UIUtils.drawableToBitmap(drawable);
        this.mDisableTrashIcon.setDensity(this.mDensityDpi);
    }

    @Override // com.miui.richeditor.style.audio.AnimAudioSpanDrawable, com.miui.richeditor.style.audio.BubbleDrawable
    protected void createTrashIcon() {
        Drawable drawable = ContextCompat.getDrawable(this.mContext, R.drawable.pad_ic_audio_bubble_delete);
        drawable.setTint(this.mStyle.contentColor);
        this.mTrashIcon = UIUtils.drawableToBitmap(drawable);
        this.mTrashIcon.setDensity(this.mDensityDpi);
    }

    public void updateRecordingTime(int i) {
        this.mRecordingTime = i;
    }

    protected void drawXiaoAiIcon(Canvas canvas) {
        canvas.setDensity(this.mDensityDpi);
        new Paint(this.mPaint).setColor(this.mStyle.contentColor);
        Paint paint = new Paint();
        int i = this.mXiaoaiIconStart;
        isRtl();
        Bitmap bitmap = this.mXiaoaiIcon;
        if (bitmap == null || bitmap.isRecycled()) {
            createXiaoaiIcon();
        }
        canvas.drawBitmap(this.mXiaoaiIcon, i, this.mBubblePaddingTop + ((this.mBubbleHeight - this.mXiaoaiIcon.getHeight()) / 2), paint);
        releaseXiaoaiIcon();
    }

    protected void drawRecordingText(Canvas canvas) {
        canvas.save();
        canvas.setDensity(this.mDensityDpi);
        Logger.INSTANCE.i("AnimBubble", "drawText canvas density" + canvas.getDensity());
        Paint paint = new Paint(this.mPaint);
        paint.setColor(this.mStyle.contentColor);
        paint.setTextSize(this.mTextSize);
        String strRecorderTimeToFormatText = recorderTimeToFormatText(this.mRecordingTime);
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        Bitmap bitmap = this.mXiaoaiIcon;
        if (bitmap == null || bitmap.isRecycled()) {
            createXiaoaiIcon();
        }
        int width = this.mXiaoaiIconStart + this.mXiaoaiIcon.getWidth() + this.mRecordingTimeMarginStart;
        this.mRecordingTimeTextToRightRect = this.mRecordingTimeTextZoneWidth + width;
        canvas.drawText(strRecorderTimeToFormatText, width, ((this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - fontMetricsInt.descent) + ((fontMetricsInt.descent - fontMetricsInt.ascent) / 2), paint);
        canvas.restore();
        releaseXiaoaiIcon();
    }

    @Override // com.miui.richeditor.style.audio.AnimAudioSpanDrawable
    protected void drawRecordingWave(Canvas canvas) {
        drawXiaoAiIcon(canvas);
        drawRecordingText(canvas);
        drawMeetingRecordingWave(canvas);
    }

    protected void drawMeetingRecordingWave(Canvas canvas) {
        Paint paint = new Paint(this.mPaint);
        paint.setColor(this.mStyle.contentColor);
        paint.setStyle(Paint.Style.FILL);
        int i = this.mRecordingTimeTextToRightRect;
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
        RectF rectF11 = new RectF(i + ((this.mWaveLineWidth + this.mWaveLineInterval) * 10), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveTmpFrame0LineHeight0 + this.mLineHeight0Offset) / 2), i + (this.mWaveLineWidth * 11) + (this.mWaveLineInterval * 10), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveTmpFrame0LineHeight0 + this.mLineHeight0Offset) / 2));
        RectF rectF12 = new RectF(i + ((this.mWaveLineWidth + this.mWaveLineInterval) * 11), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveTmpFrame0LineHeight1 + this.mLineHeight1Offset) / 2), i + (this.mWaveLineWidth * 12) + (this.mWaveLineInterval * 11), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveTmpFrame0LineHeight1 + this.mLineHeight1Offset) / 2));
        RectF rectF13 = new RectF(i + ((this.mWaveLineWidth + this.mWaveLineInterval) * 12), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveTmpFrame0LineHeight2 + this.mLineHeight2Offset) / 2), i + (this.mWaveLineWidth * 13) + (this.mWaveLineInterval * 12), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveTmpFrame0LineHeight2 + this.mLineHeight2Offset) / 2));
        RectF rectF14 = new RectF(i + ((this.mWaveLineWidth + this.mWaveLineInterval) * 13), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveTmpFrame0LineHeight1 + this.mLineHeight1Offset) / 2), i + (this.mWaveLineWidth * 14) + (this.mWaveLineInterval * 13), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveTmpFrame0LineHeight1 + this.mLineHeight1Offset) / 2));
        RectF rectF15 = new RectF(i + ((this.mWaveLineWidth + this.mWaveLineInterval) * 14), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveTmpFrame0LineHeight0 + this.mLineHeight0Offset) / 2), i + (this.mWaveLineWidth * 15) + (this.mWaveLineInterval * 14), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveTmpFrame0LineHeight0 + this.mLineHeight0Offset) / 2));
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
        canvas.drawRoundRect(rectF11, this.mWaveLineWidth / 2, this.mWaveLineWidth / 2, paint);
        canvas.drawRoundRect(rectF12, this.mWaveLineWidth / 2, this.mWaveLineWidth / 2, paint);
        canvas.drawRoundRect(rectF13, this.mWaveLineWidth / 2, this.mWaveLineWidth / 2, paint);
        canvas.drawRoundRect(rectF14, this.mWaveLineWidth / 2, this.mWaveLineWidth / 2, paint);
        canvas.drawRoundRect(rectF15, this.mWaveLineWidth / 2, this.mWaveLineWidth / 2, paint);
    }

    protected void drawNormalRecordingWave(Canvas canvas) {
        Paint paint = new Paint(this.mPaint);
        paint.setColor(this.mStyle.contentColor);
        paint.setStyle(Paint.Style.FILL);
        int i = (this.mTmpWidth - ((this.mWaveLineWidth * 15) + (this.mWaveLineInterval * 14))) / 2;
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
        RectF rectF11 = new RectF(i + ((this.mWaveLineWidth + this.mWaveLineInterval) * 10), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveTmpFrame0LineHeight0 + this.mLineHeight0Offset) / 2), i + (this.mWaveLineWidth * 11) + (this.mWaveLineInterval * 10), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveTmpFrame0LineHeight0 + this.mLineHeight0Offset) / 2));
        RectF rectF12 = new RectF(i + ((this.mWaveLineWidth + this.mWaveLineInterval) * 11), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveTmpFrame0LineHeight1 + this.mLineHeight1Offset) / 2), i + (this.mWaveLineWidth * 12) + (this.mWaveLineInterval * 11), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveTmpFrame0LineHeight1 + this.mLineHeight1Offset) / 2));
        RectF rectF13 = new RectF(i + ((this.mWaveLineWidth + this.mWaveLineInterval) * 12), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveTmpFrame0LineHeight2 + this.mLineHeight2Offset) / 2), i + (this.mWaveLineWidth * 13) + (this.mWaveLineInterval * 12), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveTmpFrame0LineHeight2 + this.mLineHeight2Offset) / 2));
        RectF rectF14 = new RectF(i + ((this.mWaveLineWidth + this.mWaveLineInterval) * 13), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveTmpFrame0LineHeight1 + this.mLineHeight1Offset) / 2), i + (this.mWaveLineWidth * 14) + (this.mWaveLineInterval * 13), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveTmpFrame0LineHeight1 + this.mLineHeight1Offset) / 2));
        RectF rectF15 = new RectF(i + ((this.mWaveLineWidth + this.mWaveLineInterval) * 14), (this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - ((this.mWaveTmpFrame0LineHeight0 + this.mLineHeight0Offset) / 2), i + (this.mWaveLineWidth * 15) + (this.mWaveLineInterval * 14), this.mBubblePaddingTop + (this.mBubbleHeight / 2) + ((this.mWaveTmpFrame0LineHeight0 + this.mLineHeight0Offset) / 2));
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
        canvas.drawRoundRect(rectF11, this.mWaveLineWidth / 2, this.mWaveLineWidth / 2, paint);
        canvas.drawRoundRect(rectF12, this.mWaveLineWidth / 2, this.mWaveLineWidth / 2, paint);
        canvas.drawRoundRect(rectF13, this.mWaveLineWidth / 2, this.mWaveLineWidth / 2, paint);
        canvas.drawRoundRect(rectF14, this.mWaveLineWidth / 2, this.mWaveLineWidth / 2, paint);
        canvas.drawRoundRect(rectF15, this.mWaveLineWidth / 2, this.mWaveLineWidth / 2, paint);
    }
}
