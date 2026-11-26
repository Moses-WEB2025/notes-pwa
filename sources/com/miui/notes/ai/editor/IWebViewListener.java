package com.miui.notes.ai.editor;

/* JADX INFO: loaded from: classes4.dex */
public interface IWebViewListener {
    String getNumberAndStringFromResource(int i, String str);

    String getStringFromResource(String str);

    boolean isNightMode();

    void notifyRefreshComplete();

    void notifySetNewContent();

    void onCorrectNumChanged(int i);

    void onFlowDone();

    void onPagePrepared();

    void onSaveData(String str);

    boolean panelNeedTaller();
}
