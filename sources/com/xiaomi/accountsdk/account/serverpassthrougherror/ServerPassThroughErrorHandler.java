package com.xiaomi.accountsdk.account.serverpassthrougherror;

import android.app.Activity;
import com.xiaomi.accountsdk.account.serverpassthrougherror.data.ErrorHandleInfo;
import com.xiaomi.accountsdk.account.serverpassthrougherror.data.PassThroughErrorInfo;

/* JADX INFO: loaded from: classes4.dex */
public class ServerPassThroughErrorHandler {
    private static ServerPassThroughErrorBaseControl sControl = new ServerPassThroughErrorDefControl();

    public static void setControl(ServerPassThroughErrorBaseControl serverPassThroughErrorBaseControl) {
        sControl = serverPassThroughErrorBaseControl;
    }

    public static ServerPassThroughErrorBaseControl getControl() {
        return sControl;
    }

    public static void handleServerPassThroughError(Activity activity, PassThroughErrorInfo passThroughErrorInfo) {
        handleServerPassThroughError(activity, passThroughErrorInfo, null);
    }

    public static void handleServerPassThroughError(Activity activity, PassThroughErrorInfo passThroughErrorInfo, PassThroughErrorInfo passThroughErrorInfo2) {
        sControl.handleError(activity, new ErrorHandleInfo(activity, passThroughErrorInfo, passThroughErrorInfo2));
    }
}
