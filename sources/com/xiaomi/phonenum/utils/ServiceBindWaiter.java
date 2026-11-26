package com.xiaomi.phonenum.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes4.dex */
public class ServiceBindWaiter {

    public static class ServiceBindTimeoutException extends Exception {
    }

    public static class ServiceBindResult {
        public final IBinder binder;
        public final ServiceConnection serviceConnection;

        public ServiceBindResult(ServiceConnection serviceConnection, IBinder iBinder) {
            this.serviceConnection = serviceConnection;
            this.binder = iBinder;
        }

        public void unbind(Context context) {
            context.unbindService(this.serviceConnection);
        }
    }

    public static class ServiceBindFailedException extends Exception {
        public ServiceBindFailedException(String str) {
            super(str);
        }
    }

    public static ServiceBindResult bindAndWait(Context context, Intent intent) throws InterruptedException, ServiceBindTimeoutException, ServiceBindFailedException {
        return bindAndWait(context, intent, 5000L);
    }

    public static ServiceBindResult bindAndWait(Context context, Intent intent, long j) throws ServiceBindTimeoutException, InterruptedException, ServiceBindFailedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final IBinder[] iBinderArr = new IBinder[1];
        ServiceConnection serviceConnection = new ServiceConnection() { // from class: com.xiaomi.phonenum.utils.ServiceBindWaiter.1
            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
            }

            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                iBinderArr[0] = iBinder;
                countDownLatch.countDown();
            }
        };
        if (!context.bindService(intent, serviceConnection, 1)) {
            throw new ServiceBindFailedException("can not bind to service intent=" + intent);
        }
        if (!countDownLatch.await(j, TimeUnit.MILLISECONDS)) {
            throw new ServiceBindTimeoutException();
        }
        return new ServiceBindResult(serviceConnection, iBinderArr[0]);
    }
}
