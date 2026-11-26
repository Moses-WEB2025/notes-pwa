package com.xiaomi.accountsdk.hasheddeviceidlib;

/* JADX INFO: loaded from: classes4.dex */
public enum DeviceIdSource {
    SYSTEM_ACCOUNT_FROM_IOT_ID(1),
    SYSTEM_ACCOUNT_FROM_LOCAL_OAID(2),
    SYSTEM_ACCOUNT_FROM_FIND_DEVICE(3),
    SYSTEM_ACCOUNT_FROM_LOCAL_ANDROID_ID(4),
    SYSTEM_ACCOUNT_FROM_LOCAL_RANDOM(5),
    SDK_LOCAL_FROM_TELEPHONY_SERVICE(6),
    SDK_LOCAL_FROM_OAID(7),
    SDK_LOCAL_FROM_ANDROID_ID(8),
    SDK_LOCAL_FROM_RANDOM(9),
    SYSTEM_ACCOUNT_FROM_OLD_VERSION(100),
    OLD_SDK_GENERATE_DEVICE_ID(101);

    public final int value;

    DeviceIdSource(int i) {
        this.value = i;
    }

    public static DeviceIdSource fromValue(int i) {
        for (DeviceIdSource deviceIdSource : values()) {
            if (deviceIdSource.value == i) {
                return deviceIdSource;
            }
        }
        return null;
    }
}
