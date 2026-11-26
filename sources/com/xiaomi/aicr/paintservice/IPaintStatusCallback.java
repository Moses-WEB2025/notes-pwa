package com.xiaomi.aicr.paintservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes4.dex */
public interface IPaintStatusCallback extends IInterface {

    public static class Default implements IPaintStatusCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.aicr.paintservice.IPaintStatusCallback
        public void onPaintFailed(byte b) throws RemoteException {
        }

        @Override // com.xiaomi.aicr.paintservice.IPaintStatusCallback
        public void onPaintFinished() throws RemoteException {
        }
    }

    void onPaintFailed(byte b) throws RemoteException;

    void onPaintFinished() throws RemoteException;

    public static abstract class Stub extends Binder implements IPaintStatusCallback {
        private static final String DESCRIPTOR = "com.xiaomi.aicr.paintservice.IPaintStatusCallback";
        static final int TRANSACTION_onPaintFailed = 2;
        static final int TRANSACTION_onPaintFinished = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPaintStatusCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IPaintStatusCallback)) {
                return (IPaintStatusCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                onPaintFinished();
                parcel2.writeNoException();
                return true;
            }
            if (i != 2) {
                if (i == 1598968902) {
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                }
                return super.onTransact(i, parcel, parcel2, i2);
            }
            parcel.enforceInterface(DESCRIPTOR);
            onPaintFailed(parcel.readByte());
            parcel2.writeNoException();
            return true;
        }

        private static class Proxy implements IPaintStatusCallback {
            public static IPaintStatusCallback sDefaultImpl;
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

            @Override // com.xiaomi.aicr.paintservice.IPaintStatusCallback
            public void onPaintFinished() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPaintFinished();
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.paintservice.IPaintStatusCallback
            public void onPaintFailed(byte b) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeByte(b);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPaintFailed(b);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IPaintStatusCallback iPaintStatusCallback) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iPaintStatusCallback == null) {
                return false;
            }
            Proxy.sDefaultImpl = iPaintStatusCallback;
            return true;
        }

        public static IPaintStatusCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
