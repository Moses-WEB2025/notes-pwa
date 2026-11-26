package com.miui.richeditor.share.element;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import com.miui.richeditor.style.lite.EditorQuoteLiteSpan;
import com.miui.richeditor.style.lite.FontH3SizeLiteSpan;
import com.miui.richeditor.style.lite.FontLargeSizeLiteSpan;
import com.miui.richeditor.style.lite.FontMidSizeLiteSpan;
import com.miui.richeditor.theme.Theme;

/* JADX INFO: loaded from: classes4.dex */
public class TextElement extends Element {
    public static final int GRAVITY_CENTER = 3;
    public static final int GRAVITY_END = 2;
    public static final int GRAVITY_JUSTIFY = 4;
    public static final int GRAVITY_START = 1;
    private boolean mBold;
    private int mFontColor;
    private float mFontSize;
    private int mGravity;
    private boolean mIsChecked;
    private boolean mIsTitle;
    private float mLineGap = 1.0f;
    private CharSequence mText;

    @Override // com.miui.richeditor.share.element.Element
    public int getType() {
        return 0;
    }

    public static class Builder extends Element.Builder<TextElement> {
        public Builder() {
            this(null);
        }

        protected Builder(TextElement textElement) {
            this(new TextElement(), textElement);
        }

        protected Builder(TextElement textElement, TextElement textElement2) {
            super(textElement, textElement2);
        }

        public Builder setFontSize(float f) {
            ((TextElement) this.iElement).mFontSize = f;
            return this;
        }

        public Builder setFontColor(int i) {
            ((TextElement) this.iElement).mFontColor = i;
            return this;
        }

        public Builder setBold(boolean z) {
            ((TextElement) this.iElement).mBold = z;
            return this;
        }

        public Builder setIsTitle(boolean z) {
            ((TextElement) this.iElement).mIsTitle = z;
            return this;
        }

        public Builder setIsChecked(boolean z) {
            ((TextElement) this.iElement).mIsChecked = z;
            return this;
        }

        public Builder setLineGap(float f) {
            ((TextElement) this.iElement).mLineGap = f;
            return this;
        }

        public Builder setGravity(int i) {
            ((TextElement) this.iElement).mGravity = i;
            return this;
        }

        public Builder setText(CharSequence charSequence) {
            ((TextElement) this.iElement).setText(charSequence);
            return this;
        }

        @Override // com.miui.richeditor.share.element.Element.Builder
        public Builder setMarginLeft(int i) {
            super.setMarginLeft(i);
            return this;
        }

        @Override // com.miui.richeditor.share.element.Element.Builder
        public Builder setMarginTop(int i) {
            super.setMarginTop(i);
            return this;
        }

        @Override // com.miui.richeditor.share.element.Element.Builder
        public Builder setMarginRight(int i) {
            super.setMarginRight(i);
            return this;
        }

        @Override // com.miui.richeditor.share.element.Element.Builder
        public Builder setMarginBottom(int i) {
            super.setMarginBottom(i);
            return this;
        }
    }

    protected TextElement() {
    }

    public float getFontSize() {
        return this.mFontSize;
    }

    public int getFontColor() {
        if (this.mIsChecked) {
            return (this.mFontColor & 16777215) + 1711276032;
        }
        return this.mFontColor;
    }

    public boolean isBold() {
        return this.mBold;
    }

    public float getLineGap() {
        return this.mLineGap;
    }

    public int getGravity() {
        return this.mGravity;
    }

    public boolean isTitle() {
        return this.mIsTitle;
    }

    public boolean isChecked() {
        return this.mIsChecked;
    }

    public CharSequence getText() {
        return this.mText;
    }

    public void updateHighLightSpan(Context context, Theme theme) {
        CharSequence charSequence = this.mText;
        if (charSequence instanceof SpannableStringBuilder) {
            for (BackgroundColorSpan backgroundColorSpan : (BackgroundColorSpan[]) ((SpannableStringBuilder) charSequence).getSpans(0, charSequence.length(), BackgroundColorSpan.class)) {
                int spanStart = ((SpannableStringBuilder) this.mText).getSpanStart(backgroundColorSpan);
                int spanEnd = ((SpannableStringBuilder) this.mText).getSpanEnd(backgroundColorSpan);
                ((SpannableStringBuilder) this.mText).removeSpan(backgroundColorSpan);
                ((SpannableStringBuilder) this.mText).setSpan(new BackgroundColorSpan(context.getColor(theme.getEditorStyle(context).mBgHighLightColor)), spanStart, spanEnd, 33);
            }
        }
    }

    public void setText(CharSequence charSequence) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(charSequence);
        for (URLSpan uRLSpan : (URLSpan[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), URLSpan.class)) {
            spannableStringBuilder.removeSpan(uRLSpan);
        }
        this.mText = spannableStringBuilder;
    }

    public void setIsChecked(boolean z) {
        this.mIsChecked = z;
    }

    @Override // com.miui.richeditor.share.element.Element
    public Builder edit() {
        return new Builder(this);
    }

    @Override // com.miui.richeditor.share.element.Element
    protected void copyFrom(Element element) {
        super.copyFrom(element);
        TextElement textElement = (TextElement) element;
        this.mFontSize = textElement.mFontSize;
        this.mFontColor = textElement.mFontColor;
        this.mBold = textElement.mBold;
        this.mLineGap = textElement.mLineGap;
        this.mGravity = textElement.mGravity;
        this.mIsTitle = textElement.mIsTitle;
        this.mIsChecked = textElement.mIsChecked;
        this.mText = textElement.mText;
    }

    public boolean isCenter() {
        CharSequence charSequence = this.mText;
        return (charSequence instanceof SpannableStringBuilder) && ((AlignmentSpan[]) ((SpannableStringBuilder) charSequence).getSpans(0, charSequence.length(), AlignmentSpan.class)).length > 0;
    }

    public boolean isBgHighLight() {
        CharSequence charSequence = this.mText;
        return (charSequence instanceof SpannableStringBuilder) && ((BackgroundColorSpan[]) ((SpannableStringBuilder) charSequence).getSpans(0, charSequence.length(), BackgroundColorSpan.class)).length > 0;
    }

    public boolean isContainRichText() {
        CharSequence charSequence = this.mText;
        if (!(charSequence instanceof SpannableStringBuilder)) {
            return false;
        }
        SpannableStringBuilder spannableStringBuilder = (SpannableStringBuilder) charSequence;
        int length = charSequence.length();
        return ((StyleSpan[]) spannableStringBuilder.getSpans(0, length, StyleSpan.class)).length > 0 || ((FontLargeSizeLiteSpan[]) spannableStringBuilder.getSpans(0, length, FontLargeSizeLiteSpan.class)).length > 0 || ((FontMidSizeLiteSpan[]) spannableStringBuilder.getSpans(0, length, FontMidSizeLiteSpan.class)).length > 0 || ((FontH3SizeLiteSpan[]) spannableStringBuilder.getSpans(0, length, FontH3SizeLiteSpan.class)).length > 0 || ((UnderlineSpan[]) spannableStringBuilder.getSpans(0, length, UnderlineSpan.class)).length > 0 || ((StrikethroughSpan[]) spannableStringBuilder.getSpans(0, length, StrikethroughSpan.class)).length > 0 || ((EditorQuoteLiteSpan[]) spannableStringBuilder.getSpans(0, length, EditorQuoteLiteSpan.class)).length > 0 || ((RelativeSizeSpan[]) spannableStringBuilder.getSpans(0, length, RelativeSizeSpan.class)).length > 0 || ((ForegroundColorSpan[]) spannableStringBuilder.getSpans(0, length, ForegroundColorSpan.class)).length > 0;
    }
}
