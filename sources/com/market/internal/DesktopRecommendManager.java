package com.market.internal;

import android.os.RemoteException;
import android.util.Log;
import com.market.sdk.DesktopRecommendCallback;
import com.market.sdk.DesktopRecommendInfo;
import com.market.sdk.IDesktopRecommendResponse;
import com.market.sdk.IMarketService;
import com.market.sdk.MarketManager;
import com.market.sdk.RemoteMethodInvoker;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes2.dex */
public class DesktopRecommendManager {
    private static Set<Long> sLoadingRequest = new HashSet();

    public static void loadDesktopRecommendInfo(final long j, final String str, final List<String> list, final DesktopRecommendCallback desktopRecommendCallback) {
        synchronized (sLoadingRequest) {
            if (!sLoadingRequest.contains(Long.valueOf(j))) {
                sLoadingRequest.add(Long.valueOf(j));
                new RemoteMethodInvoker<Void>() { // from class: com.market.internal.DesktopRecommendManager.1
                    @Override // com.market.sdk.RemoteMethodInvoker
                    public Void innerInvoke(IMarketService iMarketService) throws RemoteException {
                        try {
                            iMarketService.loadDesktopRecommendInfo(j, str, list, new DesktopRecomendResponse(j, desktopRecommendCallback));
                            return null;
                        } catch (Exception e) {
                            Log.e(MarketManager.TAG, "Exception when load desktop recommend info : " + e);
                            return null;
                        }
                    }
                }.invokeAsync();
            }
        }
    }

    private static class DesktopRecomendResponse extends IDesktopRecommendResponse.Stub {
        private DesktopRecommendCallback mCallback;
        private long mFolderId;

        public DesktopRecomendResponse(long j, DesktopRecommendCallback desktopRecommendCallback) {
            this.mFolderId = j;
            this.mCallback = desktopRecommendCallback;
        }

        @Override // com.market.sdk.IDesktopRecommendResponse
        public void onLoadSuccess(DesktopRecommendInfo desktopRecommendInfo) {
            DesktopRecommendManager.sLoadingRequest.remove(Long.valueOf(this.mFolderId));
            DesktopRecommendCallback desktopRecommendCallback = this.mCallback;
            if (desktopRecommendCallback != null) {
                desktopRecommendCallback.onLoadSuccess(desktopRecommendInfo);
            }
        }

        @Override // com.market.sdk.IDesktopRecommendResponse
        public void onLoadFailed() {
            DesktopRecommendManager.sLoadingRequest.remove(Long.valueOf(this.mFolderId));
            DesktopRecommendCallback desktopRecommendCallback = this.mCallback;
            if (desktopRecommendCallback != null) {
                desktopRecommendCallback.onLoadFailed();
            }
        }
    }
}
