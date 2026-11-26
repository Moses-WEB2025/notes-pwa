package com.xiaomi.passport.servicetoken;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes4.dex */
public final class ServiceTokenUIErrorHandler {
    private ServiceTokenUIErrorHandler() {
    }

    public static ServiceTokenResult blockingHandleIntentError(Context context, ServiceTokenResult serviceTokenResult) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (!activity.isFinishing() && serviceTokenResult != null && serviceTokenResult.errorCode == ServiceTokenResult.ErrorCode.ERROR_USER_INTERACTION_NEEDED && serviceTokenResult.intent != null) {
                return handleWithServiceTokenUIResponse(serviceTokenResult, activity);
            }
        }
        return serviceTokenResult;
    }

    private static ServiceTokenResult handleWithServiceTokenUIResponse(final ServiceTokenResult serviceTokenResult, Activity activity) {
        final ServiceTokenFuture serviceTokenFuture = new ServiceTokenFuture(null);
        serviceTokenResult.intent.putExtra("accountAuthenticatorResponse", new ServiceTokenUIResponse(new IServiceTokenUIResponse.Stub() { // from class: com.xiaomi.passport.servicetoken.ServiceTokenUIErrorHandler.1
            @Override // com.xiaomi.passport.servicetoken.IServiceTokenUIResponse
            public void onResult(Bundle bundle) throws RemoteException {
                serviceTokenFuture.setServerData(ServiceTokenResult.create(bundle, serviceTokenResult.sid));
            }

            @Override // com.xiaomi.passport.servicetoken.IServiceTokenUIResponse
            public void onRequestContinued() throws RemoteException {
                serviceTokenFuture.setServerData(serviceTokenResult);
            }

            @Override // com.xiaomi.passport.servicetoken.IServiceTokenUIResponse
            public void onError(int i, String str) throws RemoteException {
                if (i == 4) {
                    serviceTokenFuture.setServerData(new ServiceTokenResult.Builder(serviceTokenResult.sid).errorCode(ServiceTokenResult.ErrorCode.ERROR_CANCELLED).build());
                } else {
                    serviceTokenFuture.setServerData(serviceTokenResult);
                }
            }
        }));
        activity.startActivity(serviceTokenResult.intent);
        return serviceTokenFuture.get();
    }
}
