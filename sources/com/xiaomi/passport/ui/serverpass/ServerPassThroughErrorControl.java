package com.xiaomi.passport.ui.serverpass;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import com.xiaomi.accountsdk.account.serverpassthrougherror.ServerPassThroughErrorBaseControl;
import com.xiaomi.accountsdk.account.serverpassthrougherror.base.CustomJumpIntentInterface;
import com.xiaomi.accountsdk.account.serverpassthrougherror.data.ButtonInfo;
import com.xiaomi.accountsdk.account.serverpassthrougherror.data.ErrorHandleInfo;
import com.xiaomi.accountsdk.account.serverpassthrougherror.data.NativePageInfo;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.passport.ui.internal.PassportJsbWebViewActivity;
import com.xiaomi.passport.ui.page.AccountLoginActivity;
import com.xiaomi.passport.ui.view.PassportDialog;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class ServerPassThroughErrorControl extends ServerPassThroughErrorBaseControl {
    private static final String PAGE_LOGIN_ACTIVITY = "LoginActivity";
    private static final String PAGE_WEB_ACTIVITY = "PassportJsbWebViewActivity";
    private static final String TAG = "ServerPassThroughErrorControl";
    private static final Map<String, NativePageInfo> sPageMap = new HashMap();

    private static void initPageMap() {
        Map<String, NativePageInfo> map = sPageMap;
        map.put("PassportJsbWebViewActivity", new NativePageInfo(PassportJsbWebViewActivity.class));
        map.put(PAGE_LOGIN_ACTIVITY, new NativePageInfo(AccountLoginActivity.class, new CustomJumpIntentInterface() { // from class: com.xiaomi.passport.ui.serverpass.ServerPassThroughErrorControl.1
            @Override // com.xiaomi.accountsdk.account.serverpassthrougherror.base.CustomJumpIntentInterface
            public Intent getCustomIntent(Activity activity, NativePageInfo nativePageInfo, Map<String, Object> map2) {
                Bundle bundleCreateJumpPageParamsBundle = ServerPassThroughErrorBaseControl.createJumpPageParamsBundle(map2);
                Intent intent = new Intent(activity, nativePageInfo.pageClass);
                intent.putExtras(activity.getIntent().getExtras());
                intent.putExtras(bundleCreateJumpPageParamsBundle);
                intent.setFlags(603979776);
                return intent;
            }
        }));
    }

    @Override // com.xiaomi.accountsdk.account.serverpassthrougherror.ServerPassThroughErrorBaseControl
    protected Map<String, NativePageInfo> getNativePageInfoMap() {
        Map<String, NativePageInfo> map = sPageMap;
        if (map.isEmpty()) {
            initPageMap();
        }
        return map;
    }

    @Override // com.xiaomi.accountsdk.account.serverpassthrougherror.ServerPassThroughErrorBaseControl
    protected Dialog createDialog(final Activity activity, ErrorHandleInfo errorHandleInfo) {
        AccountLogger.log(TAG, "createDialog>>>" + errorHandleInfo);
        PassportDialog passportDialog = new PassportDialog(activity);
        passportDialog.setTitle(errorHandleInfo.title);
        passportDialog.setMessage(errorHandleInfo.msgContentSpan);
        final ButtonInfo buttonInfo = errorHandleInfo.negativeButtonInfo;
        if (buttonInfo != null) {
            passportDialog.setNegativeButton(buttonInfo.getText(), new View.OnClickListener() { // from class: com.xiaomi.passport.ui.serverpass.ServerPassThroughErrorControl.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ServerPassThroughErrorControl.this.clickDialogClickable(activity, buttonInfo);
                }
            });
        }
        final ButtonInfo buttonInfo2 = errorHandleInfo.neutralButtonInfo;
        if (buttonInfo2 != null) {
            passportDialog.setNeutralButton(buttonInfo2.getText(), new View.OnClickListener() { // from class: com.xiaomi.passport.ui.serverpass.ServerPassThroughErrorControl.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ServerPassThroughErrorControl.this.clickDialogClickable(activity, buttonInfo2);
                }
            });
        }
        final ButtonInfo buttonInfo3 = errorHandleInfo.positiveButtonInfo;
        if (buttonInfo3 != null) {
            passportDialog.setPositiveButton(buttonInfo3.getText(), new View.OnClickListener() { // from class: com.xiaomi.passport.ui.serverpass.ServerPassThroughErrorControl.4
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ServerPassThroughErrorControl.this.clickDialogClickable(activity, buttonInfo3);
                }
            });
        }
        return passportDialog;
    }

    @Override // com.xiaomi.accountsdk.account.serverpassthrougherror.ServerPassThroughErrorBaseControl
    protected boolean jumpToDefWebPageWhenNativeJumpErr(Activity activity, ButtonInfo buttonInfo) {
        AccountLogger.log(TAG, "jumpToDefWebPageWhenNativeJumpErr>>>" + buttonInfo);
        NativePageInfo nativePageInfo = getNativePageInfoMap().get("PassportJsbWebViewActivity");
        if (nativePageInfo != null && !TextUtils.isEmpty(buttonInfo.getExtraWebUrl())) {
            try {
                Map<String, Object> pageParams = buttonInfo.getPageParams();
                if (pageParams == null) {
                    pageParams = new HashMap<>();
                }
                pageParams.put("url", buttonInfo.getExtraWebUrl());
                activity.startActivity(createJumpIntent(activity, nativePageInfo, pageParams));
                return true;
            } catch (Exception e) {
                AccountLogger.log(TAG, "jumpToDefWebPageWhenNativeJumpErr>>>errMsg:" + e.getMessage());
            }
        }
        return false;
    }
}
