package com.miui.notes.basefeature.noteedit;

/* JADX INFO: loaded from: classes4.dex */
public abstract class ColumnsHybridNoteEditFragment extends BaseHybridNoteEditFragment {
    protected boolean mIsNaviShow = true;
    protected boolean mIsShowBlank = false;

    protected abstract void showBlankView(boolean z);

    protected void onDataEmpty() {
        this.mWorkingNote = null;
        showBlankView(true);
    }
}
