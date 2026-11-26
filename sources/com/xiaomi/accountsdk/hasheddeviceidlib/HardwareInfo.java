package com.xiaomi.accountsdk.hasheddeviceidlib;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.account.privacy_data.master.PrivacyDataMaster;
import com.xiaomi.account.privacy_data.master.PrivacyDataType;

/* JADX INFO: loaded from: classes4.dex */
public class HardwareInfo {
    public static final String DEFAULT_MAC_ADDRESS = "0";
    public static final String FAKE_MAC_ADDRESS = "02:00:00:00:00:00";
    private static final String TAG = "UserEnvironment";

    public static String getBluetoothMacAddress(Context context) {
        String strForceGet = PrivacyDataMaster.forceGet(context, PrivacyDataType.BLUETOOTH_ADDRESS, new String[0]);
        return !TextUtils.isEmpty(strForceGet) ? strForceGet : "0";
    }
}
