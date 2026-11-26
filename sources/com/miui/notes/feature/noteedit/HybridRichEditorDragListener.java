package com.miui.notes.feature.noteedit;

import android.content.ClipDescription;
import android.text.TextUtils;
import android.view.DragEvent;
import android.view.View;
import com.miui.common.stat.NotesTextStat;
import com.miui.hybirdeditor.WebRichEditor;
import com.miui.notes.ai.stat.AITextWidgetStat;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment;
import com.miui.notes.home.PadActivityController;
import com.miui.notes.ui.BaseDragListener;
import com.miui.pad.feature.notes.share.ShareAppsAdapter;
import com.miui.richeditor.IRichEditor;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes4.dex */
public class HybridRichEditorDragListener extends BaseDragListener {
    private static final String TAG = "HybridRichEditorDragListener";
    private CharSequence dragLabel;
    private int mDragTag;
    private int mDragEventType = -1;
    private WeakReference<BaseHybridNoteEditFragment> mWrFragment = null;
    private WeakReference<WebRichEditor> mWrEditor = null;
    boolean mHasDragLocation = false;
    private boolean mNeedUpdateShadow = false;
    private boolean mIsTextPlain = false;
    private int mDragStartX = 0;
    private int mDragStartY = 0;

    @Override // com.miui.notes.ui.BaseDragListener
    protected boolean needHandleEvent(DragEvent dragEvent) {
        return true;
    }

    public HybridRichEditorDragListener(int i) {
        this.mDragTag = i;
    }

    public void setEditFragment(BaseHybridNoteEditFragment baseHybridNoteEditFragment, WebRichEditor webRichEditor) {
        this.mWrFragment = new WeakReference<>(baseHybridNoteEditFragment);
        this.mWrEditor = new WeakReference<>(webRichEditor);
    }

    @Override // com.miui.notes.ui.BaseDragListener, android.view.View.OnDragListener
    public boolean onDrag(View view, DragEvent dragEvent) {
        if (dragEvent.getAction() == 6) {
            handleDragExit(view, dragEvent);
            return false;
        }
        return super.onDrag(view, dragEvent);
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
        if (isInRecord()) {
            return true;
        }
        if (dragEvent.getClipDescription() != null) {
            this.dragLabel = dragEvent.getClipDescription().getLabel();
        }
        this.mHasDragLocation = false;
        WebRichEditor webRichEditor = this.mWrEditor.get();
        this.mDragStartX = (int) dragEvent.getX();
        this.mDragStartY = (int) dragEvent.getY();
        Logger.INSTANCE.d(TAG, "handleDragStarted " + dragEvent.toString());
        if (webRichEditor == null || TextUtils.equals(this.dragLabel, PadActivityController.NOTE_LIST_DRAG_LABEL)) {
            return false;
        }
        int dragEventType = webRichEditor.getDragEventType(dragEvent);
        this.mDragEventType = dragEventType;
        if (dragEventType > 0) {
            webRichEditor.startDragMode(dragEventType);
            this.mNeedUpdateShadow = true;
            this.mIsTextPlain = dragEvent.getClipDescription().hasMimeType("text/plain");
            return true;
        }
        return webRichEditor.isValidDragEventData(dragEvent);
    }

    @Override // com.miui.notes.ui.BaseDragListener
    protected boolean handleDragEntered(View view, DragEvent dragEvent) {
        if (isInRecord()) {
            return true;
        }
        WebRichEditor webRichEditor = this.mWrEditor.get();
        boolean zOnDragEvent = view.onDragEvent(dragEvent);
        if (webRichEditor == null || this.mDragEventType <= 0) {
            return false;
        }
        webRichEditor.enterDragZone();
        return zOnDragEvent;
    }

    @Override // com.miui.notes.ui.BaseDragListener
    protected void handleDragLocation(View view, DragEvent dragEvent) {
        this.mHasDragLocation = true;
        view.onDragEvent(dragEvent);
        int x = (int) dragEvent.getX();
        int y = (int) dragEvent.getY();
        WebRichEditor webRichEditor = this.mWrEditor.get();
        if (webRichEditor != null) {
            if (this.mNeedUpdateShadow) {
                webRichEditor.changeShadowView(this.mIsTextPlain);
                this.mNeedUpdateShadow = false;
            }
            webRichEditor.overScrollByPosition(x, y);
        }
    }

    @Override // com.miui.notes.ui.BaseDragListener
    protected boolean handleDragDrop(View view, DragEvent dragEvent) {
        WebRichEditor webRichEditor;
        Logger.INSTANCE.d(TAG, "handleDragDrop");
        if (isInRecord()) {
            return true;
        }
        if (!this.mHasDragLocation || (webRichEditor = this.mWrEditor.get()) == null) {
            return false;
        }
        ClipDescription clipDescription = dragEvent.getClipDescription();
        if (clipDescription != null) {
            if (PadActivityController.NOTE_LIST_DRAG_LABEL.equals(clipDescription.getLabel())) {
                return true;
            }
            if (clipDescription.hasMimeType(ShareAppsAdapter.TYPE_IMAGE)) {
                NotesTextStat.reportNoteDragEvent(AITextWidgetStat.TYPE_IMAGE);
            }
            if (IRichEditor.CLIP_LABEL_RICH_EDITOR.equals(clipDescription.getLabel())) {
                return true;
            }
        }
        BaseHybridNoteEditFragment baseHybridNoteEditFragment = this.mWrFragment.get();
        if (webRichEditor.isDragEventContainImage(dragEvent)) {
            if (baseHybridNoteEditFragment != null) {
                baseHybridNoteEditFragment.insertImageFromDragEvent(dragEvent, false);
                baseHybridNoteEditFragment.asyncSaveNote();
            }
            return true;
        }
        Logger.INSTANCE.d(TAG, "isValidDragEventData(event): " + isValidDragEventData(dragEvent));
        boolean zOnDragEvent = isValidDragEventData(dragEvent) ? view.onDragEvent(dragEvent) : true;
        if (zOnDragEvent && baseHybridNoteEditFragment != null) {
            baseHybridNoteEditFragment.asyncSaveNote();
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
        WebRichEditor webRichEditor = this.mWrEditor.get();
        if (webRichEditor != null && this.mDragEventType > 0) {
            webRichEditor.exitDragZone();
        }
        refreshTargetView(view, false);
        handleHover(false);
        this.mNeedUpdateShadow = false;
    }

    @Override // com.miui.notes.ui.BaseDragListener
    protected boolean handleDragEnd(View view, DragEvent dragEvent) {
        BaseHybridNoteEditFragment baseHybridNoteEditFragment;
        if (TextUtils.equals(this.dragLabel, PadActivityController.NOTE_LIST_DRAG_LABEL)) {
            return false;
        }
        if (isInRecord()) {
            return true;
        }
        WebRichEditor webRichEditor = this.mWrEditor.get();
        if (webRichEditor != null && this.mDragEventType > 0 && (baseHybridNoteEditFragment = this.mWrFragment.get()) != null) {
            baseHybridNoteEditFragment.asyncSaveNote();
        }
        if (webRichEditor != null) {
            if (!this.mHasDragLocation) {
                webRichEditor.overScrollByPosition(this.mDragStartX, this.mDragStartY);
            }
            webRichEditor.endDragMode();
        }
        this.mDragEventType = -1;
        handleHover(false);
        refreshSourceView(view, false, true, dragEvent.getLocalState());
        return false;
    }

    private boolean isInRecord() {
        WeakReference<BaseHybridNoteEditFragment> weakReference = this.mWrFragment;
        BaseHybridNoteEditFragment baseHybridNoteEditFragment = weakReference == null ? null : weakReference.get();
        return baseHybridNoteEditFragment != null && baseHybridNoteEditFragment.isInRecord();
    }
}
