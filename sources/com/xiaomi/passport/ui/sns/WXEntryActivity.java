package com.xiaomi.passport.ui.sns;

import android.app.Activity;
import android.os.Bundle;
import com.xiaomi.passport.share.weixin.WeixinShareHandler;
import com.xiaomi.passport.ui.internal.WeChatAuthProvider;

/* JADX INFO: loaded from: classes4.dex */
public class WXEntryActivity extends Activity {
    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        WeChatAuthProvider.handleWxIntent(this, getIntent());
        WeixinShareHandler.handleWxIntent(this, getIntent());
        finish();
    }
}
