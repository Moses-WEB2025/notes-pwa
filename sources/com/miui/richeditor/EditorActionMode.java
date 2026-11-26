package com.miui.richeditor;

import com.miui.notes.NoteApp;
import com.miui.notes.R;

/* JADX INFO: loaded from: classes4.dex */
public enum EditorActionMode {
    VIEW("", false),
    EDIT(R.string.done, true),
    EDIT_RECODE(R.string.done, true),
    EDIT_TITLE(R.string.done, true);

    private boolean mIsEditTextEnable;
    private String mName;
    private int mNameStrId;

    EditorActionMode(String str, boolean z) {
        this.mName = str;
        this.mIsEditTextEnable = z;
    }

    EditorActionMode(int i, boolean z) {
        this.mNameStrId = i;
        this.mIsEditTextEnable = z;
    }

    public String getName() {
        if (this.mNameStrId == R.string.done) {
            this.mName = NoteApp.getInstance().getResources().getString(this.mNameStrId);
        }
        return this.mName;
    }

    public boolean isEditTextEnable() {
        return this.mIsEditTextEnable;
    }
}
