package com.miui.libhandwritingpage.listener;

import com.miui.libhandwritingpage.newbean.beanPageInfo.PageItemData;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public interface PDFImportListener {
    void onImportCompleted(List<PageItemData> list);

    void onPdfError(int i);

    void onPdfPageCount(int i);
}
