package com.market.sdk.silentupdate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import com.market.sdk.utils.AppGlobal;

/* JADX INFO: loaded from: classes2.dex */
public class UpdateResultReceiver extends BroadcastReceiver {
    private static final UpdateResultReceiver sReceiver = new UpdateResultReceiver();
    private volatile Callback callback;
    private volatile boolean registered;

    public interface Callback {
        void onResult(Bundle bundle);
    }

    public static UpdateResultReceiver get() {
        return sReceiver;
    }

    public void register(Callback callback) {
        this.callback = callback;
        if (this.registered) {
            return;
        }
        AppGlobal.getContext().registerReceiver(this, new IntentFilter("com.xiaomi.market.DOWNLOAD_INSTALL_RESULT"));
        this.registered = true;
    }

    public void unregister() {
        this.callback = null;
        AppGlobal.getContext().unregisterReceiver(this);
        this.registered = false;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (!TextUtils.equals(context.getPackageName(), intent.getStringExtra("packageName")) || this.callback == null || intent.getExtras() == null) {
            return;
        }
        this.callback.onResult(intent.getExtras());
    }
}
