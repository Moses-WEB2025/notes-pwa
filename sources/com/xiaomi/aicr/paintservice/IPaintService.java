package com.xiaomi.aicr.paintservice;

import android.graphics.Bitmap;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes4.dex */
public interface IPaintService extends IInterface {

    public static class Default implements IPaintService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.aicr.paintservice.IPaintService
        public int paintService_check() throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.aicr.paintservice.IPaintService
        public Bitmap paintService_getBitmap(int i) throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.aicr.paintservice.IPaintService
        public int paintService_getProcess(int i) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.aicr.paintservice.IPaintService
        public int paintService_init() throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.aicr.paintservice.IPaintService
        public int paintService_paintWithCallback(int i, int i2, String str, IPaintStatusCallback iPaintStatusCallback) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.aicr.paintservice.IPaintService
        public int paintService_release() throws RemoteException {
            return 0;
        }
    }

    int paintService_check() throws RemoteException;

    Bitmap paintService_getBitmap(int i) throws RemoteException;

    int paintService_getProcess(int i) throws RemoteException;

    int paintService_init() throws RemoteException;

    int paintService_paintWithCallback(int i, int i2, String str, IPaintStatusCallback iPaintStatusCallback) throws RemoteException;

    int paintService_release() throws RemoteException;

    public static abstract class Stub extends Binder implements IPaintService {
        private static final String DESCRIPTOR = "com.xiaomi.aicr.paintservice.IPaintService";
        static final int TRANSACTION_paintService_check = 1;
        static final int TRANSACTION_paintService_getBitmap = 5;
        static final int TRANSACTION_paintService_getProcess = 4;
        static final int TRANSACTION_paintService_init = 2;
        static final int TRANSACTION_paintService_paintWithCallback = 3;
        static final int TRANSACTION_paintService_release = 6;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPaintService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IPaintService)) {
                return (IPaintService) iInterfaceQueryLocalInterface;
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
                    int iPaintService_check = paintService_check();
                    parcel2.writeNoException();
                    parcel2.writeInt(iPaintService_check);
                    return true;
                case 2:
                    parcel.enforceInterface(DESCRIPTOR);
                    int iPaintService_init = paintService_init();
                    parcel2.writeNoException();
                    parcel2.writeInt(iPaintService_init);
                    return true;
                case 3:
                    parcel.enforceInterface(DESCRIPTOR);
                    int iPaintService_paintWithCallback = paintService_paintWithCallback(parcel.readInt(), parcel.readInt(), parcel.readString(), IPaintStatusCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(iPaintService_paintWithCallback);
                    return true;
                case 4:
                    parcel.enforceInterface(DESCRIPTOR);
                    int iPaintService_getProcess = paintService_getProcess(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(iPaintService_getProcess);
                    return true;
                case 5:
                    parcel.enforceInterface(DESCRIPTOR);
                    Bitmap bitmapPaintService_getBitmap = paintService_getBitmap(parcel.readInt());
                    parcel2.writeNoException();
                    if (bitmapPaintService_getBitmap != null) {
                        parcel2.writeInt(1);
                        bitmapPaintService_getBitmap.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 6:
                    parcel.enforceInterface(DESCRIPTOR);
                    int iPaintService_release = paintService_release();
                    parcel2.writeNoException();
                    parcel2.writeInt(iPaintService_release);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IPaintService {
            public static IPaintService sDefaultImpl;
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

            @Override // com.xiaomi.aicr.paintservice.IPaintService
            public int paintService_check() throws RemoteException {
                int iPaintService_check;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        iPaintService_check = Stub.getDefaultImpl().paintService_check();
                    } else {
                        parcelObtain2.readException();
                        iPaintService_check = parcelObtain2.readInt();
                    }
                    return iPaintService_check;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.paintservice.IPaintService
            public int paintService_init() throws RemoteException {
                int iPaintService_init;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        iPaintService_init = Stub.getDefaultImpl().paintService_init();
                    } else {
                        parcelObtain2.readException();
                        iPaintService_init = parcelObtain2.readInt();
                    }
                    return iPaintService_init;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.paintservice.IPaintService
            public int paintService_paintWithCallback(int i, int i2, String str, IPaintStatusCallback iPaintStatusCallback) throws RemoteException {
                int iPaintService_paintWithCallback;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(iPaintStatusCallback != null ? iPaintStatusCallback.asBinder() : null);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        iPaintService_paintWithCallback = Stub.getDefaultImpl().paintService_paintWithCallback(i, i2, str, iPaintStatusCallback);
                    } else {
                        parcelObtain2.readException();
                        iPaintService_paintWithCallback = parcelObtain2.readInt();
                    }
                    return iPaintService_paintWithCallback;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.paintservice.IPaintService
            public int paintService_getProcess(int i) throws RemoteException {
                int iPaintService_getProcess;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        iPaintService_getProcess = Stub.getDefaultImpl().paintService_getProcess(i);
                    } else {
                        parcelObtain2.readException();
                        iPaintService_getProcess = parcelObtain2.readInt();
                    }
                    return iPaintService_getProcess;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.paintservice.IPaintService
            public Bitmap paintService_getBitmap(int i) throws RemoteException {
                Bitmap bitmapPaintService_getBitmap;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(5, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        bitmapPaintService_getBitmap = Stub.getDefaultImpl().paintService_getBitmap(i);
                    } else {
                        parcelObtain2.readException();
                        bitmapPaintService_getBitmap = parcelObtain2.readInt() != 0 ? (Bitmap) Bitmap.CREATOR.createFromParcel(parcelObtain2) : null;
                    }
                    return bitmapPaintService_getBitmap;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.paintservice.IPaintService
            public int paintService_release() throws RemoteException {
                int iPaintService_release;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(6, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        iPaintService_release = Stub.getDefaultImpl().paintService_release();
                    } else {
                        parcelObtain2.readException();
                        iPaintService_release = parcelObtain2.readInt();
                    }
                    return iPaintService_release;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IPaintService iPaintService) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iPaintService == null) {
                return false;
            }
            Proxy.sDefaultImpl = iPaintService;
            return true;
        }

        public static IPaintService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
