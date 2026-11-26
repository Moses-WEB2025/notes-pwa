package com.xiaomi.passport.v2.utils;

import com.xiaomi.accountsdk.account.data.ActivatorPhoneInfo;

/* JADX INFO: loaded from: classes4.dex */
public interface ActivatorPhoneInfoCallback {
    void onDualSIM(ActivatorPhoneInfo activatorPhoneInfo, ActivatorPhoneInfo activatorPhoneInfo2);

    void onNone();

    void onSingleSIM(ActivatorPhoneInfo activatorPhoneInfo);
}
