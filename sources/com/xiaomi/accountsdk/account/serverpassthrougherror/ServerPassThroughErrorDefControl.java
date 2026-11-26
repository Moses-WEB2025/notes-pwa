package com.xiaomi.accountsdk.account.serverpassthrougherror;

import android.app.Activity;
import android.app.Dialog;
import com.xiaomi.accountsdk.account.serverpassthrougherror.data.ButtonInfo;
import com.xiaomi.accountsdk.account.serverpassthrougherror.data.ErrorHandleInfo;
import com.xiaomi.accountsdk.account.serverpassthrougherror.data.NativePageInfo;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class ServerPassThroughErrorDefControl extends ServerPassThroughErrorBaseControl {
    @Override // com.xiaomi.accountsdk.account.serverpassthrougherror.ServerPassThroughErrorBaseControl
    protected Dialog createDialog(Activity activity, ErrorHandleInfo errorHandleInfo) {
        return null;
    }

    @Override // com.xiaomi.accountsdk.account.serverpassthrougherror.ServerPassThroughErrorBaseControl
    protected Map<String, NativePageInfo> getNativePageInfoMap() {
        return null;
    }

    @Override // com.xiaomi.accountsdk.account.serverpassthrougherror.ServerPassThroughErrorBaseControl
    protected boolean jumpToDefWebPageWhenNativeJumpErr(Activity activity, ButtonInfo buttonInfo) {
        return false;
    }
}
