package com.market.sdk;

import android.content.ContentProviderClient;
import android.content.Context;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.Settings;
import com.market.sdk.utils.AppGlobal;
import com.market.sdk.utils.Log;

/* JADX INFO: loaded from: classes2.dex */
public class DiscoverUpdateManager {
    private static final String KEY_AUTO_UPDATE = "autoUpdate";
    private static final String KEY_IS_METERED_UPDATE_ANSWERED = "isMeteredUpdateAnswered";
    private static final String KEY_IS_METERED_UPDATE_CONFIRM_NEEDED_BY_REGION = "isMeteredUpdateConfirmNeededByRegion";
    private static final String METHOD_GET_AUTO_UPDATE = "getAutoUpdate";
    private static final String METHOD_IS_METERED_UPDATE_ANSWERED = "isMeteredUpdateAnswered";
    private static final String METHOD_IS_METERED_UPDATE_CONFIRM_NEEDED_BY_REGION = "isMeteredUpdateConfirmNeededByRegion";
    private static final String METHOD_SET_AUTO_UPDATE = "setAutoUpdate";
    private static final String METHOD_SET_METERED_UPDATE_ANSWERED = "setMeteredUpdateAnswered";
    private static final String SETTINGS_DISCOVER_AUTO_UPDATE = "com.xiaomi.discover.auto_update_enabled";
    private static final String SETTINGS_DISCOVER_METERED_UPDATE_ANSWERED = "com.xiaomi.discover.metered_update_answered";
    private static final String SETTINGS_DISCOVER_METERED_UPDATE_CONFIRM_NEEDED_BY_REGION = "com.xiaomi.discover.metered_update_confirm_needed_by_region";
    public static final int STRATEGY_4G = 2;
    public static final int STRATEGY_DISABLED = 0;
    public static final int STRATEGY_INVALID = -1;
    public static final int STRATEGY_WIFI = 1;
    private static DiscoverUpdateManager sInstance = new DiscoverUpdateManager();
    private final Uri DISCOVER_PREFERENCE_AUTHORITY = Uri.parse("content://com.xiaomi.discover.preferences");
    private Context mContext = AppGlobal.getContext();

    public static DiscoverUpdateManager get() {
        return sInstance;
    }

    public int getAutoUpdateStrategy() throws RemoteException, MarketFeatures.FeatureNotSupportedException {
        int i;
        MarketFeatures.DISCOVER_METERED_UPDATE_CONFIRM.throwExceptionIfNotSupported();
        try {
            i = Settings.System.getInt(this.mContext.getContentResolver(), "com.xiaomi.discover.auto_update_enabled");
        } catch (Exception unused) {
            i = -1;
        }
        if (i >= 0) {
            return i;
        }
        ContentProviderClient contentProviderClientAcquireContentProviderClient = this.mContext.getContentResolver().acquireContentProviderClient(this.DISCOVER_PREFERENCE_AUTHORITY);
        int i2 = contentProviderClientAcquireContentProviderClient.call(METHOD_GET_AUTO_UPDATE, null, null).getInt(KEY_AUTO_UPDATE, -1);
        contentProviderClientAcquireContentProviderClient.release();
        return i2;
    }

    public void setAutoUpdateStrategy(int i) throws RemoteException, MarketFeatures.FeatureNotSupportedException {
        MarketFeatures.DISCOVER_METERED_UPDATE_CONFIRM.throwExceptionIfNotSupported();
        try {
            Settings.System.putInt(this.mContext.getContentResolver(), "com.xiaomi.discover.auto_update_enabled", i);
        } catch (Exception unused) {
            ContentProviderClient contentProviderClientAcquireContentProviderClient = this.mContext.getContentResolver().acquireContentProviderClient(this.DISCOVER_PREFERENCE_AUTHORITY);
            contentProviderClientAcquireContentProviderClient.call(METHOD_SET_AUTO_UPDATE, String.valueOf(i), null);
            contentProviderClientAcquireContentProviderClient.release();
        }
    }

    public boolean isMeteredUpdateAnswered() throws RemoteException, MarketFeatures.FeatureNotSupportedException {
        int i;
        MarketFeatures.DISCOVER_METERED_UPDATE_CONFIRM.throwExceptionIfNotSupported();
        try {
            i = Settings.System.getInt(this.mContext.getContentResolver(), "com.xiaomi.discover.metered_update_answered");
        } catch (Exception unused) {
            i = -1;
        }
        if (i < 0) {
            ContentProviderClient contentProviderClientAcquireContentProviderClient = this.mContext.getContentResolver().acquireContentProviderClient(this.DISCOVER_PREFERENCE_AUTHORITY);
            i = contentProviderClientAcquireContentProviderClient.call("isMeteredUpdateAnswered", null, null).getInt("isMeteredUpdateAnswered", -1);
            contentProviderClientAcquireContentProviderClient.release();
        }
        return i > 0;
    }

    public void setMeteredUpdateAnswered(boolean z) throws RemoteException, MarketFeatures.FeatureNotSupportedException {
        MarketFeatures.DISCOVER_METERED_UPDATE_CONFIRM.throwExceptionIfNotSupported();
        try {
            Log.d(MarketManager.TAG, Settings.System.putInt(this.mContext.getContentResolver(), "com.xiaomi.discover.auto_update_enabled", z ? 1 : 0) + "");
        } catch (Exception e) {
            Log.d(MarketManager.TAG, e.toString() + "");
            ContentProviderClient contentProviderClientAcquireContentProviderClient = this.mContext.getContentResolver().acquireContentProviderClient(this.DISCOVER_PREFERENCE_AUTHORITY);
            contentProviderClientAcquireContentProviderClient.call(METHOD_SET_METERED_UPDATE_ANSWERED, String.valueOf(z), null);
            contentProviderClientAcquireContentProviderClient.release();
            Log.d(MarketManager.TAG, "finish");
        }
    }

    public boolean isMeteredUpdateConfirmNeededByRegion() throws RemoteException, MarketFeatures.FeatureNotSupportedException {
        int i;
        MarketFeatures.DISCOVER_METERED_UPDATE_CONFIRM.throwExceptionIfNotSupported();
        try {
            i = Settings.System.getInt(this.mContext.getContentResolver(), "com.xiaomi.discover.metered_update_confirm_needed_by_region");
        } catch (Exception unused) {
            i = -1;
        }
        if (i < 0) {
            ContentProviderClient contentProviderClientAcquireContentProviderClient = this.mContext.getContentResolver().acquireContentProviderClient(this.DISCOVER_PREFERENCE_AUTHORITY);
            i = contentProviderClientAcquireContentProviderClient.call("isMeteredUpdateConfirmNeededByRegion", null, null).getInt("isMeteredUpdateConfirmNeededByRegion", -1);
            contentProviderClientAcquireContentProviderClient.release();
        }
        return i > 0;
    }
}
