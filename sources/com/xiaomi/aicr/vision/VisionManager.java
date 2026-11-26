package com.xiaomi.aicr.vision;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import com.xiaomi.aicr.IAiCrCoreService;
import com.xiaomi.aicr.common.AiServiceConnection;
import com.xiaomi.aicr.common.ManagerBase;
import com.xiaomi.aicr.common.SmartLog;
import com.xiaomi.aicr.constant.Constants;
import com.xiaomi.aicr.nlp.NLPManager;
import com.xiaomi.aicr.plugin.IVisionService;
import com.xiaomi.aicr.vision.ocr.OCRRes;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class VisionManager extends ManagerBase {
    private static final String DESC = "com.xiaomi.aicr.plugin.IVisionService";
    private static final String TAG = "VisionManager";
    private VisionAttribute mAttr;
    private Bitmap mDataBitmap;
    private ParcelFileDescriptor mDataPfd;
    IVisionService mService;
    private boolean mSync;
    private VisionManager mVisionManager;

    public interface VisionCallback {
        void onResult(List<VisionObject> list);
    }

    @Override // com.xiaomi.aicr.common.ManagerBase
    protected void downloadSpeed(String str, long j) {
    }

    public VisionManager(Context context) {
        super(context);
        this.mSync = true;
    }

    public VisionManager(Context context, AiServiceConnection aiServiceConnection) {
        super(context, aiServiceConnection);
        this.mSync = true;
    }

    public VisionManager(Context context, AiServiceConnection aiServiceConnection, boolean z) {
        super(context, aiServiceConnection);
        this.mSync = z;
    }

    public static boolean isSupport(Context context) {
        return isSupport(context, DESC, "V0");
    }

    public static boolean isSupportAttr(Context context, int i) {
        if (i != 100663297) {
            return isSupport(context);
        }
        return isSupport(context) && NLPManager.isSupport(context);
    }

    @Override // com.xiaomi.aicr.common.ManagerBase
    protected void registerPlugin(final IAiCrCoreService iAiCrCoreService) {
        VisionManager visionManager;
        if (this.mSync) {
            try {
                Bundle bundle = new Bundle();
                visionManager = this;
                try {
                    IBinder pluginBinder = iAiCrCoreService.getPluginBinder(visionManager, super.toString(), DESC, "", bundle);
                    int i = bundle.getInt("Status");
                    if (i == Constants.STATUS_DEFINE.OK.ordinal()) {
                        visionManager.mService = IVisionService.Stub.asInterface(pluginBinder);
                    } else if (i == Constants.STATUS_DEFINE.DOWNLOADING_FIRST_USE.ordinal()) {
                        visionManager.onFirstTimeUse(DESC);
                    }
                } catch (Exception e) {
                    e = e;
                    SmartLog.d(TAG, "fail to fetch plugin service " + e);
                }
            } catch (Exception e2) {
                e = e2;
                visionManager = this;
            }
            visionManager.connected = visionManager.mService != null;
            if (visionManager.mServiceConn != null && visionManager.connected) {
                visionManager.mServiceConn.onServiceConnected(Constants.STATUS_DEFINE.OK.ordinal());
                return;
            } else {
                if (visionManager.mServiceConn == null || visionManager.connected || visionManager.isSupport(DESC)) {
                    return;
                }
                visionManager.mServiceConn.onServiceConnected(Constants.STATUS_DEFINE.UNSUPPORTED_CAPABILITY.ordinal());
                return;
            }
        }
        this.mVisionManager = this;
        new Thread(new Runnable() { // from class: com.xiaomi.aicr.vision.VisionManager.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Bundle bundle2 = new Bundle();
                    IBinder pluginBinder2 = iAiCrCoreService.getPluginBinder(VisionManager.this.mVisionManager, super.toString(), VisionManager.DESC, "", bundle2);
                    int i2 = bundle2.getInt("Status");
                    if (i2 == Constants.STATUS_DEFINE.OK.ordinal()) {
                        VisionManager.this.mService = IVisionService.Stub.asInterface(pluginBinder2);
                    } else if (i2 == Constants.STATUS_DEFINE.DOWNLOADING_FIRST_USE.ordinal()) {
                        VisionManager.this.onFirstTimeUse(VisionManager.DESC);
                    }
                } catch (Exception e3) {
                    SmartLog.d(VisionManager.TAG, "fail to fetch plugin service " + e3);
                }
                VisionManager visionManager2 = VisionManager.this;
                visionManager2.connected = visionManager2.mService != null;
                if (VisionManager.this.mServiceConn == null || !VisionManager.this.connected) {
                    if (VisionManager.this.mServiceConn == null || VisionManager.this.connected || VisionManager.this.isSupport(VisionManager.DESC)) {
                        return;
                    }
                    VisionManager.this.mServiceConn.onServiceConnected(Constants.STATUS_DEFINE.UNSUPPORTED_CAPABILITY.ordinal());
                    return;
                }
                VisionManager.this.mServiceConn.onServiceConnected(Constants.STATUS_DEFINE.OK.ordinal());
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

    private void setDataSource(ParcelFileDescriptor parcelFileDescriptor, Bitmap bitmap, VisionAttribute visionAttribute) {
        this.mDataPfd = parcelFileDescriptor;
        this.mDataBitmap = bitmap;
        this.mAttr = visionAttribute;
    }

    public VisionResult detect(ParcelFileDescriptor parcelFileDescriptor, VisionAttribute visionAttribute) {
        setDataSource(parcelFileDescriptor, null, visionAttribute);
        return detectInner();
    }

    private VisionResult detectInner() {
        int image;
        VisionResult visionResult = new VisionResult();
        if (this.mService == null) {
            SmartLog.d(TAG, "service not connected, try reconnect");
            visionResult.setStatus(Constants.STATUS_DEFINE.SERVICE_NOT_CONNECTED);
            tryReconnect();
            return visionResult;
        }
        visionResult.setRequestedAttr(this.mAttr);
        try {
            ParcelFileDescriptor parcelFileDescriptor = this.mDataPfd;
            if (parcelFileDescriptor != null) {
                image = this.mService.setImageParcelFileDesciptor(parcelFileDescriptor);
            } else {
                Bitmap bitmap = this.mDataBitmap;
                image = bitmap != null ? this.mService.setImage(bitmap) : -1;
            }
            if (image < 0) {
                visionResult.setStatus(Constants.STATUS_DEFINE.SET_IMAGE_FAIL);
                return visionResult;
            }
            ArrayList arrayList = new ArrayList();
            SmartLog.i(TAG, "detectInner: ");
            visionResult.setStatus(Constants.getStatusfromInt(this.mService.doDetect(image, arrayList, this.mAttr)));
            visionResult.setResult(null, arrayList);
            return visionResult;
        } catch (RemoteException unused) {
            visionResult.setStatus(Constants.STATUS_DEFINE.SERVICE_ERROR);
            return visionResult;
        }
    }

    public VisionResult detect(Bitmap bitmap, VisionAttribute visionAttribute) {
        setDataSource(null, bitmap, visionAttribute);
        return detectInner();
    }

    public OCRRes.OCRResult OCRDetect(Bitmap bitmap) {
        int image;
        try {
            IVisionService iVisionService = this.mService;
            String oCRVersion = iVisionService != null ? iVisionService.getOCRVersion() : null;
            if (oCRVersion == null || !oCRVersion.equals("V2")) {
                SmartLog.i(TAG, "OCRDetect is not support!");
                return null;
            }
        } catch (RemoteException e) {
            SmartLog.i(TAG, "OCRDetect exception: " + e);
        }
        setDataSource(null, bitmap, null);
        try {
            ParcelFileDescriptor parcelFileDescriptor = this.mDataPfd;
            if (parcelFileDescriptor != null) {
                image = this.mService.setImageParcelFileDesciptor(parcelFileDescriptor);
            } else {
                Bitmap bitmap2 = this.mDataBitmap;
                image = bitmap2 != null ? this.mService.setImage(bitmap2) : -1;
            }
            if (image < 0) {
                return null;
            }
            new ArrayList();
            SmartLog.i(TAG, "OCRDetect: ");
            this.mService.doOCRDetect(image);
            return this.mService.doOCRRecognize(image);
        } catch (RemoteException e2) {
            SmartLog.i(TAG, "OCRDetect exception: " + e2);
            return null;
        }
    }

    public OCRRes.OCRResult OCRDetect(ParcelFileDescriptor parcelFileDescriptor) {
        int image;
        try {
            IVisionService iVisionService = this.mService;
            String oCRVersion = iVisionService != null ? iVisionService.getOCRVersion() : null;
            if (oCRVersion == null || !oCRVersion.equals("V2")) {
                SmartLog.i(TAG, "OCRDetect is not support!");
                return null;
            }
        } catch (RemoteException e) {
            SmartLog.i(TAG, "OCRDetect exception: " + e);
        }
        setDataSource(parcelFileDescriptor, null, null);
        try {
            ParcelFileDescriptor parcelFileDescriptor2 = this.mDataPfd;
            if (parcelFileDescriptor2 != null) {
                image = this.mService.setImageParcelFileDesciptor(parcelFileDescriptor2);
            } else {
                Bitmap bitmap = this.mDataBitmap;
                image = (bitmap == null || bitmap.isRecycled()) ? -1 : this.mService.setImage(this.mDataBitmap);
            }
            if (image < 0) {
                return null;
            }
            new ArrayList();
            SmartLog.i(TAG, "OCRDetect: ");
            this.mService.doOCRDetect(image);
            return this.mService.doOCRRecognize(image);
        } catch (RemoteException e2) {
            SmartLog.i(TAG, "OCRDetect exception: " + e2);
            return null;
        }
    }
}
