package com.xiaomi.passport.webview;

import android.content.Context;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes4.dex */
public interface UrlInterceptor extends Parcelable {
    void release();

    boolean shouldIntercept(Context context, String str);
}
