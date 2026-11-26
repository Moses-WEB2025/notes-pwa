package com.market.sdk;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import com.market.ServiceProxy;
import com.market.sdk.utils.AppGlobal;
import com.market.sdk.utils.Log;
import com.xiaomi.market.IPreloadAppDetailService;

/* JADX INFO: loaded from: classes2.dex */
public class PreloadAppDetailService extends ServiceProxy implements IPreloadAppDetailService {
    private static final String ACTION_PRELOAD_SERVICE = "com.xiaomi.market.PRELOAD_APP_DETAIL";
    private static final String TAG = "PreloadAppDetailService";
    private IPreloadAppDetailService mAidl;

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return null;
    }

    @Override // com.market.ServiceProxy
    public void onDisconnected() {
    }

    public PreloadAppDetailService(Context context, Intent intent) {
        super(context, intent);
    }

    public static IPreloadAppDetailService openService() {
        Intent intent = new Intent(ACTION_PRELOAD_SERVICE);
        intent.setPackage("com.xiaomi.market");
        return new PreloadAppDetailService(AppGlobal.getContext(), intent);
    }

    @Override // com.xiaomi.market.IPreloadAppDetailService
    public void preloadAppDetail(final String str) throws RemoteException {
        setTask(new ServiceProxy.ProxyTask() { // from class: com.market.sdk.PreloadAppDetailService.1
            @Override // com.market.ServiceProxy.ProxyTask
            public void run() throws RemoteException {
                if (PreloadAppDetailService.this.mAidl != null) {
                    PreloadAppDetailService.this.mAidl.preloadAppDetail(str);
                } else {
                    Log.e(PreloadAppDetailService.TAG, "IPreloadAppDetailService is null");
                }
            }
        }, "preloadAppDetail");
    }

    @Override // com.market.ServiceProxy
    public void onConnected(IBinder iBinder) {
        this.mAidl = IPreloadAppDetailService.Stub.asInterface(iBinder);
    }
}
