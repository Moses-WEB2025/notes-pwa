package miui.cloud.util;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.text.TextUtils;
import miui.cloud.common.XLogger;
import miui.os.Build;

/* JADX INFO: loaded from: classes5.dex */
public class BuildUtil {
    private static final String KEY_ACCOUNT_INTERNATIONAL = "key_account_international";
    private static final String TAG = "BuildUtil";

    private BuildUtil() {
    }

    public static boolean isInternationalBuild() {
        return Build.IS_INTERNATIONAL_BUILD;
    }

    public static boolean isInternationalBuildOrAccount(Context context, Account account) {
        int iIntValue;
        if (isInternationalBuild()) {
            return true;
        }
        String userData = AccountManager.get(context).getUserData(account, KEY_ACCOUNT_INTERNATIONAL);
        if (TextUtils.isEmpty(userData)) {
            iIntValue = 2;
        } else {
            try {
                iIntValue = Integer.valueOf(userData).intValue();
            } catch (NumberFormatException e) {
                XLogger.loge(TAG, "get account type error " + e);
                iIntValue = 2;
            }
        }
        return iIntValue == 1;
    }
}
