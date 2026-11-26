package com.miui.richeditor.style;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.LeadingMarginSpan;
import android.text.style.LineHeightSpan;
import android.text.style.StrikethroughSpan;
import android.view.MotionEvent;
import com.miui.notes.R;
import com.miui.richeditor.IEditorContext;
import com.miui.richeditor.schema.HtmlParser;

/* JADX INFO: loaded from: classes4.dex */
public class LegacyCheckboxSpan extends StrikethroughSpan implements LeadingMarginSpan, LineHeightSpan, HtmlParser.IElementSpan, ClickableElement {
    private static final String TAG = "CheckboxSpan";
    private Rect mBounds;
    private Rect mBoundsInParent;
    private Context mContext;
    protected int mDefaultSize;
    protected IEditorContext mEditorContext;
    private HtmlParser.CheckBoxElement mElement;
    protected int mFontAscent;
    protected int mFontDescent;
    protected int mFontDescentAdd;
    protected float mFontInnerScale;
    protected float mFontScale;
    protected int mFontSizeLevel;
    private float mFontSizeScale;
    private boolean mIsThisLineContainSizeSpan;
    protected int mMarginEnd;
    private Rect mPaddings;
    private boolean mPressed;
    protected float mSpScale;

    @Override // com.miui.richeditor.style.ClickableElement
    public void onLongClick(int i, int i2) {
    }

    @Override // com.miui.richeditor.style.ClickableElement
    public void onRightClick(int i, int i2) {
    }

    @Override // com.miui.richeditor.style.ClickableElement
    public void onSelected(boolean z) {
    }

    @Override // com.miui.richeditor.style.ClickableElement
    public void onTouchPoint(int i, int i2, MotionEvent motionEvent) {
    }

    @Override // com.miui.richeditor.style.ClickableElement
    public void setSelected(boolean z) {
    }

    public LegacyCheckboxSpan(IEditorContext iEditorContext, boolean z) {
        this(iEditorContext.getContext(), iEditorContext, new HtmlParser.CheckBoxElement(z));
    }

    public LegacyCheckboxSpan(Context context, IEditorContext iEditorContext, boolean z) {
        this(context, iEditorContext, new HtmlParser.CheckBoxElement(z));
    }

    public LegacyCheckboxSpan(IEditorContext iEditorContext, HtmlParser.CheckBoxElement checkBoxElement) {
        this(iEditorContext.getContext(), iEditorContext, checkBoxElement);
    }

    public LegacyCheckboxSpan(Context context, IEditorContext iEditorContext, HtmlParser.CheckBoxElement checkBoxElement) {
        this.mFontSizeScale = 1.0f;
        this.mFontSizeLevel = 0;
        this.mSpScale = 1.0f;
        this.mMarginEnd = 0;
        this.mDefaultSize = 48;
        this.mFontScale = 1.0f;
        this.mFontInnerScale = 1.0f;
        this.mFontDescent = 0;
        this.mFontAscent = 0;
        this.mFontDescentAdd = 0;
        this.mContext = context;
        this.mEditorContext = iEditorContext;
        this.mElement = checkBoxElement;
        this.mBounds = new Rect();
        this.mBoundsInParent = new Rect();
        this.mPaddings = new Rect();
        this.mSpScale = this.mContext.getResources().getDisplayMetrics().scaledDensity / this.mContext.getResources().getDisplayMetrics().density;
        this.mMarginEnd = this.mContext.getResources().getDimensionPixelSize(R.dimen.note_edit_checkbox_margin_end);
        this.mDefaultSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.ic_48px_size);
        updateFontScaleParams();
        updateFontParams();
    }

    public Context getContext() {
        return this.mContext;
    }

    @Override // com.miui.richeditor.schema.HtmlParser.IElementSpan
    public HtmlParser.CheckBoxElement getElement() {
        return this.mElement;
    }

    @Override // com.miui.richeditor.style.ClickableElement
    public boolean isTouchIn(int i, int i2) {
        if (this.mEditorContext.isReadOnlyMode()) {
            return false;
        }
        return this.mBounds.contains(i - this.mEditorContext.getView().getCompoundPaddingLeft(), (i2 + this.mEditorContext.getView().getScrollY()) - this.mEditorContext.getView().getCompoundPaddingTop());
    }

    @Override // com.miui.richeditor.style.ClickableElement
    public void setPressed(boolean z) {
        if (this.mPressed != z) {
            this.mPressed = z;
        }
    }

    @Override // com.miui.richeditor.style.ClickableElement
    public void onClick(int i, int i2) {
        HtmlParser.CheckBoxElement checkBoxElement = this.mElement;
        checkBoxElement.setChecked(!checkBoxElement.isChecked());
        this.mEditorContext.onCheckboxClick(new int[]{i, i2});
    }

    @Override // android.text.style.LeadingMarginSpan
    public int getLeadingMargin(boolean z) {
        return getImageSize() + this.mMarginEnd;
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

    @Override // android.text.style.LeadingMarginSpan
    public void drawLeadingMargin(Canvas canvas, Paint paint, int i, int i2, int i3, int i4, int i5, CharSequence charSequence, int i6, int i7, boolean z, Layout layout) {
        if (z) {
            Drawable checkboxDrawable = this.mEditorContext.getImageRender().getCheckboxDrawable();
            int imageSize = getImageSize();
            int imageSize2 = getImageSize();
            if (i2 == -1) {
                this.mBounds.right = i + this.mPaddings.right;
                Rect rect = this.mBounds;
                rect.left = rect.right - imageSize;
            } else {
                this.mBounds.left = i - this.mPaddings.left;
                Rect rect2 = this.mBounds;
                rect2.right = rect2.left + imageSize;
            }
            this.mBounds.bottom = (int) (i4 + ((imageSize2 * paint.getFontMetrics().descent) / (paint.getFontMetrics().descent - paint.getFontMetrics().ascent)));
            Rect rect3 = this.mBounds;
            rect3.top = rect3.bottom - imageSize2;
            this.mBoundsInParent.set(this.mBounds);
            this.mBoundsInParent.offset(this.mEditorContext.getView().getPaddingStart(), 0);
            checkboxDrawable.setState(getDrawableState());
            checkboxDrawable.setBounds(this.mBounds);
            checkboxDrawable.draw(canvas);
        }
    }

    @Override // android.text.style.StrikethroughSpan, android.text.style.CharacterStyle
    public void updateDrawState(TextPaint textPaint) {
        textPaint.setStrikeThruText(this.mElement.isChecked());
    }

    protected int getImageSize() {
        return (int) (this.mDefaultSize * this.mEditorContext.getFontInnerScale() * this.mSpScale);
    }

    private int[] getDrawableState() {
        int[] pressedEnabledStateSet = this.mPressed ? this.mEditorContext.getPressedEnabledStateSet() : this.mEditorContext.getEnabledStateSet();
        if (!this.mElement.isChecked()) {
            return pressedEnabledStateSet;
        }
        int[] iArr = new int[pressedEnabledStateSet.length + 1];
        System.arraycopy(pressedEnabledStateSet, 0, iArr, 0, pressedEnabledStateSet.length);
        iArr[pressedEnabledStateSet.length] = 16842912;
        return iArr;
    }

    public void updateFontParams() {
        updateFontScaleParams();
        updateFontMetrics();
    }

    protected void updateFontScaleParams() {
        this.mFontScale = this.mContext.getResources().getDisplayMetrics().scaledDensity;
        float fontInnerScale = this.mEditorContext.getFontInnerScale();
        this.mFontInnerScale = fontInnerScale;
        this.mFontDescentAdd = (int) Math.round(((double) this.mFontScale) * 7.28d * ((double) fontInnerScale));
    }

    protected void updateFontMetrics() {
        int i = this.mFontSizeLevel;
        if (i == 0) {
            this.mFontDescent = (int) Math.round(((double) this.mFontScale) * 8.36d * ((double) this.mFontInnerScale));
            this.mFontAscent = -((int) Math.round(((double) this.mFontScale) * 20.72d * ((double) this.mFontInnerScale)));
            return;
        }
        if (i == 1) {
            this.mFontDescent = (int) Math.round(((double) this.mFontScale) * 8.54d * ((double) this.mFontInnerScale));
            this.mFontAscent = -Math.round(this.mFontScale * 22.0f * this.mFontInnerScale);
        } else if (i == 2) {
            this.mFontDescent = (int) Math.round(((double) this.mFontScale) * 8.91d * ((double) this.mFontInnerScale));
            this.mFontAscent = -((int) Math.round(((double) this.mFontScale) * 24.55d * ((double) this.mFontInnerScale)));
        } else {
            if (i != 3) {
                return;
            }
            this.mFontDescent = (int) Math.round(((double) this.mFontScale) * 9.09d * ((double) this.mFontInnerScale));
            this.mFontAscent = -((int) Math.round(((double) this.mFontScale) * 25.22d * ((double) this.mFontInnerScale)));
        }
    }

    @Override // com.miui.richeditor.style.ClickableElement
    public Rect getBoundsInParent() {
        return this.mBoundsInParent;
    }

    @Override // com.miui.richeditor.style.ClickableElement
    public CharSequence getContentDescription() {
        return getContext().getString(R.string.rich_text_bulleted_list_description);
    }
}
