package com.xiaomi.mirror.synergy;

import android.graphics.Bitmap;

/* JADX INFO: loaded from: classes4.dex */
public interface RelayIconCallback {
    void onIconHide();

    void onIconShow();

    void onIconUpdate(String str, Bitmap bitmap, String str2, String str3, int i, String str4, boolean z);
}
