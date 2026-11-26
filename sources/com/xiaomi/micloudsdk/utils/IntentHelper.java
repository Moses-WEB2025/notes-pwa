package com.xiaomi.micloudsdk.utils;

import android.content.Intent;

/* JADX INFO: loaded from: classes4.dex */
public class IntentHelper {
    public static Intent getWipeDataConfirmActivityIntent() {
        Intent intent = new Intent();
        intent.setAction("com.xiaomi.action.MICLOUD_WIPE_DATA_CONFIRM");
        intent.setPackage("com.miui.cloudservice");
        return intent;
    }
}
