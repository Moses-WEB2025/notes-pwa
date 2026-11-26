package com.xiaomi.accountsdk.hasheddeviceidlib;

import android.accounts.Account;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.xiaomi.account.privacy_data.master.PrivacyDataMaster;
import com.xiaomi.account.privacy_data.master.PrivacyDataType;
import com.xiaomi.accountsdk.account.AccountIntent;
import com.xiaomi.accountsdk.account.XMPassportSettings;
import com.xiaomi.accountsdk.account.data.Constants;
import com.xiaomi.accountsdk.service.DeviceInfoResult;
import com.xiaomi.accountsdk.service.PassportCommonServiceClient;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.passport.accountmanager.XiaomiAccountManager;
import com.xiaomi.passport.utils.AccountLoginUploader;
import com.xiaomi.passport.utils.LogoutInvalidateServiceTokenHelper;
import com.xiaomi.verificationsdk.internal.CommonUtils;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes4.dex */
public class AccountDeviceIdManager {
    private static final String ANDROID_ID_PREFIX = "an_";
    private static final String OAID_PREFIX = "oa_";
    private static final String OLD_SP_KEY_DEVICE_ID = "hashedDeviceId";
    private static final String OLD_SP_NAME_ACCOUNT_DEVICE = "deviceId";
    private static final String PSEUDO_DEVICE_ID_PREFIX = "android_";
    private static final String SP_KEY_DEVICE_ID = "deviceId";
    private static final String SP_KEY_DEVICE_ID_GENERATE_TIME = "deviceIdGenerateTime";
    private static final String SP_KEY_GET_DEVICE_ID_SOURCE = "deviceIdSource";
    private static final String SP_NAME_ACCOUNT_DEVICE = "sp_name_passport_sdk_account_dev";
    private static final String TAG = "AccountDeviceIdManager";
    private static IAccountDeviceIdGetter mCustomAccountDeviceIdGetter;
    private static final ExecutorService sExecutorService = Executors.newCachedThreadPool();
    private static volatile AccountDeviceIdManager sInstance = null;
    private static boolean sKeepOldDeviceId = false;
    private final Context mAppContext = XMPassportSettings.getApplicationContext();
    private volatile String mDeviceId;
    private volatile long mDeviceIdGenerateTime;
    private volatile DeviceIdSource mDeviceIdSource;

    private AccountDeviceIdManager() {
        tryLoadOldDeviceId();
        this.mDeviceId = getDeviceIdFromLocal();
        int deviceSourceFromLocal = getDeviceSourceFromLocal();
        this.mDeviceIdSource = DeviceIdSource.fromValue(deviceSourceFromLocal);
        this.mDeviceIdGenerateTime = getDeviceIdGenerateTimeFromLocal();
        boolean zCheckDeviceIdWhetherNeedReGet = checkDeviceIdWhetherNeedReGet(this.mDeviceId, this.mDeviceIdSource, this.mDeviceIdGenerateTime);
        AccountLogger.log(TAG, "init>>>mDeviceId=" + this.mDeviceId + "  mDeviceIdSource=" + this.mDeviceIdSource + "  mDeviceIdGenerateTime=" + this.mDeviceIdGenerateTime + "  isNeedReGet=" + zCheckDeviceIdWhetherNeedReGet + "  deviceIdSourceValue=" + deviceSourceFromLocal);
        if (zCheckDeviceIdWhetherNeedReGet) {
            this.mDeviceId = null;
            this.mDeviceIdSource = null;
            this.mDeviceIdGenerateTime = 0L;
            clearSp();
        }
    }

    private void tryLoadOldDeviceId() {
        if (sKeepOldDeviceId) {
            SharedPreferences sharedPreferences = this.mAppContext.getSharedPreferences("deviceId", 0);
            String string = sharedPreferences.getString(OLD_SP_KEY_DEVICE_ID, null);
            AccountLogger.log(TAG, "loadOldDeviceId>>>oldDeviceId=" + string);
            if (TextUtils.isEmpty(string)) {
                return;
            }
            saveDeviceIdAndSourceToLocal(string, DeviceIdSource.OLD_SDK_GENERATE_DEVICE_ID);
            sharedPreferences.edit().remove(OLD_SP_KEY_DEVICE_ID).apply();
        }
    }

    private boolean checkDeviceIdWhetherNeedReGet(String str, DeviceIdSource deviceIdSource, long j) {
        if (XiaomiAccountManager.isSystemXiaomiAccountApp(this.mAppContext) || TextUtils.isEmpty(str) || deviceIdSource == null) {
            return true;
        }
        if (deviceIdSource != DeviceIdSource.SYSTEM_ACCOUNT_FROM_OLD_VERSION || Math.abs(System.currentTimeMillis() - j) <= TimeUnit.DAYS.toMillis(5L)) {
            return !sKeepOldDeviceId && deviceIdSource == DeviceIdSource.OLD_SDK_GENERATE_DEVICE_ID;
        }
        return true;
    }

    public static AccountDeviceIdManager getInstance() {
        if (sInstance == null) {
            synchronized (AccountDeviceIdManager.class) {
                if (sInstance == null) {
                    sInstance = new AccountDeviceIdManager();
                }
            }
        }
        return sInstance;
    }

    public static void setKeepOldDeviceId(boolean z) {
        AccountLogger.log(TAG, "setKeepOldDeviceId>>>keepOldDeviceId=" + z);
        sKeepOldDeviceId = z;
    }

    public static void setCustomAccountDeviceIdGetter(IAccountDeviceIdGetter iAccountDeviceIdGetter) {
        AccountLogger.log(TAG, "setCustomAccountDeviceIdGetter>>>" + iAccountDeviceIdGetter.getClass().getName());
        mCustomAccountDeviceIdGetter = iAccountDeviceIdGetter;
    }

    public DeviceIdSource getDeviceIdSource() {
        getDeviceId();
        return this.mDeviceIdSource;
    }

    public FutureTask<String> getDeviceIdAsync() {
        FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() { // from class: com.xiaomi.accountsdk.hasheddeviceidlib.AccountDeviceIdManager.1
            @Override // java.util.concurrent.Callable
            public String call() throws Exception {
                return AccountDeviceIdManager.this.getDeviceId();
            }
        });
        if (CommonUtils.isMainThread()) {
            sExecutorService.submit(futureTask);
            return futureTask;
        }
        futureTask.run();
        return futureTask;
    }

    public String getDeviceId() {
        if (CommonUtils.isMainThread()) {
            AccountLogger.log(TAG, "getDeviceId>>>get device id in main thread", new Exception());
        }
        if (!TextUtils.isEmpty(this.mDeviceId)) {
            return this.mDeviceId;
        }
        return syncGetDeviceId();
    }

    private String syncGetDeviceId() {
        synchronized (AccountDeviceIdManager.class) {
            if (!TextUtils.isEmpty(this.mDeviceId)) {
                return this.mDeviceId;
            }
            this.mDeviceId = getHashedDeviceId();
            AccountLogger.log(TAG, "syncGetDeviceId>>>generate device id: " + this.mDeviceId + "  mDeviceIdSource=" + this.mDeviceIdSource);
            tryUploadAccountLogin();
            return this.mDeviceId;
        }
    }

    private void tryUploadAccountLogin() {
        Account xiaomiAccount;
        if (TextUtils.isEmpty(getDeviceIdFromLocal())) {
            return;
        }
        Set<String> serviceTokenSid = LogoutInvalidateServiceTokenHelper.getServiceTokenSid(this.mAppContext);
        AccountLogger.log(TAG, "tryUploadAccountLogin>>>uploadAccountLoginSids=" + serviceTokenSid);
        if (serviceTokenSid == null || (xiaomiAccount = XiaomiAccountManager.get(this.mAppContext).getXiaomiAccount()) == null) {
            return;
        }
        Iterator<String> it = serviceTokenSid.iterator();
        while (it.hasNext()) {
            AccountLoginUploader.uploadAccountLogin(xiaomiAccount.name, it.next());
        }
    }

    private synchronized String getHashedDeviceId() {
        String deviceIdByCustomGetter = getDeviceIdByCustomGetter();
        if (!TextUtils.isEmpty(deviceIdByCustomGetter)) {
            AccountLogger.log(TAG, "getHashedDeviceId>>>use device id: custom");
            return truncate(deviceIdByCustomGetter);
        }
        boolean zIsDeviceInfoSupported = PassportCommonServiceClient.isDeviceInfoSupported(this.mAppContext);
        AccountLogger.log(TAG, "getHashedDeviceId>>>isSupportSystemAccountDevId=" + zIsDeviceInfoSupported);
        String deviceIdFromXiaomiSystemAccount = getDeviceIdFromXiaomiSystemAccount(zIsDeviceInfoSupported);
        if (!TextUtils.isEmpty(deviceIdFromXiaomiSystemAccount)) {
            AccountLogger.log(TAG, "getHashedDeviceId>>>use device id: system account");
            return (this.mDeviceIdSource == DeviceIdSource.SYSTEM_ACCOUNT_FROM_IOT_ID || this.mDeviceIdSource == DeviceIdSource.SYSTEM_ACCOUNT_FROM_LOCAL_OAID || this.mDeviceIdSource == DeviceIdSource.SYSTEM_ACCOUNT_FROM_FIND_DEVICE || this.mDeviceIdSource == DeviceIdSource.SYSTEM_ACCOUNT_FROM_OLD_VERSION) ? truncateAndSaveToLocal(deviceIdFromXiaomiSystemAccount, this.mDeviceIdSource) : truncate(deviceIdFromXiaomiSystemAccount);
        }
        String deviceIdByPrivacyDeviceId = getDeviceIdByPrivacyDeviceId();
        if (!TextUtils.isEmpty(deviceIdByPrivacyDeviceId)) {
            AccountLogger.log(TAG, "getHashedDeviceId>>>use device id: privacy device id");
            this.mDeviceIdSource = DeviceIdSource.SDK_LOCAL_FROM_TELEPHONY_SERVICE;
            return zIsDeviceInfoSupported ? truncate(deviceIdByPrivacyDeviceId) : truncateAndSaveToLocal(deviceIdByPrivacyDeviceId, this.mDeviceIdSource);
        }
        String deviceIdByOAID = getDeviceIdByOAID();
        if (!TextUtils.isEmpty(deviceIdByOAID)) {
            AccountLogger.log(TAG, "getHashedDeviceId>>>use device id: privacy OAID id");
            this.mDeviceIdSource = DeviceIdSource.SDK_LOCAL_FROM_OAID;
            return zIsDeviceInfoSupported ? truncate(deviceIdByOAID) : truncateAndSaveToLocal(deviceIdByOAID, this.mDeviceIdSource);
        }
        String deviceIdByAndroidId = getDeviceIdByAndroidId();
        if (!TextUtils.isEmpty(deviceIdByAndroidId)) {
            AccountLogger.log(TAG, "getHashedDeviceId>>>use device id: privacy android id");
            this.mDeviceIdSource = DeviceIdSource.SDK_LOCAL_FROM_ANDROID_ID;
            return zIsDeviceInfoSupported ? truncate(deviceIdByAndroidId) : truncateAndSaveToLocal(deviceIdByAndroidId, this.mDeviceIdSource);
        }
        String deviceIdByRandom = getDeviceIdByRandom();
        this.mDeviceIdSource = DeviceIdSource.SDK_LOCAL_FROM_RANDOM;
        AccountLogger.log(TAG, "getHashedDeviceId>>>use device id: random");
        return zIsDeviceInfoSupported ? truncate(deviceIdByRandom) : truncateAndSaveToLocal(deviceIdByRandom, this.mDeviceIdSource);
    }

    private String getDeviceIdByCustomGetter() {
        IAccountDeviceIdGetter iAccountDeviceIdGetter = mCustomAccountDeviceIdGetter;
        if (iAccountDeviceIdGetter == null) {
            return null;
        }
        IAccountDeviceIdGetter.DeviceId deviceId = iAccountDeviceIdGetter.getDeviceId();
        String strHashDeviceInfo = XMPassportSettings.getDeviceType() == XMPassportSettings.DeviceType.WEAR ? deviceId.id : DeviceIdHasher.hashDeviceInfo(deviceId.id);
        if (!TextUtils.isEmpty(strHashDeviceInfo)) {
            this.mDeviceIdSource = deviceId.source;
        }
        return strHashDeviceInfo;
    }

    private String getDeviceIdFromXiaomiSystemAccount(boolean z) {
        DeviceInfoResult deviceInfoRpc;
        String string = null;
        if (CommonUtils.isMainThread()) {
            AccountLogger.log(TAG, "getDeviceIdFromXiaomiSystemAccount>>>is in main thread");
            return null;
        }
        if (!z) {
            return null;
        }
        int i = 0;
        while (true) {
            deviceInfoRpc = PassportCommonServiceClient.getDeviceInfoRpc(this.mAppContext, Constants.PASSPORT_SID, 1, 5000, true);
            AccountLogger.log(TAG, "getDeviceIdFromXiaomiSystemAccount>>>errorCode=" + (deviceInfoRpc != null ? deviceInfoRpc.errorCode : "null"));
            i++;
            if (i >= 3 || deviceInfoRpc == null) {
                break;
            }
            if (deviceInfoRpc.errorCode != DeviceInfoResult.ErrorCode.ERROR_TIME_OUT) {
                if (deviceInfoRpc.errorCode != DeviceInfoResult.ErrorCode.ERROR_APP_PERMISSION_FORBIDDEN && deviceInfoRpc.errorCode != DeviceInfoResult.ErrorCode.ERROR_EXECUTION_EXCEPTION) {
                    break;
                }
                AccountLogger.log(TAG, "getDeviceIdFromXiaomiSystemAccount>>>has no permission, retry " + i);
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    AccountLogger.log(TAG, "getDeviceIdFromXiaomiSystemAccount>>>has no permission, err  ", e);
                }
            }
        }
        if (deviceInfoRpc != null && deviceInfoRpc.deviceInfo != null && deviceInfoRpc.errorCode == DeviceInfoResult.ErrorCode.ERROR_NONE) {
            string = deviceInfoRpc.deviceInfo.getString(DeviceInfoResult.BUNDLE_KEY_HASHED_DEVICE_ID);
            if (!TextUtils.isEmpty(string)) {
                this.mDeviceIdSource = DeviceIdSource.fromValue(deviceInfoRpc.deviceInfo.getInt(AccountIntent.SYSTEM_ACCOUNT_GET_DEVICE_ID_STATUS, DeviceIdSource.SYSTEM_ACCOUNT_FROM_OLD_VERSION.value));
            }
        }
        return string;
    }

    private String getDeviceIdByPrivacyDeviceId() {
        return PrivacyDataMaster.get(this.mAppContext, PrivacyDataType.DEVICE_ID, new String[0]);
    }

    private String getDeviceIdByOAID() {
        String str = PrivacyDataMaster.get(this.mAppContext, PrivacyDataType.OAID, new String[0]);
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        AccountLogger.log(TAG, "getDeviceIdFromOAID>>>use oaid");
        return OAID_PREFIX + DeviceIDCloudCoder.getDataMd5Digest(str.getBytes());
    }

    private String getDeviceIdByAndroidId() {
        String str = PrivacyDataMaster.get(this.mAppContext, PrivacyDataType.ANDROID_ID, new String[0]);
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        AccountLogger.log(TAG, "getHashedDeviceId>>>use androidID");
        return ANDROID_ID_PREFIX + DeviceIDCloudCoder.getDataMd5Digest(str.getBytes());
    }

    private String getDeviceIdByRandom() {
        return String.format("%s%s", PSEUDO_DEVICE_ID_PREFIX, UUID.randomUUID().toString());
    }

    private String truncateAndSaveToLocal(String str, DeviceIdSource deviceIdSource) {
        String strTruncate = truncate(str);
        saveDeviceIdAndSourceToLocal(strTruncate, deviceIdSource);
        return strTruncate;
    }

    private String truncate(String str) {
        return str.length() > 128 ? str.substring(0, 128) : str;
    }

    private void saveDeviceIdAndSourceToLocal(String str, DeviceIdSource deviceIdSource) {
        this.mDeviceIdGenerateTime = System.currentTimeMillis();
        SharedPreferences.Editor spEditor = getSpEditor();
        spEditor.putString("deviceId", str);
        spEditor.putInt(SP_KEY_GET_DEVICE_ID_SOURCE, deviceIdSource.value);
        spEditor.putLong(SP_KEY_DEVICE_ID_GENERATE_TIME, this.mDeviceIdGenerateTime);
        spEditor.apply();
    }

    private String getDeviceIdFromLocal() {
        return getSp().getString("deviceId", "");
    }

    private int getDeviceSourceFromLocal() {
        return getSp().getInt(SP_KEY_GET_DEVICE_ID_SOURCE, 0);
    }

    private long getDeviceIdGenerateTimeFromLocal() {
        return getSp().getLong(SP_KEY_DEVICE_ID_GENERATE_TIME, 0L);
    }

    private SharedPreferences getSp() {
        return this.mAppContext.getSharedPreferences(SP_NAME_ACCOUNT_DEVICE, 0);
    }

    private SharedPreferences.Editor getSpEditor() {
        return getSp().edit();
    }

    private void clearSp() {
        getSpEditor().clear().apply();
    }
}
