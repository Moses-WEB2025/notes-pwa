package com.xiaomi.aicr.vision.imagesegment;

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
import com.xiaomi.aicr.plugin.IImageSegmentService;
import com.xiaomi.aicr.vision.VisionAttribute;

/* JADX INFO: loaded from: classes4.dex */
public class ImageSegmentManager extends ManagerBase {
    private static final String DESC = "com.xiaomi.aicr.plugin.IImageSegmentService";
    private static final String TAG = "ImageSegmentManager";
    IImageSegmentService mService;

    @Override // com.xiaomi.aicr.common.ManagerBase
    protected void downloadSpeed(String str, long j) {
    }

    public ImageSegmentManager(Context context) {
        this(context, null);
    }

    public ImageSegmentManager(Context context, AiServiceConnection aiServiceConnection) {
        super(context, aiServiceConnection);
    }

    public static boolean isSupport(Context context) {
        return isSupport(context, DESC, "V0");
    }

    @Override // com.xiaomi.aicr.common.ManagerBase
    protected void registerPlugin(IAiCrCoreService iAiCrCoreService) {
        ImageSegmentManager imageSegmentManager;
        try {
            Bundle bundle = new Bundle();
            imageSegmentManager = this;
            try {
                IBinder pluginBinder = iAiCrCoreService.getPluginBinder(imageSegmentManager, super.toString(), DESC, "5.0", bundle);
                int i = bundle.getInt("Status");
                if (i == Constants.STATUS_DEFINE.OK.ordinal()) {
                    imageSegmentManager.mService = IImageSegmentService.Stub.asInterface(pluginBinder);
                } else if (i == Constants.STATUS_DEFINE.DOWNLOADING_FIRST_USE.ordinal()) {
                    imageSegmentManager.onFirstTimeUse(DESC);
                }
            } catch (Exception e) {
                e = e;
                SmartLog.d(TAG, "fail to fetch plugin service " + e);
            }
        } catch (Exception e2) {
            e = e2;
            imageSegmentManager = this;
        }
        imageSegmentManager.connected = imageSegmentManager.mService != null;
        if (imageSegmentManager.mServiceConn != null && imageSegmentManager.connected) {
            imageSegmentManager.mServiceConn.onServiceConnected(Constants.STATUS_DEFINE.OK.ordinal());
        } else {
            if (imageSegmentManager.mServiceConn == null || imageSegmentManager.connected || imageSegmentManager.isSupport(DESC)) {
                return;
            }
            imageSegmentManager.mServiceConn.onServiceConnected(Constants.STATUS_DEFINE.UNSUPPORTED_CAPABILITY.ordinal());
        }
    }

    @Override // com.xiaomi.aicr.common.ManagerBase
    protected void unregisterPlugin() {
        SmartLog.d(TAG, "Service 断开连接");
        if (this.mServiceConn != null) {
            this.mServiceConn.onServiceDisconnected();
        }
    }

    public int initAlgorithmCachePath() {
        try {
            SmartLog.d(TAG, "initAlgorithmCachePath");
            IImageSegmentService iImageSegmentService = this.mService;
            if (iImageSegmentService != null) {
                return iImageSegmentService.initAlgorithmCachePath();
            }
            return -1;
        } catch (Exception e) {
            SmartLog.e(TAG, "initAlgorithmCachePath exception: " + e);
            return -1;
        }
    }

    public int initPersonModel() throws RemoteException {
        SmartLog.d(TAG, "initPersonModel");
        IImageSegmentService iImageSegmentService = this.mService;
        if (iImageSegmentService != null) {
            return iImageSegmentService.initPersonModel();
        }
        return -1;
    }

    public int initSkyModel() throws RemoteException {
        SmartLog.d(TAG, "initSkyModel");
        IImageSegmentService iImageSegmentService = this.mService;
        if (iImageSegmentService != null) {
            return iImageSegmentService.initSkyModel();
        }
        return -1;
    }

    public SegmentResult segment(Bitmap bitmap, VisionAttribute visionAttribute) {
        SmartLog.d(TAG, "segment bitmap");
        if (bitmap == null || visionAttribute == null) {
            throw new NullPointerException("bitmap or attr is null");
        }
        try {
            IImageSegmentService iImageSegmentService = this.mService;
            if (iImageSegmentService != null) {
                return iImageSegmentService.segment(bitmap, visionAttribute);
            }
            return null;
        } catch (RemoteException e) {
            SmartLog.e(TAG, "segment exception: " + e);
            return null;
        }
    }

    public SegmentResult segmentByFd(ParcelFileDescriptor parcelFileDescriptor, VisionAttribute visionAttribute) {
        SmartLog.d(TAG, "segmentByFd FileDescriptor");
        if (parcelFileDescriptor == null || visionAttribute == null) {
            throw new NullPointerException("fd or attr is null");
        }
        try {
            IImageSegmentService iImageSegmentService = this.mService;
            if (iImageSegmentService != null) {
                return iImageSegmentService.segmentByFd(parcelFileDescriptor, visionAttribute);
            }
            return null;
        } catch (RemoteException e) {
            SmartLog.e(TAG, "segment exception: " + e);
            return null;
        }
    }

    public int releaseModel(boolean z, boolean z2) throws RemoteException {
        SmartLog.d(TAG, "releaseModel");
        IImageSegmentService iImageSegmentService = this.mService;
        if (iImageSegmentService != null) {
            return iImageSegmentService.releaseModel(z, z2);
        }
        return -1;
    }

    public int releaseAll() throws RemoteException {
        SmartLog.d(TAG, "releaseAll");
        IImageSegmentService iImageSegmentService = this.mService;
        if (iImageSegmentService != null) {
            return iImageSegmentService.releaseAll();
        }
        return -1;
    }

    public String getAlgorithmVersion() throws RemoteException {
        SmartLog.d(TAG, "getAlgorithmVersion");
        IImageSegmentService iImageSegmentService = this.mService;
        return iImageSegmentService != null ? iImageSegmentService.getAlgorithmVersion() : "-1";
    }

    public SegmentResult segmentMainFunc(Bitmap bitmap, VisionAttribute visionAttribute) {
        SmartLog.d(TAG, "segmentMainFunc");
        if (bitmap == null || visionAttribute == null) {
            throw new NullPointerException("bitmap or attr is null");
        }
        try {
            IImageSegmentService iImageSegmentService = this.mService;
            String segmentMainFuncVersion = iImageSegmentService != null ? iImageSegmentService.getSegmentMainFuncVersion() : null;
            if (segmentMainFuncVersion != null && segmentMainFuncVersion.equals("V2")) {
                IImageSegmentService iImageSegmentService2 = this.mService;
                if (iImageSegmentService2 != null) {
                    return iImageSegmentService2.segmentMainFunc(bitmap, visionAttribute);
                }
                return null;
            }
            if (initAlgorithmCachePath() != 0) {
                SmartLog.e(TAG, "segmentMainFunc initAlgorithmCachePath fail");
                return null;
            }
            if (initPersonModel() != 0) {
                SmartLog.e(TAG, "segmentMainFunc initPersonModel fail");
                return null;
            }
            if (initSkyModel() != 0) {
                SmartLog.e(TAG, "segmentMainFunc initSkyModel fail");
                return null;
            }
            return segment(bitmap, visionAttribute);
        } catch (RemoteException e) {
            SmartLog.e(TAG, "segmentMainFunc exception: " + e);
            return null;
        }
    }

    public SegmentResult segmentMainFuncByFd(ParcelFileDescriptor parcelFileDescriptor, VisionAttribute visionAttribute) {
        SmartLog.d(TAG, "segmentMainFuncByFd");
        if (parcelFileDescriptor == null || visionAttribute == null) {
            throw new NullPointerException("bitmap or attr is null");
        }
        try {
            IImageSegmentService iImageSegmentService = this.mService;
            String segmentMainFuncVersion = iImageSegmentService != null ? iImageSegmentService.getSegmentMainFuncVersion() : null;
            if (segmentMainFuncVersion != null && segmentMainFuncVersion.equals("V2")) {
                IImageSegmentService iImageSegmentService2 = this.mService;
                if (iImageSegmentService2 != null) {
                    return iImageSegmentService2.segmentMainFuncByFd(parcelFileDescriptor, visionAttribute);
                }
                return null;
            }
            if (initAlgorithmCachePath() != 0) {
                SmartLog.e(TAG, "segmentMainFuncByFd initAlgorithmCachePath fail");
                return null;
            }
            if (initPersonModel() != 0) {
                SmartLog.e(TAG, "segmentMainFuncByFd initPersonModel fail");
                return null;
            }
            if (initSkyModel() != 0) {
                SmartLog.e(TAG, "segmentMainFuncByFd initSkyModel fail");
                return null;
            }
            return segmentByFd(parcelFileDescriptor, visionAttribute);
        } catch (RemoteException e) {
            SmartLog.e(TAG, "segmentMainFuncByFd exception: " + e);
            return null;
        }
    }

    public String getSegmentMainFuncVersion() {
        SmartLog.d(TAG, "getSegmentMainFuncVersion");
        try {
            IImageSegmentService iImageSegmentService = this.mService;
            if (iImageSegmentService != null) {
                return iImageSegmentService.getSegmentMainFuncVersion();
            }
            return null;
        } catch (RemoteException e) {
            SmartLog.e(TAG, "getSegmentMainFuncVersion exception: " + e);
            return null;
        }
    }

    public Bitmap depth_segmentMainFunc(Bitmap bitmap) {
        SmartLog.d(TAG, "depth_segmentMainFunc");
        if (bitmap == null) {
            throw new NullPointerException("bitmap is null");
        }
        try {
            IImageSegmentService iImageSegmentService = this.mService;
            if (iImageSegmentService != null) {
                return iImageSegmentService.depthSegmentMainFunc(bitmap);
            }
            return null;
        } catch (RemoteException e) {
            SmartLog.e(TAG, "depth_segment exception: " + e);
            return null;
        }
    }

    public Bitmap depth_segmentMainFuncByFd(ParcelFileDescriptor parcelFileDescriptor) {
        SmartLog.d(TAG, "depth_segmentMainFunc");
        if (parcelFileDescriptor == null) {
            throw new NullPointerException("fd is null");
        }
        try {
            IImageSegmentService iImageSegmentService = this.mService;
            if (iImageSegmentService != null) {
                return iImageSegmentService.depthSegmentMainFuncByFd(parcelFileDescriptor);
            }
            return null;
        } catch (RemoteException e) {
            SmartLog.e(TAG, "depth_segment exception: " + e);
            return null;
        }
    }

    public String depth_getSegmentMainFuncVersion() {
        SmartLog.d(TAG, "depth_getSegmentMainFuncVersion");
        try {
            IImageSegmentService iImageSegmentService = this.mService;
            if (iImageSegmentService != null) {
                return iImageSegmentService.getDepthSegmentMainFuncVersion();
            }
            return null;
        } catch (RemoteException e) {
            SmartLog.e(TAG, "depth_getSegmentMainFuncVersion exception: " + e);
            return null;
        }
    }
}
