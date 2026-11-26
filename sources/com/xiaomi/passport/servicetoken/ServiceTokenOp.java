package com.xiaomi.passport.servicetoken;

import com.xiaomi.accountsdk.utils.PassportExecutors;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes4.dex */
public class ServiceTokenOp {
    private static volatile Executor sOpExecutor = PassportExecutors.newFixedThreadPool(3, "ServiceTokenOp");

    public static abstract class OpWorker {
        protected abstract ServiceTokenResult doWork();

        public ServiceTokenFuture work() {
            final ServiceTokenFuture serviceTokenFuture = new ServiceTokenFuture(null);
            ServiceTokenOp.sOpExecutor.execute(new Runnable() { // from class: com.xiaomi.passport.servicetoken.ServiceTokenOp.OpWorker.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        serviceTokenFuture.setServerData(OpWorker.this.doWork());
                    } catch (Throwable th) {
                        serviceTokenFuture.setServerSideThrowable(th);
                    }
                }
            });
            return serviceTokenFuture;
        }
    }
}
