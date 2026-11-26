package com.miui.notes.editor;

/* JADX INFO: loaded from: classes4.dex */
public interface UndoOperation {
    void commit();

    boolean hasData();

    void redo();

    void undo();
}
