package com.miui.notes.audio;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import androidx.core.content.ContextCompat;
import com.google.android.material.timepicker.TimeModel;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.notes.R;
import defpackage.R2;
import java.util.Locale;

/* JADX INFO: loaded from: classes4.dex */
public class BubbleDrawable extends Drawable {
    public int mBubbleHeight;
    protected int mBubblePaddingBottom;
    protected int mBubblePaddingEnd;
    protected int mBubblePaddingStart;
    protected int mBubblePaddingTop;
    protected int mBubbleRadius;
    public int mBubbleWidth;
    protected Context mContext;
    protected int mDensityDpi;
    protected int mDividerHeight;
    protected int mDividerMarginLeft;
    protected int mDividerMarginRight;
    protected int mDividerWidth;
    protected int mDrawType;
    protected int mHornWidth;
    protected int mIncreaseTime;
    protected int mMaskRectWidth;
    protected Paint mPaint;
    public int mRectHeight;
    public int mRectWidth;
    protected int mSmallRectRadius;
    protected int mSmallRectWidth;
    protected int mStrokeWidth;
    protected Style mStyle;
    protected int mTextSize;
    protected int mTextToLeftBound;
    public int mTextToRightRect;
    protected Bitmap mTrashIcon;
    protected int mViewWidth;

    public interface DrawType {
        public static final int COMMON = 0;
        public static final int NO_CONTENT = 2;
        public static final int NO_TEXT = 1;
    }

    public static int recorderTimeToBubbleIncrease(int i, int i2) {
        return (int) ((i2 * i) / 300.0f);
    }

    protected void drawPlayBtn(Canvas canvas) {
    }

    protected void drawShadow(Canvas canvas) {
    }

    protected void drawWave(Canvas canvas) {
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    public void onUpdateTheme(int i) {
    }

    public BubbleDrawable(Context context, int i) {
        this(context, i, 0);
    }

    public BubbleDrawable(Context context, int i, int i2) {
        this.mDrawType = 0;
        this.mStyle = null;
        this.mDensityDpi = 0;
        this.mDensityDpi = context.getResources().getDisplayMetrics().densityDpi;
        this.mContext = context;
        this.mDrawType = i2;
        this.mIncreaseTime = i;
        this.mPaint = new Paint(1);
        initBubblePadding(context);
        intiBubbleDimension(context);
        initRectDimension();
        initBubbleDecoration(context);
        setBounds(0, 0, this.mRectWidth, this.mRectHeight);
        initStyle(0);
    }

    public void initStyle(int i) {
        Style style = new Style(i);
        this.mStyle = style;
        this.mPaint.setColor(style.bgColor);
        createTrashIcon();
    }

    public void setStyle(int i) {
        if (this.mStyle.themeId != i) {
            this.mStyle = new Style(i);
            createTrashIcon();
            this.mPaint.setColor(this.mStyle.bgColor);
            onUpdateTheme(i);
        }
    }

    protected void createTrashIcon() {
        Drawable drawable = ContextCompat.getDrawable(this.mContext, R.drawable.ic_audio_bubble_delete);
        drawable.setTint(this.mStyle.contentColor);
        drawable.setAlpha(R2.array.ai_panel_widget_blend_colors);
        Bitmap bitmapDrawableToBitmap = UIUtils.drawableToBitmap(drawable);
        this.mTrashIcon = bitmapDrawableToBitmap;
        bitmapDrawableToBitmap.setDensity(this.mDensityDpi);
    }

    public void setViewWidth(int i) {
        this.mViewWidth = i;
        updateDimension(this.mIncreaseTime);
    }

    protected void updateDimension(int i) {
        if (i <= 0 || this.mViewWidth <= this.mRectWidth) {
            return;
        }
        int dimension = ((int) this.mContext.getResources().getDimension(R.dimen.bubble_middle_width)) + (this.mBubbleRadius * 2);
        this.mBubbleWidth = dimension;
        this.mRectHeight = this.mBubblePaddingBottom + this.mBubblePaddingTop + this.mBubbleHeight;
        int i2 = this.mHornWidth + dimension + this.mBubblePaddingStart + this.mBubblePaddingEnd;
        this.mRectWidth = i2;
        int iRecorderTimeToBubbleIncrease = recorderTimeToBubbleIncrease(i, this.mViewWidth - i2);
        this.mBubbleWidth += iRecorderTimeToBubbleIncrease;
        this.mRectWidth += iRecorderTimeToBubbleIncrease;
        this.mTextToLeftBound = (int) this.mContext.getResources().getDimension(R.dimen.bubble_text_to_left_bound);
    }

    public BubbleDrawable(Context context) {
        this(context, 0);
    }

    protected void initBubbleDecoration(Context context) {
        this.mTextSize = (int) context.getResources().getDimension(R.dimen.bubble_text_size);
        this.mTextToLeftBound = (int) context.getResources().getDimension(R.dimen.bubble_text_to_left_bound);
        this.mMaskRectWidth = (int) context.getResources().getDimension(R.dimen.bubble_mask_width);
    }

    protected void initBubblePadding(Context context) {
        this.mBubblePaddingTop = 0;
        this.mBubblePaddingBottom = 0;
        this.mBubblePaddingStart = 0;
        this.mBubblePaddingEnd = 0;
    }

    protected void intiBubbleDimension(Context context) {
        this.mSmallRectWidth = (int) context.getResources().getDimension(R.dimen.bubble_small_rect_width);
        this.mSmallRectRadius = (int) context.getResources().getDimension(R.dimen.bubble_small_radius);
        this.mBubbleRadius = (int) context.getResources().getDimension(R.dimen.bubble_radius);
        this.mBubbleHeight = (int) context.getResources().getDimension(R.dimen.bubble_height);
        this.mBubbleWidth = ((int) context.getResources().getDimension(R.dimen.bubble_middle_width)) + (this.mBubbleRadius * 2);
        this.mStrokeWidth = (int) context.getResources().getDimension(R.dimen.bubble_stroke_width);
        this.mDividerWidth = (int) context.getResources().getDimension(R.dimen.bubble_divider_width);
        this.mDividerHeight = (int) context.getResources().getDimension(R.dimen.bubble_divider_height);
        this.mDividerMarginLeft = (int) context.getResources().getDimension(R.dimen.bubble_divider_margin_left);
        this.mDividerMarginRight = (int) context.getResources().getDimension(R.dimen.bubble_divider_margin_right);
    }

    protected void initRectDimension() {
        int i;
        this.mRectHeight = this.mBubblePaddingBottom + this.mBubblePaddingTop + this.mBubbleHeight;
        int i2 = this.mHornWidth + this.mBubbleWidth + this.mBubblePaddingStart + this.mBubblePaddingEnd;
        this.mRectWidth = i2;
        int i3 = this.mIncreaseTime;
        if (i3 <= 0 || (i = this.mViewWidth) <= i2) {
            return;
        }
        int iRecorderTimeToBubbleIncrease = recorderTimeToBubbleIncrease(i3, i - i2);
        this.mBubbleWidth += iRecorderTimeToBubbleIncrease;
        this.mRectWidth += iRecorderTimeToBubbleIncrease;
    }

    public int getIncreaseTime() {
        return this.mIncreaseTime;
    }

    public boolean isTouchLeft(int i) {
        if (isRtl()) {
            return i >= (this.mBubblePaddingStart + this.mBubbleWidth) - this.mDividerMarginLeft;
        }
        return i <= this.mBubblePaddingStart + this.mDividerMarginLeft;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        drawRoundRect(canvas);
        drawPlayBtn(canvas);
        drawText(canvas);
        drawWave(canvas);
        drawTrashIcon(canvas);
    }

    protected void drawRoundRect(Canvas canvas) {
        canvas.setDensity(this.mDensityDpi);
        int i = this.mBubblePaddingStart;
        int i2 = this.mBubblePaddingTop;
        RectF rectF = new RectF(i, i2, i + this.mBubbleWidth, i2 + this.mBubbleHeight);
        int i3 = this.mBubbleRadius;
        canvas.drawRoundRect(rectF, i3, i3, this.mPaint);
    }

    protected void drawText(Canvas canvas) {
        if (isDrawText()) {
            Paint paint = new Paint(this.mPaint);
            paint.setColor(this.mStyle.contentColor);
            paint.setTextSize(this.mTextSize);
            String strRecorderTimeToText = recorderTimeToText(this.mIncreaseTime);
            Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
            int iMeasureText = (int) paint.measureText(strRecorderTimeToText);
            int i = this.mTextToLeftBound;
            this.mTextToRightRect = i + iMeasureText;
            if (isRtl()) {
                i = (this.mBubbleWidth - this.mTextToLeftBound) - iMeasureText;
            }
            canvas.drawText(strRecorderTimeToText, i, ((this.mBubblePaddingTop + (this.mBubbleHeight / 2)) - fontMetricsInt.descent) + ((fontMetricsInt.descent - fontMetricsInt.ascent) / 2), paint);
            return;
        }
        this.mTextToRightRect = this.mTextToLeftBound;
    }

    protected void drawTrashIcon(Canvas canvas) {
        canvas.setDensity(this.mDensityDpi);
        Paint paint = new Paint(this.mPaint);
        paint.setColor(this.mStyle.contentColor);
        int i = this.mDividerMarginLeft;
        if (isRtl()) {
            i = (this.mBubbleWidth - this.mDividerMarginLeft) - this.mDividerWidth;
        }
        int i2 = this.mBubblePaddingStart;
        int i3 = this.mBubblePaddingTop;
        int i4 = this.mBubbleHeight;
        int i5 = this.mDividerHeight;
        canvas.drawRect(new Rect(i2 + i, ((i4 - i5) / 2) + i3, i2 + i + this.mDividerWidth, i3 + ((i4 - i5) / 2) + i5), paint);
        Paint paint2 = new Paint();
        int width = this.mDividerMarginLeft + this.mDividerWidth + this.mDividerMarginRight;
        if (isRtl()) {
            Bitmap bitmap = this.mTrashIcon;
            if (bitmap == null || bitmap.isRecycled()) {
                createTrashIcon();
            }
            width = (this.mBubbleWidth - width) - this.mTrashIcon.getWidth();
        }
        Bitmap bitmap2 = this.mTrashIcon;
        canvas.drawBitmap(bitmap2, this.mBubblePaddingStart + width, this.mBubblePaddingTop + ((this.mBubbleHeight - bitmap2.getHeight()) / 2), paint2);
        UIUtils.releaseBitmap(this.mTrashIcon);
    }

    protected boolean isDrawText() {
        return this.mDrawType == 0;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.mPaint.setAlpha(i);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    public static boolean isRtl() {
        return TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 1;
    }

    public static String recorderTimeToText(int i) {
        int i2 = i / 60;
        int i3 = i % 60;
        return i2 == 0 ? Utils.obtainLocalInteger(i3) + "\"" : Utils.obtainLocalInteger(i2) + "'" + Utils.obtainLocalInteger(i3) + "\"";
    }

    public static String recorderTimeToFormatText(int i) {
        return String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(i / 60)) + ":" + String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(i % 60));
    }

    public static int characterNum(int i) {
        int digitCountOfNumber;
        int i2 = i / 60;
        int i3 = i % 60;
        if (i2 > 0) {
            digitCountOfNumber = i2 < 10 ? 1 : getDigitCountOfNumber(i2);
        } else {
            digitCountOfNumber = 0;
        }
        return i3 < 10 ? digitCountOfNumber + 1 : digitCountOfNumber + 2;
    }

    public static int getDigitCountOfNumber(int i) {
        if (i < 0) {
            return getDigitCountOfNumber(-i);
        }
        return ((int) Math.log10(i)) + 1;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mRectWidth;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mRectHeight;
    }

    public static BitmapDrawable getBitmapDrawable(Context context, WaveBubbleDrawable waveBubbleDrawable) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(waveBubbleDrawable.getIntrinsicWidth(), waveBubbleDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        waveBubbleDrawable.draw(new Canvas(bitmapCreateBitmap));
        return new BitmapDrawable(context.getResources(), bitmapCreateBitmap);
    }

    public class Style {
        public int bgColor;
        public int contentColor;
        public int themeId;
        public int trashUnselectedColor;
        public int waveUnselectedColor;

        public Style(int i) {
            this.themeId = i;
            init(i);
        }

        private void init(int i) {
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 8:
                case 10:
                case 11:
                    this.contentColor = ContextCompat.getColor(BubbleDrawable.this.mContext, R.color.audio_bubble_content_color_light);
                    this.waveUnselectedColor = ContextCompat.getColor(BubbleDrawable.this.mContext, R.color.audio_bubble_wave_unselected_color_light);
                    this.bgColor = ContextCompat.getColor(BubbleDrawable.this.mContext, R.color.audio_bubble_bg_color_light);
                    this.trashUnselectedColor = ContextCompat.getColor(BubbleDrawable.this.mContext, R.color.audio_bubble_wave_unselected_color_light);
                    return;
                case 5:
                case 7:
                    this.contentColor = ContextCompat.getColor(BubbleDrawable.this.mContext, R.color.audio_bubble_content_color_candy);
                    this.waveUnselectedColor = ContextCompat.getColor(BubbleDrawable.this.mContext, R.color.audio_bubble_wave_unselected_color_candy);
                    this.bgColor = ContextCompat.getColor(BubbleDrawable.this.mContext, R.color.audio_bubble_bg_color_candy);
                    this.trashUnselectedColor = ContextCompat.getColor(BubbleDrawable.this.mContext, R.color.audio_bubble_wave_unselected_color_candy);
                    return;
                case 6:
                case 9:
                    this.contentColor = ContextCompat.getColor(BubbleDrawable.this.mContext, R.color.audio_bubble_content_color_dark);
                    this.waveUnselectedColor = ContextCompat.getColor(BubbleDrawable.this.mContext, R.color.audio_bubble_wave_unselected_color_dark);
                    this.bgColor = ContextCompat.getColor(BubbleDrawable.this.mContext, R.color.audio_bubble_bg_color_dark);
                    this.trashUnselectedColor = ContextCompat.getColor(BubbleDrawable.this.mContext, R.color.audio_bubble_wave_unselected_color_dark);
                    break;
                case 12:
                case 13:
                case 14:
                    break;
                default:
                    this.contentColor = ContextCompat.getColor(BubbleDrawable.this.mContext, R.color.audio_bubble_content_color);
                    this.waveUnselectedColor = ContextCompat.getColor(BubbleDrawable.this.mContext, R.color.audio_bubble_wave_unselected_color_light);
                    this.bgColor = ContextCompat.getColor(BubbleDrawable.this.mContext, R.color.audio_bubble_bg_color);
                    this.trashUnselectedColor = ContextCompat.getColor(BubbleDrawable.this.mContext, R.color.audio_bubble_wave_unselected_color_light);
                    return;
            }
            this.contentColor = ContextCompat.getColor(BubbleDrawable.this.mContext, R.color.audio_bubble_content_color_dark);
            this.waveUnselectedColor = ContextCompat.getColor(BubbleDrawable.this.mContext, R.color.audio_bubble_wave_unselected_color_dark);
            this.bgColor = ContextCompat.getColor(BubbleDrawable.this.mContext, R.color.audio_bubble_bg_color_dark);
            this.trashUnselectedColor = ContextCompat.getColor(BubbleDrawable.this.mContext, R.color.audio_bubble_wave_unselected_color_dark);
        }
    }
}
