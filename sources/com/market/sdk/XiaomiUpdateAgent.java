package com.market.sdk;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import androidx.hardware.DataSpace;
import com.market.sdk.utils.AppGlobal;
import com.market.sdk.utils.Client;
import com.market.sdk.utils.Coder;
import com.market.sdk.utils.Connection;
import com.market.sdk.utils.Log;
import com.market.sdk.utils.PrefUtils;
import com.market.sdk.utils.Utils;
import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import miuix.appcompat.app.AlertDialog;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class XiaomiUpdateAgent {
    private static final String TAG = "MarketUpdateAgent";
    private static volatile XiaomiUpdateAgent instance = null;
    private static LocalAppInfo mAppInfo = null;
    private static boolean mIsInited = false;
    private static boolean mIsLoading = false;
    private static UpdateInfo mUpdateInfo;
    private static XiaomiUpdateListener mUpdateListener;
    private static com.market.sdk.utils.Constants.UpdateMethod mUpdateMethod;
    public static AbTestIdentifier sAbTestIdentifer;
    private static boolean sDebug;
    public static boolean sUseSandbox;
    private static WeakReference<Context> mContext = new WeakReference<>(null);
    private static boolean mAutoPopup = true;
    public static boolean mCheckUpdateOnlyWifi = false;
    public static boolean sUseImeiMd5AsIdentifier = false;

    @Deprecated
    public static long getDownloadId() {
        return -1L;
    }

    public static int getSDKVersion() {
        return 11;
    }

    static {
        mUpdateMethod = Utils.isMiuiPad() ? com.market.sdk.utils.Constants.UpdateMethod.DOWNLOAD_MANAGER : com.market.sdk.utils.Constants.UpdateMethod.MARKET;
        sAbTestIdentifer = AbTestIdentifier.ANDROID_ID;
    }

    private XiaomiUpdateAgent() {
    }

    public static XiaomiUpdateAgent getInstance() {
        if (instance == null) {
            synchronized (XiaomiUpdateAgent.class) {
                if (instance == null) {
                    instance = new XiaomiUpdateAgent();
                }
            }
        }
        return instance;
    }

    public static void enableDebug(boolean z) {
        sDebug = z;
        Utils.DEBUG = z;
    }

    public static synchronized void update(boolean z) {
        update(z, AppGlobal.getContext().getPackageName());
    }

    public static synchronized void update(Context context, boolean z) {
        if (mIsLoading) {
            return;
        }
        mIsLoading = true;
        Client.init(AppGlobal.getContext());
        mContext = new WeakReference<>(context);
        sUseSandbox = z;
        if (!mIsInited) {
            mAppInfo = null;
            mUpdateInfo = null;
            com.market.sdk.utils.Constants.configURL();
            mIsInited = true;
        }
        new CheckUpdateTask().execute(AppGlobal.getContext().getPackageName());
    }

    @Deprecated
    public static synchronized void update(boolean z, String str) {
        if (mIsLoading) {
            return;
        }
        mIsLoading = true;
        Client.init(AppGlobal.getContext());
        mContext = new WeakReference<>(AppGlobal.getContext());
        sUseSandbox = z;
        if (!mIsInited) {
            mAppInfo = null;
            mUpdateInfo = null;
            com.market.sdk.utils.Constants.configURL();
            mIsInited = true;
        }
        new CheckUpdateTask().execute(str);
    }

    public static void setUseInternationalHost(boolean z) {
        com.market.sdk.utils.Constants.setUseInternalProductUrl(z);
    }

    public static void setServer(ServerType serverType) {
        com.market.sdk.utils.Constants.setServerType(serverType);
    }

    public static void setHostURL(String str) {
        com.market.sdk.utils.Constants.customURL(str);
    }

    public static void setUpdateAutoPopup(boolean z) {
        mAutoPopup = z;
    }

    public static void arrange() {
        Context context = mContext.get();
        if (context == null) {
            return;
        }
        Client.init(context);
        openMarketOrArrange();
    }

    public static void setCheckUpdateOnlyWifi(boolean z) {
        mCheckUpdateOnlyWifi = z;
    }

    public static void setUpdateListener(XiaomiUpdateListener xiaomiUpdateListener) {
        mUpdateListener = xiaomiUpdateListener;
    }

    public static String getSDKVersionName() {
        return AppGlobal.getContext().getResources().getString(R.string.marketSdkVersion);
    }

    @Deprecated
    public static void setUpdateMethod(com.market.sdk.utils.Constants.UpdateMethod updateMethod) {
        mUpdateMethod = updateMethod;
    }

    @Deprecated
    public static void setUseImeiMd5AsIdentifier(boolean z) {
        sAbTestIdentifer = z ? AbTestIdentifier.IMEI_MD5 : AbTestIdentifier.ANDROID_ID;
    }

    public static void setAbTestIdentifer(AbTestIdentifier abTestIdentifier) {
        sAbTestIdentifer = abTestIdentifier;
    }

    public static AbTestIdentifier getAbTestIdentifier() {
        return sAbTestIdentifer;
    }

    public static boolean useImeiMd5AsIdentifier() {
        return sUseImeiMd5AsIdentifier;
    }

    public static Context getContext() {
        return mContext.get();
    }

    public static LocalAppInfo getAppInfo(Context context, String str) {
        PackageInfo packageInfo;
        LocalAppInfo localAppInfo = LocalAppInfo.get(str);
        PackageManager packageManager = context.getPackageManager();
        try {
            packageInfo = packageManager.getPackageInfo(localAppInfo.packageName, 64);
        } catch (PackageManager.NameNotFoundException unused) {
            Log.e(TAG, "get package info failed");
            packageInfo = null;
        }
        if (packageInfo == null || packageInfo.applicationInfo == null) {
            return null;
        }
        localAppInfo.displayName = packageManager.getApplicationLabel(packageInfo.applicationInfo).toString();
        localAppInfo.versionCode = packageInfo.versionCode;
        localAppInfo.versionName = packageInfo.versionName;
        localAppInfo.signature = Coder.encodeMD5(String.valueOf(packageInfo.signatures[0].toChars()));
        localAppInfo.sourceDir = packageInfo.applicationInfo.sourceDir;
        return localAppInfo;
    }

    public static class UpdateInfo {
        String apkHash;
        long apkSize;
        String apkUrl;
        long diffSize;
        int fitness;
        String host;
        boolean matchLanguage;
        int source;
        String updateLog;
        int versionCode;
        String versionName;
        String diffUrl = "";
        String diffHash = "";

        public String toString() {
            return "UpdateInfo:\nhost = " + this.host + "\nfitness = " + this.fitness + "\nupdateLog = " + this.updateLog + "\nversionCode = " + this.versionCode + "\nversionName = " + this.versionName + "\napkUrl = " + this.apkUrl + "\napkHash = " + this.apkHash + "\napkSize = " + this.apkSize + "\ndiffUrl = " + this.diffUrl + "\ndiffHash = " + this.diffHash + "\ndiffSize = " + this.diffSize + "\nmatchLanguage = " + this.matchLanguage;
        }
    }

    private static class CheckUpdateTask extends AsyncTask<String, Void, Integer> {
        private CheckUpdateTask() {
        }

        @Override // android.os.AsyncTask
        protected void onPreExecute() {
            Log.d(XiaomiUpdateAgent.TAG, "start to check update");
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Integer doInBackground(String... strArr) {
            Context context = (Context) XiaomiUpdateAgent.mContext.get();
            if (context == null) {
                return 4;
            }
            if (!Utils.isConnected(context)) {
                return 3;
            }
            if (Utils.isWifiConnected(context) || !XiaomiUpdateAgent.mCheckUpdateOnlyWifi) {
                LocalAppInfo unused = XiaomiUpdateAgent.mAppInfo = XiaomiUpdateAgent.getAppInfo(context, strArr[0]);
                if (XiaomiUpdateAgent.mAppInfo == null) {
                    return 5;
                }
                Connection connection = new Connection(com.market.sdk.utils.Constants.UPDATE_URL);
                Objects.requireNonNull(connection);
                Connection.Parameter parameter = new Connection.Parameter(connection);
                parameter.add(com.market.sdk.utils.Constants.JSON_FILTER_INFO, getFilterParams());
                parameter.add("packageName", XiaomiUpdateAgent.mAppInfo.packageName);
                parameter.add("versionCode", XiaomiUpdateAgent.mAppInfo.versionCode + "");
                parameter.add("signature", XiaomiUpdateAgent.mAppInfo.signature);
                parameter.add("sdk", String.valueOf(Client.SDK_VERSION));
                parameter.add(com.market.sdk.utils.Constants.JSON_VERSION, Client.SYSTEM_VERSION);
                parameter.add(com.market.sdk.utils.Constants.JSON_LANGUAGE, Client.getLanguage());
                parameter.add(com.market.sdk.utils.Constants.JSON_COUNTRY, Client.getCountry());
                parameter.add(com.market.sdk.utils.Constants.JSON_REGION, Client.getRegion());
                parameter.add(com.market.sdk.utils.Constants.JSON_ANDROID_ID, Client.ANDROID_ID);
                parameter.add(com.market.sdk.utils.Constants.JSON_DEVICE, Client.getDevice());
                parameter.add("deviceType", String.valueOf(Client.getDeviceType()));
                parameter.add(com.market.sdk.utils.Constants.JSON_CPU_ARCH, Client.getCpuArch());
                parameter.add("model", Client.getModel());
                parameter.add(com.market.sdk.utils.Constants.JSON_XIAOMI_SDK_VERSION, "11");
                parameter.add(com.market.sdk.utils.Constants.JSON_XIAOMI_SDK_VERSION_NAME, context.getResources().getString(R.string.marketSdkVersion));
                parameter.add("debug", XiaomiUpdateAgent.sUseSandbox ? "1" : "0");
                parameter.add(com.market.sdk.utils.Constants.JSON_MIUI_BIG_VERSION_NAME, Client.getMiuiBigVersionName());
                parameter.add(com.market.sdk.utils.Constants.JSON_MIUI_BIG_VERSION_CODE, Client.getMiuiBigVersionCode());
                parameter.add(com.market.sdk.utils.Constants.JSON_AB_TEST_IDENTIFIER, String.valueOf(XiaomiUpdateAgent.sAbTestIdentifer.ordinal()));
                if (XiaomiUpdateAgent.sUseImeiMd5AsIdentifier || XiaomiUpdateAgent.sAbTestIdentifer == AbTestIdentifier.IMEI_MD5) {
                    parameter.add(com.market.sdk.utils.Constants.JSON_IMEI_MD5, Client.getImeiMd5());
                }
                if (Connection.NetworkError.OK == connection.requestJSON()) {
                    UpdateInfo unused2 = XiaomiUpdateAgent.mUpdateInfo = parseUpdateInfo(connection.getResponse());
                    if (XiaomiUpdateAgent.mUpdateInfo != null) {
                        Log.i(XiaomiUpdateAgent.TAG, XiaomiUpdateAgent.mUpdateInfo.toString());
                        return Integer.valueOf(XiaomiUpdateAgent.mUpdateInfo.fitness != 0 ? 1 : 0);
                    }
                }
                return 4;
            }
            return 2;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Integer num) {
            boolean unused = XiaomiUpdateAgent.mIsLoading = false;
            Context context = (Context) XiaomiUpdateAgent.mContext.get();
            if (context == null) {
                return;
            }
            UpdateResponse updateResponse = new UpdateResponse();
            if (num.intValue() == 0) {
                updateResponse.updateLog = XiaomiUpdateAgent.mUpdateInfo.updateLog;
                updateResponse.versionCode = XiaomiUpdateAgent.mUpdateInfo.versionCode;
                updateResponse.versionName = XiaomiUpdateAgent.mUpdateInfo.versionName;
                updateResponse.apkSize = XiaomiUpdateAgent.mUpdateInfo.apkSize;
                updateResponse.apkHash = XiaomiUpdateAgent.mUpdateInfo.apkHash;
                updateResponse.diffSize = XiaomiUpdateAgent.mUpdateInfo.diffSize;
                updateResponse.path = Connection.connect(XiaomiUpdateAgent.mUpdateInfo.host, XiaomiUpdateAgent.mUpdateInfo.apkUrl);
                updateResponse.matchLanguage = XiaomiUpdateAgent.mUpdateInfo.matchLanguage;
            }
            if (XiaomiUpdateAgent.mUpdateListener != null) {
                XiaomiUpdateAgent.mUpdateListener.onUpdateReturned(num.intValue(), updateResponse);
            }
            if (XiaomiUpdateAgent.mAutoPopup && num.intValue() == 0 && (context instanceof Activity) && Client.isMiui() && isNeedShowDialog()) {
                showUpdateDialog();
            }
        }

        private String getFilterParams() {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(com.market.sdk.utils.Constants.JSON_SCREEN_SIZE, Client.DISPLAY_WIDTH + "*" + Client.DISPLAY_HEIGHT);
                jSONObject.put("resolution", Client.DISPLAY_RESOLUTION);
                jSONObject.put(com.market.sdk.utils.Constants.JSON_DENSITY, Client.DISPLAY_DENSITY);
                jSONObject.put(com.market.sdk.utils.Constants.JSON_TOUCH_SCREEN, Client.TOUCH_SCREEN);
                jSONObject.put(com.market.sdk.utils.Constants.JSON_GLES_VERSION, Client.GLES_VERSION);
                jSONObject.put("feature", Client.FEATURE);
                jSONObject.put(com.market.sdk.utils.Constants.JSON_LIBRARY, Client.LIBRARY);
                jSONObject.put(com.market.sdk.utils.Constants.JSON_GL_EXTENSION, Client.GL_EXTENSION);
                jSONObject.put("sdk", Client.SDK_VERSION);
                jSONObject.put("version", Client.SYSTEM_VERSION);
                jSONObject.put("release", Client.RELEASE);
                return jSONObject.toString();
            } catch (JSONException unused) {
                return "";
            }
        }

        private UpdateInfo parseUpdateInfo(JSONObject jSONObject) {
            if (jSONObject == null) {
                Log.e(XiaomiUpdateAgent.TAG, "update info json obj null");
                return null;
            }
            if (Utils.DEBUG) {
                Log.d(XiaomiUpdateAgent.TAG, "updateInfo : " + jSONObject.toString());
            }
            UpdateInfo updateInfo = new UpdateInfo();
            updateInfo.host = jSONObject.optString("host");
            updateInfo.fitness = jSONObject.optInt(com.market.sdk.utils.Constants.FITNESS);
            updateInfo.source = jSONObject.optInt("source");
            updateInfo.updateLog = jSONObject.optString(com.market.sdk.utils.Constants.UPDATE_LOG);
            updateInfo.versionCode = jSONObject.optInt("versionCode");
            updateInfo.versionName = jSONObject.optString(com.market.sdk.utils.Constants.VERSION_NAME);
            updateInfo.apkUrl = jSONObject.optString(com.market.sdk.utils.Constants.APK_URL);
            updateInfo.apkHash = jSONObject.optString("apkHash");
            updateInfo.apkSize = jSONObject.optLong(com.market.sdk.utils.Constants.APK_SIZE);
            updateInfo.matchLanguage = jSONObject.optBoolean(com.market.sdk.utils.Constants.MATCH_LANGUAGE);
            return updateInfo;
        }

        private static boolean isNeedShowDialog() {
            if (System.currentTimeMillis() - Long.valueOf(PrefUtils.getLong(Constants.SDK_BEGIN_TIME, new PrefUtils.PrefFile[0])).longValue() >= 259200000) {
                return true;
            }
            Long lValueOf = Long.valueOf(PrefUtils.getLong(Constants.SDK_WINDON_LAST_SHOW_TIME, new PrefUtils.PrefFile[0]));
            if (System.currentTimeMillis() - lValueOf.longValue() < 21600000) {
                return false;
            }
            int i = PrefUtils.getInt(Constants.SDK_WINDON_SHOW_TIMES, new PrefUtils.PrefFile[0]);
            if (i < 2) {
                PrefUtils.setInt(Constants.SDK_WINDON_SHOW_TIMES, i + 1, new PrefUtils.PrefFile[0]);
                PrefUtils.setLong(Constants.SDK_WINDON_LAST_SHOW_TIME, System.currentTimeMillis(), new PrefUtils.PrefFile[0]);
                return true;
            }
            if (getDayOfMonth(Long.valueOf(System.currentTimeMillis())) == getDayOfMonth(lValueOf)) {
                return false;
            }
            PrefUtils.setInt(Constants.SDK_WINDON_SHOW_TIMES, 1, new PrefUtils.PrefFile[0]);
            PrefUtils.setLong(Constants.SDK_WINDON_LAST_SHOW_TIME, System.currentTimeMillis(), new PrefUtils.PrefFile[0]);
            return true;
        }

        private static int getDayOfMonth(Long l) {
            Date date = new Date(l.longValue());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(5);
        }

        private static void showUpdateDialog() {
            try {
                Context context = (Context) XiaomiUpdateAgent.mContext.get();
                if (context == null) {
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialog_Theme_DayNight);
                builder.setTitle(context.getString(R.string.xiaomi_market_sdk_update_dialog_title));
                builder.setMessage(XiaomiUpdateAgent.mUpdateInfo.updateLog);
                builder.setNegativeButton(R.string.xiaomi_market_sdk_update_dialog_cancel, (DialogInterface.OnClickListener) null).setPositiveButton(R.string.xiaomi_market_sdk_update_dialog_ok, new DialogInterface.OnClickListener() { // from class: com.market.sdk.XiaomiUpdateAgent.CheckUpdateTask.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        XiaomiUpdateAgent.openMarketOrArrange();
                    }
                });
                if (!(context instanceof Activity) || ((Activity) context).isFinishing()) {
                    return;
                }
                builder.show();
            } catch (Exception e) {
                Log.e(XiaomiUpdateAgent.TAG, "show update dialog error: " + e.getMessage());
            }
        }
    }

    public static void openMarketOrArrange() {
        UpdateInfo updateInfo;
        Context context = mContext.get();
        if (context == null || (updateInfo = mUpdateInfo) == null || mAppInfo == null) {
            return;
        }
        if (updateInfo.source != 1 && Utils.isMiuiMarketExisted(context)) {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?ref=update_sdk&back=true&startDownload=true&id=" + mAppInfo.packageName));
            intent.setPackage(Utils.getMarketPackageName());
            List<ResolveInfo> listQueryIntentActivities = context.getPackageManager().queryIntentActivities(intent, 0);
            if (listQueryIntentActivities == null || listQueryIntentActivities.size() != 1) {
                return;
            }
            ResolveInfo resolveInfo = listQueryIntentActivities.get(0);
            if (resolveInfo.activityInfo != null && resolveInfo.activityInfo.exported && resolveInfo.activityInfo.enabled) {
                intent.addFlags(DataSpace.RANGE_LIMITED);
                context.startActivity(intent);
                return;
            }
            return;
        }
        Log.e(TAG, "MiuiMarket doesn't exist");
    }

    public static void getSupport64Apps(IGetAppsCallback iGetAppsCallback) {
        GetApps64Manager.getInstance().getSupport64Apps(iGetAppsCallback);
    }
}
