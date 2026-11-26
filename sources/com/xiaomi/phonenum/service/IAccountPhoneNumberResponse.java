package com.xiaomi.phonenum.service;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes4.dex */
public interface IAccountPhoneNumberResponse extends IInterface {
    public static final String DESCRIPTOR = "com.xiaomi.phonenum.service.IAccountPhoneNumberResponse";

    public static class Default implements IAccountPhoneNumberResponse {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.phonenum.service.IAccountPhoneNumberResponse
        public void onError(int i, String str) throws RemoteException {
        }

        @Override // com.xiaomi.phonenum.service.IAccountPhoneNumberResponse
        public void onResult(Bundle bundle) throws RemoteException {
        }
    }

    void onError(int i, String str) throws RemoteException;

    void onResult(Bundle bundle) throws RemoteException;

    public static abstract class Stub extends Binder implements IAccountPhoneNumberResponse {
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onResult = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IAccountPhoneNumberResponse.DESCRIPTOR);
        }

        public static IAccountPhoneNumberResponse asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IAccountPhoneNumberResponse.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAccountPhoneNumberResponse)) {
                return (IAccountPhoneNumberResponse) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAccountPhoneNumberResponse.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAccountPhoneNumberResponse.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onResult((Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
            } else if (i == 2) {
                onError(parcel.readInt(), parcel.readString());
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IAccountPhoneNumberResponse {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAccountPhoneNumberResponse.DESCRIPTOR;
            }

            @Override // com.xiaomi.phonenum.service.IAccountPhoneNumberResponse
            public void onResult(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAccountPhoneNumberResponse.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.phonenum.service.IAccountPhoneNumberResponse
            public void onError(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAccountPhoneNumberResponse.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, null, 1);
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
