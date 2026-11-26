package com.xiaomi.accountsdk.guestaccount;

import com.xiaomi.accountsdk.utils.FidNonceBase;

/* JADX INFO: loaded from: classes4.dex */
public interface GuestAccountHardwareInfoFetcher {
    String getAndroidId();

    String getBluetoothId();

    String getDeviceId();

    FidNonceBase getFidNonceBase();

    @Deprecated
    String getMacAddress();
}
