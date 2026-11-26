package com.xiaomi.micloudsdk.sync;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/* JADX INFO: loaded from: classes4.dex */
@Deprecated
public abstract class SyncCommandServiceBase extends IntentService {

    @Deprecated
    public static final String ACTION_SYNC_COMMAND = "com.xiaomi.micloud.action.SYNC_COMMAND";

    @Deprecated
    public static final String KEY_COMMAND = "key_command";
    private static final String TAG = "SyncCommandServiceBase";

    @Deprecated
    public static final String VALUE_COMMAND_CANCEL_SYNC = "value_command_cancel_sync";

    public abstract void handleCommand(String str);

    public SyncCommandServiceBase() {
        super(TAG);
    }

    @Override // android.app.IntentService
    protected void onHandleIntent(Intent intent) {
        if (intent == null || !"com.xiaomi.micloud.action.SYNC_COMMAND".equals(intent.getAction())) {
            Log.e(TAG, "illegal arguments");
        } else {
            handleCommand(intent.getStringExtra("key_command"));
        }
    }
}
