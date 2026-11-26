package com.miui.richeditor.share.element;

/* JADX INFO: loaded from: classes4.dex */
public abstract class Element {
    public static final int TYPE_AUDIO = 6;
    public static final int TYPE_IMAGE = 1;
    public static final int TYPE_LINE = 4;
    public static final int TYPE_LINK_CARD = 5;
    public static final int TYPE_LIST = 2;
    public static final int TYPE_SCALED_TEXT = 3;
    public static final int TYPE_TEXT = 0;
    protected int mMarginBottom;
    protected int mMarginLeft;
    protected int mMarginRight;
    protected int mMarginTop;

    public abstract <T extends Element> Builder<T> edit();

    public abstract int getType();

    public static abstract class Builder<T extends Element> {
        protected final T iElement;
        protected final T iOldElement;

        protected Builder(T t, T t2) {
            this.iElement = t;
            this.iOldElement = t2;
            if (t2 != null) {
                t.copyFrom(t2);
            }
        }

        public Builder setMarginLeft(int i) {
            this.iElement.mMarginLeft = i;
            return this;
        }

        public Builder setMarginTop(int i) {
            this.iElement.mMarginTop = i;
            return this;
        }

        public Builder setMarginRight(int i) {
            this.iElement.mMarginRight = i;
            return this;
        }

        public Builder setMarginBottom(int i) {
            this.iElement.mMarginBottom = i;
            return this;
        }

        public T build() {
            return this.iElement;
        }

        public T apply() {
            T t = this.iOldElement;
            if (t == null) {
                return this.iElement;
            }
            t.copyFrom(this.iElement);
            return this.iOldElement;
        }
    }

    public int getMarginLeft() {
        return this.mMarginLeft;
    }

    public int getMarginTop() {
        return this.mMarginTop;
    }

    public int getMarginRight() {
        return this.mMarginRight;
    }

    public int getMarginBottom() {
        return this.mMarginBottom;
    }

    protected void copyFrom(Element element) {
        this.mMarginLeft = element.mMarginLeft;
        this.mMarginTop = element.mMarginTop;
        this.mMarginRight = element.mMarginRight;
        this.mMarginBottom = element.mMarginBottom;
    }
}
