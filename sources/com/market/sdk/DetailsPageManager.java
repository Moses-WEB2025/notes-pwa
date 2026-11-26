package com.market.sdk;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import androidx.hardware.DataSpace;
import com.market.sdk.utils.AppGlobal;
import com.market.sdk.utils.Log;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class DetailsPageManager {
    private static final String EXTRA_KEY_DATA = "data";
    private static volatile DetailsPageManager INSTANCE = null;
    private static final String TAG = "DetailsPageManager";

    public interface OpenMarketCallback {
        void onMarketOpened(boolean z);
    }

    private DetailsPageManager() {
    }

    public static DetailsPageManager getInstance() {
        if (INSTANCE == null) {
            synchronized (DetailsPageManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DetailsPageManager();
                }
            }
        }
        return INSTANCE;
    }

    public boolean openMarket(DetailPageRequest detailPageRequest) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new UnsupportedOperationException("Can't call this method on ui thread");
        }
        if (!MarketManager.MARKET_PACKAGE_NAME.equals("com.xiaomi.market")) {
            return false;
        }
        if (openMarketIfNotSupport(detailPageRequest)) {
            return true;
        }
        Bundle bundle = new Bundle();
        bundle.putString("data", detailPageRequest.getData());
        try {
            return DetailsPageService.openConnect().openDetailsPage(bundle);
        } catch (Exception e) {
            Log.e(TAG, "open market failed.", e);
            return false;
        }
    }

    public void openMarketAsync(DetailPageRequest detailPageRequest, OpenMarketCallback openMarketCallback) {
        if (MarketManager.MARKET_PACKAGE_NAME.equals("com.xiaomi.market")) {
            if (openMarketIfNotSupport(detailPageRequest)) {
                openMarketCallback.onMarketOpened(true);
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putString("data", detailPageRequest.getData());
            try {
                DetailsPageService.openConnect().openDetailPageAsync(bundle, openMarketCallback);
            } catch (Exception e) {
                Log.e(TAG, "open market failed.", e);
            }
        }
    }

    private boolean openMarketIfNotSupport(DetailPageRequest detailPageRequest) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.xiaomi.market", "com.xiaomi.market.data.DetailsPageService"));
        List<ResolveInfo> listQueryIntentServices = AppGlobal.getContext().getPackageManager().queryIntentServices(intent, 0);
        if (listQueryIntentServices == null || listQueryIntentServices.isEmpty()) {
            Log.w(TAG, "com.xiaomi.market.data.DetailsPageService not found, try start activity with deeplink.");
            Intent intent2 = new Intent("android.intent.action.VIEW");
            intent2.setData(Uri.parse(detailPageRequest.getData()));
            intent2.setFlags(DataSpace.RANGE_LIMITED);
            if (!AppGlobal.getContext().getPackageManager().queryIntentActivities(intent2, 0).isEmpty()) {
                AppGlobal.getContext().startActivity(intent2);
                return true;
            }
        }
        return false;
    }
}
