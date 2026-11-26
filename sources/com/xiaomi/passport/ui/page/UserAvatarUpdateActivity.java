package com.xiaomi.passport.ui.page;

import android.R;
import android.os.Bundle;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.accountsdk.utils.ParcelableAttackGuardian;
import com.xiaomi.passport.accountmanager.XiaomiAccountManager;
import com.xiaomi.passport.ui.BaseActivity;
import com.xiaomi.passport.ui.internal.util.FriendlyFragmentUtils;

/* JADX INFO: loaded from: classes4.dex */
public class UserAvatarUpdateActivity extends BaseActivity {
    public static final String CAMERA = "camera";
    public static final String EXTRA_UPDATE_ACCOUNT = "update_account";
    public static final String EXTRA_UPDATE_AVATAR_TYPE = "update_avatar_type";
    public static final String GALLERY = "gallery";
    private static final String TAG = "UserAvatarUpdateActivity";

    @Override // com.xiaomi.passport.ui.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (!new ParcelableAttackGuardian().safeCheck(this)) {
            finish();
            return;
        }
        UserAvatarUpdateFragment userAvatarUpdateFragment = new UserAvatarUpdateFragment();
        userAvatarUpdateFragment.setArguments(getIntent().getExtras());
        FriendlyFragmentUtils.addFragment(getFragmentManager(), R.id.content, userAvatarUpdateFragment);
    }

    @Override // com.xiaomi.passport.ui.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        if (XiaomiAccountManager.get(this).getXiaomiAccount() == null) {
            AccountLogger.log(TAG, "no xiaomi account");
            finish();
        }
    }

    @Override // com.xiaomi.passport.ui.BaseActivity
    protected BaseActivity.StatParams getStatParams() {
        return new BaseActivity.StatParams("头像更新页面", null);
    }
}
