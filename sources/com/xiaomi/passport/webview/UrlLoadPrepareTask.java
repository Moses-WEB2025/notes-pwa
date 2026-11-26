package com.xiaomi.passport.webview;

import android.content.Context;
import android.os.Parcelable;
import java.lang.ref.WeakReference;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public interface UrlLoadPrepareTask extends Parcelable {
    void execute(Context context, WeakReference<Context> weakReference, Map<String, String> map, Map<String, String> map2);
}
