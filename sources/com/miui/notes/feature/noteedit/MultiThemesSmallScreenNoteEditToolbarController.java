package com.miui.notes.feature.noteedit;

import android.animation.Animator;
import android.app.Activity;
import android.view.View;
import com.miui.notes.basefeature.noteedit.INoteRichEditor;
import com.miui.notes.component.noteedit.SmallScreenNoteEditToolbarController;
import com.miui.notes.theme.util.ResourceManager;

/* JADX INFO: loaded from: classes4.dex */
public class MultiThemesSmallScreenNoteEditToolbarController extends SmallScreenNoteEditToolbarController {
    protected static String TAG = "MultiThemesSmallScreenNoteEditToolbarController";

    public MultiThemesSmallScreenNoteEditToolbarController(Activity activity, View view, INoteRichEditor iNoteRichEditor) {
        super(activity, view, iNoteRichEditor);
    }

    @Override // com.miui.notes.component.noteedit.SmallScreenNoteEditToolbarController, com.miui.notes.component.noteedit.NoteEditToolbarController, com.miui.notes.component.noteedit.BaseNoteEditToolbarController, com.miui.notes.component.noteedit.INoteEditToolBar
    public void collapsePanel(Animator.AnimatorListener animatorListener, boolean z) {
        boolean z2 = this.mIsShowThemeChooser;
        super.collapsePanel(animatorListener, z);
        if (z2) {
            if (this.mNoteTheme != null) {
                ResourceManager.destroyUnusedAnimThemeView(this.mNoteTheme.getId());
            } else {
                ResourceManager.destroyUnusedAnimThemeView(-1);
            }
        }
    }
}
