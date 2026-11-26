package com.xiaomi.mirror.synergy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

/* JADX INFO: loaded from: classes4.dex */
public class RemoteBroadcastManager {
    public static final String PERMISSION_RECEIVE_REMOTE_BROADCAST = "com.xiaomi.mirror.permission.RECEIVE_REMOTE_BROADCAST";
    public static final String PERMISSION_SEND_REMOTE_BROADCAST = "com.xiaomi.mirror.permission.SEND_REMOTE_BROADCAST";
    public static RemoteBroadcastManager mInstance;
    public static final Object mLock = new Object();
    public final Context mAppContext;

    public RemoteBroadcastManager(Context context) {
        this.mAppContext = context;
    }

    public static RemoteBroadcastManager getInstance(Context context) {
        RemoteBroadcastManager remoteBroadcastManager;
        synchronized (mLock) {
            if (mInstance == null) {
                mInstance = new RemoteBroadcastManager(context.getApplicationContext());
            }
            remoteBroadcastManager = mInstance;
        }
        return remoteBroadcastManager;
    }

    public void registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        this.mAppContext.registerReceiver(broadcastReceiver, intentFilter, PERMISSION_SEND_REMOTE_BROADCAST, null);
    }

    public int sendBroadcast(Intent intent) {
        return sendBroadcastAsDevice(intent, null);
    }

    public int sendBroadcastAsDevice(Intent intent, String str) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("intent", intent);
        bundle.putString("package", this.mAppContext.getPackageName());
        Bundle bundleDoCall = CallMethod.doCall(this.mAppContext.getContentResolver(), CallMethod.METHOD_SEND_REMOTE_BROADCAST, str, bundle);
        if (bundleDoCall != null) {
            return bundleDoCall.getInt("result", -1);
        }
        return -1;
    }

    public void unregisterReceiver(BroadcastReceiver broadcastReceiver) {
        this.mAppContext.unregisterReceiver(broadcastReceiver);
    }
}
