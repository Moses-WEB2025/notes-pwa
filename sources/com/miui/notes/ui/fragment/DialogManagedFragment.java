package com.miui.notes.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.EditText;
import com.miui.common.base.BaseFragment;
import com.miui.common.base.DialogManagerInterface;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.notes.R;
import miuix.appcompat.app.Fragment;

/* JADX INFO: loaded from: classes4.dex */
public class DialogManagedFragment extends BaseFragment implements DialogManagerInterface {
    private boolean lastFoldStatus = true;
    private EditText mEditText;
    private Dialog mManagedDialog;

    @Override // com.miui.common.base.DialogManagerInterface
    public Fragment getThisFragment() {
        return this;
    }

    protected void onFoldChangeScreen() {
    }

    @Override // com.miui.common.base.DialogManagerInterface
    public void manageDialog(Dialog dialog) {
        this.mManagedDialog = dialog;
        this.mEditText = (EditText) dialog.findViewById(R.id.et_folder_name);
    }

    public void releaseDialog(Dialog dialog) {
        if (this.mManagedDialog == dialog) {
            this.mManagedDialog = null;
        }
    }

    @Override // com.miui.common.base.DialogManagerInterface
    public boolean dismissDialog() {
        if (!isDialogShowing()) {
            return false;
        }
        this.mManagedDialog.dismiss();
        this.mManagedDialog = null;
        return true;
    }

    @Override // com.miui.common.base.DialogManagerInterface
    public boolean isDialogShowing() {
        Dialog dialog = this.mManagedDialog;
        return dialog != null && dialog.isShowing();
    }

    @Override // com.miui.common.base.DialogManagerInterface
    public Activity getThisActivity() {
        return getActivity();
    }

    @Override // miuix.appcompat.app.Fragment
    public void onVisibilityChanged(boolean z) {
        Dialog dialog;
        super.onVisibilityChanged(z);
        if (z || (dialog = this.mManagedDialog) == null) {
            return;
        }
        dialog.dismiss();
        this.mManagedDialog = null;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDetach() {
        Dialog dialog = this.mManagedDialog;
        if (dialog != null) {
            dialog.dismiss();
            this.mManagedDialog = null;
        }
        super.onDetach();
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.lastFoldStatus = RomUtils.isFoldDevice() && UIUtils.isFoldInternalScreen(getContext());
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (!RomUtils.isFoldDevice() || getContext() == null || this.lastFoldStatus == UIUtils.isFoldInternalScreen(getContext())) {
            return;
        }
        this.lastFoldStatus = UIUtils.isFoldInternalScreen(getContext());
        onFoldChangeScreen();
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        EditText editText;
        super.onPause();
        Dialog dialog = this.mManagedDialog;
        if (dialog == null || !dialog.isShowing() || (editText = this.mEditText) == null) {
            return;
        }
        editText.clearFocus();
        Utils.hideSoftInput(this.mEditText);
    }
}
