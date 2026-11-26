package com.xiaomi.ai;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import com.xiaomi.ai.utils.Log;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
class b implements BluetoothProfile.ServiceListener {
    final /* synthetic */ BluetoothManager a;

    b(BluetoothManager bluetoothManager) {
        this.a = bluetoothManager;
    }

    @Override // android.bluetooth.BluetoothProfile.ServiceListener
    public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
        this.a.mBtHeadset = (BluetoothHeadset) bluetoothProfile;
        Log.MiLogD("MiSpeechSDK:BluetoothManager", "onServiceConnected profile" + i);
        List<BluetoothDevice> connectedDevices = this.a.mBtHeadset.getConnectedDevices();
        if (connectedDevices.size() <= 0) {
            Log.MiLogD("MiSpeechSDK:BluetoothManager", "onServiceConnected size" + connectedDevices.size());
            return;
        }
        Log.MiLogD("MiSpeechSDK:BluetoothManager", "l.size(): " + connectedDevices.size());
        this.a.mBtDevice = connectedDevices.get(0);
        this.a.mBindBtSuccess = true;
        if (this.a.mHasPendingRequest) {
            Log.MiLogD("MiSpeechSDK:BluetoothManager", "mHasPendingRequest, set sco on");
            this.a.setBluetoothOn(true);
            this.a.mHasPendingRequest = false;
        }
    }

    @Override // android.bluetooth.BluetoothProfile.ServiceListener
    public void onServiceDisconnected(int i) {
        Log.MiLogD("MiSpeechSDK:BluetoothManager", "onServiceDisconnected profile" + i);
        if (i == 1) {
            Log.MiLogD("MiSpeechSDK:BluetoothManager", "onServiceDisconnected");
            this.a.mBindBtSuccess = false;
            this.a.mBtHeadset = null;
        }
    }
}
