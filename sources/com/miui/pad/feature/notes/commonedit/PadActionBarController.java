package com.miui.pad.feature.notes.commonedit;

import android.graphics.drawable.Drawable;
import com.miui.notes.R;
import com.miui.notes.ui.BaseActionBarController;
import miuix.appcompat.app.Fragment;

/* JADX INFO: loaded from: classes4.dex */
public class PadActionBarController extends ActionBarWithNaviController {
    private static final String TAG = "PadHeaderController";

    @Override // com.miui.notes.ui.BaseActionBarController
    public int getLayoutResource() {
        return R.layout.pad_note_edit_header;
    }

    public PadActionBarController(Fragment fragment, BaseActionBarController.EditCallback editCallback) {
        super(fragment, editCallback);
    }

    @Override // com.miui.pad.feature.notes.commonedit.ActionBarWithNaviController
    protected Drawable getBackIcon() {
        return this.mTheme.getResTheme(this.mFragment.getContext()).getDrawable(R.drawable.ic_action_back);
    }

    @Override // com.miui.pad.feature.notes.commonedit.ActionBarWithNaviController
    protected Drawable getExpandIcon() {
        return this.mTheme.getResTheme(this.mFragment.getContext()).getDrawable(R.drawable.ic_layout_expand);
    }

    @Override // com.miui.pad.feature.notes.commonedit.ActionBarWithNaviController
    protected Drawable getCollapseIcon() {
        return this.mTheme.getResTheme(this.mFragment.getContext()).getDrawable(R.drawable.ic_layout_collapse);
    }
}
