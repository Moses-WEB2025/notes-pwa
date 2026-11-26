package com.market.sdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ResultReceiver;
import com.market.ServiceProxy;
import com.market.sdk.compat.FutureTaskCompat;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class MarketService extends ServiceProxy implements IMarketService {
    private static final String MARKET_SERVICE_CLASS_NAME = "com.xiaomi.market.data.MarketService";
    private IMarketService mService;

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return null;
    }

    @Override // com.market.ServiceProxy
    public void onDisconnected() {
    }

    public static IMarketService openService(Context context) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(MarketManager.MARKET_PACKAGE_NAME, MARKET_SERVICE_CLASS_NAME));
        return new MarketService(context, intent);
    }

    private MarketService(Context context, Intent intent) {
        super(context, intent);
    }

    @Override // com.market.ServiceProxy
    public void onConnected(IBinder iBinder) {
        this.mService = IMarketService.Stub.asInterface(iBinder);
    }

    @Override // com.market.sdk.IMarketService
    public ApkVerifyInfo getVerifyInfo(final String str, final String str2, final boolean z) throws RemoteException {
        final FutureTaskCompat futureTaskCompat = new FutureTaskCompat();
        setTask(new ServiceProxy.ProxyTask() { // from class: com.market.sdk.MarketService.1
            @Override // com.market.ServiceProxy.ProxyTask
            public void run() throws RemoteException {
                futureTaskCompat.set(MarketService.this.mService.getVerifyInfo(str, str2, z));
            }
        }, "getVerifyInfo");
        waitForCompletion();
        if (futureTaskCompat.isDone()) {
            return (ApkVerifyInfo) futureTaskCompat.get();
        }
        return null;
    }

    @Override // com.market.sdk.IMarketService
    public ApkVerifyInfo getApkCheckInfo(final String str, final String str2, final boolean z) throws RemoteException {
        final FutureTaskCompat futureTaskCompat = new FutureTaskCompat();
        setTask(new ServiceProxy.ProxyTask() { // from class: com.market.sdk.MarketService.2
            @Override // com.market.ServiceProxy.ProxyTask
            public void run() throws RemoteException {
                futureTaskCompat.set(MarketService.this.mService.getApkCheckInfo(str, str2, z));
            }
        }, "getApkCheckInfo");
        waitForCompletion();
        if (futureTaskCompat.isDone()) {
            return (ApkVerifyInfo) futureTaskCompat.get();
        }
        return null;
    }

    @Override // com.market.sdk.IMarketService
    public boolean allowConnectToNetwork() throws RemoteException {
        final FutureTaskCompat futureTaskCompat = new FutureTaskCompat();
        setTask(new ServiceProxy.ProxyTask() { // from class: com.market.sdk.MarketService.3
            @Override // com.market.ServiceProxy.ProxyTask
            public void run() throws RemoteException {
                futureTaskCompat.set(Boolean.valueOf(MarketService.this.mService.allowConnectToNetwork()));
            }
        }, "allowConnectToNetwork");
        waitForCompletion();
        if (futureTaskCompat.isDone()) {
            return ((Boolean) futureTaskCompat.get()).booleanValue();
        }
        return false;
    }

    @Override // com.market.sdk.IMarketService
    public void recordStaticsCountEvent(final String str, final String str2) throws RemoteException {
        setTask(new ServiceProxy.ProxyTask() { // from class: com.market.sdk.MarketService.4
            @Override // com.market.ServiceProxy.ProxyTask
            public void run() throws RemoteException {
                MarketService.this.mService.recordStaticsCountEvent(str, str2);
            }
        }, "recordStaticsCountEvent");
    }

    @Override // com.market.sdk.IMarketService
    public void loadIcon(final String str, final String str2, final IImageCallback iImageCallback) throws RemoteException {
        setTask(new ServiceProxy.ProxyTask() { // from class: com.market.sdk.MarketService.5
            @Override // com.market.ServiceProxy.ProxyTask
            public void run() throws RemoteException {
                MarketService.this.mService.loadIcon(str, str2, iImageCallback);
            }
        }, "loadIcon");
    }

    @Override // com.market.sdk.IMarketService
    public void loadImage(final String str, final int i, final int i2, final IImageCallback iImageCallback) throws RemoteException {
        setTask(new ServiceProxy.ProxyTask() { // from class: com.market.sdk.MarketService.6
            @Override // com.market.ServiceProxy.ProxyTask
            public void run() throws RemoteException {
                MarketService.this.mService.loadImage(str, i, i2, iImageCallback);
            }
        }, "loadImage");
    }

    @Override // com.market.sdk.IMarketService
    public void loadDesktopRecommendInfo(final long j, final String str, final List<String> list, final IDesktopRecommendResponse iDesktopRecommendResponse) throws RemoteException {
        setTask(new ServiceProxy.ProxyTask() { // from class: com.market.sdk.MarketService.7
            @Override // com.market.ServiceProxy.ProxyTask
            public void run() throws RemoteException {
                MarketService.this.mService.loadDesktopRecommendInfo(j, str, list, iDesktopRecommendResponse);
            }
        }, "loadDesktopRecommendInfo");
    }

    @Override // com.market.sdk.IMarketService
    public boolean isInWhiteSetForApkCheck(final String str) throws RemoteException {
        final FutureTaskCompat futureTaskCompat = new FutureTaskCompat();
        setTask(new ServiceProxy.ProxyTask() { // from class: com.market.sdk.MarketService.8
            @Override // com.market.ServiceProxy.ProxyTask
            public void run() throws RemoteException {
                futureTaskCompat.set(Boolean.valueOf(MarketService.this.mService.isInWhiteSetForApkCheck(str)));
            }
        }, "isInWhiteSetForApkCheck");
        waitForCompletion();
        if (futureTaskCompat.isDone()) {
            return ((Boolean) futureTaskCompat.get()).booleanValue();
        }
        return false;
    }

    @Override // com.market.sdk.IMarketService
    public String getWhiteSet() throws RemoteException {
        final FutureTaskCompat futureTaskCompat = new FutureTaskCompat();
        setTask(new ServiceProxy.ProxyTask() { // from class: com.market.sdk.MarketService.9
            @Override // com.market.ServiceProxy.ProxyTask
            public void run() throws RemoteException {
                futureTaskCompat.set(MarketService.this.mService.getWhiteSet());
            }
        }, "getWhiteSet");
        waitForCompletion();
        return futureTaskCompat.isDone() ? (String) futureTaskCompat.get() : "";
    }

    @Override // com.market.sdk.IMarketService
    public String getEnableSettings() throws RemoteException {
        final FutureTaskCompat futureTaskCompat = new FutureTaskCompat();
        setTask(new ServiceProxy.ProxyTask() { // from class: com.market.sdk.MarketService.10
            @Override // com.market.ServiceProxy.ProxyTask
            public void run() throws RemoteException {
                futureTaskCompat.set(MarketService.this.mService.getEnableSettings());
            }
        }, "getEnableSettings");
        waitForCompletion();
        return futureTaskCompat.isDone() ? (String) futureTaskCompat.get() : "";
    }

    @Override // com.market.sdk.IMarketService
    public int getCategory(final String[] strArr) throws RemoteException {
        final FutureTaskCompat futureTaskCompat = new FutureTaskCompat();
        setTask(new ServiceProxy.ProxyTask() { // from class: com.market.sdk.MarketService.11
            @Override // com.market.ServiceProxy.ProxyTask
            public void run() throws RemoteException {
                futureTaskCompat.set(Integer.valueOf(MarketService.this.mService.getCategory(strArr)));
            }
        }, "getCategory");
        waitForCompletion();
        if (futureTaskCompat.isDone()) {
            return ((Integer) futureTaskCompat.get()).intValue();
        }
        return -1;
    }

    @Override // com.market.sdk.IMarketService
    public void getWhiteSetV2(final ResultReceiver resultReceiver) throws RemoteException {
        setTask(new ServiceProxy.ProxyTask() { // from class: com.market.sdk.MarketService.12
            @Override // com.market.ServiceProxy.ProxyTask
            public void run() throws RemoteException {
                MarketService.this.mService.getWhiteSetV2(resultReceiver);
            }
        }, "getWhiteSetV2");
    }

    @Override // com.market.sdk.IMarketService
    public void getCategoryV2(final String[] strArr, final ResultReceiver resultReceiver) throws RemoteException {
        setTask(new ServiceProxy.ProxyTask() { // from class: com.market.sdk.MarketService.13
            @Override // com.market.ServiceProxy.ProxyTask
            public void run() throws RemoteException {
                MarketService.this.mService.getCategoryV2(strArr, resultReceiver);
            }
        }, "getCategoryV2");
    }

    @Override // com.market.sdk.IMarketService
    public void loadDesktopRecommendInfoV2(final long j, final String str, final List<String> list, final ResultReceiver resultReceiver) throws RemoteException {
        setTask(new ServiceProxy.ProxyTask() { // from class: com.market.sdk.MarketService.14
            @Override // com.market.ServiceProxy.ProxyTask
            public void run() throws RemoteException {
                MarketService.this.mService.loadDesktopRecommendInfoV2(j, str, list, resultReceiver);
            }
        }, "loadDesktopRecommendInfoV2");
    }

    @Override // com.market.sdk.IMarketService
    public void loadDesktopRecommendInfoV3(final Bundle bundle, final ResultReceiver resultReceiver) throws RemoteException {
        setTask(new ServiceProxy.ProxyTask() { // from class: com.market.sdk.MarketService.15
            @Override // com.market.ServiceProxy.ProxyTask
            public void run() throws RemoteException {
                MarketService.this.mService.loadDesktopRecommendInfoV3(bundle, resultReceiver);
            }
        }, "loadDesktopRecommendInfoV3");
    }

    @Override // com.market.sdk.IMarketService
    public void getDesktopFolderConfig(final ResultReceiver resultReceiver) throws RemoteException {
        setTask(new ServiceProxy.ProxyTask() { // from class: com.market.sdk.MarketService.16
            @Override // com.market.ServiceProxy.ProxyTask
            public void run() throws RemoteException {
                MarketService.this.mService.getDesktopFolderConfig(resultReceiver);
            }
        }, "getDesktopFolderConfig");
    }
}
