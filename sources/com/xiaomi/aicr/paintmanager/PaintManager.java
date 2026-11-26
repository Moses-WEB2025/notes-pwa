package com.xiaomi.aicr.paintmanager;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.miui.hybirdeditor.EmptyOverwriteGuard;
import com.miui.notes.basefeature.NotesNormalConstants;
import com.xiaomi.aicr.IAiCrCoreService;
import com.xiaomi.aicr.common.AiServiceConnection;
import com.xiaomi.aicr.common.ManagerBase;
import com.xiaomi.aicr.common.SmartLog;
import com.xiaomi.aicr.constant.Constants;
import com.xiaomi.aicr.paintservice.IPaintService;
import com.xiaomi.aicr.paintservice.IPaintStatusCallback;

/* JADX INFO: loaded from: classes4.dex */
public class PaintManager extends ManagerBase {
    private static final String DESC = "com.xiaomi.aicr.paintservice.IPaintService";
    private static final String TAG = "PaintManager:";
    private static final int imgHeight = 512;
    private static final int imgWidth = 512;
    private String PACKAGE_NAME;
    private IPaintService mService;
    private PackageManager packageManager;
    private PaintManagerStatus paintManagerStatus;

    @Override // com.xiaomi.aicr.common.ManagerBase
    protected void downloadSpeed(String str, long j) {
    }

    public PaintManager(Context context) {
        super(context);
        this.mService = null;
        this.paintManagerStatus = PaintManagerStatus.UNCONNECTED;
        this.PACKAGE_NAME = NotesNormalConstants.AICR_PACKAGE;
        this.packageManager = null;
        this.packageManager = context.getPackageManager();
    }

    public PaintManager(Context context, AiServiceConnection aiServiceConnection) {
        super(context, aiServiceConnection);
        this.mService = null;
        this.paintManagerStatus = PaintManagerStatus.UNCONNECTED;
        this.PACKAGE_NAME = NotesNormalConstants.AICR_PACKAGE;
        this.packageManager = null;
        this.packageManager = context.getPackageManager();
    }

    @Override // com.xiaomi.aicr.common.ManagerBase
    protected void registerPlugin(IAiCrCoreService iAiCrCoreService) {
        PaintManager paintManager;
        SmartLog.i(TAG, "registerPlugin");
        try {
            Bundle bundle = new Bundle();
            paintManager = this;
            try {
                IBinder pluginBinder = iAiCrCoreService.getPluginBinder(paintManager, super.toString(), DESC, "", bundle);
                int i = bundle.getInt("Status");
                if (i == Constants.STATUS_DEFINE.OK.ordinal()) {
                    paintManager.mService = IPaintService.Stub.asInterface(pluginBinder);
                } else if (i == Constants.STATUS_DEFINE.DOWNLOADING_FIRST_USE.ordinal()) {
                    paintManager.paintManagerStatus = PaintManagerStatus.UNDOWNLOADED;
                    SmartLog.i(TAG, "paintManagerStatus is UNDOWNLOADED!");
                    paintManager.onFirstTimeUse(DESC);
                }
            } catch (Exception e) {
                e = e;
                SmartLog.i(TAG, "fail to fetch plugin service " + e);
            }
        } catch (Exception e2) {
            e = e2;
            paintManager = this;
        }
        paintManager.connected = paintManager.mService != null;
        if (paintManager.paintManagerStatus == PaintManagerStatus.UNDOWNLOADED) {
            SmartLog.i(TAG, "paintManagerStatus is UNDOWNLOADED!!");
        } else if (paintManager.connected) {
            paintManager.paintManagerStatus = PaintManagerStatus.CONNECTED;
            SmartLog.i(TAG, "paintManagerStatus is CONNECTED");
        } else if (!paintManager.connected && !paintManager.isSupport(DESC)) {
            paintManager.paintManagerStatus = PaintManagerStatus.UNSUPPORTED;
            SmartLog.i(TAG, "paintManagerStatus is UNSUPPORTED");
        } else {
            paintManager.paintManagerStatus = PaintManagerStatus.UNCONNECTED;
            SmartLog.i(TAG, "paintManagerStatus is UNCONNECTED");
        }
        if (paintManager.mServiceConn != null) {
            paintManager.mServiceConn.onServiceConnected(0);
        }
    }

    @Override // com.xiaomi.aicr.common.ManagerBase
    protected void unregisterPlugin() {
        SmartLog.i(TAG, "unregisterPlugin");
        if (this.mServiceConn != null) {
            this.mServiceConn.onServiceDisconnected();
        }
        this.paintManagerStatus = PaintManagerStatus.UNCONNECTED;
        this.mService = null;
    }

    public void paintManager_retryConnect() {
        new Thread(new Runnable() { // from class: com.xiaomi.aicr.paintmanager.PaintManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m2516x6f591f1a();
            }
        }).start();
    }

    /* JADX INFO: renamed from: lambda$paintManager_retryConnect$0$com-xiaomi-aicr-paintmanager-PaintManager, reason: not valid java name */
    /* synthetic */ void m2516x6f591f1a() {
        tryReconnect();
        SmartLog.i(TAG, "paintManager_retryConnect,times:1");
        try {
            Thread.sleep(EmptyOverwriteGuard.USER_EDIT_VALID_WINDOW_MS);
            if (this.paintManagerStatus == PaintManagerStatus.UNCONNECTED) {
                SmartLog.i(TAG, "paintManager_retryConnect,times:2");
                tryReconnect();
            }
        } catch (InterruptedException unused) {
            SmartLog.e(TAG, "paintManager_retryConnect,sleep exception");
        }
        SmartLog.i(TAG, "tryReconnect finish");
    }

    public PaintManagerStatus paintManager_check() {
        Log.e(TAG, "paintManager_check !");
        try {
            SmartLog.i(TAG, "aicr version is: " + this.packageManager.getPackageInfo(this.PACKAGE_NAME, 0).versionCode);
            if (this.paintManagerStatus == PaintManagerStatus.CONNECTED || this.paintManagerStatus == PaintManagerStatus.UNDOWNLOADED) {
                try {
                    IPaintService iPaintService = this.mService;
                    if (iPaintService == null) {
                        Log.e(TAG, "paintManager_check: mService == null, download failed?" + String.valueOf(this.paintManagerStatus));
                        return this.paintManagerStatus;
                    }
                    int iPaintService_check = iPaintService.paintService_check();
                    if (iPaintService_check == PaintCheckStatus.STATUS_PASS) {
                        this.paintManagerStatus = PaintManagerStatus.CONNECTED;
                        SmartLog.i(TAG, "paintManagerStatus is CONNECTED");
                    } else {
                        if (iPaintService_check == PaintCheckStatus.STATUS_LOW_BATTERY) {
                            SmartLog.i(TAG, "paintManagerStatus is LOW_POWER");
                            return PaintManagerStatus.LOW_POWER;
                        }
                        if (iPaintService_check == PaintCheckStatus.STATUS_OVER_HEATING) {
                            SmartLog.i(TAG, "paintManagerStatus is OVER_HEATING");
                            return PaintManagerStatus.OVER_HEATING;
                        }
                        this.paintManagerStatus = PaintManagerStatus.UNDOWNLOADED;
                        SmartLog.i(TAG, "paintManagerStatus is UNDOWNLOADED");
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            if (this.paintManagerStatus == PaintManagerStatus.UNCONNECTED) {
                SmartLog.i(TAG, "try reconnect Service");
                tryReconnect();
            }
            Log.e(TAG, "paintManager_check" + String.valueOf(this.paintManagerStatus));
            return this.paintManagerStatus;
        } catch (PackageManager.NameNotFoundException unused) {
            SmartLog.i(TAG, "aicr not found, not support");
            return PaintManagerStatus.UNSUPPORTED;
        }
    }

    public int paintManager_init() {
        try {
            if (this.mService != null) {
                Log.e(TAG, "paintManager_init");
                return this.mService.paintService_init();
            }
            Log.e(TAG, "service not connected yet!");
            tryReconnect();
            return -1;
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int paintManager_paintWithCallback(String str, IPaintStatusCallback iPaintStatusCallback) {
        try {
            if (this.mService != null) {
                Log.e(TAG, "paintManager_paintWithCallback");
                return this.mService.paintService_paintWithCallback(512, 512, str, iPaintStatusCallback);
            }
            Log.e(TAG, "service not connected yet!");
            tryReconnect();
            return -1;
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int paintManager_getProcess(int i) {
        try {
            if (this.mService != null) {
                Log.e(TAG, "paintManager_getProcess");
                return this.mService.paintService_getProcess(i);
            }
            Log.e(TAG, "service not connected yet!");
            tryReconnect();
            return -1;
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public Bitmap paintManager_getBitmap(int i) {
        try {
            if (this.mService != null) {
                Log.e(TAG, "paintManager_getBitmap");
                return this.mService.paintService_getBitmap(i);
            }
            Log.e(TAG, "service not connected yet!");
            tryReconnect();
            return null;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int paintManager_release() {
        try {
            if (this.mService != null) {
                Log.e(TAG, "paintManager_release");
                return this.mService.paintService_release();
            }
            Log.e(TAG, "service not connected yet!");
            tryReconnect();
            return -1;
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int paintManager_paintWithCallback(String str, IPaintStatusCallback iPaintStatusCallback, String str2) {
        try {
            if (this.mService != null) {
                Log.i(TAG, "paintManager_paintWithCallback and requestID!");
                return this.mService.paintService_paintWithCallback(512, 512, str, iPaintStatusCallback);
            }
            Log.e(TAG, "service not connected yet!");
            tryReconnect();
            return -1;
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
