package com.xiaomi.e2ee.appkey;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes4.dex */
public interface IMiCloudKeyChainService extends IInterface {

    public static class Default implements IMiCloudKeyChainService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.e2ee.appkey.IMiCloudKeyChainService
        public void getAppKeyInfo(String str, String str2, IGetAppKeyCallback iGetAppKeyCallback) throws RemoteException {
        }

        @Override // com.xiaomi.e2ee.appkey.IMiCloudKeyChainService
        public void getAppKeyInfoByVersion(String str, long j, String str2, IGetAppKeyCallback iGetAppKeyCallback) throws RemoteException {
        }

        @Override // com.xiaomi.e2ee.appkey.IMiCloudKeyChainService
        public void getRemoteAppKeyInfo(String str, String str2, IGetAppKeyCallback iGetAppKeyCallback) throws RemoteException {
        }
    }

    void getAppKeyInfo(String str, String str2, IGetAppKeyCallback iGetAppKeyCallback) throws RemoteException;

    void getAppKeyInfoByVersion(String str, long j, String str2, IGetAppKeyCallback iGetAppKeyCallback) throws RemoteException;

    void getRemoteAppKeyInfo(String str, String str2, IGetAppKeyCallback iGetAppKeyCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IMiCloudKeyChainService {
        private static final String DESCRIPTOR = "com.xiaomi.e2ee.appkey.IMiCloudKeyChainService";
        static final int TRANSACTION_getAppKeyInfo = 1;
        static final int TRANSACTION_getAppKeyInfoByVersion = 2;
        static final int TRANSACTION_getRemoteAppKeyInfo = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMiCloudKeyChainService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IMiCloudKeyChainService)) {
                return (IMiCloudKeyChainService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                getAppKeyInfo(parcel.readString(), parcel.readString(), IGetAppKeyCallback.Stub.asInterface(parcel.readStrongBinder()));
                return true;
            }
            if (i == 2) {
                parcel.enforceInterface(DESCRIPTOR);
                getAppKeyInfoByVersion(parcel.readString(), parcel.readLong(), parcel.readString(), IGetAppKeyCallback.Stub.asInterface(parcel.readStrongBinder()));
                return true;
            }
            if (i == 3) {
                parcel.enforceInterface(DESCRIPTOR);
                getRemoteAppKeyInfo(parcel.readString(), parcel.readString(), IGetAppKeyCallback.Stub.asInterface(parcel.readStrongBinder()));
                return true;
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IMiCloudKeyChainService {
            public static IMiCloudKeyChainService sDefaultImpl;
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

            @Override // com.xiaomi.e2ee.appkey.IMiCloudKeyChainService
            public void getAppKeyInfo(String str, String str2, IGetAppKeyCallback iGetAppKeyCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongBinder(iGetAppKeyCallback != null ? iGetAppKeyCallback.asBinder() : null);
                    if (this.mRemote.transact(1, parcelObtain, null, 1) || Stub.getDefaultImpl() == null) {
                        return;
                    }
                    Stub.getDefaultImpl().getAppKeyInfo(str, str2, iGetAppKeyCallback);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.e2ee.appkey.IMiCloudKeyChainService
            public void getAppKeyInfoByVersion(String str, long j, String str2, IGetAppKeyCallback iGetAppKeyCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongBinder(iGetAppKeyCallback != null ? iGetAppKeyCallback.asBinder() : null);
                    if (this.mRemote.transact(2, parcelObtain, null, 1) || Stub.getDefaultImpl() == null) {
                        return;
                    }
                    Stub.getDefaultImpl().getAppKeyInfoByVersion(str, j, str2, iGetAppKeyCallback);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.e2ee.appkey.IMiCloudKeyChainService
            public void getRemoteAppKeyInfo(String str, String str2, IGetAppKeyCallback iGetAppKeyCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongBinder(iGetAppKeyCallback != null ? iGetAppKeyCallback.asBinder() : null);
                    if (this.mRemote.transact(3, parcelObtain, null, 1) || Stub.getDefaultImpl() == null) {
                        return;
                    }
                    Stub.getDefaultImpl().getRemoteAppKeyInfo(str, str2, iGetAppKeyCallback);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IMiCloudKeyChainService iMiCloudKeyChainService) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iMiCloudKeyChainService == null) {
                return false;
            }
            Proxy.sDefaultImpl = iMiCloudKeyChainService;
            return true;
        }

        public static IMiCloudKeyChainService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
