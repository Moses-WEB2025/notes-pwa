package com.xiaomi.phonenum.utils;

import android.content.Context;
import com.xiaomi.account.privacy_data.master.PrivacyDataMaster;
import com.xiaomi.account.privacy_data.master.PrivacyDataType;

/* JADX INFO: loaded from: classes4.dex */
public class SubId {
    public static final int INVALID_SUB_ID = -1;
    private static final String TAG = "SubId";

    public static int get(Context context, int i) {
        try {
            return Integer.parseInt(PrivacyDataMaster.forceGet(context, PrivacyDataType.SUB_ID, String.valueOf(i)));
        } catch (NumberFormatException unused) {
            return -1;
        }
    }
}
