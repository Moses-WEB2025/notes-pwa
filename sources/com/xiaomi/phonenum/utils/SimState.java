package com.xiaomi.phonenum.utils;

import android.content.Context;
import android.text.TextUtils;
import com.miui.hybirdeditor.EmptyOverwriteGuard;
import com.xiaomi.account.privacy_data.master.PrivacyDataMaster;
import com.xiaomi.account.privacy_data.master.PrivacyDataType;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.phonenum.phone.PhoneInfo;

/* JADX INFO: loaded from: classes4.dex */
public class SimState {
    public static boolean isValid(Context context, int i) {
        if (TextUtils.isEmpty(PhoneInfo.get(context).tryGetSimForSubId(i).mccmnc)) {
            return false;
        }
        String strForceGet = PrivacyDataMaster.forceGet(context, PrivacyDataType.SIM_IN_SERVICE, String.valueOf(i), String.valueOf(EmptyOverwriteGuard.USER_EDIT_VALID_WINDOW_MS));
        if (strForceGet == null) {
            AccountLogger.log("SimState", "unknown state");
            return false;
        }
        return Boolean.parseBoolean(strForceGet);
    }
}
