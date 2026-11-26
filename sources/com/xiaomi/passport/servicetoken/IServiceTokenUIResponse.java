package com.xiaomi.passport.servicetoken;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes4.dex */
public interface IServiceTokenUIResponse extends IInterface {
    public static final String DESCRIPTOR = "com.xiaomi.passport.servicetoken.IServiceTokenUIResponse";

    public static class Default implements IServiceTokenUIResponse {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.passport.servicetoken.IServiceTokenUIResponse
        public void onError(int i, String str) throws RemoteException {
        }

        @Override // com.xiaomi.passport.servicetoken.IServiceTokenUIResponse
        public void onRequestContinued() throws RemoteException {
        }

        @Override // com.xiaomi.passport.servicetoken.IServiceTokenUIResponse
        public void onResult(Bundle bundle) throws RemoteException {
        }
    }

    void onError(int i, String str) throws RemoteException;

    void onRequestContinued() throws RemoteException;

    void onResult(Bundle bundle) throws RemoteException;

    public static abstract class Stub extends Binder implements IServiceTokenUIResponse {
        static final int TRANSACTION_onError = 3;
        static final int TRANSACTION_onRequestContinued = 2;
        static final int TRANSACTION_onResult = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IServiceTokenUIResponse.DESCRIPTOR);
        }

        public static IServiceTokenUIResponse asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IServiceTokenUIResponse.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IServiceTokenUIResponse)) {
                return (IServiceTokenUIResponse) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IServiceTokenUIResponse.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IServiceTokenUIResponse.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onResult((Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
                parcel2.writeNoException();
            } else if (i == 2) {
                onRequestContinued();
                parcel2.writeNoException();
            } else if (i == 3) {
                onError(parcel.readInt(), parcel.readString());
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IServiceTokenUIResponse {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IServiceTokenUIResponse.DESCRIPTOR;
            }

            @Override // com.xiaomi.passport.servicetoken.IServiceTokenUIResponse
            public void onResult(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IServiceTokenUIResponse.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.passport.servicetoken.IServiceTokenUIResponse
            public void onRequestContinued() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IServiceTokenUIResponse.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.passport.servicetoken.IServiceTokenUIResponse
            public void onError(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IServiceTokenUIResponse.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }

    public static class _Parcel {
        /* JADX INFO: Access modifiers changed from: private */
        public static <T> T readTypedObject(Parcel parcel, Parcelable.Creator<T> creator) {
            if (parcel.readInt() != 0) {
                return creator.createFromParcel(parcel);
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static <T extends Parcelable> void writeTypedObject(Parcel parcel, T t, int i) {
            if (t != null) {
                parcel.writeInt(1);
                t.writeToParcel(parcel, i);
            } else {
                parcel.writeInt(0);
            }
        }
    }
}
