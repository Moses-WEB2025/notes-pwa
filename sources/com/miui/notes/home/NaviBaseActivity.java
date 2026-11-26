package com.miui.notes.home;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.hardware.DataSpace;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.base.DialogManagerInterface;
import com.miui.common.stat.GlobalStat;
import com.miui.common.tool.Logger;
import com.miui.common.tool.UIUtils;
import com.miui.notes.NoteApp;
import com.miui.notes.ai.utils.RomUtils;
import com.miui.notes.tool.PermissionUtils;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.tool.SyncUtils;
import miuix.appcompat.app.Fragment;
import miuix.appcompat.app.LayoutUiModeHelper;
import miuix.navigator.app.NavigatorActivity;

/* JADX INFO: loaded from: classes4.dex */
public abstract class NaviBaseActivity extends NavigatorActivity implements DialogManagerInterface {
    private Dialog mManagedDialog;

    @Override // com.miui.common.base.DialogManagerInterface
    public Activity getThisActivity() {
        return this;
    }

    @Override // com.miui.common.base.DialogManagerInterface
    public Fragment getThisFragment() {
        return null;
    }

    protected abstract void onBackAction();

    @Override // miuix.navigator.app.NavigatorActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        LayoutUiModeHelper.autoSetLayoutUiMode(this);
        BaseNoteApp.INSTANCE.setMultiWindowMode(isInMultiWindowMode());
        BaseNoteApp.INSTANCE.setFreeFormScreen(UIUtils.isFreeFormScreen(getResources().getConfiguration()));
        if ((RomUtils.isFoldDevice() && UIUtils.isInNarrowScreen(this)) || RomUtils.isPhoneDevice()) {
            getWindow().addFlags(DataSpace.RANGE_FULL);
        }
        if (!PermissionUtils.supportNewPermissionStyle()) {
            if (PreferenceUtils.isFirstHandle(getApplicationContext())) {
                Logger.INSTANCE.d(getClass().getSimpleName(), "onCreate, first in,old permission style");
                PreferenceUtils.setFirstHandle(false, getApplicationContext());
                PreferenceUtils.setCTAAccepted(true, getApplicationContext());
                PreferenceUtils.setAllowNetwork(NoteApp.getInstance(), true);
                PreferenceUtils.setAllowXiaoai(NoteApp.getInstance(), true);
                Bundle bundle2 = new Bundle();
                bundle2.putInt("push_type", 0);
                SyncUtils.requestSync(NoteApp.getInstance(), bundle2, true);
                return;
            }
            Logger.INSTANCE.d(getClass().getSimpleName(), "onCreate,not first in,old permission style");
            return;
        }
        Logger.INSTANCE.d(getClass().getSimpleName(), "onCreate,support new permission style");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        GlobalStat.onStop();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        super.onStart();
        GlobalStat.onStart();
    }

    @Override // miuix.navigator.app.NavigatorActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        LayoutUiModeHelper.autoSetLayoutUiMode(this);
        if ((RomUtils.isFoldDevice() && UIUtils.isInNarrowScreen(this)) || RomUtils.isPhoneDevice()) {
            getWindow().addFlags(DataSpace.RANGE_FULL);
        }
    }

    @Override // miuix.navigator.app.NavigatorActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override // miuix.navigator.app.NavigatorActivity, miuix.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        LayoutUiModeHelper.autoSetLayoutUiMode(this);
        BaseNoteApp.INSTANCE.setMultiWindowMode(isInMultiWindowMode());
        BaseNoteApp.INSTANCE.setFreeFormScreen(UIUtils.isFreeFormScreen(configuration));
        if ((RomUtils.isFoldDevice() && UIUtils.isInNarrowScreen(this)) || RomUtils.isPhoneDevice()) {
            getWindow().addFlags(DataSpace.RANGE_FULL);
        }
    }

    @Override // com.miui.common.base.DialogManagerInterface
    public void manageDialog(Dialog dialog) {
        this.mManagedDialog = dialog;
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
}
