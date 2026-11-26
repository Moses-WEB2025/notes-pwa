package com.xiaomi.accountsdk.hasheddeviceidlib;

import ch.qos.logback.core.CoreConstants;

/* JADX INFO: loaded from: classes4.dex */
public interface IAccountDeviceIdGetter {
    DeviceId getDeviceId();

    public static class DeviceId {
        public final String id;
        public final DeviceIdSource source;

        public DeviceId(String str, DeviceIdSource deviceIdSource) {
            this.id = str;
            this.source = deviceIdSource;
        }

        public String toString() {
            return "DeviceId{id='" + this.id + "', source=" + this.source + CoreConstants.CURLY_RIGHT;
        }
    }
}
