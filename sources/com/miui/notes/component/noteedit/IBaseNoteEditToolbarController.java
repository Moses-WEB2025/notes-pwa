package com.miui.notes.component.noteedit;

import com.miui.richeditor.IRichTextStyleController;

/* JADX INFO: loaded from: classes4.dex */
public interface IBaseNoteEditToolbarController extends INoteEditToolBar, IRichTextStyleController {
    default void showGalleryPicker() {
    }

    default void updatePaddingBottom(int i) {
    }
}
