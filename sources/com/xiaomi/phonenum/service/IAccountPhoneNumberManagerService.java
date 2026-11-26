package com.xiaomi.phonenum.service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.xiaomi.phonenum.data.AccountCertification;

/* JADX INFO: loaded from: classes4.dex */
public interface IAccountPhoneNumberManagerService extends IInterface {
    public static final String DESCRIPTOR = "com.xiaomi.phonenum.service.IAccountPhoneNumberManagerService";

    public static class Default implements IAccountPhoneNumberManagerService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.phonenum.service.IAccountPhoneNumberManagerService
        public void getAccountCertifications(String str, String str2, int i, IAccountPhoneNumberResponse iAccountPhoneNumberResponse, String str3) throws RemoteException {
        }

        @Override // com.xiaomi.phonenum.service.IAccountPhoneNumberManagerService
        public void getPlainPhoneNumbers(String str, String str2, int i, IAccountPhoneNumberResponse iAccountPhoneNumberResponse, String str3) throws RemoteException {
        }

        @Override // com.xiaomi.phonenum.service.IAccountPhoneNumberManagerService
        public void getSIMInfos(String str, String str2, String[] strArr, IAccountPhoneNumberResponse iAccountPhoneNumberResponse, String str3) throws RemoteException {
        }

        @Override // com.xiaomi.phonenum.service.IAccountPhoneNumberManagerService
        public int getVersion() throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.phonenum.service.IAccountPhoneNumberManagerService
        public int invalidateAccountCertification(String str, String str2, AccountCertification accountCertification) throws RemoteException {
            return 0;
        }
    }

    void getAccountCertifications(String str, String str2, int i, IAccountPhoneNumberResponse iAccountPhoneNumberResponse, String str3) throws RemoteException;

    void getPlainPhoneNumbers(String str, String str2, int i, IAccountPhoneNumberResponse iAccountPhoneNumberResponse, String str3) throws RemoteException;

    void getSIMInfos(String str, String str2, String[] strArr, IAccountPhoneNumberResponse iAccountPhoneNumberResponse, String str3) throws RemoteException;

    int getVersion() throws RemoteException;

    int invalidateAccountCertification(String str, String str2, AccountCertification accountCertification) throws RemoteException;

    public static abstract class Stub extends Binder implements IAccountPhoneNumberManagerService {
        static final int TRANSACTION_getAccountCertifications = 2;
        static final int TRANSACTION_getPlainPhoneNumbers = 1;
        static final int TRANSACTION_getSIMInfos = 5;
        static final int TRANSACTION_getVersion = 4;
        static final int TRANSACTION_invalidateAccountCertification = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IAccountPhoneNumberManagerService.DESCRIPTOR);
        }

        public static IAccountPhoneNumberManagerService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IAccountPhoneNumberManagerService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAccountPhoneNumberManagerService)) {
                return (IAccountPhoneNumberManagerService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAccountPhoneNumberManagerService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAccountPhoneNumberManagerService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                getPlainPhoneNumbers(parcel.readString(), parcel.readString(), parcel.readInt(), IAccountPhoneNumberResponse.Stub.asInterface(parcel.readStrongBinder()), parcel.readString());
                parcel2.writeNoException();
            } else if (i == 2) {
                getAccountCertifications(parcel.readString(), parcel.readString(), parcel.readInt(), IAccountPhoneNumberResponse.Stub.asInterface(parcel.readStrongBinder()), parcel.readString());
                parcel2.writeNoException();
            } else if (i == 3) {
                int iInvalidateAccountCertification = invalidateAccountCertification(parcel.readString(), parcel.readString(), (AccountCertification) _Parcel.readTypedObject(parcel, AccountCertification.CREATOR));
                parcel2.writeNoException();
                parcel2.writeInt(iInvalidateAccountCertification);
            } else if (i == 4) {
                int version = getVersion();
                parcel2.writeNoException();
                parcel2.writeInt(version);
            } else if (i == 5) {
                getSIMInfos(parcel.readString(), parcel.readString(), parcel.createStringArray(), IAccountPhoneNumberResponse.Stub.asInterface(parcel.readStrongBinder()), parcel.readString());
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IAccountPhoneNumberManagerService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAccountPhoneNumberManagerService.DESCRIPTOR;
            }

            @Override // com.xiaomi.phonenum.service.IAccountPhoneNumberManagerService
            public void getPlainPhoneNumbers(String str, String str2, int i, IAccountPhoneNumberResponse iAccountPhoneNumberResponse, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAccountPhoneNumberManagerService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iAccountPhoneNumberResponse);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.phonenum.service.IAccountPhoneNumberManagerService
            public void getAccountCertifications(String str, String str2, int i, IAccountPhoneNumberResponse iAccountPhoneNumberResponse, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAccountPhoneNumberManagerService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iAccountPhoneNumberResponse);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.phonenum.service.IAccountPhoneNumberManagerService
            public int invalidateAccountCertification(String str, String str2, AccountCertification accountCertification) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAccountPhoneNumberManagerService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    _Parcel.writeTypedObject(parcelObtain, accountCertification, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.phonenum.service.IAccountPhoneNumberManagerService
            public int getVersion() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAccountPhoneNumberManagerService.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.phonenum.service.IAccountPhoneNumberManagerService
            public void getSIMInfos(String str, String str2, String[] strArr, IAccountPhoneNumberResponse iAccountPhoneNumberResponse, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAccountPhoneNumberManagerService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeStrongInterface(iAccountPhoneNumberResponse);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
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
