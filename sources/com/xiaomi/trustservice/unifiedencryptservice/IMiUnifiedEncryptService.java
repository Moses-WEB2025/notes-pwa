package com.xiaomi.trustservice.unifiedencryptservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes4.dex */
public interface IMiUnifiedEncryptService extends IInterface {
    public static final String DESCRIPTOR = "com.xiaomi.trustservice.unifiedencryptservice.IMiUnifiedEncryptService";

    public static class Default implements IMiUnifiedEncryptService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.trustservice.unifiedencryptservice.IMiUnifiedEncryptService
        public int deleteAesKeyBag(String str) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.trustservice.unifiedencryptservice.IMiUnifiedEncryptService
        public int genAesKeyBag(AesKeyBag aesKeyBag) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.trustservice.unifiedencryptservice.IMiUnifiedEncryptService
        public int getAesKeyBag(String str, AesKeyBag[] aesKeyBagArr) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.trustservice.unifiedencryptservice.IMiUnifiedEncryptService
        public String getVersion() throws RemoteException {
            return null;
        }
    }

    int deleteAesKeyBag(String str) throws RemoteException;

    int genAesKeyBag(AesKeyBag aesKeyBag) throws RemoteException;

    int getAesKeyBag(String str, AesKeyBag[] aesKeyBagArr) throws RemoteException;

    String getVersion() throws RemoteException;

    public static abstract class Stub extends Binder implements IMiUnifiedEncryptService {
        static final int TRANSACTION_deleteAesKeyBag = 3;
        static final int TRANSACTION_genAesKeyBag = 2;
        static final int TRANSACTION_getAesKeyBag = 4;
        static final int TRANSACTION_getVersion = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IMiUnifiedEncryptService.DESCRIPTOR);
        }

        public static IMiUnifiedEncryptService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IMiUnifiedEncryptService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IMiUnifiedEncryptService)) {
                return (IMiUnifiedEncryptService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IMiUnifiedEncryptService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IMiUnifiedEncryptService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String version = getVersion();
                parcel2.writeNoException();
                parcel2.writeString(version);
            } else if (i == 2) {
                int iGenAesKeyBag = genAesKeyBag((AesKeyBag) _Parcel.readTypedObject(parcel, AesKeyBag.CREATOR));
                parcel2.writeNoException();
                parcel2.writeInt(iGenAesKeyBag);
            } else if (i == 3) {
                int iDeleteAesKeyBag = deleteAesKeyBag(parcel.readString());
                parcel2.writeNoException();
                parcel2.writeInt(iDeleteAesKeyBag);
            } else if (i == 4) {
                String string = parcel.readString();
                int i3 = parcel.readInt();
                AesKeyBag[] aesKeyBagArr = i3 < 0 ? null : new AesKeyBag[i3];
                int aesKeyBag = getAesKeyBag(string, aesKeyBagArr);
                parcel2.writeNoException();
                parcel2.writeInt(aesKeyBag);
                parcel2.writeTypedArray(aesKeyBagArr, 1);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IMiUnifiedEncryptService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMiUnifiedEncryptService.DESCRIPTOR;
            }

            @Override // com.xiaomi.trustservice.unifiedencryptservice.IMiUnifiedEncryptService
            public String getVersion() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMiUnifiedEncryptService.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.trustservice.unifiedencryptservice.IMiUnifiedEncryptService
            public int genAesKeyBag(AesKeyBag aesKeyBag) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMiUnifiedEncryptService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, aesKeyBag, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.trustservice.unifiedencryptservice.IMiUnifiedEncryptService
            public int deleteAesKeyBag(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMiUnifiedEncryptService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.trustservice.unifiedencryptservice.IMiUnifiedEncryptService
            public int getAesKeyBag(String str, AesKeyBag[] aesKeyBagArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMiUnifiedEncryptService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(aesKeyBagArr.length);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i = parcelObtain2.readInt();
                    parcelObtain2.readTypedArray(aesKeyBagArr, AesKeyBag.CREATOR);
                    return i;
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
