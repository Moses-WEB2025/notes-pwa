package com.miui.notes.home;

import java.util.ArrayList;
import java.util.List;
import miuix.navigator.adapter.CategoryAdapter;

/* JADX INFO: loaded from: classes4.dex */
public class NoteListEditConfig extends CategoryAdapter.EditConfig {
    private List<CategoryAdapter.EditMenu> mEditMenus;

    @Override // miuix.navigator.adapter.CategoryAdapter.EditConfig
    public boolean allowReorder() {
        return false;
    }

    @Override // miuix.navigator.adapter.CategoryAdapter.EditConfig
    public boolean hideWhenEmpty() {
        return true;
    }

    @Override // miuix.navigator.adapter.CategoryAdapter.EditConfig
    public boolean multiChoiceMode() {
        return true;
    }

    @Override // miuix.navigator.adapter.CategoryAdapter.EditConfig
    public boolean showEditWidget() {
        return true;
    }

    @Override // miuix.navigator.adapter.CategoryAdapter.EditConfig
    public boolean showEmptyCategoryEditWidget() {
        return false;
    }

    public NoteListEditConfig(boolean z) {
        super(z, false);
    }

    @Override // miuix.navigator.adapter.CategoryAdapter.EditConfig
    public List<CategoryAdapter.EditMenu> getEditMenu() {
        return this.mEditMenus;
    }

    public NoteListEditConfig addMenu(CategoryAdapter.EditMenu editMenu) {
        if (this.mEditMenus == null) {
            this.mEditMenus = new ArrayList();
        }
        this.mEditMenus.add(editMenu);
        return this;
    }

    public static NoteListEditConfig listNoteConfig(boolean z) {
        return new NoteListEditConfig(z);
    }
}
