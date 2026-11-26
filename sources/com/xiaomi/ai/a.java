package com.xiaomi.ai;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.xiaomi.ai.utils.Log;

/* JADX INFO: loaded from: classes4.dex */
class a extends BroadcastReceiver {
    final /* synthetic */ BluetoothManager a;

    a(BluetoothManager bluetoothManager) {
        this.a = bluetoothManager;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        StringBuilder sb;
        StringBuilder sbAppend;
        String action = intent.getAction();
        Log.MiLogD("MiSpeechSDK:BluetoothManager", ">>> BT SCO state changed !!! action = " + action);
        if ("android.media.ACTION_SCO_AUDIO_STATE_UPDATED".equals(action)) {
            sbAppend = new StringBuilder("status = ").append(intent.getIntExtra("android.media.extra.SCO_AUDIO_STATE", -1));
        } else {
            if (!"android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED".equals(action)) {
                return;
            }
            int intExtra = intent.getIntExtra("android.bluetooth.profile.extra.STATE", -1);
            Log.MiLogD("MiSpeechSDK:BluetoothManager", "status = " + intExtra);
            if (intExtra == 10) {
                sb = new StringBuilder("STATE_AUDIO_DISCONNECTED ");
            } else if (intExtra != 12) {
                return;
            } else {
                sb = new StringBuilder("STATE_AUDIO_CONNECTED ");
            }
            sbAppend = sb.append(this.a.isScoOn());
        }
        Log.MiLogD("MiSpeechSDK:BluetoothManager", sbAppend.toString());
    }
}
