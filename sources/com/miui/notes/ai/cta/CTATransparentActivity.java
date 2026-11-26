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
public class CTATransparentActivity extends AppCompatActivity {
    private final String TAG = "CTATransparentActivity";
    private Context mContext;
    private ActivityResultLauncher<Intent> toCtaBeforeAITask;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = this;
        this.toCtaBeforeAITask = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new CtaActivityResult() { // from class: com.miui.notes.ai.cta.CTATransparentActivity.1
            @Override // com.miui.notes.ai.cta.CtaActivityResult
            public void onCtaActivityResult(ActivityResult activityResult) {
                Logger.INSTANCE.i("CTATransparentActivity", "toCtaBeforeAITask result:" + activityResult.getResultCode());
                if (activityResult.getResultCode() == 1) {
                    CTATransparentActivity.this.finish();
                    PreferenceUtils.setPermissionAccepted(CTATransparentActivity.this.mContext, true);
                    if (CtaListenerHolder.getInstance() != null) {
                        CtaListenerHolder.getInstance().agree();
                        return;
                    }
                    return;
                }
                if (activityResult.getResultCode() != -3 && activityResult.getResultCode() == 666) {
                    CTATransparentActivity.this.finish();
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
        if (PreferenceUtils.isCTAAccepted(this) || !PermissionUtils.supportNewPermissionStyle(this)) {
            finish();
        } else if (PreferenceUtils.isFirstEnterNote(this) || !PreferenceUtils.isFirstHandle(this)) {
            PermissionUtils.showCtaDialog(this, this.toCtaBeforeAITask);
        } else {
            finish();
            PreferenceUtils.setFirstEnterNote(this, true);
        }
    }
}
