package com.miui.pad.feature.notes.commonedit;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import com.miui.common.tool.Utils;
import com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment;
import com.miui.notes.editor.NoteRichEditor;
import com.miui.notes.editor.NoteRichEditorController;
import com.miui.notes.store.NoteStore;
import com.miui.richeditor.style.EditorHintEntrySpan;

/* JADX INFO: loaded from: classes4.dex */
public class PadNoteRichEditorController extends NoteRichEditorController {
    public PadNoteRichEditorController(BaseNativeNoteEditFragment baseNativeNoteEditFragment, NoteRichEditor noteRichEditor, EditText editText) {
        super(baseNativeNoteEditFragment, noteRichEditor, editText);
    }

    public PadNoteRichEditorController(Activity activity, NoteRichEditor noteRichEditor, EditText editText) {
        super(activity, noteRichEditor, editText);
    }

    @Deprecated
    protected void createMind(EditorHintEntrySpan editorHintEntrySpan, boolean z) {
        if (this.mFragment.isReadOnlyMode()) {
            return;
        }
        Utils.hideSoftInput(this.mFragment.getView());
        this.mFragment.getView().postDelayed(new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.PadNoteRichEditorController.1
            @Override // java.lang.Runnable
            public void run() {
                PadNoteRichEditorController.this.mFragment.cancelSaveTask();
                if (PadNoteRichEditorController.this.getContext() != null) {
                    NoteStore.delete(PadNoteRichEditorController.this.getContext(), new long[]{PadNoteRichEditorController.this.mFragment.getNoteId()});
                }
                String string = PadNoteRichEditorController.this.mNoteTitleEditor.getText().toString();
                Bundle bundle = new Bundle();
                if (!TextUtils.isEmpty(string)) {
                    bundle.putString("note_title", string);
                }
                PadNoteRichEditorController.this.mFragment.notifyAction(11, bundle);
            }
        }, 200L);
    }
}
