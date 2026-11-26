package com.miui.richeditor.share.element;

import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.style.StrikethroughSpan;

/* JADX INFO: loaded from: classes4.dex */
public class ListElement extends Element {
    private int mIndent;
    private boolean mIsBullet;
    private boolean mIsChecked;
    private boolean mIsInQuote;
    private boolean mIsListOutQuote;
    private boolean mIsOrder;
    private boolean mIsQuote;
    private boolean mIsSplitLine;
    private Drawable mListIcon;
    private int mOrderNum;
    private TextElement mTextElement;

    @Override // com.miui.richeditor.share.element.Element
    public int getType() {
        return 2;
    }

    public static class Builder extends Element.Builder<ListElement> {
        public Builder() {
            this(null);
        }

        protected Builder(ListElement listElement) {
            super(new ListElement(), listElement);
        }

        public Builder setTextElement(TextElement textElement) {
            if (textElement.getMarginLeft() != 0 || textElement.getMarginTop() != 0 || textElement.getMarginRight() != 0 || textElement.getMarginBottom() != 0) {
                throw new IllegalArgumentException("TextElement's margin should be 0");
            }
            ((ListElement) this.iElement).mTextElement = textElement;
            return this;
        }

        public Builder setIsChecked(boolean z) {
            ((ListElement) this.iElement).mIsChecked = z;
            if (((ListElement) this.iElement).mTextElement != null) {
                ((ListElement) this.iElement).mTextElement.setIsChecked(z);
            }
            return this;
        }

        public Builder setListIcon(Drawable drawable) {
            ((ListElement) this.iElement).mListIcon = drawable;
            return this;
        }

        public Builder setIsBullet(boolean z) {
            ((ListElement) this.iElement).mIsBullet = z;
            return this;
        }

        public Builder setIsOrder(boolean z) {
            ((ListElement) this.iElement).mIsOrder = z;
            return this;
        }

        public Builder setOrderNum(int i) {
            ((ListElement) this.iElement).mOrderNum = i;
            return this;
        }

        public Builder setIndent(int i) {
            ((ListElement) this.iElement).mIndent = i;
            return this;
        }

        public Builder setIsQuote(boolean z) {
            ((ListElement) this.iElement).mIsQuote = z;
            return this;
        }

        public Builder setIsInQuote(boolean z) {
            ((ListElement) this.iElement).mIsInQuote = z;
            return this;
        }

        public Builder setIsLIstOutQuote(boolean z) {
            ((ListElement) this.iElement).mIsListOutQuote = z;
            return this;
        }

        public Builder setIsSplitLine(boolean z) {
            ((ListElement) this.iElement).mIsSplitLine = z;
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

    public TextElement getTextElement() {
        return this.mTextElement;
    }

    public Drawable getListIcon() {
        return this.mListIcon;
    }

    public boolean isBullet() {
        return this.mIsBullet;
    }

    public boolean isOrder() {
        return this.mIsOrder;
    }

    public int getIndent() {
        return this.mIndent;
    }

    public int getOrderNum() {
        return this.mOrderNum;
    }

    public boolean isQuote() {
        return this.mIsQuote;
    }

    public boolean isListOutQuote() {
        return this.mIsListOutQuote;
    }

    public boolean isInQuote() {
        return this.mIsInQuote || this.mIsListOutQuote;
    }

    public boolean isSplitLine() {
        return this.mIsSplitLine;
    }

    @Override // com.miui.richeditor.share.element.Element
    public Builder edit() {
        return new Builder(this);
    }

    @Override // com.miui.richeditor.share.element.Element
    protected void copyFrom(Element element) {
        super.copyFrom(element);
        ListElement listElement = (ListElement) element;
        this.mTextElement = listElement.mTextElement;
        this.mListIcon = listElement.mListIcon;
    }

    public boolean isChecked() {
        return this.mIsChecked;
    }

    public ListElement setChecked(boolean z) {
        this.mIsChecked = z;
        CharSequence text = getTextElement().getText();
        if (this.mIsChecked) {
            ((SpannableStringBuilder) text).setSpan(new StrikethroughSpan(), 0, text.length(), 33);
            return this;
        }
        SpannableStringBuilder spannableStringBuilder = (SpannableStringBuilder) text;
        for (StrikethroughSpan strikethroughSpan : (StrikethroughSpan[]) spannableStringBuilder.getSpans(0, text.length(), StrikethroughSpan.class)) {
            spannableStringBuilder.removeSpan(strikethroughSpan);
        }
        return this;
    }
}
