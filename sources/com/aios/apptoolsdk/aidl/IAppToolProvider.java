package com.aios.apptoolsdk.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
public interface IAppToolProvider extends IInterface {
    public static final String DESCRIPTOR = "com.aios.apptoolsdk.aidl.IAppToolProvider";

    public static class Default implements IAppToolProvider {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.aios.apptoolsdk.aidl.IAppToolProvider
        public String handleRequest(String str) throws RemoteException {
            return null;
        }
    }

    String handleRequest(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IAppToolProvider {
        static final int TRANSACTION_handleRequest = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IAppToolProvider.DESCRIPTOR);
        }

        public static IAppToolProvider asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IAppToolProvider.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAppToolProvider)) {
                return (IAppToolProvider) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAppToolProvider.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAppToolProvider.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String strHandleRequest = handleRequest(parcel.readString());
                parcel2.writeNoException();
                parcel2.writeString(strHandleRequest);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IAppToolProvider {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAppToolProvider.DESCRIPTOR;
            }

            @Override // com.aios.apptoolsdk.aidl.IAppToolProvider
            public String handleRequest(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAppToolProvider.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
