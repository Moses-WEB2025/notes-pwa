package com.market.pm.api;

import android.os.Bundle;
import android.os.ResultReceiver;

/* JADX INFO: loaded from: classes2.dex */
public class MarketInstallObserver extends ResultReceiver implements IMarketInstallerCode {
    private static final String KEY_PACKAGE_NAME = "packageName";
    private static final String KEY_RETURN_CODE = "returnCode";
    private static final int RESULT_CODE_INSTALLED = 0;
    private static final int RESULT_CODE_REFUSE = 1;
    private static final int RESULT_CODE_SERVICE_DEAD = 2;
    private final MarketInstallerListener mListener;

    public MarketInstallObserver(MarketInstallerListener marketInstallerListener) {
        super(null);
        this.mListener = marketInstallerListener;
    }

    @Override // android.os.ResultReceiver
    protected void onReceiveResult(int i, Bundle bundle) {
        super.onReceiveResult(i, bundle);
        MarketInstallerListener marketInstallerListener = this.mListener;
        if (marketInstallerListener != null) {
            if (i == 0) {
                marketInstallerListener.packageInstalled(getPackageName(bundle), getCode(bundle));
            } else if (i == 1) {
                marketInstallerListener.onRefuseInstall(getPackageName(bundle), getCode(bundle));
            } else {
                if (i != 2) {
                    return;
                }
                marketInstallerListener.onServiceDead();
            }
        }
    }

    public static class Proxy implements MarketInstallerListener {
        private final ResultReceiver mRemote;

        public Proxy(ResultReceiver resultReceiver) {
            this.mRemote = resultReceiver;
        }

        @Override // com.market.pm.api.MarketInstallerListener
        public void packageInstalled(String str, int i) {
            this.mRemote.send(0, MarketInstallObserver.obtainBundle(str, i));
        }

        @Override // com.market.pm.api.MarketInstallerListener
        public void onRefuseInstall(String str, int i) {
            this.mRemote.send(1, MarketInstallObserver.obtainBundle(str, i));
        }

        @Override // com.market.pm.api.MarketInstallerListener
        public void onServiceDead() {
            this.mRemote.send(2, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Bundle obtainBundle(String str, int i) {
        Bundle bundle = new Bundle(2);
        bundle.putString("packageName", str);
        bundle.putInt(KEY_RETURN_CODE, i);
        return bundle;
    }

    private static String getPackageName(Bundle bundle) {
        return bundle.getString("packageName");
    }

    private static int getCode(Bundle bundle) {
        return bundle.getInt(KEY_RETURN_CODE);
    }
}
