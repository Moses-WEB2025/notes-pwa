package com.xiaomi.passport.utils;

import android.accounts.Account;
import android.app.Application;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.xiaomi.account.passportsdk.account_sso.BuildConfig;
import com.xiaomi.accountsdk.account.XMPassport;
import com.xiaomi.accountsdk.account.XMPassportSettings;
import com.xiaomi.accountsdk.hasheddeviceidlib.AccountDeviceIdManager;
import com.xiaomi.accountsdk.service.PassportCommonServiceClient;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.accountsdk.utils.UserSpaceIdUtil;
import com.xiaomi.passport.Constants;
import com.xiaomi.passport.accountmanager.XiaomiAccountManager;
import com.xiaomi.passport.encrypt.SHA256;
import com.xiaomi.passport.servicetoken.ServiceTokenResult;
import com.xiaomi.passport.uicontroller.SimpleFutureTask;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: classes4.dex */
public class AccountLoginUploader {
    private static final int RETRY_INTERVAL_TIME = 600000;
    private static final String SP_NAME_UPLOAD_RETRY = "sp_name_login_success_upload_retry";
    private static final String SP_NAME_UPLOAD_SID_ADN_DEV_ID = "sp_name_login_success_sid_adn_dev_id";
    private static final String SP_NAME_UPLOAD_STATUS = "sp_name_login_success_upload_status";
    private static final String TAG = "AccountLoginUploader";
    private static final Handler sMainHandler = new Handler(Looper.getMainLooper());
    private static final Executor syncExecutor = Executors.newFixedThreadPool(3);
    private static final Set<String> sUploadingHashSet = Collections.synchronizedSet(new HashSet());
    private static final List<SimpleFutureTask<Boolean>> sUploadingTaskList = new CopyOnWriteArrayList();
    private static final Map<String, Runnable> sDelayRetryRunnableMap = new ConcurrentHashMap();

    public static void uploadAccountLogin(String str, String str2) {
        uploadAccountLogin(str, str2, false, false);
    }

    public static void forceUploadAccountLogin(String str, String str2, boolean z) {
        uploadAccountLogin(str, str2, z, true);
    }

    private static void uploadAccountLogin(final String str, final String str2, final boolean z, final boolean z2) {
        AccountLogger.log(TAG, "uploadAccountLogin>>>userId=" + str + "  sid=" + str2 + "  notify=" + z + "  force=" + z2);
        SimpleFutureTask<Boolean> simpleFutureTask = new SimpleFutureTask<>(new Callable<Boolean>() { // from class: com.xiaomi.passport.utils.AccountLoginUploader.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public Boolean call() {
                AccountLoginUploader.removeRetryInfo(str, str2);
                if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
                    return false;
                }
                String deviceId = AccountDeviceIdManager.getInstance().getDeviceId();
                int i = AccountDeviceIdManager.getInstance().getDeviceIdSource().value;
                String nullableUserSpaceIdCookie = UserSpaceIdUtil.getNullableUserSpaceIdCookie();
                if (nullableUserSpaceIdCookie == null) {
                    nullableUserSpaceIdCookie = "";
                }
                String str3 = nullableUserSpaceIdCookie;
                String accountLoginUploadParamHash = AccountLoginUploader.getAccountLoginUploadParamHash(str, str2, deviceId, str3);
                if (z2 || !AccountLoginUploader.isUploaded(accountLoginUploadParamHash)) {
                    if (!AccountLoginUploader.sUploadingHashSet.contains(accountLoginUploadParamHash)) {
                        AccountLoginUploader.sUploadingHashSet.add(accountLoginUploadParamHash);
                        AccountLoginUploader.asyncUploadAccountLogin(accountLoginUploadParamHash, str, str2, deviceId, str3, z, i);
                        return true;
                    }
                    AccountLogger.log(AccountLoginUploader.TAG, "uploadAccountLogin>>>is uploading, userId=" + str + "  sid=" + str2 + "  paramHash=" + accountLoginUploadParamHash);
                    return false;
                }
                AccountLogger.log(AccountLoginUploader.TAG, "uploadAccountLogin>>>has uploaded, userId=" + str + "  sid=" + str2 + "  paramHash=" + accountLoginUploadParamHash);
                return false;
            }
        }, null);
        syncExecutor.execute(simpleFutureTask);
        sUploadingTaskList.add(simpleFutureTask);
    }

    public static void retryLocalUpload() {
        Map<String, ?> all = getSp(SP_NAME_UPLOAD_RETRY).getAll();
        Set<String> setKeySet = all.keySet();
        AccountLogger.log(TAG, "retryLocalUpload>>>keySet=" + setKeySet);
        for (String str : setKeySet) {
            String[] retryKey = parseRetryKey(str);
            if (retryKey.length == 2) {
                Object obj = all.get(str);
                if (obj instanceof Long) {
                    retryAccountLoginUpload(retryKey[0], retryKey[1], 600000 - (System.currentTimeMillis() - ((Long) obj).longValue()));
                }
            }
        }
    }

    public static void clearAccountLoginUploadData() {
        AccountLogger.log(TAG, "clearAccountLoginUploadData>>>");
        Iterator<SimpleFutureTask<Boolean>> it = sUploadingTaskList.iterator();
        while (it.hasNext()) {
            it.next().cancel(true);
        }
        Iterator<Runnable> it2 = sDelayRetryRunnableMap.values().iterator();
        while (it2.hasNext()) {
            sMainHandler.removeCallbacks(it2.next());
        }
        sUploadingHashSet.clear();
        sUploadingTaskList.clear();
        sDelayRetryRunnableMap.clear();
        clearSpData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void asyncUploadAccountLogin(String str, String str2, String str3, String str4, String str5, boolean z, int i) {
        Application applicationContext = XMPassportSettings.getApplicationContext();
        XiaomiAccountManager xiaomiAccountManager = XiaomiAccountManager.get(applicationContext);
        Account xiaomiAccount = xiaomiAccountManager.getXiaomiAccount();
        if (xiaomiAccount == null || !xiaomiAccount.name.equals(str2)) {
            AccountLogger.log(TAG, "asyncUploadAccountLogin>>>bad userId, userId=" + str2 + "  sid=" + str3);
            return;
        }
        ServiceTokenResult serviceTokenResultPeekServiceToken = xiaomiAccountManager.peekServiceToken(xiaomiAccount, Constants.DEVICE_INFO_SID, null);
        if (serviceTokenResultPeekServiceToken != null) {
            xiaomiAccountManager.invalidateServiceToken(serviceTokenResultPeekServiceToken).get();
        }
        boolean zUploadLoginSucceed = XMPassport.uploadLoginSucceed(str2, xiaomiAccountManager.getServiceToken(xiaomiAccount, Constants.DEVICE_INFO_SID, null).get().serviceToken, str4, str5, str3, z, getUploadedDeviceIdFromLocal(str3), i, PassportCommonServiceClient.isDeviceInfoSupported(applicationContext));
        AccountLogger.log(TAG, "asyncUploadAccountLogin>>>userId=" + str2 + "  sid=" + str3 + "  success=" + zUploadLoginSucceed);
        saveUploadStatusToLocal(str, zUploadLoginSucceed);
        saveUploadDeviceIdToLocal(str3, str4);
        sUploadingHashSet.remove(str);
        if (zUploadLoginSucceed) {
            return;
        }
        retryAccountLoginUpload(str2, str3, 600000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void removeRetryInfo(String str, String str2) {
        String strCreateRetryKey = createRetryKey(str, str2);
        AccountLogger.log(TAG, "removeRetryInfo>>>key=" + strCreateRetryKey);
        removeLocalRetryInfo(strCreateRetryKey);
        sMainHandler.removeCallbacks(sDelayRetryRunnableMap.remove(strCreateRetryKey));
    }

    private static void retryAccountLoginUpload(final String str, final String str2, long j) {
        String strCreateRetryKey = createRetryKey(str, str2);
        AccountLogger.log(TAG, "retryAccountLoginUpload>>>key=" + strCreateRetryKey);
        saveRetryInfoToLocal(strCreateRetryKey);
        Runnable runnable = new Runnable() { // from class: com.xiaomi.passport.utils.AccountLoginUploader.2
            @Override // java.lang.Runnable
            public void run() {
                AccountLoginUploader.uploadAccountLogin(str, str2);
            }
        };
        sMainHandler.postDelayed(runnable, j);
        sDelayRetryRunnableMap.put(strCreateRetryKey, runnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getAccountLoginUploadParamHash(String str, String str2, String str3, String str4) {
        String asHexString = SHA256.getAsHexString(String.format("%s%s%s%s%s", str, str2, str3, str4, BuildConfig.VERSION_NAME));
        AccountLogger.log(TAG, "getAccountLoginUploadParamHash>>>userId:" + str + ",sid:" + str2 + ",deviceId:" + str3 + ",spaceId:" + str4 + ",sdkVersion:5.3.0.release.94,paramHash:" + asHexString);
        return asHexString;
    }

    private static void saveUploadStatusToLocal(String str, boolean z) {
        getSp(SP_NAME_UPLOAD_STATUS).edit().putBoolean(str, z).apply();
    }

    private static void saveUploadDeviceIdToLocal(String str, String str2) {
        getSp(SP_NAME_UPLOAD_SID_ADN_DEV_ID).edit().putString(str, str2).apply();
    }

    private static String getUploadedDeviceIdFromLocal(String str) {
        return getSp(SP_NAME_UPLOAD_SID_ADN_DEV_ID).getString(str, "");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isUploaded(String str) {
        return getSp(SP_NAME_UPLOAD_STATUS).getBoolean(str, false);
    }

    private static void saveRetryInfoToLocal(String str) {
        getSp(SP_NAME_UPLOAD_RETRY).edit().putLong(str, System.currentTimeMillis()).apply();
    }

    private static void removeLocalRetryInfo(String str) {
        getSp(SP_NAME_UPLOAD_RETRY).edit().remove(str).apply();
    }

    private static void clearSpData() {
        getSp(SP_NAME_UPLOAD_STATUS).edit().clear().apply();
        getSp(SP_NAME_UPLOAD_RETRY).edit().clear().apply();
        getSp(SP_NAME_UPLOAD_SID_ADN_DEV_ID).edit().clear().apply();
    }

    private static SharedPreferences getSp(String str) {
        return XMPassportSettings.getApplicationContext().getSharedPreferences(str, 0);
    }

    private static String createRetryKey(String str, String str2) {
        return str + "|" + str2;
    }

    private static String[] parseRetryKey(String str) {
        return str.split("\\|");
    }
}
