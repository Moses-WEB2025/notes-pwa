package com.xiaomi.ai;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.media.AudioManager;
import com.miui.notes.ai.stat.AITextWidgetStat;
import com.xiaomi.ai.utils.Log;

/* JADX INFO: loaded from: classes4.dex */
public class BluetoothManager {
    private static final String TAG = "MiSpeechSDK:BluetoothManager";
    private static volatile BluetoothManager sInstance;
    private AudioManager mAudioManager;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mBtDevice;
    private BluetoothHeadset mBtHeadset;
    private Context mContext;
    private volatile boolean mHasPendingRequest;
    private boolean mBindBtSuccess = false;
    private BroadcastReceiver mScoStateReceiver = new a(this);
    private BluetoothProfile.ServiceListener mBtHeadsetServiceListener = new b(this);

    private BluetoothManager(Context context) {
        this.mContext = context;
        this.mAudioManager = (AudioManager) context.getSystemService(AITextWidgetStat.TYPE_AUDIO);
        try {
            this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        } catch (RuntimeException e) {
            Log.w(TAG, "Cant get default bluetooth adapter ", e);
        }
    }

    public static BluetoothManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (BluetoothManager.class) {
                if (sInstance == null) {
                    sInstance = new BluetoothManager(context);
                }
            }
        }
        return sInstance;
    }

    public boolean canBluetooth() {
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        if (bluetoothAdapter != null && bluetoothAdapter.isEnabled() && 2 == this.mBluetoothAdapter.getProfileConnectionState(1)) {
            Log.MiLogD(TAG, "hasConnectedDevice true");
            if (this.mAudioManager.isBluetoothScoAvailableOffCall()) {
                return true;
            }
        }
        return false;
    }

    public String getBluetoothMacAddress() {
        BluetoothDevice bluetoothDevice = this.mBtDevice;
        if (bluetoothDevice == null) {
            return null;
        }
        return bluetoothDevice.getAddress();
    }

    public String getBluetoothName() {
        BluetoothDevice bluetoothDevice = this.mBtDevice;
        if (bluetoothDevice == null) {
            return null;
        }
        return bluetoothDevice.getName();
    }

    public boolean isScoOn() {
        BluetoothHeadset bluetoothHeadset = this.mBtHeadset;
        return bluetoothHeadset != null && bluetoothHeadset.isAudioConnected(this.mBtDevice);
    }

    public void register() {
        Log.MiLogD(TAG, "Register BT media receiver");
        if (canBluetooth()) {
            Log.MiLogD(TAG, "Register BT media receiver success");
            this.mBluetoothAdapter.getProfileProxy(this.mContext, this.mBtHeadsetServiceListener, 1);
            this.mBindBtSuccess = true;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.media.ACTION_SCO_AUDIO_STATE_UPDATED");
        intentFilter.addAction("android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED");
        this.mContext.registerReceiver(this.mScoStateReceiver, intentFilter);
    }

    public void setBluetoothOn(boolean z) {
        String str;
        String str2;
        long jCurrentTimeMillis = System.currentTimeMillis();
        Log.v(TAG, "BluetoothManager(" + z + ")");
        if (canBluetooth()) {
            if (!this.mBindBtSuccess) {
                Log.v(TAG, "getProfileProxy ok ");
                this.mBluetoothAdapter.getProfileProxy(this.mContext, this.mBtHeadsetServiceListener, 1);
                this.mBindBtSuccess = true;
            }
            BluetoothHeadset bluetoothHeadset = this.mBtHeadset;
            if (bluetoothHeadset != null) {
                boolean zIsAudioConnected = bluetoothHeadset.isAudioConnected(this.mBtDevice);
                if (z && !zIsAudioConnected) {
                    str2 = this.mBtHeadset.startVoiceRecognition(this.mBtDevice) ? "start success" : "start fail already";
                } else if (z || !zIsAudioConnected) {
                    str2 = "setBluetoothOn  failed  dst=" + z + " state=" + zIsAudioConnected;
                } else {
                    Log.MiLogD(TAG, "stop success");
                    this.mBtHeadset.stopVoiceRecognition(this.mBtDevice);
                }
                Log.MiLogD(TAG, str2);
            } else if (z) {
                Log.MiLogD(TAG, "set mHasPendingRequest true");
                this.mHasPendingRequest = true;
            } else {
                this.mHasPendingRequest = false;
            }
            str = "setBluetoothOn take time =" + (System.currentTimeMillis() - jCurrentTimeMillis);
        } else {
            str = "can't bluetooth";
        }
        Log.MiLogD(TAG, str);
    }

    public void unregister() {
        try {
            Log.MiLogD(TAG, "Unregister BT media receiver");
            setBluetoothOn(false);
            this.mContext.unregisterReceiver(this.mScoStateReceiver);
        } catch (Exception e) {
            Log.w(TAG, "Failed to unregister media state receiver", e);
        }
        if (this.mBindBtSuccess) {
            this.mBluetoothAdapter.closeProfileProxy(1, this.mBtHeadset);
            this.mBindBtSuccess = false;
        }
    }
}
