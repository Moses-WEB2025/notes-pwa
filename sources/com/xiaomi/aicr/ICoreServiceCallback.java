package com.xiaomi.aicr;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes4.dex */
public interface ICoreServiceCallback extends IInterface {

    public static class Default implements ICoreServiceCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.aicr.ICoreServiceCallback
        public void onDownload(IBinder iBinder) throws RemoteException {
        }

        @Override // com.xiaomi.aicr.ICoreServiceCallback
        public void speed(String str, long j) throws RemoteException {
        }
    }

    void onDownload(IBinder iBinder) throws RemoteException;

    void speed(String str, long j) throws RemoteException;

    public static abstract class Stub extends Binder implements ICoreServiceCallback {
        private static final String DESCRIPTOR = "com.xiaomi.aicr.ICoreServiceCallback";
        static final int TRANSACTION_onDownload = 1;
        static final int TRANSACTION_speed = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ICoreServiceCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ICoreServiceCallback)) {
                return (ICoreServiceCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                onDownload(parcel.readStrongBinder());
                return true;
            }
            if (i == 2) {
                parcel.enforceInterface(DESCRIPTOR);
                speed(parcel.readString(), parcel.readLong());
                return true;
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ICoreServiceCallback {
            public static ICoreServiceCallback sDefaultImpl;
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

            @Override // com.xiaomi.aicr.ICoreServiceCallback
            public void onDownload(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, parcelObtain, null, 1) || Stub.getDefaultImpl() == null) {
                        return;
                    }
                    Stub.getDefaultImpl().onDownload(iBinder);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.ICoreServiceCallback
            public void speed(String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    if (this.mRemote.transact(2, parcelObtain, null, 1) || Stub.getDefaultImpl() == null) {
                        return;
                    }
                    Stub.getDefaultImpl().speed(str, j);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ICoreServiceCallback iCoreServiceCallback) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iCoreServiceCallback == null) {
                return false;
            }
            Proxy.sDefaultImpl = iCoreServiceCallback;
            return true;
        }

        public static ICoreServiceCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
