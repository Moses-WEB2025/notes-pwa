package com.xiaomi.account.privacy_data.master;

import com.xiaomi.passport.sim.SIMInfo;

/* JADX INFO: loaded from: classes4.dex */
public enum PrivacyDataType {
    OAID("oaid"),
    ANDROID_ID("android_id"),
    DEVICE_ID("device_id"),
    MIUI_DEVICE_ID("miui_device_id"),
    BLUETOOTH_NAME("bluetooth_name"),
    BLUETOOTH_ADDRESS("bluetooth_address"),
    MAC_ADDRESS("mac_address"),
    SSID("ssid"),
    BSSID("bssid"),
    CONFIGURED_SSIDS("configured_ssids"),
    ICCID("iccid"),
    IMSI(SIMInfo.SIM_INFO_TYPE_IMSI),
    LINE_1_NUMBER("line_1_number"),
    MCCMNC(SIMInfo.SIM_INFO_TYPE_MCCMNC),
    NETWORK_MCCMNC("network_mccmnc"),
    SUB_ID("sub_id"),
    MOBILE_DATA_ENABLE("mobile_data_enable"),
    SIM_IN_SERVICE("sim_in_service");

    public final String name;

    PrivacyDataType(String str) {
        this.name = str;
    }
}
