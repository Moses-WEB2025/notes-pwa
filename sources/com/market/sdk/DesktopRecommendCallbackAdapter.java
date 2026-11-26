package com.market.sdk;

import android.os.Bundle;
import android.os.ResultReceiver;

/* JADX INFO: loaded from: classes2.dex */
public class DesktopRecommendCallbackAdapter extends ResultReceiver {
    private static final int CODE_LOAD_FAILED = 1;
    private static final int CODE_LOAD_SUCCESS = 0;
    private static final String KEY_JSON = "json";
    private final DesktopRecommendCallback mAdaptee;

    public DesktopRecommendCallbackAdapter(DesktopRecommendCallback desktopRecommendCallback) {
        super(null);
        this.mAdaptee = desktopRecommendCallback;
    }

    @Override // android.os.ResultReceiver
    protected void onReceiveResult(int i, Bundle bundle) {
        super.onReceiveResult(i, bundle);
        if (i == 0) {
            this.mAdaptee.onLoadSuccess(DesktopRecommendInfo.restore(bundle.getString(KEY_JSON)));
        } else {
            if (i != 1) {
                return;
            }
            this.mAdaptee.onLoadFailed();
        }
    }

    public static class Proxy implements DesktopRecommendCallback {
        private final ResultReceiver mRemote;

        public Proxy(ResultReceiver resultReceiver) {
            this.mRemote = resultReceiver;
        }

        @Override // com.market.sdk.DesktopRecommendCallback
        public void onLoadSuccess(DesktopRecommendInfo desktopRecommendInfo) {
            Bundle bundle = new Bundle(1);
            bundle.putString(DesktopRecommendCallbackAdapter.KEY_JSON, desktopRecommendInfo.convertToJson());
            this.mRemote.send(0, bundle);
        }

        @Override // com.market.sdk.DesktopRecommendCallback
        public void onLoadFailed() {
            this.mRemote.send(1, null);
        }
    }
}
