package com.market.sdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.market.sdk.compat.FutureTaskCompat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: classes2.dex */
public abstract class RemoteMethodInvoker<T> extends FutureTaskCompat<T> implements ServiceConnection {
    private static final String TAG = "RemoteMethodInvoker";
    private static ExecutorService sExcecutors = Executors.newCachedThreadPool();
    private final String MARKET_SERVICE_CLASS_NAME = "com.xiaomi.market.data.MarketService";
    private T mResult = null;
    private Context mContext = MarketManager.getContext();

    public abstract T innerInvoke(IMarketService iMarketService) throws RemoteException;

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, final IBinder iBinder) {
        sExcecutors.execute(new Runnable() { // from class: com.market.sdk.RemoteMethodInvoker.1
            @Override // java.lang.Runnable
            public void run() {
                Context context;
                IMarketService iMarketServiceAsInterface = IMarketService.Stub.asInterface(iBinder);
                RemoteMethodInvoker.this.mResult = null;
                try {
                    try {
                        try {
                            RemoteMethodInvoker remoteMethodInvoker = RemoteMethodInvoker.this;
                            remoteMethodInvoker.mResult = remoteMethodInvoker.innerInvoke(iMarketServiceAsInterface);
                            context = RemoteMethodInvoker.this.mContext;
                        } catch (RemoteException e) {
                            Log.e(RemoteMethodInvoker.TAG, "error while invoking market service methods", e);
                            context = RemoteMethodInvoker.this.mContext;
                        }
                        context.unbindService(RemoteMethodInvoker.this);
                        RemoteMethodInvoker remoteMethodInvoker2 = RemoteMethodInvoker.this;
                        remoteMethodInvoker2.set(remoteMethodInvoker2.mResult);
                    } catch (Throwable th) {
                        try {
                            RemoteMethodInvoker.this.mContext.unbindService(RemoteMethodInvoker.this);
                        } catch (Exception unused) {
                        }
                        throw th;
                    }
                } catch (Exception unused2) {
                }
            }
        });
    }

    public T invoke() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(MarketManager.MARKET_PACKAGE_NAME, "com.xiaomi.market.data.MarketService"));
        if (this.mContext.bindService(intent, this, 1)) {
            return get();
        }
        Log.e(TAG, "Can not find MarketService");
        return null;
    }

    public void invokeAsync() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(MarketManager.MARKET_PACKAGE_NAME, "com.xiaomi.market.data.MarketService"));
        this.mContext.bindService(intent, this, 1);
    }

    public void invokeInNewThread() {
        sExcecutors.execute(new Runnable() { // from class: com.market.sdk.RemoteMethodInvoker.2
            @Override // java.lang.Runnable
            public void run() {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(MarketManager.MARKET_PACKAGE_NAME, "com.xiaomi.market.data.MarketService"));
                RemoteMethodInvoker.this.mContext.bindService(intent, RemoteMethodInvoker.this, 1);
            }
        });
    }
}
