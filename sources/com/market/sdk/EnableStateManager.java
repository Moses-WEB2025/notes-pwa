package com.market.sdk;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.hardware.DataSpace;
import com.market.sdk.utils.CollectionUtils;
import com.market.sdk.utils.PkgUtils;
import com.market.sdk.utils.PrefUtils;
import com.market.sdk.utils.Region;
import com.market.sdk.utils.Utils;
import com.market.sdk.utils.VersionConstraint;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
class EnableStateManager {
    private static final String ACTION_UPDATE_ENABLE_STATE = "com.xiaomi.market.sdk.EnableUpdateReceiver";
    private static String mEnableSettingPath;
    private static EnableStateManager sInstance;
    private volatile boolean receiverRegistered;
    private static Map<String, List<String>> sDefaultEnableRegionList = CollectionUtils.newHashMap();
    private static Map<String, List<String>> sDefaultDisableRegionList = CollectionUtils.newHashMap();
    private static List<String> sEnableStateControlledPkgList = new ArrayList();
    private static List<String> sShouldKeepStatePackages = new ArrayList();
    private Map<String, List<String>> mCloudEnableSettings = CollectionUtils.newConconrrentHashMap();
    private Map<String, List<String>> mCloudDisableSettings = CollectionUtils.newConconrrentHashMap();
    private Context mContext = MarketManager.getContext();

    static {
        sEnableStateControlledPkgList.add("com.xiaomi.mipicks");
        sEnableStateControlledPkgList.add("com.miui.virtualsim");
        sEnableStateControlledPkgList.add("com.xiaomi.glgm");
        sEnableStateControlledPkgList.add("com.mi.globalTrendNews");
        sEnableStateControlledPkgList.add("com.mipay.wallet.in");
        sEnableStateControlledPkgList.add("com.micredit.in");
        sShouldKeepStatePackages.add("com.xiaomi.mipicks");
        ArrayList arrayListNewArrayList = CollectionUtils.newArrayList();
        arrayListNewArrayList.add(Region.IN);
        arrayListNewArrayList.add(Region.RU);
        arrayListNewArrayList.add(Region.ID);
        if (VersionConstraint.of("V10.2.0.0", "8.9.30").isAfter() && !VersionConstraint.of("V10.3.0.0", "9.3.7").isAfter()) {
            arrayListNewArrayList.add(Region.ES);
        }
        ArrayList arrayListNewArrayList2 = CollectionUtils.newArrayList();
        arrayListNewArrayList2.add(Region.ID);
        ArrayList arrayListNewArrayList3 = CollectionUtils.newArrayList();
        arrayListNewArrayList3.add(Region.IN);
        ArrayList arrayListNewArrayList4 = CollectionUtils.newArrayList();
        arrayListNewArrayList4.add(Region.IN);
        sDefaultEnableRegionList.put("com.xiaomi.mipicks", arrayListNewArrayList);
        sDefaultEnableRegionList.put("com.miui.virtualsim", arrayListNewArrayList2);
        sDefaultEnableRegionList.put("com.mipay.wallet.in", arrayListNewArrayList3);
        sDefaultEnableRegionList.put("com.micredit.in", arrayListNewArrayList4);
        sInstance = new EnableStateManager();
    }

    private EnableStateManager() {
        mEnableSettingPath = this.mContext.getFilesDir() + "/package_display_region_settings";
    }

    public static EnableStateManager getManager() {
        return sInstance;
    }

    public void updateApplicationEnableState(boolean z) {
        if (!Utils.isInternationalBuild() || sDefaultEnableRegionList.isEmpty()) {
            return;
        }
        updateApplicationEnableStateInner(z);
        registerReceiverIfNeeded();
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.market.sdk.EnableStateManager$1] */
    private void registerReceiverIfNeeded() {
        if (this.receiverRegistered) {
            return;
        }
        this.receiverRegistered = true;
        new Thread() { // from class: com.market.sdk.EnableStateManager.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                MarketManager.getContext().registerReceiver(new EnableStateUpdateReceiver(), new IntentFilter(EnableStateManager.ACTION_UPDATE_ENABLE_STATE));
                EnableStateManager.this.scheduleEnableStateUpdateReceiver(System.currentTimeMillis() + 864000000);
            }
        }.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scheduleEnableStateUpdateReceiver(long j) {
        AlarmManager alarmManager = (AlarmManager) this.mContext.getSystemService(NotificationCompat.CATEGORY_ALARM);
        Intent intent = new Intent(ACTION_UPDATE_ENABLE_STATE);
        intent.setPackage(this.mContext.getPackageName());
        alarmManager.setExact(1, j, PendingIntent.getBroadcast(this.mContext, 0, intent, DataSpace.RANGE_FULL));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateApplicationEnableStateInner(boolean z) {
        updateCloudConfigFromFile();
        for (String str : sEnableStateControlledPkgList) {
            if (isAppInstalled(str)) {
                updateEnableState(str, z);
            }
        }
        tryUpdateCloudSettings();
    }

    private boolean isAppInstalled(String str) {
        try {
            return this.mContext.getPackageManager().getApplicationInfo(str, 0) != null;
        } catch (Exception e) {
            Log.e(MarketManager.TAG, e.toString());
        }
    }

    private void updateEnableState(String str, boolean z) {
        try {
            String str2 = miuix.core.util.SystemProperties.get("ro.miui.region", "CN");
            Set<String> disableRegions = getDisableRegions(str);
            if (disableRegions.isEmpty()) {
                String string = PrefUtils.getString(Constants.LAST_REGION, null, new PrefUtils.PrefFile[0]);
                if (!TextUtils.isEmpty(string) && !TextUtils.equals(string, str2)) {
                    z = false;
                }
                Set<String> enableSettings = getEnableSettings(str, z);
                Log.d(MarketManager.TAG, "enable " + str + " in " + enableSettings.toString());
                if (enableSettings.contains(str2) || enableSettings.contains("all")) {
                    tryEnablePkg(str);
                } else {
                    tryDisablePkg(str);
                }
            } else {
                Log.d(MarketManager.TAG, "disbale " + str + " in " + disableRegions.toString());
                if (disableRegions.contains(str2) || disableRegions.contains("all")) {
                    tryDisablePkg(str);
                } else {
                    tryEnablePkg(str);
                }
            }
            PrefUtils.setString(Constants.LAST_REGION, str2, new PrefUtils.PrefFile[0]);
        } catch (Throwable th) {
            Log.d(MarketManager.TAG, th.toString());
        }
    }

    private void tryDisablePkg(String str) {
        try {
            PackageManager packageManager = this.mContext.getPackageManager();
            int applicationEnabledSetting = this.mContext.getPackageManager().getApplicationEnabledSetting(str);
            if (applicationEnabledSetting != 0 && applicationEnabledSetting != 1) {
                return;
            }
            packageManager.setApplicationEnabledSetting(str, 2, 0);
        } catch (Throwable th) {
            Log.d(MarketManager.TAG, th.toString());
        }
    }

    private void tryEnablePkg(String str) {
        try {
            PackageManager packageManager = this.mContext.getPackageManager();
            if (this.mContext.getPackageManager().getApplicationEnabledSetting(str) == 2) {
                packageManager.setApplicationEnabledSetting(str, 1, 0);
            }
        } catch (Throwable th) {
            Log.d(MarketManager.TAG, th.toString());
        }
    }

    private Set<String> getDisableRegions(String str) {
        HashSet hashSet = new HashSet();
        List<String> list = this.mCloudDisableSettings.get(str);
        if (list != null) {
            hashSet.addAll(list);
        }
        List<String> list2 = sDefaultDisableRegionList.get(str);
        if (list2 != null) {
            hashSet.addAll(list2);
        }
        String[] stringArray = Utils.getStringArray(str, "disable_regions");
        if (stringArray != null) {
            hashSet.addAll(Arrays.asList(stringArray));
        }
        return hashSet;
    }

    private Set<String> getEnableSettings(String str, boolean z) {
        HashSet hashSet = new HashSet();
        List<String> list = this.mCloudEnableSettings.get(str);
        if (list != null) {
            hashSet.addAll(list);
        }
        Log.d(MarketManager.TAG, "add " + str + " mCloudEnableSettings " + hashSet);
        List<String> list2 = sDefaultEnableRegionList.get(str);
        if (list2 != null) {
            hashSet.addAll(list2);
        }
        Log.d(MarketManager.TAG, "add " + str + " defaultRegions " + hashSet);
        String[] stringArray = Utils.getStringArray(str, "enable_regions");
        if (stringArray != null) {
            hashSet.addAll(Arrays.asList(stringArray));
        }
        Log.d(MarketManager.TAG, "add " + str + " apkPresetRegions " + hashSet);
        if (sShouldKeepStatePackages.contains(str) && TextUtils.equals(Region.getRegion(), Region.ES)) {
            Set<String> stringSet = PrefUtils.getStringSet(Constants.HAS_EVER_ENABLED + str, null, new PrefUtils.PrefFile[0]);
            if (stringSet != null) {
                hashSet.addAll(stringSet);
            }
            Log.d(MarketManager.TAG, "shouldKeep: " + z + "\n is " + str + " enbale " + PkgUtils.isPackageEnabled(str));
            if (z && PkgUtils.isPackageEnabled(str)) {
                Log.d(MarketManager.TAG, "add " + str + " at " + Region.getRegion());
                hashSet.add(Region.getRegion());
            }
            PrefUtils.setStringSet(Constants.HAS_EVER_ENABLED + str, hashSet, new PrefUtils.PrefFile[0]);
        }
        return hashSet;
    }

    private void updateCloudConfigFromFile() {
        this.mCloudEnableSettings.clear();
        this.mCloudDisableSettings.clear();
        BufferedReader bufferedReader = null;
        try {
            BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(new FileInputStream(mEnableSettingPath)));
            try {
                StringBuilder sb = new StringBuilder();
                while (true) {
                    String line = bufferedReader2.readLine();
                    if (line == null) {
                        break;
                    } else {
                        sb.append(line);
                    }
                }
                JSONObject jSONObject = new JSONObject(sb.toString());
                Iterator<String> itKeys = jSONObject.keys();
                while (itKeys.hasNext()) {
                    String next = itKeys.next();
                    JSONObject jSONObject2 = jSONObject.getJSONObject(next);
                    JSONArray jSONArrayOptJSONArray = jSONObject2.optJSONArray("enable_list");
                    if (jSONArrayOptJSONArray != null) {
                        ArrayList arrayListNewArrayList = CollectionUtils.newArrayList();
                        for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                            arrayListNewArrayList.add(jSONArrayOptJSONArray.getString(i));
                        }
                        this.mCloudEnableSettings.put(next, arrayListNewArrayList);
                    }
                    JSONArray jSONArrayOptJSONArray2 = jSONObject2.optJSONArray("disable_list");
                    if (jSONArrayOptJSONArray2 != null) {
                        ArrayList arrayListNewArrayList2 = CollectionUtils.newArrayList();
                        for (int i2 = 0; i2 < jSONArrayOptJSONArray2.length(); i2++) {
                            arrayListNewArrayList2.add(jSONArrayOptJSONArray2.getString(i2));
                        }
                        this.mCloudDisableSettings.put(next, arrayListNewArrayList2);
                    }
                }
                Utils.closeQuietly(bufferedReader2);
            } catch (Throwable th) {
                th = th;
                bufferedReader = bufferedReader2;
                try {
                    Log.e(MarketManager.TAG, th.toString());
                } finally {
                    Utils.closeQuietly(bufferedReader);
                }
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private void tryUpdateCloudSettings() {
        new RemoteMethodInvoker<Object>() { // from class: com.market.sdk.EnableStateManager.2
            @Override // com.market.sdk.RemoteMethodInvoker
            public Object innerInvoke(IMarketService iMarketService) throws Throwable {
                FileOutputStream fileOutputStream;
                Throwable th;
                try {
                    try {
                        String enableSettings = iMarketService.getEnableSettings();
                        if (!TextUtils.isEmpty(enableSettings)) {
                            fileOutputStream = new FileOutputStream(EnableStateManager.mEnableSettingPath);
                            try {
                                fileOutputStream.write(enableSettings.getBytes());
                                fileOutputStream.flush();
                            } catch (Exception e) {
                                e = e;
                                Log.e(MarketManager.TAG, e.toString(), e);
                            }
                            Utils.closeQuietly(fileOutputStream);
                            return null;
                        }
                        Utils.closeQuietly(null);
                        return null;
                    } catch (Exception e2) {
                        e = e2;
                        fileOutputStream = null;
                    } catch (Throwable th2) {
                        fileOutputStream = null;
                        th = th2;
                        Utils.closeQuietly(fileOutputStream);
                        throw th;
                    }
                    Utils.closeQuietly(fileOutputStream);
                    return null;
                } catch (Throwable th3) {
                    th = th3;
                    Utils.closeQuietly(fileOutputStream);
                    throw th;
                }
                Log.e(MarketManager.TAG, e.toString(), e);
            }
        }.invokeInNewThread();
    }

    private class EnableStateUpdateReceiver extends BroadcastReceiver {
        private EnableStateUpdateReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (Utils.isScreenOff()) {
                EnableStateManager.this.updateApplicationEnableStateInner(false);
            } else {
                EnableStateManager.this.scheduleEnableStateUpdateReceiver(System.currentTimeMillis() + 86400000);
            }
        }
    }
}
