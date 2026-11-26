package com.miui.common.base;

import android.app.Activity;
import android.app.Dialog;
import miuix.appcompat.app.Fragment;

/* JADX INFO: loaded from: classes2.dex */
public interface DialogManagerInterface {
    boolean dismissDialog();

    Activity getThisActivity();

    Fragment getThisFragment();

    boolean isDialogShowing();

    void manageDialog(Dialog dialog);
}
