package com.miui.notes.feature.noteedit;

import android.view.View;
import com.miui.notes.R;
import com.miui.notes.component.noteedit.LiteSmallScreenNoteEditToolBarController;
import com.miui.notes.component.noteedit.NoteEditToolbarController;
import com.miui.notes.theme.util.NoteThemeResCache;
import com.miui.notes.theme.util.ResourceManager;
import com.miui.notes.ui.BaseActionBarController;
import com.miui.notes.ui.PhoneLiteActionBarController;
import com.miui.richeditor.theme.Theme;

/* JADX INFO: loaded from: classes4.dex */
public class PhoneLiteHybridNoteEditFragment extends PhoneHybridNoteEditFragment {
    @Override // com.miui.notes.feature.noteedit.PhoneHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected int getLayoutResId() {
        return R.layout.hybrid_note_edit_lite_layout;
    }

    @Override // com.miui.notes.feature.noteedit.PhoneHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected BaseActionBarController createHeaderBarController(View view) {
        return new PhoneLiteActionBarController(this, this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.miui.notes.feature.noteedit.PhoneHybridNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    public NoteEditToolbarController createToolBarController(View view) {
        return new LiteSmallScreenNoteEditToolBarController(getActivity(), view.findViewById(R.id.editor_mode_tool_group), this.mEditor);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment
    protected void updateNoteThemeOnNative(int i, boolean z, boolean z2) {
        super.updateNoteThemeOnNative(0, z, z2);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment
    protected void onPostUpdateNoteTheme(Theme theme, NoteThemeResCache noteThemeResCache, boolean z) {
        this.mEditor.onThemeChanged(theme, System.currentTimeMillis());
        this.mIsForceDark = theme.getStatusBarStyle(getActivity()).mStatusBar == 2;
        getActionBarController().updateStyle(theme, noteThemeResCache);
        updateWindowStatus(this.mIsForceDark);
        updateEditContentBgStyle(theme, z);
        updateEditorTheme(theme, Boolean.valueOf(z));
        if (this.mCurrentTheme == null || !this.mCurrentTheme.equals(theme)) {
            this.mCurrentTheme = theme;
        }
        if (this.mEditToolbarController != null) {
            this.mEditToolbarController.updateTheme(theme, noteThemeResCache);
        }
        if (this.mSharePopupWindow != null) {
            this.mSharePopupWindow.updateStyle(theme);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected NoteThemeResCache loadNoteThemeResToCache(Theme theme) {
        return super.loadNoteThemeResToCache(ResourceManager.getTheme(0));
    }
}
