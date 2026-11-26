package miuix.micloudview.accounts;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;

/* JADX INFO: loaded from: classes5.dex */
public class ExtraAccountManager {
    public static final String XIAOMI_ACCOUNT_TYPE = "com.xiaomi";

    public static Account getXiaomiAccount(Context context) {
        Account[] accountsByType = AccountManager.get(context).getAccountsByType("com.xiaomi");
        if (accountsByType.length > 0) {
            return accountsByType[0];
        }
        return null;
    }

    public static void requestAccountGrant(Activity activity, int i) {
        activity.startActivityForResult(AccountManager.newChooseAccountIntent(null, null, new String[]{"com.xiaomi"}, true, null, null, null, null), i);
    }
}
