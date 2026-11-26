package com.xiaomi.aicr.plugin;

import android.graphics.Bitmap;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import com.xiaomi.aicr.vision.VisionAttribute;
import com.xiaomi.aicr.vision.imagesegment.SegmentResult;

/* JADX INFO: loaded from: classes4.dex */
public interface IImageSegmentService extends IInterface {

    public static class Default implements IImageSegmentService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.aicr.plugin.IImageSegmentService
        public Bitmap depthSegmentMainFunc(Bitmap bitmap) throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.aicr.plugin.IImageSegmentService
        public Bitmap depthSegmentMainFuncByFd(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.aicr.plugin.IImageSegmentService
        public String getAlgorithmVersion() throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.aicr.plugin.IImageSegmentService
        public String getDepthSegmentMainFuncVersion() throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.aicr.plugin.IImageSegmentService
        public String getSegmentMainFuncVersion() throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.aicr.plugin.IImageSegmentService
        public int initAlgorithmCachePath() throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.aicr.plugin.IImageSegmentService
        public int initPersonModel() throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.aicr.plugin.IImageSegmentService
        public int initSkyModel() throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.aicr.plugin.IImageSegmentService
        public int releaseAll() throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.aicr.plugin.IImageSegmentService
        public int releaseModel(boolean z, boolean z2) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.aicr.plugin.IImageSegmentService
        public SegmentResult segment(Bitmap bitmap, VisionAttribute visionAttribute) throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.aicr.plugin.IImageSegmentService
        public SegmentResult segmentByFd(ParcelFileDescriptor parcelFileDescriptor, VisionAttribute visionAttribute) throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.aicr.plugin.IImageSegmentService
        public SegmentResult segmentMainFunc(Bitmap bitmap, VisionAttribute visionAttribute) throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.aicr.plugin.IImageSegmentService
        public SegmentResult segmentMainFuncByFd(ParcelFileDescriptor parcelFileDescriptor, VisionAttribute visionAttribute) throws RemoteException {
            return null;
        }
    }

    Bitmap depthSegmentMainFunc(Bitmap bitmap) throws RemoteException;

    Bitmap depthSegmentMainFuncByFd(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    String getAlgorithmVersion() throws RemoteException;

    String getDepthSegmentMainFuncVersion() throws RemoteException;

    String getSegmentMainFuncVersion() throws RemoteException;

    int initAlgorithmCachePath() throws RemoteException;

    int initPersonModel() throws RemoteException;

    int initSkyModel() throws RemoteException;

    int releaseAll() throws RemoteException;

    int releaseModel(boolean z, boolean z2) throws RemoteException;

    SegmentResult segment(Bitmap bitmap, VisionAttribute visionAttribute) throws RemoteException;

    SegmentResult segmentByFd(ParcelFileDescriptor parcelFileDescriptor, VisionAttribute visionAttribute) throws RemoteException;

    SegmentResult segmentMainFunc(Bitmap bitmap, VisionAttribute visionAttribute) throws RemoteException;

    SegmentResult segmentMainFuncByFd(ParcelFileDescriptor parcelFileDescriptor, VisionAttribute visionAttribute) throws RemoteException;

    public static abstract class Stub extends Binder implements IImageSegmentService {
        private static final String DESCRIPTOR = "com.xiaomi.aicr.plugin.IImageSegmentService";
        static final int TRANSACTION_depthSegmentMainFunc = 12;
        static final int TRANSACTION_depthSegmentMainFuncByFd = 13;
        static final int TRANSACTION_getAlgorithmVersion = 8;
        static final int TRANSACTION_getDepthSegmentMainFuncVersion = 14;
        static final int TRANSACTION_getSegmentMainFuncVersion = 11;
        static final int TRANSACTION_initAlgorithmCachePath = 1;
        static final int TRANSACTION_initPersonModel = 2;
        static final int TRANSACTION_initSkyModel = 3;
        static final int TRANSACTION_releaseAll = 7;
        static final int TRANSACTION_releaseModel = 6;
        static final int TRANSACTION_segment = 4;
        static final int TRANSACTION_segmentByFd = 5;
        static final int TRANSACTION_segmentMainFunc = 9;
        static final int TRANSACTION_segmentMainFuncByFd = 10;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IImageSegmentService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IImageSegmentService)) {
                return (IImageSegmentService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    parcel.enforceInterface(DESCRIPTOR);
                    int iInitAlgorithmCachePath = initAlgorithmCachePath();
                    parcel2.writeNoException();
                    parcel2.writeInt(iInitAlgorithmCachePath);
                    return true;
                case 2:
                    parcel.enforceInterface(DESCRIPTOR);
                    int iInitPersonModel = initPersonModel();
                    parcel2.writeNoException();
                    parcel2.writeInt(iInitPersonModel);
                    return true;
                case 3:
                    parcel.enforceInterface(DESCRIPTOR);
                    int iInitSkyModel = initSkyModel();
                    parcel2.writeNoException();
                    parcel2.writeInt(iInitSkyModel);
                    return true;
                case 4:
                    parcel.enforceInterface(DESCRIPTOR);
                    SegmentResult segmentResultSegment = segment(parcel.readInt() != 0 ? (Bitmap) Bitmap.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? VisionAttribute.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    if (segmentResultSegment != null) {
                        parcel2.writeInt(1);
                        segmentResultSegment.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 5:
                    parcel.enforceInterface(DESCRIPTOR);
                    SegmentResult segmentResultSegmentByFd = segmentByFd(parcel.readInt() != 0 ? (ParcelFileDescriptor) ParcelFileDescriptor.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? VisionAttribute.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    if (segmentResultSegmentByFd != null) {
                        parcel2.writeInt(1);
                        segmentResultSegmentByFd.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 6:
                    parcel.enforceInterface(DESCRIPTOR);
                    int iReleaseModel = releaseModel(parcel.readInt() != 0, parcel.readInt() != 0);
                    parcel2.writeNoException();
                    parcel2.writeInt(iReleaseModel);
                    return true;
                case 7:
                    parcel.enforceInterface(DESCRIPTOR);
                    int iReleaseAll = releaseAll();
                    parcel2.writeNoException();
                    parcel2.writeInt(iReleaseAll);
                    return true;
                case 8:
                    parcel.enforceInterface(DESCRIPTOR);
                    String algorithmVersion = getAlgorithmVersion();
                    parcel2.writeNoException();
                    parcel2.writeString(algorithmVersion);
                    return true;
                case 9:
                    parcel.enforceInterface(DESCRIPTOR);
                    SegmentResult segmentResultSegmentMainFunc = segmentMainFunc(parcel.readInt() != 0 ? (Bitmap) Bitmap.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? VisionAttribute.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    if (segmentResultSegmentMainFunc != null) {
                        parcel2.writeInt(1);
                        segmentResultSegmentMainFunc.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 10:
                    parcel.enforceInterface(DESCRIPTOR);
                    SegmentResult segmentResultSegmentMainFuncByFd = segmentMainFuncByFd(parcel.readInt() != 0 ? (ParcelFileDescriptor) ParcelFileDescriptor.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? VisionAttribute.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    if (segmentResultSegmentMainFuncByFd != null) {
                        parcel2.writeInt(1);
                        segmentResultSegmentMainFuncByFd.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 11:
                    parcel.enforceInterface(DESCRIPTOR);
                    String segmentMainFuncVersion = getSegmentMainFuncVersion();
                    parcel2.writeNoException();
                    parcel2.writeString(segmentMainFuncVersion);
                    return true;
                case 12:
                    parcel.enforceInterface(DESCRIPTOR);
                    Bitmap bitmapDepthSegmentMainFunc = depthSegmentMainFunc(parcel.readInt() != 0 ? (Bitmap) Bitmap.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    if (bitmapDepthSegmentMainFunc != null) {
                        parcel2.writeInt(1);
                        bitmapDepthSegmentMainFunc.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 13:
                    parcel.enforceInterface(DESCRIPTOR);
                    Bitmap bitmapDepthSegmentMainFuncByFd = depthSegmentMainFuncByFd(parcel.readInt() != 0 ? (ParcelFileDescriptor) ParcelFileDescriptor.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    if (bitmapDepthSegmentMainFuncByFd != null) {
                        parcel2.writeInt(1);
                        bitmapDepthSegmentMainFuncByFd.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 14:
                    parcel.enforceInterface(DESCRIPTOR);
                    String depthSegmentMainFuncVersion = getDepthSegmentMainFuncVersion();
                    parcel2.writeNoException();
                    parcel2.writeString(depthSegmentMainFuncVersion);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IImageSegmentService {
            public static IImageSegmentService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.xiaomi.aicr.plugin.IImageSegmentService
            public int initAlgorithmCachePath() throws RemoteException {
                int iInitAlgorithmCachePath;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        iInitAlgorithmCachePath = Stub.getDefaultImpl().initAlgorithmCachePath();
                    } else {
                        parcelObtain2.readException();
                        iInitAlgorithmCachePath = parcelObtain2.readInt();
                    }
                    return iInitAlgorithmCachePath;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.plugin.IImageSegmentService
            public int initPersonModel() throws RemoteException {
                int iInitPersonModel;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        iInitPersonModel = Stub.getDefaultImpl().initPersonModel();
                    } else {
                        parcelObtain2.readException();
                        iInitPersonModel = parcelObtain2.readInt();
                    }
                    return iInitPersonModel;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.plugin.IImageSegmentService
            public int initSkyModel() throws RemoteException {
                int iInitSkyModel;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        iInitSkyModel = Stub.getDefaultImpl().initSkyModel();
                    } else {
                        parcelObtain2.readException();
                        iInitSkyModel = parcelObtain2.readInt();
                    }
                    return iInitSkyModel;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.plugin.IImageSegmentService
            public SegmentResult segment(Bitmap bitmap, VisionAttribute visionAttribute) throws RemoteException {
                SegmentResult segmentResultCreateFromParcel;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (bitmap != null) {
                        parcelObtain.writeInt(1);
                        bitmap.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (visionAttribute != null) {
                        parcelObtain.writeInt(1);
                        visionAttribute.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        segmentResultCreateFromParcel = Stub.getDefaultImpl().segment(bitmap, visionAttribute);
                    } else {
                        parcelObtain2.readException();
                        segmentResultCreateFromParcel = parcelObtain2.readInt() != 0 ? SegmentResult.CREATOR.createFromParcel(parcelObtain2) : null;
                    }
                    return segmentResultCreateFromParcel;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.plugin.IImageSegmentService
            public SegmentResult segmentByFd(ParcelFileDescriptor parcelFileDescriptor, VisionAttribute visionAttribute) throws RemoteException {
                SegmentResult segmentResultCreateFromParcel;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelFileDescriptor != null) {
                        parcelObtain.writeInt(1);
                        parcelFileDescriptor.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (visionAttribute != null) {
                        parcelObtain.writeInt(1);
                        visionAttribute.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(5, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        segmentResultCreateFromParcel = Stub.getDefaultImpl().segmentByFd(parcelFileDescriptor, visionAttribute);
                    } else {
                        parcelObtain2.readException();
                        segmentResultCreateFromParcel = parcelObtain2.readInt() != 0 ? SegmentResult.CREATOR.createFromParcel(parcelObtain2) : null;
                    }
                    return segmentResultCreateFromParcel;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.plugin.IImageSegmentService
            public int releaseModel(boolean z, boolean z2) throws RemoteException {
                int iReleaseModel;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(z ? 1 : 0);
                    parcelObtain.writeInt(z2 ? 1 : 0);
                    if (!this.mRemote.transact(6, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        iReleaseModel = Stub.getDefaultImpl().releaseModel(z, z2);
                    } else {
                        parcelObtain2.readException();
                        iReleaseModel = parcelObtain2.readInt();
                    }
                    return iReleaseModel;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.plugin.IImageSegmentService
            public int releaseAll() throws RemoteException {
                int iReleaseAll;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(7, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        iReleaseAll = Stub.getDefaultImpl().releaseAll();
                    } else {
                        parcelObtain2.readException();
                        iReleaseAll = parcelObtain2.readInt();
                    }
                    return iReleaseAll;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.plugin.IImageSegmentService
            public String getAlgorithmVersion() throws RemoteException {
                String string;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(8, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        string = Stub.getDefaultImpl().getAlgorithmVersion();
                    } else {
                        parcelObtain2.readException();
                        string = parcelObtain2.readString();
                    }
                    return string;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.plugin.IImageSegmentService
            public SegmentResult segmentMainFunc(Bitmap bitmap, VisionAttribute visionAttribute) throws RemoteException {
                SegmentResult segmentResultCreateFromParcel;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (bitmap != null) {
                        parcelObtain.writeInt(1);
                        bitmap.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (visionAttribute != null) {
                        parcelObtain.writeInt(1);
                        visionAttribute.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(9, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        segmentResultCreateFromParcel = Stub.getDefaultImpl().segmentMainFunc(bitmap, visionAttribute);
                    } else {
                        parcelObtain2.readException();
                        segmentResultCreateFromParcel = parcelObtain2.readInt() != 0 ? SegmentResult.CREATOR.createFromParcel(parcelObtain2) : null;
                    }
                    return segmentResultCreateFromParcel;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.plugin.IImageSegmentService
            public SegmentResult segmentMainFuncByFd(ParcelFileDescriptor parcelFileDescriptor, VisionAttribute visionAttribute) throws RemoteException {
                SegmentResult segmentResultCreateFromParcel;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelFileDescriptor != null) {
                        parcelObtain.writeInt(1);
                        parcelFileDescriptor.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (visionAttribute != null) {
                        parcelObtain.writeInt(1);
                        visionAttribute.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(10, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        segmentResultCreateFromParcel = Stub.getDefaultImpl().segmentMainFuncByFd(parcelFileDescriptor, visionAttribute);
                    } else {
                        parcelObtain2.readException();
                        segmentResultCreateFromParcel = parcelObtain2.readInt() != 0 ? SegmentResult.CREATOR.createFromParcel(parcelObtain2) : null;
                    }
                    return segmentResultCreateFromParcel;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.plugin.IImageSegmentService
            public String getSegmentMainFuncVersion() throws RemoteException {
                String string;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(11, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        string = Stub.getDefaultImpl().getSegmentMainFuncVersion();
                    } else {
                        parcelObtain2.readException();
                        string = parcelObtain2.readString();
                    }
                    return string;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.plugin.IImageSegmentService
            public Bitmap depthSegmentMainFunc(Bitmap bitmap) throws RemoteException {
                Bitmap bitmapDepthSegmentMainFunc;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (bitmap != null) {
                        parcelObtain.writeInt(1);
                        bitmap.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(12, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        bitmapDepthSegmentMainFunc = Stub.getDefaultImpl().depthSegmentMainFunc(bitmap);
                    } else {
                        parcelObtain2.readException();
                        bitmapDepthSegmentMainFunc = parcelObtain2.readInt() != 0 ? (Bitmap) Bitmap.CREATOR.createFromParcel(parcelObtain2) : null;
                    }
                    return bitmapDepthSegmentMainFunc;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.plugin.IImageSegmentService
            public Bitmap depthSegmentMainFuncByFd(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Bitmap bitmapDepthSegmentMainFuncByFd;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelFileDescriptor != null) {
                        parcelObtain.writeInt(1);
                        parcelFileDescriptor.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(13, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        bitmapDepthSegmentMainFuncByFd = Stub.getDefaultImpl().depthSegmentMainFuncByFd(parcelFileDescriptor);
                    } else {
                        parcelObtain2.readException();
                        bitmapDepthSegmentMainFuncByFd = parcelObtain2.readInt() != 0 ? (Bitmap) Bitmap.CREATOR.createFromParcel(parcelObtain2) : null;
                    }
                    return bitmapDepthSegmentMainFuncByFd;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.plugin.IImageSegmentService
            public String getDepthSegmentMainFuncVersion() throws RemoteException {
                String string;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(14, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        string = Stub.getDefaultImpl().getDepthSegmentMainFuncVersion();
                    } else {
                        parcelObtain2.readException();
                        string = parcelObtain2.readString();
                    }
                    return string;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IImageSegmentService iImageSegmentService) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iImageSegmentService == null) {
                return false;
            }
            Proxy.sDefaultImpl = iImageSegmentService;
            return true;
        }

        public static IImageSegmentService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
