package com.xiaomi.aicr;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes4.dex */
public interface IDownloadCallback extends IInterface {

    public static class Default implements IDownloadCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.aicr.IDownloadCallback
        public void onDownloadComplete(String str) throws RemoteException {
        }

        @Override // com.xiaomi.aicr.IDownloadCallback
        public void onDownloadStateChanged(int i) throws RemoteException {
        }

        @Override // com.xiaomi.aicr.IDownloadCallback
        public void onError(int i) throws RemoteException {
        }

        @Override // com.xiaomi.aicr.IDownloadCallback
        public void onProgressUpdate(int i) throws RemoteException {
        }
    }

    void onDownloadComplete(String str) throws RemoteException;

    void onDownloadStateChanged(int i) throws RemoteException;

    void onError(int i) throws RemoteException;

    void onProgressUpdate(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IDownloadCallback {
        private static final String DESCRIPTOR = "com.xiaomi.aicr.IDownloadCallback";
        static final int TRANSACTION_onDownloadComplete = 2;
        static final int TRANSACTION_onDownloadStateChanged = 4;
        static final int TRANSACTION_onError = 3;
        static final int TRANSACTION_onProgressUpdate = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IDownloadCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IDownloadCallback)) {
                return (IDownloadCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                onProgressUpdate(parcel.readInt());
                parcel2.writeNoException();
                return true;
            }
            if (i == 2) {
                parcel.enforceInterface(DESCRIPTOR);
                onDownloadComplete(parcel.readString());
                parcel2.writeNoException();
                return true;
            }
            if (i == 3) {
                parcel.enforceInterface(DESCRIPTOR);
                onError(parcel.readInt());
                parcel2.writeNoException();
                return true;
            }
            if (i != 4) {
                if (i == 1598968902) {
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                }
                return super.onTransact(i, parcel, parcel2, i2);
            }
            parcel.enforceInterface(DESCRIPTOR);
            onDownloadStateChanged(parcel.readInt());
            parcel2.writeNoException();
            return true;
        }

        private static class Proxy implements IDownloadCallback {
            public static IDownloadCallback sDefaultImpl;
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

            @Override // com.xiaomi.aicr.IDownloadCallback
            public void onProgressUpdate(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onProgressUpdate(i);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.IDownloadCallback
            public void onDownloadComplete(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDownloadComplete(str);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.IDownloadCallback
            public void onError(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onError(i);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.IDownloadCallback
            public void onDownloadStateChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDownloadStateChanged(i);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IDownloadCallback iDownloadCallback) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iDownloadCallback == null) {
                return false;
            }
            Proxy.sDefaultImpl = iDownloadCallback;
            return true;
        }

        public static IDownloadCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
