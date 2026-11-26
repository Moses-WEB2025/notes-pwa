package com.miui.richeditor;

/* JADX INFO: loaded from: classes4.dex */
public interface UndoOperation {
    public static final int UNDO_MODE_ADD = 1;
    public static final int UNDO_MODE_DEL = 2;
    public static final int UNDO_MODE_NONE = 0;
    public static final int UNDO_MODE_REP = 3;

    void commit();

    boolean hasData();

    void redo();

    void undo();
}
