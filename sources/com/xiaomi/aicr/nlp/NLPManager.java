package com.xiaomi.aicr.nlp;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import com.xiaomi.aicr.IAiCrCoreService;
import com.xiaomi.aicr.common.AiServiceConnection;
import com.xiaomi.aicr.common.ManagerBase;
import com.xiaomi.aicr.common.SmartLog;
import com.xiaomi.aicr.constant.Constants;
import com.xiaomi.aicr.plugin.INerService;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class NLPManager extends ManagerBase {
    private static final String DESC = "com.xiaomi.aicr.plugin.INerService";
    private static final String TAG = "NLPManager";
    private NLPManager mNLPManager;
    private INerService mService;
    private boolean mSync;

    @Override // com.xiaomi.aicr.common.ManagerBase
    protected void downloadSpeed(String str, long j) {
    }

    public NLPManager(Context context) {
        super(context);
        this.mSync = true;
    }

    public NLPManager(Context context, AiServiceConnection aiServiceConnection) {
        super(context, aiServiceConnection);
        this.mSync = true;
    }

    public NLPManager(Context context, AiServiceConnection aiServiceConnection, boolean z) {
        super(context, aiServiceConnection);
        this.mSync = z;
    }

    public static boolean isSupport(Context context) {
        return isSupport(context, DESC, "V0");
    }

    @Override // com.xiaomi.aicr.common.ManagerBase
    protected void registerPlugin(final IAiCrCoreService iAiCrCoreService) {
        NLPManager nLPManager;
        if (this.mSync) {
            try {
                Bundle bundle = new Bundle();
                nLPManager = this;
                try {
                    IBinder pluginBinder = iAiCrCoreService.getPluginBinder(nLPManager, super.toString(), DESC, "", bundle);
                    int i = bundle.getInt("Status");
                    if (i == Constants.STATUS_DEFINE.OK.ordinal()) {
                        nLPManager.mService = INerService.Stub.asInterface(pluginBinder);
                    } else if (i == Constants.STATUS_DEFINE.DOWNLOADING_FIRST_USE.ordinal()) {
                        nLPManager.onFirstTimeUse(DESC);
                    }
                } catch (Exception e) {
                    e = e;
                    SmartLog.d(TAG, "fail to fetch plugin service " + e);
                }
            } catch (Exception e2) {
                e = e2;
                nLPManager = this;
            }
            nLPManager.connected = nLPManager.mService != null;
            if (nLPManager.mServiceConn != null && nLPManager.connected) {
                nLPManager.mServiceConn.onServiceConnected(Constants.STATUS_DEFINE.OK.ordinal());
                return;
            } else {
                if (nLPManager.mServiceConn == null || nLPManager.connected || nLPManager.isSupport(DESC)) {
                    return;
                }
                nLPManager.mServiceConn.onServiceConnected(Constants.STATUS_DEFINE.UNSUPPORTED_CAPABILITY.ordinal());
                return;
            }
        }
        this.mNLPManager = this;
        new Thread(new Runnable() { // from class: com.xiaomi.aicr.nlp.NLPManager.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Bundle bundle2 = new Bundle();
                    IBinder pluginBinder2 = iAiCrCoreService.getPluginBinder(NLPManager.this.mNLPManager, super.toString(), NLPManager.DESC, "", bundle2);
                    int i2 = bundle2.getInt("Status");
                    if (i2 == Constants.STATUS_DEFINE.OK.ordinal()) {
                        NLPManager.this.mService = INerService.Stub.asInterface(pluginBinder2);
                    } else if (i2 == Constants.STATUS_DEFINE.DOWNLOADING_FIRST_USE.ordinal()) {
                        NLPManager.this.onFirstTimeUse(NLPManager.DESC);
                    }
                } catch (Exception e3) {
                    SmartLog.d(NLPManager.TAG, "fail to fetch plugin service " + e3);
                }
                NLPManager nLPManager2 = NLPManager.this;
                nLPManager2.connected = nLPManager2.mService != null;
                if (NLPManager.this.mServiceConn == null || !NLPManager.this.connected) {
                    if (NLPManager.this.mServiceConn == null || NLPManager.this.connected || NLPManager.this.isSupport(NLPManager.DESC)) {
                        return;
                    }
                    NLPManager.this.mServiceConn.onServiceConnected(Constants.STATUS_DEFINE.UNSUPPORTED_CAPABILITY.ordinal());
                    return;
                }
                NLPManager.this.mServiceConn.onServiceConnected(Constants.STATUS_DEFINE.OK.ordinal());
            }
        }).start();
    }

    @Override // com.xiaomi.aicr.common.ManagerBase
    protected void unregisterPlugin() {
        SmartLog.d(TAG, "Service 断开连接");
        if (this.mServiceConn != null) {
            this.mServiceConn.onServiceDisconnected();
        }
    }

    public List<Entity> detect(String str) {
        try {
            INerService iNerService = this.mService;
            if (iNerService != null) {
                return iNerService.extract(str);
            }
            SmartLog.e(TAG, "service not connected yet!");
            tryReconnect();
            return null;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Entity> detectForScreenRecognition(String str) {
        try {
            INerService iNerService = this.mService;
            if (iNerService != null) {
                return iNerService.extractForScreenRecognition(str);
            }
            SmartLog.e(TAG, "service not connected yet!");
            tryReconnect();
            return null;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Entity> detectForContext(String str) {
        try {
            INerService iNerService = this.mService;
            if (iNerService != null) {
                return iNerService.extractForContext(str);
            }
            SmartLog.e(TAG, "service not connected yet!");
            tryReconnect();
            return null;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> cut(String str) {
        try {
            INerService iNerService = this.mService;
            if (iNerService != null) {
                return iNerService.cut(str);
            }
            SmartLog.e(TAG, "service not connected yet!");
            tryReconnect();
            return null;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }
}
