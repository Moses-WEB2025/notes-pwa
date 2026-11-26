package com.miui.notes.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.hardware.DataSpace;
import com.miui.common.base.BaseActivity;
import com.miui.common.stat.WidgetStat;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.basefeature.mindnote.BaseWebMindNoteFragment;
import com.miui.notes.feature.mindnote.PhoneWebMindNoteFragment;
import com.miui.notes.tool.HandoffHelper;
import com.miui.notes.tool.PermissionUtils;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.tool.SyncUtils;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes4.dex */
public class WebViewBrainActivity extends BaseActivity {
    private static final String TAG = "WebViewBrainActivity";
    private BaseWebMindNoteFragment mWebFragment;

    @Override // com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(DataSpace.RANGE_FULL);
        setContentView(R.layout.webview_layout);
        Logger.INSTANCE.e(TAG, "onCreate");
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        BaseWebMindNoteFragment baseWebMindNoteFragment = (BaseWebMindNoteFragment) supportFragmentManager.findFragmentById(R.id.content);
        this.mWebFragment = baseWebMindNoteFragment;
        if (baseWebMindNoteFragment == null) {
            this.mWebFragment = new PhoneWebMindNoteFragment();
            supportFragmentManager.beginTransaction().add(R.id.content, this.mWebFragment).commit();
        }
        WidgetStat.handleEditEnter(getIntent(), WidgetStat.NOTES_BRAIN_PAGE);
        this.mHandoffSession = HandoffHelper.publishDeepLink(this, new Supplier() { // from class: com.miui.notes.ui.activity.WebViewBrainActivity$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.lambda$onCreate$0();
            }
        }, 4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Object lambda$onCreate$0() {
        BaseWebMindNoteFragment baseWebMindNoteFragment = this.mWebFragment;
        if (baseWebMindNoteFragment != null && (baseWebMindNoteFragment instanceof PhoneWebMindNoteFragment)) {
            Uri deepLink = ((PhoneWebMindNoteFragment) baseWebMindNoteFragment).getDeepLink();
            Logger.INSTANCE.d("Notes:HandoffHelper", "get webmind deeplink: " + deepLink);
            if (deepLink != null) {
                return deepLink;
            }
        }
        return Uri.parse("minote://data/notes/");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPostResume() {
        super.onPostResume();
    }

    @Override // com.miui.common.base.BaseActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        BaseWebMindNoteFragment baseWebMindNoteFragment = this.mWebFragment;
        if (baseWebMindNoteFragment != null) {
            baseWebMindNoteFragment.onNewWebIntent(intent);
        }
    }

    @Override // com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override // com.miui.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        getWindow().addFlags(DataSpace.RANGE_FULL);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        BaseWebMindNoteFragment baseWebMindNoteFragment = this.mWebFragment;
        if (baseWebMindNoteFragment == null || !baseWebMindNoteFragment.onBackPressed()) {
            Logger.INSTANCE.e(TAG, "onBackpressed");
            setResult(-1);
            BaseWebMindNoteFragment baseWebMindNoteFragment2 = this.mWebFragment;
            if (baseWebMindNoteFragment2 != null) {
                baseWebMindNoteFragment2.saveData();
            }
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 200) {
            if (i2 == 1) {
                PreferenceUtils.setFirstHandle(false, getApplicationContext());
                PreferenceUtils.setCTAAccepted(true, getApplicationContext());
                PreferenceUtils.setAllowNetwork(this, true);
                Bundle bundle = new Bundle();
                bundle.putInt("push_type", 0);
                SyncUtils.requestSync(NoteApp.getInstance(), bundle, true);
            } else if (i2 == 666) {
                PreferenceUtils.setFirstHandle(false, getApplicationContext());
                PreferenceUtils.setCTAAccepted(false, getApplicationContext());
            }
            PermissionUtils.processCtaResult(this, i2);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 4) {
            PermissionUtils.processOnStoragePermission(this, strArr, iArr);
        }
    }

    @Override // com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        getWindow().addFlags(DataSpace.RANGE_FULL);
    }
}
