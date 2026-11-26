package com.market.sdk;

import android.os.Bundle;
import android.os.ResultReceiver;

/* JADX INFO: loaded from: classes2.dex */
public class DesktopFolderConfigCallbackAdapter extends ResultReceiver {
    private static final String KEY_DATA = "key_data";
    private static final int RESULT_CODE_FAILED = 2;
    private static final int RESULT_CODE_SUCCESS = 1;
    private final IDesktopFolderConfigCallback mAdaptee;

    public DesktopFolderConfigCallbackAdapter(IDesktopFolderConfigCallback iDesktopFolderConfigCallback) {
        super(null);
        this.mAdaptee = iDesktopFolderConfigCallback;
    }

    @Override // android.os.ResultReceiver
    protected void onReceiveResult(int i, Bundle bundle) {
        if (i == 1) {
            this.mAdaptee.onSuccess(bundle.getString(KEY_DATA));
        } else {
            if (i != 2) {
                return;
            }
            this.mAdaptee.onFailed(bundle.getString(KEY_DATA));
        }
    }

    public static class Proxy implements IDesktopFolderConfigCallback {
        private final ResultReceiver mRemote;

        public Proxy(ResultReceiver resultReceiver) {
            this.mRemote = resultReceiver;
        }

        @Override // com.market.sdk.IDesktopFolderConfigCallback
        public void onSuccess(String str) {
            Bundle bundle = new Bundle(1);
            bundle.putString(DesktopFolderConfigCallbackAdapter.KEY_DATA, str);
            this.mRemote.send(1, bundle);
        }

        @Override // com.market.sdk.IDesktopFolderConfigCallback
        public void onFailed(String str) {
            Bundle bundle = new Bundle(1);
            bundle.putString(DesktopFolderConfigCallbackAdapter.KEY_DATA, str);
            this.mRemote.send(2, bundle);
        }
    }
}
