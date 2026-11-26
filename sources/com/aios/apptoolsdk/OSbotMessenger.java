package com.aios.apptoolsdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.aios.apptoolsdk.aidl.IOSbotMessenger;

/* JADX INFO: loaded from: classes.dex */
public class OSbotMessenger {
    private static final String ACTION_MESSENGER = "com.aios.osbot.action.MESSENGER";
    private static final String OSBOT_PACKAGE = "com.aios.osbot";
    private static final String TAG = "OSbotMessenger";
    private boolean bound = false;
    private final ServiceConnection connection = new ServiceConnection() { // from class: com.aios.apptoolsdk.OSbotMessenger.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            OSbotMessenger.this.messenger = IOSbotMessenger.Stub.asInterface(iBinder);
            OSbotMessenger.this.bound = true;
            Log.i(OSbotMessenger.TAG, "已连接到 OSbot Messenger Service");
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            OSbotMessenger.this.messenger = null;
            OSbotMessenger.this.bound = false;
            Log.w(OSbotMessenger.TAG, "OSbot Messenger Service 连接断开");
        }
    };
    private final Context context;
    private IOSbotMessenger messenger;

    public OSbotMessenger(Context context) {
        this.context = context.getApplicationContext();
    }

    public boolean bind() {
        Intent intent = new Intent(ACTION_MESSENGER);
        intent.setPackage(OSBOT_PACKAGE);
        try {
            boolean zBindService = this.context.bindService(intent, this.connection, 1);
            Log.i(TAG, "bindService result=" + zBindService);
            return zBindService;
        } catch (Exception e) {
            Log.e(TAG, "bindService 失败: " + e.getMessage(), e);
            return false;
        }
    }

    public void unbind() {
        if (this.bound) {
            try {
                this.context.unbindService(this.connection);
            } catch (Exception e) {
                Log.w(TAG, "unbindService 失败: " + e.getMessage());
            }
            this.messenger = null;
            this.bound = false;
        }
    }

    public boolean isConnected() {
        return this.bound && this.messenger != null;
    }

    public boolean sendNotification(String str) {
        IOSbotMessenger iOSbotMessenger = this.messenger;
        if (iOSbotMessenger == null) {
            Log.w(TAG, "未连接到 OSbot，请先调用 bind()");
            return false;
        }
        try {
            iOSbotMessenger.sendNotification(str);
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, "发送通知事件失败: " + e.getMessage(), e);
            return false;
        }
    }
}
