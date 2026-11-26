package com.miui.notes.basefeature.noteedit;

/* JADX INFO: loaded from: classes4.dex */
public abstract class ColumnsNativeNoteEditFragment extends BaseNativeNoteEditFragment {
    protected abstract void showBlankView(boolean z);

    protected void onDataEmpty() {
        this.mWorkingNote = null;
        showBlankView(true);
    }
}
