package com.miui.pc.feature.notes;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import com.miui.common.tool.ImageUtils;
import com.miui.notes.editor.BaseNoteRichEditorController;
import com.miui.notes.editor.NoteRichEditor;
import com.miui.notes.store.NoteStore;
import com.miui.notes.tool.AttachmentUtils;
import com.miui.notes.ui.BaseDragListener;
import com.miui.pad.feature.notes.share.ShareAppsAdapter;
import com.miui.pc.richeditor.PcEditorActionListener;
import com.miui.richeditor.EditorActionListener;
import com.miui.richeditor.EditorActionMode;
import com.miui.richeditor.style.BaseImageSpan;
import com.miui.richeditor.style.EditorHintEntrySpan;
import com.miui.richeditor.style.LinkCardSpan;
import com.miui.richeditor.style.NormalImageSpan;
import com.xiaomi.mirror.synergy.MiuiSynergySdk;
import java.io.File;

/* JADX INFO: loaded from: classes4.dex */
public class PcNoteRichEditorController extends BaseNoteRichEditorController {
    private static final String TAG = "PcNoteRichEditorController";
    private PcEditorActionListener mActionListener;
    private PcNoteEditFragment mFragment;

    public PcNoteRichEditorController(PcNoteEditFragment pcNoteEditFragment, NoteRichEditor noteRichEditor, EditText editText) {
        this(pcNoteEditFragment.getActivity(), noteRichEditor, editText);
        this.mFragment = pcNoteEditFragment;
    }

    public PcNoteRichEditorController(Activity activity, NoteRichEditor noteRichEditor, EditText editText) {
        super(activity, noteRichEditor, editText);
    }

    @Override // com.miui.notes.editor.BaseNoteRichEditorController
    protected EditorActionListener createActionListener() {
        if (this.mActionListener == null) {
            this.mActionListener = new PcEditorActionListener() { // from class: com.miui.pc.feature.notes.PcNoteRichEditorController.1
                @Override // com.miui.richeditor.EditorActionListener
                public void onImageSpanClick(BaseImageSpan baseImageSpan, int[] iArr) {
                }

                @Override // com.miui.richeditor.EditorActionListener
                public void onLinkSpanClick(LinkCardSpan linkCardSpan, int[] iArr) {
                }

                @Override // com.miui.richeditor.EditorActionListener
                public void onPlayingRecord(boolean z) {
                }

                @Override // com.miui.richeditor.EditorActionListener
                public void onTrackEvent(String str) {
                }

                @Override // com.miui.richeditor.EditorActionListener
                public void onSaveData() {
                    PcNoteRichEditorController.this.mFragment.asyncSaveNote();
                }

                @Override // com.miui.richeditor.EditorActionListener
                public void onInsertImages(Uri... uriArr) {
                    PcNoteRichEditorController.this.mFragment.insertImages(uriArr);
                }

                @Override // com.miui.richeditor.EditorActionListener
                public void onRichTextLoaded() {
                    if (PcNoteRichEditorController.this.mDataLoadedCallback != null) {
                        PcNoteRichEditorController.this.mDataLoadedCallback.run();
                    }
                    if (PcNoteRichEditorController.this.mPendingTaskOnDataLoaded != null) {
                        PcNoteRichEditorController.this.mPendingTaskOnDataLoaded.run();
                        PcNoteRichEditorController.this.mPendingTaskOnDataLoaded = null;
                    }
                    PcNoteRichEditorController.this.mFragment.onLoadDataFinish();
                }

                @Override // com.miui.richeditor.EditorActionListener
                public void onImageSpanAnnotationClick(BaseImageSpan baseImageSpan, int[] iArr) {
                    PcNoteRichEditorController.this.mFragment.annotateImage(baseImageSpan);
                }

                @Override // com.miui.richeditor.EditorActionListener
                public void onHintSpanClick(EditorHintEntrySpan editorHintEntrySpan, boolean z, int[] iArr) {
                    PcNoteRichEditorController.this.mFragment.onChangeToMindNote();
                }

                @Override // com.miui.pc.richeditor.PcEditorActionListener
                public void onImageMenuClick(View view, BaseImageSpan baseImageSpan) {
                    if (PcNoteRichEditorController.this.mPopMenuListener != null) {
                        PcNoteRichEditorController.this.mPopMenuListener.onImageMenuClick(view, baseImageSpan);
                    }
                }

                @Override // com.miui.pc.richeditor.PcEditorActionListener
                public void onSaveToSynergy(NormalImageSpan normalImageSpan) {
                    PcNoteRichEditorController.this.saveToSynergy(normalImageSpan);
                }

                @Override // com.miui.pc.richeditor.PcEditorActionListener
                public void onImageDragged() {
                    if (PcNoteRichEditorController.this.mFragment.isInEditAction()) {
                        return;
                    }
                    PcNoteRichEditorController.this.mFragment.updateActionMode(EditorActionMode.EDIT, true);
                }
            };
        }
        return this.mActionListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveToSynergy(NormalImageSpan normalImageSpan) {
        if (this.mFragment.isAdded()) {
            File file = new File(AttachmentUtils.getAttachmentPath(getContext(), normalImageSpan.getSrcFileId()));
            if (file.exists()) {
                MiuiSynergySdk.getInstance().saveToSynergy(this.mFragment.getActivity(), new ClipData(new ClipDescription(BaseDragListener.LABEL_NOTE_EDITOR, new String[]{ShareAppsAdapter.TYPE_IMAGE}), new ClipData.Item(ImageUtils.INSTANCE.getImageContentUri(getContext(), file))), "custom extra");
            }
        }
    }

    private void createMind(EditorHintEntrySpan editorHintEntrySpan, boolean z) {
        if (this.mFragment.isReadOnlyMode()) {
            return;
        }
        this.mFragment.cancelSaveTask();
        if (getContext() != null) {
            NoteStore.delete(getContext(), new long[]{this.mFragment.getNoteId()});
        }
        String string = this.mNoteTitleEditor.getText().toString();
        Bundle bundle = new Bundle();
        if (!TextUtils.isEmpty(string)) {
            bundle.putString("note_title", string);
        }
        this.mFragment.notifyAction(11, bundle);
    }
}
