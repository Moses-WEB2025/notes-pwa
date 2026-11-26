package com.xiaomi.accountsdk.hasheddeviceidlib;

/* JADX INFO: loaded from: classes4.dex */
public class DeviceIDCloudCoder {
    protected DeviceIDCloudCoder() {
    }

    public static String getDataMd5Digest(byte[] bArr) {
        return DeviceIDCoder.getDataMd5Digest(bArr);
    }

    public static String getMd5DigestUpperCase(String str) {
        return DeviceIDCoder.getMd5DigestUpperCase(str);
    }
}
