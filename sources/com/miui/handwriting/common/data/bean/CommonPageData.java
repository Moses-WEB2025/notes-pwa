package com.miui.handwriting.common.data.bean;

import com.miui.libhandwritingpage.newbean.BgGridConfiguration;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class CommonPageData {
    public int bgColor;
    public BgGridConfiguration bgConfig;
    public int bgGrid;
    public String bgImage;
    public List<String> bgImagePathList;
    public List<String> bitmapPathList;
    public String covePath;
    public int exportBottom;
    public int exportLeft;
    public int exportRight;
    public int exportTop;
    public float fixWidth;
    public int height;
    public String inkPath;
    public boolean isFixSize;
    public boolean isUnLimit;
    public int maxWidth;
    public String pdfId;
    public int pdfIndex;
    public String pdfPath;
    public float radius;
    public int sizeUnit;
    public int standardWidth;
    public int width;

    public CommonPageData(List<String> list, List<String> list2, String str, int i, String str2, int i2, String str3, int i3, String str4, int i4, String str5, BgGridConfiguration bgGridConfiguration, int i5, float f, boolean z, int i6, int i7, int i8, int i9, int i10, int i11, int i12, boolean z2, float f2) {
        this.bgImagePathList = list;
        this.bitmapPathList = list2;
        this.bgImage = str;
        this.width = i;
        this.covePath = str2;
        this.pdfIndex = i2;
        this.pdfPath = str3;
        this.bgColor = i3;
        this.inkPath = str4;
        this.height = i4;
        this.pdfId = str5;
        this.bgConfig = bgGridConfiguration;
        this.bgGrid = i5;
        this.radius = f;
        this.isUnLimit = z;
        this.sizeUnit = i6;
        this.maxWidth = i7;
        this.standardWidth = i8;
        this.exportLeft = i9;
        this.exportTop = i10;
        this.exportRight = i11;
        this.exportBottom = i12;
        this.isFixSize = z2;
        this.fixWidth = f2;
    }
}
