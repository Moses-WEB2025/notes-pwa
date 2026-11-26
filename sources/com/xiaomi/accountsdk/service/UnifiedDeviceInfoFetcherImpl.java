package com.xiaomi.accountsdk.service;

import android.content.Context;
import com.xiaomi.accountsdk.account.data.Constants;
import com.xiaomi.accountsdk.hasheddeviceidlib.IUnifiedDeviceIdFetcher;
import com.xiaomi.accountsdk.utils.AccountLogger;

/* JADX INFO: loaded from: classes4.dex */
public class UnifiedDeviceInfoFetcherImpl implements IUnifiedDeviceIdFetcher {
    private static final String TAG = "UnifiedDeviceInfoFetcherImpl";

    @Override // com.xiaomi.accountsdk.hasheddeviceidlib.IUnifiedDeviceIdFetcher
    public String getHashedDeviceId(Context context) {
        DeviceInfoResult deviceInfoRpc;
        for (int i = 0; i < 3 && (deviceInfoRpc = PassportCommonServiceClient.getDeviceInfoRpc(context, Constants.PASSPORT_SID, 1, 5000)) != null; i++) {
            AccountLogger.log(TAG, "getHashedDeviceId>>>errorCode=" + deviceInfoRpc.errorCode);
            if (deviceInfoRpc.errorCode == DeviceInfoResult.ErrorCode.ERROR_APP_PERMISSION_FORBIDDEN || deviceInfoRpc.errorCode == DeviceInfoResult.ErrorCode.ERROR_TIME_OUT || deviceInfoRpc.errorCode == DeviceInfoResult.ErrorCode.ERROR_EXECUTION_EXCEPTION) {
                AccountLogger.log(TAG, "getHashedDeviceId>>>has no permission, retry " + i);
                try {
                    Thread.sleep(1500L);
                } catch (InterruptedException e) {
                    AccountLogger.log(TAG, "getHashedDeviceId>>>has no permission, err  ", e);
                    return null;
                }
            } else {
                if (deviceInfoRpc.deviceInfo != null) {
                    return deviceInfoRpc.deviceInfo.getString(DeviceInfoResult.BUNDLE_KEY_HASHED_DEVICE_ID);
                }
                return null;
            }
        }
        return null;
    }
}
