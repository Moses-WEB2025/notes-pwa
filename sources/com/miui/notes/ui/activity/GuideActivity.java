package com.miui.notes.ui.activity;

import android.os.Bundle;
import com.miui.common.base.BaseActivity;
import com.miui.notes.R;

/* JADX INFO: loaded from: classes4.dex */
public class GuideActivity extends BaseActivity {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_guide);
    }
}
