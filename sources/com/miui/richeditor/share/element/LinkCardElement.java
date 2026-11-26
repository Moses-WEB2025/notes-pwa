package com.miui.richeditor.share.element;

import android.text.TextUtils;
import com.miui.richeditor.style.LinkCardInfo;

/* JADX INFO: loaded from: classes4.dex */
public class LinkCardElement extends Element {
    private String mContent;
    private String mLink;
    private int mRadius;
    private String mTitle;
    private String mWebIconId;

    @Override // com.miui.richeditor.share.element.Element
    public int getType() {
        return 5;
    }

    public static class Builder extends Element.Builder<LinkCardElement> {
        public Builder() {
            this(null);
        }

        protected Builder(LinkCardElement linkCardElement) {
            super(new LinkCardElement(), linkCardElement);
        }

        public Builder setInfo(LinkCardInfo linkCardInfo) {
            if (!TextUtils.isEmpty(linkCardInfo.title)) {
                ((LinkCardElement) this.iElement).mTitle = linkCardInfo.title;
            }
            if (!TextUtils.isEmpty(linkCardInfo.link)) {
                ((LinkCardElement) this.iElement).mLink = linkCardInfo.link;
            }
            if (!TextUtils.isEmpty(linkCardInfo.content)) {
                ((LinkCardElement) this.iElement).mContent = linkCardInfo.content;
            }
            if (!TextUtils.isEmpty(linkCardInfo.webIconId)) {
                ((LinkCardElement) this.iElement).mWebIconId = linkCardInfo.webIconId;
            }
            return this;
        }

        public Builder setRadius(int i) {
            ((LinkCardElement) this.iElement).mRadius = i;
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

    private LinkCardElement() {
        this.mTitle = "";
        this.mLink = "";
        this.mContent = "";
        this.mWebIconId = "";
        this.mRadius = 0;
    }

    public void setTitle(String str) {
        this.mTitle = str;
    }

    public void setRadius(int i) {
        this.mRadius = i;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public String getLink() {
        return this.mLink;
    }

    public String getContent() {
        return this.mContent;
    }

    public String getWebIconId() {
        return this.mWebIconId;
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
        LinkCardElement linkCardElement = (LinkCardElement) element;
        this.mTitle = linkCardElement.getTitle();
        this.mLink = linkCardElement.getLink();
        this.mContent = linkCardElement.getContent();
        this.mWebIconId = linkCardElement.getWebIconId();
    }
}
