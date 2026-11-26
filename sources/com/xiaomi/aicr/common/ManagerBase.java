package com.xiaomi.aicr.common;

import android.app.ForegroundServiceStartNotAllowedException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.provider.Settings;
import com.xiaomi.aicr.IAiCrCoreService;
import com.xiaomi.aicr.ICoreServiceCallback;
import com.xiaomi.aicr.constant.Constants;
import java.util.HashMap;
import java.util.HashSet;
import net.lingala.zip4j.util.InternalZipConstants;

/* JADX INFO: loaded from: classes4.dex */
public abstract class ManagerBase extends ICoreServiceCallback.Stub implements ServiceConnection {
    private static final String AI_SERVICE_NAME = "com.xiaomi.aicr.access.AiCrCoreService";
    private static final String PACKAGE_NAME = "com.xiaomi.aicr";
    private static final String PRE_CLOUD_PLUGIN_CONFIG = "Aicr_Plugin_Version";
    protected static final int STATE_CONNECTED = 2;
    protected static final int STATE_CONNECTING = 1;
    protected static final int STATE_DISCONNECT = 0;
    private static final String TAG = "ManagerBase";
    protected static IAiCrCoreService mCoreService;
    private static volatile int sServiceState;
    protected volatile boolean connected = false;
    private Context mContext;
    protected AiServiceConnection mServiceConn;
    private static final Object token = new Object();
    private static HashSet<String> connectedService = new HashSet<>();

    protected abstract void downloadSpeed(String str, long j);

    protected abstract void registerPlugin(IAiCrCoreService iAiCrCoreService);

    protected abstract void unregisterPlugin();

    protected static boolean isSupport(Context context, String str, String str2) {
        try {
            int i = context.getPackageManager().getPackageInfo("com.xiaomi.aicr", 0).versionCode;
            SmartLog.d(TAG, "isSupport code :" + i);
            String str3 = getPluginConfigs(context, i).get(str);
            return str3 != null && str3.compareTo(str2) >= 0;
        } catch (PackageManager.NameNotFoundException unused) {
            SmartLog.i(TAG, "isSupport: false!!!");
            return false;
        }
    }

    protected static HashMap<String, String> getPluginConfigs(Context context, int i) {
        HashMap<String, String> map = new HashMap<>();
        String string = Settings.Global.getString(context.getContentResolver(), PRE_CLOUD_PLUGIN_CONFIG);
        if (string != null && !string.equals("")) {
            for (String str : string.split("\\|")) {
                String[] strArrSplit = str.split(",");
                if (strArrSplit.length == 2) {
                    map.put(strArrSplit[0], strArrSplit[1]);
                }
            }
        } else if (i >= 20230703) {
            map.put("com.xiaomi.aicr.plugin.INerService", "V0");
            map.put("com.xiaomi.aicr.plugin.IImageSegmentService", "V0");
            map.put("com.xiaomi.aicr.plugin.IVisionService", "V0");
            map.put("com.xiaomi.aicr.plugin.IHMindAlgoService", "V0");
        }
        if (i >= 2024082221) {
            map.put("com.xiaomi.aicr.plugin.IBarcodeService", "V0");
        }
        return map;
    }

    protected boolean isSupport(String str) {
        try {
            this.mContext.getPackageManager().getPackageInfo("com.xiaomi.aicr", 0);
            IAiCrCoreService iAiCrCoreService = mCoreService;
            return iAiCrCoreService != null && iAiCrCoreService.isSupport(str);
        } catch (PackageManager.NameNotFoundException | RemoteException unused) {
            SmartLog.i(TAG, "isSupport: false!!!");
            return false;
        }
    }

    public boolean isConnected() {
        return this.connected;
    }

    public boolean isServiceDisconnect() {
        return sServiceState == 0;
    }

    public boolean isServiceConnecting() {
        return sServiceState == 1;
    }

    public boolean isServiceConnected() {
        return sServiceState == 2 && mCoreService != null;
    }

    private void setServiceState(int i) {
        sServiceState = i;
    }

    public boolean waitForConnection(long j) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        SmartLog.d(TAG, "current " + jCurrentTimeMillis);
        if (isServiceDisconnect() || Looper.getMainLooper().isCurrentThread()) {
            SmartLog.d(TAG, "waitForConnection can't be called in main thread!");
            return false;
        }
        while (System.currentTimeMillis() <= jCurrentTimeMillis + j) {
            if (this.connected) {
                return true;
            }
            if (!isServiceConnecting()) {
                return false;
            }
            try {
                Thread.sleep(40L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return this.connected;
    }

    public ManagerBase(Context context) {
        SmartLog.d(TAG, "ManagerBase, create manager: " + this);
        this.mContext = context;
        connectCoreService();
    }

    public ManagerBase(Context context, AiServiceConnection aiServiceConnection) {
        SmartLog.d(TAG, "ManagerBase, create manager: " + this);
        this.mContext = context;
        this.mServiceConn = aiServiceConnection;
        connectCoreService();
    }

    public void connectCoreService() {
        if (isServiceConnected()) {
            SmartLog.i(TAG, "connectCoreService has connected.");
            onServiceConnected();
            return;
        }
        if (isServiceConnecting()) {
            SmartLog.w(TAG, "connectCoreService connecting...");
            return;
        }
        if (Build.VERSION.SDK_INT >= 31) {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.xiaomi.aicr", AI_SERVICE_NAME));
            intent.putExtra(Constants.KEY_BIND_TYPE, toString());
            try {
                this.mContext.startForegroundService(intent);
            } catch (ForegroundServiceStartNotAllowedException | SecurityException e) {
                SmartLog.e(TAG, "start service failed", e);
            }
            try {
                intent.setIdentifier(DateUtilKt.currentTimeStr());
                this.mContext.bindService(intent, this, 1);
                setServiceState(1);
            } catch (SecurityException e2) {
                SmartLog.e(TAG, "bind service failed", e2);
            }
        }
    }

    protected void onFirstTimeUse(String str) {
        SmartLog.d(TAG, "use " + str + " first time, downloading...");
    }

    protected void tryReconnect() {
        IAiCrCoreService iAiCrCoreService = mCoreService;
        if (iAiCrCoreService != null) {
            registerPlugin(iAiCrCoreService);
        } else {
            connectCoreService();
        }
    }

    public void releaseConnect() {
        if (isServiceDisconnect()) {
            SmartLog.i(TAG, "releaseConnect has disconnected.");
            return;
        }
        if (connectedService.contains(toString())) {
            SmartLog.d(TAG, "release connected service:" + toString());
            connectedService.remove(toString());
            try {
                mCoreService.releaseConnect(toString());
            } catch (Exception unused) {
                SmartLog.d(TAG, "fail to release connect :" + toString());
            }
        }
        if (connectedService.isEmpty()) {
            SmartLog.i(TAG, "no service connection, unbind" + toString());
            try {
                try {
                    this.mContext.unbindService(this);
                } catch (Exception e) {
                    SmartLog.e(TAG, "unbind error", e);
                }
            } finally {
                onServiceDisconnect();
            }
        }
    }

    private void onServiceConnected() {
        setServiceState(2);
        connectedService.add(toString());
        SmartLog.d(TAG, "onServiceConnected, current: " + toString());
        registerPlugin(mCoreService);
    }

    private void onServiceDisconnect() {
        mCoreService = null;
        this.connected = false;
        setServiceState(0);
        unregisterPlugin();
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        IAiCrCoreService iAiCrCoreServiceAsInterface = IAiCrCoreService.Stub.asInterface(iBinder);
        mCoreService = iAiCrCoreServiceAsInterface;
        if (iAiCrCoreServiceAsInterface != null) {
            onServiceConnected();
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        SmartLog.d(TAG, "onServiceDisconnected, current: " + toString());
        onServiceDisconnect();
    }

    public String toString() {
        return token.toString() + InternalZipConstants.ZIP_FILE_SEPARATOR + super.toString();
    }

    @Override // com.xiaomi.aicr.ICoreServiceCallback
    public void onDownload(IBinder iBinder) throws RemoteException {
        SmartLog.d(TAG, "onDownload" + iBinder.toString());
        IAiCrCoreService iAiCrCoreService = mCoreService;
        if (iAiCrCoreService != null) {
            registerPlugin(iAiCrCoreService);
        }
    }

    @Override // com.xiaomi.aicr.ICoreServiceCallback
    public void speed(String str, long j) throws RemoteException {
        downloadSpeed(str, j);
    }
}
