package com.market.sdk;

import android.text.TextUtils;
import android.util.Log;

/* JADX INFO: loaded from: classes2.dex */
public class DirectMailManager {
    private static final String TAG = "DirectMailManager";
    private static volatile DirectMailManager sInstance;

    public static DirectMailManager get() {
        if (sInstance == null) {
            synchronized (DirectMailManager.class) {
                if (sInstance == null) {
                    sInstance = new DirectMailManager();
                }
            }
        }
        return sInstance;
    }

    public void preloadAppDetail(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        try {
            PreloadAppDetailService.openService().preloadAppDetail(str);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }
}
