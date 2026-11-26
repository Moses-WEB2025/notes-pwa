package com.xiaomi.aicr.miot;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import com.xiaomi.aicr.IAiCrCoreService;
import com.xiaomi.aicr.common.AiServiceConnection;
import com.xiaomi.aicr.common.ManagerBase;
import com.xiaomi.aicr.common.SmartLog;
import com.xiaomi.aicr.constant.Constants;
import com.xiaomi.aicr.plugin.IMIotAlgoService;

/* JADX INFO: loaded from: classes4.dex */
public class MIotManager extends ManagerBase {
    private static final String DESC = "com.xiaomi.aicr.plugin.IMIotAlgoService";
    private static final String TAG = "HMindManager";
    private IMIotAlgoService mService;

    @Override // com.xiaomi.aicr.common.ManagerBase
    protected void downloadSpeed(String str, long j) {
    }

    public MIotManager(Context context) {
        super(context);
    }

    public MIotManager(Context context, AiServiceConnection aiServiceConnection) {
        super(context, aiServiceConnection);
    }

    public static boolean isSupport(Context context) {
        return isSupport(context, DESC, "V0");
    }

    @Override // com.xiaomi.aicr.common.ManagerBase
    protected void registerPlugin(IAiCrCoreService iAiCrCoreService) {
        MIotManager mIotManager;
        try {
            Bundle bundle = new Bundle();
            mIotManager = this;
            try {
                IBinder pluginBinder = iAiCrCoreService.getPluginBinder(mIotManager, super.toString(), DESC, "", bundle);
                int i = bundle.getInt("Status");
                if (i == Constants.STATUS_DEFINE.OK.ordinal()) {
                    mIotManager.mService = IMIotAlgoService.Stub.asInterface(pluginBinder);
                } else if (i == Constants.STATUS_DEFINE.DOWNLOADING_FIRST_USE.ordinal()) {
                    mIotManager.onFirstTimeUse(DESC);
                }
            } catch (Exception e) {
                e = e;
                SmartLog.d(TAG, "fail to fetch plugin service " + e);
            }
        } catch (Exception e2) {
            e = e2;
            mIotManager = this;
        }
        mIotManager.connected = mIotManager.mService != null;
        if (mIotManager.mServiceConn != null && mIotManager.connected) {
            mIotManager.mServiceConn.onServiceConnected(Constants.STATUS_DEFINE.OK.ordinal());
        } else {
            if (mIotManager.mServiceConn == null || mIotManager.connected || mIotManager.isSupport(DESC)) {
                return;
            }
            mIotManager.mServiceConn.onServiceConnected(Constants.STATUS_DEFINE.UNSUPPORTED_CAPABILITY.ordinal());
        }
    }

    @Override // com.xiaomi.aicr.common.ManagerBase
    protected void unregisterPlugin() {
        SmartLog.d(TAG, "Service 断开连接");
        if (this.mServiceConn != null) {
            this.mServiceConn.onServiceDisconnected();
        }
    }

    public void initAlgo(String str) throws RemoteException {
        IMIotAlgoService iMIotAlgoService = this.mService;
        if (iMIotAlgoService != null) {
            iMIotAlgoService.initAlgo(str);
        }
    }

    public String doCalculation(String str) throws RemoteException {
        IMIotAlgoService iMIotAlgoService = this.mService;
        if (iMIotAlgoService != null) {
            return iMIotAlgoService.doCalculation(str);
        }
        return null;
    }

    public void releaseAlgo() throws RemoteException {
        IMIotAlgoService iMIotAlgoService = this.mService;
        if (iMIotAlgoService != null) {
            iMIotAlgoService.releaseAlgo();
        }
    }

    public String getBrightness() throws RemoteException {
        IMIotAlgoService iMIotAlgoService = this.mService;
        if (iMIotAlgoService != null) {
            return iMIotAlgoService.getBrightness();
        }
        return null;
    }

    public String getCaculatedCurve() throws RemoteException {
        IMIotAlgoService iMIotAlgoService = this.mService;
        if (iMIotAlgoService != null) {
            return iMIotAlgoService.getCaculatedCurve();
        }
        return null;
    }
}
