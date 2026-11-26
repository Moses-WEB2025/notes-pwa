package com.xiaomi.accountsdk.account.serverpassthrougherror.base;

import android.app.Activity;
import android.content.Intent;
import com.xiaomi.accountsdk.account.serverpassthrougherror.data.NativePageInfo;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public interface CustomJumpIntentInterface {
    Intent getCustomIntent(Activity activity, NativePageInfo nativePageInfo, Map<String, Object> map);
}
