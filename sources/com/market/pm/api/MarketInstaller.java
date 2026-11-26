package com.market.pm.api;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import com.market.pm.IMarketInstallerService;

/* JADX INFO: loaded from: classes2.dex */
public class MarketInstaller implements IMarketInstallerContract {
    private final Context mContext;
    private MarketInstallerListener mListener;

    public MarketInstaller(Context context) {
        this.mContext = context;
    }

    public void setListener(MarketInstallerListener marketInstallerListener) {
        this.mListener = marketInstallerListener;
    }

    public void installPackage(Uri uri, String str, String str2, String str3, String str4) throws ComponentNotFoundException {
        if (uri == null) {
            throw new IllegalStateException("uri must not be null.");
        }
        IMarketInstallerService iMarketInstallerServiceOpenService = MarketInstallerService.openService(this.mContext);
        Bundle bundle = new Bundle();
        bundle.putString(IMarketInstallerContract.EXTRA_REF, str);
        bundle.putString(IMarketInstallerContract.EXTRA_APP_CLIENT_ID, str2);
        bundle.putString(IMarketInstallerContract.EXTRA_NONCE, str3);
        bundle.putString(IMarketInstallerContract.EXTRA_APP_SIGNATURE, str4);
        bundle.putString(IMarketInstallerContract.EXTRA_CALLER_PACKAGE_NAME, this.mContext.getPackageName());
        try {
            iMarketInstallerServiceOpenService.installPackage(uri, new MarketInstallObserver(this.mListener), bundle);
        } catch (RemoteException unused) {
        }
    }
}
