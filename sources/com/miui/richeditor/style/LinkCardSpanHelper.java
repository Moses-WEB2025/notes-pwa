package com.miui.richeditor.style;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.StaticLayout;
import android.text.TextPaint;
import androidx.core.content.ContextCompat;
import com.miui.common.tool.Utils;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.theme.util.ResourceManager;
import com.miui.richeditor.theme.Theme;
import com.miui.richeditor.util.SmoothDrawHelper;

/* JADX INFO: loaded from: classes4.dex */
public class LinkCardSpanHelper {
    protected Bitmap mBgBm;
    protected Drawable mBgDrawable;
    protected Paint mImageSelectBorderPaint;
    protected TextPaint mLinkCardWebLinkPaint;
    protected TextPaint mLinkCardWebTitlePaint;
    protected Drawable mLinkIconDrawable;
    protected SmoothDrawHelper mSmoothDrawHelper;
    protected Theme mTheme;
    protected Rect mLinkIconRect = new Rect();
    protected RectF mRoundRectF = new RectF();
    protected Path mRoundPath = new Path();
    protected int mSpanPadding = 0;
    protected int mLinkMarginStart = 0;
    protected int mLinkMarginTop = 0;
    protected int mRadius = 0;
    protected float mBorderWidth = 0.0f;
    protected Canvas mCanvas = new Canvas();

    public void drawWebContent(String str, Canvas canvas, float f, int i) {
    }

    public void drawWebIcon(Canvas canvas, float f, int i) {
    }

    public LinkCardSpanHelper(Theme theme) {
        this.mTheme = theme;
        TextPaint textPaint = new TextPaint();
        this.mLinkCardWebTitlePaint = textPaint;
        textPaint.setAntiAlias(true);
        this.mLinkCardWebTitlePaint.setStyle(Paint.Style.FILL);
        TextPaint textPaint2 = new TextPaint();
        this.mLinkCardWebLinkPaint = textPaint2;
        textPaint2.setAntiAlias(true);
        this.mLinkCardWebLinkPaint.setStyle(Paint.Style.FILL);
        this.mSmoothDrawHelper = new SmoothDrawHelper();
        Paint paint = new Paint();
        this.mImageSelectBorderPaint = paint;
        paint.setAntiAlias(true);
        this.mImageSelectBorderPaint.setColor(ContextCompat.getColor(getContext(), R.color.smartimagespan_border_color_s));
        this.mImageSelectBorderPaint.setStyle(Paint.Style.STROKE);
        initDrawSize();
        updateStyle();
    }

    protected void initDrawSize() {
        this.mLinkCardWebTitlePaint.setTextSize(getContext().getResources().getDimensionPixelSize(R.dimen.link_card_span_title_text_size));
        this.mLinkCardWebLinkPaint.setTextSize(getContext().getResources().getDimensionPixelSize(R.dimen.link_card_span_link_text_size));
        this.mSpanPadding = getContext().getResources().getDimensionPixelSize(R.dimen.link_card_span_padding);
        this.mLinkMarginStart = getContext().getResources().getDimensionPixelSize(R.dimen.link_card_span_link_margin_start);
        this.mLinkMarginTop = getContext().getResources().getDimensionPixelSize(R.dimen.link_card_span_link_margin_top);
        this.mBorderWidth = 4.0f;
        this.mRadius = getContext().getResources().getDimensionPixelSize(R.dimen.link_card_span_radius);
        this.mImageSelectBorderPaint.setStrokeWidth(this.mBorderWidth);
    }

    public void setImageRadius(int i) {
        this.mRadius = i;
    }

    protected Context getContext() {
        return NoteApp.getInstance();
    }

    public void updateTheme(Theme theme) {
        Theme theme2 = this.mTheme;
        if (theme2 == null || theme2.getId() != theme.getId()) {
            this.mTheme = theme;
            updateStyle();
        }
    }

    protected void updateStyle() {
        Theme theme = this.mTheme;
        if (theme == null) {
            theme = ResourceManager.getTheme(0);
        }
        this.mLinkCardWebTitlePaint.setColor(theme.getLinkCardStyle(getContext()).getTitleColor());
        this.mLinkCardWebLinkPaint.setColor(theme.getLinkCardStyle(getContext()).getLinkColor());
        Drawable linkIcon = theme.getLinkCardStyle(getContext()).getLinkIcon();
        this.mLinkIconDrawable = linkIcon;
        linkIcon.setAlpha(204);
        this.mBgDrawable = getContext().getResources().getDrawable(R.drawable.link_card_bg, theme.getLinkCardStyle(getContext()).getResTheme());
    }

    public TextPaint getTitlePaint() {
        return this.mLinkCardWebTitlePaint;
    }

    public TextPaint getLinkPaint() {
        return this.mLinkCardWebLinkPaint;
    }

    public int getTextContentWidth(Rect rect) {
        return (rect.width() - (this.mSpanPadding * 2)) - this.mLinkMarginStart;
    }

    public void drawBg(Rect rect, Canvas canvas, boolean z, float f, int i) {
        canvas.save();
        int i2 = rect.left;
        rect.offset(-i2, 0);
        this.mBgDrawable.setAlpha(i);
        this.mBgDrawable.setBounds(rect);
        this.mBgDrawable.draw(canvas);
        if (z) {
            this.mRoundPath.reset();
            this.mRoundRectF.set(rect.left + (this.mBorderWidth / 2.0f), rect.top + (this.mBorderWidth / 2.0f), rect.right - (this.mBorderWidth / 2.0f), rect.bottom - (this.mBorderWidth / 2.0f));
            Path path = this.mRoundPath;
            RectF rectF = this.mRoundRectF;
            int i3 = this.mRadius;
            path.addRoundRect(rectF, i3, i3, Path.Direction.CW);
            this.mImageSelectBorderPaint.setAlpha(i);
            canvas.drawPath(this.mRoundPath, this.mImageSelectBorderPaint);
        }
        rect.offset(i2, 0);
        canvas.restore();
    }

    public void drawSpanIcon(Rect rect, Canvas canvas, float f, int i) {
        canvas.save();
        this.mLinkIconRect.left = (int) (this.mSpanPadding * f);
        this.mLinkIconRect.top = rect.top + ((int) (this.mSpanPadding * f));
        this.mLinkIconRect.right = (int) ((this.mSpanPadding + this.mLinkIconDrawable.getIntrinsicWidth()) * f);
        this.mLinkIconRect.bottom = rect.top + ((int) ((this.mSpanPadding + this.mLinkIconDrawable.getIntrinsicHeight()) * f));
        if (Utils.isRTL()) {
            this.mLinkIconRect.left = (int) (rect.right - ((this.mSpanPadding + this.mLinkIconDrawable.getIntrinsicWidth()) * f));
            this.mLinkIconRect.right = (int) (rect.right - (this.mLinkIconDrawable.getIntrinsicWidth() * f));
        }
        this.mLinkIconDrawable.setBounds(this.mLinkIconRect);
        int alpha = this.mLinkIconDrawable.getAlpha();
        this.mLinkIconDrawable.setAlpha((i * alpha) / 255);
        this.mLinkIconDrawable.draw(canvas);
        this.mLinkIconDrawable.setAlpha(alpha);
        canvas.restore();
    }

    public void drawWebTitle(StaticLayout staticLayout, Rect rect, Canvas canvas, float f, int i) {
        if (staticLayout == null) {
            Logger.INSTANCE.e("LinkHelper", "textLayout == null ");
            return;
        }
        canvas.save();
        Logger.INSTANCE.i("LinkHelper", "drawWebTitle  rect.top  = " + rect.top + " rect.bottom = " + rect.bottom);
        int i2 = (int) ((this.mSpanPadding + this.mLinkMarginStart) * f);
        int i3 = rect.top + ((int) (this.mSpanPadding * f));
        if (Utils.isRTL()) {
            i2 = (int) (this.mSpanPadding * f);
        }
        canvas.translate(i2, i3);
        canvas.scale(f, f);
        staticLayout.getPaint().setAlpha(i);
        staticLayout.draw(canvas);
        canvas.restore();
    }

    public void drawWebLink(StaticLayout staticLayout, Rect rect, Canvas canvas, float f, int i) {
        canvas.save();
        Logger.INSTANCE.i("LinkHelper", "drawWebLink  rect.top  = " + rect.top + " rect.bottom = " + rect.bottom);
        int i2 = (int) ((this.mSpanPadding + this.mLinkMarginStart) * f);
        int i3 = rect.top + ((int) ((this.mSpanPadding + this.mLinkMarginTop) * f));
        if (Utils.isRTL()) {
            i2 = (int) (this.mSpanPadding * f);
        }
        canvas.translate(i2, i3);
        canvas.scale(f, f);
        int alpha = staticLayout.getPaint().getAlpha();
        staticLayout.getPaint().setAlpha((i * alpha) / 255);
        staticLayout.draw(canvas);
        staticLayout.getPaint().setAlpha(alpha);
        canvas.restore();
    }
}
