package com.miui.perm.autostart;

import android.app.AppOpsManager;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import com.android.internal.app.IMessenger;
import com.xiaomi.mirror.synergy.CallMethod;

/* JADX INFO: loaded from: classes4.dex */
public class AutoStartManager {
    public static final int MODE_ALLOWED = 0;
    public static final int MODE_IGNORED = 1;
    public static final int MODE_UNKNOWN = -1;
    public static final int STATE_FAILED = 0;
    public static final int STATE_SUCCESS = 1;
    public static final int STATE_UNSUPPORTED = -1;
    private static final String TAG = "AutoStartManager";

    public interface AutoStartManagerObserver {
        void onAutoStartSwitched(int i);
    }

    public static int checkAutoStart(Context context) {
        if (context == null) {
            return -1;
        }
        try {
            return ((AppOpsManager) context.getSystemService("appops")).checkOpNoThrow(10008, Process.myUid(), context.getPackageName());
        } catch (Exception e) {
            Log.e(TAG, "exception when check op!", e);
            return -1;
        }
    }

    public static void switchAutoStart(boolean z, Context context, final AutoStartManagerObserver autoStartManagerObserver) {
        if (context == null) {
            return;
        }
        ContentResolver contentResolver = context.getContentResolver();
        Bundle bundle = new Bundle();
        if (autoStartManagerObserver != null) {
            bundle.putBinder(CallMethod.ARG_CALLBACK, new IMessenger.Stub() { // from class: com.miui.perm.autostart.AutoStartManager.1
                @Override // com.android.internal.app.IMessenger
                public void send(Message message) {
                    try {
                        autoStartManagerObserver.onAutoStartSwitched(message.arg1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.asBinder());
        }
        try {
            contentResolver.call(Uri.parse("content://com.lbe.security.miui.autostartmgr"), z ? "openAutoStart" : "closeAutoStart", (String) null, bundle);
        } catch (Exception e) {
            Log.e(TAG, "open not support", e);
            if (autoStartManagerObserver != null) {
                autoStartManagerObserver.onAutoStartSwitched(-1);
            }
        }
    }
}
