package com.market.sdk;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;
import androidx.hardware.DataSpace;
import com.market.internal.DesktopRecommendManager;
import com.market.pm.api.ComponentNotFoundException;
import com.market.pm.api.MarketInstaller;
import com.market.pm.api.MarketInstallerListener;
import com.market.sdk.compat.FutureTaskCompat;
import com.market.sdk.homeguide.AppstoreUserGuide;
import com.market.sdk.utils.AppGlobal;
import com.market.sdk.utils.Utils;
import com.market.sdk.utils.WhiteSetManager;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import miuix.os.Build;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class MarketManager {
    private static final String ACTION_DOWNLOAD = "com.xiaomi.market.service.AppDownloadInstallService";
    public static final String COM_XIAOMI_DISCOVER = "com.xiaomi.discover";
    public static final String COM_XIAOMI_MARKET = "com.xiaomi.market";
    public static final String COM_XIAOMI_MIPICKS = "com.xiaomi.mipicks";
    public static final String EXTRA_APK_PATH = "apkPath";
    public static final String EXTRA_PACKGAE_NAME = "packageName";
    public static final String EXTRA_REF = "ref";
    public static final String EXTRA_START_DOWNLOAD = "startDownload";
    public static final String MARKET_PACKAGE_NAME = initMarketPackageName();
    public static final String TAG = "MarketManager";
    private static volatile MarketManager sManager;
    private final String DETAIL_CLASS_NAME = "com.xiaomi.market.ui.AppDetailActivity";
    public final String MARKET_SERVICE_CLASS_NAME = "com.xiaomi.market.data.MarketService";
    private final String MARKET_USER_AGREEMENT_CLASS = "com.xiaomi.market.ui.UserAgreementActivity";
    private Context mContext;

    private MarketManager(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public static MarketManager getManager() {
        if (sManager == null) {
            synchronized (MarketManager.class) {
                if (sManager == null) {
                    sManager = new MarketManager(AppGlobal.getContext());
                }
            }
        }
        return sManager;
    }

    public static Context getContext() {
        return sManager.mContext;
    }

    public static String initMarketPackageName() {
        try {
            return Build.IS_INTERNATIONAL_BUILD ? "com.xiaomi.discover" : "com.xiaomi.market";
        } catch (Throwable unused) {
            return "com.xiaomi.market";
        }
    }

    private void ensureNotUiThread() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new UnsupportedOperationException("Can't call the method on ui thread");
        }
    }

    public static String getMarketPackageName() {
        return MARKET_PACKAGE_NAME;
    }

    public boolean isAppStoreInstalled() {
        return isAppStoreInstalled(true);
    }

    public boolean isAppStoreEnabled() {
        PackageManager packageManager = this.mContext.getPackageManager();
        try {
            if (!isAppStoreInstalled()) {
                return false;
            }
            int applicationEnabledSetting = packageManager.getApplicationEnabledSetting(MARKET_PACKAGE_NAME);
            return applicationEnabledSetting == 0 || applicationEnabledSetting == 1;
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "IllegalArgmentException when get enabled state of appstore : " + e);
            return false;
        }
    }

    public boolean isAppStoreInstalled(boolean z) {
        try {
            ApplicationInfo applicationInfo = this.mContext.getPackageManager().getApplicationInfo(MARKET_PACKAGE_NAME, 0);
            if (applicationInfo != null) {
                return (z && (applicationInfo.flags & 1) == 0) ? false : true;
            }
        } catch (Exception unused) {
        }
        return false;
    }

    public ApkVerifyInfo getApkVerifyInfo(final String str, final String str2, final boolean z) {
        ensureNotUiThread();
        return new RemoteMethodInvoker<ApkVerifyInfo>() { // from class: com.market.sdk.MarketManager.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.market.sdk.RemoteMethodInvoker
            public ApkVerifyInfo innerInvoke(IMarketService iMarketService) {
                try {
                    return iMarketService.getVerifyInfo(str, str2, z);
                } catch (RemoteException unused) {
                    return null;
                }
            }
        }.invoke();
    }

    public ApkVerifyInfo getApkCheckInfo(final String str, final String str2, final boolean z) {
        ensureNotUiThread();
        return new RemoteMethodInvoker<ApkVerifyInfo>() { // from class: com.market.sdk.MarketManager.2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.market.sdk.RemoteMethodInvoker
            public ApkVerifyInfo innerInvoke(IMarketService iMarketService) {
                try {
                    return iMarketService.getApkCheckInfo(str, str2, z);
                } catch (RemoteException unused) {
                    return null;
                }
            }
        }.invoke();
    }

    public boolean isInWhiteSetForApkCheck(String str, String str2) {
        return WhiteSetManager.isInWhiteSetForApkCheck(this.mContext, str, str2);
    }

    public Intent getOpenDetailIntent(String str, String str2) {
        Intent intent = new Intent();
        intent.putExtra("packageName", str);
        intent.putExtra("ref", str2);
        intent.setData(Uri.parse("mimarket://details"));
        intent.setComponent(new ComponentName(MARKET_PACKAGE_NAME, "com.xiaomi.market.ui.AppDetailActivity"));
        return intent;
    }

    public Intent getSearchIntent(String str, String str2) {
        Uri uri = Uri.parse("mimarket://search?q=" + str + "&ref=" + str2);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addCategory("android.intent.category.BROWSABLE");
        intent.setData(uri);
        return intent;
    }

    public void openSearchActivity(Activity activity, String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return;
        }
        Uri uri = Uri.parse("mimarket://search?q=" + str + "&ref=" + str2);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addCategory("android.intent.category.BROWSABLE");
        intent.setData(uri);
        if (activity == null) {
            intent.setFlags(DataSpace.RANGE_LIMITED);
            this.mContext.startActivity(intent);
        } else {
            activity.startActivity(intent);
        }
    }

    public void openSearchActivity(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return;
        }
        Uri uri = Uri.parse("mimarket://search?q=" + str + "&ref=" + str2);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addCategory("android.intent.category.BROWSABLE");
        intent.setData(uri);
        intent.addFlags(DataSpace.RANGE_LIMITED);
        this.mContext.startActivity(intent);
    }

    public void recourdStaticsCountEvent(final String str, final String str2) {
        new RemoteMethodInvoker<Void>() { // from class: com.market.sdk.MarketManager.3
            @Override // com.market.sdk.RemoteMethodInvoker
            public Void innerInvoke(IMarketService iMarketService) {
                try {
                    iMarketService.recordStaticsCountEvent(str, str2);
                    return null;
                } catch (RemoteException unused) {
                    return null;
                }
            }
        }.invokeAsync();
    }

    public boolean allowConnectToNetwork() {
        Boolean boolInvoke;
        ensureNotUiThread();
        if (isAppStoreInstalled(true) && (boolInvoke = new RemoteMethodInvoker<Boolean>() { // from class: com.market.sdk.MarketManager.4
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.market.sdk.RemoteMethodInvoker
            public Boolean innerInvoke(IMarketService iMarketService) {
                try {
                    return Boolean.valueOf(iMarketService.allowConnectToNetwork());
                } catch (Exception e) {
                    Log.e(MarketManager.TAG, "Exception: " + e);
                    return false;
                }
            }
        }.invoke()) != null) {
            return boolInvoke.booleanValue();
        }
        return false;
    }

    public boolean startUserAgreementActivity(Activity activity, int i) {
        if (!isAppStoreInstalled(true) || activity == null) {
            return false;
        }
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(MARKET_PACKAGE_NAME, "com.xiaomi.market.ui.UserAgreementActivity"));
        activity.startActivityForResult(intent, i);
        return true;
    }

    public void loadIcon(String str, String str2, ImageCallback imageCallback) {
        ImageManager.loadIcon(str, str2, imageCallback);
    }

    public void loadImage(String str, int i, int i2, ImageCallback imageCallback) {
        ImageManager.loadImage(str, i, i2, imageCallback);
    }

    public boolean hasFeature(MarketFeatures marketFeatures) {
        return marketFeatures.isSupported();
    }

    public void loadDesktopRecommendInfo(long j, String str, ArrayList<String> arrayList, DesktopRecommendCallback desktopRecommendCallback) {
        if (hasFeature(MarketFeatures.DESK_RECOMMEND_V2)) {
            try {
                MarketService.openService(this.mContext).loadDesktopRecommendInfoV2(j, str, arrayList, new DesktopRecommendCallbackAdapter(desktopRecommendCallback));
                return;
            } catch (RemoteException unused) {
                return;
            }
        }
        DesktopRecommendManager.loadDesktopRecommendInfo(j, str, arrayList, desktopRecommendCallback);
    }

    public void loadDesktopRecommendInfo(long j, String str, ArrayList<String> arrayList, Map<String, String> map, DesktopRecommendCallback desktopRecommendCallback) {
        if (hasFeature(MarketFeatures.DESK_RECOMMEND_V3)) {
            DesktopRecommendArgs desktopRecommendArgs = new DesktopRecommendArgs(j, str, arrayList, map);
            try {
                MarketService.openService(this.mContext).loadDesktopRecommendInfoV3(desktopRecommendArgs.parcel(), new DesktopRecommendCallbackAdapter(desktopRecommendCallback));
                return;
            } catch (RemoteException unused) {
                return;
            }
        }
        loadDesktopRecommendInfo(j, str, arrayList, desktopRecommendCallback);
    }

    public void getDesktopFolderConfig(IDesktopFolderConfigCallback iDesktopFolderConfigCallback) {
        if (hasFeature(MarketFeatures.DESK_RECOMMEND_V3)) {
            try {
                MarketService.openService(this.mContext).getDesktopFolderConfig(new DesktopFolderConfigCallbackAdapter(iDesktopFolderConfigCallback));
                return;
            } catch (RemoteException unused) {
                return;
            }
        }
        iDesktopFolderConfigCallback.onFailed("Market service not impl.");
    }

    public DownloadResponse startDownload(String str, String str2, String str3, Map<String, String> map) {
        DownloadResponse downloadResponse = new DownloadResponse();
        if (TextUtils.isEmpty(str) && TextUtils.isEmpty(str2)) {
            downloadResponse.code = -1;
            return downloadResponse;
        }
        if (!Utils.isConnected(this.mContext)) {
            downloadResponse.code = -2;
            downloadResponse.msg = Utils.getStringResources("install_no_network_description");
            return downloadResponse;
        }
        Intent intent = new Intent(ACTION_DOWNLOAD);
        intent.setPackage(MARKET_PACKAGE_NAME);
        intent.putExtra("appId", str);
        intent.putExtra("packageName", str2);
        intent.putExtra(Constants.EXTRA_SENDER_PACKAGE_NAME, this.mContext.getPackageName());
        intent.putExtra("ref", str3);
        if (map != null) {
            Set<String> setKeySet = map.keySet();
            JSONObject jSONObject = new JSONObject();
            try {
                for (String str4 : setKeySet) {
                    jSONObject.put(str4, map.get(str4));
                }
                intent.putExtra(Constants.EXTRA_PARAMS, jSONObject.toString());
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }
        this.mContext.startService(intent);
        downloadResponse.code = 0;
        return downloadResponse;
    }

    public void updateApplicationEnableState() {
        try {
            EnableStateManager.getManager().updateApplicationEnableState(!Utils.isFirstBoot());
            Log.d(TAG, "isFirstBoot: " + Utils.isFirstBoot());
        } catch (Exception e) {
            Log.w(TAG, e.toString(), e);
        }
    }

    public int getCategory(final String... strArr) {
        ensureNotUiThread();
        if (isAppStoreInstalled(true) && strArr.length != 0) {
            final FutureTaskCompat futureTaskCompat = new FutureTaskCompat();
            new RemoteMethodInvoker<Void>() { // from class: com.market.sdk.MarketManager.5
                @Override // com.market.sdk.RemoteMethodInvoker
                public Void innerInvoke(IMarketService iMarketService) throws RemoteException {
                    iMarketService.getCategoryV2(strArr, new ResultReceiver(null) { // from class: com.market.sdk.MarketManager.5.1
                        @Override // android.os.ResultReceiver
                        protected void onReceiveResult(int i, Bundle bundle) {
                            futureTaskCompat.set(Integer.valueOf(i));
                        }
                    });
                    return null;
                }
            }.invokeAsync();
            Integer num = (Integer) futureTaskCompat.get();
            if (num != null) {
                return num.intValue();
            }
        }
        return -1;
    }

    public String getCategoryName(final String... strArr) {
        ensureNotUiThread();
        if (!isAppStoreInstalled(true) || strArr.length == 0) {
            return null;
        }
        final FutureTaskCompat futureTaskCompat = new FutureTaskCompat();
        new RemoteMethodInvoker<Void>() { // from class: com.market.sdk.MarketManager.6
            @Override // com.market.sdk.RemoteMethodInvoker
            public Void innerInvoke(IMarketService iMarketService) throws RemoteException {
                iMarketService.getCategoryV2(strArr, new ResultReceiver(null) { // from class: com.market.sdk.MarketManager.6.1
                    @Override // android.os.ResultReceiver
                    protected void onReceiveResult(int i, Bundle bundle) {
                        if (bundle != null) {
                            futureTaskCompat.set(bundle.getString("categoryName"));
                        } else {
                            futureTaskCompat.set(null);
                        }
                    }
                });
                return null;
            }
        }.invokeAsync();
        return (String) futureTaskCompat.get();
    }

    public void installSystemPackage(Uri uri, MarketInstallerListener marketInstallerListener) throws ComponentNotFoundException {
        installThirdPartPackage(uri, null, null, null, null, marketInstallerListener);
    }

    public void installThirdPartPackage(Uri uri, String str, String str2, String str3, String str4, MarketInstallerListener marketInstallerListener) throws ComponentNotFoundException {
        MarketInstaller marketInstaller = new MarketInstaller(this.mContext);
        marketInstaller.setListener(marketInstallerListener);
        marketInstaller.installPackage(uri, str, str2, str3, str4);
    }

    public AppstoreUserGuide getAppstoreUserGuide() {
        return new AppstoreUserGuide();
    }

    public DiscoverUpdateManager getDiscoverUpdateManager() {
        return DiscoverUpdateManager.get();
    }

    public FloatCardManager getFloatCardManager() {
        return FloatCardManager.get((Application) this.mContext.getApplicationContext());
    }

    public DirectMailManager getDirectMailManager() {
        return DirectMailManager.get();
    }
}
