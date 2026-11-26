package com.miui.libhandwritingpage.newbean.beanPageInfo;

import com.miui.libhandwritingpage.newbean.BgGridConfiguration;

/* JADX INFO: loaded from: classes3.dex */
public class PagePreviewData {
    public int bgColor;
    public BgGridConfiguration bgConfig;
    public int bgGrid;
    public String bgImage;
    public int bgImageMode;
    public String bookmark;
    public String coverPath;
    public String pdfId;
    public int viewId;

    public PagePreviewData() {
    }

    public PagePreviewData(String str, BgGridConfiguration bgGridConfiguration, String str2, int i, String str3, int i2, int i3, String str4, int i4) {
        this.pdfId = str;
        this.bgConfig = bgGridConfiguration;
        this.bookmark = str2;
        this.bgImageMode = i;
        this.bgImage = str3;
        this.bgColor = i2;
        this.viewId = i3;
        this.coverPath = str4;
        this.bgGrid = i4;
    }
}
