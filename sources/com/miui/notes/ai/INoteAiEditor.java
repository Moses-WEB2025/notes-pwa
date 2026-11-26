package com.miui.notes.ai;

import com.miui.notes.ai.editor.IAiEditor;

/* JADX INFO: loaded from: classes4.dex */
public interface INoteAiEditor extends IAiEditor {
    void doneCorrection();

    void insertSummary(String str);

    void onDone();

    void onFloatingWindowClose(boolean z, int i);

    void onFloatingWindowVisible();

    void restoreToSavedScrollPosition();

    void revert();

    void saveScrollPosition();

    void trimAIStatement();
}
