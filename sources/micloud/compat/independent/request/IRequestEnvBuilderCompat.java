package micloud.compat.independent.request;

import android.accounts.Account;
import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: loaded from: classes5.dex */
public interface IRequestEnvBuilderCompat {

    public interface RequestEnv {
        long getAutoRetryInterval();

        String getEncryptedUserId(Context context, IBinder iBinder, Account account) throws RemoteException;

        int getMaxRetryCount();

        Account getSystemAccount(Context context);

        String getUserAgent();

        void invalidateAuthToken(Context context);

        String queryAuthToken(Context context) throws QueryAuthTokenException;

        boolean shouldUpdateHost();
    }

    RequestEnv build();
}
