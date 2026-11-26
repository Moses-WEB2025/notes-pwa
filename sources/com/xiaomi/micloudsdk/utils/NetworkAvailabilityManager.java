package com.xiaomi.micloudsdk.utils;

import android.content.Context;
import micloud.compat.independent.request.NetworkAvailabilityManagerCompat;

/* JADX INFO: loaded from: classes4.dex */
public class NetworkAvailabilityManager {
    public static boolean getAvailability(Context context) {
        return NetworkAvailabilityManagerCompat.getAvailability(context);
    }
}
