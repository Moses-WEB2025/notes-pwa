package com.market.sdk;

import android.app.Activity;
import android.app.Application;
import android.net.Uri;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.market.sdk.utils.AppGlobal;
import com.xiaomi.market.IAppDownloadManager;
import miuix.os.Build;

/* JADX INFO: loaded from: classes2.dex */
public class FloatCardManager {
    private static final String OVERLAY_POSITION = "&overlayPosition=";
    private static volatile FloatCardManager sInstance;
    private String targetPackage;

    public static FloatCardManager get(Application application) {
        if (sInstance == null) {
            synchronized (FloatCardManager.class) {
                if (sInstance == null) {
                    sInstance = new FloatCardManager();
                }
            }
        }
        return sInstance;
    }

    public void setUseGetApps(boolean z) {
        if (Build.IS_INTERNATIONAL_BUILD && z) {
            this.targetPackage = "com.xiaomi.mipicks";
        } else {
            Log.e(MarketManager.TAG, "you can only set target market package name in international build!");
        }
    }

    public boolean downloadOnly(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            FloatService.openService(AppGlobal.getContext(), this.targetPackage).downloadByUri(Uri.parse(str));
            return true;
        } catch (Exception e) {
            Log.e(MarketManager.TAG, e.toString());
            return false;
        }
    }

    @Deprecated
    public boolean downloadByFloat(String str, int i) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        if (i != 1 && i != -1) {
            i = 1;
        }
        try {
            FloatService.openService(AppGlobal.getContext(), this.targetPackage).downloadByUri(Uri.parse(str + OVERLAY_POSITION + i));
            return true;
        } catch (Exception e) {
            Log.e(MarketManager.TAG, e.toString());
            return false;
        }
    }

    public boolean downloadByFloat(String str) {
        if (!TextUtils.isEmpty(str) && str.contains(OVERLAY_POSITION)) {
            try {
                FloatService.openService(AppGlobal.getContext(), this.targetPackage).downloadByUri(Uri.parse(str));
                return true;
            } catch (Exception e) {
                Log.e(MarketManager.TAG, e.toString());
            }
        }
        return false;
    }

    @Deprecated
    public boolean pauseByFloat(String str, int i) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        if (i != 1 && i != -1) {
            i = 1;
        }
        final String str2 = str + OVERLAY_POSITION + i;
        try {
            final IAppDownloadManager iAppDownloadManagerOpenService = FloatService.openService(AppGlobal.getContext(), this.targetPackage);
            if (MarketManager.getManager().hasFeature(MarketFeatures.FLOAT_CARD)) {
                iAppDownloadManagerOpenService.pauseByUri(Uri.parse(str2));
            } else {
                new Thread(new Runnable() { // from class: com.market.sdk.FloatCardManager.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            iAppDownloadManagerOpenService.pause(FloatCardManager.this.getStringFromUri(Uri.parse(str2), "packageName"), AppGlobal.getContext().getPackageName());
                        } catch (RemoteException unused) {
                        }
                    }
                }).start();
            }
            return true;
        } catch (Exception e) {
            Log.e(MarketManager.TAG, e.toString());
            return false;
        }
    }

    public boolean pauseByFloat(final String str) {
        if (!TextUtils.isEmpty(str) && str.contains(OVERLAY_POSITION)) {
            try {
                final IAppDownloadManager iAppDownloadManagerOpenService = FloatService.openService(AppGlobal.getContext(), this.targetPackage);
                if (MarketManager.getManager().hasFeature(MarketFeatures.FLOAT_CARD)) {
                    iAppDownloadManagerOpenService.pauseByUri(Uri.parse(str));
                    return true;
                }
                new Thread(new Runnable() { // from class: com.market.sdk.FloatCardManager.2
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            iAppDownloadManagerOpenService.pause(FloatCardManager.this.getStringFromUri(Uri.parse(str), "packageName"), AppGlobal.getContext().getPackageName());
                        } catch (RemoteException unused) {
                        }
                    }
                }).start();
                return true;
            } catch (Exception e) {
                Log.e(MarketManager.TAG, e.toString());
            }
        }
        return false;
    }

    @Deprecated
    public boolean resumeByFloat(String str, int i) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        if (i != 1 && i != -1) {
            i = 1;
        }
        final String str2 = str + OVERLAY_POSITION + i;
        try {
            final IAppDownloadManager iAppDownloadManagerOpenService = FloatService.openService(AppGlobal.getContext(), this.targetPackage);
            if (MarketManager.getManager().hasFeature(MarketFeatures.FLOAT_CARD)) {
                iAppDownloadManagerOpenService.resumeByUri(Uri.parse(str2));
            } else {
                new Thread(new Runnable() { // from class: com.market.sdk.FloatCardManager.3
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            iAppDownloadManagerOpenService.resume(FloatCardManager.this.getStringFromUri(Uri.parse(str2), "packageName"), AppGlobal.getContext().getPackageName());
                        } catch (RemoteException unused) {
                        }
                    }
                }).start();
            }
            return true;
        } catch (Exception e) {
            Log.e(MarketManager.TAG, e.toString());
            return false;
        }
    }

    public boolean resumeByFloat(final String str) {
        if (!TextUtils.isEmpty(str) && str.contains(OVERLAY_POSITION)) {
            try {
                final IAppDownloadManager iAppDownloadManagerOpenService = FloatService.openService(AppGlobal.getContext(), this.targetPackage);
                if (MarketManager.getManager().hasFeature(MarketFeatures.FLOAT_CARD)) {
                    iAppDownloadManagerOpenService.resumeByUri(Uri.parse(str));
                    return true;
                }
                new Thread(new Runnable() { // from class: com.market.sdk.FloatCardManager.4
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            iAppDownloadManagerOpenService.resume(FloatCardManager.this.getStringFromUri(Uri.parse(str), "packageName"), AppGlobal.getContext().getPackageName());
                        } catch (RemoteException unused) {
                        }
                    }
                }).start();
                return true;
            } catch (Exception e) {
                Log.e(MarketManager.TAG, e.toString());
            }
        }
        return false;
    }

    public boolean lifecycleChanged(Activity activity, int i) {
        try {
            FloatService.openService(AppGlobal.getContext(), this.targetPackage).lifecycleChanged(activity.toString(), i);
            return true;
        } catch (Exception e) {
            Log.e(MarketManager.TAG, e.toString());
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getStringFromUri(Uri uri, String str) {
        if (uri == null || !uri.isHierarchical()) {
            return "";
        }
        return uri.getQueryParameter(str);
    }
}
