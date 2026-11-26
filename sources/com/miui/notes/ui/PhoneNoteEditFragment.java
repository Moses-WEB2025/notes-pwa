package com.miui.notes.ui;

import android.view.View;
import com.miui.notes.R;
import com.miui.notes.component.noteedit.IBaseNoteEditToolbarController;
import com.miui.notes.feature.noteedit.MultiThemesSmallScreenNoteEditToolbarController;
import com.miui.notes.feature.noteedit.PhoneNativeNoteEditFragment;
import com.miui.notes.theme.AnimTheme;
import com.miui.notes.theme.util.NoteThemeResCache;
import com.miui.richeditor.theme.Theme;

/* JADX INFO: loaded from: classes4.dex */
public class PhoneNoteEditFragment extends PhoneNativeNoteEditFragment {
    private static final String TAG = "PhoneNoteEditFragment";

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected int getLayoutResId() {
        return R.layout.phone_note_edit;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected BaseActionBarController createHeaderBarController(View view) {
        return new PhoneActionBarController(this, this);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected IBaseNoteEditToolbarController createToolBarController(View view) {
        return new MultiThemesSmallScreenNoteEditToolbarController(getActivity(), view, this.mEditorView);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void updateNoteTheme(Theme theme, boolean z) {
        this.mEditToolbarController.lock();
        super.updateNoteTheme(theme, z);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected NoteThemeResCache loadNoteThemeResToCache(Theme theme) {
        if (theme instanceof AnimTheme) {
            ((AnimTheme) theme).getBackgroundView(getContext());
        }
        return super.loadNoteThemeResToCache(theme);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void onPostUpdateNoteTheme(Theme theme, NoteThemeResCache noteThemeResCache, boolean z) {
        super.onPostUpdateNoteTheme(theme, noteThemeResCache, z);
        if (this.mAnnotationController != null) {
            this.mAnnotationController.updateStyle(theme);
        }
    }
}
