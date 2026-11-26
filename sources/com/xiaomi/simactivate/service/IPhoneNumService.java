package com.xiaomi.simactivate.service;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes4.dex */
public interface IPhoneNumService extends IInterface {
    public static final String DESCRIPTOR = "com.xiaomi.simactivate.service.IPhoneNumService";

    public static class Default implements IPhoneNumService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.simactivate.service.IPhoneNumService
        public Bundle blockObtainPhoneNum(int i, String str, int i2, boolean z) throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.simactivate.service.IPhoneNumService
        public boolean invalidatePhoneNum(int i, String str, int i2) throws RemoteException {
            return false;
        }

        @Override // com.xiaomi.simactivate.service.IPhoneNumService
        public boolean invalidateVerifiedToken(int i, String str, int i2) throws RemoteException {
            return false;
        }

        @Override // com.xiaomi.simactivate.service.IPhoneNumService
        public boolean isSupport(int i, String str, String str2) throws RemoteException {
            return false;
        }
    }

    Bundle blockObtainPhoneNum(int i, String str, int i2, boolean z) throws RemoteException;

    boolean invalidatePhoneNum(int i, String str, int i2) throws RemoteException;

    boolean invalidateVerifiedToken(int i, String str, int i2) throws RemoteException;

    boolean isSupport(int i, String str, String str2) throws RemoteException;

    public static abstract class Stub extends Binder implements IPhoneNumService {
        static final int TRANSACTION_blockObtainPhoneNum = 2;
        static final int TRANSACTION_invalidatePhoneNum = 3;
        static final int TRANSACTION_invalidateVerifiedToken = 4;
        static final int TRANSACTION_isSupport = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IPhoneNumService.DESCRIPTOR);
        }

        public static IPhoneNumService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IPhoneNumService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IPhoneNumService)) {
                return (IPhoneNumService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IPhoneNumService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IPhoneNumService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean zIsSupport = isSupport(parcel.readInt(), parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                parcel2.writeInt(zIsSupport ? 1 : 0);
            } else if (i == 2) {
                Bundle bundleBlockObtainPhoneNum = blockObtainPhoneNum(parcel.readInt(), parcel.readString(), parcel.readInt(), parcel.readInt() != 0);
                parcel2.writeNoException();
                _Parcel.writeTypedObject(parcel2, bundleBlockObtainPhoneNum, 1);
            } else if (i == 3) {
                boolean zInvalidatePhoneNum = invalidatePhoneNum(parcel.readInt(), parcel.readString(), parcel.readInt());
                parcel2.writeNoException();
                parcel2.writeInt(zInvalidatePhoneNum ? 1 : 0);
            } else if (i == 4) {
                boolean zInvalidateVerifiedToken = invalidateVerifiedToken(parcel.readInt(), parcel.readString(), parcel.readInt());
                parcel2.writeNoException();
                parcel2.writeInt(zInvalidateVerifiedToken ? 1 : 0);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IPhoneNumService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPhoneNumService.DESCRIPTOR;
            }

            @Override // com.xiaomi.simactivate.service.IPhoneNumService
            public boolean isSupport(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneNumService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.simactivate.service.IPhoneNumService
            public Bundle blockObtainPhoneNum(int i, String str, int i2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneNumService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) _Parcel.readTypedObject(parcelObtain2, Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.simactivate.service.IPhoneNumService
            public boolean invalidatePhoneNum(int i, String str, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneNumService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.simactivate.service.IPhoneNumService
            public boolean invalidateVerifiedToken(int i, String str, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneNumService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
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
