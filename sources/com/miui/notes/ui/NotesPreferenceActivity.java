package com.miui.notes.ui;

import android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import androidx.fragment.app.FragmentManager;
import com.miui.common.tool.Logger;
import com.miui.common.tool.UIUtils;
import com.miui.notes.NoteApp;
import com.miui.notes.cta.CTAActivity;
import com.miui.notes.cta.CTAManager;
import com.miui.notes.feature.settings.PhoneAppSettingsFragment;
import com.miui.notes.tool.HandoffHelper;
import com.miui.notes.tool.PermissionUtils;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.tool.SyncUtils;

/* JADX INFO: loaded from: classes4.dex */
public class NotesPreferenceActivity extends CTAActivity {
    @Override // com.miui.notes.cta.CTAActivity, com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        if (supportFragmentManager.findFragmentById(R.id.content) == null) {
            supportFragmentManager.beginTransaction().replace(R.id.content, new PhoneAppSettingsFragment()).commit();
        }
        this.mHandoffSession = HandoffHelper.onSettings(this);
    }

    public static void open(Activity activity) {
        Logger.INSTANCE.d("NotePreferenceActivity", "open");
        activity.startActivity(new Intent(activity, (Class<?>) NotesPreferenceActivity.class));
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        processActivityResult(i, i2, intent);
    }

    @Override // com.miui.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        if (UIUtils.isInFullWindowGestureMode(this)) {
            UIUtils.setNavigationTrans(this);
        }
    }

    protected void processActivityResult(int i, int i2, Intent intent) {
        if (i == 200) {
            if (i2 == 1) {
                PreferenceUtils.setFirstHandle(false, getApplicationContext());
                this.needShowCtaDialog = false;
                PreferenceUtils.setCTAAccepted(true, getApplicationContext());
                PreferenceUtils.setAllowNetwork(NoteApp.getInstance(), true);
                CTAManager.getInstance().notifyAccept();
                Bundle bundle = new Bundle();
                bundle.putInt("push_type", 0);
                SyncUtils.requestSync(NoteApp.getInstance(), bundle, true);
            } else if (i2 == 666) {
                PreferenceUtils.setFirstHandle(false, getApplicationContext());
                this.needShowCtaDialog = false;
                CTAManager.getInstance().notifyReject();
            }
            this.isShowedCtaDialogV12 = false;
            PermissionUtils.processCtaResult(this, i2);
        }
    }

    @Override // com.miui.notes.cta.CTAActivity, com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService("input_method");
        if (inputMethodManager != null && getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        super.onDestroy();
    }
}
