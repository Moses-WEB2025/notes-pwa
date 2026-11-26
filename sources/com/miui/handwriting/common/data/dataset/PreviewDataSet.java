package com.miui.handwriting.common.data.dataset;

import androidx.core.content.ContextCompat;
import com.miui.creation.common.R;
import com.miui.handwriting.common.HandWritingViewApplication;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PagePreviewData;

/* JADX INFO: loaded from: classes3.dex */
public class PreviewDataSet {
    public static void copyBg(PagePreviewData pagePreviewData, PagePreviewData pagePreviewData2) {
        pagePreviewData2.bgGrid = pagePreviewData.bgGrid;
        pagePreviewData2.bgImage = pagePreviewData.bgImage;
        pagePreviewData2.bgConfig = pagePreviewData.bgConfig;
        pagePreviewData2.bgImageMode = pagePreviewData.bgImageMode;
        pagePreviewData2.bgColor = pagePreviewData.bgColor == 0 ? ContextCompat.getColor(HandWritingViewApplication.INSTANCE.getApplication(), R.color.paper_white) : pagePreviewData.bgColor;
    }
}
