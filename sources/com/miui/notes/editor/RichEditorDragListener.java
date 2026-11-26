package com.miui.notes.editor;

import android.view.DragEvent;
import android.view.View;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment;
import com.miui.notes.ui.BaseDragListener;
import com.miui.richeditor.RichEditTextView;
import com.miui.richeditor.style.BaseImageSpan;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes4.dex */
public class RichEditorDragListener extends BaseDragListener {
    private static final String TAG = "RichEditDragListener";
    private int mDragTag;
    private boolean mIsDrop = false;
    private WeakReference<BaseNativeNoteEditFragment> mWrFragment = null;

    @Override // com.miui.notes.ui.BaseDragListener
    protected boolean needHandleEvent(DragEvent dragEvent) {
        return true;
    }

    public RichEditorDragListener(int i) {
        this.mDragTag = i;
    }

    public void setEditFragment(BaseNativeNoteEditFragment baseNativeNoteEditFragment) {
        this.mWrFragment = new WeakReference<>(baseNativeNoteEditFragment);
    }

    @Override // com.miui.notes.ui.BaseDragListener
    protected void refreshSourceView(View view, boolean z, boolean z2, Object obj) {
        if (view == null) {
            return;
        }
        if (z) {
            view.setAlpha(0.0f);
        }
        if (z2 && obj != null && (obj instanceof Integer) && ((Integer) obj).intValue() == this.mDragTag) {
            Logger.INSTANCE.i(TAG, "refreshSourceView setAlpha 1");
            view.setAlpha(1.0f);
        }
    }

    @Override // com.miui.notes.ui.BaseDragListener
    protected boolean handleDragStarted(View view, DragEvent dragEvent) {
        RichEditTextView richEditTextView = (RichEditTextView) view;
        Logger.INSTANCE.i(TAG, "handleDragStarted " + dragEvent.toString());
        if ((dragEvent.getLocalState() instanceof BaseImageSpan) || richEditTextView.isDragEventContainImage(dragEvent)) {
            richEditTextView.startDragMode();
            return true;
        }
        return richEditTextView.isValidDragEventData(dragEvent);
    }

    @Override // com.miui.notes.ui.BaseDragListener
    protected boolean handleDragEntered(View view, DragEvent dragEvent) {
        RichEditTextView richEditTextView = (RichEditTextView) view;
        boolean zOnDragEvent = view.onDragEvent(dragEvent);
        if (!(dragEvent.getLocalState() instanceof BaseImageSpan) && !richEditTextView.isDragEventContainImage(dragEvent)) {
            return false;
        }
        richEditTextView.enterDragZone();
        return zOnDragEvent;
    }

    @Override // com.miui.notes.ui.BaseDragListener
    protected void handleDragLocation(View view, DragEvent dragEvent) {
        this.mIsDrop = false;
        view.onDragEvent(dragEvent);
        ((RichEditTextView) view).overScrollByPosition((int) dragEvent.getX(), (int) dragEvent.getY());
    }

    @Override // com.miui.notes.ui.BaseDragListener
    protected boolean handleDragDrop(View view, DragEvent dragEvent) {
        RichEditTextView richEditTextView = (RichEditTextView) view;
        BaseNativeNoteEditFragment baseNativeNoteEditFragment = this.mWrFragment.get();
        richEditTextView.getDragEventMimeType(dragEvent);
        if (dragEvent.getLocalState() instanceof BaseImageSpan) {
            richEditTextView.moveImageSpanAfterDragDrop((BaseImageSpan) dragEvent.getLocalState(), true);
            if (baseNativeNoteEditFragment != null) {
                baseNativeNoteEditFragment.asyncSaveNote();
            }
            return true;
        }
        if (richEditTextView.isDragEventContainImage(dragEvent)) {
            if (baseNativeNoteEditFragment != null) {
                baseNativeNoteEditFragment.insertImageFromDragEvent(dragEvent, false);
                baseNativeNoteEditFragment.asyncSaveNote();
            }
            return true;
        }
        if (baseNativeNoteEditFragment != null && baseNativeNoteEditFragment.insertImageFromDragEvent(dragEvent, false)) {
            return true;
        }
        boolean zOnDragEvent = view.onDragEvent(dragEvent);
        if (zOnDragEvent && baseNativeNoteEditFragment != null) {
            this.mIsDrop = true;
            baseNativeNoteEditFragment.asyncSaveNote();
        }
        return zOnDragEvent;
    }

    @Override // com.miui.notes.ui.BaseDragListener
    protected void handleDragExit(View view, DragEvent dragEvent) {
        RichEditTextView richEditTextView = (RichEditTextView) view;
        if ((dragEvent.getLocalState() instanceof BaseImageSpan) || richEditTextView.isDragEventContainImage(dragEvent)) {
            richEditTextView.exitDragZone();
            richEditTextView.invalidateDeeply();
        }
        refreshTargetView(view, false);
        handleHover(false);
    }

    @Override // com.miui.notes.ui.BaseDragListener
    protected boolean handleDragEnd(View view, DragEvent dragEvent) {
        RichEditTextView richEditTextView = (RichEditTextView) view;
        if ((dragEvent.getLocalState() instanceof BaseImageSpan) || richEditTextView.isDragEventContainImage(dragEvent)) {
            BaseNativeNoteEditFragment baseNativeNoteEditFragment = this.mWrFragment.get();
            if (baseNativeNoteEditFragment != null) {
                baseNativeNoteEditFragment.asyncSaveNote();
            }
        } else if (this.mIsDrop) {
            this.mWrFragment.get().dragToEditMode();
        }
        this.mIsDrop = false;
        richEditTextView.endDragMode();
        richEditTextView.invalidateDeeply();
        handleHover(false);
        refreshSourceView(view, false, true, dragEvent.getLocalState());
        return true;
    }
}
