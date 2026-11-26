package com.xiaomi.miai.config;

import com.xiaomi.miai.auth.UserInfo;
import com.xiaomi.miai.network.UserAgent;
import java.lang.reflect.Type;

/* JADX INFO: loaded from: classes4.dex */
public class ConfigKeys {
    public static MiaiSdkConfig.Key<Endpoints> ENV_ENDPOINTS = makeKey(Endpoints.class, "environment.endpoints");
    public static MiaiSdkConfig.Key<UserAgent> USER_AGENT = makeKey(UserAgent.class, "config.user.agent");
    public static MiaiSdkConfig.Key<UserInfo> USER_INFO = makeKey(UserInfo.class, "config.caller");

    public static class Network {
        public static MiaiSdkConfig.Key<Long> CONNECT_TIMEOUT = ConfigKeys.makeKey(Long.class, "network.connect.timeout");
        public static MiaiSdkConfig.Key<Long> READ_TIMEOUT = ConfigKeys.makeKey(Long.class, "network.read.timeout");
        public static MiaiSdkConfig.Key<Long> WRITE_TIMEOUT = ConfigKeys.makeKey(Long.class, "network.write.timeout");
    }

    protected static <T> MiaiSdkConfig.Key<T> makeKey(Type type, String str) {
        return new MiaiSdkConfig.Key<>(str, type);
    }
}
