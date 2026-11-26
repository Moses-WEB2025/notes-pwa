package com.market.sdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import com.market.ServiceProxy;
import com.market.sdk.compat.FutureTaskCompat;
import com.market.sdk.utils.AppGlobal;
import com.market.sdk.utils.Log;
import com.xiaomi.market.IDetailsPageManager;

/* JADX INFO: loaded from: classes2.dex */
class DetailsPageService extends ServiceProxy implements IDetailsPageManager {
    static final String SERVICE_NAME = "com.xiaomi.market.data.DetailsPageService";
    private static final String TAG = "DetailsPageService";
    private IDetailsPageManager manager;

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return null;
    }

    @Override // com.market.ServiceProxy
    public void onDisconnected() {
    }

    static DetailsPageService openConnect() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.xiaomi.market", SERVICE_NAME));
        return new DetailsPageService(AppGlobal.getContext(), intent);
    }

    private DetailsPageService(Context context, Intent intent) {
        super(context, intent);
    }

    @Override // com.market.ServiceProxy
    public void onConnected(IBinder iBinder) {
        this.manager = IDetailsPageManager.Stub.asInterface(iBinder);
    }

    @Override // com.xiaomi.market.IDetailsPageManager
    public boolean openDetailsPage(final Bundle bundle) throws RemoteException {
        final FutureTaskCompat futureTaskCompat = new FutureTaskCompat();
        setTask(new ServiceProxy.ProxyTask() { // from class: com.market.sdk.DetailsPageService.1
            @Override // com.market.ServiceProxy.ProxyTask
            public void run() throws RemoteException {
                if (DetailsPageService.this.manager != null) {
                    futureTaskCompat.set(Boolean.valueOf(DetailsPageService.this.manager.openDetailsPage(bundle)));
                } else {
                    Log.e(DetailsPageService.TAG, "IDetailsPageManager is null");
                }
            }
        }, "open_market_request");
        waitForCompletion();
        if (futureTaskCompat.isDone()) {
            return ((Boolean) futureTaskCompat.get()).booleanValue();
        }
        return false;
    }

    void openDetailPageAsync(final Bundle bundle, final DetailsPageManager.OpenMarketCallback openMarketCallback) throws RemoteException {
        setTask(new ServiceProxy.ProxyTask() { // from class: com.market.sdk.DetailsPageService.2
            @Override // com.market.ServiceProxy.ProxyTask
            public void run() throws RemoteException {
                if (DetailsPageService.this.manager != null) {
                    openMarketCallback.onMarketOpened(DetailsPageService.this.manager.openDetailsPage(bundle));
                } else {
                    Log.e(DetailsPageService.TAG, "IDetailsPageManager is null");
                }
            }
        }, "open_market_request_async");
    }
}
