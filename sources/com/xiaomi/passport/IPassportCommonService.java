package com.xiaomi.passport;

import android.accounts.Account;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.xiaomi.accountsdk.service.DeviceInfoResult;

/* JADX INFO: loaded from: classes4.dex */
public interface IPassportCommonService extends IInterface {
    public static final String DESCRIPTOR = "com.xiaomi.passport.IPassportCommonService";

    public static class Default implements IPassportCommonService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.passport.IPassportCommonService
        public DeviceInfoResult getDeviceInfo(String str, int i) throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.passport.IPassportCommonService
        public Bundle getSystemAccountData(String[] strArr) throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.passport.IPassportCommonService
        public String[] getUserData(Account account, String str, String[] strArr) throws RemoteException {
            return null;
        }

        @Override // com.xiaomi.passport.IPassportCommonService
        public void setUserData(Account account, String str, String[] strArr, String[] strArr2) throws RemoteException {
        }
    }

    DeviceInfoResult getDeviceInfo(String str, int i) throws RemoteException;

    Bundle getSystemAccountData(String[] strArr) throws RemoteException;

    String[] getUserData(Account account, String str, String[] strArr) throws RemoteException;

    void setUserData(Account account, String str, String[] strArr, String[] strArr2) throws RemoteException;

    public static abstract class Stub extends Binder implements IPassportCommonService {
        static final int TRANSACTION_getDeviceInfo = 1;
        static final int TRANSACTION_getSystemAccountData = 4;
        static final int TRANSACTION_getUserData = 2;
        static final int TRANSACTION_setUserData = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IPassportCommonService.DESCRIPTOR);
        }

        public static IPassportCommonService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IPassportCommonService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IPassportCommonService)) {
                return (IPassportCommonService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IPassportCommonService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IPassportCommonService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                DeviceInfoResult deviceInfo = getDeviceInfo(parcel.readString(), parcel.readInt());
                parcel2.writeNoException();
                _Parcel.writeTypedObject(parcel2, deviceInfo, 1);
            } else if (i == 2) {
                String[] userData = getUserData((Account) _Parcel.readTypedObject(parcel, Account.CREATOR), parcel.readString(), parcel.createStringArray());
                parcel2.writeNoException();
                parcel2.writeStringArray(userData);
            } else if (i == 3) {
                setUserData((Account) _Parcel.readTypedObject(parcel, Account.CREATOR), parcel.readString(), parcel.createStringArray(), parcel.createStringArray());
                parcel2.writeNoException();
            } else if (i == 4) {
                Bundle systemAccountData = getSystemAccountData(parcel.createStringArray());
                parcel2.writeNoException();
                _Parcel.writeTypedObject(parcel2, systemAccountData, 1);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IPassportCommonService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPassportCommonService.DESCRIPTOR;
            }

            @Override // com.xiaomi.passport.IPassportCommonService
            public DeviceInfoResult getDeviceInfo(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPassportCommonService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (DeviceInfoResult) _Parcel.readTypedObject(parcelObtain2, DeviceInfoResult.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.passport.IPassportCommonService
            public String[] getUserData(Account account, String str, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPassportCommonService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, account, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.passport.IPassportCommonService
            public void setUserData(Account account, String str, String[] strArr, String[] strArr2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPassportCommonService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, account, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeStringArray(strArr2);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.passport.IPassportCommonService
            public Bundle getSystemAccountData(String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPassportCommonService.DESCRIPTOR);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) _Parcel.readTypedObject(parcelObtain2, Bundle.CREATOR);
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
