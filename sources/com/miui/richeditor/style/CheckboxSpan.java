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
import com.miui.common.tool.Logger;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.richeditor.IEditorContext;
import com.miui.richeditor.schema.HtmlParser;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes4.dex */
public class CheckboxSpan extends StrikethroughSpan implements LeadingMarginSpan, LineHeightSpan, HtmlParser.IElementSpan, ClickableElement {
    private static final String TAG = "CheckboxSpan";
    private Rect mBounds;
    private Rect mBoundsInParent;
    private Context mContext;
    protected int mDefaultSize;
    protected WeakReference<IEditorContext> mEditorContextWeakReference;
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
    protected int mStandLineHeight;
    protected int mStandParagraphMargin;

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

    public CheckboxSpan(IEditorContext iEditorContext, boolean z) {
        this(iEditorContext, new HtmlParser.CheckBoxElement(z));
    }

    public CheckboxSpan(IEditorContext iEditorContext, HtmlParser.CheckBoxElement checkBoxElement) {
        this.mFontSizeScale = 1.0f;
        this.mFontSizeLevel = 0;
        this.mSpScale = 1.0f;
        this.mMarginEnd = 0;
        this.mDefaultSize = 48;
        this.mStandLineHeight = 0;
        this.mStandParagraphMargin = 0;
        this.mFontScale = 1.0f;
        this.mFontInnerScale = 1.0f;
        this.mFontDescent = 0;
        this.mFontAscent = 0;
        this.mFontDescentAdd = 0;
        this.mContext = NoteApp.getInstance();
        this.mEditorContextWeakReference = new WeakReference<>(iEditorContext);
        this.mElement = checkBoxElement;
        this.mBounds = new Rect();
        this.mBoundsInParent = new Rect();
        this.mPaddings = new Rect();
        this.mSpScale = this.mContext.getResources().getDisplayMetrics().scaledDensity / this.mContext.getResources().getDisplayMetrics().density;
        this.mMarginEnd = this.mContext.getResources().getDimensionPixelSize(R.dimen.note_edit_checkbox_margin_end);
        this.mDefaultSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.checkbox_span_size);
        this.mStandLineHeight = this.mContext.getResources().getDimensionPixelSize(R.dimen.note_edit_stand_line_height);
        this.mStandParagraphMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.note_edit_stand_paragraph_margin);
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
        WeakReference<IEditorContext> weakReference = this.mEditorContextWeakReference;
        IEditorContext iEditorContext = weakReference == null ? null : weakReference.get();
        if (iEditorContext == null || iEditorContext.isReadOnlyMode()) {
            return false;
        }
        return this.mBounds.contains(i - iEditorContext.getView().getCompoundPaddingLeft(), (i2 + iEditorContext.getView().getScrollY()) - iEditorContext.getView().getCompoundPaddingTop());
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
        WeakReference<IEditorContext> weakReference = this.mEditorContextWeakReference;
        IEditorContext iEditorContext = weakReference == null ? null : weakReference.get();
        if (iEditorContext != null) {
            iEditorContext.onCheckboxClick(new int[]{i, i2});
        }
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
            WeakReference<IEditorContext> weakReference = this.mEditorContextWeakReference;
            IEditorContext iEditorContext = weakReference == null ? null : weakReference.get();
            if (iEditorContext == null) {
                return;
            }
            Drawable checkboxDrawable = iEditorContext.getImageRender().getCheckboxDrawable();
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
            this.mBounds.bottom = (int) (i4 + (imageSize2 * 0.15f));
            Rect rect3 = this.mBounds;
            rect3.top = rect3.bottom - imageSize2;
            this.mBoundsInParent.set(this.mBounds);
            this.mBoundsInParent.offset(iEditorContext.getView().getPaddingStart(), 0);
            checkboxDrawable.setState(getDrawableState());
            checkboxDrawable.setBounds(this.mBounds);
            checkboxDrawable.draw(canvas);
        }
    }

    @Override // android.text.style.StrikethroughSpan, android.text.style.CharacterStyle
    public void updateDrawState(TextPaint textPaint) {
        if (this.mElement.isChecked()) {
            textPaint.setAlpha(102);
        } else {
            textPaint.setAlpha(255);
        }
    }

    protected int getImageSize() {
        WeakReference<IEditorContext> weakReference = this.mEditorContextWeakReference;
        IEditorContext iEditorContext = weakReference == null ? null : weakReference.get();
        if (iEditorContext == null) {
            return 0;
        }
        return (int) (this.mDefaultSize * iEditorContext.getFontInnerScale() * this.mSpScale);
    }

    private int[] getDrawableState() {
        WeakReference<IEditorContext> weakReference = this.mEditorContextWeakReference;
        IEditorContext iEditorContext = weakReference == null ? null : weakReference.get();
        if (iEditorContext == null) {
            Logger.INSTANCE.e(TAG, "getDrawableState iEditorContext = null");
            return new int[0];
        }
        int[] pressedEnabledStateSet = this.mPressed ? iEditorContext.getPressedEnabledStateSet() : iEditorContext.getEnabledStateSet();
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
        WeakReference<IEditorContext> weakReference = this.mEditorContextWeakReference;
        IEditorContext iEditorContext = weakReference == null ? null : weakReference.get();
        this.mFontScale = this.mContext.getResources().getDisplayMetrics().scaledDensity;
        if (iEditorContext != null) {
            this.mFontInnerScale = iEditorContext.getFontInnerScale();
        }
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

    @Override // com.miui.richeditor.style.ClickableElement
    public Rect getBoundsInParent() {
        return this.mBoundsInParent;
    }

    @Override // com.miui.richeditor.style.ClickableElement
    public CharSequence getContentDescription() {
        return getContext().getString(R.string.rich_text_bulleted_list_description);
    }
}
