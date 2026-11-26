package com.miui.pad.feature.notes.commonedit;

import android.view.DragEvent;
import android.view.View;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment;
import com.miui.notes.ui.BaseDragListener;
import com.miui.pad.feature.notes.share.ShareAppsAdapter;
import com.miui.richeditor.RichEditTextView;
import com.miui.richeditor.style.BaseImageSpan;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes4.dex */
public class PadRichEditorDragListener extends BaseDragListener {
    private static final String TAG = "RichEditDragListener";
    private int mDragTag;
    private boolean mIsDrop = false;
    private WeakReference<BaseNormalNoteEditFragment> mWrFragment = null;

    @Override // com.miui.notes.ui.BaseDragListener
    protected boolean needHandleEvent(DragEvent dragEvent) {
        return true;
    }

    public PadRichEditorDragListener(int i) {
        this.mDragTag = i;
    }

    public void setEditFragment(BaseNormalNoteEditFragment baseNormalNoteEditFragment) {
        this.mWrFragment = new WeakReference<>(baseNormalNoteEditFragment);
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
        richEditTextView.getDragEventMimeType(dragEvent);
        BaseNormalNoteEditFragment baseNormalNoteEditFragment = this.mWrFragment.get();
        if (dragEvent.getLocalState() instanceof BaseImageSpan) {
            richEditTextView.moveImageSpanAfterDragDrop((BaseImageSpan) dragEvent.getLocalState(), true);
            if (baseNormalNoteEditFragment != null) {
                baseNormalNoteEditFragment.saveNoteInstantlyIfNeeded();
            }
            return true;
        }
        if (richEditTextView.isDragEventContainImage(dragEvent)) {
            if (baseNormalNoteEditFragment != null) {
                baseNormalNoteEditFragment.insertImageFromDragEvent(dragEvent, false);
                baseNormalNoteEditFragment.saveNoteInstantlyIfNeeded();
            }
            return true;
        }
        Logger.INSTANCE.d(TAG, "isValidDragEventData(event): " + isValidDragEventData(dragEvent));
        boolean zOnDragEvent = isValidDragEventData(dragEvent) ? view.onDragEvent(dragEvent) : true;
        if (zOnDragEvent && baseNormalNoteEditFragment != null) {
            baseNormalNoteEditFragment.asyncSaveNote();
        }
        return zOnDragEvent;
    }

    public boolean isValidDragEventData(DragEvent dragEvent) {
        if (dragEvent.getClipDescription() != null) {
            Logger.INSTANCE.d(TAG, "isValidDragEventData event.getClipDescription(): " + dragEvent.getClipDescription());
            String[] strArrFilterMimeTypes = dragEvent.getClipDescription().filterMimeTypes("text/*");
            String[] strArrFilterMimeTypes2 = dragEvent.getClipDescription().filterMimeTypes(ShareAppsAdapter.TYPE_IMAGE);
            if ((strArrFilterMimeTypes2 != null && strArrFilterMimeTypes2.length > 0) || (strArrFilterMimeTypes != null && strArrFilterMimeTypes.length > 0)) {
                return true;
            }
        }
        if (dragEvent.getClipData() == null || dragEvent.getClipData().getDescription() == null) {
            return false;
        }
        String[] strArrFilterMimeTypes3 = dragEvent.getClipData().getDescription().filterMimeTypes(ShareAppsAdapter.TYPE_IMAGE);
        String[] strArrFilterMimeTypes4 = dragEvent.getClipData().getDescription().filterMimeTypes("text/*");
        Logger.INSTANCE.d(TAG, "isValidDragEventData event.getClipDescription():" + dragEvent.getClipData().getDescription());
        return (strArrFilterMimeTypes3 != null && strArrFilterMimeTypes3.length > 0) || (strArrFilterMimeTypes4 != null && strArrFilterMimeTypes4.length > 0);
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
            BaseNormalNoteEditFragment baseNormalNoteEditFragment = this.mWrFragment.get();
            if (baseNormalNoteEditFragment != null) {
                baseNormalNoteEditFragment.saveNoteInstantlyIfNeeded();
            }
        } else if (this.mIsDrop) {
            this.mWrFragment.get().dragToEditMode();
        }
        richEditTextView.endDragMode();
        richEditTextView.invalidateDeeply();
        handleHover(false);
        refreshSourceView(view, false, true, dragEvent.getLocalState());
        return true;
    }
}
