package com.miui.richeditor.share.element;

/* JADX INFO: loaded from: classes4.dex */
public class LineElement extends Element {
    private int mColor;
    private int mStrokeWidth;

    @Override // com.miui.richeditor.share.element.Element
    public int getType() {
        return 4;
    }

    public static class Builder extends Element.Builder<LineElement> {
        public Builder() {
            this(null);
        }

        protected Builder(LineElement lineElement) {
            super(new LineElement(), lineElement);
        }

        public Builder setColor(int i) {
            ((LineElement) this.iElement).mColor = i;
            return this;
        }

        public Builder setStrokeWidth(int i) {
            ((LineElement) this.iElement).mStrokeWidth = i;
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

    private LineElement() {
    }

    public int getColor() {
        return this.mColor;
    }

    public int getStrokeWidth() {
        return this.mStrokeWidth;
    }

    @Override // com.miui.richeditor.share.element.Element
    public Builder edit() {
        return new Builder(this);
    }

    @Override // com.miui.richeditor.share.element.Element
    protected void copyFrom(Element element) {
        super.copyFrom(element);
        LineElement lineElement = (LineElement) element;
        this.mColor = lineElement.mColor;
        this.mStrokeWidth = lineElement.mStrokeWidth;
    }
}
