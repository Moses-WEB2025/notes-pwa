package com.xiaomi.passport.webview;

import android.app.Activity;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes4.dex */
public interface PassportJsbWebPageLifecycleListener extends Parcelable {
    void onPageEntered(Activity activity);

    void onPageExited(Activity activity);

    void onPageHidden(Activity activity);

    void onPageShown(Activity activity);
}
