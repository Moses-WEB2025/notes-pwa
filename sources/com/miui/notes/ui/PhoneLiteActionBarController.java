package com.miui.notes.ui;

import android.view.View;
import com.miui.notes.R;
import com.miui.notes.ui.view.INoteInfoHeader;
import miuix.appcompat.app.Fragment;

/* JADX INFO: loaded from: classes4.dex */
public class PhoneLiteActionBarController extends PhoneActionBarController {
    private static final String TAG = "PhoneLiteHeaderController";

    @Override // com.miui.notes.ui.PhoneActionBarController, com.miui.notes.ui.BaseActionBarController
    public int getLayoutResource() {
        return R.layout.phone_lite_edit_header;
    }

    public PhoneLiteActionBarController(Fragment fragment, BaseActionBarController.EditCallback editCallback) {
        super(fragment, editCallback);
    }

    @Override // com.miui.notes.ui.PhoneActionBarController, com.miui.notes.ui.BaseActionBarController
    public void initializeView(View view, INoteInfoHeader iNoteInfoHeader) {
        super.initializeView(view, iNoteInfoHeader);
        this.mViewThemeView.setEnabled(false);
    }
}
