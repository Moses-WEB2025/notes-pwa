package com.xiaomi.passport.servicetoken;

import android.os.RemoteException;
import com.xiaomi.accountsdk.futureservice.ClientFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class ServiceTokenFuture extends ClientFuture<ServiceTokenResult, ServiceTokenResult> {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.accountsdk.futureservice.ClientFuture
    public ServiceTokenResult convertServerDataToClientData(ServiceTokenResult serviceTokenResult) throws Throwable {
        return serviceTokenResult;
    }

    public ServiceTokenFuture(ClientFuture.ClientCallback<ServiceTokenResult> clientCallback) {
        super(clientCallback);
    }

    @Override // com.xiaomi.accountsdk.futureservice.ClientFuture
    public void interpretExecutionException(ExecutionException executionException) throws Exception {
        throw new IllegalStateException("not going here");
    }

    @Override // com.xiaomi.accountsdk.futureservice.ClientFuture, java.util.concurrent.FutureTask, java.util.concurrent.Future
    public ServiceTokenResult get() {
        return getInternal(null, null);
    }

    @Override // com.xiaomi.accountsdk.futureservice.ClientFuture, java.util.concurrent.FutureTask, java.util.concurrent.Future
    public ServiceTokenResult get(long j, TimeUnit timeUnit) {
        return getInternal(Long.valueOf(j), timeUnit);
    }

    private ServiceTokenResult getInternal(Long l, TimeUnit timeUnit) {
        try {
            if (l != null && timeUnit != null) {
                return (ServiceTokenResult) super.get(l.longValue(), timeUnit);
            }
            return (ServiceTokenResult) super.get();
        } catch (InterruptedException e) {
            return new ServiceTokenResult.Builder(null).errorCode(ServiceTokenResult.ErrorCode.ERROR_CANCELLED).errorMessage(e.getMessage()).build();
        } catch (ExecutionException e2) {
            if (e2.getCause() instanceof RemoteException) {
                return new ServiceTokenResult.Builder(null).errorCode(ServiceTokenResult.ErrorCode.ERROR_REMOTE_EXCEPTION).errorMessage(e2.getMessage()).build();
            }
            return new ServiceTokenResult.Builder(null).errorCode(ServiceTokenResult.ErrorCode.ERROR_UNKNOWN).errorMessage(e2.getCause() != null ? e2.getCause().getMessage() : e2.getMessage()).build();
        } catch (TimeoutException unused) {
            return new ServiceTokenResult.Builder(null).errorCode(ServiceTokenResult.ErrorCode.ERROR_TIME_OUT).errorMessage("time out after " + l + StringUtils.SPACE + timeUnit).build();
        }
    }

    public static abstract class ServiceTokenCallback implements ClientFuture.ClientCallback<ServiceTokenResult> {
        protected abstract void call(ServiceTokenFuture serviceTokenFuture);

        @Override // com.xiaomi.accountsdk.futureservice.ClientFuture.ClientCallback
        public final void call(ClientFuture<?, ServiceTokenResult> clientFuture) {
            call((ServiceTokenFuture) clientFuture);
        }
    }
}
