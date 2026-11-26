package com.miui.richeditor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;

/* JADX INFO: loaded from: classes4.dex */
public class MindDrawable extends Drawable {
    private int mBackgroundColor;
    private int mBackgroundStrokeColor;
    protected int mBubbleHeight;
    protected int mBubblePaddingBottom;
    protected int mBubblePaddingEnd;
    protected int mBubblePaddingStart;
    protected int mBubblePaddingTop;
    protected int mBubbleRadius;
    protected int mBubbleWidth;
    private int mContentColor;
    private Context mContext;
    private Paint.FontMetricsInt mFontMetricsInt;
    protected Bitmap mIconBitmap;
    protected int mIconToLeftBound;
    private Paint mPaint = new Paint(1);
    protected int mRectHeight;
    protected int mRectWidth;
    private Paint mTextPaint;
    protected int mTextSize;
    protected int mTextToLeftBound;
    private int mThemeId;

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    public MindDrawable(Context context, int i, int i2) {
        this.mContext = context;
        this.mThemeId = i;
        this.mBubbleWidth = i2;
        initParam(context);
    }

    private void initParam(Context context) {
        initDimenParam(context);
        this.mContentColor = this.mContext.getResources().getColor(R.color.note_mind_entrance_text_color);
        this.mBackgroundColor = this.mContext.getResources().getColor(R.color.note_mind_entrance_bg_color);
        this.mBackgroundStrokeColor = this.mContext.getResources().getColor(R.color.note_mind_entrance_bg_stroke_color);
        switch (this.mThemeId) {
            case 1:
                this.mContentColor = this.mContext.getResources().getColor(R.color.note_mind_entrance_text_color_blue);
                this.mBackgroundColor = this.mContext.getResources().getColor(R.color.note_mind_entrance_bg_color_blue);
                this.mBackgroundStrokeColor = this.mContext.getResources().getColor(R.color.note_mind_entrance_bg_color_blue_stroke);
                break;
            case 2:
                this.mContentColor = this.mContext.getResources().getColor(R.color.note_mind_entrance_text_color_yellow);
                this.mBackgroundColor = this.mContext.getResources().getColor(R.color.note_mind_entrance_bg_color_yellow);
                this.mBackgroundStrokeColor = this.mContext.getResources().getColor(R.color.note_mind_entrance_bg_color_yellow_stroke);
                break;
            case 3:
                this.mContentColor = this.mContext.getResources().getColor(R.color.note_mind_entrance_text_color_green);
                this.mBackgroundColor = this.mContext.getResources().getColor(R.color.note_mind_entrance_bg_color_green);
                this.mBackgroundStrokeColor = this.mContext.getResources().getColor(R.color.note_mind_entrance_bg_color_green_stroke);
                break;
            case 4:
                this.mContentColor = this.mContext.getResources().getColor(R.color.note_mind_entrance_text_color_red);
                this.mBackgroundColor = this.mContext.getResources().getColor(R.color.note_mind_entrance_bg_color_red);
                this.mBackgroundStrokeColor = this.mContext.getResources().getColor(R.color.note_mind_entrance_bg_color_red_stroke);
                break;
            case 5:
                this.mContentColor = this.mContext.getResources().getColor(R.color.note_mind_entrance_text_color_candy);
                this.mBackgroundColor = this.mContext.getResources().getColor(R.color.note_mind_entrance_bg_color_candy);
                this.mBackgroundStrokeColor = this.mContext.getResources().getColor(R.color.note_mind_entrance_bg_color_candy_stroke);
                break;
            case 6:
                this.mContentColor = this.mContext.getResources().getColor(R.color.note_mind_entrance_text_color_dark);
                this.mBackgroundColor = this.mContext.getResources().getColor(R.color.note_mind_entrance_bg_color_dark);
                this.mBackgroundStrokeColor = this.mContext.getResources().getColor(R.color.note_mind_entrance_bg_color_dark_stroke);
                break;
            case 7:
                this.mContentColor = this.mContext.getResources().getColor(R.color.note_mind_entrance_text_color_antique);
                this.mBackgroundColor = this.mContext.getResources().getColor(R.color.note_mind_entrance_bg_color_antique);
                this.mBackgroundStrokeColor = this.mContext.getResources().getColor(R.color.note_mind_entrance_bg_color_antique_stroke);
                break;
            case 8:
                this.mContentColor = this.mContext.getResources().getColor(R.color.note_mind_entrance_text_color_arty);
                this.mBackgroundColor = this.mContext.getResources().getColor(R.color.note_mind_entrance_bg_color_arty);
                this.mBackgroundStrokeColor = this.mContext.getResources().getColor(R.color.note_mind_entrance_bg_color_arty_stroke);
                break;
            case 9:
                this.mContentColor = this.mContext.getResources().getColor(R.color.note_mind_entrance_text_color_star);
                this.mBackgroundColor = this.mContext.getResources().getColor(R.color.note_mind_entrance_bg_color_star);
                this.mBackgroundStrokeColor = this.mContext.getResources().getColor(R.color.note_mind_entrance_bg_color_star_stroke);
                break;
            case 10:
                this.mContentColor = this.mContext.getResources().getColor(R.color.note_mind_entrance_text_color_light);
                this.mBackgroundColor = this.mContext.getResources().getColor(R.color.note_mind_entrance_bg_color_light);
                this.mBackgroundStrokeColor = this.mContext.getResources().getColor(R.color.note_mind_entrance_bg_color_light_stroke);
                break;
            case 11:
                this.mContentColor = this.mContext.getResources().getColor(R.color.note_mind_entrance_text_color_shadow);
                this.mBackgroundColor = this.mContext.getResources().getColor(R.color.note_mind_entrance_bg_color_shadow);
                this.mBackgroundStrokeColor = this.mContext.getResources().getColor(R.color.note_mind_entrance_bg_color_shadow_stroke);
                break;
            case 12:
                this.mContentColor = this.mContext.getResources().getColor(R.color.note_mind_entrance_text_color_leaf);
                this.mBackgroundColor = this.mContext.getResources().getColor(R.color.note_mind_entrance_bg_color_leaf);
                this.mBackgroundStrokeColor = this.mContext.getResources().getColor(R.color.note_mind_entrance_bg_color_leaf_stroke);
                break;
            case 13:
                this.mContentColor = this.mContext.getResources().getColor(R.color.note_mind_entrance_text_color_nightsky);
                this.mBackgroundColor = this.mContext.getResources().getColor(R.color.note_mind_entrance_bg_color_nightsky);
                this.mBackgroundStrokeColor = this.mContext.getResources().getColor(R.color.note_mind_entrance_bg_color_nightsky_stroke);
                break;
            case 14:
                this.mContentColor = this.mContext.getResources().getColor(R.color.note_mind_entrance_text_color_geometry);
                this.mBackgroundColor = this.mContext.getResources().getColor(R.color.note_mind_entrance_bg_color_geometry);
                this.mBackgroundStrokeColor = this.mContext.getResources().getColor(R.color.note_mind_entrance_bg_color_geometry_stroke);
                break;
        }
        if (UIUtils.isPcDensity(context)) {
            this.mIconBitmap = createMindIcon(R.drawable.pc_ic_note_mind_entrance);
        } else {
            this.mIconBitmap = createMindIcon(R.drawable.ic_note_mind_outline_entrance);
        }
        Paint paint = new Paint();
        this.mTextPaint = paint;
        paint.setAntiAlias(true);
        this.mTextPaint.setColor(this.mContentColor);
        this.mTextPaint.setTextSize(this.mTextSize);
        this.mFontMetricsInt = this.mTextPaint.getFontMetricsInt();
    }

    protected void initDimenParam(Context context) {
        this.mTextSize = (int) context.getResources().getDimension(R.dimen.mind_entrance_text_size);
        this.mBubbleRadius = (int) context.getResources().getDimension(R.dimen.mind_entrance_radius);
        this.mRectHeight = (int) context.getResources().getDimension(R.dimen.mind_entrance_height);
        this.mBubblePaddingStart = (int) context.getResources().getDimension(R.dimen.mind_entrance_padding_start);
        this.mBubblePaddingEnd = (int) context.getResources().getDimension(R.dimen.mind_entrance_padding_end);
        this.mTextToLeftBound = (int) context.getResources().getDimension(R.dimen.mind_entrance_text_padding_start);
        if (UIUtils.isPcDensity(context)) {
            this.mBubblePaddingEnd = (int) context.getResources().getDimension(R.dimen.pc_mind_entrance_padding_end);
            this.mTextToLeftBound = (int) context.getResources().getDimension(R.dimen.pc_mind_entrance_text_padding_start);
        }
        int i = this.mBubbleWidth;
        int i2 = this.mBubbleRadius * 2;
        int i3 = this.mBubblePaddingStart;
        int i4 = i + i2 + i3 + this.mBubblePaddingEnd;
        this.mBubbleWidth = i4;
        this.mBubbleHeight = this.mBubblePaddingTop + this.mRectHeight + this.mBubblePaddingBottom;
        this.mRectWidth = i4 + i3;
        this.mIconToLeftBound = (int) context.getResources().getDimension(R.dimen.mind_entrance_icon_padding_start);
    }

    protected Bitmap createMindIcon(int i) {
        Drawable drawable = ContextCompat.getDrawable(this.mContext, i);
        drawable.setTint(this.mContentColor);
        return UIUtils.drawableToBitmap(drawable);
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        float f = getBounds().top;
        drawRoundRect(canvas, f);
        drawMindIcon(canvas, f);
        drawText(canvas, f);
    }

    protected void drawRoundRect(Canvas canvas, float f) {
        int i = this.mBubblePaddingStart;
        int i2 = this.mBubblePaddingTop;
        RectF rectF = new RectF(i, i2, i + this.mBubbleWidth, i2 + this.mRectHeight);
        int i3 = this.mBubblePaddingStart;
        int i4 = this.mBubblePaddingTop;
        RectF rectF2 = new RectF(i3 + 1, i4 + 1, (i3 + this.mBubbleWidth) - 1, (i4 + this.mRectHeight) - 1);
        rectF.offset(0.0f, f);
        rectF2.offset(0.0f, f);
        Paint paint = new Paint(1);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(this.mBackgroundStrokeColor);
        paint.setStrokeWidth(1.0f);
        int i5 = this.mBubbleRadius;
        canvas.drawRoundRect(rectF2, i5, i5, paint);
        this.mPaint.setColor(this.mBackgroundColor);
        int i6 = this.mBubbleRadius;
        canvas.drawRoundRect(rectF, i6, i6, this.mPaint);
    }

    public static BitmapDrawable getBitmapDrawable(Context context, MindDrawable mindDrawable) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(mindDrawable.getIntrinsicWidth(), mindDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        mindDrawable.draw(new Canvas(bitmapCreateBitmap));
        return new BitmapDrawable(context.getResources(), bitmapCreateBitmap);
    }

    private void drawText(Canvas canvas, float f) {
        String string = this.mContext.getResources().getString(R.string.note_mindnote_entrance_icon_text_newversion);
        this.mTextPaint.measureText(string);
        canvas.drawText(string, this.mTextToLeftBound + this.mBubblePaddingStart, f + this.mBubblePaddingTop + (this.mBubbleHeight * 0.5f) + UIUtils.dip2px(this.mContext, 1.0f) + this.mFontMetricsInt.bottom, this.mTextPaint);
    }

    private void drawMindIcon(Canvas canvas, float f) {
        Bitmap bitmap = this.mIconBitmap;
        if (bitmap == null || bitmap.isRecycled()) {
            if (UIUtils.isPcDensity(this.mContext)) {
                this.mIconBitmap = createMindIcon(R.drawable.pc_ic_note_mind_entrance);
            } else {
                this.mIconBitmap = createMindIcon(R.drawable.ic_note_mind_outline_entrance);
            }
        }
        Paint paint = new Paint();
        Bitmap bitmap2 = this.mIconBitmap;
        canvas.drawBitmap(bitmap2, this.mBubblePaddingStart + this.mIconToLeftBound, f + this.mBubblePaddingTop + ((this.mBubbleHeight - bitmap2.getHeight()) * 0.5f), paint);
        UIUtils.releaseBitmap(this.mIconBitmap);
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

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mRectWidth;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mBubbleHeight;
    }

    public int getBubbleWidth() {
        return this.mBubbleWidth;
    }
}
