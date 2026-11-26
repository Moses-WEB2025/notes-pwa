package micloud.compat.independent.request;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import com.xiaomi.accountsdk.account.AccountIntent;

/* JADX INFO: loaded from: classes5.dex */
public class BindAccountServiceCompatInternal implements IBindAccountServiceCompat {
    @Override // micloud.compat.independent.request.IBindAccountServiceCompat
    public boolean bindAccountService(Context context, ServiceConnection serviceConnection) {
        return bindAccountService(context, "com.xiaomi.account.action.BIND_XIAOMI_ACCOUNT_SERVICE", serviceConnection) || bindAccountService(context, "android.intent.action.BIND_XIAOMI_ACCOUNT_SERVICE", serviceConnection);
    }

    private static boolean bindAccountService(Context context, String str, ServiceConnection serviceConnection) {
        Intent intent = new Intent(str);
        intent.setPackage(AccountIntent.PACKAGE_XIAOMI_ACCOUNT);
        return context.bindService(intent, serviceConnection, 1);
    }
}
