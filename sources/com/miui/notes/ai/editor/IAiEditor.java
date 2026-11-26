package com.miui.notes.ai.editor;

/* JADX INFO: loaded from: classes4.dex */
public interface IAiEditor {
    void addText(String str, boolean z, boolean z2);

    void beforeRefreshNewContent();

    void clearDoc();

    void clearFlowStatus();

    void closeCorrectionPop();

    void doCopy();

    void finishFlow(boolean z, String str, String str2);

    void onDataLoading();

    void refreshNewContent();

    void setData(String str, String str2);

    void setDataWithConfig(String str, String str2);

    void stopInput(String str);
}
