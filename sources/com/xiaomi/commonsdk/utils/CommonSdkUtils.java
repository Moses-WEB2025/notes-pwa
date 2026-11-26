package com.xiaomi.commonsdk.utils;

import com.xiaomi.micloudsdk.cloudinfo.utils.CloudInfoUtils;

/* JADX INFO: loaded from: classes4.dex */
@Deprecated
public class CommonSdkUtils {
    public static String getUserAgent() {
        return CloudInfoUtils.getUserAgent();
    }
}
