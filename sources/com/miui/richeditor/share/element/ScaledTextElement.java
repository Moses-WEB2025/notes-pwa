package com.miui.richeditor.share.element;

/* JADX INFO: loaded from: classes4.dex */
public class ScaledTextElement extends TextElement {
    private float mMinFontSize;

    @Override // com.miui.richeditor.share.element.TextElement, com.miui.richeditor.share.element.Element
    public int getType() {
        return 3;
    }

    public static class Builder extends TextElement.Builder {
        public Builder() {
            this(null);
        }

        public Builder(ScaledTextElement scaledTextElement) {
            super(new ScaledTextElement(), scaledTextElement);
        }

        public Builder setMinFontSize(float f) {
            ((ScaledTextElement) this.iElement).mMinFontSize = f;
            return this;
        }

        @Override // com.miui.richeditor.share.element.TextElement.Builder
        public Builder setFontSize(float f) {
            super.setFontSize(f);
            return this;
        }

        @Override // com.miui.richeditor.share.element.TextElement.Builder
        public Builder setFontColor(int i) {
            super.setFontColor(i);
            return this;
        }

        @Override // com.miui.richeditor.share.element.TextElement.Builder
        public Builder setBold(boolean z) {
            super.setBold(z);
            return this;
        }

        @Override // com.miui.richeditor.share.element.TextElement.Builder
        public Builder setLineGap(float f) {
            super.setLineGap(f);
            return this;
        }

        @Override // com.miui.richeditor.share.element.TextElement.Builder
        public Builder setGravity(int i) {
            super.setGravity(i);
            return this;
        }

        @Override // com.miui.richeditor.share.element.TextElement.Builder
        public Builder setText(CharSequence charSequence) {
            super.setText((CharSequence) charSequence.toString());
            return this;
        }

        @Override // com.miui.richeditor.share.element.TextElement.Builder, com.miui.richeditor.share.element.Element.Builder
        public Builder setMarginLeft(int i) {
            super.setMarginLeft(i);
            return this;
        }

        @Override // com.miui.richeditor.share.element.TextElement.Builder, com.miui.richeditor.share.element.Element.Builder
        public Builder setMarginTop(int i) {
            super.setMarginTop(i);
            return this;
        }

        @Override // com.miui.richeditor.share.element.TextElement.Builder, com.miui.richeditor.share.element.Element.Builder
        public Builder setMarginRight(int i) {
            super.setMarginRight(i);
            return this;
        }

        @Override // com.miui.richeditor.share.element.TextElement.Builder, com.miui.richeditor.share.element.Element.Builder
        public Builder setMarginBottom(int i) {
            super.setMarginBottom(i);
            return this;
        }

        @Override // com.miui.richeditor.share.element.TextElement.Builder
        public Builder setIsTitle(boolean z) {
            super.setIsTitle(z);
            return this;
        }

        @Override // com.miui.richeditor.share.element.Element.Builder
        public ScaledTextElement build() {
            return (ScaledTextElement) super.build();
        }

        @Override // com.miui.richeditor.share.element.Element.Builder
        public ScaledTextElement apply() {
            return (ScaledTextElement) super.apply();
        }
    }

    protected ScaledTextElement() {
    }

    public float getMinFontSize() {
        return this.mMinFontSize;
    }

    @Override // com.miui.richeditor.share.element.TextElement, com.miui.richeditor.share.element.Element
    public Builder edit() {
        return new Builder(this);
    }

    @Override // com.miui.richeditor.share.element.TextElement, com.miui.richeditor.share.element.Element
    protected void copyFrom(Element element) {
        super.copyFrom(element);
        this.mMinFontSize = ((ScaledTextElement) element).mMinFontSize;
    }
}
