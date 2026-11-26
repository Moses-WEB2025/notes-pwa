package com.xiaomi.passport.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import com.xiaomi.accountsdk.account.serverpassthrougherror.ServerPassThroughErrorConstants;
import com.xiaomi.accountsdk.account.stat.AccountStatInterface;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.passport.ui.utils.AdaptUtil;
import java.util.Locale;

/* JADX INFO: loaded from: classes4.dex */
public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";

    protected abstract StatParams getStatParams();

    public static class StatParams {
        public final String mActivityRefName;
        public final String mStatViewTip;

        public StatParams(String str, String str2) {
            this.mActivityRefName = str;
            this.mStatViewTip = str2;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        AdaptUtil.fitDeviceNotch(this);
        updateLocaleConfiguration();
        adapterCutoutMode();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        AccountStatInterface.getInstance().statEventWithTip("view", getStatParams().mStatViewTip, "ref", getPageRef());
    }

    private void updateLocaleConfiguration() {
        Locale locale = Locale.getDefault();
        AccountLogger.log(TAG, "updateLocaleConfiguration>>>locale: " + locale);
        Configuration configuration = getResources().getConfiguration();
        configuration.setLocale(locale);
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
    }

    public String getPageRef() {
        Intent intent = getIntent();
        if (!TextUtils.isEmpty(intent.getStringExtra("ref"))) {
            return intent.getStringExtra("ref");
        }
        if (intent.getBooleanExtra(ServerPassThroughErrorConstants.PARAM_BOOLEAN_IS_FROM_PASS_THROUGH_ERROR_JUMP, false)) {
            return intent.getStringExtra(ServerPassThroughErrorConstants.PARAM_STRING_TYPE);
        }
        Uri referrer = getReferrer();
        return referrer == null ? "Unknown" : referrer.getHost();
    }

    protected void statClick(int i) {
        AccountStatInterface.getInstance().statEventWithTip(com.xiaomi.accountsdk.account.stat.StatConstants.Event.CLICK, getString(i), "ref", getStatParams().mActivityRefName);
    }

    private void adapterCutoutMode() {
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        if (Build.VERSION.SDK_INT >= 30) {
            attributes.layoutInDisplayCutoutMode = 3;
        } else {
            attributes.layoutInDisplayCutoutMode = 1;
        }
        getWindow().setAttributes(attributes);
    }
}
