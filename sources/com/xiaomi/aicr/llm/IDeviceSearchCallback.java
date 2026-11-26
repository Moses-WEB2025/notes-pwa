package com.xiaomi.aicr.llm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes4.dex */
public interface IDeviceSearchCallback extends IInterface {

    public static class Default implements IDeviceSearchCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.aicr.llm.IDeviceSearchCallback
        public void onConnected() throws RemoteException {
        }

        @Override // com.xiaomi.aicr.llm.IDeviceSearchCallback
        public void onDisConnected() throws RemoteException {
        }

        @Override // com.xiaomi.aicr.llm.IDeviceSearchCallback
        public void onFailed(int i) throws RemoteException {
        }
    }

    void onConnected() throws RemoteException;

    void onDisConnected() throws RemoteException;

    void onFailed(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IDeviceSearchCallback {
        private static final String DESCRIPTOR = "com.xiaomi.aicr.llm.IDeviceSearchCallback";
        static final int TRANSACTION_onConnected = 1;
        static final int TRANSACTION_onDisConnected = 2;
        static final int TRANSACTION_onFailed = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IDeviceSearchCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IDeviceSearchCallback)) {
                return (IDeviceSearchCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                onConnected();
                parcel2.writeNoException();
                return true;
            }
            if (i == 2) {
                parcel.enforceInterface(DESCRIPTOR);
                onDisConnected();
                parcel2.writeNoException();
                return true;
            }
            if (i != 3) {
                if (i == 1598968902) {
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                }
                return super.onTransact(i, parcel, parcel2, i2);
            }
            parcel.enforceInterface(DESCRIPTOR);
            onFailed(parcel.readInt());
            parcel2.writeNoException();
            return true;
        }

        private static class Proxy implements IDeviceSearchCallback {
            public static IDeviceSearchCallback sDefaultImpl;
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

            @Override // com.xiaomi.aicr.llm.IDeviceSearchCallback
            public void onConnected() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onConnected();
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.llm.IDeviceSearchCallback
            public void onDisConnected() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDisConnected();
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.llm.IDeviceSearchCallback
            public void onFailed(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onFailed(i);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IDeviceSearchCallback iDeviceSearchCallback) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iDeviceSearchCallback == null) {
                return false;
            }
            Proxy.sDefaultImpl = iDeviceSearchCallback;
            return true;
        }

        public static IDeviceSearchCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
