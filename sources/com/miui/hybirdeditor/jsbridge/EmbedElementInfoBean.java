package com.miui.hybirdeditor.jsbridge;

/* JADX INFO: loaded from: classes3.dex */
public class EmbedElementInfoBean {
    public int clickedXInScreen;
    public int clickedYInScreen;
    public int eBottom;
    public int eLeft;
    public int eRight;
    public int eTop;
    public String fileName;
    public int imgSizeLevel;
    public String mirrorBase64;
    public String textContent;
    public int type;
    public String url;

    public int getElementWidth() {
        return this.eRight - this.eLeft;
    }

    public int getElementHeight() {
        return this.eBottom - this.eTop;
    }
}
