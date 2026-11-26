package com.xiaomi.e2ee.appkey;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes4.dex */
public interface IGetAppKeyCallback extends IInterface {

    public static class Default implements IGetAppKeyCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.e2ee.appkey.IGetAppKeyCallback
        public void onGetAppKeyResult(String str, int i, String str2) throws RemoteException {
        }
    }

    void onGetAppKeyResult(String str, int i, String str2) throws RemoteException;

    public static abstract class Stub extends Binder implements IGetAppKeyCallback {
        private static final String DESCRIPTOR = "com.xiaomi.e2ee.appkey.IGetAppKeyCallback";
        static final int TRANSACTION_onGetAppKeyResult = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IGetAppKeyCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IGetAppKeyCallback)) {
                return (IGetAppKeyCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1) {
                if (i == 1598968902) {
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                }
                return super.onTransact(i, parcel, parcel2, i2);
            }
            parcel.enforceInterface(DESCRIPTOR);
            onGetAppKeyResult(parcel.readString(), parcel.readInt(), parcel.readString());
            parcel2.writeNoException();
            return true;
        }

        private static class Proxy implements IGetAppKeyCallback {
            public static IGetAppKeyCallback sDefaultImpl;
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

            @Override // com.xiaomi.e2ee.appkey.IGetAppKeyCallback
            public void onGetAppKeyResult(String str, int i, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onGetAppKeyResult(str, i, str2);
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IGetAppKeyCallback iGetAppKeyCallback) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iGetAppKeyCallback == null) {
                return false;
            }
            Proxy.sDefaultImpl = iGetAppKeyCallback;
            return true;
        }

        public static IGetAppKeyCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
