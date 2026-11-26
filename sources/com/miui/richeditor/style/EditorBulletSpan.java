package com.miui.richeditor.style;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.Spanned;
import android.text.style.BulletSpan;
import android.text.style.LeadingMarginSpan;
import android.text.style.LineHeightSpan;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.richeditor.IEditorContext;
import com.miui.richeditor.schema.HtmlParser;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes4.dex */
public class EditorBulletSpan extends BulletSpan implements LeadingMarginSpan, LineHeightSpan, HtmlParser.IElementSpan {
    private Rect mBounds;
    private Context mContext;
    protected WeakReference<IEditorContext> mEditorContext;
    private HtmlParser.BulletElement mElement;
    protected int mFontAscent;
    protected int mFontDescent;
    protected int mFontDescentAdd;
    protected float mFontInnerScale;
    protected float mFontScale;
    protected int mFontSizeLevel;
    private boolean mIsThisLineContainSizeSpan;
    protected int mMarginEnd;
    private Rect mPaddings;
    protected float mSpScale;
    protected int mStandLineHeight;
    protected int mStandParagraphMargin;

    public EditorBulletSpan(IEditorContext iEditorContext) {
        this.mBounds = new Rect();
        this.mPaddings = new Rect();
        this.mFontSizeLevel = 0;
        this.mSpScale = 1.0f;
        this.mMarginEnd = 0;
        this.mStandLineHeight = 0;
        this.mStandParagraphMargin = 0;
        this.mFontScale = 1.0f;
        this.mFontInnerScale = 1.0f;
        this.mFontDescent = 0;
        this.mFontAscent = 0;
        this.mFontDescentAdd = 0;
        this.mContext = NoteApp.getInstance();
        this.mEditorContext = new WeakReference<>(iEditorContext);
        this.mElement = new HtmlParser.BulletElement();
        this.mSpScale = this.mContext.getResources().getDisplayMetrics().scaledDensity / this.mContext.getResources().getDisplayMetrics().density;
        this.mMarginEnd = this.mContext.getResources().getDimensionPixelSize(R.dimen.note_edit_bullet_margin_end);
        this.mStandLineHeight = this.mContext.getResources().getDimensionPixelSize(R.dimen.note_edit_stand_line_height);
        this.mStandParagraphMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.note_edit_stand_paragraph_margin);
        updateFontScaleParams();
        updateFontParams();
    }

    public EditorBulletSpan(IEditorContext iEditorContext, HtmlParser.BulletElement bulletElement) {
        this.mBounds = new Rect();
        this.mPaddings = new Rect();
        this.mFontSizeLevel = 0;
        this.mSpScale = 1.0f;
        this.mMarginEnd = 0;
        this.mStandLineHeight = 0;
        this.mStandParagraphMargin = 0;
        this.mFontScale = 1.0f;
        this.mFontInnerScale = 1.0f;
        this.mFontDescent = 0;
        this.mFontAscent = 0;
        this.mFontDescentAdd = 0;
        this.mContext = NoteApp.getInstance();
        this.mEditorContext = new WeakReference<>(iEditorContext);
        this.mElement = bulletElement;
        this.mSpScale = this.mContext.getResources().getDisplayMetrics().scaledDensity / this.mContext.getResources().getDisplayMetrics().density;
        this.mMarginEnd = this.mContext.getResources().getDimensionPixelSize(R.dimen.note_edit_bullet_margin_end);
        this.mStandLineHeight = this.mContext.getResources().getDimensionPixelSize(R.dimen.note_edit_stand_line_height);
        this.mStandParagraphMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.note_edit_stand_paragraph_margin);
        updateFontScaleParams();
        updateFontMetrics();
    }

    @Override // com.miui.richeditor.schema.HtmlParser.IElementSpan
    public HtmlParser.BulletElement getElement() {
        return this.mElement;
    }

    @Override // android.text.style.BulletSpan, android.text.style.LeadingMarginSpan
    public int getLeadingMargin(boolean z) {
        if (this.mEditorContext.get() == null) {
            return 0;
        }
        return getImageSize(this.mEditorContext.get().getImageRender().getBulletDrawable().getIntrinsicWidth()) + this.mMarginEnd;
    }

    @Override // android.text.style.LineHeightSpan
    public void chooseHeight(CharSequence charSequence, int i, int i2, int i3, int i4, Paint.FontMetricsInt fontMetricsInt) {
        char c;
        Spanned spanned = (Spanned) charSequence;
        char c2 = 0;
        if (i == spanned.getSpanStart(this)) {
            FontLargeSizeSpan[] fontLargeSizeSpanArr = (FontLargeSizeSpan[]) spanned.getSpans(i, i2, FontLargeSizeSpan.class);
            FontMidSizeSpan[] fontMidSizeSpanArr = (FontMidSizeSpan[]) spanned.getSpans(i, i2, FontMidSizeSpan.class);
            if (fontLargeSizeSpanArr.length > 0) {
                c = 2;
            } else {
                c = fontMidSizeSpanArr.length > 0 ? (char) 1 : (char) 0;
            }
            this.mIsThisLineContainSizeSpan = fontLargeSizeSpanArr.length > 0 || fontMidSizeSpanArr.length > 0;
            c2 = c;
        }
        if (c2 != this.mFontSizeLevel) {
            this.mFontSizeLevel = c2;
            updateFontMetrics();
        }
        fontMetricsInt.ascent = this.mFontAscent;
        fontMetricsInt.descent = this.mFontDescent;
        if (charSequence.charAt(i2 - 1) == '\n') {
            fontMetricsInt.descent += this.mFontDescentAdd;
        }
        fontMetricsInt.top = fontMetricsInt.ascent;
        fontMetricsInt.bottom = fontMetricsInt.descent;
    }

    @Override // android.text.style.BulletSpan, android.text.style.LeadingMarginSpan
    public void drawLeadingMargin(Canvas canvas, Paint paint, int i, int i2, int i3, int i4, int i5, CharSequence charSequence, int i6, int i7, boolean z, Layout layout) {
        if (z && this.mEditorContext.get() != null) {
            Drawable bulletDrawable = this.mEditorContext.get().getImageRender().getBulletDrawable();
            int imageSize = getImageSize(bulletDrawable.getIntrinsicWidth());
            int imageSize2 = getImageSize(bulletDrawable.getIntrinsicHeight());
            if (i2 == -1) {
                this.mBounds.right = i + this.mPaddings.right;
                Rect rect = this.mBounds;
                rect.left = rect.right - imageSize;
            } else {
                this.mBounds.left = i - this.mPaddings.left;
                Rect rect2 = this.mBounds;
                rect2.right = rect2.left + imageSize;
            }
            this.mBounds.bottom = (int) (i4 + (imageSize2 * 0.15f));
            Rect rect3 = this.mBounds;
            rect3.top = rect3.bottom - imageSize2;
            bulletDrawable.setState(this.mEditorContext.get().getView().getDrawableState());
            bulletDrawable.setBounds(this.mBounds);
            bulletDrawable.draw(canvas);
        }
    }

    protected Context getContext() {
        return this.mContext;
    }

    protected int getImageSize(int i) {
        if (this.mEditorContext.get() == null) {
            return 0;
        }
        return (int) (i * this.mEditorContext.get().getFontInnerScale() * this.mSpScale);
    }

    public void updateFontParams() {
        updateFontScaleParams();
        updateFontMetrics();
    }

    protected void updateFontScaleParams() {
        if (this.mEditorContext.get() == null) {
            return;
        }
        this.mFontScale = this.mContext.getResources().getDisplayMetrics().scaledDensity;
        this.mFontInnerScale = this.mEditorContext.get().getFontInnerScale();
        this.mFontDescentAdd = this.mStandParagraphMargin;
    }

    protected void updateFontMetrics() {
        float f;
        float f2;
        int i = this.mStandLineHeight;
        int i2 = this.mFontSizeLevel;
        if (i2 == 1) {
            f = i * EditLineHeightSpan.FONT_MIDDLE_LINE_HEIGHT_RATIO * this.mFontScale;
            f2 = this.mFontInnerScale;
        } else if (i2 == 2) {
            f = i * EditLineHeightSpan.FONT_LARGE_LINE_HEIGHT_RATIO * this.mFontScale;
            f2 = this.mFontInnerScale;
        } else {
            f = i * this.mFontScale;
            f2 = this.mFontInnerScale;
        }
        int i3 = (int) (f * f2);
        int iFloor = (int) Math.floor(i3 * EditLineHeightSpan.FONT_BASE_LINE_RATIO);
        this.mFontDescent = iFloor;
        this.mFontAscent = iFloor - i3;
    }
}
