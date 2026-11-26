package com.xiaomi.aicr.barcode;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import com.xiaomi.aicr.IAiCrCoreService;
import com.xiaomi.aicr.common.AiServiceConnection;
import com.xiaomi.aicr.common.ManagerBase;
import com.xiaomi.aicr.common.SmartLog;
import com.xiaomi.aicr.constant.Constants;
import com.xiaomi.aicr.plugin.IBarcodeService;

/* JADX INFO: loaded from: classes4.dex */
public class BarcodeManager extends ManagerBase {
    private static final String DESC = "com.xiaomi.aicr.plugin.IBarcodeService";
    private static final String TAG = "BarcodeManager";
    IBarcodeService mService;

    @Override // com.xiaomi.aicr.common.ManagerBase
    protected void downloadSpeed(String str, long j) {
    }

    public BarcodeManager(Context context) {
        super(context);
    }

    public BarcodeManager(Context context, AiServiceConnection aiServiceConnection) {
        super(context, aiServiceConnection);
    }

    public static boolean isSupport(Context context) {
        return isSupport(context, DESC, "V0");
    }

    @Override // com.xiaomi.aicr.common.ManagerBase
    protected void registerPlugin(IAiCrCoreService iAiCrCoreService) {
        BarcodeManager barcodeManager;
        try {
            Bundle bundle = new Bundle();
            barcodeManager = this;
            try {
                IBinder pluginBinder = iAiCrCoreService.getPluginBinder(barcodeManager, super.toString(), DESC, "", bundle);
                int i = bundle.getInt("Status");
                if (i == Constants.STATUS_DEFINE.OK.ordinal()) {
                    barcodeManager.mService = IBarcodeService.Stub.asInterface(pluginBinder);
                } else if (i == Constants.STATUS_DEFINE.DOWNLOADING_FIRST_USE.ordinal()) {
                    barcodeManager.onFirstTimeUse(DESC);
                }
            } catch (Exception e) {
                e = e;
                SmartLog.d(TAG, "fail to fetch plugin service " + e);
            }
        } catch (Exception e2) {
            e = e2;
            barcodeManager = this;
        }
        barcodeManager.connected = barcodeManager.mService != null;
        if (barcodeManager.mServiceConn != null && barcodeManager.connected) {
            barcodeManager.mServiceConn.onServiceConnected(Constants.STATUS_DEFINE.OK.ordinal());
        } else {
            if (barcodeManager.mServiceConn == null || barcodeManager.connected || barcodeManager.isSupport(DESC)) {
                return;
            }
            barcodeManager.mServiceConn.onServiceConnected(Constants.STATUS_DEFINE.UNSUPPORTED_CAPABILITY.ordinal());
        }
    }

    @Override // com.xiaomi.aicr.common.ManagerBase
    protected void unregisterPlugin() {
        SmartLog.d(TAG, "Service disconnect");
        if (this.mServiceConn != null) {
            this.mServiceConn.onServiceDisconnected();
        }
    }

    public void registerBarcodeResultListener(String str, IBarcodeResultListener iBarcodeResultListener) {
        if (this.mService == null || TextUtils.isEmpty(str) || iBarcodeResultListener == null) {
            SmartLog.d(TAG, "registerBarcodeResultListener failed");
            return;
        }
        try {
            this.mService.registerBarcodeResultListener(iBarcodeResultListener, str);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void unRegisterBarcodeResultListener(String str) {
        if (this.mService == null || TextUtils.isEmpty(str)) {
            SmartLog.d(TAG, "unRegisterBarcodeResultListener failed");
            return;
        }
        try {
            this.mService.unregisterBarcodeResultListener(str);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void detectBarcodeFromBitmap(Bitmap bitmap, String str) {
        if (this.mService == null || bitmap == null || TextUtils.isEmpty(str)) {
            SmartLog.d(TAG, "detectBarcodeFromBitmap failed");
            return;
        }
        try {
            this.mService.detectBarcodeFromBitmap(bitmap, str);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
