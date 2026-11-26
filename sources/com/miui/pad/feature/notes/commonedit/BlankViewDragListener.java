package com.miui.pad.feature.notes.commonedit;

import android.view.DragEvent;
import android.view.View;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment;
import com.miui.notes.ui.BaseDragListener;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes4.dex */
public class BlankViewDragListener extends BaseDragListener {
    private static final String TAG = "BlankViewDragListener";
    private int mDragTag;
    private WeakReference<BaseNormalNoteEditFragment> mFragmentWr = null;

    @Override // com.miui.notes.ui.BaseDragListener
    protected void handleDragLocation(View view, DragEvent dragEvent) {
    }

    @Override // com.miui.notes.ui.BaseDragListener
    protected boolean needHandleEvent(DragEvent dragEvent) {
        return true;
    }

    public BlankViewDragListener(int i) {
        this.mDragTag = i;
    }

    public void setEditFragment(BaseNormalNoteEditFragment baseNormalNoteEditFragment) {
        this.mFragmentWr = new WeakReference<>(baseNormalNoteEditFragment);
    }

    @Override // com.miui.notes.ui.BaseDragListener
    protected boolean handleDragStarted(View view, DragEvent dragEvent) {
        Logger.INSTANCE.i(TAG, "handleDragStarted " + dragEvent.toString());
        return isFromMirror(dragEvent);
    }

    @Override // com.miui.notes.ui.BaseDragListener
    protected boolean handleDragEntered(View view, DragEvent dragEvent) {
        if (!isFromMirror(dragEvent)) {
            return false;
        }
        handleHover(true);
        return true;
    }

    @Override // com.miui.notes.ui.BaseDragListener
    protected boolean handleDragDrop(View view, DragEvent dragEvent) {
        if (!isFromMirror(dragEvent)) {
            return false;
        }
        if (this.mFragmentWr.get() == null) {
            return true;
        }
        this.mFragmentWr.get().onDragDropFromBlankView(dragEvent);
        return true;
    }

    @Override // com.miui.notes.ui.BaseDragListener
    protected void handleDragExit(View view, DragEvent dragEvent) {
        handleHover(false);
    }

    @Override // com.miui.notes.ui.BaseDragListener
    protected boolean handleDragEnd(View view, DragEvent dragEvent) {
        handleHover(false);
        return true;
    }

    @Override // com.miui.notes.ui.BaseDragListener
    protected void handleHover(boolean z) {
        if (this.mFragmentWr.get() != null) {
            this.mFragmentWr.get().onShowAddNewNote(z);
        }
    }

    private boolean isFromMirror(DragEvent dragEvent) {
        return (dragEvent.getClipDescription() == null || dragEvent.getClipDescription().getLabel() == null || !"mirror".equals(dragEvent.getClipDescription().getLabel().toString())) ? false : true;
    }
}
