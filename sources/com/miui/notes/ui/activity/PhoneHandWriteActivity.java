package com.miui.notes.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import com.miui.common.base.BaseActivity;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.controller.PhoneHandWriteController;
import com.miui.notes.tool.HandoffHelper;
import com.miui.notes.ui.IFragmentController;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes4.dex */
public class PhoneHandWriteActivity extends BaseActivity {
    protected IFragmentController mController;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        IFragmentController iFragmentControllerCreateController = createController();
        this.mController = iFragmentControllerCreateController;
        iFragmentControllerCreateController.onCreate(bundle);
        this.mHandoffSession = HandoffHelper.publishDeepLink(this, new Supplier() { // from class: com.miui.notes.ui.activity.PhoneHandWriteActivity.1
            @Override // java.util.function.Supplier
            public Object get() {
                if (PhoneHandWriteActivity.this.mController != null && (PhoneHandWriteActivity.this.mController instanceof PhoneHandWriteController)) {
                    Uri deepLink = ((PhoneHandWriteController) PhoneHandWriteActivity.this.mController).getDeepLink();
                    Logger.INSTANCE.d("Notes:HandoffHelper", "get handwrite deepLink : " + deepLink);
                    if (deepLink != null) {
                        return deepLink;
                    }
                }
                return Uri.parse("minote://data/notes/");
            }
        }, 3);
    }

    private IFragmentController createController() {
        return new PhoneHandWriteController(this);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        IFragmentController iFragmentController = this.mController;
        if (iFragmentController == null || !iFragmentController.onBackPressed()) {
            setResult(-1);
            super.onBackPressed();
        }
    }
}
