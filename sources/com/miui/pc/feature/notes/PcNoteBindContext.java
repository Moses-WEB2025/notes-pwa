package com.miui.pc.feature.notes;

import com.miui.notes.adapter.BindContext;
import com.miui.pc.component.EditableNoteListWrapper;

/* JADX INFO: loaded from: classes4.dex */
public class PcNoteBindContext extends BindContext {
    private EditableNoteListWrapper<?> mEditableNoteListWrapper;

    public void setEditableNoteListWrapper(EditableNoteListWrapper<?> editableNoteListWrapper) {
        this.mEditableNoteListWrapper = editableNoteListWrapper;
    }

    @Override // com.miui.notes.adapter.BindContext
    public boolean isItemIdChecked(long j) {
        EditableNoteListWrapper<?> editableNoteListWrapper = this.mEditableNoteListWrapper;
        return editableNoteListWrapper != null && editableNoteListWrapper.isItemIdChecked(j);
    }

    @Override // com.miui.notes.adapter.BindContext
    public boolean isInActionMode() {
        EditableNoteListWrapper<?> editableNoteListWrapper = this.mEditableNoteListWrapper;
        return editableNoteListWrapper != null && editableNoteListWrapper.isInActionMode();
    }
}
