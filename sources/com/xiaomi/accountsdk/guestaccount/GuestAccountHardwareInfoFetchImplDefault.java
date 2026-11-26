package com.xiaomi.accountsdk.guestaccount;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.account.privacy_data.master.PrivacyDataMaster;
import com.xiaomi.account.privacy_data.master.PrivacyDataType;
import com.xiaomi.accountsdk.hasheddeviceidlib.AccountDeviceIdManager;
import com.xiaomi.accountsdk.hasheddeviceidlib.HardwareInfo;
import com.xiaomi.accountsdk.utils.FidNonceBase;
import java.util.UUID;

/* JADX INFO: loaded from: classes4.dex */
public class GuestAccountHardwareInfoFetchImplDefault implements GuestAccountHardwareInfoFetcher {
    private static final String TAG = "HardwareInfoFetcherDefa";
    private final Context context;

    @Override // com.xiaomi.accountsdk.guestaccount.GuestAccountHardwareInfoFetcher
    public FidNonceBase getFidNonceBase() {
        return null;
    }

    public GuestAccountHardwareInfoFetchImplDefault(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context == null");
        }
        this.context = context;
    }

    @Override // com.xiaomi.accountsdk.guestaccount.GuestAccountHardwareInfoFetcher
    public String getDeviceId() {
        return AccountDeviceIdManager.getInstance().getDeviceId();
    }

    @Override // com.xiaomi.accountsdk.guestaccount.GuestAccountHardwareInfoFetcher
    @Deprecated
    public String getMacAddress() {
        return HardwareInfo.FAKE_MAC_ADDRESS;
    }

    @Override // com.xiaomi.accountsdk.guestaccount.GuestAccountHardwareInfoFetcher
    public String getBluetoothId() {
        return HardwareInfo.getBluetoothMacAddress(this.context);
    }

    @Override // com.xiaomi.accountsdk.guestaccount.GuestAccountHardwareInfoFetcher
    public String getAndroidId() {
        Context context = this.context;
        if (context == null) {
            throw new IllegalArgumentException("context == null");
        }
        String str = PrivacyDataMaster.get(context, PrivacyDataType.ANDROID_ID, new String[0]);
        if (!TextUtils.isEmpty(str)) {
            return str;
        }
        if (TextUtils.isEmpty(SharedPreferencesUtils.getUUID(this.context))) {
            String str2 = "f_" + UUID.randomUUID().toString().replace("-", "").trim().substring(8, 24);
            SharedPreferencesUtils.saveUUID(this.context, str2);
            return str2;
        }
        return SharedPreferencesUtils.getUUID(this.context);
    }
}
