package com.market.sdk;

import android.net.Uri;

/* JADX INFO: loaded from: classes2.dex */
public interface ImageCallback {
    void onImageLoadFailed(String str);

    void onImageLoadSuccess(String str, Uri uri);
}
