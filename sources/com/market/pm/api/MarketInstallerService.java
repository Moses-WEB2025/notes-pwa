package com.market.pm.api;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.util.Log;
import com.market.ServiceProxy;
import com.market.pm.IMarketInstallerService;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class MarketInstallerService extends ServiceProxy implements IMarketInstallerService, IMarketInstallerContract {
    private IMarketInstallerService mService;

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return null;
    }

    @Override // com.market.ServiceProxy
    public void onDisconnected() {
    }

    private MarketInstallerService(Context context, Intent intent) {
        super(context, intent);
    }

    static IMarketInstallerService openService(Context context) throws ComponentNotFoundException {
        Intent intent = new Intent(IMarketInstallerContract.ACTION);
        intent.setPackage(MARKET_PACKAGE);
        List<ResolveInfo> listQueryIntentServices = context.getPackageManager().queryIntentServices(intent, 0);
        if (listQueryIntentServices != null && listQueryIntentServices.isEmpty()) {
            throw new ComponentNotFoundException("Not found MarketInstallerService");
        }
        return new MarketInstallerService(context, intent);
    }

    @Override // com.market.ServiceProxy
    public void onConnected(IBinder iBinder) {
        this.mService = IMarketInstallerService.Stub.asInterface(iBinder);
    }

    @Override // com.market.pm.IMarketInstallerService
    public void installPackage(final Uri uri, final ResultReceiver resultReceiver, final Bundle bundle) throws RemoteException {
        setTask(new ServiceProxy.ProxyTask() { // from class: com.market.pm.api.MarketInstallerService.1
            @Override // com.market.ServiceProxy.ProxyTask
            public void run() throws RemoteException {
                if (MarketInstallerService.this.mService == null) {
                    return;
                }
                try {
                    MarketInstallerService.this.mService.installPackage(uri, resultReceiver, bundle);
                } catch (RemoteException e) {
                    Log.w(MarketInstallerService.this.mTag, "fail install package", e);
                    if (resultReceiver instanceof MarketInstallObserver) {
                        new MarketInstallObserver.Proxy(resultReceiver).onServiceDead();
                    }
                    throw e;
                }
            }
        }, "installPackage");
    }
}
