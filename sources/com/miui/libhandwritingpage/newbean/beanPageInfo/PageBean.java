package com.miui.libhandwritingpage.newbean.beanPageInfo;

import java.io.Serializable;

/* JADX INFO: loaded from: classes3.dex */
public class PageBean implements Serializable {
    public String bgImage;
    public short cover;
    public long createTime;
    public int height;
    public Object iinkPackage;
    public boolean isSuniaSinglePage;
    public String moreInfo;
    public short orientation;
    public String path;
    public String pdfPassword;
    public String pdfPath;
    public float radius;
    public int sizeType;
    public String title;
    public int width;

    public PageBean() {
        this.path = "";
        this.pdfPath = "";
        this.pdfPassword = "";
        this.title = "";
        this.moreInfo = "";
        this.isSuniaSinglePage = false;
        this.iinkPackage = null;
    }

    public PageBean(float f, String str, String str2, String str3, String str4, String str5, short s, short s2, int i, int i2, long j, int i3, String str6) {
        this.isSuniaSinglePage = false;
        this.iinkPackage = null;
        this.radius = f;
        this.path = str;
        this.pdfPath = str2;
        this.pdfPassword = str3;
        this.title = str4;
        this.moreInfo = str5;
        this.orientation = s;
        this.cover = s2;
        this.width = i;
        this.height = i2;
        this.createTime = j;
        this.sizeType = i3;
        this.bgImage = str6;
    }
}
