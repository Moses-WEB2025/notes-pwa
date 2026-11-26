package com.miui.mishare.tapshare;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class NfcTouchDataHandler extends TapShareDataHandler {
    private static final String TAG = "NfcTouchDataHandler";
    private final String mBtMac;
    private final int mDeviceType;
    private final int mOneHopVersion;

    protected NfcTouchDataHandler(Context context, int i, String str, int i2) {
        super(context);
        this.mDeviceType = i;
        this.mBtMac = str;
        this.mOneHopVersion = i2;
    }

    @Override // com.miui.mishare.tapshare.TapShareDataHandler
    public int sendByUris(List<Uri> list, TapShareSendBean tapShareSendBean) {
        Log.i(TAG, "call sendByUris by nfc touch!");
        if (list == null) {
            Log.e(TAG, "files is null, do nothing!");
            return 4;
        }
        return NfcTouchManager.getInstance().sendFileToDeviceByBtMac(this.mContext, this.mBtMac, this.mDeviceType, new ArrayList<>(list), this.mOneHopVersion);
    }

    @Override // com.miui.mishare.tapshare.TapShareDataHandler
    public int cancelSend(int i, String str) {
        Log.w(TAG, "nfc touch call cancelSend, do nothing!");
        return 0;
    }

    @Override // com.miui.mishare.tapshare.TapShareDataHandler
    public int sendByString(String str, TapShareSendBean tapShareSendBean) {
        Log.w(TAG, "nfc touch call sendByString, invalid method called!");
        return 3;
    }

    @Override // com.miui.mishare.tapshare.TapShareDataHandler
    public Bundle getTapShareInfo() {
        Log.w(TAG, "nfc touch call getTapShareInfo, invalid method called!");
        return null;
    }
}
