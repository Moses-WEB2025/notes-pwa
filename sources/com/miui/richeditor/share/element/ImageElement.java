package com.miui.richeditor.share.element;

import android.net.Uri;

/* JADX INFO: loaded from: classes4.dex */
public class ImageElement extends Element {
    private String mAnnotation;
    private int mFontColor;
    private boolean mIsScaled;
    private boolean mIsShowBig;
    private int mRadius;
    private Uri mUri;

    @Override // com.miui.richeditor.share.element.Element
    public int getType() {
        return 1;
    }

    public static class Builder extends Element.Builder<ImageElement> {
        public Builder() {
            this(null);
        }

        protected Builder(ImageElement imageElement) {
            super(new ImageElement(), imageElement);
        }

        public Builder setUri(Uri uri) {
            ((ImageElement) this.iElement).mUri = uri;
            return this;
        }

        public Builder setAnnotation(String str) {
            ((ImageElement) this.iElement).mAnnotation = str;
            return this;
        }

        public Builder setIsShowBig(boolean z) {
            ((ImageElement) this.iElement).mIsShowBig = z;
            return this;
        }

        public Builder setRadius(int i) {
            ((ImageElement) this.iElement).mRadius = i;
            return this;
        }

        public Builder setFontColor(int i) {
            ((ImageElement) this.iElement).mFontColor = i;
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

        public Builder setIsScaled(boolean z) {
            ((ImageElement) this.iElement).mIsScaled = z;
            return this;
        }
    }

    private ImageElement() {
        this.mAnnotation = "";
        this.mIsShowBig = true;
        this.mIsScaled = true;
        this.mRadius = 0;
        this.mFontColor = -16777216;
    }

    public Uri getUri() {
        return this.mUri;
    }

    public String getAnnotation() {
        return this.mAnnotation;
    }

    public boolean isShowBig() {
        return this.mIsShowBig;
    }

    public boolean isScaled() {
        return this.mIsScaled;
    }

    public int getFontColor() {
        return this.mFontColor;
    }

    public int getRadius() {
        return this.mRadius;
    }

    @Override // com.miui.richeditor.share.element.Element
    public Builder edit() {
        return new Builder(this);
    }

    @Override // com.miui.richeditor.share.element.Element
    protected void copyFrom(Element element) {
        super.copyFrom(element);
        ImageElement imageElement = (ImageElement) element;
        this.mUri = imageElement.mUri;
        this.mAnnotation = imageElement.mAnnotation;
        this.mIsShowBig = imageElement.mIsShowBig;
        this.mIsScaled = imageElement.mIsScaled;
        this.mFontColor = imageElement.mFontColor;
    }
}
