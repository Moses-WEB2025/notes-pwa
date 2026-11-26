package com.miui.handwriting.common.tools;

import android.content.Context;
import com.miui.handwriting.common.data.bean.CommonPageData;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PageData;
import com.miui.libhandwritingpage.utils.PageEntUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class PageLoadUtil {
    private PageEntUtil entUtil = new PageEntUtil();

    public void loadPageEnt(String str, Context context) {
        this.entUtil.loadPageEnt(str, context);
    }

    public List<CommonPageData> getPageDataList() {
        ArrayList arrayList = new ArrayList();
        for (Iterator<PageData> it = this.entUtil.getPageDataList().iterator(); it.hasNext(); it = it) {
            PageData next = it.next();
            arrayList.add(new CommonPageData(next.bgImagePathList, next.bitmapPathList, next.bgImage, next.width, next.covePath, next.pdfIndex, next.pdfPath, next.bgColor, next.inkPath, next.height, next.pdfId, next.bgConfig, next.bgGrid, next.radius, next.isUnLimit, next.sizeUnit, next.maxWidth, next.standardWidth, next.exportLeft, next.exportTop, next.exportRight, next.exportBottom, next.isFixSize, next.fixWidth));
        }
        return arrayList;
    }
}
