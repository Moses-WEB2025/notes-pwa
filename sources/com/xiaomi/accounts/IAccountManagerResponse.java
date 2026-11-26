package com.xiaomi.accounts;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes4.dex */
public interface IAccountManagerResponse extends IInterface {
    public static final String DESCRIPTOR = "com.xiaomi.accounts.IAccountManagerResponse";

    public static class Default implements IAccountManagerResponse {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.accounts.IAccountManagerResponse
        public void onError(int i, String str) throws RemoteException {
        }

        @Override // com.xiaomi.accounts.IAccountManagerResponse
        public void onRequestContinued() throws RemoteException {
        }

        @Override // com.xiaomi.accounts.IAccountManagerResponse
        public void onResult(Bundle bundle) throws RemoteException {
        }
    }

    void onError(int i, String str) throws RemoteException;

    void onRequestContinued() throws RemoteException;

    void onResult(Bundle bundle) throws RemoteException;

    public static abstract class Stub extends Binder implements IAccountManagerResponse {
        static final int TRANSACTION_onError = 3;
        static final int TRANSACTION_onRequestContinued = 2;
        static final int TRANSACTION_onResult = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IAccountManagerResponse.DESCRIPTOR);
        }

        public static IAccountManagerResponse asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IAccountManagerResponse.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAccountManagerResponse)) {
                return (IAccountManagerResponse) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAccountManagerResponse.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAccountManagerResponse.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onResult((Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
            } else if (i == 2) {
                onRequestContinued();
            } else if (i == 3) {
                onError(parcel.readInt(), parcel.readString());
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IAccountManagerResponse {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAccountManagerResponse.DESCRIPTOR;
            }

            @Override // com.xiaomi.accounts.IAccountManagerResponse
            public void onResult(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAccountManagerResponse.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.accounts.IAccountManagerResponse
            public void onRequestContinued() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAccountManagerResponse.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.accounts.IAccountManagerResponse
            public void onError(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAccountManagerResponse.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
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
