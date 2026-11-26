package micloud.compat.independent.request;

import android.accounts.Account;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import com.xiaomi.micloudsdk.data.ExtendedAuthToken;
import com.xiaomi.micloudsdk.remote.RemoteMethodInvoker;
import com.xiaomi.micloudsdk.request.utils.RequestContext;

/* JADX INFO: loaded from: classes5.dex */
public class RequestEnvBuilderCompat {
    private static final String TAG = "RequestEvnBuilderCompat";
    private static IRequestEnvBuilderCompat sRequestEnvBuilderCompatImpl = new RequestEnvBuilderCompatImpl();

    private RequestEnvBuilderCompat() {
    }

    public static void setRequestEnvBuilderCompatImpl(IRequestEnvBuilderCompat iRequestEnvBuilderCompat) {
        sRequestEnvBuilderCompatImpl = iRequestEnvBuilderCompat;
    }

    public static RequestContext.RequestEnv build() {
        final IRequestEnvBuilderCompat.RequestEnv requestEnvBuild = sRequestEnvBuilderCompatImpl.build();
        if (requestEnvBuild == null) {
            return null;
        }
        return new RequestContext.RequestEnv() { // from class: micloud.compat.independent.request.RequestEnvBuilderCompat.1
            @Override // com.xiaomi.micloudsdk.request.utils.RequestContext.RequestEnv
            public ExtendedAuthToken queryAuthToken() throws QueryAuthTokenException {
                String strQueryAuthToken = requestEnvBuild.queryAuthToken(RequestContext.getContext());
                if (strQueryAuthToken == null) {
                    return null;
                }
                return ExtendedAuthToken.parse(strQueryAuthToken);
            }

            @Override // com.xiaomi.micloudsdk.request.utils.RequestContext.RequestEnv
            public void invalidateAuthToken() {
                requestEnvBuild.invalidateAuthToken(RequestContext.getContext());
            }

            @Override // com.xiaomi.micloudsdk.request.utils.RequestContext.RequestEnv
            public String getAccountName() {
                Account systemAccount = requestEnvBuild.getSystemAccount(RequestContext.getContext());
                if (systemAccount == null) {
                    return null;
                }
                return systemAccount.name;
            }

            @Override // com.xiaomi.micloudsdk.request.utils.RequestContext.RequestEnv
            public String queryEncryptedAccountName() {
                final Account systemAccount = requestEnvBuild.getSystemAccount(RequestContext.getContext());
                if (systemAccount == null) {
                    return null;
                }
                return new RemoteMethodInvoker<String>(RequestContext.getContext()) { // from class: micloud.compat.independent.request.RequestEnvBuilderCompat.1.1
                    @Override // com.xiaomi.micloudsdk.remote.RemoteMethodInvoker
                    protected boolean bindService(Context context, ServiceConnection serviceConnection) {
                        return BindAccountServiceCompat.bindAccountService(context, serviceConnection);
                    }

                    /* JADX INFO: Access modifiers changed from: protected */
                    @Override // com.xiaomi.micloudsdk.remote.RemoteMethodInvoker
                    public String invokeRemoteMethod(IBinder iBinder) throws RemoteException {
                        return requestEnvBuild.getEncryptedUserId(RequestContext.getContext(), iBinder, systemAccount);
                    }
                }.invoke();
            }

            @Override // com.xiaomi.micloudsdk.request.utils.RequestContext.RequestEnv
            public String getUserAgent() {
                return requestEnvBuild.getUserAgent();
            }

            @Override // com.xiaomi.micloudsdk.request.utils.RequestContext.RequestEnv
            public boolean shouldUpdateHost() {
                return requestEnvBuild.shouldUpdateHost();
            }

            @Override // com.xiaomi.micloudsdk.request.utils.RequestContext.RequestEnv
            public int getMaxRetryCount() {
                return requestEnvBuild.getMaxRetryCount();
            }

            @Override // com.xiaomi.micloudsdk.request.utils.RequestContext.RequestEnv
            public long getAutoRetryInterval() {
                return requestEnvBuild.getAutoRetryInterval();
            }
        };
    }
}
