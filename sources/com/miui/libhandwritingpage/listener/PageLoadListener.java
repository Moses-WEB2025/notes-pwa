package com.miui.libhandwritingpage.listener;

/* JADX INFO: loaded from: classes3.dex */
public interface PageLoadListener {
    void onLoadCompleted(boolean z, boolean z2, String str);

    void onPageCount(int i);

    void onPdfError(int i);

    default String getSuniaSinglePageBgType() {
        return "";
    }
}
