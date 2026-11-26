package com.miui.richeditor.style.render;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import androidx.core.content.ContextCompat;
import com.google.android.material.timepicker.TimeModel;
import com.miui.common.tool.UIUtils;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.theme.util.ResourceManager;
import com.miui.richeditor.theme.Theme;
import com.miui.todo.utils.TodoUtils;
import java.util.ArrayList;
import java.util.Random;

/* JADX INFO: loaded from: classes4.dex */
public class AudioSpanHelper {
    private static final String TAG = "AudioSpanHelper";
    public int mAudioBubbleHeight;
    protected int mAudioBubblePaddingBottom;
    protected int mAudioBubblePaddingEnd;
    protected int mAudioBubblePaddingStart;
    protected int mAudioBubblePaddingTop;
    public int mAudioBubbleWidth;
    protected int mAudioContentEnd;
    protected int mAudioContentStart;
    protected int mBgColor;
    protected int mBorderColor;
    public int mBubbleHeight;
    protected int mBubbleRadius;
    public int mBubbleWidth;
    protected int mContentColor;
    protected int mContentTextSize;
    protected float mContentToTopRect;
    protected int mDividerColor;
    protected float mDividerHeight;
    protected int mDividerMargin;
    protected int mHornWidth;
    protected int mIconColor;
    protected int mIncreaseTime;
    protected int mMaskRectWidth;
    protected int mMaxWaveLineHeight;
    protected int mMinWaveLineHeight;
    protected Paint mPaint;
    protected Bitmap mPlayStatusBmp;
    public int mRectHeight;
    public int mRectWidth;
    protected int mSmallRectRadius;
    protected int mSmallRectWidth;
    protected int mStrokeWidth;
    protected int mSummaryHeight;
    protected Bitmap mSummaryIconBmp;
    StaticLayout mSummaryLayout;
    protected int mSummaryPaddingStart;
    protected int mSummaryPaddingTop;
    protected int mTextPaddingStart;
    protected int mTextSize;
    protected int mTextToLeftBound;
    public int mTextToRightRect;
    protected Theme mTheme;
    protected int mThemeColor;
    protected int mTimeColor;
    protected int mViewWidth;
    protected Bitmap mWaveBmp;
    protected int mWaveLineInterval;
    protected int mWaveLineWidth;
    protected int mWavePadding;
    protected boolean isShowSummary = false;
    protected boolean isShowSummaryIcon = false;
    protected boolean isWithStroke = false;
    protected double[][] wave = {new double[]{0.5d, 0.81d, 0.34d, 0.48d, 0.67d, 0.32d, 0.46d, 0.27d, 0.87d, 0.56d, 0.61d, 0.68d, 0.42d, 0.6d, 0.67d, 0.41d, 0.7d, 0.16d, 0.6d, 0.39d, 0.34d, 0.5d, 0.7d, 0.66d, 0.64d, 0.3d, 0.79d, 0.35d, 0.59d, 0.37d, 0.5d}, new double[]{0.5d, 0.66d, 0.86d, 0.48d, 0.87d, 0.31d, 0.46d, 0.7d, 0.68d, 0.56d, 0.1d, 0.33d, 0.58d, 0.35d, 0.86d, 0.41d, 0.7d, 0.16d, 0.4d, 0.37d, 0.17d, 0.62d, 0.38d, 0.34d, 0.64d, 0.7d, 0.64d, 0.35d, 0.41d, 0.37d, 0.34d}, new double[]{0.5d, 0.3d, 0.83d, 0.52d, 0.67d, 0.87d, 0.58d, 0.3d, 0.51d, 0.44d, 0.74d, 0.68d, 0.59d, 0.7d, 0.85d, 0.41d, 0.7d, 0.5d, 0.61d, 0.63d, 0.66d, 0.62d, 0.7d, 0.81d, 0.77d, 0.8d, 0.79d, 0.65d, 0.5d, 0.76d, 0.66d}, new double[]{0.5d, 0.3d, 0.34d, 0.52d, 0.81d, 0.69d, 0.46d, 0.69d, 0.31d, 0.56d, 0.39d, 0.32d, 0.35d, 0.75d, 0.67d, 0.59d, 0.7d, 0.16d, 0.61d, 0.63d, 0.66d, 0.38d, 0.47d, 0.5d, 0.36d, 0.49d, 0.5d, 0.2d, 0.39d, 0.5d, 0.34d}, new double[]{0.5d, 0.52d, 0.84d, 0.5d, 0.53d, 0.49d, 0.46d, 0.41d, 0.86d, 0.61d, 0.61d, 0.14d, 0.5d, 0.41d, 0.67d, 0.41d, 0.29d, 0.33d, 0.39d, 0.48d, 0.66d, 0.38d, 0.53d, 0.66d, 0.64d, 0.7d, 0.36d, 0.35d, 0.39d, 0.63d, 0.34d}};

    public AudioSpanHelper(Theme theme) {
        this.mTheme = theme;
        initBubblePadding(NoteApp.getInstance());
        intiBubbleDimension(NoteApp.getInstance());
        updateStyle(theme);
    }

    public void updateStyle(Theme theme) {
        if (theme == null) {
            theme = ResourceManager.getTheme(0);
        }
        int id = theme.getId();
        Drawable drawable = ContextCompat.getDrawable(NoteApp.getInstance(), R.drawable.audio_share_play);
        Drawable drawable2 = ContextCompat.getDrawable(NoteApp.getInstance(), R.drawable.audio_share_summary_icon);
        this.isWithStroke = false;
        switch (id) {
            case 1:
                this.mThemeColor = TodoUtils.getColor(R.color.audio_share_color_blue);
                this.mTimeColor = TodoUtils.getColor(R.color.audio_share_time_color_blue);
                this.mContentColor = TodoUtils.getColor(R.color.audio_share_content_color_blue);
                this.mIconColor = TodoUtils.getColor(R.color.audio_share_icon_color_blue);
                this.mBgColor = TodoUtils.getColor(R.color.audio_share_bg_blue);
                ContextCompat.getDrawable(NoteApp.getInstance(), R.drawable.audio_share_wave_long);
                this.mDividerColor = this.mBgColor;
                break;
            case 2:
                this.mThemeColor = TodoUtils.getColor(R.color.audio_share_color_yellow);
                this.mTimeColor = TodoUtils.getColor(R.color.audio_share_time_color_yellow);
                this.mContentColor = TodoUtils.getColor(R.color.audio_share_content_color_yellow);
                this.mIconColor = TodoUtils.getColor(R.color.audio_share_icon_color_yellow);
                this.mBgColor = TodoUtils.getColor(R.color.audio_share_bg_yellow);
                ContextCompat.getDrawable(NoteApp.getInstance(), R.drawable.audio_share_wave_long);
                this.mDividerColor = this.mBgColor;
                break;
            case 3:
                this.mThemeColor = TodoUtils.getColor(R.color.audio_share_color_green);
                this.mTimeColor = TodoUtils.getColor(R.color.audio_share_time_color_green);
                this.mContentColor = TodoUtils.getColor(R.color.audio_share_content_color_green);
                this.mIconColor = TodoUtils.getColor(R.color.audio_share_icon_color_green);
                this.mBgColor = TodoUtils.getColor(R.color.audio_share_bg_green);
                ContextCompat.getDrawable(NoteApp.getInstance(), R.drawable.audio_share_wave_long);
                this.mDividerColor = this.mBgColor;
                break;
            case 4:
                this.mThemeColor = TodoUtils.getColor(R.color.audio_share_color_red);
                this.mTimeColor = TodoUtils.getColor(R.color.audio_share_time_color_red);
                this.mContentColor = TodoUtils.getColor(R.color.audio_share_content_color_red);
                this.mIconColor = TodoUtils.getColor(R.color.audio_share_icon_color_red);
                this.mBgColor = TodoUtils.getColor(R.color.audio_share_bg_red);
                ContextCompat.getDrawable(NoteApp.getInstance(), R.drawable.audio_share_wave_long);
                this.mDividerColor = this.mBgColor;
                break;
            case 5:
                this.mThemeColor = TodoUtils.getColor(R.color.audio_share_color_candy);
                this.mTimeColor = TodoUtils.getColor(R.color.audio_share_time_color_candy);
                this.mContentColor = TodoUtils.getColor(R.color.audio_share_content_color_candy);
                this.mIconColor = TodoUtils.getColor(R.color.audio_share_icon_color_candy);
                this.mBgColor = TodoUtils.getColor(R.color.audio_share_bg_candy);
                ContextCompat.getDrawable(NoteApp.getInstance(), R.drawable.audio_share_wave_middle);
                this.mDividerColor = this.mBgColor;
                break;
            case 6:
                this.mThemeColor = TodoUtils.getColor(R.color.audio_share_color_white);
                this.mTimeColor = TodoUtils.getColor(R.color.audio_share_time_color_white);
                this.mContentColor = TodoUtils.getColor(R.color.audio_share_content_color_white);
                this.mIconColor = TodoUtils.getColor(R.color.audio_share_icon_color_white);
                this.mBgColor = TodoUtils.getColor(R.color.audio_share_bg_white);
                ContextCompat.getDrawable(NoteApp.getInstance(), R.drawable.audio_share_wave_short);
                this.mDividerColor = TodoUtils.getColor(R.color.audio_share_divider_color_white);
                break;
            case 7:
                this.mThemeColor = TodoUtils.getColor(R.color.audio_share_color_antique);
                this.mTimeColor = TodoUtils.getColor(R.color.audio_share_time_color_antique);
                this.mContentColor = TodoUtils.getColor(R.color.audio_share_content_color_antique);
                this.mIconColor = TodoUtils.getColor(R.color.audio_share_icon_color_antique);
                this.mBgColor = TodoUtils.getColor(R.color.audio_share_bg_antique);
                ContextCompat.getDrawable(NoteApp.getInstance(), R.drawable.audio_share_wave_middle);
                this.mDividerColor = this.mBgColor;
                this.isWithStroke = true;
                this.mBorderColor = TodoUtils.getColor(R.color.audio_share_border_color_antique);
                break;
            case 8:
                this.mThemeColor = TodoUtils.getColor(R.color.audio_share_color_arty);
                this.mTimeColor = TodoUtils.getColor(R.color.audio_share_time_color_arty);
                this.mContentColor = TodoUtils.getColor(R.color.audio_share_content_color_arty);
                this.mIconColor = TodoUtils.getColor(R.color.audio_share_icon_color_arty);
                this.mBgColor = TodoUtils.getColor(R.color.audio_share_bg_arty);
                ContextCompat.getDrawable(NoteApp.getInstance(), R.drawable.audio_share_wave_middle);
                this.mDividerColor = this.mBgColor;
                this.isWithStroke = true;
                this.mBorderColor = TodoUtils.getColor(R.color.audio_share_border_color_arty);
                break;
            case 9:
            case 10:
            case 12:
            case 13:
            case 14:
                this.mThemeColor = TodoUtils.getColor(R.color.audio_share_color_white);
                this.mTimeColor = TodoUtils.getColor(R.color.audio_share_time_color_white);
                this.mContentColor = TodoUtils.getColor(R.color.audio_share_content_color_white);
                this.mIconColor = TodoUtils.getColor(R.color.audio_share_icon_color_white);
                this.mBgColor = TodoUtils.getColor(R.color.audio_share_bg_white);
                ContextCompat.getDrawable(NoteApp.getInstance(), R.drawable.audio_share_wave_long);
                this.mDividerColor = TodoUtils.getColor(R.color.audio_share_divider_color_white);
                break;
            case 11:
                this.mThemeColor = TodoUtils.getColor(R.color.audio_share_color_shadow);
                this.mTimeColor = TodoUtils.getColor(R.color.audio_share_time_color_shadow);
                this.mContentColor = TodoUtils.getColor(R.color.audio_share_content_color_shadow);
                this.mIconColor = TodoUtils.getColor(R.color.audio_share_icon_color_shadow);
                this.mBgColor = TodoUtils.getColor(R.color.audio_share_bg_shadow);
                ContextCompat.getDrawable(NoteApp.getInstance(), R.drawable.audio_share_wave_long);
                this.mDividerColor = this.mBgColor;
                break;
            default:
                this.mThemeColor = TodoUtils.getColor(R.color.audio_share_color_default);
                this.mTimeColor = TodoUtils.getColor(R.color.audio_share_time_color_default);
                this.mContentColor = TodoUtils.getColor(R.color.audio_share_content_color_default);
                this.mIconColor = TodoUtils.getColor(R.color.audio_share_icon_color_default);
                this.mBgColor = TodoUtils.getColor(R.color.audio_share_bg_default);
                ContextCompat.getDrawable(NoteApp.getInstance(), R.drawable.audio_share_wave_middle);
                this.mDividerColor = this.mBgColor;
                break;
        }
        if (drawable != null) {
            drawable.setColorFilter(this.mThemeColor, PorterDuff.Mode.SRC_IN);
        }
        this.mPlayStatusBmp = UIUtils.drawableToBitmap(drawable);
        if (drawable2 != null) {
            drawable2.setColorFilter(this.mThemeColor, PorterDuff.Mode.SRC_IN);
        }
        this.mSummaryIconBmp = UIUtils.drawableToBitmap(drawable2);
        Paint paint = new Paint(1);
        this.mPaint = paint;
        paint.setColor(this.mBgColor);
    }

    protected void initBubblePadding(Context context) {
        this.mAudioContentStart = (int) context.getResources().getDimension(R.dimen.audio_share_margin_start);
        this.mAudioContentEnd = (int) context.getResources().getDimension(R.dimen.audio_span_padding_start_and_end_level3);
        this.mTextPaddingStart = (int) context.getResources().getDimension(R.dimen.audio_span_text_padding_start_level3);
        this.mWavePadding = (int) context.getResources().getDimension(R.dimen.audio_share_wave_padding);
        this.mDividerMargin = (int) context.getResources().getDimension(R.dimen.audio_share_divider_margin);
        this.mSummaryPaddingStart = (int) context.getResources().getDimension(R.dimen.audio_share_content_padding_h);
        this.mSummaryPaddingTop = (int) context.getResources().getDimension(R.dimen.audio_share_content_padding_v);
        this.mAudioBubblePaddingTop = 0;
        this.mAudioBubblePaddingBottom = 0;
        this.mAudioBubblePaddingStart = 0;
        this.mAudioBubblePaddingEnd = 0;
    }

    protected void intiBubbleDimension(Context context) {
        this.mAudioBubbleHeight = (int) context.getResources().getDimension(R.dimen.audio_share_height);
        this.mAudioBubbleWidth = (int) context.getResources().getDimension(R.dimen.audio_share_width);
        this.mBubbleHeight = this.mAudioBubbleHeight;
        this.mStrokeWidth = (int) context.getResources().getDimension(R.dimen.bubble_stroke_width);
        this.mDividerHeight = context.getResources().getDimension(R.dimen.audio_share_divider_height);
        this.mWaveLineWidth = (int) context.getResources().getDimension(R.dimen.audio_share_wave_line_width);
        this.mWaveLineInterval = (int) context.getResources().getDimension(R.dimen.audio_share_wave_line_interval);
        this.mMaxWaveLineHeight = (int) context.getResources().getDimension(R.dimen.audio_share_wave_line_max_height);
        this.mMinWaveLineHeight = (int) context.getResources().getDimension(R.dimen.audio_share_wave_line_min_height);
    }

    public int setSummary(String str, boolean z, int i) {
        int width;
        int i2;
        this.isShowSummaryIcon = z;
        this.mBubbleWidth = i;
        Bitmap bitmap = this.mSummaryIconBmp;
        if (bitmap == null || bitmap.isRecycled()) {
            Drawable drawable = ContextCompat.getDrawable(NoteApp.getInstance(), R.drawable.audio_share_summary_icon);
            if (drawable != null) {
                drawable.setColorFilter(this.mThemeColor, PorterDuff.Mode.SRC_IN);
            }
            this.mSummaryIconBmp = UIUtils.drawableToBitmap(drawable);
        }
        if (str != null && !str.isEmpty()) {
            this.isShowSummary = true;
            TextPaint textPaint = new TextPaint();
            textPaint.setColor(this.mContentColor);
            int dimension = (int) NoteApp.getInstance().getResources().getDimension(R.dimen.audio_share_content_size);
            this.mContentTextSize = dimension;
            textPaint.setTextSize(dimension);
            textPaint.setAntiAlias(true);
            if (z) {
                width = this.mBubbleWidth - this.mSummaryIconBmp.getWidth();
                i2 = this.mSummaryPaddingStart * 3;
            } else {
                width = this.mBubbleWidth;
                i2 = this.mSummaryPaddingStart * 2;
            }
            StaticLayout.Builder builderObtain = StaticLayout.Builder.obtain(str, 0, str.length(), textPaint, width - i2);
            builderObtain.setMaxLines(2);
            builderObtain.setEllipsize(TextUtils.TruncateAt.END);
            StaticLayout staticLayoutBuild = builderObtain.build();
            this.mSummaryLayout = staticLayoutBuild;
            int height = staticLayoutBuild.getHeight();
            this.mSummaryHeight = height;
            this.mBubbleHeight = (int) (this.mAudioBubbleHeight + this.mDividerHeight + height + (this.mSummaryPaddingTop * 2));
        }
        UIUtils.releaseBitmap(this.mSummaryIconBmp);
        return this.mBubbleHeight;
    }

    public void drawBg(Rect rect, Canvas canvas, int i) {
        drawTime(canvas, i);
        drawWave(canvas);
        if (this.isShowSummary) {
            drawDivider(canvas);
            if (this.isShowSummaryIcon) {
                drawSummaryIcon(canvas);
            }
            drawSummary(canvas);
        }
        drawRoundRect(canvas, rect);
    }

    private void drawRoundRect(Canvas canvas, Rect rect) {
        Resources resources;
        int i;
        canvas.save();
        RectF rectF = new RectF(rect.left, rect.top, rect.right, rect.bottom);
        if (this.isShowSummary) {
            resources = NoteApp.getInstance().getResources();
            i = R.dimen.audio_share_bubble_radius_with_description;
        } else {
            resources = NoteApp.getInstance().getResources();
            i = R.dimen.audio_share_bubble_radius;
        }
        int dimension = (int) resources.getDimension(i);
        this.mBubbleRadius = dimension;
        canvas.drawRoundRect(rectF, dimension, dimension, this.mPaint);
        if (this.isWithStroke) {
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(this.mBorderColor);
            paint.setStrokeWidth(this.mStrokeWidth);
            float f = rect.left + this.mStrokeWidth;
            float f2 = rect.top + this.mStrokeWidth;
            int i2 = rect.right;
            int i3 = this.mStrokeWidth;
            RectF rectF2 = new RectF(f, f2, i2 - i3, this.mBubbleHeight - i3);
            int i4 = this.mBubbleRadius;
            canvas.drawRoundRect(rectF2, i4, i4, paint);
        }
        canvas.restore();
    }

    private void drawPlayBtn(Canvas canvas) {
        canvas.save();
        Paint paint = new Paint(this.mPaint);
        paint.setColor(this.mThemeColor);
        Bitmap bitmap = this.mPlayStatusBmp;
        if (bitmap == null || bitmap.isRecycled()) {
            Drawable drawable = ContextCompat.getDrawable(NoteApp.getInstance(), R.drawable.audio_share_play);
            if (drawable != null) {
                drawable.setColorFilter(this.mThemeColor, PorterDuff.Mode.SRC_IN);
            }
            this.mPlayStatusBmp = UIUtils.drawableToBitmap(drawable);
        }
        Bitmap bitmap2 = this.mPlayStatusBmp;
        canvas.drawBitmap(bitmap2, this.mAudioContentStart, this.mAudioBubblePaddingTop + ((this.mAudioBubbleHeight - bitmap2.getHeight()) / 2), paint);
        canvas.restore();
        UIUtils.releaseBitmap(this.mPlayStatusBmp);
    }

    public static String recorderTimeToFormatText(int i) {
        return String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(i / 60)) + ":" + String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(i % 60));
    }

    private void drawTime(Canvas canvas, int i) {
        canvas.save();
        Paint paint = new Paint(this.mPaint);
        paint.setColor(this.mTimeColor);
        int dimension = (int) NoteApp.getInstance().getResources().getDimension(R.dimen.audio_share_time_text_size);
        this.mTextSize = dimension;
        paint.setTextSize(dimension);
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        String strRecorderTimeToFormatText = recorderTimeToFormatText(i);
        int iMeasureText = (int) paint.measureText(strRecorderTimeToFormatText);
        int i2 = this.mAudioContentStart;
        this.mTextToRightRect = iMeasureText + i2;
        canvas.drawText(strRecorderTimeToFormatText, i2, ((this.mAudioBubblePaddingTop + (this.mAudioBubbleHeight / 2)) - fontMetricsInt.descent) + ((fontMetricsInt.descent - fontMetricsInt.ascent) / 2), paint);
        canvas.restore();
    }

    private void drawWave(Canvas canvas) {
        canvas.save();
        int i = this.mTextToRightRect;
        int i2 = this.mWavePadding;
        float f = i + i2;
        float f2 = (this.mBubbleWidth - f) - i2;
        float f3 = this.mWaveLineWidth + this.mWaveLineInterval;
        int i3 = (int) (f2 / f3);
        ArrayList arrayList = new ArrayList();
        int iNextInt = new Random().nextInt(5);
        for (int i4 = 0; i4 < i3; i4++) {
            int i5 = this.mMinWaveLineHeight;
            float f4 = i5 + ((float) (((double) (this.mMaxWaveLineHeight - i5)) * this.wave[iNextInt][i4 % 31]));
            float f5 = (this.mAudioBubbleHeight - f4) / 2.0f;
            arrayList.add(new RectF(f, f5, this.mWaveLineWidth + f, f4 + f5));
            f += f3;
        }
        Paint paint = new Paint(this.mPaint);
        paint.setColor(this.mThemeColor);
        paint.setStyle(Paint.Style.FILL);
        for (int i6 = 0; i6 < arrayList.size(); i6++) {
            RectF rectF = (RectF) arrayList.get(i6);
            int i7 = this.mWaveLineWidth;
            canvas.drawRoundRect(rectF, i7 / 2.0f, i7 / 2.0f, paint);
        }
        canvas.restore();
    }

    private void drawDivider(Canvas canvas) {
        canvas.save();
        Paint paint = new Paint(this.mPaint);
        paint.setColor(this.mDividerColor);
        int i = this.mAudioBubbleHeight;
        float f = i + this.mDividerHeight;
        this.mContentToTopRect = f;
        int i2 = this.mDividerMargin;
        canvas.drawRect(i2, i, this.mBubbleWidth - i2, f, paint);
        canvas.restore();
    }

    private void drawSummaryIcon(Canvas canvas) {
        canvas.save();
        Paint paint = new Paint(this.mPaint);
        paint.setColor(this.mThemeColor);
        Bitmap bitmap = this.mSummaryIconBmp;
        if (bitmap == null || bitmap.isRecycled()) {
            Drawable drawable = ContextCompat.getDrawable(NoteApp.getInstance(), R.drawable.audio_share_summary_icon);
            if (drawable != null) {
                drawable.setColorFilter(this.mThemeColor, PorterDuff.Mode.SRC_IN);
            }
            this.mSummaryIconBmp = UIUtils.drawableToBitmap(drawable);
        }
        canvas.drawBitmap(this.mSummaryIconBmp, this.mSummaryPaddingStart, this.mContentToTopRect + this.mSummaryPaddingTop, paint);
        canvas.restore();
        UIUtils.releaseBitmap(this.mSummaryIconBmp);
    }

    private void drawSummary(Canvas canvas) {
        canvas.save();
        Bitmap bitmap = this.mSummaryIconBmp;
        if (bitmap == null || bitmap.isRecycled()) {
            Drawable drawable = ContextCompat.getDrawable(NoteApp.getInstance(), R.drawable.audio_share_summary_icon);
            if (drawable != null) {
                drawable.setColorFilter(this.mThemeColor, PorterDuff.Mode.SRC_IN);
            }
            this.mSummaryIconBmp = UIUtils.drawableToBitmap(drawable);
        }
        canvas.translate(this.isShowSummaryIcon ? this.mSummaryIconBmp.getWidth() + (this.mSummaryPaddingStart * 2) : this.mSummaryPaddingStart, this.mContentToTopRect + this.mSummaryPaddingTop);
        this.mSummaryLayout.draw(canvas);
        canvas.restore();
        UIUtils.releaseBitmap(this.mSummaryIconBmp);
    }
}
