package com.xiaomi.micloudsdk.utils;

import android.util.Log;

/* JADX INFO: loaded from: classes4.dex */
public class MiCloudSdkBuild {
    public static final int CURRENT_VERSION;
    private static final String TAG = "MiCloudSdkBuild";

    static {
        int i = MiCloudSDKDependencyUtil.SDKEnvironment;
        if (i < 0) {
            throw new RuntimeException("No MiCloudSDK runtime!");
        }
        CURRENT_VERSION = i;
        Log.i(TAG, "MiCloudSdk version: " + i);
    }
}
