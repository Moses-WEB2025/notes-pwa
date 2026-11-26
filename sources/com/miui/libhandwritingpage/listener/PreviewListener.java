package com.miui.libhandwritingpage.listener;

import com.miui.libhandwritingpage.newbean.beanPageInfo.PagePreviewData;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public interface PreviewListener {
    void onBgColorItemUpdated(int i, int i2);

    void onPreviewItemUpdated(int i, PagePreviewData pagePreviewData);

    void onPreviewUpdate(List<PagePreviewData> list);
}
