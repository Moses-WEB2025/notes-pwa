package com.miui.notes.ai.cta;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import com.miui.notes.ai.utils.PermissionUtils;
import com.miui.notes.ai.utils.PreferenceUtils;
import com.miui.notes.base.utils.Logger;
import miuix.appcompat.app.AppCompatActivity;

/* JADX INFO: loaded from: classes4.dex */
public class GlobalPermissionActivity extends AppCompatActivity {
    public static final int GLOBAL_CTA_RESULT_AGREE = 2;
    public static final int GLOBAL_CTA_RESULT_REJECT = 1;
    private static final int LACK_INFO = -1;
    private static final int LOCAL_CHANGE = -3;
    private static final int NOT_IN_WHITE = -2;
    private static final int REQUEST_CODE = 200;
    private final String TAG = "GlobalPermissionActivity";
    private Context mContext;
    private ActivityResultLauncher<Intent> toGlobalPermissionLauncher;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = this;
        this.toGlobalPermissionLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new CtaActivityResult() { // from class: com.miui.notes.ai.cta.GlobalPermissionActivity.1
            @Override // com.miui.notes.ai.cta.CtaActivityResult
            public void onCtaActivityResult(ActivityResult activityResult) {
                Logger.INSTANCE.i("GlobalPermissionActivity", "toGlobalPermissionLauncher result:" + activityResult.getResultCode());
                if (activityResult.getResultCode() == 2) {
                    GlobalPermissionActivity.this.finish();
                    PreferenceUtils.setGlobalPermissionAccepted(GlobalPermissionActivity.this.mContext, true);
                    if (CtaListenerHolder.getInstance() != null) {
                        CtaListenerHolder.getInstance().agree();
                        return;
                    }
                    return;
                }
                if (activityResult.getResultCode() == -3) {
                    PermissionUtils.requestGlobalPermissionDialog(GlobalPermissionActivity.this.mContext, GlobalPermissionActivity.this.toGlobalPermissionLauncher);
                } else if (activityResult.getResultCode() == 1) {
                    GlobalPermissionActivity.this.finish();
                    if (CtaListenerHolder.getInstance() != null) {
                        CtaListenerHolder.getInstance().reject();
                    }
                }
            }
        });
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        PermissionUtils.requestGlobalPermissionDialog(this.mContext, this.toGlobalPermissionLauncher);
    }
}
