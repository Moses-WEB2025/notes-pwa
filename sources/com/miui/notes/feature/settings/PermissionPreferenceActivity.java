package com.miui.notes.feature.settings;

import android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.miui.common.tool.UIUtils;
import com.miui.notes.cta.CTAActivity;

/* JADX INFO: loaded from: classes4.dex */
public class PermissionPreferenceActivity extends CTAActivity {
    @Override // com.miui.notes.cta.CTAActivity, com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        if (supportFragmentManager.findFragmentById(R.id.content) == null) {
            supportFragmentManager.beginTransaction().replace(R.id.content, new PermissionSettingsFragment()).commit();
        }
    }

    @Override // com.miui.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        if (UIUtils.isInFullWindowGestureMode(this)) {
            UIUtils.setNavigationTrans(this);
        }
    }

    @Override // com.miui.notes.cta.CTAActivity, com.miui.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
    }

    public static void open(Activity activity) {
        activity.startActivity(new Intent(activity, (Class<?>) PermissionPreferenceActivity.class));
    }
}
